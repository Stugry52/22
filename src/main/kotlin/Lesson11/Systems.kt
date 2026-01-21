//package Lesson11
//
//class LogSystem{
//    fun register(){
//        EventBus.subscribe { event ->
//            println("[INFO] Получено событие: $event")
//            // Реагирует на все возможные события, и логирует их в консоли
//        }
//    }
//}
//
//class AchievementsSystem{
//    private var killCount: Int = 0
//
//    fun register(){
//        EventBus.subscribe { event ->
//            when(event){
//                is GameEvent.CharacterDied -> {
//                    if (event.killerName == "Oleg"){
//                        killCount += 1
//                        println("[!]Счетчик убийства игрока: $killCount")
//
//                        if (killCount == 1){
//                            EventBus.post(GameEvent.AchievementUnlocked("first_blood"))
//                        }
//                        if (killCount == 3){
//                            EventBus.post(GameEvent.AchievementUnlocked("cho_ti_nadelal"))
//                        }
//                    }
//                }
//                else -> {}
//            }
//        }
//    }
//}