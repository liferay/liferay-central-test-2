/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetLinkLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.util.DDMTemplateTestUtil;
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Juan Fernández
 */
public class BasePortletExportImportTestCase extends BaseExportImportTestCase {

	public String getNamespace() {
		return null;
	}

	public String getPortletId() {
		return null;
	}

	@Test
	public void testExportImportAssetLinks() throws Exception {
		StagedModel stagedModel = addStagedModel(group.getGroupId());

		StagedModel relatedStagedModel1 = addStagedModel(group.getGroupId());
		StagedModel relatedStagedModel2 = addStagedModel(group.getGroupId());

		addAssetLink(
			group.getGroupId(), getStagedModelUuid(stagedModel),
			getStagedModelUuid(relatedStagedModel1), 1);
		addAssetLink(
			group.getGroupId(), getStagedModelUuid(stagedModel),
			getStagedModelUuid(relatedStagedModel2), 2);

		doExportImportPortlet(getPortletId());

		StagedModel importedStagedModel = getStagedModel(
			getStagedModelUuid(stagedModel), importedGroup.getGroupId());

		Assert.assertNotNull(importedStagedModel);

		validateImportedLinks(getStagedModelUuid(stagedModel));
	}

	@Test
	public void testExportImportDisplayStyleFromCurrentGroup()
		throws Exception {

		testExportImportDisplayStyle(group.getGroupId(), StringPool.BLANK);
	}

	@Test
	public void testExportImportDisplayStyleFromDifferentGroup()
		throws Exception {

		Group group2 = GroupTestUtil.addGroup();

		testExportImportDisplayStyle(group2.getGroupId(), StringPool.BLANK);
	}

	@Test
	public void testExportImportDisplayStyleFromGlobalScope() throws Exception {
		Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
			group.getCompanyId());

