import kotlin.contracts.Effect
import kotlin.random.Random

class Item(
    val id: String,
    val name: String,
    val description: String,
    val value: Int = 0,
    val useEffect: (Player) -> Unit = {} // Функция использования
){
    fun use(player: Player){
        println("Используется $name")
        useEffect(player)
    }

    fun displayInfo(){
        println("$name - $description (Ценность: $value)")
    }
}

class Inventory{
    // mutableListOf<Item>() - создает пустой изменяймый список в который можно положить только предметы (Item)
    // private - оозначает, что доступ к списку предметов, есть только внутри класса инвенторя
    private val items = mutableListOf<Item>()

    fun addItem(item: Item){
        // .add(элемент) - метод для добавления элемента в конец списка
        items.add(item)
        println("Предмет '${item.name}' добавлен в инвентарь ")
    }

    fun removeItem(index: Int): Boolean{
        // index in 0 until items.size - проверяет находится ли index в диапозоне от 0 до размера списка (не Включительно)
        if (index in 0 until items.size){
            val removedItem = items.removeAt(index) // удаляет элемент по указаному индексу
            println("Предмет ${removedItem.name} удален из ивентаря")
            return true
        }
        println("Неверный индекс предмета! Такого нет в инвентаре")
        return false
    }

    fun useItem(index: Int, player: Player): Boolean{
        if (index in 0 until items.size){
            val item = items[index]
            item.use(player)
            items.removeAt(index)
            return true
        }
        println("Неверный индекс предмета! Такого нет в инвентаре")
        return false
    }

    fun display(){
        if (items.isEmpty()){
            println("Инвентарь пуст")
        }else{
            println("\n=== Инвентарь ===")
            // { index, item -> ...} - лямбда выражения с параметром index и item (функция без названия)
            items.forEachIndexed { index, item ->
                println("${index + 1}. ${item.name} - ${item.description}")
            }
            println("Всего предметов ${items.size}")
        }
    }

    fun findItemById(itemId: String): Item? {
        // .find{} ищет первый элемент удоблетворяющий условию поиска
        // it - ключевое слово, обозначающие текущи1 элемент в поиске
        // ? - функция может вернуть null (ничего), если ничего не найдено
        return items.find { it.id == itemId }
    }

    fun hasItem(itemId: String): Boolean{
        // .any {} - вернет true, если хотя бы один элемент удоблетворяет поиску
        return items.any{ it.id == itemId}
    }

    fun countItems(itemId: String): Int{
        return items.count{ it.id == itemId}
    }
}

open class Character(val name: String, var health: Int, val attack: Int){
    val isAlive: Boolean get() = health > 0

    open fun takeDamage(damage: Int){
        health -= damage
        println("$name получает $damage")
        if(health <= 0) println("$name пал в бою!")
    }

    fun attack(target: Character){
        if (!isAlive || !target.isAlive) return
        val damage = Random.nextInt(attack - 3, attack + 4) // Случайный урон в диапозоне
        println("$name атакует ${target.name}")
        target.takeDamage(damage)
    }
}

// Класс квест
class Quest(
    val id: String,
    val name: String,
    val description: String,
    val requiredItemId: String? = null, // ID предмета необходимого для выполнения квеста (может быть null - тд не требовать)
    val rewardGold: Int = 0,
    val rewardItem: Item? = null
){
    var isCompleted: Boolean = false
    var isActive: Boolean = false

    fun checkCompletion(player: Player): Boolean{
        if (!isCompleted && isActive){
            // если квест требует предмет проверяем его наличие у игрока
            if (requiredItemId != null && player.inventory.hasItem(requiredItemId)){
                completeQuest(player)
                return true
            }
        }
        return false
    }

    private fun completeQuest(player: Player){
        isCompleted = true
        isActive = false

        println("\n*** КВЕСТ $name ВЫПОЛНЕН ***")
        println("Награда: ")

        if (rewardGold > 0){
            println("- Золото: $rewardGold")
            // В реальной игре тут будет добавление золота нашему игроку
        }

        if (rewardItem != null){
            println("- Предмет: ${rewardItem.name}")
            player.inventory.addItem(rewardItem)
        }
    }

    fun displayInfo(){
        val status = when{
            isCompleted -> "Выполнен"
            isActive -> "Активен"
            else -> "Не активен"
        }
        println("[$status] $name: $description")
    }
}

class QuestManager{
    // mutableMapOf<String, Quest>() - создаем изменяемый словарь с квестами, где:
    // String - типом данных ключа (id квеста)
    // Quest - тип значения (объект Квеста)
    private val quests = mutableMapOf<String, Quest>()

    fun addQuest(quest: Quest){
        // quests[quest.id] = quest - добавляет в словарь по ключу quest.id
        quests[quest.id] = quest
    }

    fun getQuest(questId: String): Quest? {
        return quests[questId]
    }
}

class Player(
    name: String,
    health: Int,
    attack: Int
): Character(name, health, attack){
    val inventory = Inventory()

    fun usePoisen(){
        // Используем поиск по id зелье здоровье
        val potion = inventory.findItemById("health_potion")
        if (potion != null){
            potion.use(this) // this - ссылка на текущий объект Player
        }else{
            println("У вас нет зелий здоровья!")
        }
    }

    fun pickUpItem(item: Item){
        inventory.addItem(item)
    }

    fun showInventory(){
        inventory.display()
    }
}

fun main(){
    println("=== Система Инвенторя ===")

    val player = Player("Олег", 100, 15)

    val healthPotion = Item(
        "health_potion",
        "Зелье Здоровье",
        "Восстанавливает 30HP",
        25,
        {player ->
            player.health += 30
            println("${player.name} востанавливает 30HP!")
        }
    )
    val strenghtPotion = Item(
        "strenght_potion",
        "Зелье силы",
        "Увеличивает урон на 10 (на 3 хода)",
        40,
        { player ->
            println("${player.name} вы чуствуете прилив сил (эффект временный)")
        }
    )

    val oldKey = Item(
        "old_key",
        "Старый ключ",
        "может открыть что-то",
        5
    )// useEffect не указан - он по умолчанию остается {}

    println("=== Игра Началась, Минута.... ПОШЛА!")

    println("Игрок нашел предмет")
    // Добавление предметов в инвентарь

    player.pickUpItem(healthPotion)
    player.pickUpItem(strenghtPotion)
    player.pickUpItem(oldKey)
    player.pickUpItem(healthPotion)

    player.showInventory()

    println("--- Использование предметов ---")
    player.inventory.useItem(0, player)

    println("Поиск предметов")
    val foundKey = player.inventory.findItemById("old_key")
    if (foundKey != null){
        println("Вы открываете дверь. Но ключ рассыпается у вас в руках")
    }else{
        println("Мне нужен ключ от этой двери")
    }

    if (player.inventory.hasItem("health_potion")){
        println("Вы можете похилиться у вас есть зелье здровье: ${player.inventory.countItems("health_potion")} штук(а)")
    }


}














