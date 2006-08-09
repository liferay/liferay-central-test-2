/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.client.portal.model.LayoutModel;
import com.liferay.client.portal.model.LayoutReference;
import com.liferay.client.portal.service.http.LayoutServiceSoap;
import com.liferay.client.portal.service.http.LayoutServiceSoapServiceLocator;
import com.liferay.client.portlet.messageboards.model.MBCategoryModel;
import com.liferay.client.portlet.messageboards.model.MBMessageModel;
import com.liferay.client.portlet.messageboards.service.http.MBCategoryServiceSoap;
import com.liferay.client.portlet.messageboards.service.http.MBCategoryServiceSoapServiceLocator;
import com.liferay.client.portlet.messageboards.service.http.MBMessageServiceSoap;
import com.liferay.client.portlet.messageboards.service.http.MBMessageServiceSoapServiceLocator;

import java.net.URL;

import java.util.Locale;

/**
 * <a href="Test.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class Test {

    public static void main(String[] args) {
		try {
			Test test = new Test();

			test.portalLayout();
			test.portletMessageBoardsCategory();
			test.portletMessageBoardsMessage();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public URL getURL(String serviceName) throws Exception {

		// Unathenticated url

		String url = "http://localhost:8080/tunnel-web/axis/" + serviceName;

		// Authenticated url

		if (true) {
			String userId = "liferay.com.1";
			String password = "qUqP5cyxm6YcTAhz05Hph5gvu9M=";
			
			url =
				"http://" + userId + ":" + password +
					"@localhost:8080/tunnel-web/secure/axis/" + serviceName;
		}

		return new URL(url);
	}

	public void portalLayout() throws Exception {
		LayoutServiceSoapServiceLocator locator =
			new LayoutServiceSoapServiceLocator();

		LayoutServiceSoap soap = locator.getPortal_LayoutService(
			getURL("Portal_LayoutService"));

		LayoutReference[] layoutReferences = soap.getLayoutReferences(
			"liferay.com", "56", "article-id", "PRODUCTS-LICENSING");

		for (int i = 0; i < layoutReferences.length; i++) {
			LayoutModel layoutModel = layoutReferences[i].getLayoutModel();
			String portletId = layoutReferences[i].getPortletId();

			String layoutName = soap.getLayoutName(
				layoutModel.getLayoutId(), layoutModel.getOwnerId(),
				Locale.getDefault().toString());

			System.out.println("Layout id " + layoutModel.getLayoutId());
			System.out.println("Layout name " + layoutName);
			System.out.println("Portlet id " + portletId);
		}
	}

	public void portletMessageBoardsCategory() throws Exception {
		MBCategoryServiceSoapServiceLocator locator =
			new MBCategoryServiceSoapServiceLocator();

		MBCategoryServiceSoap soap =
			locator.getPortlet_Message_Boards_MBCategoryService(
				getURL("Portlet_Message_Boards_MBCategoryService"));

		MBCategoryModel category = soap.addCategory(
			"PRI.3.1", "-1", "Test Category", "This is a test category.", true,
			true);

		//soap.deleteCategory(category.getCategoryId());
	}

	public void portletMessageBoardsMessage() throws Exception {
		MBMessageServiceSoapServiceLocator locator =
			new MBMessageServiceSoapServiceLocator();

		MBMessageServiceSoap soap =
			locator.getPortlet_Message_Boards_MBMessageService(
				getURL("Portlet_Message_Boards_MBMessageService"));

		MBMessageModel message = soap.addMessage(
			"1", "Test Subject", "This is a test body.", new Object[0], false,
			true, true);

		//soap.deleteMessage(message.getMessageId());
	}

}