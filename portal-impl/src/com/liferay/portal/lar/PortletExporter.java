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

package com.liferay.portal.lar;

import com.liferay.portal.LayoutImportException;
import com.liferay.portal.NoSuchPortletPreferencesException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.service.PortletItemLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.util.DocumentUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.util.MapUtil;
import com.liferay.util.xml.XMLFormatter;

import java.io.IOException;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * <a href="PortletExporter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Joel Kozikowski
 * @author Charles May
 * @author Raymond Augé
 * @author Jorge Ferrer
 * @author Bruno Farache
 *
 */
public class PortletExporter {

	public byte[] exportPortletInfo(
			long plid, String portletId, Map<String, String[]> parameterMap,
			Date startDate, Date endDate)
		throws PortalException, SystemException {

		boolean exportPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PERMISSIONS);
		boolean exportUserPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.USER_PERMISSIONS);
		boolean exportPortletData = true;
		boolean exportPortletSetup = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_SETUP);
		boolean exportPortletArchivedSetups = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS);
		boolean exportPortletUserPreferences = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_USER_PREFERENCES);

		if (_log.isDebugEnabled()) {
			_log.debug("Export permissions " + exportPermissions);
			_log.debug("Export user permissions " + exportUserPermissions);
			_log.debug("Export portlet data " + exportPortletData);
			_log.debug("Export portlet setup " + exportPortletSetup);
			_log.debug(
				"Export portlet archived setups " +
					exportPortletArchivedSetups);
			_log.debug(
				"Export portlet user preferences " +
					exportPortletUserPreferences);
		}

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		LayoutCache layoutCache = new LayoutCache();

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		if (!layout.getType().equals(LayoutConstants.TYPE_PORTLET)) {
			throw new LayoutImportException(
				"Layout type " + layout.getType() + " is not valid");
		}

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		if (!layoutTypePortlet.hasPortletId(portletId)) {
			throw new LayoutImportException(
				"The specified layout does not have portlet " + portletId);
		}

		long companyId = layout.getCompanyId();
		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		ZipWriter zipWriter = new ZipWriter();

		PortletDataContext context = new PortletDataContextImpl(
			companyId, layout.getGroupId(), parameterMap, new HashSet(),
			startDate, endDate, zipWriter);

		context.setPlid(plid);

		// Build compatibility

		Document doc = DocumentHelper.createDocument();

		Element root = doc.addElement("root");

		Element header = root.addElement("header");

		header.addAttribute(
			"build-number", String.valueOf(ReleaseInfo.getBuildNumber()));
		header.addAttribute("export-date", Time.getRFC822());

		if (context.hasDateRange()) {
			header.addAttribute(
				"start-date", String.valueOf(context.getStartDate()));
			header.addAttribute(
				"end-date", String.valueOf(context.getEndDate()));
		}

		header.addAttribute("type", "portlet");
		header.addAttribute("group-id", String.valueOf(layout.getGroupId()));
		header.addAttribute(
			"private-layout", String.valueOf(layout.isPrivateLayout()));
		header.addAttribute(
			"root-portlet-id", PortletConstants.getRootPortletId(portletId));

		// Portlet

		exportPortlet(
			context, layoutCache, portletId, layout, root, defaultUserId,
			exportPortletData, exportPortletSetup, exportPortletArchivedSetups,
			exportPortletUserPreferences, exportPermissions,
			exportUserPermissions);

		// Comments

		exportComments(context, root);

		// Ratings

		exportRatings(context, root);

		// Tags

		exportTags(context, root);

		// Log

		if (_log.isInfoEnabled()) {
			_log.info("Exporting portlet took " + stopWatch.getTime() + " ms");
		}

		// Zip

		try {
			context.addZipEntry(
				"/manifest.xml", XMLFormatter.toString(doc));

			return zipWriter.finish();
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected void exportComments(PortletDataContext context, Element parentEl)
		throws SystemException {

		try {
			Document doc = DocumentHelper.createDocument();

			Element root = doc.addElement("comments");

			Map<String, List> commentsMap = context.getComments();

			for (Map.Entry<String, List> entry : commentsMap.entrySet()) {
				String[] comment = entry.getKey().split(StringPool.POUND);

				String path = getCommentPath(context, comment[0], comment[1]);

				Element asset = root.addElement("asset");
				asset.addAttribute("path", path);
				asset.addAttribute("class-name", comment[0]);
				asset.addAttribute("class-pk", comment[1]);

				List<MBMessage> messages = entry.getValue();

				for (MBMessage message : messages) {
					path = getCommentPath(
						context, comment[0], comment[1], message);

					context.addZipEntry(path, message);
				}
			}

			context.addZipEntry(
				context.getRootPath() + "/comments.xml",
				XMLFormatter.toString(doc));
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected Element exportGroupPermissions(
			long companyId, long groupId, String resourceName,
			String resourcePrimKey, Element parentEl, String elName)
		throws SystemException {

		Element el = parentEl.addElement(elName);

		List<Permission> permissions =
			PermissionLocalServiceUtil.getGroupPermissions(
				groupId, companyId, resourceName,
				ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey);

		List<String> actions = ResourceActionsUtil.getActions(permissions);

		for (int i = 0; i < actions.size(); i++) {
			String action = actions.get(i);

			Element actionKeyEl = el.addElement("action-key");

			actionKeyEl.addText(action);
		}

		return el;
	}

	protected void exportGroupRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String entityName, Element parentEl)
		throws PortalException, SystemException {

		List<Role> roles = layoutCache.getGroupRoles(groupId);

		Element groupEl = exportRoles(
			companyId, resourceName, ResourceConstants.SCOPE_GROUP,
			String.valueOf(groupId), parentEl, entityName + "-roles", roles);

		if (groupEl.elements().isEmpty()) {
			parentEl.remove(groupEl);
		}
	}

	protected void exportInheritedPermissions(
			LayoutCache layoutCache, long companyId, String resourceName,
			String resourcePrimKey, Element parentEl, String entityName)
		throws SystemException {

		Element entityPermissionsEl = DocumentHelper.createElement(
			entityName + "-permissions");

		Map<String, Long> entityMap = layoutCache.getEntityMap(
			companyId, entityName);

		Iterator<Map.Entry<String, Long>> itr = entityMap.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, Long> entry = itr.next();

			String name = entry.getKey().toString();

			long entityGroupId = entry.getValue();

			Element entityEl = exportGroupPermissions(
				companyId, entityGroupId, resourceName, resourcePrimKey,
				entityPermissionsEl, entityName + "-actions");

			if (entityEl.elements().isEmpty()) {
				entityPermissionsEl.remove(entityEl);
			}
			else {
				entityEl.addAttribute("name", name);
			}
		}

		if (!entityPermissionsEl.elements().isEmpty()) {
			parentEl.add(entityPermissionsEl);
		}
	}

	protected void exportInheritedRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String entityName, Element parentEl)
		throws PortalException, SystemException {

		Element entityRolesEl = DocumentHelper.createElement(
			entityName + "-roles");

		Map<String, Long> entityMap = layoutCache.getEntityMap(
			companyId, entityName);

		Iterator<Map.Entry<String, Long>> itr = entityMap.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, Long> entry = itr.next();

			String name = entry.getKey().toString();

			long entityGroupId = entry.getValue();

			List<Role> entityRoles = layoutCache.getGroupRoles(entityGroupId);

			Element entityEl = exportRoles(
				companyId, resourceName, ResourceConstants.SCOPE_GROUP,
				String.valueOf(groupId), entityRolesEl, entityName,
				entityRoles);

			if (entityEl.elements().isEmpty()) {
				entityRolesEl.remove(entityEl);
			}
			else {
				entityEl.addAttribute("name", name);
			}
		}

		if (!entityRolesEl.elements().isEmpty()) {
			parentEl.add(entityRolesEl);
		}
	}

	protected void exportPortlet(
			PortletDataContext context, LayoutCache layoutCache,
			String portletId, Layout layout, Element parentEl,
			long defaultUserId, boolean exportPortletData,
			boolean exportPortletSetup, boolean exportPortletArchivedSetups,
			boolean exportPortletUserPreferences, boolean exportPermissions,
			boolean exportUserPermissions)
		throws PortalException, SystemException {

		long companyId = context.getCompanyId();
		long groupId = context.getGroupId();

		Document portletDoc = DocumentHelper.createDocument();

		Element portletEl = portletDoc.addElement("portlet");

		portletEl.addAttribute("portlet-id", portletId);
		portletEl.addAttribute(
			"root-portlet-id", PortletConstants.getRootPortletId(portletId));

		// Data

		javax.portlet.PortletPreferences jxPrefs =
			PortletPreferencesFactoryUtil.getPortletSetup(
				layout, portletId, StringPool.BLANK);

		if (exportPortletData) {
			exportPortletData(context, portletId, jxPrefs, portletEl);
		}

		// Portlet preferences

		if (exportPortletSetup) {
			exportPortletPreferences(
				context, PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, false, layout, portletId,
				portletEl);

			exportPortletPreferences(
				context, groupId, PortletKeys.PREFS_OWNER_TYPE_GROUP, false,
				layout, portletId, portletEl);

			exportPortletPreferences(
				context, companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY, false,
				layout, portletId, portletEl);
		}

		// Portlet preferences

		if (exportPortletUserPreferences) {
			exportPortletPreferences(
				context, defaultUserId, PortletKeys.PREFS_OWNER_TYPE_USER,
				true, layout, portletId, portletEl);

			try {
				PortletPreferences groupPortletPreferences =
					PortletPreferencesLocalServiceUtil.getPortletPreferences(
						groupId, PortletKeys.PREFS_OWNER_TYPE_GROUP,
						PortletKeys.PREFS_PLID_SHARED, portletId);

				exportPortletPreference(
					context, groupId, PortletKeys.PREFS_OWNER_TYPE_GROUP, false,
					groupPortletPreferences, portletId,
					PortletKeys.PREFS_PLID_SHARED, portletEl);
			}
			catch (NoSuchPortletPreferencesException nsppe) {
			}
		}

		// Archived setups

		if (exportPortletArchivedSetups) {
			String rootPortletId = PortletConstants.getRootPortletId(portletId);

			List<PortletItem> portletItems =
				PortletItemLocalServiceUtil.getPortletItems(
					groupId, rootPortletId, PortletPreferences.class.getName());

			for (PortletItem portletItem: portletItems) {
				long ownerId = portletItem.getPortletItemId();
				int ownerType = PortletKeys.PREFS_OWNER_TYPE_ARCHIVED;

				exportPortletPreferences(
					context, ownerId, ownerType, false, null,
					portletItem.getPortletId(), portletEl);
			}
		}

		Group guestGroup = GroupLocalServiceUtil.getGroup(
			companyId, GroupImpl.GUEST);

		// Permissions

		if (exportPermissions) {
			Element permissionsEl = portletEl.addElement("permissions");

			exportPortletPermissions(
				layoutCache, companyId, groupId, guestGroup, layout.getPlid(),
				portletId, permissionsEl, exportUserPermissions);

			Element rolesEl = portletEl.addElement("roles");

			exportPortletRoles(
				layoutCache, companyId, groupId, portletId, rolesEl);
		}

		// Zip

		String portletPath = context.getPortletPath(portletId) + "/portlet.xml";

		Element el = parentEl.addElement("portlet");
		el.addAttribute("portlet-id", portletId);
		el.addAttribute(
			"layout-id", String.valueOf(layout.getLayoutId()));
		el.addAttribute("path", portletPath);

		try {
			context.addZipEntry(portletPath, XMLFormatter.toString(portletDoc));
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected void exportPortletData(
			PortletDataContext context, String portletId,
			javax.portlet.PortletPreferences portletPreferences,
			Element parentEl)
		throws PortalException, SystemException {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			context.getCompanyId(), portletId);

		if (portlet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not export portlet data for " + portletId +
						" because the portlet does not exist");
			}

			return;
		}

		PortletDataHandler portletDataHandler =
			portlet.getPortletDataHandlerInstance();

		if (portletDataHandler == null) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Exporting data for " + portletId);
		}

		Map parameterMap = context.getParameterMap();

		boolean exportData = false;

		if (MapUtil.getBoolean(
				parameterMap,
				PortletDataHandlerKeys.PORTLET_DATA + "_" +
					portlet.getRootPortletId()) ||
			MapUtil.getBoolean(
				parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL)) {

			exportData = true;
		}

		if (!exportData) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Not exporting data for " + portletId +
						" because it was not selected by the user");
			}

			return;
		}

		String data = portletDataHandler.exportData(
			context, portletId, portletPreferences);

		if (data == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Not exporting data for " + portletId +
						" because null data was returned");
			}

			return;
		}

		String portletDataPath = getPortletDataPath(context, portletId);

		parentEl.addElement("portlet-data").addAttribute(
			"path", portletDataPath);

		context.addZipEntry(portletDataPath, data);
	}

	protected void exportPortletPermissions(
			LayoutCache layoutCache, long companyId, long groupId,
			Group guestGroup, long plid, String portletId,
			Element permissionsEl, boolean exportUserPermissions)
		throws PortalException, SystemException {

		String resourceName = PortletConstants.getRootPortletId(portletId);
		String resourcePrimKey = PortletPermissionUtil.getPrimaryKey(
			plid, portletId);

		exportGroupPermissions(
			companyId, groupId, resourceName, resourcePrimKey, permissionsEl,
			"community-actions");

		if (groupId != guestGroup.getGroupId()) {
			exportGroupPermissions(
				companyId, guestGroup.getGroupId(), resourceName,
				resourcePrimKey, permissionsEl, "guest-actions");
		}

		if (exportUserPermissions) {
			exportUserPermissions(
				layoutCache, companyId, groupId, resourceName, resourcePrimKey,
				permissionsEl);
		}

		exportInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsEl, "organization");

		exportInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsEl, "location");

		exportInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsEl, "user-group");
	}

	protected void exportPortletPreference(
			PortletDataContext context, long ownerId, int ownerType,
			boolean defaultUser, PortletPreferences portletPreferences,
			String portletId, long plid, Element parentEl)
		throws SystemException {

		try {
			Document prefsDoc = DocumentUtil.readDocumentFromXML(
				portletPreferences.getPreferences());

			Element root = prefsDoc.getRootElement();

			root.addAttribute("owner-id", String.valueOf(ownerId));
			root.addAttribute("owner-type", String.valueOf(ownerType));
			root.addAttribute("default-user", String.valueOf(defaultUser));
			root.addAttribute("plid", String.valueOf(plid));
			root.addAttribute("portlet-id", portletId);

			if (ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) {
				PortletItem portletItem =
					PortletItemLocalServiceUtil.getPortletItem(ownerId);

				User user = UserLocalServiceUtil.getUserById(
					portletItem.getUserId());

				root.addAttribute("archive-user-uuid", user.getUuid());
				root.addAttribute("archive-name", portletItem.getName());
			}

			String path = getPortletPreferencesPath(
				context, portletId, ownerId, ownerType, plid);

			parentEl.addElement("portlet-preference").addAttribute("path", path);

			if (context.isPathNotProcessed(path)) {
				context.addZipEntry(
					path, XMLFormatter.toString(prefsDoc));
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected void exportPortletPreferences(
			PortletDataContext context, long ownerId, int ownerType,
			boolean defaultUser, Layout layout, String portletId,
			Element parentEl)
		throws PortalException, SystemException {

		PortletPreferences portletPreferences = null;

		long plid = PortletKeys.PREFS_OWNER_ID_DEFAULT;

		if (layout != null) {
			plid = layout.getPlid();
		}

		if ((ownerType == PortletKeys.PREFS_OWNER_TYPE_COMPANY) ||
			(ownerType == PortletKeys.PREFS_OWNER_TYPE_GROUP) ||
			(ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED)) {

			plid = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		}

		try {
			portletPreferences =
				PortletPreferencesLocalServiceUtil.getPortletPreferences(
					ownerId, ownerType, plid, portletId);

			LayoutTypePortlet layoutTypePortlet = null;

			if (layout != null) {
				layoutTypePortlet = (LayoutTypePortlet)layout.getLayoutType();
			}

			if ((layoutTypePortlet == null) ||
				(layoutTypePortlet.hasPortletId(portletId))) {

				exportPortletPreference(
					context, ownerId, ownerType, defaultUser,
					portletPreferences, portletId, plid, parentEl);
			}
		}
		catch (NoSuchPortletPreferencesException nsppe) {
		}
	}

	protected void exportPortletRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String portletId, Element rolesEl)
		throws PortalException, SystemException {

		String resourceName = PortletConstants.getRootPortletId(
			portletId);

		Element portletEl = rolesEl.addElement("portlet");

		portletEl.addAttribute("portlet-id", portletId);

		exportGroupRoles(
			layoutCache, companyId, groupId, resourceName, "community",
			portletEl);

		exportUserRoles(
			layoutCache, companyId, groupId, resourceName, portletEl);

		exportInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "organization",
			portletEl);

		exportInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "location",
			portletEl);

		exportInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "user-group",
			portletEl);

		if (portletEl.elements().isEmpty()) {
			rolesEl.remove(portletEl);
		}
	}

	protected void exportRatings(PortletDataContext context, Element parentEl)
		throws SystemException {

		try {
			Document doc = DocumentHelper.createDocument();

			Element root = doc.addElement("ratings");

			Map<String, List> ratingsEntriesMap = context.getRatingsEntries();

			for (Map.Entry<String, List> entry : ratingsEntriesMap.entrySet()) {
				String[] ratingsEntry = entry.getKey().split(StringPool.POUND);

				String ratingPath = getRatingPath(
					context, ratingsEntry[0], ratingsEntry[1]);

				Element asset = root.addElement("asset");
				asset.addAttribute("path", ratingPath);
				asset.addAttribute("class-name", ratingsEntry[0]);
				asset.addAttribute("class-pk", ratingsEntry[1]);

				List<RatingsEntry> ratingsEntries = entry.getValue();

				for (RatingsEntry rating : ratingsEntries) {
					ratingPath = getRatingPath(
						context, ratingsEntry[0], ratingsEntry[1], rating);

					context.addZipEntry(ratingPath, rating);
				}
			}

			context.addZipEntry(
				context.getRootPath() + "/ratings.xml",
				XMLFormatter.toString(doc));
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected Element exportRoles(
			long companyId, String resourceName, int scope,
			String resourcePrimKey, Element parentEl, String elName,
			List<Role> roles)
		throws SystemException {

		Element el = parentEl.addElement(elName);

		Map<String, List<String>> resourceRoles =
			RoleLocalServiceUtil.getResourceRoles(
				companyId, resourceName, scope, resourcePrimKey);

		Iterator<Map.Entry<String, List<String>>> itr =
			resourceRoles.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry<String, List<String>> entry = itr.next();

			String roleName = entry.getKey().toString();

			if (hasRole(roles, roleName)) {
				Element roleEl = el.addElement("role");

				roleEl.addAttribute("name", roleName);

				List<String> actions = entry.getValue();

				for (int i = 0; i < actions.size(); i++) {
					String action = actions.get(i);

					Element actionKeyEl = roleEl.addElement("action-key");

					actionKeyEl.addText(action);
					actionKeyEl.addAttribute("scope", String.valueOf(scope));
				}
			}
		}

		return el;
	}

	protected void exportTags(PortletDataContext context, Element parentEl)
		throws SystemException {

		try {
			Document doc = DocumentHelper.createDocument();

			Element root = doc.addElement("tags");

			Map<String, String[]> tagsEntries = context.getTagsEntries();

			for (Map.Entry<String, String[]> entry : tagsEntries.entrySet()) {
				String[] tagsEntry = entry.getKey().split(StringPool.POUND);

				Element asset = root.addElement("asset");
				asset.addAttribute("class-name", tagsEntry[0]);
				asset.addAttribute("class-pk", tagsEntry[1]);
				asset.addAttribute(
					"entries", StringUtil.merge(entry.getValue(), ","));
			}

			context.addZipEntry(
				context.getRootPath() + "/tags.xml",
				XMLFormatter.toString(doc));
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected void exportUserPermissions(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String resourcePrimKey, Element parentEl)
		throws PortalException, SystemException {

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		Element userPermissionsEl = DocumentHelper.createElement(
			"user-permissions");

		List<User> users = layoutCache.getGroupUsers(groupId);

		for (User user : users) {
			String emailAddress = user.getEmailAddress();

			Element userActionsEl =
				DocumentHelper.createElement("user-actions");

			List<Permission> permissions =
				PermissionLocalServiceUtil.getUserPermissions(
					user.getUserId(), companyId, resourceName,
					ResourceConstants.SCOPE_INDIVIDUAL, resourcePrimKey);

			List<String> actions = ResourceActionsUtil.getActions(permissions);

			for (String action : actions) {
				Element actionKeyEl = userActionsEl.addElement("action-key");

				actionKeyEl.addText(action);
			}

			if (!userActionsEl.elements().isEmpty()) {
				userActionsEl.addAttribute("email-address", emailAddress);
				userPermissionsEl.add(userActionsEl);
			}
		}

		if (!userPermissionsEl.elements().isEmpty()) {
			parentEl.add(userPermissionsEl);
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Export user permissions for {" + resourceName + ", " +
					resourcePrimKey + "} with " + users.size() +
						" users takes " + stopWatch.getTime() + " ms");
		}
	}

	protected void exportUserRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, Element parentEl)
		throws PortalException, SystemException {

		Element userRolesEl = DocumentHelper.createElement("user-roles");

		List<User> users = layoutCache.getGroupUsers(groupId);

		for (User user : users) {
			long userId = user.getUserId();
			String emailAddress = user.getEmailAddress();

			List<Role> userRoles = layoutCache.getUserRoles(userId);

			Element userEl = exportRoles(
				companyId, resourceName, ResourceConstants.SCOPE_GROUP,
				String.valueOf(groupId), userRolesEl, "user", userRoles);

			if (userEl.elements().isEmpty()) {
				userRolesEl.remove(userEl);
			}
			else {
				userEl.addAttribute("email-address", emailAddress);
			}
		}

		if (!userRolesEl.elements().isEmpty()) {
			parentEl.add(userRolesEl);
		}
	}

	protected String getCommentPath(
			PortletDataContext context, String className, String classPK) {

		StringMaker sm = new StringMaker();
		sm.append(context.getRootPath());
		sm.append("/comments/");
		sm.append(PortalUtil.getClassNameId(className));
		sm.append(CharPool.FORWARD_SLASH);
		sm.append(classPK);
		sm.append(CharPool.FORWARD_SLASH);

		return sm.toString();
	}

	protected String getCommentPath(
			PortletDataContext context, String className, String classPK,
			MBMessage message) {

		StringMaker sm = new StringMaker();
		sm.append(context.getRootPath());
		sm.append("/comments/");
		sm.append(PortalUtil.getClassNameId(className));
		sm.append(CharPool.FORWARD_SLASH);
		sm.append(classPK);
		sm.append(CharPool.FORWARD_SLASH);
		sm.append(message.getMessageId());
		sm.append(".xml");

		return sm.toString();
	}

	protected String getRatingPath(
			PortletDataContext context, String className, String classPK) {

		StringMaker sm = new StringMaker();
		sm.append(context.getRootPath());
		sm.append("/ratings/");
		sm.append(PortalUtil.getClassNameId(className));
		sm.append(CharPool.FORWARD_SLASH);
		sm.append(classPK);
		sm.append(CharPool.FORWARD_SLASH);

		return sm.toString();
	}

	protected String getRatingPath(
			PortletDataContext context, String className, String classPK,
			RatingsEntry rating) {

		StringMaker sm = new StringMaker();
		sm.append(context.getRootPath());
		sm.append("/ratings/");
		sm.append(PortalUtil.getClassNameId(className));
		sm.append(CharPool.FORWARD_SLASH);
		sm.append(classPK);
		sm.append(CharPool.FORWARD_SLASH);
		sm.append(rating.getEntryId());
		sm.append(".xml");

		return sm.toString();
	}

	protected String getPortletDataPath(
			PortletDataContext context, String portletId) {

		return context.getPortletPath(portletId) + "/portlet-data.xml";
	}

	protected String getPortletPreferencesPath(
			PortletDataContext context, String portletId, long ownerId,
			int ownerType, long plid) {
		StringMaker preferencePath = new StringMaker();
		preferencePath.append(context.getPortletPath(portletId));
		preferencePath.append("/preferences/");

		switch(ownerType) {
			case PortletKeys.PREFS_OWNER_TYPE_COMPANY:
				preferencePath.append("company/");
				break;
			case PortletKeys.PREFS_OWNER_TYPE_GROUP:
				preferencePath.append("group/");
				break;
			case PortletKeys.PREFS_OWNER_TYPE_LAYOUT:
				preferencePath.append("layout/");
				break;
			case PortletKeys.PREFS_OWNER_TYPE_USER:
				preferencePath.append("user/");
				break;
			case PortletKeys.PREFS_OWNER_TYPE_ARCHIVED:
				preferencePath.append("archived/");
				break;
		}

		preferencePath.append(ownerId);
		preferencePath.append(CharPool.FORWARD_SLASH);
		preferencePath.append(plid);
		preferencePath.append(CharPool.FORWARD_SLASH);
		preferencePath.append("portlet-preferences.xml");

		return preferencePath.toString();
	}

	protected boolean hasRole(List<Role> roles, String roleName) {
		if ((roles == null) || (roles.size() == 0)) {
			return false;
		}

		for (int i = 0; i < roles.size(); i++) {
			Role role = roles.get(i);

			if (role.getName().equals(roleName)) {
				return true;
			}
		}

		return false;
	}

	private static Log _log = LogFactory.getLog(PortletExporter.class);

}