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

package com.liferay.portlet.blogs.lar;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Namespace;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.lar.PortletDataContext;
import com.liferay.portal.lar.PortletDataException;
import com.liferay.portal.lar.PortletDataHandler;
import com.liferay.portal.lar.PortletDataHandlerBoolean;
import com.liferay.portal.lar.PortletDataHandlerControl;
import com.liferay.portal.lar.PortletDataHandlerKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="BlogsPortletDataHandlerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Raymond Augé
 *
 */
public class BlogsPortletDataHandlerImpl implements PortletDataHandler {

	public PortletPreferences deleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws PortletDataException {

		try {
			if (!context.addPrimaryKey(
					BlogsPortletDataHandlerImpl.class, "deleteData")) {

				BlogsEntryLocalServiceUtil.deleteEntries(context.getGroupId());
			}

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws PortletDataException {

		try {
			Document doc = SAXReaderUtil.createDocument();

			Element root = doc.addElement("blogs-data");

			root.addAttribute("group-id", String.valueOf(context.getGroupId()));

			List<BlogsEntry> entries = BlogsEntryUtil.findByGroupId(
				context.getGroupId());

			for (BlogsEntry entry : entries) {
				exportEntry(context, root, entry);
			}

			return doc.formattedString();
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_entries, _comments, _ratings, _tags
		};
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {
			_entries, _comments, _ratings, _tags, _wordpress
		};
	}

	public PortletPreferences importData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences, String data)
		throws PortletDataException {

		try {
			Document doc = SAXReaderUtil.read(data);

			Element root = doc.getRootElement();

			List<Element> entryEls = root.elements("entry");

			for (Element entryEl : entryEls) {
				String path = entryEl.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				BlogsEntry entry = (BlogsEntry)context.getZipEntryAsObject(
					path);

				importEntry(context, entry);
			}

			if (context.getBooleanParameter(_NAMESPACE, "wordpress")) {
				importWordpress(context);
			}

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	protected void importWordpress(PortletDataContext context)
		throws PortalException, SystemException {

		Map<String, Long> userMap = getWordpressUserMap(context);

		String path = getWordpressPath(context, "wordpress.xml");

		String fileData = context.getZipEntryAsString(path);

		if (Validator.isNull(fileData)) {
			return;
		}

		Document wordpressDoc = null;

		try {
			wordpressDoc = SAXReaderUtil.read(fileData);

		}
		catch (DocumentException de) {
			_log.error(
				"Processing " + path + ": " + de.getMessage());

			return;
		}

		User defaultUser = UserLocalServiceUtil.getDefaultUser(
			context.getCompanyId());

		Element root = wordpressDoc.getRootElement();

		List<Element> itemEls = root.element("channel").elements("item");

		DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormater.setTimeZone(TimeZone.getTimeZone("UTC"));

		Calendar cal = Calendar.getInstance();

		for (Element itemEl : itemEls) {
			long userId = context.getUserId(null);

			String creator = itemEl.elementText(
				SAXReaderUtil.createQName("creator", _dcNs));

			if (userMap.containsKey(creator)) {
				userId = userMap.get(creator);
			}

			long plid = context.getPlid();

			String title = itemEl.elementTextTrim("title");

			if (Validator.isNull(title)) {
				title = itemEl.elementTextTrim(
					SAXReaderUtil.createQName("post_name", _wpNs));
			}

			String content = itemEl.elementText(
				SAXReaderUtil.createQName("encoded", _contentNs));

			content = content.replaceAll("\\n", "\n<br/>");

			String dateText = itemEl.elementTextTrim(
				SAXReaderUtil.createQName("post_date_gmt", _wpNs));

			Date postDate = new Date();

			try {
				postDate = dateFormater.parse(dateText);
			}
			catch (ParseException pe) {
				_log.warn("Parse " + dateText + ": " + pe.getMessage());
			}

			cal.setTime(postDate);

			int displayDateMonth = cal.get(Calendar.MONTH);
			int displayDateDay = cal.get(Calendar.DAY_OF_MONTH);
			int displayDateYear = cal.get(Calendar.YEAR);
			int displayDateHour = cal.get(Calendar.HOUR_OF_DAY);
			int displayDateMinute = cal.get(Calendar.MINUTE);

			String statusText = itemEl.elementTextTrim(
				SAXReaderUtil.createQName("status", _wpNs));

			boolean draft = statusText.equalsIgnoreCase("draft");

			String pingStatusText = itemEl.elementTextTrim(
				SAXReaderUtil.createQName("ping_status", _wpNs));

			boolean allowTrackbacks =
				pingStatusText.equalsIgnoreCase("open");

			String[] tagsEntries = null;

			String categoryText = itemEl.elementTextTrim("category");

			if (Validator.isNotNull(categoryText)) {
				tagsEntries = new String[] {categoryText};
			}

			BlogsEntry entry = null;

			try {
				entry = BlogsEntryLocalServiceUtil.addEntry(
					userId, plid, title, content.toString(),
					displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour,
					displayDateMinute, draft, allowTrackbacks, null,
					tagsEntries, true, true, null);
			}
			catch (Exception e) {
				_log.error(
					"Entry " + title + ": " + e.getMessage());

				continue;
			}

			MBMessageDisplay messageDisplay =
				MBMessageLocalServiceUtil.getDiscussionMessageDisplay(
					userId, BlogsEntry.class.getName(), entry.getEntryId());

			Map<Long, Long> messageIdMap = new HashMap<Long, Long>();

			List<Node> commentNodes = itemEl.selectNodes(
				"wp:comment", "wp:comment_parent/text()");

			long groupId = context.getGroupId();

			for (Node commentNode : commentNodes) {
				Element commentEl = (Element)commentNode;

				long commentId = GetterUtil.getLong(
					commentEl.elementTextTrim(
						SAXReaderUtil.createQName("comment_id", _wpNs)));

				String commentContent = commentEl.elementTextTrim(
					SAXReaderUtil.createQName("comment_content", _wpNs));

				if (Validator.isNull(commentContent)) {
					continue;
				}

				String commentAuthor = commentEl.elementTextTrim(
					SAXReaderUtil.createQName(
						"comment_author", _wpNs));

				commentAuthor = commentAuthor.substring(
					0, Math.min(75, commentAuthor.length()));

				long commentParentId = GetterUtil.getLong(
					commentEl.elementTextTrim(
						SAXReaderUtil.createQName("comment_parent", _wpNs)));

				if (commentParentId == 0) {
					commentParentId =
						messageDisplay.getMessage().getMessageId();
				}
				else {
					commentParentId = messageIdMap.get(commentParentId);
				}

				MBMessage message =
					MBMessageLocalServiceUtil.addDiscussionMessage(
						defaultUser.getUserId(), commentAuthor, groupId,
						BlogsEntry.class.getName(), entry.getEntryId(),
						messageDisplay.getThread().getThreadId(),
						commentParentId, null, commentContent);

				messageIdMap.put(commentId, message.getMessageId());
			}
		}
	}

	public boolean isPublishToLiveByDefault() {
		return false;
	}

	protected void exportEntry(
			PortletDataContext context, Element root, BlogsEntry entry)
		throws PortalException, SystemException {

		if (!context.isWithinDateRange(entry.getModifiedDate())) {
			return;
		}

		String path = getEntryPath(context, entry);

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		Element entryEl = root.addElement("entry");

		entryEl.addAttribute("path", path);

		if (context.getBooleanParameter(_NAMESPACE, "comments")) {
			context.addComments(BlogsEntry.class, entry.getEntryId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
			context.addRatingsEntries(BlogsEntry.class, entry.getEntryId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			context.addTagsEntries(BlogsEntry.class, entry.getEntryId());
		}

		entry.setUserUuid(entry.getUserUuid());

		context.addZipEntry(path, entry);
	}

	protected String getEntryPath(
		PortletDataContext context, BlogsEntry entry) {

		StringBuilder sb = new StringBuilder();

		sb.append(context.getPortletPath(PortletKeys.BLOGS));
		sb.append("/entries/");
		sb.append(entry.getEntryId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getWordpressPath(
		PortletDataContext context, String fileName) {

		StringBuilder sb = new StringBuilder();

		sb.append(context.getImportPortletPath(PortletKeys.BLOGS));
		sb.append(StringPool.SLASH);
		sb.append(fileName);

		return sb.toString();
	}

	protected void importEntry(PortletDataContext context, BlogsEntry entry)
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

		if (displayDateCal.get(Calendar.AM_PM) == Calendar.PM) {
			displayDateHour += 12;
		}

		boolean draft = entry.isDraft();
		boolean allowTrackbacks = entry.isAllowTrackbacks();
		String[] trackbacks = StringUtil.split(entry.getTrackbacks());

		String[] tagsEntries = null;

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			tagsEntries = context.getTagsEntries(
				BlogsEntry.class, entry.getEntryId());
		}

		boolean addCommunityPermissions = true;
		boolean addGuestPermissions = true;

		ThemeDisplay themeDisplay = null;

		BlogsEntry existingEntry = null;

		if (context.getDataStrategy().equals(
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

			existingEntry = BlogsEntryUtil.fetchByUUID_G(
				entry.getUuid(), context.getGroupId());

			if (existingEntry == null) {
				existingEntry = BlogsEntryLocalServiceUtil.addEntry(
					entry.getUuid(), userId, plid, entry.getTitle(),
					entry.getContent(), displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					draft, allowTrackbacks, trackbacks, tagsEntries,
					addCommunityPermissions, addGuestPermissions, themeDisplay);
			}
			else {
				existingEntry = BlogsEntryLocalServiceUtil.updateEntry(
					userId, existingEntry.getEntryId(), entry.getTitle(),
					entry.getContent(), displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					draft, allowTrackbacks, trackbacks, tagsEntries,
					themeDisplay);
			}
		}
		else {
			existingEntry = BlogsEntryLocalServiceUtil.addEntry(
				userId, plid, entry.getTitle(), entry.getContent(),
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, draft, allowTrackbacks,
				trackbacks, tagsEntries, addCommunityPermissions,
				addGuestPermissions, themeDisplay);
		}

		if (context.getBooleanParameter(_NAMESPACE, "comments")) {
			context.importComments(
				BlogsEntry.class, entry.getEntryId(),
				existingEntry.getEntryId(), context.getGroupId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
			context.importRatingsEntries(
				BlogsEntry.class, entry.getEntryId(),
				existingEntry.getEntryId());
		}
	}

	protected Map<String, Long> getWordpressUserMap(PortletDataContext context) {

		Map<String, Long> userMap = new HashMap<String, Long>();

		String path = getWordpressPath(context, "wordpress-user-map.xml");

		String fileData = context.getZipEntryAsString(path);

		if (Validator.isNull(fileData)) {
			return userMap;
		}

		try {
			Document wordpressUserMapDoc = SAXReaderUtil.read(fileData);

			Element root = wordpressUserMapDoc.getRootElement();

			List<Element> userEls = root.elements("wordpress-user");

			for (Element userEl : userEls) {
				try {
					User user = UserLocalServiceUtil.getUserByEmailAddress(
						context.getCompanyId(),
						userEl.attributeValue("email-address"));

					userMap.put(
						userEl.getTextTrim(),
						user.getUserId());
				}
				catch (Exception e) {
				}

			}
		}
		catch(DocumentException de) {
		}

		return userMap;
	}

	private static Log _log = LogFactory.getLog(
		BlogsPortletDataHandlerImpl.class);

	private static final String _NAMESPACE = "blogs";

	private static final PortletDataHandlerBoolean _entries =
		new PortletDataHandlerBoolean(_NAMESPACE, "entries", true, true);

	private static final PortletDataHandlerBoolean _comments =
		new PortletDataHandlerBoolean(_NAMESPACE, "comments");

	private static final PortletDataHandlerBoolean _ratings =
		new PortletDataHandlerBoolean(_NAMESPACE, "ratings");

	private static final PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

	private static final PortletDataHandlerBoolean _wordpress =
		new PortletDataHandlerBoolean(_NAMESPACE, "wordpress");

	private static final Namespace _contentNs = SAXReaderUtil.createNamespace(
		"content", "http://purl.org/rss/1.0/modules/content/");

	private static final Namespace _wfwNs = SAXReaderUtil.createNamespace(
		"wfw", "http://wellformedweb.org/CommentAPI/");

	private static final Namespace _dcNs = SAXReaderUtil.createNamespace(
		"dc", "http://purl.org/dc/elements/1.1/");

	private static final Namespace _wpNs = SAXReaderUtil.createNamespace(
		"wp", "http://wordpress.org/export/1.0/");

}