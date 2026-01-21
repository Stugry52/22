package Lesson12

import Lesson11.EventBus
import Lesson11.GameEvent

class TrainingNpcSystem {
    private val npc = TrainingNpc("Trainer")

    fun register(){
        EventBus.subscribe { event ->
            when(event){
                is GameEvent.DialogueStarted -> {
                    npc.onDialogueStarted(event.playerId)
                }
                is GameEvent.DialogueChoiceSelected -> {
                    npc.onDialogueChoiceSelected(
                        event.playerId,
                        event.choiceId
                    )
                    npc.wrongDialogueChoice(
                        event.playerId,
                        event.choiceId
                    )
                }
                else -> {}
            }
        }
    }

    fun playerApphoaches(playerId: String){
        npc.onPlayerApproached(playerId)
    }
}

