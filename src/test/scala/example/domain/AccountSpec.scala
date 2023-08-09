package example.domain

class AccountSpec extends munit.FunSuite {

  test("Validar que el balance de una cuenta nueva (sin movimientos) sea cero") {
    val account: Account = Account()

    val statement: String = account.printStatement()

    assertEquals(
      statement,
      s"Date\tAmount\tBalance\n",
      "El balance de una cuenta sin movimientos tiene que ser cero"
    )
  }

  test("Validar que al realizar un depósito, el balance de la cuenta aumente") {
    val amount: Int = 500
    val account: Account = Account().deposit(amount)

    val statement: String = account.printStatement()

    assertEquals(
      statement,
      s"Date\tAmount\tBalance\n24.12.2015\t+$amount\t$amount",
      "El balance debe aumentar cuando se realice un depósito"
    )
  }

  test("Validar que no se permita un balance negativo") {
    val amount: Int = 100
    val account: Account = Account()

    intercept[example.exceptions.NegativeBalanceException] {
      account.withdraw(amount)
    }
  }

  test("Validar que sólo se permiten depósitos usando números enteros positivos") {
    val amount: Int = -500
    val account: Account = Account()

    intercept[example.exceptions.InvalidArgumentException] {
      account.deposit(amount)
    }
  }

  test("Validar que sólo se permiten retiros usando números enteros positivos") {
    val amount: Int = -100
    val account: Account = Account()

    intercept[example.exceptions.InvalidArgumentException] {
      account.withdraw(amount)
    }
  }

  test("Validar que no se permita realizar un depósito con valor en cero") {
    val amount: Int = 0
    val account: Account = Account()

    intercept[example.exceptions.InvalidArgumentException] {
      account.deposit(amount)
    }
  }

  test("Validar que no se permita realizar un retiro con valor en cero") {
    val amount: Int = 0
    val account: Account = Account()

    intercept[example.exceptions.InvalidArgumentException] {
      account.withdraw(amount)
    }
  }

}
