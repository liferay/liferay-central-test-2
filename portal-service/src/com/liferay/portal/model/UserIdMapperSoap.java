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
 * <a href="UserIdMapperSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * {@link com.liferay.portal.service.http.UserIdMapperServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portal.service.http.UserIdMapperServiceSoap
 * @generated
 */
public class UserIdMapperSoap implements Serializable {
	public static UserIdMapperSoap toSoapModel(UserIdMapper model) {
		UserIdMapperSoap soapModel = new UserIdMapperSoap();

		soapModel.setUserIdMapperId(model.getUserIdMapperId());
		soapModel.setUserId(model.getUserId());
		soapModel.setType(model.getType());
		soapModel.setDescription(model.getDescription());
		soapModel.setExternalUserId(model.getExternalUserId());

		return soapModel;
	}

	public static UserIdMapperSoap[] toSoapModels(UserIdMapper[] models) {
		UserIdMapperSoap[] soapModels = new UserIdMapperSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static UserIdMapperSoap[][] toSoapModels(UserIdMapper[][] models) {
		UserIdMapperSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new UserIdMapperSoap[models.length][models[0].length];
		}
		else {
			soapModels = new UserIdMapperSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static UserIdMapperSoap[] toSoapModels(List<UserIdMapper> models) {
		List<UserIdMapperSoap> soapModels = new ArrayList<UserIdMapperSoap>(models.size());

		for (UserIdMapper model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new UserIdMapperSoap[soapModels.size()]);
	}

	public UserIdMapperSoap() {
	}

	public long getPrimaryKey() {
		return _userIdMapperId;
	}

	public void setPrimaryKey(long pk) {
		setUserIdMapperId(pk);
	}

	public long getUserIdMapperId() {
		return _userIdMapperId;
	}

	public void setUserIdMapperId(long userIdMapperId) {
		_userIdMapperId = userIdMapperId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
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

	private long _userIdMapperId;
	private long _userId;
	private String _type;
	private String _description;
	private String _externalUserId;
}