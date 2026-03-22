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

import nu.validator.datatype.Zero;
import org.junit.Test;

public class ZeroTest {

    private static final Zero DATATYPE = Zero.THE_INSTANCE;

    @Test
    public void testValidZero() {
        assertTrue(DATATYPE.isValid("0", null));
    }

    @Test
    public void testInvalidNonZeroDigit() {
        assertFalse(DATATYPE.isValid("1", null));
        assertFalse(DATATYPE.isValid("9", null));
    }

    @Test
    public void testInvalidMultipleDigits() {
        assertFalse(DATATYPE.isValid("00", null));
        assertFalse(DATATYPE.isValid("01", null));
        assertFalse(DATATYPE.isValid("10", null));
    }

    @Test
    public void testInvalidEmpty() {
        assertFalse(DATATYPE.isValid("", null));
    }

    @Test
    public void testInvalidNegativeZero() {
        assertFalse(DATATYPE.isValid("-0", null));
    }

    @Test
    public void testInvalidWithDecimal() {
        assertFalse(DATATYPE.isValid("0.0", null));
        assertFalse(DATATYPE.isValid("0.5", null));
    }

    @Test
    public void testInvalidLetters() {
        assertFalse(DATATYPE.isValid("zero", null));
        assertFalse(DATATYPE.isValid("o", null));
    }

    @Test
    public void testInvalidWhitespace() {
        assertFalse(DATATYPE.isValid(" 0", null));
        assertFalse(DATATYPE.isValid("0 ", null));
        assertFalse(DATATYPE.isValid(" ", null));
    }
}
