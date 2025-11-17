import kotlin.random.Random
import kotlinx.coroutines.*
// Корутины - Легкие потоки, они позволяют выполнять сразу несколько задач одновременно, без блокировки основного потока
// GlobalScope - область видимости для корутин. Где будут "жить" корутины во время работы всего приложения
// launch - запуск новой корутины
// delay - приостановка корутины, на указанное нами время, не блокируя другие потоки(корутины)

// ТЕСТОВЫЙ КЛАСС С ДЕМОНСТРАЦИЕЙ ПРИМИТИВНЫХ КОРУТИН
class BasicCoroutinesDemo{
    // ФУНКЦИЯ С ЗАДЕРЖКОЙ
    // suspend - ключевое слово, что отмечает функцию, которую можно остановить
    // !! suspend функции можно вызвать только из других suspend функций или корутин
    suspend fun simpleDelayDemo(){
        println("=== ДЕМО ЗАДЕРЖКИ КОРУТИНЫ ===")
        println("Начало выполнения: ${System.currentTimeMillis()} ")

        // 1000L - время в миллисекундах (1000мс - 1сек)
        delay(1000L) // Приостановка корутины на 1 сек

        println("Прошла 1 секунда: ${System.currentTimeMillis()}")

        delay(500L)

        println("Прошло ещё 0.5 секунда: ${System.currentTimeMillis()}")
    }

    fun multipleCoroutinesDemo(){
        println("=== Demo запуск несколько корутин ===")

        // GlobalScope.launch - запускает НОВУЮ корутину в глобальной области видимости
        // внутри GlobalScope {} - лямбда функции или же тело запускаемой функции
        GlobalScope.launch {
            // Это 1 корутина
            println("Корутина 1 - начала работу")
            delay(1000L)
            println("Корутина 1 - завершила свое выполнение")
        }

        GlobalScope.launch {
            // Это 2 корутина
            println("Корутина 2 - начала работу")
            delay(500L)
            println("Корутина 2 - завершила свое выполнение")
        }
        GlobalScope.launch {
            // Это 3 корутина
            println("Корутина 3 - начала работу")
            delay(2000L)
            println("Корутина 3 - завершила свое выполнение")
        }

        println("Все корутины завершены! Основной поток продолжает свою работу...")
    }

    suspend fun animationDemo(){
        println("\n === Demo анимации ===")

        val playerName = "Oleg"

        // Анимация появления текста по буквам
        for (i in 1..4){
            print(".")
            delay(200L)
        }
        println()

        val message = "Добро пожаловать, $playerName!"
        for (char in message){
            print(char)
            delay(100L)
        }
        println()

        println("\nЗагрузка игры...")
        val loadingFrames = listOf("⣾","⣽","⣻","⢿","⡿","⣟","⣯","⣷")

        repeat(16){ frame ->
            // % - оператор деления с остатком.
            val frameChar = loadingFrames[frame % loadingFrames.size]
            print("\r$frameChar Загрузка... ${frame * 6}%") // \r - возврат каретки (перезапись прошлой строки)
            delay(100L)
        }
        println("\nЗагрузка завершена!")

    }

    // ОЖИДАНИЕ ЗАВЕРШЕНИЯ КОРУТИНЫ
    suspend fun waitingForCoroutines(){
        // job - это объект, который представляет в себе запущенную корутину
        // job нужен для управления корутиной (например отменой выполнения или ожиданием)
        val job1 = GlobalScope.launch {
            println("JOB1: Пример долгой корутины... (задачи)")
            delay(2500L)
            println("JOB1: Долгая задача завершена")
        }
        val job2 = GlobalScope.launch {
            println("JOB2: Пример быстрой корутины... (задачи)")
            delay(500L)
            println("JOB2: Быстрая задача завершена")
        }

        println("Ожидаем завершения обеих задач")

        // job.join() - приастанавливает текущую корутину до завершения job
        job1.join() // ждем завершения первой корутины
        job2.join() // ждем завершения второй корутины
    }
}

// Пример работы с корутинами
// runBlocking - специальная функция, которая создает корутину и блокирует текущий поток до ее завершения

fun main() = runBlocking{
    // main сейчас - это основная корутина, которая блокирует поток программы до завершения всех корутин внутри ее
    val demo = BasicCoroutinesDemo()

    // Последовательный запуск демонстраций
    demo.simpleDelayDemo()
    demo.multipleCoroutinesDemo()

    //ждать нужно, чтобы асинхронные корутины успели выполниться
    delay(2000L)

    demo.animationDemo()
    demo.waitingForCoroutines()

    println("=== ЗАВЕРЩЕНИЕ ТЕКСТА КОРУТИНЫ ===")
}

