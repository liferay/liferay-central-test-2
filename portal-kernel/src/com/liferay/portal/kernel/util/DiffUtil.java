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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

import java.io.Reader;

import java.util.List;

/**
 * <a href="DiffUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class DiffUtil {

	public static List<DiffResult>[] diff(Reader source, Reader target) {
		return getDiff().diff(source, target);
	}

	public static List<DiffResult>[] diff(
		Reader source, Reader target, String addedMarkerStart,
		String addedMarkerEnd, String deletedMarkerStart,
		String deletedMarkerEnd, int margin) {

		return getDiff().diff(
			source, target, addedMarkerStart, addedMarkerEnd,
			deletedMarkerStart, deletedMarkerEnd, margin);
	}

	public static Diff getDiff() {
		return _getUtil()._diff;
	}

	public void setDiff(Diff diff) {
		_diff = diff;
	}

	private static DiffUtil _getUtil() {
		if (_util == null) {
			_util = (DiffUtil)PortalBeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = DiffUtil.class.getName();

	private static DiffUtil _util;

	private Diff _diff;

}