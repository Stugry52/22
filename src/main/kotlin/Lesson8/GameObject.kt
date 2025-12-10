package Lesson8

open class GameObject (
    // open - ключевое слово, которое позволяет наследовать данный класс другим классам

    var x: Double, // x- позиция по оси Х (горизонталь - пока примитив)

    var speed: Double // speed - скорость (сколько единиц в секунду обьект пройдет по горизантали)
){
    open fun update(deltaTimeSeconds: Double){
        // open fun - позволяет переопределить метод в дочерних классах
        // без него override не будет работать
        // В роли параметра принимает количество секунд с прошлого кадра

        x += speed * deltaTimeSeconds
        // Умножением мы указываем сколько единиц мы должны пройти за это время
        // Например: speed = 2.0(2 юнита/секунд), delta = 0.5 сек
        // в = 2.0 * 0.5 = 1.0 (пройдет 1 юнит за отведенную секунду)
        // x+= - прибавляем посчитанное время к текущей позиции игрока или врага на координате х
    }
}

class Player(
    x: Double,
    speed: Double,
    val name: String
): GameObject (x, speed){
    // :GameObject - означает наследовать родителя с передачей парметров базовому классу
    fun printPosition(){
        println("Игрок $name находется сейчас на позии x = $x")
    }
}

class Enemy(
    x: Double,
    speed: Double,
    val id: Int
) :GameObject (x, speed){
    fun printPosition(){
        println("Враг: $id находится на позиции x = $x")
    }
}