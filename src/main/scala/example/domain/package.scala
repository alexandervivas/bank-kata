package example

package object domain {

  // This class extends Int capabilities to also perform operations between Integers and Transactions
  implicit class IntOps(value: Int) {

    def +(transaction: Transaction): Int = transaction match {
      case Deposit(amount, _) => value + amount
      case Withdraw(amount, _) => value - amount
    }

  }

}
