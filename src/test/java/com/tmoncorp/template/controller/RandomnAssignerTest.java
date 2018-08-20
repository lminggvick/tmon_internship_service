package com.tmoncorp.template.controller;

import com.tmoncorp.operator.datagenerator.RandomAssigner;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RandomnAssignerTest {
    static RandomAssigner randomAssigner;

    @BeforeClass
    public static void setUpBeforeClass() {
        randomAssigner = new RandomAssigner();
    }

    @Test
    public void testRandomnIdOrNo() {
        assertEquals(9, randomAssigner.assignRandomnIdOrNo(1));
        assertEquals(99, randomAssigner.assignRandomnIdOrNo(2));
        assertEquals(999, randomAssigner.assignRandomnIdOrNo(3));
        assertEquals(999999999, randomAssigner.assignRandomnIdOrNo(9));
        assertEquals(-1, randomAssigner.assignRandomnIdOrNo(10));
        assertEquals(-1, randomAssigner.assignRandomnIdOrNo(0));
    }

    @Test
    public void testRandomIndex() {
        System.out.println(randomAssigner.assignRandomIndexOrQuantity(1));
        System.out.println(randomAssigner.assignRandomIndexOrQuantity(2));
        System.out.println(randomAssigner.assignRandomIndexOrQuantity(3));
    }

    @Test
    public void testRandomPrice() {
        System.out.println(randomAssigner.assignRandomPrice(10, 100));
        System.out.println(randomAssigner.assignRandomPrice(10, 20));
        System.out.println(randomAssigner.assignRandomPrice(10, 40));
        System.out.println(randomAssigner.assignRandomPrice(10000, 100000));
    }
}
