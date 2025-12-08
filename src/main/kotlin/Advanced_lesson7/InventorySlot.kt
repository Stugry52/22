package Advanced_lesson7

// Класс для одной ячейки инвентаря
data class InventorySlot(
    val item: Item,
    var quantity: Int
    // quantity - количество предметов которое сейчас лежит в ячейке инвентаря (в слоте)

)