
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

package com.liferay.taglib.util;

import com.liferay.portal.kernel.servlet.taglib.BaseBodyTagSupport;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Shuyang Zhou
 */
public class CollectionTag extends BaseBodyTagSupport implements BodyTag {

	public CollectionTag(String webKey) {
		_webKey = webKey;
	}

	public int doStartTag() {
		if (Validator.isNotNull(_outputKey)) {

			Set<String> keySet = getKeySet();

			if (!keySet.add(_outputKey)) {
				_needOutput=false;

				return SKIP_BODY;
			}
		}

		_needOutput = true;
		return EVAL_BODY_BUFFERED;
	}

	public int doEndTag() {
		if (!_needOutput) {
			return EVAL_PAGE;
		}

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		StringBundler sb = (StringBundler)request.getAttribute(_webKey);

		if (sb == null) {
			sb = new StringBundler();

			request.setAttribute(_webKey, sb);
		}

		sb.append(getBodyContentAsStringBundler());

		return EVAL_PAGE;
	}

	public void setOutputKey(String outputKey) {
		_outputKey = outputKey;
	}

	protected Set<String> getKeySet() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		Set<String> keySet = (Set<String>)request.getAttribute(_KEYSET_KEY);
		if (keySet == null) {
			keySet = new HashSet<String>();
			request.setAttribute(_KEYSET_KEY, keySet);
		}

		return keySet;
	}

	private static final String _KEYSET_KEY = "KEYSET_KEY";

	private boolean _needOutput;

	private String _outputKey;

	private final String _webKey;

}