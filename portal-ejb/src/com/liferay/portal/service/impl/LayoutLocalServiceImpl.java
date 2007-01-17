/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.PortalException;
import com.liferay.portal.RequiredLayoutException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutReference;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.permission.PortletPermission;
import com.liferay.portal.service.persistence.LayoutFinder;
import com.liferay.portal.service.persistence.LayoutPK;
import com.liferay.portal.service.persistence.LayoutUtil;
import com.liferay.portal.service.persistence.PortletPreferencesPK;
import com.liferay.portal.service.persistence.PortletPreferencesUtil;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.servlet.FriendlyURLPortletPlugin;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.ReleaseInfo;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesSerializer;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.util.CollectionFactory;
import com.liferay.util.GetterUtil;
import com.liferay.util.InstancePool;
import com.liferay.util.LocaleUtil;
import com.liferay.util.Time;
import com.liferay.util.Validator;
import com.liferay.util.xml.XMLFormatter;
import com.liferay.util.zip.ZipReader;
import com.liferay.util.zip.ZipWriter;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
 * @author  Brian Wing Shun Chan
 * @author  Joel Kozikowski
 *
 */
public class LayoutLocalServiceImpl implements LayoutLocalService {

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
			ResourceImpl.TYPE_CLASS, ResourceImpl.SCOPE_INDIVIDUAL,
			layout.getPrimaryKey().toString());

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

		PortletDataContext context = new PortletDataContext(
			layoutSet.getCompanyId(), layoutSet.getGroupId(),
			CollectionFactory.getHashSet());

		Group guestGroup = GroupLocalServiceUtil.getGroup(
			layoutSet.getCompanyId(), GroupImpl.GUEST);

		String guestPrefsOwnerId = null;

		if (!layoutSet.isPrivateLayout()) {
			guestPrefsOwnerId =
				ownerId + StringPool.PERIOD + PortletKeys.PREFS_OWNER_ID_USER +
					StringPool.PERIOD +
						UserImpl.getDefaultUserId(layoutSet.getCompanyId());
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

			Element permissionsEl = layoutEl.addElement("permissions");

			exportLayoutPermissions(
				layout, layout.getGroupId(), permissionsEl,
				"community-actions");

			if (layout.getGroupId() != guestGroup.getGroupId()) {
				exportLayoutPermissions(
					layout, guestGroup.getGroupId(), permissionsEl,
					"guest-actions");
			}

			if (layout.getType().equals(LayoutImpl.TYPE_PORTLET)) {
				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				// Portlet permissions

				Iterator itr2 = layoutTypePortlet.getPortletIds().iterator();

				while (itr2.hasNext()) {
					String portletId = (String)itr2.next();

					if (layoutTypePortlet.hasPortletId(portletId)) {
						Element portletEl = permissionsEl.addElement("portlet");

						portletEl.addAttribute("portlet-id", portletId);

						exportPortletPermissions(
							portletId, layout, layout.getGroupId(), portletEl,
							"community-actions");

						if (layout.getGroupId() != guestGroup.getGroupId()) {
							exportPortletPermissions(
								portletId, layout, guestGroup.getGroupId(),
								portletEl, "guest-actions");
						}
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

		// Portlet preferences

		String groupPrefsOwnerId =
			PortletKeys.PREFS_OWNER_ID_GROUP + StringPool.PERIOD +
				layoutSet.getGroupId();

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

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(ownerId);

		PortletDataContext context = new PortletDataContext(
			layoutSet.getCompanyId(), layoutSet.getGroupId(),
			CollectionFactory.getHashSet());

		Group guestGroup = GroupLocalServiceUtil.getGroup(
			layoutSet.getCompanyId(), GroupImpl.GUEST);

		// XML file

		Element root = null;

		try {
			ZipReader zipReader = new ZipReader(file);

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
			throw new LayoutImportException();
		}

		// Look and feel

		String themeId = header.attributeValue("theme-id");
		String colorSchemeId = header.attributeValue("color-scheme-id");

		LayoutSetLocalServiceUtil.updateLookAndFeel(
			ownerId, themeId, colorSchemeId);

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

			LayoutUtil.update(layout);

			// Layout permissions

			Element permissionsEl = layoutEl.element("permissions");

			importLayoutPermissions(
				layout, layout.getGroupId(), permissionsEl,
				"community-actions");

			if (layout.getGroupId() != guestGroup.getGroupId()) {
				importLayoutPermissions(
					layout, guestGroup.getGroupId(), permissionsEl,
					"guest-actions");
			}

			// Portlet permissions

			Iterator itr2 = permissionsEl.elements("portlet").iterator();

			while (itr2.hasNext()) {
				Element portletEl = (Element)itr2.next();

				String portletId = portletEl.attributeValue("portlet-id");

				importPortletPermissions(
					portletId, layout, layout.getGroupId(), portletEl,
					"community-actions");

				if (layout.getGroupId() != guestGroup.getGroupId()) {
					importPortletPermissions(
						portletId, layout, guestGroup.getGroupId(),
						portletEl, "guest-actions");
				}
			}

			// Portlet preferences

			importPortletPreferences(layoutSet, layoutEl);

			// Portlet data

			importPortletData(context, layout, layoutEl);
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
			String colorSchemeId)
		throws PortalException, SystemException {

		Layout layout = LayoutUtil.findByPrimaryKey(
			new LayoutPK(layoutId, ownerId));

		layout.setThemeId(themeId);
		layout.setColorSchemeId(colorSchemeId);

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

	protected void deleteMissingLayouts(String ownerId, List newLayoutPKs)
		throws SystemException, PortalException {

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

	protected void exportLayoutPermissions(
			Layout layout, long groupId, Element parentEl, String elName)
		throws PortalException, SystemException {

		Element el = parentEl.addElement(elName);

		try {
			Resource resource = ResourceLocalServiceUtil.getResource(
				layout.getCompanyId(), Layout.class.getName(),
				ResourceImpl.TYPE_CLASS, ResourceImpl.SCOPE_INDIVIDUAL,
				layout.getPrimaryKey().toString());

			List permissions = PermissionLocalServiceUtil.getGroupPermissions(
				groupId, resource.getResourceId());

			List actions = ResourceActionsUtil.getActions(permissions);

			for (int i = 0; i < actions.size(); i++) {
				String action = (String)actions.get(i);

				el.addElement("action-key").addText(action);
			}
		}
		catch (NoSuchResourceException nsre) {
			_log.warn(nsre);
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

			String portletId = prefs.getPortletId();

			if (layoutTypePortlet.hasPortletId(portletId)) {
				exportPortletData(context, portletId, prefs, parentEl);
			}
		}
	}

	protected void exportPortletData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs, Element parentEl)
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

		String data = null;

		PortletPreferencesImpl prefsImpl =
			(PortletPreferencesImpl)
				PortletPreferencesSerializer.fromDefaultXML(
					prefs.getPreferences());

		data = portletDataHandler.exportData(context, portletId, prefsImpl);

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
			String portletId, Layout layout, long groupId, Element parentEl,
			String elName)
		throws PortalException, SystemException {

		Element el = parentEl.addElement(elName);

		try {
			String name = PortletImpl.getRootPortletId(portletId);
			String primKey = PortletPermission.getPrimaryKey(
				layout.getPlid(), portletId);

			Resource resource = ResourceLocalServiceUtil.getResource(
				layout.getCompanyId(), name, ResourceImpl.TYPE_CLASS,
				ResourceImpl.SCOPE_INDIVIDUAL, primKey);

			List permissions = PermissionLocalServiceUtil.getGroupPermissions(
				groupId, resource.getResourceId());

			List actions = ResourceActionsUtil.getActions(permissions);

			for (int i = 0; i < actions.size(); i++) {
				String action = (String)actions.get(i);

				el.addElement("action-key").addText(action);
			}
		}
		catch (NoSuchResourceException nsre) {
			_log.warn(nsre);
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

	protected void importLayoutPermissions(
			Layout layout, long groupId, Element parentEl, String elName)
		throws PortalException, SystemException {

		Element el = parentEl.element(elName);

		Resource resource = null;

		try {
			resource = ResourceLocalServiceUtil.getResource(
				layout.getCompanyId(), Layout.class.getName(),
				ResourceImpl.TYPE_CLASS, ResourceImpl.SCOPE_INDIVIDUAL,
				layout.getPrimaryKey().toString());
		}
		catch (NoSuchResourceException nsre) {
			ResourceLocalServiceUtil.addResources(
				layout.getCompanyId(), layout.getGroupId(), null,
				Layout.class.getName(), layout.getPrimaryKey().toString(),
				false, true, true);

			resource = ResourceLocalServiceUtil.getResource(
				layout.getCompanyId(), Layout.class.getName(),
				ResourceImpl.TYPE_CLASS, ResourceImpl.SCOPE_INDIVIDUAL,
				layout.getPrimaryKey().toString());
		}

		List actions = new ArrayList();

		Iterator itr = el.elements("action-key").iterator();

		while (itr.hasNext()) {
			Element actionEl = (Element)itr.next();

			actions.add(actionEl.getText());
		}

		PermissionLocalServiceUtil.setGroupPermissions(
			groupId, (String[])actions.toArray(new String[0]),
			resource.getResourceId());
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

	protected void importPortletPermissions(
			String portletId, Layout layout, long groupId, Element parentEl,
			String elName)
		throws PortalException, SystemException {

		Element el = parentEl.element(elName);

		String name = PortletImpl.getRootPortletId(portletId);
		String primKey = PortletPermission.getPrimaryKey(
			layout.getPlid(), portletId);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			layout.getCompanyId(), name);

		if (portlet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not import portlet permissions for " + portletId +
						" because the portlet does not exist");
			}

			return;
		}

		Resource resource = null;

		try {
			resource = ResourceLocalServiceUtil.getResource(
				layout.getCompanyId(), name, ResourceImpl.TYPE_CLASS,
				ResourceImpl.SCOPE_INDIVIDUAL, primKey);
		}
		catch (NoSuchResourceException nsre) {
			ResourceLocalServiceUtil.addResources(
				layout.getCompanyId(), layout.getGroupId(), null, name, primKey,
				true, true, true);

			resource = ResourceLocalServiceUtil.getResource(
				layout.getCompanyId(), name, ResourceImpl.TYPE_CLASS,
				ResourceImpl.SCOPE_INDIVIDUAL, primKey);
		}

		List actions = new ArrayList();

		Iterator itr = el.elements("action-key").iterator();

		while (itr.hasNext()) {
			Element actionEl = (Element)itr.next();

			actions.add(actionEl.getText());
		}

		PermissionLocalServiceUtil.setGroupPermissions(
			groupId, (String[])actions.toArray(new String[0]),
			resource.getResourceId());
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

			Map portletPlugins =
				PortletLocalServiceUtil.getFriendlyURLPlugins();

			Iterator itr = portletPlugins.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry entry = (Map.Entry)itr.next();

				String className = (String)entry.getValue();

				FriendlyURLPortletPlugin portletPlugin =
					(FriendlyURLPortletPlugin)InstancePool.get(className);

				if (friendlyURL.indexOf(portletPlugin.getMapping()) != -1) {
					LayoutFriendlyURLException lfurle =
						new LayoutFriendlyURLException(
							LayoutFriendlyURLException.KEYWORD_CONFLICT);

					lfurle.setKeywordConflict(portletPlugin.getMapping());

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