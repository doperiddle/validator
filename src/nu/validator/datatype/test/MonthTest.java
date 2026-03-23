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

import nu.validator.datatype.Month;
import org.junit.Test;

public class MonthTest {

    private static final Month DATATYPE = Month.THE_INSTANCE;

    private void assertValid(String value) {
        assertTrue("Expected valid: " + value,
                DATATYPE.isValid(value, null));
    }

    private void assertInvalid(String value) {
        assertFalse("Expected invalid: " + value,
                DATATYPE.isValid(value, null));
    }

    @Test
    public void testValidMonths() {
        assertValid("2021-01");
        assertValid("2021-12");
        assertValid("2000-06");
        assertValid("1999-11");
        assertValid("0001-01");
    }

    @Test
    public void testValidLongYear() {
        assertValid("12345-01");
        assertValid("99999-12");
    }

    @Test
    public void testInvalidMonthOutOfRange() {
        assertInvalid("2021-00");
        assertInvalid("2021-13");
        assertInvalid("2021-99");
    }

    @Test
    public void testInvalidYearZero() {
        assertInvalid("0000-01");
    }

    @Test
    public void testInvalidFormat() {
        assertInvalid("2021-1");    // month not zero-padded
        assertInvalid("21-01");     // year too short
        assertInvalid("2021/01");   // wrong separator
        assertInvalid("2021_01");
        assertInvalid("202101");    // no separator
        assertInvalid("2021-01-01"); // too long (this is a date)
        assertInvalid("");
        assertInvalid("abcd-ef");
    }

    @Test
    public void testInvalidLeadingOrTrailingWhitespace() {
        assertInvalid(" 2021-01");
        assertInvalid("2021-01 ");
        assertInvalid(" 2021-01 ");
    }
}
