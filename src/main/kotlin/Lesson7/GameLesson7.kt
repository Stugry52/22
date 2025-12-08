package Lesson7

class Player(
    // первичный конструктор - который должен быть обязательно задан при создании предмета
    val name: String,
){
    // Вторичный конструктор - эти свойства все ещё существуют, но их не обязательно использовать или перезаписывать при объявлении объекта
    val inventory = Inventory()
    // свойство-объект класса игрока
}

// Точка входа программы ( то есть запуска компилятора и команды компилятору выполнить код внутри)
fun main(){

    println("=== ПРЕДМЕТЫ И ИНВЕНТАРЬ ===")

    // Сделаем создание предметов
    val sword = Item(
        id = 1,
        name = "Железный меч",
        description = "Простой но не простой меч",
        price = 50,
        weight = 10
    )

    val apple = Item(
        id = 2,
        name = "Яблоко",
        description = "Вкусное яблоко, дает вкусного червячка",
        price = 6,
        weight = 1
    )

    val potion = Item(
        id = 3,
        name = "Зелье лечение",
        description = "Мгновенно востанавливает много здоровья (то есть 1)",
        price = 45,
        weight = 3
    )

    val player = Player("Oleg")

    println("Создан игрок ${player.name}")

    player.inventory.addItem(sword)

    player.inventory.addItem(apple)
    player.inventory.addItem(potion)

    println()
    println("Содержимое инвентаря после подбирания предмета")
    player.inventory.printInventory()



    println("Содержимое инвентаря после удаления предмета")

    player.inventory.printInventory()

    // Вес предмета (ограничение инвентаря)
    // Добавить в Item свойство weight: Int - вес предмета
    // В Inventory: добавить свойство максимального веса
    // Сделать функцию getCurrentWeight(): Int - которая суммирует все веса предметов инвентаре
    // В addItem перед добавлением предмета проверять, не превышен ли лимит инвентаря
    // Если превышен - не добавлять, а просто вывести сообщение в консоль

    // Поиск предмета по имени или id
    // Добавь метод в Inventory: fun findItemByName(name: String) Item? {}
    // Проходит по items в цикле for.
    // Если находит предмет с таким name, возвращает его
    // Если не находит - возвращает null
    // Сделай похожий метод findItemById(id: Int): Item?

    // В main: попробуй найти предмет по имени. Если найдешь - выведи его описание
    player.inventory.findItemByName("Яблоко")
    player.inventory.findItemById(1)
 }