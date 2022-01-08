package ru.fmproject.android.calculator.input;

import ru.fmproject.android.calculator.editors.EditXOct;

public class OctInputStrategy extends AbstractInputStrategy {

    private final EditXOct editXOct;

    public OctInputStrategy(EditXOct editXOct) {
        super(editXOct);
        this.editXOct = editXOct;
    }

    @Override
    public void button07() {
        // 7
        editXOct.add('7');
    }

    @Override
    public void button04() {
        // 4
        editXOct.add('4');
    }

    @Override
    public void button05() {
        // 5
        editXOct.add('5');
    }

    @Override
    public void button06() {
        // 6
        editXOct.add('6');
    }

    @Override
    public void button02() {
        // 2
        editXOct.add('2');
    }

    @Override
    public void button03() {
        // 3
        editXOct.add('3');
    }
}
