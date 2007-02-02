/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.tags.model.impl;

import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.sql.Types;

/**
 * <a href="TagsSourceModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class TagsSourceModelImpl extends BaseModelImpl {
	public static String TABLE_NAME = "TagsSource";
	public static Object[][] TABLE_COLUMNS = {
			{ "sourceId", new Integer(Types.BIGINT) },
			{ "parentSourceId", new Integer(Types.BIGINT) },
			{ "name", new Integer(Types.VARCHAR) },
			{ "acronym", new Integer(Types.VARCHAR) }
		};
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.tags.model.TagsSource"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_NAME = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.tags.model.TagsSource.name"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_ACRONYM = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.tags.model.TagsSource.acronym"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.tags.model.TagsSourceModel"));

	public TagsSourceModelImpl() {
	}

	public long getPrimaryKey() {
		return _sourceId;
	}

	public void setPrimaryKey(long pk) {
		setSourceId(pk);
	}

	public long getSourceId() {
		return _sourceId;
	}

	public void setSourceId(long sourceId) {
		if (sourceId != _sourceId) {
			_sourceId = sourceId;
		}
	}

	public long getParentSourceId() {
		return _parentSourceId;
	}

	public void setParentSourceId(long parentSourceId) {
		if (parentSourceId != _parentSourceId) {
			_parentSourceId = parentSourceId;
		}
	}

	public String getName() {
		return GetterUtil.getString(_name);
	}

	public void setName(String name) {
		if (((name == null) && (_name != null)) ||
				((name != null) && (_name == null)) ||
				((name != null) && (_name != null) && !name.equals(_name))) {
			if (!XSS_ALLOW_NAME) {
				name = XSSUtil.strip(name);
			}

			_name = name;
		}
	}

	public String getAcronym() {
		return GetterUtil.getString(_acronym);
	}

	public void setAcronym(String acronym) {
		if (((acronym == null) && (_acronym != null)) ||
				((acronym != null) && (_acronym == null)) ||
				((acronym != null) && (_acronym != null) &&
				!acronym.equals(_acronym))) {
			if (!XSS_ALLOW_ACRONYM) {
				acronym = XSSUtil.strip(acronym);
			}

			_acronym = acronym;
		}
	}

	public Object clone() {
		TagsSourceImpl clone = new TagsSourceImpl();
		clone.setSourceId(getSourceId());
		clone.setParentSourceId(getParentSourceId());
		clone.setName(getName());
		clone.setAcronym(getAcronym());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		TagsSourceImpl tagsSource = (TagsSourceImpl)obj;
		long pk = tagsSource.getPrimaryKey();

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

		TagsSourceImpl tagsSource = null;

		try {
			tagsSource = (TagsSourceImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = tagsSource.getPrimaryKey();

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

	private long _sourceId;
	private long _parentSourceId;
	private String _name;
	private String _acronym;
}