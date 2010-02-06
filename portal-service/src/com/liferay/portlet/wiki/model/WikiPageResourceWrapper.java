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
 * <a href="WikiPageResourceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link WikiPageResource}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       WikiPageResource
 * @generated
 */
public class WikiPageResourceWrapper implements WikiPageResource {
	public WikiPageResourceWrapper(WikiPageResource wikiPageResource) {
		_wikiPageResource = wikiPageResource;
	}

	public long getPrimaryKey() {
		return _wikiPageResource.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_wikiPageResource.setPrimaryKey(pk);
	}

	public long getResourcePrimKey() {
		return _wikiPageResource.getResourcePrimKey();
	}

	public void setResourcePrimKey(long resourcePrimKey) {
		_wikiPageResource.setResourcePrimKey(resourcePrimKey);
	}

	public long getNodeId() {
		return _wikiPageResource.getNodeId();
	}

	public void setNodeId(long nodeId) {
		_wikiPageResource.setNodeId(nodeId);
	}

	public java.lang.String getTitle() {
		return _wikiPageResource.getTitle();
	}

	public void setTitle(java.lang.String title) {
		_wikiPageResource.setTitle(title);
	}

	public com.liferay.portlet.wiki.model.WikiPageResource toEscapedModel() {
		return _wikiPageResource.toEscapedModel();
	}

	public boolean isNew() {
		return _wikiPageResource.isNew();
	}

	public boolean setNew(boolean n) {
		return _wikiPageResource.setNew(n);
	}

	public boolean isCachedModel() {
		return _wikiPageResource.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_wikiPageResource.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _wikiPageResource.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_wikiPageResource.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _wikiPageResource.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _wikiPageResource.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_wikiPageResource.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _wikiPageResource.clone();
	}

	public int compareTo(
		com.liferay.portlet.wiki.model.WikiPageResource wikiPageResource) {
		return _wikiPageResource.compareTo(wikiPageResource);
	}

	public int hashCode() {
		return _wikiPageResource.hashCode();
	}

	public java.lang.String toString() {
		return _wikiPageResource.toString();
	}

	public java.lang.String toXmlString() {
		return _wikiPageResource.toXmlString();
	}

	public WikiPageResource getWrappedWikiPageResource() {
		return _wikiPageResource;
	}

	private WikiPageResource _wikiPageResource;
}