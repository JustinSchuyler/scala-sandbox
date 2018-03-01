object Index extends App {
  // val binding can not change
  val greeting = "hello world"
  // var binding can change
  var greeting2 = "hello world"
  greeting2 = "hello new world"

  // implicit return type
  def say(s: String) { println(s) }
  // explicit return type
  def say2(s: String): Unit = { println(s) }
  say2(greeting2)

  // anonymous function
  val addOne = (x: Int) => x + 1
  println(addOne(3))

  // partial application (curry)
  def parrot(times: Int, saying: String): Unit = {
    var index = 0
    for (index <- 1 to times) {
      println(saying)
    }
  }
  val thrice = parrot(3, _: String)
  parrot(2, "shiver me timbers!")
  thrice("aye aye captain!")

  // multiple arguments
  def multiply(m: Int)(n: Int): Int = m * n
  println(multiply(8)(9))

  // more curry
  val timesTwo = multiply(2) _
  println(timesTwo(6))

  // variable length arguments
  def capitalizeAll(args: String*) = {
    args.map { arg =>
      arg.capitalize
    }
  }
  val planets = capitalizeAll("mercury", "venus", "earth", "mars")
  println(planets.mkString(", "))

  // classes
  class Car {
    val brand: String = "Toyota"
    def honk(s: String) { println(s) }
  }
  val car = new Car
  car.honk("beep beep")

  // constructor
  class Dinosaur(age: Int) {
    /**
      * constructors = code outside class method definitions
      */
    val era: String = if (age <= 252 && age >= 201) {
      "Triassic"
    } else if (age < 201 && age >= 145) {
      "Jurassic"
    } else if (age < 145 && age >= 65) {
      "Cretaceous"
    } else {
      "You're not a dinosaur!"
    }
  }
  val dino = new Dinosaur(185)
  println(dino.era)

  // abstract classes (interfaces)
  abstract class Shape {
    def getArea(): Int
  }
  class Circle(r: Int) extends Shape {
    def getArea(): Int = { r * r * 3 }
  }
  val c = new Circle(2)
  println(c.getArea)

  // traits = collections of extendable fields + behaviors
  trait Shiny {
    val shineRefraction: Int
  }
  trait Heavy {
    val weight: Int
  }
  // extend multiple traits with "with"
  class Coin extends Shiny with Heavy {
    val shineRefraction = 3
    val weight = 5
  }

  // traits vs classes
  //  + class can extend multiple traits
  //  - class can extend only one class
  //  + classes can take parameters
  //  - traits can not take parameters

  // generics
  trait Cache[K, V] {
    def get(key: K): V
    def put(key: K, value: V)
    def delete(key: K)
  }
  // def remove[K](key: K)
}