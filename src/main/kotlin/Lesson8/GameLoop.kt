package Lesson8

import kotlin.random.Random

fun  main(){
    println("=== Простая имитация игрового времени и цикла ===")

    val gameTime = GameTime()
    val randomX = Random.nextDouble(25.0, 100.0)
    val randomHp = Random.nextInt(40,90)

    // Создаем объект, который будет считаться нам deltaTime и totalTime

    // Создание игрока и врага
    val player = Player(
        x = 0.0,
        speed = 5.0,
        name = "Oleg",
        health = 100,
        maxHealth = 100,
        damage = Random.nextInt(10,40),

    )

    val enemy = Enemy(
        x = randomX,
        speed = 0.0, // Скорость -1.0 так как враг двигается влево (к игроку)
        id = 1,
        health = randomHp,
        maxHealth = randomHp,
        damage = Random.nextInt(5,20)
    )
    val sword = Item(
        id = 10,
        name = "Меч"
    )

    println("Начальное положение сущность")
    player.printPosition()
    enemy.printPosition()

    println()
    println("Начало игры ")

    while (true){
        gameTime.update()
        // Обновляем время каждый цикл - считаем дельту и общее время

        val dt  = gameTime.deltaTimeSeconds
        // dt - локальная переменная для deltaTime, чисто для удобства
        // Сколько секунд прошло с прошлого кадра

        // Обновляем позиции объектов с прошлого кадра

        // Обновляем позиции объекта по времени
        player.update(dt)
        enemy.update(dt)

        // Выводим информацию
        println()
        println("Прошло времени: ${"%.3f".format(gameTime.totalTimeSeconds)}")
        // %.3f".format(...) - форматирование числа:
        // %.3f - число с 3 знаками после запятой (например 1.234)

        player.printPosition()
        enemy.printPosition()

        // Проверим, "столкнулись" ли игрок и враг
        val distance = enemy.x - player.x
        // distance - расстояние между врагом и игроком по оси х
        // enemy.x - player.x - обычное вычитание двух Double

        println("Расстояния между игроком и врагом: ${"%.3f".format(distance)}")

        if (distance <= 0.0){
            // Если расстояние <= 0, считаем, что они встретились или пересеклись
            println()
            println("Игрок и враг столкнулись! Начинается бой")
            while (player.isAlive){
                player.attacked(enemy)
                if (!enemy.isAlive) {
                    println("Бой окончен. Вы победили")
                    break
                }
                enemy.attacked(player)
                if (!player.isAlive){
                    println("Неудача. Вы проиграли")
                    break
                }
            }

        }
        // Немного "спим", чтобы цикл не крутился слишком быстро
        Thread.sleep(200)
        // Thread - класс, представляющий поток (здесь главный поток программы)
        // .sleep(200) - приостановить поток на 200 миллисекунд
        // Это нужно для того, чтобы успевал видеть изменения в консоли
        if(player.x >= 110.0){
            println("Вы дошли до финиша!!!")
            break
        }
    }

    println()
    println("=== Конец демонстрации игрового цикла")
}