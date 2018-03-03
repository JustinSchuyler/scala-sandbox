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
    // convention: include parentheses if method has side-effects
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

  // arrays preserve order, can contain duplicates, are mutable
  val numbers = Array(1, 2, 1)
  // prints something like [I@5315b42e
  println(numbers)
  println(numbers(1))
  numbers(1) = 5
  println(numbers(1))

  // lists preserve order, can contain duplicates, are immutable
  val numbers2 = List(1, 2, 1)
  // "value update is not a member of List[Int]"
  // numbers2(1) = 7

  // sets do not preserve order, no duplicates
  val numbers3 = Set(1, 2, 1)
  println(numbers3)

  // tuple groups simple logical collections without using a class
  val hostPort = ("localhost", 80)
  // tuples don't have named accessors; instead they are named by position (1-based)
  println("host: " + hostPort._1)
  println("port: " + hostPort._2)

  val matchMessage = hostPort match {
    case ("localhost", 80) => "perfect match!"
    case ("localhost", port) => "host matched; port: %d".format(port)
    case (host, port) => "host: %s; port: %d".format(host, port)
  }
  println(matchMessage)

  // syntactic sugar for creating tuple of 2 values
  val sugarTuple = 1 -> 2
  println(sugarTuple)

  // maps can hold basic datatypes
  val testMap = Map("holyGrail" -> "over the bridge and through the woods")
  println(testMap)
  println(testMap("holyGrail"))

  // Option is a container that may or may not hold a value
  val numberMap = Map("one" -> 1, "two" -> 2)
  val optionFromMap = numberMap.get("one")
  // prints Some(1)
  println(optionFromMap)
  val result = optionFromMap.getOrElse(0)
  println(result * 7)

  val optionFromMap2 = numberMap.get("three")
  // prints None
  println(optionFromMap2)
  val result2 = optionFromMap2.getOrElse(0)
  println(result * 9)

  // pattern matching works with options too
  val result3 = optionFromMap match {
    case Some(n) => n * 2
    case None => 0
  }
  println(result3)

  // map is a functional combinator
  def squared(m: Double): Double = scala.math.pow(m, 2)
  val squarables = List(1.0, 5.0, 9.0)
  // val squaredResults = squarables.map((m: Double) => squared(m))
  val squaredResults = squarables.map(squared)
  println(squaredResults)

  // foreach is like map, but returns nothing (intended for side-effects only)
  squarables.foreach((m: Double) => m * 2)

  // filter removes elements where the function passed in evaluates to false
  // functions that return booleans = predicate functions
  val largeNumbers = squarables.filter((m: Double) => m > 5)
  println(largeNumbers)
  def isEven(m: Int): Boolean = m % 2 == 0
  println(List(2, 5, 17, 24).filter(isEven))

  // zip aggregates two lists into single list of pairs
  val pairs = List(1, 2, 3).zip(List("a", "b", "c"))
  println(pairs)

  // partition splits a list based on a predicate function
  val run = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
  val runPartitioned = run.partition(_ % 2 == 0)
  println(runPartitioned)

  // find returns the first element that matches the predicate
  val target = run.find(_ > 5)
  // target is an Option[Int]
  println(target)

  // drop removes the first i elements
  println(run.drop(5))

  // dropWhile removes elements matching the predicate until the predicate fails to match
  // 9 will survive because it's shielded by 8
  println(List(1, 3, 5, 8, 9).dropWhile(_ % 2 != 0))

  // foldLeft takes a starting value, iterates over collection (left to right), adds to accumulator
  val compressed = run.foldLeft(0)((acc: Int, cur: Int) => acc + cur)
  println(compressed)
  // see each iteration:
  val compressed2 = run.foldLeft(0) { (acc: Int, cur: Int) => println("acc: " + acc + " cur: " + cur); acc + cur }
  println(compressed2)

  // foldRight does the same, iterating right to left

  // flatten collapses one level of a nested structure
  println(List(List(1, 2), List(3, 4)).flatten)

  // flatMap will map and flatten
  val nestedNumbers = List(List(1, 2), List(3, 4))
  val flatMapResult = nestedNumbers.flatMap(x => x.map(_ * 2))
  println(flatMapResult)
  // short-hand for:
  val flatMapResult2 = nestedNumbers.map((x: List[Int]) => x.map(_ * 2)).flatten
  println(flatMapResult2)

  // Maps + functional combinators
  val phoneExtensions = Map("Sally" -> 100, "Marly" -> 101, "Olive" -> 201)
  // this requires positional accessors:
  val filteredPhones = phoneExtensions.filter((namePhone: (String, Int)) => namePhone._2 < 200)
  // we can get around that with case:
  val filteredPhones2 = phoneExtensions.filter({ case (name, extension) => extension < 200 })
  println(filteredPhones2)
}