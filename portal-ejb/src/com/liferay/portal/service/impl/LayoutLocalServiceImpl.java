/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.LayoutFriendlyURLException;
import com.liferay.portal.LayoutHiddenException;
import com.liferay.portal.LayoutImportException;
import com.liferay.portal.LayoutNameException;
import com.liferay.portal.LayoutParentLayoutIdException;
import com.liferay.portal.LayoutTypeException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.NoSuchPortletPreferencesException;
import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.PortalException;
import com.liferay.portal.RequiredLayoutException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutReference;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.OrganizationImpl;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.base.LayoutLocalServiceBaseImpl;
import com.liferay.portal.service.permission.PortletPermission;
import com.liferay.portal.service.persistence.LayoutFinder;
import com.liferay.portal.service.persistence.LayoutPK;
import com.liferay.portal.service.persistence.LayoutUtil;
import com.liferay.portal.service.persistence.PortletPreferencesPK;
import com.liferay.portal.service.persistence.PortletPreferencesUtil;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.ReleaseInfo;
import com.liferay.portal.util.comparator.LayoutPriorityComparator;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesSerializer;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.util.CollectionFactory;
import com.liferay.util.GetterUtil;
import com.liferay.util.InstancePool;
import com.liferay.util.LocaleUtil;
import com.liferay.util.Time;
import com.liferay.util.Validator;
import com.liferay.util.dao.hibernate.QueryUtil;
import com.liferay.util.xml.XMLFormatter;
import com.liferay.util.zip.ZipReader;
import com.liferay.util.zip.ZipWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="LayoutLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Joel Kozikowski
 * @author Charles May
 *
 */
public class LayoutLocalServiceImpl extends LayoutLocalServiceBaseImpl {

	public Layout addLayout(
			long groupId, String userId, boolean privateLayout,
			String parentLayoutId, String name, String title, String type,
			boolean hidden, String friendlyURL)
		throws PortalException, SystemException {

		// Layout

		String ownerId = null;

		if (privateLayout) {
			ownerId = LayoutImpl.PRIVATE + groupId;
		}
		else {
			ownerId = LayoutImpl.PUBLIC + groupId;
		}

		String layoutId = getNextLayoutId(ownerId);
		User user = UserUtil.findByPrimaryKey(userId);
		parentLayoutId = getParentLayoutId(ownerId, parentLayoutId);
		int priority = getNextPriority(ownerId, parentLayoutId);

		validate(
			layoutId, ownerId, parentLayoutId, name, type, hidden, friendlyURL);

		Layout layout = LayoutUtil.create(new LayoutPK(layoutId, ownerId));

		layout.setCompanyId(user.getActualCompanyId());
		layout.setParentLayoutId(parentLayoutId);
		layout.setName(name, null);
		layout.setTitle(title, null);
		layout.setType(type);
		layout.setHidden(hidden);
		layout.setFriendlyURL(friendlyURL);
		layout.setPriority(priority);

		LayoutUtil.update(layout);

		// Resources

		ResourceLocalServiceUtil.addResources(
			user.getActualCompanyId(), groupId, user.getUserId(),
			Layout.class.getName(), layout.getPrimaryKey().toString(), false,
			true, true);

		// Layout set

		LayoutSetLocalServiceUtil.updatePageCount(ownerId);

		return layout;
	}

	public void deleteLayout(String layoutId, String ownerId)
		throws PortalException, SystemException {

		Layout layout = LayoutUtil.findByPrimaryKey(
			new LayoutPK(layoutId, ownerId));

		deleteLayout(layout, true);
	}

	public void deleteLayout(Layout layout, boolean updateLayoutSet)
		throws PortalException, SystemException {

		// Child layouts

		List childLayouts = LayoutUtil.findByO_P(
			layout.getOwnerId(), layout.getLayoutId());

		for (int i = 0; i < childLayouts.size(); i++) {
			Layout childLayout = (Layout)childLayouts.get(i);

			deleteLayout(childLayout, updateLayoutSet);
		}

		// Portlet preferences

		PortletPreferencesLocalServiceUtil.deletePortletPreferences(
			layout.getLayoutId(), layout.getOwnerId());

		// Journal content searches

		JournalContentSearchLocalServiceUtil.deleteLayoutContentSearches(
			layout.getLayoutId(), layout.getOwnerId());

		// Icon

		ImageLocalUtil.remove(layout.getIconImageId());

		// Resources

		Iterator itr = ResourceFinder.findByC_P(
			layout.getCompanyId(), layout.getPlid() +
				PortletImpl.LAYOUT_SEPARATOR + "%").iterator();

		while (itr.hasNext()) {
			Resource resource = (Resource)itr.next();

			ResourceLocalServiceUtil.deleteResource(resource);
		}

		ResourceLocalServiceUtil.deleteResource(
			layout.getCompanyId(), Layout.class.getName(),
			ResourceImpl.SCOPE_INDIVIDUAL, layout.getPrimaryKey().toString());

		// Layout

		LayoutUtil.remove(layout.getPrimaryKey());

		// Layout set

		if (updateLayoutSet) {
			LayoutSetLocalServiceUtil.updatePageCount(layout.getOwnerId());
		}
	}

	public void deleteLayouts(String ownerId)
		throws PortalException, SystemException {

		// Layouts

		Iterator itr = LayoutUtil.findByO_P(
			ownerId, LayoutImpl.DEFAULT_PARENT_LAYOUT_ID).iterator();

		while (itr.hasNext()) {
			Layout layout = (Layout)itr.next();

			try {
				deleteLayout(layout, false);
			}
			catch (NoSuchLayoutException nsle) {
			}
		}

		// Layout set

		LayoutSetLocalServiceUtil.updatePageCount(ownerId);
	}

