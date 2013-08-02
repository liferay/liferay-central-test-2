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

package com.liferay.portalweb.portlet.wikidisplay.comment.editwdfrontpagecomment;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.wiki.portlet.addportletwiki.AddPageWikiTest;
import com.liferay.portalweb.portlet.wiki.portlet.addportletwiki.AddPortletWikiTest;
import com.liferay.portalweb.portlet.wiki.wikinode.addwikinode.TearDownWikiNodeTest;
import com.liferay.portalweb.portlet.wiki.wikipage.addwikifrontpage.AddWikiFrontPageTest;
import com.liferay.portalweb.portlet.wikidisplay.comment.addwdfrontpagecomment.AddWDFrontPageCommentTest;
import com.liferay.portalweb.portlet.wikidisplay.portlet.addportletwd.AddPageWDTest;
import com.liferay.portalweb.portlet.wikidisplay.portlet.addportletwd.AddPortletWDTest;
import com.liferay.portalweb.portlet.wikidisplay.wikinode.selectmainnode.SelectMainNodeTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class EditWDFrontPageCommentTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageWikiTest.class);
		testSuite.addTestSuite(AddPortletWikiTest.class);
		testSuite.addTestSuite(AddPageWDTest.class);
		testSuite.addTestSuite(AddPortletWDTest.class);
		testSuite.addTestSuite(AddWikiFrontPageTest.class);
		testSuite.addTestSuite(SelectMainNodeTest.class);
		testSuite.addTestSuite(AddWDFrontPageCommentTest.class);
		testSuite.addTestSuite(EditWDFrontPageCommentTest.class);
		testSuite.addTestSuite(ViewEditWDFrontPageCommentTest.class);
		testSuite.addTestSuite(TearDownWikiNodeTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}