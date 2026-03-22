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

import nu.validator.datatype.NonEmptyString;
import org.junit.Test;

public class NonEmptyStringTest {

    private static final NonEmptyString DATATYPE = NonEmptyString.THE_INSTANCE;

    @Test
    public void testValidNonEmpty() {
        assertTrue(DATATYPE.isValid("a", null));
        assertTrue(DATATYPE.isValid("hello", null));
        assertTrue(DATATYPE.isValid(" ", null));         // a space is non-empty
        assertTrue(DATATYPE.isValid("  ", null));
        assertTrue(DATATYPE.isValid("123", null));
        assertTrue(DATATYPE.isValid("\t", null));
        assertTrue(DATATYPE.isValid("x y z", null));
    }

    @Test
    public void testInvalidEmpty() {
        assertFalse(DATATYPE.isValid("", null));
    }

    @Test
    public void testValidSpecialChars() {
        assertTrue(DATATYPE.isValid("<div>", null));
        assertTrue(DATATYPE.isValid("&amp;", null));
        assertTrue(DATATYPE.isValid("\u00e9", null)); // é
    }
}
