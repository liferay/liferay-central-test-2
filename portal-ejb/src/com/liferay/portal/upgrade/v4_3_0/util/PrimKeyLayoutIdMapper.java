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
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.util.StringUtil;

/**
 * <a href="PrimKeyLayoutIdMapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class PrimKeyLayoutIdMapper implements ValueMapper {

	public PrimKeyLayoutIdMapper(ValueMapper groupIdMapper) {
		_groupIdMapper = groupIdMapper;
	}

	public void appendException(Object exception) {
	}

	public Object getNewValue(Object oldValue) throws Exception {
		String primKey = (String)oldValue;

		// PRI.1234.1_LAYOUT_56

		String[] keyParts =
			StringUtil.split(primKey, StringPool.PERIOD);

		Long groupId =
			(Long)_groupIdMapper.getNewValue(new Long(keyParts[1]));

		keyParts[1] = String.valueOf(groupId);

		return StringUtil.merge(keyParts, StringPool.PERIOD);
	}

	public void mapValue(Object oldValue, Object newValue) throws Exception {
	}

	private ValueMapper _groupIdMapper;

}