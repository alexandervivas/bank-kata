package example.domain

import example.exceptions.{InvalidArgumentException, NegativeBalanceException}

import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

case class Account(timeWrapper: ZonedDateTimeWrapper = ZonedDateTimeWrapper(), transactions: Seq[Transaction] = Seq.empty) {

  private lazy val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

  def withdraw(amount: Int): Account = {
    ensureAmountToWithdrawIsNotZero(amount)
    ensureAmountToWithdrawIsNotNegative(amount)

    val transaction: Transaction = Transaction(-amount, timeWrapper.now(ZoneOffset.UTC))

    transactions.map(_.amount).sum - amount match {
      case balance if balance < 0 => throw new NegativeBalanceException
      case _ => copy(transactions = transactions :+ transaction)
    }
  }

  private def ensureAmountToWithdrawIsNotNegative(amount: Int): Unit =
    if (amount < 0) throw new InvalidArgumentException

  private def ensureAmountToWithdrawIsNotZero(amount: Int): Unit =
    if (amount == 0) throw new InvalidArgumentException

  def deposit(amount: Int): Account = {
    ensureAmountToDepositIsNotNegative(amount)
    ensureAmountToDepositIsNotZero(amount)

    val transaction: Transaction = Transaction(amount, timeWrapper.now(ZoneOffset.UTC))

    copy(transactions = transactions :+ transaction)
  }

  private def ensureAmountToDepositIsNotNegative(amount: Int): Unit =
    if (amount < 0) throw new InvalidArgumentException

  private def ensureAmountToDepositIsNotZero(amount: Int): Unit =
    if (amount == 0) throw new InvalidArgumentException

  def printStatement(): String = {
    type ProvisionalStatement = (String, Int)
    transactions
      .foldLeft[ProvisionalStatement](("Date\tAmount\tBalance\n", 0)) {
        case ((statement, currentBalance), transaction) =>
          val newBalance: Int = currentBalance + transaction.amount
          val sign: String = if (transaction.amount > 0) "+" else ""
          val line: String = s"${transaction.date.format(dateFormatter)}\t$sign${transaction.amount}\t$newBalance\n"
          (statement + line, newBalance)
      }._1
  }

}
