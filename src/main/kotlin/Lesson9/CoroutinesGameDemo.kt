package Lesson9

import kotlinx.coroutines.*

fun main() = runBlocking{
    // runBlocking - нужно для запуска вторичных нее вторичных процессов
    val player = GameCharacter(
        "Олег",
        70,
        30,
        8
    )

    val enemy = GameCharacter(
        "Хейтер",
        40,
        0,
        10
    )

    player.printStatus()
    enemy.printStatus()

    player.startManaRegeneration(
        3,
        3000L
    )

    println("${enemy.name} жестко токсит на ${player.name}")
    player.applyPotionEffect(
        2,
        5,
        1500L
    )
    println("Олег получит бафф силы на +5 на 5 секунд")
    player.applyAttackBuff(
        5,
        5000L
    )

    val attackJob = launch {
        repeat(6){ attackNumber ->
            // Повторить 6 раз лямбду (attackNumber - считает всего попыток атаковать)

            delay(2000L)

            println()
            println("Попытка атаки #${attackNumber + 1}")
            player.attack(enemy)

            if (enemy.currentHealth <= 0){
                enemy.currentHealth = 0
                println("Хейтер повержен, стоп атаку")
                return@launch
                // return@launch - выходим из корутины attackJob
                // То есть для выхода (прерывания) корутины на донной точке, нужно указать, точку откуда началась (launch)
            }
        }
    }

    var monitorJob = launch {
        repeat(12){
            delay(1000L)

            println(">>> Мониторинг состояние персонажей <<<")
            player.printStatus()
            enemy.printStatus()

            if (player.currentHealth <= 0){
                player.currentHealth = 0
                println("Олег помер")
                return@launch
            }
        }
    }

    delay(15000L)

    player.stopManaRegeneration()
    player.clearPotion()

    attackJob.cancel()
    monitorJob.cancel()
}