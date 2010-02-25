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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.WorkflowDefinitionLink;
import com.liferay.portal.model.WorkflowDefinitionLinkSoap;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="WorkflowDefinitionLinkModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the WorkflowDefinitionLink table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WorkflowDefinitionLinkImpl
 * @see       com.liferay.portal.model.WorkflowDefinitionLink
 * @see       com.liferay.portal.model.WorkflowDefinitionLinkModel
 * @generated
 */
public class WorkflowDefinitionLinkModelImpl extends BaseModelImpl<WorkflowDefinitionLink> {
	public static final String TABLE_NAME = "WorkflowDefinitionLink";
	public static final Object[][] TABLE_COLUMNS = {
			{ "workflowDefinitionLinkId", new Integer(Types.BIGINT) },
			{ "groupId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "userName", new Integer(Types.VARCHAR) },
			{ "createDate", new Integer(Types.TIMESTAMP) },
			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			{ "classNameId", new Integer(Types.BIGINT) },
			{ "workflowDefinitionName", new Integer(Types.VARCHAR) },
			{ "workflowDefinitionVersion", new Integer(Types.INTEGER) }
		};
	public static final String TABLE_SQL_CREATE = "create table WorkflowDefinitionLink (workflowDefinitionLinkId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,workflowDefinitionName VARCHAR(75) null,workflowDefinitionVersion INTEGER)";
	public static final String TABLE_SQL_DROP = "drop table WorkflowDefinitionLink";
	public static final String ORDER_BY_JPQL = " ORDER BY workflowDefinitionLink.workflowDefinitionName ASC";
	public static final String ORDER_BY_SQL = " ORDER BY WorkflowDefinitionLink.workflowDefinitionName ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.WorkflowDefinitionLink"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.WorkflowDefinitionLink"),
			true);

	public static WorkflowDefinitionLink toModel(
		WorkflowDefinitionLinkSoap soapModel) {
		WorkflowDefinitionLink model = new WorkflowDefinitionLinkImpl();

		model.setWorkflowDefinitionLinkId(soapModel.getWorkflowDefinitionLinkId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setClassNameId(soapModel.getClassNameId());
		model.setWorkflowDefinitionName(soapModel.getWorkflowDefinitionName());
		model.setWorkflowDefinitionVersion(soapModel.getWorkflowDefinitionVersion());

		return model;
	}

	public static List<WorkflowDefinitionLink> toModels(
		WorkflowDefinitionLinkSoap[] soapModels) {
		List<WorkflowDefinitionLink> models = new ArrayList<WorkflowDefinitionLink>(soapModels.length);

		for (WorkflowDefinitionLinkSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.WorkflowDefinitionLink"));

	public WorkflowDefinitionLinkModelImpl() {
	}

	public long getPrimaryKey() {
		return _workflowDefinitionLinkId;
	}

	public void setPrimaryKey(long pk) {
		setWorkflowDefinitionLinkId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_workflowDefinitionLinkId);
	}

	public long getWorkflowDefinitionLinkId() {
		return _workflowDefinitionLinkId;
	}

	public void setWorkflowDefinitionLinkId(long workflowDefinitionLinkId) {
		_workflowDefinitionLinkId = workflowDefinitionLinkId;
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

		if (!_setOriginalCompanyId) {
			_setOriginalCompanyId = true;

			_originalCompanyId = companyId;
		}
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
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

	public String getClassName() {
		if (getClassNameId() <= 0) {
			return StringPool.BLANK;
		}

		return PortalUtil.getClassName(getClassNameId());
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;

		if (!_setOriginalClassNameId) {
			_setOriginalClassNameId = true;

			_originalClassNameId = classNameId;
		}
	}

	public long getOriginalClassNameId() {
		return _originalClassNameId;
	}

	public String getWorkflowDefinitionName() {
		return GetterUtil.getString(_workflowDefinitionName);
	}

	public void setWorkflowDefinitionName(String workflowDefinitionName) {
		_workflowDefinitionName = workflowDefinitionName;
	}

	public int getWorkflowDefinitionVersion() {
		return _workflowDefinitionVersion;
	}

	public void setWorkflowDefinitionVersion(int workflowDefinitionVersion) {
		_workflowDefinitionVersion = workflowDefinitionVersion;
	}

	public WorkflowDefinitionLink toEscapedModel() {
		if (isEscapedModel()) {
			return (WorkflowDefinitionLink)this;
		}
		else {
			WorkflowDefinitionLink model = new WorkflowDefinitionLinkImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setWorkflowDefinitionLinkId(getWorkflowDefinitionLinkId());
			model.setGroupId(getGroupId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setUserName(HtmlUtil.escape(getUserName()));
			model.setCreateDate(getCreateDate());
			model.setModifiedDate(getModifiedDate());
			model.setClassNameId(getClassNameId());
			model.setWorkflowDefinitionName(HtmlUtil.escape(
					getWorkflowDefinitionName()));
			model.setWorkflowDefinitionVersion(getWorkflowDefinitionVersion());

			model = (WorkflowDefinitionLink)Proxy.newProxyInstance(WorkflowDefinitionLink.class.getClassLoader(),
					new Class[] { WorkflowDefinitionLink.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
					WorkflowDefinitionLink.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		WorkflowDefinitionLinkImpl clone = new WorkflowDefinitionLinkImpl();

		clone.setWorkflowDefinitionLinkId(getWorkflowDefinitionLinkId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setClassNameId(getClassNameId());
		clone.setWorkflowDefinitionName(getWorkflowDefinitionName());
		clone.setWorkflowDefinitionVersion(getWorkflowDefinitionVersion());

		return clone;
	}

	public int compareTo(WorkflowDefinitionLink workflowDefinitionLink) {
		int value = 0;

		value = getWorkflowDefinitionName()
					.compareTo(workflowDefinitionLink.getWorkflowDefinitionName());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		WorkflowDefinitionLink workflowDefinitionLink = null;

		try {
			workflowDefinitionLink = (WorkflowDefinitionLink)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = workflowDefinitionLink.getPrimaryKey();

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

	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{workflowDefinitionLinkId=");
		sb.append(getWorkflowDefinitionLinkId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", workflowDefinitionName=");
		sb.append(getWorkflowDefinitionName());
		sb.append(", workflowDefinitionVersion=");
		sb.append(getWorkflowDefinitionVersion());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(34);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.WorkflowDefinitionLink");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>workflowDefinitionLinkId</column-name><column-value><![CDATA[");
		sb.append(getWorkflowDefinitionLinkId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append(getUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>workflowDefinitionName</column-name><column-value><![CDATA[");
		sb.append(getWorkflowDefinitionName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>workflowDefinitionVersion</column-name><column-value><![CDATA[");
		sb.append(getWorkflowDefinitionVersion());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _workflowDefinitionLinkId;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private String _workflowDefinitionName;
	private int _workflowDefinitionVersion;
	private transient ExpandoBridge _expandoBridge;
}