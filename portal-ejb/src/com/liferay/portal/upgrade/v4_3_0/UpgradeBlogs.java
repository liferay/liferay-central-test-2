/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
package com.liferay.portal.upgrade.v4_3_0;

import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.portal.upgrade.v4_3_0.util.ResourceUtil;
import com.liferay.portlet.blogs.model.BlogsCategory;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.model.impl.BlogsCategoryImpl;
import com.liferay.portlet.blogs.model.impl.BlogsEntryImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeBlogs.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class UpgradeBlogs extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			_upgradeBlogs();
			_upgradeResource();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	private void _upgradeBlogs() throws Exception {

		// Blogs Category

		PKUpgradeColumnImpl pkUpgradeColumn = new PKUpgradeColumnImpl(0, true);

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			BlogsCategoryImpl.TABLE_NAME, BlogsCategoryImpl.TABLE_COLUMNS,
			pkUpgradeColumn);

		upgradeTable.updateTable();

		_categoryIdMapper = pkUpgradeColumn.getValueMapper();

		_categoryIdMapper.appendException(
			new Long(BlogsCategoryImpl.DEFAULT_PARENT_CATEGORY_ID));

		upgradeTable = new DefaultUpgradeTableImpl(
			BlogsCategoryImpl.TABLE_NAME, BlogsCategoryImpl.TABLE_COLUMNS,
			new SwapUpgradeColumnImpl("parentCategoryId", _categoryIdMapper));

		upgradeTable.updateTable();

		// Blogs Entry

		pkUpgradeColumn = new PKUpgradeColumnImpl(0, true);

		upgradeTable = new DefaultUpgradeTableImpl(
			BlogsEntryImpl.TABLE_NAME, BlogsEntryImpl.TABLE_COLUMNS,
			pkUpgradeColumn,
			new SwapUpgradeColumnImpl("categoryId", _categoryIdMapper));

		upgradeTable.updateTable();

		_entryIdMapper = pkUpgradeColumn.getValueMapper();
	}

	private void _upgradeResource() throws Exception {
		ResourceUtil.upgradePrimKey(
			_categoryIdMapper, BlogsCategory.class.getName());

		ResourceUtil.upgradePrimKey(
			_entryIdMapper, BlogsEntry.class.getName());
	}

	private ValueMapper _categoryIdMapper;

	private ValueMapper _entryIdMapper;

	private static Log _log = LogFactory.getLog(UpgradeBlogs.class);

}