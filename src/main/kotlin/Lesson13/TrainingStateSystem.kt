package Lesson13

import Lesson11.EventBus
import Lesson11.GameEvent

class TrainingStateSystem{
    private val progress = TrainingProgress()

    fun register(){
        EventBus.subscribe { event ->
            when(event){
                is GameEvent.DialogueStarted,
                is GameEvent.DialogueChoiceSelected,
                is GameEvent.CharacterDied -> {
                    progress.handleEvent(event.playerId, event)
                }
//                is GameEvent.StateChanged -> {
//                    progress.handleEvent(event.playerId, event)
//                }
                else -> {}
            }
        }
    }
}