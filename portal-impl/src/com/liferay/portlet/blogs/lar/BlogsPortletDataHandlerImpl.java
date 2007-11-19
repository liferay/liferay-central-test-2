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

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryUtil;
import com.liferay.util.MapUtil;

import com.thoughtworks.xstream.XStream;

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

/**
 * <a href="BlogsPortletDataHandlerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class BlogsPortletDataHandlerImpl implements PortletDataHandler {

	public PortletDataHandlerControl[] getExportControls()
		throws PortletDataException {

		return new PortletDataHandlerControl[] {_enableExport};
	}

	public PortletDataHandlerControl[] getImportControls()
		throws PortletDataException{

		return new PortletDataHandlerControl[] {_enableImport};
	}

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs)
		throws PortletDataException {

		Map parameterMap = context.getParameterMap();

		boolean exportData = MapUtil.getBoolean(
			parameterMap, _EXPORT_BLOGS_DATA);
		boolean staging = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.STAGING);

		if (_log.isDebugEnabled()) {
			if (exportData) {
				_log.debug("Exporting data is enabled");
			}
			else {
				_log.debug("Exporting data is disabled");
			}
		}

		if (!exportData && !staging) {
			return null;
		}

		try {
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
					entry.setUserUuid(entry.getUserUuid());

					context.addComments(
						BlogsEntry.class, entry.getPrimaryKeyObj());

					context.addRatingsEntries(
						BlogsEntry.class, entry.getPrimaryKeyObj());

					context.addTagsEntries(
						BlogsEntry.class, entry.getPrimaryKeyObj());
				}
			}

			String xml = xStream.toXML(entries);

			Element el = root.addElement("blog-entries");

			Document tempDoc = PortalUtil.readDocumentFromXML(xml);

			el.content().add(tempDoc.getRootElement().createCopy());

			return doc.asXML();
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
			parameterMap, _IMPORT_BLOGS_DATA);
		boolean staging = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.STAGING);

		if (_log.isDebugEnabled()) {
			if (importData) {
				_log.debug("Importing data is enabled");
			}
			else {
				_log.debug("Importing data is disabled");
			}
		}

		if (!importData && !staging) {
			return null;
		}

		boolean mergeData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.MERGE_DATA);

		try {
			XStream xStream = new XStream();

			Document doc = PortalUtil.readDocumentFromXML(data);

			Element root = doc.getRootElement();

			// Entries

			Element el = root.element("blog-entries").element("list");

			Document tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			List entries = (List)xStream.fromXML(tempDoc.asXML());

			Iterator itr = entries.iterator();

			while (itr.hasNext()) {
				BlogsEntry entry = (BlogsEntry)itr.next();

				importEntry(context, mergeData, entry);
			}

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	protected void importEntry(
			PortletDataContext context, boolean mergeData, BlogsEntry entry)
		throws Exception {

		long userId = context.getUserId(entry.getUserUuid());
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
					entry.getUuid(), userId, plid, entry.getCategoryId(),
					entry.getTitle(), entry.getContent(), displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, themeDisplay, tagsEntries,
					addCommunityPermissions, addGuestPermissions);
			}
			else {
				existingEntry = BlogsEntryLocalServiceUtil.updateEntry(
					userId, existingEntry.getEntryId(), entry.getCategoryId(),
					entry.getTitle(), entry.getContent(), displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, themeDisplay, tagsEntries);
			}
		}
		else {
			existingEntry = BlogsEntryLocalServiceUtil.addEntry(
				userId, plid, entry.getCategoryId(), entry.getTitle(),
				entry.getContent(), displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				themeDisplay, tagsEntries, addCommunityPermissions,
				addGuestPermissions);
		}

		context.importComments(
			BlogsEntry.class, entry.getPrimaryKeyObj(),
			existingEntry.getPrimaryKeyObj(), context.getGroupId());

		context.importRatingsEntries(
			BlogsEntry.class, entry.getPrimaryKeyObj(),
			existingEntry.getPrimaryKeyObj());
	}

	private static final String _EXPORT_BLOGS_DATA =
		"export-" + PortletKeys.BLOGS + "-data";

	private static final String _IMPORT_BLOGS_DATA =
		"import-" + PortletKeys.BLOGS + "-data";

	private static final PortletDataHandlerBoolean _enableExport =
		new PortletDataHandlerBoolean(_EXPORT_BLOGS_DATA, true, null);

	private static final PortletDataHandlerBoolean _enableImport =
		new PortletDataHandlerBoolean(_IMPORT_BLOGS_DATA, true, null);

	private static Log _log =
		LogFactory.getLog(BlogsPortletDataHandlerImpl.class);

}