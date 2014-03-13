package s99

import org.scalatest.FunSuite

/**
 * Created by ignasi on 09/02/14.
 */
class ArithmeticSuite extends FunSuite {


  import s99.Arithmetic._

  // P31
  test("Determine whether a given integer number is prime.") {
    assert{ 7.isPrime  == true}
  }

  test("Determine the greatest common divisor of two positive integer numbers. Using Euclid alg."){
    assert(gcd(36, 63)==9)
  }

  test("Determine whether two positive integer numbers are coprime. Two numbers are coprime if their greatest common divisor equals 1."){
    assert(35.isCoprimeTo(64) == true)
  }

  // P34
  test("Calculate Euler's totient function phi(m).\nEuler's so-called totient function phi(m) is defined " +
    "as the number of positive integers r (1 <= r <= m) that are coprime to m.") {
    assert(10.totient == 4)
  }

  /*
  P35 (**) Determine the prime factors of a given positive integer.
Construct a flat list containing the prime factors in ascending order.
scala> 315.primeFactors
res0: List[Int] = List(3, 3, 5, 7)
P36 (**) Determine the prime factors of a given positive integer (2).
Construct a list containing the prime factors and their multiplicity.
scala> 315.primeFactorMultiplicity
res0: List[(Int, Int)] = List((3,2), (5,1), (7,1))
Alternately, use a Map for the result.

scala> 315.primeFactorMultiplicity
res0: Map[Int,Int] = Map(3 -> 2, 5 -> 1, 7 -> 1)
P37 (**) Calculate Euler's totient function phi(m) (improved).
See problem P34 for the definition of Euler's totient function. If the list of the prime factors of a number m is known in the form of problem P36 then the function phi(m>) can be efficiently calculated as follows: Let [[p1, m1], [p2, m2], [p3, m3], ...] be the list of prime factors (and their multiplicities) of a given number m. Then phi(m) can be calculated with the following formula:
phi(m) = (p1-1)*p1(m1-1) * (p2-1)*p2(m2-1) * (p3-1)*p3(m3-1) * ...

Note that ab stands for the bth power of a.

P38 (*) Compare the two methods of calculating Euler's totient function.
Use the solutions of problems P34 and P37 to compare the algorithms. Try to calculate phi(10090) as an example.

P39 (*) A list of prime numbers.
Given a range of integers by its lower and upper limit, construct a list of all prime numbers in that range.
scala> listPrimesinRange(7 to 31)
res0: List[Int] = List(7, 11, 13, 17, 19, 23, 29, 31)
   */
}
