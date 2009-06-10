/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;

import java.util.List;

/**
 * <a href="AssetEntryQuery.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 *
 */
public class AssetEntryQuery {

	public AssetEntryQuery() {
		_query = DynamicQueryFactoryUtil.forClass(
			AssetEntry.class, "AssetEntry",
			PortalClassLoaderUtil.getClassLoader());
	}

	public void setCategoryIds(long[] categoryIds, boolean andOperator) {
	}

	public void setNotCategoryIds(long[] notCategoryIds, boolean andOperator) {
	}

	public void setNotTagNames(String[] notTagNames, boolean andOperator) {
	}

	public void setTagNames(String[] tagNames, boolean andOperator) {
		for (String tagName : tagNames) {
			Property property = PropertyFactoryUtil.forName("name");

			Criterion criterion = property.eq(tagName);

			_query = _query.add(criterion);
		}
	}

	public List<Object> list() throws SystemException {
		return AssetEntryLocalServiceUtil.dynamicQuery(_query);
	}

	public List<Object> list(int start, int end) throws SystemException {
		return AssetEntryLocalServiceUtil.dynamicQuery(_query, start, end);
	}

	private DynamicQuery _query;

}