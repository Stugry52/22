package Lesson12

import Lesson7.Player
import Lesson11.EventBus
import Lesson11.GameEvent

class TrainingNpc (
    val name: String,
    var state: NpcState = NpcState.IDLE
    // Динамическое состояние npc по умолчанию IDLE
) {
    fun onPlayerApproached(playerId: String) {
        // Игрок подошел
        if (state == NpcState.IDLE) {
            println("$name: Привет, $playerId")
            state = NpcState.WAITING
            val newState = NpcState.WAITING
            val oldState = NpcState.IDLE
            println("[INFO] Теперь $name поменял свое состоянии с $oldState на $newState")
        }
    }

    fun onDialogueStarted(playerId: String) {
        if (state == NpcState.WAITING){
            println("$name: Я начну тебя драться с бомжами")
            state = NpcState.TALKING
            val newState = NpcState.TALKING
            val oldState = NpcState.WAITING
            println("[INFO] Теперь $name поменял свое состоянии с $oldState на $newState")
        }
    }

    fun onDialogueChoiceSelected(playerId: String, choice: String){
        if (state == NpcState.TALKING && choice == "accept"){
            println("$name: Хорош, учить тебя не буду, держи награду за решимость")
            state = NpcState.REWARDED
            val newState = NpcState.REWARDED
            val oldState = NpcState.TALKING
            println("[INFO] Теперь $name поменял свое состоянии с $oldState на $newState")
        }
    }

    fun wrongDialogueChoice(playerId: String, choice: String){
        if (state == NpcState.TALKING && choice == "wrong"){
            println("$name: Натворил ты делов, малец")
            state = NpcState.ANGRY
            val newState = NpcState.ANGRY
            val oldState = NpcState.TALKING
            println("[INFO] Теперь $name поменял свое состоянии с $oldState на $newState")
        }
    }


    // Конкретный нпс ничего не знает о событиях напрямую и о их существовании
    // События лишь будут влиять на его состояния state
}