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

package com.liferay.journal.asset;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.asset.search.test.BaseAssetSearchTestCase;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestUtil;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMTemplateTestUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalFolderConstants;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
@Sync
public class JournalArticleAssetSearchTest extends BaseAssetSearchTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		_testMode = PortalRunMode.isTestMode();

		PortalRunMode.setTestMode(true);

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		PortalRunMode.setTestMode(_testMode);
	}

	@Override
	protected BaseModel<?> addBaseModel(
			BaseModel<?> parentBaseModel, Map<Locale, String> titleMap,
			ServiceContext serviceContext)
		throws Exception {

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		return JournalTestUtil.addArticle(
			serviceContext.getScopeGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, titleMap, null,
			titleMap, LocaleUtil.getSiteDefault(), false, false,
			serviceContext);
	}

	@Override
	protected BaseModel<?> addBaseModel(
			BaseModel<?> parentBaseModel, String keywords, Date expirationDate,
			ServiceContext serviceContext)
		throws Exception {

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		return JournalTestUtil.addArticle(
			serviceContext.getScopeGroupId(), keywords, "Content",
			expirationDate, serviceContext);
	}

	@Override
	protected BaseModel<?> addBaseModel(
			BaseModel<?> parentBaseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		return JournalTestUtil.addArticle(
			serviceContext.getScopeGroupId(), keywords, "Content",
			serviceContext);
	}

	@Override
	protected BaseModel<?> addBaseModelWithClassType(
			BaseModel<?> parentBaseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		if (_ddmStructure == null) {
			_ddmStructure = DDMStructureTestUtil.addStructure(
				serviceContext.getScopeGroupId(),
				JournalArticle.class.getName());
		}

		if (_ddmTemplate == null) {
			_ddmTemplate = DDMTemplateTestUtil.addTemplate(
				serviceContext.getScopeGroupId(),
				_ddmStructure.getStructureId());
		}

		String content = DDMStructureTestUtil.getSampleStructuredContent();

		return JournalTestUtil.addArticleWithXMLContent(
			serviceContext.getScopeGroupId(), content,
			_ddmStructure.getStructureKey(), _ddmTemplate.getTemplateKey());
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return JournalArticle.class;
	}

	@Override
	protected long[] getClassTypeIds() {
		if (_ddmStructure == null) {
			return null;
		}

		return new long[] {_ddmStructure.getStructureId()};
	}

	@Override
	protected String getSearchKeywords() {
		return "title";
	}

	protected DDMStructure _ddmStructure;
	protected DDMTemplate _ddmTemplate;

	private boolean _testMode;

}