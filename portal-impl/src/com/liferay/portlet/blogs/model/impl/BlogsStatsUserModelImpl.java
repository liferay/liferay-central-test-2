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

package com.liferay.portlet.blogs.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.blogs.model.BlogsStatsUser;
import com.liferay.portlet.blogs.model.BlogsStatsUserSoap;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="BlogsStatsUserModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the BlogsStatsUser table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BlogsStatsUserImpl
 * @see       com.liferay.portlet.blogs.model.BlogsStatsUser
 * @see       com.liferay.portlet.blogs.model.BlogsStatsUserModel
 * @generated
 */
public class BlogsStatsUserModelImpl extends BaseModelImpl<BlogsStatsUser> {
	public static final String TABLE_NAME = "BlogsStatsUser";
	public static final Object[][] TABLE_COLUMNS = {
			{ "statsUserId", new Integer(Types.BIGINT) },
			{ "groupId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "entryCount", new Integer(Types.INTEGER) },
			{ "lastPostDate", new Integer(Types.TIMESTAMP) },
			{ "ratingsTotalEntries", new Integer(Types.INTEGER) },
			{ "ratingsTotalScore", new Integer(Types.DOUBLE) },
			{ "ratingsAverageScore", new Integer(Types.DOUBLE) }
		};
	public static final String TABLE_SQL_CREATE = "create table BlogsStatsUser (statsUserId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,entryCount INTEGER,lastPostDate DATE null,ratingsTotalEntries INTEGER,ratingsTotalScore DOUBLE,ratingsAverageScore DOUBLE)";
	public static final String TABLE_SQL_DROP = "drop table BlogsStatsUser";
	public static final String ORDER_BY_JPQL = " ORDER BY blogsStatsUser.entryCount DESC";
	public static final String ORDER_BY_SQL = " ORDER BY BlogsStatsUser.entryCount DESC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.blogs.model.BlogsStatsUser"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.blogs.model.BlogsStatsUser"),
			true);

	public static BlogsStatsUser toModel(BlogsStatsUserSoap soapModel) {
		BlogsStatsUser model = new BlogsStatsUserImpl();

		model.setStatsUserId(soapModel.getStatsUserId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setEntryCount(soapModel.getEntryCount());
		model.setLastPostDate(soapModel.getLastPostDate());
		model.setRatingsTotalEntries(soapModel.getRatingsTotalEntries());
		model.setRatingsTotalScore(soapModel.getRatingsTotalScore());
		model.setRatingsAverageScore(soapModel.getRatingsAverageScore());

		return model;
	}

	public static List<BlogsStatsUser> toModels(BlogsStatsUserSoap[] soapModels) {
		List<BlogsStatsUser> models = new ArrayList<BlogsStatsUser>(soapModels.length);

		for (BlogsStatsUserSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.blogs.model.BlogsStatsUser"));

	public BlogsStatsUserModelImpl() {
	}

	public long getPrimaryKey() {
		return _statsUserId;
	}

	public void setPrimaryKey(long pk) {
		setStatsUserId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_statsUserId);
	}

	public long getStatsUserId() {
		return _statsUserId;
	}

	public void setStatsUserId(long statsUserId) {
		_statsUserId = statsUserId;
	}

	public String getStatsUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getStatsUserId(), "uuid", _statsUserUuid);
	}

	public void setStatsUserUuid(String statsUserUuid) {
		_statsUserUuid = statsUserUuid;
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

		if (!_setOriginalUserId) {
			_setOriginalUserId = true;

			_originalUserId = userId;
		}
	}

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	public int getEntryCount() {
		return _entryCount;
	}

	public void setEntryCount(int entryCount) {
		_entryCount = entryCount;
	}

	public Date getLastPostDate() {
		return _lastPostDate;
	}

	public void setLastPostDate(Date lastPostDate) {
		_lastPostDate = lastPostDate;
	}

