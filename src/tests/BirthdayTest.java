package tests;

import java.util.Calendar;
import java.util.Date;

import android.test.AndroidTestCase;
import android.util.Log;

import com.norrielm.birthday.Birthday;
import com.norrielm.birthday.InvalidAllowanceException;

public class BirthdayTest extends AndroidTestCase {

	private static final boolean DEBUG = false;
	private static final String TAG = "Test";

	protected Birthday birthday;
	protected int birthDay = 1;
	protected int birthMonth = 0;// JAN == 0
	protected int birthYear = 1990;
	protected int thisYear = 2014;
	protected int lastYear = thisYear - 1;
	protected int nextYear = thisYear + 1;

	protected void setUp() {
		birthday = new Birthday(birthDay, birthMonth, birthYear);
	}

	public void testGetBirthday() {	
		assertTrue(birthday.getDay() == birthDay);
		assertTrue(birthday.getMonth() == birthMonth);
		assertTrue(birthday.getYear() == birthYear);
	}

	public void getNextBirthday(Calendar today, int expectedYear) {
		// Get the next birthday
		long dateInMillis = birthday.getNextBirthday(today);
		Calendar nextBirthday = Calendar.getInstance();
		nextBirthday.setTime(new Date(dateInMillis));
		// Check that the day hasn't changed
		assertTrue(nextBirthday.get(Calendar.DATE) == birthDay);
		assertTrue(nextBirthday.get(Calendar.MONTH) == birthMonth); // JAN = 0
		// Check that the year is correct
		assertTrue(nextBirthday.get(Calendar.YEAR) == expectedYear);		
	}

	public void testNextBirthday() {
		// Today is the birthday.
		Calendar today = Calendar.getInstance();
		today.set(Calendar.DATE, birthDay);
		today.set(Calendar.MONTH, birthMonth); 
		today.set(Calendar.YEAR, thisYear);
		// The next birthday should be the year after.
		getNextBirthday(today, nextYear);

		// Today is the day after the birthday.
		today = Calendar.getInstance();
		today.set(Calendar.DATE, birthDay + 1);
		today.set(Calendar.MONTH, birthMonth);
		today.set(Calendar.YEAR, thisYear);
		// The next birthday should be the year after.
		getNextBirthday(today, nextYear);

		// Today is the day before the birthday.
		today = Calendar.getInstance();
		today.set(Calendar.DATE, birthDay - 1);
		today.set(Calendar.MONTH, birthMonth);
		today.set(Calendar.YEAR, thisYear);
		// The next birthday should be tomorrow.
		getNextBirthday(today, thisYear);
	}

	public void testBirthdayToday() {
		// Today is the birthday.
		Calendar today = Calendar.getInstance();
		today.set(Calendar.DATE, birthDay);
		today.set(Calendar.MONTH, birthMonth); 
		today.set(Calendar.YEAR, thisYear);
		assertTrue(birthday.isBirthdayToday(today));

		// Today is the day after the birthday.
		today = Calendar.getInstance();
		today.set(Calendar.DATE, birthDay + 1);
		today.set(Calendar.MONTH, birthMonth);
		today.set(Calendar.YEAR, thisYear);
		assertFalse(birthday.isBirthdayToday(today));

		// Today is the day before the birthday.
		today = Calendar.getInstance();
		today.set(Calendar.DATE, birthDay - 1);
		today.set(Calendar.MONTH, birthMonth);
		today.set(Calendar.YEAR, thisYear);
		assertFalse(birthday.isBirthdayToday(today));
	}

	public void testBirthdayAge() {
		// Today is the birthday.
		int expectedAge = thisYear - birthYear; 
		Calendar today = Calendar.getInstance();
		today.set(Calendar.DATE, birthDay);
		today.set(Calendar.MONTH, birthMonth); 
		today.set(Calendar.YEAR, thisYear);
		assertTrue(birthday.getAge(today) == expectedAge);

		// Today is the day after the birthday.
		expectedAge = thisYear - birthYear;
		today = Calendar.getInstance();
		today.set(Calendar.DATE, birthDay + 1);
		today.set(Calendar.MONTH, birthMonth);
		today.set(Calendar.YEAR, thisYear);
		int age = birthday.getAge(today);
		assertTrue(age == expectedAge);
		assertFalse(age == (expectedAge - 1));
		assertFalse(age == (expectedAge + 1));

		// Today is the day before the birthday.
		expectedAge = lastYear - birthYear;
		today = Calendar.getInstance();
		today.set(Calendar.DATE, birthDay - 1);
		today.set(Calendar.MONTH, birthMonth);
		today.set(Calendar.YEAR, thisYear);
		assertTrue(birthday.getAge(today) == expectedAge);
	}

