//import kotlinx.coroutines.* // Импорт библиотеки корутин (* - значит импорт все)
//import kotlin.system.measureTimeMillis // Функция измерения времени выполнения
//import kotlin.time.Duration
//import kotlin.time.DurationUnit
//import kotlin.time.ExperimentalTime
//
//// Класс для упраления игровым временем
//class GameTime{
//    // L - Long - тип данных для больших чисел (в милисекундах)
//    private var lastFrameTime = 0L
//
//    val deltaTime: Float
//        get() {
//            val currentTime = System.currentTimeMillis() // Возвращает текущее время в миллисекундах
//            val delta = (currentTime - lastFrameTime) / 1000f // 1000f преобразование мллисекунд в секунды (float)
//            lastFrameTime = currentTime // обновление времени последнего кадра
//
//            return delta
//        }
//
//    fun initialize(){
//        lastFrameTime = System.currentTimeMillis() // Инициализируем время при старте игры
//
//    }
//}
//
//
//// Базовый класс для игровых объектов с анимацией
//open class GameObject(var x: Float, var y: Float){
//    // скорости в единицах в секундах (не в кадрах)
//    open val speed: Float = 50f // 50 пикселей в секунду
//}






















