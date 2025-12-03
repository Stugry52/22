package Lesson7

class Inventory{
    // Обычный класс
    private val items: MutableList<Item> = mutableListOf()
    private val maxWeight = 13
    private var weightNow = 0
    // private - модификатор доступа: это поле (кусок кода) виден только внутри класса Inventory
    // MutableList<Item> - тип свойства:
    // MutableList - изменяемый список
    // <Item> - тип который будет принимать и хранить свойство
    // Создаем пустой изменяемый список mutableListOf - который соответственно принимает в себя только объекты класса Item

    fun addItem(item: Item){
        // В круглых скобках объявляется параметры
        // Имя параметра: тип параметра
        if (getCurrentWeight(item) + item.weight <= maxWeight){
            items.add(item)
            println("Добавлен в инвентарь предмет: ${item.name}")
        }else{
            println("Превышение веса")
        }

    }

    fun removeItem(item: Item): Boolean{
        // :Boolean - это то что будет ретернить функция после выполнения
        val removed = items.remove(item)
        // items - сейчас является списком
        // У списков есть встроенные свои методы, которыми мы можем пользоваться через точку

        if (removed){
            println("Предмет удален: ${item.name}")
        }else{
            println("Предмет не удалось удалить: ${item.name}")
        }

        return removed // Возвращаем либо false либо true
        // потому что метод
    }

    fun printInventory(){
        // isEmpty() - метод что возвращает t/f в зависимости от того пустой ли список
        if (items.isEmpty()){
            println("Инвентарь пуст...")
            return // досрочно завершит (выйдет из функции) и код ниже не будет выполняться
        }
        println("==== ИНВЕНТАРЬ ИГРОКА ====")
        for (item in items){
            // (item in items) - читается как "для каждого item в коллекции items "
            // item (временная стековая переменная цикла) в ней по очереди будет содержаться каждый элемент списка (каждую итерацию новый)
            // Итерация - повторение или "ШАГ" цикла
            println("- ${item.name}, (id= ${item.id}) цена=${item.price} вес=${item.weight}")
        }

        println("$weightNow/$maxWeight")
    }
    fun getCurrentWeight(item: Item): Int{
        var currentWeight = 0
        for (item in items){
            currentWeight += item.weight
        }
        weightNow = currentWeight
        return currentWeight
    }
    fun findItemByName(name: String) Item? {
        for (item in items){
            if (name == item.name){
                println("Предмет найден: ${item.name}")
                return name
                break
            }else{
                println("Предмет не найден")
                return null
            }
        }

    }
    fun findItemById(itemId: Int): Item? {
        return items.find { it.id == itemId }
    }
}