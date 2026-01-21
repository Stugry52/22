//package Lesson11
//
//import Lesson10.Quest
//
//fun main(){
//    val logSystem = LogSystem()
//    logSystem.register()
//
//    val achievementsSystem = AchievementsSystem()
//    achievementsSystem.register()
//
//    val questSystem = QuestSystem()
//    questSystem.register()
//
//    val npcSystem = NpcSystem()
//    npcSystem.register()
//
//    EventBus.post(GameEvent.DialogueStarted("Старый", "Олег"))
//
//    EventBus.processQueue(50)
//
//    println()
//    val comboMenu = CombatSystemDemo()
//    comboMenu.simulateFight()
//
//    EventBus.processQueue(50)
//
//    EventBus.post(
//        GameEvent.DialogueChoiceSelected(
//            "Старый",
//            "Олег",
//            "done"
//        )
//    )
//
//    EventBus.processQueue(50)
//}