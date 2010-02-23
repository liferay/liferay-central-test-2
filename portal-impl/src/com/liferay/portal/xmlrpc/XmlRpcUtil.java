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

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.xmlrpc.response.Fault;
import com.liferay.portal.xmlrpc.response.Response;
import com.liferay.portal.xmlrpc.response.Success;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

/**
 * <a href="XmlRpcUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 */
public class XmlRpcUtil {

	public static String buildMethod(String methodName, Object[] args)
		throws XmlRpcException {

		StringBundler sb = new StringBundler();

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<methodCall>\n");
		sb.append("<methodName>" + methodName + "</methodName>\n");
		sb.append("<params>\n");

		if (args != null) {
			for (Object arg : args) {
				sb.append("<param>" + wrapValue(arg) + "</param>\n");
			}
		}

		sb.append("</params>\n");
		sb.append("</methodCall>");

		return sb.toString();
	}

	public static Tuple parseMethod(String xml) throws IOException {
		XMLStreamReader xmlStreamReader = null;

		String methodName = null;
		List<Object> args = new ArrayList<Object>();

		try {
			XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

			xmlStreamReader = xmlInputFactory.createXMLStreamReader(
				new UnsyncStringReader(xml));

			xmlStreamReader.nextTag();
			xmlStreamReader.nextTag();
			xmlStreamReader.next();

			methodName = xmlStreamReader.getText();

			xmlStreamReader.nextTag();

			String name = xmlStreamReader.getLocalName();

			while (name != "methodCall") {
				xmlStreamReader.nextTag();

				name = xmlStreamReader.getLocalName();

				if (name.equals("param")) {
					xmlStreamReader.nextTag();

					name = xmlStreamReader.getLocalName();

					int event = xmlStreamReader.next();

					if (event == XMLStreamConstants.START_ELEMENT) {
						name = xmlStreamReader.getLocalName();

						xmlStreamReader.next();

						String text = xmlStreamReader.getText();

						if (name.equals("string")) {
							args.add(text);
						}
						else if (name.equals("int") || name.equals("i4")) {
							args.add(Integer.parseInt(text));
						}
						else if (name.equals("double")) {
							args.add(Double.parseDouble(text));
						}
						else if (name.equals("boolean")) {
							args.add(Boolean.parseBoolean(text));
						}
						else {
							throw new IOException(
								"XML-RPC not implemented for " + name);
						}

						xmlStreamReader.nextTag();
						xmlStreamReader.nextTag();
						xmlStreamReader.nextTag();
					}
					else {
						String text = xmlStreamReader.getText();

						args.add(text);

						xmlStreamReader.nextTag();
						xmlStreamReader.nextTag();
					}

					name = xmlStreamReader.getLocalName();
				}
			}
		}
		catch (Exception e) {
			throw new IOException(e);
		}
		finally {
			if (xmlStreamReader != null) {
				try {
					xmlStreamReader.close();
				}
				catch (Exception e) {
				}
			}
		}

		return new Tuple(methodName, args.toArray());
	}

	public static Response parseResponse(String xml) throws XmlRpcException {
		Response response = null;

		XMLStreamReader xmlStreamReader = null;

		try {
			XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

			xmlStreamReader = xmlInputFactory.createXMLStreamReader(
				new UnsyncStringReader(xml));

			xmlStreamReader.nextTag();
			xmlStreamReader.nextTag();

			String name = xmlStreamReader.getLocalName();

			if (name.equals("params")) {
				String description = null;

				xmlStreamReader.nextTag();
				xmlStreamReader.nextTag();

				int event = xmlStreamReader.next();

				if (event == XMLStreamConstants.START_ELEMENT) {
					xmlStreamReader.next();

					description = xmlStreamReader.getText();
				}
				else {
					description = xmlStreamReader.getText();
				}

				return new Success(description);
			}
			else if (name.equals("fault")) {
				int code = 0;
				String description = null;

				xmlStreamReader.nextTag();
				xmlStreamReader.nextTag();

				for (int i = 0; i < 2; i++) {
					xmlStreamReader.nextTag();
					xmlStreamReader.nextTag();

					xmlStreamReader.next();

					String valueName = xmlStreamReader.getText();

					if (valueName.equals("faultCode")) {
						xmlStreamReader.nextTag();
						xmlStreamReader.nextTag();
						xmlStreamReader.nextTag();

						name = xmlStreamReader.getLocalName();

						if (name.equals("int") || name.equals("i4")) {
							xmlStreamReader.next();

							code = Integer.parseInt(xmlStreamReader.getText());
						}

						xmlStreamReader.nextTag();
						xmlStreamReader.nextTag();
						xmlStreamReader.nextTag();
					}
					else if (valueName.equals("faultString")) {
						xmlStreamReader.nextTag();
						xmlStreamReader.nextTag();

						int event = xmlStreamReader.next();

						if (event == XMLStreamConstants.START_ELEMENT) {
							xmlStreamReader.next();

							description = xmlStreamReader.getText();

							xmlStreamReader.nextTag();
						}
						else {
							description = xmlStreamReader.getText();
						}

						xmlStreamReader.nextTag();
						xmlStreamReader.nextTag();
					}
				}

				return new Fault(code, description);
			}
		}
		catch (Exception e) {
			throw new XmlRpcException(xml, e);
		}
		finally {
			if (xmlStreamReader != null) {
				try {
					xmlStreamReader.close();
				}
				catch (Exception e) {
				}
			}
		}

		return response;
	}

	public static String wrapValue(Object value) throws XmlRpcException {
		if (value == null) {
			return "";
		}

		StringBundler sb = new StringBundler();

		sb.append("<value>");

		if (value instanceof String) {
			sb.append("<string>" + value.toString() + "</string>");
		}
		else if ((value instanceof Integer) || (value instanceof Short)) {
			sb.append("<i4>" + value.toString() + "</i4>");
		}
		else if ((value instanceof Double) || (value instanceof Float)) {
			sb.append("<double>" + value.toString() + "</double>");
		}
		else if (value instanceof Boolean) {
			String strValue = "0";

			if ((Boolean)value) {
				strValue = "1";
			}

			sb.append("<boolean>" + strValue + "</boolean>");
		}
		else {
			throw new XmlRpcException("Unsupported type " + value.getClass());
		}

		sb.append("</value>");

		return sb.toString();
	}

}