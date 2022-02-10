package ru.fmproject.android.calculator.input;

import ru.fmproject.android.calculator.editors.EditXDec;
import ru.fmproject.android.calculator.MyExceptions;

public class DecInputStrategy extends AbstractInputStrategy {

    private final EditXDec editXDec;

    public DecInputStrategy(EditXDec editXDec) {
        super(editXDec);
        this.editXDec = editXDec;
    }

    @Override
    public void buttonSci() {
        editXDec.switchSciMode();
    }

    @Override
    public void buttonSin() {
        editXDec.sin();
    }

    @Override
    public void buttonCos() {
        editXDec.cos();
    }

    @Override
    public void buttonTan() throws MyExceptions {
        editXDec.tan();
    }

    @Override
    public void buttonExp() {
        editXDec.exp();
    }

    @Override
    public void buttonXpowY() throws MyExceptions {
        editXDec.power();
    }

    @Override
    public void buttonSqr() {
        editXDec.sqrt();
    }

    @Override
    public void buttonToRad() {
        editXDec.toDeg();
    }

    @Override
    public void buttonLn() {
        editXDec.ln();
    }

    @Override
    public void buttonLog() {
        editXDec.log();
    }

    @Override
    public void buttonX2() {
        editXDec.x2();
    }

    @Override
    public void buttonA() {
        editXDec.toA();
    }

    @Override
    public void buttonB() {
        editXDec.toB();
    }

    @Override
    public void buttonOpenBracket() throws MyExceptions {
        editXDec.openBracket();
    }

    @Override
    public void buttonCloseBracket() throws MyExceptions {
        editXDec.closeBracket();
    }

    @Override
    public void button07() {
        editXDec.add('7');
    }

    @Override
    public void button08() {
        editXDec.add('8');
    }

    @Override
    public void button09() {
        editXDec.add('9');
    }

    @Override
    public void button04() {
        editXDec.add('4');
    }

    @Override
    public void button05() {
        editXDec.add('5');
    }

    @Override
    public void button06() {
        editXDec.add('6');
    }

    @Override
    public void button02() {
        editXDec.add('2');
    }

    @Override
    public void button03() {
        editXDec.add('3');
    }

    @Override
    public void buttonDot() {
        editXDec.dot();
    }
}
