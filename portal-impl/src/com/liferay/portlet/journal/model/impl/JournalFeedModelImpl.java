/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.JournalFeedSoap;

import java.io.Serializable;

import java.lang.StringBuilder;
import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="JournalFeedModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>JournalFeed</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.journal.model.JournalFeed
 * @see com.liferay.portlet.journal.model.JournalFeedModel
 * @see com.liferay.portlet.journal.model.impl.JournalFeedImpl
 *
 */
public class JournalFeedModelImpl extends BaseModelImpl<JournalFeed> {
	public static final String TABLE_NAME = "JournalFeed";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", new Integer(Types.VARCHAR) },
			

			{ "id_", new Integer(Types.BIGINT) },
			

			{ "groupId", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "userId", new Integer(Types.BIGINT) },
			

			{ "userName", new Integer(Types.VARCHAR) },
			

			{ "createDate", new Integer(Types.TIMESTAMP) },
			

			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			

			{ "feedId", new Integer(Types.VARCHAR) },
			

			{ "name", new Integer(Types.VARCHAR) },
			

			{ "description", new Integer(Types.VARCHAR) },
			

			{ "type_", new Integer(Types.VARCHAR) },
			

			{ "structureId", new Integer(Types.VARCHAR) },
			

			{ "templateId", new Integer(Types.VARCHAR) },
			

			{ "rendererTemplateId", new Integer(Types.VARCHAR) },
			

			{ "delta", new Integer(Types.INTEGER) },
			

			{ "orderByCol", new Integer(Types.VARCHAR) },
			

			{ "orderByType", new Integer(Types.VARCHAR) },
			

			{ "targetLayoutFriendlyUrl", new Integer(Types.VARCHAR) },
			

			{ "targetPortletId", new Integer(Types.VARCHAR) },
			

			{ "contentField", new Integer(Types.VARCHAR) },
			

			{ "feedType", new Integer(Types.VARCHAR) },
			

