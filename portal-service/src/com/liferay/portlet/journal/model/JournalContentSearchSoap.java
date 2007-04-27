/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.model;

import com.liferay.portlet.journal.service.persistence.JournalContentSearchPK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="JournalContentSearchSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by <code>com.liferay.portlet.journal.service.http.JournalContentSearchServiceSoap</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.journal.service.http.JournalContentSearchServiceSoap
 *
 */
public class JournalContentSearchSoap implements Serializable {
	public static JournalContentSearchSoap toSoapModel(
		JournalContentSearch model) {
		JournalContentSearchSoap soapModel = new JournalContentSearchSoap();
		soapModel.setPortletId(model.getPortletId());
		soapModel.setLayoutId(model.getLayoutId());
		soapModel.setOwnerId(model.getOwnerId());
		soapModel.setArticleId(model.getArticleId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setGroupId(model.getGroupId());

		return soapModel;
	}

	public static JournalContentSearchSoap[] toSoapModels(List models) {
		List soapModels = new ArrayList(models.size());

		for (int i = 0; i < models.size(); i++) {
			JournalContentSearch model = (JournalContentSearch)models.get(i);
			soapModels.add(toSoapModel(model));
		}

		return (JournalContentSearchSoap[])soapModels.toArray(new JournalContentSearchSoap[0]);
	}

	public JournalContentSearchSoap() {
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
		return _portletId;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	public String getLayoutId() {
		return _layoutId;
	}

	public void setLayoutId(String layoutId) {
		_layoutId = layoutId;
	}

	public String getOwnerId() {
		return _ownerId;
	}

	public void setOwnerId(String ownerId) {
		_ownerId = ownerId;
	}

	public String getArticleId() {
		return _articleId;
	}

	public void setArticleId(String articleId) {
		_articleId = articleId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	private String _portletId;
	private String _layoutId;
	private String _ownerId;
	private String _articleId;
	private long _companyId;
	private long _groupId;
}