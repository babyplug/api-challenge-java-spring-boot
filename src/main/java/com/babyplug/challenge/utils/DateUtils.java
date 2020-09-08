package com.babyplug.challenge.utils;

import com.babyplug.challenge.core.exception.InvalidFormatException;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

	private static final int YEAR_THAI = 543;
	public static final SimpleDateFormat TH_MMM_YYYY = new SimpleDateFormat(
			"MMM yyyy", new Locale("th", "TH"));
	public static SimpleDateFormat THAI_DATE_FORMAT_DD_MM_YYYY_HH_MM = new SimpleDateFormat(
			"วันที่พิมพ์ dd/MM/yyyy เวลา HH:mm น.", new Locale("th", "TH"));
	public static SimpleDateFormat THAI_DATE_FORMAT_DD_MMMM_YYYY = new SimpleDateFormat("dd MMMM yyyy",
			new Locale("th", "TH"));
	public static SimpleDateFormat THAI_DATE_FORMAT_DD_MM_YYYY = new SimpleDateFormat("dd/MM/yyyy",
			new Locale("th", "TH"));
	public static SimpleDateFormat THAI_DATE_FORMAT_MMMM_YYYY = new SimpleDateFormat("เดือน MMMM พ.ศ. yyyy",
			new Locale("th", "TH"));
	public static SimpleDateFormat THAI_DATE_FORMAT_DD_MM_YY_NO_SLASH = new SimpleDateFormat(
			"dd MMM yy ", new Locale("th", "TH"));
	private static final String[] dayNameTH = { "อาทิตย์", "จันทร์", "อังคาร", "พุธ", "พฤหัสบดี", "ศุกร์", "เสาร์" };
	private static final String[] months = { "ม.ค", "ก.พ", "มี.ค", "เม.ย", "พ.ค", "มิ.ย", "ก.ค", "ส.ค", "ก.ย", "ต.ค", "พ.ย",
			"ธ.ค" };
	private static final String[] monthsFullTH = { "มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน", "พฤษภาคม", "มิถุนายน",
			"กรกฎาคม", "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม" };
	public static SimpleDateFormat DD_MM_YYYY = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
	public static SimpleDateFormat DD_MM_YYYY_HH_MM = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US);
	public static SimpleDateFormat DD_MM_YYYY_HH_MM_SS = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
	public static SimpleDateFormat DD_MMM_YYYY = new SimpleDateFormat("dd MMM yyyy", Locale.US);
	public static SimpleDateFormat MM_YYYY = new SimpleDateFormat("MM/yyyy", Locale.US);
	public static SimpleDateFormat TH_DD_MMM_YYYY = new SimpleDateFormat("dd MMM YYYY", new Locale("th", "TH"));

	public static Date addDayToDate(Date date, int day) {
		LocalDateTime localEndDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		localEndDate = localEndDate.plusDays(day);
		Date endDate = java.sql.Timestamp.valueOf(localEndDate);
		return endDate;
	}

	public static LocalDateTime convertDateToLocalDateTime(Date date) {
		LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

		return localDateTime;
	}

	public static Calendar dateToCalendar(Date date) {
		Calendar calendar = Calendar.getInstance(Locale.US);
		calendar.setTime(date);

		return calendar;
	}

