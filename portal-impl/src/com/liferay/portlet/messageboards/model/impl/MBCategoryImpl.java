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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="MBCategoryImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class MBCategoryImpl extends MBCategoryModelImpl implements MBCategory {

	public MBCategoryImpl() {
	}

	public List<Long> getAncestorCategoryIds()
		throws PortalException, SystemException {

		List<Long> ancestorCategoryIds = new ArrayList<Long>();

		MBCategory category = this;

		while (true) {
			if (!category.isRoot()) {
				category = MBCategoryLocalServiceUtil.getCategory(
					category.getParentCategoryId());

				ancestorCategoryIds.add(category.getCategoryId());
			}
			else {
				break;
			}
		}

		return ancestorCategoryIds;
	}

	public List<MBCategory> getAncestors()
		throws PortalException, SystemException {

		List<MBCategory> ancestors = new ArrayList<MBCategory>();

		MBCategory category = this;

		while (true) {
			if (!category.isRoot()) {
				category = MBCategoryLocalServiceUtil.getCategory(
					category.getParentCategoryId());

				ancestors.add(category);
			}
			else {
				break;
			}
		}

		return ancestors;
	}

	public boolean isRoot() {
		if (getParentCategoryId() ==
				MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			return true;
		}
		else {
			return false;
		}
	}

}