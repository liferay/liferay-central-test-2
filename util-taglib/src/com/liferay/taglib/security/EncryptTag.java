/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.taglib.security;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.Encryptor;
import com.liferay.util.EncryptorException;

import java.security.Key;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <a href="EncryptTag.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class EncryptTag extends TagSupport {

	public int doStartTag() throws JspException {
		try {
			StringBundler sb = new StringBundler();

			// Open anchor

			sb.append("<a ");

			// Class

			if (Validator.isNotNull(_className)) {
				sb.append("class=\"");
				sb.append(_className);
				sb.append("\" ");
			}

			// HREF

			sb.append("href=\"");
			sb.append(_protocol);
			sb.append(Http.PROTOCOL_DELIMITER);

			int pos = _url.indexOf(StringPool.QUESTION);

			if (pos == -1) {
				sb.append(_url);
			}
			else {
				sb.append(_url.substring(0, pos));
				sb.append(StringPool.QUESTION);

				Company company = PortalUtil.getCompany(
					(HttpServletRequest)pageContext.getRequest());

				Key key = company.getKeyObj();

				StringTokenizer st = new StringTokenizer(
					_url.substring(pos + 1, _url.length()),
					StringPool.AMPERSAND);

				while (st.hasMoreTokens()) {
					String paramAndValue = st.nextToken();

					int x = paramAndValue.indexOf(StringPool.EQUAL);

					String param = paramAndValue.substring(0, x);
					String value = paramAndValue.substring(
						x + 1, paramAndValue.length());

					sb.append(param).append(StringPool.EQUAL);

					if (_unencryptedParamsSet.contains(param)) {
						sb.append(HttpUtil.encodeURL(value));
					}
					else {
						try {
							sb.append(HttpUtil.encodeURL(
								Encryptor.encrypt(key, value)));
						}
						catch (EncryptorException ee) {
							_log.error(ee.getMessage());
						}

						if (st.hasMoreTokens()) {
							sb.append(StringPool.AMPERSAND);
						}
					}
				}

				sb.append("&shuo=1");
			}

			sb.append("\" ");

			// Style

			if (Validator.isNotNull(_style)) {
				sb.append("style=\"");
				sb.append(_style);
				sb.append("\" ");
			}

			// Target

			if (Validator.isNotNull(_target)) {
				sb.append("target=\"" + _target + "\"");
			}

			// Close anchor

			sb.append(">");

			pageContext.getOut().print(sb.toString());

			return EVAL_BODY_INCLUDE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public int doEndTag() throws JspException {
		try {
			pageContext.getOut().print("</a>");

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setStyle(String style) {
		_style = style;
	}

	public void setProtocol(String protocol) {
		_protocol = protocol;
	}

	public void setUnencryptedParams(String unencryptedParams) {
		_unencryptedParamsSet.clear();

		String[] unencryptedParamsArray = StringUtil.split(unencryptedParams);

		for (int i = 0; i < unencryptedParamsArray.length; i++) {
			_unencryptedParamsSet.add(unencryptedParamsArray[i]);
		}
	}

	public void setUrl(String url) {
		_url = url;
	}

	public void setTarget(String target) {
		_target = target;
	}

	private static Log _log = LogFactoryUtil.getLog(EncryptTag.class);

	private String _className;
	private String _style;
	private String _protocol;
	private Set<String> _unencryptedParamsSet = new HashSet<String>();
	private String _url;
	private String _target;

}