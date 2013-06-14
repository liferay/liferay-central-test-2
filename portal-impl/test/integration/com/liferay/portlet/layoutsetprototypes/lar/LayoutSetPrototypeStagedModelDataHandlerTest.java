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

package com.liferay.portlet.layoutsetprototypes.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.lar.BaseStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutFriendlyURL;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.LayoutFriendlyURLLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.LayoutTestUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Daniela Zapata Riesco
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class LayoutSetPrototypeStagedModelDataHandlerTest
	extends BaseStagedModelDataHandlerTestCase {

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		LayoutSetPrototype layoutSetPrototype =
			LayoutTestUtil.addLayoutSetPrototype(
				ServiceTestUtil.randomString());

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			layoutSetPrototype.getGroupId(), true,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		Assert.assertEquals(1, layouts.size());

		Layout layout = layouts.get(0);

		_addLayout(LayoutSetPrototype.class, layout);
		_addLayoutFriendlyURLs(LayoutSetPrototype.class, layout.getPlid());

		LayoutPrototype layoutPrototype = _addLayoutPrototype(
			dependentStagedModelsMap);

		Layout layoutPrototypeBasedLayout = LayoutTestUtil.addLayout(
			layoutSetPrototype.getGroupId(), ServiceTestUtil.randomString(),
			true, layoutPrototype, true);

		_addLayout(LayoutSetPrototype.class, layoutPrototypeBasedLayout);
		_addLayoutFriendlyURLs(
			LayoutSetPrototype.class, layoutPrototypeBasedLayout.getPlid());

		return layoutSetPrototype;
	}

	@Override
	protected void deleteStagedModel(
			StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		LayoutSetPrototypeLocalServiceUtil.deleteLayoutSetPrototype(
			(LayoutSetPrototype)stagedModel);

		List<StagedModel> dependentStagedModels = dependentStagedModelsMap.get(
			LayoutPrototype.class.getSimpleName());

		LayoutPrototype layoutPrototype =
			(LayoutPrototype)dependentStagedModels.get(0);

		LayoutPrototypeLocalServiceUtil.deleteLayoutPrototype(layoutPrototype);
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return LayoutSetPrototypeLocalServiceUtil.
				fetchLayoutSetPrototypeByUuidAndCompanyId(
					uuid, group.getCompanyId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return LayoutSetPrototype.class;
	}

	@Override
	protected void validateImport(
			StagedModel stagedModel,
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws Exception {

		LayoutSetPrototype importedLayoutSetPrototype =
			(LayoutSetPrototype)getStagedModel(stagedModel.getUuid(), group);

		Assert.assertNotNull(importedLayoutSetPrototype);

		LayoutPrototype importedLayoutPrototype = _getImportedLayoutPrototype(
			dependentStagedModelsMap, group);

		Layout importedLayout = _importLayoutFromLAR(stagedModel);

		_validateLayouts(
			importedLayoutSetPrototype, importedLayoutPrototype,
			importedLayout);
	}

	private void _addLayout(Class<?> clazz, Layout layout)
		throws SystemException {

		List<Layout> layouts = _layouts.get(clazz.getSimpleName());

		if (layouts == null) {
			layouts = new ArrayList<Layout>();

			_layouts.put(clazz.getSimpleName(), layouts);
		}

		layouts.add(layout);

		UnicodeProperties typeSettings = layout.getTypeSettingsProperties();

		typeSettings.setProperty(
			LayoutSetPrototypeStagedModelDataHandlerTest.class.getName(),
			Boolean.TRUE.toString());

		LayoutLocalServiceUtil.updateLayout(layout);
	}

	private void _addLayoutFriendlyURLs(Class<?> clazz, long plid)
		throws SystemException {

		List<LayoutFriendlyURL> layoutFriendlyURLs = _layoutFriendlyURLs.get(
			clazz.getSimpleName());

		if (layoutFriendlyURLs == null) {
			layoutFriendlyURLs = new ArrayList<LayoutFriendlyURL>();

			_layoutFriendlyURLs.put(clazz.getSimpleName(), layoutFriendlyURLs);
		}

		List<LayoutFriendlyURL> layoutLayoutFriendlyURLs =
			LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLs(plid);

		Assert.assertEquals(1, layoutLayoutFriendlyURLs.size());

		layoutFriendlyURLs.add(layoutLayoutFriendlyURLs.get(0));
	}

	private LayoutPrototype _addLayoutPrototype(
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		LayoutPrototype layoutPrototype = LayoutTestUtil.addLayoutPrototype(
			ServiceTestUtil.randomString());

		addDependentStagedModel(
			dependentStagedModelsMap, LayoutPrototype.class, layoutPrototype);

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			layoutPrototype.getGroupId(), true,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		Assert.assertEquals(1, layouts.size());

		Layout layout = layouts.get(0);

		addDependentStagedModel(dependentStagedModelsMap, Layout.class, layout);

		_addLayout(LayoutPrototype.class, layout);

		List<LayoutFriendlyURL> layoutFriendlyURLs =
			LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLs(
				layout.getPlid());

		Assert.assertEquals(1, layoutFriendlyURLs.size());

		addDependentStagedModel(
			dependentStagedModelsMap, LayoutFriendlyURL.class,
			layoutFriendlyURLs.get(0));

		_addLayoutFriendlyURLs(LayoutPrototype.class, layout.getPlid());

		return layoutPrototype;
	}

	private LayoutPrototype _getImportedLayoutPrototype(
			Map<String, List<StagedModel>> dependentStagedModelsMap,
			Group group)
		throws SystemException {

		List<StagedModel> dependentLayoutPrototypeStagedModels =
			dependentStagedModelsMap.get(LayoutPrototype.class.getSimpleName());

		Assert.assertEquals(1, dependentLayoutPrototypeStagedModels.size());

		LayoutPrototype layoutPrototype =
			(LayoutPrototype)dependentLayoutPrototypeStagedModels.get(0);

		LayoutPrototype importedLayoutPrototype =
			LayoutPrototypeLocalServiceUtil.
				fetchLayoutPrototypeByUuidAndCompanyId(
					layoutPrototype.getUuid(), group.getCompanyId());

		Assert.assertNotNull(importedLayoutPrototype);

		return importedLayoutPrototype;
	}

	private List<LayoutFriendlyURL> _getLayoutFriendlyURLs(Class<?> clazz) {
		return _layoutFriendlyURLs.get(clazz.getSimpleName());
	}

	private List<Layout> _getLayouts(Class<?> clazz) {
		return _layouts.get(clazz.getSimpleName());
	}

	private Layout _importLayoutFromLAR(StagedModel stagedModel)
		throws DocumentException, IOException {

		LayoutSetPrototypeStagedModelDataHandler
			layoutSetPrototypeStagedModelDataHandler =
				new LayoutSetPrototypeStagedModelDataHandler();

		String fileName =
			layoutSetPrototypeStagedModelDataHandler.
				getLayoutSetPrototypeLARFileName(
					(LayoutSetPrototype)stagedModel);

		String modelPath = ExportImportPathUtil.getModelPath(
			stagedModel, fileName);

		InputStream inputStream = portletDataContext.getZipEntryAsInputStream(
			modelPath);

		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(inputStream);

		Document document = SAXReaderUtil.read(
			zipReader.getEntryAsString("manifest.xml"));

		Element rootElement = document.getRootElement();

		Element layoutElement = rootElement.element("Layout");

		List<Element> elements = layoutElement.elements();

		List<Layout> importedLayouts = new ArrayList<Layout>(elements.size());

		for (Element element : elements) {
			String layoutPrototypeUuid = element.attributeValue(
				"layout-prototype-uuid");

			if (Validator.isNotNull(layoutPrototypeUuid)) {
				String path = element.attributeValue("path");

				Layout layout = (Layout)portletDataContext.fromXML(
					zipReader.getEntryAsString(path));

				importedLayouts.add(layout);
			}
		}

		Assert.assertEquals(1, importedLayouts.size());

		return importedLayouts.get(0);
	}

	private void _validateLayouts(
			LayoutSetPrototype importedLayoutSetPrototype,
			LayoutPrototype importedLayoutPrototype,
			Layout layoutSetPrototypeLayout)
		throws PortalException, SystemException {

		_validatePrototypedLayouts(
			LayoutSetPrototype.class, importedLayoutSetPrototype.getGroupId());
		_validatePrototypedLayouts(
			LayoutPrototype.class, importedLayoutPrototype.getGroupId());

		Assert.assertNotNull(layoutSetPrototypeLayout.getLayoutPrototypeUuid());

		Layout importedLayout =
			LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				layoutSetPrototypeLayout.getUuid(),
				importedLayoutSetPrototype.getGroupId(), true);

		Assert.assertNotNull(importedLayout);
		Assert.assertEquals(
			importedLayoutSetPrototype.getGroupId(),
			importedLayout.getGroupId());
		Assert.assertEquals(
			importedLayoutPrototype.getUuid(),
			importedLayout.getLayoutPrototypeUuid());
	}

	private void _validatePrototypedLayouts(Class<?> clazz, long groupId)
		throws SystemException {

		List<Layout> layouts = _getLayouts(clazz);

		for (Layout layout : layouts) {
			Layout importedLayout =
				LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
					layout.getUuid(), groupId, layout.getPrivateLayout());

			Assert.assertNotNull(importedLayout);
			Assert.assertEquals(
				layout.getTypeSettingsProperty(
					LayoutSetPrototypeStagedModelDataHandlerTest.class.
						getName()),
				importedLayout.getTypeSettingsProperty(
					LayoutSetPrototypeStagedModelDataHandlerTest.class.
						getName()));
		}

		List<LayoutFriendlyURL> layoutFriendlyURLs = _getLayoutFriendlyURLs(
			clazz);

		for (LayoutFriendlyURL layoutFriendlyURL : layoutFriendlyURLs) {
			LayoutFriendlyURL importedLayoutFriendlyURL =
				LayoutFriendlyURLLocalServiceUtil.
					fetchLayoutFriendlyURLByUuidAndGroupId(
						layoutFriendlyURL.getUuid(), groupId);

			Assert.assertNotNull(importedLayoutFriendlyURL);
			Assert.assertEquals(
				layoutFriendlyURL.getFriendlyURL(),
				importedLayoutFriendlyURL.getFriendlyURL());
		}
	}

	private Map<String, List<LayoutFriendlyURL>> _layoutFriendlyURLs =
		new HashMap<String, List<LayoutFriendlyURL>>();
	private Map<String, List<Layout>> _layouts =
		new HashMap<String, List<Layout>>();

}