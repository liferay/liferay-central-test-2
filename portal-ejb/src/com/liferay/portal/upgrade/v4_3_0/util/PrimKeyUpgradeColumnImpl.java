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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="PrimKeyUpgradeColumnImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class PrimKeyUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public PrimKeyUpgradeColumnImpl(TempScopeUpgradeColumnImpl scopeColumn,
									List keyMapperList) {

		super("primKey");

		_scopeColumn = scopeColumn;
		_keyMapperList = keyMapperList;
	}

	public Object getNewValue(Object oldValue) throws Exception {
		String primKey = (String)oldValue;

		// {layoutId=1234, ownerId=PRI.5678}

		if (_scopeColumn.isScopeIndividual() &&
			primKey.startsWith(StringPool.OPEN_CURLY_BRACE) &&
			primKey.endsWith(StringPool.CLOSE_CURLY_BRACE)) {

			String[] parts = StringUtil.split(
				primKey.substring(1, primKey.length() - 1),
				StringPool.COMMA + StringPool.SPACE);

			for (int i = 0; i < parts.length; i++) {
				String[] kvp = StringUtil.split(parts[i], StringPool.EQUAL);

				Iterator itr = _keyMapperList.iterator();

				while (itr.hasNext()) {
					Object[] keyMapper = (Object[])itr.next();

					if (kvp[0].equals(keyMapper[0])) {
						ValueMapper vm = (ValueMapper)keyMapper[1];

						Object value = null;

						if (Validator.isNumber(kvp[1])) {
							value = new Long(kvp[1]);
						}
						else {
							value = (String)kvp[1];
						}

						kvp[1] = String.valueOf(vm.getNewValue(value));

						parts[i] =
							StringUtil.merge(kvp, StringPool.EQUAL);
					}
				}
			}

			primKey =
				StringPool.OPEN_CURLY_BRACE +
				StringUtil.merge(parts, StringPool.COMMA + StringPool.SPACE) +
				StringPool.CLOSE_CURLY_BRACE;
		}

		return primKey;
	}

	protected TempScopeUpgradeColumnImpl getScopeColumn() {
		return _scopeColumn;
	}

	private TempScopeUpgradeColumnImpl _scopeColumn;
	private List _keyMapperList;

}