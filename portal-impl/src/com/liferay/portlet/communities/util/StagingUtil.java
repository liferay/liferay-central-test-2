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

package com.liferay.portlet.communities.util;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.PortalException;
import com.liferay.portal.RemoteExportException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.cal.DayAndPosition;
import com.liferay.portal.kernel.cal.Duration;
import com.liferay.portal.kernel.cal.Recurrence;
import com.liferay.portal.kernel.cal.RecurrenceSerializer;
import com.liferay.portal.kernel.io.FileCacheOutputStream;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageStatus;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.lar.PortletDataHandlerKeys;
import com.liferay.portal.lar.UserIdStrategy;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.http.GroupServiceHttp;
import com.liferay.portal.service.http.LayoutServiceHttp;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.communities.messaging.LayoutsLocalPublisherRequest;
import com.liferay.portlet.communities.messaging.LayoutsRemotePublisherRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Map;
import java.util.TimeZone;

import javax.portlet.ActionRequest;

/**
 * <a href="StagingUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 * @author Bruno Farache
 */
public class StagingUtil {

	public static void copyFromLive(ActionRequest actionRequest)
		throws Exception {

		long stagingGroupId = ParamUtil.getLong(
			actionRequest, "stagingGroupId");

		Group stagingGroup = GroupLocalServiceUtil.getGroup(stagingGroupId);

		long liveGroupId = stagingGroup.getLiveGroupId();

		Map<String, String[]> parameterMap = getStagingParameters(
			actionRequest);

		_publishLayouts(
			actionRequest, liveGroupId, stagingGroupId, parameterMap, false);
	}

	public static void copyFromLive(
			ActionRequest actionRequest, Portlet portlet)
		throws Exception {

		long plid = ParamUtil.getLong(actionRequest, "plid");

		Layout targetLayout = LayoutLocalServiceUtil.getLayout(plid);

		Group stagingGroup = targetLayout.getGroup();
		Group liveGroup = stagingGroup.getLiveGroup();

		Layout sourceLayout = LayoutLocalServiceUtil.getLayout(
			liveGroup.getGroupId(), targetLayout.isPrivateLayout(),
			targetLayout.getLayoutId());

		copyPortlet(
			actionRequest, liveGroup.getGroupId(), stagingGroup.getGroupId(),
			sourceLayout.getPlid(), targetLayout.getPlid(),
			portlet.getPortletId());
	}

	public static void copyPortlet(
			ActionRequest actionRequest, long sourceGroupId, long targetGroupId,
			long sourcePlid, long targetPlid, String portletId)
		throws Exception {

		Map<String, String[]> parameterMap = getStagingParameters(
			actionRequest);

		File file =
			LayoutLocalServiceUtil.exportPortletInfoAsFile(
				sourcePlid, sourceGroupId, portletId, parameterMap, null, null);

		LayoutServiceUtil.importPortletInfo(
			targetPlid, targetGroupId, portletId, parameterMap, file);
	}

	public static void copyRemoteLayouts(
			long sourceGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap,
			Map<String, String[]> exportParameterMap, String remoteAddress,
			int remotePort, boolean secureConnection, long remoteGroupId,
			boolean remotePrivateLayout,
			Map<String, String[]> importParameterMap, Date startDate,
			Date endDate)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user = UserLocalServiceUtil.getUser(permissionChecker.getUserId());

		StringBuilder sb = new StringBuilder();

		if (secureConnection) {
			sb.append(Http.HTTPS_WITH_SLASH);
		}
		else {
			sb.append(Http.HTTP_WITH_SLASH);
		}

		sb.append(remoteAddress);
		sb.append(StringPool.COLON);
		sb.append(remotePort);

		String url = sb.toString();

		HttpPrincipal httpPrincipal = new HttpPrincipal(
			url, user.getEmailAddress(), user.getPassword(),
			user.getPasswordEncrypted());

		// Ping remote host and verify that the group exists

		try {
			GroupServiceHttp.getGroup(httpPrincipal, remoteGroupId);
		}
		catch (NoSuchGroupException nsge) {
			RemoteExportException ree = new RemoteExportException(
				RemoteExportException.NO_GROUP);

			ree.setGroupId(remoteGroupId);

			throw ree;
		}
		catch (SystemException se) {
			RemoteExportException ree = new RemoteExportException(
				RemoteExportException.BAD_CONNECTION);

			ree.setURL(url);

			throw ree;
		}

