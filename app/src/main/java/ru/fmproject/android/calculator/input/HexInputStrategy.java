package ru.fmproject.android.calculator.input;

import ru.fmproject.android.calculator.editors.EditXHex;
import ru.fmproject.android.calculator.MyExceptions;

public class HexInputStrategy extends AbstractInputStrategy {

    private final EditXHex editXHex;

    public HexInputStrategy(EditXHex editXHex) {
        super(editXHex);
        this.editXHex = editXHex;
    }

    @Override
    public void buttonExp() {
        // A
        editXHex.add('A');
    }

    @Override
    public void buttonXpowY() throws MyExceptions {
        // B
        editXHex.add('b');
    }

    @Override
    public void buttonSqr() {
        // C
        editXHex.add('C');
    }

    @Override
    public void buttonToRad() {
        // D
        editXHex.add('d');
    }

    @Override
    public void buttonLn() {
        // E
        editXHex.add('E');
    }

    @Override
    public void buttonLog() {
        // F
        editXHex.add('F');
    }

    @Override
    public void button07() {
        // 7
        editXHex.add('7');
    }

    @Override
    public void button08() {
        // 8
        editXHex.add('8');
    }

    @Override
    public void button09() {
        // 9
        editXHex.add('9');
    }

    @Override
    public void button04() {
        // 4
        editXHex.add('4');
    }

    @Override
    public void button05() {
        // 5
        editXHex.add('5');
    }

    @Override
    public void button06() {
        // 6
        editXHex.add('6');
    }

    @Override
    public void button02() {
        // 2
        editXHex.add('2');
    }

    @Override
    public void button03() {
        // 3
        editXHex.add('3');
    }
}
