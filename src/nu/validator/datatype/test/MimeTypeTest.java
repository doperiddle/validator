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

import nu.validator.datatype.MimeType;
import org.junit.Test;

public class MimeTypeTest {

    private static final MimeType DATATYPE = MimeType.THE_INSTANCE;

    private void assertValid(String value) {
        assertTrue("Expected valid: " + value,
                DATATYPE.isValid(value, null));
    }

    private void assertInvalid(String value) {
        assertFalse("Expected invalid: " + value,
                DATATYPE.isValid(value, null));
    }

    @Test
    public void testValidBasicMimeTypes() {
        assertValid("text/html");
        assertValid("text/plain");
        assertValid("application/json");
        assertValid("application/xml");
        assertValid("image/png");
        assertValid("image/jpeg");
        assertValid("audio/mpeg");
        assertValid("video/mp4");
    }

    @Test
    public void testValidWithParameters() {
        assertValid("text/html; charset=utf-8");
        assertValid("text/html;charset=utf-8");
        assertValid("application/json; charset=UTF-8");
        assertValid("multipart/form-data; boundary=something");
    }

    @Test
    public void testValidWithQuotedParameters() {
        assertValid("text/html; charset=\"utf-8\"");
        assertValid("multipart/form-data; boundary=\"----FormBoundary\"");
    }

    @Test
    public void testValidVendorTypes() {
        assertValid("application/vnd.ms-excel");
        assertValid("application/x-www-form-urlencoded");
        assertValid("application/octet-stream");
    }

    @Test
    public void testInvalidEmpty() {
        assertInvalid("");
    }

    @Test
    public void testInvalidMissingSubtype() {
        assertInvalid("text");
        assertInvalid("text/");
    }

    @Test
    public void testInvalidMissingSupertype() {
        assertInvalid("/html");
    }

    @Test
    public void testInvalidWithInvalidChars() {
        assertInvalid("text html");         // space in type
        assertInvalid("text/html<>");       // angle brackets
        assertInvalid("text/html; =value"); // missing param name
    }

    @Test
    public void testInvalidTrailingWhitespace() {
        assertInvalid("text/html ");
    }

    @Test
    public void testInvalidSemicolonWithoutParam() {
        assertInvalid("text/html;");
        assertInvalid("text/html; ");
    }

    @Test
    public void testValidMultipleParameters() {
        assertValid("text/html; charset=utf-8; boundary=something");
    }
}
