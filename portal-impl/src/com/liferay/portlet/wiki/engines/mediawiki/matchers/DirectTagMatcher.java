/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.engines.mediawiki.matchers;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CallbackMatcher;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.wiki.model.WikiPage;

import java.util.regex.MatchResult;

/**
 * @author Kenneth Chang
 */
public class DirectTagMatcher extends CallbackMatcher {

	public DirectTagMatcher(WikiPage page) {
		_page = page;

		setRegex(_REGEX);
	}

	public String replaceMatches(CharSequence charSequence) {
		return replaceMatches(charSequence, _callBack);
	}

	private static final String _REGEX = "\\[\\[([^\\]]+)\\]\\]";

	private Callback _callBack = new Callback() {

		@Override
		public String foundMatch(MatchResult matchResult) {
			String fileName = matchResult.group(1);

			if (!fileName.contains(StringPool.UNDERLINE)) {
				return null;
			}

			if (fileName.indexOf(CharPool.PIPE) >= 0) {
				fileName = StringUtil.extractFirst(fileName, CharPool.PIPE);
			}

			try {
				String[] attachments = _page.getAttachmentsFiles();

				String link =
					StringPool.SLASH + _page.getAttachmentsDir() +
						StringPool.SLASH + fileName;

				if (!ArrayUtil.contains(attachments, link)) {
					return null;
				}
			}
			catch (Exception e) {
				return null;
			}

			fileName = StringUtil.replace(
				fileName, StringPool.UNDERLINE, "%5F");

			return StringUtil.replace(
				matchResult.group(0), matchResult.group(1), fileName);
		}

	};

	private WikiPage _page;

}