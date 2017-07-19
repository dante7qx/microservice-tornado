package com.tornado.commom.util;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.tornado.commom.util.DateUtils.TimeFormat;

/**
 * 日期工具类测试类
 * 
 * @author dante
 *
 */
public class DateUtilsTest {
	
	@Test
	public void testGetCurrentDate() {
		String date = DateUtils.getCurrentDate();
		Assert.assertNotNull(date);
	}
	
	@Test
	public void testGetCurrentDatetime() {
		String date = DateUtils.getCurrentDatetime();
		Assert.assertNotNull(date);
	}
	
	@Test
	public void testGetCurrentDateWithMilliSecond() {
		String date = DateUtils.getCurrentDateWithMilliSecond();
		Assert.assertNotNull(date);
	}
	
	@Test
	public void testFormatDateTime() {
		Date date = DateUtils.currentDate();
		String dateStr = DateUtils.formatDate(date);
		String dateTimeStr = DateUtils.formatDateTime(date);
		String yearMonth = DateUtils.formatDate(date, TimeFormat.SHORT_DATE_PATTERN_YEAR_MONTH);
		Assert.assertNotNull(dateStr);
		Assert.assertNotNull(dateTimeStr);
		Assert.assertNotNull(yearMonth);
	}
	
	@Test
	public void testParseDate() {
		String str = "2016-03-06";
		System.out.println("" + DateUtils.parseDate(str));
		
		String str1 = "20160306";
		System.out.println("" + DateUtils.parseDate(str1, TimeFormat.SHORT_DATE_PATTERN_NONE));
		
		String str2 = "2016-03-06 22:23:34";
		System.out.println("" + DateUtils.parseDateTime(str2));
		
		String str3 = "2016-03-06 22:23:34.213";
		System.out.println("" + DateUtils.parseDateTime(str3, TimeFormat.LONG_DATE_PATTERN_WITH_MILSEC_LINE));
	}
	
}
