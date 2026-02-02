package Lesson13

import Lesson11.EventBus
import Lesson11.GameEvent

// State - состояние
// Event - события
// Graph - граф (карта всех возможных переходов между состояниями)

// Пример Метро или остановки
// Станции (остановки) -> states
// Переходы -> Transitions
// Поезд едет -> Event
// ВАЖНО:
// Мы не можем телепортироваться на любую станцию
// Мы не можем ехать куда угодно (мы можем ехать строго по заданным маршрутам)

// Пример Квеста
// Start
//   |  (DialogueStarted)
// TALKED_TO_NPC
//   |  (ChoiceAccepted)
// KILL_DUMMY
//   |  (CharacterDied)
// REPORT_BACK
//   |  (DialogueChoiceSelected)
// COMPLETED
// Не используем if else
// Граф переходов состояний

// Без State Graph:
// Почти Невозможно делать ветвления (выбор диалоги, несколько концовок, влияние игровой логики на выбор игрока)
// Почти не возможно понять где сейчас находиться игрок (Узнать игрока на его индивидуальной карте графов)
// Почти невозможно грамотно сохранить прогресс игрока
// С StateGraph:
// Сохраняем каждому игроку его текущий узел - События просто будут двигать игрока по его графу
// Событие это выбор конкретного диалога, убийство конкретного npc

fun main(){
    val system = TrainingStateSystem()
    val player = "Oleg"

    EventBus.post(GameEvent.DialogueStarted("Тренер", player, player))
    EventBus.processQueue()

    EventBus.post(GameEvent.DialogueStarted("Тренер", player, player))
    EventBus.processQueue()

    EventBus.post(GameEvent.DialogueChoiceSelected("Тренер", player, player, "accept"))
    EventBus.processQueue()

    EventBus.post(GameEvent.DialogueChoiceSelected("Тренер", player, player, "refuse"))
    EventBus.processQueue()

    EventBus.post(GameEvent.CharacterDied("Макан", player, player))
    EventBus.processQueue()

    EventBus.post(GameEvent.DialogueChoiceSelected("Тренер", player, player, "finish"))
    EventBus.processQueue()



}
