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

package com.liferay.blogs.layout.prototype.action;

import com.liferay.blogs.recent.bloggers.web.constants.RecentBloggersPortletKeys;
import com.liferay.blogs.web.constants.BlogsPortletKeys;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.LayoutPrototypeLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.util.DefaultLayoutPrototypesUtil;

import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = AddLayoutPrototypeAction.class)
public class AddLayoutPrototypeAction {

	@Activate
	protected void activate() throws Exception {
		List<Company> companies = _companyLocalService.getCompanies();

		for (Company company : companies) {
			long defaultUserId = _userLocalService.getDefaultUserId(
				company.getCompanyId());

			List<LayoutPrototype> layoutPrototypes =
				_layoutPrototypeLocalService.search(
					company.getCompanyId(), null, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null);

			addBlogPage(
				company.getCompanyId(), defaultUserId, layoutPrototypes);
		}
	}

	protected void addBlogPage(
			long companyId, long defaultUserId,
			List<LayoutPrototype> layoutPrototypes)
		throws Exception {

		ResourceBundle resourceBundle = ResourceBundle.getBundle(
			"content.Language", LocaleUtil.getDefault());

		Layout layout = DefaultLayoutPrototypesUtil.addLayoutPrototype(
			companyId, defaultUserId,
			LanguageUtil.get(resourceBundle, "layout-prototype-blog-title"),
			LanguageUtil.get(
				resourceBundle, "layout-prototype-blog-description"),
			"2_columns_iii", layoutPrototypes);

		if (layout == null) {
			return;
		}

		DefaultLayoutPrototypesUtil.addPortletId(
			layout, BlogsPortletKeys.BLOGS, "column-1");

		DefaultLayoutPrototypesUtil.addPortletId(
			layout, RecentBloggersPortletKeys.RECENT_BLOGGERS, "column-2");
	}

	@Reference(unbind = "-")
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutPrototypeLocalService(
		LayoutPrototypeLocalService layoutPrototypeLocalService) {

		_layoutPrototypeLocalService = layoutPrototypeLocalService;
	}

	@Reference(
		target = "(javax.portlet.name=com.liferay.blogs.web.portlet.BlogsPortlet)"
	)
	protected void setPortlet(Portlet portlet) {
	}

	@Reference(target = "(original.bean=*)", unbind = "-")
	protected void setServletContext(ServletContext servletContext) {
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private CompanyLocalService _companyLocalService;
	private LayoutPrototypeLocalService _layoutPrototypeLocalService;
	private UserLocalService _userLocalService;

}