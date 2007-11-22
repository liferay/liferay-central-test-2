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
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryUtil;

import com.thoughtworks.xstream.XStream;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

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

		return new PortletDataHandlerControl[] {
			_entries, _comments, _ratings, _tags};
	}

	public PortletDataHandlerControl[] getImportControls()
		throws PortletDataException{

		return new PortletDataHandlerControl[] {
			_entries, _comments, _ratings, _tags};
	}

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs)
		throws PortletDataException {

		boolean exportComments = context.getBooleanParameter(
			_NAMESPACE, _COMMENTS);
		boolean exportRatings = context.getBooleanParameter(
			_NAMESPACE, _RATINGS);
		boolean exportTags = context.getBooleanParameter(_NAMESPACE, _TAGS);

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

					if (exportComments) {
						context.addComments(
							BlogsEntry.class, entry.getPrimaryKeyObj());
					}

					if (exportRatings) {
						context.addRatingsEntries(
							BlogsEntry.class, entry.getPrimaryKeyObj());
					}

					if (exportTags) {
						context.addTagsEntries(
							BlogsEntry.class, entry.getPrimaryKeyObj());
					}
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

		boolean importComments = context.getBooleanParameter(
			_NAMESPACE, _COMMENTS);
		boolean importRatings = context.getBooleanParameter(
			_NAMESPACE, _RATINGS);
		boolean importTags = context.getBooleanParameter(_NAMESPACE, _TAGS);

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

				importEntry(
					context, importComments, importRatings,
					importTags, entry);
			}

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	protected void importEntry(
			PortletDataContext context, boolean importComments,
			boolean importRatings, boolean importTags, BlogsEntry entry)
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

		String[] tagsEntries = null;

		if (importTags) {
			tagsEntries = context.getTagsEntries(
				BlogsEntry.class, entry.getPrimaryKeyObj());
		}

		boolean addCommunityPermissions = true;
		boolean addGuestPermissions = true;

		BlogsEntry existingEntry = null;

		if (context.getDataStrategy().equals(
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

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

		if (importComments) {
			context.importComments(
				BlogsEntry.class, entry.getPrimaryKeyObj(),
				existingEntry.getPrimaryKeyObj(), context.getGroupId());
		}

		if (importRatings) {
			context.importRatingsEntries(
				BlogsEntry.class, entry.getPrimaryKeyObj(),
				existingEntry.getPrimaryKeyObj());
		}
	}

	private static final String _ENTRIES = "entries";

	private static final String _COMMENTS = "comments";

	private static final String _RATINGS = "ratings";

	private static final String _TAGS = "tags";

	private static final String _NAMESPACE = "blogs";

	private static final PortletDataHandlerBoolean _entries =
		new PortletDataHandlerBoolean(_NAMESPACE, _ENTRIES, true, true, null);

	private static final PortletDataHandlerBoolean _comments =
		new PortletDataHandlerBoolean(_NAMESPACE, _COMMENTS, true, null);

	private static final PortletDataHandlerBoolean _ratings =
		new PortletDataHandlerBoolean(_NAMESPACE, _RATINGS, true, null);

	private static final PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, _TAGS, true, null);

	private static Log _log =
		LogFactory.getLog(BlogsPortletDataHandlerImpl.class);

}