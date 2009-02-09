/*
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

package com.liferay.portal.benchmark.model;

/**
 * <a href="LayoutSet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class LayoutSet {
	public LayoutSet(long layoutSetId, long groupId, long companyId,
					 boolean privateLayout, String themeId,
					 String colorSchemeId, String wapThemeId,
					 String wapColorSchemeId) {
		_layoutSetId = layoutSetId;
		_groupId = groupId;
		_companyId = companyId;
		_privateLayout = privateLayout;
		_themeId = themeId;
		_colorSchemeId = colorSchemeId;
		_wapThemeId = wapThemeId;
		_wapColorSchemeId = wapColorSchemeId;
	}

	public long getLayoutSetId() {
		return _layoutSetId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	public boolean isLogo() {
		return _logo;
	}

	public long getLogoId() {
		return _logoId;
	}

	public String getThemeId() {
		return _themeId;
	}

	public String getColorSchemeId() {
		return _colorSchemeId;
	}

	public String getWapThemeId() {
		return _wapThemeId;
	}

	public String getWapColorSchemeId() {
		return _wapColorSchemeId;
	}

	public String getCss() {
		return _css;
	}

	public int getPageCount() {
		return _pageCount;
	}

	public String getVirtualHost() {
		return _virtualHost;
	}

	public void setLogo(boolean logo) {
		_logo = logo;
	}

	public void setLogoId(long logoId) {
		_logoId = logoId;
	}

	public void setThemeId(String themeId) {
		_themeId = themeId;
	}

	public void setColorSchemeId(String colorSchemeId) {
		_colorSchemeId = colorSchemeId;
	}

	public void setWapThemeId(String wapThemeId) {
		_wapThemeId = wapThemeId;
	}

	public void setWapColorSchemeId(String wapColorSchemeId) {
		_wapColorSchemeId = wapColorSchemeId;
	}

	public void setCss(String css) {
		_css = css;
	}

	public void setPageCount(int pageCount) {
		_pageCount = pageCount;
	}

	public void setVirtualHost(String virtualHost) {
		_virtualHost = virtualHost;
	}

	private long _layoutSetId;
	private long _groupId;
	private long _companyId;
	private boolean _privateLayout;
	private boolean _logo;
	private long _logoId;
	private String _themeId;
	private String _colorSchemeId;
	private String _wapThemeId;
	private String _wapColorSchemeId;
	private String _css = "";
	private int _pageCount;
	private String _virtualHost = "";	
}
