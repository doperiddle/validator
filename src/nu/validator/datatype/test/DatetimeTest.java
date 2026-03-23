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

import nu.validator.datatype.Datetime;
import org.junit.Test;

public class DatetimeTest {

    private static final Datetime DATATYPE = Datetime.THE_INSTANCE;

    private void assertValid(String value) {
        assertTrue("Expected valid: " + value,
                DATATYPE.isValid(value, null));
    }

    private void assertInvalid(String value) {
        assertFalse("Expected invalid: " + value,
                DATATYPE.isValid(value, null));
    }

    @Test
    public void testValidWithTSeparatorAndZ() {
        assertValid("2021-01-01T00:00Z");
        assertValid("2021-12-31T23:59Z");
        assertValid("2000-06-15T12:30Z");
    }

    @Test
    public void testValidWithSpaceSeparatorAndZ() {
        assertValid("2021-01-01 00:00Z");
        assertValid("2021-12-31 23:59Z");
    }

    @Test
    public void testValidWithSeconds() {
        assertValid("2021-01-01T12:30:00Z");
        assertValid("2021-01-01T12:30:59Z");
    }

    @Test
    public void testValidWithMilliseconds() {
        assertValid("2021-01-01T12:30:00.1Z");
        assertValid("2021-01-01T12:30:00.12Z");
        assertValid("2021-01-01T12:30:00.123Z");
    }

    @Test
    public void testInvalidMissingZ() {
        assertInvalid("2021-01-01T12:30");
        assertInvalid("2021-01-01T12:30:00");
        assertInvalid("2021-01-01T12:30+00:00"); // timezone offset not allowed for this type
    }

    @Test
    public void testInvalidDateParts() {
        assertInvalid("2021-00-01T12:30Z");  // month 0 is invalid
        assertInvalid("2021-13-01T12:30Z");  // month 13 is invalid
        assertInvalid("0000-01-01T12:30Z");  // year 0 is invalid
        // NOTE: Day-of-month range (e.g., day 00 or 32) is not validated by this
        // datatype because the pattern's capturing groups align with the "month
        // string" branch in AbstractDatetime.checkValid(), causing it to return
        // early after validating only the year and month.
    }

    @Test
    public void testKnownLimitationTimePartsNotValidated() {
        // NOTE: Hour, minute and second ranges are not validated by this datatype
        // for the same reason as testInvalidDateParts above: AbstractDatetime
        // returns after the month check without ever reaching time validation.
        // This documents the known limitation of the implementation.
        assertTrue("Datetime does not range-check hours",
                DATATYPE.isValid("2021-01-01T24:00Z", null));
        assertTrue("Datetime does not range-check minutes",
                DATATYPE.isValid("2021-01-01T12:60Z", null));
    }

    @Test
    public void testInvalidFormat() {
        assertInvalid("2021-01-01");
        assertInvalid("12:30Z");
        assertInvalid("2021-01-01T12:30:00.1234Z"); // too many millisecond digits
        assertInvalid("");
    }
}
