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
 * <a href="ExpandoTableSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * {@link com.liferay.portlet.expando.service.http.ExpandoTableServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.expando.service.http.ExpandoTableServiceSoap
 * @generated
 */
public class ExpandoTableSoap implements Serializable {
	public static ExpandoTableSoap toSoapModel(ExpandoTable model) {
		ExpandoTableSoap soapModel = new ExpandoTableSoap();

		soapModel.setTableId(model.getTableId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setName(model.getName());

		return soapModel;
	}

	public static ExpandoTableSoap[] toSoapModels(ExpandoTable[] models) {
		ExpandoTableSoap[] soapModels = new ExpandoTableSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ExpandoTableSoap[][] toSoapModels(ExpandoTable[][] models) {
		ExpandoTableSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ExpandoTableSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ExpandoTableSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ExpandoTableSoap[] toSoapModels(List<ExpandoTable> models) {
		List<ExpandoTableSoap> soapModels = new ArrayList<ExpandoTableSoap>(models.size());

		for (ExpandoTable model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ExpandoTableSoap[soapModels.size()]);
	}

	public ExpandoTableSoap() {
	}

	public long getPrimaryKey() {
		return _tableId;
	}

	public void setPrimaryKey(long pk) {
		setTableId(pk);
	}

	public long getTableId() {
		return _tableId;
	}

	public void setTableId(long tableId) {
		_tableId = tableId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	private long _tableId;
	private long _companyId;
	private long _classNameId;
	private String _name;
}