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
 * <a href="ExpandoColumnSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * {@link com.liferay.portlet.expando.service.http.ExpandoColumnServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.expando.service.http.ExpandoColumnServiceSoap
 * @generated
 */
public class ExpandoColumnSoap implements Serializable {
	public static ExpandoColumnSoap toSoapModel(ExpandoColumn model) {
		ExpandoColumnSoap soapModel = new ExpandoColumnSoap();

		soapModel.setColumnId(model.getColumnId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setTableId(model.getTableId());
		soapModel.setName(model.getName());
		soapModel.setType(model.getType());
		soapModel.setDefaultData(model.getDefaultData());
		soapModel.setTypeSettings(model.getTypeSettings());

		return soapModel;
	}

	public static ExpandoColumnSoap[] toSoapModels(ExpandoColumn[] models) {
		ExpandoColumnSoap[] soapModels = new ExpandoColumnSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ExpandoColumnSoap[][] toSoapModels(ExpandoColumn[][] models) {
		ExpandoColumnSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ExpandoColumnSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ExpandoColumnSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ExpandoColumnSoap[] toSoapModels(List<ExpandoColumn> models) {
		List<ExpandoColumnSoap> soapModels = new ArrayList<ExpandoColumnSoap>(models.size());

		for (ExpandoColumn model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ExpandoColumnSoap[soapModels.size()]);
	}

	public ExpandoColumnSoap() {
	}

	public long getPrimaryKey() {
		return _columnId;
	}

	public void setPrimaryKey(long pk) {
		setColumnId(pk);
	}

	public long getColumnId() {
		return _columnId;
	}

	public void setColumnId(long columnId) {
		_columnId = columnId;
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

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public String getDefaultData() {
		return _defaultData;
	}

	public void setDefaultData(String defaultData) {
		_defaultData = defaultData;
	}

	public String getTypeSettings() {
		return _typeSettings;
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;
	}

	private long _columnId;
	private long _companyId;
	private long _tableId;
	private String _name;
	private int _type;
	private String _defaultData;
	private String _typeSettings;
}