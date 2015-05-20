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

package com.liferay.asset.publisher.lar;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.publisher.web.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.web.util.AssetPublisherUtil;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.lar.exportimportconfiguration.ExportImportConfigurationConstants;
import com.liferay.portal.kernel.lar.exportimportconfiguration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.lar.test.BasePortletExportImportTestCase;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PortletInstance;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.asset.util.test.AssetTestUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestUtil;
import com.liferay.portlet.journal.model.JournalArticle;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Julio Camarero
 */
@RunWith(Arquillian.class)
@Sync
public class AssetPublisherExportImportTest
	extends BasePortletExportImportTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	public String getPortletId() throws Exception {
		PortletInstance portletInstance = new PortletInstance(
			AssetPublisherPortletKeys.ASSET_PUBLISHER,
			RandomTestUtil.randomString());

		return portletInstance.getPortletInstanceKey();
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		ServiceTestUtil.setUser(TestPropsValues.getUser());
	}

	@Test
	public void testAnyDLFileEntryType() throws Exception {
		Map<String, String[]> preferenceMap = new HashMap<>();

		long dlFileEntryClassNameId = PortalUtil.getClassNameId(
			DLFileEntry.class);

		preferenceMap.put(
			"anyAssetType",
			new String[] {String.valueOf(dlFileEntryClassNameId)});
		preferenceMap.put(
			"anyClassTypeDLFileEntryAssetRendererFactory",
			new String[] {String.valueOf(Boolean.TRUE)});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		long anyAssetType = GetterUtil.getLong(
			portletPreferences.getValue("anyAssetType", null));

		Assert.assertEquals(dlFileEntryClassNameId, anyAssetType);

		String anyClassTypeDLFileEntryAssetRendererFactory =
			portletPreferences.getValue(
				"anyClassTypeDLFileEntryAssetRendererFactory", null);

		Assert.assertEquals(
			anyClassTypeDLFileEntryAssetRendererFactory,
			String.valueOf(Boolean.TRUE));
	}

	@Test
	public void testAnyJournalStructure() throws Exception {
		Map<String, String[]> preferenceMap = new HashMap<>();

		long journalArticleClassNameId = PortalUtil.getClassNameId(
			JournalArticle.class);

		preferenceMap.put(
			"anyAssetType",
			new String[] {String.valueOf(journalArticleClassNameId)});
		preferenceMap.put(
			"anyClassTypeJournalArticleAssetRendererFactory",
			new String[] {String.valueOf(Boolean.TRUE)});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		long anyAssetType = GetterUtil.getLong(
			portletPreferences.getValue("anyAssetType", null));

		Assert.assertEquals(journalArticleClassNameId, anyAssetType);

		String anyClassTypeDLFileEntryAssetRendererFactory =
			portletPreferences.getValue(
				"anyClassTypeJournalArticleAssetRendererFactory", null);

		Assert.assertEquals(
			anyClassTypeDLFileEntryAssetRendererFactory,
			String.valueOf(Boolean.TRUE));
	}

	@Test
	public void testAssetCategories() throws Exception {
		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			group.getGroupId());

		AssetCategory assetCategory = AssetTestUtil.addCategory(
			group.getGroupId(), assetVocabulary.getVocabularyId());

		Map<String, String[]> preferenceMap = new HashMap<>();

		preferenceMap.put("queryName0", new String[] {"assetCategories"});

		preferenceMap.put(
			"queryValues0",
			new String[] {String.valueOf(assetCategory.getCategoryId())});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		long importedAssetCategoryId = GetterUtil.getLong(
			portletPreferences.getValue("queryValues0", null));

		Assert.assertNotEquals(importedAssetCategoryId, 0L);

		Assert.assertNotEquals(
			assetCategory.getCategoryId(), importedAssetCategoryId);

		AssetCategory importedAssetCategory =
			AssetCategoryLocalServiceUtil.fetchAssetCategory(
				importedAssetCategoryId);

		Assert.assertNotNull(importedAssetCategory);
		Assert.assertEquals(
			assetCategory.getUuid(), importedAssetCategory.getUuid());
	}

	@Test
	public void testChildLayoutScopeIds() throws Exception {
		Map<String, String[]> preferenceMap = new HashMap<>();

		Group childGroup = GroupTestUtil.addGroup(group.getGroupId());

		preferenceMap.put(
			"scopeIds",
			new String[] {
				AssetPublisherUtil.SCOPE_ID_CHILD_GROUP_PREFIX +
					childGroup.getGroupId()
			});

		try {
			PortletPreferences portletPreferences =
				getImportedPortletPreferences(preferenceMap);

			Assert.assertEquals(
				null, portletPreferences.getValue("scopeId", null));
			Assert.assertTrue(
				"The child group ID should have been filtered out on import",
				ArrayUtil.isEmpty(
					portletPreferences.getValues("scopeIds", null)));
		}
		finally {
			GroupLocalServiceUtil.deleteGroup(childGroup);
		}
	}

	@Test
	public void testDisplayStyle() throws Exception {
		Map<String, String[]> preferenceMap = new HashMap<>();

		String displayStyle = RandomTestUtil.randomString();

		preferenceMap.put("displayStyle", new String[] {displayStyle});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		Assert.assertEquals(
			displayStyle, portletPreferences.getValue("displayStyle", null));
		Assert.assertTrue(
			"The display style should not be null",
			Validator.isNotNull(
				portletPreferences.getValue("displayStyle", null)));
	}

	@Ignore()
	@Override
	@Test
	public void testExportImportAssetLinks() throws Exception {
	}

	@Test
	public void testGlobalScopeId() throws Exception {
		Map<String, String[]> preferenceMap = new HashMap<>();

		Company company = CompanyLocalServiceUtil.getCompany(
			layout.getCompanyId());

		Group companyGroup = company.getGroup();

		preferenceMap.put(
			"scopeIds",
			new String[] {
				AssetPublisherUtil.SCOPE_ID_GROUP_PREFIX +
					companyGroup.getGroupId()
			});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		Assert.assertEquals(
			AssetPublisherUtil.SCOPE_ID_GROUP_PREFIX +
				companyGroup.getGroupId(),
			portletPreferences.getValue("scopeIds", null));
		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));
	}

	@Test
	public void testLayoutScopeId() throws Exception {
		Map<String, String[]> preferenceMap = new HashMap<>();

		GroupTestUtil.addGroup(TestPropsValues.getUserId(), layout);

		preferenceMap.put(
			"scopeIds",
			new String[] {
				AssetPublisherUtil.SCOPE_ID_LAYOUT_UUID_PREFIX +
					layout.getUuid()
			});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		Assert.assertEquals(
			AssetPublisherUtil.SCOPE_ID_LAYOUT_UUID_PREFIX +
				importedLayout.getUuid(),
			portletPreferences.getValue("scopeIds", null));
		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));
	}

	@Test
	public void testLegacyLayoutScopeId() throws Exception {
		Map<String, String[]> preferenceMap = new HashMap<>();

		GroupTestUtil.addGroup(TestPropsValues.getUserId(), layout);

		preferenceMap.put(
			"scopeIds", new String[] {
				AssetPublisherUtil.SCOPE_ID_LAYOUT_PREFIX + layout.getLayoutId()
			});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		Assert.assertEquals(
			AssetPublisherUtil.SCOPE_ID_LAYOUT_UUID_PREFIX +
				importedLayout.getUuid(),
			portletPreferences.getValue("scopeIds", null));
		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));
	}

	@Test
	public void testOneDLFileEntryType() throws Exception {
		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), DLFileEntryType.class.getName());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntryType dlFileEntryType = addDLFileEntryType(
			group.getGroupId(), ddmStructure.getStructureId(), serviceContext);

		serviceContext.setUuid(ddmStructure.getUuid());

		DDMStructure importedDDMStructure = DDMStructureTestUtil.addStructure(
			importedGroup.getGroupId(), DLFileEntryType.class.getName(), 0,
			ddmStructure.getDDMForm(), LocaleUtil.getDefault(), serviceContext);

		serviceContext.setUuid(dlFileEntryType.getUuid());

		DLFileEntryType importedDLFileEntryType = addDLFileEntryType(
			importedGroup.getGroupId(), importedDDMStructure.getStructureId(),
			serviceContext);

		Map<String, String[]> preferenceMap = new HashMap<>();

		long dlFileEntryClassNameId = PortalUtil.getClassNameId(
			DLFileEntry.class);

		preferenceMap.put(
			"anyAssetType",
			new String[] {String.valueOf(dlFileEntryClassNameId)});
		preferenceMap.put(
			"anyClassTypeDLFileEntryAssetRendererFactory",
			new String[] {
				String.valueOf(dlFileEntryType.getFileEntryTypeId())
			});
		preferenceMap.put(
			"classTypeIds",
			new String[] {
				String.valueOf(dlFileEntryType.getFileEntryTypeId())
			});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		long anyClassTypeDLFileEntryAssetRendererFactory = GetterUtil.getLong(
			portletPreferences.getValue(
				"anyClassTypeDLFileEntryAssetRendererFactory", null));

		Assert.assertEquals(
			anyClassTypeDLFileEntryAssetRendererFactory,
			importedDLFileEntryType.getFileEntryTypeId());

		long anyAssetType = GetterUtil.getLong(
			portletPreferences.getValue("anyAssetType", null));

		Assert.assertEquals(dlFileEntryClassNameId, anyAssetType);

		long classTypeIds = GetterUtil.getLong(
			portletPreferences.getValue("classTypeIds", null));

		Assert.assertEquals(
			importedDLFileEntryType.getFileEntryTypeId(), classTypeIds);
	}

	@Test
	public void testOneJournalStructure() throws Exception {
		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setUuid(ddmStructure.getUuid());

		DDMStructure importedDDMStructure = DDMStructureTestUtil.addStructure(
			importedGroup.getGroupId(), JournalArticle.class.getName(), 0,
			ddmStructure.getDDMForm(), LocaleUtil.getDefault(), serviceContext);

		Map<String, String[]> preferenceMap = new HashMap<>();

		long journalArticleClassNameId = PortalUtil.getClassNameId(
			JournalArticle.class);

		preferenceMap.put(
			"anyAssetType",
			new String[] {String.valueOf(journalArticleClassNameId)});
		preferenceMap.put(
			"anyClassTypeJournalArticleAssetRendererFactory",
			new String[] {String.valueOf(ddmStructure.getStructureId())});
		preferenceMap.put(
			"classTypeIds",
			new String[] {String.valueOf(ddmStructure.getStructureId())});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		long anyClassTypeJournalArticleAssetRendererFactory =
			GetterUtil.getLong(
				portletPreferences.getValue(
					"anyClassTypeJournalArticleAssetRendererFactory", null));

		Assert.assertEquals(
			anyClassTypeJournalArticleAssetRendererFactory,
			importedDDMStructure.getStructureId());

		long anyAssetType = GetterUtil.getLong(
			portletPreferences.getValue("anyAssetType", null));

		Assert.assertEquals(journalArticleClassNameId, anyAssetType);

		long classTypeIds = GetterUtil.getLong(
			portletPreferences.getValue("classTypeIds", null));

		Assert.assertEquals(
			importedDDMStructure.getStructureId(), classTypeIds);
	}

	@Test
	public void testSeveralDLFileEntryTypes() throws Exception {
		DDMStructure ddmStructure1 = DDMStructureTestUtil.addStructure(
			group.getGroupId(), DLFileEntryType.class.getName());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		DLFileEntryType dlFileEntryType1 = addDLFileEntryType(
			group.getGroupId(), ddmStructure1.getStructureId(), serviceContext);

		serviceContext.setUuid(ddmStructure1.getUuid());

		DDMStructure importedDDMStructure1 = DDMStructureTestUtil.addStructure(
			importedGroup.getGroupId(), DLFileEntryType.class.getName(), 0,
			ddmStructure1.getDDMForm(), LocaleUtil.getDefault(),
			serviceContext);

		serviceContext.setUuid(dlFileEntryType1.getUuid());

		DLFileEntryType importedDLFileEntryType1 = addDLFileEntryType(
			importedGroup.getGroupId(), importedDDMStructure1.getStructureId(),
			serviceContext);

		DDMStructure ddmStructure2 = DDMStructureTestUtil.addStructure(
			group.getGroupId(), DLFileEntryType.class.getName());

		serviceContext.setUuid(null);

		DLFileEntryType dlFileEntryType2 = addDLFileEntryType(
			group.getGroupId(), ddmStructure2.getStructureId(), serviceContext);

		serviceContext.setUuid(ddmStructure2.getUuid());

		DDMStructure importedDDMStructure2 = DDMStructureTestUtil.addStructure(
			importedGroup.getGroupId(), DLFileEntryType.class.getName(), 0,
			ddmStructure2.getDDMForm(), LocaleUtil.getDefault(),
			serviceContext);

		serviceContext.setUuid(dlFileEntryType2.getUuid());

		DLFileEntryType importedDLFileEntryType2 = addDLFileEntryType(
			importedGroup.getGroupId(), importedDDMStructure2.getStructureId(),
			serviceContext);

		Map<String, String[]> preferenceMap = new HashMap<>();

		preferenceMap.put(
			"anyClassTypeDLFileEntryAssetRendererFactory",
			new String[] {String.valueOf(Boolean.FALSE)});

		preferenceMap.put(
			"classTypeIdsDLFileEntryAssetRendererFactory",
			new String[] {
				String.valueOf(dlFileEntryType1.getFileEntryTypeId()),
				String.valueOf(dlFileEntryType2.getFileEntryTypeId())
			});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		Assert.assertEquals(
			importedDLFileEntryType1.getFileEntryTypeId() + StringPool.COMMA +
				importedDLFileEntryType2.getFileEntryTypeId(),
			StringUtil.merge(
				portletPreferences.getValues(
					"classTypeIdsDLFileEntryAssetRendererFactory", null)));
	}

	@Test
	public void testSeveralJournalStructures() throws Exception {
		DDMStructure ddmStructure1 = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setUuid(ddmStructure1.getUuid());

		DDMStructure importedDDMStructure1 = DDMStructureTestUtil.addStructure(
			importedGroup.getGroupId(), JournalArticle.class.getName(), 0,
			ddmStructure1.getDDMForm(), LocaleUtil.getDefault(),
			serviceContext);

		DDMStructure ddmStructure2 = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		serviceContext.setUuid(ddmStructure2.getUuid());

		DDMStructure importedDDMStructure2 = DDMStructureTestUtil.addStructure(
			importedGroup.getGroupId(), JournalArticle.class.getName(), 0,
			ddmStructure1.getDDMForm(), LocaleUtil.getDefault(),
			serviceContext);

		Map<String, String[]> preferenceMap = new HashMap<>();

		preferenceMap.put(
			"anyClassTypeJournalArticleAssetRendererFactory",
			new String[] {String.valueOf(Boolean.FALSE)});

		preferenceMap.put(
			"classTypeIdsJournalArticleAssetRendererFactory",
			new String[] {
				String.valueOf(ddmStructure1.getStructureId()),
				String.valueOf(ddmStructure2.getStructureId())
			});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		Assert.assertEquals(
			importedDDMStructure1.getStructureId() + StringPool.COMMA +
				importedDDMStructure2.getStructureId(),
			StringUtil.merge(
				portletPreferences.getValues(
					"classTypeIdsJournalArticleAssetRendererFactory", null)));
	}

	@Test
	public void testSeveralLayoutScopeIds() throws Exception {
		Company company = CompanyLocalServiceUtil.getCompany(
			layout.getCompanyId());

		Layout secondLayout = LayoutTestUtil.addLayout(group);

		GroupTestUtil.addGroup(TestPropsValues.getUserId(), secondLayout);

		Map<String, String[]> preferenceMap = new HashMap<>();

		GroupTestUtil.addGroup(TestPropsValues.getUserId(), layout);

		Group companyGroup = company.getGroup();

		preferenceMap.put(
			"scopeIds",
			new String[] {
				AssetPublisherUtil.SCOPE_ID_GROUP_PREFIX +
					companyGroup.getGroupId(),
				AssetPublisherUtil.SCOPE_ID_LAYOUT_UUID_PREFIX +
					layout.getUuid(),
				AssetPublisherUtil.SCOPE_ID_LAYOUT_UUID_PREFIX +
					secondLayout.getUuid()
			});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		Layout importedSecondLayout =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				secondLayout.getUuid(), importedGroup.getGroupId(),
				importedLayout.isPrivateLayout());

		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));

		StringBundler sb = new StringBundler(8);

		sb.append(AssetPublisherUtil.SCOPE_ID_GROUP_PREFIX);
		sb.append(companyGroup.getGroupId());
		sb.append(StringPool.COMMA);
		sb.append(AssetPublisherUtil.SCOPE_ID_LAYOUT_UUID_PREFIX);
		sb.append(importedLayout.getUuid());
		sb.append(StringPool.COMMA);
		sb.append(AssetPublisherUtil.SCOPE_ID_LAYOUT_UUID_PREFIX);
		sb.append(importedSecondLayout.getUuid());

		Assert.assertEquals(
			sb.toString(),
			StringUtil.merge(portletPreferences.getValues("scopeIds", null)));
	}

	@Test
	public void testSeveralLegacyLayoutScopeIds() throws Exception {
		Layout secondLayout = LayoutTestUtil.addLayout(group);

		GroupTestUtil.addGroup(TestPropsValues.getUserId(), secondLayout);

		Map<String, String[]> preferenceMap = new HashMap<>();

		GroupTestUtil.addGroup(TestPropsValues.getUserId(), layout);

		preferenceMap.put(
			"scopeIds",
			new String[] {
				AssetPublisherUtil.SCOPE_ID_LAYOUT_PREFIX +
					layout.getLayoutId(),
				AssetPublisherUtil.SCOPE_ID_LAYOUT_PREFIX +
					secondLayout.getLayoutId()
			});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		Layout importedSecondLayout =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				secondLayout.getUuid(), importedGroup.getGroupId(),
				importedLayout.isPrivateLayout());

		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));

		StringBundler sb = new StringBundler(5);

		sb.append(AssetPublisherUtil.SCOPE_ID_LAYOUT_UUID_PREFIX);
		sb.append(importedLayout.getUuid());
		sb.append(StringPool.COMMA);
		sb.append(AssetPublisherUtil.SCOPE_ID_LAYOUT_UUID_PREFIX);
		sb.append(importedSecondLayout.getUuid());

		Assert.assertEquals(
			sb.toString(),
			StringUtil.merge(portletPreferences.getValues("scopeIds", null)));
	}

	@Test
	public void testSortByAssetVocabulary() throws Exception {
		testSortByAssetVocabulary(false);
	}

	@Test
	public void testSortByGlobalAssetVocabulary() throws Exception {
		testSortByAssetVocabulary(true);
	}

	protected DLFileEntryType addDLFileEntryType(
			long groupId, long ddmStructureId, ServiceContext serviceContext)
		throws Exception {

		return DLFileEntryTypeLocalServiceUtil.addFileEntryType(
			serviceContext.getUserId(), groupId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new long[] {ddmStructureId},
			serviceContext);
	}

	@Override
	protected void exportImportPortlet(String portletId) throws Exception {
		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			layout.getGroupId(), layout.isPrivateLayout());

		User user = TestPropsValues.getUser();

		Map<String, Serializable> exportSettingsMap =
			ExportImportConfigurationSettingsMapFactory.buildSettingsMap(
				user.getUserId(), layout.getGroupId(), layout.isPrivateLayout(),
				ExportImportHelperUtil.getLayoutIds(layouts),
				getExportParameterMap(), user.getLocale(), user.getTimeZone());

		ExportImportConfiguration exportConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addExportImportConfiguration(
					user.getUserId(), layout.getGroupId(), StringPool.BLANK,
					StringPool.BLANK,
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					exportSettingsMap, WorkflowConstants.STATUS_DRAFT,
					new ServiceContext());

		larFile = LayoutLocalServiceUtil.exportLayoutsAsFile(
			exportConfiguration);

		// Import site LAR

		Map<String, Serializable> importSettingsMap =
			ExportImportConfigurationSettingsMapFactory.buildImportSettingsMap(
				user.getUserId(), importedGroup.getGroupId(),
				layout.isPrivateLayout(), null, getImportParameterMap(),
				Constants.IMPORT, user.getLocale(), user.getTimeZone(),
				larFile.getName());

		ExportImportConfiguration importConfiguration =
			ExportImportConfigurationLocalServiceUtil.
				addExportImportConfiguration(
					user.getUserId(), importedGroup.getGroupId(),
					StringPool.BLANK, StringPool.BLANK,
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					importSettingsMap, WorkflowConstants.STATUS_DRAFT,
					new ServiceContext());

		LayoutLocalServiceUtil.importLayouts(importConfiguration, larFile);

		importedLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
			layout.getUuid(), importedGroup.getGroupId(),
			layout.isPrivateLayout());

		Assert.assertNotNull(importedLayout);
	}

	@Override
	protected Map<String, String[]> getExportParameterMap() throws Exception {
		Map<String, String[]> parameterMap = new HashMap<>();

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()});

		return parameterMap;
	}

	@Override
	protected Map<String, String[]> getImportParameterMap() throws Exception {
		return getExportParameterMap();
	}

	protected void testSortByAssetVocabulary(boolean globalVocabulary)
		throws Exception {

		long groupId = group.getGroupId();

		if (globalVocabulary) {
			Company company = CompanyLocalServiceUtil.getCompany(
				layout.getCompanyId());

			groupId = company.getGroupId();
		}

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), groupId,
				RandomTestUtil.randomString(),
				ServiceContextTestUtil.getServiceContext(groupId));

		Map<String, String[]> preferenceMap = new HashMap<>();

		preferenceMap.put(
			"assetVocabularyId",
			new String[] {String.valueOf(assetVocabulary.getVocabularyId())});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			preferenceMap);

		Assert.assertNotNull(
			"Portlet preference \"assetVocabularyId\" is null",
			portletPreferences.getValue("assetVocabularyId", null));

		long importedAssetVocabularyId = GetterUtil.getLong(
			portletPreferences.getValue("assetVocabularyId", null));

		AssetVocabulary importedVocabulary =
			AssetVocabularyLocalServiceUtil.fetchAssetVocabulary(
				importedAssetVocabularyId);

		Assert.assertNotNull(
			"Vocabulary " + importedAssetVocabularyId + " does not exist",
			importedVocabulary);

		long expectedGroupId = groupId;

		if (!globalVocabulary) {
			expectedGroupId = importedGroup.getGroupId();
		}

		Assert.assertEquals(
			"Vocabulary " + importedAssetVocabularyId +
				" does not belong to group " + expectedGroupId,
			expectedGroupId, importedVocabulary.getGroupId());

		AssetVocabularyLocalServiceUtil.deleteAssetVocabulary(assetVocabulary);
	}

}