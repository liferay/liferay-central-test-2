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
import com.liferay.portal.upgrade.StagnantRowException;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.portal.util.PortletKeys;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;

/**
 * <a href="OwnerIdMapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class OwnerIdMapper implements ValueMapper {

	public OwnerIdMapper(ValueMapper groupIdMapper) {
		_groupIdMapper = groupIdMapper;
	}

	public Object getNewValue(Object oldValue) throws Exception {
		String ownerId = (String)oldValue;

		try {
			if (ownerId.startsWith(PortletKeys.PREFS_OWNER_ID_GROUP)) {
				int index = PortletKeys.PREFS_OWNER_ID_GROUP.length() + 1;

				String s = ownerId.substring(index);

				if (Validator.isNumber(s)) {
					Long groupId =
						(Long)_groupIdMapper.getNewValue(new Long(s));

					ownerId =
						PortletKeys.PREFS_OWNER_ID_GROUP + StringPool.PERIOD +
							groupId;
				}
			}
			else if (ownerId.startsWith(LayoutImpl.PUBLIC) ||
					 ownerId.startsWith(LayoutImpl.PRIVATE)) {

				String[] parts = StringUtil.split(ownerId, StringPool.PERIOD);

				Long groupId = new Long(parts[1]);

				if (groupId.longValue() <= 0) {
					throw new UpgradeException(
						"Owner id " + ownerId + " is invalid");
				}

				groupId = (Long)_groupIdMapper.getNewValue(groupId);

				parts[1] = String.valueOf(groupId);

				ownerId = StringUtil.merge(parts, StringPool.PERIOD);
			}
		}
		catch (StagnantRowException sre) {
			throw new StagnantRowException(ownerId, sre);
		}

		return ownerId;
	}

	public void mapValue(Object oldValue, Object newValue) throws Exception {
	}

	public void appendException(Object exception) {
	}

	private ValueMapper _groupIdMapper;

}