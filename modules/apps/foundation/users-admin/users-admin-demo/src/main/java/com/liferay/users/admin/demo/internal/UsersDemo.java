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

package com.liferay.users.admin.demo.internal;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.roles.admin.demo.data.creator.RoleDemoDataCreator;
import com.liferay.site.demo.data.creator.SiteDemoDataCreator;
import com.liferay.users.admin.demo.data.creator.CompanyAdminUserDemoDataCreator;
import com.liferay.users.admin.demo.data.creator.SiteAdminUserDemoDataCreator;
import com.liferay.users.admin.demo.data.creator.SiteMemberUserDemoDataCreator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class UsersDemo extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		_companyAdminUserDemoDataCreator.create(
			company.getCompanyId(), "bruno@liferay.com");

		Group acmeCorpGroup = _siteDemoDataCreator.create(
			company.getCompanyId(), "Acme’s Corporation");

		_siteAdminUserDemoDataCreator.create(
			acmeCorpGroup.getGroupId(), "helen@liferay.com");

		// Web Content Author role

		String webContentAuthorPermissionsXML = StringUtil.read(
			UsersDemo.class, "dependencies/permissions-web-content-author.xml");

		Role webContentAuthorRole = _siteRoleDemoDataCreator.create(
			company.getCompanyId(), "Web Content Author",
			webContentAuthorPermissionsXML);

		_siteMemberUserDemoDataCreator.create(
			acmeCorpGroup.getGroupId(), "joe@liferay.com",
			new long[] {webContentAuthorRole.getRoleId()});

		// Forum Moderator role

		Group petLoversGroup = _siteDemoDataCreator.create(
			company.getCompanyId(), "Pet Lovers");

		String forumModeratorPermissionsXML = StringUtil.read(
			UsersDemo.class, "dependencies/permissions-forum-moderator.xml");

		Role forumModeratorRole = _siteRoleDemoDataCreator.create(
			company.getCompanyId(), "Forum Moderator",
			forumModeratorPermissionsXML);

		_siteMemberUserDemoDataCreator.create(
			petLoversGroup.getGroupId(), "maria@liferay.com",
			new long[] {forumModeratorRole.getRoleId()});
	}

	@Deactivate
	protected void deactivate() throws PortalException {
		_companyAdminUserDemoDataCreator.delete();
		_siteAdminUserDemoDataCreator.delete();
		_siteMemberUserDemoDataCreator.delete();

		_siteDemoDataCreator.delete();
		_siteRoleDemoDataCreator.delete();
	}

	@Reference
	private CompanyAdminUserDemoDataCreator _companyAdminUserDemoDataCreator;

	@Reference
	private SiteAdminUserDemoDataCreator _siteAdminUserDemoDataCreator;

	@Reference
	private SiteDemoDataCreator _siteDemoDataCreator;

	@Reference
	private SiteMemberUserDemoDataCreator _siteMemberUserDemoDataCreator;

	@Reference(target = "(role.type=site)")
	private RoleDemoDataCreator _siteRoleDemoDataCreator;

}