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

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;

/**
 * @author Miguel Pastor
 */
public class WikiPageNode extends BaseParentableNode {

	public WikiPageNode(int tokenType) {
		this(new CommonToken(tokenType), null);
	}

	public WikiPageNode(CollectionNode sectionsNode) {
		super(sectionsNode);
	}

	public WikiPageNode(Token token, CollectionNode sectionsNode) {
		super(sectionsNode);
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

}