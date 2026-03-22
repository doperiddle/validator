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

import nu.validator.datatype.Time;
import org.junit.Test;

public class TimeTest {

    private static final Time DATATYPE = Time.THE_INSTANCE;

    private void assertValid(String value) {
        assertTrue("Expected valid: " + value,
                DATATYPE.isValid(value, null));
    }

    private void assertInvalid(String value) {
        assertFalse("Expected invalid: " + value,
                DATATYPE.isValid(value, null));
    }

    @Test
    public void testValidHoursAndMinutes() {
        assertValid("00:00");
        assertValid("23:59");
        assertValid("12:30");
        assertValid("01:01");
    }

    @Test
    public void testValidWithSeconds() {
        assertValid("12:30:00");
        assertValid("23:59:59");
        assertValid("00:00:00");
    }

    @Test
    public void testValidWithMilliseconds() {
        assertValid("12:30:00.1");
        assertValid("12:30:00.12");
        assertValid("12:30:00.123");
    }

    @Test
    public void testInvalidHoursOutOfRange() {
        assertInvalid("24:00");
        assertInvalid("25:00");
        assertInvalid("99:00");
    }

    @Test
    public void testInvalidMinutesOutOfRange() {
        assertInvalid("12:60");
        assertInvalid("12:99");
    }

    @Test
    public void testInvalidSecondsOutOfRange() {
        assertInvalid("12:30:60");
        assertInvalid("12:30:99");
    }

    @Test
    public void testInvalidFormat() {
        assertInvalid("1:30");      // hour not zero-padded
        assertInvalid("12:3");      // minute not zero-padded
        assertInvalid("12-30");     // wrong separator
        assertInvalid("1230");      // no separator
        assertInvalid("12:30:00.1234"); // too many millisecond digits
        assertInvalid("");
        assertInvalid("ab:cd");
    }

    @Test
    public void testInvalidLeadingOrTrailingWhitespace() {
        assertInvalid(" 12:30");
        assertInvalid("12:30 ");
    }
}
