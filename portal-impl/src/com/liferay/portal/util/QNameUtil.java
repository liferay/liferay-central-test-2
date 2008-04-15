/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.namespace.QName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Element;
import org.dom4j.Namespace;

/**
 * <a href="QNameUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class QNameUtil {

	public static final String PUBLIC_RENDER_PARAMETER_NAMESPACE = "p_r_p_";

	public static String getKey(QName qName) {
		return getKey(qName.getNamespaceURI(), qName.getLocalPart());
	}

	public static String getKey(String uri, String localPart) {
		StringMaker sm = new StringMaker();

		sm.append(uri);
		sm.append(_KEY_SEPARATOR);
		sm.append(localPart);

		return sm.toString();
	}

	public static String getPublicRenderParameterName(QName qName) {
		return _instance._getPublicRenderParameterName(qName);
	}

	public static QName getQName(String publicRenderParameterName) {
		return _instance._getQName(publicRenderParameterName);
	}

	public static QName getQName(
		Element qNameEl, Element nameEl, String defaultNamespace) {

		if ((qNameEl == null) && (nameEl == null)) {
			_log.error("both qname and name elements are null");

			return null;
		}

		if (qNameEl == null) {
			return new QName(defaultNamespace, nameEl.getTextTrim());
		}

		String localPart = qNameEl.getTextTrim();

		int pos = localPart.indexOf(StringPool.COLON);

		if (pos == -1) {
			_log.error("qname " + localPart + " does not have a prefix");

			return null;
		}

		String prefix = localPart.substring(0, pos);

		Namespace namespace = qNameEl.getNamespaceForPrefix(prefix);

		if (namespace == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("qname " + localPart + " does not have a namespace");
			}

			return null;
		}

		String uri = namespace.getURI();

		localPart = localPart.substring(prefix.length() + 1);

		return new QName(uri, localPart, prefix);
	}

	private QNameUtil() {
		_qNames = new ConcurrentHashMap<String, QName>();
	}

	private String _getPublicRenderParameterName(QName qName) {
		StringMaker sm = new StringMaker();

		sm.append(PUBLIC_RENDER_PARAMETER_NAMESPACE);
		sm.append(qName.getNamespaceURI().hashCode());
		sm.append(StringPool.UNDERLINE);
		sm.append(qName.getLocalPart());

		String publicRenderParameterName = sm.toString();

		if (!_qNames.containsKey(publicRenderParameterName)) {
			_qNames.put(publicRenderParameterName, qName);
		}

		return publicRenderParameterName;
	}

	private QName _getQName(String publicRenderParameterName) {
		if (!publicRenderParameterName.startsWith(
			QNameUtil.PUBLIC_RENDER_PARAMETER_NAMESPACE)) {

			return null;
		}

		return _qNames.get(publicRenderParameterName);
	}

	private static final String _KEY_SEPARATOR = "_KEY_";

	private static Log _log = LogFactory.getLog(QNameUtil.class);

	private static QNameUtil _instance = new QNameUtil();

	private Map<String, QName> _qNames;

}