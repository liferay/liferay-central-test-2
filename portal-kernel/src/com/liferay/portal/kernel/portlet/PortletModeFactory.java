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

import javax.portlet.PortletMode;

/**
 * <a href="PortletModeFactory.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PortletModeFactory {

	public static PortletMode getPortletMode(String name) {
		return _instance._getPortletMode(name);
	}

	private PortletModeFactory() {
		_portletModes = new HashMap<String, PortletMode>();

		_portletModes.put(_EDIT, LiferayPortletMode.EDIT);
		_portletModes.put(_HELP, LiferayPortletMode.HELP);
		_portletModes.put(_VIEW, LiferayPortletMode.VIEW);
		_portletModes.put(_ABOUT, LiferayPortletMode.ABOUT);
		_portletModes.put(_CONFIG, LiferayPortletMode.CONFIG);
		_portletModes.put(_EDIT_DEFAULTS, LiferayPortletMode.EDIT_DEFAULTS);
		_portletModes.put(_EDIT_GUEST, LiferayPortletMode.EDIT_GUEST);
		_portletModes.put(_PREVIEW, LiferayPortletMode.PREVIEW);
		_portletModes.put(_PRINT, LiferayPortletMode.PRINT);
	}

	private PortletMode _getPortletMode(String name) {
		PortletMode portletMode = _portletModes.get(name);

		if (portletMode == null) {
			portletMode = new PortletMode(name);
		}

		return portletMode;
	}

	private static final String _EDIT = PortletMode.EDIT.toString();

	private static final String _HELP = PortletMode.HELP.toString();

	private static final String _VIEW = PortletMode.VIEW.toString();

	private static final String _ABOUT = LiferayPortletMode.ABOUT.toString();

	private static final String _CONFIG = LiferayPortletMode.CONFIG.toString();

	private static final String _EDIT_DEFAULTS =
		LiferayPortletMode.EDIT_DEFAULTS.toString();

	private static final String _EDIT_GUEST =
		LiferayPortletMode.EDIT_GUEST.toString();

	private static final String _PREVIEW =
		LiferayPortletMode.PREVIEW.toString();

	private static final String _PRINT = LiferayPortletMode.PRINT.toString();

	private static PortletModeFactory _instance = new PortletModeFactory();

	private Map<String, PortletMode> _portletModes;

}