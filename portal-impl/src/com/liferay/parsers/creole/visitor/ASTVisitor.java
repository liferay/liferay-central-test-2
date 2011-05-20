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

package com.liferay.parsers.creole.visitor;

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

/**
 * Interface for all the visitors needing to traverse the AST
 * built by the parser.
 *
 * @author Miguel Pastor
 */
public interface ASTVisitor {

	void visit(BoldTextNode node);

	void visit(TableDataNode node);

	void visit(TableHeaderNode node);

	void visit(CollectionNode node);

	void visit(ForcedEndOfLineNode node);

	void visit(FormattedTextNode node);

	void visit(HeadingNode node);

	void visit(HorizontalNode node);

	void visit(ImageNode node);

	void visit(ItalicTextNode node);

	void visit(LineNode node);

	void visit(LinkNode node);

	void visit(NoWikiSectionNode node);

	void visit(OrderedListItemNode node);

	void visit(OrderedListNode node);

	void visit(ParagraphNode node);

	void visit(ScapedNode node);

	void visit(TableNode node);

	void visit(UnformattedTextNode node);

	void visit(UnorderedListItemNode node);

	void visit(UnorderedListNode node);

	void visit(WikiPageNode node);

}