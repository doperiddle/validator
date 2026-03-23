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

import nu.validator.datatype.SimpleColor;
import org.junit.Test;

public class SimpleColorTest {

    private static final SimpleColor DATATYPE = SimpleColor.THE_INSTANCE;

    private void assertValid(String value) {
        assertTrue("Expected valid: " + value,
                DATATYPE.isValid(value, null));
    }

    private void assertInvalid(String value) {
        assertFalse("Expected invalid: " + value,
                DATATYPE.isValid(value, null));
    }

    @Test
    public void testValidLowercase() {
        assertValid("#000000");
        assertValid("#ffffff");
        assertValid("#aabbcc");
        assertValid("#123abc");
    }

    @Test
    public void testValidUppercase() {
        assertValid("#FFFFFF");
        assertValid("#AABBCC");
        assertValid("#123ABC");
    }

    @Test
    public void testValidMixedCase() {
        assertValid("#AbCdEf");
        assertValid("#1A2b3C");
    }

    @Test
    public void testValidCommonColors() {
        assertValid("#ff0000"); // red
        assertValid("#00ff00"); // green
        assertValid("#0000ff"); // blue
        assertValid("#000000"); // black
        assertValid("#ffffff"); // white
        assertValid("#808080"); // gray
    }

    @Test
    public void testInvalidMissingHash() {
        assertInvalid("000000");
        assertInvalid("ffffff");
        assertInvalid("aabbcc");
    }

    @Test
    public void testInvalidTooShort() {
        assertInvalid("#00000");
        assertInvalid("#0000");
        assertInvalid("#fff");
        assertInvalid("#");
        assertInvalid("");
    }

    @Test
    public void testInvalidTooLong() {
        assertInvalid("#0000000");
        assertInvalid("#ffffffff");
    }

    @Test
    public void testInvalidNonHexDigits() {
        assertInvalid("#gggggg");
        assertInvalid("#zzzzzz");
        assertInvalid("#12345g");
        assertInvalid("#1234-6");
        assertInvalid("#12345 ");
    }

    @Test
    public void testInvalidWrongPrefix() {
        assertInvalid("0x000000");
        assertInvalid("rgb(0,0,0)");
    }
}
