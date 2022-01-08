package ru.fmproject.android.calculator.editors;

import ru.fmproject.android.calculator.MyExceptions;

public interface EditX {
    void restart();

    void del();

    void x_to_m();

    void memoryPlus() throws MyExceptions;

    void readMemory();

    void clearMemory();

    void add(char pressedKey);

    void mult() throws MyExceptions;

    void div() throws MyExceptions;

    void plus() throws MyExceptions;

    void minus() throws MyExceptions;

    void sign();

    void calc() throws MyExceptions;

    void ce();

    void x_to_y() throws MyExceptions;

//    void setNumber(int intNumber);
//
//    void setNumber(long longNumber);

    void setNumber(double doubleNumber);

    double getNumber();

    void toOct();

    void toBin();

    void toDec();

    void toHex();

    StringBuilder copyToClipboard();

    void pasteFromClipboard(String str);
}
