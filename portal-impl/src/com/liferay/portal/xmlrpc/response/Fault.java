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

package com.liferay.portal.xmlrpc.response;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.xmlrpc.XmlRpcException;
import com.liferay.portal.xmlrpc.XmlRpcUtil;

/**
 * <a href="Fault.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class Fault extends Response {

	public Fault(int code, String description) {
		_code = code;
		_description = description;
	}

	public int getCode() {
		return _code;
	}

	public String getDescription() {
		return _description;
	}

	public String toString() {
		return "XML-RPC fault " + _code + " " + _description;
	}

	public String toXml() throws XmlRpcException {
		StringBundler sb = new StringBundler();

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<methodResponse>\n");
		sb.append("<fault>\n");
		sb.append("<value>\n");
		sb.append("<struct>\n");
		sb.append("<member>\n");
		sb.append("<name>faultCode</name>\n");
		sb.append(XmlRpcUtil.wrapValue(_code) + "\n");
		sb.append("</member>\n");
		sb.append("<member>\n");
		sb.append("<name>faultString</name>\n");
		sb.append(XmlRpcUtil.wrapValue(_description) + "\n");
		sb.append("</member>\n");
		sb.append("</struct>\n");
		sb.append("</value>\n");
		sb.append("</fault>\n");
		sb.append("</methodResponse>");

		return sb.toString();
	}

	private int _code;
	private String _description;

}