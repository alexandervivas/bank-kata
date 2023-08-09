package example.domain

import example.exceptions.{InvalidArgumentException, NegativeBalanceException}

case class Account(transactions: Seq[Int] = Seq.empty) {

  def withdraw(amount: Int): Account = {
    ensureAmountToWithdrawIsNotZero(amount)
    ensureAmountToWithdrawIsNotNegative(amount)

    transactions.sum - amount match {
      case balance if balance < 0 => throw new NegativeBalanceException
      case _ => copy(transactions = transactions :+ -amount)
    }
  }

  private def ensureAmountToWithdrawIsNotNegative(amount: Int): Unit =
    if (amount < 0) throw new InvalidArgumentException

  private def ensureAmountToWithdrawIsNotZero(amount: Int): Unit =
    if (amount == 0) throw new InvalidArgumentException

  private def ensureAmountToDepositIsNotNegative(amount: Int): Unit =
    if (amount < 0) throw new InvalidArgumentException

  private def ensureAmountToDepositIsNotZero(amount: Int): Unit =
    if (amount == 0) throw new InvalidArgumentException

  def deposit(amount: Int): Account = {
    ensureAmountToDepositIsNotNegative(amount)
    ensureAmountToDepositIsNotZero(amount)

    copy(transactions = transactions :+ amount)
  }

  def printStatement(): String = "Date\tAmount\tBalance\n" + transactions.map {
    transaction => s"24.12.2015\t+$transaction\t$transaction"
  }.mkString("\n")

}
