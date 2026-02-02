package Lesson11

import Lesson7.Player
import jdk.jfr.DataAmount

sealed class GameEvent(open val playerId: String){
    // Боевые события //
    data class  CharacterDied(
        val characterName: String,
        override val playerId: String,
        val killerName: String
    ) : GameEvent(playerId)

    data class DamageDealt(
        val attackerName: String,
        override val playerId: String,
        val targetName: String,
        val amount: Int
    ): GameEvent(playerId)

    data class EffectApplied(
        val characterName: String,
        override val playerId: String,
        val effectName: String
    ): GameEvent(playerId)

    data class EffectEnded(
        val characterName: String,
        override val playerId: String,
        val effectName: String
    ): GameEvent(playerId)

    // Диалоги и NPC //
    data class DialogueStarted(
        val npcName: String,
        override val playerId: String,
        val playerName: String
    ): GameEvent(playerId)

    data class DialogueChoiceSelected(
        val npcName: String,
        override val playerId: String,
        val playerName: String,
        val choiceId: String
    ): GameEvent(playerId)

    data class DialogueLineUnlocked(
        val npcName: String,
        override val playerId: String,
        val lineId: String
    ): GameEvent(playerId)

    // Квесты и прогресс
    data class QuestStarted(
        override val playerId: String,
        val questId: String
    ): GameEvent(playerId)

    data class QuestStepCompleted(
        val questId: String,
        override val playerId: String,
        val stepId: String
    ): GameEvent(playerId)

    data class QuestCompleted(
        override val playerId: String,
        val questId: String
    ): GameEvent(playerId)

    // Достижения
    data class AchievementUnlocked(
        override val playerId: String,
        val achievementId: String
    ): GameEvent(playerId)

    // Событие сохранения прогресса игрока
    data class PlayerProgressSaved(
        override val playerId: String,
        val guestId: String,
        val stepId: String
    ) : GameEvent(playerId)

    // Событие изменения состояния
    data class NpcStateChanged(
        override val playerId: String,
        val npcName: String,
        val oldState: String,
        val newState: String
    ) : GameEvent(playerId)
}