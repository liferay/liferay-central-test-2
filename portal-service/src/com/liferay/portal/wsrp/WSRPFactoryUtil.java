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

package com.liferay.portal.wsrp;

import com.liferay.portal.model.Portlet;

import com.sun.portal.container.ChannelMode;
import com.sun.portal.container.ChannelState;
import com.sun.portal.container.ChannelURLFactory;
import com.sun.portal.container.Container;
import com.sun.portal.container.WindowRequestReader;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="WSRPFactoryUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class WSRPFactoryUtil {

	public static Container getContainer() {
		return getWSRPFactory().getContainer();
	}

	public static ProfileMapManager getProfileMapManager() {
		return getWSRPFactory().getProfileMapManager();
	}

	public static ChannelURLFactory getWindowChannelURLFactory(
		HttpServletRequest request, Portlet portlet, ChannelState windowState,
		ChannelMode portletMode, long plid) {

		return getWSRPFactory().getWindowChannelURLFactory(
			request, portlet, windowState, portletMode, plid);
	}

	public static WindowRequestReader getWindowRequestReader() {
		return getWSRPFactory().getWindowRequestReader();
	}

	public static WSRPFactory getWSRPFactory() {
		return _wsrpFactory;
	}

	public void setWSRPFactory(WSRPFactory wsrpFactory) {
		_wsrpFactory = wsrpFactory;
	}

	private static WSRPFactory _wsrpFactory;

}