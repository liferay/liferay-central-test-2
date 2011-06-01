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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TreeNode;
import com.liferay.portal.parsers.creole.ast.CollectionNode;
import com.liferay.portal.parsers.creole.ast.HeadingNode;
import com.liferay.portal.parsers.creole.ast.ImageNode;
import com.liferay.portal.parsers.creole.ast.WikiPageNode;
import com.liferay.portal.parsers.creole.ast.extension.TableOfContentsNode;
import com.liferay.portal.parsers.creole.ast.link.LinkNode;
import com.liferay.portal.parsers.creole.visitor.impl.XhtmlTranslationVisitor;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.engines.antlrwiki.translator.internal.UnformattedHeadingTextVisitor;
import com.liferay.portlet.wiki.engines.antlrwiki.translator.internal.UnformattedLinksTextVisitor;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import javax.portlet.PortletURL;
import java.util.List;

/**
 * @author Miguel Pastor
 */
public class XhtmlTranslator extends XhtmlTranslationVisitor {

	public String translate(
		WikiPage wikiPage, PortletURL viewUrl, PortletURL editUrl,
		String attachmentURLPrefix, WikiPageNode wikiPageNode) {

		_attachmentURLPrefix = attachmentURLPrefix;
		_editUrl = editUrl;
		_rootNode = wikiPageNode;
		_uniqueIdHeaderSequence = 0;
		_viewUrl = viewUrl;
		_wikiPage = wikiPage;

		return super.translate(wikiPageNode);
	}

	public void visit(HeadingNode headingNode) {
		int level = headingNode.getLevel();

		append("<h");
		append(level);

		String unformattedText = getUnformattedHeadingText(
			headingNode);

		String markup = getHeadingMarkup(
			_wikiPage.getTitle(), unformattedText);

		append(" id=\"");
		append(markup);
		append("\">");

		traverse(headingNode.getChildASTNodes());

		append("<a class=\"hashlink\" href=\"");

		if (_viewUrl != null) {
			append(_viewUrl.toString());
		}

		append(StringPool.POUND);
		append(markup);
		append("\">");
		append(StringPool.POUND);
		append("</a>");
		append("</h");
		append(level);
		append(">");
	}

	public void visit(ImageNode imageNode) {
		append("<img src=\"");

		if (imageNode.isAbsoluteLink()) {
			append(imageNode.getLink());
		}
		else {
			append(_attachmentURLPrefix);
			append(imageNode.getLink());
		}

		append(StringPool.QUOTE);
		append(StringPool.SPACE);

		if (imageNode.hasAltCollectionNode()) {
			append("alt=\"");

			CollectionNode altCollectionNode = imageNode.getAltNode();

			traverse(altCollectionNode.getASTNodes());

			append(StringPool.QUOTE);
		}

		append("/>");
	}

	public void visit(LinkNode linkNode) {
		append("<a href=\"");

		appendHref(linkNode);

		append("\">");

		if (linkNode.hasAltCollectionNode()) {
			CollectionNode altCollectionNode = linkNode.getAltCollectionNode();

			traverse(altCollectionNode.getASTNodes());
		}
		else {
			append(HtmlUtil.escape(linkNode.getLink()));
		}

		append("</a>");
	}

	public void visit(TableOfContentsNode tableOfContentsNode) {
		TreeNode<HeadingNode> tableOfContents =
			new TableOfContentsVisitor().compose(_rootNode);

		append("<div class=\"toc\">");
		append("<div class=\"collapsebox\">");
		append("<h4>Table Of Contents");
		append(StringPool.NBSP);
		append("<a class=\"toc-trigger\" href=\"javascript:;\">[-]</a></h4>");
		append("<div class=\"toc-index\">");

		appendTableOfContents(tableOfContents, 1);

		append("</div>");
		append("</div>");
		append("</div>");

	}

	protected void appendAbsoluteHref(LinkNode linkNode) {
		append(HtmlUtil.escape(linkNode.getLink()));
	}

	protected void appendHref(LinkNode linkNode) {
		if (linkNode.getLink() == null) {

			// Recovering from bad code

			linkNode.setLink(
				new UnformattedLinksTextVisitor().getUnformattedText(
					linkNode));
		}

		if (linkNode.isAbsoluteLink()) {
			appendAbsoluteHref(linkNode);
		}
		else {
			appendWikiHref(linkNode);
		}
	}

	protected void appendTableOfContents(
		TreeNode<HeadingNode> tableOfContents, int depth) {

		append("<ol>");

		List<TreeNode<HeadingNode>> childrenNodes =
			tableOfContents.getChildNodes();

		if (childrenNodes != null) {
			for (TreeNode<HeadingNode> treeNode : childrenNodes) {
				append("<li class=\"toc-level-" + depth + "\">");

				HeadingNode headingNode = treeNode.getValue();

				String content = getUnformattedHeadingText(headingNode);

				append("<a class=\"wikipage\" href=\"");

				if (_viewUrl != null) {
					append(_viewUrl.toString());
				}

				append(StringPool.POUND);
				append(getHeadingMarkup(_wikiPage.getTitle(), content));
				append("\">");
				append(content);
				append("</a>");

				appendTableOfContents(treeNode, depth + 1);

				append("</li>");
			}
		}

		append("</ol>");
	}

	protected void appendWikiHref(LinkNode linkNode) {
		WikiPage page = null;

		try {
			page = WikiPageLocalServiceUtil.getPage(
				_wikiPage.getNodeId(), linkNode.getLink());
		}
		catch (NoSuchPageException nspe) {
		}
		catch (Exception e) {
			_log.warn(
				"Unexpected error searching for page: " + linkNode.getLink() +
				". Assuming that it does not exist.");
		}

		String href = null;

		String pageTitle = HtmlUtil.escape(linkNode.getLink());

		if (page != null && _viewUrl != null) {
			_viewUrl.setParameter("title", pageTitle);

			href = _viewUrl.toString();
		}
		else if (_editUrl != null) {
			_editUrl.setParameter("title", pageTitle);

			href = _editUrl.toString();
		}

		append(href);
	}

	protected String getHeadingMarkup(String prefix, String text) {
		StringBundler trimmedText = new StringBundler(5);

		trimmedText.append(_HEADING_ANCHOR_PREFIX);
		trimmedText.append(prefix);
		trimmedText.append(StringPool.DASH);
		trimmedText.append(text.trim());

		if (StringPool.BLANK.equals(trimmedText)) {
			trimmedText.append(++_uniqueIdHeaderSequence);
		}

		return StringUtil.replace(
			trimmedText.toString(), StringPool.SPACE, StringPool.PLUS);
	}

	protected String getUnformattedHeadingText(HeadingNode headingNode) {

		return new UnformattedHeadingTextVisitor().getUnformattedText(
			headingNode);
	}

	private static final String _HEADING_ANCHOR_PREFIX = "section-";

	private static Log _log = LogFactoryUtil.getLog(XhtmlTranslator.class);

	private String _attachmentURLPrefix;
	private PortletURL _editUrl;
	private WikiPageNode _rootNode;
	private int _uniqueIdHeaderSequence = 0;
	private PortletURL _viewUrl;
	private WikiPage _wikiPage = null;

}