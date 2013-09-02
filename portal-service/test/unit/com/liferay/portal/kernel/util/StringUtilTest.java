/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alexander Chow
 * @author Shuyang Zhou
 * @author Hugo Huijser
 */
public class StringUtilTest {

	@Test
	public void testAppendParentheticalSuffixInteger() throws Exception {
		Assert.assertEquals(
			"Hello World (2)",
			StringUtil.appendParentheticalSuffix("Hello World", 2));
		Assert.assertEquals(
			"Hello (World) (2)",
			StringUtil.appendParentheticalSuffix("Hello (World)", 2));
		Assert.assertEquals(
			"Hello World (3)",
			StringUtil.appendParentheticalSuffix("Hello World (2)", 3));
		Assert.assertEquals(
			"Hello World (2) (4)",
			StringUtil.appendParentheticalSuffix("Hello World (2)", 4));
	}

	@Test
	public void testAppendParentheticalSuffixString() throws Exception {
		Assert.assertEquals(
			"Hello (World)",
			StringUtil.appendParentheticalSuffix("Hello", "World"));
		Assert.assertEquals(
			"Hello (World) (Liferay)",
			StringUtil.appendParentheticalSuffix("Hello (World)", "Liferay"));
	}

	@Test
	public void testHighlight() throws Exception {
		Assert.assertEquals(
			"<span class=\"highlight\">Hello</span> World <span " +
				"class=\"highlight\">Liferay</span>",
			StringUtil.highlight(
				"Hello World Liferay", new String[] {"Hello","Liferay"}));
	}

	@Test
	public void testIndexOfAny() throws Exception {
		char[] chars = {CharPool.COLON, CharPool.COMMA};

		Assert.assertEquals(-1, StringUtil.indexOfAny(null, chars));
		Assert.assertEquals(-1, StringUtil.indexOfAny(null, chars, 1));
		Assert.assertEquals(-1, StringUtil.indexOfAny(null, chars, 1, 5));

		Assert.assertEquals(4, StringUtil.indexOfAny("test,:test", chars));
		Assert.assertEquals(
			5, StringUtil.indexOfAny("test,:test,:test", chars, 5));
		Assert.assertEquals(
			-1, StringUtil.indexOfAny("test,:test,:test", chars, 7, 9));
		Assert.assertEquals(
			10, StringUtil.indexOfAny("test,:test,:test", chars, 7, 12));

		String[] strings = {null, "ab", "cd"};

		Assert.assertEquals(-1, StringUtil.indexOfAny(null, strings));
		Assert.assertEquals(-1, StringUtil.indexOfAny(null, strings, 1));
		Assert.assertEquals(-1, StringUtil.indexOfAny(null, strings, 1, 5));

		Assert.assertEquals(4, StringUtil.indexOfAny("1234cdab1234", strings));
		Assert.assertEquals(
			6, StringUtil.indexOfAny("1234cdabcd1234", strings, 5));
		Assert.assertEquals(
			-1, StringUtil.indexOfAny("1234cdab1234abcd", strings, 7, 9));
		Assert.assertEquals(
			12, StringUtil.indexOfAny("1234cdab1234cdab", strings, 7, 15));

		Assert.assertEquals(
			0, StringUtil.indexOfAny("1234", new String[] {""}));
		Assert.assertEquals(
			2, StringUtil.indexOfAny("1234", new String[] {""}, 2));
		Assert.assertEquals(
			2, StringUtil.indexOfAny("1234", new String[] {""}, 2, 4));
	}

	@Test
	public void testIsLowerCase() throws Exception {
		Assert.assertTrue(StringUtil.isLowerCase("hello world"));
		Assert.assertFalse(StringUtil.isLowerCase("Hello World"));
		Assert.assertFalse(StringUtil.isLowerCase("HELLO WORLD"));
		Assert.assertTrue(StringUtil.isLowerCase("hello-world-1"));
		Assert.assertFalse(StringUtil.isLowerCase("HELLO-WORLD-1"));
	}

