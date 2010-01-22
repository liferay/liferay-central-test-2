/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.xml;

/**
 * <a href="XMLBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class XMLBuilder {

	public static BuilderElement createRootElement(String name) {
		return new BuilderElement(name);
	}

	public static BuilderElement createRootElement(String name, String text) {
		return new BuilderElement(name, text);
	}

	public static BuilderElement createRootElement(String name, Object obj) {
		if (obj == null) {
			return new BuilderElement(name);
		}
		else {
			return new BuilderElement(name, String.valueOf(obj));
		}
	}

	public static BuilderElement addElement(
		BuilderElement parentElement, String name) {
		return new BuilderElement(parentElement, name);
	}

	public static BuilderElement addElement(
		BuilderElement parentElement, String name, String text) {
		return new BuilderElement(parentElement, name, text);
	}

	public static BuilderElement addElement(
		BuilderElement parentElement, String name, Object obj) {
		if (obj == null) {
			return new BuilderElement(parentElement, name);
		}
		else {
			return new BuilderElement(parentElement, name, String.valueOf(obj));
		}
	}

}