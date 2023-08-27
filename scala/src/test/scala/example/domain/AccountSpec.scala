package example.domain

import org.assertj.core.api.Assertions.assertThat
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.{times, verify, when}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.mockito.MockitoSugar

import java.time.{ZoneOffset, ZonedDateTime}

class AccountSpec extends AnyFunSuite with MockitoSugar {

  test("Validar que el balance de una cuenta nueva (sin movimientos) sea cero") {
    val account: Account = Account()

    val statement: String = account.printStatement()

    assertThat(statement).isEqualTo(s"Date\tAmount\tBalance\n")
  }

  test("Validar que al realizar un depósito, el balance de la cuenta aumente") {
    val amount: Int = 500
    val timeWrapper: ZonedDateTimeWrapper = mock[ZonedDateTimeWrapper]
    val zonedDateTime: ZonedDateTime = ZonedDateTime.of(2015, 12, 24, 0, 0, 0, 0, ZoneOffset.UTC)
    when(timeWrapper.now(any())).thenReturn(zonedDateTime) // esto es un STUB
    val account: Account = Account(timeWrapper).deposit(amount)

    val statement: String = account.printStatement()

    assertThat(statement).isEqualTo(s"Date\tAmount\tBalance\n24.12.2015\t+$amount\t$amount\n")
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

  test("Validar que al realizar un retiro, el balance de la cuenta disminuye") {
    val initialBalance: Int = 500
    val amount: Int = 100
    val timeWrapper: ZonedDateTimeWrapper = mock[ZonedDateTimeWrapper]
    val depositTime: ZonedDateTime = ZonedDateTime.of(2015, 12, 24, 0, 0, 0, 0, ZoneOffset.UTC)
    val withdrawTime: ZonedDateTime = ZonedDateTime.of(2016, 8, 23, 0, 0, 0, 0, ZoneOffset.UTC)
    when(timeWrapper.now(any())).thenReturn(depositTime).thenReturn(withdrawTime) // esto es un STUB
    val account: Account = Account(timeWrapper).deposit(initialBalance).withdraw(amount)

    val statement: String = account.printStatement()

    assertThat(statement).isEqualTo(s"Date\tAmount\tBalance\n24.12.2015\t+$initialBalance\t$initialBalance\n23.08.2016\t-$amount\t${initialBalance - amount}\n")
  }

}
