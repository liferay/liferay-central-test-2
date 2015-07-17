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

package com.liferay.document.library.layout.set.prototype;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;

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

			addPrivateSite(
				company.getCompanyId(), defaultUserId, layoutSetPrototypes);
		}
	}

	protected void addPrivateSite(
			long companyId, long defaultUserId,
			List<LayoutSetPrototype> layoutSetPrototypes)
		throws Exception {

		ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"content.Language", LocaleUtil.getDefault());

		LayoutSet layoutSet =
			DefaultLayoutSetPrototypesUtil.addLayoutSetPrototype(
				companyId, defaultUserId,
				LanguageUtil.get(
					resourceBundle, "layout-set-prototype-intranet-site-title"),
				LanguageUtil.get(
					resourceBundle,
					"layout-set-prototype-intranet-site-description"),
				layoutSetPrototypes);

		if (layoutSet == null) {
			return;
		}

		// Home layout

		DefaultLayoutPrototypesUtil.addLayout(
			layoutSet, "home", "/home", "2_columns_i");

		// Documents layout

		Layout layout = DefaultLayoutPrototypesUtil.addLayout(
			layoutSet, "documents-and-media", "/documents", "1_column");

		String portletId = DefaultLayoutPrototypesUtil.addPortletId(
			layout, PortletKeys.DOCUMENT_LIBRARY, "column-1");

		Map<String, String> preferences = new HashMap<>();

		preferences.put("portletSetupShowBorders", Boolean.FALSE.toString());

		DefaultLayoutPrototypesUtil.updatePortletSetup(
			layout, portletId, preferences);
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

	@Reference(target = "(original.bean=*)", unbind = "-")
	protected void setServletContext(ServletContext servletContext) {
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private CompanyLocalService _companyLocalService;
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;
	private UserLocalService _userLocalService;

}