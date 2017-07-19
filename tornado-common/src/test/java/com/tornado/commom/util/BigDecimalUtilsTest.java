package com.tornado.commom.util;

import org.junit.Test;

/**
 * BigDecimalUtils 测试类
 * 
 * @author dante
 *
 */
public class BigDecimalUtilsTest {
	
	@Test
	public void testAdd() {
		Double d1 = 10.872;
		Double d2 = 38.987;
		BigDecimalUtils.add(d1, d2);
	}
	
	@Test
	public void testSub() {
		Double d1 = 10.872;
		Double d2 = 38.987;
		BigDecimalUtils.sub(d2, d1);
	}
	
	@Test
	public void testMul() {
		Double d1 = 10.872;
		Double d2 = 38.987;
		BigDecimalUtils.mul(d2, d1);
	}
	
	@Test
	public void testDiv() throws IllegalAccessException {
		Double d1 = 3.0;
		Double d2 = 7.0;
		BigDecimalUtils.div(d2, d1, 2);
	}
	
	@Test
	public void testRound() {
		Double d1 = 10.8725;
		BigDecimalUtils.round(d1, 3);
	}
	
	@Test
	public void testAddRound() {
		Double d1 = 10.872;
		Double d2 = 38.987;
		BigDecimalUtils.add(d1, d2, 3);
	}
	
	@Test
	public void testSubRound() {
		Double d1 = 10.872;
		Double d2 = 38.987;
		BigDecimalUtils.sub(d2, d1, 3);
	}
	
	@Test
	public void testMulRound() {
		Double d1 = 10.872;
		Double d2 = 38.987;
		BigDecimalUtils.mul(d2, d1, 3);
	}
	
}
