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


/**
 * <a href="LayoutServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link LayoutService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutService
 * @generated
 */
public class LayoutServiceWrapper implements LayoutService {
	public LayoutServiceWrapper(LayoutService layoutService) {
		_layoutService = layoutService;
	}

	public com.liferay.portal.model.Layout addLayout(long groupId,
		boolean privateLayout, long parentLayoutId,
		java.util.Map<java.util.Locale, String> localeNamesMap,
		java.util.Map<java.util.Locale, String> localeTitlesMap,
		java.lang.String description, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutService.addLayout(groupId, privateLayout, parentLayoutId,
			localeNamesMap, localeTitlesMap, description, type, hidden,
			friendlyURL, serviceContext);
	}

	public com.liferay.portal.model.Layout addLayout(long groupId,
		boolean privateLayout, long parentLayoutId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String type, boolean hidden, java.lang.String friendlyURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutService.addLayout(groupId, privateLayout, parentLayoutId,
			name, title, description, type, hidden, friendlyURL, serviceContext);
	}

	public void deleteLayout(long plid)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutService.deleteLayout(plid);
	}

	public void deleteLayout(long groupId, boolean privateLayout, long layoutId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutService.deleteLayout(groupId, privateLayout, layoutId);
	}

	public byte[] exportLayouts(long groupId, boolean privateLayout,
		long[] layoutIds, java.util.Map<String, String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutService.exportLayouts(groupId, privateLayout, layoutIds,
			parameterMap, startDate, endDate);
	}

	public byte[] exportLayouts(long groupId, boolean privateLayout,
		java.util.Map<String, String[]> parameterMap, java.util.Date startDate,
		java.util.Date endDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutService.exportLayouts(groupId, privateLayout,
			parameterMap, startDate, endDate);
	}

	public java.io.File exportLayoutsAsFile(long groupId,
		boolean privateLayout, long[] layoutIds,
		java.util.Map<String, String[]> parameterMap, java.util.Date startDate,
		java.util.Date endDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutService.exportLayoutsAsFile(groupId, privateLayout,
			layoutIds, parameterMap, startDate, endDate);
	}

	public byte[] exportPortletInfo(long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<String, String[]> parameterMap, java.util.Date startDate,
		java.util.Date endDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutService.exportPortletInfo(plid, groupId, portletId,
			parameterMap, startDate, endDate);
	}

	public java.io.File exportPortletInfoAsFile(long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<String, String[]> parameterMap, java.util.Date startDate,
		java.util.Date endDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutService.exportPortletInfoAsFile(plid, groupId, portletId,
			parameterMap, startDate, endDate);
	}

	public java.lang.String getLayoutName(long groupId, boolean privateLayout,
		long layoutId, java.lang.String languageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutService.getLayoutName(groupId, privateLayout, layoutId,
			languageId);
	}

	public com.liferay.portal.model.LayoutReference[] getLayoutReferences(
		long companyId, java.lang.String portletId,
		java.lang.String preferencesKey, java.lang.String preferencesValue)
		throws com.liferay.portal.SystemException {
		return _layoutService.getLayoutReferences(companyId, portletId,
			preferencesKey, preferencesValue);
	}

	public void importLayouts(long groupId, boolean privateLayout,
		java.util.Map<String, String[]> parameterMap, byte[] bytes)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutService.importLayouts(groupId, privateLayout, parameterMap, bytes);
	}

	public void importLayouts(long groupId, boolean privateLayout,
		java.util.Map<String, String[]> parameterMap, java.io.File file)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutService.importLayouts(groupId, privateLayout, parameterMap, file);
	}

	public void importLayouts(long groupId, boolean privateLayout,
		java.util.Map<String, String[]> parameterMap, java.io.InputStream is)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutService.importLayouts(groupId, privateLayout, parameterMap, is);
	}

	public void importPortletInfo(long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<String, String[]> parameterMap, java.io.File file)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutService.importPortletInfo(plid, groupId, portletId,
			parameterMap, file);
	}

	public void importPortletInfo(long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<String, String[]> parameterMap, java.io.InputStream is)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutService.importPortletInfo(plid, groupId, portletId,
			parameterMap, is);
	}

	public void schedulePublishToLive(long sourceGroupId, long targetGroupId,
		boolean privateLayout, java.util.Map<Long, Boolean> layoutIdMap,
		java.util.Map<String, String[]> parameterMap, java.lang.String scope,
		java.util.Date startDate, java.util.Date endDate,
		java.lang.String groupName, java.lang.String cronText,
		java.util.Date schedulerStartDate, java.util.Date schedulerEndDate,
		java.lang.String description)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutService.schedulePublishToLive(sourceGroupId, targetGroupId,
			privateLayout, layoutIdMap, parameterMap, scope, startDate,
			endDate, groupName, cronText, schedulerStartDate, schedulerEndDate,
			description);
	}

	public void schedulePublishToRemote(long sourceGroupId,
		boolean privateLayout, java.util.Map<Long, Boolean> layoutIdMap,
		java.util.Map<String, String[]> parameterMap,
		java.lang.String remoteAddress, int remotePort,
		boolean secureConnection, long remoteGroupId,
		boolean remotePrivateLayout, java.util.Date startDate,
		java.util.Date endDate, java.lang.String groupName,
		java.lang.String cronText, java.util.Date schedulerStartDate,
		java.util.Date schedulerEndDate, java.lang.String description)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutService.schedulePublishToRemote(sourceGroupId, privateLayout,
			layoutIdMap, parameterMap, remoteAddress, remotePort,
			secureConnection, remoteGroupId, remotePrivateLayout, startDate,
			endDate, groupName, cronText, schedulerStartDate, schedulerEndDate,
			description);
	}

	public void setLayouts(long groupId, boolean privateLayout,
		long parentLayoutId, long[] layoutIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutService.setLayouts(groupId, privateLayout, parentLayoutId,
			layoutIds);
	}

	public void unschedulePublishToLive(long groupId, java.lang.String jobName,
		java.lang.String groupName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutService.unschedulePublishToLive(groupId, jobName, groupName);
	}

	public void unschedulePublishToRemote(long groupId,
		java.lang.String jobName, java.lang.String groupName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_layoutService.unschedulePublishToRemote(groupId, jobName, groupName);
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
		return _layoutService.updateLayout(groupId, privateLayout, layoutId,
			parentLayoutId, localeNamesMap, localeTitlesMap, description, type,
			hidden, friendlyURL, iconImage, iconBytes, serviceContext);
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
		return _layoutService.updateLayout(groupId, privateLayout, layoutId,
			parentLayoutId, localeNamesMap, localeTitlesMap, description, type,
			hidden, friendlyURL, serviceContext);
	}

	public com.liferay.portal.model.Layout updateLayout(long groupId,
		boolean privateLayout, long layoutId, java.lang.String typeSettings)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutService.updateLayout(groupId, privateLayout, layoutId,
			typeSettings);
	}

	public com.liferay.portal.model.Layout updateLookAndFeel(long groupId,
		boolean privateLayout, long layoutId, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String css, boolean wapTheme)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutService.updateLookAndFeel(groupId, privateLayout,
			layoutId, themeId, colorSchemeId, css, wapTheme);
	}

	public com.liferay.portal.model.Layout updateName(long groupId,
		boolean privateLayout, long layoutId, java.lang.String name,
		java.lang.String languageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutService.updateName(groupId, privateLayout, layoutId,
			name, languageId);
	}

	public com.liferay.portal.model.Layout updateName(long plid,
		java.lang.String name, java.lang.String languageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutService.updateName(plid, name, languageId);
	}

	public com.liferay.portal.model.Layout updateParentLayoutId(long groupId,
		boolean privateLayout, long layoutId, long parentLayoutId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutService.updateParentLayoutId(groupId, privateLayout,
			layoutId, parentLayoutId);
	}

	public com.liferay.portal.model.Layout updateParentLayoutId(long plid,
		long parentPlid)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutService.updateParentLayoutId(plid, parentPlid);
	}

	public com.liferay.portal.model.Layout updatePriority(long groupId,
		boolean privateLayout, long layoutId, int priority)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutService.updatePriority(groupId, privateLayout, layoutId,
			priority);
	}

	public com.liferay.portal.model.Layout updatePriority(long plid,
		int priority)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _layoutService.updatePriority(plid, priority);
	}

	public LayoutService getWrappedLayoutService() {
		return _layoutService;
	}

	private LayoutService _layoutService;
}