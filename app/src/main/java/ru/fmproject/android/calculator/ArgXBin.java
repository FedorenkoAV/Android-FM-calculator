package ru.fmproject.android.calculator;

//import ru.fmproject.android.calculator.L;

/**
 * Created by User on 29.06.2017.
 * ArgX это переменная, в которой в текстовом виде хранится число в двоичной форме в дополнительном коде
 * (число отдельно, знак отдельно)
 */

public class ArgXBin extends ArgXParent {

    private static final String TAG = "ArgXClass";

    public ArgXBin() {
        super();
    }

    public ArgXBin(double doubleNumber) {
        super(doubleNumber);
    }

    @Override
    public void setNumber(int intNumber) {
        setSign(false);
        if (intNumber < 0) {
            setSign(true);
            intNumber = -intNumber;
        }
        setNumber(new StringBuilder(Integer.toBinaryString(intNumber)));
        setEditable(false);
        setNotVirgin();
    }

    @Override
    public void setNumber(long longNumber) {
        setSign(false);
        if (longNumber < 0) {
            setSign(true);
            longNumber = -longNumber;
        }
        setNumber(new StringBuilder(Long.toBinaryString(longNumber)));
        setEditable(false);
        setNotVirgin();
    }

    @Override
    public long getLong(int byteLength) {
        long longNumber = 0;
        L.d(TAG, "В ArgXBin лежит: " + getNumber());
        if (getNumber().length() == 0) {
            return 0;
        }
        StringBuilder num = new StringBuilder();
        num.append(getNumber());
        int curDigit;
        boolean sign = isSign();
        curDigit = Character.digit(num.charAt(0), 2);
        if ((num.length() == byteLength * 8) && (curDigit > 0)) {//Если у нас число максимальной длинны и первый символ больше 0, то у нас отрицательное число
            L.d(TAG, "У нас число максимальной длинны и первый символ больше 0, т.е. " + num + " это отрицательное число.");
            sign = !sign;//Инвертируем знак
            L.d(TAG, "Инвертируем знак sign = " + sign);
            L.d(TAG, "Инвертируем цифры числа побитово.");
            for (int i = 0; i < num.length(); i++) {
                switch (num.charAt(i)) {
                    case '0':
                        num.setCharAt(i, '1');
                        break;
                    case '1':
                        num.setCharAt(i, '0');
                        break;
                }
            }
            L.d(TAG, "Положительное число в прямом коде: " + num);
            longNumber = Long.parseLong(num.toString(), 2);
            L.d(TAG, "Преобразовали к типу long: " + longNumber);
            longNumber++;
            L.d(TAG, "Увеличили на еденицу: " + longNumber);
        } else {
            try {
                longNumber = Long.parseLong(num.toString(), 2);

            } catch (Exception e) {
                L.d(TAG, "При преобразовании String в long произошла ошибка: " + e);
                StackTraceElement[] stackTraceElements = e.getStackTrace();

                for (int i = 0; i < stackTraceElements.length; i++) {
                    L.d(TAG, i + ": " + stackTraceElements[i].toString());
                }
            }
        }
        if (sign) {
            longNumber = -1 * longNumber;
        }
        L.d(TAG, "После преобразования в long получили: " + longNumber);
        return longNumber;
    }
}