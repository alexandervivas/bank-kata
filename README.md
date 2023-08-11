# bank-kata

Your bank is tired of its mainframe COBOL accounting software and they hired both of you for a greenfield project in - what a happy coincidence

- your favorite programming language!

Your task is to show them that your TDD-fu and your new-age programming language can cope with good ole’ COBOL!

Requirements
Write a class Account that offers the following methods void deposit(int) void withdraw(int) String printStatement()

An example statement would be:

```shell
Date        Amount  Balance
24.12.2015   +500      500
23.8.2016    -100      400
```

Testea para probar el comportamiento, no para probar la implementación

Casos

- Validar que el balance de una cuenta nueva (sin movimientos) sea cero
- Validar que al realizar un depósito, el balance de la cuenta aumente
- Validar que no se permita un balance negativo
- Validar que sólo se permiten depósitos usando números enteros positivos
- Validar que sólo se permiten retiros usando números enteros positivos
- Validar que no se permita realizar un depósito con valor en cero
- Validar que no se permita realizar un retiro con valor en cero
- Validar que al realizar un retiro, el balance de la cuenta disminuye
- Validar que las fechas que se muestran corresponden a las fechas reales de las operaciones
