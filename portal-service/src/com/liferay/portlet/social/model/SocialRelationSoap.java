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

package com.liferay.portlet.social.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="SocialRelationSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * {@link com.liferay.portlet.social.service.http.SocialRelationServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.social.service.http.SocialRelationServiceSoap
 * @generated
 */
public class SocialRelationSoap implements Serializable {
	public static SocialRelationSoap toSoapModel(SocialRelation model) {
		SocialRelationSoap soapModel = new SocialRelationSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setRelationId(model.getRelationId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setUserId1(model.getUserId1());
		soapModel.setUserId2(model.getUserId2());
		soapModel.setType(model.getType());

		return soapModel;
	}

	public static SocialRelationSoap[] toSoapModels(SocialRelation[] models) {
		SocialRelationSoap[] soapModels = new SocialRelationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SocialRelationSoap[][] toSoapModels(SocialRelation[][] models) {
		SocialRelationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SocialRelationSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SocialRelationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SocialRelationSoap[] toSoapModels(List<SocialRelation> models) {
		List<SocialRelationSoap> soapModels = new ArrayList<SocialRelationSoap>(models.size());

		for (SocialRelation model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SocialRelationSoap[soapModels.size()]);
	}

	public SocialRelationSoap() {
	}

	public long getPrimaryKey() {
		return _relationId;
	}

	public void setPrimaryKey(long pk) {
		setRelationId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getRelationId() {
		return _relationId;
	}

	public void setRelationId(long relationId) {
		_relationId = relationId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(long createDate) {
		_createDate = createDate;
	}

	public long getUserId1() {
		return _userId1;
	}

	public void setUserId1(long userId1) {
		_userId1 = userId1;
	}

	public long getUserId2() {
		return _userId2;
	}

	public void setUserId2(long userId2) {
		_userId2 = userId2;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	private String _uuid;
	private long _relationId;
	private long _companyId;
	private long _createDate;
	private long _userId1;
	private long _userId2;
	private int _type;
}