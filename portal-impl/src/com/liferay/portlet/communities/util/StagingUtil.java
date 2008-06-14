/**
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

package com.liferay.portlet.communities.util;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.cal.DayAndPosition;
import com.liferay.portal.kernel.cal.Duration;
import com.liferay.portal.kernel.cal.Recurrence;
import com.liferay.portal.kernel.cal.RecurrenceSerializer;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.GroupImpl;
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
import com.liferay.portal.service.http.LayoutServiceHttp;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;

import java.io.ByteArrayInputStream;

import java.net.InetAddress;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="StagingUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 * @author Bruno Farache
 *
 */
public class StagingUtil {

	public static void copyFromLive(ActionRequest req) throws Exception {
		String tabs1 = ParamUtil.getString(req, "tabs1");

		long stagingGroupId = ParamUtil.getLong(req, "stagingGroupId");

		Group stagingGroup = GroupLocalServiceUtil.getGroup(stagingGroupId);

		boolean privateLayout = true;

		if (tabs1.equals("public-pages")) {
			privateLayout = false;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Copying live to staging for group " +
					stagingGroup.getLiveGroupId());
		}

		Map<String, String[]> parameterMap = getStagingParameters();

		String scope = ParamUtil.getString(req, "scope");

		if (scope.equals("all-pages")) {
			publishLayouts(
				stagingGroup.getLiveGroupId(), stagingGroup.getGroupId(),
				privateLayout, parameterMap);
		}
		else if (scope.equals("selected-pages")) {
			Map<Long, Boolean> layoutIdMap = new LinkedHashMap<Long, Boolean>();

			long[] rowIds = ParamUtil.getLongValues(req, "rowIds");

			for (long selPlid : rowIds) {
				boolean includeChildren = ParamUtil.getBoolean(
					req, "includeChildren_" + selPlid);

				layoutIdMap.put(
					new Long(selPlid), new Boolean(includeChildren));
			}

			publishLayouts(
				stagingGroup.getLiveGroupId(), stagingGroup.getGroupId(),
				privateLayout, layoutIdMap, parameterMap);
		}
	}

	public static void copyFromLive(ActionRequest req, Portlet portlet)
		throws Exception {

		long plid = ParamUtil.getLong(req, "plid");

		Layout targetLayout = LayoutLocalServiceUtil.getLayout(plid);

		Group stagingGroup = targetLayout.getGroup();
		Group liveGroup = stagingGroup.getLiveGroup();

		Layout sourceLayout = LayoutLocalServiceUtil.getLayout(
			liveGroup.getGroupId(), targetLayout.isPrivateLayout(),
			targetLayout.getLayoutId());

		copyPortlet(
			req, sourceLayout.getPlid(), targetLayout.getPlid(),
			portlet.getPortletId());
	}

	public static void copyPortlet(
			ActionRequest req, long sourcePlid, long targetPlid,
			String portletId)
		throws Exception {

		Map<String, String[]> parameterMap = getStagingParameters(req);

		byte[] bytes = LayoutLocalServiceUtil.exportPortletInfo(
			sourcePlid, portletId, parameterMap, null, null);

		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

		LayoutServiceUtil.importPortletInfo(
			targetPlid, portletId, parameterMap, bais);
	}

	public static void copyRemoteLayouts(
			long sourceGroupId, String remoteAddress, int remotePort,
			boolean secure, long remoteGroupId, boolean privateLayout,
			Map<String, String[]> exportParameterMap,
			Map<String, String[]> importParameterMap, Date startDate,
			Date endDate)
		throws Exception {

		InetAddress inetAddress = InetAddress.getByName(remoteAddress);

		if (!inetAddress.isReachable(800)) {
			throw new SystemException(
				"Host " + inetAddress.getHostName() + " cannot be reached");
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		User user = UserLocalServiceUtil.getUser(permissionChecker.getUserId());

		StringMaker sm = new StringMaker();

		if (secure) {
			sm.append(Http.HTTPS_WITH_SLASH);
		}
		else {
			sm.append(Http.HTTP_WITH_SLASH);
		}

		sm.append(inetAddress.getHostAddress());
		sm.append(StringPool.COLON);
		sm.append(remotePort);

		HttpPrincipal httpPrincipal = new HttpPrincipal(
			sm.toString(), user.getUserId(), user.getPassword(),
			user.getPasswordEncrypted());

		byte[] bytes = LayoutServiceUtil.exportLayouts(
			sourceGroupId, privateLayout, exportParameterMap, startDate,
			endDate);

		LayoutServiceHttp.importLayouts(
			httpPrincipal, remoteGroupId, privateLayout, importParameterMap,
			bytes);
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

	public static Map<String, String[]> getStagingParameters(
		ActionRequest req) {

		Map<String, String[]> parameterMap =
			new LinkedHashMap<String, String[]>(req.getParameterMap());

		if (!parameterMap.containsKey(
				PortletDataHandlerKeys.PORTLET_DATA)) {

			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA,
				new String[] {Boolean.FALSE.toString()});
		}

		if (!parameterMap.containsKey(
				PortletDataHandlerKeys.PORTLET_DATA_ALL)) {

			parameterMap.put(
				PortletDataHandlerKeys.PORTLET_DATA_ALL,
				new String[] {Boolean.FALSE.toString()});
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

		if (!parameterMap.containsKey(PortletDataHandlerKeys.DATA_STRATEGY)) {
			parameterMap.put(
				PortletDataHandlerKeys.DATA_STRATEGY,
				new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR});
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

		byte[] bytes = LayoutServiceUtil.exportLayouts(
			layout.getGroupId(), layout.isPrivateLayout(), layoutIds,
			parameterMap, null, null);

		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

		LayoutServiceUtil.importLayouts(
			liveGroupId, layout.isPrivateLayout(), parameterMap, bais);
	}

	public static Map<String, String[]> getStagingParameters() {
		Map<String, String[]> parameterMap =
			new LinkedHashMap<String, String[]>();

		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.USER_PERMISSIONS,
			new String[] {Boolean.FALSE.toString()});
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
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR});
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			new String[] {UserIdStrategy.CURRENT_USER_ID});

		return parameterMap;
	}

	public static void publishLayouts(
			long sourceGroupId, long targetGroupId, boolean privateLayout,
			Map<String, String[]> parameterMap)
		throws Exception {

		byte[] bytes = LayoutServiceUtil.exportLayouts(
			sourceGroupId, privateLayout, parameterMap, null, null);

		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

		LayoutServiceUtil.importLayouts(
			targetGroupId, privateLayout, parameterMap, bais);
	}

	public static void publishLayouts(
			long sourceGroupId, long targetGroupId, boolean privateLayout,
			Map<Long, Boolean> layoutIdMap, Map<String, String[]> parameterMap)
		throws Exception {

		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			new String[] {Boolean.FALSE.toString()});

		List<Layout> layouts = new ArrayList<Layout>();

		Iterator<Map.Entry<Long, Boolean>> itr1 =
			layoutIdMap.entrySet().iterator();

		while (itr1.hasNext()) {
			Entry<Long, Boolean> entry = itr1.next();

			long plid = entry.getKey();
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

		byte[] bytes = LayoutServiceUtil.exportLayouts(
			sourceGroupId, privateLayout, layoutIds, parameterMap, null, null);

		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

		LayoutServiceUtil.importLayouts(
			targetGroupId, privateLayout, parameterMap, bais);
	}

	public static void publishToLive(ActionRequest req) throws Exception {
		String tabs1 = ParamUtil.getString(req, "tabs1");

		long stagingGroupId = ParamUtil.getLong(req, "stagingGroupId");

		Group stagingGroup = GroupLocalServiceUtil.getGroup(stagingGroupId);

		boolean privateLayout = true;

		if (tabs1.equals("public-pages")) {
			privateLayout = false;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Copying staging to live for group " +
					stagingGroup.getLiveGroupId());
		}

		String scope = ParamUtil.getString(req, "scope");

		Map<String, String[]> parameterMap = getStagingParameters(req);

		if (scope.equals("all-pages")) {
			publishLayouts(
				stagingGroup.getGroupId(), stagingGroup.getLiveGroupId(),
				privateLayout, parameterMap);
		}
		else if (scope.equals("selected-pages")) {
			Map<Long, Boolean> layoutIdMap = new LinkedHashMap<Long, Boolean>();

			long[] rowIds = ParamUtil.getLongValues(req, "rowIds");

			for (long selPlid : rowIds) {
				boolean includeChildren = ParamUtil.getBoolean(
					req, "includeChildren_" + selPlid);

				layoutIdMap.put(selPlid, includeChildren);
			}

			publishLayouts(
				stagingGroup.getGroupId(), stagingGroup.getLiveGroupId(),
				privateLayout, layoutIdMap, parameterMap);
		}
	}

	public static void publishToLive(ActionRequest req, Portlet portlet)
		throws Exception {

		long plid = ParamUtil.getLong(req, "plid");

		Layout sourceLayout = LayoutLocalServiceUtil.getLayout(plid);

		Group stagingGroup = sourceLayout.getGroup();
		Group liveGroup = stagingGroup.getLiveGroup();

		Layout targetLayout = LayoutLocalServiceUtil.getLayout(
			liveGroup.getGroupId(), sourceLayout.isPrivateLayout(),
			sourceLayout.getLayoutId());

		copyPortlet(
			req, sourceLayout.getPlid(), targetLayout.getPlid(),
			portlet.getPortletId());
	}

	public static void schedulePublishToLive(ActionRequest req)
		throws Exception {

		String tabs1 = ParamUtil.getString(req, "tabs1");

		long stagingGroupId = ParamUtil.getLong(req, "stagingGroupId");

		Group stagingGroup = GroupLocalServiceUtil.getGroup(stagingGroupId);

		long liveGroupId = stagingGroup.getLiveGroupId();

		boolean privateLayout = true;

		if (tabs1.equals("public-pages")) {
			privateLayout = false;
		}

		String scope = ParamUtil.getString(req, "scope");

		Map<Long, Boolean> layoutIdMap = new LinkedHashMap<Long, Boolean>();

		if (scope.equals("selected-pages")) {
			long[] rowIds = ParamUtil.getLongValues(req, "rowIds");

			for (long selPlid : rowIds) {
				boolean includeChildren = ParamUtil.getBoolean(
					req, "includeChildren_" + selPlid);

				layoutIdMap.put(selPlid, includeChildren);
			}
		}

		Map<String, String[]> parameterMap = getStagingParameters(req);

		boolean timeZoneSensitive = ParamUtil.getBoolean(
			req, "timeZoneSensitive");

		int recurrenceType = ParamUtil.getInteger(req, "recurrenceType");

		Calendar startCal = _getDate(req, "startDate", timeZoneSensitive);

		String cronText = _getCronText(
			req, startCal, timeZoneSensitive, recurrenceType);

		Date endDate = null;

		int endDateType = ParamUtil.getInteger(req, "endDateType");

		if (endDateType == 1) {
			Calendar endCal = _getDate(req, "endDate", timeZoneSensitive);

			endDate = endCal.getTime();
		}

		String description = ParamUtil.getString(req, "description");

		LayoutServiceUtil.schedulePublishToLive(
			stagingGroupId, liveGroupId, privateLayout, layoutIdMap,
			parameterMap, scope, cronText, startCal.getTime(), endDate,
			description);
	}

	public static void unschedulePublishToLive(ActionRequest req)
		throws Exception {

		long stagingGroupId = ParamUtil.getLong(req, "stagingGroupId");

		Group stagingGroup = GroupLocalServiceUtil.getGroup(stagingGroupId);

		long liveGroupId = stagingGroup.getLiveGroupId();

		String jobName = ParamUtil.getString(req, "jobName");

		LayoutServiceUtil.unschedulePublishToLive(liveGroupId, jobName);
	}

	public static void updateStaging(ActionRequest req) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		long liveGroupId = ParamUtil.getLong(req, "liveGroupId");

		if (!GroupPermissionUtil.contains(
				permissionChecker, liveGroupId, ActionKeys.MANAGE_STAGING)) {

			throw new PrincipalException();
		}

		long stagingGroupId = ParamUtil.getLong(req, "stagingGroupId");

		boolean stagingEnabled = ParamUtil.getBoolean(req, "stagingEnabled");

		if ((stagingGroupId > 0) && !stagingEnabled) {
			GroupServiceUtil.deleteGroup(stagingGroupId);

			GroupServiceUtil.updateWorkflow(liveGroupId, false, 0, null);
		}
		else if ((stagingGroupId == 0) && stagingEnabled) {
			Group liveGroup = GroupServiceUtil.getGroup(liveGroupId);

			Group stagingGroup = GroupServiceUtil.addGroup(
				liveGroup.getGroupId(), liveGroup.getName() + " (Staging)",
				liveGroup.getDescription(), GroupImpl.TYPE_COMMUNITY_PRIVATE,
				null, liveGroup.isActive());

			if (liveGroup.hasPrivateLayouts()) {
				Map<String, String[]> parameterMap = getStagingParameters();

				publishLayouts(
					liveGroup.getGroupId(), stagingGroup.getGroupId(), true,
					parameterMap);
			}

			if (liveGroup.hasPublicLayouts()) {
				Map<String, String[]> parameterMap = getStagingParameters();

				publishLayouts(
					liveGroup.getGroupId(), stagingGroup.getGroupId(), false,
					parameterMap);
			}
		}
	}

	private static void _addWeeklyDayPos(
		ActionRequest req, List<DayAndPosition> list, int day) {

		if (ParamUtil.getBoolean(req, "weeklyDayPos" + day)) {
			list.add(new DayAndPosition(day, 0));
		}
	}

	private static String _getCronText(
			ActionRequest req, Calendar startDate, boolean timeZoneSensitive,
			int recurrenceType)
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
			int dailyType = ParamUtil.getInteger(req, "dailyType");

			if (dailyType == 0) {
				int dailyInterval = ParamUtil.getInteger(req, "dailyInterval");

				// LEP-3468

				if (dailyInterval <= 0) {
					dailyInterval = 1;
				}

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
				req, "weeklyInterval", 1);

			recurrence.setInterval(weeklyInterval);

			List<DayAndPosition> dayPos = new ArrayList<DayAndPosition>();

			_addWeeklyDayPos(req, dayPos, Calendar.SUNDAY);
			_addWeeklyDayPos(req, dayPos, Calendar.MONDAY);
			_addWeeklyDayPos(req, dayPos, Calendar.TUESDAY);
			_addWeeklyDayPos(req, dayPos, Calendar.WEDNESDAY);
			_addWeeklyDayPos(req, dayPos, Calendar.THURSDAY);
			_addWeeklyDayPos(req, dayPos, Calendar.FRIDAY);
			_addWeeklyDayPos(req, dayPos, Calendar.SATURDAY);

			if (dayPos.size() == 0) {
				dayPos.add(new DayAndPosition(Calendar.MONDAY, 0));
			}

			recurrence.setByDay(dayPos.toArray(new DayAndPosition[0]));
		}
		else if (recurrenceType == Recurrence.MONTHLY) {
			int monthlyType = ParamUtil.getInteger(req, "monthlyType");

			if (monthlyType == 0) {
				int monthlyDay = ParamUtil.getInteger(req, "monthlyDay0", 1);

				recurrence.setByMonthDay(new int[] {monthlyDay});

				int monthlyInterval = ParamUtil.getInteger(
					req, "monthlyInterval0", 1);

				recurrence.setInterval(monthlyInterval);
			}
			else {
				int monthlyPos = ParamUtil.getInteger(req, "monthlyPos");
				int monthlyDay = ParamUtil.getInteger(req, "monthlyDay1");

				DayAndPosition[] dayPos = {
					new DayAndPosition(monthlyDay, monthlyPos)};

				recurrence.setByDay(dayPos);

				int monthlyInterval = ParamUtil.getInteger(
					req, "monthlyInterval1", 1);

				recurrence.setInterval(monthlyInterval);
			}
		}
		else if (recurrenceType == Recurrence.YEARLY) {
			int yearlyType = ParamUtil.getInteger(req, "yearlyType");

			if (yearlyType == 0) {
				int yearlyMonth = ParamUtil.getInteger(req, "yearlyMonth0");
				int yearlyDay = ParamUtil.getInteger(req, "yearlyDay0", 1);

				recurrence.setByMonth(new int[] {yearlyMonth});
				recurrence.setByMonthDay(new int[] {yearlyDay});

				int yearlyInterval = ParamUtil.getInteger(
					req, "yearlyInterval0", 1);

				recurrence.setInterval(yearlyInterval);
			}
			else {
				int yearlyPos = ParamUtil.getInteger(req, "yearlyPos");
				int yearlyDay = ParamUtil.getInteger(req, "yearlyDay1");
				int yearlyMonth = ParamUtil.getInteger(req, "yearlyMonth1");

				DayAndPosition[] dayPos = {
					new DayAndPosition(yearlyDay, yearlyPos)};

				recurrence.setByDay(dayPos);

				recurrence.setByMonth(new int[] {yearlyMonth});

				int yearlyInterval = ParamUtil.getInteger(
					req, "yearlyInterval1", 1);

				recurrence.setInterval(yearlyInterval);
			}
		}

		return RecurrenceSerializer.toCronText(recurrence);
	}

	private static Calendar _getDate(
			ActionRequest req, String paramPrefix, boolean timeZoneSensitive)
		throws Exception {

		int dateMonth = ParamUtil.getInteger(req, paramPrefix + "Month");
		int dateDay = ParamUtil.getInteger(req, paramPrefix + "Day");
		int dateYear = ParamUtil.getInteger(req, paramPrefix + "Year");
		int dateHour = ParamUtil.getInteger(req, paramPrefix + "Hour");
		int dateMinute = ParamUtil.getInteger(req, paramPrefix + "Minute");
		int dateAmPm = ParamUtil.getInteger(req, paramPrefix + "AmPm");

		if (dateAmPm == Calendar.PM) {
			dateHour += 12;
		}

		Locale locale = null;
		TimeZone timeZone = null;

		if (timeZoneSensitive) {
			User user = PortalUtil.getUser(req);

			locale = user.getLocale();
			timeZone = user.getTimeZone();
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

	private static Log _log = LogFactory.getLog(StagingUtil.class);

}