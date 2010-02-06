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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="WikiPageResourceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * {@link com.liferay.portlet.wiki.service.http.WikiPageResourceServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.wiki.service.http.WikiPageResourceServiceSoap
 * @generated
 */
public class WikiPageResourceSoap implements Serializable {
	public static WikiPageResourceSoap toSoapModel(WikiPageResource model) {
		WikiPageResourceSoap soapModel = new WikiPageResourceSoap();

		soapModel.setResourcePrimKey(model.getResourcePrimKey());
		soapModel.setNodeId(model.getNodeId());
		soapModel.setTitle(model.getTitle());

		return soapModel;
	}

	public static WikiPageResourceSoap[] toSoapModels(WikiPageResource[] models) {
		WikiPageResourceSoap[] soapModels = new WikiPageResourceSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static WikiPageResourceSoap[][] toSoapModels(
		WikiPageResource[][] models) {
		WikiPageResourceSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new WikiPageResourceSoap[models.length][models[0].length];
		}
		else {
			soapModels = new WikiPageResourceSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static WikiPageResourceSoap[] toSoapModels(
		List<WikiPageResource> models) {
		List<WikiPageResourceSoap> soapModels = new ArrayList<WikiPageResourceSoap>(models.size());

		for (WikiPageResource model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new WikiPageResourceSoap[soapModels.size()]);
	}

	public WikiPageResourceSoap() {
	}

	public long getPrimaryKey() {
		return _resourcePrimKey;
	}

	public void setPrimaryKey(long pk) {
		setResourcePrimKey(pk);
	}

	public long getResourcePrimKey() {
		return _resourcePrimKey;
	}

	public void setResourcePrimKey(long resourcePrimKey) {
		_resourcePrimKey = resourcePrimKey;
	}

	public long getNodeId() {
		return _nodeId;
	}

	public void setNodeId(long nodeId) {
		_nodeId = nodeId;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private long _resourcePrimKey;
	private long _nodeId;
	private String _title;
}