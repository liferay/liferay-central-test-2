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

import com.liferay.portal.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.TempUpgradeColumnImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="DLFolderNameColumnImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class DLFolderNameColumnImpl extends BaseUpgradeColumnImpl {

	public DLFolderNameColumnImpl(
		TempUpgradeColumnImpl groupIdColumn,
		TempUpgradeColumnImpl parentFolderIdColumn) {

		super("name", null);

		_groupIdColumn = groupIdColumn;
		_parentFolderIdColumn = parentFolderIdColumn;
	}

	public Object getNewValue(Object oldValue) throws Exception {
		String newName = (String)oldValue;

		while (_distinctNames.contains(_getTriplet(newName))) {
			_counter++;

			newName = newName + " " + _counter;
		}

		_distinctNames.add(_getTriplet(newName));

		return newName;
	}

	private String _getTriplet(String name) {
		return _groupIdColumn.getOldValue() +
			"_" + _parentFolderIdColumn.getOldValue() + "_" + name;
	}

	private int _counter = 0;
	private List _distinctNames = new ArrayList();
	private TempUpgradeColumnImpl _groupIdColumn;
	private TempUpgradeColumnImpl _parentFolderIdColumn;

}