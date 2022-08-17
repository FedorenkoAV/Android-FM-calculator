package ru.fmproject.android.calculator;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ArgXHexTest {

    ArgXHex argX;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetLong1() {
        argX = new ArgXHex();
        argX.setNumber(-10L);
        long actual = argX.getLong(8);
        long expected = -10;
        assertEquals(expected, actual);
    }
    @Test
    public void testGetLong2() {
        argX = new ArgXHex();
        argX.setNumber(10L);
        long actual = argX.getLong(8);
        long expected = 10;
        assertEquals(expected, actual);
    }
    @Test
    public void testGetLong3() {
        argX = new ArgXHex();
        argX.setNumber(Long.MAX_VALUE);
        long actual = argX.getLong(8);
        long expected = Long.MAX_VALUE;
        assertEquals(expected, actual);
    }
    @Test
    public void testGetLong4() {
        argX = new ArgXHex();
        argX.setNumber(Long.MIN_VALUE);
        long actual = argX.getLong(8);
        long expected = Long.MIN_VALUE;
        assertEquals(expected, actual);
    }
    @Test
    public void testGetLong5() {
        argX = new ArgXHex();
        argX.setNumber(0L);
        long actual = argX.getLong(8);
        long expected = 0L;
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws Exception {
    }
}