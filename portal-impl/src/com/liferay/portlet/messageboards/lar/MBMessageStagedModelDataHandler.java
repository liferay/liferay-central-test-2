/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.lar.FileEntryUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.service.RatingsEntryLocalServiceUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class MBMessageStagedModelDataHandler
	extends BaseStagedModelDataHandler<MBMessage> {

	public static final String[] CLASS_NAMES = {MBMessage.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		MBMessage message = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (message != null) {
			MBMessageLocalServiceUtil.deleteMessage(message);
		}
	}

	@Override
	public MBMessage fetchStagedModelByUuidAndCompanyId(
		String uuid, long companyId) {

		List<MBMessage> messages =
			MBMessageLocalServiceUtil.getMBMessagesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<MBMessage>());

		if (ListUtil.isEmpty(messages)) {
			return null;
		}

		return messages.get(0);
	}

	@Override
	public MBMessage fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return MBMessageLocalServiceUtil.fetchMBMessageByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(MBMessage message) {
		return message.getSubject();
	}

	protected MBMessage addDiscussionMessage(
			PortletDataContext portletDataContext, long userId, long threadId,
			long parentMessageId, MBMessage message,
			ServiceContext serviceContext)
		throws PortalException {

		MBDiscussion discussion =
			MBDiscussionLocalServiceUtil.getThreadDiscussion(threadId);

		MBMessage importedMessage = null;

		if (!message.isRoot()) {
			importedMessage = MBMessageLocalServiceUtil.addDiscussionMessage(
				userId, message.getUserName(),
				portletDataContext.getScopeGroupId(), discussion.getClassName(),
				discussion.getClassPK(), threadId, parentMessageId,
				message.getSubject(), message.getBody(), serviceContext);
		}
		else {
			MBThread thread = MBThreadLocalServiceUtil.getThread(threadId);

			importedMessage = MBMessageLocalServiceUtil.getMBMessage(
				thread.getRootMessageId());
		}

		return importedMessage;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, MBMessage message)
		throws Exception {

		if (message.isDiscussion()) {
			MBDiscussion discussion =
				MBDiscussionLocalServiceUtil.getDiscussion(
					message.getClassName(), message.getClassPK());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, message, discussion,
				PortletDataContext.REFERENCE_TYPE_PARENT);

			// Ratings that belong to discussion messages cannot be exported
			// automatically because of the special class name and class PK pair

			List<RatingsEntry> ratingsEntries =
				RatingsEntryLocalServiceUtil.getEntries(
					MBDiscussion.class.getName(), message.getMessageId());

			for (RatingsEntry ratingsEntry : ratingsEntries) {
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, message, ratingsEntry,
					PortletDataContext.REFERENCE_TYPE_WEAK);
			}
		}
		else if (message.getCategoryId() !=
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, message, message.getCategory(),
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		if (!message.isRoot()) {
			MBMessage parentMessage = MBMessageLocalServiceUtil.getMessage(
				message.getParentMessageId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, message, parentMessage,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		message.setPriority(message.getPriority());

		MBThread thread = message.getThread();

		Element messageElement = portletDataContext.getExportDataElement(
			message);

		messageElement.addAttribute(
			"question", String.valueOf(thread.isQuestion()));

		boolean hasAttachmentsFileEntries =
			message.getAttachmentsFileEntriesCount() > 0;

		messageElement.addAttribute(
			"hasAttachmentsFileEntries",
			String.valueOf(hasAttachmentsFileEntries));

		if (hasAttachmentsFileEntries) {
			for (FileEntry fileEntry : message.getAttachmentsFileEntries()) {
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, message, fileEntry,
					PortletDataContext.REFERENCE_TYPE_WEAK);
			}

			long folderId = message.getAttachmentsFolderId();

			if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				message.setAttachmentsFolderId(folderId);
			}
		}

		portletDataContext.addClassedModel(
			messageElement, ExportImportPathUtil.getModelPath(message),
			message);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, MBMessage message)
		throws Exception {

		long userId = portletDataContext.getUserId(message.getUserUuid());

		Map<Long, Long> categoryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MBCategory.class);

		long parentCategoryId = MapUtil.getLong(
			categoryIds, message.getCategoryId(), message.getCategoryId());

		Map<Long, Long> threadIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MBThread.class);

		long threadId = MapUtil.getLong(threadIds, message.getThreadId(), 0);

		if (!message.isRoot()) {
			StagedModelDataHandlerUtil.importReferenceStagedModel(
				portletDataContext, message, MBMessage.class,
				message.getParentMessageId());
		}

		Map<Long, Long> messageIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				MBMessage.class);

		long parentMessageId = MapUtil.getLong(
			messageIds, message.getParentMessageId(),
			message.getParentMessageId());

		Element element = portletDataContext.getImportDataStagedModelElement(
			message);

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			getAttachments(portletDataContext, element, message);

		try {
			ServiceContext serviceContext =
				portletDataContext.createServiceContext(message);

			MBMessage importedMessage = null;

			if (portletDataContext.isDataStrategyMirror()) {
				MBMessage existingMessage = fetchStagedModelByUuidAndGroupId(
					message.getUuid(), portletDataContext.getScopeGroupId());

				if (existingMessage == null) {
					serviceContext.setUuid(message.getUuid());

					if (message.isDiscussion()) {
						importedMessage = addDiscussionMessage(
							portletDataContext, userId, threadId,
							parentMessageId, message, serviceContext);
					}
					else {
						importedMessage = MBMessageLocalServiceUtil.addMessage(
							userId, message.getUserName(),
							portletDataContext.getScopeGroupId(),
							parentCategoryId, threadId, parentMessageId,
							message.getSubject(), message.getBody(),
							message.getFormat(), inputStreamOVPs,
							message.getAnonymous(), message.getPriority(),
							message.getAllowPingbacks(), serviceContext);
					}
				}
				else {
					if (!message.isRoot() && message.isDiscussion()) {
						MBDiscussion discussion =
							MBDiscussionLocalServiceUtil.getThreadDiscussion(
								threadId);

						importedMessage =
							MBMessageLocalServiceUtil.updateDiscussionMessage(
								userId, existingMessage.getMessageId(),
								discussion.getClassName(),
								discussion.getClassPK(), message.getSubject(),
								message.getBody(), serviceContext);
					}
					else {
						importedMessage =
							MBMessageLocalServiceUtil.updateMessage(
								userId, existingMessage.getMessageId(),
								message.getSubject(), message.getBody(),
								inputStreamOVPs, new ArrayList<String>(),
								message.getPriority(),
								message.getAllowPingbacks(), serviceContext);
					}
				}
			}
			else {
				if (message.isDiscussion()) {
					importedMessage = addDiscussionMessage(
						portletDataContext, userId, threadId, parentMessageId,
						message, serviceContext);
				}
				else {
					importedMessage = MBMessageLocalServiceUtil.addMessage(
						userId, message.getUserName(),
						portletDataContext.getScopeGroupId(), parentCategoryId,
						threadId, parentMessageId, message.getSubject(),
						message.getBody(), message.getFormat(), inputStreamOVPs,
						message.getAnonymous(), message.getPriority(),
						message.getAllowPingbacks(), serviceContext);
				}
			}

			MBMessageLocalServiceUtil.updateAnswer(
				importedMessage, message.isAnswer(), false);

			if (importedMessage.isRoot() && !importedMessage.isDiscussion()) {
				MBThreadLocalServiceUtil.updateQuestion(
					importedMessage.getThreadId(),
					GetterUtil.getBoolean(element.attributeValue("question")));
			}

			if (message.isDiscussion()) {
				Map<Long, Long> discussionIds =
					(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
						MBDiscussion.class);

				discussionIds.put(
					message.getMessageId(), importedMessage.getMessageId());
			}

			threadIds.put(message.getThreadId(), importedMessage.getThreadId());

			portletDataContext.importClassedModel(message, importedMessage);
		}
		finally {
			for (ObjectValuePair<String, InputStream> inputStreamOVP :
					inputStreamOVPs) {

				InputStream inputStream = inputStreamOVP.getValue();

				StreamUtil.cleanUp(inputStream);
			}
		}
	}

	@Override
	protected void doRestoreStagedModel(
			PortletDataContext portletDataContext, MBMessage message)
		throws Exception {

		long userId = portletDataContext.getUserId(message.getUserUuid());

		MBMessage existingMessage = fetchStagedModelByUuidAndGroupId(
			message.getUuid(), portletDataContext.getScopeGroupId());

		if (existingMessage == null) {
			return;
		}

		if (existingMessage.isInTrash()) {
			TrashHandler trashHandler = existingMessage.getTrashHandler();

			if (trashHandler.isRestorable(existingMessage.getMessageId())) {
				trashHandler.restoreTrashEntry(
					userId, existingMessage.getMessageId());
			}
		}

		if (existingMessage.isInTrashContainer()) {
			MBThread existingThread = existingMessage.getThread();

			TrashHandler trashHandler = existingThread.getTrashHandler();

			if (trashHandler.isRestorable(existingThread.getThreadId())) {
				trashHandler.restoreTrashEntry(
					userId, existingThread.getThreadId());
			}
		}
	}

	protected List<ObjectValuePair<String, InputStream>> getAttachments(
		PortletDataContext portletDataContext, Element messageElement,
		MBMessage message) {

		boolean hasAttachmentsFileEntries = GetterUtil.getBoolean(
			messageElement.attributeValue("hasAttachmentsFileEntries"));

		if (!hasAttachmentsFileEntries) {
			return Collections.emptyList();
		}

		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			new ArrayList<>();

		List<Element> attachmentElements =
			portletDataContext.getReferenceDataElements(
				messageElement, DLFileEntry.class,
				PortletDataContext.REFERENCE_TYPE_WEAK);

		for (Element attachmentElement : attachmentElements) {
			String path = attachmentElement.attributeValue("path");

			FileEntry fileEntry =
				(FileEntry)portletDataContext.getZipEntryAsObject(path);

			InputStream inputStream = null;

			String binPath = attachmentElement.attributeValue("bin-path");

			if (Validator.isNull(binPath) &&
				portletDataContext.isPerformDirectBinaryImport()) {

				try {
					inputStream = FileEntryUtil.getContentStream(fileEntry);
				}
				catch (Exception e) {
				}
			}
			else {
				inputStream = portletDataContext.getZipEntryAsInputStream(
					binPath);
			}

			if (inputStream == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to import attachment for file entry " +
							fileEntry.getFileEntryId());
				}

				continue;
			}

			ObjectValuePair<String, InputStream> inputStreamOVP =
				new ObjectValuePair<>(fileEntry.getTitle(), inputStream);

			inputStreamOVPs.add(inputStreamOVP);
		}

		if (inputStreamOVPs.isEmpty()) {
			_log.error(
				"Could not find attachments for message " +
					message.getMessageId());
		}

		return inputStreamOVPs;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MBMessageStagedModelDataHandler.class);

}