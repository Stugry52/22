//import kotlin.contracts.Effect
//import kotlin.random.Random
//
//class Item(
//    val id: String,
//    val name: String,
//    val description: String,
//    val value: Int = 0,
//    val useEffect: (Player) -> Unit = {} // Функция использования
//){
//    fun use(player: Player){
//        println("Используется $name")
//        useEffect(player)
//    }
//
//    fun displayInfo(){
//        println("$name - $description (Ценность: $value)")
//    }
//}
//
//class Inventory{
//    // mutableListOf<Item>() - создает пустой изменяймый список в который можно положить только предметы (Item)
//    // private - означает, что доступ к списку предметов, есть только внутри класса инвенторя
//    val items = mutableListOf<Item>()
//
//    fun addItem(item: Item){
//        // .add(элемент) - метод для добавления элемента в конец списка
//        items.add(item)
//        println("Предмет '${item.name}' добавлен в инвентарь ")
//    }
//
//    fun removeItem(index: Int): Boolean{
//        // index in 0 until items.size - проверяет находится ли index в диапозоне от 0 до размера списка (не Включительно)
//        if (index in 0 until items.size){
//            val removedItem = items.removeAt(index) // удаляет элемент по указаному индексу
//            println("Предмет ${removedItem.name} удален из ивентаря")
//            return true
//        }
//        println("Неверный индекс предмета! Такого нет в инвентаре")
//        return false
//    }
//
//    fun useItem(index: Int, player: Player): Boolean{
//        if (index in 0 until items.size){
//            val item = items[index]
//            item.use(player)
//            items.removeAt(index)
//            return true
//        }
//        println("Неверный индекс предмета! Такого нет в инвентаре")
//        return false
//    }
//
//    fun display(){
//        if (items.isEmpty()){
//            println("Инвентарь пуст")
//        }else{
//            println("\n=== Инвентарь ===")
//            // { index, item -> ...} - лямбда выражения с параметром index и item (функция без названия)
//            items.forEachIndexed { index, item ->
//                println("${index + 1}. ${item.name} - ${item.description}")
//            }
//            println("Всего предметов ${items.size}")
//        }
//    }
//
//    fun findItemById(itemId: String): Item? {
//        // .find{} ищет первый элемент удоблетворяющий условию поиска
//        // it - ключевое слово, обозначающие текущи1 элемент в поиске
//        // ? - функция может вернуть null (ничего), если ничего не найдено
//        return items.find { it.id == itemId }
//    }
//
//    fun hasItem(itemId: String): Boolean{
//        // .any {} - вернет true, если хотя бы один элемент удоблетворяет поиску
//        return items.any{ it.id == itemId}
//    }
//
//    fun countItems(itemId: String): Int{
//        return items.count{ it.id == itemId}
//    }
//}
//
//open class Character(val name: String, var health: Int, val attack: Int){
//    val isAlive: Boolean get() = health > 0
//
//    open fun takeDamage(damage: Int){
//        health -= damage
//        println("$name получает $damage")
//        if(health <= 0) println("$name пал в бою!")
//    }
//
//    open fun attack(target: Character){
//        if (!isAlive || !target.isAlive) return
//        val damage = Random.nextInt(attack - 3, attack + 4) // Случайный урон в диапозоне
//        println("$name атакует ${target.name}")
//        target.takeDamage(damage)
//    }
//}
//
//// Класс квест
//class Quest(
//    val id: String,
//    val name: String,
//    val description: String,
//    val requiredItemId: String? = null, // ID предмета необходимого для выполнения квеста (может быть null - тд не требовать)
//    val rewardGold: Int = 0,
//    val rewardItem: Item? = null
//){
//    var isCompleted: Boolean = false
//    var isActive: Boolean = false
//
//    fun checkCompletion(player: Player): Boolean{
//        if (!isCompleted && isActive){
//            // если квест требует предмет проверяем его наличие у игрока
//            if (requiredItemId != null && player.inventory.hasItem(requiredItemId)){
//                completeQuest(player)
//                return true
//            }
//        }
//        return false
//    }
//
//    private fun completeQuest(player: Player){
//        isCompleted = true
//        isActive = false
//
//        println("\n*** КВЕСТ $name ВЫПОЛНЕН ***")
//        println("Награда: ")
//
//        if (rewardGold > 0){
//            println("- Золото: $rewardGold")
//            // В реальной игре тут будет добавление золота нашему игроку
//        }
//
//        if (rewardItem != null){
//            println("- Предмет: ${rewardItem.name}")
//            player.inventory.addItem(rewardItem)
//        }
//    }
//
//    fun displayInfo(){
//        val status = when{
//            isCompleted -> "Выполнен"
//            isActive -> "Активен"
//            else -> "Не активен"
//        }
//        println("[$status] $name: $description")
//    }
//}
//
//class QuestManager{
//    // mutableMapOf<String, Quest>() - создаем изменяемый словарь с квестами, где:
//    // String - типом данных ключа (id квеста)
//    // Quest - тип значения (объект Квеста)
//    private val quests = mutableMapOf<String, Quest>()
//
//    fun addQuest(quest: Quest){
//        // quests[quest.id] = quest - добавляет в словарь по ключу quest.id
//        quests[quest.id] = quest
//    }
//
//    fun getQuest(questId: String): Quest? {
//        return quests[questId]
//    }
//
//    fun startQuest(questId: String): Boolean{
//        val quest = quests[questId]
//        if (quest != null && !quest.isCompleted){
//            quest.isActive = true
//            println("Квест активирован: ${quest.name}")
//            return true
//        }
//        return false
//    }
//    // Функция проверки выполнения всех активных квестов
//    fun checkAllQuests(player: Player){
//        // .values - получает все значения словаря (все квесты)
//        // .filter { } - фильтрует только активные квесты
//        quests.values.filter { it.isActive }.forEach { quest ->
//            quest.checkCompletion(player)
//        }
//    }
//
//    fun displayQuest(){
//        if (quests.isEmpty()){
//            println("Список квестов пуст")
//        }else{
//            println("\n=== Журнал Квестов ===")
//            // Перебор всех значение словаря квестов
//            quests.values.forEach { quest ->
//                quest.displayInfo()
//            }
//        }
//    }
//
//    fun getActiveQuests(): List<Quest>{
//        // .toList - преобразует в изменяймый список
//        return quests.values.filter { it.isActive }.toList()
//    }
//}
//
//class NPC(val name: String, val description: String){
//    // mutableMapOf<String, String> - словарь диалогов
//    // Ключ - фраза игрока, Значение - ответ NPS
//    private val dialoques = mutableMapOf<String, String>()
//
//    fun addDialogue(playerPhrase: String, npsResponse: String){
//        dialoques[playerPhrase] = npsResponse
//    }
//
//    fun talk(){
//        println("\n=== Диалог с $name ===")
//        println("$name: $description")
//
//        if (dialoques.isEmpty()){
//            println("$name не о чем с вами говорить")
//            return
//        }
//
//        // Показываем варианты ответов игрока
//        println("\n Варианты ответов:")
//        dialoques.keys.forEachIndexed { index, phrase ->
//            println("${index + 1}. $phrase")
//        }
//        println("${dialoques.size + 1}. Уйти")
//        // Обработка ввода игрока
//        println("Выберите реплику:")
//        val choise = readln().toIntOrNull() ?: 0
//
//        if (choise in 1 .. dialoques.size){
//            // Преобразуем ключи в список и берем по индексу
//            val playerPhrase = dialoques.keys.toList()[choise - 1]
//            val npcResponse  = dialoques[playerPhrase] // Получаем ответ NPC по ключу (фразе игрока)
//
//            println("\nВы: $playerPhrase")
//            println("$name: $npcResponse")
//        }else{
//            println("Вы прощаетесь с $name")
//        }
//    }
//}
//
//class Player(
//    name: String,
//    health: Int,
//    attack: Int,
//    var gold: Int
//): Character(name, health, attack){
//    val inventory = Inventory()
//    val maxHealth: Int = 100
//    var isPowered: Boolean = false
//
//    val questManager = QuestManager()
//
//    fun usePoisen(){
//        // Используем поиск по id зелье здоровье
//        val potion = inventory.findItemById("health_potion")
//        if (potion != null){
//            potion.use(this) // this - ссылка на текущий объект Player
//        }else{
//            println("У вас нет зелий здоровья!")
//        }
//    }
//
//    fun pickUpItem(item: Item){
//        inventory.addItem(item)
//    }
//
//    fun showInventory(){
//        inventory.display()
//    }
//
//    override fun attack(target: Character) {
//        if (isPowered == true){
//            val damage = attack + 10
//            target.takeDamage(damage)
//        }else{
//            val damage = Random.nextInt(attack - 3, attack + 4) // Случайный урон в диапозоне
//            println("$name атакует ${target.name}")
//            target.takeDamage(damage)
//        }
//    }
//
//
//}
//
//class Shop(val name: String, val description: String){
//    private val itemsForSale = mutableMapOf<Item, Int>()
//
//    private val buyPrices = mutableMapOf<String, Int>()
//
//    fun addItem(item: Item, price: Int){
//        itemsForSale[item] = price
//        buyPrices[item.id] = (price * 0.6).toInt()
//    }
//
//    fun openShop(player: Player){
//        println("\n=== Добро Пожаловать в Магазин: $name ===")
//        println(description)
//
//        var shopping = true
//
//        while (shopping){
//            println("\n--- Меню Магазина ---")
//            println("1. Купить предметы")
//            println("2. Продать предметы")
//            println("3. Уйти")
//
//            println("Выберите действие: ")
//            when(readln().toIntOrNull() ?: 0){
//                1 -> showItemsForSale(player)
//                2 -> showBuyMenu(player)
//                3 -> {
//                    shopping = false
//                    println("Вы покидаете магазин.")
//                }
//                else -> println("Неправильно, постарайся")
//            }
//        }
//    }
//
//    private fun showItemsForSale(player: Player){
//        if (itemsForSale.isEmpty()){
//            println("Товаров сейчас нет")
//            return
//        }
//        println("\n=== Товары на продажу ===")
//        itemsForSale.forEach { (item, price) ->
//            println("${item.name} - ${item.description} | Цена: $price золотых")
//        }
//        println("${itemsForSale.size + 1}. Назад")
//
//        println("Выберите товар для покупки:")
//        val choice = readln().toIntOrNull() ?: 0
//
//        if (choice in 1..itemsForSale.size){
//            val selectedItem = itemsForSale.keys.toList()[choice - 1]
//            val price = itemsForSale[selectedItem] ?: 0
//
//            // ДЗ Реализовать проверку золота у игрока (хватает или нет)
//            if (player.gold < price){
//                println("У вас не достаточно золота для покупки")
//            }else{
//                println("Вы покупаете ${selectedItem.name} за $price золотых")
//                player.inventory.addItem(selectedItem)
//                // ДЗ Здесь вычесть у него золотые
//                player.gold -= price
//            }
//
//        }
//    }
//
//    private fun showBuyMenu(player: Player){
//        val sellableItems = player.inventory.items.filter { item ->
//            // containsKey - содержит ключи (по id)
//            buyPrices.containsKey(item.id)
//        }
//
//        if (sellableItems.isEmpty()){
//            println("У вас нет предметов на продажу")
//            return
//        }
//
//        println("\n=== Ваши предметы для продажи ===")
//        sellableItems.forEachIndexed { index, item ->
//            val price = buyPrices[item.id] ?: 0
//            println("${index + 1}. ${item.name} - цена продажи: $price золотых")
//
//        }
//        println("${sellableItems.size + 1}. Назад")
//
//        println("Выберите предмет для продажи: ")
//        val choice = readln().toIntOrNull() ?: 0
//
//        if (choice in 1..sellableItems.size){
//            val selectedItem = sellableItems[choice - 1]
//            val price = buyPrices[selectedItem.id] ?: 0
//            println("Вы продаете: ${selectedItem.name} за $price золотых")
//            player.inventory.removeItem(choice - 1)
//            // Реализация добавления золота игроку на price
//            player.gold += price
//        }
//    }
//
//    fun getBuyPrice(itemId: String): Int{
//        return buyPrices[itemId] ?: 0
//    }
//}
//
//class Location(val name: String, val description: String){
//    val items = mutableListOf<Item>()
//    val enemies = mutableListOf<Character>()
//    // может быть null если на локации нет магазина
//    var shop: Shop? = null
//
//    fun setShop(shop: Shop){
//        this.shop = shop
//    }
//
//    fun describe(){
//        println("\n=== $name ===")
//        println(description)
//
//        if (enemies.isNotEmpty()){
//            println("\nЧупокабры рядом: ")
//            enemies.forEachIndexed { index, enemy ->
//                println("${index + 1}. ${enemy.name} (HP: ${enemy.health})")
//            }
//        }
//
//        if (items.isNotEmpty()){
//            println("\nПодлутаемся: ")
//            items.forEachIndexed { index, item ->
//                println("${index + 1}. ${item.name} - ${item.description}")
//            }
//        }
//
//        if (shop != null){
//            // !! - Это строгое утверждение, что shop не null
//            println("\nМагазин: ${shop!!.name}")
//        }
//    }
//}
//
//fun main(){
////    println("=== Система Инвенторя Inventory ===")
////
////    val player = Player("Олег", 100, 15)
////
////    val healthPotion = Item(
////        "health_potion",
////        "Зелье Здоровье",
////        "Восстанавливает 30HP",
////        25,
////        {player ->
////            player.health += 30
////            println("${player.name} востанавливает 30HP!")
////        }
////    )
////    val apple = Item(
////        "apple",
////        "Не простое яблоко",
////        "Восстанавливает 10HP",
////        10,
////        {player ->
////            player.health += 10
////            println("${player.name} востанавливает 10HP!")
////        }
////    )
////    val strenghtPotion = Item(
////        "strenght_potion",
////        "Зелье силы",
////        "Увеличивает урон на 10 (на 3 хода)",
////        40,
////        { player ->
////            println("${player.name} вы чуствуете прилив сил (эффект временный)")
////        }
////    )
////
////    val oldKey = Item(
////        "old_key",
////        "Старый ключ",
////        "может открыть что-то",
////        5
////    )// useEffect не указан - он по умолчанию остается {}
////
////    println("=== Игра Началась, Минута.... ПОШЛА!")
////
////    println("Игрок нашел предмет")
////    // Добавление предметов в инвентарь
////
////    player.pickUpItem(healthPotion)
////    player.pickUpItem(strenghtPotion)
////    player.pickUpItem(oldKey)
////    player.pickUpItem(healthPotion)
////    player.pickUpItem(apple)
////    player.pickUpItem(apple)
////    player.pickUpItem(apple)
////
////    player.showInventory()
////
////    println("--- Использование предметов ---")
////    player.inventory.useItem(0, player)
////
////    println("Поиск предметов")
////    val foundKey = player.inventory.findItemById("old_key")
////    if (foundKey != null){
////        println("Вы открываете дверь. Но ключ рассыпается у вас в руках")
////    }else{
////        println("Мне нужен ключ от этой двери")
////    }
////
////    if (player.inventory.hasItem("health_potion")){
////        println("Вы можете похилиться у вас есть зелье здровье: ${player.inventory.countItems("health_potion")} штук(а)")
////    }
////    if (player.inventory.hasItem("apple")){
////        println("Вы можете подкрепится у вас есть яблоки: ${player.inventory.countItems("apple")} штук(а)")
////    }
//    println(" === Система Квестов и NPC ===")
//
//    val player = Player("Олег", 100, 15, 100)
//
//    val healthPotion = Item(
//        "health_potion",
//        "Зелье Здоровье",
//        "Восстанавливает 30HP",
//        25,
//        {player ->
//            // minOf(a,b) - a это округляемое значение, b это то, до которого его надо округлить
//            player.health = minOf((player.health + 30), player.maxHealth)
//            println("${player.name} востанавливает 30HP!")
//        }
//    )
//    val strengthPotion = Item(
//        "strength_potion",
//        "Зелье силы",
//        "Увеличивает урон на 10 (на 1 ход)",
//        50,
//        { player ->
//            println("${player.name} вы чуствуете прилив сил (эффект временный)")
//        }
//    )
//
//    // Предметы для квестов (создание)
//    val mysteryHerb = Item(
//        "mystery_herb",
//        "Таинственная трава",
//        "Редкое растение с целебными свойствами",
//        15
//    )
//
//    val ancientAmulet = Item(
//        "ancient_amulet",
//        "Древний амулет",
//        "Старынный амулет, с магическими свойствами",
//        100
//    )
//
//    // Создание Квестов
//    val herdQuest = Quest(
//        "find_herbs",
//        "Сбор целебных трав",
//        "Найдите таинственную траву в лесу",
//        "mystery_herb",
//        50,
//        ancientAmulet
//    )
//
//    val monsterQuest = Quest(
//        "kill_monsters",
//        "Очистка леса",
//        "Убейте 3 гремнов в лесу",
//        rewardGold = 100
//    )
//
//    val villageElder = NPC("Старейшина деревни","Мудрый старик, знающий много секретов")
//
//    villageElder.addDialogue("Поздороваться", "Добро пожаловать путник")
//    villageElder.addDialogue("Спросить о работе","Лес кишит монстрами, поможешь с этой работенкой?")
//    villageElder.addDialogue("Спросить о траве","В глубине леса растет дикая трава, драться умеет, собери немного для меня")
//
//    player.questManager.addQuest(herdQuest)
//    player.questManager.addQuest(monsterQuest)
//
//    player.questManager.startQuest("find_herbs")
//
//    // Игра с демонстрацией работы
//    println("--- Взаимодействие с NPC ---")
//    villageElder.talk()
//
//    println("\n--- Проверка квестов ---")
//    player.questManager.displayQuest()
//
//    println("--- Игрок нашел траву ---")
//    player.inventory.addItem(mysteryHerb)
//
//    println("\n--- Проверка на выполнения квеста ---")
//    player.questManager.checkAllQuests(player)
//
//    println("\n--- Финальный статус квестов ---")
//    player.questManager.displayQuest()
//
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
