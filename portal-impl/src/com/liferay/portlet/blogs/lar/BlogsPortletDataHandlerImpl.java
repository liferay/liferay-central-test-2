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

package com.liferay.portlet.blogs.lar;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.model.BlogsStatsUser;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.BlogsStatsUserLocalServiceUtil;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryUtil;
import com.liferay.portlet.blogs.service.persistence.BlogsStatsUserUtil;
import com.liferay.util.MapUtil;
import com.liferay.util.xml.XMLFormatter;

import com.thoughtworks.xstream.XStream;

import java.io.StringReader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="BlogsPortletDataHandlerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class BlogsPortletDataHandlerImpl implements PortletDataHandler {

	public PortletDataHandlerControl[] getExportControls()
		throws PortletDataException {

		return new PortletDataHandlerControl[] {
			_enableExport, _enableStatsExport
		};
	}

	public PortletDataHandlerControl[] getImportControls()
		throws PortletDataException{

		return new PortletDataHandlerControl[] {
			_enableImport, _enableStatsImport
		};
	}

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs)
		throws PortletDataException {

		Map parameterMap = context.getParameterMap();

		boolean exportData = MapUtil.getBoolean(
			parameterMap, _EXPORT_BLOGS_DATA);

		if (_log.isDebugEnabled()) {
			if (exportData) {
				_log.debug("Exporting data is enabled");
			}
			else {
				_log.debug("Exporting data is disabled");
			}
		}

		if (!exportData) {
			return null;
		}

		boolean exportStats = MapUtil.getBoolean(
			parameterMap, _EXPORT_BLOGS_STATS);

		try {
			SAXReader reader = SAXReaderFactory.getInstance();

			XStream xStream = new XStream();

			Document doc = DocumentHelper.createDocument();

			Element root = doc.addElement("blogs-data");

			root.addAttribute("group-id", String.valueOf(context.getGroupId()));

			// Entries

			List entries = BlogsEntryUtil.findByGroupId(context.getGroupId());

			Iterator itr = entries.iterator();

			while (itr.hasNext()) {
				BlogsEntry entry = (BlogsEntry)itr.next();

				if (context.addPrimaryKey(
						BlogsEntry.class, entry.getPrimaryKeyObj())) {

					itr.remove();
				}
				else {
					context.addRatingsEntries(
						BlogsEntry.class, entry.getPrimaryKeyObj());

					context.addTagsEntries(
						BlogsEntry.class, entry.getPrimaryKeyObj());
				}
			}

			String xml = xStream.toXML(entries);

			Element el = root.addElement("blog-entries");

			Document tempDoc = reader.read(new StringReader(xml));

			el.content().add(tempDoc.getRootElement().createCopy());

			// Stats users

			List statsUsers = new ArrayList();

			if (exportStats) {
				statsUsers = BlogsStatsUserUtil.findByGroupId(
					context.getGroupId());

				itr = statsUsers.iterator();

				while (itr.hasNext()) {
					BlogsStatsUser statsUser = (BlogsStatsUser)itr.next();

					if (context.addPrimaryKey(
							BlogsStatsUser.class,
							statsUser.getPrimaryKeyObj())) {

						itr.remove();
					}
				}
			}

			xml = xStream.toXML(statsUsers);

			el = root.addElement("blog-stats-users");

			tempDoc = reader.read(new StringReader(xml));

			el.content().add(tempDoc.getRootElement().createCopy());

			return XMLFormatter.toString(doc);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletPreferences importData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs, String data)
		throws PortletDataException {

		Map parameterMap = context.getParameterMap();

		boolean importData = MapUtil.getBoolean(
			parameterMap, _IMPORT_BLOGS_DATA, _enableImport.getDefaultState());

		if (_log.isDebugEnabled()) {
			if (importData) {
				_log.debug("Importing data is enabled");
			}
			else {
				_log.debug("Importing data is disabled");
			}
		}

		if (!importData) {
			return null;
		}

		boolean mergeData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.MERGE_DATA);

		boolean importStats = MapUtil.getBoolean(
			parameterMap, _IMPORT_BLOGS_STATS);

		try {
			SAXReader reader = SAXReaderFactory.getInstance();

			XStream xStream = new XStream();

			Document doc = reader.read(new StringReader(data));

			Element root = doc.getRootElement();

			// Entries

			Element el = root.element("blog-entries").element("list");

			Document tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			List entries = (List)xStream.fromXML(
				XMLFormatter.toString(tempDoc));

			Iterator itr = entries.iterator();

			while (itr.hasNext()) {
				BlogsEntry entry = (BlogsEntry)itr.next();

				importEntry(context, mergeData, entry);
			}

			// Stats users

			if (importStats) {
				el = root.element("blog-stats-users").element("list");

				tempDoc = DocumentHelper.createDocument();

				tempDoc.content().add(el.createCopy());

				List statsUsers = (List) xStream.fromXML(
					XMLFormatter.toString(tempDoc));

				itr = statsUsers.iterator();

				while (itr.hasNext()) {
					BlogsStatsUser statsUser = (BlogsStatsUser)itr.next();

					importStatsUser(context, statsUser);
				}
			}

			// No special modification to the incoming portlet preferences
			// needed

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	protected void importEntry(
			PortletDataContext context, boolean mergeData, BlogsEntry entry)
		throws Exception {

		long plid = context.getPlid();

		Calendar displayDateCal = CalendarFactoryUtil.getCalendar();

		displayDateCal.setTime(entry.getDisplayDate());

		int displayDateMonth = displayDateCal.get(Calendar.MONTH);
		int displayDateDay = displayDateCal.get(Calendar.DATE);
		int displayDateYear = displayDateCal.get(Calendar.YEAR);
		int displayDateHour = displayDateCal.get(Calendar.HOUR);
		int displayDateMinute = displayDateCal.get(Calendar.MINUTE);

		ThemeDisplay themeDisplay = null;
		String[] tagsEntries = context.getTagsEntries(
			BlogsEntry.class, entry.getPrimaryKeyObj());

		boolean addCommunityPermissions = true;
		boolean addGuestPermissions = true;

		BlogsEntry existingEntry = null;

		if (mergeData) {
			existingEntry = BlogsEntryUtil.fetchByUUID_G(
				entry.getUuid(), context.getGroupId());

			if (existingEntry == null) {
				existingEntry = BlogsEntryLocalServiceUtil.addEntry(
					entry.getUuid(), entry.getUserId(), plid,
					entry.getCategoryId(), entry.getTitle(), entry.getContent(),
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, themeDisplay,
					tagsEntries, addCommunityPermissions, addGuestPermissions);
			}
			else {
				existingEntry = BlogsEntryLocalServiceUtil.updateEntry(
					entry.getUserId(), existingEntry.getEntryId(),
					entry.getCategoryId(), entry.getTitle(), entry.getContent(),
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, themeDisplay,
					tagsEntries);
			}
		}
		else {
			existingEntry = BlogsEntryLocalServiceUtil.addEntry(
				entry.getUserId(), plid, entry.getCategoryId(),
				entry.getTitle(), entry.getContent(), displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, themeDisplay, tagsEntries,
				addCommunityPermissions, addGuestPermissions);
		}

		context.importRatingsEntries(
			BlogsEntry.class, entry.getPrimaryKeyObj(),
			existingEntry.getPrimaryKeyObj());
	}

	protected void importStatsUser(
			PortletDataContext context, BlogsStatsUser statsUser)
		throws Exception {

		try {
			UserUtil.findByPrimaryKey(statsUser.getUserId());

			long groupId = context.getGroupId();

			BlogsStatsUserLocalServiceUtil.updateStatsUser(
				groupId, statsUser.getUserId(), statsUser.getLastPostDate());
		}
		catch (NoSuchUserException nsue) {
			_log.error(
				"Could not find the user for stats " +
					statsUser.getStatsUserId());
		}
	}

	private static final String _EXPORT_BLOGS_DATA =
		"export-" + PortletKeys.BLOGS + "-data";

	private static final String _IMPORT_BLOGS_DATA =
		"import-" + PortletKeys.BLOGS + "-data";

	private static final String _EXPORT_BLOGS_STATS =
		"export-" + PortletKeys.BLOGS + "-stats";

	private static final String _IMPORT_BLOGS_STATS =
		"import-" + PortletKeys.BLOGS + "-stats";

	private static final PortletDataHandlerBoolean _enableExport =
		new PortletDataHandlerBoolean(_EXPORT_BLOGS_DATA, true, null);

	private static final PortletDataHandlerBoolean _enableImport =
		new PortletDataHandlerBoolean(_IMPORT_BLOGS_DATA, true, null);

	private static final PortletDataHandlerBoolean _enableStatsExport =
		new PortletDataHandlerBoolean(_EXPORT_BLOGS_STATS, true, null);

	private static final PortletDataHandlerBoolean _enableStatsImport =
		new PortletDataHandlerBoolean(_IMPORT_BLOGS_STATS, true, null);

	private static Log _log =
		LogFactory.getLog(BlogsPortletDataHandlerImpl.class);

}