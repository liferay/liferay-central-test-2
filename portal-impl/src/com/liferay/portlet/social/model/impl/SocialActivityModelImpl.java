/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivitySoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="SocialActivityModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>SocialActivity</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.social.service.model.SocialActivity
 * @see com.liferay.portlet.social.service.model.SocialActivityModel
 * @see com.liferay.portlet.social.service.model.impl.SocialActivityImpl
 *
 */
public class SocialActivityModelImpl extends BaseModelImpl {
	public static final String TABLE_NAME = "SocialActivity";
	public static final Object[][] TABLE_COLUMNS = {
			{ "activityId", new Integer(Types.BIGINT) },
			

			{ "groupId", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "userId", new Integer(Types.BIGINT) },
			

			{ "userName", new Integer(Types.VARCHAR) },
			

			{ "createDate", new Integer(Types.TIMESTAMP) },
			

			{ "classNameId", new Integer(Types.BIGINT) },
			

			{ "classPK", new Integer(Types.BIGINT) },
			

			{ "type_", new Integer(Types.VARCHAR) },
			

			{ "extraData", new Integer(Types.CLOB) },
			

			{ "receiverUserId", new Integer(Types.BIGINT) },
			

			{ "receiverUserName", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table SocialActivity (activityId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,classNameId LONG,classPK LONG,type_ VARCHAR(75) null,extraData TEXT null,receiverUserId LONG,receiverUserName VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table SocialActivity";
	public static final boolean CACHE_ENABLED = GetterUtil.getBoolean(PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.social.model.SocialActivity"),
			true);

	public static SocialActivity toModel(SocialActivitySoap soapModel) {
		SocialActivity model = new SocialActivityImpl();

		model.setActivityId(soapModel.getActivityId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setClassNameId(soapModel.getClassNameId());
		model.setClassPK(soapModel.getClassPK());
		model.setType(soapModel.getType());
		model.setExtraData(soapModel.getExtraData());
		model.setReceiverUserId(soapModel.getReceiverUserId());
		model.setReceiverUserName(soapModel.getReceiverUserName());

		return model;
	}

	public static List<SocialActivity> toModels(SocialActivitySoap[] soapModels) {
		List<SocialActivity> models = new ArrayList<SocialActivity>(soapModels.length);

		for (SocialActivitySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.social.model.SocialActivity"));

	public SocialActivityModelImpl() {
	}

	public long getPrimaryKey() {
		return _activityId;
	}

	public void setPrimaryKey(long pk) {
		setActivityId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_activityId);
	}

	public long getActivityId() {
		return _activityId;
	}

	public void setActivityId(long activityId) {
		if (activityId != _activityId) {
			_activityId = activityId;
		}
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		if (groupId != _groupId) {
			_groupId = groupId;
		}
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		if (companyId != _companyId) {
			_companyId = companyId;
		}
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		if (userId != _userId) {
			_userId = userId;
		}
	}

	public String getUserName() {
		return GetterUtil.getString(_userName);
	}

	public void setUserName(String userName) {
		if (((userName == null) && (_userName != null)) ||
				((userName != null) && (_userName == null)) ||
				((userName != null) && (_userName != null) &&
				!userName.equals(_userName))) {
			_userName = userName;
		}
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		if (((createDate == null) && (_createDate != null)) ||
				((createDate != null) && (_createDate == null)) ||
				((createDate != null) && (_createDate != null) &&
				!createDate.equals(_createDate))) {
			_createDate = createDate;
		}
	}

	public String getClassName() {
		return PortalUtil.getClassName(getClassNameId());
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		if (classNameId != _classNameId) {
			_classNameId = classNameId;
		}
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		if (classPK != _classPK) {
			_classPK = classPK;
		}
	}

	public String getType() {
		return GetterUtil.getString(_type);
	}

	public void setType(String type) {
		if (((type == null) && (_type != null)) ||
				((type != null) && (_type == null)) ||
				((type != null) && (_type != null) && !type.equals(_type))) {
			_type = type;
		}
	}

	public String getExtraData() {
		return GetterUtil.getString(_extraData);
	}

	public void setExtraData(String extraData) {
		if (((extraData == null) && (_extraData != null)) ||
				((extraData != null) && (_extraData == null)) ||
				((extraData != null) && (_extraData != null) &&
				!extraData.equals(_extraData))) {
			_extraData = extraData;
		}
	}

	public long getReceiverUserId() {
		return _receiverUserId;
	}

	public void setReceiverUserId(long receiverUserId) {
		if (receiverUserId != _receiverUserId) {
			_receiverUserId = receiverUserId;
		}
	}

	public String getReceiverUserName() {
		return GetterUtil.getString(_receiverUserName);
	}

	public void setReceiverUserName(String receiverUserName) {
		if (((receiverUserName == null) && (_receiverUserName != null)) ||
				((receiverUserName != null) && (_receiverUserName == null)) ||
				((receiverUserName != null) && (_receiverUserName != null) &&
				!receiverUserName.equals(_receiverUserName))) {
			_receiverUserName = receiverUserName;
		}
	}

	public SocialActivity toEscapedModel() {
		if (isEscapedModel()) {
			return (SocialActivity)this;
		}
		else {
			SocialActivity model = new SocialActivityImpl();

			model.setEscapedModel(true);

			model.setActivityId(getActivityId());
			model.setGroupId(getGroupId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setUserName(HtmlUtil.escape(getUserName()));
			model.setCreateDate(getCreateDate());
			model.setClassNameId(getClassNameId());
			model.setClassPK(getClassPK());
			model.setType(HtmlUtil.escape(getType()));
			model.setExtraData(HtmlUtil.escape(getExtraData()));
			model.setReceiverUserId(getReceiverUserId());
			model.setReceiverUserName(HtmlUtil.escape(getReceiverUserName()));

			model = (SocialActivity)Proxy.newProxyInstance(SocialActivity.class.getClassLoader(),
					new Class[] { SocialActivity.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public Object clone() {
		SocialActivityImpl clone = new SocialActivityImpl();

		clone.setActivityId(getActivityId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setClassNameId(getClassNameId());
		clone.setClassPK(getClassPK());
		clone.setType(getType());
		clone.setExtraData(getExtraData());
		clone.setReceiverUserId(getReceiverUserId());
		clone.setReceiverUserName(getReceiverUserName());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		SocialActivityImpl socialActivity = (SocialActivityImpl)obj;

		int value = 0;

		value = DateUtil.compareTo(getCreateDate(),
				socialActivity.getCreateDate());

		value = value * -1;

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		SocialActivityImpl socialActivity = null;

		try {
			socialActivity = (SocialActivityImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = socialActivity.getPrimaryKey();

		if (getPrimaryKey() == pk) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (int)getPrimaryKey();
	}

	private long _activityId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private long _classNameId;
	private long _classPK;
	private String _type;
	private String _extraData;
	private long _receiverUserId;
	private String _receiverUserName;
}