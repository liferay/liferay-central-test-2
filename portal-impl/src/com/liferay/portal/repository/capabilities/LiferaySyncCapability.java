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

package com.liferay.portal.repository.capabilities;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.capabilities.BulkOperationCapability;
import com.liferay.portal.kernel.repository.capabilities.SyncCapability;
import com.liferay.portal.kernel.repository.event.RepositoryEventListener;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.repository.event.TrashRepositoryEventType;
import com.liferay.portal.kernel.repository.event.WorkflowRepositoryEventType;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryModelOperation;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackRegistryUtil;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.model.Group;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLSyncConstants;
import com.liferay.portlet.documentlibrary.model.DLSyncEvent;
import com.liferay.portlet.documentlibrary.service.DLSyncEventLocalServiceUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Adolfo Pérez
 */
public class LiferaySyncCapability implements SyncCapability {

	public void addFileEntry(FileEntry fileEntry) {
		registerDLSyncEventCallback(DLSyncConstants.EVENT_ADD, fileEntry);
	}

	public void addFolder(Folder folder) {
		registerDLSyncEventCallback(DLSyncConstants.EVENT_ADD, folder);
	}

	public void deleteFileEntry(FileEntry fileEntry) {
		registerDLSyncEventCallback(DLSyncConstants.EVENT_DELETE, fileEntry);
	}

	public void deleteFolder(Folder folder) {
		registerDLSyncEventCallback(DLSyncConstants.EVENT_DELETE, folder);
	}

	@Override
	public void destroyDocumentRepository(DocumentRepository documentRepository)
		throws PortalException {

		if (!documentRepository.isCapabilityProvided(
				BulkOperationCapability.class)) {

			return;
		}

		BulkOperationCapability bulkOperationCapability =
			documentRepository.getCapability(BulkOperationCapability.class);

		bulkOperationCapability.execute(new DeleteRepositoryModelOperation());
	}

	public void moveFileEntry(FileEntry fileEntry) {
		registerDLSyncEventCallback(DLSyncConstants.EVENT_MOVE, fileEntry);
	}

	public void moveFolder(Folder folder) {
		registerDLSyncEventCallback(DLSyncConstants.EVENT_MOVE, folder);
	}

	public void registerRepositoryEventListeners(
		RepositoryEventRegistry repositoryEventRegistry) {

		registerRepositoryEventListener(
			repositoryEventRegistry, RepositoryEventType.Add.class,
			Folder.class, "addFolder");
		registerRepositoryEventListener(
			repositoryEventRegistry, RepositoryEventType.Update.class,
			FileEntry.class, "updateFileEntry");
		registerRepositoryEventListener(
			repositoryEventRegistry, RepositoryEventType.Update.class,
			Folder.class, "updateFolder");
		registerRepositoryEventListener(
			repositoryEventRegistry, RepositoryEventType.Delete.class,
			FileEntry.class, "deleteFileEntry");
		registerRepositoryEventListener(
			repositoryEventRegistry, RepositoryEventType.Delete.class,
			Folder.class, "deleteFolder");
		registerRepositoryEventListener(
			repositoryEventRegistry, RepositoryEventType.Move.class,
			FileEntry.class, "moveFileEntry");
		registerRepositoryEventListener(
			repositoryEventRegistry, RepositoryEventType.Move.class,
			Folder.class, "moveFolder");
		registerRepositoryEventListener(
			repositoryEventRegistry,
			TrashRepositoryEventType.EntryTrashed.class, FileEntry.class,
			"trashFileEntry");
		registerRepositoryEventListener(
			repositoryEventRegistry,
			TrashRepositoryEventType.EntryRestored.class, FileEntry.class,
			"restoreFileEntry");
		registerRepositoryEventListener(
			repositoryEventRegistry,
			TrashRepositoryEventType.EntryRestored.class, Folder.class,
			"restoreFolder");
		registerRepositoryEventListener(
			repositoryEventRegistry,
			TrashRepositoryEventType.EntryTrashed.class, Folder.class,
			"trashFolder");
		registerRepositoryEventListener(
			repositoryEventRegistry, WorkflowRepositoryEventType.Add.class,
			FileEntry.class, "addFileEntry");
		registerRepositoryEventListener(
			repositoryEventRegistry, WorkflowRepositoryEventType.Update.class,
			FileEntry.class, "updateFileEntry");
	}