		testExportImportDisplayStyle(companyGroup.getGroupId(), "company");
	}

	@Test
	public void testExportImportDisplayStyleFromLayoutScope() throws Exception {
		testExportImportDisplayStyle(group.getGroupId(), "layout");
	}

	protected AssetLink addAssetLink(
			long groupId, String sourceStagedModelUuid,
			String targetStagedModelUuid, int weight)
		throws PortalException, SystemException {

		AssetEntry originAssetEntry = AssetEntryLocalServiceUtil.getEntry(
			groupId, sourceStagedModelUuid);
		AssetEntry targetAssetEntry = AssetEntryLocalServiceUtil.getEntry(
			groupId, targetStagedModelUuid);

		return AssetLinkLocalServiceUtil.addLink(
			TestPropsValues.getUserId(), originAssetEntry.getEntryId(),
			targetAssetEntry.getEntryId(), 0, weight);
	}

	protected void addParameter(
		Map<String, String[]> parameterMap, String name, boolean value) {

		addParameter(parameterMap, getNamespace(), name, value);
	}

	protected void doExportImportPortlet(String portletId) throws Exception {
		larFile = LayoutLocalServiceUtil.exportPortletInfoAsFile(
			layout.getPlid(), layout.getGroupId(), portletId,
			getExportParameterMap(), null, null);

		importedLayout = LayoutTestUtil.addLayout(
			importedGroup.getGroupId(), ServiceTestUtil.randomString());

		LayoutLocalServiceUtil.importPortletInfo(
			TestPropsValues.getUserId(), importedLayout.getPlid(),
			importedGroup.getGroupId(), portletId, getImportParameterMap(),
			larFile);
	}

	protected PortletPreferences getImportedPortletPreferences(
			Map<String, String[]> preferenceMap)
		throws Exception {

		// Export site LAR

		String portletId = LayoutTestUtil.addPortletToLayout(
			TestPropsValues.getUserId(), this.layout, getPortletId(),
			"column-1", preferenceMap);

		doExportImportPortlet(portletId);

		return LayoutTestUtil.getPortletPreferences(
			importedLayout.getCompanyId(), importedLayout.getPlid(), portletId);
	}

	protected void testExportImportDisplayStyle(
			long displayStyleGroupId, String scopeType)
		throws Exception {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			group.getCompanyId(), getPortletId());

		if (portlet == null) {
			return;
		}

		if (scopeType.equals("layout") && !portlet.isScopeable()) {
			Assert.assertTrue("This test does not apply", true);

			return;
		}

		TemplateHandler templateHandler = portlet.getTemplateHandlerInstance();

		if (templateHandler == null) {
			Assert.assertTrue("This test does not apply", true);

			return;
		}

		String className = templateHandler.getClassName();

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			displayStyleGroupId, PortalUtil.getClassNameId(className), 0);

		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		String displayStyle =
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX + ddmTemplate.getUuid();

		preferenceMap.put("displayStyle", new String[] {displayStyle});
		preferenceMap.put(
			"displayStyleGroupId",
			new String[] {String.valueOf(ddmTemplate.getGroupId())});
		preferenceMap.put("lfrScopeType", new String[] {scopeType});

		if (scopeType.equals("layout")) {
			preferenceMap.put(
				"lfrScopeLayoutUuid", new String[] {this.layout.getUuid()});
		}

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		String importedDisplayStyle = portletPreferences.getValue(
			"displayStyle", StringPool.BLANK);

		Assert.assertEquals(displayStyle, importedDisplayStyle);

		long importedDisplayStyleGroupId = GetterUtil.getLong(
			portletPreferences.getValue("displayStyleGroupId", null));

		long expectedDisplayStyleGroupId = importedGroup.getGroupId();

		if (scopeType.equals("company")) {
			Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
				importedGroup.getCompanyId());

			expectedDisplayStyleGroupId = companyGroup.getGroupId();
		}
		else if (displayStyleGroupId != group.getGroupId()) {
			expectedDisplayStyleGroupId = displayStyleGroupId;
		}

		Assert.assertEquals(
			expectedDisplayStyleGroupId, importedDisplayStyleGroupId);
	}

	protected void validateImportedLinks(String uuid)
		throws PortalException, SystemException {

		AssetEntry originalAssetEntry = AssetEntryLocalServiceUtil.getEntry(
			group.getGroupId(), uuid);

		List<AssetLink> originalAssetLinks = AssetLinkLocalServiceUtil.getLinks(
			originalAssetEntry.getEntryId());

		AssetEntry importedAssetEntry =  AssetEntryLocalServiceUtil.getEntry(
			importedGroup.getGroupId(), uuid);

		List<AssetLink> importedAssetLinks = AssetLinkLocalServiceUtil.getLinks(
			importedAssetEntry.getEntryId());

		Assert.assertEquals(
			originalAssetLinks.size(), importedAssetLinks.size());

		for (AssetLink originalLink : originalAssetLinks) {
			AssetEntry sourceAssetEntry = AssetEntryLocalServiceUtil.getEntry(
				originalLink.getEntryId1());

			AssetEntry targetAssetEntry = AssetEntryLocalServiceUtil.getEntry(
				originalLink.getEntryId2());

			Iterator<AssetLink> iterator = importedAssetLinks.iterator();

			while (iterator.hasNext()) {
				AssetLink importedLink = iterator.next();

				AssetEntry importedLinkSourceAssetEntry =
					AssetEntryLocalServiceUtil.getEntry(
						importedLink.getEntryId1());
				AssetEntry importedLinkTargetAssetEntry =
					AssetEntryLocalServiceUtil.getEntry(
						importedLink.getEntryId2());

				if (!sourceAssetEntry.getClassUuid().equals(
						importedLinkSourceAssetEntry.getClassUuid())) {

					continue;
				}

				if (!targetAssetEntry.getClassUuid().equals(
						importedLinkTargetAssetEntry.getClassUuid())) {

					continue;
				}

				Assert.assertEquals(
					originalLink.getWeight(), importedLink.getWeight());
				Assert.assertEquals(
					originalLink.getType(), importedLink.getType());

				iterator.remove();

				break;
			}
		}

		Assert.assertEquals(0, importedAssetLinks.size());
	}

}