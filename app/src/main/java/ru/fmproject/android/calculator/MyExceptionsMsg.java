package ru.fmproject.android.calculator;

public enum MyExceptionsMsg {
    NONE(""),
    DIVIDE_BY_ZERO("Деление на ноль."),
    NOT_INTEGER("Ожидается целое число."),
    TOO_BIG("Очень большое число."),
    TOO_SMALL("Очень маленькое число."),
    NEGATIVE("Ожидается положительное число."),
    INFINITY("Бесконечность."),
    NEGATIVE_INFINITY("Минус бесконечность."),
    NOT_NUMBER("Ожидается число.")
    ;

    MyExceptionsMsg(String s) {
    }
}
