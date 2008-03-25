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

import com.liferay.portal.service.persistence.ExpandoRowValuesPK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ExpandoRowValuesSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * <code>com.liferay.portal.service.http.ExpandoRowValuesServiceSoap</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.http.ExpandoRowValuesServiceSoap
 *
 */
public class ExpandoRowValuesSoap implements Serializable {
	public static ExpandoRowValuesSoap toSoapModel(ExpandoRowValues model) {
		ExpandoRowValuesSoap soapModel = new ExpandoRowValuesSoap();

		soapModel.setRowId(model.getRowId());
		soapModel.setValueId(model.getValueId());

		return soapModel;
	}

	public static ExpandoRowValuesSoap[] toSoapModels(
		List<ExpandoRowValues> models) {
		List<ExpandoRowValuesSoap> soapModels = new ArrayList<ExpandoRowValuesSoap>(models.size());

		for (int i = 0; i < models.size(); i++) {
			ExpandoRowValues model = models.get(i);

			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ExpandoRowValuesSoap[soapModels.size()]);
	}

	public ExpandoRowValuesSoap() {
	}

	public ExpandoRowValuesPK getPrimaryKey() {
		return new ExpandoRowValuesPK(_rowId, _valueId);
	}

	public void setPrimaryKey(ExpandoRowValuesPK pk) {
		setRowId(pk.rowId);
		setValueId(pk.valueId);
	}

	public long getRowId() {
		return _rowId;
	}

	public void setRowId(long rowId) {
		_rowId = rowId;
	}

	public long getValueId() {
		return _valueId;
	}

	public void setValueId(long valueId) {
		_valueId = valueId;
	}

	private long _rowId;
	private long _valueId;
}