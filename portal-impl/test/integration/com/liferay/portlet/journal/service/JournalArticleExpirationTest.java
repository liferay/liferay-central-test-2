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

package com.liferay.portlet.journal.service;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestUtil;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMTemplateTestUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.service.persistence.JournalArticlePersistence;
import com.liferay.portlet.journal.service.persistence.JournalArticleUtil;
import com.liferay.portlet.journal.util.test.JournalTestUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Zsolt Oláh
 */
@Sync
public class JournalArticleExpirationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testExpireDraftArticle()
			throws Exception {
		testExpireArticle(false, _NO_CHANGE);
	}
	
	@Test
	public void testExpireApprovedArticle()
			throws Exception {
		testExpireArticle(true, _NO_CHANGE);
	}

	@Test
	public void testExpireDraftArticlePostponedExpiring()
			throws Exception {
		testExpireArticle(false, _POSTPONE_EXPIRE);
	}

	@Test
	public void testExpireApprovedArticlePostponedExpiring()
			throws Exception {
		testExpireArticle(true, _POSTPONE_EXPIRE);
	}

	protected JournalArticle addArticle(
			long groupId, boolean approved)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<>();

		titleMap.put(LocaleUtil.getDefault(), RandomTestUtil.randomString());

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(
			LocaleUtil.getDefault(), RandomTestUtil.randomString());

		String content = DDMStructureTestUtil.getSampleStructuredContent();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			groupId, JournalArticle.class.getName());

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			groupId, ddmStructure.getStructureId());

		Calendar displayDateCalendar = new GregorianCalendar();
		displayDateCalendar.setTime(new Date());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		if (approved) {
			serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
		}
		else {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}

		Calendar expirationDateCalendar = getExpirationCalendar(
				Time.MINUTE, 1);

		return JournalArticleLocalServiceUtil.addArticle(
			TestPropsValues.getUserId(), groupId,
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, 0, StringPool.BLANK,
			true, JournalArticleConstants.VERSION_DEFAULT, titleMap,
			descriptionMap, content, ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey(), null,
			displayDateCalendar.get(Calendar.MONTH),
			displayDateCalendar.get(Calendar.DAY_OF_MONTH),
			displayDateCalendar.get(Calendar.YEAR),
			displayDateCalendar.get(Calendar.HOUR_OF_DAY),
			displayDateCalendar.get(Calendar.MINUTE),
			expirationDateCalendar.get(Calendar.MONTH),
			expirationDateCalendar.get(Calendar.DAY_OF_MONTH),
			expirationDateCalendar.get(Calendar.YEAR),
			expirationDateCalendar.get(Calendar.HOUR_OF_DAY),
			expirationDateCalendar.get(Calendar.MINUTE), false, 0, 0, 0, 0, 0,
			true, true, false, null, null, null, null, serviceContext);
	}

	protected Calendar getExpirationCalendar(long timeUnit, int timeValue) {
		Calendar calendar = new GregorianCalendar();

		calendar.setTime(new Date(new Date().getTime() + timeUnit * timeValue));

		return calendar;
	}

	protected void testExpireArticle(boolean approved,
			int mode)
		throws Exception {

		// Add Expiring, Approved Article

		JournalArticle article = addArticle(
			_group.getGroupId(), approved);

		Date originalExpirationDate = article.getExpirationDate();

		// Add a version of the article, changing expire date

		article = updateArticle(article, mode);
		
		// Wait until the original article expire date passes
		
		long waitTime = originalExpirationDate.getTime() - new Date().getTime();

		Thread.sleep(waitTime);

		// Simulate automatic expiration

		JournalArticleLocalServiceUtil.checkArticles();

		article = JournalArticleLocalServiceUtil.getArticle(article.getId());

		if (approved) {
			if (mode == _POSTPONE_EXPIRE) {
				Assert.assertFalse(article.isExpired());
			}
			else {
				Assert.assertTrue(article.isExpired());
			}
		}
		else {
			Assert.assertFalse(article.isExpired());
		}
	}

	protected JournalArticle updateArticle(
			JournalArticle article, int mode)
		throws Exception {

		if (mode == _POSTPONE_EXPIRE) {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(article.getGroupId());

			serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

			Calendar displayDateCalendar = new GregorianCalendar();
			displayDateCalendar.setTime(article.getDisplayDate());

			Calendar expirationDateCalendar = getExpirationCalendar(
					Time.YEAR, 1);

			return JournalArticleLocalServiceUtil.updateArticle(
				TestPropsValues.getUserId(), article.getGroupId(),
				article.getFolderId(), article.getArticleId(),
				article.getVersion(),
				article.getTitleMap(), article.getDescriptionMap(),
				article.getContent(), article.getDDMStructureKey(),
				article.getDDMTemplateKey(), article.getLayoutUuid(),
				displayDateCalendar.get(Calendar.MONTH),
				displayDateCalendar.get(Calendar.DAY_OF_MONTH),
				displayDateCalendar.get(Calendar.YEAR),
				displayDateCalendar.get(Calendar.HOUR_OF_DAY),
				displayDateCalendar.get(Calendar.MINUTE),
				expirationDateCalendar.get(Calendar.MONTH),
				expirationDateCalendar.get(Calendar.DAY_OF_MONTH),
				expirationDateCalendar.get(Calendar.YEAR),
				expirationDateCalendar.get(Calendar.HOUR_OF_DAY),
				expirationDateCalendar.get(Calendar.MINUTE), true,
				0, 0, 0, 0, 0, true,
				article.getIndexable(), article.isSmallImage(),
				article.getSmallImageURL(), null, null, null, serviceContext);
		}
		else {
			return article;
		}
	}

	private static final int _NO_CHANGE = 0;

	private static final int _POSTPONE_EXPIRE = 1;

	@DeleteAfterTestRun
	private Group _group;

}