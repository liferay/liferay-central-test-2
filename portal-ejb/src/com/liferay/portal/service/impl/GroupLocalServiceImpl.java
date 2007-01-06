/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.counter.model.Counter;
import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.DuplicateGroupException;
import com.liferay.portal.GroupFriendlyURLException;
import com.liferay.portal.GroupNameException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.PortalException;
import com.liferay.portal.RequiredGroupException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.OrgGroupPermission;
import com.liferay.portal.model.OrgGroupRole;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.GroupFinder;
import com.liferay.portal.service.persistence.GroupUtil;
import com.liferay.portal.service.persistence.LayoutSetUtil;
import com.liferay.portal.service.persistence.OrgGroupPermissionPK;
import com.liferay.portal.service.persistence.OrgGroupPermissionUtil;
import com.liferay.portal.service.persistence.OrgGroupRolePK;
import com.liferay.portal.service.persistence.OrgGroupRoleUtil;
import com.liferay.portal.service.persistence.ResourceUtil;
import com.liferay.portal.service.persistence.RoleUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.service.persistence.BlogsEntryUtil;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.persistence.BookmarksFolderUtil;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.service.CalEventLocalServiceUtil;
import com.liferay.portlet.calendar.service.persistence.CalEventUtil;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderUtil;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.service.IGFolderLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.persistence.IGFolderUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalContentSearch;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.persistence.JournalArticlePK;
import com.liferay.portlet.journal.service.persistence.JournalArticleUtil;
import com.liferay.portlet.journal.service.persistence.JournalContentSearchPK;
import com.liferay.portlet.journal.service.persistence.JournalContentSearchUtil;
import com.liferay.portlet.journal.service.persistence.JournalStructurePK;
import com.liferay.portlet.journal.service.persistence.JournalStructureUtil;
import com.liferay.portlet.journal.service.persistence.JournalTemplatePK;
import com.liferay.portlet.journal.service.persistence.JournalTemplateUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBStatsUser;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBStatsUserLocalServiceUtil;
import com.liferay.portlet.messageboards.service.persistence.MBCategoryUtil;
import com.liferay.portlet.messageboards.service.persistence.MBStatsUserPK;
import com.liferay.portlet.messageboards.service.persistence.MBStatsUserUtil;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.service.PollsQuestionLocalServiceUtil;
import com.liferay.portlet.polls.service.persistence.PollsQuestionUtil;
import com.liferay.portlet.shopping.model.ShoppingCart;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.model.ShoppingCoupon;
import com.liferay.portlet.shopping.model.ShoppingOrder;
import com.liferay.portlet.shopping.service.ShoppingCartLocalServiceUtil;
import com.liferay.portlet.shopping.service.persistence.ShoppingCartUtil;
import com.liferay.portlet.shopping.service.persistence.ShoppingCategoryUtil;
import com.liferay.portlet.shopping.service.persistence.ShoppingCouponUtil;
import com.liferay.portlet.shopping.service.persistence.ShoppingOrderUtil;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.portlet.wiki.service.persistence.WikiNodeUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <a href="GroupLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @author  Alexander Chow
 *
 */
public class GroupLocalServiceImpl implements GroupLocalService {

