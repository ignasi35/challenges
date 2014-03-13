package s99

object Lists {

  def last[T](xs: List[T]): T = xs match {
    case Nil => throw new IllegalArgumentException
    case l => l.reverse.head
  }

  def penultimate[T](xs: List[T]): T = xs match {
    case Nil => throw new IllegalArgumentException
    case x :: Nil => throw new IllegalArgumentException
    case l => l.reverse.tail.head
  }

  @scala.annotation.tailrec
  def nth[T](index: Int, xs: List[T]): T = index match {
    case 0 => xs.head
    case _ => nth(index - 1, xs.tail)
  }

  def length[T](xs: List[T]): Int = xs.foldLeft(0)((acc, x) => acc + 1)

  def reverse[T](xs: List[T]): List[T] = xs.foldLeft(List[T]())((acc, x) => x :: acc)

  // I suspect this can be tailrec but still havent wrapped my head around it...
  def isPalindrome[T](xs: List[T]): Boolean =
    xs == reverse(xs)

  // this solution I don't like,
  def flatten(xss: List[Any]): List[Any] = xss.foldLeft(List[Any]())((acc, x) =>
    x match {
      case l: List[_] => flatten(l).reverse ::: acc
      case _ => x :: acc
    }).reverse

  def compress[T](xs: List[T]): List[T] = xs.foldLeft(List[T]())(
    (acc, x) =>
      if (acc.isEmpty || x != acc.head) x :: acc else acc
  ).reverse

  def pack[T](xs: List[T]): List[List[T]] = {
    @scala.annotation.tailrec
    def pack0(result: List[List[T]], acc: List[T], xs: List[T]): List[List[T]] = {
      (acc, xs) match {
        case (_, Nil) => (acc :: result).reverse
        case (Nil, _) => pack0(result, List(xs.head), xs.tail)
        case (_, h :: t) if (acc.head == h) => pack0(result, h :: acc, t)
        case _ => pack0(acc :: result, List(xs.head), xs.tail)
      }
    }
    pack0(Nil, Nil, xs)
  }

  def encode[T](xs: List[T]): List[(Int, T)] = pack(xs) map {
    l => (l.length, l.head)
  }

  def encodeModified[T](xs: List[T]): List[Any] = {
    encode(xs) map {
      case (count, key) =>
        (count, key) match {
          case (i, x) if i == 1 => x
          case tuple => tuple
        }
    }
  }

  def decode[T](xs: List[(Int, T)]): List[T] =
    xs flatMap {
      t: (Int, T) => Stream.continually(t._2).take(t._1).toList
    }

  def encodeDirect[T](xs: List[T]): List[(Int, T)] = {
    xs.foldLeft(List[(Int, T)]())((acc, x) => {
      acc match {
        case head :: tail if (head._2 == x) => (head._1 + 1, head._2) :: tail
        case _ => (1, x) :: acc
      }
    }
    ).reverse
  }

  def duplicate[T](xs: List[T]): List[T] = xs flatMap {
    x => List(x, x)
  }

  def duplicateN[T](factor: Int, xs: List[T]): List[T] =
    xs.flatMap {
      x => Stream.continually(x).take(factor)
    }

  // This requires 2 list traversals :-(
  def drop[T](factor: Int, xs: List[T]): List[T] =
    xs.zipWithIndex.filter {
      case (x, i) => (i + 1) % factor != 0
    } map {
      case (x, i) => x
    }

  def split[T](pointCut: Int, xs: List[T]): (List[T], List[T]) = {
    def removeIndex(xs: List[(T, Int)]) = xs.map(entry => entry._1)
    val (front, back) = xs.zipWithIndex.partition {
      case (x, i) => i < pointCut
    }
    (removeIndex(front), removeIndex(back))
  }

  def slice[T](offset: Int, length: Int, xs: List[T]): List[T] =
    xs.drop(offset).take(length - offset)


  def rotate[T](offset: Int, xs: List[T]): List[T] = {
    val (front, back) = split((length(xs) + offset) % length(xs), xs)
    back ++ front
  }

  def removeAt[T](index: Int, xs: List[T]): (List[T], T) = {
    val (front, back) = split(index, xs)
    (front ++ back.tail, back.head)
  }

  def insertAt[T](newItem: T, index: Int, xs: List[T]): List[T] = {
    val (front, back) = split(index, xs)
    front ++ (newItem :: back)
  }

  def range(start: Int, end: Int): List[Int] = {
    def range0(start: Int, end: Int, xs: List[Int]): List[Int] = {
      if (start == end) start :: xs
      else range0(start, end - 1, end :: xs)
    }
    range0(start, end, Nil)
  }

  def randomSelect[T](num: Int, xs: List[T]): List[T] = {
    randomPermute(xs).take(num)
  }

  def lotto(count: Int, max: Int): List[Int] = randomSelect(count, range(1, max))

  def randomPermute[T](xs: List[T]): List[T] = {
    scala.util.Random.shuffle(xs)
  }

}
