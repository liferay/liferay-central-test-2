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

package com.liferay.portlet.journal.model.impl;

import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PropsUtil;

import com.liferay.portlet.journal.service.persistence.JournalContentSearchPK;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

/**
 * <a href="JournalContentSearchModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalContentSearchModelImpl extends BaseModelImpl {
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalContentSearch"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_PORTLETID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalContentSearch.portletId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_LAYOUTID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalContentSearch.layoutId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_OWNERID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalContentSearch.ownerId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_ARTICLEID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalContentSearch.articleId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalContentSearch.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_GROUPID = GetterUtil.getBoolean(PropsUtil.get(
				"xss.allow.com.liferay.portlet.journal.model.JournalContentSearch.groupId"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.journal.model.JournalContentSearchModel"));

	public JournalContentSearchModelImpl() {
	}

	public JournalContentSearchPK getPrimaryKey() {
		return new JournalContentSearchPK(_portletId, _layoutId, _ownerId,
			_articleId);
	}

	public void setPrimaryKey(JournalContentSearchPK pk) {
		setPortletId(pk.portletId);
		setLayoutId(pk.layoutId);
		setOwnerId(pk.ownerId);
		setArticleId(pk.articleId);
	}

	public String getPortletId() {
		return GetterUtil.getString(_portletId);
	}

	public void setPortletId(String portletId) {
		if (((portletId == null) && (_portletId != null)) ||
				((portletId != null) && (_portletId == null)) ||
				((portletId != null) && (_portletId != null) &&
				!portletId.equals(_portletId))) {
			if (!XSS_ALLOW_PORTLETID) {
				portletId = XSSUtil.strip(portletId);
			}

			_portletId = portletId;
		}
	}

	public String getLayoutId() {
		return GetterUtil.getString(_layoutId);
	}

	public void setLayoutId(String layoutId) {
		if (((layoutId == null) && (_layoutId != null)) ||
				((layoutId != null) && (_layoutId == null)) ||
				((layoutId != null) && (_layoutId != null) &&
				!layoutId.equals(_layoutId))) {
			if (!XSS_ALLOW_LAYOUTID) {
				layoutId = XSSUtil.strip(layoutId);
			}

			_layoutId = layoutId;
		}
	}

	public String getOwnerId() {
		return GetterUtil.getString(_ownerId);
	}

	public void setOwnerId(String ownerId) {
		if (((ownerId == null) && (_ownerId != null)) ||
				((ownerId != null) && (_ownerId == null)) ||
				((ownerId != null) && (_ownerId != null) &&
				!ownerId.equals(_ownerId))) {
			if (!XSS_ALLOW_OWNERID) {
				ownerId = XSSUtil.strip(ownerId);
			}

			_ownerId = ownerId;
		}
	}

	public String getArticleId() {
		return GetterUtil.getString(_articleId);
	}

	public void setArticleId(String articleId) {
		if (((articleId == null) && (_articleId != null)) ||
				((articleId != null) && (_articleId == null)) ||
				((articleId != null) && (_articleId != null) &&
				!articleId.equals(_articleId))) {
			if (!XSS_ALLOW_ARTICLEID) {
				articleId = XSSUtil.strip(articleId);
			}

			_articleId = articleId;
		}
	}

	public String getCompanyId() {
		return GetterUtil.getString(_companyId);
	}

	public void setCompanyId(String companyId) {
		if (((companyId == null) && (_companyId != null)) ||
				((companyId != null) && (_companyId == null)) ||
				((companyId != null) && (_companyId != null) &&
				!companyId.equals(_companyId))) {
			if (!XSS_ALLOW_COMPANYID) {
				companyId = XSSUtil.strip(companyId);
			}

			_companyId = companyId;
		}
	}

	public String getGroupId() {
		return GetterUtil.getString(_groupId);
	}

	public void setGroupId(String groupId) {
		if (((groupId == null) && (_groupId != null)) ||
				((groupId != null) && (_groupId == null)) ||
				((groupId != null) && (_groupId != null) &&
				!groupId.equals(_groupId))) {
			if (!XSS_ALLOW_GROUPID) {
				groupId = XSSUtil.strip(groupId);
			}

			_groupId = groupId;
		}
	}

	public Object clone() {
		JournalContentSearchImpl clone = new JournalContentSearchImpl();
		clone.setPortletId(getPortletId());
		clone.setLayoutId(getLayoutId());
		clone.setOwnerId(getOwnerId());
		clone.setArticleId(getArticleId());
		clone.setCompanyId(getCompanyId());
		clone.setGroupId(getGroupId());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		JournalContentSearchImpl journalContentSearch = (JournalContentSearchImpl)obj;
		JournalContentSearchPK pk = journalContentSearch.getPrimaryKey();

		return getPrimaryKey().compareTo(pk);
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		JournalContentSearchImpl journalContentSearch = null;

		try {
			journalContentSearch = (JournalContentSearchImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		JournalContentSearchPK pk = journalContentSearch.getPrimaryKey();

		if (getPrimaryKey().equals(pk)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return getPrimaryKey().hashCode();
	}

	private String _portletId;
	private String _layoutId;
	private String _ownerId;
	private String _articleId;
	private String _companyId;
	private String _groupId;
}