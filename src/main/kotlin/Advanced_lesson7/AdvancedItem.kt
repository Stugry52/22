package Advanced_lesson7

// Класс данных для предметов с типом предмета и максимальным числом в стаке
data class Item(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val type: ItemType,   // Тип предметов, который принимает только вариант из enum
    val maxStackSize: Int // Максимальное число предметов, которые можно в стаке положить в 1 ячейку инвентаря
)