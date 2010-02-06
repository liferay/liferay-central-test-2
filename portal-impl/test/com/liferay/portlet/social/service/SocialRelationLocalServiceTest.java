/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.social.service;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.BaseServiceTestCase;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.comparator.UserScreenNameComparator;
import com.liferay.portlet.social.model.SocialRelationConstants;
import com.liferay.portlet.social.service.SocialRelationLocalServiceUtil;

import java.util.List;

/**
 * <a href="SocialRelationLocalServiceTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class SocialRelationLocalServiceTest extends BaseServiceTestCase {

	public void testAddRelationWithBiType() throws Exception {
		User dlc1User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "dlc1");

		User dlc2User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "dlc2");

		User dlc3User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "dlc3");

		User dlc4User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "dlc4");

		User dlc5User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "dlc5");

		User dlc6User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "dlc6");

		User dlc7User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "dlc7");

		User dlc8User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "dlc8");

		User dlc9User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "dlc9");

		// Friend

		SocialRelationLocalServiceUtil.addRelation(
			dlc1User.getUserId(), dlc2User.getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND);

		SocialRelationLocalServiceUtil.addRelation(
			dlc1User.getUserId(), dlc3User.getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND);

		SocialRelationLocalServiceUtil.addRelation(
			dlc1User.getUserId(), dlc4User.getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND);

		SocialRelationLocalServiceUtil.addRelation(
			dlc1User.getUserId(), dlc5User.getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND);

		SocialRelationLocalServiceUtil.addRelation(
			dlc1User.getUserId(), dlc6User.getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND);

		SocialRelationLocalServiceUtil.addRelation(
			dlc1User.getUserId(), dlc7User.getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND);

		SocialRelationLocalServiceUtil.addRelation(
			dlc1User.getUserId(), dlc8User.getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND);

		// Friend

		SocialRelationLocalServiceUtil.addRelation(
			dlc2User.getUserId(), dlc3User.getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND);

		SocialRelationLocalServiceUtil.addRelation(
			dlc2User.getUserId(), dlc4User.getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND);

		SocialRelationLocalServiceUtil.addRelation(
			dlc2User.getUserId(), dlc5User.getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND);

		// Coworker

		SocialRelationLocalServiceUtil.addRelation(
			dlc1User.getUserId(), dlc9User.getUserId(),
			SocialRelationConstants.TYPE_BI_COWORKER);

		SocialRelationLocalServiceUtil.addRelation(
			dlc2User.getUserId(), dlc9User.getUserId(),
			SocialRelationConstants.TYPE_BI_COWORKER);

		// Romantic partner

		SocialRelationLocalServiceUtil.addRelation(
			dlc1User.getUserId(), dlc2User.getUserId(),
			SocialRelationConstants.TYPE_BI_ROMANTIC_PARTNER);
	}

	public void testAddRelationWithUniType() throws Exception {
		User fra1User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "fra1");

		User fra2User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "fra2");

		User fra3User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "fra3");

		User fra4User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "fra4");

		User fra5User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "fra5");

		User fra6User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "fra6");

		User fra7User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "fra7");

		User fra8User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "fra8");

		User fra9User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "fra9");

		// Parent

		SocialRelationLocalServiceUtil.addRelation(
			fra1User.getUserId(), fra2User.getUserId(),
			SocialRelationConstants.TYPE_UNI_PARENT);

		SocialRelationLocalServiceUtil.addRelation(
			fra1User.getUserId(), fra3User.getUserId(),
			SocialRelationConstants.TYPE_UNI_PARENT);

		SocialRelationLocalServiceUtil.addRelation(
			fra1User.getUserId(), fra4User.getUserId(),
			SocialRelationConstants.TYPE_UNI_PARENT);

		SocialRelationLocalServiceUtil.addRelation(
			fra1User.getUserId(), fra5User.getUserId(),
			SocialRelationConstants.TYPE_UNI_PARENT);

		SocialRelationLocalServiceUtil.addRelation(
			fra1User.getUserId(), fra6User.getUserId(),
			SocialRelationConstants.TYPE_UNI_PARENT);

		SocialRelationLocalServiceUtil.addRelation(
			fra1User.getUserId(), fra7User.getUserId(),
			SocialRelationConstants.TYPE_UNI_PARENT);

		SocialRelationLocalServiceUtil.addRelation(
			fra1User.getUserId(), fra8User.getUserId(),
			SocialRelationConstants.TYPE_UNI_PARENT);

		SocialRelationLocalServiceUtil.addRelation(
			fra1User.getUserId(), fra9User.getUserId(),
			SocialRelationConstants.TYPE_UNI_PARENT);

		// Child

		SocialRelationLocalServiceUtil.addRelation(
			fra3User.getUserId(), fra1User.getUserId(),
			SocialRelationConstants.TYPE_UNI_CHILD);

		SocialRelationLocalServiceUtil.addRelation(
			fra3User.getUserId(), fra2User.getUserId(),
			SocialRelationConstants.TYPE_UNI_CHILD);

		SocialRelationLocalServiceUtil.addRelation(
			fra4User.getUserId(), fra1User.getUserId(),
			SocialRelationConstants.TYPE_UNI_CHILD);

		SocialRelationLocalServiceUtil.addRelation(
			fra4User.getUserId(), fra2User.getUserId(),
			SocialRelationConstants.TYPE_UNI_CHILD);

		SocialRelationLocalServiceUtil.addRelation(
			fra5User.getUserId(), fra1User.getUserId(),
			SocialRelationConstants.TYPE_UNI_CHILD);
	}

	public void testGetMutualRelations() throws Exception {
		User dlc1User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "dlc1");

		User dlc2User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "dlc2");

		// Do dlc1 and dlc2 have 4 mutual relations?

		List<User> users = UserLocalServiceUtil.getSocialUsers(
			dlc1User.getUserId(), dlc2User.getUserId(), QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new UserScreenNameComparator(true));

		assertEquals(4, users.size());
	}

	public void testGetMutualRelationsByBiType() throws Exception {
		User dlc1User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "dlc1");

		User dlc2User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "dlc2");

		// Do dlc1 and dlc2 have 3 mutual friends?

		List<User> users = UserLocalServiceUtil.getSocialUsers(
			dlc1User.getUserId(), dlc2User.getUserId(),
			SocialRelationConstants.TYPE_BI_FRIEND, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new UserScreenNameComparator(true));

		assertEquals(3, users.size());

		assertEquals("dlc3", users.get(0).getScreenName());
		assertEquals("dlc4", users.get(1).getScreenName());
		assertEquals("dlc5", users.get(2).getScreenName());
	}

	public void testGetMutualRelationsByUniType() throws Exception {
		User fra3User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "fra3");

		User fra4User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "fra4");

		User fra5User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "fra5");

		// Are fra3 and fra4 both children of fra1 and fra2?

		List<User> users = UserLocalServiceUtil.getSocialUsers(
			fra3User.getUserId(), fra4User.getUserId(),
			SocialRelationConstants.TYPE_UNI_CHILD, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new UserScreenNameComparator(true));

		assertEquals(2, users.size());

		assertEquals("fra1", users.get(0).getScreenName());
		assertEquals("fra2", users.get(1).getScreenName());

		// Are fra3 and fra5 both children of fra1?

		users = UserLocalServiceUtil.getSocialUsers(
			fra3User.getUserId(), fra5User.getUserId(),
			SocialRelationConstants.TYPE_UNI_CHILD, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new UserScreenNameComparator(true));

		assertEquals(1, users.size());

		assertEquals("fra1", users.get(0).getScreenName());
	}

	public void testGetRelations() throws Exception {
		User dlc1User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "dlc1");

		// Does dlc1 have 8 relations?

		List<User> users = UserLocalServiceUtil.getSocialUsers(
			dlc1User.getUserId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new UserScreenNameComparator(true));

		assertEquals(8, users.size());

		assertEquals("dlc2", users.get(0).getScreenName());
		assertEquals("dlc3", users.get(1).getScreenName());
		assertEquals("dlc4", users.get(2).getScreenName());
		assertEquals("dlc5", users.get(3).getScreenName());
		assertEquals("dlc6", users.get(4).getScreenName());
		assertEquals("dlc7", users.get(5).getScreenName());
		assertEquals("dlc8", users.get(6).getScreenName());
		assertEquals("dlc9", users.get(7).getScreenName());
	}

	public void testGetRelationsByBiType() throws Exception {
		User dlc1User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "dlc1");

		User dlc2User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "dlc2");

		User dlc3User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "dlc3");

		// Does dlc1 have 7 friends?

		List<User> users = UserLocalServiceUtil.getSocialUsers(
			dlc1User.getUserId(), SocialRelationConstants.TYPE_BI_FRIEND,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new UserScreenNameComparator(true));

		assertEquals(7, users.size());

		assertEquals("dlc2", users.get(0).getScreenName());
		assertEquals("dlc3", users.get(1).getScreenName());
		assertEquals("dlc4", users.get(2).getScreenName());
		assertEquals("dlc5", users.get(3).getScreenName());
		assertEquals("dlc6", users.get(4).getScreenName());
		assertEquals("dlc7", users.get(5).getScreenName());
		assertEquals("dlc8", users.get(6).getScreenName());

		// Is dlc1 a coworker of dlc9?

		users = UserLocalServiceUtil.getSocialUsers(
			dlc1User.getUserId(), SocialRelationConstants.TYPE_BI_COWORKER,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new UserScreenNameComparator(true));

		assertEquals(1, users.size());

		assertEquals("dlc9", users.get(0).getScreenName());

		// Is dlc1 romantically involved with dlc2?

		users = UserLocalServiceUtil.getSocialUsers(
			dlc1User.getUserId(),
			SocialRelationConstants.TYPE_BI_ROMANTIC_PARTNER, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new UserScreenNameComparator(true));

		assertEquals(1, users.size());

		assertEquals("dlc2", users.get(0).getScreenName());

		// Is dlc2 romantically involved with dlc1?

		users = UserLocalServiceUtil.getSocialUsers(
			dlc2User.getUserId(),
			SocialRelationConstants.TYPE_BI_ROMANTIC_PARTNER, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new UserScreenNameComparator(true));

		assertEquals(1, users.size());

		assertEquals("dlc1", users.get(0).getScreenName());

		// Is dlc3 romantically involved with anyone?

		users = UserLocalServiceUtil.getSocialUsers(
			dlc3User.getUserId(),
			SocialRelationConstants.TYPE_BI_ROMANTIC_PARTNER, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, new UserScreenNameComparator(true));

		assertEquals(0, users.size());
	}

	public void testGetRelationsByUniType() throws Exception {
		User fra1User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "fra1");

		User fra2User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "fra2");

		User fra3User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "fra3");

		User fra6User = UserLocalServiceUtil.getUserByScreenName(
			TestPropsValues.COMPANY_ID, "fra6");

		// Is fra1 a parent to 8 children?

		List<User> users = UserLocalServiceUtil.getSocialUsers(
			fra1User.getUserId(), SocialRelationConstants.TYPE_UNI_PARENT,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new UserScreenNameComparator(true));

		assertEquals(8, users.size());

		assertEquals("fra2", users.get(0).getScreenName());
		assertEquals("fra3", users.get(1).getScreenName());
		assertEquals("fra4", users.get(2).getScreenName());
		assertEquals("fra5", users.get(3).getScreenName());
		assertEquals("fra6", users.get(4).getScreenName());
		assertEquals("fra7", users.get(5).getScreenName());
		assertEquals("fra8", users.get(6).getScreenName());
		assertEquals("fra9", users.get(7).getScreenName());

		// Is fra2 a parent of anyone?

		users = UserLocalServiceUtil.getSocialUsers(
			fra2User.getUserId(), SocialRelationConstants.TYPE_UNI_PARENT,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new UserScreenNameComparator(true));

		assertEquals(0, users.size());

		// Is fra3 a child of anyone?

		users = UserLocalServiceUtil.getSocialUsers(
			fra3User.getUserId(), SocialRelationConstants.TYPE_UNI_CHILD,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new UserScreenNameComparator(true));

		assertEquals(2, users.size());

		assertEquals("fra1", users.get(0).getScreenName());
		assertEquals("fra2", users.get(1).getScreenName());

		// Is fra6 a child of fra1?

		users = UserLocalServiceUtil.getSocialUsers(
			fra6User.getUserId(), SocialRelationConstants.TYPE_UNI_CHILD,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new UserScreenNameComparator(true));

		assertEquals(0, users.size());
	}

}