	public int getRatingsTotalEntries() {
		return _ratingsTotalEntries;
	}

	public void setRatingsTotalEntries(int ratingsTotalEntries) {
		_ratingsTotalEntries = ratingsTotalEntries;
	}

	public double getRatingsTotalScore() {
		return _ratingsTotalScore;
	}

	public void setRatingsTotalScore(double ratingsTotalScore) {
		_ratingsTotalScore = ratingsTotalScore;
	}

	public double getRatingsAverageScore() {
		return _ratingsAverageScore;
	}

	public void setRatingsAverageScore(double ratingsAverageScore) {
		_ratingsAverageScore = ratingsAverageScore;
	}

	public BlogsStatsUser toEscapedModel() {
		if (isEscapedModel()) {
			return (BlogsStatsUser)this;
		}
		else {
			BlogsStatsUser model = new BlogsStatsUserImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setStatsUserId(getStatsUserId());
			model.setGroupId(getGroupId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setEntryCount(getEntryCount());
			model.setLastPostDate(getLastPostDate());
			model.setRatingsTotalEntries(getRatingsTotalEntries());
			model.setRatingsTotalScore(getRatingsTotalScore());
			model.setRatingsAverageScore(getRatingsAverageScore());

			model = (BlogsStatsUser)Proxy.newProxyInstance(BlogsStatsUser.class.getClassLoader(),
					new Class[] { BlogsStatsUser.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(BlogsStatsUser.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		BlogsStatsUserImpl clone = new BlogsStatsUserImpl();

		clone.setStatsUserId(getStatsUserId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setEntryCount(getEntryCount());
		clone.setLastPostDate(getLastPostDate());
		clone.setRatingsTotalEntries(getRatingsTotalEntries());
		clone.setRatingsTotalScore(getRatingsTotalScore());
		clone.setRatingsAverageScore(getRatingsAverageScore());

		return clone;
	}

	public int compareTo(BlogsStatsUser blogsStatsUser) {
		int value = 0;

		if (getEntryCount() < blogsStatsUser.getEntryCount()) {
			value = -1;
		}
		else if (getEntryCount() > blogsStatsUser.getEntryCount()) {
			value = 1;
		}
		else {
			value = 0;
		}

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

		BlogsStatsUser blogsStatsUser = null;

		try {
			blogsStatsUser = (BlogsStatsUser)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = blogsStatsUser.getPrimaryKey();

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
		StringBundler sb = new StringBundler(19);

		sb.append("{statsUserId=");
		sb.append(getStatsUserId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", entryCount=");
		sb.append(getEntryCount());
		sb.append(", lastPostDate=");
		sb.append(getLastPostDate());
		sb.append(", ratingsTotalEntries=");
		sb.append(getRatingsTotalEntries());
		sb.append(", ratingsTotalScore=");
		sb.append(getRatingsTotalScore());
		sb.append(", ratingsAverageScore=");
		sb.append(getRatingsAverageScore());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(31);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.blogs.model.BlogsStatsUser");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>statsUserId</column-name><column-value><![CDATA[");
		sb.append(getStatsUserId());
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
			"<column><column-name>entryCount</column-name><column-value><![CDATA[");
		sb.append(getEntryCount());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lastPostDate</column-name><column-value><![CDATA[");
		sb.append(getLastPostDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>ratingsTotalEntries</column-name><column-value><![CDATA[");
		sb.append(getRatingsTotalEntries());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>ratingsTotalScore</column-name><column-value><![CDATA[");
		sb.append(getRatingsTotalScore());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>ratingsAverageScore</column-name><column-value><![CDATA[");
		sb.append(getRatingsAverageScore());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _statsUserId;
	private String _statsUserUuid;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private int _entryCount;
	private Date _lastPostDate;
	private int _ratingsTotalEntries;
	private double _ratingsTotalScore;
	private double _ratingsAverageScore;
	private transient ExpandoBridge _expandoBridge;
}