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

package com.liferay.portal.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ClassNameMapperSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by <code>com.liferay.portal.service.http.ClassNameMapperServiceSoap</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.http.ClassNameMapperServiceSoap
 *
 */
public class ClassNameMapperSoap implements Serializable {
	public static ClassNameMapperSoap toSoapModel(ClassNameMapper model) {
		ClassNameMapperSoap soapModel = new ClassNameMapperSoap();
		soapModel.setClassNameMapperId(model.getClassNameMapperId());
		soapModel.setClassName(model.getClassName());

		return soapModel;
	}

	public static ClassNameMapperSoap[] toSoapModels(List models) {
		List soapModels = new ArrayList(models.size());

		for (int i = 0; i < models.size(); i++) {
			ClassNameMapper model = (ClassNameMapper)models.get(i);
			soapModels.add(toSoapModel(model));
		}

		return (ClassNameMapperSoap[])soapModels.toArray(new ClassNameMapperSoap[0]);
	}

	public ClassNameMapperSoap() {
	}

	public long getPrimaryKey() {
		return _classNameMapperId;
	}

	public void setPrimaryKey(long pk) {
		setClassNameMapperId(pk);
	}

	public long getClassNameMapperId() {
		return _classNameMapperId;
	}

	public void setClassNameMapperId(long classNameMapperId) {
		_classNameMapperId = classNameMapperId;
	}

	public String getClassName() {
		return _className;
	}

	public void setClassName(String className) {
		_className = className;
	}

	private long _classNameMapperId;
	private String _className;
}