//	public static String exportDateTH(Date date) {
//		String strExportDateTh = THAI_DATE_FORMAT_DD_MM_YYYY_HH_MM.format(new Date());
//		return strExportDateTh;
//	}

	public static String dayTH(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return dayNameTH[calendar.get(Calendar.DAY_OF_WEEK) - 1];
	}

	public static String monthTH(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return months[calendar.get(Calendar.MONTH)];
	}

	public static String monthFullTH(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return monthsFullTH[calendar.get(Calendar.MONTH)];
	}

	public static String convertStringToStringMonthFullTH(String month) {
		int monthIndex = Integer.parseInt(month) - 1;

		return monthsFullTH[monthIndex];
	}

	public static String convertLongToStringMonthFullTH(Long month) {
		if (month != null) {
			return monthsFullTH[month.intValue() - 1];
		}
		return "";
	}

	public static String yearTH(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return String.valueOf(calendar.get(Calendar.YEAR) + 543);
	}

	public static Date addDayToDateResetTime(Date date, int day) {
		LocalDateTime localEndDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		localEndDate = localEndDate.plusDays(day);
		localEndDate = localEndDate.withHour(0);
		localEndDate = localEndDate.withMinute(0);
		localEndDate = localEndDate.withSecond(0);
		Date endDate = java.sql.Timestamp.valueOf(localEndDate);
		return endDate;
	}

	public static Date resetTime(Date date) {
		LocalDateTime localEndDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		localEndDate = localEndDate.withHour(0);
		localEndDate = localEndDate.withMinute(0);
		localEndDate = localEndDate.withSecond(0);
		Date endDate = java.sql.Timestamp.valueOf(localEndDate);
		return endDate;
	}

	public static boolean validateFormat(SimpleDateFormat format, String value) {
		boolean result = false;

		if (StringUtils.isNotBlank(value)) {
			try {
				format.parse(value);
				result = true;
			} catch (ParseException e) {

			}
		}

		return result;
	}

	public static String convertDateToString(SimpleDateFormat format, Date date) {
		String result = null;

		result = format.format(date);
		return result;
	}

	public static Calendar connvertDateToCalendar(Date date) {
		Calendar calendar = Calendar.getInstance(Locale.US);
		calendar.setTime(date);
		return calendar;
	}

	public static String convertDateToString(Date date, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.US);
		return dateFormat.format(date);
	}
	
	public static String convertDateToStringTH(Date date, String pattern) {
		Calendar calendar = connvertDateToCalendar(date);
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, new Locale("th", "TH"));
		return dateFormat.format(calendar.getTime());
	}

	public static boolean compareDateAndString(Date date, String compareString, SimpleDateFormat format) {
		String dateString = format.format(date);
		if (StringUtils.isNotBlank(dateString) && StringUtils.isNotBlank(compareString)
				&& dateString.equals(compareString)) {
			return true;
		}
		return false;
	}

	public static Date convertStringToDate(String value, SimpleDateFormat sdf) throws InvalidFormatException {
		if (StringUtils.isEmpty(value)) {
			return null;
		}

		Date date = null;
		try {
			date = sdf.parse(value);
		} catch (ParseException ex) {
			throw new InvalidFormatException("EDATEFORMAT", "Invalid date format!!!");
		}
		return date;
	}

	public static String createDateString1JanFromYear(String year) {
		StringBuilder result = new StringBuilder("01/01/");

		Calendar calendar = Calendar.getInstance(Locale.US);
		if (year != null) {
			result.append(year);
		}else {
			result.append(calendar.get(Calendar.YEAR));
		}

		return result.toString();
	}

	public static String createDateString31DecFromYear(String year) {
		StringBuilder result = new StringBuilder("31/12/");

		Calendar calendar = Calendar.getInstance(Locale.US);
		if (year != null) {
			result.append(year);
		}else {
			result.append(calendar.get(Calendar.YEAR));
		}

		return result.toString();
	}

	public static String createFirstDayOfMonthFromCurrent() {
		StringBuilder result = new StringBuilder("01/");

		Calendar calendar = Calendar.getInstance(Locale.US);

		result.append(calendar.get(Calendar.MONTH) + 1).append("/").append(calendar.get(Calendar.YEAR));

		return result.toString();
	}

	public static String createLastDayOfMonthFromCurrent() {
		StringBuilder result = new StringBuilder();

		Calendar calendar = Calendar.getInstance(Locale.US);

		result.append(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)).append("/").append(calendar.get(Calendar.MONTH) + 1).append("/").append(calendar.get(Calendar.YEAR));

		return result.toString();
	}

	public static String createFirstDayOfMonthFromCurrentByYYYYMMDD() {
		StringBuilder result = new StringBuilder();

		Calendar calendar = Calendar.getInstance(Locale.US);

		result.append(calendar.get(Calendar.YEAR)).append("/").append(calendar.get(Calendar.MONTH) + 1).append("/").append("01");

		return result.toString();
	}

	public static String createLastDayOfMonthFromCurrentByYYYYMMDD() {
		StringBuilder result = new StringBuilder();

		Calendar calendar = Calendar.getInstance(Locale.US);

		result.append(calendar.get(Calendar.YEAR)).append("/").append(calendar.get(Calendar.MONTH) + 1).append("/")
				.append(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		return result.toString();
	}

}
