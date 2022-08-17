package ru.fmproject.android.calculator.editors;

import ru.fmproject.android.calculator.MyExceptions;

public interface EditX {
    void restart();

    void del();

    void x_to_m();//перенести в input

    void memoryPlus() throws MyExceptions;//перенести в input

    void readMemory();//перенести в input

    void clearMemory();//перенести в input

    void add(char pressedKey);

    void mult() throws MyExceptions;//перенести в input

    void div() throws MyExceptions;//перенести в input

    void plus() throws MyExceptions;//перенести в input

    void minus() throws MyExceptions;//перенести в input

    void sign();

    void calc() throws MyExceptions;//перенести в input

    void ce();

    void x_to_y() throws MyExceptions;//перенести в input

//    void setNumber(int intNumber);
//
//    void setNumber(long longNumber);

    void setNumber(double doubleNumber);

    double getNumber();

    void toOct();

    void toBin();

    void toDec();

    void toHex();

    StringBuilder copyToClipboard();//перенести в input

    void pasteFromClipboard(String str);//перенести в input
}
