package ru.fmproject.android.calculator

/**
 * Created by User on 23.06.2017.
 */

internal class MyExceptions : Throwable {

    var exp = arrayOf("", "Деление на ноль.", "Ожидается целое число.", "Очень большое число.", "Очень маленькое число.", "Ожидается положительное число.", "Бесконечность.", "Минус бесконечность.", "Ожидается число.", "Запрос был перенаправлен по другому адресу.")

    var msg = ""
    private var reason: Int = 0


    constructor(reason: Int) {
        this.reason = reason
    }

    constructor(reason: Int, msg: String) {
        this.reason = reason
        this.msg = msg
    }

    fun getReason(): String {
        return exp[reason]
    }

    companion object {

        val NONE = 0
        val DIVIDE_BY_ZERO = 1
        val NOT_INTEGER = 2
        val TOO_BIG = 3
        val TOO_SMALL = 4
        val NEGATIVE = 5
        val INFINITY = 6
        val NEGATIVE_INFINITY = 7
        val NOT_NUMBER = 8
        val REDIRECT = 9
    }
}
