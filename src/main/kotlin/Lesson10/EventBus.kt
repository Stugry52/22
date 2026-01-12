package Lesson10

// object - это Singleton
// Singleton - это обьект в единственном экземпляре
// Это очень важно так как обьект отвечает за события должен быть только один
// То есть EventBus - руководит и хранить в себе информацию о событиях (сердце игровой логики)
object EventBus {
    // Список всех слушателей (подписчиков)
    private val listeners = mutableListOf<(GameEvent) -> Unit>()
    // (GameEvent) -> Unit - функция лямбда
    // Принимает GameEvent, а возвращает Unit (по умолчанию ничего пустота)

    fun subscribe(listener: (GameEvent) -> Unit){
        // subscribe - метод подписки на событие

        listeners.add(listener)

        println("Новый подписчик добавлен! Всего подписчиков: ${listeners.size}")
    }

    fun publish(event: GameEvent){
        // Публикация событий для слушателей

        println("Событие опубликовано: $event")

        // Проходит по всем подписчикам данного события
        for(listener in listeners){
            // Вызываем функцию слушателя с конкретным событием
            listener(event)
        }
    }
}
// То есть EventBus - сам по себе не знает что такое NPC QUEST UI
// Он просто как рассылка которая говорит вот событие - кто подписан реагируй