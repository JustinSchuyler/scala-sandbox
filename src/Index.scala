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

  // apply = syntactic sugar for class/object with single use
  class Canvas {}
  object CanvasMaker {
    def apply() = new Canvas
  }
  // with apply, we can call an object like a function
  val newCanvas = CanvasMaker()
  println(newCanvas)

  // objects = single instances of a class (good for factories)
  object Timer {
    var count = 0

    def currentCount(): Long = {
      count += 1
      count
    }
  }
  println(Timer.currentCount())
  println(Timer.currentCount())

  // classes and objects can share the same name
  // when they do, the object is a "companion object"
  // companion objects are good for factories
  class Flugzeug(brand: String)
  object Flugzeug {
    def apply(brand: String) = new Flugzeug(brand)
  }
  val airbus = Flugzeug("airbus")
  val boeing = Flugzeug("boeing")
  println(airbus)
  println(boeing)

  // functions are objects
  // a function is a set of traits
  // classes/objects can inherit from Function
  // Function0 through Function22 exist
  // the number indicates # of arguments
  object addTwo extends Function1[Int, Int] {
    def apply(m: Int): Int = m + 2
  }
  println(addTwo(4))
  // shorthand
  object addThree extends (Int => Int) {
    def apply(m: Int): Int = m + 3
  }

  // packages
  // package com.example.index
  // values + functions must reside in class or object
  // objects useful for holding static functions

  // pattern matching
  val dice = 2
  dice match {
    case 1 => "one"
    case 2 => "two"
    case _ => "some other number"
  }
  dice match {
    case i if i == 1 => "one"
    case i if i == 2 => "two"
    case _ => "some other number"
  }

  // matching on type
  def checker(x: Any): Any = {
    x match {
      case i: Int if i < 0 => i * -1
      case d: Double => d + 0.1
      case s: String => s + "."
      case _ => "not sure"
    }
  }
  println(checker(-2))
  println(checker(2))
  println(checker(2.2))
  println(checker("hello"))

  // case classes = conveniently store and match contents of class
  case class Calculator(brand: String, model: String)
  val a = Calculator("TI", "89")
  val b = Calculator("TI", "89")
  println(a == b)

  def calcType(calc: Calculator): String = calc match {
    case Calculator("TI", "89") => "school"
    case Calculator("HP", "20B") => "financial"
    case Calculator("HP", "48G") => "scientific"
    // case Calculator(brand, model) => "Calculator: %s %s is of unknown type".format(brand, model)

    // last match alternatives:
    // case Calculator(_, _) => "unknown"
    // case _ => "unknown"
    // will print toString of c: Calculator(TI,9000)
    //case c@Calculator(_, _) => "Calculator: %s of unknown type".format(c)
    case c@Calculator(_, _) => "Calculator: %s %s of unknown type".format(c.brand, c.model)
  }
  println(calcType(a))
  println(calcType(Calculator("HP", "48G")))
  println(calcType(Calculator("TI", "9000")))

  // exceptions
  class ServerIsDownException extends Exception
  class RemoteCalculator {
    def add(m: Int, n: Int): Int = m + n
    def addBroken(m: Int, n: Int): Int = throw new Exception
    def addBroken2(m: Int, n: Int): Int = throw new ServerIsDownException
    // convention: include parentheses if method has side effects
    def close(): Unit = println("close remote calculator")
  }

  val remote = new RemoteCalculator
  try {
    println(remote.add(1, 2))
    println(remote.addBroken2(2, 3))
  } catch {
    case e: ServerIsDownException => println("error: remote calculator server is down")
    case e: Exception => println("error: log remote calculator exception")
  } finally {
    remote.close()
  }
}