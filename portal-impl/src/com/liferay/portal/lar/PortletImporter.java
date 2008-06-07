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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.LARFileException;
import com.liferay.portal.LARTypeException;
import com.liferay.portal.LayoutImportException;
import com.liferay.portal.NoSuchPortletPreferencesException;
import com.liferay.portal.PortalException;
import com.liferay.portal.PortletIdException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.User;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletItemLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.PortletPreferencesUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.DocumentUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesSerializer;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.util.MapUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

/**
 * <a href="PortletImporter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Joel Kozikowski
 * @author Charles May
 * @author Raymond Augé
 * @author Jorge Ferrer
 * @author Bruno Farache
 *
 */
public class PortletImporter {

	public void importPortletInfo(
			long userId, long plid, String portletId,
			Map<String, String[]> parameterMap, InputStream is)
		throws PortalException, SystemException {

		boolean deletePortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.DELETE_PORTLET_DATA);
		boolean importPortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_DATA);
		boolean importPortletArchivedSetups = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS);
		boolean importPortletSetup = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_SETUP);
		boolean importUserPreferences = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_USER_PREFERENCES);
		String userIdStrategy = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.USER_ID_STRATEGY);

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		long companyId = layout.getCompanyId();

		User user = UserUtil.findByPrimaryKey(userId);

		UserIdStrategy strategy = getUserIdStrategy(user, userIdStrategy);

		ZipReader zipReader = new ZipReader(is);

		PortletDataContext context = new PortletDataContextImpl(
			companyId, layout.getGroupId(), parameterMap, new HashSet(),
			strategy, zipReader);

		context.setPlid(plid);

		// Zip

		Element root = null;

		// Manifest

		String xml = context.getZipEntryAsString("/manifest.xml");

		try {
			Document doc = DocumentUtil.readDocumentFromXML(xml);

			root = doc.getRootElement();
		}
		catch (Exception e) {
			throw new LARFileException(
				"Cannot locate a manifest in this LAR file.");
		}

		// Build compatibility

		Element header = root.element("header");

		int buildNumber = ReleaseInfo.getBuildNumber();

		int importBuildNumber = GetterUtil.getInteger(
			header.attributeValue("build-number"));

		if (buildNumber != importBuildNumber) {
			throw new LayoutImportException(
				"LAR build number " + importBuildNumber + " does not match " +
					"portal build number " + buildNumber);
		}

		// Type compatibility

		String type = header.attributeValue("type");

		if (!type.equals("portlet")) {
			throw new LARTypeException(
				"Invalid type of LAR file (" + type + ")");
		}

		// Portlet compatibility

		String rootPortletId = header.attributeValue("root-portlet-id");

		if (!PortletConstants.getRootPortletId(portletId).equals(
				rootPortletId)) {

			throw new PortletIdException("Invalid portlet id " + rootPortletId);
		}

		// Import GroupId

		long importGroupId = GetterUtil.getLong(
			header.attributeValue("group-id"));

		context.setImportGroupId(importGroupId);

		// Read comments, ratings, and tags to make them available to the data
		// handlers through the context

		readComments(context, root);
		readRatings(context, root);
		readTags(context, root);

		// Delete portlet data

		if (_log.isDebugEnabled()) {
			_log.debug("Deleting portlet data");
		}

		if (deletePortletData) {
			deletePortletData(context, portletId, plid);
		}

		Element portletRefEl = root.element("portlet");
		Element portletEl = null;

		try {
			Document portletDoc = DocumentUtil.readDocumentFromXML(
				context.getZipEntryAsString(
					portletRefEl.attributeValue("path")));

			portletEl = portletDoc.getRootElement();
		}
		catch (DocumentException de) {
			throw new SystemException(de);
		}

		// Portlet preferences

		importPortletPreferences(
			context, layout.getCompanyId(), layout.getGroupId(), plid,
			portletId, portletEl, importPortletSetup,
			importPortletArchivedSetups, importUserPreferences);

		// Portlet data

		if (_log.isDebugEnabled()) {
			_log.debug("Importing portlet data");
		}

		if (importPortletData) {
			importPortletData(
				context, portletId, plid, portletEl.element("portlet-data"));
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				"Importing portlet data takes " + stopWatch.getTime() + " ms");
		}
	}

	protected void deletePortletData(
			PortletDataContext context, String portletId, long plid)
		throws PortalException, SystemException {

		try {
			PortletPreferences portletPreferences =
				PortletPreferencesUtil.findByO_O_P_P(
					PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid,
					portletId);

			String preferences = deletePortletData(
				context, portletId, portletPreferences);

			if (preferences != null) {
				portletPreferences.setPreferences(preferences);

				PortletPreferencesUtil.update(portletPreferences, false);
			}
		}
		catch (NoSuchPortletPreferencesException nsppe) {
		}
	}

	protected String deletePortletData(
			PortletDataContext context, String portletId,
			PortletPreferences portletPreferences)
		throws PortalException, SystemException {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			context.getCompanyId(), portletId);

		if (portlet == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not delete portlet data for " + portletId +
						" because the portlet does not exist");
			}

			return null;
		}

		PortletDataHandler portletDataHandler =
			portlet.getPortletDataHandlerInstance();

		if (portletDataHandler == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not delete portlet data for " + portletId +
						" because the portlet does not have a " +
							"PortletDataHandler");
			}

			return null;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Deleting data for " + portletId);
		}

		PortletPreferencesImpl prefsImpl =
			(PortletPreferencesImpl)PortletPreferencesSerializer.fromDefaultXML(
				portletPreferences.getPreferences());

		prefsImpl = (PortletPreferencesImpl)portletDataHandler.deleteData(
			context, portletId, prefsImpl);

		if (prefsImpl == null) {
			return null;
		}

		return PortletPreferencesSerializer.toXML(prefsImpl);
	}

	protected UserIdStrategy getUserIdStrategy(
		User user, String userIdStrategy) {

		if (UserIdStrategy.ALWAYS_CURRENT_USER_ID.equals(userIdStrategy)) {
			return new AlwaysCurrentUserIdStrategy(user);
		}

		return new CurrentUserIdStrategy(user);
	}

	protected void importPortletData(
			PortletDataContext context, String portletId, long plid,
			Element portletDataRefEl)
		throws PortalException, SystemException {

		try {
			PortletPreferences portletPreferences =
				PortletPreferencesUtil.findByO_O_P_P(
					PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid,
					portletId);

			String preferences = importPortletData(
				context, portletId, portletPreferences, portletDataRefEl);

			if (preferences != null) {
				portletPreferences.setPreferences(preferences);

				PortletPreferencesUtil.update(portletPreferences, false);
			}
		}
		catch (NoSuchPortletPreferencesException nsppe) {
		}
	}

	protected String importPortletData(
			PortletDataContext context, String portletId,
			PortletPreferences portletPreferences, Element portletDataRefEl)
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

		PortletDataHandler portletDataHandler =
			portlet.getPortletDataHandlerInstance();

		if (portletDataHandler == null) {
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
				portletPreferences.getPreferences());

		String portletData = context.getZipEntryAsString(
			portletDataRefEl.attributeValue("path"));

		prefsImpl = (PortletPreferencesImpl)portletDataHandler.importData(
			context, portletId, prefsImpl, portletData);

		if (prefsImpl == null) {
			return null;
		}

		return PortletPreferencesSerializer.toXML(prefsImpl);
	}

	protected void importPortletPreferences(
			PortletDataContext context, long companyId, long groupId, long plid,
			String portletId, Element parentEl, boolean importPortletSetup,
			boolean importPortletArchivedSetups, boolean importUserPreferences)
		throws PortalException, SystemException {

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		List<Element> prefsEls = parentEl.elements("portlet-preference");

		for (Element prefEl : prefsEls) {
			String path = prefEl.attributeValue("path");

			if (context.isPathNotProcessed(path)) {

				Element el = null;
				String preferences = null;

				try {
					preferences = context.getZipEntryAsString(path);

					Document prefsDoc = DocumentUtil.readDocumentFromXML(
						preferences);

					el = prefsDoc.getRootElement();
				}
				catch (DocumentException de) {
					throw new SystemException(de);
				}

				long ownerId = GetterUtil.getLong(el.attributeValue("owner-id"));
				int ownerType = GetterUtil.getInteger(
					el.attributeValue("owner-type"));

				if (ownerType == PortletKeys.PREFS_OWNER_TYPE_COMPANY) {
					continue;
				}

				if (((ownerType == PortletKeys.PREFS_OWNER_TYPE_GROUP) ||
					 (ownerType == PortletKeys.PREFS_OWNER_TYPE_LAYOUT)) &&
					!importPortletSetup) {

					continue;
				}

				if ((ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) &&
					!importPortletArchivedSetups) {

					continue;
				}

				if ((ownerType == PortletKeys.PREFS_OWNER_TYPE_USER) &&
					(ownerId != PortletKeys.PREFS_OWNER_ID_DEFAULT) &&
					!importUserPreferences) {

					continue;
				}

				if (ownerType == PortletKeys.PREFS_OWNER_TYPE_GROUP) {
					plid = PortletKeys.PREFS_PLID_SHARED;
					ownerId = context.getGroupId();
				}

				boolean defaultUser = GetterUtil.getBoolean(
					el.attributeValue("default-user"));

				if (portletId == null) {
					portletId = el.attributeValue("portlet-id");
				}

				if (ownerType == PortletKeys.PREFS_OWNER_TYPE_ARCHIVED) {
					String userUuid = el.attributeValue("archive-user-uuid");
					String name = el.attributeValue("archive-name");

					long userId = context.getUserId(userUuid);

					PortletItem portletItem =
						PortletItemLocalServiceUtil.updatePortletItem(
							userId, groupId, name, portletId,
							PortletPreferences.class.getName());

					plid = 0;
					ownerId = portletItem.getPortletItemId();
				}

				if (defaultUser) {
					ownerId = defaultUserId;
				}

				try {
					PortletPreferencesLocalServiceUtil.deletePortletPreferences(
						ownerId, ownerType, plid, portletId);
				}
				catch (NoSuchPortletPreferencesException nsppe) {
				}

				long portletPreferencesId = CounterLocalServiceUtil.increment();

				PortletPreferences portletPreferences =
					PortletPreferencesUtil.create(portletPreferencesId);

				portletPreferences.setOwnerId(ownerId);
				portletPreferences.setOwnerType(ownerType);
				portletPreferences.setPlid(plid);
				portletPreferences.setPortletId(portletId);

				portletPreferences.setPreferences(preferences);

				PortletPreferencesUtil.update(portletPreferences, true);
			}
		}
	}

	protected void readComments(PortletDataContext context, Element parentEl)
		throws SystemException {

		try {
			String xml = context.getZipEntryAsString(
				context.getImportRootPath() + "/comments.xml");

			Document doc = DocumentUtil.readDocumentFromXML(xml);

			Element root = doc.getRootElement();

			List<Element> assets = root.elements("asset");

			for (Element asset : assets) {
				String path = asset.attributeValue("path");
				String className = asset.attributeValue("class-name");
				long classPK = GetterUtil.getLong(
					asset.attributeValue("class-pk"));

				List<ObjectValuePair<String, byte[]>> entries =
					context.getZipFolderEntries(path);

				List<MBMessage> messages = new ArrayList<MBMessage>();

				for (ObjectValuePair<String, byte[]> entry : entries) {
					MBMessage message =
						(MBMessage)context.fromXML(entry.getValue());

					messages.add(message);
				}

				context.addComments(className, new Long(classPK), messages);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected void readRatings(PortletDataContext context, Element parentEl)
		throws SystemException {

		try {
			String xml = context.getZipEntryAsString(
				context.getImportRootPath() + "/ratings.xml");

			Document doc = DocumentUtil.readDocumentFromXML(xml);

			Element root = doc.getRootElement();

			List<Element> assets = root.elements("asset");

			for (Element asset : assets) {
				String path = asset.attributeValue("path");
				String className = asset.attributeValue("class-name");
				long classPK = GetterUtil.getLong(
					asset.attributeValue("class-pk"));

				List<ObjectValuePair<String, byte[]>> entries =
					context.getZipFolderEntries(path);

				List<RatingsEntry> ratings = new ArrayList<RatingsEntry>();

				for (ObjectValuePair<String, byte[]> entry : entries) {
					RatingsEntry rating =
						(RatingsEntry)context.fromXML(entry.getValue());

					ratings.add(rating);
				}

				context.addRatingsEntries(className, new Long(classPK), ratings);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected void readTags(PortletDataContext context, Element parentEl)
		throws SystemException {

		try {
			String xml = context.getZipEntryAsString(
				context.getImportRootPath() + "/tags.xml");

			Document doc = DocumentUtil.readDocumentFromXML(xml);

			Element root = doc.getRootElement();

			List<Element> assets = root.elements("asset");

			for (Element asset : assets) {
				String className = GetterUtil.getString(
					asset.attributeValue("class-name"));
				long classPK = GetterUtil.getLong(
					asset.attributeValue("class-pk"));
				String entries = GetterUtil.getString(
					asset.attributeValue("entries"));

				context.addTagsEntries(
					className, new Long(classPK),
					StringUtil.split(entries, ","));
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	private static Log _log = LogFactory.getLog(PortletImporter.class);

}