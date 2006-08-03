/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.tools;

import com.liferay.portal.util.SAXReaderFactory;
import com.liferay.util.FileUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.TextFormatter;
import com.liferay.util.ant.Java2WsddTask;

import java.io.File;
import java.io.IOException;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="WSDDBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class WSDDBuilder {

	public static void main(String[] args) {
		if (args.length == 2) {
			new WSDDBuilder(args[0], args[1]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public WSDDBuilder(String fileName, String serverConfigFileName) {
		try {
			_serverConfigFileName = serverConfigFileName;

			SAXReader reader = SAXReaderFactory.getInstance();

			Document doc = reader.read(new File(fileName));

			Element root = doc.getRootElement();

			String packagePath = root.attributeValue("package-path");

			_portletName = root.element("portlet").attributeValue("name");

			_portletPackageName =
				TextFormatter.format(_portletName, TextFormatter.B);

			_outputPath =
				"src/" + StringUtil.replace(packagePath, ".", "/") + "/" +
					_portletPackageName + "/service/http";

			_packagePath = packagePath + "." + _portletPackageName;

			List entities = root.elements("entity");

			Iterator itr = entities.iterator();

			while (itr.hasNext()) {
				Element entity = (Element)itr.next();

				String entityName = entity.attributeValue("name");

				boolean remoteService = GetterUtil.getBoolean(
					entity.attributeValue("remote-service"), true);

				if (remoteService) {
					_createServiceWSDD(entityName);

					WSDDMerger.merge(
						_outputPath + "/" + entityName + "Service_deploy.wsdd",
						_serverConfigFileName);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _createServiceWSDD(String entityName) throws IOException {
		String className =
			_packagePath + ".service.http." + entityName + "ServiceSoap";

		String serviceName = StringUtil.replace(_portletName, " ", "_");

		if (!_portletName.equals("Portal")) {
			serviceName = "Portlet_" + serviceName;
		}

		serviceName += ("_" + entityName + "Service");

		String[] wsdds = Java2WsddTask.generateWsdd(className, serviceName);

		FileUtil.write(
			new File(_outputPath + "/" + entityName + "Service_deploy.wsdd"),
			wsdds[0], true);

		FileUtil.write(
			new File(_outputPath + "/" + entityName + "Service_undeploy.wsdd"),
			wsdds[1], true);
	}

	private String _serverConfigFileName;
	private String _portletName;
	private String _portletPackageName;
	private String _outputPath;
	private String _packagePath;

}