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

package com.liferay.portlet.messageboards.lar;

import com.liferay.documentlibrary.NoSuchDirectoryException;
import com.liferay.documentlibrary.service.DLServiceUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.messageboards.NoSuchCategoryException;
import com.liferay.portlet.messageboards.NoSuchMessageException;
import com.liferay.portlet.messageboards.NoSuchThreadException;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageFlag;
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
import com.liferay.util.CollectionFactory;
import com.liferay.util.MapUtil;

import com.thoughtworks.xstream.XStream;

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
 * <a href="MBPortletDataHandlerImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Bruno Farache
 *
 */
public class MBPortletDataHandlerImpl implements PortletDataHandler {

	public PortletDataHandlerControl[] getExportControls()
		throws PortletDataException {

		return new PortletDataHandlerControl[] {
			_enableExport, _enableAttachmentsExport, _enableBansExport,
			_enableFlagsExport
		};
	}

	public PortletDataHandlerControl[] getImportControls()
		throws PortletDataException{

		return new PortletDataHandlerControl[] {
			_enableImport, _enableAttachmentsImport, _enableBansImport,
			_enableFlagsImport
		};
	}

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs)
		throws PortletDataException {

		Map parameterMap = context.getParameterMap();

		boolean exportData = MapUtil.getBoolean(
			parameterMap, _EXPORT_MESSAGE_BOARDS_DATA);
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

		boolean exportAttachments = MapUtil.getBoolean(
			parameterMap, _EXPORT_MESSAGE_BOARDS_ATTACHMENTS);
		boolean exportBans = MapUtil.getBoolean(
			parameterMap, _EXPORT_MESSAGE_BOARDS_BANS);
		boolean exportFlags = MapUtil.getBoolean(
			parameterMap, _EXPORT_MESSAGE_BOARDS_FLAGS);

		try {
			XStream xStream = new XStream();

			Document doc = DocumentHelper.createDocument();

			Element root = doc.addElement("message-boards-data");

			root.addAttribute("group-id", String.valueOf(context.getGroupId()));

			// Categories

			List categories = MBCategoryUtil.findByGroupId(
				context.getGroupId());

			List messages = new ArrayList();

			Iterator itr = categories.iterator();

			while (itr.hasNext()) {
				MBCategory category = (MBCategory)itr.next();

				if (context.addPrimaryKey(
						MBCategory.class, category.getPrimaryKeyObj())) {

					itr.remove();
				}
				else {
					category.setUserUuid(category.getUserUuid());

					List categoryMessages = MBMessageUtil.findByCategoryId(
						category.getCategoryId());

					messages.addAll(categoryMessages);
				}
			}

			String xml = xStream.toXML(categories);

			Element el = root.addElement("message-board-categories");

			Document tempDoc = PortalUtil.readDocumentFromXML(xml);

			el.content().add(tempDoc.getRootElement().createCopy());

			// Messages

			List flags = new ArrayList();

			itr = messages.iterator();

			while (itr.hasNext()) {
				MBMessage message = (MBMessage)itr.next();

				if (context.addPrimaryKey(
						MBMessage.class, message.getPrimaryKeyObj())) {

					itr.remove();
				}
				else {
					message.setUserUuid(message.getUserUuid());
					message.setPriority(message.getPriority());

					context.addTagsEntries(
						MBMessage.class, message.getPrimaryKeyObj());

					// Attachments

					if (message.isAttachments() && exportAttachments) {
						String[] fileNames = null;

						try {
							fileNames = DLServiceUtil.getFileNames(
								context.getCompanyId(), CompanyImpl.SYSTEM,
								message.getAttachmentsDir());

							for (int i = 0; i < fileNames.length; i++) {
								byte[] byteArray  = DLServiceUtil.getFile(
									context.getCompanyId(), CompanyImpl.SYSTEM,
									fileNames[i]);

								context.getZipWriter().addEntry(
									fileNames[i], byteArray);
							}

							message.setAttachmentsDir(
								message.getAttachmentsDir());
						}
						catch (NoSuchDirectoryException nsde) {
						}
					}

					if (exportFlags) {
						List messageFlags = MBMessageFlagUtil.findByMessageId(
							message.getMessageId());

						flags.addAll(messageFlags);
					}
				}
			}

			xml = xStream.toXML(messages);

			el = root.addElement("message-board-messages");

			tempDoc = PortalUtil.readDocumentFromXML(xml);

			el.content().add(tempDoc.getRootElement().createCopy());

			// Flags

			itr = flags.iterator();

			while (itr.hasNext()) {
				MBMessageFlag flag = (MBMessageFlag)itr.next();

				if (context.addPrimaryKey(
						MBMessageFlag.class, flag.getPrimaryKeyObj())) {

					itr.remove();
				}
				else {
					flag.setUserUuid(flag.getUserUuid());
				}
			}

			xml = xStream.toXML(flags);

			el = root.addElement("message-board-flags");

			tempDoc = PortalUtil.readDocumentFromXML(xml);

			el.content().add(tempDoc.getRootElement().createCopy());

			// Bans

			List bans = new ArrayList();

			if (exportBans) {
				bans = MBBanUtil.findByGroupId(context.getGroupId());

				itr = bans.iterator();

				while (itr.hasNext()) {
					MBBan ban = (MBBan)itr.next();

					if (context.addPrimaryKey(
							MBBan.class, ban.getPrimaryKeyObj())) {

						itr.remove();
					}
					else {
						ban.setBanUserUuid(ban.getBanUserUuid());

						ban.setUserUuid(ban.getUserUuid());
					}
				}
			}

			xml = xStream.toXML(bans);

			el = root.addElement("message-board-bans");

			tempDoc = PortalUtil.readDocumentFromXML(xml);

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
			parameterMap, _IMPORT_MESSAGE_BOARDS_DATA);
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
		boolean importAttachments = MapUtil.getBoolean(
			parameterMap, _IMPORT_MESSAGE_BOARDS_ATTACHMENTS);
		boolean importBans = MapUtil.getBoolean(
			parameterMap, _IMPORT_MESSAGE_BOARDS_BANS);
		boolean importFlags = MapUtil.getBoolean(
			parameterMap, _IMPORT_MESSAGE_BOARDS_FLAGS);

		try {
			XStream xStream = new XStream();

			Document doc = PortalUtil.readDocumentFromXML(data);

			Element root = doc.getRootElement();

			// Categories

			Element el = root.element(
				"message-board-categories").element("list");

			Document tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			Map categoryPKs = CollectionFactory.getHashMap();

			List categories = (List)xStream.fromXML(tempDoc.asXML());

			Iterator itr = categories.iterator();

			while (itr.hasNext()) {
				MBCategory category = (MBCategory)itr.next();

				importCategory(context, mergeData, categoryPKs, category);
			}

			// Messages

			el = root.element("message-board-messages").element("list");

			tempDoc = DocumentHelper.createDocument();

			tempDoc.content().add(el.createCopy());

			Map messagePKs = CollectionFactory.getHashMap();
			Map threadPKs = CollectionFactory.getHashMap();

			List messages = (List)xStream.fromXML(tempDoc.asXML());

			itr = messages.iterator();

			while (itr.hasNext()) {
				MBMessage message = (MBMessage)itr.next();

				importMessage(
					context, mergeData, importAttachments, categoryPKs,
					messagePKs, threadPKs, message);
			}

			// Flags

			if (importFlags) {
				el = root.element("message-board-flags").element("list");

				tempDoc = DocumentHelper.createDocument();

				tempDoc.content().add(el.createCopy());

				List flags = (List)xStream.fromXML(tempDoc.asXML());

				itr = flags.iterator();

				while (itr.hasNext()) {
					MBMessageFlag flag = (MBMessageFlag)itr.next();

					importFlag(context, messagePKs, flag);
				}
			}

			// Bans

			if (importBans) {
				el = root.element("message-board-bans").element("list");

				tempDoc = DocumentHelper.createDocument();

				tempDoc.content().add(el.createCopy());

				List bans = (List)xStream.fromXML(tempDoc.asXML());

				itr = bans.iterator();

				while (itr.hasNext()) {
					MBBan ban = (MBBan)itr.next();

					importBan(context, ban);
				}
			}

			return null;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	protected void importBan(PortletDataContext context, MBBan ban)
		throws Exception {

		long userId = context.getUserId(ban.getUserUuid());
		long plid = context.getPlid();

		List users = UserUtil.findByUuid(ban.getBanUserUuid());

		Iterator itr = users.iterator();

		if (itr.hasNext()) {
			User user = (User)itr.next();

			MBBanLocalServiceUtil.addBan(userId, plid, user.getUserId());
		}
		else {
			_log.error(
				"Could not find banned user with uuid " + ban.getBanUserUuid());
		}
	}

	protected void importCategory(
			PortletDataContext context, boolean mergeData, Map categoryPKs,
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

			if (mergeData) {
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
				category.getPrimaryKeyObj(),
				existingCategory.getPrimaryKeyObj());
		}
		catch (NoSuchCategoryException nsce) {
			_log.error(
				"Could not find the parent category for category " +
					category.getCategoryId());
		}
	}

	protected void importFlag(
			PortletDataContext context, Map messagePKs, MBMessageFlag flag)
		throws Exception {

		long userId = context.getUserId(flag.getUserUuid());
		long messageId = MapUtil.getLong(
			messagePKs, flag.getMessageId(), flag.getMessageId());

		try {
			List messages = new ArrayList();

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
			PortletDataContext context, boolean mergeData,
			boolean importAttachments, Map categoryPKs, Map messagePKs,
			Map threadPKs, MBMessage message)
		throws Exception {

		long userId = context.getUserId(message.getUserUuid());
		long categoryId = MapUtil.getLong(
			categoryPKs, message.getCategoryId(), message.getCategoryId());
		long parentMessageId = MapUtil.getLong(
			messagePKs, message.getParentMessageId(),
			message.getParentMessageId());
		long threadId = MapUtil.getLong(
			threadPKs, message.getThreadId(), message.getThreadId());

		List files = new ArrayList();

		if (message.isAttachments() && importAttachments) {
			files = (List)context.getZipReader().getFolderEntries().get(
				message.getAttachmentsDir() + "/");

			if (files == null) {
				_log.error(
					"Could not find attachments for message " +
						message.getMessageId());

				files = new ArrayList();
			}
		}

		String[] tagsEntries = context.getTagsEntries(
			MBMessage.class, message.getPrimaryKeyObj());

		PortletPreferences prefs = null;

		boolean addCommunityPermissions = true;
		boolean addGuestPermissions = true;

		MBMessage existingMessage = null;

		try {
			MBCategoryUtil.findByPrimaryKey(categoryId);

			if (parentMessageId != MBMessageImpl.DEFAULT_PARENT_MESSAGE_ID) {
				MBMessageUtil.findByPrimaryKey(parentMessageId);
				MBThreadUtil.findByPrimaryKey(threadId);
			}

			if (mergeData) {
				existingMessage = MBMessageFinderUtil.findByUuid_G(
					message.getUuid(), context.getGroupId());

				if (existingMessage == null) {
					existingMessage = MBMessageLocalServiceUtil.addMessage(
						message.getUuid(), userId, categoryId, threadId,
						parentMessageId, message.getSubject(),
						message.getBody(), files, message.getAnonymous(),
						message.getPriority(), tagsEntries, prefs,
						addCommunityPermissions, addGuestPermissions);
				}
				else {
					MBMessageLocalServiceUtil.updateMessage(
						userId, existingMessage.getMessageId(), categoryId,
						message.getSubject(), message.getBody(), files,
						message.getPriority(), tagsEntries, prefs);
				}
			}
			else {
				existingMessage = MBMessageLocalServiceUtil.addMessage(
					userId, categoryId, threadId, parentMessageId,
					message.getSubject(), message.getBody(), files,
					message.getAnonymous(), message.getPriority(), tagsEntries,
					prefs, addCommunityPermissions, addGuestPermissions);
			}

			messagePKs.put(
				message.getPrimaryKeyObj(),
				existingMessage.getPrimaryKeyObj());
			threadPKs.put(
				new Long(message.getThreadId()),
				new Long(existingMessage.getThreadId()));
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

	private static final String _EXPORT_MESSAGE_BOARDS_DATA =
		"export-" + PortletKeys.MESSAGE_BOARDS + "-data";

	private static final String _IMPORT_MESSAGE_BOARDS_DATA =
		"import-" + PortletKeys.MESSAGE_BOARDS + "-data";

	private static final String _EXPORT_MESSAGE_BOARDS_ATTACHMENTS =
		"export-" + PortletKeys.MESSAGE_BOARDS + "-attachments";

	private static final String _IMPORT_MESSAGE_BOARDS_ATTACHMENTS =
		"import-" + PortletKeys.MESSAGE_BOARDS + "-attachments";

	private static final String _EXPORT_MESSAGE_BOARDS_BANS =
		"export-" + PortletKeys.MESSAGE_BOARDS + "-bans";

	private static final String _IMPORT_MESSAGE_BOARDS_BANS =
		"import-" + PortletKeys.MESSAGE_BOARDS + "-bans";

	private static final String _EXPORT_MESSAGE_BOARDS_FLAGS =
		"export-" + PortletKeys.MESSAGE_BOARDS + "-flags";

	private static final String _IMPORT_MESSAGE_BOARDS_FLAGS =
		"import-" + PortletKeys.MESSAGE_BOARDS + "-flags";

	private static final PortletDataHandlerBoolean _enableExport =
		new PortletDataHandlerBoolean(_EXPORT_MESSAGE_BOARDS_DATA, true, null);

	private static final PortletDataHandlerBoolean _enableImport =
		new PortletDataHandlerBoolean(_IMPORT_MESSAGE_BOARDS_DATA, true, null);

	private static final PortletDataHandlerBoolean _enableAttachmentsExport =
		new PortletDataHandlerBoolean(
			_EXPORT_MESSAGE_BOARDS_ATTACHMENTS, true, null);

	private static final PortletDataHandlerBoolean _enableAttachmentsImport =
		new PortletDataHandlerBoolean(
			_IMPORT_MESSAGE_BOARDS_ATTACHMENTS, true, null);

	private static final PortletDataHandlerBoolean _enableBansExport =
		new PortletDataHandlerBoolean(_EXPORT_MESSAGE_BOARDS_BANS, true, null);

	private static final PortletDataHandlerBoolean _enableBansImport =
		new PortletDataHandlerBoolean(_IMPORT_MESSAGE_BOARDS_BANS, true, null);

	private static final PortletDataHandlerBoolean _enableFlagsExport =
		new PortletDataHandlerBoolean(_EXPORT_MESSAGE_BOARDS_FLAGS, true, null);

	private static final PortletDataHandlerBoolean _enableFlagsImport =
		new PortletDataHandlerBoolean(_IMPORT_MESSAGE_BOARDS_FLAGS, true, null);

	private static Log _log =
		LogFactory.getLog(MBPortletDataHandlerImpl.class);

}