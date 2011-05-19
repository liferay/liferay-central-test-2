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

/**
 * @author Miguel Pastor
 */
public class HeadingNode extends ASTNode {

	public HeadingNode(int tokenType) {
		super(tokenType);
	}

	public HeadingNode(int tokenType, String content, int level) {
		this(tokenType);

		_content = content;
		_level = level;
	}

	public HeadingNode(String content, int level) {
		_content = content;
		_level = level;
	}

	@Override
	public void accept(ASTVisitor visitor) {
		visitor.visit(this);
	}

	public String getContent() {
		return _content;
	}

	public int getLevel() {
		return _level;
	}

	public void setContent(String content) {
		_content = content;
	}

	public void setLevel(int level) {
		_level = level;
	}

	private String _content;
	private int _level;

}