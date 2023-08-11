package example.domain

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

sealed trait Transaction {

  private lazy val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

  val amount: Int
  val date: ZonedDateTime
  protected val sign: Char

  override def toString: String = s"${date.format(dateFormatter)}\t$sign$amount"

}

case class Deposit(amount: Int, date: ZonedDateTime) extends Transaction {
  override protected val sign: Char = '+'
}

case class Withdraw(amount: Int, date: ZonedDateTime) extends Transaction {
  override protected val sign: Char = '-'
}

object Transaction {

  def apply(amount: Int, date: ZonedDateTime): Transaction =
    amount match {
      case deposit if deposit > 0 => Deposit(amount, date)
      case withdraw if withdraw < 0 => Withdraw(Math.abs(amount), date)
    }

}