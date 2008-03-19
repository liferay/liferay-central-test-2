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

package com.liferay.portal.mirage.custom;

import com.sun.portal.cms.mirage.exception.CMSException;
import com.sun.portal.cms.mirage.exception.CategoryNotEmptyException;
import com.sun.portal.cms.mirage.exception.DuplicateCategoryException;
import com.sun.portal.cms.mirage.model.core.User;
import com.sun.portal.cms.mirage.model.custom.Category;
import com.sun.portal.cms.mirage.model.custom.ContentType;
import com.sun.portal.cms.mirage.service.custom.CategoryService;

/**
 * <a href="CategoryServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class CategoryServiceImpl implements CategoryService {

	public void assignContentTypeToCategory(
		ContentType contentType, Category category) throws CMSException {
	}

	public void assignSubCategoryToCategory(
			Category subCategory, Category newParent)
		throws CMSException, DuplicateCategoryException {
	}

	public void createCategory(Category category)
		throws CMSException, DuplicateCategoryException {
	}

	public void deleteCategory(Category category)
		throws CMSException, CategoryNotEmptyException {
	}

	public Category getCategory(String selectedCategoryUUID, User user)
		throws CMSException {

		return null;
	}

	public Category getCategory(Category category, User user)
		throws CMSException {
		return null;
	}

	public void updateCategory(Category category) throws CMSException {
	}

}