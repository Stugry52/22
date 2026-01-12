package Lesson10

class Npc(val name: String){
    fun register(){
        EventBus.subscribe { event ->

            when(event){
                is GameEvent.QuestCompleted -> {
                    println("NPC $name: Хорошая работа Олег вот твоя награда за квест ${event.questId}")
                }
                is GameEvent.EffectApplied -> {
                    println("NPC $name не хочет давать вам награду так как от вас воняет ${event.effectName}")
                }
                is GameEvent.DialogStarted -> {
                    println("NPC $name приняв трудный бой, погиб ${event.DialogChoiceSelected}")
                    EventBus.publish(
                        GameEvent.CharacterDied(name)
                    )
                }
                else -> {}
                // else обязателен для when - чтобы обозначить что делать если ни один из вариантов не совпал
            }
        }
    }
}