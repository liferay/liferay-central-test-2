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

package com.liferay.message.boards.layout.set.prototype.instance.lifecycle;

import com.liferay.layout.set.prototype.constants.LayoutSetPrototypePortletKeys;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.web.constants.MBPortletKeys;
import com.liferay.polls.constants.PollsPortletKeys;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.DefaultLayoutPrototypesUtil;
import com.liferay.portal.kernel.util.DefaultLayoutSetPrototypesUtil;
import com.liferay.social.user.statistics.web.constants.SocialUserStatisticsPortletKeys;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class AddLayoutSetPrototypePortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		long defaultUserId = _userLocalService.getDefaultUserId(
			company.getCompanyId());

		List<LayoutSetPrototype> layoutSetPrototypes =
			_layoutSetPrototypeLocalService.search(
				company.getCompanyId(), null, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		addPublicSite(
			company.getCompanyId(), defaultUserId, layoutSetPrototypes);
	}

	protected void addPublicSite(
			long companyId, long defaultUserId,
			List<LayoutSetPrototype> layoutSetPrototypes)
		throws Exception {

		LayoutSet layoutSet =
			DefaultLayoutSetPrototypesUtil.addLayoutSetPrototype(
				companyId, defaultUserId,
				"layout-set-prototype-community-site-title",
				"layout-set-prototype-community-site-description",
				layoutSetPrototypes, getClassLoader());

		if (layoutSet == null) {
			return;
		}

		// Home layout

		Layout layout = DefaultLayoutPrototypesUtil.addLayout(
			layoutSet, "home", "/home", "2_columns_iii");
		String portletId = PortletProviderUtil.getPortletId(
			MBMessage.class.getName(), PortletProvider.Action.EDIT);

		DefaultLayoutPrototypesUtil.addPortletId(layout, portletId, "column-1");

		DefaultLayoutPrototypesUtil.addPortletId(
			layout, PollsPortletKeys.POLLS_DISPLAY, "column-2");

		DefaultLayoutPrototypesUtil.addPortletId(
			layout, SocialUserStatisticsPortletKeys.SOCIAL_USER_STATISTICS,
			"column-2");

		// Wiki layout

		DefaultLayoutPrototypesUtil.addLayout(
			layoutSet, "wiki", "/wiki", "2_columns_iii");
	}

	@Reference(unbind = "-")
	protected void setLayoutSetPrototypeLocalService(
		LayoutSetPrototypeLocalService layoutSetPrototypeLocalService) {

		_layoutSetPrototypeLocalService = layoutSetPrototypeLocalService;
	}

	@Reference(
		target = "(javax.portlet.name=" + LayoutSetPrototypePortletKeys.LAYOUT_SET_PROTOTYPE + ")",
		unbind = "-"
	)
	protected void setLayoutSetPrototypePortlet(Portlet portlet) {
	}

	@Reference(
		target = "(javax.portlet.name=" + MBPortletKeys.MESSAGE_BOARDS + ")",
		unbind = "-"
	)
	protected void setMessageBoardsPortlet(Portlet portlet) {
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(target = "(javax.portlet.name=59)", unbind = "-")
	protected void setPollsPortlet(Portlet portlet) {
	}

	@Reference(
		target = "(javax.portlet.name=" + SocialUserStatisticsPortletKeys.SOCIAL_USER_STATISTICS + ")",
		unbind = "-"
	)
	protected void setSocialUserStatisticsPortletKeys(Portlet portlet) {
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;
	private UserLocalService _userLocalService;

}