			{ "feedVersion", new Integer(Types.DOUBLE) }
		};
	public static final String TABLE_SQL_CREATE = "create table JournalFeed (uuid_ VARCHAR(75) null,id_ LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,feedId VARCHAR(75) null,name VARCHAR(75) null,description STRING null,type_ VARCHAR(75) null,structureId VARCHAR(75) null,templateId VARCHAR(75) null,rendererTemplateId VARCHAR(75) null,delta INTEGER,orderByCol VARCHAR(75) null,orderByType VARCHAR(75) null,targetLayoutFriendlyUrl VARCHAR(75) null,targetPortletId VARCHAR(75) null,contentField VARCHAR(75) null,feedType VARCHAR(75) null,feedVersion DOUBLE)";
	public static final String TABLE_SQL_DROP = "drop table JournalFeed";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.journal.model.JournalFeed"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.journal.model.JournalFeed"),
			true);

	public static JournalFeed toModel(JournalFeedSoap soapModel) {
		JournalFeed model = new JournalFeedImpl();

		model.setUuid(soapModel.getUuid());
		model.setId(soapModel.getId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setFeedId(soapModel.getFeedId());
		model.setName(soapModel.getName());
		model.setDescription(soapModel.getDescription());
		model.setType(soapModel.getType());
		model.setStructureId(soapModel.getStructureId());
		model.setTemplateId(soapModel.getTemplateId());
		model.setRendererTemplateId(soapModel.getRendererTemplateId());
		model.setDelta(soapModel.getDelta());
		model.setOrderByCol(soapModel.getOrderByCol());
		model.setOrderByType(soapModel.getOrderByType());
		model.setTargetLayoutFriendlyUrl(soapModel.getTargetLayoutFriendlyUrl());
		model.setTargetPortletId(soapModel.getTargetPortletId());
		model.setContentField(soapModel.getContentField());
		model.setFeedType(soapModel.getFeedType());
		model.setFeedVersion(soapModel.getFeedVersion());

		return model;
	}

	public static List<JournalFeed> toModels(JournalFeedSoap[] soapModels) {
		List<JournalFeed> models = new ArrayList<JournalFeed>(soapModels.length);

		for (JournalFeedSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.journal.model.JournalFeed"));

	public JournalFeedModelImpl() {
	}

	public long getPrimaryKey() {
		return _id;
	}

	public void setPrimaryKey(long pk) {
		setId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_id);
	}

	public String getUuid() {
		return GetterUtil.getString(_uuid);
	}

	public void setUuid(String uuid) {
		_uuid = uuid;

		if (_originalUuid == null) {
			_originalUuid = uuid;
		}
	}

	public String getOriginalUuid() {
		return GetterUtil.getString(_originalUuid);
	}

	public long getId() {
		return _id;
	}

	public void setId(long id) {
		_id = id;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;

		if (!_setOriginalGroupId) {
			_setOriginalGroupId = true;

			_originalGroupId = groupId;
		}
	}

	public long getOriginalGroupId() {
		return _originalGroupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return GetterUtil.getString(_userName);
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getFeedId() {
		return GetterUtil.getString(_feedId);
	}

	public void setFeedId(String feedId) {
		_feedId = feedId;

		if (_originalFeedId == null) {
			_originalFeedId = feedId;
		}
	}

	public String getOriginalFeedId() {
		return GetterUtil.getString(_originalFeedId);
	}

	public String getName() {
		return GetterUtil.getString(_name);
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return GetterUtil.getString(_description);
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getType() {
		return GetterUtil.getString(_type);
	}

	public void setType(String type) {
		_type = type;
	}

	public String getStructureId() {
		return GetterUtil.getString(_structureId);
	}

	public void setStructureId(String structureId) {
		_structureId = structureId;
	}

	public String getTemplateId() {
		return GetterUtil.getString(_templateId);
	}

	public void setTemplateId(String templateId) {
		_templateId = templateId;
	}

	public String getRendererTemplateId() {
		return GetterUtil.getString(_rendererTemplateId);
	}

	public void setRendererTemplateId(String rendererTemplateId) {
		_rendererTemplateId = rendererTemplateId;
	}

	public int getDelta() {
		return _delta;
	}

	public void setDelta(int delta) {
		_delta = delta;
	}

	public String getOrderByCol() {
		return GetterUtil.getString(_orderByCol);
	}

	public void setOrderByCol(String orderByCol) {
		_orderByCol = orderByCol;
	}

	public String getOrderByType() {
		return GetterUtil.getString(_orderByType);
	}

	public void setOrderByType(String orderByType) {
		_orderByType = orderByType;
	}

	public String getTargetLayoutFriendlyUrl() {
		return GetterUtil.getString(_targetLayoutFriendlyUrl);
	}

	public void setTargetLayoutFriendlyUrl(String targetLayoutFriendlyUrl) {
		_targetLayoutFriendlyUrl = targetLayoutFriendlyUrl;
	}

	public String getTargetPortletId() {
		return GetterUtil.getString(_targetPortletId);
	}

	public void setTargetPortletId(String targetPortletId) {
		_targetPortletId = targetPortletId;
	}

	public String getContentField() {
		return GetterUtil.getString(_contentField);
	}

	public void setContentField(String contentField) {
		_contentField = contentField;
	}

	public String getFeedType() {
		return GetterUtil.getString(_feedType);
	}

	public void setFeedType(String feedType) {
		_feedType = feedType;
	}

	public double getFeedVersion() {
		return _feedVersion;
	}

	public void setFeedVersion(double feedVersion) {
		_feedVersion = feedVersion;
	}

	public JournalFeed toEscapedModel() {
		if (isEscapedModel()) {
			return (JournalFeed)this;
		}
		else {
			JournalFeed model = new JournalFeedImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setUuid(HtmlUtil.escape(getUuid()));
			model.setId(getId());
			model.setGroupId(getGroupId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setUserName(HtmlUtil.escape(getUserName()));
			model.setCreateDate(getCreateDate());
			model.setModifiedDate(getModifiedDate());
			model.setFeedId(getFeedId());
			model.setName(HtmlUtil.escape(getName()));
			model.setDescription(HtmlUtil.escape(getDescription()));
			model.setType(HtmlUtil.escape(getType()));
			model.setStructureId(getStructureId());
			model.setTemplateId(getTemplateId());
			model.setRendererTemplateId(HtmlUtil.escape(getRendererTemplateId()));
			model.setDelta(getDelta());
			model.setOrderByCol(HtmlUtil.escape(getOrderByCol()));
			model.setOrderByType(HtmlUtil.escape(getOrderByType()));
			model.setTargetLayoutFriendlyUrl(HtmlUtil.escape(
					getTargetLayoutFriendlyUrl()));
			model.setTargetPortletId(HtmlUtil.escape(getTargetPortletId()));
			model.setContentField(HtmlUtil.escape(getContentField()));
			model.setFeedType(HtmlUtil.escape(getFeedType()));
			model.setFeedVersion(getFeedVersion());

			model = (JournalFeed)Proxy.newProxyInstance(JournalFeed.class.getClassLoader(),
					new Class[] { JournalFeed.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(JournalFeed.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		JournalFeedImpl clone = new JournalFeedImpl();

		clone.setUuid(getUuid());
		clone.setId(getId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setFeedId(getFeedId());
		clone.setName(getName());
		clone.setDescription(getDescription());
		clone.setType(getType());
		clone.setStructureId(getStructureId());
		clone.setTemplateId(getTemplateId());
		clone.setRendererTemplateId(getRendererTemplateId());
		clone.setDelta(getDelta());
		clone.setOrderByCol(getOrderByCol());
		clone.setOrderByType(getOrderByType());
		clone.setTargetLayoutFriendlyUrl(getTargetLayoutFriendlyUrl());
		clone.setTargetPortletId(getTargetPortletId());
		clone.setContentField(getContentField());
		clone.setFeedType(getFeedType());
		clone.setFeedVersion(getFeedVersion());

		return clone;
	}

	public int compareTo(JournalFeed journalFeed) {
		int value = 0;

		value = getFeedId().compareTo(journalFeed.getFeedId());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		JournalFeed journalFeed = null;

		try {
			journalFeed = (JournalFeed)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = journalFeed.getPrimaryKey();

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

	public String toHtmlString() {
		StringBuilder sb = new StringBuilder();

		sb.append("<table class=\"lfr-table\">\n");

		sb.append("<tr><td align=\"right\" valign=\"top\"><b>uuid</b></td><td>" +
			getUuid() + "</td></tr>\n");
		sb.append("<tr><td align=\"right\" valign=\"top\"><b>id</b></td><td>" +
			getId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>groupId</b></td><td>" +
			getGroupId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>companyId</b></td><td>" +
			getCompanyId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>userId</b></td><td>" +
			getUserId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>userName</b></td><td>" +
			getUserName() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>createDate</b></td><td>" +
			getCreateDate() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>modifiedDate</b></td><td>" +
			getModifiedDate() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>feedId</b></td><td>" +
			getFeedId() + "</td></tr>\n");
		sb.append("<tr><td align=\"right\" valign=\"top\"><b>name</b></td><td>" +
			getName() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>description</b></td><td>" +
			getDescription() + "</td></tr>\n");
		sb.append("<tr><td align=\"right\" valign=\"top\"><b>type</b></td><td>" +
			getType() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>structureId</b></td><td>" +
			getStructureId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>templateId</b></td><td>" +
			getTemplateId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>rendererTemplateId</b></td><td>" +
			getRendererTemplateId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>delta</b></td><td>" +
			getDelta() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>orderByCol</b></td><td>" +
			getOrderByCol() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>orderByType</b></td><td>" +
			getOrderByType() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>targetLayoutFriendlyUrl</b></td><td>" +
			getTargetLayoutFriendlyUrl() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>targetPortletId</b></td><td>" +
			getTargetPortletId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>contentField</b></td><td>" +
			getContentField() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>feedType</b></td><td>" +
			getFeedType() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>feedVersion</b></td><td>" +
			getFeedVersion() + "</td></tr>\n");

		sb.append("</table>");

		return sb.toString();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("com.liferay.portlet.journal.model.JournalFeed (");

		sb.append("uuid: " + getUuid() + ", ");
		sb.append("id: " + getId() + ", ");
		sb.append("groupId: " + getGroupId() + ", ");
		sb.append("companyId: " + getCompanyId() + ", ");
		sb.append("userId: " + getUserId() + ", ");
		sb.append("userName: " + getUserName() + ", ");
		sb.append("createDate: " + getCreateDate() + ", ");
		sb.append("modifiedDate: " + getModifiedDate() + ", ");
		sb.append("feedId: " + getFeedId() + ", ");
		sb.append("name: " + getName() + ", ");
		sb.append("description: " + getDescription() + ", ");
		sb.append("type: " + getType() + ", ");
		sb.append("structureId: " + getStructureId() + ", ");
		sb.append("templateId: " + getTemplateId() + ", ");
		sb.append("rendererTemplateId: " + getRendererTemplateId() + ", ");
		sb.append("delta: " + getDelta() + ", ");
		sb.append("orderByCol: " + getOrderByCol() + ", ");
		sb.append("orderByType: " + getOrderByType() + ", ");
		sb.append("targetLayoutFriendlyUrl: " + getTargetLayoutFriendlyUrl() +
			", ");
		sb.append("targetPortletId: " + getTargetPortletId() + ", ");
		sb.append("contentField: " + getContentField() + ", ");
		sb.append("feedType: " + getFeedType() + ", ");
		sb.append("feedVersion: " + getFeedVersion() + ", ");

		sb.append(")");

		return sb.toString();
	}

	private String _uuid;
	private String _originalUuid;
	private long _id;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _feedId;
	private String _originalFeedId;
	private String _name;
	private String _description;
	private String _type;
	private String _structureId;
	private String _templateId;
	private String _rendererTemplateId;
	private int _delta;
	private String _orderByCol;
	private String _orderByType;
	private String _targetLayoutFriendlyUrl;
	private String _targetPortletId;
	private String _contentField;
	private String _feedType;
	private double _feedVersion;
	private transient ExpandoBridge _expandoBridge;
}