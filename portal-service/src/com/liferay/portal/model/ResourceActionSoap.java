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
 * <a href="ResourceActionSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * {@link com.liferay.portal.service.http.ResourceActionServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portal.service.http.ResourceActionServiceSoap
 * @generated
 */
public class ResourceActionSoap implements Serializable {
	public static ResourceActionSoap toSoapModel(ResourceAction model) {
		ResourceActionSoap soapModel = new ResourceActionSoap();

		soapModel.setResourceActionId(model.getResourceActionId());
		soapModel.setName(model.getName());
		soapModel.setActionId(model.getActionId());
		soapModel.setBitwiseValue(model.getBitwiseValue());

		return soapModel;
	}

	public static ResourceActionSoap[] toSoapModels(ResourceAction[] models) {
		ResourceActionSoap[] soapModels = new ResourceActionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ResourceActionSoap[][] toSoapModels(ResourceAction[][] models) {
		ResourceActionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ResourceActionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ResourceActionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ResourceActionSoap[] toSoapModels(List<ResourceAction> models) {
		List<ResourceActionSoap> soapModels = new ArrayList<ResourceActionSoap>(models.size());

		for (ResourceAction model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ResourceActionSoap[soapModels.size()]);
	}

	public ResourceActionSoap() {
	}

	public long getPrimaryKey() {
		return _resourceActionId;
	}

	public void setPrimaryKey(long pk) {
		setResourceActionId(pk);
	}

	public long getResourceActionId() {
		return _resourceActionId;
	}

	public void setResourceActionId(long resourceActionId) {
		_resourceActionId = resourceActionId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getActionId() {
		return _actionId;
	}

	public void setActionId(String actionId) {
		_actionId = actionId;
	}

	public long getBitwiseValue() {
		return _bitwiseValue;
	}

	public void setBitwiseValue(long bitwiseValue) {
		_bitwiseValue = bitwiseValue;
	}

	private long _resourceActionId;
	private String _name;
	private String _actionId;
	private long _bitwiseValue;
}