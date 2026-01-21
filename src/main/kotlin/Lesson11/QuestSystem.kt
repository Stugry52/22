//package Lesson11
//
//class QuestSystem{
//    // Флаги
//    data class QuestProgressState(
//        // Флаги
//         var questStarted: Boolean = false,
//         var stepTalked: Boolean = false,
//         var stepKilled: Boolean = false,
//         var stepReportedBack: Boolean = false,
//         var questCompleted: Boolean = false
//    )
//
//    private val questId = "ultra_mega_pro_quest_001"
//
//    private val progressbyPlayer: MutableMap<String, QuestProgressState> = mutableMapOf()
//
//    fun register(){
//        val questProgressState = QuestProgressState()
//        EventBus.subscribe { event ->
//            when(event){
//                is GameEvent.DialogueStarted -> {
//                    if (event.npcName == "Старый" && !questProgressState.questStarted) {
//                        questProgressState.questStarted = true
//                        questProgressState.stepTalked = true
//                        println("Квест $questId начат через диалог с ${event.npcName}")
//
//                        EventBus.post(GameEvent.QuestStarted(playerId, questId))
//                        EventBus.post(GameEvent.QuestStepCompleted(questId, "talk_to_elder"))
//                    }
//                }
//                is GameEvent.CharacterDied -> {
//                    if (questStarted && event.characterName == "Sahar" && event.killerName == "Oleg"){
//                        stepKilled = true
//                        println("Шаг квеста: Сахара зарубили")
//                        EventBus.post(GameEvent.QuestStepCompleted(questId, "kill_Sahar"))
//                    }
//                }
//                is GameEvent.DialogueChoiceSelected -> {
//                    if (questStarted && event.npcName == "Старый" && event.choiceId == "report_done"){
//                        stepReportedBack = true
//                        println("Игрок сообщил Старому, что выполнил квест")
//                        EventBus.post(GameEvent.QuestStepCompleted(questId, "report_back"))
//                    }
//                }
//                is GameEvent.QuestCompleted -> {
//                    if (questStarted && stepTalked && stepKilled && stepReportedBack){
//                        println("Вы успешно выполнили квест")
//                        EventBus.post(GameEvent.QuestCompleted(questId))
//                        questStarted = false
//                    }
//                }
//                else -> {}
//            }
//        }
//    }
//}