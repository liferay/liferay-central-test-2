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
 * <a href="CompositePrimKeyMapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class CompositePrimKeyMapper implements ValueMapper {

	public CompositePrimKeyMapper(String key, ValueMapper valueMapper) {
		this(new KeyValueMapperPair(key, valueMapper));
	}

	public CompositePrimKeyMapper(KeyValueMapperPair kvm) {
		this(new KeyValueMapperPair[] { kvm });
	}

	public CompositePrimKeyMapper(KeyValueMapperPair[] kvms) {
		_kvms = kvms;
	}

	public void appendException(Object exception) {
	}

	public Object getNewValue(Object oldValue) throws Exception {
		String primKey = (String)oldValue;

		// {layoutId=1234, ownerId=PRI.5678}

		if (primKey.startsWith(StringPool.OPEN_CURLY_BRACE) &&
			primKey.endsWith(StringPool.CLOSE_CURLY_BRACE)) {

			String[] parts = StringUtil.split(
				primKey.substring(1, primKey.length() - 1),
				StringPool.COMMA + StringPool.SPACE);

			for (int i = 0; i < parts.length; i++) {
				String[] kvp = StringUtil.split(parts[i], StringPool.EQUAL);

				for (int j = 0; j < _kvms.length; j++) {
					if (kvp[0].equals(_kvms[j].getKey())) {
						Object value = null;

						if (_kvms[j].isLong()) {
							value = new Long(kvp[1]);
						}
						else {
							value = (String)kvp[1];
						}

						ValueMapper vm = _kvms[j].getValueMapper();

						kvp[1] = String.valueOf(vm.getNewValue(value));

						parts[i] =
							StringUtil.merge(kvp, StringPool.EQUAL);
					}
				}
			}

			String partsString = StringUtil.merge(
				parts, StringPool.COMMA + StringPool.SPACE);

			primKey =
				StringPool.OPEN_CURLY_BRACE + partsString +
					StringPool.CLOSE_CURLY_BRACE;
		}

		return primKey;
	}

	public void mapValue(Object oldValue, Object newValue) throws Exception {
	}

	public int size() throws Exception {
		return 0;
	}

	private KeyValueMapperPair[] _kvms;

}