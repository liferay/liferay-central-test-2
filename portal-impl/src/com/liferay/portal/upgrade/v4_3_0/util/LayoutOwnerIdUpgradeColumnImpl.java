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

package com.liferay.portal.upgrade.v4_3_0.util;

import com.liferay.portal.upgrade.StagnantRowException;
import com.liferay.portal.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.ValueMapper;

/**
 * <a href="LayoutOwnerIdUpgradeColumnImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class LayoutOwnerIdUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public LayoutOwnerIdUpgradeColumnImpl(String name,
										  TempUpgradeColumnImpl upgradeColumn,
										  ValueMapper valueMapper) {

		super(name);

		_name = name;
		_upgradeColumn = upgradeColumn;
		_valueMapper = valueMapper;
	}

	public Object getNewValue(Object oldValue) throws Exception {
		String ownerId = (String)_upgradeColumn.getTemp();

		if (_name.equals("groupId")) {
			if (ownerId.startsWith("PUB.") || ownerId.startsWith("PRI.")) {
				return new Long(ownerId.substring(4, ownerId.length()));
			}
			else {
				throw new StagnantRowException(ownerId);
			}
		}
		else {
			if (ownerId.startsWith("PUB.")) {
				return Boolean.FALSE;
			}
			else if (ownerId.startsWith("PRI.")) {
				return Boolean.TRUE;
			}
			else {
				throw new StagnantRowException(ownerId);
			}
		}
	}

	private String _name;
	private TempUpgradeColumnImpl _upgradeColumn;
	private ValueMapper _valueMapper;

}