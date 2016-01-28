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

package com.liferay.portal.security.sso.google.internal.lifecycle;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.service.ExpandoColumnLocalService;
import com.liferay.portlet.expando.service.ExpandoTableLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true)
public class AddGoogleExpandoColumnsPortalInstanceLifecycleListener extends SimpleAction {

	@Override
	public void run(String[] ids) throws ActionException {
		try {
			doRun(GetterUtil.getLong(ids[0]));
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	@Activate
	protected void activate() throws ActionException {
		Long companyId = CompanyThreadLocal.getCompanyId();

		try {
			List<Company> companies = _companyLocalService.getCompanies();

			for (Company company : companies) {
				CompanyThreadLocal.setCompanyId(company.getCompanyId());

				run(new String[] {String.valueOf(company.getCompanyId())});
			}
		}
		finally {
			CompanyThreadLocal.setCompanyId(companyId);
		}
	}

	protected void addExpandoColumn(
			ExpandoTable expandoTable, String name,
			UnicodeProperties properties)
		throws Exception {

		ExpandoColumn expandoColumn = _expandoColumnLocalService.getColumn(
			expandoTable.getTableId(), name);

		if (expandoColumn != null) {
			return;
		}

		expandoColumn = _expandoColumnLocalService.addColumn(
			expandoTable.getTableId(), name, ExpandoColumnConstants.STRING);

		_expandoColumnLocalService.updateTypeSettings(
			expandoColumn.getColumnId(), properties.toString());
	}

	protected void doRun(long companyId) throws Exception {
		ExpandoTable expandoTable = null;

		try {
			expandoTable = _expandoTableLocalService.addTable(
				companyId, User.class.getName(),
				ExpandoTableConstants.DEFAULT_TABLE_NAME);
		}
		catch (Exception e) {
			expandoTable = _expandoTableLocalService.getTable(
				companyId, User.class.getName(),
				ExpandoTableConstants.DEFAULT_TABLE_NAME);
		}

		UnicodeProperties properties = new UnicodeProperties();

		properties.setProperty("hidden", "true");
		properties.setProperty("visible-with-update-permission", "false");

		addExpandoColumn(expandoTable, "googleAccessToken", properties);
		addExpandoColumn(expandoTable, "googleRefreshToken", properties);
		addExpandoColumn(expandoTable, "googleUserId", properties);
	}

	@Reference(unbind = "-")
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference(unbind = "-")
	protected void setExpandoColumnLocalService(
		ExpandoColumnLocalService expandoColumnLocalService) {

		_expandoColumnLocalService = expandoColumnLocalService;
	}

	@Reference(unbind = "-")
	protected void setExpandoTableLocalService(
		ExpandoTableLocalService expandoTableLocalService) {

		_expandoTableLocalService = expandoTableLocalService;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private CompanyLocalService _companyLocalService;
	private ExpandoColumnLocalService _expandoColumnLocalService;
	private ExpandoTableLocalService _expandoTableLocalService;

}