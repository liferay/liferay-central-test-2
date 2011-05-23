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

	public ImageNode(CollectionNode altCollectionNode, String uri) {
		_altCollectionNode = altCollectionNode;
		_uri = uri;
	}

	public ImageNode(int token) {
		super(token);
	}

	public ImageNode(
		int tokenType, CollectionNode altCollectionNode, String uri) {

		this(tokenType);

		_altCollectionNode = altCollectionNode;
		_uri = uri;
	}

	public void accept(ASTVisitor astVisitor) {
		astVisitor.visit(this);
	}

	public CollectionNode getAltNode() {
		return _altCollectionNode;
	}

	public String getUri() {
		return _uri;
	}

	public boolean hasAltCollectionNode() {
		if (_altCollectionNode != null) {
			return true;
		}
		else {
			return false;
		}
	}

	public void setAltCollectionNode(CollectionNode altNode) {
		_altCollectionNode = altNode;
	}

	public void setUri(String uri) {
		_uri = uri;
	}

	private CollectionNode _altCollectionNode;
	private String _uri;

}