	public Group addGroup(
			String userId, String className, String classPK, String name,
			String description, String type, String friendlyURL)
		throws PortalException, SystemException {

		// Group

		User user = UserUtil.findByPrimaryKey(userId);

		if (Validator.isNull(className) || Validator.isNull(classPK)) {
			validateName(0, user.getActualCompanyId(), name);
		}

		validateFriendlyURL(0, user.getActualCompanyId(), friendlyURL);

		long groupId = CounterLocalServiceUtil.increment(
			Counter.class.getName());

		if (Validator.isNotNull(className) && Validator.isNotNull(classPK)) {
			name = String.valueOf(groupId);
		}

		Group group = GroupUtil.create(groupId);

		group.setCompanyId(user.getActualCompanyId());
		group.setClassName(className);
		group.setClassPK(classPK);
		group.setParentGroupId(GroupImpl.DEFAULT_PARENT_GROUP_ID);
		group.setName(name);
		group.setDescription(description);
		group.setType(type);
		group.setFriendlyURL(friendlyURL);

		GroupUtil.update(group);

		// Layout sets

		LayoutSetLocalServiceUtil.addLayoutSet(
			LayoutImpl.PRIVATE + groupId, group.getCompanyId());

		LayoutSetLocalServiceUtil.addLayoutSet(
			LayoutImpl.PUBLIC + groupId, group.getCompanyId());

		if (Validator.isNull(className) && Validator.isNull(classPK) &&
			!UserImpl.isDefaultUser(userId)) {

			// Resources

			ResourceLocalServiceUtil.addResources(
				group.getCompanyId(), 0, userId, Group.class.getName(),
				String.valueOf(group.getPrimaryKey()), false, false, false);

			// User

			UserLocalServiceUtil.addGroupUsers(
				group.getGroupId(), new String[] {userId});
		}

		return group;
	}

	public void addRoleGroups(String roleId, long[] groupIds)
		throws PortalException, SystemException {

		RoleUtil.addGroups(roleId, groupIds);
	}

