/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.lar;

import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.NoSuchTeamException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataContextListener;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.UserIdStrategy;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.impl.LockImpl;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LockLocalServiceUtil;
import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.TeamLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.blogs.model.impl.BlogsEntryImpl;
import com.liferay.portlet.bookmarks.model.impl.BookmarksEntryImpl;
import com.liferay.portlet.bookmarks.model.impl.BookmarksFolderImpl;
import com.liferay.portlet.calendar.model.impl.CalEventImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileRankImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.imagegallery.model.impl.IGFolderImpl;
import com.liferay.portlet.imagegallery.model.impl.IGImageImpl;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
import com.liferay.portlet.journal.model.impl.JournalFeedImpl;
import com.liferay.portlet.journal.model.impl.JournalStructureImpl;
import com.liferay.portlet.journal.model.impl.JournalTemplateImpl;
import com.liferay.portlet.messageboards.NoSuchDiscussionException;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBBanImpl;
import com.liferay.portlet.messageboards.model.impl.MBCategoryImpl;
import com.liferay.portlet.messageboards.model.impl.MBMessageFlagImpl;
import com.liferay.portlet.messageboards.model.impl.MBMessageImpl;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.service.persistence.MBDiscussionUtil;
import com.liferay.portlet.messageboards.service.persistence.MBMessageUtil;
import com.liferay.portlet.polls.model.impl.PollsChoiceImpl;
import com.liferay.portlet.polls.model.impl.PollsQuestionImpl;
import com.liferay.portlet.polls.model.impl.PollsVoteImpl;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.model.impl.RatingsEntryImpl;
import com.liferay.portlet.ratings.service.RatingsEntryLocalServiceUtil;
import com.liferay.portlet.wiki.model.impl.WikiNodeImpl;
import com.liferay.portlet.wiki.model.impl.WikiPageImpl;

import com.thoughtworks.xstream.XStream;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * Holds context information that is used during exporting and importing portlet
 * data.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Bruno Farache
 * @author Alex Chow
 */
public class PortletDataContextImpl implements PortletDataContext {

	public PortletDataContextImpl(
			long companyId, long groupId, Map<String, String[]> parameterMap,
			Set<String> primaryKeys, Date startDate, Date endDate,
			ZipWriter zipWriter)
		throws PortletDataException {

		validateDateRange(startDate, endDate);

		_companyId = companyId;
		_groupId = groupId;
		_scopeGroupId = groupId;
		_parameterMap = parameterMap;
		_primaryKeys = primaryKeys;
		_dataStrategy =  null;
		_userIdStrategy = null;
		_startDate = startDate;
		_endDate = endDate;
		_zipReader = null;
		_zipWriter = zipWriter;

		initXStream();
	}

	public PortletDataContextImpl(
		long companyId, long groupId, Map<String, String[]> parameterMap,
		Set<String> primaryKeys, UserIdStrategy userIdStrategy,
		ZipReader zipReader) {

		_companyId = companyId;
		_groupId = groupId;
		_scopeGroupId = groupId;
		_parameterMap = parameterMap;
		_primaryKeys = primaryKeys;
		_dataStrategy =  MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.DATA_STRATEGY,
			PortletDataHandlerKeys.DATA_STRATEGY_MIRROR);
		_userIdStrategy = userIdStrategy;
		_zipReader = zipReader;
		_zipWriter = null;