	@Test
	public void testIsUpperCase() throws Exception {
		Assert.assertFalse(StringUtil.isUpperCase("hello world"));
		Assert.assertFalse(StringUtil.isUpperCase("Hello World"));
		Assert.assertTrue(StringUtil.isUpperCase("HELLO WORLD"));
		Assert.assertFalse(StringUtil.isUpperCase("hello-world-1"));
		Assert.assertTrue(StringUtil.isUpperCase("HELLO-WORLD-1"));
	}

	@Test
	public void testLastIndexOfAny() throws Exception {
		char[] chars = {CharPool.COLON, CharPool.COMMA};

		Assert.assertEquals(-1, StringUtil.lastIndexOfAny(null, chars));
		Assert.assertEquals(-1, StringUtil.lastIndexOfAny(null, chars, 1));
		Assert.assertEquals(-1, StringUtil.lastIndexOfAny(null, chars, 1, 5));

		Assert.assertEquals(5, StringUtil.lastIndexOfAny("test,:test", chars));
		Assert.assertEquals(
			5, StringUtil.lastIndexOfAny("test,:test,:test", chars, 7));
		Assert.assertEquals(
			-1, StringUtil.lastIndexOfAny("test,:test,:test", chars, 7, 9));
		Assert.assertEquals(
			11, StringUtil.lastIndexOfAny("test,:test,:test", chars, 7, 12));

		String[] strings = {null, "ab", "cd"};

		Assert.assertEquals(-1, StringUtil.lastIndexOfAny(null, strings));
		Assert.assertEquals(-1, StringUtil.lastIndexOfAny(null, strings, 1));
		Assert.assertEquals(-1, StringUtil.lastIndexOfAny(null, strings, 1, 5));

		Assert.assertEquals(
			6, StringUtil.lastIndexOfAny("1234cdab1234", strings));
		Assert.assertEquals(
			4, StringUtil.lastIndexOfAny("1234cdabcd1234", strings, 5));
		Assert.assertEquals(
			-1, StringUtil.lastIndexOfAny("1234cdab1234abcd", strings, 7, 9));
		Assert.assertEquals(
			12, StringUtil.lastIndexOfAny("1234cdab1234cdab", strings, 7, 14));

		Assert.assertEquals(
			3, StringUtil.lastIndexOfAny("1234", new String[] {""}));
		Assert.assertEquals(
			2, StringUtil.lastIndexOfAny("1234", new String[] {""}, 2));
		Assert.assertEquals(
			3, StringUtil.lastIndexOfAny("1234", new String[] {""}, 2, 3));
	}

	@Test
	public void testReplaceChar() throws Exception {
		Assert.assertEquals(
			"127_0_0_1", StringUtil.replace("127.0.0.1", '.', '_'));
	}

	@Test
	public void testReplaceEmptyString() throws Exception {
		Assert.assertEquals(
			"Hello World HELLO WORLD Hello World",
			StringUtil.replace(
				"Hello World HELLO WORLD Hello World", "", "Aloha"));
	}

	@Test
	public void testReplaceFirstChar() throws Exception {
		Assert.assertEquals(
			"127_0.0.1", StringUtil.replaceFirst("127.0.0.1", '.', '_'));
	}

	@Test
	public void testReplaceFirstString() throws Exception {
		Assert.assertEquals(
			"Aloha World HELLO WORLD Hello World",
			StringUtil.replaceFirst(
				"Hello World HELLO WORLD Hello World", "Hello", "Aloha"));
		Assert.assertEquals(
			"Hello World HELLO WORLD Aloha World",
			StringUtil.replaceFirst(
				"Hello World HELLO WORLD Hello World", "Hello", "Aloha", 10));
	}

	@Test
	public void testReplaceFirstStringArray() throws Exception {
		Assert.assertEquals(
			"Aloha World ALOHA WORLD Hello World HELLO WORLD",
			StringUtil.replaceFirst(
				"Hello World HELLO WORLD Hello World HELLO WORLD",
				new String[] {"Hello", "HELLO"},
				new String[] {"Aloha", "ALOHA"}));
	}

