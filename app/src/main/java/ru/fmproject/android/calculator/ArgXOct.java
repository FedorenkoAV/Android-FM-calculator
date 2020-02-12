package ru.fmproject.android.calculator;

//import ru.fmproject.android.calculator.L;

/**
 * Created by User on 21.07.2017.
 * ArgX это переменная, в которой в текстовом виде хранится число в восьмеричной форме в дополнительном коде
 * (число отдельно, знак отдельно)
 */

class ArgXOct extends ArgXParent {

    private static final String TAG = "ArgXClass";

    ArgXOct() {
        super();
    }

    ArgXOct(double doubleNumber) {
        super(doubleNumber);
    }

    @Override
    void setNumber(int intNumber) {
        setSign(false);
        if (intNumber < 0) {
            setSign(true);
            intNumber = -intNumber;
        }
        setNumber(new StringBuilder(Integer.toOctalString(intNumber)));
        setEditable(false);
        setNotVirgin();
    }

    @Override
    void setNumber(long longNumber) {
        setSign(false);
        if (longNumber < 0) {
            setSign(true);
            longNumber = -longNumber;
        }
        setNumber(new StringBuilder(Long.toOctalString(longNumber)));
        setEditable(false);
        setNotVirgin();
    }

    @Override
    long getLong(int byteLength) {
        long longNumber = 0;
        L.d(TAG, "В ArgXOct лежит: " + getNumber());
        try {
            longNumber = Long.parseLong(getNumber().toString(), 8);
        } catch (Exception e) {
//            customToast.setToastText("Произошла неизвестная ошибка: " + e);
//            customToast.show();
            L.d(TAG, "При старте приложения произошла ошибка: " + e);
            StackTraceElement[] stackTraceElements = e.getStackTrace();

            for (int i = 0; i < stackTraceElements.length; i++) {
                L.d(TAG, i + ": " + stackTraceElements[i].toString());
            }
        }

        if (isSign()) {
            longNumber = -1 * longNumber;
        }
        L.d(TAG, "После преобразования в long получили: " + longNumber);
        return longNumber;
    }
}