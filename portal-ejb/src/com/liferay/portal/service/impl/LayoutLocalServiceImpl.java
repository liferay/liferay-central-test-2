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
import com.liferay.portal.PortalException;
import com.liferay.portal.RequiredLayoutException;
import com.liferay.portal.SystemException;
import com.liferay.portal.comm.CommLink;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutReference;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.Theme;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.ColorSchemeImpl;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
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
import com.liferay.portal.theme.ThemeLoader;
import com.liferay.portal.theme.ThemeLoaderFactory;
import com.liferay.portal.util.ContentUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.ReleaseInfo;
import com.liferay.portal.util.comparator.LayoutPriorityComparator;
import com.liferay.portal.velocity.VelocityContextPool;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesSerializer;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.util.CollectionFactory;
import com.liferay.util.FileUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.InstancePool;
import com.liferay.util.LocaleUtil;
import com.liferay.util.MapUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Time;
import com.liferay.util.Validator;
import com.liferay.util.xml.XMLFormatter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.commons.lang.time.StopWatch;
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
 * @author Raymond Auge
 *
 */
public class LayoutLocalServiceImpl extends LayoutLocalServiceBaseImpl {

	public Layout addLayout(
			long groupId, long userId, boolean privateLayout,
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

		layout.setCompanyId(user.getCompanyId());
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
			user.getCompanyId(), groupId, user.getUserId(),
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

	public byte[] exportLayouts(String ownerId, Map parameterMap)
		throws PortalException, SystemException {

		boolean exportPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.EXPORT_PERMISSIONS);
		boolean exportPortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.EXPORT_PORTLET_DATA);
		boolean exportPortletPreferences = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.EXPORT_PORTLET_PREFERENCES);
		boolean exportTheme = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.EXPORT_THEME);

		if (_log.isDebugEnabled()) {
			_log.debug("Export permissions " + exportPermissions);
			_log.debug("Export portlet data " + exportPortletData);
			_log.debug(
				"Export portlet preferences " + exportPortletPreferences);
			_log.debug("Export theme " + exportTheme);
		}

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		LayoutCache layoutCache = new LayoutCache();

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(ownerId);

		long companyId = layoutSet.getCompanyId();
		long groupId = layoutSet.getGroupId();

		ZipWriter zipWriter = new ZipWriter();

		PortletDataContext context = new PortletDataContext(
			companyId, groupId, parameterMap, CollectionFactory.getHashSet(),
			zipWriter);

		Group guestGroup = GroupLocalServiceUtil.getGroup(
			companyId, GroupImpl.GUEST);

		String guestPrefsOwnerId = null;

		if (!layoutSet.isPrivateLayout()) {
			long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
				companyId);

			guestPrefsOwnerId =
				ownerId + StringPool.PERIOD + PortletKeys.PREFS_OWNER_ID_USER +
					StringPool.PERIOD + defaultUserId;
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

		Iterator itr = getLayouts(ownerId).iterator();

		while (itr.hasNext()) {
			Layout layout = (Layout)itr.next();

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

			Element permissionsEl = layoutEl.addElement("permissions");

			// Layout permissions

			if (exportPermissions) {
				exportLayoutPermissions(
					layoutCache, companyId, groupId, guestGroup, layout,
					permissionsEl);
			}

			if (layout.getType().equals(LayoutImpl.TYPE_PORTLET)) {
				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				// The order of the export is important. You must always export
				// the portlet data first, then the portlet preferences, then
				// the portlet permissions. The export of the portlet data
				// process may modify the portlet preferences, and those changes
				// should be included in the exported LAR.

				// Portlet data

				if (exportPortletData) {
					exportPortletData(
						context, layout, layoutTypePortlet, layoutEl);
				}

				// Portlet preferences

				if (exportPortletPreferences) {
					exportPortletPreferences(
						layout.getLayoutId(), ownerId, layoutTypePortlet,
						layoutEl);

					if (guestPrefsOwnerId != null) {
						exportPortletPreferences(
							layout.getLayoutId(), guestPrefsOwnerId,
							layoutTypePortlet, layoutEl);
					}
				}

				// Portlet permissions

				if (exportPermissions) {
					exportPortletPermissions(
						layoutCache, companyId, groupId, guestGroup,
						layout, layoutTypePortlet, portletIds, permissionsEl);
				}
			}
		}

		// Portlet preferences

		if (exportPortletPreferences) {
			String groupPrefsOwnerId =
				PortletKeys.PREFS_OWNER_ID_GROUP + StringPool.PERIOD + groupId;

			exportPortletPreferences(
				PortletKeys.PREFS_LAYOUT_ID_SHARED, groupPrefsOwnerId, root);
		}

		Element rolesEl = root.addElement("roles");

		// Layout roles

		if (exportPermissions) {
			exportLayoutRoles(layoutCache, companyId, groupId, rolesEl);
		}

		// Portlet roles

		if (exportPermissions) {
			exportPortletRoles(
				layoutCache, companyId, groupId, portletIds, rolesEl);
		}

		// Look and feel

		byte[] themeZip = null;

		try {
			if (exportTheme) {
				themeZip = exportTheme(layoutSet);
			}
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		// XML

		if (_log.isInfoEnabled()) {
			_log.info("Exporting layouts takes " + stopWatch.getTime() + " ms");
		}

		// Zip

		try {
			zipWriter.addEntry("layouts.xml", XMLFormatter.toString(doc));

			if (themeZip != null) {
				zipWriter.addEntry("theme.zip", themeZip);
			}

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
			long companyId, String portletId, String prefsKey,
			String prefsValue)
		throws SystemException {

		List list = LayoutFinder.findByC_P_P(
			companyId, portletId, prefsKey, prefsValue);

		return (LayoutReference[])list.toArray(new LayoutReference[0]);
	}

	public void importLayouts(
			long userId, String ownerId, Map parameterMap, File file)
		throws PortalException, SystemException {

		try {
			importLayouts(
				userId, ownerId, parameterMap, new FileInputStream(file));
		}
		catch (FileNotFoundException fnfe) {
			throw new SystemException(fnfe);
		}
	}

	public void importLayouts(
			long userId, String ownerId, Map parameterMap, InputStream is)
		throws PortalException, SystemException {

		boolean importPermissions = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.IMPORT_PERMISSIONS);
		boolean importPortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.IMPORT_PORTLET_DATA);
		boolean importPortletPreferences = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.IMPORT_PORTLET_PREFERENCES);
		boolean importTheme = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.IMPORT_THEME);

		if (_log.isDebugEnabled()) {
			_log.debug("Import permissions " + importPermissions);
			_log.debug("Import portlet data " + importPortletData);
			_log.debug(
				"Import portlet preferences " + importPortletPreferences);
			_log.debug("Import theme " + importTheme);
		}

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

        LayoutCache layoutCache = new LayoutCache();

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(ownerId);

		long companyId = layoutSet.getCompanyId();
		long groupId = layoutSet.getGroupId();

		ZipReader zipReader = new ZipReader(is);

		PortletDataContext context = new PortletDataContext(
			companyId, groupId, parameterMap, CollectionFactory.getHashSet(),
			zipReader);

		Group guestGroup = GroupLocalServiceUtil.getGroup(
			companyId, GroupImpl.GUEST);

		// Zip

		Element root = null;
		byte[] themeZip = null;

		try {

			// XML

			String xml = zipReader.getEntryAsString("layouts.xml");

			SAXReader reader = new SAXReader();

			Document doc = reader.read(new StringReader(xml));

			root = doc.getRootElement();

			// Look and feel

			if (importTheme) {
				themeZip = zipReader.getEntryAsByteArray("theme.zip");
			}
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

		boolean useThemeZip = false;

		if (themeZip != null) {
			try {
				String importThemeId = importTheme(layoutSet, themeZip);

				if (importThemeId != null) {
					themeId = importThemeId;
					colorSchemeId =
						ColorSchemeImpl.getDefaultRegularColorSchemeId();

					useThemeZip = true;
				}

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Importing theme takes " + stopWatch.getTime() + " ms");
				}
			}
			catch (Exception e) {
				throw new SystemException(e);
			}
		}

		boolean wapTheme = false;

		LayoutSetLocalServiceUtil.updateLookAndFeel(
			ownerId, themeId, colorSchemeId, StringPool.BLANK, wapTheme);

		// Layouts

		User user = UserUtil.findByPrimaryKey(userId);

		List newLayoutPKs = new ArrayList();

		Iterator itr = root.elements("layout").iterator();

		if (_log.isDebugEnabled()) {
			if (itr.hasNext()) {
				_log.debug("Importing layouts");
			}
		}

		while (itr.hasNext()) {
			Element layoutEl = (Element)itr.next();

			String layoutId = layoutEl.attributeValue("layout-id");
			String parentLayoutId = layoutEl.elementText(
				"parent-layout-id");

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Importing layout with layout id " + layoutId +
						" and parent layout id " + parentLayoutId);
			}

			String name = layoutEl.elementText("name");
			String title = layoutEl.elementText("title");
			String type = layoutEl.elementText("type");
			String typeSettings = layoutEl.elementText("type-settings");
			boolean hidden = GetterUtil.getBoolean(
				layoutEl.elementText("hidden"));
			String friendlyURL = layoutEl.elementText("friendly-url");

			if (useThemeZip) {
				themeId = StringPool.BLANK;
				colorSchemeId = StringPool.BLANK;
			}
			else {
				themeId = layoutEl.elementText("theme-id");
				colorSchemeId = layoutEl.elementText("color-scheme-id");
			}

			int priority = GetterUtil.getInteger(
				layoutEl.elementText("priority"));

			LayoutPK layoutPK = new LayoutPK(layoutId, ownerId);

			newLayoutPKs.add(layoutPK);

			Layout layout = LayoutUtil.fetchByPrimaryKey(layoutPK);

			if (_log.isDebugEnabled()) {
				if (layout == null) {
					_log.debug(
						"Layout with primary key " + layoutPK +
							" does not exist");
				}
				else {
					_log.debug(
						"Layout with primary key " + layoutPK + " exists");
				}
			}

			if (layout == null) {
				layout = LayoutUtil.create(layoutPK);
			}

			layout.setCompanyId(user.getCompanyId());
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

			Element permissionsEl = layoutEl.element("permissions");

			// Layout permissions

			if (importPermissions) {
				importLayoutPermissions(
					layoutCache, companyId, groupId, guestGroup, layout,
					permissionsEl);
			}

			// The order of the import is important. You must always import
			// the portlet preferences first, then the portlet data, then
			// the portlet permissions. The import of the portlet data
			// assumes that portlet preferences already exist.

			// Portlet preferences

			if (importPortletPreferences) {
				importPortletPreferences(layoutSet, layoutEl);
			}

			// Portlet data

			if (importPortletData) {
				importPortletData(context, layout, layoutEl);
			}

			// Portlet permissions

			if (importPermissions) {
				importPortletPermissions(
					layoutCache, companyId, groupId, guestGroup, layout,
					permissionsEl);
			}
		}

		// Portlet preferences

		if (importPortletPreferences) {
			importPortletPreferences(layoutSet, root);
		}

		Element rolesEl = (Element)root.element("roles");

		// Layout roles

		if (importPermissions) {
			importLayoutRoles(layoutCache, companyId, groupId, rolesEl);
		}

		// Portlet roles

		if (importPermissions) {
			importPortletRoles(layoutCache, companyId, groupId, rolesEl);
		}

		// Delete missing layouts

		deleteMissingLayouts(ownerId, newLayoutPKs);

		// Page count

		LayoutSetLocalServiceUtil.updatePageCount(ownerId);

		if (_log.isInfoEnabled()) {
			_log.info("Importing layouts takes " + stopWatch.getTime() + " ms");
		}
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
			String colorSchemeId, String css, boolean wapTheme)
		throws PortalException, SystemException {

		Layout layout = LayoutUtil.findByPrimaryKey(
			new LayoutPK(layoutId, ownerId));

		if (wapTheme) {
			layout.setWapThemeId(themeId);
			layout.setWapColorSchemeId(colorSchemeId);
		}
		else {
			layout.setThemeId(themeId);
			layout.setColorSchemeId(colorSchemeId);
			layout.setCss(css);
		}

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

	public Layout updateParentLayoutId(
			String layoutId, String ownerId, String parentLayoutId)
		throws PortalException, SystemException {

		parentLayoutId = getParentLayoutId(ownerId, parentLayoutId);

		validateParentLayoutId(layoutId, ownerId, parentLayoutId);

		Layout layout = LayoutUtil.findByPrimaryKey(
			new LayoutPK(layoutId, ownerId));

		if (!parentLayoutId.equals(layout.getParentLayoutId())) {
			layout.setPriority(getNextPriority(ownerId, parentLayoutId));
		}

		layout.setParentLayoutId(parentLayoutId);

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

		if (_log.isDebugEnabled()) {
			if (newLayoutPKs.size() > 0) {
				_log.debug("Delete missing layouts");
			}
		}

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
			long companyId, long groupId, String resourceName,
			String resourcePrimKey, Element parentEl, String elName)
		throws PortalException, SystemException {

		Element el = parentEl.addElement(elName);

		List permissions = PermissionLocalServiceUtil.getGroupPermissions(
			groupId, companyId, resourceName, ResourceImpl.SCOPE_INDIVIDUAL,
			resourcePrimKey);

		List actions = ResourceActionsUtil.getActions(permissions);

		for (int i = 0; i < actions.size(); i++) {
			String action = (String)actions.get(i);

			Element actionKeyEl = el.addElement("action-key");

			actionKeyEl.addText(action);
		}

		return el;
	}

	protected void exportGroupRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String entityName, Element parentEl)
		throws PortalException, SystemException {

		List roles = layoutCache.getGroupRoles(groupId);

		Element groupEl = exportRoles(
			companyId, resourceName, ResourceImpl.SCOPE_GROUP,
			String.valueOf(groupId), parentEl, entityName + "-roles", roles);

		if (groupEl.elements().isEmpty()) {
			parentEl.remove(groupEl);
		}
	}

	protected void exportInheritedPermissions(
			LayoutCache layoutCache, long companyId, String resourceName,
			String resourcePrimKey, Element parentEl, String entityName)
		throws PortalException, SystemException {

		Element entityPermissionsEl = DocumentHelper.createElement(
			entityName + "-permissions");

		Map entityMap = layoutCache.getEntityMap(companyId, entityName);

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
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String entityName, Element parentEl)
		throws PortalException, SystemException {

		Element entityRolesEl = DocumentHelper.createElement(
			entityName + "-roles");

		Map entityMap = layoutCache.getEntityMap(companyId, entityName);

		Iterator itr = entityMap.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry entry = (Map.Entry)itr.next();

			String name = entry.getKey().toString();

			long entityGroupId = ((Long)entry.getValue()).longValue();

			List entityRoles = layoutCache.getGroupRoles(entityGroupId);

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

	protected void exportLayoutPermissions(
			LayoutCache layoutCache, long companyId, long groupId,
			Group guestGroup, Layout layout, Element permissionsEl)
		throws PortalException, SystemException {

		String resourceName = Layout.class.getName();
		String resourcePrimKey = layout.getPrimaryKey().toString();

		exportGroupPermissions(
			companyId, groupId, resourceName, resourcePrimKey, permissionsEl,
			"community-actions");

		if (groupId != guestGroup.getGroupId()) {
			exportGroupPermissions(
				companyId, guestGroup.getGroupId(), resourceName,
				resourcePrimKey, permissionsEl, "guest-actions");
		}

		exportUserPermissions(
			layoutCache, companyId, groupId, resourceName, resourcePrimKey,
			permissionsEl);

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

	protected void exportLayoutRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			Element rolesEl)
		throws PortalException, SystemException {

		String resourceName = Layout.class.getName();

		exportGroupRoles(
			layoutCache, companyId, groupId, resourceName, "community",
			rolesEl);

		exportUserRoles(layoutCache, companyId, groupId, resourceName, rolesEl);

		exportInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "organization",
			rolesEl);

		exportInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "location", rolesEl);

		exportInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "user-group",
			rolesEl);
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

	protected void exportPortletPermissions(
			LayoutCache layoutCache, long companyId, long groupId,
			Group guestGroup, Layout layout,
			LayoutTypePortlet layoutTypePortlet, Set portletIds,
			Element permissionsEl)
		throws PortalException, SystemException {

		Iterator itr = layoutTypePortlet.getPortletIds().iterator();

		while (itr.hasNext()) {
			String portletId = (String)itr.next();

			if (!portletIds.contains(portletId)) {
				portletIds.add(portletId);
			}

			if (layoutTypePortlet.hasPortletId(portletId)) {
				String resourceName = PortletImpl.getRootPortletId(portletId);
				String resourcePrimKey = PortletPermission.getPrimaryKey(
					layout.getPlid(), portletId);

				Element portletEl = permissionsEl.addElement("portlet");

				portletEl.addAttribute("portlet-id", portletId);

				exportGroupPermissions(
					companyId, groupId, resourceName, resourcePrimKey,
					portletEl, "community-actions");

				if (groupId != guestGroup.getGroupId()) {
					exportGroupPermissions(
						companyId, guestGroup.getGroupId(), resourceName,
						resourcePrimKey, portletEl, "guest-actions");
				}

				exportUserPermissions(
					layoutCache, companyId, groupId, resourceName,
					resourcePrimKey, portletEl);

				exportInheritedPermissions(
					layoutCache, companyId, resourceName, resourcePrimKey,
					portletEl, "organization");

				exportInheritedPermissions(
					layoutCache, companyId, resourceName, resourcePrimKey,
					portletEl, "location");

				exportInheritedPermissions(
					layoutCache, companyId, resourceName, resourcePrimKey,
					portletEl, "user-group");
			}
		}
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

	protected void exportPortletRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			Set portletIds, Element rolesEl)
		throws PortalException, SystemException {

		Iterator itr = portletIds.iterator();

		while (itr.hasNext()) {
			String portletId = (String)itr.next();

			String resourceName = PortletImpl.getRootPortletId(portletId);

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
	}

	protected Element exportRoles(
			long companyId, String resourceName, int scope,
			String resourcePrimKey, Element parentEl, String elName,
			List roles)
		throws PortalException, SystemException {

		Element el = parentEl.addElement(elName);

		Map resourceRoles = RoleLocalServiceUtil.getResourceRoles(
			companyId, resourceName, scope, resourcePrimKey);

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

					Element actionKeyEl = roleEl.addElement("action-key");

					actionKeyEl.addText(action);
					actionKeyEl.addAttribute("scope", String.valueOf(scope));
				}
			}
		}

		return el;
	}

	protected byte[] exportTheme(LayoutSet layoutSet)
		throws IOException, PortalException, SystemException {

		Theme theme = layoutSet.getTheme();

		ZipWriter zipWriter = new ZipWriter();

		String lookAndFeelXML = ContentUtil.get(
			"com/liferay/portal/dependencies/liferay-look-and-feel.xml.tmpl");

		lookAndFeelXML = StringUtil.replace(
			lookAndFeelXML,
			new String[] {
				"[$TEMPLATE_EXTENSION$]", "[$VIRTUAL_PATH$]"
			},
			new String[] {
				theme.getTemplateExtension(), theme.getVirtualPath()
			}
		);

		zipWriter.addEntry("liferay-look-and-feel.xml", lookAndFeelXML);

		String servletContextName = theme.getServletContextName();

		ServletContext ctx = VelocityContextPool.get(servletContextName);

		if (ctx == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Servlet context not found for theme " +
						theme.getThemeId());
			}

			return null;
		}

		File cssPath = new File(ctx.getRealPath(theme.getCssPath()));

		exportThemeFiles("css", cssPath, zipWriter);

		File imagesPath = new File(ctx.getRealPath(theme.getImagesPath()));

		exportThemeFiles("images", imagesPath, zipWriter);

		File javaScriptPath = new File(
			ctx.getRealPath(theme.getJavaScriptPath()));

		exportThemeFiles("javascript", javaScriptPath, zipWriter);

		File templatesPath = new File(
			ctx.getRealPath(theme.getTemplatesPath()));

		exportThemeFiles("templates", templatesPath, zipWriter);

		return zipWriter.finish();
	}

	protected void exportThemeFiles(String path, File dir, ZipWriter zipWriter)
		throws IOException {

		if (!dir.exists()) {
			return;
		}

		File[] files = dir.listFiles();

		for (int i = 0; i < files.length; i++) {
			File file = files[i];

			if (file.isDirectory()) {
				exportThemeFiles(path + "/" + file.getName(), file, zipWriter);
			}
			else {
				zipWriter.addEntry(
					path + "/" + file.getName(), FileUtil.getBytes(file));
			}
		}
	}

	protected void exportUserPermissions(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String resourcePrimKey, Element parentEl)
		throws PortalException, SystemException {

		Element userPermissionsEl = DocumentHelper.createElement(
			"user-permissions");

		List users = layoutCache.getGroupUsers(groupId);

		for (int i = 0; i < users.size(); i++) {
			User user = (User)users.get(i);

			String emailAddress = user.getEmailAddress();

			Element userActionsEl =
				userPermissionsEl.addElement("user-actions");

			List permissions = PermissionLocalServiceUtil.getUserPermissions(
				user.getUserId(), companyId, resourceName,
				ResourceImpl.SCOPE_INDIVIDUAL, resourcePrimKey);

			List actions = ResourceActionsUtil.getActions(permissions);

			for (int j = 0; j < actions.size(); j++) {
				String action = (String)actions.get(j);

				Element actionKeyEl = userActionsEl.addElement("action-key");

				actionKeyEl.addText(action);
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
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, Element parentEl)
		throws PortalException, SystemException {

		Element userRolesEl = DocumentHelper.createElement("user-roles");

		List users = layoutCache.getGroupUsers(groupId);

		for (int i = 0; i < users.size(); i++) {
			User user = (User)users.get(i);

			long userId = user.getUserId();
			String emailAddress = user.getEmailAddress();

			List userRoles = layoutCache.getUserRoles(userId);

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
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String resourcePrimKey, Element parentEl,
			String elName, boolean portletActions)
		throws PortalException, SystemException {

		Element actionEl = parentEl.element(elName);

		if (actionEl == null) {
			return;
		}

		List actions = getActions(actionEl);

		Resource resource = layoutCache.getResource(
			companyId, groupId, resourceName, ResourceImpl.SCOPE_INDIVIDUAL,
			resourcePrimKey, portletActions);

		PermissionLocalServiceUtil.setGroupPermissions(
			groupId, (String[])actions.toArray(new String[0]),
			resource.getResourceId());
	}

	protected void importGroupRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String entityName,
			Element parentEl)
		throws PortalException, SystemException {

		Element entityRolesEl = parentEl.element(entityName + "-roles");

		if (entityRolesEl == null) {
			return;
		}

		importRolePermissions(
			layoutCache, companyId, resourceName, ResourceImpl.SCOPE_GROUP,
			String.valueOf(groupId), entityRolesEl, true);
	}

	protected void importInheritedPermissions(
			LayoutCache layoutCache, long companyId, String resourceName,
			String resourcePrimKey, Element permissionsEl, String entityName,
			boolean portletActions)
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

			long entityGroupId = layoutCache.getEntityGroupId(
				companyId, entityName, name);

			if (entityGroupId == 0) {
				_log.warn(
					"Ignore inherited permissions for entity " + entityName +
						" with name " + name);
			}
			else {
				Element parentEl = DocumentHelper.createElement("parent");

				parentEl.add(actionEl.createCopy());

				importGroupPermissions(
					layoutCache, companyId, entityGroupId, resourceName,
					resourcePrimKey, parentEl, entityName + "-actions",
					portletActions);
			}
		}
	}

	protected void importInheritedRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String entityName, Element parentEl)
		throws PortalException, SystemException {

		Element entityRolesEl = parentEl.element(entityName + "-roles");

		if (entityRolesEl == null) {
			return;
		}

		List entityEls = entityRolesEl.elements(entityName);

		for (int i = 0; i < entityEls.size(); i++) {
			Element entityEl = (Element)entityEls.get(i);

			String name = entityEl.attributeValue("name");

			long entityGroupId = layoutCache.getEntityGroupId(
				companyId, entityName, name);

			if (entityGroupId == 0) {
				_log.warn(
					"Ignore inherited roles for entity " + entityName +
						" with name " + name);
			}
			else {
				importRolePermissions(
					layoutCache, companyId, resourceName,
					ResourceImpl.SCOPE_GROUP, String.valueOf(groupId), entityEl,
					false);
			}
		}
	}

	protected void importLayoutPermissions(
			LayoutCache layoutCache, long companyId, long groupId,
			Group guestGroup, Layout layout, Element permissionsEl)
		throws PortalException, SystemException {

		String resourceName = Layout.class.getName();
		String resourcePrimKey = layout.getPrimaryKey().toString();

		importGroupPermissions(
			layoutCache, companyId, groupId, resourceName, resourcePrimKey,
			permissionsEl, "community-actions", false);

		if (groupId != guestGroup.getGroupId()) {
			importGroupPermissions(
				layoutCache, companyId, guestGroup.getGroupId(), resourceName,
				resourcePrimKey, permissionsEl, "guest-actions", false);
		}

		importUserPermissions(
			layoutCache, companyId, groupId, resourceName, resourcePrimKey,
			permissionsEl, false);

		importInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsEl, "organization", false);

		importInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsEl, "location", false);

		importInheritedPermissions(
			layoutCache, companyId, resourceName, resourcePrimKey,
			permissionsEl, "user-group", false);
	}

	protected void importLayoutRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			Element rolesEl)
		throws PortalException, SystemException {

		String resourceName = Layout.class.getName();

		importGroupRoles(
			layoutCache, companyId, groupId, resourceName, "community",
			rolesEl);

		importUserRoles(layoutCache, companyId, groupId, resourceName, rolesEl);

		importInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "organization",
			rolesEl);

		importInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "location", rolesEl);

		importInheritedRoles(
			layoutCache, companyId, groupId, resourceName, "user-group",
			rolesEl);
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

			try {
				PortletPreferences prefs =
					PortletPreferencesUtil.findByPrimaryKey(pk);

				String preferences =
					importPortletData(context, portletId, prefs, el);

				if (preferences != null) {
					prefs.setPreferences(preferences);

					PortletPreferencesUtil.update(prefs);
				}
			}
			catch (NoSuchPortletPreferencesException nsppe) {
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

	protected void importPortletPermissions(
			LayoutCache layoutCache, long companyId, long groupId,
			Group guestGroup, Layout layout, Element permissionsEl)
		throws PortalException, SystemException {

		Iterator itr = permissionsEl.elements("portlet").iterator();

		while (itr.hasNext()) {
			Element portletEl = (Element)itr.next();

			String portletId = portletEl.attributeValue("portlet-id");

			String resourceName = PortletImpl.getRootPortletId(portletId);
			String resourcePrimKey = PortletPermission.getPrimaryKey(
				layout.getPlid(), portletId);

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				companyId, resourceName);

			if (portlet == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Do not import portlet permissions for " + portletId +
							" because the portlet does not exist");
				}
			}
			else {
				importGroupPermissions(
					layoutCache, companyId, groupId, resourceName,
					resourcePrimKey, portletEl, "community-actions", true);

				if (groupId != guestGroup.getGroupId()) {
					importGroupPermissions(
						layoutCache, companyId, guestGroup.getGroupId(),
						resourceName, resourcePrimKey, portletEl,
						"guest-actions", true);
				}

				importUserPermissions(
					layoutCache, companyId, groupId, resourceName,
					resourcePrimKey, portletEl, true);

				importInheritedPermissions(
					layoutCache, companyId, resourceName, resourcePrimKey,
					portletEl, "organization", true);

				importInheritedPermissions(
					layoutCache, companyId, resourceName, resourcePrimKey,
					portletEl, "location", true);

				importInheritedPermissions(
					layoutCache, companyId, resourceName, resourcePrimKey,
					portletEl, "user-group", true);
			}
		}
	}

	protected void importPortletPreferences(
			LayoutSet layoutSet, Element parentEl)
		throws PortalException, SystemException {

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
			layoutSet.getCompanyId());

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
							defaultUserId;
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

	protected void importPortletRoles(
			LayoutCache layoutCache, long companyId, long groupId,
			Element rolesEl)
		throws PortalException, SystemException {

		Iterator itr = rolesEl.elements("portlet").iterator();

		while (itr.hasNext()) {
			Element portletEl = (Element)itr.next();

			String portletId = portletEl.attributeValue("portlet-id");

			String resourceName = PortletImpl.getRootPortletId(portletId);

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
					layoutCache, companyId, groupId, resourceName, "community",
					portletEl);

				importUserRoles(
					layoutCache, companyId, groupId, resourceName, portletEl);

				importInheritedRoles(
					layoutCache, companyId, groupId, resourceName,
					"organization", portletEl);

				importInheritedRoles(
					layoutCache, companyId, groupId, resourceName, "location",
					portletEl);

				importInheritedRoles(
					layoutCache, companyId, groupId, resourceName, "user-group",
					portletEl);
			}
		}
	}

	protected void importRolePermissions(
			LayoutCache layoutCache, long companyId, String resourceName,
			int scope, String resourcePrimKey, Element parentEl,
			boolean communityRole)
		throws PortalException, SystemException {

		List roleEls = parentEl.elements("role");

		for (int i = 0; i < roleEls.size(); i++) {
			Element roleEl = (Element)roleEls.get(i);

			String roleName = roleEl.attributeValue("name");

			Role role = layoutCache.getRole(companyId, roleName);

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

	protected String importTheme(LayoutSet layoutSet, byte[] themeZip)
		throws IOException {

		ThemeLoader themeLoader = ThemeLoaderFactory.getDefaultThemeLoader();

		if (themeLoader == null) {
			_log.error("No theme loaders are deployed");

			return null;
		}

		ZipReader zipReader = new ZipReader(new ByteArrayInputStream(themeZip));

		Map entries = zipReader.getEntries();

		String lookAndFeelXML = new String(
			(byte[])entries.get("liferay-look-and-feel.xml"));

		Date now = new Date();

		String themeId =
			layoutSet.getGroupId() + "-" + Time.getShortTimestamp(now);
		String themeName = themeId;

		lookAndFeelXML = StringUtil.replace(
			lookAndFeelXML,
			new String[] {
				"[$GROUP_ID$]", "[$THEME_ID$]", "[$THEME_NAME$]"
			},
			new String[] {
				String.valueOf(layoutSet.getGroupId()), themeId, themeName
			}
		);

		Iterator itr = entries.entrySet().iterator();

		while (itr.hasNext()) {
			Map.Entry entry = (Map.Entry)itr.next();

			String key = (String)entry.getKey();
			byte[] value = (byte[])entry.getValue();

			if (key.equals("liferay-look-and-feel.xml")) {
				value = lookAndFeelXML.getBytes();
			}

			FileUtil.write(
				themeLoader.getRoot() + "/" + themeId + "/" + key, value);
		}

		themeLoader.loadThemes();

		CommLink commLink = CommLink.getInstance();

		MethodWrapper methodWrapper = new MethodWrapper(
			ThemeLoaderFactory.class.getName(), "loadThemes");

		commLink.send(methodWrapper);

		themeId +=
			PortletImpl.WAR_SEPARATOR + themeLoader.getServletContextName();

		return PortalUtil.getJsSafePortletName(themeId);
	}

	protected void importUserPermissions(
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, String resourcePrimKey, Element parentEl,
			boolean portletActions)
		throws PortalException, SystemException {

		Element userPermissionsEl = parentEl.element("user-permissions");

		if (userPermissionsEl == null) {
			return;
		}

		List userActionsEls = userPermissionsEl.elements("user-actions");

		for (int i = 0; i < userActionsEls.size(); i++) {
			Element userActionsEl = (Element)userActionsEls.get(i);

			String emailAddress = userActionsEl.attributeValue("email-address");

			User user = layoutCache.getUser(companyId, groupId, emailAddress);

			if (user == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Ignoring permissions for user with email address " +
							emailAddress);
				}
			}
			else {
				List actions = getActions(userActionsEl);

				Resource resource = layoutCache.getResource(
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
			LayoutCache layoutCache, long companyId, long groupId,
			String resourceName, Element parentEl)
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

			User user = layoutCache.getUser(companyId, groupId, emailAddress);

			if (user == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Ignoring roles for user with email address " +
							emailAddress);
				}
			}
			else {
				importRolePermissions(
					layoutCache, companyId, resourceName,
					ResourceImpl.SCOPE_GROUP, String.valueOf(groupId), userEl,
					false);
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