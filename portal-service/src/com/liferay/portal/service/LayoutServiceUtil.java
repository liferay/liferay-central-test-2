/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="LayoutServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link LayoutService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutService
 * @generated
 */
public class LayoutServiceUtil {
	public static com.liferay.portal.model.Layout addLayout(long groupId,
		boolean privateLayout, long parentLayoutId,
		java.util.Map<java.util.Locale, String> localeNamesMap,
		java.util.Map<java.util.Locale, String> localeTitlesMap,
		java.lang.String description, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addLayout(groupId, privateLayout, parentLayoutId,
			localeNamesMap, localeTitlesMap, description, type, hidden,
			friendlyURL, serviceContext);
	}

	public static com.liferay.portal.model.Layout addLayout(long groupId,
		boolean privateLayout, long parentLayoutId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String type, boolean hidden, java.lang.String friendlyURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addLayout(groupId, privateLayout, parentLayoutId, name,
			title, description, type, hidden, friendlyURL, serviceContext);
	}

	public static void deleteLayout(long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLayout(plid);
	}

	public static void deleteLayout(long groupId, boolean privateLayout,
		long layoutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLayout(groupId, privateLayout, layoutId);
	}

	public static byte[] exportLayouts(long groupId, boolean privateLayout,
		long[] layoutIds, java.util.Map<String, String[]> parameterMap,
		java.util.Date startDate, java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .exportLayouts(groupId, privateLayout, layoutIds,
			parameterMap, startDate, endDate);
	}

	public static byte[] exportLayouts(long groupId, boolean privateLayout,
		java.util.Map<String, String[]> parameterMap, java.util.Date startDate,
		java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .exportLayouts(groupId, privateLayout, parameterMap,
			startDate, endDate);
	}

	public static java.io.File exportLayoutsAsFile(long groupId,
		boolean privateLayout, long[] layoutIds,
		java.util.Map<String, String[]> parameterMap, java.util.Date startDate,
		java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .exportLayoutsAsFile(groupId, privateLayout, layoutIds,
			parameterMap, startDate, endDate);
	}

	public static byte[] exportPortletInfo(long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<String, String[]> parameterMap, java.util.Date startDate,
		java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .exportPortletInfo(plid, groupId, portletId, parameterMap,
			startDate, endDate);
	}

