package Lesson8

class GameTime {
    private var lastTimeMillis: Long = System.currentTimeMillis()
    // private == время только в этом классе
    // Считаем последнее время в миллисекундах на которое будет опираться игровое событие
    // :Long - это целое длинное число (может хранить до 4 294 967 295)
    // System - это класс для стандартной Java - библиотеки
    // .currentTimeMillis() - метод которые возвращает текущее время в миллисекундах
    // 1/1000 - секунды

    var deltaTimeSeconds: Double = 0.0
    // Delta - это время прошедшее между двумя кадрами
    // Double - число с плавающей точкой (может иметь дробную часть)

    var totalTimeSeconds: Double = 0.0
    // Подсчет сколько всего прошло времени с момента запуска игры

    fun update(){
        // Метод update мы будем вызывать каждый игровой кадр (Frame)
        // Все что он делает, это обновляет totalTimeSeconds и deltaTimeSeconds

        val currentTimeMillis = System.currentTimeMillis()
        // Текущее время

        val deltaMillis = currentTimeMillis - lastTimeMillis
        // Дельта - разница между текущим временем и прошлым кадром

        deltaTimeSeconds = deltaMillis / 1000.0
        // Просто перевод миллисекунд в секунды

        totalTimeSeconds += deltaTimeSeconds
        // Накапливаем общее время между всеми прошедшими кадрами за все время игры

        lastTimeMillis = currentTimeMillis
        // Обновляем время предыдущего кадра на текущее
        // В следующем кадре будем считать разницу от этого верменного момента
    }
}

// То есть время - дял нас сейчас не константа, мы каждый обновленем время, которое уже прошло
// И за счет этого будет "двигаться" наш мир и фиксироваться события в нем относительно друг друга

