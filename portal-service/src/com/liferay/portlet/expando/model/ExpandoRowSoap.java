/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.expando.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ExpandoRowSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * {@link com.liferay.portlet.expando.service.http.ExpandoRowServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.expando.service.http.ExpandoRowServiceSoap
 * @generated
 */
public class ExpandoRowSoap implements Serializable {
	public static ExpandoRowSoap toSoapModel(ExpandoRow model) {
		ExpandoRowSoap soapModel = new ExpandoRowSoap();

		soapModel.setRowId(model.getRowId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setTableId(model.getTableId());
		soapModel.setClassPK(model.getClassPK());

		return soapModel;
	}

	public static ExpandoRowSoap[] toSoapModels(ExpandoRow[] models) {
		ExpandoRowSoap[] soapModels = new ExpandoRowSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ExpandoRowSoap[][] toSoapModels(ExpandoRow[][] models) {
		ExpandoRowSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ExpandoRowSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ExpandoRowSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ExpandoRowSoap[] toSoapModels(List<ExpandoRow> models) {
		List<ExpandoRowSoap> soapModels = new ArrayList<ExpandoRowSoap>(models.size());

		for (ExpandoRow model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ExpandoRowSoap[soapModels.size()]);
	}

	public ExpandoRowSoap() {
	}

	public long getPrimaryKey() {
		return _rowId;
	}

	public void setPrimaryKey(long pk) {
		setRowId(pk);
	}

	public long getRowId() {
		return _rowId;
	}

	public void setRowId(long rowId) {
		_rowId = rowId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getTableId() {
		return _tableId;
	}

	public void setTableId(long tableId) {
		_tableId = tableId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	private long _rowId;
	private long _companyId;
	private long _tableId;
	private long _classPK;
}