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
 * <a href="ResourceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * {@link com.liferay.portal.service.http.ResourceServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portal.service.http.ResourceServiceSoap
 * @generated
 */
public class ResourceSoap implements Serializable {
	public static ResourceSoap toSoapModel(Resource model) {
		ResourceSoap soapModel = new ResourceSoap();

		soapModel.setResourceId(model.getResourceId());
		soapModel.setCodeId(model.getCodeId());
		soapModel.setPrimKey(model.getPrimKey());

		return soapModel;
	}

	public static ResourceSoap[] toSoapModels(Resource[] models) {
		ResourceSoap[] soapModels = new ResourceSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ResourceSoap[][] toSoapModels(Resource[][] models) {
		ResourceSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ResourceSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ResourceSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ResourceSoap[] toSoapModels(List<Resource> models) {
		List<ResourceSoap> soapModels = new ArrayList<ResourceSoap>(models.size());

		for (Resource model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ResourceSoap[soapModels.size()]);
	}

	public ResourceSoap() {
	}

	public long getPrimaryKey() {
		return _resourceId;
	}

	public void setPrimaryKey(long pk) {
		setResourceId(pk);
	}

	public long getResourceId() {
		return _resourceId;
	}

	public void setResourceId(long resourceId) {
		_resourceId = resourceId;
	}

	public long getCodeId() {
		return _codeId;
	}

	public void setCodeId(long codeId) {
		_codeId = codeId;
	}

	public String getPrimKey() {
		return _primKey;
	}

	public void setPrimKey(String primKey) {
		_primKey = primKey;
	}

	private long _resourceId;
	private long _codeId;
	private String _primKey;
}