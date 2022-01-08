package ru.fmproject.android.calculator;

public class MyExceptions extends Throwable {

    public static final int NONE = 0;
    public static final int DIVIDE_BY_ZERO = 1;
    public static final int NOT_INTEGER = 2;
    public static final int TOO_BIG = 3;
    public static final int TOO_SMALL = 4;
    public static final int NEGATIVE = 5;
    public static final int INFINITY = 6;
    public static final int NEGATIVE_INFINITY = 7;
    public static final int NOT_NUMBER = 8;

    private final String[] exp = {
            "",
            "Деление на ноль.",
            "Ожидается целое число.",
            "Очень большое число.",
            "Очень маленькое число.",
            "Ожидается положительное число.",
            "Бесконечность.",
            "Минус бесконечность.",
            "Ожидается число.",
            "Запрос был перенаправлен по другому адресу."
    };

    private final int reason;
    private final String msg;


    public MyExceptions() {
        this(NONE, "");
    }

    public MyExceptions(int reason) {
        this(reason, "");
    }

    public MyExceptions(int reason, String msg) {
        this.reason = reason;
        this.msg = msg;

    }

    public String getReason() {
        return exp[reason];
    }

    public String getMsg() {
        return msg;
    }
}