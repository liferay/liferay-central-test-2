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

package com.liferay.asset.publisher.internal.exportimport.portlet.preferences.processor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.asset.publisher.web.constants.AssetPublisherPortletKeys;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.portlet.preferences.processor.ExportImportPortletPreferencesProcessor;
import com.liferay.exportimport.test.util.ExportImportTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portlet.asset.util.test.AssetTestUtil;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.util.HashMap;

import javax.portlet.PortletPreferences;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mate Thurzo
 */
@RunWith(Arquillian.class)
@Sync
public class AssetPublisherExportImportPortletPreferencesProcessorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() {
		Registry registry = RegistryUtil.getRegistry();

		StringBundler sb = new StringBundler(5);

		sb.append("(&(javax.portlet.name=");
		sb.append(AssetPublisherPortletKeys.ASSET_PUBLISHER);
		sb.append(")(objectClass=");
		sb.append(ExportImportPortletPreferencesProcessor.class.getName());
		sb.append("))");

		Filter filter = registry.getFilter(sb.toString());

		_serviceTracker = registry.trackServices(filter);

		_serviceTracker.open();
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceTracker.close();
	}

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addLayout(_group.getGroupId());

		LayoutTestUtil.addPortletToLayout(
			TestPropsValues.getUserId(), _layout,
			AssetPublisherPortletKeys.ASSET_PUBLISHER, "column-1",
			new HashMap<String, String[]>());

		_portletDataContextExport =
			ExportImportTestUtil.getExportPortletDataContext(
				_group.getGroupId());

		_portletDataContextExport.setPlid(_layout.getPlid());
		_portletDataContextExport.setPortletId(
			AssetPublisherPortletKeys.ASSET_PUBLISHER);

		_portletDataContextImport =
			ExportImportTestUtil.getImportPortletDataContext(
				_group.getGroupId());

		_portletDataContextExport.setPlid(_layout.getPlid());
		_portletDataContextImport.setPortletId(
			AssetPublisherPortletKeys.ASSET_PUBLISHER);

		_exportImportPortletPreferencesProcessor = _serviceTracker.getService();

		_portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				_layout, AssetPublisherPortletKeys.ASSET_PUBLISHER);

		_portletPreferences.setValue("selectionStyle", "manual");
	}

	@Test
	public void testProcessAssetVocabularyId() throws Exception {
		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		_portletPreferences.setValue(
			"assetVocabularyId",
			String.valueOf(assetVocabulary.getVocabularyId()));

		_portletPreferences.store();

		PortletPreferences exportedPortletPreferences =
			_exportImportPortletPreferencesProcessor.
				processExportPortletPreferences(
					_portletDataContextExport, _portletPreferences);

		String exportedAssetVocabularyId = exportedPortletPreferences.getValue(
			"assetVocabularyId", "");

		Assert.assertEquals(
			assetVocabulary.getUuid() + StringPool.POUND + _group.getGroupId(),
			exportedAssetVocabularyId);

		// Update asset vocabulary to have a different primary key. We will swap
		// to the new one and verify it.

		AssetVocabularyLocalServiceUtil.deleteAssetVocabulary(
			assetVocabulary.getVocabularyId());

		assetVocabulary = AssetTestUtil.addVocabulary(_group.getGroupId());

		assetVocabulary.setUuid(
			exportedAssetVocabularyId.substring(
				0, exportedAssetVocabularyId.indexOf(CharPool.POUND)));

		AssetVocabularyLocalServiceUtil.updateAssetVocabulary(assetVocabulary);

		// Test the import

		PortletPreferences importedPortletPreferences =
			_exportImportPortletPreferencesProcessor.
				processImportPortletPreferences(
					_portletDataContextImport, exportedPortletPreferences);

		String importedVocabularyId = importedPortletPreferences.getValue(
			"assetVocabularyId", "");

		Assert.assertEquals(
			assetVocabulary.getVocabularyId(),
			GetterUtil.getLong(importedVocabularyId));
	}

	@Test
	public void testProcessQueryNames() throws Exception {
		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		AssetCategory assetCategory = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		_portletPreferences.setValue("queryName0", "assetCategories");
		_portletPreferences.setValue(
			"queryValues0", String.valueOf(assetCategory.getCategoryId()));

		_portletPreferences.store();

		PortletPreferences exportedPortletPreferences =
			_exportImportPortletPreferencesProcessor.
				processExportPortletPreferences(
					_portletDataContextExport, _portletPreferences);

		String exportedAssetCategoryId = exportedPortletPreferences.getValue(
			"queryValues0", "");

		Assert.assertEquals(
			assetCategory.getUuid() + StringPool.POUND + _group.getGroupId(),
			exportedAssetCategoryId);

		// Update asset vocabulary to have a different primary key. We will swap
		// to the new one and verify it.

		AssetCategoryLocalServiceUtil.deleteAssetCategory(
			assetCategory.getCategoryId());

		assetCategory = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		assetCategory.setUuid(
			exportedAssetCategoryId.substring(
				0, exportedAssetCategoryId.indexOf(StringPool.POUND)));

		AssetCategoryLocalServiceUtil.updateAssetCategory(assetCategory);

		// Test the import

		PortletPreferences importedPortletPreferences =
			_exportImportPortletPreferencesProcessor.
				processImportPortletPreferences(
					_portletDataContextImport, exportedPortletPreferences);

		String importedAssetCategoryId = importedPortletPreferences.getValue(
			"queryValues0", "");

		Assert.assertEquals(
			assetCategory.getCategoryId(),
			GetterUtil.getLong(importedAssetCategoryId));
	}

	private static ServiceTracker
		<ExportImportPortletPreferencesProcessor,
			ExportImportPortletPreferencesProcessor> _serviceTracker;

	private ExportImportPortletPreferencesProcessor
		_exportImportPortletPreferencesProcessor;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;
	private PortletDataContext _portletDataContextExport;
	private PortletDataContext _portletDataContextImport;
	private PortletPreferences _portletPreferences;

}