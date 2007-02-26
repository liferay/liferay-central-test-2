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

package com.liferay.portal.upgrade.util;

import com.liferay.counter.model.Counter;
import com.liferay.counter.service.CounterLocalServiceUtil;

/**
 * <a href="PKUpgradeColumnImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 *
 */
public class PKUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public PKUpgradeColumnImpl() {
		this(0, false);
	}

	public PKUpgradeColumnImpl(String name, boolean trackValues) {
		this(name, null, null, trackValues);
	}

	public PKUpgradeColumnImpl(int pos, boolean trackValues) {
		this(pos, null, null, trackValues);
	}

	public PKUpgradeColumnImpl(String name, Integer oldColumnType,
							   Integer newColumnType, boolean trackValues) {

		super(name);

		_oldColumnType = oldColumnType;
		_newColumnType = newColumnType;
		_trackValues = trackValues;

		if (_trackValues) {
			_valueMapper = new MemoryValueMapper();
		}
	}

	public PKUpgradeColumnImpl(int pos, Integer oldColumnType,
							   Integer newColumnType, boolean trackValues) {

		super(pos);

		_oldColumnType = oldColumnType;
		_newColumnType = newColumnType;
		_trackValues = trackValues;

		if (_trackValues) {
			_valueMapper = new MemoryValueMapper();
		}
	}

	public Integer getOldColumnType(Integer defaultType) {
		if (_oldColumnType == null) {
			return defaultType;
		}
		else {
			return _oldColumnType;
		}
	}

	public Integer getNewColumnType(Integer defaultType) {
		if (_newColumnType == null) {
			return defaultType;
		}
		else {
			return _newColumnType;
		}
	}

	public Object getNewValue(Object oldValue) throws Exception {
		Long newValue = new Long(
			CounterLocalServiceUtil.increment(Counter.class.getName()));

		if (_trackValues) {
			_valueMapper.mapValue(oldValue, newValue);
		}

		return newValue;
	}

	public ValueMapper getValueMapper() {
		return _valueMapper;
	}

	private Integer _oldColumnType;
	private Integer _newColumnType;
	private boolean _trackValues;
	private ValueMapper _valueMapper;

}