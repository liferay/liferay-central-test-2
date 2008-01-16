/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.util.diff;

import com.liferay.util.diff.DiffResult;
import com.liferay.util.diff.DiffUtil;

import java.io.StringReader;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * <a href="DiffTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class DiffTest extends TestCase {

	public void testOne() {
		StringReader reader1 = new StringReader("liferay");
		StringReader reader2 = new StringReader("liferay");

		List results = DiffUtil.diff(reader1, reader2);

		List expected = new ArrayList();

		assertEquals(results, expected);
	}

	public void testTwo() {
		StringReader reader1 = new StringReader("liferay");
		StringReader reader2 = new StringReader("LifeRay");

		List results = DiffUtil.diff(reader1, reader2);

		List expected = new ArrayList();
		List changedLines = new ArrayList();

		changedLines.add(
			DiffUtil.OPEN_DEL + "l" + DiffUtil.CLOSE_DEL + "ife" +
				DiffUtil.OPEN_DEL + "r" + DiffUtil.CLOSE_DEL + "ay");
		expected.add(new DiffResult(DiffResult.SOURCE, 0, changedLines));

		changedLines = new ArrayList();

		changedLines.add(
			DiffUtil.OPEN_ADD + "L" + DiffUtil.CLOSE_ADD + "ife" +
				DiffUtil.OPEN_ADD + "R" + DiffUtil.CLOSE_ADD + "ay");
		expected.add(new DiffResult(DiffResult.TARGET, 0, changedLines));

		assertEquals(results, expected);
	}

	public void testThree() {
		StringReader reader1 = new StringReader("aaa");
		StringReader reader2 = new StringReader("bbb");

		List results = DiffUtil.diff(reader1, reader2);

		List expected = new ArrayList();
		List changedLines = new ArrayList();

		changedLines.add(DiffUtil.OPEN_ADD + "bbb" + DiffUtil.CLOSE_ADD);
		expected.add(new DiffResult(DiffResult.TARGET, 0, changedLines));

		changedLines = new ArrayList();

		changedLines.add(DiffUtil.OPEN_DEL + "aaa" + DiffUtil.CLOSE_DEL);
		expected.add(new DiffResult(DiffResult.SOURCE, 0, changedLines));

		assertEquals(results, expected);
	}

	public void testFour() {
		StringReader reader1 = new StringReader("rahab");
		StringReader reader2 = new StringReader("boaz");

		List results = DiffUtil.diff(reader1, reader2);

		List expected = new ArrayList();
		List changedLines = new ArrayList();

		changedLines.add(DiffUtil.OPEN_ADD + "boaz" + DiffUtil.CLOSE_ADD);
		expected.add(new DiffResult(DiffResult.TARGET, 0, changedLines));

		changedLines = new ArrayList();

		changedLines.add(DiffUtil.OPEN_DEL + "rahab" + DiffUtil.CLOSE_DEL);
		expected.add(new DiffResult(DiffResult.SOURCE, 0, changedLines));

		assertEquals(results, expected);
	}

	public void testFive() {
		StringReader reader1 = new StringReader("aaa\nbbb");
		StringReader reader2 = new StringReader("ccc\naaa");

		List results = DiffUtil.diff(reader1, reader2);

		List expected = new ArrayList();
		List changedLines = new ArrayList();

		changedLines.add(DiffUtil.OPEN_ADD + "ccc" + DiffUtil.CLOSE_ADD);
		expected.add(new DiffResult(DiffResult.TARGET, 0, changedLines));

		changedLines = new ArrayList();

		changedLines.add(DiffUtil.OPEN_DEL + "bbb" + DiffUtil.CLOSE_DEL);
		expected.add(new DiffResult(DiffResult.SOURCE, 1, changedLines));

		assertEquals(results, expected);
	}

	public void testSix() {
		StringReader reader1 = new StringReader("ccc\naaa");
		StringReader reader2 = new StringReader("aaa\nbbb");

		List results = DiffUtil.diff(reader1, reader2);

		List expected = new ArrayList();
		List changedLines = new ArrayList();

		changedLines.add(DiffUtil.OPEN_DEL + "ccc" + DiffUtil.CLOSE_DEL);
		expected.add(new DiffResult(DiffResult.SOURCE, 0, changedLines));

		changedLines = new ArrayList();

		changedLines.add(DiffUtil.OPEN_ADD + "bbb" + DiffUtil.CLOSE_ADD);
		expected.add(new DiffResult(DiffResult.TARGET, 1, changedLines));

		assertEquals(results, expected);
	}

	public void testSeven() {
		StringReader reader1 = new StringReader("ccc\naaa\nbbe");
		StringReader reader2 = new StringReader("aaa\nbbb");

		List results = DiffUtil.diff(reader1, reader2);

		List expected = new ArrayList();
		List changedLines = new ArrayList();

		changedLines.add(DiffUtil.OPEN_DEL + "ccc" + DiffUtil.CLOSE_DEL);
		expected.add(new DiffResult(DiffResult.SOURCE, 0, changedLines));

		changedLines = new ArrayList();

		changedLines.add("bb" + DiffUtil.OPEN_DEL + "e" + DiffUtil.CLOSE_DEL);
		expected.add(new DiffResult(DiffResult.SOURCE, 2, changedLines));

		changedLines = new ArrayList();

		changedLines.add("bb" + DiffUtil.OPEN_ADD + "b" + DiffUtil.CLOSE_ADD);
		expected.add(new DiffResult(DiffResult.TARGET, 1, changedLines));

		assertEquals(results, expected);
	}

	public void testEight() {
		StringReader reader1 = new StringReader("add\nbbb\nccc");
		StringReader reader2 = new StringReader("bbb\nccc\naee");

		List results = DiffUtil.diff(reader1, reader2);

		List expected = new ArrayList();
		List changedLines = new ArrayList();

		changedLines.add(DiffUtil.OPEN_DEL + "add" + DiffUtil.CLOSE_DEL);
		expected.add(new DiffResult(DiffResult.SOURCE, 0, changedLines));

		changedLines = new ArrayList();

		changedLines.add(DiffUtil.OPEN_ADD + "aee" + DiffUtil.CLOSE_ADD);
		expected.add(new DiffResult(DiffResult.TARGET, 2, changedLines));

		assertEquals(results, expected);
	}

	public void testNine() {
		StringReader reader1 = new StringReader("aaa\nbbb\nccc");
		StringReader reader2 = new StringReader("bbb\nccc\naaa");

		List results = DiffUtil.diff(reader1, reader2);

		List expected = new ArrayList();
		List changedLines = new ArrayList();

		changedLines.add(DiffUtil.OPEN_DEL + "aaa" + DiffUtil.CLOSE_DEL);
		expected.add(new DiffResult(DiffResult.SOURCE, 0, changedLines));

		changedLines = new ArrayList();

		changedLines.add(DiffUtil.OPEN_ADD + "aaa" + DiffUtil.CLOSE_ADD);
		expected.add(new DiffResult(DiffResult.TARGET, 2, changedLines));

		assertEquals(results, expected);
	}

	public void testTen() {
		StringReader reader1 = new StringReader("aaa\nbbb\nccc\n\n\nywy");
		StringReader reader2 = new StringReader("bbb\nccc\naaa\n\n\nyyy");

		List results = DiffUtil.diff(reader1, reader2);

		List expected = new ArrayList();
		List changedLines = new ArrayList();

		changedLines.add(DiffUtil.OPEN_DEL + "aaa" + DiffUtil.CLOSE_DEL);
		expected.add(new DiffResult(DiffResult.SOURCE, 0, changedLines));

		changedLines = new ArrayList();

		changedLines.add(DiffUtil.OPEN_ADD + "aaa" + DiffUtil.CLOSE_ADD);
		expected.add(new DiffResult(DiffResult.TARGET, 2, changedLines));

		changedLines = new ArrayList();

		changedLines.add(
			"y" + DiffUtil.OPEN_DEL + "w" + DiffUtil.CLOSE_DEL + "y");
		expected.add(new DiffResult(DiffResult.SOURCE, 5, changedLines));

		changedLines = new ArrayList();

		changedLines.add(
			"y" + DiffUtil.OPEN_ADD + "y" + DiffUtil.CLOSE_ADD + "y");
		expected.add(new DiffResult(DiffResult.TARGET, 5, changedLines));

		assertEquals(results, expected);
	}

	public void testEleven() {
		StringReader reader1 = new StringReader("aaa\nbbb");
		StringReader reader2 = new StringReader("ccc\naaa\nddd\nbbb");

		List results = DiffUtil.diff(reader1, reader2);

		List expected = new ArrayList();
		List changedLines = new ArrayList();

		changedLines.add(DiffUtil.OPEN_ADD + "ccc" + DiffUtil.CLOSE_ADD);
		expected.add(new DiffResult(DiffResult.TARGET, 0, changedLines));

		changedLines = new ArrayList();

		changedLines.add(DiffUtil.OPEN_ADD + "ddd" + DiffUtil.CLOSE_ADD);
		expected.add(new DiffResult(DiffResult.TARGET, 2, changedLines));

		assertEquals(results, expected);
	}

	public void testTwelve() {
		StringReader reader1 = new StringReader("abcd");
		StringReader reader2 = new StringReader("abcdee");

		List results = DiffUtil.diff(reader1, reader2);

		List expected = new ArrayList();
		List changedLines = new ArrayList();

		changedLines.add(
			"abcd" + DiffUtil.OPEN_ADD + "ee" + DiffUtil.CLOSE_ADD);
		expected.add(new DiffResult(DiffResult.TARGET, 0, changedLines));

		assertEquals(results, expected);
	}

	public void testThirteen() {
		StringReader reader1 = new StringReader("abcd");
		StringReader reader2 = new StringReader("abcdeee");

		List results = DiffUtil.diff(reader1, reader2);

		List expected = new ArrayList();
		List changedLines = new ArrayList();

		changedLines.add(DiffUtil.OPEN_ADD + "abcdeee" + DiffUtil.CLOSE_ADD);
		expected.add(new DiffResult(DiffResult.TARGET, 0, changedLines));

		changedLines = new ArrayList();

		changedLines.add(DiffUtil.OPEN_DEL + "abcd" + DiffUtil.CLOSE_DEL);
		expected.add(new DiffResult(DiffResult.SOURCE, 0, changedLines));

		assertEquals(results, expected);
	}

}