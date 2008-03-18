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
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutServiceUtil;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import java.io.ByteArrayInputStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;

import javax.portlet.ActionRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="StagingUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
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

		Map parameterMap = getStagingParameters();

		String scope = ParamUtil.getString(req, "scope");

		if (scope.equals("all-pages")) {
			copyLayouts(
				stagingGroup.getLiveGroupId(), stagingGroup.getGroupId(),
				privateLayout, parameterMap);
		}
		else if (scope.equals("selected-pages")) {
			Map layoutIdMap = new LinkedHashMap();

			long[] rowIds = ParamUtil.getLongValues(req, "rowIds");

			for (int i = 0; i < rowIds.length; i++) {
				long selPlid = rowIds[i];
				boolean includeChildren = ParamUtil.getBoolean(
					req, "includeChildren_" + selPlid);

				layoutIdMap.put(
					new Long(selPlid), new Boolean(includeChildren));
			}

			publishLayouts(
				layoutIdMap, stagingGroup.getLiveGroupId(),
				stagingGroup.getGroupId(), privateLayout, parameterMap);
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

	public static void copyLayouts(
			long sourceGroupId, long targetGroupId, boolean privateLayout,
			Map parameterMap)
		throws Exception {

		byte[] data = LayoutServiceUtil.exportLayouts(
			sourceGroupId, privateLayout, parameterMap);

		ByteArrayInputStream bais = new ByteArrayInputStream(data);

		LayoutServiceUtil.importLayouts(
			targetGroupId, privateLayout, parameterMap, bais);
	}

	public static void copyPortlet(
			ActionRequest req, long sourcePlid, long targetPlid,
			String portletId)
		throws Exception {

		Map parameterMap = getStagingParameters(req);

		byte[] data = LayoutLocalServiceUtil.exportPortletInfo(
			sourcePlid, portletId, parameterMap);

		ByteArrayInputStream bais = new ByteArrayInputStream(data);

		LayoutServiceUtil.importPortletInfo(
			targetPlid, portletId, parameterMap, bais);
	}

	public static List getMissingParents(Layout layout, long liveGroupId)
		throws PortalException, SystemException {

		List missingParents = new ArrayList();

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

	public static Map getStagingParameters(ActionRequest req) {
		Map parameterMap = new LinkedHashMap(req.getParameterMap());

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL, Boolean.FALSE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.THEME, Boolean.FALSE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			Boolean.FALSE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			PortletDataHandlerKeys.DATA_STRATEGY_MIRROR);
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			UserIdStrategy.CURRENT_USER_ID);

		return parameterMap;
	}

	public static Map getStagingParameters() {
		Map parameterMap = new LinkedHashMap();

		parameterMap.put(
			PortletDataHandlerKeys.PERMISSIONS, Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.USER_PERMISSIONS, Boolean.FALSE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA, Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL, Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP, Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
			Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.THEME, Boolean.FALSE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			Boolean.TRUE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.DELETE_PORTLET_DATA,
			Boolean.FALSE.toString());
		parameterMap.put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			PortletDataHandlerKeys.DATA_STRATEGY_MIRROR);
		parameterMap.put(
			PortletDataHandlerKeys.USER_ID_STRATEGY,
			UserIdStrategy.CURRENT_USER_ID);

		return parameterMap;
	}

	public static void publishLayout(
			long plid, long liveGroupId, boolean includeChildren)
		throws Exception {

		Map parameterMap = getStagingParameters();

		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			Boolean.FALSE.toString());

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		List layouts = new ArrayList();

		layouts.add(layout);

		layouts.addAll(getMissingParents(layout, liveGroupId));

		if (includeChildren) {
			layouts.addAll(layout.getAllChildren());
		}

		Iterator itr = layouts.iterator();

		long[] layoutIds = new long[layouts.size()];

		for (int i = 0; itr.hasNext(); i++) {
			Layout curLayout = (Layout)itr.next();

			layoutIds[i] = curLayout.getLayoutId();
		}

		byte[] data = LayoutServiceUtil.exportLayouts(
			layout.getGroupId(), layout.isPrivateLayout(), layoutIds,
			parameterMap);

		ByteArrayInputStream bais = new ByteArrayInputStream(data);

		LayoutServiceUtil.importLayouts(
			liveGroupId, layout.isPrivateLayout(), parameterMap, bais);
	}

	public static void publishLayouts(
			Map layoutIdMap, long stagingGroupId, long liveGroupId,
			boolean privateLayout, Map parameterMap)
		throws Exception {

		parameterMap.put(
			PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS,
			Boolean.FALSE.toString());

		List layouts = new ArrayList();

		Iterator itr1 = layoutIdMap.entrySet().iterator();

		while (itr1.hasNext()) {
			Entry entry = (Entry)itr1.next();

			long plid = ((Long)entry.getKey()).longValue();
			boolean includeChildren =
				((Boolean)entry.getValue()).booleanValue();

			Layout layout = LayoutLocalServiceUtil.getLayout(plid);

			if (!layouts.contains(layout)) {
				layouts.add(layout);
			}

			Iterator itr2 = getMissingParents(layout, liveGroupId).iterator();

			while (itr2.hasNext()) {
				Layout parentLayout = (Layout)itr2.next();

				if (!layouts.contains(parentLayout)) {
					layouts.add(parentLayout);
				}
			}

			if (includeChildren) {
				itr2 = layout.getAllChildren().iterator();

				while (itr2.hasNext()) {
					Layout childLayout = (Layout)itr2.next();

					if (!layouts.contains(childLayout)) {
						layouts.add(childLayout);
					}
				}
			}
		}

		itr1 = layouts.iterator();

		long[] layoutIds = new long[layouts.size()];

		for (int i = 0; itr1.hasNext(); i++) {
			Layout curLayout = (Layout)itr1.next();

			layoutIds[i] = curLayout.getLayoutId();
		}

		byte[] data = LayoutServiceUtil.exportLayouts(
			stagingGroupId, privateLayout, layoutIds, parameterMap);

		ByteArrayInputStream bais = new ByteArrayInputStream(data);

		LayoutServiceUtil.importLayouts(
			liveGroupId, privateLayout, parameterMap, bais);
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

		Map parameterMap = getStagingParameters(req);

		if (scope.equals("all-pages")) {
			copyLayouts(
				stagingGroup.getGroupId(), stagingGroup.getLiveGroupId(),
				privateLayout, parameterMap);
		}
		else if (scope.equals("selected-pages")) {
			Map layoutIdMap = new LinkedHashMap();

			long[] rowIds = ParamUtil.getLongValues(req, "rowIds");

			for (int i = 0; i < rowIds.length; i++) {
				long selPlid = rowIds[i];
				boolean includeChildren = ParamUtil.getBoolean(
					req, "includeChildren_" + selPlid);

				layoutIdMap.put(
					new Long(selPlid), new Boolean(includeChildren));
			}

			publishLayouts(
				layoutIdMap, stagingGroup.getGroupId(),
				stagingGroup.getLiveGroupId(), privateLayout, parameterMap);
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
				Map parameterMap = getStagingParameters();

				copyLayouts(
					liveGroup.getGroupId(), stagingGroup.getGroupId(), true,
					parameterMap);
			}

			if (liveGroup.hasPublicLayouts()) {
				Map parameterMap = getStagingParameters();

				copyLayouts(
					liveGroup.getGroupId(), stagingGroup.getGroupId(), false,
					parameterMap);
			}
		}
	}

	private static Log _log = LogFactory.getLog(StagingUtil.class);

}