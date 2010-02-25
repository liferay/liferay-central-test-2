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

package com.liferay.portlet.journal.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.model.JournalArticleResourceSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="JournalArticleResourceModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the JournalArticleResource table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalArticleResourceImpl
 * @see       com.liferay.portlet.journal.model.JournalArticleResource
 * @see       com.liferay.portlet.journal.model.JournalArticleResourceModel
 * @generated
 */
public class JournalArticleResourceModelImpl extends BaseModelImpl<JournalArticleResource> {
	public static final String TABLE_NAME = "JournalArticleResource";
	public static final Object[][] TABLE_COLUMNS = {
			{ "resourcePrimKey", new Integer(Types.BIGINT) },
			{ "groupId", new Integer(Types.BIGINT) },
			{ "articleId", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table JournalArticleResource (resourcePrimKey LONG not null primary key,groupId LONG,articleId VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table JournalArticleResource";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.journal.model.JournalArticleResource"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.journal.model.JournalArticleResource"),
			true);

	public static JournalArticleResource toModel(
		JournalArticleResourceSoap soapModel) {
		JournalArticleResource model = new JournalArticleResourceImpl();

		model.setResourcePrimKey(soapModel.getResourcePrimKey());
		model.setGroupId(soapModel.getGroupId());
		model.setArticleId(soapModel.getArticleId());

		return model;
	}

	public static List<JournalArticleResource> toModels(
		JournalArticleResourceSoap[] soapModels) {
		List<JournalArticleResource> models = new ArrayList<JournalArticleResource>(soapModels.length);

		for (JournalArticleResourceSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.journal.model.JournalArticleResource"));

	public JournalArticleResourceModelImpl() {
	}

	public long getPrimaryKey() {
		return _resourcePrimKey;
	}

	public void setPrimaryKey(long pk) {
		setResourcePrimKey(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_resourcePrimKey);
	}

	public long getResourcePrimKey() {
		return _resourcePrimKey;
	}

	public void setResourcePrimKey(long resourcePrimKey) {
		_resourcePrimKey = resourcePrimKey;
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

	public String getArticleId() {
		return GetterUtil.getString(_articleId);
	}

	public void setArticleId(String articleId) {
		_articleId = articleId;

		if (_originalArticleId == null) {
			_originalArticleId = articleId;
		}
	}

	public String getOriginalArticleId() {
		return GetterUtil.getString(_originalArticleId);
	}

	public JournalArticleResource toEscapedModel() {
		if (isEscapedModel()) {
			return (JournalArticleResource)this;
		}
		else {
			JournalArticleResource model = new JournalArticleResourceImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setResourcePrimKey(getResourcePrimKey());
			model.setGroupId(getGroupId());
			model.setArticleId(HtmlUtil.escape(getArticleId()));

			model = (JournalArticleResource)Proxy.newProxyInstance(JournalArticleResource.class.getClassLoader(),
					new Class[] { JournalArticleResource.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(0,
					JournalArticleResource.class.getName(), getResourcePrimKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		JournalArticleResourceImpl clone = new JournalArticleResourceImpl();

		clone.setResourcePrimKey(getResourcePrimKey());
		clone.setGroupId(getGroupId());
		clone.setArticleId(getArticleId());

		return clone;
	}

	public int compareTo(JournalArticleResource journalArticleResource) {
		long pk = journalArticleResource.getPrimaryKey();

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

		JournalArticleResource journalArticleResource = null;

		try {
			journalArticleResource = (JournalArticleResource)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = journalArticleResource.getPrimaryKey();

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
		StringBundler sb = new StringBundler(7);

		sb.append("{resourcePrimKey=");
		sb.append(getResourcePrimKey());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", articleId=");
		sb.append(getArticleId());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(13);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portlet.journal.model.JournalArticleResource");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>resourcePrimKey</column-name><column-value><![CDATA[");
		sb.append(getResourcePrimKey());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>articleId</column-name><column-value><![CDATA[");
		sb.append(getArticleId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _resourcePrimKey;
	private long _groupId;
	private long _originalGroupId;
	private boolean _setOriginalGroupId;
	private String _articleId;
	private String _originalArticleId;
	private transient ExpandoBridge _expandoBridge;
}