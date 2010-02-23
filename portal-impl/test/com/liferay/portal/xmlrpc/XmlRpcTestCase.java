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

package com.liferay.portal.xmlrpc;

import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.xmlrpc.response.Fault;
import com.liferay.portal.xmlrpc.response.Response;
import com.liferay.portal.xmlrpc.response.Success;

import junit.framework.TestCase;

/**
 * <a href="XmlRpcTestCase.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class XmlRpcTestCase extends TestCase {

	public void testFaultResponseGenerator() throws Exception {
		Fault fault = new Fault(1234, "Fault!");

		Response response = XmlRpcUtil.parseResponse(fault.toXml());

		assertTrue(response instanceof Fault);

		fault = (Fault)response;

		assertEquals("Fault!", fault.getDescription());
		assertEquals(1234, fault.getCode());
	}

	public void testFaultResponseParser() throws Exception {
		for (String xml : _FAULT_RESPONSES) {
			Response response = XmlRpcUtil.parseResponse(xml);

			assertTrue(response instanceof Fault);

			Fault fault = (Fault)response;

			assertEquals(4, fault.getCode());
			assertEquals("Too many parameters.", fault.getDescription());
		}
	}

	public void testMethodBuilder() throws Exception {
		String xml = XmlRpcUtil.buildMethod(
			"method.name", new Object[] { "hello", "world" });

		Tuple tuple = XmlRpcUtil.parseMethod(xml);

		String methodName = (String)tuple.getObject(0);
		Object[] args = (Object[]) tuple.getObject(1);

		assertEquals("method.name", methodName);
		assertEquals(2, args.length);
		assertEquals("hello", args[0]);
		assertEquals("world", args[1]);
	}

	public void testMethodParser() throws Exception {
		Tuple tuple = XmlRpcUtil.parseMethod(_PARAMETERIZED_METHOD);

		String methodName = (String)tuple.getObject(0);
		Object[] args = (Object[]) tuple.getObject(1);

		assertEquals("params", methodName);
		assertEquals(3, args.length);
		assertEquals(1024, args[0]);
		assertEquals("hello", args[1]);
		assertEquals("world", args[2]);

		for (String xml : _NON_PARAMETERIZED_METHODS) {
			tuple = XmlRpcUtil.parseMethod(xml);

			methodName = (String)tuple.getObject(0);
			args = (Object[]) tuple.getObject(1);

			assertEquals("noParams", methodName);
			assertEquals(0, args.length);
		}
	}

	public void testSuccessResponseGenerator() throws Exception {
		Success success = new Success("Success!");

		Response response = XmlRpcUtil.parseResponse(success.toXml());

		assertTrue(response instanceof Success);

		success = (Success)response;

		assertEquals("Success!", success.getDescription());
	}

	public void testSuccessResponseParser() throws Exception {
		for (String xml : _SUCCESS_RESPONSES) {
			Response response = XmlRpcUtil.parseResponse(xml);

			assertTrue(response instanceof Success);

			assertEquals("South Dakota", response.getDescription());
		}
	}

	private static String _PARAMETERIZED_METHOD =
		"<?xml version=\"1.0\"?>" +
		"<methodCall>" +
		"<methodName>params</methodName>" +
		"<params>" +
		"<param><value><i4>1024</i4></value></param>" +
		"<param><value>hello</value></param>" +
		"<param><value><string>world</string></value></param>" +
		"</params>" +
		"</methodCall>";

	private static String[] _NON_PARAMETERIZED_METHODS = new String[] {
			"<?xml version=\"1.0\"?>" +
			"<methodCall>" +
			"<methodName>noParams</methodName>" +
			"<params>" +
			"</params>" +
			"</methodCall>"
		,
			"<?xml version=\"1.0\"?>" +
			"<methodCall>" +
			"<methodName>noParams</methodName>" +
			"</methodCall>"
		};

	private static String[] _SUCCESS_RESPONSES = new String[] {
			"<?xml version=\"1.0\"?>" +
			"<methodResponse>" +
			"  <params>" +
			"    <param>" +
			"      <value><string>South Dakota</string></value>" +
			"    </param>" +
			"  </params>" +
			"</methodResponse>"
		,
			"<?xml version=\"1.0\"?>" +
			"<methodResponse>" +
			"  <params>" +
			"    <param>" +
			"      <value>South Dakota</value>" +
			"    </param>" +
			"  </params>" +
			"</methodResponse>"
		};

	private static String[] _FAULT_RESPONSES = new String[] {
			"<?xml version=\"1.0\"?>" +
			"<methodResponse>" +
			"  <fault>" +
			"    <value>" +
			"      <struct>" +
			"        <member>" +
			"          <name>faultCode</name>" +
			"          <value><int>4</int></value>" +
			"        </member>" +
			"        <member>" +
			"          <name>faultString</name>" +
			"          <value><string>Too many parameters.</string></value>" +
			"        </member>" +
			"      </struct>" +
			"    </value>" +
			"  </fault>" +
			"</methodResponse>"
		,
			"<?xml version=\"1.0\"?>" +
			"<methodResponse>" +
			"  <fault>" +
			"    <value>" +
			"      <struct>" +
			"        <member>" +
			"          <name>faultCode</name>" +
			"          <value><i4>4</i4></value>" +
			"        </member>" +
			"        <member>" +
			"          <name>faultString</name>" +
			"          <value>Too many parameters.</value>" +
			"        </member>" +
			"      </struct>" +
			"    </value>" +
			"  </fault>" +
			"</methodResponse>"
		};

}