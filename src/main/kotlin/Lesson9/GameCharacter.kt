package Lesson9
// Корутины - это доступ к асинхроному программированию (async - возможность больше 1 действия одновременно)
// Корутина - это выделенный поток, в котором и будет выполняться второстенная/фоновая/дополнительная к основной части игры - процесс

import kotlinx.coroutines.*
// Импорт библиотеки где * означает импорт всех методов и Классов из неё
// То есть без надобности подключать по отдельности методы launch, delay, Job и тд

class GameCharacter(
    val name: String,
    val maxHealth: Int,
    val maxMan: Int,
    val baseAttack: Int
){
    var currentHealth: Int = maxHealth
    // Изменяемое здоровье, на которое будет накладываться модификатор (по умолчанию максимальное)

    var currentMan: Int = maxMan

    var canAttack: Boolean = true

    // создание ссылки на корутину, чтобы можно было из вызывать или отменять(прервать) по необходимости
    // Job? - это "работа" корутины, на работу моно ссылаться, отменять, проверять ее выполнение и тд. (Процесс)
    private var manaRegenJob: Job? = null
    // ? - доступность null в данной ссылке обязателен, так как ссылка по умолчанию может вовсе не ссылаться на корутину

    private var potionJob: Job? = null
    // null - значит что при создании ссылки - она пока пустая
    // Корутина, которая будет обрабатывать получение урона от яда, и время сколько ещё яд будет действовать

    fun printStatus(){
        println("=== Статус сущности: $name ===")
        println("HP: $currentHealth / $maxHealth")
        println("MP: $currentMan / $maxMan")
        println("Может атаковать? $canAttack")
        println("------------------------------")
    }
    fun attack(target: GameCharacter){
        if (!canAttack){
            println("[!]$name не может пока атаковать")
            return// прерывать метод
        }

        if (currentHealth <= 0){
            println("[!] $name уже дед")
            return
        }

        val damage = baseAttack
        // К основному урону будем добавлять криты, баффы, эффекты и тд

        println("$name атакует ${target.name} и наносит $damage урона")
        target.takeDamage(damage)

        // Создаем кулдаун способности обычной атаки (2 сек послу атаки задержка)
        startAttackCooldown(2000L) // L - так тип данных для миллисекунд у нас будет long

    }

    fun takeDamage(amount: Int){
        if (amount <= 0){
            println("[!] $name не получает урона")
            return
        }

        currentHealth -= amount
        println("$name получен $amount урона. HP: $currentHealth / $maxHealth")

        if (currentHealth <= 0){
            currentHealth = 0
            println("$name погиб")
        }
    }

    private fun startAttackCooldown(cooldowmMillis: Long){
        canAttack = false
        println("[*] $name получает кулдаун атаки на $cooldowmMillis мс")

        // Запуск корутины в глобальной области корутин
        GlobalScope.launch {
            // GlobalScope - глобальная область для корутин, данная область выделяется и живет до тех пор по жив процесс в котором он объявлен
            // То есть запустили мы в глобальном потоке main - корутина прервется либо сама, либо тогда, когда закончится main
            // launch - это запуск корутины в главном потоке (фоновая задача)

            delay(cooldowmMillis)
            // delay - задержка, ею мы как бы задерживаем корутину
            // Но правильней сказать либо замораживаем, либо усыпляем.

            canAttack = true
            println("[*] Кулдаун на атаку закончился")
        }
    }

    fun startManaRegeneration(
        amountPerTick: Int, // Сколько маны восстановится за один тик
        intervalMillis: Long // Интервал между тем, как часто будет регенерироваться (вызываться 1 тик)
    ){
        // Проверка, если мана уже регенерирует - перезапустить корутину
        if (manaRegenJob != null){
            println("[$] Мана уже регенерирует, перезапускаем.......")
            manaRegenJob?.cancel()
            // cancel() - прерывание корутины, на которую ссылаемся. Грубо говоря удаляет е из фонового выполнения (не просто остановливает)

        }

        manaRegenJob = GlobalScope.launch {
            // В manaRegenJob сохраняем ссылку на этот процесс
            println("[*] Началась регенирация маны для $name")

            while (true){
                // Бесконечный цикл - выйдет из него когда нам потребуется (когда восстановим ману целиком или умрем)
                delay(intervalMillis)
                // Ждем заданный интервал между каждой единицей восстановления маны

                if (currentHealth <= 0){
                    println("[!] $name уже мертв")
                    break
                }

                if (currentMan >= maxMan){
                    println("[*] Мана полностью восстановлена")
                    continue
                    // continue - переход к следующей итерации цикла
                }

                currentMan += amountPerTick

                if (currentMan > maxMan){
                    currentMan = maxMan
                }
            }
            println("[&] Корутина регенерации маны - закончила работу")
        }

    }

    // Здесь после завершения корутины (или точнее выхода из launch{}) - ссылка manaRegenJob станет null

    // Создать метод отмены корутины регенерации маны

    fun stopManaRegeneration(){
        if (currentMan >= maxMan){
            println("[*] Мана полностью восстановлена")
            return
        }
    }

    fun potionTickDamage(
        damage: Int,
        amountTick: Int,
        intervalMillis: Long
    ){
        if (potionJob != null){
            println("[$] Яд уже наложен, перезапускаем.......")
            potionJob?.cancel()
        }

        potionJob = GlobalScope.launch {
            println("[*] Вы отравлены")

            while (true){
                delay(intervalMillis)

                currentHealth -= damage

                if (currentHealth <= 0){

                }
            }
        }
    }
    // Отнимать тики каждую итерацию цикла
    // Каждую итерацию цикла 
}
