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

package com.liferay.portal.util.comparator;

import com.liferay.portal.model.Portlet;

import java.util.Comparator;

/**
 * <a href="PortletControlPanelWeightComparator.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Jorge Ferrer
 * @author Minhchau Dang
 * @author Brian Wing Shun Chan
 */
public class PortletControlPanelWeightComparator
	implements Comparator<Portlet> {

	public int compare(Portlet portlet1, Portlet portlet2) {
		double portletWeight1 = portlet1.getControlPanelEntryWeight();
		double portletWeight2 = portlet2.getControlPanelEntryWeight();

		int value = Double.compare(portletWeight1, portletWeight2);

		if (value != 0) {
			return value;
		}

		String portletId1 = portlet1.getPortletId();
		String portletId2 = portlet2.getPortletId();

		return portletId1.compareTo(portletId2);
	}

}