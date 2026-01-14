package Lesson11

object EventBus{
    // Типы слушателей (callback)
    typealias Listener = (GameEvent) -> Unit
    // typealias - это псевдоним для типов данных (очень грубо говоря переменная хранящая в себе какой-то тип данных)
    // Список подписоты
    private val listeners = mutableMapOf<Int, Listener>()

    private var nextId: Int = 1

    private val eventQueue = ArrayDeque<GameEvent>()
    // ArrayDeque - двусторонняя очередь
    // С ней взаимодействовать можно как с начала так и с конца
    // Здесь будем хранить события которые будет обрабатывать либо позже либо когда получим подтверждение
    fun subscribe(listener: Listener): Int{
        val id = nextId
        nextId += 1

        listeners[id] = listener
        println("Подписчик добавлен id=$id. Всего: ${listeners.size}")

        return id
    }

    fun unsubscribe(id: Int){
        val removed = listeners.remove(id)
        // removed может быть null, потому что id может быть получен не правильно ?

        if (removed != null){
            println("Подписчик удален id=$id")
        }else{
            println("Не удалось отписаться, неправильный id=$id")
        }
    }

    fun subscribeOnce(listener: Listener): Int{
        // Подписка на 1 раз после одного полученного события слушатель сам отписывается
        var id: Int = -1
        id = subscribe{ event ->
            //Создание обычной подписки
            listener(event)
            // Вызов слушателя исходного
            unsubscribe(id)
            // Вызов отписки после первого же прослушивание
        }
        return id
    }

    fun publish(event: GameEvent){
        println("[Событие опубликовано: $event]")

        for (listener in listeners.values){
            // listeners.values - все значения словаря перебираем (все функции-слушателя)
            listener(event)
        }
    }

    fun post(event: GameEvent){
        // post - кладем событие в очередь (то есть не обрабатываем сразу)
        eventQueue.addLast(event)
        // addLast - добавить в конце очереди

        println("Событие добавлено в очередь $event (В очереди: ${eventQueue.size - 1})")
    }

    fun processQueue(maxEvents: Int = 10){
        // Метод обработка событий из очереди

        var processed = 0
        // Сколько обработано событий
        while (processed < maxEvents && eventQueue.isNotEmpty()){
            val event = eventQueue.removeFirst()
            // removeFirst() - возвращает элемент из очереди и удаляет его

            publish(event)

            processed += 1
        }
    }
}