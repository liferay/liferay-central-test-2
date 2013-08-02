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

import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * @author Julio Camarero
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@PrepareForTest({PortletLocalServiceUtil.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class AssetPublisherExportImportTest extends BaseExportImportTestCase {

	@Before
	public void setUp() throws Exception {
		_group = ServiceTestUtil.addGroup();

		_layout = ServiceTestUtil.addLayout(
			_group.getGroupId(), ServiceTestUtil.randomString());

		// Delete and readd to ensure a different layout ID (not ID or UUID).
		// See LPS-32132.

		LayoutLocalServiceUtil.deleteLayout(
			_layout, true, new ServiceContext());

		_layout = ServiceTestUtil.addLayout(
			_group.getGroupId(), ServiceTestUtil.randomString());
	}

	@Test
	public void testDefaultScopeId() throws Exception {
		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		Company company = CompanyLocalServiceUtil.getCompany(
			_layout.getCompanyId());

		Group companyGroup = company.getGroup();

		preferenceMap.put(
			"defaultScope",
			new String[] {"GroupId_" + companyGroup.getGroupId()});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			_layout, preferenceMap);

		Assert.assertEquals(
			"GroupId_" + companyGroup.getGroupId(),
			portletPreferences.getValue("defaultScope", null));
		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));
		Assert.assertEquals(
			null, portletPreferences.getValue("scopeIds", null));
	}

	@Test
	public void testGlobalScopeId() throws Exception {
		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		preferenceMap.put(
			"defaultScope", new String[] {Boolean.TRUE.toString()});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			_layout, preferenceMap);

		Assert.assertEquals(
			Boolean.TRUE.toString(),
			portletPreferences.getValue("defaultScope", null));
		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));
		Assert.assertEquals(
			null, portletPreferences.getValue("scopeIds", null));
	}

	@Test
	public void testLayoutScopeId() throws Exception {
		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		addGroup(TestPropsValues.getUserId(), _layout);

		preferenceMap.put(
			"defaultScope", new String[] {"LayoutUuid_" + _layout.getUuid()});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			_layout, preferenceMap);

		Assert.assertEquals(
			"LayoutUuid_" + _importedLayout.getUuid(),
			portletPreferences.getValue("defaultScope", null));
		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));
		Assert.assertEquals(
			null, portletPreferences.getValue("scopeIds", null));
	}

	@Test
	public void testLegacyLayoutScopeId() throws Exception {
		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		addGroup(TestPropsValues.getUserId(), _layout);

		preferenceMap.put(
			"defaultScope", new String[] {"Layout_" + _layout.getLayoutId()});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			_layout, preferenceMap);

		Assert.assertEquals(
			"LayoutUuid_" + _importedLayout.getUuid(),
			portletPreferences.getValue("defaultScope", null));
		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));
		Assert.assertEquals(
			null, portletPreferences.getValue("scopeIds", null));
	}

	@Test
	public void testSeveralLayoutScopeIds() throws Exception {
		Company company = CompanyLocalServiceUtil.getCompany(
			_layout.getCompanyId());

		Layout secondLayout = ServiceTestUtil.addLayout(
			_group.getGroupId(), ServiceTestUtil.randomString());

		addGroup(TestPropsValues.getUserId(), secondLayout);

		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		addGroup(TestPropsValues.getUserId(), _layout);

		preferenceMap.put(
			"defaultScope", new String[] {Boolean.FALSE.toString()});

		Group companyGroup = company.getGroup();

		preferenceMap.put(
			"scopeIds",
			new String[] {
				"GroupId_" + companyGroup.getGroupId(),
				"LayoutUuid_" + _layout.getUuid(),
				"LayoutUuid_" + secondLayout.getUuid()
			});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			_layout, preferenceMap);

		Layout importedSecondLayout =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				secondLayout.getUuid(), _importedGroup.getGroupId(),
				_importedLayout.isPrivateLayout());

		Assert.assertEquals(
			Boolean.FALSE.toString(),
			portletPreferences.getValue("defaultScope", null));
		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));
		Assert.assertEquals(
			"GroupId_" + companyGroup.getGroupId() + ",LayoutUuid_" +
				_importedLayout.getUuid() + ",LayoutUuid_" +
					importedSecondLayout.getUuid(),
			StringUtil.merge(portletPreferences.getValues("scopeIds", null)));
	}

	@Test
	public void testSeveralLegacyLayoutScopeIds() throws Exception {
		Layout secondLayout = ServiceTestUtil.addLayout(
			_group.getGroupId(), ServiceTestUtil.randomString());

		addGroup(TestPropsValues.getUserId(), secondLayout);

		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		addGroup(TestPropsValues.getUserId(), _layout);

		preferenceMap.put(
			"defaultScope", new String[] {Boolean.FALSE.toString()});
		preferenceMap.put(
			"scopeIds",
			new String[] {
				"Layout_" + _layout.getLayoutId(),
				"Layout_" + secondLayout.getLayoutId()
			});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			_layout, preferenceMap);

		Layout importedSecondLayout =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				secondLayout.getUuid(), _importedGroup.getGroupId(),
				_importedLayout.isPrivateLayout());

		Assert.assertEquals(
			Boolean.FALSE.toString(),
			portletPreferences.getValue("defaultScope", null));
		Assert.assertEquals(null, portletPreferences.getValue("scopeId", null));
		Assert.assertEquals(
			"LayoutUuid_" + _importedLayout.getUuid() + ",LayoutUuid_" +
				importedSecondLayout.getUuid(),
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

	protected String addAssetPublisherPortletToLayout(
			long userId, Layout layout, String columnId,
			Map<String, String[]> preferenceMap)
		throws Exception {

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		String assetPublisherPortletId = layoutTypePortlet.addPortletId(
			userId, PortletKeys.ASSET_PUBLISHER, columnId, -1);

		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());

		PortletPreferences portletPreferences = getPortletPreferences(
			layout.getCompanyId(), layout.getPlid(), assetPublisherPortletId);

		for (String key : preferenceMap.keySet()) {
			portletPreferences.setValues(key, preferenceMap.get(key));
		}

		updatePortletPreferences(
			layout.getPlid(), assetPublisherPortletId, portletPreferences);

		return assetPublisherPortletId;
	}

	protected Group addGroup(long userId, Layout layout) throws Exception {
		Group scopeGroup = layout.getScopeGroup();

		if (scopeGroup != null) {
			return scopeGroup;
		}

		return GroupLocalServiceUtil.addGroup(
			userId, Layout.class.getName(), layout.getPlid(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID,
			String.valueOf(layout.getPlid()), null, 0, null, false, true, null);
	}

	protected PortletPreferences getImportedPortletPreferences(
			Layout layout, Map<String, String[]> preferenceMap)
		throws Exception {

		// Export site LAR

		String assetPublisherPortletId = addAssetPublisherPortletToLayout(
			TestPropsValues.getUserId(), _layout, "column-1", preferenceMap);

		Map<String, String[]> parameterMap =  new HashMap<String, String[]>();

		parameterMap.put(
			PortletDataHandlerKeys.CATEGORIES,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP,
			new String[] {Boolean.TRUE.toString()});

		File file = LayoutLocalServiceUtil.exportLayoutsAsFile(
			layout.getGroupId(), layout.isPrivateLayout(), null, parameterMap,
			null, null);

		_importedGroup = ServiceTestUtil.addGroup();

		// Import site LAR

		LayoutLocalServiceUtil.importLayouts(
			TestPropsValues.getUserId(), _importedGroup.getGroupId(),
			layout.isPrivateLayout(), parameterMap, file);

		_importedLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
			layout.getUuid(), _importedGroup.getGroupId(),
			layout.isPrivateLayout());

		Assert.assertNotNull(_importedLayout);

		return getPortletPreferences(
			_importedLayout.getCompanyId(), _importedLayout.getPlid(),
			assetPublisherPortletId);
	}

	protected PortletPreferences getPortletPreferences(
			long companyId, long plid, String portletId)
		throws Exception {

		return PortletPreferencesLocalServiceUtil.getPreferences(
			companyId, PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portletId);
	}

	protected void testSortByAssetVocabulary(boolean globalVocabulary)
		throws Exception {

		long groupId = _group.getGroupId();

		if (globalVocabulary) {
			Company company = CompanyLocalServiceUtil.getCompany(
				_layout.getCompanyId());

			Group companyGroup = company.getGroup();

			groupId = companyGroup.getGroupId();
		}

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), ServiceTestUtil.randomString(),
				ServiceTestUtil.getServiceContext(groupId));

		Map<String, String[]> preferenceMap = new HashMap<String, String[]>();

		preferenceMap.put(
			"assetVocabularyId",
			new String[] {String.valueOf(assetVocabulary.getVocabularyId())});

		PortletPreferences portletPreferences = getImportedPortletPreferences(
			_layout, preferenceMap);

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
			expectedGroupId = _importedGroup.getGroupId();
		}

		Assert.assertEquals(
			"Vocabulary " + importedAssetVocabularyId +
				" does not belong to group " + expectedGroupId,
			expectedGroupId, importedVocabulary.getGroupId());

		AssetVocabularyLocalServiceUtil.deleteAssetVocabulary(assetVocabulary);
	}

	protected void updatePortletPreferences(
			long plid, String portletId, PortletPreferences portletPreferences)
		throws Exception {

		PortletPreferencesLocalServiceUtil.updatePreferences(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portletId,
			portletPreferences);
	}

	private Group _group;
	private Group _importedGroup;
	private Layout _importedLayout;
	private Layout _layout;

}