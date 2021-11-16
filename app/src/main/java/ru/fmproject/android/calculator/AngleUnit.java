package ru.fmproject.android.calculator;

public enum AngleUnit {
    DEG("\u00B0"),//градусы
    RAD("rad"),//радианы
    GRAD("g");//грады

    private String name;

    private AngleUnit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
