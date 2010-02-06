/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.flags.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.mail.service.MailService;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.CompanyService;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.GroupService;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.LayoutService;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.RoleLocalService;
import com.liferay.portal.service.RoleService;
import com.liferay.portal.service.UserGroupLocalService;
import com.liferay.portal.service.UserGroupRoleLocalService;
import com.liferay.portal.service.UserGroupRoleService;
import com.liferay.portal.service.UserGroupService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.base.PrincipalBean;
import com.liferay.portal.service.persistence.CompanyPersistence;
import com.liferay.portal.service.persistence.GroupFinder;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.LayoutFinder;
import com.liferay.portal.service.persistence.LayoutPersistence;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.RoleFinder;
import com.liferay.portal.service.persistence.RolePersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserGroupFinder;
import com.liferay.portal.service.persistence.UserGroupPersistence;
import com.liferay.portal.service.persistence.UserGroupRoleFinder;
import com.liferay.portal.service.persistence.UserGroupRolePersistence;
import com.liferay.portal.service.persistence.UserPersistence;

import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.asset.service.AssetEntryService;
import com.liferay.portlet.asset.service.persistence.AssetEntryFinder;
import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.flags.service.FlagsEntryService;

/**
 * <a href="FlagsEntryServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class FlagsEntryServiceBaseImpl extends PrincipalBean
	implements FlagsEntryService {
	public FlagsEntryService getFlagsEntryService() {
		return flagsEntryService;
	}

	public void setFlagsEntryService(FlagsEntryService flagsEntryService) {
		this.flagsEntryService = flagsEntryService;
	}

	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	public CounterService getCounterService() {
		return counterService;
	}

	public void setCounterService(CounterService counterService) {
		this.counterService = counterService;
	}

	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public CompanyLocalService getCompanyLocalService() {
		return companyLocalService;
	}

	public void setCompanyLocalService(CompanyLocalService companyLocalService) {
		this.companyLocalService = companyLocalService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public CompanyPersistence getCompanyPersistence() {
		return companyPersistence;
	}

	public void setCompanyPersistence(CompanyPersistence companyPersistence) {
		this.companyPersistence = companyPersistence;
	}

	public GroupLocalService getGroupLocalService() {
		return groupLocalService;
	}

	public void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public GroupPersistence getGroupPersistence() {
		return groupPersistence;
	}

	public void setGroupPersistence(GroupPersistence groupPersistence) {
		this.groupPersistence = groupPersistence;
	}

	public GroupFinder getGroupFinder() {
		return groupFinder;
	}

	public void setGroupFinder(GroupFinder groupFinder) {
		this.groupFinder = groupFinder;
	}

	public LayoutLocalService getLayoutLocalService() {
		return layoutLocalService;
	}

	public void setLayoutLocalService(LayoutLocalService layoutLocalService) {
		this.layoutLocalService = layoutLocalService;
	}

	public LayoutService getLayoutService() {
		return layoutService;
	}

	public void setLayoutService(LayoutService layoutService) {
		this.layoutService = layoutService;
	}

	public LayoutPersistence getLayoutPersistence() {
		return layoutPersistence;
	}

	public void setLayoutPersistence(LayoutPersistence layoutPersistence) {
		this.layoutPersistence = layoutPersistence;
	}

	public LayoutFinder getLayoutFinder() {
		return layoutFinder;
	}

	public void setLayoutFinder(LayoutFinder layoutFinder) {
		this.layoutFinder = layoutFinder;
	}

	public ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	public void setResourceLocalService(
		ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	public ResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public ResourcePersistence getResourcePersistence() {
		return resourcePersistence;
	}

	public void setResourcePersistence(ResourcePersistence resourcePersistence) {
		this.resourcePersistence = resourcePersistence;
	}

	public ResourceFinder getResourceFinder() {
		return resourceFinder;
	}

	public void setResourceFinder(ResourceFinder resourceFinder) {
		this.resourceFinder = resourceFinder;
	}

	public RoleLocalService getRoleLocalService() {
		return roleLocalService;
	}

	public void setRoleLocalService(RoleLocalService roleLocalService) {
		this.roleLocalService = roleLocalService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public RolePersistence getRolePersistence() {
		return rolePersistence;
	}

	public void setRolePersistence(RolePersistence rolePersistence) {
		this.rolePersistence = rolePersistence;
	}

	public RoleFinder getRoleFinder() {
		return roleFinder;
	}

	public void setRoleFinder(RoleFinder roleFinder) {
		this.roleFinder = roleFinder;
	}

	public UserLocalService getUserLocalService() {
		return userLocalService;
	}

	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public UserFinder getUserFinder() {
		return userFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public UserGroupLocalService getUserGroupLocalService() {
		return userGroupLocalService;
	}

	public void setUserGroupLocalService(
		UserGroupLocalService userGroupLocalService) {
		this.userGroupLocalService = userGroupLocalService;
	}

	public UserGroupService getUserGroupService() {
		return userGroupService;
	}

	public void setUserGroupService(UserGroupService userGroupService) {
		this.userGroupService = userGroupService;
	}

	public UserGroupPersistence getUserGroupPersistence() {
		return userGroupPersistence;
	}

	public void setUserGroupPersistence(
		UserGroupPersistence userGroupPersistence) {
		this.userGroupPersistence = userGroupPersistence;
	}

	public UserGroupFinder getUserGroupFinder() {
		return userGroupFinder;
	}

	public void setUserGroupFinder(UserGroupFinder userGroupFinder) {
		this.userGroupFinder = userGroupFinder;
	}

	public UserGroupRoleLocalService getUserGroupRoleLocalService() {
		return userGroupRoleLocalService;
	}

	public void setUserGroupRoleLocalService(
		UserGroupRoleLocalService userGroupRoleLocalService) {
		this.userGroupRoleLocalService = userGroupRoleLocalService;
	}

	public UserGroupRoleService getUserGroupRoleService() {
		return userGroupRoleService;
	}

	public void setUserGroupRoleService(
		UserGroupRoleService userGroupRoleService) {
		this.userGroupRoleService = userGroupRoleService;
	}

	public UserGroupRolePersistence getUserGroupRolePersistence() {
		return userGroupRolePersistence;
	}

	public void setUserGroupRolePersistence(
		UserGroupRolePersistence userGroupRolePersistence) {
		this.userGroupRolePersistence = userGroupRolePersistence;
	}

	public UserGroupRoleFinder getUserGroupRoleFinder() {
		return userGroupRoleFinder;
	}

	public void setUserGroupRoleFinder(UserGroupRoleFinder userGroupRoleFinder) {
		this.userGroupRoleFinder = userGroupRoleFinder;
	}

	public AssetEntryLocalService getAssetEntryLocalService() {
		return assetEntryLocalService;
	}

	public void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {
		this.assetEntryLocalService = assetEntryLocalService;
	}

	public AssetEntryService getAssetEntryService() {
		return assetEntryService;
	}

	public void setAssetEntryService(AssetEntryService assetEntryService) {
		this.assetEntryService = assetEntryService;
	}

	public AssetEntryPersistence getAssetEntryPersistence() {
		return assetEntryPersistence;
	}

	public void setAssetEntryPersistence(
		AssetEntryPersistence assetEntryPersistence) {
		this.assetEntryPersistence = assetEntryPersistence;
	}

	public AssetEntryFinder getAssetEntryFinder() {
		return assetEntryFinder;
	}

	public void setAssetEntryFinder(AssetEntryFinder assetEntryFinder) {
		this.assetEntryFinder = assetEntryFinder;
	}

	protected void runSQL(String sql) throws SystemException {
		try {
			DB db = DBFactoryUtil.getDB();

			db.runSQL(sql);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(name = "com.liferay.portlet.flags.service.FlagsEntryService")
	protected FlagsEntryService flagsEntryService;
	@BeanReference(name = "com.liferay.counter.service.CounterLocalService")
	protected CounterLocalService counterLocalService;
	@BeanReference(name = "com.liferay.counter.service.CounterService")
	protected CounterService counterService;
	@BeanReference(name = "com.liferay.mail.service.MailService")
	protected MailService mailService;
	@BeanReference(name = "com.liferay.portal.service.CompanyLocalService")
	protected CompanyLocalService companyLocalService;
	@BeanReference(name = "com.liferay.portal.service.CompanyService")
	protected CompanyService companyService;
	@BeanReference(name = "com.liferay.portal.service.persistence.CompanyPersistence")
	protected CompanyPersistence companyPersistence;
	@BeanReference(name = "com.liferay.portal.service.GroupLocalService")
	protected GroupLocalService groupLocalService;
	@BeanReference(name = "com.liferay.portal.service.GroupService")
	protected GroupService groupService;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupPersistence")
	protected GroupPersistence groupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.GroupFinder")
	protected GroupFinder groupFinder;
	@BeanReference(name = "com.liferay.portal.service.LayoutLocalService")
	protected LayoutLocalService layoutLocalService;
	@BeanReference(name = "com.liferay.portal.service.LayoutService")
	protected LayoutService layoutService;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence")
	protected LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutFinder")
	protected LayoutFinder layoutFinder;
	@BeanReference(name = "com.liferay.portal.service.ResourceLocalService")
	protected ResourceLocalService resourceLocalService;
	@BeanReference(name = "com.liferay.portal.service.ResourceService")
	protected ResourceService resourceService;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceFinder")
	protected ResourceFinder resourceFinder;
	@BeanReference(name = "com.liferay.portal.service.RoleLocalService")
	protected RoleLocalService roleLocalService;
	@BeanReference(name = "com.liferay.portal.service.RoleService")
	protected RoleService roleService;
	@BeanReference(name = "com.liferay.portal.service.persistence.RolePersistence")
	protected RolePersistence rolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.RoleFinder")
	protected RoleFinder roleFinder;
	@BeanReference(name = "com.liferay.portal.service.UserLocalService")
	protected UserLocalService userLocalService;
	@BeanReference(name = "com.liferay.portal.service.UserService")
	protected UserService userService;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserFinder")
	protected UserFinder userFinder;
	@BeanReference(name = "com.liferay.portal.service.UserGroupLocalService")
	protected UserGroupLocalService userGroupLocalService;
	@BeanReference(name = "com.liferay.portal.service.UserGroupService")
	protected UserGroupService userGroupService;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupPersistence")
	protected UserGroupPersistence userGroupPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupFinder")
	protected UserGroupFinder userGroupFinder;
	@BeanReference(name = "com.liferay.portal.service.UserGroupRoleLocalService")
	protected UserGroupRoleLocalService userGroupRoleLocalService;
	@BeanReference(name = "com.liferay.portal.service.UserGroupRoleService")
	protected UserGroupRoleService userGroupRoleService;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupRolePersistence")
	protected UserGroupRolePersistence userGroupRolePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserGroupRoleFinder")
	protected UserGroupRoleFinder userGroupRoleFinder;
	@BeanReference(name = "com.liferay.portlet.asset.service.AssetEntryLocalService")
	protected AssetEntryLocalService assetEntryLocalService;
	@BeanReference(name = "com.liferay.portlet.asset.service.AssetEntryService")
	protected AssetEntryService assetEntryService;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetEntryPersistence")
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.asset.service.persistence.AssetEntryFinder")
	protected AssetEntryFinder assetEntryFinder;
}