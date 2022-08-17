package ru.fmproject.android.calculator;

public abstract class ArgXParent {
    private static final String TAG = "ArgXClass";

    private StringBuilder number;// здесь будет храниться число
    private boolean sign;// Знак числа
    private boolean editable;// Число можно редактировать
    private boolean virginity;// Число еще не редактировали

    public ArgXParent() {
        reset();
        //L.d(TAG, "Создали новый пустой ArgXParent");
    }

    public ArgXParent(double doubleNumber) {
        setNumber(doubleNumber);
        //L.d(TAG, "Создали новый НЕпустой ArgXParent");
    }

    public void reset () {
        number = new StringBuilder();
        sign = false;
        editable = true;
        virginity = true;
    }

    public void setNumber(StringBuilder sbNumber) {
        number = new StringBuilder(sbNumber);
        editable = false;
        virginity = false;
    }

    abstract void setNumber(int intNumber);

    abstract void setNumber(long longNumber);

    public void setNumber(double doubleNumber) {
        long longNumber = (long) doubleNumber;
        setNumber(longNumber);
    }

    public StringBuilder getNumber() {
        return number;
    }

    public double getDouble(int byteLength) {
        double doubleNumber;
        doubleNumber = (double) getLong(byteLength);
        //L.d(TAG, "После преобразования в double получили: " + doubleNumber);
        return doubleNumber;
    }

    abstract long getLong(int byteLength);

    public void setFromStringBuilder(StringBuilder num) {
        number = num;
    }

    public boolean isSign() {
        return sign;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isVirginity() {
        return virginity;
    }

    public void setVirginity(boolean virginity) {
        this.virginity = virginity;
    }
}