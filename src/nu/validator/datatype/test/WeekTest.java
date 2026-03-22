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

import nu.validator.datatype.Week;
import org.junit.Test;

public class WeekTest {

    private static final Week DATATYPE = Week.THE_INSTANCE;

    private void assertValid(String value) {
        assertTrue("Expected valid: " + value,
                DATATYPE.isValid(value, null));
    }

    private void assertInvalid(String value) {
        assertFalse("Expected invalid: " + value,
                DATATYPE.isValid(value, null));
    }

    @Test
    public void testValidWeeks() {
        assertValid("2021-W01");
        assertValid("2021-W52");
        assertValid("2000-W20");
    }

    @Test
    public void testValidLongYear() {
        assertValid("12345-W01");
        assertValid("20000-W52");
    }

    @Test
    public void testInvalidWeekOutOfRange() {
        assertInvalid("2021-W00");
        assertInvalid("2021-W54");
        assertInvalid("2021-W99");
    }

    @Test
    public void testInvalid53WeeksForNonSpecialYear() {
        // 2021 does not have 53 weeks (2021 % 400 = 21, not in special years table)
        assertInvalid("2021-W53");
        // 1999 does not have 53 weeks (1999 % 400 = 399, not in special years table)
        assertInvalid("1999-W53");
        // 2019 does not have 53 weeks
        assertInvalid("2019-W53");
    }

    @Test
    public void testValid53WeeksForSpecialYear() {
        // Years with 53 weeks (in the 400-year cycle, e.g., 2004, 2009, 2015, 2020)
        assertValid("2004-W53");
        assertValid("2009-W53");
        assertValid("2015-W53");
        assertValid("2020-W53");
    }

    @Test
    public void testInvalidYearZero() {
        assertInvalid("0000-W01");
    }

    @Test
    public void testInvalidFormat() {
        assertInvalid("2021-W1");    // week not zero-padded
        assertInvalid("21-W01");     // year too short
        assertInvalid("2021W01");    // missing hyphen
        assertInvalid("2021-w01");   // lowercase w
        assertInvalid("2021-01");    // not a week string (month format)
        assertInvalid("");
        assertInvalid("abcd-Wef");
    }

    @Test
    public void testInvalidLeadingOrTrailingWhitespace() {
        assertInvalid(" 2021-W01");
        assertInvalid("2021-W01 ");
    }
}
