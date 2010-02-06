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

package com.liferay.portal.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="LayoutSetPrototypeSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * {@link com.liferay.portal.service.http.LayoutSetPrototypeServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portal.service.http.LayoutSetPrototypeServiceSoap
 * @generated
 */
public class LayoutSetPrototypeSoap implements Serializable {
	public static LayoutSetPrototypeSoap toSoapModel(LayoutSetPrototype model) {
		LayoutSetPrototypeSoap soapModel = new LayoutSetPrototypeSoap();

		soapModel.setLayoutSetPrototypeId(model.getLayoutSetPrototypeId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setSettings(model.getSettings());
		soapModel.setActive(model.getActive());

		return soapModel;
	}

	public static LayoutSetPrototypeSoap[] toSoapModels(
		LayoutSetPrototype[] models) {
		LayoutSetPrototypeSoap[] soapModels = new LayoutSetPrototypeSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LayoutSetPrototypeSoap[][] toSoapModels(
		LayoutSetPrototype[][] models) {
		LayoutSetPrototypeSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new LayoutSetPrototypeSoap[models.length][models[0].length];
		}
		else {
			soapModels = new LayoutSetPrototypeSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LayoutSetPrototypeSoap[] toSoapModels(
		List<LayoutSetPrototype> models) {
		List<LayoutSetPrototypeSoap> soapModels = new ArrayList<LayoutSetPrototypeSoap>(models.size());

		for (LayoutSetPrototype model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LayoutSetPrototypeSoap[soapModels.size()]);
	}

	public LayoutSetPrototypeSoap() {
	}

	public long getPrimaryKey() {
		return _layoutSetPrototypeId;
	}

	public void setPrimaryKey(long pk) {
		setLayoutSetPrototypeId(pk);
	}

	public long getLayoutSetPrototypeId() {
		return _layoutSetPrototypeId;
	}

	public void setLayoutSetPrototypeId(long layoutSetPrototypeId) {
		_layoutSetPrototypeId = layoutSetPrototypeId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getSettings() {
		return _settings;
	}

	public void setSettings(String settings) {
		_settings = settings;
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	private long _layoutSetPrototypeId;
	private long _companyId;
	private String _name;
	private String _description;
	private String _settings;
	private boolean _active;
}