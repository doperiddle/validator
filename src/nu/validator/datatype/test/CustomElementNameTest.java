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

import nu.validator.datatype.CustomElementName;
import org.junit.Test;

public class CustomElementNameTest {

    private static final CustomElementName DATATYPE = CustomElementName.THE_INSTANCE;

    private void assertValid(String value) {
        assertTrue("Expected valid: " + value,
                DATATYPE.isValid(value, null));
    }

    private void assertInvalid(String value) {
        assertFalse("Expected invalid: " + value,
                DATATYPE.isValid(value, null));
    }

    @Test
    public void testValidNames() {
        assertValid("my-element");
        assertValid("x-component");
        assertValid("my-custom-element");
        assertValid("foo-bar");
        assertValid("a-b");
    }

    @Test
    public void testValidNamesWithNumbers() {
        assertValid("my-element1");
        assertValid("x-2component");
        assertValid("a-1");
    }

    @Test
    public void testValidNamesWithSpecialChars() {
        assertValid("my-element.sub");
        assertValid("x-foo_bar");
    }

    @Test
    public void testInvalidMissingHyphen() {
        assertInvalid("myelement");
        assertInvalid("custom");
        assertInvalid("a");
        assertInvalid("abc");
    }

    @Test
    public void testInvalidUppercase() {
        assertInvalid("My-element");
        assertInvalid("MY-element");
        assertInvalid("my-Element");
    }

    @Test
    public void testInvalidStartsWithNonAlpha() {
        assertInvalid("-element");
        assertInvalid("1-element");
        assertInvalid("0-component");
    }

    @Test
    public void testInvalidEmpty() {
        assertInvalid("");
    }

    @Test
    public void testInvalidProhibitedNames() {
        assertInvalid("annotation-xml");
        assertInvalid("color-profile");
        assertInvalid("font-face");
        assertInvalid("font-face-format");
        assertInvalid("font-face-name");
        assertInvalid("font-face-src");
        assertInvalid("font-face-uri");
        assertInvalid("missing-glyph");
    }

    @Test
    public void testInvalidSpecialChars() {
        assertInvalid("my element");     // space
        assertInvalid("my/element");     // slash
        assertInvalid("my@element");     // at-sign
    }
}
