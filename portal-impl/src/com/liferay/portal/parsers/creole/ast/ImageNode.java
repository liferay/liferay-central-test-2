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

package com.liferay.portal.parsers.creole.ast;

import com.liferay.portal.parsers.creole.visitor.ASTVisitor;

/**
 * @author Miguel Pastor
 */
public class ImageNode extends ASTNode {

	public ImageNode() {
	}

	public ImageNode(int token) {
		super(token);
	}

	public ImageNode(int tokenType, CollectionNode altNode, String uri) {
		this(tokenType);

		_altNode = altNode;
		_uri = uri;
	}

	public ImageNode(CollectionNode altNode, String uri) {
		_altNode = altNode;
		_uri = uri;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	public boolean hasAltNode() {
		return _altNode != null;
	}

	public CollectionNode getAltNode() {
		return _altNode;
	}

	public String getUri() {
		return _uri;
	}

	public void setAltNode(CollectionNode altNode) {
		_altNode = altNode;
	}

	public void setUri(String uri) {
		_uri = uri;
	}

	private CollectionNode _altNode;
	private String _uri;

}