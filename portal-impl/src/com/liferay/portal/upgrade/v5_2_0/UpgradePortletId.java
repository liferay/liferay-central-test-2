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

package com.liferay.portal.upgrade.v5_2_0;

/**
 * <a href="UpgradePortletId.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class UpgradePortletId
	extends com.liferay.portal.upgrade.v4_3_5.UpgradePortletId {

	protected String[][] getPortletIdsArray() {
		return new String[][] {
			new String[] {
				"109",
				"1_WAR_webformportlet"
			},
			new String[] {
				"google_adsense_portlet_WAR_googleadsenseportlet",
				"1_WAR_googleadsenseportlet"
			},
			new String[] {
				"google_gadget_portlet_WAR_googlegadgetportlet",
				"1_WAR_googlegadgetportlet"
			},
			new String[] {
				"google_maps_portlet_WAR_googlemapsportlet",
				"1_WAR_googlemapsportlet"
			}
		};
	}

}