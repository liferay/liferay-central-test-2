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

package com.liferay.portlet.ratings.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.model.RatingsStatsSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="RatingsStatsModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the RatingsStats table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RatingsStatsImpl
 * @see       com.liferay.portlet.ratings.model.RatingsStats
 * @see       com.liferay.portlet.ratings.model.RatingsStatsModel
 * @generated
 */
public class RatingsStatsModelImpl extends BaseModelImpl<RatingsStats> {
	public static final String TABLE_NAME = "RatingsStats";
	public static final Object[][] TABLE_COLUMNS = {
			{ "statsId", new Integer(Types.BIGINT) },
			{ "classNameId", new Integer(Types.BIGINT) },
			{ "classPK", new Integer(Types.BIGINT) },
			{ "totalEntries", new Integer(Types.INTEGER) },
			{ "totalScore", new Integer(Types.DOUBLE) },
			{ "averageScore", new Integer(Types.DOUBLE) }
		};
	public static final String TABLE_SQL_CREATE = "create table RatingsStats (statsId LONG not null primary key,classNameId LONG,classPK LONG,totalEntries INTEGER,totalScore DOUBLE,averageScore DOUBLE)";
	public static final String TABLE_SQL_DROP = "drop table RatingsStats";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.ratings.model.RatingsStats"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.ratings.model.RatingsStats"),
			true);

	public static RatingsStats toModel(RatingsStatsSoap soapModel) {
		RatingsStats model = new RatingsStatsImpl();

		model.setStatsId(soapModel.getStatsId());
		model.setClassNameId(soapModel.getClassNameId());
		model.setClassPK(soapModel.getClassPK());
		model.setTotalEntries(soapModel.getTotalEntries());
		model.setTotalScore(soapModel.getTotalScore());
		model.setAverageScore(soapModel.getAverageScore());

		return model;
	}

	public static List<RatingsStats> toModels(RatingsStatsSoap[] soapModels) {
		List<RatingsStats> models = new ArrayList<RatingsStats>(soapModels.length);

		for (RatingsStatsSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.ratings.model.RatingsStats"));

	public RatingsStatsModelImpl() {
	}

	public long getPrimaryKey() {
		return _statsId;
	}

	public void setPrimaryKey(long pk) {
		setStatsId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_statsId);
	}

	public long getStatsId() {
		return _statsId;
	}

	public void setStatsId(long statsId) {
		_statsId = statsId;
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

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;

		if (!_setOriginalClassPK) {
			_setOriginalClassPK = true;

			_originalClassPK = classPK;
		}
	}

	public long getOriginalClassPK() {
		return _originalClassPK;
	}

	public int getTotalEntries() {
		return _totalEntries;
	}

	public void setTotalEntries(int totalEntries) {
		_totalEntries = totalEntries;
	}

	public double getTotalScore() {
		return _totalScore;
	}

	public void setTotalScore(double totalScore) {
		_totalScore = totalScore;
	}

	public double getAverageScore() {
		return _averageScore;
	}

	public void setAverageScore(double averageScore) {
		_averageScore = averageScore;
	}

	public RatingsStats toEscapedModel() {
		if (isEscapedModel()) {
			return (RatingsStats)this;
		}
		else {
			RatingsStats model = new RatingsStatsImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setStatsId(getStatsId());
			model.setClassNameId(getClassNameId());
			model.setClassPK(getClassPK());
			model.setTotalEntries(getTotalEntries());
			model.setTotalScore(getTotalScore());
			model.setAverageScore(getAverageScore());

			model = (RatingsStats)Proxy.newProxyInstance(RatingsStats.class.getClassLoader(),
					new Class[] { RatingsStats.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(RatingsStats.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		RatingsStatsImpl clone = new RatingsStatsImpl();

		clone.setStatsId(getStatsId());
		clone.setClassNameId(getClassNameId());
		clone.setClassPK(getClassPK());
		clone.setTotalEntries(getTotalEntries());
		clone.setTotalScore(getTotalScore());
		clone.setAverageScore(getAverageScore());

		return clone;
	}

	public int compareTo(RatingsStats ratingsStats) {
		long pk = ratingsStats.getPrimaryKey();

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

		RatingsStats ratingsStats = null;

		try {
			ratingsStats = (RatingsStats)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = ratingsStats.getPrimaryKey();

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
		StringBundler sb = new StringBundler(13);

		sb.append("{statsId=");
		sb.append(getStatsId());
		sb.append(", classNameId=");
		sb.append(getClassNameId());
		sb.append(", classPK=");
		sb.append(getClassPK());
		sb.append(", totalEntries=");
		sb.append(getTotalEntries());
		sb.append(", totalScore=");
		sb.append(getTotalScore());
		sb.append(", averageScore=");
		sb.append(getAverageScore());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(22);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.ratings.model.RatingsStats");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>statsId</column-name><column-value><![CDATA[");
		sb.append(getStatsId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classNameId</column-name><column-value><![CDATA[");
		sb.append(getClassNameId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>classPK</column-name><column-value><![CDATA[");
		sb.append(getClassPK());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>totalEntries</column-name><column-value><![CDATA[");
		sb.append(getTotalEntries());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>totalScore</column-name><column-value><![CDATA[");
		sb.append(getTotalScore());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>averageScore</column-name><column-value><![CDATA[");
		sb.append(getAverageScore());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _statsId;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private int _totalEntries;
	private double _totalScore;
	private double _averageScore;
	private transient ExpandoBridge _expandoBridge;
}