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

package com.liferay.portal.upgrade.v4_4_0.util;

import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.util.FileUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * <a href="DLFileEntryTitleColumnImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class DLFileEntryTitleColumnImpl extends BaseUpgradeColumnImpl {

	public DLFileEntryTitleColumnImpl(
			TempUpgradeColumnImpl nameColumn,
			TempUpgradeColumnImpl folderIdColumn) {

		super("title", null);

		_nameColumn = nameColumn;
		_folderIdColumn = folderIdColumn;
	}

	public Object getNewValue(Object oldValue) throws Exception {
		String newTitle = (String)oldValue;
		String name = (String)_nameColumn.getOldValue();
		String extension = FileUtil.getExtension(name);

		newTitle = DLFileEntryImpl.stripExtension(name, newTitle);

		while (_distinctTitles.contains(_getTriplet(newTitle, extension))) {
			_counter++;

			newTitle = newTitle + StringPool.SPACE + _counter;
		}

		_distinctTitles.add(_getTriplet(newTitle, extension));

		return newTitle;
	}

	private String _getTriplet(String title, String extension) {
		StringMaker sm = new StringMaker();

		sm.append(_folderIdColumn.getOldValue());
		sm.append(StringPool.UNDERLINE);
		sm.append(title);
		sm.append(StringPool.UNDERLINE);
		sm.append(extension);

		return sm.toString();
	}

	private TempUpgradeColumnImpl _folderIdColumn;
	private TempUpgradeColumnImpl _nameColumn;
	private int _counter = 0;
	private Set _distinctTitles = new HashSet();

}