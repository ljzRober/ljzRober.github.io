package com.ljz.plat.android;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UtilsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void primes() {
        Utils.primes().limit(10).forEach(System.out::println);
    }

    @Test
    public void testJava() {
        System.out.println(-2 ^ -4);
    }
}