	public byte[] exportLayouts(String ownerId)
		throws PortalException, SystemException {

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(ownerId);

		String companyId = layoutSet.getCompanyId();
		long groupId = layoutSet.getGroupId();

		PortletDataContext context = new PortletDataContext(
			companyId, groupId, CollectionFactory.getHashSet());

		Group guestGroup = GroupLocalServiceUtil.getGroup(
			companyId, GroupImpl.GUEST);

		String guestPrefsOwnerId = null;

		if (!layoutSet.isPrivateLayout()) {
			guestPrefsOwnerId =
				ownerId + StringPool.PERIOD + PortletKeys.PREFS_OWNER_ID_USER +
					StringPool.PERIOD + UserImpl.getDefaultUserId(companyId);
		}

		// Build compatibility

		Document doc = DocumentHelper.createDocument();

		Element root = doc.addElement("root");

		Element header = root.addElement("header");

		header.addAttribute(
			"build-number", String.valueOf(ReleaseInfo.getBuildNumber()));
		header.addAttribute("owner-id", ownerId);
		header.addAttribute("export-date", Time.getRFC822());
		header.addAttribute("theme-id", layoutSet.getThemeId());
		header.addAttribute("color-scheme-id", layoutSet.getColorSchemeId());

		// Layouts

		Set portletIds = new LinkedHashSet();

		Iterator itr1 = getLayouts(ownerId).iterator();

		while (itr1.hasNext()) {
			Layout layout = (Layout)itr1.next();

			Element layoutEl = root.addElement("layout");

			layoutEl.addAttribute("layout-id", layout.getLayoutId());
			layoutEl.addElement("parent-layout-id").addText(
				layout.getParentLayoutId());
			layoutEl.addElement("name").addCDATA(layout.getName());
			layoutEl.addElement("title").addCDATA(layout.getTitle());
			layoutEl.addElement("type").addText(layout.getType());
			layoutEl.addElement("type-settings").addCDATA(
				layout.getTypeSettings());
			layoutEl.addElement("hidden").addText(
				String.valueOf(layout.getHidden()));
			layoutEl.addElement("friendly-url").addText(
				layout.getFriendlyURL());
			layoutEl.addElement("theme-id").addText(layout.getThemeId());
			layoutEl.addElement("color-scheme-id").addText(
				layout.getColorSchemeId());
			layoutEl.addElement("priority").addText(
				String.valueOf(layout.getPriority()));

			// Layout permissions

			String resourceName = Layout.class.getName();
			String resourcePrimKey = layout.getPrimaryKey().toString();

			Element permissionsEl = layoutEl.addElement("permissions");

			exportGroupPermissions(
				companyId, groupId, resourceName, resourcePrimKey,
				permissionsEl, "community-actions");

			if (groupId != guestGroup.getGroupId()) {
				exportGroupPermissions(
					companyId, guestGroup.getGroupId(), resourceName,
					resourcePrimKey, permissionsEl, "guest-actions");
			}

			exportUserPermissions(
				companyId, groupId, resourceName, resourcePrimKey,
				permissionsEl);

			exportInheritedPermissions(
				companyId, resourceName, resourcePrimKey, permissionsEl,
				"organization");

			exportInheritedPermissions(
				companyId, resourceName, resourcePrimKey, permissionsEl,
				"location");

			exportInheritedPermissions(
				companyId, resourceName, resourcePrimKey, permissionsEl,
				"user-group");

			if (layout.getType().equals(LayoutImpl.TYPE_PORTLET)) {
				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				// Portlet permissions

				Iterator itr2 = layoutTypePortlet.getPortletIds().iterator();

				while (itr2.hasNext()) {
					String portletId = (String)itr2.next();

					if (!portletIds.contains(portletId)) {
						portletIds.add(portletId);
					}

					if (layoutTypePortlet.hasPortletId(portletId)) {
						resourceName = PortletImpl.getRootPortletId(portletId);
						resourcePrimKey = PortletPermission.getPrimaryKey(
							layout.getPlid(), portletId);

						Element portletEl = permissionsEl.addElement("portlet");

						portletEl.addAttribute("portlet-id", portletId);

						exportGroupPermissions(
							companyId, groupId, resourceName, resourcePrimKey,
							portletEl, "community-actions");

						if (groupId != guestGroup.getGroupId()) {
							exportGroupPermissions(
								companyId, guestGroup.getGroupId(),
								resourceName, resourcePrimKey, portletEl,
								"guest-actions");
						}

						exportUserPermissions(
							companyId, groupId, resourceName, resourcePrimKey,
							portletEl);

						exportInheritedPermissions(
							companyId, resourceName, resourcePrimKey, portletEl,
							"organization");

						exportInheritedPermissions(
							companyId, resourceName, resourcePrimKey, portletEl,
							"location");

						exportInheritedPermissions(
							companyId, resourceName, resourcePrimKey, portletEl,
							"user-group");
					}
				}

				// Portlet preferences

				exportPortletPreferences(
					layout.getLayoutId(), ownerId, layoutTypePortlet, layoutEl);

				if (guestPrefsOwnerId != null) {
					exportPortletPreferences(
						layout.getLayoutId(), guestPrefsOwnerId,
						layoutTypePortlet, layoutEl);
				}

				// Portlet data

				exportPortletData(context, layout, layoutTypePortlet, layoutEl);
			}
		}

		// Layout roles

		Element rolesEl = root.addElement("roles");

		String resourceName = Layout.class.getName();

		exportGroupRoles(
			companyId, groupId, resourceName, "community", rolesEl);

		exportUserRoles(companyId, groupId, resourceName, rolesEl);

		exportInheritedRoles(
			companyId, groupId, resourceName, "organization", rolesEl);

		exportInheritedRoles(
			companyId, groupId, resourceName, "location", rolesEl);

		exportInheritedRoles(
			companyId, groupId, resourceName, "user-group", rolesEl);

		// Portlet roles

		itr1 = portletIds.iterator();

		while (itr1.hasNext()) {
			String portletId = (String)itr1.next();

			resourceName = PortletImpl.getRootPortletId(portletId);

			Element portletEl = rolesEl.addElement("portlet");

			portletEl.addAttribute("portlet-id", portletId);

			exportGroupRoles(
				companyId, groupId, resourceName, "community", portletEl);

			exportUserRoles(companyId, groupId, resourceName, portletEl);

			exportInheritedRoles(
				companyId, groupId, resourceName, "organization", portletEl);

			exportInheritedRoles(
				companyId, groupId, resourceName, "location", portletEl);

			exportInheritedRoles(
				companyId, groupId, resourceName, "user-group", portletEl);

			if (portletEl.elements().isEmpty()) {
				rolesEl.remove(portletEl);
			}
		}

		// Portlet preferences

		String groupPrefsOwnerId =
			PortletKeys.PREFS_OWNER_ID_GROUP + StringPool.PERIOD + groupId;

		exportPortletPreferences(
			PortletKeys.PREFS_LAYOUT_ID_SHARED, groupPrefsOwnerId, root);

		// XML file

		try {
			ZipWriter zipWriter = new ZipWriter();

			zipWriter.addEntry("layouts.xml", XMLFormatter.toString(doc));

			return zipWriter.finish();
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public Layout getFriendlyURLLayout(String ownerId, String friendlyURL)
		throws PortalException, SystemException {

		if (Validator.isNull(friendlyURL)) {
			throw new NoSuchLayoutException();
		}

		return LayoutUtil.findByO_F(ownerId, friendlyURL);
	}

	public Layout getLayout(String plid)
		throws PortalException, SystemException {

		String layoutId = LayoutImpl.getLayoutId(plid);
		String ownerId = LayoutImpl.getOwnerId(plid);

		return getLayout(layoutId, ownerId);
	}

	public Layout getLayout(String layoutId, String ownerId)
		throws PortalException, SystemException {

		return LayoutUtil.findByPrimaryKey(new LayoutPK(layoutId, ownerId));
	}

	public List getLayouts(String ownerId) throws SystemException {
		return LayoutUtil.findByOwnerId(ownerId);
	}

	public List getLayouts(String ownerId, String parentLayoutId)
		throws SystemException {

		return LayoutUtil.findByO_P(ownerId, parentLayoutId);
	}

	public LayoutReference[] getLayouts(
			String companyId, String portletId, String prefsKey,
			String prefsValue)
		throws SystemException {

		List list = LayoutFinder.findByC_P_P(
			companyId, portletId, prefsKey, prefsValue);

		return (LayoutReference[])list.toArray(new LayoutReference[0]);
	}

	public void importLayouts(String userId, String ownerId, File file)
		throws PortalException, SystemException {

		try {
			importLayouts(userId, ownerId, new FileInputStream(file));
		}
		catch (FileNotFoundException fnfe) {
			throw new SystemException(fnfe);
		}
	}

	public void importLayouts(String userId, String ownerId, InputStream is)
		throws PortalException, SystemException {

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(ownerId);

		String companyId = layoutSet.getCompanyId();
		long groupId = layoutSet.getGroupId();

		PortletDataContext context = new PortletDataContext(
			companyId, groupId, CollectionFactory.getHashSet());

		Group guestGroup = GroupLocalServiceUtil.getGroup(
			companyId, GroupImpl.GUEST);

		// XML

		Element root = null;

		try {
			ZipReader zipReader = new ZipReader(is);

			String xml = zipReader.getEntryAsString("layouts.xml");

			SAXReader reader = new SAXReader();

			Document doc = reader.read(new StringReader(xml));

			root = doc.getRootElement();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}

		// Build compatibility

		Element header = (Element)root.element("header");

		int buildNumber = ReleaseInfo.getBuildNumber();

		int importBuildNumber = GetterUtil.getInteger(
			header.attributeValue("build-number"));

		if (buildNumber != importBuildNumber) {
			throw new LayoutImportException(
				"LAR build number " + importBuildNumber + " does not match " +
					"portal build number " + buildNumber);
		}

		// Look and feel

		String themeId = header.attributeValue("theme-id");
		String colorSchemeId = header.attributeValue("color-scheme-id");

		LayoutSetLocalServiceUtil.updateLookAndFeel(
			ownerId, themeId, colorSchemeId, StringPool.BLANK);

		// Layouts

		User user = UserUtil.findByPrimaryKey(userId);

		List newLayoutPKs = new ArrayList();

		Iterator itr1 = root.elements("layout").iterator();

		while (itr1.hasNext()) {
			Element layoutEl = (Element)itr1.next();

			String layoutId = layoutEl.attributeValue("layout-id");
			String parentLayoutId = layoutEl.elementText(
				"parent-layout-id");
			String name = layoutEl.elementText("name");
			String title = layoutEl.elementText("title");
			String type = layoutEl.elementText("type");
			String typeSettings = layoutEl.elementText("type-settings");
			boolean hidden = GetterUtil.getBoolean(
				layoutEl.elementText("hidden"));
			String friendlyURL = layoutEl.elementText("friendly-url");
			themeId = layoutEl.elementText("theme-id");
			colorSchemeId = layoutEl.elementText("color-scheme-id");
			int priority = GetterUtil.getInteger(
				layoutEl.elementText("priority"));

			LayoutPK layoutPK = new LayoutPK(layoutId, ownerId);

			newLayoutPKs.add(layoutPK);

			Layout layout = LayoutUtil.fetchByPrimaryKey(layoutPK);

			if (layout == null) {
				layout = LayoutUtil.create(layoutPK);
			}

			layout.setCompanyId(user.getActualCompanyId());
			layout.setParentLayoutId(parentLayoutId);
			layout.setName(name);
			layout.setTitle(title);
			layout.setType(type);
			layout.setTypeSettings(typeSettings);
			layout.setHidden(hidden);
			layout.setFriendlyURL(friendlyURL);
			layout.setThemeId(themeId);
			layout.setColorSchemeId(colorSchemeId);
			layout.setPriority(priority);

			fixTypeSettings(layout);

			LayoutUtil.update(layout);

			// Layout permissions

			String resourceName = Layout.class.getName();
			String resourcePrimKey = layout.getPrimaryKey().toString();

			Element permissionsEl = layoutEl.element("permissions");

			importGroupPermissions(
				companyId, groupId, resourceName, resourcePrimKey,
				permissionsEl, "community-actions", false);

			if (groupId != guestGroup.getGroupId()) {
				importGroupPermissions(
					companyId, guestGroup.getGroupId(), resourceName,
					resourcePrimKey, permissionsEl, "guest-actions", false);
			}

			importUserPermissions(
				companyId, groupId, resourceName, resourcePrimKey,
				permissionsEl, false);

			importInheritedPermissions(
				companyId, resourceName, resourcePrimKey, permissionsEl,
				"organization", false);

			importInheritedPermissions(
				companyId, resourceName, resourcePrimKey, permissionsEl,
				"location", false);

			importInheritedPermissions(
				companyId, resourceName, resourcePrimKey, permissionsEl,
				"user-group", false);

			// Portlet permissions

			Iterator itr2 = permissionsEl.elements("portlet").iterator();

			while (itr2.hasNext()) {
				Element portletEl = (Element)itr2.next();

				String portletId = portletEl.attributeValue("portlet-id");

				resourceName = PortletImpl.getRootPortletId(portletId);
				resourcePrimKey = PortletPermission.getPrimaryKey(
					layout.getPlid(), portletId);

				Portlet portlet = PortletLocalServiceUtil.getPortletById(
					companyId, resourceName);

				if (portlet == null) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Do not import portlet permissions for " +
								portletId +
									" because the portlet does not exist");
					}
				}
				else {
					importGroupPermissions(
						companyId, groupId, resourceName, resourcePrimKey,
						portletEl, "community-actions", true);

					if (groupId != guestGroup.getGroupId()) {
						importGroupPermissions(
							companyId, guestGroup.getGroupId(), resourceName,
							resourcePrimKey, portletEl, "guest-actions", true);
					}

					importUserPermissions(
						companyId, groupId, resourceName, resourcePrimKey,
						portletEl, true);

					importInheritedPermissions(
						companyId, resourceName, resourcePrimKey, portletEl,
						"organization", true);

					importInheritedPermissions(
						companyId, resourceName, resourcePrimKey, portletEl,
						"location", true);

					importInheritedPermissions(
						companyId, resourceName, resourcePrimKey, portletEl,
						"user-group", true);
				}
			}

			// Portlet preferences

			importPortletPreferences(layoutSet, layoutEl);

			// Portlet data

			importPortletData(context, layout, layoutEl);
		}

