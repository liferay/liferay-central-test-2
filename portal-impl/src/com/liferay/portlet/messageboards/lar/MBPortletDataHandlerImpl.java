/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.messageboards.lar;

import com.liferay.documentlibrary.service.DLServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.lar.BasePortletDataHandler;
import com.liferay.portal.lar.PortletDataContext;
import com.liferay.portal.lar.PortletDataException;
import com.liferay.portal.lar.PortletDataHandlerBoolean;
import com.liferay.portal.lar.PortletDataHandlerControl;
import com.liferay.portal.lar.PortletDataHandlerKeys;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.messageboards.NoSuchCategoryException;
import com.liferay.portlet.messageboards.NoSuchMessageException;
import com.liferay.portlet.messageboards.NoSuchThreadException;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.model.MBMessageFlag;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageFlagLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.persistence.MBBanUtil;
import com.liferay.portlet.messageboards.service.persistence.MBCategoryUtil;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFlagUtil;
import com.liferay.portlet.messageboards.service.persistence.MBMessageUtil;
import com.liferay.portlet.messageboards.service.persistence.MBThreadUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * <a href="MBPortletDataHandlerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class MBPortletDataHandlerImpl extends BasePortletDataHandler {

	public PortletPreferences deleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
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
			PortletPreferences preferences)
		throws PortletDataException {

		try {
			context.addPermissions(
				"com.liferay.portlet.messageboards", context.getGroupId());

			Document doc = SAXReaderUtil.createDocument();

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

			return doc.formattedString();
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
			PortletPreferences preferences, String data)
		throws PortletDataException {

		try {
			context.importPermissions(
				"com.liferay.portlet.messageboards", context.getSourceGroupId(),
				context.getGroupId());

			Document doc = SAXReaderUtil.read(data);

			Element root = doc.getRootElement();

			List<Element> categoryEls = root.element("categories").elements(
				"category");

			Map<Long, Long> categoryPKs =
				(Map<Long, Long>)context.getNewPrimaryKeysMap(MBCategory.class);

			for (Element categoryEl : categoryEls) {
				String path = categoryEl.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				MBCategory category = (MBCategory)context.getZipEntryAsObject(
					path);

				importCategory(context, categoryPKs, category);
			}

			List<Element> messageEls = root.element("messages").elements(
				"message");

			Map<Long, Long> threadPKs =
				(Map<Long, Long>)context.getNewPrimaryKeysMap(MBThread.class);
			Map<Long, Long> messagePKs =
				(Map<Long, Long>)context.getNewPrimaryKeysMap(MBMessage.class);

			for (Element messageEl : messageEls) {
				String path = messageEl.attributeValue("path");

				if (!context.isPathNotProcessed(path)) {
					continue;
				}

				MBMessage message = (MBMessage)context.getZipEntryAsObject(
					path);

				importMessage(
					context, categoryPKs, threadPKs, messagePKs, messageEl,
					message);
			}

			if (context.getBooleanParameter(_NAMESPACE, "flags")) {
				List<Element> flagEls = root.element("message-flags").elements(
					"flag");

				for (Element flagEl : flagEls) {
					String path = flagEl.attributeValue("path");

					if (!context.isPathNotProcessed(path)) {
						continue;
					}

					MBMessageFlag flag =
						(MBMessageFlag)context.getZipEntryAsObject(path);

					importFlag(context, messagePKs, flag);
				}
			}

			if (context.getBooleanParameter(_NAMESPACE, "user-bans")) {
				List<Element> banEls = root.element("user-bans").elements(
					"user-ban");

				for (Element banEl : banEls) {
					String path = banEl.attributeValue("path");

					if (!context.isPathNotProcessed(path)) {
						continue;
					}

					MBBan ban = (MBBan)context.getZipEntryAsObject(path);

					importBan(context, ban);
				}
			}

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	protected void exportCategory(
			PortletDataContext context, Element categoriesEl,
			Element messagesEl, Element messageFlagsEl, MBCategory category)
		throws PortalException, SystemException {

		if (context.isWithinDateRange(category.getModifiedDate())) {
			exportParentCategory(
				context, categoriesEl, category.getParentCategoryId());

			String path = getCategoryPath(context, category);

			if (context.isPathNotProcessed(path)) {
				Element categoryEl = categoriesEl.addElement("category");

				categoryEl.addAttribute("path", path);

				category.setUserUuid(category.getUserUuid());

				context.addPermissions(
					MBCategory.class, category.getCategoryId());

				context.addZipEntry(path, category);
			}
		}

		List<MBMessage> messages = MBMessageUtil.findByG_C(
			category.getGroupId(), category.getCategoryId());

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

		exportParentCategory(context, categoriesEl, message.getCategoryId());

		String path = getMessagePath(context, message);

		if (context.isPathNotProcessed(path)) {
			Element messageEl = messagesEl.addElement("message");

			messageEl.addAttribute("path", path);

			message.setUserUuid(message.getUserUuid());
			message.setPriority(message.getPriority());

			context.addPermissions(MBMessage.class, message.getMessageId());

			if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
				context.addRatingsEntries(
					MBMessage.class, message.getMessageId());
			}

			if (context.getBooleanParameter(_NAMESPACE, "tags")) {
				context.addAssetTags(MBMessage.class, message.getMessageId());
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

					byte[] bytes = DLServiceUtil.getFile(
						context.getCompanyId(), CompanyConstants.SYSTEM,
						attachment);

					context.addZipEntry(binPath, bytes);
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
	}

	protected void exportMessageFlag(
			PortletDataContext context, Element messageFlagsEl,
			MBMessageFlag messageFlag)
		throws SystemException {

		String path = getMessageFlagPath(context, messageFlag);

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		Element messageFlagEl = messageFlagsEl.addElement("message-flag");

		messageFlagEl.addAttribute("path", path);

		messageFlag.setUserUuid(messageFlag.getUserUuid());

		context.addZipEntry(path, messageFlag);
	}

	protected void exportParentCategory(
			PortletDataContext context, Element categoriesEl, long categoryId)
		throws PortalException, SystemException {

		if ((!context.hasDateRange()) ||
			(categoryId == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID)) {

			return;
		}

		MBCategory category = MBCategoryUtil.findByPrimaryKey(categoryId);

		exportParentCategory(
			context, categoriesEl, category.getParentCategoryId());

		String path = getCategoryPath(context, category);

		if (context.isPathNotProcessed(path)) {
			Element categoryEl = categoriesEl.addElement("category");

			categoryEl.addAttribute("path", path);

			category.setUserUuid(category.getUserUuid());

			context.addPermissions(MBCategory.class, category.getCategoryId());

			context.addZipEntry(path, category);
		}
	}

	protected void exportUserBan(
			PortletDataContext context, Element userBansEl, MBBan ban)
		throws SystemException {

		if (!context.isWithinDateRange(ban.getModifiedDate())) {
			return;
		}

		String path = getUserBanPath(context, ban);

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		Element userBanEl = userBansEl.addElement("user-ban");

		userBanEl.addAttribute("path", path);

		ban.setBanUserUuid(ban.getBanUserUuid());
		ban.setUserUuid(ban.getUserUuid());

		context.addZipEntry(path, ban);
	}

	protected void importBan(PortletDataContext context, MBBan ban)
		throws Exception {

		long userId = context.getUserId(ban.getUserUuid());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(context.getGroupId());

		List<User> users = UserUtil.findByUuid(ban.getBanUserUuid());

		Iterator<User> itr = users.iterator();

		if (itr.hasNext()) {
			User user = itr.next();

			MBBanLocalServiceUtil.addBan(
				userId, user.getUserId(), serviceContext);
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
		long parentCategoryId = MapUtil.getLong(
			categoryPKs, category.getParentCategoryId(),
			category.getParentCategoryId());

		String emailAddress = null;
		String inProtocol = null;
		String inServerName = null;
		int inServerPort = 0;
		boolean inUseSSL = false;
		String inUserName = null;
		String inPassword = null;
		int inReadInterval = 0;
		String outEmailAddress = null;
		boolean outCustom = false;
		String outServerName = null;
		int outServerPort = 0;
		boolean outUseSSL = false;
		String outUserName = null;
		String outPassword = null;
		boolean mailingListActive = false;

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(context.getGroupId());

		if ((parentCategoryId !=
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
			(parentCategoryId == category.getParentCategoryId())) {

			String path = getImportCategoryPath(context, parentCategoryId);

			MBCategory parentCategory =
				(MBCategory)context.getZipEntryAsObject(path);

			importCategory(context, categoryPKs, parentCategory);

			parentCategoryId = MapUtil.getLong(
				categoryPKs, category.getParentCategoryId(),
				category.getParentCategoryId());
		}

		MBCategory importedCategory = null;

		try {
			if (parentCategoryId !=
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

				MBCategoryUtil.findByPrimaryKey(parentCategoryId);
			}

			if (context.getDataStrategy().equals(
					PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

				MBCategory existingCategory = MBCategoryUtil.fetchByUUID_G(
					category.getUuid(), context.getGroupId());

				if (existingCategory == null) {
					importedCategory = MBCategoryLocalServiceUtil.addCategory(
						category.getUuid(), userId, parentCategoryId,
						category.getName(), category.getDescription(),
						emailAddress, inProtocol, inServerName, inServerPort,
						inUseSSL, inUserName, inPassword, inReadInterval,
						outEmailAddress, outCustom, outServerName,
						outServerPort, outUseSSL, outUserName, outPassword,
						mailingListActive, serviceContext);
				}
				else {
					importedCategory =
						MBCategoryLocalServiceUtil.updateCategory(
							existingCategory.getCategoryId(), parentCategoryId,
							category.getName(), category.getDescription(),
							emailAddress, inProtocol, inServerName,
							inServerPort, inUseSSL, inUserName, inPassword,
							inReadInterval, outEmailAddress, outCustom,
							outServerName, outServerPort, outUseSSL,
							outUserName, outPassword, mailingListActive, false,
							serviceContext);
				}
			}
			else {
				importedCategory = MBCategoryLocalServiceUtil.addCategory(
					userId, parentCategoryId, category.getName(),
					category.getDescription(), emailAddress, inProtocol,
					inServerName, inServerPort, inUseSSL, inUserName,
					inPassword, inReadInterval, outEmailAddress, outCustom,
					outServerName, outServerPort, outUseSSL, outUserName,
					outPassword, mailingListActive, serviceContext);
			}

			categoryPKs.put(
				category.getCategoryId(), importedCategory.getCategoryId());

			context.importPermissions(
				MBCategory.class, category.getCategoryId(),
				importedCategory.getCategoryId());
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
			MBMessage message = MBMessageUtil.findByPrimaryKey(messageId);

			MBThread thread = message.getThread();

			MBMessageFlagLocalServiceUtil.addReadFlags(userId, thread);
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

		String[] assetTagNames = null;

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			assetTagNames = context.getAssetTagNames(
				MBMessage.class, message.getMessageId());
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAssetTagNames(assetTagNames);
		serviceContext.setScopeGroupId(context.getGroupId());
		serviceContext.setStartWorkflow(false);
		serviceContext.setStatus(message.getStatus());

		if ((categoryId != MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) &&
			(categoryId == message.getCategoryId())) {

			String path = getImportCategoryPath(context, categoryId);

			MBCategory category = (MBCategory)context.getZipEntryAsObject(path);

			importCategory(context, categoryPKs, category);

			categoryId = MapUtil.getLong(
				categoryPKs, message.getCategoryId(), message.getCategoryId());
		}

		MBMessage importedMessage = null;

		try {
			MBCategoryUtil.findByPrimaryKey(categoryId);

			if (parentMessageId !=
					MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID) {

				MBMessageUtil.findByPrimaryKey(parentMessageId);
				MBThreadUtil.findByPrimaryKey(threadId);
			}

			if (context.getDataStrategy().equals(
					PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

				MBMessage existingMessage = MBMessageUtil.fetchByUUID_G(
					message.getUuid(), context.getGroupId());

				if (existingMessage == null) {
					importedMessage = MBMessageLocalServiceUtil.addMessage(
						message.getUuid(), userId, userName,
						message.getGroupId(), categoryId, threadId,
						parentMessageId, message.getSubject(),
						message.getBody(), files, message.getAnonymous(),
						message.getPriority(), message.getAllowPingbacks(),
						serviceContext);
				}
				else {
					importedMessage = MBMessageLocalServiceUtil.updateMessage(
						userId, existingMessage.getMessageId(),
						message.getSubject(), message.getBody(), files,
						existingFiles, message.getPriority(),
						message.getAllowPingbacks(), serviceContext);
				}
			}
			else {
				importedMessage = MBMessageLocalServiceUtil.addMessage(
					userId, userName, message.getGroupId(), categoryId,
					threadId, parentMessageId, message.getSubject(),
					message.getBody(), files, message.getAnonymous(),
					message.getPriority(), message.getAllowPingbacks(),
					serviceContext);
			}

			threadPKs.put(message.getThreadId(), importedMessage.getThreadId());
			messagePKs.put(
				message.getMessageId(), importedMessage.getMessageId());

			context.importPermissions(
				MBMessage.class, message.getMessageId(),
				importedMessage.getMessageId());

			if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
				context.importRatingsEntries(
					MBMessage.class, message.getMessageId(),
					importedMessage.getMessageId());
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

		StringBundler sb = new StringBundler(4);

		sb.append(context.getPortletPath(PortletKeys.MESSAGE_BOARDS));
		sb.append("/categories/");
		sb.append(category.getCategoryId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getImportCategoryPath(
		PortletDataContext context, long categoryId) {

		StringBundler sb = new StringBundler(4);

		sb.append(context.getSourcePortletPath(PortletKeys.MESSAGE_BOARDS));
		sb.append("/categories/");
		sb.append(categoryId);
		sb.append(".xml");

		return sb.toString();
	}

	protected String getMessageAttachementBinPath(
		PortletDataContext context, MBMessage message, String attachment) {

		StringBundler sb = new StringBundler(5);

		sb.append(context.getPortletPath(PortletKeys.MESSAGE_BOARDS));
		sb.append("/bin/");
		sb.append(message.getMessageId());
		sb.append(StringPool.SLASH);
		sb.append(attachment);

		return sb.toString();
	}

	protected String getMessageFlagPath(
		PortletDataContext context, MBMessageFlag messageFlag) {

		StringBundler sb = new StringBundler(4);

		sb.append(context.getPortletPath(PortletKeys.MESSAGE_BOARDS));
		sb.append("/message-flags/");
		sb.append(messageFlag.getMessageFlagId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getMessagePath(
		PortletDataContext context, MBMessage message) {

		StringBundler sb = new StringBundler(4);

		sb.append(context.getPortletPath(PortletKeys.MESSAGE_BOARDS));
		sb.append("/messages/");
		sb.append(message.getMessageId());
		sb.append(".xml");

		return sb.toString();
	}

	protected String getUserBanPath(PortletDataContext context, MBBan ban) {
		StringBundler sb = new StringBundler(4);

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

	private static Log _log = LogFactoryUtil.getLog(
		MBPortletDataHandlerImpl.class);

}