package Lesson8

fun  main(){
    println("=== Простая имитация игрового времени и цикла ===")

    val gameTime = GameTime()
    // Создаем объект, который будет считаться нам deltaTime и totalTime

    // Создание игрока и врага
    val player = Player(
        x = 0.0,
        speed = 2.0,
        name = "Oleg"
    )

    val enemy = Enemy(
        x = 10.0,
        speed = -1.0, // Скорость -1.0 так как враг двигается влево (к игроку)
        id = 1
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
        val tt = gameTime.totalTimeSeconds
        // dt - локальная переменная для deltaTime, чисто для удобства
        // Сколько секунд прошло спрошлого кадра

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
            println("Игрок и враг столкнулись! Останавливаем игровой цикл")
            break

        }
        // Немного "спим", чтобы цикл не крутился слишком быстро
        Thread.sleep(200)
        // Thread - класс, представляющий поток (здесь главный поток программы)
        // .sleep(200) - приостановить поток на 200 миллисекунд
        // Это нужно для того, чтобы успевал видеть изменения в консоли
        if (tt >= 5){
            println("---- Время вышло $tt ----")
            break
        }
    }

    println()
    println("=== Конец демонстрации игрового цикла")
}