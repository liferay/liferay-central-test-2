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

package com.liferay.asset.categories.admin.web.lar;

import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.model.adapter.ModelAdapterUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.adapter.StagedAssetLink;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.asset.service.AssetLinkLocalService;
import com.liferay.portlet.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class StagedAssetLinkStagedModelDataHandler
	extends BaseStagedModelDataHandler<StagedAssetLink> {

	public static final String[] CLASS_NAMES =
		{StagedAssetLink.class.getName()};

	@Override
	public void deleteStagedModel(StagedAssetLink stagedAssetLink)
		throws PortalException {

		_assetLinkLocalService.deleteAssetLink(stagedAssetLink);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		StagedAssetLink stagedAssetLink = fetchExistingAssetLink(
			groupId, parseAssetEntry1Uuid(uuid), parseAssetEntry2Uuid(uuid));

		if (stagedAssetLink != null) {
			deleteStagedModel(stagedAssetLink);
		}
	}

	@Override
	public List<StagedAssetLink> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		DynamicQuery dynamicQuery = getAssetLinkDynamicQuery(
			companyId, 0, parseAssetEntry1Uuid(uuid),
			parseAssetEntry2Uuid(uuid));

		dynamicQuery.addOrder(OrderFactoryUtil.desc("linkId"));

		List<AssetLink> assetLinks = _assetLinkLocalService.dynamicQuery(
			dynamicQuery);

		return ModelAdapterUtil.adapt(
			assetLinks, AssetLink.class, StagedAssetLink.class);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	protected void addAssetReference(
		PortletDataContext portletDataContext, StagedAssetLink stagedAssetLink,
		Element stagedAssetLinkElement, AssetEntry assetEntry) {

		AssetRenderer<? extends StagedModel> assetRenderer = null;
		StagedModel stagedModel = null;

		try {
			assetRenderer =
				(AssetRenderer<? extends StagedModel>)
					assetEntry.getAssetRenderer();

			stagedModel = assetRenderer.getAssetObject();
		}
		catch (Exception e) {
			return;
		}

		if (stagedModel == null) {
			return;
		}

		portletDataContext.addReferenceElement(
			stagedAssetLink, stagedAssetLinkElement, stagedModel,
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY_DISPOSABLE, true);
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			StagedAssetLink stagedAssetLink)
		throws Exception {

		Element stagedAssetLinkElement =
			portletDataContext.getExportDataElement(stagedAssetLink);

		AssetEntry assetEntry1 = _assetEntryLocalService.fetchEntry(
			stagedAssetLink.getEntryId1());

		addAssetReference(
			portletDataContext, stagedAssetLink, stagedAssetLinkElement,
			assetEntry1);

		AssetEntry assetEntry2 = _assetEntryLocalService.fetchEntry(
			stagedAssetLink.getEntryId2());

		addAssetReference(
			portletDataContext, stagedAssetLink, stagedAssetLinkElement,
			assetEntry2);

		portletDataContext.addClassedModel(
			stagedAssetLinkElement,
			ExportImportPathUtil.getModelPath(stagedAssetLink),
			stagedAssetLink);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			StagedAssetLink stagedAssetLink)
		throws Exception {

		long userId = portletDataContext.getUserId(
			stagedAssetLink.getUserUuid());

		StagedAssetLink existingStagedAssetLink = fetchExistingAssetLink(
			portletDataContext.getScopeGroupId(),
			stagedAssetLink.getEntry1Uuid(), stagedAssetLink.getEntry2Uuid());

		if (existingStagedAssetLink != null) {
			_assetLinkLocalService.updateLink(
				userId, existingStagedAssetLink.getEntryId1(),
				existingStagedAssetLink.getEntryId2(),
				stagedAssetLink.getType(), stagedAssetLink.getWeight());

			return;
		}

		Map<Long, Long> assetEntry1Ids =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				stagedAssetLink.getEntry1ClassName());
		Map<Long, Long> assetEntry2Ids =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				stagedAssetLink.getEntry2ClassName());

		long assetEntry1Id = MapUtil.getLong(
			assetEntry1Ids, stagedAssetLink.getEntryId1(), -1);
		long assetEntry2Id = MapUtil.getLong(
			assetEntry2Ids, stagedAssetLink.getEntryId2(), -1);

		if ((assetEntry1Id <= 0) || (assetEntry2Id <= 0)) {
			return;
		}

		_assetLinkLocalService.addLink(
			userId, assetEntry1Id, assetEntry2Id, stagedAssetLink.getType(),
			stagedAssetLink.getWeight());
	}

	protected StagedAssetLink fetchExistingAssetLink(
		long groupId, String assetEntry1Uuid, String assetEntry2Uuid) {

		DynamicQuery dynamicQuery = getAssetLinkDynamicQuery(
			0, groupId, assetEntry1Uuid, assetEntry2Uuid);

		List<AssetLink> assetLinks = _assetLinkLocalService.dynamicQuery(
			dynamicQuery);

		if (ListUtil.isNotEmpty(assetLinks)) {
			return ModelAdapterUtil.adapt(
				assetLinks.get(0), AssetLink.class, StagedAssetLink.class);
		}

		return null;
	}

	protected DynamicQuery getAssetLinkDynamicQuery(
		long companyId, long groupId, String assetEntry1Uuid,
		String assetEntry2Uuid) {

		// Asset entry 1 dynamic query

		Projection entryIdProjection = ProjectionFactoryUtil.property(
			"entryId");

		DynamicQuery assetEntry1DynamicQuery =
			_assetEntryLocalService.dynamicQuery();

		assetEntry1DynamicQuery.setProjection(entryIdProjection);

		Property classUuidProperty = PropertyFactoryUtil.forName("classUuid");

		assetEntry1DynamicQuery.add(classUuidProperty.eq(assetEntry1Uuid));

		// Asset entry 2 dynamic query

		DynamicQuery assetEntry2DynamicQuery =
			_assetEntryLocalService.dynamicQuery();

		assetEntry2DynamicQuery.setProjection(entryIdProjection);

		assetEntry2DynamicQuery.add(classUuidProperty.eq(assetEntry2Uuid));

		// Asset link dynamic query

		DynamicQuery dynamicQuery = _assetLinkLocalService.dynamicQuery();

		Property entryId1IdProperty = PropertyFactoryUtil.forName("entry1Id");

		dynamicQuery.add(entryId1IdProperty.eq(assetEntry1DynamicQuery));

		Property entryId2IdProperty = PropertyFactoryUtil.forName("entry2Id");

		dynamicQuery.add(entryId2IdProperty.eq(assetEntry2DynamicQuery));

		// Company ID

		if (companyId > 0) {
			Property companyIdProperty = PropertyFactoryUtil.forName(
				"companyId");

			Criterion companyIdCriterion = companyIdProperty.eq(companyId);

			assetEntry1DynamicQuery.add(companyIdCriterion);
			assetEntry2DynamicQuery.add(companyIdCriterion);
			dynamicQuery.add(companyIdCriterion);
		}

		// Group ID

		if (groupId > 0) {
			Property groupIdProperty = PropertyFactoryUtil.forName("groupId");

			Criterion groupIdCriterion = groupIdProperty.eq(groupId);

			assetEntry1DynamicQuery.add(groupIdCriterion);
			assetEntry2DynamicQuery.add(groupIdCriterion);
			dynamicQuery.add(groupIdCriterion);
		}

		return dynamicQuery;
	}

	protected String parseAssetEntry1Uuid(String uuid) {
		return uuid.substring(0, uuid.indexOf(StringPool.POUND));
	}

	protected String parseAssetEntry2Uuid(String uuid) {
		return uuid.substring(uuid.indexOf(StringPool.POUND) + 1);
	}

	@Reference
	protected void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	@Reference
	protected void setAssetLinkLocalService(
		AssetLinkLocalService assetLinkLocalService) {

		_assetLinkLocalService = assetLinkLocalService;
	}

	private AssetEntryLocalService _assetEntryLocalService;
	private AssetLinkLocalService _assetLinkLocalService;

}