		byte[] bytes = null;

		if (layoutIdMap == null) {
			bytes = LayoutServiceUtil.exportLayouts(
				sourceGroupId, privateLayout, exportParameterMap, startDate,
				endDate);
		}
		else {
			List<Layout> layouts = new ArrayList<Layout>();

			Iterator<Map.Entry<Long, Boolean>> itr1 =
				layoutIdMap.entrySet().iterator();

			while (itr1.hasNext()) {
				Entry<Long, Boolean> entry = itr1.next();

				long plid = GetterUtil.getLong(String.valueOf(entry.getKey()));
				boolean includeChildren = entry.getValue();

				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				if (!layouts.contains(layout)) {
					layouts.add(layout);
				}

				Iterator<Layout> itr2 = getMissingParents(
					layout, sourceGroupId).iterator();

				while (itr2.hasNext()) {
					Layout parentLayout = itr2.next();

					if (!layouts.contains(parentLayout)) {
						layouts.add(parentLayout);
					}
				}

				if (includeChildren) {
					itr2 = layout.getAllChildren().iterator();

					while (itr2.hasNext()) {
						Layout childLayout = itr2.next();

						if (!layouts.contains(childLayout)) {
							layouts.add(childLayout);
						}
					}
				}
			}

			long[] layoutIds = new long[layouts.size()];

			for (int i = 0; i < layouts.size(); i++) {
				Layout curLayout = layouts.get(i);

				layoutIds[i] = curLayout.getLayoutId();
			}

			if (layoutIds.length <= 0) {
				throw new RemoteExportException(
					RemoteExportException.NO_LAYOUTS);
			}

			bytes = LayoutServiceUtil.exportLayouts(
				sourceGroupId, privateLayout, layoutIds, exportParameterMap,
				startDate, endDate);
		}

