/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.engines.creole.ast.link;

/**
 * @author Miguel Pastor
 */
public abstract class InterwikiLinkNode extends LinkNode {

	public InterwikiLinkNode() {

	}

	public InterwikiLinkNode(int token) {
		super(token);
	}

	public InterwikiLinkNode(String wikiname) {
		_wikiname = wikiname;
	}

	public InterwikiLinkNode(String uri, String wikiname) {
		_uri = uri;
		_wikiname = wikiname;
	}

	public InterwikiLinkNode(int token, String uri, String wikiname) {
		this(token);

		_uri = uri;
		_wikiname = wikiname;
	}

	public String getUri() {
		return _uri;
	}

	public String getWikiname() {
		return _wikiname;
	}

	public void setUri(String uri) {
		_uri = uri;
	}

	private String _uri;
	private String _wikiname;

}