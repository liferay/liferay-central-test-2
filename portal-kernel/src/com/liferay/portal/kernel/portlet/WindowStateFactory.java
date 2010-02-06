/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.portlet;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.WindowState;

/**
 * <a href="WindowStateFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class WindowStateFactory {

	public static WindowState getWindowState(String name) {
		return _instance._getWindowState(name);
	}

	private WindowStateFactory() {
		_windowStates = new HashMap<String, WindowState>();

		_windowStates.put(_NORMAL, LiferayWindowState.NORMAL);
		_windowStates.put(_MAXIMIZED, LiferayWindowState.MAXIMIZED);
		_windowStates.put(_MINIMIZED, LiferayWindowState.MINIMIZED);
		_windowStates.put(_EXCLUSIVE, LiferayWindowState.EXCLUSIVE);
		_windowStates.put(_POP_UP, LiferayWindowState.POP_UP);
	}

	private WindowState _getWindowState(String name) {
		WindowState windowState = _windowStates.get(name);

		if (windowState == null) {
			windowState = new WindowState(name);
		}

		return windowState;
	}

	private static final String _NORMAL = WindowState.NORMAL.toString();

	private static final String _MAXIMIZED = WindowState.MAXIMIZED.toString();

	private static final String _MINIMIZED = WindowState.MINIMIZED.toString();

	private static final String _EXCLUSIVE =
		LiferayWindowState.EXCLUSIVE.toString();

	private static final String _POP_UP = LiferayWindowState.POP_UP.toString();

	private static WindowStateFactory _instance = new WindowStateFactory();

	private Map<String, WindowState> _windowStates;

}