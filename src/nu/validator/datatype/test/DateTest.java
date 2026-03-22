/*
 * Copyright (c) 2024 Mozilla Foundation
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package nu.validator.datatype.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nu.validator.datatype.Date;
import org.junit.Test;

public class DateTest {

    private static final Date DATATYPE = Date.THE_INSTANCE;

    private void assertValid(String value) {
        assertTrue("Expected valid: " + value,
                DATATYPE.isValid(value, null));
    }

    private void assertInvalid(String value) {
        assertFalse("Expected invalid: " + value,
                DATATYPE.isValid(value, null));
    }

    @Test
    public void testValidDates() {
        assertValid("2021-01-01");
        assertValid("2021-12-31");
        assertValid("2000-02-29"); // leap year
        assertValid("2020-02-29"); // leap year
        assertValid("1999-06-15");
        assertValid("0001-01-01");
    }

    @Test
    public void testValidLongYear() {
        assertValid("12345-01-01");
    }

    @Test
    public void testInvalidMonthOutOfRange() {
        assertInvalid("2021-00-01");
        assertInvalid("2021-13-01");
    }

    @Test
    public void testInvalidDayOutOfRange() {
        assertInvalid("2021-01-00");
        assertInvalid("2021-01-32");
        assertInvalid("2021-04-31"); // April has 30 days
        assertInvalid("2021-02-29"); // 2021 is not a leap year
        assertInvalid("1900-02-29"); // 1900 is not a leap year (divisible by 100 but not 400)
    }

    @Test
    public void testValidLeapYearFeb29() {
        assertValid("2000-02-29"); // divisible by 400
        assertValid("1600-02-29"); // divisible by 400
        assertValid("2004-02-29"); // divisible by 4, not by 100
    }

    @Test
    public void testInvalidNonLeapYearFeb29() {
        assertInvalid("2001-02-29");
        assertInvalid("2100-02-29"); // divisible by 100 but not 400
    }

    @Test
    public void testInvalidYearZero() {
        assertInvalid("0000-01-01");
    }

    @Test
    public void testInvalidFormat() {
        assertInvalid("2021-1-01");     // month not zero-padded
        assertInvalid("2021-01-1");     // day not zero-padded
        assertInvalid("21-01-01");      // year too short
        assertInvalid("2021/01/01");    // wrong separator
        assertInvalid("20210101");      // no separators
        assertInvalid("2021-01");       // missing day (month format)
        assertInvalid("");
        assertInvalid("abcd-ef-gh");
    }

    @Test
    public void testInvalidLeadingOrTrailingWhitespace() {
        assertInvalid(" 2021-01-01");
        assertInvalid("2021-01-01 ");
    }

    @Test
    public void testMonthBoundaryDays() {
        assertValid("2021-01-31"); // January has 31 days
        assertValid("2021-03-31"); // March has 31 days
        assertValid("2021-04-30"); // April has 30 days
        assertValid("2021-02-28"); // February non-leap year
        assertInvalid("2021-04-31"); // April has only 30 days
        assertInvalid("2021-06-31"); // June has only 30 days
        assertInvalid("2021-09-31"); // September has only 30 days
        assertInvalid("2021-11-31"); // November has only 30 days
    }
}