	@Test
	public void testReplaceLastChar() throws Exception {
		Assert.assertEquals(
			"127.0.0_1", StringUtil.replaceLast("127.0.0.1", '.', '_'));
	}

	@Test
	public void testReplaceLastString() throws Exception {
		Assert.assertEquals(
			"Hello World HELLO WORLD Aloha World",
			StringUtil.replaceLast(
				"Hello World HELLO WORLD Hello World", "Hello", "Aloha"));
	}

	@Test
	public void testReplaceLastStringArray() throws Exception {
		Assert.assertEquals(
			"Hello World HELLO WORLD Aloha World ALOHA WORLD",
			StringUtil.replaceLast(
				"Hello World HELLO WORLD Hello World HELLO WORLD",
				new String[] {"Hello", "HELLO"},
				new String[] {"Aloha", "ALOHA"}));
	}

	@Test
	public void testReplaceSpaceString() throws Exception {
		Assert.assertEquals(
			"HelloWorldHELLOWORLDHelloWorld",
			StringUtil.replace(
				"Hello World HELLO WORLD Hello World", " ", StringPool.BLANK));
	}

	@Test
	public void testReplaceString() throws Exception {
		Assert.assertEquals(
			"Aloha World HELLO WORLD Aloha World",
			StringUtil.replace(
				"Hello World HELLO WORLD Hello World", "Hello", "Aloha"));
	}

	@Test
	public void testReplaceStringArray() throws Exception {
		Assert.assertEquals(
			"Aloha World ALOHA WORLD Aloha World",
			StringUtil.replace(
				"Hello World HELLO WORLD Hello World",
				new String[] {"Hello", "HELLO"},
				new String[] {"Aloha", "ALOHA"}));
	}

	@Test
	public void testShortenString() {
		Assert.assertEquals(
			"Hello World HELLO...",
			StringUtil.shorten("Hello World HELLO WORLD Hello World"));
		Assert.assertEquals("Hi Hello", StringUtil.shorten("Hi Hello", 8));
		Assert.assertEquals("Hello...", StringUtil.shorten("Hello World", 8));
		Assert.assertEquals("Hi...", StringUtil.shorten("Hi Hello World", 8));
		Assert.assertEquals("...", StringUtil.shorten(" Hello World", 8));
		Assert.assertEquals(
			"HelloWorldHe... etc.",
			StringUtil.shorten(
				"HelloWorldHelloWorldHelloWorldHelloWorldHelloWorldHello", 20,
				"... etc."));
	}

	@Test
	public void testSplitLines() {
		String singleLine = "abcdefg";

		String[] lines = StringUtil.splitLines(singleLine);

		Assert.assertEquals(1, lines.length);
		Assert.assertEquals(singleLine, lines[0]);

		String splitByReturn = "abcd\refg\rhijk\rlmn\r";

		lines = StringUtil.splitLines(splitByReturn);

		Assert.assertEquals(4, lines.length);
		Assert.assertEquals("abcd", lines[0]);
		Assert.assertEquals("efg", lines[1]);
		Assert.assertEquals("hijk", lines[2]);
		Assert.assertEquals("lmn", lines[3]);

		String splitByNewLine = "abcd\nefg\nhijk\nlmn\n";

		lines = StringUtil.splitLines(splitByNewLine);

		Assert.assertEquals(4, lines.length);
		Assert.assertEquals("abcd", lines[0]);
		Assert.assertEquals("efg", lines[1]);
		Assert.assertEquals("hijk", lines[2]);
		Assert.assertEquals("lmn", lines[3]);

		String splitByBoth = "abcd\r\nefg\r\nhijk\r\nlmn\r\n";

		lines = StringUtil.splitLines(splitByBoth);

		Assert.assertEquals(4, lines.length);
		Assert.assertEquals("abcd", lines[0]);
		Assert.assertEquals("efg", lines[1]);
		Assert.assertEquals("hijk", lines[2]);
		Assert.assertEquals("lmn", lines[3]);

		String splitByMix = "abcd\refg\nhijk\n\rlmn\r\n";

		lines = StringUtil.splitLines(splitByMix);

		Assert.assertEquals(5, lines.length);
		Assert.assertEquals("abcd", lines[0]);
		Assert.assertEquals("efg", lines[1]);
		Assert.assertEquals("hijk", lines[2]);
		Assert.assertEquals("", lines[3]);
		Assert.assertEquals("lmn", lines[4]);
	}

