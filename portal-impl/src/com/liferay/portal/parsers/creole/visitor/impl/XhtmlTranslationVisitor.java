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

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.parsers.creole.ast.ASTNode;
import com.liferay.portal.parsers.creole.ast.BoldTextNode;
import com.liferay.portal.parsers.creole.ast.CollectionNode;
import com.liferay.portal.parsers.creole.ast.ForcedEndOfLineNode;
import com.liferay.portal.parsers.creole.ast.FormattedTextNode;
import com.liferay.portal.parsers.creole.ast.HeadingNode;
import com.liferay.portal.parsers.creole.ast.HorizontalNode;
import com.liferay.portal.parsers.creole.ast.ImageNode;
import com.liferay.portal.parsers.creole.ast.ItalicTextNode;
import com.liferay.portal.parsers.creole.ast.LineNode;
import com.liferay.portal.parsers.creole.ast.NoWikiSectionNode;
import com.liferay.portal.parsers.creole.ast.OrderedListItemNode;
import com.liferay.portal.parsers.creole.ast.OrderedListNode;
import com.liferay.portal.parsers.creole.ast.ParagraphNode;
import com.liferay.portal.parsers.creole.ast.ScapedNode;
import com.liferay.portal.parsers.creole.ast.UnformattedTextNode;
import com.liferay.portal.parsers.creole.ast.UnorderedListItemNode;
import com.liferay.portal.parsers.creole.ast.UnorderedListNode;
import com.liferay.portal.parsers.creole.ast.WikiPageNode;
import com.liferay.portal.parsers.creole.ast.extension.TableOfContentsNode;
import com.liferay.portal.parsers.creole.ast.link.LinkNode;
import com.liferay.portal.parsers.creole.ast.table.TableDataNode;
import com.liferay.portal.parsers.creole.ast.table.TableHeaderNode;
import com.liferay.portal.parsers.creole.ast.table.TableNode;
import com.liferay.portal.parsers.creole.visitor.ASTVisitor;

import java.util.List;

/**
 * @author Miguel Pastor
 */
public class XhtmlTranslationVisitor implements ASTVisitor {

	public String translate(WikiPageNode wikiPageNode) {
		_sb.setIndex(0);

		visit(wikiPageNode);

		return _sb.toString();
	}

	public void visit(BoldTextNode boldTextNode) {
		write("<strong>");

		if (boldTextNode.getContent() != null) {
			write(HtmlUtil.escape(boldTextNode.getContent()));
		}
		else {
			traverse(boldTextNode.getChildASTNodes());
		}

		write("</strong>");
	}

	public void visit(CollectionNode collectionNode) {
		for (ASTNode astNode : collectionNode.getASTNodes()) {
			astNode.accept(this);
		}
	}

	public void visit(ForcedEndOfLineNode forcedEndOfLineNode) {
		write("<br/>");
	}

	public void visit(FormattedTextNode formattedTextNode) {
		if (formattedTextNode.getContent() != null) {
			write(HtmlUtil.escape(formattedTextNode.getContent()));
		}
		else {
			traverse(formattedTextNode.getChildASTNodes());
		}
	}

	public void visit(HeadingNode headingNode) {
		int level = headingNode.getLevel();

		write("<h");
		write(level);
		write(">");

		traverse(headingNode.getChildASTNodes());

		write("</h");
		write(level);
		write(">");
	}

	public void visit(HorizontalNode horizontalNode) {
		write("<hr/>");
	}

	public void visit(ImageNode imageNode) {
		write("<img src=\"");
		write(HtmlUtil.escape(imageNode.getLink()));
		write("\" ");

		if (imageNode.hasAltCollectionNode()) {
			write("alt=\"");

			CollectionNode altCollectionNode = imageNode.getAltNode();

			traverse(altCollectionNode.getASTNodes());

			write("\"");
		}

		write("/>");
	}

	public void visit(ItalicTextNode italicTextNode) {
		write("<em>");

		if (italicTextNode.getContent() != null) {
			write(HtmlUtil.escape(italicTextNode.getContent()));
		}
		else {
			traverse(italicTextNode.getChildASTNodes());
		}

		write("</em>");
	}

	public void visit(LineNode lineNode) {
		traverse(lineNode.getChildASTNodes(), null, StringPool.SPACE);
	}

	public void visit(LinkNode linkNode) {
		write("<a href=\"");
		write(HtmlUtil.escape(linkNode.getLink()));
		write("\">");

		if (linkNode.hasAltCollectionNode()) {
			CollectionNode altCollectionNode = linkNode.getAltCollectionNode();

			traverse(altCollectionNode.getASTNodes());
		}
		else {
			write(HtmlUtil.escape(linkNode.getLink()));
		}

		write("</a>");
	}

	public void visit(NoWikiSectionNode noWikiSectionNode) {
		write("<pre>");
		write(HtmlUtil.escape(noWikiSectionNode.getContent()));
		write("</pre>");
	}

	public void visit(OrderedListItemNode orderedListItemNode) {
		traverse(orderedListItemNode.getChildASTNodes(), "<li>", "</li>");
	}

	public void visit(OrderedListNode orderedListNode) {
		write("<ol>");

		traverse(orderedListNode.getChildASTNodes());

		write("</ol>");
	}

	public void visit(ParagraphNode paragraphNode) {
		traverse(paragraphNode.getChildASTNodes(), "<p>", "</p>");
	}

	public void visit(ScapedNode scapedNode) {
		write(HtmlUtil.escape(scapedNode.getContent()));
	}

	public void visit(TableDataNode tableDataNode) {
		traverse(tableDataNode.getChildASTNodes(), "<td>", "</td>");
	}

	public void visit(TableHeaderNode tableHeaderNode) {
		traverse(tableHeaderNode.getChildASTNodes(), "<th>", "</th>");
	}

	public void visit(TableNode tableNode) {
		write("<table>");

		traverseAndWriteForEach(tableNode.getChildASTNodes(), "<tr>", "</tr>");

		write("</table>");
	}

	public void visit(TableOfContentsNode tableOfContentsNode) {
	}

	public void visit(UnformattedTextNode unformattedTextNode) {
		if (unformattedTextNode.hasContent()) {
			write(HtmlUtil.escape(unformattedTextNode.getContent()));
		}
		else {
			traverse(unformattedTextNode.getChildASTNodes());
		}
	}

	public void visit(UnorderedListItemNode unorderedListItemNode) {
		traverse(unorderedListItemNode.getChildASTNodes(), "<li>", "</li>");
	}

	public void visit(UnorderedListNode unorderedListNode) {
		write("<ul>");

		traverse(unorderedListNode.getChildASTNodes());

		write("</ul>");
	}

	public void visit(WikiPageNode wikiPageNode) {
		traverse(wikiPageNode.getChildASTNodes());
	}

	protected void traverse(List<ASTNode> astNodes) {
		if (astNodes != null) {
			for (ASTNode curNode : astNodes) {
				curNode.accept(this);
			}
		}
	}

	protected void traverse(List<ASTNode> astNodes, String open, String close) {
		write(open);

		traverse(astNodes);

		write(close);
	}

	protected void traverseAndWriteForEach(
		List<ASTNode> astNodes, String open, String close) {

		for (ASTNode curNode : astNodes) {
			write(open);

			curNode.accept(this);

			write(close);
		}
	}

	protected void write(Object object) {
		if (object != null) {
			_sb.append(object);
		}
	}

	private StringBundler _sb = new StringBundler();

}