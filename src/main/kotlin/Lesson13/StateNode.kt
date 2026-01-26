package Lesson13

import Lesson11.GameEvent

// Ноды - это как станции с пересадками на разные переходы состояний

class StateNode (
    val state: TrainingState
    // state - состояние, которое представляет этот узел (остановки от которой поедет)
){
    private val transitions = mutableMapOf<Class<out GameEvent>, TrainingState>()
    // out - позволяет в роли типа данных класть не сам класс, а его наследников
    // Так как GameEvent - иерархия, а его дочерние классы - это уже сами события
    // Ключ - тип события (при котором сработает переход)
    // Значение - это следующее состояние, на которое перейдет

    fun addTransition(
        eventType: Class<out GameEvent>,
        nextState: TrainingState
    ){
        transitions[eventType] = nextState
    }

    fun getNextState(event: GameEvent): TrainingState?{
        // Берем класс события и ищем переход
        return transitions[event::class.java]
        // event = David
        // event::Class = программист
        // То есть event::Class - означает: "Скажи мне, КАКОГО ТИПА этого обьект"
        // :: - оператор ссылки
        // Это не использование объекта, а ссылка на информации о нем
        // Ссылка на класс объекта event

        // java - Kotlin - красивая обертка джавы
        // Kotlin - работает поверх JVM (Java Virtual Machine)
        // jvm - в которой находится MAP - создан для java и map - это класс java
        // event::Class - Kotlin класс
        // event::Class.java - Java класс
        // Это не разные классы - это две формы записи одного и того типа

        // Java класс здесь нам нужен для:
        // Map<Class<...>, ...> - это использують "Class<>" именно из стандртной Java
        // Kotlin - это мод для Java, но он не рабодает вместо Java он упрощает возможности Java
        // И то что в нем не реализовано мы всегда можем вместо мода использовать стандартный язык
    }
}
