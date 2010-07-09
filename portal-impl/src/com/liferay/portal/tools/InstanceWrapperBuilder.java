/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.tools.servicebuilder.ServiceBuilder;
import com.liferay.portal.util.InitUtil;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.Type;
import com.thoughtworks.qdox.model.TypeVariable;

import java.io.File;
import java.io.IOException;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class InstanceWrapperBuilder {

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		if (args.length == 1) {
			new InstanceWrapperBuilder(args[0]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public InstanceWrapperBuilder(String xml) {
		try {
			File file = new File(xml);

			Document doc = null;

			try {
				doc = SAXReaderUtil.read(file);
			}
			catch (DocumentException de) {
				de.printStackTrace();
			}

			Element root = doc.getRootElement();

			Iterator<Element> itr = root.elements(
				"instance-wrapper").iterator();

			while (itr.hasNext()) {
				Element instanceWrapper = itr.next();

				String parentDir = instanceWrapper.attributeValue("parent-dir");
				String srcFile = instanceWrapper.attributeValue("src-file");

				_createIW(parentDir, srcFile);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _createIW(String parentDir, String srcFile)
		throws IOException {

		JavaClass javaClass = _getJavaClass(parentDir, srcFile);

		JavaMethod[] methods = javaClass.getMethods();

		StringBuilder sb = new StringBuilder();

		// Package

		sb.append("package " + javaClass.getPackage().getName() + ";");

		// Class declaration

		sb.append("public class " + javaClass.getName() + "_IW {");

		// Methods

		sb.append("public static " + javaClass.getName() + "_IW getInstance() {");
		sb.append("return _instance;");
		sb.append("}\n");

		for (int i = 0; i < methods.length; i++) {
			JavaMethod javaMethod = methods[i];

			String methodName = javaMethod.getName();

			if (!javaMethod.isPublic() || !javaMethod.isStatic()) {
				continue;
			}

			if (methodName.equals("getInstance")) {
				methodName = "getWrappedInstance";
			}

			DocletTag[] docletTags = javaMethod.getTagsByName("deprecated");

			if ((docletTags != null) && (docletTags.length > 0)) {
				sb.append("\t/**\n");
				sb.append("\t * @deprecated\n");
				sb.append("\t */\n");
			}

			sb.append("public ");

			TypeVariable[] typeParameters = javaMethod.getTypeParameters();

			if (typeParameters.length > 0) {
				sb.append(" " + typeParameters[0].getGenericValue() + " ");
			}

			sb.append(_getTypeGenericsName(javaMethod.getReturns()) + " " + methodName + "(");

			JavaParameter[] parameters = javaMethod.getParameters();

			for (int j = 0; j < parameters.length; j++) {
				JavaParameter javaParameter = parameters[j];

				sb.append(_getTypeGenericsName(javaParameter.getType()));

				if (javaParameter.isVarArgs()) {
					sb.append("...");
				}

				sb.append(" " + javaParameter.getName());

				if ((j + 1) != parameters.length) {
					sb.append(", ");
				}
			}

			sb.append(")");

			Type[] thrownExceptions = javaMethod.getExceptions();

			Set<String> newExceptions = new LinkedHashSet<String>();

			for (int j = 0; j < thrownExceptions.length; j++) {
				Type thrownException = thrownExceptions[j];

				newExceptions.add(thrownException.getValue());
			}

			if (newExceptions.size() > 0) {
				sb.append(" throws ");

				Iterator<String> itr = newExceptions.iterator();

				while (itr.hasNext()) {
					sb.append(itr.next());

					if (itr.hasNext()) {
						sb.append(", ");
					}
				}
			}

			sb.append("{\n");

			if (!javaMethod.getReturns().getValue().equals("void")) {
				sb.append("return ");
			}

			sb.append(javaClass.getName() + "." + javaMethod.getName() + "(");

			for (int j = 0; j < parameters.length; j++) {
				JavaParameter javaParameter = parameters[j];

				sb.append(javaParameter.getName());

				if ((j + 1) != parameters.length) {
					sb.append(", ");
				}
			}

			sb.append(");");
			sb.append("}\n");
		}

		// Private constructor

		sb.append("private " + javaClass.getName() + "_IW() {");
		sb.append("}");

		// Fields

		sb.append("private static " + javaClass.getName() + "_IW _instance = new " + javaClass.getName() + "_IW();");

		// Class close brace

		sb.append("}");

		// Write file

		File file = new File(parentDir + "/" + StringUtil.replace(javaClass.getPackage().getName(), ".", "/") + "/" + javaClass.getName() + "_IW.java");

		ServiceBuilder.writeFile(file, sb.toString());
	}

	private String _getDimensions(Type type) {
		String dimensions = "";

		for (int i = 0; i < type.getDimensions(); i++) {
			dimensions += "[]";
		}

		return dimensions;
	}

	private JavaClass _getJavaClass(String parentDir, String srcFile)
		throws IOException {

		String className = StringUtil.replace(
			srcFile.substring(0, srcFile.length() - 5), "/", ".");

		JavaDocBuilder builder = new JavaDocBuilder();

		builder.addSource(new File(parentDir + "/" + srcFile));

		return builder.getClassByName(className);
	}

	private String _getTypeGenericsName(Type type) {
		StringBuilder sb = new StringBuilder();

		sb.append(type.getValue());

		Type[] actualTypeArguments = type.getActualTypeArguments();

		if (actualTypeArguments != null) {
			sb.append("<");

			for (int i = 0; i < actualTypeArguments.length; i++) {
				if (i > 0) {
					sb.append(", ");
				}

				sb.append(_getTypeGenericsName(actualTypeArguments[i]));
			}

			sb.append(">");
		}

		sb.append(_getDimensions(type));

		return sb.toString();
	}

}