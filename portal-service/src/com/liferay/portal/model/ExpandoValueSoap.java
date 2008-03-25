/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ExpandoValueSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * <code>com.liferay.portal.service.http.ExpandoValueServiceSoap</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.http.ExpandoValueServiceSoap
 *
 */
public class ExpandoValueSoap implements Serializable {
	public static ExpandoValueSoap toSoapModel(ExpandoValue model) {
		ExpandoValueSoap soapModel = new ExpandoValueSoap();

		soapModel.setValueId(model.getValueId());
		soapModel.setColumnId(model.getColumnId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setValue(model.getValue());

		return soapModel;
	}

	public static ExpandoValueSoap[] toSoapModels(List<ExpandoValue> models) {
		List<ExpandoValueSoap> soapModels = new ArrayList<ExpandoValueSoap>(models.size());

		for (int i = 0; i < models.size(); i++) {
			ExpandoValue model = models.get(i);

			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ExpandoValueSoap[soapModels.size()]);
	}

	public ExpandoValueSoap() {
	}

	public long getPrimaryKey() {
		return _valueId;
	}

	public void setPrimaryKey(long pk) {
		setValueId(pk);
	}

	public long getValueId() {
		return _valueId;
	}

	public void setValueId(long valueId) {
		_valueId = valueId;
	}

	public long getColumnId() {
		return _columnId;
	}

	public void setColumnId(long columnId) {
		_columnId = columnId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public String getValue() {
		return _value;
	}

	public void setValue(String value) {
		_value = value;
	}

	private long _valueId;
	private long _columnId;
	private long _classPK;
	private String _value;
}