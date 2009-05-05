/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.service.ResourceCodeLocalServiceUtil;

/**
 * <a href="ServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 *
 */
public class ServiceImpl implements Service {

	public static ServiceImpl getInstance() {
		return _instance;
	}

	public BaseModel getModel(Resource resource)
		throws PortalException, SystemException {

		ResourceCode resourceCode =
			ResourceCodeLocalServiceUtil.getResourceCode(resource.getCodeId());

		String modelName = resourceCode.getName();
		String primKey = resource.getPrimKey();

		return getModel(modelName, primKey);
	}

	public BaseModel getModel(ResourcePermission resourcePermission)
		throws PortalException, SystemException {

		String modelName = resourcePermission.getName();
		String primKey = resourcePermission.getPrimKey();

		return getModel(modelName, primKey);
	}

	public BaseModel getModel(String modelName, String primKey)
		throws PortalException, SystemException {

		if (modelName.contains(".model.")) {
			String[] parts = StringUtil.split(modelName, ".");

			if (parts.length > 2 && parts[parts.length - 2].equals("model")) {
				parts[parts.length - 2] = "service";

				String serviceName =
					StringUtil.merge(parts, ".") + "LocalServiceUtil";
				String methodName = "get" + parts[parts.length - 1];

				Method method = null;

				try {
					Class serviceUtil = Class.forName(serviceName);

					if (Validator.isNumber(primKey)) {
						method = serviceUtil.getMethod(
							methodName, new Class[] { Long.TYPE });

						return (BaseModel)method.invoke(
							null, new Long(primKey));
					}
					else {
						method = serviceUtil.getMethod(
							methodName, new Class[] { String.class });

						return (BaseModel)method.invoke(null, primKey);
					}
				}
				catch (Exception e) {
					Throwable cause = e.getCause();

					if (cause instanceof PortalException) {
						throw (PortalException)cause;
					}
					else if (cause instanceof SystemException) {
						throw (SystemException)cause;
					}
					else {
						throw new SystemException(cause);
					}
				}
			}
		}

		return null;
	}

	public String toHtml(BaseModel model) {
		StringBuilder sb = new StringBuilder();

		sb.append("<table class=\"lfr-table\">\n");

		try {
			SAXReader reader = new SAXReader();

			String xml = model.toXmlString();

			Document doc = reader.read(new StringReader(xml));

			Element root = doc.getRootElement();

			Iterator<Element> itr = root.elements("column").iterator();

			while (itr.hasNext()) {
				Element column = itr.next();

				String name = column.element("column-name").getTextTrim();
				String value = column.element("column-value").getTextTrim();

				sb.append("<tr>");
				sb.append("<td align=\"right\" valign=\"top\">");
				sb.append("<b>" + name + "</b>");
				sb.append("</td>");
				sb.append("<td>");
				sb.append(value);
				sb.append("</td>");
				sb.append("</tr>\n");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		sb.append("</table>");

		return sb.toString();
	}

	private static ServiceImpl _instance = new ServiceImpl();

}