/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.filters;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.stringtree.factory.AbstractStringFetcher;

/**
 * <a href="CodeBlock.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class CodeBlock extends AbstractStringFetcher {

	public Object getObject(String content) {
		StringBundler sb = new StringBundler();

		Matcher matcher = _pattern.matcher(content);

		if (matcher.matches()) {
			sb.append("<div class=\"wiki-code\">");

			String[] lines = matcher.group(1).split("\\n");

			for (int i = 0; i < lines.length; i++) {
				if (i != 0) {
					sb.append("<br />");
				}

				String translation = StringUtil.replace(
					lines[i],
					new String[] {
						"\\s",
						"<",
						">",
						"=",
						"\"",
						"'",
						"\t"
					},
					new String[] {
						"&nbsp;",
						"&lt;",
						"&gt;",
						"&#x003D;",
						"&#0034;",
						"&#0039;",
						"&nbsp;&#8594;&nbsp;"
					});

				int padlength =
					String.valueOf(lines.length).length() -
					String.valueOf(i + 1).length();

				String padding = "";

				for (int j = 0; j < padlength; j++) {
					padding += "&#0149;";
				}

				sb.append("<span class=\"code-lines\">");
				sb.append(padding + (i + 1));
				sb.append("</span>");

				sb.append(translation);
			}

			sb.append("</div>");

			content = sb.toString();
		}

		return content;
	}

	private static Pattern _pattern = Pattern.compile(
		"\\[code\\]((.|\\n)*?)\\[/code\\]",
		Pattern.MULTILINE | Pattern.UNIX_LINES);

}