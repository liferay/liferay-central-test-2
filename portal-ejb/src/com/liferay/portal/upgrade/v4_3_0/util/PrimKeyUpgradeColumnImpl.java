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
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.util.StringUtil;

/**
 * <a href="PrimKeyUpgradeColumnImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class PrimKeyUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public PrimKeyUpgradeColumnImpl(TempScopeUpgradeColumnImpl upgradeColumn,
									ValueMapper groupIdMapper,
									ValueMapper ownerIdMapper) {

		super("primKey");

		_upgradeColumn = upgradeColumn;
		_groupIdMapper = groupIdMapper;
		_ownerIdMapper = ownerIdMapper;
	}

	public Object getNewValue(Object oldValue) throws Exception {
		Object newValue = oldValue;

		if (_upgradeColumn.isScopeGroup()) {
			newValue = _groupIdMapper.getNewValue(new Long((String)oldValue));
		}
		else if (_upgradeColumn.isScopeIndividual()) {
			String primKey = (String)oldValue;

			if (primKey.startsWith(LayoutImpl.PUBLIC) ||
				primKey.startsWith(LayoutImpl.PRIVATE)) {

				// PRI.1234.1_LAYOUT_56

				String[] keyParts =
					StringUtil.split(primKey, StringPool.PERIOD);

				Long groupId =
					(Long)_groupIdMapper.getNewValue(new Long(keyParts[1]));

				keyParts[1] = String.valueOf(groupId);

				primKey = StringUtil.merge(keyParts, StringPool.PERIOD);
			}
			else if (primKey.indexOf("groupId=") != -1 ||
					 primKey.indexOf("ownerId=") != -1) {

				// {layoutId=1234, ownerId=PRI.5678}

				String[] keyParts = StringUtil.split(
					primKey.substring(1, primKey.length() - 1),
					StringPool.COMMA + StringPool.SPACE);

				for (int i = 0; i < keyParts.length; i++) {
					String[] kvp =
						StringUtil.split(keyParts[i], StringPool.EQUAL);

					if (kvp[0].equals("groupId")) {
						kvp[1] =
							String.valueOf(
								_groupIdMapper.getNewValue(new Long(kvp[1])));

						keyParts[i] =
							StringUtil.merge(kvp, StringPool.EQUAL);
					}
					else if (kvp[0].equals("ownerId")) {
						kvp[1] = (String)_ownerIdMapper.getNewValue(kvp[1]);

						keyParts[i] =
							StringUtil.merge(kvp, StringPool.EQUAL);
					}
				}

				primKey =
					StringPool.OPEN_CURLY_BRACE +
					StringUtil.merge(
						keyParts, StringPool.COMMA + StringPool.SPACE) +
					StringPool.CLOSE_CURLY_BRACE;
			}
		}

		return newValue;
	}

	private TempScopeUpgradeColumnImpl _upgradeColumn;
	private ValueMapper _groupIdMapper;
	private ValueMapper _ownerIdMapper;

}