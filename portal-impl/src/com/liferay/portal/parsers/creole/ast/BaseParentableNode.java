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

import java.util.List;

/**
 * This class offers some funcionallity to those nodes which need to represent
 * some relationship parent-child or similar
 *
 * @author Miguel Pastor
 */
public abstract class BaseParentableNode extends ASTNode {

	public BaseParentableNode() {
	}

	public BaseParentableNode(int tokenType) {
		super(tokenType);
	}

	public BaseParentableNode(CollectionNode collectionNode) {
		_collectionNode = collectionNode;
	}

	public void addChildNode(ASTNode node) {
		_collectionNode.add(node);
	}

	public ASTNode getChildNode(int position) {
		return _collectionNode.get(position);
	}

	public List<ASTNode> getChildNodes() {
		return _collectionNode.getNodes();
	}

	public int getNumOfChildNodes() {
		return _collectionNode.size();
	}

	public void setChildNodes(CollectionNode collectionNode) {
		_collectionNode = collectionNode;
	}

	private CollectionNode _collectionNode = new CollectionNode();

}