import example.domain._

val account: Account = Account()
  .deposit(500)
  .withdraw(100)
  .deposit(500)
  .withdraw(100)
  .deposit(500)
  .withdraw(100)

println(account.printStatement())