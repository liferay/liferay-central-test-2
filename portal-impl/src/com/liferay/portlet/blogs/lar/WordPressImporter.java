/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.StatusConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Namespace;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.lar.PortletDataContext;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * <a href="WordPressImporter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class WordPressImporter {

	public static void importData(PortletDataContext context)
		throws PortalException, SystemException {

		Map<String, Long> userMap = getWordPressUserMap(context);

		String path = getWordPressPath(context, _EXPORT_FILE);

		String fileData = context.getZipEntryAsString(path);

		if (Validator.isNull(fileData)) {
			return;
		}

		Document wordPressDoc = null;

		try {
			wordPressDoc = SAXReaderUtil.read(fileData);
		}
		catch (DocumentException de) {
			_log.error("Reading " + path, de);

			return;
		}

		User defaultUser = UserLocalServiceUtil.getDefaultUser(
			context.getCompanyId());

		Element root = wordPressDoc.getRootElement();

		List<Element> entryEls = root.element("channel").elements("item");

		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			_DATE_FORMAT);

		dateFormat.setTimeZone(TimeZone.getTimeZone(StringPool.UTC));

		for (Element entryEl : entryEls) {
			importEntry(context, defaultUser, userMap, dateFormat, entryEl);
		}
	}

	protected static String getWordPressPath(
		PortletDataContext context, String fileName) {
		return context.getSourcePortletPath(PortletKeys.BLOGS).concat(
			StringPool.SLASH).concat(fileName);
	}

	protected static Map<String, Long> getWordPressUserMap(
		PortletDataContext context) {

		Map<String, Long> userMap = new HashMap<String, Long>();

		String path = getWordPressPath(context, _USER_MAP_FILE);

		String fileData = context.getZipEntryAsString(path);

		if (Validator.isNull(fileData)) {
			return userMap;
		}

		Document doc = null;

		try {
			doc = SAXReaderUtil.read(fileData);
		}
		catch(DocumentException de) {
			_log.error(de.getMessage(), de);

			return userMap;
		}

		Element root = doc.getRootElement();

		List<Element> userEls = root.elements("wordpress-user");

		for (Element userEl : userEls) {
			try {
				User user = UserUtil.findByC_EA(
					context.getCompanyId(),
					userEl.attributeValue("email-address"));

				userMap.put(userEl.getTextTrim(), user.getUserId());
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"User for {" + context.getCompanyId() + ", " +
							userEl.attributeValue("email-address") + "}", e);
				}
			}
		}

		return userMap;
	}

	protected static void importComment(
			PortletDataContext context, User defaultUser,
			MBMessageDisplay messageDisplay, Map<Long, Long> messageIdMap,
			BlogsEntry entry, Element commentEl)
		throws PortalException, SystemException {

		long commentId = GetterUtil.getLong(
			commentEl.elementTextTrim(
				SAXReaderUtil.createQName("comment_id", _NS_WP)));

		String commentContent = commentEl.elementTextTrim(
			SAXReaderUtil.createQName("comment_content", _NS_WP));

		if (Validator.isNull(commentContent)) {
			return;
		}

		String commentAuthor = commentEl.elementTextTrim(
			SAXReaderUtil.createQName("comment_author", _NS_WP));

		commentAuthor = commentAuthor.substring(
			0, Math.min(75, commentAuthor.length()));

		long commentParentId = GetterUtil.getLong(
			commentEl.elementTextTrim(
				SAXReaderUtil.createQName("comment_parent", _NS_WP)));

		if (commentParentId == 0) {
			commentParentId =
				messageDisplay.getMessage().getMessageId();
		}
		else {
			commentParentId = messageIdMap.get(commentParentId);
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(context.getGroupId());

		MBMessage message = MBMessageLocalServiceUtil.addDiscussionMessage(
			defaultUser.getUserId(), commentAuthor, BlogsEntry.class.getName(),
			entry.getEntryId(), messageDisplay.getThread().getThreadId(),
			commentParentId, null, commentContent, serviceContext);

		messageIdMap.put(commentId, message.getMessageId());
	}

	protected static void importEntry(
			PortletDataContext context, User defaultUser,
			Map<String, Long> userMap, DateFormat dateFormat, Element entryEl)
		throws PortalException, SystemException {

		long userId = context.getUserId(null);

		String creator = entryEl.elementText(
			SAXReaderUtil.createQName("creator", _NS_DC));

		if (userMap.containsKey(creator)) {
			userId = userMap.get(creator);
		}

		String title = entryEl.elementTextTrim("title");

		if (Validator.isNull(title)) {
			title = entryEl.elementTextTrim(
				SAXReaderUtil.createQName("post_name", _NS_WP));
		}

		String content = entryEl.elementText(
			SAXReaderUtil.createQName("encoded", _NS_CONTENT));

		content = content.replaceAll("\\n", "\n<br />");

		// LPS-1425

		if (Validator.isNull(content)) {
			content = "<br />";
		}

		String dateText = entryEl.elementTextTrim(
			SAXReaderUtil.createQName("post_date_gmt", _NS_WP));

		Date postDate = new Date();

		try {
			postDate = dateFormat.parse(dateText);
		}
		catch (ParseException pe) {
			_log.warn("Parse " + dateText, pe);
		}

		Calendar cal = Calendar.getInstance();

		cal.setTime(postDate);

		int displayDateMonth = cal.get(Calendar.MONTH);
		int displayDateDay = cal.get(Calendar.DAY_OF_MONTH);
		int displayDateYear = cal.get(Calendar.YEAR);
		int displayDateHour = cal.get(Calendar.HOUR_OF_DAY);
		int displayDateMinute = cal.get(Calendar.MINUTE);

		String statusText = entryEl.elementTextTrim(
			SAXReaderUtil.createQName("status", _NS_WP));

		int status = StatusConstants.APPROVED;

		if (statusText.equalsIgnoreCase("draft")) {
			status = StatusConstants.DRAFT;
		}

		String pingStatusText = entryEl.elementTextTrim(
			SAXReaderUtil.createQName("ping_status", _NS_WP));

		boolean allowTrackbacks = pingStatusText.equalsIgnoreCase("open");

		String[] assetTagNames = null;

		String categoryText = entryEl.elementTextTrim("category");

		if (Validator.isNotNull(categoryText)) {
			assetTagNames = new String[] {categoryText};
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAssetTagNames(assetTagNames);
		serviceContext.setScopeGroupId(context.getGroupId());
		serviceContext.setStatus(status);

		BlogsEntry entry = null;

		try {
			entry = BlogsEntryLocalServiceUtil.addEntry(
				null, userId, title, content, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				allowTrackbacks, null, serviceContext);
		}
		catch (Exception e) {
			_log.error("Add entry " + title, e);

			return;
		}

		MBMessageDisplay messageDisplay =
			MBMessageLocalServiceUtil.getDiscussionMessageDisplay(
				userId, BlogsEntry.class.getName(), entry.getEntryId(),
				StatusConstants.APPROVED);

		Map<Long, Long> messageIdMap = new HashMap<Long, Long>();

		List<Node> commentNodes = entryEl.selectNodes(
			"wp:comment", "wp:comment_parent/text()");

		for (Node commentNode : commentNodes) {
			Element commentEl = (Element)commentNode;

			importComment(
				context, defaultUser, messageDisplay, messageIdMap, entry,
				commentEl);
		}
	}

	private static final String _DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private static final String _EXPORT_FILE = "wordpress.xml";

	private static final Namespace _NS_CONTENT = SAXReaderUtil.createNamespace(
		"content", "http://purl.org/rss/1.0/modules/content/");

	private static final Namespace _NS_DC = SAXReaderUtil.createNamespace(
		"dc", "http://purl.org/dc/elements/1.1/");

	private static final Namespace _NS_WP = SAXReaderUtil.createNamespace(
		"wp", "http://wordpress.org/export/1.0/");

	private static final String _USER_MAP_FILE = "wordpress-user-map.xml";

	private static Log _log = LogFactoryUtil.getLog(WordPressImporter.class);

}