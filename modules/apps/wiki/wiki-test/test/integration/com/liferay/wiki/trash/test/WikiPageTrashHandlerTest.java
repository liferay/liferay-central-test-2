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

package com.liferay.wiki.trash.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.trash.test.BaseTrashHandlerTestCase;
import com.liferay.portlet.trash.test.DefaultWhenIsIndexableBaseModel;
import com.liferay.portlet.trash.test.WhenCanBeDuplicatedInTrash;
import com.liferay.portlet.trash.test.WhenHasParent;
import com.liferay.portlet.trash.test.WhenIsAssetableBaseModel;
import com.liferay.portlet.trash.test.WhenIsIndexableBaseModel;
import com.liferay.portlet.trash.test.WhenIsUpdatableBaseModel;
import com.liferay.wiki.asset.WikiPageAssetRenderer;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.util.test.WikiPageTrashHandlerTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
@Sync
public class WikiPageTrashHandlerTest
	extends BaseTrashHandlerTestCase
	implements WhenCanBeDuplicatedInTrash, WhenHasParent,
			   WhenIsAssetableBaseModel, WhenIsIndexableBaseModel,
			   WhenIsUpdatableBaseModel {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	public String getParentBaseModelClassName() {
		Class<WikiNode> wikiNodeClass = WikiNode.class;

		return wikiNodeClass.getName();
	}

	@Override
	public String getSearchKeywords() {
		return WikiPageTrashHandlerTestUtil.getSearchKeywords();
	}

	@Override
	public void moveParentBaseModelToTrash(long primaryKey) throws Exception {
		WikiPageTrashHandlerTestUtil.moveParentBaseModelToTrash(primaryKey);
	}

	@Override
	public int searchBaseModelsCount(Class<?> clazz, long groupId)
		throws Exception {

		return _whenIsIndexableBaseModel.searchBaseModelsCount(clazz, groupId);
	}

	@Override
	public int searchTrashEntriesCount(
			String keywords, ServiceContext serviceContext)
		throws Exception {

		return _whenIsIndexableBaseModel.searchTrashEntriesCount(
			keywords, serviceContext);
	}

	@Before
	@Override
	public void setUp() throws Exception {
		_whenIsIndexableBaseModel = new DefaultWhenIsIndexableBaseModel();

		_testMode = PortalRunMode.isTestMode();

		PortalRunMode.setTestMode(true);

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		PortalRunMode.setTestMode(_testMode);
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndDeleteWithDraftStatus() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndDeleteWithDraftStatusIndexable() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndDeleteWithDraftStatusIsNotFound() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndRestoreWithDraftStatus() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndRestoreWithDraftStatusIndexable() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndRestoreWithDraftStatusIsNotVisible()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndRestoreWithDraftStatusRestoreStatus()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashAndRestoreWithDraftStatusRestoreUniqueTitle()
		throws Exception {
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
	public void testTrashParentWithBaseModelIsInTrashContainer()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashParentWithBaseModelIsIsNotVisible() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionParentBaseModel() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionParentBaseModelAndRestore() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionParentBaseModelAndRestoreIsNotInTrashContainer()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionParentBaseModelAndRestoreIsVisible()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionParentBaseModelIndexable() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testTrashVersionParentBaseModelIsNotVisible() throws Exception {
	}

	@Override
	public BaseModel<?> updateBaseModel(
			long primaryKey, ServiceContext serviceContext)
		throws Exception {

		return WikiPageTrashHandlerTestUtil.updateBaseModel(
			primaryKey, serviceContext);
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		return WikiPageTrashHandlerTestUtil.addBaseModelWithWorkflow(
			parentBaseModel, approved, serviceContext);
	}

	@Override
	protected Long getAssetClassPK(ClassedModel classedModel) {
		return WikiPageAssetRenderer.getClassPK((WikiPage)classedModel);
	}

	@Override
	protected BaseModel<?> getBaseModel(long primaryKey) throws Exception {
		return WikiPageTrashHandlerTestUtil.getBaseModel(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return WikiPageTrashHandlerTestUtil.getBaseModelClass();
	}

	@Override
	protected String getBaseModelName(ClassedModel classedModel) {
		return WikiPageTrashHandlerTestUtil.getBaseModelName(classedModel);
	}

	@Override
	protected int getNotInTrashBaseModelsCount(BaseModel<?> parentBaseModel)
		throws Exception {

		return WikiPageTrashHandlerTestUtil.getNotInTrashBaseModelsCount(
			parentBaseModel);
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return WikiPageTrashHandlerTestUtil.getParentBaseModel(
			group, serviceContext);
	}

	@Override
	protected long getTrashEntryClassPK(ClassedModel classedModel) {
		return WikiPageTrashHandlerTestUtil.getTrashEntryClassPK(classedModel);
	}

	@Override
	protected String getUniqueTitle(BaseModel<?> baseModel) {
		return WikiPageTrashHandlerTestUtil.getUniqueTitle(baseModel);
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		WikiPageTrashHandlerTestUtil.moveBaseModelToTrash(primaryKey);
	}

	private boolean _testMode;
	private WhenIsIndexableBaseModel _whenIsIndexableBaseModel;

}