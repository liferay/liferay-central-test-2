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

package com.liferay.portlet.layoutsadmin.lar;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutFriendlyURL;
import com.liferay.portal.service.LayoutFriendlyURLLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;

import java.util.List;
import java.util.Map;

/**
 * @author Sergio González
 */
@OSGiBeanProperties
public class LayoutFriendlyURLStagedModelDataHandler
	extends BaseStagedModelDataHandler<LayoutFriendlyURL> {

	public static final String[] CLASS_NAMES =
		{LayoutFriendlyURL.class.getName()};

	@Override
	public void deleteStagedModel(LayoutFriendlyURL layoutFriendlyURL) {
		LayoutFriendlyURLLocalServiceUtil.deleteLayoutFriendlyURL(
			layoutFriendlyURL);
	}

	@Override
	public void deleteStagedModel(
		String uuid, long groupId, String className, String extraData) {

		LayoutFriendlyURL layoutFriendlyURL = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		deleteStagedModel(layoutFriendlyURL);
	}

	@Override
	public LayoutFriendlyURL fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return LayoutFriendlyURLLocalServiceUtil.
			fetchLayoutFriendlyURLByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<LayoutFriendlyURL> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return LayoutFriendlyURLLocalServiceUtil.
			getLayoutFriendlyURLsByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<LayoutFriendlyURL>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			LayoutFriendlyURL layoutFriendlyURL)
		throws Exception {

		Element layoutFriendlyURLElement =
			portletDataContext.getExportDataElement(layoutFriendlyURL);

		portletDataContext.addClassedModel(
			layoutFriendlyURLElement,
			ExportImportPathUtil.getModelPath(layoutFriendlyURL),
			layoutFriendlyURL);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			LayoutFriendlyURL layoutFriendlyURL)
		throws Exception {

		long userId = portletDataContext.getUserId(
			layoutFriendlyURL.getUserUuid());

		Map<Long, Long> plids =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class);

		long plid = MapUtil.getLong(
			plids, layoutFriendlyURL.getPlid(), layoutFriendlyURL.getPlid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			layoutFriendlyURL);

		LayoutFriendlyURL importedLayoutFriendlyURL = null;

		if (portletDataContext.isDataStrategyMirror()) {
			LayoutFriendlyURL existingLayoutFriendlyURL =
				fetchExistingLayoutFriendlyURL(
					portletDataContext, layoutFriendlyURL, plid);

			layoutFriendlyURL = getUniqueLayoutFriendlyURL(
				portletDataContext, layoutFriendlyURL,
				existingLayoutFriendlyURL);

			if (existingLayoutFriendlyURL == null) {
				serviceContext.setUuid(layoutFriendlyURL.getUuid());

				String friendlyURL = layoutFriendlyURL.getFriendlyURL();

				if (Validator.isNumber(friendlyURL.substring(1))) {
					Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);

					friendlyURL = StringPool.SLASH + layout.getLayoutId();
				}

				importedLayoutFriendlyURL =
					LayoutFriendlyURLLocalServiceUtil.addLayoutFriendlyURL(
						userId, portletDataContext.getCompanyId(),
						portletDataContext.getScopeGroupId(), plid,
						portletDataContext.isPrivateLayout(), friendlyURL,
						layoutFriendlyURL.getLanguageId(), serviceContext);
			}
			else {
				importedLayoutFriendlyURL =
					LayoutFriendlyURLLocalServiceUtil.updateLayoutFriendlyURL(
						userId, portletDataContext.getCompanyId(),
						portletDataContext.getScopeGroupId(), plid,
						portletDataContext.isPrivateLayout(),
						layoutFriendlyURL.getFriendlyURL(),
						layoutFriendlyURL.getLanguageId(), serviceContext);
			}
		}
		else {
			layoutFriendlyURL = getUniqueLayoutFriendlyURL(
				portletDataContext, layoutFriendlyURL, null);

			importedLayoutFriendlyURL =
				LayoutFriendlyURLLocalServiceUtil.addLayoutFriendlyURL(
					userId, portletDataContext.getCompanyId(),
					portletDataContext.getScopeGroupId(), plid,
					portletDataContext.isPrivateLayout(),
					layoutFriendlyURL.getFriendlyURL(),
					layoutFriendlyURL.getLanguageId(), serviceContext);
		}

		portletDataContext.importClassedModel(
			layoutFriendlyURL, importedLayoutFriendlyURL);
	}

	protected LayoutFriendlyURL fetchExistingLayoutFriendlyURL(
		PortletDataContext portletDataContext,
		LayoutFriendlyURL layoutFriendlyURL, long plid) {

		LayoutFriendlyURL existingLayoutFriendlyURL =
			fetchStagedModelByUuidAndGroupId(
				layoutFriendlyURL.getUuid(),
				portletDataContext.getScopeGroupId());

		if (existingLayoutFriendlyURL == null) {
			existingLayoutFriendlyURL =
				LayoutFriendlyURLLocalServiceUtil.fetchLayoutFriendlyURL(
					plid, layoutFriendlyURL.getLanguageId(), false);
		}

		return existingLayoutFriendlyURL;
	}

	protected LayoutFriendlyURL getUniqueLayoutFriendlyURL(
			PortletDataContext portletDataContext,
			LayoutFriendlyURL layoutFriendlyURL,
			LayoutFriendlyURL existingLayoutFriendlyURL)
		throws Exception {

		String friendlyURL = layoutFriendlyURL.getFriendlyURL();

		for (int i = 1;; i++) {
			LayoutFriendlyURL duplicateLayoutFriendlyURL =
				LayoutFriendlyURLLocalServiceUtil.fetchLayoutFriendlyURL(
					portletDataContext.getScopeGroupId(),
					layoutFriendlyURL.isPrivateLayout(),
					layoutFriendlyURL.getFriendlyURL(),
					layoutFriendlyURL.getLanguageId());

			if ((duplicateLayoutFriendlyURL == null) ||
				((existingLayoutFriendlyURL != null) &&
				 (existingLayoutFriendlyURL.getLayoutFriendlyURLId() ==
					 duplicateLayoutFriendlyURL.getLayoutFriendlyURLId()))) {

				break;
			}

			layoutFriendlyURL.setFriendlyURL(friendlyURL + i);
		}

		return layoutFriendlyURL;
	}

}