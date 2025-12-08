package old_money

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
    // T - Плейсхолдер (заполнитель) для любого типа
    // Надо думать о T как о "Чем-то временном или абстрактном" как ячейка в которую подставим какой-то объект
    protected fun <T> executeSafely(operation: String, block: () -> T): T?{
        // Читаем верхнюю строку, как:
        // "Функция executeSafely работает(выполняется) с каким-то типом T"
        // Она принимает операцию (строку) и блок кода, который возвращает T
        // Сама функция после выполнения возвращает T? (T или null)
        try {
            logger.debug("$systemName: Начало операции $operation")
            val result = block() // Выполняем переданный блок кода
            logger.debug("$systemName: Операции $operation завершена успешно")
            return result
            // вернуть результат работы
        }catch (e: Exception){
            logger.error("$systemName: Ошибка операции $operation - ${e.message}")
            return null
        } // сделать catch исключения с выводом лог-строк с уровнем error и вернуть null
    }

    // Абстрактный метод для инициализации системы
    abstract fun initialize(): Boolean
    // Абстрактный метод для экстренной остановки системы
    abstract fun emergencyShutdown()

    val resultName: String? = executeSafely("Получение имени игрока "){
        // Здесь может быть не ограниченое число кода, которое после вычесления должен вурнуть String
        "Oleg"
    }
    val resultInt: Int? = executeSafely("Расчет урона"){
        42
    }
    val resultBool: Boolean? = executeSafely("Проверка жизни"){
        true
    }
}

// Система боя с обработкой ошибок
class CombatSystem(logger: GameLogger) : GameSystem("old_money.CombatSystem", logger){
    private var isInitialized = false

    override fun initialize(): Boolean {
        return executeSafely("initialize"){
            // Имитация инициализация системы боя
            logger.info("Инициализация системы боя...")
            Thread.sleep(100) // Имитация загрузки элементов и процесов
            isInitialized =true
            logger.info("Система боя успешно инициализирована")
            true
        } ?: false
    }

    fun performAttack(attacker: String, target: String, damage: Int): Boolean{
        if (!isInitialized){
            logger.warn("Попытка атаки при неинициализированной системы боя")
            return false
        }

        return  executeSafely("performAttack"){
            // Проверка корректности введенных параметров
            if (damage < 0){
                throw IllegalArgumentException("Урон не может быть отрицательным: $damage")
            }
            if (attacker.isBlank() || target.isBlank()){
                throw IllegalArgumentException("Имена персонажей не могут быть пустыми")
            }

            logger.info("$attacker атакует $target с уроном: $damage")
            true
        } ?: false
    }

    override fun emergencyShutdown() {
        logger.warn("Аварийное завершение системы боя")
        isInitialized = false
        // Здесь в будущем освобождение ресурсов, сохранение состояний и тд.

    }
}

class InventorySystem(logger: GameLogger): GameSystem("old_money.InventorySystem", logger){
    private val items = mutableListOf<String>()
    private var isInitialized = false

    override fun initialize(): Boolean {
        return executeSafely("initialize") {
            logger.info("Инит системы инвенторя...")
            // Загрузка предметов по умолчания, при создании игрока
            items.addAll(listOf("Старый меч", "Поношенный доспех"))
            isInitialized = true
            logger.info("Система инвентаря инит. успешно")
            true
        } ?: false
    }
    fun addItem(item: String): Boolean{
        if (!isInitialized){
            logger.warn("Попытка добавить предмет в инвентарь без инит. системы")
            return false
        }

        return executeSafely("addItem"){
            if (item.isBlank()){
                throw IllegalArgumentException("Название предмета не может быть пустым")
            }
            if (items.size >= 20){
                throw IllegalArgumentException("Инвентарь переполнен (максимум 20 предметов)")
            }

            items.add(item)
            logger.info("Предмет $item добавлен в инвентарь. Всего предмето: ${items.size}")

            true
        } ?: false
    }

    fun getItems(): List<String>{
        if (!isInitialized){
            logger.warn("Попытка получить предмет без инит. системы")
            return emptyList()
        }

        return executeSafely("getItems"){
            items.toList() // Возвращаем копии списка
        } ?: emptyList()

    }

    override fun emergencyShutdown() {
        logger.warn("Аварийное завершение системы боя")
         // Логирование warn экстренного отключения системы инвенторя

        // Сохранение состояния инвенторя перед отключением
        // Создание бэкап-списка - использовать метод joinToString("\n")
        val backUpItems = items.joinToString("\n")
        // Используя метод File в аттрибут которого мы кладем "название_файла-бэкапа .txt" + записать в файл созданный бэкап - writeText(бэкап-список)
        val fileBackUp = File("$backUpItems .txt")
        fileBackUp.writeText( backUpItems)
        // Проверка на инициализацию должна стать false
        isInitialized = false
    }
}