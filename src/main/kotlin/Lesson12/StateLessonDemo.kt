package Lesson12

import Lesson11.EventBus
import  Lesson11.GameEvent

fun main(){
    val npcSystem = TrainingNpcSystem()
    npcSystem.register()

    val playerId = "Oleg"

    println("$playerId подходит к NPC")
    npcSystem.playerApphoaches(playerId)

    println("$playerId Начинает диалог")
    EventBus.post(
        GameEvent.DialogueStarted(
            playerId,
            "Trainer",
            playerId
        )
    )
    EventBus.processQueue()

//    println("$playerId выбирает вариант 'accept'")
//    EventBus.post(
//        GameEvent.DialogueChoiceSelected(
//            playerId,
//            "Trainer",
//            playerId,
//            "accept"
//        )
//    )
    EventBus.post(
        GameEvent.DialogueChoiceSelected(
            playerId,
            "Trainer",
            playerId,
            "wrong"
        )
    )
    EventBus.processQueue()
}