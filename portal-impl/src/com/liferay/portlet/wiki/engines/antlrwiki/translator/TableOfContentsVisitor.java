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

package com.liferay.portlet.wiki.engines.antlrwiki.translator;

import com.liferay.portal.kernel.util.TreeNode;
import com.liferay.portal.parsers.creole.ast.HeadingNode;
import com.liferay.portal.parsers.creole.ast.WikiPageNode;
import com.liferay.portal.parsers.creole.visitor.impl.BaseASTVisitor;

import java.util.List;

/**
 * @author Miguel Pastor
 */
public class TableOfContentsVisitor extends BaseASTVisitor {

	public TreeNode<HeadingNode> compose(WikiPageNode wikiPageNode) {
		_tableOfContents = new TreeNode<HeadingNode>(
			(new HeadingNode(Integer.MIN_VALUE)));

		visit(wikiPageNode);

		return _tableOfContents;
	}

	public void visit(HeadingNode headingNode) {
		addNodeToTree(_tableOfContents, headingNode);
	}

	protected void addNode(
		TreeNode<HeadingNode> treeNode, HeadingNode headingNode) {

		if (headingNode.getLevel() <= treeNode.getValue().getLevel()) {

			// Add as a sibling

			treeNode.getParentNode().addChildNode(headingNode);
		}
		else {
			treeNode.addChildNode(headingNode);
		}
	}

	protected boolean addNodeToTree(
		TreeNode<HeadingNode> treeNode, HeadingNode headingNode) {

		if (!continueRecursion(treeNode, headingNode)) {
			addNode(treeNode, headingNode);

			// Stop recursion

			return false;
		}

		List<TreeNode<HeadingNode>> treeNodes = treeNode.getChildNodes();

		int size = treeNode.getChildNodes().size();

		for (int i = size - 1; i >= 0; --i) {
			return addNodeToTree(treeNodes.get(i), headingNode);
		}

		return true;
	}

	protected boolean continueRecursion(
		TreeNode<HeadingNode> treeNode, HeadingNode headingNode) {

		List<TreeNode<HeadingNode>> children = treeNode.getChildNodes();

		if ((headingNode.getLevel() > treeNode.getValue().getLevel()) &&
			(children != null) && (children.size() > 0)) {

			return true;
		}

		return false;
	}

	private TreeNode<HeadingNode> _tableOfContents = null;

}