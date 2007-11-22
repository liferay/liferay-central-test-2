/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.kernel.util.StringPool;

/**
 * <a href="PortletDataHandlerControl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class PortletDataHandlerControl {

	public static String getNamespacedControlName(
		String namespace, String controlName) {
		return StringPool.UNDERLINE + namespace + StringPool.UNDERLINE +
			controlName;
	}

	public PortletDataHandlerControl(String namespace, String controlName) {
		this(namespace, controlName, false);
	}

	public PortletDataHandlerControl(
		String namespace, String controlName, boolean disabled) {
		_controlName = controlName;
		_disabled = disabled;
		_namespace = namespace;
	}

	public String getControlName() {
		return _controlName;
	}

	public boolean isDisabled() {
		return _disabled;
	}

	public String getNamespace() {
		return _namespace;
	}

	public String getNamespacedControlName() {
		return getNamespacedControlName(_namespace, getControlName());
	}

	private String _controlName;
	private boolean _disabled;
	private String _namespace;

}