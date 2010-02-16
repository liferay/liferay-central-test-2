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

package com.liferay.portal.model;


/**
 * <a href="LayoutSetSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link LayoutSet}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutSet
 * @generated
 */
public class LayoutSetWrapper implements LayoutSet {
	public LayoutSetWrapper(LayoutSet layoutSet) {
		_layoutSet = layoutSet;
	}

	public long getPrimaryKey() {
		return _layoutSet.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_layoutSet.setPrimaryKey(pk);
	}

	public long getLayoutSetId() {
		return _layoutSet.getLayoutSetId();
	}

	public void setLayoutSetId(long layoutSetId) {
		_layoutSet.setLayoutSetId(layoutSetId);
	}

	public long getGroupId() {
		return _layoutSet.getGroupId();
	}

	public void setGroupId(long groupId) {
		_layoutSet.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _layoutSet.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_layoutSet.setCompanyId(companyId);
	}

	public boolean getPrivateLayout() {
		return _layoutSet.getPrivateLayout();
	}

	public boolean isPrivateLayout() {
		return _layoutSet.isPrivateLayout();
	}

	public void setPrivateLayout(boolean privateLayout) {
		_layoutSet.setPrivateLayout(privateLayout);
	}

	public boolean getLogo() {
		return _layoutSet.getLogo();
	}

	public boolean isLogo() {
		return _layoutSet.isLogo();
	}

	public void setLogo(boolean logo) {
		_layoutSet.setLogo(logo);
	}

	public long getLogoId() {
		return _layoutSet.getLogoId();
	}

	public void setLogoId(long logoId) {
		_layoutSet.setLogoId(logoId);
	}

	public java.lang.String getThemeId() {
		return _layoutSet.getThemeId();
	}

	public void setThemeId(java.lang.String themeId) {
		_layoutSet.setThemeId(themeId);
	}

	public java.lang.String getColorSchemeId() {
		return _layoutSet.getColorSchemeId();
	}

	public void setColorSchemeId(java.lang.String colorSchemeId) {
		_layoutSet.setColorSchemeId(colorSchemeId);
	}

	public java.lang.String getWapThemeId() {
		return _layoutSet.getWapThemeId();
	}

	public void setWapThemeId(java.lang.String wapThemeId) {
		_layoutSet.setWapThemeId(wapThemeId);
	}

	public java.lang.String getWapColorSchemeId() {
		return _layoutSet.getWapColorSchemeId();
	}

	public void setWapColorSchemeId(java.lang.String wapColorSchemeId) {
		_layoutSet.setWapColorSchemeId(wapColorSchemeId);
	}

	public java.lang.String getCss() {
		return _layoutSet.getCss();
	}

	public void setCss(java.lang.String css) {
		_layoutSet.setCss(css);
	}

	public int getPageCount() {
		return _layoutSet.getPageCount();
	}

	public void setPageCount(int pageCount) {
		_layoutSet.setPageCount(pageCount);
	}

	public java.lang.String getVirtualHost() {
		return _layoutSet.getVirtualHost();
	}

	public void setVirtualHost(java.lang.String virtualHost) {
		_layoutSet.setVirtualHost(virtualHost);
	}

	public long getLayoutSetPrototypeId() {
		return _layoutSet.getLayoutSetPrototypeId();
	}

	public void setLayoutSetPrototypeId(long layoutSetPrototypeId) {
		_layoutSet.setLayoutSetPrototypeId(layoutSetPrototypeId);
	}

	public com.liferay.portal.model.LayoutSet toEscapedModel() {
		return _layoutSet.toEscapedModel();
	}

	public boolean isNew() {
		return _layoutSet.isNew();
	}

	public boolean setNew(boolean n) {
		return _layoutSet.setNew(n);
	}

	public boolean isCachedModel() {
		return _layoutSet.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_layoutSet.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _layoutSet.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_layoutSet.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _layoutSet.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _layoutSet.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_layoutSet.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _layoutSet.clone();
	}

	public int compareTo(com.liferay.portal.model.LayoutSet layoutSet) {
		return _layoutSet.compareTo(layoutSet);
	}

	public int hashCode() {
		return _layoutSet.hashCode();
	}

	public java.lang.String toString() {
		return _layoutSet.toString();
	}

	public java.lang.String toXmlString() {
		return _layoutSet.toXmlString();
	}

	public com.liferay.portal.model.Theme getTheme()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSet.getTheme();
	}

	public com.liferay.portal.model.ColorScheme getColorScheme()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSet.getColorScheme();
	}

	public com.liferay.portal.model.Group getGroup() {
		return _layoutSet.getGroup();
	}

	public com.liferay.portal.model.Theme getWapTheme()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSet.getWapTheme();
	}

	public com.liferay.portal.model.ColorScheme getWapColorScheme()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSet.getWapColorScheme();
	}

	public LayoutSet getWrappedLayoutSet() {
		return _layoutSet;
	}

	private LayoutSet _layoutSet;
}