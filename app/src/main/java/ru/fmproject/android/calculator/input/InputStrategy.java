package ru.fmproject.android.calculator.input;

import ru.fmproject.android.calculator.MyExceptions;

public interface InputStrategy {

    void buttonDel();

    void buttonSci();

    void buttonDrg();

    void buttonSin() throws MyExceptions;

    void buttonCos() throws MyExceptions;

    void buttonTan() throws MyExceptions;

    void buttonExp();

    void buttonXpowY() throws MyExceptions;

    void buttonSqr();

    void buttonToRad();

    void buttonLn() throws MyExceptions;

    void buttonLog();

    void buttonX2() throws MyExceptions;

    void buttonA();

    void buttonB();

    void buttonOpenBracket() throws MyExceptions;

    void buttonCloseBracket() throws MyExceptions;

    void buttonXtoM();

    void button07();

    void button08();

    void button09();

    void buttonMem() throws MyExceptions;

    void buttonMR();

    void button04();

    void button05();

    void button06();

    void buttonMult() throws MyExceptions;

    void buttonDiv() throws MyExceptions;

    void button01();

    void button02();

    void button03();

    void buttonPlus() throws MyExceptions;

    void buttonMinus() throws MyExceptions;

    void button00();

    void buttonDot();

    void buttonSign();

    void buttonCalc() throws MyExceptions;

    void buttonCE() throws MyExceptions;
}
