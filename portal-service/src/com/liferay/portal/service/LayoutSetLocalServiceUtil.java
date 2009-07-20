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

package com.liferay.portal.service;

public class LayoutSetLocalServiceUtil {
	public static com.liferay.portal.model.LayoutSet addLayoutSet(
		com.liferay.portal.model.LayoutSet layoutSet)
		throws com.liferay.portal.SystemException {
		return getService().addLayoutSet(layoutSet);
	}

	public static com.liferay.portal.model.LayoutSet createLayoutSet(
		long layoutSetId) {
		return getService().createLayoutSet(layoutSetId);
	}

	public static void deleteLayoutSet(long layoutSetId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteLayoutSet(layoutSetId);
	}

	public static void deleteLayoutSet(
		com.liferay.portal.model.LayoutSet layoutSet)
		throws com.liferay.portal.SystemException {
		getService().deleteLayoutSet(layoutSet);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.LayoutSet getLayoutSet(
		long layoutSetId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getLayoutSet(layoutSetId);
	}

	public static java.util.List<com.liferay.portal.model.LayoutSet> getLayoutSets(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getLayoutSets(start, end);
	}

	public static int getLayoutSetsCount()
		throws com.liferay.portal.SystemException {
		return getService().getLayoutSetsCount();
	}

	public static com.liferay.portal.model.LayoutSet updateLayoutSet(
		com.liferay.portal.model.LayoutSet layoutSet)
		throws com.liferay.portal.SystemException {
		return getService().updateLayoutSet(layoutSet);
	}

	public static com.liferay.portal.model.LayoutSet updateLayoutSet(
		com.liferay.portal.model.LayoutSet layoutSet, boolean merge)
		throws com.liferay.portal.SystemException {
		return getService().updateLayoutSet(layoutSet, merge);
	}

	public static com.liferay.portal.model.LayoutSet addLayoutSet(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addLayoutSet(groupId, privateLayout);
	}

	public static void deleteLayoutSet(long groupId, boolean privateLayout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteLayoutSet(groupId, privateLayout);
	}

	public static com.liferay.portal.model.LayoutSet getLayoutSet(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getLayoutSet(groupId, privateLayout);
	}

	public static com.liferay.portal.model.LayoutSet getLayoutSet(
		java.lang.String virtualHost)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getLayoutSet(virtualHost);
	}

	public static void updateLogo(long groupId, boolean privateLayout,
		boolean logo, java.io.File file)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().updateLogo(groupId, privateLayout, logo, file);
	}

	public static void updateLogo(long groupId, boolean privateLayout,
		boolean logo, java.io.InputStream is)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().updateLogo(groupId, privateLayout, logo, is);
	}

	public static void updateLookAndFeel(long groupId,
		java.lang.String themeId, java.lang.String colorSchemeId,
		java.lang.String css, boolean wapTheme)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.updateLookAndFeel(groupId, themeId, colorSchemeId, css, wapTheme);
	}

	public static com.liferay.portal.model.LayoutSet updateLookAndFeel(
		long groupId, boolean privateLayout, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String css, boolean wapTheme)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateLookAndFeel(groupId, privateLayout, themeId,
			colorSchemeId, css, wapTheme);
	}

	public static com.liferay.portal.model.LayoutSet updatePageCount(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updatePageCount(groupId, privateLayout);
	}

	public static com.liferay.portal.model.LayoutSet updateVirtualHost(
		long groupId, boolean privateLayout, java.lang.String virtualHost)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService()
				   .updateVirtualHost(groupId, privateLayout, virtualHost);
	}

	public static LayoutSetLocalService getService() {
		if (_service == null) {
			throw new RuntimeException("LayoutSetLocalService is not set");
		}

		return _service;
	}

	public void setService(LayoutSetLocalService service) {
		_service = service;
	}

	private static LayoutSetLocalService _service;
}