		LayoutServiceHttp.importLayouts(
			httpPrincipal, remoteGroupId, remotePrivateLayout,
			importParameterMap, bytes);
	}

	public static List<Layout> getMissingParents(
			Layout layout, long liveGroupId)
		throws PortalException, SystemException {

		List<Layout> missingParents = new ArrayList<Layout>();

		long parentLayoutId = layout.getParentLayoutId();

		while (parentLayoutId > 0) {
			try {
				LayoutLocalServiceUtil.getLayout(
					liveGroupId, layout.isPrivateLayout(), parentLayoutId);

				// If one parent is found all others are assumed to exist

				break;
			}
			catch (NoSuchLayoutException nsle) {
				Layout parent = LayoutLocalServiceUtil.getLayout(
					layout.getGroupId(), layout.isPrivateLayout(),
					parentLayoutId);

				missingParents.add(parent);

				parentLayoutId = parent.getParentLayoutId();
			}
		}

		return missingParents;
	}

	public static String getSchedulerGroupName(
		String destinationName, long groupId) {

		StringBuilder sb = new StringBuilder();

		sb.append(destinationName);
		sb.append(StringPool.SLASH);
		sb.append(groupId);

		return sb.toString();
	}

	public static Map<String, String[]> getStagingParameters() {
		Map<String, String[]> parameterMap =
			new LinkedHashMap<String, String[]>();

		parameterMap.put(
			PortletDataHandlerKeys.CATEGORIES,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.THEME,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {UserIdStrategy.CURRENT_USER_ID});
		parameterMap.put(
			PortletDataHandlerKeys.USER_PERMISSIONS,
			new String[] {Boolean.FALSE.toString()});

		return parameterMap;
	}

	public static Map<String, String[]> getStagingParameters(
		ActionRequest actionRequest) {

		Map<String, String[]> parameterMap =
			new LinkedHashMap<String, String[]>(
				actionRequest.getParameterMap());

		if (!parameterMap.containsKey(PortletDataHandlerKeys.DATA_STRATEGY)) {
			parameterMap.put(
				PortletDataHandlerKeys.DATA_STRATEGY,
				new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR});
		}

		if (!parameterMap.containsKey(
				PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS)) {

			parameterMap.put(
				PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
				new String[] {Boolean.TRUE.toString()});
		}

		if (!parameterMap.containsKey(
				PortletDataHandlerKeys.DELETE_PORTLET_DATA)) {

			parameterMap.put(
				PortletDataHandlerKeys.DELETE_PORTLET_DATA,
				new String[] {Boolean.FALSE.toString()});
		}

		if (!parameterMap.containsKey(
				PortletDataHandlerKeys.PORTLET_DATA)) {

			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA,
				new String[] {Boolean.FALSE.toString()});
		}

		if (!parameterMap.containsKey(
				PortletDataHandlerKeys.PORTLET_DATA_ALL)) {

			Boolean portletDataAll = Boolean.FALSE;

			if (MapUtil.getBoolean(
					parameterMap, PortletDataHandlerKeys.PORTLET_DATA)) {

				portletDataAll = Boolean.TRUE;
			}

			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA_ALL,
				new String[] {portletDataAll.toString()});
		}

		if (!parameterMap.containsKey(PortletDataHandlerKeys.PORTLET_SETUP)) {
			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_SETUP,
				new String[] {Boolean.TRUE.toString()});
		}

		if (!parameterMap.containsKey(
				PortletDataHandlerKeys.PORTLET_USER_PREFERENCES)) {

			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
				new String[] {Boolean.TRUE.toString()});
		}

		if (!parameterMap.containsKey(PortletDataHandlerKeys.THEME)) {
			parameterMap.put(
				PortletDataHandlerKeys.THEME,
				new String[] {Boolean.FALSE.toString()});
		}

		if (!parameterMap.containsKey(
				PortletDataHandlerKeys.USER_ID_STRATEGY)) {

			parameterMap.put(
				PortletDataHandlerKeys.USER_ID_STRATEGY,
				new String[] {UserIdStrategy.CURRENT_USER_ID});
		}

		return parameterMap;
	}

	public static void publishLayout(
			long plid, long liveGroupId, boolean includeChildren)
		throws Exception {

		Map<String, String[]> parameterMap = getStagingParameters();

		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.FALSE.toString()});

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		List<Layout> layouts = new ArrayList<Layout>();

		layouts.add(layout);

		layouts.addAll(getMissingParents(layout, liveGroupId));

		if (includeChildren) {
			layouts.addAll(layout.getAllChildren());
		}

		Iterator<Layout> itr = layouts.iterator();

		long[] layoutIds = new long[layouts.size()];

		for (int i = 0; itr.hasNext(); i++) {
			Layout curLayout = itr.next();

			layoutIds[i] = curLayout.getLayoutId();
		}

		publishLayouts(
			layout.getGroupId(), liveGroupId, layout.isPrivateLayout(),
			layoutIds, parameterMap, null, null);
	}

	public static void publishLayouts(
			long sourceGroupId, long targetGroupId, boolean privateLayout,
			Map<String, String[]> parameterMap, Date startDate, Date endDate)
		throws Exception {

		publishLayouts(
			sourceGroupId, targetGroupId, privateLayout, (long[])null,
			parameterMap, startDate, endDate);
	}

	public static void publishLayouts(
			long sourceGroupId, long targetGroupId, boolean privateLayout,
			long[] layoutIds, Map<String, String[]> parameterMap,
			Date startDate, Date endDate)
		throws Exception {

		File file =
			LayoutLocalServiceUtil.exportLayoutsAsFile(
				sourceGroupId, privateLayout, layoutIds, parameterMap,
				startDate, endDate);

		LayoutServiceUtil.importLayouts(
			targetGroupId, privateLayout, parameterMap, file);
	}

	public static void publishLayouts(
			long sourceGroupId, long targetGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap,
			Date startDate, Date endDate)
		throws Exception {

		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.FALSE.toString()});

		List<Layout> layouts = new ArrayList<Layout>();

		Iterator<Map.Entry<Long, Boolean>> itr1 =
			layoutIdMap.entrySet().iterator();

		while (itr1.hasNext()) {
			Entry<Long, Boolean> entry = itr1.next();

			long plid = GetterUtil.getLong(String.valueOf(entry.getKey()));
			boolean includeChildren = entry.getValue();

			Layout layout = LayoutLocalServiceUtil.getLayout(plid);

			if (!layouts.contains(layout)) {
				layouts.add(layout);
			}

			Iterator<Layout> itr2 = getMissingParents(
				layout, targetGroupId).iterator();

			while (itr2.hasNext()) {
				Layout parentLayout = itr2.next();

				if (!layouts.contains(parentLayout)) {
					layouts.add(parentLayout);
				}
			}

			if (includeChildren) {
				itr2 = layout.getAllChildren().iterator();

				while (itr2.hasNext()) {
					Layout childLayout = itr2.next();

					if (!layouts.contains(childLayout)) {
						layouts.add(childLayout);
					}
				}
			}
		}

		long[] layoutIds = new long[layouts.size()];

		for (int i = 0; i < layouts.size(); i++) {
			Layout curLayout = layouts.get(i);

			layoutIds[i] = curLayout.getLayoutId();
		}

		publishLayouts(
			sourceGroupId, targetGroupId, privateLayout, layoutIds,
			parameterMap, startDate, endDate);
	}

	public static void publishToLive(ActionRequest actionRequest)
		throws Exception {

		long stagingGroupId = ParamUtil.getLong(
			actionRequest, "stagingGroupId");

		Group stagingGroup = GroupLocalServiceUtil.getGroup(stagingGroupId);

		long liveGroupId = stagingGroup.getLiveGroupId();

		Map<String, String[]> parameterMap = getStagingParameters(
			actionRequest);

		_publishLayouts(
			actionRequest, stagingGroupId, liveGroupId, parameterMap, false);
	}

	public static void publishToLive(
			ActionRequest actionRequest, Portlet portlet)
		throws Exception {

		long plid = ParamUtil.getLong(actionRequest, "plid");

		Layout sourceLayout = LayoutLocalServiceUtil.getLayout(plid);

		Group stagingGroup = sourceLayout.getGroup();
		Group liveGroup = stagingGroup.getLiveGroup();

		Layout targetLayout = LayoutLocalServiceUtil.getLayout(
			liveGroup.getGroupId(), sourceLayout.isPrivateLayout(),
			sourceLayout.getLayoutId());

		copyPortlet(
			actionRequest, stagingGroup.getGroupId(), liveGroup.getGroupId(),
			sourceLayout.getPlid(), targetLayout.getPlid(),
			portlet.getPortletId());
	}

	public static void publishToRemote(ActionRequest actionRequest)
		throws Exception {

		_publishToRemote(actionRequest, false);
	}

	public static void scheduleCopyFromLive(ActionRequest actionRequest)
		throws Exception {

		long stagingGroupId = ParamUtil.getLong(
			actionRequest, "stagingGroupId");

		Group stagingGroup = GroupLocalServiceUtil.getGroup(stagingGroupId);

		long liveGroupId = stagingGroup.getLiveGroupId();

		Map<String, String[]> parameterMap = getStagingParameters(
			actionRequest);

		_publishLayouts(
			actionRequest, liveGroupId, stagingGroupId, parameterMap, true);
	}

	public static void schedulePublishToLive(ActionRequest actionRequest)
		throws Exception {

		long stagingGroupId = ParamUtil.getLong(
			actionRequest, "stagingGroupId");

		Group stagingGroup = GroupLocalServiceUtil.getGroup(stagingGroupId);

		long liveGroupId = stagingGroup.getLiveGroupId();

		Map<String, String[]> parameterMap = getStagingParameters(
			actionRequest);

		_publishLayouts(
			actionRequest, stagingGroupId, liveGroupId, parameterMap, true);
	}

	public static void schedulePublishToRemote(ActionRequest actionRequest)
		throws Exception {

		_publishToRemote(actionRequest, true);
	}

	public static void unscheduleCopyFromLive(ActionRequest actionRequest)
		throws Exception {

		long stagingGroupId = ParamUtil.getLong(
			actionRequest, "stagingGroupId");

		String jobName = ParamUtil.getString(actionRequest, "jobName");
		String groupName = getSchedulerGroupName(
			DestinationNames.LAYOUTS_LOCAL_PUBLISHER, stagingGroupId);

		LayoutServiceUtil.unschedulePublishToLive(
			stagingGroupId, jobName, groupName);
	}

	public static void unschedulePublishToLive(ActionRequest actionRequest)
		throws Exception {

		long stagingGroupId = ParamUtil.getLong(
			actionRequest, "stagingGroupId");

		Group stagingGroup = GroupLocalServiceUtil.getGroup(stagingGroupId);

		long liveGroupId = stagingGroup.getLiveGroupId();

		String jobName = ParamUtil.getString(actionRequest, "jobName");
		String groupName = getSchedulerGroupName(
			DestinationNames.LAYOUTS_LOCAL_PUBLISHER, liveGroupId);

		LayoutServiceUtil.unschedulePublishToLive(
			liveGroupId, jobName, groupName);
	}

	public static void unschedulePublishToRemote(ActionRequest actionRequest)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		String jobName = ParamUtil.getString(actionRequest, "jobName");
		String groupName = getSchedulerGroupName(
			DestinationNames.LAYOUTS_REMOTE_PUBLISHER, groupId);

		LayoutServiceUtil.unschedulePublishToRemote(
			groupId, jobName, groupName);
	}

	public static void updateStaging(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");

		if (!GroupPermissionUtil.contains(
				permissionChecker, liveGroupId, ActionKeys.MANAGE_STAGING)) {

			throw new PrincipalException();
		}

		long stagingGroupId = ParamUtil.getLong(
			actionRequest, "stagingGroupId");

		boolean stagingEnabled = ParamUtil.getBoolean(
			actionRequest, "stagingEnabled");

		if ((stagingGroupId > 0) && !stagingEnabled) {
			GroupServiceUtil.deleteGroup(stagingGroupId);

			GroupServiceUtil.updateWorkflow(liveGroupId, false, 0, null);
		}
		else if ((stagingGroupId == 0) && stagingEnabled) {
			Group liveGroup = GroupServiceUtil.getGroup(liveGroupId);

			Group stagingGroup = GroupServiceUtil.addGroup(
				liveGroup.getGroupId(),
				liveGroup.getDescriptiveName() + " (Staging)",
				liveGroup.getDescription(),
				GroupConstants.TYPE_COMMUNITY_PRIVATE, null,
				liveGroup.isActive(), null);

			if (liveGroup.hasPrivateLayouts()) {
				Map<String, String[]> parameterMap = getStagingParameters();

				publishLayouts(
					liveGroup.getGroupId(), stagingGroup.getGroupId(), true,
					parameterMap, null, null);
			}

			if (liveGroup.hasPublicLayouts()) {
				Map<String, String[]> parameterMap = getStagingParameters();

				publishLayouts(
					liveGroup.getGroupId(), stagingGroup.getGroupId(), false,
					parameterMap, null, null);
			}
		}
	}

	private static void _addWeeklyDayPos(
		ActionRequest actionRequest, List<DayAndPosition> list, int day) {

		if (ParamUtil.getBoolean(actionRequest, "weeklyDayPos" + day)) {
			list.add(new DayAndPosition(day, 0));
		}
	}

	private static String _getCronText(
			ActionRequest actionRequest, Calendar startDate,
			boolean timeZoneSensitive, int recurrenceType)
		throws Exception {

		Calendar startCal = null;

		if (timeZoneSensitive) {
			startCal = CalendarFactoryUtil.getCalendar();

			startCal.setTime(startDate.getTime());
		}
		else {
			startCal = (Calendar)startDate.clone();
		}

		Recurrence recurrence = new Recurrence(
			startCal, new Duration(1, 0, 0, 0), recurrenceType);

		recurrence.setWeekStart(Calendar.SUNDAY);

		if (recurrenceType == Recurrence.DAILY) {
			int dailyType = ParamUtil.getInteger(actionRequest, "dailyType");

			if (dailyType == 0) {
				int dailyInterval = ParamUtil.getInteger(
					actionRequest, "dailyInterval", 1);

				recurrence.setInterval(dailyInterval);
			}
			else {
				DayAndPosition[] dayPos = {
					new DayAndPosition(Calendar.MONDAY, 0),
					new DayAndPosition(Calendar.TUESDAY, 0),
					new DayAndPosition(Calendar.WEDNESDAY, 0),
					new DayAndPosition(Calendar.THURSDAY, 0),
					new DayAndPosition(Calendar.FRIDAY, 0)};

				recurrence.setByDay(dayPos);
			}
		}
		else if (recurrenceType == Recurrence.WEEKLY) {
			int weeklyInterval = ParamUtil.getInteger(
				actionRequest, "weeklyInterval", 1);

			recurrence.setInterval(weeklyInterval);

			List<DayAndPosition> dayPos = new ArrayList<DayAndPosition>();

			_addWeeklyDayPos(actionRequest, dayPos, Calendar.SUNDAY);
			_addWeeklyDayPos(actionRequest, dayPos, Calendar.MONDAY);
			_addWeeklyDayPos(actionRequest, dayPos, Calendar.TUESDAY);
			_addWeeklyDayPos(actionRequest, dayPos, Calendar.WEDNESDAY);
			_addWeeklyDayPos(actionRequest, dayPos, Calendar.THURSDAY);
			_addWeeklyDayPos(actionRequest, dayPos, Calendar.FRIDAY);
			_addWeeklyDayPos(actionRequest, dayPos, Calendar.SATURDAY);

			if (dayPos.size() == 0) {
				dayPos.add(new DayAndPosition(Calendar.MONDAY, 0));
			}

			recurrence.setByDay(dayPos.toArray(new DayAndPosition[0]));
		}
		else if (recurrenceType == Recurrence.MONTHLY) {
			int monthlyType = ParamUtil.getInteger(
				actionRequest, "monthlyType");

			if (monthlyType == 0) {
				int monthlyDay = ParamUtil.getInteger(
					actionRequest, "monthlyDay0", 1);

				recurrence.setByMonthDay(new int[] {monthlyDay});

				int monthlyInterval = ParamUtil.getInteger(
					actionRequest, "monthlyInterval0", 1);

				recurrence.setInterval(monthlyInterval);
			}
			else {
				int monthlyPos = ParamUtil.getInteger(
					actionRequest, "monthlyPos");
				int monthlyDay = ParamUtil.getInteger(
					actionRequest, "monthlyDay1");

				DayAndPosition[] dayPos = {
					new DayAndPosition(monthlyDay, monthlyPos)};

				recurrence.setByDay(dayPos);

				int monthlyInterval = ParamUtil.getInteger(
					actionRequest, "monthlyInterval1", 1);

				recurrence.setInterval(monthlyInterval);
			}
		}
		else if (recurrenceType == Recurrence.YEARLY) {
			int yearlyType = ParamUtil.getInteger(actionRequest, "yearlyType");

			if (yearlyType == 0) {
				int yearlyMonth = ParamUtil.getInteger(
					actionRequest, "yearlyMonth0");
				int yearlyDay = ParamUtil.getInteger(
					actionRequest, "yearlyDay0", 1);

				recurrence.setByMonth(new int[] {yearlyMonth});
				recurrence.setByMonthDay(new int[] {yearlyDay});

				int yearlyInterval = ParamUtil.getInteger(
					actionRequest, "yearlyInterval0", 1);

				recurrence.setInterval(yearlyInterval);
			}
			else {
				int yearlyPos = ParamUtil.getInteger(
					actionRequest, "yearlyPos");
				int yearlyDay = ParamUtil.getInteger(
					actionRequest, "yearlyDay1");
				int yearlyMonth = ParamUtil.getInteger(
					actionRequest, "yearlyMonth1");

				DayAndPosition[] dayPos = {
					new DayAndPosition(yearlyDay, yearlyPos)};

				recurrence.setByDay(dayPos);

				recurrence.setByMonth(new int[] {yearlyMonth});

				int yearlyInterval = ParamUtil.getInteger(
					actionRequest, "yearlyInterval1", 1);

				recurrence.setInterval(yearlyInterval);
			}
		}

		return RecurrenceSerializer.toCronText(recurrence);
	}

	private static Calendar _getDate(
			ActionRequest actionRequest, String paramPrefix,
			boolean timeZoneSensitive)
		throws Exception {

		int dateMonth = ParamUtil.getInteger(
			actionRequest, paramPrefix + "Month");
		int dateDay = ParamUtil.getInteger(actionRequest, paramPrefix + "Day");
		int dateYear = ParamUtil.getInteger(
			actionRequest, paramPrefix + "Year");
		int dateHour = ParamUtil.getInteger(
			actionRequest, paramPrefix + "Hour");
		int dateMinute = ParamUtil.getInteger(
			actionRequest, paramPrefix + "Minute");
		int dateAmPm = ParamUtil.getInteger(
			actionRequest, paramPrefix + "AmPm");

		if (dateAmPm == Calendar.PM) {
			dateHour += 12;
		}

		Locale locale = null;
		TimeZone timeZone = null;

		if (timeZoneSensitive) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			locale = themeDisplay.getLocale();
			timeZone = themeDisplay.getTimeZone();
		}
		else {
			locale = LocaleUtil.getDefault();
			timeZone = TimeZoneUtil.getDefault();
		}

		Calendar cal = CalendarFactoryUtil.getCalendar(timeZone, locale);

		cal.set(Calendar.MONTH, dateMonth);
		cal.set(Calendar.DATE, dateDay);
		cal.set(Calendar.YEAR, dateYear);
		cal.set(Calendar.HOUR_OF_DAY, dateHour);
		cal.set(Calendar.MINUTE, dateMinute);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal;
	}

	private static void _publishLayouts(
			ActionRequest actionRequest, long sourceGroupId, long targetGroupId,
			Map<String, String[]> parameterMap, boolean schedule)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		String tabs1 = ParamUtil.getString(actionRequest, "tabs1");

		boolean privateLayout = true;

		if (tabs1.equals("public-pages")) {
			privateLayout = false;
		}

		String scope = ParamUtil.getString(actionRequest, "scope");

		Map<Long, Boolean> layoutIdMap = new LinkedHashMap<Long, Boolean>();

		if (scope.equals("selected-pages")) {
			long[] rowIds = ParamUtil.getLongValues(actionRequest, "rowIds");

			for (long selPlid : rowIds) {
				boolean includeChildren = ParamUtil.getBoolean(
					actionRequest, "includeChildren_" + selPlid);

				layoutIdMap.put(selPlid, includeChildren);
			}
		}

		String range = ParamUtil.getString(actionRequest, "range");

		Date startDate = null;
		Date endDate = null;

		if (range.equals("dateRange")) {
			startDate = _getDate(actionRequest, "startDate", true).getTime();

			endDate = _getDate(actionRequest, "endDate", true).getTime();
		}
		else if (range.equals("last")) {
			int rangeLast = ParamUtil.getInteger(actionRequest, "last");

			Date now = new Date();

			startDate = new Date(now.getTime() - (rangeLast * Time.HOUR));

			endDate = now;
		}

		if (schedule) {
			String groupName = getSchedulerGroupName(
				DestinationNames.LAYOUTS_LOCAL_PUBLISHER, targetGroupId);

			int recurrenceType = ParamUtil.getInteger(
				actionRequest, "recurrenceType");

			Calendar startCal = _getDate(
				actionRequest, "schedulerStartDate", false);

			String cronText = _getCronText(
				actionRequest, startCal, false, recurrenceType);

			Date schedulerEndDate = null;

			int endDateType = ParamUtil.getInteger(
				actionRequest, "endDateType");

			if (endDateType == 1) {
				Calendar endCal = _getDate(
					actionRequest, "schedulerEndDate", false);

				schedulerEndDate = endCal.getTime();
			}

			String description = ParamUtil.getString(
				actionRequest, "description");

			LayoutServiceUtil.schedulePublishToLive(
				sourceGroupId, targetGroupId, privateLayout, layoutIdMap,
				parameterMap, scope, startDate, endDate, groupName, cronText,
				startCal.getTime(), schedulerEndDate, description);
		}
		else {
			MessageStatus messageStatus = new MessageStatus();

			messageStatus.startTimer();

			String command =
				LayoutsLocalPublisherRequest.COMMAND_SELECTED_PAGES;

			try {
				if (scope.equals("all-pages")) {
					command = LayoutsLocalPublisherRequest.COMMAND_ALL_PAGES;

					publishLayouts(
						sourceGroupId, targetGroupId, privateLayout,
						parameterMap, startDate, endDate);
				}
				else {
					publishLayouts(
						sourceGroupId, targetGroupId, privateLayout,
						layoutIdMap, parameterMap, startDate, endDate);
				}
			}
			catch (Exception e) {
				messageStatus.setException(e);

				throw e;
			}
			finally {
				messageStatus.stopTimer();

				LayoutsLocalPublisherRequest publisherRequest =
					new LayoutsLocalPublisherRequest(
						command, themeDisplay.getUserId(), sourceGroupId,
						targetGroupId, privateLayout, layoutIdMap, parameterMap,
						startDate, endDate);

				messageStatus.setPayload(publisherRequest);

				MessageBusUtil.sendMessage(
					DestinationNames.MESSAGE_BUS_MESSAGE_STATUS, messageStatus);
			}
		}
	}

	private static void _publishToRemote(
			ActionRequest actionRequest, boolean schedule)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		String tabs1 = ParamUtil.getString(actionRequest, "tabs1");

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		boolean privateLayout = true;

		if (tabs1.equals("public-pages")) {
			privateLayout = false;
		}

		String scope = ParamUtil.getString(actionRequest, "scope");

		if (Validator.isNull(scope)) {
			scope = "all-pages";
		}

		Map<Long, Boolean> layoutIdMap = null;
		Map<String, String[]> parameterMap = actionRequest.getParameterMap();

		if (scope.equals("selected-pages")) {
			layoutIdMap = new LinkedHashMap<Long, Boolean>();

			long[] rowIds = ParamUtil.getLongValues(actionRequest, "rowIds");

			for (long selPlid : rowIds) {
				boolean includeChildren = ParamUtil.getBoolean(
					actionRequest, "includeChildren_" + selPlid);

				layoutIdMap.put(selPlid, includeChildren);
			}
		}

		String remoteAddress = ParamUtil.getString(
			actionRequest, "remoteAddress");
		int remotePort = ParamUtil.getInteger(actionRequest, "remotePort");
		boolean secureConnection = ParamUtil.getBoolean(
			actionRequest, "secureConnection");

		long remoteGroupId = ParamUtil.getLong(actionRequest, "remoteGroupId");
		boolean remotePrivateLayout = ParamUtil.getBoolean(
			actionRequest, "remotePrivateLayout");

		String range = ParamUtil.getString(actionRequest, "range");

		Date startDate = null;
		Date endDate = null;

		if (range.equals("dateRange")) {
			startDate = _getDate(actionRequest, "startDate", true).getTime();

			endDate = _getDate(actionRequest, "endDate", true).getTime();
		}
		else if (range.equals("last")) {
			int rangeLast = ParamUtil.getInteger(actionRequest, "last");

			Date now = new Date();

			startDate = new Date(now.getTime() - (rangeLast * Time.HOUR));

			endDate = now;
		}

		if (schedule) {
			String groupName = getSchedulerGroupName(
				DestinationNames.LAYOUTS_REMOTE_PUBLISHER, groupId);

			int recurrenceType = ParamUtil.getInteger(
				actionRequest, "recurrenceType");

			Calendar startCal = _getDate(
				actionRequest, "schedulerStartDate", false);

			String cronText = _getCronText(
				actionRequest, startCal, false, recurrenceType);

			Date schedulerEndDate = null;

			int endDateType = ParamUtil.getInteger(
				actionRequest, "endDateType");

			if (endDateType == 1) {
				Calendar endCal = _getDate(
					actionRequest, "schedulerEndDate", false);

				schedulerEndDate = endCal.getTime();
			}

			String description = ParamUtil.getString(
				actionRequest, "description");

			LayoutServiceUtil.schedulePublishToRemote(
				groupId, privateLayout, layoutIdMap,
				getStagingParameters(actionRequest), remoteAddress, remotePort,
				secureConnection, remoteGroupId, remotePrivateLayout, startDate,
				endDate, groupName, cronText, startCal.getTime(),
				schedulerEndDate, description);
		}
		else {
			MessageStatus messageStatus = new MessageStatus();

			messageStatus.startTimer();

			try {
				copyRemoteLayouts(
					groupId, privateLayout, layoutIdMap, parameterMap,
					remoteAddress, remotePort, secureConnection, remoteGroupId,
					remotePrivateLayout, getStagingParameters(actionRequest),
					startDate, endDate);
			}
			catch (Exception e) {
				messageStatus.setException(e);

				throw e;
			}
			finally {
				messageStatus.stopTimer();

				LayoutsRemotePublisherRequest publisherRequest =
					new LayoutsRemotePublisherRequest(
						themeDisplay.getUserId(), groupId, privateLayout,
						layoutIdMap, parameterMap, remoteAddress, remotePort,
						secureConnection, remoteGroupId, remotePrivateLayout,
						startDate, endDate);

				messageStatus.setPayload(publisherRequest);

				MessageBusUtil.sendMessage(
					DestinationNames.MESSAGE_BUS_MESSAGE_STATUS, messageStatus);
			}
		}
	}

}