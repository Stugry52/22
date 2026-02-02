package Lesson13

import Lesson11.GameEvent

class TrainingStateGraph {
    private val nodes = mutableMapOf<TrainingState, StateNode>()
    // Все узлы графа

    init {
        // init - блок который выполняется при создании объекта

        // создаем подготовленные узлы
        val start = StateNode(TrainingState.START)
        val approached = StateNode(TrainingState.APPROACHED)
        val talking = StateNode(TrainingState.TALKING)
        val accepted = StateNode(TrainingState.ACCEPTED)
        val refuse = StateNode(TrainingState.FAILED)
        val dummyKilled = StateNode(TrainingState.DUMMY_KILLED)
        val completed = StateNode(TrainingState.COMPLETED)

        // Описание переходов (Это и будет наш граф)
        start.addTransition(
            GameEvent.DialogueStarted::class.java,
            TrainingState.APPROACHED
        )
        approached.addTransition(
            GameEvent.DialogueChoiceSelected::class.java,
            TrainingState.TALKING
        )
        talking.addTransition(
            GameEvent.DialogueChoiceSelected::class.java,
            TrainingState.ACCEPTED
        )
        talking.addTransition(
            GameEvent.DialogueChoiceSelected::class.java,
            TrainingState.FAILED
        )
        accepted.addTransition(
            GameEvent.CharacterDied::class.java,
            TrainingState.DUMMY_KILLED
        )
        dummyKilled.addTransition(
            GameEvent.DialogueChoiceSelected::class.java,
            TrainingState.COMPLETED
        )

        // Кладем узлы на карту
        nodes[start.state] = start
        nodes[approached.state] = approached
        nodes[talking.state] = talking
        nodes[accepted.state] = accepted
        nodes[dummyKilled.state] = dummyKilled
        nodes[completed.state] = completed
        nodes[refuse.state] = refuse
    }

    fun getNode(state: TrainingState): StateNode{
        return nodes[state]!!
        // !! - явно и уверенно заявляем что узел существует (не будет null)

    }
}