		initXStream();
	}

	public void addAssetCategories(Class<?> classObj, long classPK)
		throws SystemException {

		List<AssetCategory> assetCategories =
			AssetCategoryLocalServiceUtil.getCategories(
				classObj.getName(), classPK);

		if (assetCategories.isEmpty()) {
			return;
		}

		_assetCategoryUuidsMap.put(
			getPrimaryKeyString(classObj, classPK),
			StringUtil.split(ListUtil.toString(assetCategories, "uuid")));
		_assetCategoryIdsMap.put(
			getPrimaryKeyString(classObj, classPK),
			StringUtil.split(
				ListUtil.toString(assetCategories, "categoryId"), 0L));
	}

	public void addAssetCategories(
		String className, long classPK, long[] assetCategoryIds) {

		_assetCategoryIdsMap.put(
			getPrimaryKeyString(className, classPK), assetCategoryIds);
	}

	public void addAssetTags(Class<?> classObj, long classPK)
		throws SystemException {

		String[] tagNames = AssetTagLocalServiceUtil.getTagNames(
			classObj.getName(), classPK);

		if (tagNames.length == 0) {
			return;
		}

		_assetTagNamesMap.put(
			getPrimaryKeyString(classObj, classPK), tagNames);
	}

	public void addAssetTags(
		String className, long classPK, String[] assetTagNames) {

		_assetTagNamesMap.put(
			getPrimaryKeyString(className, classPK), assetTagNames);
	}

	public void addComments(Class<?> classObj, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(classObj);

		MBDiscussion discussion = MBDiscussionUtil.fetchByC_C(
			classNameId, classPK);

		if (discussion == null) {
			return;
		}

		List<MBMessage> messages = MBMessageLocalServiceUtil.getThreadMessages(
			discussion.getThreadId(), WorkflowConstants.STATUS_APPROVED);

		if (messages.size() == 0) {
			return;
		}

		Iterator<MBMessage> itr = messages.iterator();

		while (itr.hasNext()) {
			MBMessage message = itr.next();

			message.setUserUuid(message.getUserUuid());

			addRatingsEntries(MBMessage.class, message.getPrimaryKey());
		}

		_commentsMap.put(getPrimaryKeyString(classObj, classPK), messages);
	}

	public void addComments(
		String className, long classPK, List<MBMessage> messages) {

		_commentsMap.put(getPrimaryKeyString(className, classPK), messages);
	}

	public void addLocks(Class<?> classObj, String key)
		throws PortalException, SystemException {

		if (!_locksMap.containsKey(getPrimaryKeyString(classObj, key)) &&
			LockLocalServiceUtil.isLocked(classObj.getName(), key)) {

			Lock lock = LockLocalServiceUtil.getLock(classObj.getName(), key);

			addLocks(classObj.getName(), key, lock);
		}
	}

	public void addLocks(String className, String key, Lock lock) {
		_locksMap.put(getPrimaryKeyString(className, key), lock);
	}

	public void addPermissions(Class<?> classObj, long classPK)
		throws PortalException, SystemException {

		addPermissions(classObj.getName(), classPK);
	}

	public void addPermissions(String resourceName, long resourcePK)
		throws PortalException, SystemException {

		if (((PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 5) &&
			 (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 6)) ||
			(!MapUtil.getBoolean(
				_parameterMap, PortletDataHandlerKeys.PERMISSIONS))) {

			return;
		}

		List<KeyValuePair> permissions = new ArrayList<KeyValuePair>();

		Group group = GroupLocalServiceUtil.getGroup(_groupId);

		List<Role> roles = RoleLocalServiceUtil.getRoles(_companyId);

		for (Role role : roles) {
			int type = role.getType();

			if ((type == RoleConstants.TYPE_REGULAR) ||
				((type == RoleConstants.TYPE_COMMUNITY) &&
				 (group.isCommunity())) ||
				((type == RoleConstants.TYPE_ORGANIZATION) &&
				 (group.isOrganization()))) {

				String name = role.getName();
				String actionIds = getActionIds(
					role, resourceName, String.valueOf(resourcePK));

				KeyValuePair permission = new KeyValuePair(name, actionIds);

				permissions.add(permission);
			}
			else if ((type == RoleConstants.TYPE_PROVIDER) && role.isTeam()) {
				Team team = TeamLocalServiceUtil.getTeam(role.getClassPK());

				if (team.getGroupId() != _groupId) {
					String name =
						PermissionExporter.ROLE_TEAM_PREFIX + team.getName();
					String actionIds = getActionIds(
						role, resourceName, String.valueOf(resourcePK));

					KeyValuePair permission = new KeyValuePair(name, actionIds);

					permissions.add(permission);
				}
			}
		}

		_permissionsMap.put(
			getPrimaryKeyString(resourceName, resourcePK), permissions);
	}

	public void addPermissions(
		String resourceName, long resourcePK, List<KeyValuePair> permissions) {

		if ((PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 5) &&
			(PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 6)) {

			return;
		}

		_permissionsMap.put(
			getPrimaryKeyString(resourceName, resourcePK), permissions);
	}

	public boolean addPrimaryKey(Class<?> classObj, String primaryKey) {
		boolean value = hasPrimaryKey(classObj, primaryKey);

		if (!value) {
			_primaryKeys.add(getPrimaryKeyString(classObj, primaryKey));
		}

		return value;
	}

	public void addRatingsEntries(Class<?> classObj, long classPK)
		throws SystemException {

		List<RatingsEntry> ratingsEntries =
			RatingsEntryLocalServiceUtil.getEntries(
				classObj.getName(), classPK);

		if (ratingsEntries.size() == 0) {
			return;
		}

		Iterator<RatingsEntry> itr = ratingsEntries.iterator();

		while (itr.hasNext()) {
			RatingsEntry entry = itr.next();

			entry.setUserUuid(entry.getUserUuid());
		}

		_ratingsEntriesMap.put(
			getPrimaryKeyString(classObj, classPK), ratingsEntries);
	}

	public void addRatingsEntries(
		String className, long classPK, List<RatingsEntry> ratingsEntries) {

		_ratingsEntriesMap.put(
			getPrimaryKeyString(className, classPK), ratingsEntries);
	}

	public void addZipEntry(String path, byte[] bytes) throws SystemException {
		if (_portletDataContextListener != null) {
			_portletDataContextListener.onAddZipEntry(path);
		}

		try {
			getZipWriter().addEntry(path, bytes);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public void addZipEntry(String path, InputStream is)
		throws SystemException {

		if (_portletDataContextListener != null) {
			_portletDataContextListener.onAddZipEntry(path);
		}

		try {
			getZipWriter().addEntry(path, is);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public void addZipEntry(String path, Object object) throws SystemException {
		addZipEntry(path, toXML(object));
	}

	public void addZipEntry(String path, String s) throws SystemException {
		if (_portletDataContextListener != null) {
			_portletDataContextListener.onAddZipEntry(path);
		}

		try {
			getZipWriter().addEntry(path, s);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public void addZipEntry(String path, StringBuilder sb)
		throws SystemException {

		if (_portletDataContextListener != null) {
			_portletDataContextListener.onAddZipEntry(path);
		}

		try {
			getZipWriter().addEntry(path, sb);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public Object fromXML(byte[] bytes) {
		return _xStream.fromXML(new String(bytes));
	}

	public Object fromXML(String xml) {
		return _xStream.fromXML(xml);
	}

	public long[] getAssetCategoryIds(Class<?> classObj, long classPK) {
		return _assetCategoryIdsMap.get(
			getPrimaryKeyString(classObj, classPK));
	}

	public Map<String, long[]> getAssetCategoryIdsMap() {
		return _assetCategoryIdsMap;
	}

	public Map<String, String[]> getAssetCategoryUuidsMap() {
		return _assetCategoryUuidsMap;
	}

	public String[] getAssetTagNames(Class<?> classObj, long classPK) {
		return _assetTagNamesMap.get(getPrimaryKeyString(classObj, classPK));
	}

	public String[] getAssetTagNames(String className, long classPK) {
		return _assetTagNamesMap.get(getPrimaryKeyString(className, classPK));
	}

	public Map<String, String[]> getAssetTagNamesMap() {
		return _assetTagNamesMap;
	}

	public boolean getBooleanParameter(String namespace, String name) {
		boolean defaultValue = MapUtil.getBoolean(
			getParameterMap(),
			PortletDataHandlerKeys.PORTLET_DATA_CONTROL_DEFAULT, true);

		return MapUtil.getBoolean(
			getParameterMap(),
			PortletDataHandlerControl.getNamespacedControlName(namespace, name),
			defaultValue);
	}

	public ClassLoader getClassLoader() {
		return _xStream.getClassLoader();
	}

	public Map<String, List<MBMessage>> getComments() {
		return _commentsMap;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public String getDataStrategy() {
		return _dataStrategy;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public long getGroupId() {
		return _groupId;
	}

	public String getLayoutPath(long layoutId) {
		return getRootPath() + ROOT_PATH_LAYOUTS + layoutId;
	}

	public Map<String, Lock> getLocks() {
		return _locksMap;
	}

	public Map<?, ?> getNewPrimaryKeysMap(Class<?> classObj) {
		return getNewPrimaryKeysMap(classObj.getName());
	}

	public Map<?, ?> getNewPrimaryKeysMap(String className) {
		Map<?, ?> map = _newPrimaryKeysMaps.get(className);

		if (map == null) {
			map = new HashMap<Object, Object>();

			_newPrimaryKeysMaps.put(className, map);
		}

		return map;
	}

	public long getOldPlid() {
		return _oldPlid;
	}

	public Map<String, String[]> getParameterMap() {
		return _parameterMap;
	}

	public Map<String, List<KeyValuePair>> getPermissions() {
		return _permissionsMap;
	}

	public long getPlid() {
		return _plid;
	}

	public String getPortletPath(String portletId) {
		return getRootPath() + ROOT_PATH_PORTLETS + portletId;
	}

	public Set<String> getPrimaryKeys() {
		return _primaryKeys;
	}

	public Map<String, List<RatingsEntry>> getRatingsEntries() {
		return _ratingsEntriesMap;
	}

	public String getRootPath() {
		return ROOT_PATH_GROUPS + getScopeGroupId();
	}

	public long getScopeGroupId() {
		return _scopeGroupId;
	}

	public String getScopeLayoutUuid() {
		return _scopeLayoutUuid;
	}

	public long getSourceGroupId() {
		return _sourceGroupId;
	}

	public String getSourceLayoutPath(long layoutId) {
		return getSourceRootPath() + ROOT_PATH_LAYOUTS + layoutId;
	}

	public String getSourcePortletPath(String portletId) {
		return getSourceRootPath() + ROOT_PATH_PORTLETS + portletId;
	}

	public String getSourceRootPath() {
		return ROOT_PATH_GROUPS + getSourceGroupId();
	}

	public Date getStartDate() {
		return _startDate;
	}

	public long getUserId(String userUuid) throws SystemException {
		return _userIdStrategy.getUserId(userUuid);
	}

	public UserIdStrategy getUserIdStrategy() {
		return _userIdStrategy;
	}

	public List<String> getZipEntries() {
		return getZipReader().getEntries();
	}

	public byte[] getZipEntryAsByteArray(String path) {
		if (_portletDataContextListener != null) {
			_portletDataContextListener.onGetZipEntry(path);
		}

		return getZipReader().getEntryAsByteArray(path);
	}

	public InputStream getZipEntryAsInputStream(String path) {
		if (_portletDataContextListener != null) {
			_portletDataContextListener.onGetZipEntry(path);
		}

		return getZipReader().getEntryAsInputStream(path);
	}

	public Object getZipEntryAsObject(String path) {
		return fromXML(getZipEntryAsString(path));
	}

	public String getZipEntryAsString(String path) {
		if (_portletDataContextListener != null) {
			_portletDataContextListener.onGetZipEntry(path);
		}

		return getZipReader().getEntryAsString(path);
	}

	public List<String> getZipFolderEntries() {
		return getZipFolderEntries(StringPool.SLASH);
	}

	public List<String> getZipFolderEntries(String path) {
		return getZipReader().getFolderEntries(path);
	}

	public ZipReader getZipReader() {
		return _zipReader;
	}

	public ZipWriter getZipWriter() {
		return _zipWriter;
	}

	public boolean hasDateRange() {
		if (_startDate != null) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean hasNotUniquePerLayout(String dataKey) {
		return _notUniquePerLayout.contains(dataKey);
	}

	public boolean hasPrimaryKey(Class<?> classObj, String primaryKey) {
		return _primaryKeys.contains(getPrimaryKeyString(classObj, primaryKey));
	}

	public void importComments(
			Class<?> classObj, long classPK, long newClassPK, long groupId)
		throws PortalException, SystemException {

		Map<Long, Long> messagePKs = new HashMap<Long, Long>();
		Map<Long, Long> threadPKs = new HashMap<Long, Long>();

		List<MBMessage> messages = _commentsMap.get(
			getPrimaryKeyString(classObj, classPK));

		if (messages == null) {
			return;
		}

		MBDiscussion discussion = null;

		try {
			discussion = MBDiscussionLocalServiceUtil.getDiscussion(
				classObj.getName(), newClassPK);
		}
		catch (NoSuchDiscussionException nsde) {
		}

		for (MBMessage message : messages) {
			long userId = getUserId(message.getUserUuid());
			long parentMessageId = MapUtil.getLong(
				messagePKs, message.getParentMessageId(),
				message.getParentMessageId());
			long threadId = MapUtil.getLong(
				threadPKs, message.getThreadId(), message.getThreadId());

			if ((message.getParentMessageId() ==
					MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID) &&
				(discussion != null)) {

				MBThread thread = MBThreadLocalServiceUtil.getThread(
					discussion.getThreadId());

				long rootMessageId = thread.getRootMessageId();

				messagePKs.put(message.getMessageId(), rootMessageId);
				threadPKs.put(message.getThreadId(), thread.getThreadId());
			}
			else {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setScopeGroupId(groupId);

				MBMessage importedMessage = null;

				if (_dataStrategy.equals(
						PortletDataHandlerKeys.DATA_STRATEGY_MIRROR) ||
					_dataStrategy.equals(
						PortletDataHandlerKeys.
							DATA_STRATEGY_MIRROR_OVERWRITE)) {

					MBMessage existingMessage = MBMessageUtil.fetchByUUID_G(
						message.getUuid(), groupId);

					if (existingMessage == null) {
						serviceContext.setUuid(message.getUuid());

						importedMessage =
							MBMessageLocalServiceUtil.addDiscussionMessage(
								userId, message.getUserName(), groupId,
								classObj.getName(), newClassPK, threadId,
								parentMessageId, message.getSubject(),
								message.getBody(), null, null, null,
								serviceContext);
					}
					else {
						importedMessage =
							MBMessageLocalServiceUtil.updateDiscussionMessage(
								userId, existingMessage.getMessageId(),
								classObj.getName(), newClassPK,
								message.getSubject(), message.getBody(),
								WorkflowConstants.ACTION_PUBLISH);
					}
				}
				else {
					importedMessage =
						MBMessageLocalServiceUtil.addDiscussionMessage(
							userId, message.getUserName(), groupId,
							classObj.getName(), newClassPK, threadId,
							parentMessageId, message.getSubject(),
							message.getBody(), null, null, null,
							serviceContext);
				}

				messagePKs.put(
					message.getMessageId(), importedMessage.getMessageId());
				threadPKs.put(
					message.getThreadId(), importedMessage.getThreadId());
			}

			importRatingsEntries(
				MBMessage.class, message.getPrimaryKey(),
				messagePKs.get(message.getPrimaryKey()));
		}
	}

	public void importLocks(Class<?> classObj, String key, String newKey)
		throws PortalException, SystemException {

		Lock lock = _locksMap.get(getPrimaryKeyString(classObj, key));

		if (lock == null) {
			return;
		}

		long userId = getUserId(lock.getUserUuid());

		long expirationTime = 0;

		if (lock.getExpirationDate() != null) {
			Date expirationDate = lock.getExpirationDate();

			expirationTime = expirationDate.getTime();
		}

		LockLocalServiceUtil.lock(
			userId, classObj.getName(), newKey, lock.getOwner(),
			lock.getInheritable(), expirationTime);
	}

	public void importPermissions(
			Class<?> classObj, long classPK, long newClassPK)
		throws PortalException, SystemException {

		importPermissions(classObj.getName(), classPK, newClassPK);
	}

	public void importPermissions(
			String resourceName, long resourcePK, long newResourcePK)
		throws PortalException, SystemException {

		if (((PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 5) &&
			 (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 6)) ||
			(!MapUtil.getBoolean(
				_parameterMap, PortletDataHandlerKeys.PERMISSIONS))) {

			return;
		}

		List<KeyValuePair> permissions = _permissionsMap.get(
			getPrimaryKeyString(resourceName, resourcePK));

		if (permissions == null) {
			return;
		}

		for (KeyValuePair permission : permissions) {
			String roleName = permission.getKey();

			Role role = null;

			Team team = null;

			if (roleName.startsWith(PermissionExporter.ROLE_TEAM_PREFIX)) {
				roleName = roleName.substring(
					PermissionExporter.ROLE_TEAM_PREFIX.length());

				try {
					team = TeamLocalServiceUtil.getTeam(_groupId, roleName);
				}
				catch (NoSuchTeamException nste) {
					if (_log.isWarnEnabled()) {
						_log.warn("Team " + roleName + " does not exist");
					}

					continue;
				}
			}

			try {
				if (team != null) {
					role = RoleLocalServiceUtil.getTeamRole(
						_companyId, team.getTeamId());
				}
				else {
					role = RoleLocalServiceUtil.getRole(_companyId, roleName);
				}
			}
			catch (NoSuchRoleException nsre) {
				if (_log.isWarnEnabled()) {
					_log.warn("Role " + roleName + " does not exist");
				}

				continue;
			}

			String[] actionIds = StringUtil.split(permission.getValue());

			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
				Resource resource = null;

				try {
					resource = ResourceLocalServiceUtil.getResource(
						_companyId, resourceName,
						ResourceConstants.SCOPE_INDIVIDUAL,
						String.valueOf(newResourcePK));
				}
				catch (NoSuchResourceException nsre) {
					resource = ResourceLocalServiceUtil.addResource(
						_companyId, resourceName,
						ResourceConstants.SCOPE_INDIVIDUAL,
						String.valueOf(newResourcePK));
				}

				PermissionLocalServiceUtil.setRolePermissions(
					role.getRoleId(), actionIds, resource.getResourceId());
			}
			else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
				ResourcePermissionLocalServiceUtil.setResourcePermissions(
					_companyId, resourceName,
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(newResourcePK), role.getRoleId(), actionIds);
			}
		}
	}

	public void importRatingsEntries(
			Class<?> classObj, long classPK, long newClassPK)
		throws PortalException, SystemException {

		List<RatingsEntry> ratingsEntries = _ratingsEntriesMap.get(
			getPrimaryKeyString(classObj, classPK));

		if (ratingsEntries == null) {
			return;
		}

		ServiceContext serviceContext = new ServiceContext();

		for (RatingsEntry ratingsEntry : ratingsEntries) {
			long userId = getUserId(ratingsEntry.getUserUuid());

			serviceContext.setCreateDate(ratingsEntry.getCreateDate());
			serviceContext.setModifiedDate(ratingsEntry.getModifiedDate());

			RatingsEntryLocalServiceUtil.updateEntry(
				userId, classObj.getName(), ((Long)newClassPK).longValue(),
				ratingsEntry.getScore(), serviceContext);
		}
	}

	public boolean isDataStrategyMirror() {
		if (_dataStrategy.equals(PortletDataHandlerKeys.DATA_STRATEGY_MIRROR) ||
			_dataStrategy.equals(
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE)) {

			return true;
		}
		else {
			return false;
		}
	}

	public boolean isDataStrategyMirrorWithOverwritting() {
		if (_dataStrategy.equals(
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE)) {

			return true;
		}
		else {
			return false;
		}
	}

	public boolean isPathNotProcessed(String path) {
		return !addPrimaryKey(String.class, path);
	}

	public boolean isPerformDirectBinaryImport() {
		return MapUtil.getBoolean(
			_parameterMap, PortletDataHandlerKeys.PERFORM_DIRECT_BINARY_IMPORT);
	}

	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	public boolean isWithinDateRange(Date modifiedDate) {
		if (!hasDateRange()) {
			return true;
		}
		else if ((_startDate.compareTo(modifiedDate) <= 0) &&
				 (_endDate.after(modifiedDate))) {

			return true;
		}
		else {
			return false;
		}
	}

	public void putNotUniquePerLayout(String dataKey) {
		_notUniquePerLayout.add(dataKey);
	}

	public void setClassLoader(ClassLoader classLoader) {
		_xStream.setClassLoader(classLoader);
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setOldPlid(long oldPlid) {
		_oldPlid = oldPlid;
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	public void setPortetDataContextListener(
		PortletDataContextListener portletDataContextListener) {

		_portletDataContextListener = portletDataContextListener;
	}

	public void setPrivateLayout(boolean privateLayout) {
		_privateLayout = privateLayout;
	}

	public void setScopeGroupId(long scopeGroupId) {
		_scopeGroupId = scopeGroupId;
	}

	public void setScopeLayoutUuid(String scopeLayoutUuid) {
		_scopeLayoutUuid = scopeLayoutUuid;
	}

	public void setSourceGroupId(long sourceGroupId) {
		_sourceGroupId = sourceGroupId;
	}

	public String toXML(Object object) {
		return _xStream.toXML(object);
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	protected String getActionIds(
			Role role, String className, String primKey)
		throws PortalException, SystemException {

		List<String> allActionIds = ResourceActionsUtil.getModelResourceActions(
			className);

		List<String> actionIds = new ArrayList<String>(allActionIds.size());

		for (String actionId : allActionIds) {
			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
				if (PermissionLocalServiceUtil.hasRolePermission(
						role.getRoleId(), role.getCompanyId(), className,
						ResourceConstants.SCOPE_INDIVIDUAL, primKey,
						actionId)) {

					actionIds.add(actionId);
				}
			}
			else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
				if (ResourcePermissionLocalServiceUtil.hasResourcePermission(
						role.getCompanyId(), className,
						ResourceConstants.SCOPE_INDIVIDUAL, primKey,
						role.getRoleId(), actionId)) {

					actionIds.add(actionId);
				}
			}
		}

		return StringUtil.merge(actionIds);
	}

	protected String getPrimaryKeyString(Class<?> classObj, long classPK) {
		return getPrimaryKeyString(classObj.getName(), String.valueOf(classPK));
	}

	protected String getPrimaryKeyString(Class<?> classObj, String primaryKey) {
		return getPrimaryKeyString(classObj.getName(), primaryKey);
	}

	protected String getPrimaryKeyString(String className, long classPK) {
		return getPrimaryKeyString(className, String.valueOf(classPK));
	}

	protected String getPrimaryKeyString(String className, String primaryKey) {
		return className.concat(StringPool.POUND).concat(primaryKey);
	}

	protected void initXStream() {
		_xStream = new XStream();

		_xStream.alias("BlogsEntry", BlogsEntryImpl.class);
		_xStream.alias("BookmarksFolder", BookmarksFolderImpl.class);
		_xStream.alias("BookmarksEntry", BookmarksEntryImpl.class);
		_xStream.alias("CalEvent", CalEventImpl.class);
		_xStream.alias("DLFolder", DLFolderImpl.class);
		_xStream.alias("DLFileEntry", DLFileEntryImpl.class);
		_xStream.alias("DLFileShortcut", DLFileShortcutImpl.class);
		_xStream.alias("DLFileRank", DLFileRankImpl.class);
		_xStream.alias("IGFolder", IGFolderImpl.class);
		_xStream.alias("IGImage", IGImageImpl.class);
		_xStream.alias("JournalArticle", JournalArticleImpl.class);
		_xStream.alias("JournalFeed", JournalFeedImpl.class);
		_xStream.alias("JournalStructure", JournalStructureImpl.class);
		_xStream.alias("JournalTemplate", JournalTemplateImpl.class);
		_xStream.alias("Lock", LockImpl.class);
		_xStream.alias("MBCategory", MBCategoryImpl.class);
		_xStream.alias("MBMessage", MBMessageImpl.class);
		_xStream.alias("MBMessageFlag", MBMessageFlagImpl.class);
		_xStream.alias("MBBan", MBBanImpl.class);
		_xStream.alias("PollsQuestion", PollsQuestionImpl.class);
		_xStream.alias("PollsChoice", PollsChoiceImpl.class);
		_xStream.alias("PollsVote", PollsVoteImpl.class);
		_xStream.alias("RatingsEntry", RatingsEntryImpl.class);
		_xStream.alias("WikiNode", WikiNodeImpl.class);
		_xStream.alias("WikiPage", WikiPageImpl.class);
	}

	protected void validateDateRange(Date startDate, Date endDate)
		throws PortletDataException {

		if ((startDate == null) ^ (endDate == null)) {
			throw new PortletDataException(
				"Both start and end dates must have valid values or be null");
		}

		if (startDate != null) {
			if (startDate.after(endDate) || startDate.equals(endDate)) {
				throw new PortletDataException(
					"The start date cannot be after the end date");
			}

			Date now = new Date();

			if (startDate.after(now) || endDate.after(now)) {
				throw new PortletDataException(
					"Dates must not be in the future");
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortletDataContextImpl.class);

	private Map<String, long[]> _assetCategoryIdsMap =
		new HashMap<String, long[]>();
	private Map<String, String[]> _assetCategoryUuidsMap =
		new HashMap<String, String[]>();
	private Map<String, String[]> _assetTagNamesMap =
		new HashMap<String, String[]>();
	private Map<String, List<MBMessage>> _commentsMap =
		new HashMap<String, List<MBMessage>>();
	private long _companyId;
	private String _dataStrategy;
	private Date _endDate;
	private long _groupId;
	private Map<String, Lock> _locksMap = new HashMap<String, Lock>();
	private Map<String, Map<?, ?>> _newPrimaryKeysMaps =
		new HashMap<String, Map<?, ?>>();
	private Set<String> _notUniquePerLayout = new HashSet<String>();
	private long _oldPlid;
	private Map<String, String[]> _parameterMap;
	private Map<String, List<KeyValuePair>> _permissionsMap =
		new HashMap<String, List<KeyValuePair>>();
	private long _plid;
	private PortletDataContextListener _portletDataContextListener;
	private Set<String> _primaryKeys;
	private boolean _privateLayout;
	private Map<String, List<RatingsEntry>> _ratingsEntriesMap =
		new HashMap<String, List<RatingsEntry>>();
	private long _scopeGroupId;
	private String _scopeLayoutUuid;
	private long _sourceGroupId;
	private Date _startDate;
	private UserIdStrategy _userIdStrategy;
	private XStream _xStream;
	private ZipReader _zipReader;
	private ZipWriter _zipWriter;

}