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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StaticResources;

import java.util.HashSet;
import java.util.Set;

/**
 * <a href="StaticResourcesImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Samuel Kong
 *
 */
public class StaticResourcesImpl implements StaticResources {

	public StaticResourcesImpl() {
		_extensions = new HashSet<String>();

		for (String extension : PropsValues.STATIC_RESOURCES_404_EXTENSIONS) {
			_extensions.add(extension);
		}
	}

	public boolean isStaticExtension(String extension) {
		return _extensions.contains(extension);
	}

	public boolean isStaticPath(String path) {
		String extension = FileUtil.getExtension(path);

		return isStaticExtension(extension);
	}

	private Set<String> _extensions;

}