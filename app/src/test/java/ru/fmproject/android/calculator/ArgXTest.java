package ru.fmproject.android.calculator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArgXTest{

    ArgX argX;

    //Метод который позволяет инициализировать некоторые данные
    @Before
    public void setUp() {

    }

    @Test
    public void getRoundedMantissaFracPart01() {
        argX = new ArgX(1.66666666666667);
        String actual = argX.getRoundedMantissaFracPart(17, false).toString();
        String expected = "66666666666667";
        assertEquals(expected, actual);
    }
    @Test
    public void getRoundedMantissaFracPart02() {
        argX = new ArgX(0.0);
        String actual = argX.getRoundedMantissaFracPart(7, true).toString();
        String expected = "0000000";
        assertEquals(expected, actual);
    }
    @Test
    public void getRoundedMantissaFracPart03() {
        argX = new ArgX(0.0);
        String actual = argX.getRoundedMantissaFracPart(8, true).toString();
        String expected = "00000000";
        assertEquals(expected, actual);
    }
    @Test
    public void getRoundedMantissaFracPart04() {
        argX = new ArgX(0.0);
        String actual = argX.getRoundedMantissaFracPart(9, true).toString();
        String expected = "000000000";
        assertEquals(expected, actual);
    }
}