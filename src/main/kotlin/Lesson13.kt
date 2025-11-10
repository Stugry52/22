import jdk.dynalink.Operation
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Класс для логирования ошибок и событий игры
class GameLogger{
    private val logFile = "game_log.txt"
    private val fileManager = SafeFileManager()

    // Метод записи логов в файл
    fun log(message: String, level: String = "INFO"){
        val timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val logEntry = "[$timestamp] [$level] $message\n"

        // Проверка на дублирование важных сообщений
        if (level == "ERROR" || level == "WARN"){
            println("LOG [$level]: $message")
        }

        // Запись в файл логирования
        fileManager.writeFileSafely(logFile, logEntry)
    }

    // Методы помощники для разных уровней логирования
    fun info(message: String) = log(message, "INFO")
    fun warn(message: String) = log(message, "WARN")
    fun error(message: String) = log(message, "ERROR")
    fun debug(message: String) = log(message, "DEBUG")
}

// Базовый класс для всех игровых систем с обработкой ошибок
abstract class GameSystem(val systemName: String, protected val logger: GameLogger){

    // Метод для безопасного выполнения операций системы
    // <T> - объявление обобщенного типа данных
    // T - Плейсхолдер (заполнитель) для любого типа данных
    protected fun <T> executeSafely(operation: String, block: () -> T): T?{
        try {
            logger.debug("$systemName: Начало операции $operation")
            val result = block() // Выполняем переданный блок кода
            logger.debug("$systemName: Операции $operation завершена успешно")
            return result
            // вернуть результат работы
        }catch (e: Error){
            println("ERROR: Can't end operation")
            return null
        } // сделать catch исключения с выводом лог-строк с уровнем error и вернуть null
    }
}