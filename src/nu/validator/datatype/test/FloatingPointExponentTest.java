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

import nu.validator.datatype.FloatingPointExponent;
import nu.validator.datatype.FloatingPointExponentNonNegative;
import nu.validator.datatype.FloatingPointExponentPositive;
import org.junit.Test;

public class FloatingPointExponentTest {

    private static final FloatingPointExponent FLOAT = FloatingPointExponent.THE_INSTANCE;
    private static final FloatingPointExponentNonNegative FLOAT_NON_NEG = FloatingPointExponentNonNegative.THE_INSTANCE;
    private static final FloatingPointExponentPositive FLOAT_POS = FloatingPointExponentPositive.THE_INSTANCE;

    // ---- FloatingPointExponent ----

    @Test
    public void testFloatValidIntegers() {
        assertTrue(FLOAT.isValid("0", null));
        assertTrue(FLOAT.isValid("1", null));
        assertTrue(FLOAT.isValid("-1", null));
        assertTrue(FLOAT.isValid("42", null));
        assertTrue(FLOAT.isValid("-42", null));
    }

    @Test
    public void testFloatValidDecimals() {
        assertTrue(FLOAT.isValid("1.0", null));
        assertTrue(FLOAT.isValid("-1.5", null));
        assertTrue(FLOAT.isValid("3.14", null));
        assertTrue(FLOAT.isValid(".5", null));
    }

    @Test
    public void testFloatValidExponent() {
        assertTrue(FLOAT.isValid("1e5", null));
        assertTrue(FLOAT.isValid("1E5", null));
        assertTrue(FLOAT.isValid("1.5e10", null));
        assertTrue(FLOAT.isValid("1e+5", null));
        assertTrue(FLOAT.isValid("1e-5", null));
        assertTrue(FLOAT.isValid("-1.5e-10", null));
    }

    @Test
    public void testFloatInvalidEmpty() {
        assertFalse(FLOAT.isValid("", null));
    }

    @Test
    public void testFloatInvalidMinusOnly() {
        assertFalse(FLOAT.isValid("-", null));
    }

    @Test
    public void testFloatInvalidDotOnly() {
        assertFalse(FLOAT.isValid(".", null));
    }

    @Test
    public void testFloatInvalidExponentOnly() {
        assertFalse(FLOAT.isValid("e5", null));
        assertFalse(FLOAT.isValid("1e", null));
        assertFalse(FLOAT.isValid("1e+", null));
    }

    @Test
    public void testFloatInvalidNonNumeric() {
        assertFalse(FLOAT.isValid("abc", null));
        assertFalse(FLOAT.isValid("1x5", null));
        assertFalse(FLOAT.isValid(" 1", null));
        assertFalse(FLOAT.isValid("1 ", null));
    }

    // ---- FloatingPointExponentNonNegative ----

    @Test
    public void testFloatNonNegValid() {
        assertTrue(FLOAT_NON_NEG.isValid("0", null));
        assertTrue(FLOAT_NON_NEG.isValid("1", null));
        assertTrue(FLOAT_NON_NEG.isValid("1.5", null));
        assertTrue(FLOAT_NON_NEG.isValid("1e5", null));
    }

    @Test
    public void testFloatNonNegInvalidNegative() {
        assertFalse(FLOAT_NON_NEG.isValid("-1", null));
        assertFalse(FLOAT_NON_NEG.isValid("-0.5", null));
    }

    @Test
    public void testFloatNonNegInvalidEmpty() {
        assertFalse(FLOAT_NON_NEG.isValid("", null));
    }

    // ---- FloatingPointExponentPositive ----

    @Test
    public void testFloatPositiveValid() {
        assertTrue(FLOAT_POS.isValid("1", null));
        assertTrue(FLOAT_POS.isValid("0.1", null));
        assertTrue(FLOAT_POS.isValid("1.5", null));
        assertTrue(FLOAT_POS.isValid("1e5", null));
    }

    @Test
    public void testFloatPositiveInvalidZero() {
        assertFalse(FLOAT_POS.isValid("0", null));
    }

    @Test
    public void testFloatPositiveInvalidNegative() {
        assertFalse(FLOAT_POS.isValid("-1", null));
        assertFalse(FLOAT_POS.isValid("-0.5", null));
    }

    @Test
    public void testFloatPositiveInvalidEmpty() {
        assertFalse(FLOAT_POS.isValid("", null));
    }
}
