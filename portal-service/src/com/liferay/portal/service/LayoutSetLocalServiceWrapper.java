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

package com.liferay.portal.service;


/**
 * <a href="LayoutSetLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link LayoutSetLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutSetLocalService
 * @generated
 */
public class LayoutSetLocalServiceWrapper implements LayoutSetLocalService {
	public LayoutSetLocalServiceWrapper(
		LayoutSetLocalService layoutSetLocalService) {
		_layoutSetLocalService = layoutSetLocalService;
	}

	public com.liferay.portal.model.LayoutSet addLayoutSet(
		com.liferay.portal.model.LayoutSet layoutSet)
		throws com.liferay.portal.SystemException {
		return _layoutSetLocalService.addLayoutSet(layoutSet);
	}

	public com.liferay.portal.model.LayoutSet createLayoutSet(long layoutSetId) {
		return _layoutSetLocalService.createLayoutSet(layoutSetId);
	}

	public void deleteLayoutSet(long layoutSetId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutSetLocalService.deleteLayoutSet(layoutSetId);
	}

	public void deleteLayoutSet(com.liferay.portal.model.LayoutSet layoutSet)
		throws com.liferay.portal.SystemException {
		_layoutSetLocalService.deleteLayoutSet(layoutSet);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _layoutSetLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _layoutSetLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portal.model.LayoutSet getLayoutSet(long layoutSetId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutSetLocalService.getLayoutSet(layoutSetId);
	}

	public java.util.List<com.liferay.portal.model.LayoutSet> getLayoutSets(
		int start, int end) throws com.liferay.portal.SystemException {
		return _layoutSetLocalService.getLayoutSets(start, end);
	}

	public int getLayoutSetsCount() throws com.liferay.portal.SystemException {
		return _layoutSetLocalService.getLayoutSetsCount();
	}

	public com.liferay.portal.model.LayoutSet updateLayoutSet(
		com.liferay.portal.model.LayoutSet layoutSet)
		throws com.liferay.portal.SystemException {
		return _layoutSetLocalService.updateLayoutSet(layoutSet);
	}

	public com.liferay.portal.model.LayoutSet updateLayoutSet(
		com.liferay.portal.model.LayoutSet layoutSet, boolean merge)
		throws com.liferay.portal.SystemException {
		return _layoutSetLocalService.updateLayoutSet(layoutSet, merge);
	}

	public com.liferay.portal.model.LayoutSet addLayoutSet(long groupId,
		boolean privateLayout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutSetLocalService.addLayoutSet(groupId, privateLayout);
	}

	public void deleteLayoutSet(long groupId, boolean privateLayout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutSetLocalService.deleteLayoutSet(groupId, privateLayout);
	}

	public com.liferay.portal.model.LayoutSet getLayoutSet(long groupId,
		boolean privateLayout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutSetLocalService.getLayoutSet(groupId, privateLayout);
	}

	public com.liferay.portal.model.LayoutSet getLayoutSet(
		java.lang.String virtualHost)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutSetLocalService.getLayoutSet(virtualHost);
	}

	public void updateLogo(long groupId, boolean privateLayout, boolean logo,
		java.io.File file)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutSetLocalService.updateLogo(groupId, privateLayout, logo, file);
	}

	public void updateLogo(long groupId, boolean privateLayout, boolean logo,
		java.io.InputStream is)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutSetLocalService.updateLogo(groupId, privateLayout, logo, is);
	}

	public void updateLookAndFeel(long groupId, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String css, boolean wapTheme)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutSetLocalService.updateLookAndFeel(groupId, themeId,
			colorSchemeId, css, wapTheme);
	}

	public com.liferay.portal.model.LayoutSet updateLookAndFeel(long groupId,
		boolean privateLayout, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String css, boolean wapTheme)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutSetLocalService.updateLookAndFeel(groupId, privateLayout,
			themeId, colorSchemeId, css, wapTheme);
	}

	public com.liferay.portal.model.LayoutSet updatePageCount(long groupId,
		boolean privateLayout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutSetLocalService.updatePageCount(groupId, privateLayout);
	}

	public com.liferay.portal.model.LayoutSet updateVirtualHost(long groupId,
		boolean privateLayout, java.lang.String virtualHost)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutSetLocalService.updateVirtualHost(groupId, privateLayout,
			virtualHost);
	}

	public LayoutSetLocalService getWrappedLayoutSetLocalService() {
		return _layoutSetLocalService;
	}

	private LayoutSetLocalService _layoutSetLocalService;
}