	@Test
	public void testStripChar() {
		Assert.assertEquals("abcd", StringUtil.strip(" a b  c   d", ' '));
	}

	@Test
	public void testWildcardMatches() {

		// Exact match in a case sensitive manner

		String s = "abc";
		String wildcard = "abc";

		Assert.assertTrue(
			s,
			StringUtil.wildcardMatches(
				s, wildcard, CharPool.UNDERLINE, CharPool.PERCENT,
				CharPool.BACK_SLASH, true));

		// Exact match in a case insensitive manner

		s = "aBc";
		wildcard = "abc";

		Assert.assertTrue(
			s,
			StringUtil.wildcardMatches(
				s, wildcard, CharPool.UNDERLINE, CharPool.PERCENT,
				CharPool.BACK_SLASH, false));

		// Head match with a wildcard multiple character

		s = "abc";
		wildcard = "%c";

		Assert.assertTrue(
			s,
			StringUtil.wildcardMatches(
				s, wildcard, CharPool.UNDERLINE, CharPool.PERCENT,
				CharPool.BACK_SLASH, true));

		// Head match with a wildcard single character

		s = "abc";
		wildcard = "__c";

		Assert.assertTrue(
			s,
			StringUtil.wildcardMatches(
				s, wildcard, CharPool.UNDERLINE, CharPool.PERCENT,
				CharPool.BACK_SLASH, true));

		// Head mismatch with a single wildcard character

		s = "abc";
		wildcard = "a_Z";

		Assert.assertFalse(
			s,
			StringUtil.wildcardMatches(
				s, wildcard, CharPool.UNDERLINE, CharPool.PERCENT,
				CharPool.BACK_SLASH, true));

		// Head mismatch with a multiple wildcard character (this is not
		// logically possible because a head mismatch with a multipe wildcard
		// character is a tail mismatch)

		// Body match with a multiple wildcard character

		s = "abc";
		wildcard = "a%";

		Assert.assertTrue(
			s,
			StringUtil.wildcardMatches(
				s, wildcard, CharPool.UNDERLINE, CharPool.PERCENT,
				CharPool.BACK_SLASH, true));

		// Body match with a single wildcard character

		s = "abcd";
		wildcard = "a%__d";

		Assert.assertTrue(
			s,
			StringUtil.wildcardMatches(
				s, wildcard, CharPool.UNDERLINE, CharPool.PERCENT,
				CharPool.BACK_SLASH, true));

		// Tail match

		s = "abc";
		wildcard = "abc%";

		Assert.assertTrue(
			s,
			StringUtil.wildcardMatches(
				s, wildcard, CharPool.UNDERLINE, CharPool.PERCENT,
				CharPool.BACK_SLASH, true));

		// Tail mismatch

		s = "abc";
		wildcard = "abc%z";

		Assert.assertFalse(
			s,
			StringUtil.wildcardMatches(
				s, wildcard, CharPool.UNDERLINE, CharPool.PERCENT,
				CharPool.BACK_SLASH, true));

		// Match without a conflicting escape wildcard character

		s = "a_b%c";
		wildcard = "a\\_b\\%c";

		Assert.assertTrue(
			s,
			StringUtil.wildcardMatches(
				s, wildcard, CharPool.UNDERLINE, CharPool.PERCENT,
				CharPool.BACK_SLASH, true));

		// Match with a conflicting escape wildcard character

		s = new String(
			new char[] {(char)0, '_', 'a', (char)2, '%', 'c', 'd', 'e'});
		wildcard = new String(
			new char[] {(char)0, '\\', '_', '_', (char)2, '\\', '%', 'c', '%'});

		Assert.assertTrue(
			s,
			StringUtil.wildcardMatches(
				s, wildcard, CharPool.UNDERLINE, CharPool.PERCENT,
				CharPool.BACK_SLASH, true));
	}

}