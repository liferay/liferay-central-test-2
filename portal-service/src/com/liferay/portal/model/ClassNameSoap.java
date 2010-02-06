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
 * <a href="ClassNameSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * {@link com.liferay.portal.service.http.ClassNameServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portal.service.http.ClassNameServiceSoap
 * @generated
 */
public class ClassNameSoap implements Serializable {
	public static ClassNameSoap toSoapModel(ClassName model) {
		ClassNameSoap soapModel = new ClassNameSoap();

		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setValue(model.getValue());

		return soapModel;
	}

	public static ClassNameSoap[] toSoapModels(ClassName[] models) {
		ClassNameSoap[] soapModels = new ClassNameSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ClassNameSoap[][] toSoapModels(ClassName[][] models) {
		ClassNameSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ClassNameSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ClassNameSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ClassNameSoap[] toSoapModels(List<ClassName> models) {
		List<ClassNameSoap> soapModels = new ArrayList<ClassNameSoap>(models.size());

		for (ClassName model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ClassNameSoap[soapModels.size()]);
	}

	public ClassNameSoap() {
	}

	public long getPrimaryKey() {
		return _classNameId;
	}

	public void setPrimaryKey(long pk) {
		setClassNameId(pk);
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public String getValue() {
		return _value;
	}

	public void setValue(String value) {
		_value = value;
	}

	private long _classNameId;
	private String _value;
}