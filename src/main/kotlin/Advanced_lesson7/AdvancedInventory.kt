package Advanced_lesson7

class Inventory(
    private val maxSlots: Int // максимальное число слотов инвентаря
) {
    private val slots: MutableList<InventorySlot> = mutableListOf()
    // private - никто снаружи класса Inventory не сможет изменить или взаимодействовать с данным списком

    // Публичный метод, который позволяет взаимодействовать с приватными списками внутри Inventory но только так как мы это запишем
    // По факту - могут повлиять на приватные свойства только через параметры которые положим в метод
    fun addItem(item: Item, amount: Int = 1){
        if (amount <= 0){
            // Если число предметов меньше или равно 0 оброботать ошибку
            println("Нельзя положть неположительное число х$amount предметов ${item.name}")
            return // Точка выхода из метода прервать выполение кода
        }
        var remaining = amount
        // Сохранение числа предметов которых ещё осталось добавить

        // 1. Пытаемся доложить в уже существующие слоты с таким же предметом (но не заполненные)
        for(slot in slots){
            // Перебераем все слоты в инвентаре - времменая перемменая slot будет каждую итерацию принимать в себя каждый элемент списка слотов

            if (slot.item.id == item.id){

                val  freeSpace = item.maxStackSize - slot.quantity
                // Сколько ещё предметов можно положить в слот до его заполнения
                // maxStackSize - максимальный стак в слоте для данного предмета
                // slot.quantity - сколько таких предметов уже лежит в данном слоте
                if(freeSpace > 0){
                    // Проверка на то ест ли место для докладки в слот
                    val toAdd = minOf(remaining, freeSpace)
                    // toAdd - сколько реально положим в данный слот
                    // берем минимальное от:
                    // сколько осталось добавить (remaining)
                    // сколько помещается в слот (осталось свободного места с учетом лежащих там уже полицейских)

                    slot.quantity += toAdd
                    // Увеличиваем число предметов в слоте
                    remaining -= toAdd

                    // Уменьшаем число предметов, которые ещё не положены в инвентарь (ждут своего часа расплаты над Денисом)

                    println(" + Добавление ч$toAdd предметов ${item.name} в существующий слот. Теперь в слоте: ${slot.quantity}")
                    if (remaining == 0){
                        // Если не осталось предметов дял покладки в инвентаре, то закончить метод
                        return
                    }
                }
            }
        }

        // 2. Если оставить предметы, то есть не поместились в существующие слоты, то пробуем создать новый слот
        while (remaining > 0){
            // выполняется пока осталось что класть в инвентаре

            if (slots.size >= maxSlots){
                println(" ! Инвентарь переполнен ! Не удалось положить x$remaining ${item.name}")
                return
            }

            val toAdd = minOf(remaining,item.maxStackSize)

            val newSlot = InventorySlot(item, toAdd)

            println(" ++ Создан новый слот для ${item.name} с количеством $toAdd")

            remaining -= toAdd
        }
    }

    fun removeItem(item: Item, amount: Int = 1): Boolean{
        if(amount <= 0){
            println(" ! Знаешь что, я так подумал, ты обнаглел в край")
        }

        var remaining = amount

        for (i in slots.size -1 downTo 0){
            // -1 - то для получения последнего индекса из списка
            // downTo 0 - счет перебора по убыванию вниз до 0 включительно
            val slot = slots[i]

            if(slot.item.id == item.id){

                if (slot.quantity <= remaining){
                    remaining -= slot.quantity
                    println("Удален слот с предметом ${item.name} в количестве ${slot.quantity}")
                    slots.removeAt(i)
                    // removeAt - метод списков, для удаления элементов списка по индексу

                    if (remaining == 0){
                        return true
                    }
                }else{
                    // Если предметов в ячейке больше чем то число которое мы хотим удалить
                    slot.quantity -= remaining
                    println("Уменьшено количество ${item.name} в слоте на $remaining")
                    return true
                }
            }
        }

        // Если сюда дошли - то есть не смогли удалить все нужное число предметов в слоте
        println("Не удалось удалить x$remaining предметов ${item.name} таких предметов больше нет в инвентаре")
        return false
    }

    fun printInventory(){
        if(slots.isEmpty()){
            println("Инвентарь пуст")
            return
        }

        println("\n=== ИНВЕНТАРЬ (слотов ${slots.size} / $maxSlots) ===")

        for((index, slot) in slots.withIndex()){
            // withIndex - возвращает пару (индекс и значение данного индекса)
            // временные переменные index и slot нужно для хранения и обработки данной пары каждую итерацию

            println(
                "Слот ${index + 1}: ${slot.item.name}" +
                "| тип=${slot.item.type} " +
                "| кол-во=${slot.quantity} / ${slot.item.maxStackSize}"
            )
        }
    }

    fun getTotalCountOf(item: Item): Int{
        var totalCount = 0
        for ((index, slot) in slots.withIndex()){
            totalCount += slot.quantity
        }
        println(totalCount)
        return totalCount
    }
}