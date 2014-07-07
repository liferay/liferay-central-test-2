/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.markdown.converter.internal.pegdown.serializer;

import com.liferay.markdown.converter.internal.pegdown.ast.PicWithCaptionNode;
import com.liferay.markdown.converter.internal.pegdown.ast.SidebarNode;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

import org.pegdown.LinkRenderer;
import org.pegdown.ToHtmlSerializer;
import org.pegdown.ast.HeaderNode;
import org.pegdown.ast.Node;
import org.pegdown.ast.SuperNode;
import org.pegdown.ast.TextNode;

/**
 * Provides a visitor implementation for printing HTML for pictures with
 * captions, "side-bars", and in-line images.
 *
 * @author James Hinkey
 */
public class LiferayToHtmlSerializer extends ToHtmlSerializer {

	public LiferayToHtmlSerializer(LinkRenderer linkRenderer) {
		super(linkRenderer);
	}

	@Override
	public void visit(HeaderNode node) {
    	if (node.getLevel() != 1) {
    		List<Node> children = node.getChildren();
    		if (!children.isEmpty()) {
    			TextNode childNode = (TextNode) children.get(0);
				String text = childNode.getText().toLowerCase()
						.replaceAll("[^a-z0-9 ]", "");
				text = StringUtil.trim(text);
				text = text.replace(' ', '-');
    			printer.print("<a name=\"" + text + "\" />");
    		}
    	}

		super.visit(node);
	}

	public void visit(PicWithCaptionNode picWithCaptionNode) {
		print(picWithCaptionNode);
	}

	public void visit(SidebarNode sidebarNode) {
		print(sidebarNode);
	}

	@Override
	public void visit(SuperNode superNode) {
		if (superNode instanceof PicWithCaptionNode) {
			visit((PicWithCaptionNode)superNode);
		}
		else if (superNode instanceof SidebarNode) {
			visit((SidebarNode)superNode);
		}
		else {
			visitChildren(superNode);
		}
	}

	protected void print(PicWithCaptionNode picWithCaptionNode) {
		printer.print("<p><img src=\"");
		printer.print(picWithCaptionNode.getSrc());
		printer.print("\" alt=\"");
		printer.print(picWithCaptionNode.getAlt());
		printer.print("\" /><p class=\"caption\">");

		visitChildren(picWithCaptionNode);

		printer.print("</p></p>");
	}

	protected void print(SidebarNode sidebarNode) {
		printer.print("<p><img src=\"");
		printer.print(sidebarNode.getSrc());
		printer.print("\" alt=\"");
		printer.print(sidebarNode.getAlt());
		printer.print("\" />");

		visitChildren(sidebarNode);

		printer.print("</p>");
	}

}