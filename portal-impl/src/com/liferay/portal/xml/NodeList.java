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

package com.liferay.portal.xml;

import com.liferay.portal.kernel.util.TranslatedList;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;

import java.util.List;

/**
 * <a href="NodeList.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class NodeList<E, F> extends TranslatedList<E, F> {

	public NodeList(List<E> newList, List<F> oldList) {
		super(newList, oldList);
	}

	protected TranslatedList<E, F> newInstance(
		List<E> newList, List<F> oldList) {

		return new NodeList<E, F>(newList, oldList);
	}

	protected F toOldObject(E o) {
		if (o instanceof Element) {
			ElementImpl elementImpl = (ElementImpl)o;

			return (F)elementImpl.getWrappedElement();
		}
		else if (o instanceof Node) {
			NodeImpl nodeImpl = (NodeImpl)o;

			return (F)nodeImpl.getWrappedNode();
		}

		throw new IllegalArgumentException(o.getClass().getName());
	}

}