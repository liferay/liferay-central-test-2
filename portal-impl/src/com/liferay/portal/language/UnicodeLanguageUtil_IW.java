/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.language;

/**
 * <a href="UnicodeLanguageUtil_IW.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UnicodeLanguageUtil_IW {
	public static UnicodeLanguageUtil_IW getInstance() {
		return _instance;
	}

	public java.lang.String get(com.liferay.portal.model.User user,
		java.lang.String key)
		throws com.liferay.portal.language.LanguageException {
		return UnicodeLanguageUtil.get(user, key);
	}

	public java.lang.String get(long companyId, java.util.Locale locale,
		java.lang.String key)
		throws com.liferay.portal.language.LanguageException {
		return UnicodeLanguageUtil.get(companyId, locale, key);
	}

	public java.lang.String get(javax.servlet.jsp.PageContext pageContext,
		java.lang.String key)
		throws com.liferay.portal.language.LanguageException {
		return UnicodeLanguageUtil.get(pageContext, key);
	}

	public java.lang.String format(javax.servlet.jsp.PageContext pageContext,
		java.lang.String pattern, java.lang.Object argument)
		throws com.liferay.portal.language.LanguageException {
		return UnicodeLanguageUtil.format(pageContext, pattern, argument);
	}

	public java.lang.String format(javax.servlet.jsp.PageContext pageContext,
		java.lang.String pattern, java.lang.Object argument,
		boolean translateArguments)
		throws com.liferay.portal.language.LanguageException {
		return UnicodeLanguageUtil.format(pageContext, pattern, argument,
			translateArguments);
	}

	public java.lang.String format(javax.servlet.jsp.PageContext pageContext,
		java.lang.String pattern, java.lang.Object[] arguments)
		throws com.liferay.portal.language.LanguageException {
		return UnicodeLanguageUtil.format(pageContext, pattern, arguments);
	}

	public java.lang.String format(javax.servlet.jsp.PageContext pageContext,
		java.lang.String pattern, java.lang.Object[] arguments,
		boolean translateArguments)
		throws com.liferay.portal.language.LanguageException {
		return UnicodeLanguageUtil.format(pageContext, pattern, arguments,
			translateArguments);
	}

	public java.lang.String format(javax.servlet.jsp.PageContext pageContext,
		java.lang.String pattern,
		com.liferay.portal.language.LanguageWrapper argument)
		throws com.liferay.portal.language.LanguageException {
		return UnicodeLanguageUtil.format(pageContext, pattern, argument);
	}

	public java.lang.String format(javax.servlet.jsp.PageContext pageContext,
		java.lang.String pattern,
		com.liferay.portal.language.LanguageWrapper argument,
		boolean translateArguments)
		throws com.liferay.portal.language.LanguageException {
		return UnicodeLanguageUtil.format(pageContext, pattern, argument,
			translateArguments);
	}

	public java.lang.String format(javax.servlet.jsp.PageContext pageContext,
		java.lang.String pattern,
		com.liferay.portal.language.LanguageWrapper[] arguments)
		throws com.liferay.portal.language.LanguageException {
		return UnicodeLanguageUtil.format(pageContext, pattern, arguments);
	}

	public java.lang.String format(javax.servlet.jsp.PageContext pageContext,
		java.lang.String pattern,
		com.liferay.portal.language.LanguageWrapper[] arguments,
		boolean translateArguments)
		throws com.liferay.portal.language.LanguageException {
		return UnicodeLanguageUtil.format(pageContext, pattern, arguments,
			translateArguments);
	}

	public java.lang.String getTimeDescription(
		javax.servlet.jsp.PageContext pageContext, java.lang.Long milliseconds)
		throws com.liferay.portal.language.LanguageException {
		return UnicodeLanguageUtil.getTimeDescription(pageContext, milliseconds);
	}

	public java.lang.String getTimeDescription(
		javax.servlet.jsp.PageContext pageContext, long milliseconds)
		throws com.liferay.portal.language.LanguageException {
		return UnicodeLanguageUtil.getTimeDescription(pageContext, milliseconds);
	}

	private UnicodeLanguageUtil_IW() {
	}

	private static UnicodeLanguageUtil_IW _instance = new UnicodeLanguageUtil_IW();
}