	public void restoreFileEntry(FileEntry fileEntry) {
		registerDLSyncEventCallback(DLSyncConstants.EVENT_RESTORE, fileEntry);
	}

	public void restoreFolder(Folder folder) {
		registerDLSyncEventCallback(DLSyncConstants.EVENT_RESTORE, folder);
	}

	public void trashFileEntry(FileEntry fileEntry) {
		registerDLSyncEventCallback(DLSyncConstants.EVENT_TRASH, fileEntry);
	}

	public void trashFolder(Folder folder) {
		registerDLSyncEventCallback(DLSyncConstants.EVENT_TRASH, folder);
	}

	public void updateFileEntry(FileEntry fileEntry) {
		registerDLSyncEventCallback(DLSyncConstants.EVENT_UPDATE, fileEntry);
	}

	public void updateFolder(Folder folder) {
		registerDLSyncEventCallback(DLSyncConstants.EVENT_UPDATE, folder);
	}

	protected static boolean isStagingGroup(long groupId) {
		try {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			return group.isStagingGroup();
		}
		catch (Exception e) {
			return false;
		}
	}

	protected static void registerDLSyncEventCallback(
		String event, FileEntry fileEntry) {

		if (isStagingGroup(fileEntry.getGroupId()) ||
			!(fileEntry instanceof LiferayFileEntry)) {

			return;
		}

		registerDLSyncEventCallback(
			event, DLSyncConstants.TYPE_FILE, fileEntry.getFileEntryId());
	}

	protected static void registerDLSyncEventCallback(String event, Folder folder) {
		if (isStagingGroup(folder.getGroupId()) ||
			!(folder instanceof LiferayFolder)) {

			return;
		}

		registerDLSyncEventCallback(
			event, DLSyncConstants.TYPE_FOLDER, folder.getFolderId());
	}

