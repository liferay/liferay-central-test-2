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

import com.liferay.portal.service.persistence.UserIdMapperPK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="UserIdMapperSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UserIdMapperSoap implements Serializable {
	public static UserIdMapperSoap toSoapModel(UserIdMapper model) {
		UserIdMapperSoap soapModel = new UserIdMapperSoap();
		soapModel.setUserId(model.getUserId());
		soapModel.setType(model.getType());
		soapModel.setDescription(model.getDescription());
		soapModel.setExternalUserId(model.getExternalUserId());

		return soapModel;
	}

	public static UserIdMapperSoap[] toSoapModels(List models) {
		List soapModels = new ArrayList(models.size());

		for (int i = 0; i < models.size(); i++) {
			UserIdMapper model = (UserIdMapper)models.get(i);
			soapModels.add(toSoapModel(model));
		}

		return (UserIdMapperSoap[])soapModels.toArray(new UserIdMapperSoap[0]);
	}

	public UserIdMapperSoap() {
	}

	public UserIdMapperPK getPrimaryKey() {
		return new UserIdMapperPK(_userId, _type);
	}

	public void setPrimaryKey(UserIdMapperPK pk) {
		setUserId(pk.userId);
		setType(pk.type);
	}

	public String getUserId() {
		return _userId;
	}

	public void setUserId(String userId) {
		_userId = userId;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getExternalUserId() {
		return _externalUserId;
	}

	public void setExternalUserId(String externalUserId) {
		_externalUserId = externalUserId;
	}

	private String _userId;
	private String _type;
	private String _description;
	private String _externalUserId;
}