	public void checkSystemGroups(String companyId)
		throws PortalException, SystemException {

		String[] systemGroups = PortalUtil.getSystemGroups();

		for (int i = 0; i < systemGroups.length; i++) {
			Group group = null;

			try {
				group = GroupFinder.findByC_N(companyId, systemGroups[i]);
			}
			catch (NoSuchGroupException nsge) {
				group = addGroup(
					UserImpl.getDefaultUserId(companyId), null, null,
					systemGroups[i], null, null, null);
			}

			if (group.getName().equals(GroupImpl.GUEST)) {
				LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
					LayoutImpl.PUBLIC + group.getGroupId());

				if (layoutSet.getPageCount() == 0) {
					addDefaultLayouts(group);
				}
			}
		}
	}

	public void deleteGroup(long groupId)
		throws PortalException, SystemException {

		Group group = GroupUtil.findByPrimaryKey(groupId);

		if (PortalUtil.isSystemGroup(group.getName())) {
			throw new RequiredGroupException();
		}

		// Layout sets

		LayoutSetLocalServiceUtil.deleteLayoutSet(LayoutImpl.PRIVATE + groupId);
		LayoutSetLocalServiceUtil.deleteLayoutSet(LayoutImpl.PUBLIC + groupId);

		// Role

		try {
			Role role = RoleLocalServiceUtil.getGroupRole(
				group.getCompanyId(), groupId);

			RoleLocalServiceUtil.deleteRole(role.getRoleId());
		}
		catch (NoSuchRoleException nsre) {
		}

		// Blogs

		BlogsEntryLocalServiceUtil.deleteEntries(groupId);

		// Bookmarks

		BookmarksFolderLocalServiceUtil.deleteFolders(groupId);

		// Calendar

		CalEventLocalServiceUtil.deleteEvents(groupId);

		// Document library

		DLFolderLocalServiceUtil.deleteFolders(groupId);

		// Image gallery

		IGFolderLocalServiceUtil.deleteFolders(groupId);

		// Journal

		JournalArticleLocalServiceUtil.deleteArticles(groupId);

		// Message boards

		MBCategoryLocalServiceUtil.deleteCategories(groupId);
		MBStatsUserLocalServiceUtil.deleteStatsUserByGroupId(groupId);

		// Polls

		PollsQuestionLocalServiceUtil.deleteQuestions(groupId);

		// Shopping

		ShoppingCartLocalServiceUtil.deleteGroupCarts(groupId);

		// Wiki

		WikiNodeLocalServiceUtil.deleteNodes(groupId);

		// Resources

		Iterator itr = ResourceUtil.findByC_T_S_P(
			group.getCompanyId(), ResourceImpl.TYPE_CLASS,
			ResourceImpl.SCOPE_GROUP, String.valueOf(groupId)).iterator();

		while (itr.hasNext()) {
			Resource resource = (Resource)itr.next();

			ResourceLocalServiceUtil.deleteResource(resource);
		}

		if (Validator.isNull(group.getClassName()) &&
			Validator.isNull(group.getClassPK())) {

			ResourceLocalServiceUtil.deleteResource(
				group.getCompanyId(), Group.class.getName(),
				ResourceImpl.TYPE_CLASS, ResourceImpl.SCOPE_INDIVIDUAL,
				group.getPrimaryKey());
		}

		// Group

		GroupUtil.remove(groupId);
	}

	public Group getFriendlyURLGroup(String companyId, String friendlyURL)
		throws PortalException, SystemException {

		if (Validator.isNull(friendlyURL)) {
			throw new NoSuchGroupException();
		}

		return GroupUtil.findByC_F(companyId, friendlyURL);
	}

	public Group getGroup(long groupId)
		throws PortalException, SystemException {

		return GroupUtil.findByPrimaryKey(groupId);
	}

	public Group getGroup(String companyId, String name)
		throws PortalException, SystemException {

		return GroupFinder.findByC_N(companyId, name);
	}

	public Group getOrganizationGroup(String companyId, String organizationId)
		throws PortalException, SystemException {

		return GroupUtil.findByC_C_C(
			companyId, Organization.class.getName(), organizationId);
	}

	public List getOrganizationsGroups(List organizations)
		throws PortalException, SystemException {

		List organizationGroups = new ArrayList();

		for (int i = 0; i < organizations.size(); i++) {
			Organization organization = (Organization)organizations.get(i);

			Group group = organization.getGroup();

			organizationGroups.add(group);
		}

		return organizationGroups;
	}

	public List getRoleGroups(String roleId)
		throws PortalException, SystemException {

		return RoleUtil.getGroups(roleId);
	}

	public Group getUserGroup(String companyId, String userId)
		throws PortalException, SystemException {

		return GroupUtil.findByC_C_C(
			companyId, User.class.getName(), userId);
	}

	public Group getUserGroupGroup(String companyId, String userGroupId)
		throws PortalException, SystemException {

		return GroupUtil.findByC_C_C(
			companyId, UserGroup.class.getName(), userGroupId);
	}

	public List getUserGroupsGroups(List userGroups)
		throws PortalException, SystemException {

		List userGroupGroups = new ArrayList();

		for (int i = 0; i < userGroups.size(); i++) {
			UserGroup userGroup = (UserGroup)userGroups.get(i);

			Group group = userGroup.getGroup();

			userGroupGroups.add(group);
		}

		return userGroupGroups;
	}

	public boolean hasRoleGroup(String roleId, long groupId)
		throws PortalException, SystemException {

		return RoleUtil.containsGroup(roleId, groupId);
	}

	public boolean hasUserGroup(String userId, long groupId)
		throws SystemException {

		if (GroupFinder.countByG_U(groupId, userId) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public Map renewGroupIds() throws PortalException, SystemException {

		List groups = GroupUtil.findAll();

		// Save old groups, mappings, and entries with composite keys

		Map orgMap = new HashMap(groups.size());
		Map permissionMap = new HashMap(groups.size());
		Map roleMap = new HashMap(groups.size());
		Map userMap = new HashMap(groups.size());
		Map userGroupMap = new HashMap(groups.size());

		Map journalArticleMap = new HashMap(groups.size());
		Map journalContentSearchMap = new HashMap(groups.size());
		Map journalStructureMap = new HashMap(groups.size());
		Map journalTemplateMap = new HashMap(groups.size());
		Map mBStatsUserMap = new HashMap(groups.size());
		Map orgPermissionMap = new HashMap(groups.size());
		Map orgRoleMap = new HashMap(groups.size());

		Iterator itr1 = groups.iterator();

		while (itr1.hasNext()) {
			Group group = (Group)itr1.next();

			long groupId = group.getGroupId();
			Long wrappedGroupId = new Long(groupId);

			orgMap.put(wrappedGroupId, GroupUtil.getOrganizations(groupId));
			permissionMap.put(
				wrappedGroupId, GroupUtil.getPermissions(groupId));
			roleMap.put(wrappedGroupId, GroupUtil.getRoles(groupId));
			userMap.put(wrappedGroupId, GroupUtil.getUsers(groupId));
			userGroupMap.put(wrappedGroupId, GroupUtil.getUserGroups(groupId));

			journalArticleMap.put(
				wrappedGroupId, JournalArticleUtil.findByGroupId(groupId));
			JournalArticleUtil.removeByGroupId(groupId);
			journalContentSearchMap.put(
				wrappedGroupId,
				JournalContentSearchUtil.findByGroupId(groupId));
			JournalContentSearchUtil.removeByGroupId(groupId);
			journalStructureMap.put(
				wrappedGroupId, JournalStructureUtil.findByGroupId(groupId));
			JournalStructureUtil.removeByGroupId(groupId);
			journalTemplateMap.put(
				wrappedGroupId, JournalTemplateUtil.findByGroupId(groupId));
			JournalTemplateUtil.removeByGroupId(groupId);
			mBStatsUserMap.put(
				wrappedGroupId, MBStatsUserUtil.findByGroupId(groupId));
			MBStatsUserUtil.removeByGroupId(groupId);
			orgPermissionMap.put(
				wrappedGroupId, OrgGroupPermissionUtil.findByGroupId(groupId));
			OrgGroupPermissionUtil.removeByGroupId(groupId);
			orgRoleMap.put(
				wrappedGroupId, OrgGroupRoleUtil.findByGroupId(groupId));
			OrgGroupRoleUtil.removeByGroupId(groupId);

			GroupUtil.remove(groupId);
		}

		// Insert new groups, mappings, and entries with composite keys

		Map groupIdMap = new HashMap(groups.size() + 1);
		groupIdMap.put(new Long(-1), new Long(-1));

		itr1 = groups.iterator();

		while (itr1.hasNext()) {
			Group group = (Group)itr1.next();

			Long oldGroupId = new Long(group.getGroupId());
			long newGroupId = CounterLocalServiceUtil.increment(
				Counter.class.getName());

			groupIdMap.put(oldGroupId, new Long(newGroupId));

			Group newGroup = GroupUtil.create(newGroupId);

			newGroup.setCompanyId(group.getCompanyId());
			newGroup.setClassName(group.getClassName());
			newGroup.setClassPK(group.getClassPK());
			newGroup.setParentGroupId(group.getParentGroupId());
			newGroup.setName(group.getName());
			newGroup.setDescription(group.getDescription());
			newGroup.setType(group.getType());
			newGroup.setFriendlyURL(group.getFriendlyURL());

			GroupUtil.update(newGroup);

			GroupUtil.addOrganizations(
				newGroupId, (List)orgMap.get(oldGroupId));
			GroupUtil.addPermissions(
				newGroupId, (List)permissionMap.get(oldGroupId));
			GroupUtil.addRoles(newGroupId, (List)roleMap.get(oldGroupId));
			GroupUtil.addUsers(newGroupId, (List)userMap.get(oldGroupId));
			GroupUtil.addUserGroups(
				newGroupId, (List)userGroupMap.get(oldGroupId));

			// Handle composite keys

			Iterator itr2 =
				((List)journalArticleMap.get(oldGroupId)).iterator();

			while (itr2.hasNext()) {
				JournalArticle oldJa = (JournalArticle)itr2.next();

				JournalArticle ja =
					JournalArticleUtil.create(new JournalArticlePK());

				ja.setCompanyId(oldJa.getCompanyId());
				ja.setGroupId(newGroupId);
				ja.setArticleId(oldJa.getArticleId());
				ja.setVersion(oldJa.getVersion());
				ja.setUserId(oldJa.getUserId());
				ja.setUserName(oldJa.getUserName());
				ja.setCreateDate(oldJa.getCreateDate());
				ja.setModifiedDate(oldJa.getModifiedDate());
				ja.setTitle(oldJa.getTitle());
				ja.setDescription(oldJa.getDescription());
				ja.setContent(oldJa.getContent());
				ja.setType(oldJa.getType());
				ja.setStructureId(oldJa.getStructureId());
				ja.setTemplateId(oldJa.getTemplateId());
				ja.setDisplayDate(oldJa.getDisplayDate());
				ja.setApproved(oldJa.getApproved());
				ja.setApprovedByUserId(oldJa.getApprovedByUserId());
				ja.setApprovedByUserName(oldJa.getApprovedByUserName());
				ja.setApprovedDate(oldJa.getApprovedDate());
				ja.setExpired(oldJa.getExpired());
				ja.setExpirationDate(oldJa.getExpirationDate());
				ja.setReviewDate(oldJa.getReviewDate());

				JournalArticleUtil.update(ja);
			}

			itr2 = ((List)journalContentSearchMap.get(oldGroupId)).iterator();

			while (itr2.hasNext()) {
				JournalContentSearch oldJcsa =
					(JournalContentSearch)itr2.next();

				JournalContentSearch jcsa =
					JournalContentSearchUtil.create(
						new JournalContentSearchPK());

				jcsa.setPortletId(oldJcsa.getPortletId());
				jcsa.setLayoutId(oldJcsa.getLayoutId());
				jcsa.setOwnerId(oldJcsa.getOwnerId());
				jcsa.setArticleId(oldJcsa.getArticleId());
				jcsa.setCompanyId(oldJcsa.getCompanyId());
				jcsa.setGroupId(newGroupId);

				JournalContentSearchUtil.update(jcsa);
			}

			itr2 = ((List)journalStructureMap.get(oldGroupId)).iterator();

			while (itr2.hasNext()) {
				JournalStructure oldJs = (JournalStructure)itr2.next();

				JournalStructure js =
					JournalStructureUtil.create(new JournalStructurePK());

				js.setCompanyId(oldJs.getCompanyId());
				js.setGroupId(newGroupId);
				js.setStructureId(oldJs.getStructureId());
				js.setUserId(oldJs.getUserId());
				js.setUserName(oldJs.getUserName());
				js.setCreateDate(oldJs.getCreateDate());
				js.setModifiedDate(oldJs.getModifiedDate());
				js.setName(oldJs.getName());
				js.setDescription(oldJs.getDescription());
				js.setXsd(oldJs.getXsd());

				JournalStructureUtil.update(js);
			}

			itr2 = ((List)journalTemplateMap.get(oldGroupId)).iterator();

			while (itr2.hasNext()) {
				JournalTemplate oldJt = (JournalTemplate)itr2.next();

				JournalTemplate jt =
					JournalTemplateUtil.create(new JournalTemplatePK());

				jt.setCompanyId(oldJt.getCompanyId());
				jt.setGroupId(newGroupId);
				jt.setTemplateId(oldJt.getTemplateId());
				jt.setUserId(oldJt.getUserId());
				jt.setUserName(oldJt.getUserName());
				jt.setCreateDate(oldJt.getCreateDate());
				jt.setModifiedDate(oldJt.getModifiedDate());
				jt.setStructureId(oldJt.getStructureId());
				jt.setName(oldJt.getName());
				jt.setDescription(oldJt.getDescription());
				jt.setXsl(oldJt.getXsl());
				jt.setLangType(oldJt.getLangType());
				jt.setSmallImage(oldJt.getSmallImage());
				jt.setSmallImageURL(oldJt.getSmallImageURL());

				JournalTemplateUtil.update(jt);
			}

			itr2 = ((List)mBStatsUserMap.get(oldGroupId)).iterator();

			while (itr2.hasNext()) {
				MBStatsUser oldMbsu = (MBStatsUser)itr2.next();

				MBStatsUser mbsu = MBStatsUserUtil.create(new MBStatsUserPK());

				mbsu.setGroupId(newGroupId);
				mbsu.setUserId(oldMbsu.getUserId());
				mbsu.setMessageCount(oldMbsu.getMessageCount());
				mbsu.setLastPostDate(oldMbsu.getLastPostDate());

				MBStatsUserUtil.update(mbsu);
			}

			itr2 = ((List)orgPermissionMap.get(oldGroupId)).iterator();

			while (itr2.hasNext()) {
				OrgGroupPermission oldOgp = (OrgGroupPermission)itr2.next();

				OrgGroupPermission ogp =
					OrgGroupPermissionUtil.create(new OrgGroupPermissionPK());

				ogp.setOrganizationId(oldOgp.getOrganizationId());
				ogp.setGroupId(newGroupId);
				ogp.setPermissionId(oldOgp.getPermissionId());

				OrgGroupPermissionUtil.update(ogp);
			}

			itr2 = ((List)orgRoleMap.get(oldGroupId)).iterator();

			while (itr2.hasNext()) {
				OrgGroupRole oldOgr = (OrgGroupRole)itr2.next();

				OrgGroupRole ogr =
					OrgGroupRoleUtil.create(new OrgGroupRolePK());

				ogr.setOrganizationId(oldOgr.getOrganizationId());
				ogr.setGroupId(newGroupId);
				ogr.setRoleId(oldOgr.getRoleId());

				OrgGroupRoleUtil.update(ogr);
			}
		}

		// Fix parentGroupIds in Group_

		itr1 = GroupUtil.findAll().iterator();

		while (itr1.hasNext()) {
			Group group = (Group)itr1.next();

			Long pgId = new Long(group.getParentGroupId());

			group.setParentGroupId(((Long)groupIdMap.get(pgId)).longValue());

			GroupUtil.update(group);
		}

		// Fix all other groupIds

		renewGroupIds(groupIdMap, BlogsEntry.class, BlogsEntryUtil.class);
		renewGroupIds(
			groupIdMap, BookmarksFolder.class, BookmarksFolderUtil.class);
		renewGroupIds(groupIdMap, CalEvent.class, CalEventUtil.class);
		renewGroupIds(groupIdMap, DLFolder.class, DLFolderUtil.class);
		renewGroupIds(groupIdMap, IGFolder.class, IGFolderUtil.class);
		renewGroupIds(groupIdMap, LayoutSet.class, LayoutSetUtil.class);
		renewGroupIds(groupIdMap, MBCategory.class, MBCategoryUtil.class);
		renewGroupIds(groupIdMap, PollsQuestion.class, PollsQuestionUtil.class);
		renewGroupIds(groupIdMap, ShoppingCart.class, ShoppingCartUtil.class);
		renewGroupIds(
			groupIdMap, ShoppingCategory.class, ShoppingCategoryUtil.class);
		renewGroupIds(
			groupIdMap, ShoppingCoupon.class, ShoppingCouponUtil.class);
		renewGroupIds(groupIdMap, ShoppingOrder.class, ShoppingOrderUtil.class);
		renewGroupIds(groupIdMap, WikiNode.class, WikiNodeUtil.class);

		return groupIdMap;
	}

	public List search(
			String companyId, String name, String description, Map params,
			int begin, int end)
		throws SystemException {

		return GroupFinder.findByC_N_D(
			companyId, name, description, params, begin, end);
	}

	public int searchCount(
			String companyId, String name, String description, Map params)
		throws SystemException {

		return GroupFinder.countByC_N_D(companyId, name, description, params);
	}

	public void setRoleGroups(String roleId, long[] groupIds)
		throws PortalException, SystemException {

		RoleUtil.setGroups(roleId, groupIds);
	}

	public void unsetRoleGroups(String roleId, long[] groupIds)
		throws PortalException, SystemException {

		RoleUtil.removeGroups(roleId, groupIds);
	}

	public Group updateGroup(
			long groupId, String name, String description, String type,
			String friendlyURL)
		throws PortalException, SystemException {

		Group group = GroupUtil.findByPrimaryKey(groupId);

		String className = group.getClassName();
		String classPK = group.getClassPK();

		if (Validator.isNull(className) || Validator.isNull(classPK)) {
			validateName(group.getGroupId(), group.getCompanyId(), name);
		}

		if (PortalUtil.isSystemGroup(group.getName()) &&
			!group.getName().equals(name)) {

			throw new RequiredGroupException();
		}

		validateFriendlyURL(
			group.getGroupId(), group.getCompanyId(), friendlyURL);

		group.setName(name);
		group.setDescription(description);
		group.setType(type);
		group.setFriendlyURL(friendlyURL);

		GroupUtil.update(group);

		return group;
	}

	protected void addDefaultLayouts(Group group)
		throws PortalException, SystemException {

		String userId = UserImpl.getDefaultUserId(group.getCompanyId());
		String name = PropsUtil.get(PropsUtil.DEFAULT_GUEST_LAYOUT_NAME);

		Layout layout = LayoutLocalServiceUtil.addLayout(
			group.getGroupId(), userId, false,
			LayoutImpl.DEFAULT_PARENT_LAYOUT_ID, name, StringPool.BLANK,
			LayoutImpl.TYPE_PORTLET, false, StringPool.BLANK);

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		String layoutTemplateId = PropsUtil.get(
			PropsUtil.DEFAULT_GUEST_LAYOUT_TEMPLATE_ID);

		layoutTypePortlet.setLayoutTemplateId(layoutTemplateId);

		for (int i = 0; i < 10; i++) {
			String columnId = "column-" + i;
			String portletIds = PropsUtil.get(
				PropsUtil.DEFAULT_GUEST_LAYOUT_COLUMN + i);

			layoutTypePortlet.addPortletIds(
				null, StringUtil.split(portletIds), columnId, false);
		}

		LayoutLocalServiceUtil.updateLayout(
			layout.getLayoutId(), layout.getOwnerId(),
			layout.getTypeSettings());
	}

	protected void renewGroupIds(
			Map groupIdMap, Class modelClass, Class persistenceClass)
		throws SystemException {

		try {
			Method findAll =
				persistenceClass.getMethod("findAll", new Class[] {});
			Method update =
				persistenceClass.getMethod("update", new Class[] { modelClass });

			Method setGroupId =
				modelClass.getMethod("setGroupId", new Class[] { Long.TYPE });
			Method getGroupId =
				modelClass.getMethod("getGroupId", new Class[] {});

			Iterator itr = ((List)findAll.invoke(null, null)).iterator();

			while (itr.hasNext()) {
				Object model = itr.next();

				Long groupId = (Long)getGroupId.invoke(model, null);

				setGroupId.invoke(
					model, new Object[] { groupIdMap.get(groupId) });

				update.invoke(null, new Object[] { model });
			}
		}
		catch (Exception ex) {
			throw new SystemException(ex);
		}
	}

	protected void validateFriendlyURL(
			long groupId, String companyId, String friendlyURL)
		throws PortalException, SystemException {

		if (Validator.isNotNull(friendlyURL)) {
			int exceptionType = LayoutImpl.validateFriendlyURL(friendlyURL);

			if (exceptionType != -1) {
				throw new GroupFriendlyURLException(exceptionType);
			}

			try {
				Group group = GroupUtil.findByC_F(companyId, friendlyURL);

				if ((groupId <= 0) || group.getGroupId() != groupId) {
					throw new GroupFriendlyURLException(
						GroupFriendlyURLException.DUPLICATE);
				}
			}
			catch (NoSuchGroupException nsge) {
			}
		}
	}

	protected void validateName(long groupId, String companyId, String name)
		throws PortalException, SystemException {

		if ((Validator.isNull(name)) || (Validator.isNumber(name)) ||
			(name.indexOf(StringPool.COMMA) != -1) ||
			(name.indexOf(StringPool.STAR) != -1)) {

			throw new GroupNameException();
		}

		try {
			Group group = GroupFinder.findByC_N(companyId, name);

			if ((groupId <= 0) || group.getGroupId() != groupId) {
				throw new DuplicateGroupException();
			}
		}
		catch (NoSuchGroupException nsge) {
		}
	}

}