/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Jorge Ferrer
 */
public class TreeNodeView {

	public TreeNodeView(int id) {
		_id = id;
	}

	public int getDepth() {
		return _depth;
	}

	public void setDepth(int depth) {
		_depth = depth;
	}

	public String getHref() {
		return _href;
	}

	public void setHref(String href) {
		_href = href;
	}

	public long getId() {
		return _id;
	}

	public void setId(long id) {
		_id = id;
	}

	public String getImg() {
		return _img;
	}

	public void setImg(String img) {
		_img = img;
	}

	public String getLs() {
		return _ls;
	}

	public void setLs(String ls) {
		_ls = ls;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getObjId() {
		return _objId;
	}

	public void setObjId(String objId) {
		_objId = objId;
	}

	public long getParentId() {
		return _parentId;
	}

	public void setParentId(long parentId) {
		_parentId = parentId;
	}

	private int _depth;
	private String _href = "javascript:;";
	private long _id;
	private String _img = StringPool.BLANK;
	private String _ls = StringPool.BLANK;
	private String _name = StringPool.BLANK;
	private String _objId = StringPool.BLANK;
	private long _parentId;

}