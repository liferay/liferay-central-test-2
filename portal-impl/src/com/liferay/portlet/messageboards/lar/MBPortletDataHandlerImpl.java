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

package com.liferay.portlet.messageboards.lar;

import com.liferay.documentlibrary.service.DLServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.DocumentUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.messageboards.NoSuchCategoryException;
import com.liferay.portlet.messageboards.NoSuchMessageException;
import com.liferay.portlet.messageboards.NoSuchThreadException;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageFlag;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBCategoryImpl;
import com.liferay.portlet.messageboards.model.impl.MBMessageImpl;
import com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageFlagLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.persistence.MBBanUtil;
import com.liferay.portlet.messageboards.service.persistence.MBCategoryUtil;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFinderUtil;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFlagUtil;
import com.liferay.portlet.messageboards.service.persistence.MBMessageUtil;
import com.liferay.portlet.messageboards.service.persistence.MBThreadUtil;
import com.liferay.util.MapUtil;
import com.liferay.util.xml.XMLFormatter;

import java.rmi.RemoteException;

import java.util.ArrayList;
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
 * <a href="MBPortletDataHandlerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Raymond Augé
 *
 */
public class MBPortletDataHandlerImpl implements PortletDataHandler {

	public PortletPreferences deleteData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs)
		throws PortletDataException {

		try {
			if (!context.addPrimaryKey(
					MBPortletDataHandlerImpl.class, "deleteData")) {

				MBCategoryLocalServiceUtil.deleteCategories(
					context.getGroupId());
			}

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs)
		throws PortletDataException {

		try {
			Document doc = DocumentHelper.createDocument();

			Element root = doc.addElement("message-boards-data");

			root.addAttribute("group-id", String.valueOf(context.getGroupId()));

			Element categoriesEl = root.addElement("categories");
			Element messagesEl = root.addElement("messages");
			Element messageFlagsEl = root.addElement("message-flags");
			Element userBansEl = root.addElement("user-bans");

			List<MBCategory> categories = MBCategoryUtil.findByGroupId(
				context.getGroupId());

			for (MBCategory category : categories) {
				exportCategory(
					context, categoriesEl, messagesEl, messageFlagsEl,
					category);
			}

			if (context.getBooleanParameter(_NAMESPACE, "user-bans")) {
				List<MBBan> bans = MBBanUtil.findByGroupId(
					context.getGroupId());

				for (MBBan ban : bans) {
					exportUserBan(context, userBansEl, ban);
				}
			}

			return XMLFormatter.toString(doc);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_categoriesAndMessages, _attachments, _userBans, _flags, _ratings,
			_tags
		};
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {
			_categoriesAndMessages, _attachments, _userBans, _flags, _ratings,
			_tags
		};
	}

	public PortletPreferences importData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs, String data)
		throws PortletDataException {

		try {
			Document doc = DocumentUtil.readDocumentFromXML(data);

			Element root = doc.getRootElement();

			List<Element> categoryEls = root.element("categories").elements(
				"category");

			Map<Long, Long> categoryPKs = context.getNewPrimaryKeysMap(
				MBCategory.class);

			for (Element categoryEl : categoryEls) {
				String path = categoryEl.attributeValue("path");

				if (context.isPathNotProcessed(path)) {
					MBCategory category =
						(MBCategory)context.getZipEntryAsObject(path);

					importCategory(context, categoryPKs, category);
				}
			}

			List<Element> messageEls = root.element("messages").elements(
				"message");

			Map<Long, Long> threadPKs = context.getNewPrimaryKeysMap(
				MBThread.class);
			Map<Long, Long> messagePKs = context.getNewPrimaryKeysMap(
				MBMessage.class);

			for (Element messageEl : messageEls) {
				String path = messageEl.attributeValue("path");

				if (context.isPathNotProcessed(path)) {
					MBMessage message = (MBMessage)context.getZipEntryAsObject(
						path);

					importMessage(
						context, categoryPKs, threadPKs, messagePKs, messageEl,
						message);
				}
			}

			if (context.getBooleanParameter(_NAMESPACE, "flags")) {
				List<Element> flagEls = root.element("message-flags").elements(
					"flag");

				for (Element flagEl : flagEls) {
					String path = flagEl.attributeValue("path");

					if (context.isPathNotProcessed(path)) {
						MBMessageFlag flag =
							(MBMessageFlag)context.getZipEntryAsObject(path);

						importFlag(context, messagePKs, flag);
					}
				}
			}

			if (context.getBooleanParameter(_NAMESPACE, "user-bans")) {
				List<Element> banEls = root.element("user-bans").elements(
					"user-ban");

				for (Element banEl : banEls) {
					String path = banEl.attributeValue("path");

					if (context.isPathNotProcessed(path)) {
						MBBan ban = (MBBan)context.getZipEntryAsObject(path);

						importBan(context, ban);
					}
				}
			}

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public boolean isPublishToLiveByDefault() {
		return false;
	}

	protected void exportCategory(
			PortletDataContext context, Element categoriesEl,
			Element messagesEl, Element messageFlagsEl, MBCategory category)
		throws PortalException, SystemException {

		if (context.isWithinDateRange(category.getModifiedDate())) {
			String path = getCategoryPath(context, category);

			Element categoryEl = categoriesEl.addElement("category");

			categoryEl.addAttribute("path", path);

			if (context.isPathNotProcessed(path)) {
				category.setUserUuid(category.getUserUuid());

				context.addZipEntry(path, category);
			}

			exportParentCategory(
				context, categoriesEl, category.getParentCategoryId());
		}

		List<MBMessage> messages = MBMessageUtil.findByCategoryId(
			category.getCategoryId());

		for (MBMessage message : messages) {
			exportMessage(
				context, categoriesEl, messagesEl, messageFlagsEl, message);
		}
	}

	protected void exportMessage(
			PortletDataContext context, Element categoriesEl,
			Element messagesEl, Element messageFlagsEl, MBMessage message)
		throws PortalException, SystemException {

		if (!context.isWithinDateRange(message.getModifiedDate())) {
			return;
		}

		String path = getMessagePath(context, message);

		Element messageEl = messagesEl.addElement("message");

		messageEl.addAttribute("path", path);

		if (context.isPathNotProcessed(path)) {
			message.setUserUuid(message.getUserUuid());
			message.setPriority(message.getPriority());

			if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
				context.addRatingsEntries(
					MBMessage.class, message.getMessageId());
			}

			if (context.getBooleanParameter(_NAMESPACE, "tags")) {
				context.addTagsEntries(
					MBMessage.class, message.getMessageId());
			}

			if (context.getBooleanParameter(_NAMESPACE, "attachments") &&
				message.isAttachments()) {

				for (String attachment : message.getAttachmentsFiles()) {
					int pos = attachment.lastIndexOf(StringPool.FORWARD_SLASH);

					String name = attachment.substring(pos + 1);
					String binPath = getMessageAttachementBinPath(
						context, message, name);

					Element attachmentEl = messageEl.addElement("attachment");

					attachmentEl.addAttribute("name", name);
					attachmentEl.addAttribute("bin-path", binPath);

					try {
						byte[] bytes = DLServiceUtil.getFile(
							context.getCompanyId(), CompanyConstants.SYSTEM,
							attachment);

						context.addZipEntry(binPath, bytes);
					}
					catch (RemoteException re) {
					}
				}

				message.setAttachmentsDir(message.getAttachmentsDir());
			}

			if (context.getBooleanParameter(_NAMESPACE, "flags")) {
				List<MBMessageFlag> messageFlags =
					MBMessageFlagUtil.findByMessageId(
						message.getMessageId());

				for (MBMessageFlag messageFlag : messageFlags) {
					exportMessageFlag(context, messageFlagsEl, messageFlag);
				}
			}

			context.addZipEntry(path, message);
		}

		exportParentCategory(context, categoriesEl, message.getCategoryId());
	}

	protected void exportMessageFlag(
			PortletDataContext context, Element messageFlagsEl,
			MBMessageFlag messageFlag)
		throws SystemException {

		String path = getMessageFlagPath(context, messageFlag);

		Element messageFlagEl = messageFlagsEl.addElement("message-flag");

		messageFlagEl.addAttribute("path", path);

		if (context.isPathNotProcessed(path)) {
			messageFlag.setUserUuid(messageFlag.getUserUuid());

			context.addZipEntry(path, messageFlag);
		}
	}

	protected void exportParentCategory(
			PortletDataContext context, Element categoriesEl, long categoryId)
		throws PortalException, SystemException {

		if ((!context.hasDateRange()) ||
			(categoryId == MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID)) {

			return;
		}

		MBCategory category = MBCategoryUtil.findByPrimaryKey(categoryId);

		String path = getCategoryPath(context, category);

		Element categoryEl = categoriesEl.addElement("category");

		categoryEl.addAttribute("path", path);

		if (context.isPathNotProcessed(path)) {
			category.setUserUuid(category.getUserUuid());

			context.addZipEntry(path, category);
		}

		exportParentCategory(
			context, categoriesEl, category.getParentCategoryId());
	}

	protected void exportUserBan(
			PortletDataContext context, Element userBansEl, MBBan ban)
		throws SystemException {

		if (!context.isWithinDateRange(ban.getModifiedDate())) {
			return;
		}

		String path = getUserBanPath(context, ban);

		Element userBanEl = userBansEl.addElement("user-ban");

		userBanEl.addAttribute("path", path);

		if (context.isPathNotProcessed(path)) {
			ban.setBanUserUuid(ban.getBanUserUuid());
			ban.setUserUuid(ban.getUserUuid());

			context.addZipEntry(path, ban);
		}
	}

	protected void importBan(PortletDataContext context, MBBan ban)
		throws Exception {

		long userId = context.getUserId(ban.getUserUuid());
		long plid = context.getPlid();

		List<User> users = UserUtil.findByUuid(ban.getBanUserUuid());

		Iterator<User> itr = users.iterator();

		if (itr.hasNext()) {
			User user = itr.next();

			MBBanLocalServiceUtil.addBan(userId, plid, user.getUserId());
		}
		else {
			_log.error(
				"Could not find banned user with uuid " + ban.getBanUserUuid());
		}
	}

	protected void importCategory(
			PortletDataContext context, Map<Long, Long> categoryPKs,
			MBCategory category)
		throws Exception {

		long userId = context.getUserId(category.getUserUuid());
		long plid = context.getPlid();
		long parentCategoryId = MapUtil.getLong(
			categoryPKs, category.getParentCategoryId(),
			category.getParentCategoryId());

		boolean addCommunityPermissions = true;
		boolean addGuestPermissions = true;

		MBCategory existingCategory = null;

		try {
			if (parentCategoryId != MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID) {
				MBCategoryUtil.findByPrimaryKey(parentCategoryId);
			}

			if (context.getDataStrategy().equals(
					PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

				existingCategory = MBCategoryUtil.fetchByUUID_G(
					category.getUuid(), context.getGroupId());

				if (existingCategory == null) {
					existingCategory = MBCategoryLocalServiceUtil.addCategory(
						category.getUuid(), userId, plid, parentCategoryId,
						category.getName(), category.getDescription(),
						addCommunityPermissions, addGuestPermissions);
				}
				else {
					existingCategory =
						MBCategoryLocalServiceUtil.updateCategory(
							existingCategory.getCategoryId(), parentCategoryId,
							category.getName(), category.getDescription(),
							false);
				}
			}
			else {
				existingCategory = MBCategoryLocalServiceUtil.addCategory(
					userId, plid, parentCategoryId, category.getName(),
					category.getDescription(), addCommunityPermissions,
					addGuestPermissions);
			}

			categoryPKs.put(
				category.getCategoryId(), existingCategory.getCategoryId());
		}
		catch (NoSuchCategoryException nsce) {
			_log.error(
				"Could not find the parent category for category " +
					category.getCategoryId());
		}
	}

	protected void importFlag(
			PortletDataContext context, Map<Long, Long> messagePKs,
			MBMessageFlag flag)
		throws Exception {

		long userId = context.getUserId(flag.getUserUuid());
		long messageId = MapUtil.getLong(
			messagePKs, flag.getMessageId(), flag.getMessageId());

		try {
			List<MBMessage> messages = new ArrayList<MBMessage>();

			messages.add(MBMessageUtil.findByPrimaryKey(messageId));

			MBMessageFlagLocalServiceUtil.addReadFlags(userId, messages);
		}
		catch (NoSuchMessageException nsme) {
			_log.error(
				"Could not find the message for flag " +
					flag.getMessageFlagId());
		}
	}

	protected void importMessage(
			PortletDataContext context, Map<Long, Long> categoryPKs,
			Map<Long, Long> threadPKs, Map<Long, Long> messagePKs,
			Element messageEl, MBMessage message)
		throws Exception {

		long userId = context.getUserId(message.getUserUuid());
		String userName = message.getUserName();
		long categoryId = MapUtil.getLong(
			categoryPKs, message.getCategoryId(), message.getCategoryId());
		long threadId = MapUtil.getLong(
			threadPKs, message.getThreadId(), message.getThreadId());
		long parentMessageId = MapUtil.getLong(
			messagePKs, message.getParentMessageId(),
			message.getParentMessageId());

		List<ObjectValuePair<String, byte[]>> files =
			new ArrayList<ObjectValuePair<String, byte[]>>();
		List<String> existingFiles = new ArrayList<String>();

		if (context.getBooleanParameter(_NAMESPACE, "attachments") &&
			message.isAttachments()) {

			List<Element> attachmentEls = messageEl.elements("attachment");

			for (Element attachmentEl : attachmentEls) {
				String name = attachmentEl.attributeValue("name");
				String binPath = attachmentEl.attributeValue("bin-path");

				byte[] bytes = context.getZipEntryAsByteArray(binPath);

				files.add(new ObjectValuePair<String, byte[]>(name, bytes));
			}

			if (files.size() <= 0) {
				_log.error(
					"Could not find attachments for message " +
						message.getMessageId());
			}
		}

		String[] tagsEntries = null;

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			tagsEntries = context.getTagsEntries(
				MBMessage.class, message.getMessageId());
		}

		PortletPreferences prefs = null;

		boolean addCommunityPermissions = true;
		boolean addGuestPermissions = true;

		ThemeDisplay themeDisplay = null;

		MBMessage existingMessage = null;

		try {
			MBCategoryUtil.findByPrimaryKey(categoryId);

			if (parentMessageId != MBMessageImpl.DEFAULT_PARENT_MESSAGE_ID) {
				MBMessageUtil.findByPrimaryKey(parentMessageId);
				MBThreadUtil.findByPrimaryKey(threadId);
			}

			if (context.getDataStrategy().equals(
					PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

				try {
					existingMessage = MBMessageFinderUtil.findByUuid_G(
						message.getUuid(), context.getGroupId());

					MBMessageLocalServiceUtil.updateMessage(
						userId, existingMessage.getMessageId(),
						message.getSubject(), message.getBody(), files,
						existingFiles, message.getPriority(), tagsEntries,
						prefs, themeDisplay);
				}
				catch (NoSuchMessageException nsme) {
					existingMessage = MBMessageLocalServiceUtil.addMessage(
						message.getUuid(), userId, userName, categoryId,
						threadId, parentMessageId, message.getSubject(),
						message.getBody(), files, message.getAnonymous(),
						message.getPriority(), tagsEntries, prefs,
						addCommunityPermissions, addGuestPermissions,
						themeDisplay);
				}
			}
			else {
				existingMessage = MBMessageLocalServiceUtil.addMessage(
					userId, userName, categoryId, threadId, parentMessageId,
					message.getSubject(), message.getBody(), files,
					message.getAnonymous(), message.getPriority(), tagsEntries,
					prefs, addCommunityPermissions, addGuestPermissions,
					themeDisplay);
			}

			threadPKs.put(message.getThreadId(), existingMessage.getThreadId());
			messagePKs.put(
				message.getMessageId(), existingMessage.getMessageId());

			if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
				context.importRatingsEntries(
					MBMessage.class, message.getMessageId(),
					existingMessage.getMessageId());
			}
		}
		catch (NoSuchCategoryException nsce) {
			_log.error(
				"Could not find the parent category for message " +
					message.getMessageId());
		}
		catch (NoSuchMessageException nsme) {
			_log.error(
				"Could not find the parent message for message " +
					message.getMessageId());
		}
		catch (NoSuchThreadException nste) {
			_log.error(
				"Could not find the thread for message " +
					message.getMessageId());
		}
	}

	protected String getCategoryPath(
		PortletDataContext context, MBCategory category) {

		StringBuilder sb = new StringBuilder();

		sb.append(context.getPortletPath(PortletKeys.MESSAGE_BOARDS));
		sb.append("/categories/");
		sb.append(category.getCategoryId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getMessageAttachementBinPath(
		PortletDataContext context, MBMessage message, String attachment) {

		StringBuilder sb = new StringBuilder();

		sb.append(context.getPortletPath(PortletKeys.MESSAGE_BOARDS));
		sb.append("/bin/");
		sb.append(message.getMessageId());
		sb.append(StringPool.SLASH);
		sb.append(attachment);

		return sb.toString();
	}

	protected String getMessageFlagPath(
		PortletDataContext context, MBMessageFlag messageFlag) {

		StringBuilder sb = new StringBuilder();

		sb.append(context.getPortletPath(PortletKeys.MESSAGE_BOARDS));
		sb.append("/message-flags/");
		sb.append(messageFlag.getMessageFlagId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getMessagePath(
		PortletDataContext context, MBMessage message) {

		StringBuilder sb = new StringBuilder();

		sb.append(context.getPortletPath(PortletKeys.MESSAGE_BOARDS));
		sb.append("/messages/");
		sb.append(message.getMessageId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getUserBanPath(PortletDataContext context, MBBan ban) {
		StringBuilder sb = new StringBuilder();

		sb.append(context.getPortletPath(PortletKeys.MESSAGE_BOARDS));
		sb.append("/user-bans/");
		sb.append(ban.getBanId());
		sb.append(".xml");

		return sb.toString();
	}

	private static final String _NAMESPACE = "message_board";

	private static final PortletDataHandlerBoolean _categoriesAndMessages =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "categories-and-messages", true, true);

	private static final PortletDataHandlerBoolean _attachments =
		new PortletDataHandlerBoolean(_NAMESPACE, "attachments");

	private static final PortletDataHandlerBoolean _userBans =
		new PortletDataHandlerBoolean(_NAMESPACE, "user-bans");

	private static final PortletDataHandlerBoolean _flags =
		new PortletDataHandlerBoolean(_NAMESPACE, "flags");

	private static final PortletDataHandlerBoolean _ratings =
		new PortletDataHandlerBoolean(_NAMESPACE, "ratings");

	private static final PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

	private static Log _log =
		LogFactory.getLog(MBPortletDataHandlerImpl.class);

}