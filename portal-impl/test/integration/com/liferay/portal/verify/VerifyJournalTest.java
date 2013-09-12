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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalTestUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class VerifyJournalTest extends BaseVerifyTestCase {

	@Test
	@Transactional
	public void testTreePathWithJournalArticleInTrash() throws Exception {
		Group group = GroupTestUtil.addGroup();

		JournalFolder parentFolder = JournalTestUtil.addFolder(
			group.getGroupId(), "parentFolder");

		JournalArticle article = JournalTestUtil.addArticle(
			group.getGroupId(), parentFolder.getFolderId(), "title", "content");

		JournalArticleLocalServiceUtil.moveArticleToTrash(
			TestPropsValues.getUserId(), group.getGroupId(),
			article.getArticleId());

		JournalFolderLocalServiceUtil.deleteFolder(
			parentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	@Transactional
	public void testTreePathWithJournalFolderInTrash() throws Exception {
		Group group = GroupTestUtil.addGroup();

		JournalFolder parentFolder = JournalTestUtil.addFolder(
			group.getGroupId(), "parentFolder");

		JournalFolder childFolder = JournalTestUtil.addFolder(
			group.getGroupId(), parentFolder.getFolderId(), "childFolder");

		JournalFolderLocalServiceUtil.moveFolderToTrash(
			TestPropsValues.getUserId(), childFolder.getFolderId());

		JournalFolderLocalServiceUtil.deleteFolder(
			parentFolder.getFolderId(), false);

		doVerify();
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new VerifyJournal();
	}

}