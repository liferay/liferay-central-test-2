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

package com.liferay.portlet.wiki.model;


/**
 * <a href="WikiNodeSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link WikiNode}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiNode
 * @generated
 */
public class WikiNodeWrapper implements WikiNode {
	public WikiNodeWrapper(WikiNode wikiNode) {
		_wikiNode = wikiNode;
	}

	public long getPrimaryKey() {
		return _wikiNode.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_wikiNode.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _wikiNode.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_wikiNode.setUuid(uuid);
	}

	public long getNodeId() {
		return _wikiNode.getNodeId();
	}

	public void setNodeId(long nodeId) {
		_wikiNode.setNodeId(nodeId);
	}

	public long getGroupId() {
		return _wikiNode.getGroupId();
	}

	public void setGroupId(long groupId) {
		_wikiNode.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _wikiNode.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_wikiNode.setCompanyId(companyId);
	}

	public long getUserId() {
		return _wikiNode.getUserId();
	}

	public void setUserId(long userId) {
		_wikiNode.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.SystemException {
		return _wikiNode.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_wikiNode.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _wikiNode.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_wikiNode.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _wikiNode.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_wikiNode.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _wikiNode.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_wikiNode.setModifiedDate(modifiedDate);
	}

	public java.lang.String getName() {
		return _wikiNode.getName();
	}

	public void setName(java.lang.String name) {
		_wikiNode.setName(name);
	}

	public java.lang.String getDescription() {
		return _wikiNode.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_wikiNode.setDescription(description);
	}

	public java.util.Date getLastPostDate() {
		return _wikiNode.getLastPostDate();
	}

	public void setLastPostDate(java.util.Date lastPostDate) {
		_wikiNode.setLastPostDate(lastPostDate);
	}

	public com.liferay.portlet.wiki.model.WikiNode toEscapedModel() {
		return _wikiNode.toEscapedModel();
	}

	public boolean isNew() {
		return _wikiNode.isNew();
	}

	public boolean setNew(boolean n) {
		return _wikiNode.setNew(n);
	}

	public boolean isCachedModel() {
		return _wikiNode.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_wikiNode.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _wikiNode.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_wikiNode.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _wikiNode.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _wikiNode.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_wikiNode.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _wikiNode.clone();
	}

	public int compareTo(com.liferay.portlet.wiki.model.WikiNode wikiNode) {
		return _wikiNode.compareTo(wikiNode);
	}

	public int hashCode() {
		return _wikiNode.hashCode();
	}

	public java.lang.String toString() {
		return _wikiNode.toString();
	}

	public java.lang.String toXmlString() {
		return _wikiNode.toXmlString();
	}

	public WikiNode getWrappedWikiNode() {
		return _wikiNode;
	}

	private WikiNode _wikiNode;
}