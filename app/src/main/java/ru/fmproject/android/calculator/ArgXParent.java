package ru.fmproject.android.calculator;

public abstract class ArgXParent {
    private static final String TAG = "ArgXClass";

    private StringBuilder number;// здесь будет храниться число
    private boolean sign;// Знак числа
    private boolean editable;// Число можно редактировать
    private boolean virginity;// Число еще не редактировали

    ArgXParent() {
        reset();
//        number = new StringBuilder();
//        sign = false;
//        editable = true;
//        virginity = true;
        L.d(TAG, "Создали новый пустой ArgXParent");
    }

//    ArgXParent(long longNumber) {
//        setNumber(longNumber);
//        L.d(TAG, "Создали новый НЕпустой ArgXParent");
//    }
//
//    ArgXParent(int intNumber) {
//        setNumber(intNumber);
//        L.d(TAG, "Создали новый НЕпустой ArgXParent");
//    }

    ArgXParent(double doubleNumber) {
        setNumber(doubleNumber);
        L.d(TAG, "Создали новый НЕпустой ArgXParent");
    }

    void reset () {
        number = new StringBuilder();
        sign = false;
        editable = true;
        virginity = true;
    }

    void setNumber(StringBuilder sbNumber) {
        number = new StringBuilder(sbNumber);
        editable = false;
        virginity = false;
    }

    abstract void setNumber(int intNumber);

    abstract void setNumber(long longNumber);

    void setNumber(double doubleNumber) {
        long longNumber = (long) doubleNumber;
        setNumber(longNumber);
    }

    StringBuilder getNumber() {
        return number;
    }

    double getDouble(int byteLength) {
        double doubleNumber;
        doubleNumber = (double) getLong(byteLength);
        L.d(TAG, "После преобразования в double получили: " + doubleNumber);
        return doubleNumber;
    }

    abstract long getLong(int byteLength);

    void setFromStringBuilder(StringBuilder num) {
        number = num;
    }

    public boolean isSign() {
        return sign;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
        L.d(TAG, "sign: " + this.sign);
    }

    boolean isEditable() {
        return editable;
    }

    void setEditable(boolean editable) {
        this.editable = editable;
    }

    boolean isVirgin() {
        return virginity;
    }

    void setNotVirgin() {
        virginity = false;
    }
}