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

package com.liferay.portal.upgrade.v5_1_0.util;

import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.blogs.util.BlogsUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * <a href="BlogsEntryUrlTitleUpgradeColumnImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class BlogsEntryUrlTitleUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public BlogsEntryUrlTitleUpgradeColumnImpl(
		UpgradeColumn entryIdColumn, UpgradeColumn titleColumn) {

		super("urlTitle");

		_entryIdColumn = entryIdColumn;
		_titleColumn = titleColumn;
		_urlTitles = new HashSet<String>();
	}

	public Object getNewValue(Object oldValue) throws Exception {
		//String oldUrlTitle = (String)oldValue;
		String oldUrlTitle = StringPool.BLANK;

		String newUrlTitle = oldUrlTitle;

		if (Validator.isNull(oldUrlTitle)) {
			long entryId = ((Long)_entryIdColumn.getOldValue()).longValue();

			String title = (String)_titleColumn.getOldValue();

			newUrlTitle = getUrlTitle(entryId, title);

			_urlTitles.add(newUrlTitle);
		}

		return newUrlTitle;
	}

	protected String getUrlTitle(long entryId, String title) {
		String urlTitle = BlogsUtil.getUrlTitle(entryId, title);

		String newUrlTitle = new String(urlTitle);

		for (int i = 1;; i++) {
			if (!_urlTitles.contains(newUrlTitle)) {
				break;
			}

			newUrlTitle = urlTitle + "_" + i;
		}

		return newUrlTitle;
	}

	private UpgradeColumn _entryIdColumn;
	private UpgradeColumn _titleColumn;
	private Set<String> _urlTitles;

}