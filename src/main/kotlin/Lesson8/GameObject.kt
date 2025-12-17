package Lesson8
import Lesson7.Item
import kotlin.random.Random

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

open class Character(
    val name: String,
    var health: Int,
    val attack: Int){
    val isAlive: Boolean get() = health > 0

    open fun takeDamage(damage: Int){
        health -= damage
        println("$name получает $damage")
        if(health <= 0) println("$name пал в бою!")
    }

    open fun attack(target: Character){
        if (!isAlive || !target.isAlive) return
        val damage = Random.nextInt(attack - 3, attack + 4) // Случайный урон в диапозоне
        println("$name атакует ${target.name}")
        target.takeDamage(damage)
    }
}
class Player(
    x: Double,
    speed: Double,
    val name: String,
    var health: Int,
    val maxHealth: Int,
    val damage: Int,
    var isAlive: Boolean = true
): GameObject (x, speed){
    // :GameObject - означает наследовать родителя с передачей параметров базовому классу
    fun printPosition(){
        println("Игрок $name находится сейчас на позиции x = $x")
    }
    fun attacked(enemy: Enemy){
        if (!isAlive || !enemy.isAlive) return
        enemy.takeDamage(damage)
        println("Игрок наносит урон по Врагу $damage")

    }
    fun takeDamage(damage: Int){
        health -= damage
        println("Игрок получил урон, осталось $health/$maxHealth")
        if(health <= 0){
            isAlive = false
            println("$name пал в бою!")
        }
    }

}

class Enemy(
    x: Double,
    speed: Double,
    val id: Int,
    var health: Int,
    val maxHealth: Int,
    val damage: Int,
    var isAlive: Boolean = true
) :GameObject (x, speed){

    fun printPosition(){
        println("Враг: $id находится на позиции x = $x")
    }
    fun spawnEnemy(){
        println("Появился враг на растоянии $x")

    }
    fun attacked(player: Player){
        if (!isAlive || !player.isAlive) return
        player.takeDamage(damage)
        println("Враг наносит урон по игроку $damage, у игрока осталось $health/$maxHealth")
    }
    fun takeDamage(damage: Int){
        health -= damage
        println("Враг получил урон, осталось $health/$maxHealth")
        if(health <= 0) {
            isAlive = false
            println("Враг побежден пал в бою!")
        }
    }
}
class Item(
    val id: Int,
    val name: String,
)
class Inventory{
    val items: MutableList<Item> = mutableListOf()

    fun addItem(item: Item){
        println("Вы нашли предмет ${item.name}")
        items.add(item)
    }
}