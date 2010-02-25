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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Release;
import com.liferay.portal.model.ReleaseSoap;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="ReleaseModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the Release_ table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ReleaseImpl
 * @see       com.liferay.portal.model.Release
 * @see       com.liferay.portal.model.ReleaseModel
 * @generated
 */
public class ReleaseModelImpl extends BaseModelImpl<Release> {
	public static final String TABLE_NAME = "Release_";
	public static final Object[][] TABLE_COLUMNS = {
			{ "releaseId", new Integer(Types.BIGINT) },
			{ "createDate", new Integer(Types.TIMESTAMP) },
			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			{ "servletContextName", new Integer(Types.VARCHAR) },
			{ "buildNumber", new Integer(Types.INTEGER) },
			{ "buildDate", new Integer(Types.TIMESTAMP) },
			{ "verified", new Integer(Types.BOOLEAN) },
			{ "testString", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table Release_ (releaseId LONG not null primary key,createDate DATE null,modifiedDate DATE null,servletContextName VARCHAR(75) null,buildNumber INTEGER,buildDate DATE null,verified BOOLEAN,testString VARCHAR(1024) null)";
	public static final String TABLE_SQL_DROP = "drop table Release_";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.Release"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.Release"),
			true);

	public static Release toModel(ReleaseSoap soapModel) {
		Release model = new ReleaseImpl();

		model.setReleaseId(soapModel.getReleaseId());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setServletContextName(soapModel.getServletContextName());
		model.setBuildNumber(soapModel.getBuildNumber());
		model.setBuildDate(soapModel.getBuildDate());
		model.setVerified(soapModel.getVerified());
		model.setTestString(soapModel.getTestString());

		return model;
	}

	public static List<Release> toModels(ReleaseSoap[] soapModels) {
		List<Release> models = new ArrayList<Release>(soapModels.length);

		for (ReleaseSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.Release"));

	public ReleaseModelImpl() {
	}

	public long getPrimaryKey() {
		return _releaseId;
	}

	public void setPrimaryKey(long pk) {
		setReleaseId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_releaseId);
	}

	public long getReleaseId() {
		return _releaseId;
	}

	public void setReleaseId(long releaseId) {
		_releaseId = releaseId;
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

	public String getServletContextName() {
		return GetterUtil.getString(_servletContextName);
	}

	public void setServletContextName(String servletContextName) {
		_servletContextName = servletContextName;

		if (_originalServletContextName == null) {
			_originalServletContextName = servletContextName;
		}
	}

	public String getOriginalServletContextName() {
		return GetterUtil.getString(_originalServletContextName);
	}

	public int getBuildNumber() {
		return _buildNumber;
	}

	public void setBuildNumber(int buildNumber) {
		_buildNumber = buildNumber;
	}

	public Date getBuildDate() {
		return _buildDate;
	}

	public void setBuildDate(Date buildDate) {
		_buildDate = buildDate;
	}

	public boolean getVerified() {
		return _verified;
	}

	public boolean isVerified() {
		return _verified;
	}

	public void setVerified(boolean verified) {
		_verified = verified;
	}

	public String getTestString() {
		return GetterUtil.getString(_testString);
	}

	public void setTestString(String testString) {
		_testString = testString;
	}

	public Release toEscapedModel() {
		if (isEscapedModel()) {
			return (Release)this;
		}
		else {
			Release model = new ReleaseImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setReleaseId(getReleaseId());
			model.setCreateDate(getCreateDate());
			model.setModifiedDate(getModifiedDate());
			model.setServletContextName(HtmlUtil.escape(getServletContextName()));
			model.setBuildNumber(getBuildNumber());
			model.setBuildDate(getBuildDate());
			model.setVerified(getVerified());
			model.setTestString(HtmlUtil.escape(getTestString()));

			model = (Release)Proxy.newProxyInstance(Release.class.getClassLoader(),
					new Class[] { Release.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(0,
					Release.class.getName(), getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		ReleaseImpl clone = new ReleaseImpl();

		clone.setReleaseId(getReleaseId());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setServletContextName(getServletContextName());
		clone.setBuildNumber(getBuildNumber());
		clone.setBuildDate(getBuildDate());
		clone.setVerified(getVerified());
		clone.setTestString(getTestString());

		return clone;
	}

	public int compareTo(Release release) {
		long pk = release.getPrimaryKey();

		if (getPrimaryKey() < pk) {
			return -1;
		}
		else if (getPrimaryKey() > pk) {
			return 1;
		}
		else {
			return 0;
		}
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		Release release = null;

		try {
			release = (Release)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = release.getPrimaryKey();

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
		StringBundler sb = new StringBundler(17);

		sb.append("{releaseId=");
		sb.append(getReleaseId());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", servletContextName=");
		sb.append(getServletContextName());
		sb.append(", buildNumber=");
		sb.append(getBuildNumber());
		sb.append(", buildDate=");
		sb.append(getBuildDate());
		sb.append(", verified=");
		sb.append(getVerified());
		sb.append(", testString=");
		sb.append(getTestString());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(28);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.Release");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>releaseId</column-name><column-value><![CDATA[");
		sb.append(getReleaseId());
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
			"<column><column-name>servletContextName</column-name><column-value><![CDATA[");
		sb.append(getServletContextName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>buildNumber</column-name><column-value><![CDATA[");
		sb.append(getBuildNumber());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>buildDate</column-name><column-value><![CDATA[");
		sb.append(getBuildDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>verified</column-name><column-value><![CDATA[");
		sb.append(getVerified());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>testString</column-name><column-value><![CDATA[");
		sb.append(getTestString());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _releaseId;
	private Date _createDate;
	private Date _modifiedDate;
	private String _servletContextName;
	private String _originalServletContextName;
	private int _buildNumber;
	private Date _buildDate;
	private boolean _verified;
	private String _testString;
	private transient ExpandoBridge _expandoBridge;
}