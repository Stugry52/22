package Lesson10

fun main(){
    println("=== Система Поиска Событий ===")

    val quest = Quest(
        "kill_sahar",
        "Сахарок"
    )

    val npc = Npc("Кирилл 42")
    val nps = Npc("Никитос")

    // Создали нпс и квест для него но нам надо их зарегестрировать как слушателей событий
    quest.register()
    npc.register()
    nps.register()

    //Вызываем игровое событие
    EventBus.publish(
        GameEvent.EffectApplied(
            "Кирилл 42",
            "Беспрерывный поноса"
        )
    )

    // Вызываем событие смерти сахара
    EventBus.publish(
        GameEvent.CharacterDied(
            "Сахарок"
        )
    )
    EventBus.publish(
        GameEvent.DialogStarted(
            "Бой с гоблином"
        )
    )
}
