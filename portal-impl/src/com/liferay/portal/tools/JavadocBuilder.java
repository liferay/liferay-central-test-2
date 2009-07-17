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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.InitUtil;
import com.liferay.util.xml.DocUtil;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.AbstractJavaEntity;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.Type;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.tools.ant.DirectoryScanner;

/**
 * <a href="JavadocBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JavadocBuilder {

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		String command = System.getProperty("javadoc.command");

		new JavadocBuilder(command);
	}

	public JavadocBuilder(String command) {
		try {
			_process(command);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _addClassCommentElement(
		Element rootElement, JavaClass javaClass) {

		Element commentElement = rootElement.addElement("comment");

		String comment = _getCDATA(javaClass);

		if (comment.startsWith(
				"<a href=\"" + javaClass.getName() + ".java.html\">")) {

			int pos = comment.indexOf("</a>");

			comment = comment.substring(pos + 4).trim();
		}

		commentElement.addCDATA(comment);
	}

	private void _addDocletElements(
		Element parentElement, AbstractJavaEntity abstractJavaEntity,
		String name) {

		DocletTag[] docletTags = abstractJavaEntity.getTagsByName(name);

		for (DocletTag docletTag : docletTags) {
			String value = docletTag.getValue();

			DocUtil.add(parentElement, name, value);
		}
	}

	private void _addDocletTags(
		Element parentElement, String name, String indent, StringBuilder sb) {

		List<Element> elements = parentElement.elements(name);

		for (Element element : elements) {
			sb.append(indent);
			sb.append(" * @");
			sb.append(name);
			sb.append(" ");

			Element commentElement = element.element("comment");

			if (commentElement != null) {
				sb.append(element.elementText("name"));
				sb.append(" ");
				sb.append(element.elementText("comment"));
			}
			else {
				sb.append(element.getText());
			}

			sb.append("\n");
		}
	}

	private void _addMethodElement(Element rootElement, JavaMethod javaMethod) {
		Element methodElement = rootElement.addElement("method");

		DocUtil.add(methodElement, "name", javaMethod.getName());

		Element commentElement = methodElement.addElement("comment");

		commentElement.addCDATA(_getCDATA(javaMethod));

		_addDocletElements(methodElement, javaMethod, "deprecated");
		_addParamElements(methodElement, javaMethod);
		_addReturnElement(methodElement, javaMethod);
		_addDocletElements(methodElement, javaMethod, "see");
		_addDocletElements(methodElement, javaMethod, "since");
		_addThrowsElements(methodElement, javaMethod);
		_addDocletElements(methodElement, javaMethod, "version");
	}

	private void _addParamElement(
		Element methodElement, JavaParameter javaParameter,
		DocletTag[] paramDocletTags) {

		String name = javaParameter.getName();
		String type = javaParameter.getType().getValue();

		for (DocletTag paramDocletTag : paramDocletTags) {
			String value = paramDocletTag.getValue();

			if (!value.startsWith(name)) {
				continue;
			}

			Element paramElement = methodElement.addElement("param");

			DocUtil.add(paramElement, "name", name);
			DocUtil.add(paramElement, "type", type);

			value = value.substring(name.length());

			Element commentElement = paramElement.addElement("comment");

			commentElement.addCDATA(_getCDATA(value));

			break;
		}
	}

	private void _addParamElements(
		Element methodElement, JavaMethod javaMethod) {

		JavaParameter[] javaParameters = javaMethod.getParameters();

		DocletTag[] paramDocletTags = javaMethod.getTagsByName("param");

		for (JavaParameter javaParameter : javaParameters) {
			_addParamElement(methodElement, javaParameter, paramDocletTags);
		}
	}

	private void _addReturnElement(
		Element methodElement, JavaMethod javaMethod) {

		Type returns = javaMethod.getReturns();

		if ((returns == null) || returns.getValue().equals("void")) {
			return;
		}

		_addDocletElements(methodElement, javaMethod, "return");
	}

	private void _addThrowsElement(
		Element methodElement, Type exception, DocletTag[] throwsDocletTags) {

		String name = exception.getJavaClass().getName();

		for (DocletTag throwsDocletTag : throwsDocletTags) {
			String value = throwsDocletTag.getValue();

			if (!value.startsWith(name)) {
				continue;
			}

			Element throwsElement = methodElement.addElement("throws");

			DocUtil.add(throwsElement, "name", name);
			DocUtil.add(throwsElement, "type", exception.getValue());

			value = value.substring(name.length());

			Element commentElement = throwsElement.addElement("comment");

			commentElement.addCDATA(_getCDATA(value));

			break;
		}
	}

	private void _addThrowsElements(
		Element methodElement, JavaMethod javaMethod) {

		Type[] exceptions = javaMethod.getExceptions();

		DocletTag[] throwsDocletTags = javaMethod.getTagsByName("throws");

		for (Type exception : exceptions) {
			_addThrowsElement(methodElement, exception, throwsDocletTags);
		}
	}

	private String _getCDATA(AbstractJavaEntity abstractJavaEntity) {
		return _getCDATA(abstractJavaEntity.getComment());
	}

	private String _getCDATA(DocletTag docletTag) {
		return _getCDATA(docletTag.getValue());
	}

	private String _getCDATA(String cdata) {
		if (cdata == null) {
			return StringPool.BLANK;
		}

		cdata = StringUtil.replace(cdata, "\n", " ");

		while (cdata.contains("> ")) {
			cdata = StringUtil.replace(cdata, "> ", ">");
		}

		while (cdata.contains(" <")) {
			cdata = StringUtil.replace(cdata, " <", "<");
		}

		while (cdata.contains("  ")) {
			cdata = StringUtil.replace(cdata, "  ", " ");
		}

		return cdata.trim();
	}

	private JavaClass _getJavaClass(String fileName) throws Exception {
		fileName = StringUtil.replace(fileName, "\\", "/");

		int pos = fileName.indexOf("/");

		String srcFile = fileName.substring(pos + 1, fileName.length());
		String className = StringUtil.replace(
			srcFile.substring(0, srcFile.length() - 5), "/", ".");

		JavaDocBuilder builder = new JavaDocBuilder();

		File file = new File(fileName);

		if (!file.exists()) {
			return null;
		}

		builder.addSource(file);

		return builder.getClassByName(className);
	}

	private String _getJavaClassComment(
		Element rootElement, JavaClass javaClass) {

		StringBuilder sb = new StringBuilder();

		sb.append("/**\n");
		sb.append(" * <a href=\"");
		sb.append(javaClass.getName());
		sb.append(".java.html\"><b><i>View Source</i></b></a>\n");
		sb.append(" *\n");
		sb.append(" * ");
		sb.append(rootElement.elementText("comment"));
		sb.append("\n");
		sb.append(" *\n");

		String indent = " ";

		_addDocletTags(rootElement, "author", indent, sb);
		_addDocletTags(rootElement, "deprecated", indent, sb);
		_addDocletTags(rootElement, "see", indent, sb);
		_addDocletTags(rootElement, "serial", indent, sb);
		_addDocletTags(rootElement, "since", indent, sb);
		_addDocletTags(rootElement, "version", indent, sb);

		sb.append(" */\n");

		return sb.toString();
	}

	private String _getJavadocXml(JavaClass javaClass) throws Exception {
		Element rootElement = SAXReaderUtil.createElement("javadoc");

		Document document = SAXReaderUtil.createDocument(rootElement);

		DocUtil.add(rootElement, "name", javaClass.getName());
		DocUtil.add(rootElement, "type", javaClass.getFullyQualifiedName());

		_addClassCommentElement(rootElement, javaClass);
		_addDocletElements(rootElement, javaClass, "author");
		_addDocletElements(rootElement, javaClass, "deprecated");
		_addDocletElements(rootElement, javaClass, "see");
		_addDocletElements(rootElement, javaClass, "serial");
		_addDocletElements(rootElement, javaClass, "since");
		_addDocletElements(rootElement, javaClass, "version");

		JavaMethod[] javaMethods = javaClass.getMethods();

		for (JavaMethod javaMethod : javaMethods) {
			_addMethodElement(rootElement, javaMethod);
		}

		return document.formattedString();
	}

	private String _getJavaMethodComment(
		String[] lines, Map<String, Element> methodElementsMap,
		JavaMethod javaMethod) {

		String methodKey = _getMethodKey(javaMethod);

		Element methodElement = methodElementsMap.get(methodKey);

		if (methodElement == null) {
			return null;
		}

		String line = lines[javaMethod.getLineNumber() - 1];

		String indent = "";

		for (char c : line.toCharArray()) {
			if (Character.isWhitespace(c)) {
				indent += c;
			}
			else {
				break;
			}
		}

		StringBuilder sb = new StringBuilder();

		sb.append(indent);
		sb.append("/**\n");
		sb.append(indent);
		sb.append(" * ");
		sb.append(methodElement.elementText("comment"));
		sb.append("\n");
		sb.append(indent);
		sb.append(" *\n");

		_addDocletTags(methodElement, "deprecated", indent, sb);
		_addDocletTags(methodElement, "param", indent, sb);
		_addDocletTags(methodElement, "return", indent, sb);
		_addDocletTags(methodElement, "see", indent, sb);
		_addDocletTags(methodElement, "since", indent, sb);
		_addDocletTags(methodElement, "throws", indent, sb);
		_addDocletTags(methodElement, "version", indent, sb);

		sb.append(indent);
		sb.append(" */\n");

		return sb.toString();
	}

	private String _getMethodKey(Element methodElement) {
		StringBuilder sb = new StringBuilder();

		sb.append(methodElement.elementText("name"));
		sb.append("(");

		List<Element> paramElements = methodElement.elements("param");

		for (Element paramElement : paramElements) {
			sb.append(paramElement.elementText("name"));
			sb.append("|");
			sb.append(paramElement.elementText("type"));
			sb.append(",");
		}

		sb.append(")");

		return sb.toString();
	}

	private String _getMethodKey(JavaMethod javaMethod) {
		StringBuilder sb = new StringBuilder();

		sb.append(javaMethod.getName());
		sb.append("(");

		JavaParameter[] javaParameters = javaMethod.getParameters();

		for (JavaParameter javaParameter : javaParameters) {
			sb.append(javaParameter.getName());
			sb.append("|");
			sb.append(javaParameter.getType().getValue());
			sb.append(",");
		}

		sb.append(")");

		return sb.toString();
	}

	private void _process(String command) throws Exception {
		String basedir = "./";

		List<File> list = new ArrayList<File>();

		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir(basedir);
		ds.setIncludes(new String[] {"**\\*.java"});

		ds.scan();

		String[] fileNames = ds.getIncludedFiles();

		for (String fileName : fileNames) {
			if (!fileName.endsWith("WorkflowInstanceManager.java")) {
				continue;
			}

			if (command.equals("delete")) {
				_removeJavadocFromJava(basedir, fileName);
			}
			else if (command.equals("get")) {
				_removeJavadocFromJava(basedir, fileName);
				_updateJavaFromJavadoc(basedir, fileName);
			}
			else if (command.equals("save")) {
				_updateJavadocFromJava(basedir, fileName);
			}
		}
	}

	private void _removeJavadocFromJava(String basedir, String fileName)
		throws Exception {

		File file = new File(basedir + fileName);

		String oldContent = FileUtil.read(file);

		String[] lines = StringUtil.split(oldContent, "\n");

		JavaClass javaClass = _getJavaClass(fileName);

		List<Integer> lineNumbers = new ArrayList<Integer>();

		lineNumbers.add(javaClass.getLineNumber());

		JavaMethod[] javaMethods = javaClass.getMethods();

		for (JavaMethod javaMethod : javaMethods) {
			lineNumbers.add(javaMethod.getLineNumber());
		}

		for (int i = 0; i < lineNumbers.size(); i++) {
			int lineNumber = lineNumbers.get(i);

			int pos = lineNumber - 2;

			String line = lines[pos].trim();

			if (line.endsWith("*/")) {
				while (true) {
					lines[pos] = null;

					if (line.startsWith("/**")) {
						break;
					}

					line = lines[--pos].trim();
				}
			}
		}

		StringBuilder sb = new StringBuilder(oldContent.length());

		for (String line : lines) {
			if (line != null) {
				sb.append(line);
				sb.append("\n");
			}
		}

		String newContent = sb.toString().trim();

		if ((oldContent == null) || !oldContent.equals(newContent)) {
			FileUtil.write(file, newContent);

			System.out.println("Writing " + file);
		}
	}

	private void _updateJavadocFromJava(String basedir, String fileName)
		throws Exception {

		File file = new File(basedir + fileName + "doc");

		JavaClass javaClass = _getJavaClass(fileName);

		String javadocXml = _getJavadocXml(javaClass);

		_writeFile(file, javadocXml);
	}

	private void _updateJavaFromJavadoc(String basedir, String fileName)
		throws Exception {

		File javadocFile = new File(basedir + fileName + "doc");

		if (!javadocFile.exists()) {
			return;
		}

		File file = new File(basedir + fileName);

		String oldContent = FileUtil.read(file);

		String[] lines = StringUtil.split(oldContent, "\n");

		JavaClass javaClass = _getJavaClass(fileName);

		Document javadocDocument = SAXReaderUtil.read(javadocFile);

		Element rootElement = javadocDocument.getRootElement();

		Map<Integer, String> commentsMap = new TreeMap<Integer, String>();

		commentsMap.put(
			javaClass.getLineNumber(),
			_getJavaClassComment(rootElement, javaClass));

		Map<String, Element> methodElementsMap = new HashMap<String, Element>();

		List<Element> methodElements = rootElement.elements("method");

		for (Element methodElement : methodElements) {
			String methodKey = _getMethodKey(methodElement);

			methodElementsMap.put(methodKey, methodElement);
		}

		JavaMethod[] javaMethods = javaClass.getMethods();

		for (JavaMethod javaMethod : javaMethods) {
			commentsMap.put(
				javaMethod.getLineNumber(),
				_getJavaMethodComment(lines, methodElementsMap, javaMethod));
		}

		StringBuilder sb = new StringBuilder(oldContent.length());

		for (int lineNumber = 1; lineNumber <= lines.length; lineNumber++) {
			String line = lines[lineNumber - 1];

			String comments = commentsMap.get(lineNumber);

			if (comments != null) {
				sb.append(comments);
			}

			sb.append(line);
			sb.append("\n");
		}

		String newContent = sb.toString().trim();

		if ((oldContent == null) || !oldContent.equals(newContent)) {
			FileUtil.write(file, newContent);

			System.out.println("Writing " + file);
		}
	}

	private void _writeFile(File file, String newContent) throws Exception {
		String oldContent = null;

		if (file.exists()) {
			oldContent = FileUtil.read(file);
		}

		if ((oldContent == null) || !oldContent.equals(newContent)) {
			FileUtil.write(file, newContent);

			System.out.println("Writing " + file);
		}
	}

}