	public static java.io.File exportPortletInfoAsFile(long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<String, String[]> parameterMap, java.util.Date startDate,
		java.util.Date endDate)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .exportPortletInfoAsFile(plid, groupId, portletId,
			parameterMap, startDate, endDate);
	}

	public static java.lang.String getLayoutName(long groupId,
		boolean privateLayout, long layoutId, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getLayoutName(groupId, privateLayout, layoutId, languageId);
	}

	public static com.liferay.portal.model.LayoutReference[] getLayoutReferences(
		long companyId, java.lang.String portletId,
		java.lang.String preferencesKey, java.lang.String preferencesValue)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getLayoutReferences(companyId, portletId, preferencesKey,
			preferencesValue);
	}

	public static void importLayouts(long groupId, boolean privateLayout,
		java.util.Map<String, String[]> parameterMap, byte[] bytes)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().importLayouts(groupId, privateLayout, parameterMap, bytes);
	}

	public static void importLayouts(long groupId, boolean privateLayout,
		java.util.Map<String, String[]> parameterMap, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().importLayouts(groupId, privateLayout, parameterMap, file);
	}

	public static void importLayouts(long groupId, boolean privateLayout,
		java.util.Map<String, String[]> parameterMap, java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().importLayouts(groupId, privateLayout, parameterMap, is);
	}

	public static void importPortletInfo(long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<String, String[]> parameterMap, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.importPortletInfo(plid, groupId, portletId, parameterMap, file);
	}

	public static void importPortletInfo(long plid, long groupId,
		java.lang.String portletId,
		java.util.Map<String, String[]> parameterMap, java.io.InputStream is)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.importPortletInfo(plid, groupId, portletId, parameterMap, is);
	}

	public static void schedulePublishToLive(long sourceGroupId,
		long targetGroupId, boolean privateLayout,
		java.util.Map<Long, Boolean> layoutIdMap,
		java.util.Map<String, String[]> parameterMap, java.lang.String scope,
		java.util.Date startDate, java.util.Date endDate,
		java.lang.String groupName, java.lang.String cronText,
		java.util.Date schedulerStartDate, java.util.Date schedulerEndDate,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.schedulePublishToLive(sourceGroupId, targetGroupId, privateLayout,
			layoutIdMap, parameterMap, scope, startDate, endDate, groupName,
			cronText, schedulerStartDate, schedulerEndDate, description);
	}

	public static void schedulePublishToRemote(long sourceGroupId,
		boolean privateLayout, java.util.Map<Long, Boolean> layoutIdMap,
		java.util.Map<String, String[]> parameterMap,
		java.lang.String remoteAddress, int remotePort,
		boolean secureConnection, long remoteGroupId,
		boolean remotePrivateLayout, java.util.Date startDate,
		java.util.Date endDate, java.lang.String groupName,
		java.lang.String cronText, java.util.Date schedulerStartDate,
		java.util.Date schedulerEndDate, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.schedulePublishToRemote(sourceGroupId, privateLayout, layoutIdMap,
			parameterMap, remoteAddress, remotePort, secureConnection,
			remoteGroupId, remotePrivateLayout, startDate, endDate, groupName,
			cronText, schedulerStartDate, schedulerEndDate, description);
	}

	public static void setLayouts(long groupId, boolean privateLayout,
		long parentLayoutId, long[] layoutIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.setLayouts(groupId, privateLayout, parentLayoutId, layoutIds);
	}

	public static void unschedulePublishToLive(long groupId,
		java.lang.String jobName, java.lang.String groupName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unschedulePublishToLive(groupId, jobName, groupName);
	}

	public static void unschedulePublishToRemote(long groupId,
		java.lang.String jobName, java.lang.String groupName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().unschedulePublishToRemote(groupId, jobName, groupName);
	}

	public static com.liferay.portal.model.Layout updateLayout(long groupId,
		boolean privateLayout, long layoutId, long parentLayoutId,
		java.util.Map<java.util.Locale, String> localeNamesMap,
		java.util.Map<java.util.Locale, String> localeTitlesMap,
		java.lang.String description, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL, java.lang.Boolean iconImage,
		byte[] iconBytes,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateLayout(groupId, privateLayout, layoutId,
			parentLayoutId, localeNamesMap, localeTitlesMap, description, type,
			hidden, friendlyURL, iconImage, iconBytes, serviceContext);
	}

	public static com.liferay.portal.model.Layout updateLayout(long groupId,
		boolean privateLayout, long layoutId, long parentLayoutId,
		java.util.Map<java.util.Locale, String> localeNamesMap,
		java.util.Map<java.util.Locale, String> localeTitlesMap,
		java.lang.String description, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateLayout(groupId, privateLayout, layoutId,
			parentLayoutId, localeNamesMap, localeTitlesMap, description, type,
			hidden, friendlyURL, serviceContext);
	}

	public static com.liferay.portal.model.Layout updateLayout(long groupId,
		boolean privateLayout, long layoutId, java.lang.String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateLayout(groupId, privateLayout, layoutId, typeSettings);
	}

	public static com.liferay.portal.model.Layout updateLookAndFeel(
		long groupId, boolean privateLayout, long layoutId,
		java.lang.String themeId, java.lang.String colorSchemeId,
		java.lang.String css, boolean wapTheme)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateLookAndFeel(groupId, privateLayout, layoutId,
			themeId, colorSchemeId, css, wapTheme);
	}

	public static com.liferay.portal.model.Layout updateName(long groupId,
		boolean privateLayout, long layoutId, java.lang.String name,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateName(groupId, privateLayout, layoutId, name,
			languageId);
	}

	public static com.liferay.portal.model.Layout updateName(long plid,
		java.lang.String name, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateName(plid, name, languageId);
	}

	public static com.liferay.portal.model.Layout updateParentLayoutId(
		long groupId, boolean privateLayout, long layoutId, long parentLayoutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateParentLayoutId(groupId, privateLayout, layoutId,
			parentLayoutId);
	}

	public static com.liferay.portal.model.Layout updateParentLayoutId(
		long plid, long parentPlid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateParentLayoutId(plid, parentPlid);
	}

	public static com.liferay.portal.model.Layout updatePriority(long groupId,
		boolean privateLayout, long layoutId, int priority)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updatePriority(groupId, privateLayout, layoutId, priority);
	}

	public static com.liferay.portal.model.Layout updatePriority(long plid,
		int priority)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePriority(plid, priority);
	}

	public static LayoutService getService() {
		if (_service == null) {
			_service = (LayoutService)PortalBeanLocatorUtil.locate(LayoutService.class.getName());
		}

		return _service;
	}

	public void setService(LayoutService service) {
		_service = service;
	}

	private static LayoutService _service;
}