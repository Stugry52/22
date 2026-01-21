//package Lesson11
//
//class NpcSystem{
//    fun register(){
//        EventBus.subscribe { event ->
//            when (event) {
//                is GameEvent.QuestStarted -> {
//                    println("[NPC-Система] Старый теперь ждет результата выполнения квеста")
//                    EventBus.post(GameEvent.DialogueLineUnlocked("Старый", "Completed"))
//                }
//
//                is GameEvent.QuestStepCompleted -> {
//                    if (event.stepId == "kill_sahar"){
//                        println("[NPC-Система] Отрыта реплика 'Ты убил его?'")
//                        EventBus.post(GameEvent.DialogueLineUnlocked("Старый", "kill_sahar"))
//                    }
//                }
//                is GameEvent.QuestCompleted -> {
//                    println("[NPC-Система] Квест завершен, открываем диалог с наградой")
//                    EventBus.post(GameEvent.DialogueLineUnlocked("Старый","congrats"))
//                }
//                else -> {}
//            }
//        }
//    }
//}