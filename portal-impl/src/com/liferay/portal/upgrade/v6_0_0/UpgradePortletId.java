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

package com.liferay.portal.upgrade.v6_0_0;

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
				"7",
				"1_WAR_biblegatewayportlet"
			},
			new String[] {
				"21",
				"1_WAR_randombibleverseportlet"
			},
			new String[] {
				"46",
				"1_WAR_gospelforasiaportlet"
			},
			new String[] {
				"1_WAR_wolportlet",
				"1_WAR_socialnetworkingportlet"
			},
			new String[] {
				"2_WAR_wolportlet",
				"1_WAR_socialcodingportlet"
			},
			new String[] {
				"3_WAR_wolportlet",
				"2_WAR_socialcodingportlet"
			},
			new String[] {
				"4_WAR_wolportlet",
				"2_WAR_socialnetworkingportlet"
			},
			new String[] {
				"5_WAR_wolportlet",
				"3_WAR_socialnetworkingportlet"
			},
			new String[] {
				"6_WAR_wolportlet",
				"4_WAR_socialnetworkingportlet"
			},
			new String[] {
				"7_WAR_wolportlet",
				"5_WAR_socialnetworkingportlet"
			},
			new String[] {
				"8_WAR_wolportlet",
				"6_WAR_socialnetworkingportlet"
			},
			new String[] {
				"9_WAR_wolportlet",
				"7_WAR_socialnetworkingportlet"
			},
			new String[] {
				"10_WAR_wolportlet",
				"8_WAR_socialnetworkingportlet"
			}
		};
	}

}