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
 * <a href="LayoutLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link LayoutLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutLocalService
 * @generated
 */
public class LayoutLocalServiceWrapper implements LayoutLocalService {
	public LayoutLocalServiceWrapper(LayoutLocalService layoutLocalService) {
		_layoutLocalService = layoutLocalService;
	}

	public com.liferay.portal.model.Layout addLayout(
		com.liferay.portal.model.Layout layout)
		throws com.liferay.portal.SystemException {
		return _layoutLocalService.addLayout(layout);
	}

	public com.liferay.portal.model.Layout createLayout(long plid) {
		return _layoutLocalService.createLayout(plid);
	}

	public void deleteLayout(long plid)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutLocalService.deleteLayout(plid);
	}

	public void deleteLayout(com.liferay.portal.model.Layout layout)
		throws com.liferay.portal.SystemException {
		_layoutLocalService.deleteLayout(layout);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _layoutLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _layoutLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portal.model.Layout getLayout(long plid)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.getLayout(plid);
	}

	public java.util.List<com.liferay.portal.model.Layout> getLayouts(
		int start, int end) throws com.liferay.portal.SystemException {
		return _layoutLocalService.getLayouts(start, end);
	}

	public int getLayoutsCount() throws com.liferay.portal.SystemException {
		return _layoutLocalService.getLayoutsCount();
	}

	public com.liferay.portal.model.Layout updateLayout(
		com.liferay.portal.model.Layout layout)
		throws com.liferay.portal.SystemException {
		return _layoutLocalService.updateLayout(layout);
	}

	public com.liferay.portal.model.Layout updateLayout(
		com.liferay.portal.model.Layout layout, boolean merge)
		throws com.liferay.portal.SystemException {
		return _layoutLocalService.updateLayout(layout, merge);
	}

	public com.liferay.portal.model.Layout addLayout(long userId, long groupId,
		boolean privateLayout, long parentLayoutId,
		java.util.Map<java.util.Locale, String> localeNamesMap,
		java.util.Map<java.util.Locale, String> localeTitlesMap,
		java.lang.String description, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL, long dlFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.addLayout(userId, groupId, privateLayout,
			parentLayoutId, localeNamesMap, localeTitlesMap, description, type,
			hidden, friendlyURL, dlFolderId, serviceContext);
	}

	public com.liferay.portal.model.Layout addLayout(long userId, long groupId,
		boolean privateLayout, long parentLayoutId,
		java.util.Map<java.util.Locale, String> localeNamesMap,
		java.util.Map<java.util.Locale, String> localeTitlesMap,
		java.lang.String description, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.addLayout(userId, groupId, privateLayout,
			parentLayoutId, localeNamesMap, localeTitlesMap, description, type,
			hidden, friendlyURL, serviceContext);
	}

	public com.liferay.portal.model.Layout addLayout(long userId, long groupId,
		boolean privateLayout, long parentLayoutId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String type, boolean hidden, java.lang.String friendlyURL,
		long dlFolderId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.addLayout(userId, groupId, privateLayout,
			parentLayoutId, name, title, description, type, hidden,
			friendlyURL, dlFolderId, serviceContext);
	}

	public com.liferay.portal.model.Layout addLayout(long userId, long groupId,
		boolean privateLayout, long parentLayoutId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String type, boolean hidden, java.lang.String friendlyURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.addLayout(userId, groupId, privateLayout,
			parentLayoutId, name, title, description, type, hidden,
			friendlyURL, serviceContext);
	}

	public void deleteLayout(com.liferay.portal.model.Layout layout,
		boolean updateLayoutSet)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutLocalService.deleteLayout(layout, updateLayoutSet);
	}

	public void deleteLayout(long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutLocalService.deleteLayout(groupId, privateLayout, layoutId);
	}

	public void deleteLayouts(long groupId, boolean privateLayout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutLocalService.deleteLayouts(groupId, privateLayout);
	}

	public byte[] exportLayouts(long groupId, boolean privateLayout,
		long[] layoutIds, java.util.Map<String, String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.exportLayouts(groupId, privateLayout,
			layoutIds, parameterMap, startDate, endDate);
	}

	public byte[] exportLayouts(long groupId, boolean privateLayout,
		java.util.Map<String, String[]> parameterMap, java.util.Date startDate,
		java.util.Date endDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.exportLayouts(groupId, privateLayout,
			parameterMap, startDate, endDate);
	}

	public java.io.File exportLayoutsAsFile(long groupId,
		boolean privateLayout, long[] layoutIds,
		java.util.Map<String, String[]> parameterMap, java.util.Date startDate,
		java.util.Date endDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.exportLayoutsAsFile(groupId, privateLayout,
			layoutIds, parameterMap, startDate, endDate);
	}

	public byte[] exportPortletInfo(long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<String, String[]> parameterMap, java.util.Date startDate,
		java.util.Date endDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.exportPortletInfo(plid, groupId, portletId,
			parameterMap, startDate, endDate);
	}

	public java.io.File exportPortletInfoAsFile(long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<String, String[]> parameterMap, java.util.Date startDate,
		java.util.Date endDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.exportPortletInfoAsFile(plid, groupId,
			portletId, parameterMap, startDate, endDate);
	}

	public long getDefaultPlid(long groupId)
		throws com.liferay.portal.SystemException {
		return _layoutLocalService.getDefaultPlid(groupId);
	}

	public long getDefaultPlid(long groupId, boolean privateLayout)
		throws com.liferay.portal.SystemException {
		return _layoutLocalService.getDefaultPlid(groupId, privateLayout);
	}

	public long getDefaultPlid(long groupId, boolean privateLayout,
		java.lang.String portletId) throws com.liferay.portal.SystemException {
		return _layoutLocalService.getDefaultPlid(groupId, privateLayout,
			portletId);
	}

	public com.liferay.portal.model.Layout getDLFolderLayout(long dlFolderId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.getDLFolderLayout(dlFolderId);
	}

	public com.liferay.portal.model.Layout getFriendlyURLLayout(long groupId,
		boolean privateLayout, java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.getFriendlyURLLayout(groupId, privateLayout,
			friendlyURL);
	}

	public com.liferay.portal.model.Layout getLayout(long groupId,
		boolean privateLayout, long layoutId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.getLayout(groupId, privateLayout, layoutId);
	}

	public com.liferay.portal.model.Layout getLayoutByIconImageId(
		long iconImageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.getLayoutByIconImageId(iconImageId);
	}

	public java.util.List<com.liferay.portal.model.Layout> getLayouts(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.SystemException {
		return _layoutLocalService.getLayouts(groupId, privateLayout);
	}

	public java.util.List<com.liferay.portal.model.Layout> getLayouts(
		long groupId, boolean privateLayout, long parentLayoutId)
		throws com.liferay.portal.SystemException {
		return _layoutLocalService.getLayouts(groupId, privateLayout,
			parentLayoutId);
	}

	public java.util.List<com.liferay.portal.model.Layout> getLayouts(
		long groupId, boolean privateLayout, long parentLayoutId, int start,
		int end) throws com.liferay.portal.SystemException {
		return _layoutLocalService.getLayouts(groupId, privateLayout,
			parentLayoutId, start, end);
	}

	public java.util.List<com.liferay.portal.model.Layout> getLayouts(
		long groupId, boolean privateLayout, long[] layoutIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.getLayouts(groupId, privateLayout, layoutIds);
	}

	public java.util.List<com.liferay.portal.model.Layout> getLayouts(
		long groupId, boolean privateLayout, java.lang.String type)
		throws com.liferay.portal.SystemException {
		return _layoutLocalService.getLayouts(groupId, privateLayout, type);
	}

	public com.liferay.portal.model.LayoutReference[] getLayouts(
		long companyId, java.lang.String portletId,
		java.lang.String preferencesKey, java.lang.String preferencesValue)
		throws com.liferay.portal.SystemException {
		return _layoutLocalService.getLayouts(companyId, portletId,
			preferencesKey, preferencesValue);
	}

	public long getNextLayoutId(long groupId, boolean privateLayout)
		throws com.liferay.portal.SystemException {
		return _layoutLocalService.getNextLayoutId(groupId, privateLayout);
	}

	public java.util.List<com.liferay.portal.model.Layout> getNullFriendlyURLLayouts()
		throws com.liferay.portal.SystemException {
		return _layoutLocalService.getNullFriendlyURLLayouts();
	}

	public boolean hasLayouts(long groupId, boolean privateLayout,
		long parentLayoutId) throws com.liferay.portal.SystemException {
		return _layoutLocalService.hasLayouts(groupId, privateLayout,
			parentLayoutId);
	}

	public void importLayouts(long userId, long groupId, boolean privateLayout,
		java.util.Map<String, String[]> parameterMap, byte[] bytes)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutLocalService.importLayouts(userId, groupId, privateLayout,
			parameterMap, bytes);
	}

	public void importLayouts(long userId, long groupId, boolean privateLayout,
		java.util.Map<String, String[]> parameterMap, java.io.File file)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutLocalService.importLayouts(userId, groupId, privateLayout,
			parameterMap, file);
	}

	public void importLayouts(long userId, long groupId, boolean privateLayout,
		java.util.Map<String, String[]> parameterMap, java.io.InputStream is)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutLocalService.importLayouts(userId, groupId, privateLayout,
			parameterMap, is);
	}

	public void importPortletInfo(long userId, long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<String, String[]> parameterMap, java.io.File file)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutLocalService.importPortletInfo(userId, plid, groupId, portletId,
			parameterMap, file);
	}

	public void importPortletInfo(long userId, long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<String, String[]> parameterMap, java.io.InputStream is)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutLocalService.importPortletInfo(userId, plid, groupId, portletId,
			parameterMap, is);
	}

	public void setLayouts(long groupId, boolean privateLayout,
		long parentLayoutId, long[] layoutIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutLocalService.setLayouts(groupId, privateLayout, parentLayoutId,
			layoutIds);
	}

	public com.liferay.portal.model.Layout updateFriendlyURL(long plid,
		java.lang.String friendlyURL)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.updateFriendlyURL(plid, friendlyURL);
	}

	public com.liferay.portal.model.Layout updateLayout(long groupId,
		boolean privateLayout, long layoutId, long parentLayoutId,
		java.util.Map<java.util.Locale, String> localeNamesMap,
		java.util.Map<java.util.Locale, String> localeTitlesMap,
		java.lang.String description, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL, java.lang.Boolean iconImage,
		byte[] iconBytes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.updateLayout(groupId, privateLayout,
			layoutId, parentLayoutId, localeNamesMap, localeTitlesMap,
			description, type, hidden, friendlyURL, iconImage, iconBytes,
			serviceContext);
	}

	public com.liferay.portal.model.Layout updateLayout(long groupId,
		boolean privateLayout, long layoutId, long parentLayoutId,
		java.util.Map<java.util.Locale, String> localeNamesMap,
		java.util.Map<java.util.Locale, String> localeTitlesMap,
		java.lang.String description, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.updateLayout(groupId, privateLayout,
			layoutId, parentLayoutId, localeNamesMap, localeTitlesMap,
			description, type, hidden, friendlyURL, serviceContext);
	}

	public com.liferay.portal.model.Layout updateLayout(long groupId,
		boolean privateLayout, long layoutId, java.lang.String typeSettings)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.updateLayout(groupId, privateLayout,
			layoutId, typeSettings);
	}

	public com.liferay.portal.model.Layout updateLookAndFeel(long groupId,
		boolean privateLayout, long layoutId, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String css, boolean wapTheme)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.updateLookAndFeel(groupId, privateLayout,
			layoutId, themeId, colorSchemeId, css, wapTheme);
	}

	public com.liferay.portal.model.Layout updateName(
		com.liferay.portal.model.Layout layout, java.lang.String name,
		java.lang.String languageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.updateName(layout, name, languageId);
	}

	public com.liferay.portal.model.Layout updateName(long groupId,
		boolean privateLayout, long layoutId, java.lang.String name,
		java.lang.String languageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.updateName(groupId, privateLayout, layoutId,
			name, languageId);
	}

	public com.liferay.portal.model.Layout updateName(long plid,
		java.lang.String name, java.lang.String languageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.updateName(plid, name, languageId);
	}

	public com.liferay.portal.model.Layout updateParentLayoutId(long groupId,
		boolean privateLayout, long layoutId, long parentLayoutId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.updateParentLayoutId(groupId, privateLayout,
			layoutId, parentLayoutId);
	}

	public com.liferay.portal.model.Layout updateParentLayoutId(long plid,
		long parentPlid)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.updateParentLayoutId(plid, parentPlid);
	}

	public com.liferay.portal.model.Layout updatePriority(
		com.liferay.portal.model.Layout layout, int priority)
		throws com.liferay.portal.SystemException {
		return _layoutLocalService.updatePriority(layout, priority);
	}

	public com.liferay.portal.model.Layout updatePriority(long groupId,
		boolean privateLayout, long layoutId, int priority)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.updatePriority(groupId, privateLayout,
			layoutId, priority);
	}

	public com.liferay.portal.model.Layout updatePriority(long plid,
		int priority)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutLocalService.updatePriority(plid, priority);
	}

	public LayoutLocalService getWrappedLayoutLocalService() {
		return _layoutLocalService;
	}

	private LayoutLocalService _layoutLocalService;
}