	/**
	 * Test the allowed number of days to open the envelope with valid and invalid input. 
	 */
	public void testAllowance() {
		try {
			openEnvelopeValidTestSet(0);
			openEnvelopeValidTestSet(1);
			openEnvelopeValidTestSet(2);
		} catch (InvalidAllowanceException e) {
			fail("Failed on valid allowance");
		}
		openEnvelopeInvalidTestSet(-1);
		openEnvelopeInvalidTestSet(366);
		openEnvelopeInvalidTestSet(1000);
	}

	public void openEnvelopeTest(int day, int addDays, int month, int year, 
			int extraDaysAllowed, boolean expected) throws InvalidAllowanceException {
		Calendar today = Calendar.getInstance();
		today.set(Calendar.DATE, birthDay);
		today.set(Calendar.MONTH, birthMonth);
		today.set(Calendar.YEAR, thisYear);
		today.add(Calendar.DATE, addDays);
		boolean result = birthday.canOpenEnvelope(today, extraDaysAllowed);
		if (DEBUG) Log.d(TAG, "Expect true, returned " + result
				+ " Today: " + today.get(Calendar.DATE) + "/" + (today.get(Calendar.MONTH)+1) + "/" + today.get(Calendar.YEAR)
				+ " Birthday: " + birthday.getDay() + "/" + (birthday.getMonth()+1) + "/" + birthday.getYear());
		assertTrue(result == expected);
	}

	public void openEnvelopeValidTestSet(int allowance) throws InvalidAllowanceException {
		if (DEBUG) Log.d(TAG, "Testing allowance " + allowance);
		// Today is the birthday
		openEnvelopeTest(birthDay, 0, birthMonth, thisYear, allowance, true);
		// Today is the day before the birthday
		openEnvelopeTest(birthDay, -1, birthMonth, thisYear, allowance, false);
		// Today is the day after the allowance
		openEnvelopeTest(birthDay, allowance + 1, birthMonth, thisYear, allowance, false);
		// Today is the last day of the allowance
		openEnvelopeTest(birthDay, allowance, birthMonth, thisYear, allowance, true);
		if (allowance > 0) {
			// Today is the day before the allowance ends
			openEnvelopeTest(birthDay, allowance - 1, birthMonth, thisYear, allowance, true);
		}
	}

	public void openEnvelopeInvalidTestSet(int allowance) {
		if (DEBUG) Log.d(TAG, "Testing allowance " + allowance);
		try {
			// Today is the birthday
			openEnvelopeTest(birthDay, 0, birthMonth, thisYear, allowance, true);
			fail("Passed on invalid allowance");
		} catch (InvalidAllowanceException e) {
			// Pass
		}
		try {
			// Today is the day before the birthday
			openEnvelopeTest(birthDay, -1, birthMonth, thisYear, allowance, false);
			fail("Passed on invalid allowance");
		} catch (InvalidAllowanceException e) {
			// Pass
		}
		try {
			// Today is the day after the allowance
			openEnvelopeTest(birthDay, allowance + 1, birthMonth, thisYear, allowance, false);
			fail("Passed on invalid allowance");
		} catch (InvalidAllowanceException e) {
			// Pass
		}
		try {
			// Today is the last day of the allowance
			openEnvelopeTest(birthDay, allowance, birthMonth, thisYear, allowance, true);
			fail("Passed on invalid allowance");
		} catch (InvalidAllowanceException e) {
			// Pass
		}
		try {
			if (allowance > 0) {
				// Today is the day before the allowance
				openEnvelopeTest(birthDay, allowance - 1, birthMonth, thisYear, allowance, true);
				fail("Passed on invalid allowance");
			}
		} catch (InvalidAllowanceException e) {
			// Pass
		}
	}
}
