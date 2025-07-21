package com.onextel;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MainTest {
    @Test
    public void testGreet() {
        Main hw = new Main();
        assertEquals("Hello, World!", hw.greet());
    }

    @Test
    public void testAdd() {
        Main calc = new Main();
        assertEquals(5, calc.add(2, 3));
    }
}
