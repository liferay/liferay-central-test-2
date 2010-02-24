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

package com.liferay.portal.kernel.xmlrpc;

/**
 * <a href="XmlRpcConstants.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public interface XmlRpcConstants {

	public static final int APPLICATION_ERROR = -32500;

	public static final int INTERNAL_XMLRPC_ERROR = -32603;

	public static final int INVALID_CHARACTER_FOR_ENCODING = -32702;

	public static final int INVALID_METHOD_PARAMETERS = -32602;

	public static final int INVALID_XMLRPC = -32600;

	public static final int NOT_WELL_FORMED = -32700;

	public static final int REQUESTED_METHOD_NOT_FOUND = -32601;

	public static final int SYSTEM_ERROR = -32400;

	public static final int TRANSPORT_ERROR = -32300;

	public static final int UNSUPPORTED_ENCODING = -32701;

}