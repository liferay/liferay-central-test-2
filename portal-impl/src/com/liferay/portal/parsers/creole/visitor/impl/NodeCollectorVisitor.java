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

package com.liferay.portal.parsers.creole.visitor.impl;

import com.liferay.portal.parsers.creole.ast.ASTNode;
import com.liferay.portal.parsers.creole.ast.WikiPageNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Miguel Pastor
 */
public abstract class NodeCollectorVisitor extends BaseASTVisitor {

	public List<ASTNode> collect(WikiPageNode start) {

		_collectedNodes = new ArrayList<ASTNode>();

		visit(start);

		List<ASTNode> collectedNodesCopy = new ArrayList<ASTNode>(
			_collectedNodes);

		_collectedNodes = null;

		return collectedNodesCopy;
	}

	protected void addNode(ASTNode node) {
		_collectedNodes.add(node);
	}

	private List<ASTNode> _collectedNodes = null;

}