/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.client.portlet.messageboards.service.http;

import com.liferay.client.portal.service.http.BaseSoapTest;
import com.liferay.client.portlet.messageboards.model.MBCategorySoap;
import com.liferay.client.portlet.messageboards.model.MBMessageSoap;

/**
 * <a href="MessageBoardsSoapTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MessageBoardsSoapTest extends BaseSoapTest {

	public void test() {
		try {
			String plid = "PRI.3.1";
			String parentCategoryId = "-1";
			String name = "Test Category";
			String description = "This is a test category.";

			String[] communityPermissions = new String[0];
			String[] guestPermissions = new String[0];

			// Axis 1.4 has a bug where it will try to deserialize a boolean
			// addCommunityPermissions to an array communityPermissions when the
			// method names are the same.

			//boolean addCommunityPermissions = true;
			//boolean addGuestPermissions = true;

			MBCategorySoap category = getMBCategoryService().addCategory(
				plid, parentCategoryId, name, description, communityPermissions,
				guestPermissions);

			String categoryId = category.getCategoryId();
			String subject = "Test Subject";
			String body = "This is a test body.";
			Object[] files = new Object[0];
			boolean anonymous = false;
			double priority = 0.0;

			MBMessageSoap message = getMBMessageService().addMessage(
				categoryId, subject, body, files, anonymous, priority,
				communityPermissions, guestPermissions);

			getMBMessageService().deleteMessage(message.getMessageId());

			getMBCategoryService().deleteCategory(category.getCategoryId());
		}
		catch (Exception e) {
			fail(e);
		}
	}

	protected MBCategoryServiceSoap getMBCategoryService() throws Exception {
		MBCategoryServiceSoapServiceLocator locator =
			new MBCategoryServiceSoapServiceLocator();

		MBCategoryServiceSoap service =
			locator.getPortlet_Message_Boards_MBCategoryService(
				getURL("Portlet_Message_Boards_MBCategoryService"));

		return service;
	}

	protected MBMessageServiceSoap getMBMessageService() throws Exception {
		MBMessageServiceSoapServiceLocator locator =
			new MBMessageServiceSoapServiceLocator();

		MBMessageServiceSoap service =
			locator.getPortlet_Message_Boards_MBMessageService(
				getURL("Portlet_Message_Boards_MBMessageService"));

		return service;
	}

}