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

  def printStatement(): String = {
    val transactionsWithIndex: Seq[(Int, Int)] = transactions.zipWithIndex
    "Date\tAmount\tBalance\n" + transactionsWithIndex.map {
      case (transaction, index) => s"24.12.2015\t${if(transaction > 0) "+" else ""}${transaction}\t${transactionsWithIndex.filter(_._2 <= index).map(_._1).sum}"
    }.mkString("\n") + s"${if(transactions.nonEmpty) "\n" else ""}"
  }

}