	protected static void registerDLSyncEventCallback(
		final String event, final String type, final long typePK) {

		DLSyncEvent dlSyncEvent = DLSyncEventLocalServiceUtil.addDLSyncEvent(
			event, type, typePK);

		final long modifiedTime = dlSyncEvent.getModifiedTime();

		TransactionCommitCallbackRegistryUtil.registerCallback(
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					Message message = new Message();

					Map<String, Object> values = new HashMap<String, Object>(4);

					values.put("event", event);
					values.put("modifiedTime", modifiedTime);
					values.put("type", type);
					values.put("typePK", typePK);

					message.setValues(values);

					MessageBusUtil.sendMessage(
						DestinationNames.DOCUMENT_LIBRARY_SYNC_EVENT_PROCESSOR,
						message);

					return null;
				}

			}
		);
	}

	protected <S extends RepositoryEventType, T>
		void registerRepositoryEventListener(
			RepositoryEventRegistry repositoryEventRegistry,
			Class<S> repositoryEventTypeClass, Class<T> modelClass,
			String methodName) {

		RepositoryEventListener<S, T> repositoryEventListener =
			new MethodKeyRepositoryEventListener<S, T>(
				new MethodKey(
					LiferaySyncCapability.class, methodName, modelClass));

		repositoryEventRegistry.registerRepositoryEventListener(
			repositoryEventTypeClass, modelClass, repositoryEventListener);
	}

	private class DeleteRepositoryModelOperation
		implements RepositoryModelOperation {

		@Override
		public void execute(FileEntry fileEntry) {
			deleteFileEntry(fileEntry);
		}

		@Override
		public void execute(FileVersion fileVersion) {
		}

		@Override
		public void execute(Folder folder) {
			deleteFolder(folder);
		}

	}

	private class MethodKeyRepositoryEventListener
			<S extends RepositoryEventType, T>
		implements RepositoryEventListener<S, T> {

		public MethodKeyRepositoryEventListener(MethodKey methodKey) {
			_methodKey = methodKey;
		}

		@Override
		public void execute(T model) {
			try {
				Method method = _methodKey.getMethod();

				method.invoke(LiferaySyncCapability.this, model);
			}
			catch (IllegalAccessException iae) {
				throw new SystemException(iae);
			}
			catch (InvocationTargetException ite) {
				throw new SystemException(ite);
			}
			catch (NoSuchMethodException nsme) {
				throw new SystemException(nsme);
			}
		}

		private final MethodKey _methodKey;

	}

	private static class SyncFileEntryRepositoryEventListener
			<S extends RepositoryEventType>
		implements RepositoryEventListener<S, FileEntry> {

		public SyncFileEntryRepositoryEventListener(String syncEvent) {

			_syncEvent = syncEvent;
		}

		@Override
		public void execute(FileEntry fileEntry) throws PortalException {
			registerDLSyncEventCallback(_syncEvent, fileEntry);
		}

		private final String _syncEvent;

	}

	private static class SyncFolderRepositoryEventListener
			<S extends RepositoryEventType>
		implements RepositoryEventListener<S, Folder> {

		public SyncFolderRepositoryEventListener(String syncEvent) {

			_syncEvent = syncEvent;
		}

		@Override
		public void execute(Folder folder) throws PortalException {
			registerDLSyncEventCallback(_syncEvent, folder);
		}

		private final String _syncEvent;

	}

	private static final RepositoryEventListener
		<RepositoryEventType.Add, Folder> ADD_FOLDER_EVENT_LISTENER =
			new SyncFolderRepositoryEventListener<>(DLSyncConstants.EVENT_ADD);
	private static final RepositoryEventListener
		<RepositoryEventType.Delete, FileEntry>
			DELETE_FILE_ENTRY_EVENT_LISTENER =
				new SyncFileEntryRepositoryEventListener<>(
					DLSyncConstants.EVENT_DELETE);
	private static final RepositoryEventListener
		<RepositoryEventType.Delete, Folder> DELETE_FOLDER_EVENT_LISTENER =
			new SyncFolderRepositoryEventListener<>(
				DLSyncConstants.EVENT_DELETE);
	private static final RepositoryEventListener
		<RepositoryEventType.Move, FileEntry> MOVE_FILE_ENTRY_EVENT_LISTENER =
			new SyncFileEntryRepositoryEventListener<>(
				DLSyncConstants.EVENT_MOVE);
	private static final RepositoryEventListener
		<RepositoryEventType.Move, Folder> MOVE_FOLDER_EVENT_LISTENER =
			new SyncFolderRepositoryEventListener<>(DLSyncConstants.EVENT_MOVE);
	private static final RepositoryEventListener
		<TrashRepositoryEventType.EntryRestored, FileEntry>
			RESTORE_FILE_ENTRY_EVENT_LISTENER =
				new SyncFileEntryRepositoryEventListener<>(
					DLSyncConstants.EVENT_RESTORE);
	private static final RepositoryEventListener
		<TrashRepositoryEventType.EntryRestored, Folder>
			RESTORE_FOLDER_EVENT_LISTENER =
				new SyncFolderRepositoryEventListener<>(
					DLSyncConstants.EVENT_RESTORE);
	private static final RepositoryEventListener
		<TrashRepositoryEventType.EntryTrashed, FileEntry>
			TRASH_FILE_ENTRY_EVENT_LISTENER =
				new SyncFileEntryRepositoryEventListener<>(
					DLSyncConstants.EVENT_TRASH);
	private static final RepositoryEventListener
		<TrashRepositoryEventType.EntryTrashed, Folder>
			TRASH_FOLDER_EVENT_LISTENER =
				new SyncFolderRepositoryEventListener<>(
					DLSyncConstants.EVENT_TRASH);
	private static final RepositoryEventListener
		<RepositoryEventType.Update, FileEntry>
			UPDATE_FILE_ENTRY_EVENT_LISTENER =
				new SyncFileEntryRepositoryEventListener<>(
					DLSyncConstants.EVENT_UPDATE);
	private static final RepositoryEventListener
		<RepositoryEventType.Update, Folder> UPDATE_FOLDER_EVENT_LISTENER =
			new SyncFolderRepositoryEventListener<>(
				DLSyncConstants.EVENT_UPDATE);
	private static final RepositoryEventListener
		<WorkflowRepositoryEventType.Add, FileEntry>
			WORKFLOW_ADD_FILE_ENTRY_EVENT_LISTENER =
				new SyncFileEntryRepositoryEventListener<>(
					DLSyncConstants.EVENT_ADD);
	private static final RepositoryEventListener
		<WorkflowRepositoryEventType.Update, FileEntry>
			WORKFLOW_UPDATE_FILE_ENTRY_EVENT_LISTENER =
				new SyncFileEntryRepositoryEventListener<>(
					DLSyncConstants.EVENT_UPDATE);

}