package Lesson11

import Lesson7.Player

class SavesSystem{
    private val progress: MutableMap<String, MutableMap<String, MutableSet<String>>> = mutableMapOf()
    // MutableMap<String, ...> - ключ: playerId
    // MutableMap<String, MutableSet<String>> - ключ: guestId
    // MutableSet<String> - набор stepId, что уже выполнено

    fun register(){
        EventBus.subscribe { event ->
            when(event){
                is GameEvent.PlayerProgressSaved -> {
                    savedStep(event.playerId, event.stepId, event.guestId)
                }
                else -> {}
            }
        }
    }

    fun savedStep(playerId: String, questId: String, stepId: String){
        val playerData = progress.getOrPut(playerId) {mutableMapOf()}
        // getOrPut - проверяет если ключ (сейчас questIs) есть то возвращаем его значение
        // если его нет создать его вручную через блок {......} и кладем в map
        val questSteps = playerData.getOrPut(questId){mutableSetOf()}
        // Получаем квесты игрока и создаем набор шагов квеста

        val wasAdded = questSteps.add(stepId)
        // add вернет true если шаг добавился впервые false если шаг уже был

        if(wasAdded){
            println("[SAVE] Сохранение: player=$playerId quest=$questId, step=$stepId")
        } else{
            println("[SAVE] Шаг квеста уже был сохранен ранее: quest=$questId, step=$stepId")
        }
    }

    fun printProgress(playerId: String){
        println("=== ПРОГРЕСС ДЛЯ ИГРОКА: $playerId ===")

        val plyerData = progress[playerId]
        if (plyerData == null){
            println("Прогресса у игрока нет")
            return
        }

        for ((questId, steps) in plyerData){
            println("Квест: $questId")
            println("Шаги: $steps")
            println("======================")
        }
    }
}