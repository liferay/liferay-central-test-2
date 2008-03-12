/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
package com.liferay.portlet.messageboards.service;

import com.liferay.portal.kernel.bean.BeanLocatorUtil;
import com.liferay.portal.service.BaseServiceTestCase;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.impl.MBCategoryImpl;

import java.util.List;

import org.springframework.context.ApplicationContext;

/**
 * <a href="MBMessageServiceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class MBMessageServiceTest extends BaseServiceTestCase {

	public void testConcurrentWrites() throws Exception {
		int numOfThreads = 50;
		long categoryId = _category.getCategoryId();

		MBMessageWorkerThread[] threads =
			new MBMessageWorkerThread[numOfThreads];

		for (int i = 0; i < threads.length; i++) {
			String subject = "MBMessage Thread " + (i + 1);

			threads[i] = new MBMessageWorkerThread(categoryId, subject);
		}

		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}

		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException ie) {
			}
		}

		int successCount = 0;

		for (int i = 0; i < threads.length; i++) {
			if (threads[i].getSuccess()) {
				successCount++;
			}
		}

		assertTrue(
			"Only " + successCount + " out of " + numOfThreads +
				" threads added messages successfully.",
			successCount == numOfThreads);
	}

	protected void setUp() throws Exception {
		super.setUp();

		// Make sure to fully load all the beans in spring

		ApplicationContext context = SpringUtil.getContext();

		String[] beanDefinitionNames = context.getBeanDefinitionNames();

		for (String beanDefinitionName: beanDefinitionNames) {
			BeanLocatorUtil.locate(beanDefinitionName, false);
		}

		// Clean out any pre-existing test category

		String name = "Test Category";
		String description = "This is a test category.";

		long groupId = PortalUtil.getPortletGroupId(
			TestPropsValues.LAYOUT_PLID);

		int count = MBCategoryServiceUtil.getCategoriesCount(
			groupId, MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID);

		List<MBCategory> list = MBCategoryServiceUtil.getCategories(
			groupId, MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID, 0, count);

		for (MBCategory category: list) {
			if (category.getName().equals(name)) {
				MBCategoryServiceUtil.deleteCategory(category.getCategoryId());

				break;
			}
		}

		// Add a new test category

		_category = MBCategoryServiceUtil.addCategory(
			TestPropsValues.LAYOUT_PLID,
			MBCategoryImpl.DEFAULT_PARENT_CATEGORY_ID, name, description,
			null, null);
	}

	protected void tearDown() throws Exception {
		if (_category != null) {
			MBCategoryServiceUtil.deleteCategory(_category.getCategoryId());
		}

		super.tearDown();
	}

	private MBCategory _category;
}