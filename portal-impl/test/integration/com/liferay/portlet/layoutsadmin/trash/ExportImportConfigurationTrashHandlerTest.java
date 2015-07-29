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

package com.liferay.portlet.layoutsadmin.trash;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.exportimport.configuration.ExportImportConfigurationConstants;
import com.liferay.portlet.exportimport.model.ExportImportConfiguration;
import com.liferay.portlet.exportimport.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portlet.layoutsadmin.util.test.ExportImportConfigurationTestUtil;
import com.liferay.portlet.trash.test.BaseTrashHandlerTestCase;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Levente Hudak
 */
@Sync
public class ExportImportConfigurationTrashHandlerTest
	extends BaseTrashHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Ignore
	@Override
	@Test
	public void testTrashBaseModelAndParent() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashBaseModelAndParentAndDeleteGroupTrashEntries()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashBaseModelAndParentAndDeleteParent() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashBaseModelAndParentAndDeleteParentNoMoveableFromTrash()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashBaseModelAndParentAndRestoreModel() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashBaseModelAndParentAndRestoreModelIsVisible()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashBaseModelAndParentIsInContainerBaseModel()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashBaseModelAndParentIsNotVisible() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashBaseModelIsInTrashContainer() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashDuplicate() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashGrandparentBaseModel() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashGrandparentBaseModelAndRestoreParentModel()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashGrandparentBaseModelAndRestoreParentModelIsNotInTrashContainer()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashGrandparentBaseModelAndRestoreParentModelIsVisible()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashGrandparentBaseModelIsNotVisible() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashParent() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashParentAndDeleteGroupTrashEntries() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashParentAndDeleteParent() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashParentAndRestoreBaseModelIsVisible() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashParentAndRestoreIndexable() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashParentIndexable() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashParentIsNotVisible() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashParentWithBaseModelIsInTrashContainer()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashParentWithBaseModelIsIsNotVisible() throws Exception {
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		Group group = (Group)parentBaseModel;

		return ExportImportConfigurationTestUtil.addExportImportConfiguration(
			group.getGroupId(),
			ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT);
	}

	@Override
	protected BaseModel<?> getBaseModel(long primaryKey) throws Exception {
		return ExportImportConfigurationLocalServiceUtil.
			getExportImportConfiguration(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return ExportImportConfiguration.class;
	}

	@Override
	protected int getNotInTrashBaseModelsCount(BaseModel<?> parentBaseModel)
		throws Exception {

		return ExportImportConfigurationLocalServiceUtil.
			getExportImportConfigurationsCount(
				(Long)parentBaseModel.getPrimaryKeyObj());
	}

	@Override
	protected String getUniqueTitle(BaseModel<?> baseModel) {
		return null;
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		ExportImportConfigurationLocalServiceUtil.
			moveExportImportConfigurationToTrash(
				TestPropsValues.getUserId(), primaryKey);
	}

}