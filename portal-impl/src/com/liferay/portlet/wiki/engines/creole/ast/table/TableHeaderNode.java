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

package com.liferay.portlet.wiki.engines.creole.ast.table;

import com.liferay.portlet.wiki.engines.creole.ast.CollectionNode;
import com.liferay.portlet.wiki.engines.creole.visitor.ASTVisitor;

/**
 * @author Miguel Pastor
 */

public class TableHeaderNode extends TableCellNode {

	public TableHeaderNode() {
	}

	public TableHeaderNode(int token) {
		super(token);
	}

	public TableHeaderNode(CollectionNode cellsNode) {
		super(cellsNode);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

}