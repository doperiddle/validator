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

import nu.validator.datatype.Int;
import nu.validator.datatype.IntNonNegative;
import nu.validator.datatype.IntPositive;
import org.junit.Test;

public class IntTest {

    private static final Int INT = Int.THE_INSTANCE;
    private static final IntPositive INT_POSITIVE = IntPositive.THE_INSTANCE;
    private static final IntNonNegative INT_NON_NEGATIVE = IntNonNegative.THE_INSTANCE;

    // ---- Int ----

    @Test
    public void testIntValid() {
        assertTrue(INT.isValid("0", null));
        assertTrue(INT.isValid("1", null));
        assertTrue(INT.isValid("-1", null));
        assertTrue(INT.isValid("42", null));
        assertTrue(INT.isValid("-42", null));
        assertTrue(INT.isValid("1000000", null));
        assertTrue(INT.isValid("-1000000", null));
    }

    @Test
    public void testIntInvalidEmpty() {
        assertFalse(INT.isValid("", null));
    }

    @Test
    public void testIntInvalidNonNumeric() {
        assertFalse(INT.isValid("abc", null));
        assertFalse(INT.isValid("1.0", null));
        assertFalse(INT.isValid("1e5", null));
        assertFalse(INT.isValid(" 1", null));
        assertFalse(INT.isValid("1 ", null));
        assertFalse(INT.isValid("--1", null));
        assertFalse(INT.isValid("+1", null));
    }

    @Test
    public void testIntAcceptsMinusOnly() {
        // Note: the Int validator accepts a lone minus sign as valid, because the
        // checkInt() implementation only requires that the first char is '-' or a digit,
        // and then checks remaining chars are digits. A single '-' has no remaining chars.
        assertTrue(INT.isValid("-", null));
    }

    // ---- IntPositive ----

    @Test
    public void testIntPositiveValid() {
        assertTrue(INT_POSITIVE.isValid("1", null));
        assertTrue(INT_POSITIVE.isValid("42", null));
        assertTrue(INT_POSITIVE.isValid("1000000", null));
    }

    @Test
    public void testIntPositiveInvalidZero() {
        assertFalse(INT_POSITIVE.isValid("0", null));
        assertFalse(INT_POSITIVE.isValid("00", null));
        assertFalse(INT_POSITIVE.isValid("000", null));
    }

    @Test
    public void testIntPositiveInvalidNegative() {
        assertFalse(INT_POSITIVE.isValid("-1", null));
        assertFalse(INT_POSITIVE.isValid("-42", null));
    }

    @Test
    public void testIntPositiveInvalidEmpty() {
        assertFalse(INT_POSITIVE.isValid("", null));
    }

    @Test
    public void testIntPositiveInvalidNonNumeric() {
        assertFalse(INT_POSITIVE.isValid("abc", null));
        assertFalse(INT_POSITIVE.isValid("1.0", null));
        assertFalse(INT_POSITIVE.isValid(" 1", null));
    }

    // ---- IntNonNegative ----

    @Test
    public void testIntNonNegativeValid() {
        assertTrue(INT_NON_NEGATIVE.isValid("0", null));
        assertTrue(INT_NON_NEGATIVE.isValid("1", null));
        assertTrue(INT_NON_NEGATIVE.isValid("42", null));
        assertTrue(INT_NON_NEGATIVE.isValid("1000000", null));
    }

    @Test
    public void testIntNonNegativeInvalidNegative() {
        assertFalse(INT_NON_NEGATIVE.isValid("-1", null));
        assertFalse(INT_NON_NEGATIVE.isValid("-42", null));
    }

    @Test
    public void testIntNonNegativeInvalidEmpty() {
        assertFalse(INT_NON_NEGATIVE.isValid("", null));
    }

    @Test
    public void testIntNonNegativeInvalidNonNumeric() {
        assertFalse(INT_NON_NEGATIVE.isValid("abc", null));
        assertFalse(INT_NON_NEGATIVE.isValid("1.0", null));
        assertFalse(INT_NON_NEGATIVE.isValid(" 0", null));
        assertFalse(INT_NON_NEGATIVE.isValid("+1", null));
    }
}
