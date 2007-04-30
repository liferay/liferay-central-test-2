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
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.service.ResourceCodeLocalServiceUtil;
import com.liferay.portal.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.util.StringUtil;

/**
 * <a href="PrimKeyUpgradeColumnImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class PrimKeyUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public PrimKeyUpgradeColumnImpl(TempUpgradeColumnImpl upgradeColumn,
									ValueMapper groupIdMapper,
									ValueMapper ownerIdMapper) {

		super("primKey");

		_upgradeColumn = upgradeColumn;
		_groupIdMapper = groupIdMapper;
		_ownerIdMapper = ownerIdMapper;

		_compositeMapper = new CompositePrimKeyMapper(new KeyValueMapperPair[] {
				new KeyValueMapperPair("groupId", _groupIdMapper, true),
				new KeyValueMapperPair("ownerId", _ownerIdMapper, false)
			});
	}

	public Object getNewValue(Object oldValue) throws Exception {
		Object newValue = oldValue;

		Long codeId = (Long)_upgradeColumn.getTemp();

		ResourceCode resourceCode =
			ResourceCodeLocalServiceUtil.getResourceCode(codeId.longValue());

		int scope = resourceCode.getScope();

		if (scope == ResourceImpl.SCOPE_GROUP) {
			newValue = _groupIdMapper.getNewValue(new Long((String)oldValue));
		}
		else if (scope == ResourceImpl.SCOPE_INDIVIDUAL) {
			String primKey = (String)oldValue;

			if (primKey.startsWith(LayoutImpl.PUBLIC) ||
				primKey.startsWith(LayoutImpl.PRIVATE)) {

				// PRI.1234.1_LAYOUT_56

				String[] keyParts =
					StringUtil.split(primKey, StringPool.PERIOD);

				Long groupId =
					(Long)_groupIdMapper.getNewValue(new Long(keyParts[1]));

				keyParts[1] = String.valueOf(groupId);

				newValue = StringUtil.merge(keyParts, StringPool.PERIOD);
			}
			else {
				newValue = _compositeMapper.getNewValue(oldValue);
			}
		}

		return newValue;
	}

	private TempUpgradeColumnImpl _upgradeColumn;
	private ValueMapper _groupIdMapper;
	private ValueMapper _ownerIdMapper;
	private CompositePrimKeyMapper _compositeMapper;

}