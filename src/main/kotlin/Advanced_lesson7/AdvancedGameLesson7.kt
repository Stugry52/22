package Advanced_lesson7

class Player(
    val name: String,
    val inventory: Inventory
)

fun main(){
    val inventory = Inventory(5)

    val player = Player(
        name = "Oleg",
        inventory = inventory
    )

    val sword = Item(
        1,
        "Меч",
        "Обычный меч",
        35,
        ItemType.WEAPON,
        1 // Максимальное число стака данного предмета (1 значит только один предмет на одну ячейку)
    )

    val helmet = Item(
        2,
        "Шлем кожанный",
        "Шлем из кожи хейтеров",
        60,
        ItemType.ARMOR,
        1
    )

    val potion = Item(
        3,
        "Зелье здоровья",
        "Хилит на 20 хп",
        25,
        ItemType.CONSUMABLE,
        10
    )

    player.inventory.addItem(sword)
    player.inventory.addItem(helmet)
    player.inventory.addItem(potion, 7)
    player.inventory.addItem(potion, 12)

    player.inventory.printInventory()

    player.inventory.addItem(sword)
    player.inventory.removeItem(potion, 5)
    player.inventory.removeItem(helmet)

    player.inventory.printInventory()

    player.inventory.addItem(potion, 50)

    player.inventory.printInventory()
    player.inventory.getTotalCountOf(potion)

}

// Подсчитать общее количество предметов любого типа
// Добавить в Inventory функции:
// fun getTotalCountOf(item: Item): Int
// Она должно пройти по всем slots и вернуть суммарное количество quantity и для слотов с этим item.id