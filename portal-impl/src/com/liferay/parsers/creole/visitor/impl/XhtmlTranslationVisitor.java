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

package com.liferay.parsers.creole.visitor.impl;

import com.liferay.parsers.creole.ast.ASTNode;
import com.liferay.parsers.creole.ast.BoldTextNode;
import com.liferay.parsers.creole.ast.CollectionNode;
import com.liferay.parsers.creole.ast.ForcedEndOfLineNode;
import com.liferay.parsers.creole.ast.FormattedTextNode;
import com.liferay.parsers.creole.ast.HeadingNode;
import com.liferay.parsers.creole.ast.HorizontalNode;
import com.liferay.parsers.creole.ast.ImageNode;
import com.liferay.parsers.creole.ast.ItalicTextNode;
import com.liferay.parsers.creole.ast.LineNode;
import com.liferay.parsers.creole.ast.NoWikiSectionNode;
import com.liferay.parsers.creole.ast.OrderedListItemNode;
import com.liferay.parsers.creole.ast.OrderedListNode;
import com.liferay.parsers.creole.ast.ParagraphNode;
import com.liferay.parsers.creole.ast.ScapedNode;
import com.liferay.parsers.creole.ast.UnformattedTextNode;
import com.liferay.parsers.creole.ast.UnorderedListItemNode;
import com.liferay.parsers.creole.ast.UnorderedListNode;
import com.liferay.parsers.creole.ast.WikiPageNode;
import com.liferay.parsers.creole.ast.link.LinkNode;
import com.liferay.parsers.creole.ast.table.TableDataNode;
import com.liferay.parsers.creole.ast.table.TableHeaderNode;
import com.liferay.parsers.creole.ast.table.TableNode;
import com.liferay.parsers.creole.visitor.ASTVisitor;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;

/**
 * This visitor traverses the <code>AST</code> in order to generate an
 * <code>XHTML</code> representation
 *
 * @author Miguel Pastor
 */
public class XhtmlTranslationVisitor implements ASTVisitor {

	public String translate(WikiPageNode start) {
		_xhtml.setLength(0);

		visit(start);

		return _xhtml.toString();
	}

	public void visit(BoldTextNode node) {
		write("<strong>");

		if (node.getContent() != null) {
			write(node.getContent());
		}
		else {
			traverse(node.getChildNodes());
		}

		write("</strong>");
	}

	public void visit(TableDataNode node) {
		traverse(node.getChildNodes(), "<td>", "</td>");
	}

	public void visit(TableHeaderNode node) {
		traverse(node.getChildNodes(), "<th>", "</th>");
	}

	public void visit(CollectionNode node) {
		for (ASTNode curNode : node.getNodes()) {
			curNode.accept(this);
		}
	}

	public void visit(ForcedEndOfLineNode node) {
		write("<br/>");
	}

	public void visit(FormattedTextNode node) {
		if (node.getContent() != null) {
			write(node.getContent());
		}
		else {
			traverse(node.getChildNodes());
		}
	}

	public void visit(HeadingNode node) {
		int level = node.getLevel();

		write("<h");
		write(level);
		write(">");
		write(node.getContent());
		write("</h");
		write(level);
		write(">");
	}

	public void visit(HorizontalNode node) {
		write("<hr/>");
	}

	public void visit(ImageNode node) {
		write("<img src=\"");
		write(node.getUri());
		write("\" ");

		if (node.hasAltNode()) {
			write("alt=\"");

			traverse(node.getAltNode().getNodes());

			write("\"");
		}

		write("/>");
	}

	public void visit(ItalicTextNode node) {
		write("<em>");

		if (node.getContent() != null) {
			write(node.getContent());
		}
		else {
			traverse(node.getChildNodes());
		}

		write("</em>");
	}

	public void visit(LineNode node) {
		traverse(node.getChildNodes(), null, StringPool.SPACE);
	}

	public void visit(LinkNode node) {
		write("<a href=\"");
		write(node.getLink());
		write("\">");

		if (node.hasAlternative()) {
			traverse(node.getAlternative().getNodes());
		}
		else {
			write(node.getLink());
		}

		write("</a>");
	}

	public void visit(NoWikiSectionNode node) {
		write("<pre>");
		write(node.getContent());
		write("</pre>");
	}

	public void visit(OrderedListItemNode node) {
		traverseAndWriteForEach(node.getChildNodes(), "<li>", "</li>");
	}

	public void visit(OrderedListNode node) {
		write("<ol>");

		traverse(node.getChildNodes());

		write("</ol>");
	}

	public void visit(ParagraphNode node) {
		traverse(node.getChildNodes(), "<p>", "</p>");
	}

	public void visit(ScapedNode scapedNode) {
		write(scapedNode.getContent());
	}

	public void visit(TableNode node) {
		write("<table>");

		traverseAndWriteForEach(node.getChildNodes(), "<tr>", "</tr>");

		write("</table>");
	}

	public void visit(UnformattedTextNode node) {
		if (node.hasContent()) {
			write(node.getContent());
		}
		else {
			traverse(node.getChildNodes());
		}
	}

	public void visit(UnorderedListItemNode node) {
		traverseAndWriteForEach(node.getChildNodes(), "<li>", "</li>");
	}

	public void visit(UnorderedListNode node) {
		write("<ul>");

		traverse(node.getChildNodes());

		write("</ul>");
	}

	public void visit(WikiPageNode node) {
		traverse(node.getChildNodes());
	}

	protected void traverse(List<ASTNode> nodes) {
		if (nodes != null) {
			for (ASTNode curNode : nodes) {
				curNode.accept(this);
			}
		}
	}

	protected void traverse(List<ASTNode> nodes, String open, String close) {
		write(open);

		traverse(nodes);

		write(close);
	}

	protected void traverseAndWriteForEach(
		List<ASTNode> nodes, String open, String close) {

		for (ASTNode curNode : nodes) {
			write(open);

			curNode.accept(this);

			write(close);
		}
	}

	protected void write(Object obj) {
		if (obj != null) {
			_xhtml.append(obj);
		}
	}

	private StringBuffer _xhtml = new StringBuffer();

}