		// Layout roles

		Element rolesEl = (Element)root.element("roles");

		String resourceName = Layout.class.getName();

		importGroupRoles(
			companyId, groupId, resourceName, "community", rolesEl);

		importUserRoles(
			companyId, groupId, resourceName, rolesEl);

		importInheritedRoles(
			companyId, groupId, resourceName, "organization", rolesEl);

		importInheritedRoles(
			companyId, groupId, resourceName, "location", rolesEl);

		importInheritedRoles(
			companyId, groupId, resourceName, "user-group", rolesEl);

		// Portlet roles

		itr1 = rolesEl.elements("portlet").iterator();

		while (itr1.hasNext()) {
			Element portletEl = (Element)itr1.next();

			String portletId = portletEl.attributeValue("portlet-id");

			resourceName = PortletImpl.getRootPortletId(portletId);

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				companyId, resourceName);

			if (portlet == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Do not import portlet roles for " + portletId +
							" because the portlet does not exist");
				}
			}
			else {
				importGroupRoles(
					companyId, groupId, resourceName, "community", portletEl);

				importUserRoles(companyId, groupId, resourceName, portletEl);

				importInheritedRoles(
					companyId, groupId, resourceName, "organization",
					portletEl);

				importInheritedRoles(
					companyId, groupId, resourceName, "location", portletEl);

				importInheritedRoles(
					companyId, groupId, resourceName, "user-group", portletEl);
			}
		}

		// Portlet preferences

		importPortletPreferences(layoutSet, root);

		// Delete missing layouts

		deleteMissingLayouts(ownerId, newLayoutPKs);

		// Page count

		LayoutSetLocalServiceUtil.updatePageCount(ownerId);
	}

	public void setLayouts(
			String ownerId, String parentLayoutId, String[] layoutIds)
		throws PortalException, SystemException {

		if (layoutIds == null) {
			return;
		}

		if (parentLayoutId.equals(LayoutImpl.DEFAULT_PARENT_LAYOUT_ID)) {
			if (layoutIds.length < 1) {
				throw new RequiredLayoutException(
					RequiredLayoutException.AT_LEAST_ONE);
			}

			Layout layout = LayoutUtil.findByPrimaryKey(
				new LayoutPK(layoutIds[0], ownerId));

			if (!layout.getType().equals(LayoutImpl.TYPE_PORTLET)) {
				throw new RequiredLayoutException(
					RequiredLayoutException.FIRST_LAYOUT_TYPE);
			}

			if (layout.isHidden()) {
				throw new RequiredLayoutException(
					RequiredLayoutException.FIRST_LAYOUT_HIDDEN);
			}
		}

		Set layoutIdsSet = new LinkedHashSet();

		for (int i = 0; i < layoutIds.length; i++) {
			layoutIdsSet.add(layoutIds[i]);
		}

		Set newLayoutIdsSet = CollectionFactory.getHashSet();

		Iterator itr = LayoutUtil.findByO_P(ownerId, parentLayoutId).iterator();

		while (itr.hasNext()) {
			Layout layout = (Layout)itr.next();

			if (!layoutIdsSet.contains(layout.getLayoutId())) {
				deleteLayout(layout, true);
			}
			else {
				newLayoutIdsSet.add(layout.getLayoutId());
			}
		}

		int priority = 0;

		itr = layoutIdsSet.iterator();

		while (itr.hasNext()) {
			String layoutId = (String)itr.next();

			Layout layout = LayoutUtil.findByPrimaryKey(
				new LayoutPK(layoutId, ownerId));

			layout.setPriority(priority++);

			LayoutUtil.update(layout);
		}

		LayoutSetLocalServiceUtil.updatePageCount(ownerId);
	}

	public Layout updateLayout(
			String layoutId, String ownerId, String parentLayoutId, String name,
			String title, String languageId, String type, boolean hidden,
			String friendlyURL)
		throws PortalException, SystemException {

		return updateLayout(
			layoutId, ownerId, parentLayoutId, name, title, languageId, type,
			hidden, friendlyURL, null, null);
	}

	public Layout updateLayout(
			String layoutId, String ownerId, String parentLayoutId, String name,
			String title, String languageId, String type, boolean hidden,
			String friendlyURL, Boolean iconImage, byte[] iconBytes)
		throws PortalException, SystemException {

		// Layout

		parentLayoutId = getParentLayoutId(ownerId, parentLayoutId);
		Locale locale = LocaleUtil.fromLanguageId(languageId);

		validate(
			layoutId, ownerId, parentLayoutId, name, type, hidden, friendlyURL);

		validateParentLayoutId(layoutId, ownerId, parentLayoutId);

		Layout layout = LayoutUtil.findByPrimaryKey(
			new LayoutPK(layoutId, ownerId));

		if (!parentLayoutId.equals(layout.getParentLayoutId())) {
			layout.setPriority(getNextPriority(ownerId, parentLayoutId));
		}

		layout.setParentLayoutId(parentLayoutId);
		layout.setName(name, locale);
		layout.setTitle(title, locale);
		layout.setType(type);
		layout.setHidden(hidden);
		layout.setFriendlyURL(friendlyURL);

		if (iconImage != null) {
			layout.setIconImage(iconImage.booleanValue());
		}

		LayoutUtil.update(layout);

		// Icon

		if (iconImage != null) {
			if (!iconImage.booleanValue()) {
				ImageLocalUtil.remove(layout.getIconImageId());
			}
			else if ((iconBytes != null) && (iconBytes.length > 0)) {
				ImageLocalUtil.put(layout.getIconImageId(), iconBytes);
			}
		}

		return layout;
	}

	public Layout updateLayout(
			String layoutId, String ownerId, String typeSettings)
		throws PortalException, SystemException {

		Layout layout = LayoutUtil.findByPrimaryKey(
			new LayoutPK(layoutId, ownerId));

		layout.setTypeSettings(typeSettings);

		LayoutUtil.update(layout);

		return layout;
	}

	public Layout updateLookAndFeel(
			String layoutId, String ownerId, String themeId,
			String colorSchemeId, String css)
		throws PortalException, SystemException {

		Layout layout = LayoutUtil.findByPrimaryKey(
			new LayoutPK(layoutId, ownerId));

		layout.setThemeId(themeId);
		layout.setColorSchemeId(colorSchemeId);
		layout.setCss(css);

		LayoutUtil.update(layout);

		return layout;
	}

	public Layout updateName(
			String layoutId, String ownerId, String name, String languageId)
		throws PortalException, SystemException {

		Layout layout = LayoutUtil.findByPrimaryKey(
			new LayoutPK(layoutId, ownerId));

		layout.setName(name, LocaleUtil.fromLanguageId(languageId));

		LayoutUtil.update(layout);

		return layout;
	}

	public Layout updatePriority(String layoutId, String ownerId, int priority)
		throws PortalException, SystemException {

		Layout layout = LayoutUtil.findByPrimaryKey(
			new LayoutPK(layoutId, ownerId));

		if (layout.getPriority() == priority) {
			return layout;
		}

		boolean lessThan = false;

		if (layout.getPriority() < priority) {
			lessThan = true;
		}

		layout.setPriority(priority);

		LayoutUtil.update(layout);

		priority = 0;

		List layouts = LayoutUtil.findByO_P(
			ownerId, layout.getParentLayoutId());

		Collections.sort(
			layouts, new LayoutPriorityComparator(layout, lessThan));

		Iterator itr = layouts.iterator();

		while (itr.hasNext()) {
			Layout curLayout = (Layout)itr.next();

			curLayout.setPriority(priority++);

			LayoutUtil.update(curLayout);

			if (curLayout.equals(layout)) {
				layout = curLayout;
			}
		}

		return layout;
	}

	protected void deleteMissingLayouts(String ownerId, List newLayoutPKs)
		throws PortalException, SystemException {

		// Layouts

		Iterator itr = LayoutUtil.findByO_P(
			ownerId, LayoutImpl.DEFAULT_PARENT_LAYOUT_ID).iterator();

		while (itr.hasNext()) {
			Layout layout = (Layout)itr.next();

			if (!newLayoutPKs.contains(layout.getPrimaryKey())) {
				try {
					deleteLayout(layout, false);
				}
				catch (NoSuchLayoutException nsle) {
				}
			}
		}

		// Layout set

		LayoutSetLocalServiceUtil.updatePageCount(ownerId);
	}

	protected Element exportGroupPermissions(
			String companyId, long groupId, String resourceName,
			String resourcePrimKey, Element parentEl, String elName)
		throws PortalException, SystemException {

		Element el = parentEl.addElement(elName);

		try {
			Resource resource = ResourceLocalServiceUtil.getResource(
				companyId, resourceName, ResourceImpl.SCOPE_INDIVIDUAL,
				resourcePrimKey);

			List permissions = PermissionLocalServiceUtil.getGroupPermissions(
				groupId, resource.getResourceId());

			List actions = ResourceActionsUtil.getActions(permissions);

			for (int i = 0; i < actions.size(); i++) {
				String action = (String)actions.get(i);

				Element actionKeyEl = el.addElement("action-key");

				actionKeyEl.addText(action);
			}
		}
		catch (NoSuchResourceException nsre) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsre);
			}
		}

		return el;
	}

	protected void exportGroupRoles(
			String companyId, long groupId, String resourceName,
			String entityName, Element parentEl)
		throws PortalException, SystemException {

		List roles = RoleLocalServiceUtil.getGroupRoles(groupId);

		Element groupEl = exportRoles(
			companyId, resourceName, ResourceImpl.SCOPE_GROUP,
			String.valueOf(groupId), parentEl, entityName + "-roles",
			roles);

		if (groupEl.elements().isEmpty()) {
			parentEl.remove(groupEl);
		}
	}

	protected void exportInheritedPermissions(
			String companyId, String resourceName, String resourcePrimKey,
			Element parentEl, String entityName)
		throws PortalException, SystemException {

		Element entityPermissionsEl = DocumentHelper.createElement(
			entityName + "-permissions");

		Map entityMap = getEntityMap(companyId, entityName);

		Iterator itr = entityMap.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry entry = (Map.Entry)itr.next();

			String name = entry.getKey().toString();

			long entityGroupId = ((Long)entry.getValue()).longValue();

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
			String companyId, long groupId, String resourceName,
			String entityName, Element parentEl)
		throws PortalException, SystemException {

		Element entityRolesEl = DocumentHelper.createElement(
			entityName + "-roles");

		Map entityMap = getEntityMap(companyId, entityName);

		Iterator itr = entityMap.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry entry = (Map.Entry)itr.next();

			String name = entry.getKey().toString();

			long entityGroupId = ((Long)entry.getValue()).longValue();

			List entityRoles = RoleLocalServiceUtil.getGroupRoles(
				entityGroupId);

			Element entityEl = exportRoles(
				companyId, resourceName, ResourceImpl.SCOPE_GROUP,
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

	protected void exportPortletData(
			PortletDataContext context, Layout layout,
			LayoutTypePortlet layoutTypePortlet, Element parentEl)
		throws PortalException, SystemException {

		Iterator itr =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesByLayout(
				layout.getLayoutId(), layout.getOwnerId()).iterator();

		while (itr.hasNext()) {
			PortletPreferences prefs = (PortletPreferences)itr.next();

			javax.portlet.PortletPreferences jxPrefs =
				PortletPreferencesLocalServiceUtil.getPreferences(
					layout.getCompanyId(), prefs.getPrimaryKey());

			String portletId = prefs.getPortletId();

			if (layoutTypePortlet.hasPortletId(portletId)) {
				exportPortletData(context, portletId, jxPrefs, parentEl);
			}
		}
	}

	protected void exportPortletData(
			PortletDataContext context, String portletId,
			javax.portlet.PortletPreferences prefs, Element parentEl)
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
			portlet.getPortletDataHandler();

		if (portletDataHandler == null) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Exporting data for " + portletId);
		}

		String data = portletDataHandler.exportData(context, portletId, prefs);

		if (data == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Not exporting data for " + portletId +
						" because null data was returned");
			}

			return;
		}

		Element el = parentEl.addElement("portlet-data");

		el.addAttribute("portlet-id", portletId);
		el.addCDATA(data);
	}

	protected void exportPortletPreferences(
			String layoutId, String ownerId, Element parentEl)
		throws PortalException, SystemException {

		exportPortletPreferences(layoutId, ownerId, null, parentEl);
	}

	protected void exportPortletPreferences(
			String layoutId, String ownerId,
			LayoutTypePortlet layoutTypePortlet, Element parentEl)
		throws PortalException, SystemException {

		Iterator itr =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesByLayout(
				layoutId, ownerId).iterator();

		while (itr.hasNext()) {
			PortletPreferences prefs = (PortletPreferences)itr.next();

			String portletId = prefs.getPortletId();

			if ((layoutTypePortlet == null) ||
				(layoutTypePortlet.hasPortletId(portletId))) {

				Element el = parentEl.addElement("portlet-preferences");

				el.addAttribute("portlet-id", portletId);
				el.addAttribute("layout-id", layoutId);
				el.addAttribute("owner-id", ownerId);
				el.addElement("preferences").addCDATA(prefs.getPreferences());
			}
		}
	}

	protected Element exportRoles(
			String companyId, String resourceName, String scope,
			String resourcePrimKey, Element parentEl, String elName,
			List roles)
		throws PortalException, SystemException {

		Element el = parentEl.addElement(elName);

		try {
			Resource resource = ResourceLocalServiceUtil.getResource(
				companyId, resourceName, scope, resourcePrimKey);

			Map resourceRoles = RoleLocalServiceUtil.getResourceRoles(
				resource.getResourceId());

			Iterator itr = resourceRoles.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry entry = (Map.Entry)itr.next();

				String roleName = entry.getKey().toString();

				if (hasRole(roles, roleName)) {
					Element roleEl = el.addElement("role");

					roleEl.addAttribute("name", roleName);

					List actions = (List)entry.getValue();

					for (int i = 0; i < actions.size(); i++) {
						String action = (String)actions.get(i);

						Element actionKeyEl = roleEl.addElement(
							"action-key");

						actionKeyEl.addText(action);
						actionKeyEl.addAttribute("scope", scope);
					}
				}
			}
		}
		catch (NoSuchResourceException nsre) {
			if (_log.isWarnEnabled()) {
				_log.warn(nsre);
			}
		}

		return el;
	}

	protected void exportUserPermissions(
			String companyId, long groupId, String resourceName,
			String resourcePrimKey, Element parentEl)
		throws PortalException, SystemException {

		Element userPermissionsEl = DocumentHelper.createElement(
			"user-permissions");

		List users = UserLocalServiceUtil.getGroupUsers(groupId);

		for (int i = 0; i < users.size(); i++) {
			User user = (User)users.get(i);

			String emailAddress = user.getEmailAddress();

			Element userActionsEl =
				userPermissionsEl.addElement("user-actions");

			try {
				Resource resource = ResourceLocalServiceUtil.getResource(
					companyId, resourceName, ResourceImpl.SCOPE_INDIVIDUAL,
					resourcePrimKey);

				List permissions =
					PermissionLocalServiceUtil.getUserPermissions(
						user.getUserId(), resource.getResourceId());

				List actions = ResourceActionsUtil.getActions(permissions);

				for (int j = 0; j < actions.size(); j++) {
					String action = (String)actions.get(j);

					Element actionKeyEl = userActionsEl.addElement(
						"action-key");

					actionKeyEl.addText(action);
				}
			}
			catch (NoSuchResourceException nsre) {
				if (_log.isWarnEnabled()) {
					_log.warn(nsre.getMessage());
				}
			}

			if (userActionsEl.elements().isEmpty()) {
				userPermissionsEl.remove(userActionsEl);
			}
			else {
				userActionsEl.addAttribute("email-address", emailAddress);
			}
		}

		if (!userPermissionsEl.elements().isEmpty()) {
			parentEl.add(userPermissionsEl);
		}
	}

	protected void exportUserRoles(
			String companyId, long groupId, String resourceName,
			Element parentEl)
		throws PortalException, SystemException {

		Element userRolesEl = DocumentHelper.createElement("user-roles");

		List users = UserLocalServiceUtil.getGroupUsers(groupId);

		for (int i = 0; i < users.size(); i++) {
			User user = (User)users.get(i);

			String emailAddress = user.getEmailAddress();

			List userRoles = RoleLocalServiceUtil.getUserRoles(
				user.getUserId());

			Element userEl = exportRoles(
				companyId, resourceName, ResourceImpl.SCOPE_GROUP,
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

	protected void fixTypeSettings(Layout layout) {
		if (layout.getType().equals(LayoutImpl.TYPE_URL)) {
			Properties typeSettings = layout.getTypeSettingsProperties();

			String url = GetterUtil.getString(typeSettings.getProperty("url"));

			String friendlyURLPrivateGroupPath = PropsUtil.get(
				PropsUtil.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING);
			String friendlyURLPrivateUserPath = PropsUtil.get(
				PropsUtil.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING);
			String friendlyURLPublicPath = PropsUtil.get(
				PropsUtil.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING);

			if (url.startsWith(friendlyURLPrivateGroupPath) ||
				url.startsWith(friendlyURLPrivateUserPath) ||
				url.startsWith(friendlyURLPublicPath)) {

				int x = url.indexOf(StringPool.SLASH, 1);

				if (x > 0) {
					int y = url.indexOf(StringPool.SLASH, x + 1);

					if (y > x) {
						String fixedUrl = url.substring(0, x) +

						layout.getGroup().getFriendlyURL() +

						url.substring(y);

						typeSettings.setProperty("url", fixedUrl);
					}
				}
			}
		}
	}

	protected List getActions(Element el) {
		List actions = new ArrayList();

		Iterator itr = el.elements("action-key").iterator();

		while (itr.hasNext()) {
			Element actionEl = (Element)itr.next();

			actions.add(actionEl.getText());
		}

		return actions;
	}

	protected Map getEntityMap(String companyId, String entityName)
		throws PortalException, SystemException {

		Map entityList = new HashMap();

		if (entityName.equals("user-group")) {
			List userGroups = UserGroupLocalServiceUtil.search(
				companyId, null, null, null, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

			for (int i = 0; i < userGroups.size(); i++) {
				UserGroup userGroup = (UserGroup)userGroups.get(i);

				Group group = userGroup.getGroup();

				entityList.put(
					userGroup.getName(), new Long(group.getGroupId()));
			}
		}
		else if (entityName.equals("organization") ||
				 entityName.equals("location")) {

			List organizations = null;

			if (entityName.equals("organization")) {
				organizations = OrganizationLocalServiceUtil.search(
					companyId, OrganizationImpl.DEFAULT_PARENT_ORGANIZATION_ID,
					StringPool.EQUAL, null, null, null, null, null, null, null,
					true, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			}
			else if (entityName.equals("location")) {
				organizations = OrganizationLocalServiceUtil.search(
					companyId, OrganizationImpl.DEFAULT_PARENT_ORGANIZATION_ID,
					StringPool.NOT_EQUAL, null, null, null, null, null, null,
					null, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			}

			for (int i = 0; i < organizations.size(); i++) {
				Organization organization = (Organization)organizations.get(i);

				Group group = organization.getGroup();

				entityList.put(
					organization.getName(), new Long(group.getGroupId()));
			}
		}

		return entityList;
	}

	protected String getNextLayoutId(String ownerId) throws SystemException {
		int layoutId = 0;

		List layouts = LayoutUtil.findByOwnerId(ownerId);

		for (int i = 0; i < layouts.size(); i++) {
			Layout curLayout = (Layout)layouts.get(i);

			String curLayoutId = curLayout.getLayoutId();

			int x = GetterUtil.getInteger(curLayoutId);

			if (x > layoutId) {
				layoutId = x;
			}
		}

		return String.valueOf(++layoutId);
	}

	protected int getNextPriority(String ownerId, String parentLayoutId)
		throws SystemException {

		List layouts = LayoutUtil.findByO_P(ownerId, parentLayoutId);

		if (layouts.size() == 0) {
			return 0;
		}

		Layout layout = (Layout)layouts.get(layouts.size() - 1);

		return layout.getPriority() + 1;
	}

	protected String getParentLayoutId(String ownerId, String parentLayoutId)
		throws PortalException, SystemException {

		if (!parentLayoutId.equals(LayoutImpl.DEFAULT_PARENT_LAYOUT_ID)) {

			// Ensure parent layout exists

			try {
				LayoutUtil.findByPrimaryKey(
					new LayoutPK(parentLayoutId, ownerId));
			}
			catch (NoSuchLayoutException nsfe) {
				parentLayoutId = LayoutImpl.DEFAULT_PARENT_LAYOUT_ID;
			}
		}

		return parentLayoutId;
	}

	protected Resource getResource(
			String companyId, long groupId, String resourceName, String scope,
			String resourcePrimKey, boolean portletActions)
		throws PortalException, SystemException {

		Resource resource = null;

		try {
			resource = ResourceLocalServiceUtil.getResource(
				companyId, resourceName, scope, resourcePrimKey);
		}
		catch (NoSuchResourceException nsre) {
			ResourceLocalServiceUtil.addResources(
				companyId, groupId, null, resourceName, resourcePrimKey,
				portletActions, true, true);

			resource = ResourceLocalServiceUtil.getResource(
				companyId, resourceName, scope, resourcePrimKey);
		}

		return resource;
	}

	protected boolean hasRole(List roles, String roleName) {
		if ((roles == null) || (roles.size() == 0)) {
			return false;
		}

		for (int i = 0; i < roles.size(); i++) {
			Role role = (Role)roles.get(i);

			if (role.getName().equals(roleName)) {
				return true;
			}
		}

		return false;
	}

	protected void importGroupPermissions(
			String companyId, long groupId, String resourceName,
			String resourcePrimKey, Element parentEl, String elName,
			boolean portletActions)
		throws PortalException, SystemException {

		Element actionEl = parentEl.element(elName);

		if (actionEl == null) {
			return;
		}

		List actions = getActions(actionEl);

		Resource resource = getResource(
			companyId, groupId, resourceName, ResourceImpl.SCOPE_INDIVIDUAL,
			resourcePrimKey, portletActions);

		PermissionLocalServiceUtil.setGroupPermissions(
			groupId, (String[])actions.toArray(new String[0]),
			resource.getResourceId());
	}

	protected void importGroupRoles(
			String companyId, long groupId,
			String resourceName, String entityName,
			Element parentEl)
		throws PortalException, SystemException {

		Element entityRolesEl = parentEl.element(entityName + "-roles");

		if (entityRolesEl == null) {
			return;
		}

		importRolePermissions(
			companyId, resourceName, ResourceImpl.SCOPE_GROUP,
			String.valueOf(groupId), entityRolesEl, true);
	}

	protected void importInheritedPermissions(
			String companyId, String resourceName, String resourcePrimKey,
			Element permissionsEl, String entityName, boolean portletActions)
		throws PortalException, SystemException {

		Element entityPermissionsEl = permissionsEl.element(
			entityName + "-permissions");

		if (entityPermissionsEl == null) {
			return;
		}

		List actionsEls = entityPermissionsEl.elements(
			entityName + "-actions");

		for (int i = 0; i < actionsEls.size(); i++) {
			Element actionEl = (Element)actionsEls.get(i);

			String name = actionEl.attributeValue("name");

			long entityGroupId = searchGroupId(companyId, entityName, name);

			if (entityGroupId == 0) {
				_log.warn(
					"Ignore inherited permissions for entity " + entityName +
						" with name " + name);
			}
			else {
				Element parentEl = DocumentHelper.createElement("parent");

				parentEl.add(actionEl.createCopy());

				importGroupPermissions(
					companyId, entityGroupId, resourceName, resourcePrimKey,
					parentEl, entityName + "-actions", portletActions);
			}
		}
	}

	protected void importInheritedRoles(
			String companyId, long groupId, String resourceName,
			String entityName, Element parentEl)
		throws PortalException, SystemException {

		Element entityRolesEl = parentEl.element(entityName + "-roles");

		if (entityRolesEl == null) {
			return;
		}

		List entityEls = entityRolesEl.elements(entityName);

		for (int i = 0; i < entityEls.size(); i++) {
			Element entityEl = (Element)entityEls.get(i);

			String name = entityEl.attributeValue("name");

			long entityGroupId = searchGroupId(companyId, entityName, name);

			if (entityGroupId == 0) {
				_log.warn(
					"Ignore inherited roles for entity " + entityName +
						" with name " + name);
			}
			else {
				importRolePermissions(
					companyId, resourceName, ResourceImpl.SCOPE_GROUP,
					String.valueOf(groupId), entityEl, false);
			}
		}
	}

	protected void importPortletData(
			PortletDataContext context, Layout layout, Element parentEl)
		throws PortalException, SystemException {

		Iterator itr = parentEl.elements("portlet-data").iterator();

		while (itr.hasNext()) {
			Element el = (Element)itr.next();

			String portletId = el.attributeValue("portlet-id");

			PortletPreferencesPK pk = new PortletPreferencesPK(
				portletId, layout.getLayoutId(), layout.getOwnerId());

			PortletPreferences prefs =
				PortletPreferencesUtil.findByPrimaryKey(pk);

			String preferences =
				importPortletData(context, portletId, prefs, el);

			if (preferences != null) {
				prefs.setPreferences(preferences);

				PortletPreferencesUtil.update(prefs);
			}
		}
	}

	protected String importPortletData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs, Element parentEl)
		throws PortalException, SystemException {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			context.getCompanyId(), portletId);

		if (portlet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not import portlet data for " + portletId +
						" because the portlet does not exist");
			}

			return null;
		}

		PortletDataHandler portletDataHandler = portlet.getPortletDataHandler();

		if (portlet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not import portlet data for " + portletId +
						" because the portlet does not have a " +
							"PortletDataHandler");
			}

			return null;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Importing data for " + portletId);
		}

		PortletPreferencesImpl prefsImpl =
			(PortletPreferencesImpl)PortletPreferencesSerializer.fromDefaultXML(
				prefs.getPreferences());

		prefsImpl = (PortletPreferencesImpl)portletDataHandler.importData(
			context, portletId, prefsImpl, parentEl.getText());

		if (prefsImpl == null) {
			return null;
		}

		return PortletPreferencesSerializer.toXML(prefsImpl);
	}

	protected void importPortletPreferences(
			LayoutSet layoutSet, Element parentEl)
		throws PortalException, SystemException {

		Iterator itr = parentEl.elements("portlet-preferences").iterator();

		while (itr.hasNext()) {
			Element el = (Element)itr.next();

			String portletId = el.attributeValue("portlet-id");
			String layoutId = el.attributeValue("layout-id");
			String ownerId = el.attributeValue("owner-id");
			String preferences = el.elementText("preferences");

			if (ownerId.startsWith(PortletKeys.PREFS_OWNER_ID_GROUP)) {
				ownerId =
					PortletKeys.PREFS_OWNER_ID_GROUP + StringPool.PERIOD +
						layoutSet.getGroupId();
			}
			else if (ownerId.indexOf(PortletKeys.PREFS_OWNER_ID_USER) != -1) {
				ownerId =
					layoutSet.getOwnerId() + StringPool.PERIOD +
						PortletKeys.PREFS_OWNER_ID_USER + StringPool.PERIOD +
							UserImpl.getDefaultUserId(layoutSet.getCompanyId());
			}
			else if (ownerId.startsWith(LayoutImpl.PUBLIC)) {
				ownerId = LayoutImpl.PUBLIC + layoutSet.getGroupId();
			}
			else if (ownerId.startsWith(LayoutImpl.PRIVATE)) {
				ownerId = LayoutImpl.PRIVATE + layoutSet.getGroupId();
			}

			PortletPreferencesPK pk = new PortletPreferencesPK(
				portletId, layoutId, ownerId);

			try {
				PortletPreferencesLocalServiceUtil.deletePortletPreferences(pk);
			}
			catch (NoSuchPortletPreferencesException nsppe) {
			}

			PortletPreferences prefs = PortletPreferencesUtil.create(pk);

			prefs.setPreferences(preferences);

			PortletPreferencesUtil.update(prefs);
		}
	}

	protected void importRolePermissions(
			String companyId, String resourceName, String scope,
			String resourcePrimKey, Element parentEl, boolean communityRole)
		throws PortalException, SystemException {

		List roleEls = parentEl.elements("role");

		for (int i = 0; i < roleEls.size(); i++) {
			Element roleEl = (Element)roleEls.get(i);

			String roleName = roleEl.attributeValue("name");

			Role role = searchRole(companyId, roleName);

			if (role == null) {
				_log.warn(
					"Ignoring permissions for role with name " + roleName);
			}
			else {
				List actions = getActions(roleEl);

				PermissionLocalServiceUtil.setRolePermissions(
					role.getRoleId(), companyId, resourceName, scope,
					resourcePrimKey, (String[])actions.toArray(new String[0]));

				if (communityRole) {
					long[] groupIds = {GetterUtil.getLong(resourcePrimKey)};

					GroupLocalServiceUtil.addRoleGroups(
						role.getRoleId(), groupIds);
				}
			}
		}
	}

	protected void importUserPermissions(
			String companyId, long groupId, String resourceName,
			String resourcePrimKey, Element parentEl, boolean portletActions)
		throws PortalException, SystemException {

		Element userPermissionsEl = parentEl.element("user-permissions");

		if (userPermissionsEl == null) {
			return;
		}

		List userActionsEls = userPermissionsEl.elements("user-actions");

		for (int i = 0; i < userActionsEls.size(); i++) {
			Element userActionsEl = (Element)userActionsEls.get(i);

			String emailAddress = userActionsEl.attributeValue("email-address");

			User user = searchUser(companyId, emailAddress, groupId);

			if (user == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Ignoring permissions for user with email address " +
							emailAddress);
				}
			}
			else {
				List actions = getActions(userActionsEl);

				Resource resource = getResource(
					companyId, groupId, resourceName,
					ResourceImpl.SCOPE_INDIVIDUAL, resourcePrimKey,
					portletActions);

				PermissionLocalServiceUtil.setUserPermissions(
					user.getUserId(), (String[])actions.toArray(new String[0]),
					resource.getResourceId());
			}
		}
	}

	protected void importUserRoles(
			String companyId, long groupId, String resourceName,
			Element parentEl)
		throws PortalException, SystemException {

		Element userRolesEl = parentEl.element("user-roles");

		if (userRolesEl == null) {
			return;
		}

		LinkedHashMap params = new LinkedHashMap();

		params.put("usersGroups", new Long(groupId));

		List userEls = userRolesEl.elements("user");

		for (int i = 0; i < userEls.size(); i++) {
			Element userEl = (Element)userEls.get(i);

			String emailAddress = userEl.attributeValue("email-address");

			User user = searchUser(companyId, emailAddress, groupId);

			if (user == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Ignoring roles for user with email address " +
							emailAddress);
				}
			}
			else {
				importRolePermissions(
					companyId, resourceName, ResourceImpl.SCOPE_GROUP,
					String.valueOf(groupId), userEl, false);
			}
		}
	}

	protected boolean isDescendant(Layout layout, String layoutId)
		throws PortalException, SystemException {

		if (layout.getLayoutId().equals(layoutId)) {
			return true;
		}
		else {
			Iterator itr = layout.getChildren().iterator();

			while (itr.hasNext()) {
				Layout childLayout = (Layout)itr.next();

				if (isDescendant(childLayout, layoutId)) {
					return true;
				}
			}

			return false;
		}
	}

	protected long searchGroupId(
			String companyId, String entityName, String name)
		throws PortalException, SystemException {

		if (entityName.equals("user-group")) {
			List userGroups = UserGroupLocalServiceUtil.search(
				companyId, name, null, null, 0, 1);

			if (userGroups.size() > 0) {
				UserGroup userGroup = (UserGroup)userGroups.get(0);

				Group group = userGroup.getGroup();

				return group.getGroupId();
			}
		}
		else if (entityName.equals("organization") ||
				 entityName.equals("location")) {

			List organizations = null;

			if (entityName.equals("organization")) {
				organizations = OrganizationLocalServiceUtil.search(
					companyId, OrganizationImpl.DEFAULT_PARENT_ORGANIZATION_ID,
					StringPool.EQUAL, name, null, null, null, null, null, null,
					true, 0, 1);

			}
			else if (entityName.equals("location")) {
				organizations = OrganizationLocalServiceUtil.search(
					companyId, OrganizationImpl.DEFAULT_PARENT_ORGANIZATION_ID,
					StringPool.NOT_EQUAL, null, null, null, null, null, null,
					null, true, 0, 1);
			}

			if (organizations.size() > 0) {
				Organization organization = (Organization)organizations.get(0);

				Group group = organization.getGroup();

				return group.getGroupId();
			}
		}

		return 0;
	}

	protected Role searchRole(String companyId, String name)
		throws PortalException, SystemException {

		try {
			return RoleLocalServiceUtil.getRole(companyId, name);
		}
		catch (NoSuchRoleException nsre) {
			return null;
		}
	}

	protected User searchUser(
			String companyId, String emailAddress, long groupId)
		throws SystemException {

		LinkedHashMap params = new LinkedHashMap();

		params.put("usersGroups", new Long(groupId));

		List users = UserLocalServiceUtil.search(
			companyId, null, null, null, emailAddress, true, params, true, 0, 1,
			null);

		if (users.size() == 0) {
			return null;
		}
		else {
			return (User)users.get(0);
		}
	}

	protected void validate(
			String layoutId, String ownerId, String parentLayoutId, String name,
			String type, boolean hidden, String friendlyURL)
		throws PortalException, SystemException {

		friendlyURL = friendlyURL.toLowerCase();

		boolean firstLayout = false;

		if (parentLayoutId.equals(LayoutImpl.DEFAULT_PARENT_LAYOUT_ID)) {
			List layouts = LayoutUtil.findByO_P(ownerId, parentLayoutId);

			if (layouts.size() == 0) {
				firstLayout = true;
			}
			else {
				String firstLayoutId = ((Layout)layouts.get(0)).getLayoutId();

				if (firstLayoutId.equals(layoutId)) {
					firstLayout = true;
				}
			}
		}

		if (firstLayout) {
			validateFirstLayout(type, hidden);
		}

		if (Validator.isNull(name)) {
			throw new LayoutNameException();
		}

		if (!PortalUtil.isLayoutParentable(type)) {
			if (LayoutUtil.countByO_P(ownerId, layoutId) > 0) {
				throw new LayoutTypeException(
					LayoutTypeException.NOT_PARENTABLE);
			}
		}

		if (Validator.isNotNull(friendlyURL)) {
			int exceptionType = LayoutImpl.validateFriendlyURL(friendlyURL);

			if (exceptionType != -1) {
				throw new LayoutFriendlyURLException(exceptionType);
			}

			try {
				Layout layout = LayoutUtil.findByO_F(ownerId, friendlyURL);

				if (!layout.getLayoutId().equals(layoutId)) {
					throw new LayoutFriendlyURLException(
						LayoutFriendlyURLException.DUPLICATE);
				}
			}
			catch (NoSuchLayoutException nsle) {
			}

			LayoutImpl.validateFriendlyURLKeyword(friendlyURL);

			Map friendlyURLMappers =
				PortletLocalServiceUtil.getFriendlyURLMappers();

			Iterator itr = friendlyURLMappers.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry entry = (Map.Entry)itr.next();

				String className = (String)entry.getValue();

				FriendlyURLMapper friendlyURLMapper =
					(FriendlyURLMapper)InstancePool.get(className);

				if (friendlyURL.indexOf(friendlyURLMapper.getMapping()) != -1) {
					LayoutFriendlyURLException lfurle =
						new LayoutFriendlyURLException(
							LayoutFriendlyURLException.KEYWORD_CONFLICT);

					lfurle.setKeywordConflict(friendlyURLMapper.getMapping());

					throw lfurle;
				}
			}

		}
	}

	protected void validateFirstLayout(String type, boolean hidden)
		throws PortalException {

		if (!type.equals(LayoutImpl.TYPE_PORTLET)) {
			throw new LayoutTypeException(LayoutTypeException.FIRST_LAYOUT);
		}

		if (hidden) {
			throw new LayoutHiddenException();
		}
	}

	protected void validateParentLayoutId(
			String layoutId, String ownerId, String parentLayoutId)
		throws PortalException, SystemException {

		Layout layout = LayoutUtil.findByPrimaryKey(
			new LayoutPK(layoutId, ownerId));

		if (!parentLayoutId.equals(layout.getParentLayoutId())) {

			// Layouts can always be moved to the root level

			if (parentLayoutId.equals(LayoutImpl.DEFAULT_PARENT_LAYOUT_ID)) {
				return;
			}

			// Layout cannot become a child of a layout that is not parentable

			Layout parentLayout = LayoutUtil.findByPrimaryKey(new LayoutPK(
				parentLayoutId, layout.getOwnerId()));

			if (!PortalUtil.isLayoutParentable(parentLayout)) {
				throw new LayoutParentLayoutIdException(
					LayoutParentLayoutIdException.NOT_PARENTABLE);
			}

			// Layout cannot become descendant of itself

			if (isDescendant(layout, parentLayoutId)) {
				throw new LayoutParentLayoutIdException(
					LayoutParentLayoutIdException.SELF_DESCENDANT);
			}

			// If layout is moved, the new first layout must be valid

			if (layout.getParentLayoutId().equals(
					LayoutImpl.DEFAULT_PARENT_LAYOUT_ID)) {

				List layouts = LayoutUtil.findByO_P(
					ownerId, LayoutImpl.DEFAULT_PARENT_LAYOUT_ID);

				// You can only reach this point if there are more than two
				// layouts at the root level because of the descendant check

				String firstLayoutId = ((Layout)layouts.get(0)).getLayoutId();

				if (firstLayoutId.equals(layoutId)) {
					Layout secondLayout = (Layout)layouts.get(1);

					try {
						validateFirstLayout(
							secondLayout.getType(), secondLayout.getHidden());
					}
					catch (LayoutHiddenException lhe) {
						throw new LayoutParentLayoutIdException(
							LayoutParentLayoutIdException.FIRST_LAYOUT_HIDDEN);
					}
					catch (LayoutTypeException lte) {
						throw new LayoutParentLayoutIdException(
							LayoutParentLayoutIdException.FIRST_LAYOUT_TYPE);
					}
				}
			}
		}
	}

	private static Log _log = LogFactory.getLog(LayoutLocalServiceImpl.class);

}