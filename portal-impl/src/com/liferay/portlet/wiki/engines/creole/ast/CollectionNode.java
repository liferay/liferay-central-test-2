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

package com.liferay.portlet.wiki.engines.creole.ast;

import com.liferay.portlet.wiki.engines.creole.visitor.ASTVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Miguel Pastor
 */
public class CollectionNode extends ASTNode {

	public CollectionNode() {
	}

	public CollectionNode(int token) {
		super(token);
	}

	public CollectionNode(List<ASTNode> nodes) {
		_nodes = nodes;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	public void add(ASTNode node) {
		_nodes.add(node);
	}

	public ASTNode get(int position) {
		return _nodes.get(position);
	}

	public List<ASTNode> getNodes() {
		return _nodes;
	}

	public int size() {
		return _nodes.size();
	}

	private List<ASTNode> _nodes = new ArrayList<ASTNode>();

}