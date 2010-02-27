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

import org.stringtree.factory.AbstractStringFetcher;
import org.stringtree.factory.Container;
import org.stringtree.factory.Fetcher;
import org.stringtree.juicer.Initialisable;
import org.stringtree.regex.Matcher;
import org.stringtree.regex.Pattern;

/**
 * <a href="MediaWikiLocalLink.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class MediaWikiLocalLink
	extends AbstractStringFetcher implements Initialisable {

	public MediaWikiLocalLink() {
		this(null);
	}

	public MediaWikiLocalLink(Container pages) {
		_pages = pages;
	}

	public void init(Fetcher context) {
		_pages = (Container)context.getObject("wiki.pages");
	}

	public Object getObject(String key) {
		String value = key;

		Matcher match = _pattern.matcher(key);

		if (match.find()) {
			key = encodeKey(match.group(3).trim());

			String title = match.group(6).trim();

			if (_pages.contains(key)) {
				value = "\nview_mw{" + title + "," + key + "}\n";
			}
			else{
				value = "\nedit_mw{" + title + "," + key + "}\n";
			}
		}

		return value;
	}

	protected String encodeKey(String key) {
		return key.replaceAll("\\W", "_");
	}

	private static Pattern _pattern = Pattern.compile(
		"(^|\\p{Punct}|\\p{Space})(\\[\\s*(((\\p{Lu}\\p{Ll}+)\\s?)+)\\s*\\|\\s*((([\\p{Alpha}\\p{Digit}]+)\\s?)+)\\s*\\])(\\z|\\n|\\p{Punct}|\\p{Space})");

	private Container _pages;

}