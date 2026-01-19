package Lesson11

import Lesson7.Player
import jdk.jfr.DataAmount

sealed class GameEvent{
    // Боевые события //
    data class  CharacterDied(
        val characterName: String,
        val playerId: String,
        val killerName: String
    ) : GameEvent()

    data class DamageDealt(
        val attackerName: String,
        val playerId: String,
        val targetName: String,
        val amount: Int
    ): GameEvent()

    data class EffectApplied(
        val characterName: String,
        val playerId: String,
        val effectName: String
    ): GameEvent()

    data class EffectEnded(
        val characterName: String,
        val playerId: String,
        val effectName: String
    ): GameEvent()

    // Диалоги и NPC //
    data class DialogueStarted(
        val npcName: String,
        val playerId: String,
        val playerName: String
    ): GameEvent()

    data class DialogueChoiceSelected(
        val npcName: String,
        val playerId: String,
        val playerName: String,
        val choiceId: String
    ): GameEvent()

    data class DialogueLineUnlocked(
        val npcName: String,
        val playerId: String,
        val lineId: String
    ): GameEvent()

    // Квесты и прогресс
    data class QuestStarted(
        val playerId: String,
        val questId: String
    ): GameEvent()

    data class QuestStepCompleted(
        val questId: String,
        val playerId: String,
        val stepId: String
    ): GameEvent()

    data class QuestCompleted(
        val playerId: String,
        val questId: String
    ): GameEvent()

    // Достижения
    data class AchievementUnlocked(
        val playerId: String,
        val achievementId: String
    ): GameEvent()

    // Событие сохранения прогресса игрока
    data class PlayerProgressSaved(
        val playerId: String,
        val guestId: String,
        val stepId: String
    ) : GameEvent()
}