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

package com.liferay.message.boards.layout.set.prototype.action;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.util.DefaultLayoutPrototypesUtil;
import com.liferay.portal.util.DefaultLayoutSetPrototypesUtil;
import com.liferay.portal.util.PortletKeys;

import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = AddLayoutSetPrototypeAction.class)
public class AddLayoutSetPrototypeAction {

	@Activate
	protected void activate() throws Exception {
		List<Company> companies = _companyLocalService.getCompanies();

		for (Company company : companies) {
			long defaultUserId = _userLocalService.getDefaultUserId(
				company.getCompanyId());

			List<LayoutSetPrototype> layoutSetPrototypes =
				_layoutSetPrototypeLocalService.search(
					company.getCompanyId(), null, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);

			addPublicSite(
				company.getCompanyId(), defaultUserId, layoutSetPrototypes);
		}
	}

	protected void addPublicSite(
			long companyId, long defaultUserId,
			List<LayoutSetPrototype> layoutSetPrototypes)
		throws Exception {

		ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"content.Language", LocaleUtil.getDefault());

		LayoutSet layoutSet =
			DefaultLayoutSetPrototypesUtil.addLayoutSetPrototype(
				companyId, defaultUserId,
				LanguageUtil.get(
					resourceBundle,
					"layout-set-prototype-community-site-title"),
				LanguageUtil.get(
					resourceBundle,
					"layout-set-prototype-community-site-description"),
				layoutSetPrototypes);

		if (layoutSet == null) {
			return;
		}

		// Home layout

		Layout layout = DefaultLayoutPrototypesUtil.addLayout(
			layoutSet, "home", "/home", "2_columns_iii");

		DefaultLayoutPrototypesUtil.addPortletId(
			layout, PortletKeys.MESSAGE_BOARDS, "column-1");

		DefaultLayoutPrototypesUtil.addPortletId(
			layout, PortletKeys.USER_STATISTICS, "column-2");

		// Wiki layout

		DefaultLayoutPrototypesUtil.addLayout(
			layoutSet, "wiki", "/wiki", "2_columns_iii");
	}

	protected void doRun(long companyId) throws Exception {
		long defaultUserId = _userLocalService.getDefaultUserId(companyId);

		List<LayoutSetPrototype> layoutSetPrototypes =
			_layoutSetPrototypeLocalService.search(
				companyId, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		addPublicSite(companyId, defaultUserId, layoutSetPrototypes);
	}

	@Reference(unbind = "-")
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutSetPrototypeLocalService(
		LayoutSetPrototypeLocalService layoutSetPrototypeLocalService) {

		_layoutSetPrototypeLocalService = layoutSetPrototypeLocalService;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(
		target = "(javax.portlet.name=com_liferay_layout_set_prototype_web_portlet_LayoutSetPrototypePortlet)",
		unbind = "-"
	)
	protected void setPortlet(Portlet portlet) {
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private CompanyLocalService _companyLocalService;
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;
	private UserLocalService _userLocalService;

}