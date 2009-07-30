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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.xml.SAXReaderImpl;
import com.liferay.util.xml.DocUtil;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.AbstractJavaEntity;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.Type;

import jargs.gnu.CmdLineParser;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.tools.ant.DirectoryScanner;

public class JavadocFormatter {

	public static void main(String[] args) {
		try {
			new JavadocFormatter(args);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JavadocFormatter(String[] args) throws Exception {
		CmdLineParser cmdLineParser = new CmdLineParser();

		CmdLineParser.Option limitOption = cmdLineParser.addStringOption(
			"limit");
		CmdLineParser.Option initOption = cmdLineParser.addStringOption(
			"init");

		cmdLineParser.parse(args);

		String limit = (String)cmdLineParser.getOptionValue(limitOption);
		String init = (String)cmdLineParser.getOptionValue(initOption);

		if (!init.startsWith("$")) {
			_initializeMissingJavadocs = GetterUtil.getBoolean(init);
		}

		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir(_basedir);
		ds.setExcludes(
			new String[] {
				"**\\classes\\**", "**\\portal-client\\**",
				"**\\portal-web\\**\\*Test.java"
			});

		List<String> includes = new ArrayList<String>();

		if (Validator.isNotNull(limit) && !limit.startsWith("$")) {
			String[] limitArray = StringUtil.split(limit, "/");

			for (String curLimit : limitArray) {
				includes.add(
					"**\\" + StringUtil.replace(curLimit, ".", "\\") +
						"\\**\\*.java");
				includes.add("**\\" + curLimit + ".java");
			}
		}
		else {
			includes.add("**\\*.java");
		}

		ds.setIncludes(includes.toArray(new String[includes.size()]));

		ds.scan();

		String[] fileNames = ds.getIncludedFiles();

		for (String fileName : fileNames) {
			fileName = StringUtil.replace(fileName, "\\", "/");

			_format(fileName);
		}
	}

	private void _addClassCommentElement(
		Element rootElement, JavaClass javaClass) {

		Element commentElement = rootElement.addElement("comment");

		String comment = _getCDATA(javaClass);

		if (comment.startsWith("Copyright (c) 2000-2009 Liferay, Inc.")) {
			comment = StringPool.BLANK;
		}

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

			if (name.equals("author") || name.equals("see") ||
				name.equals("since") || name.equals("version")) {

				if (value.startsWith("Raymond Aug")) {
					value = "Raymond Augé";
				}

				DocUtil.add(parentElement, name, value);
			}
			else {
				Element element = parentElement.addElement(name);

				element.addCDATA(value);
			}
		}

		if ((_initializeMissingJavadocs) &&
			(docletTags.length == 0) && (name.equals("author"))) {

			DocUtil.add(parentElement, name, "");
		}
	}

	private String _addDocletTags(
		Element parentElement, String[] names, String indent) {

		StringBuilder sb = new StringBuilder();

		int maxNameLength = 0;

		for (String name : names) {
			if (name.length() < maxNameLength) {
				continue;
			}

			List<Element> elements = parentElement.elements(name);

			for (Element element : elements) {
				Element commentElement = element.element("comment");

				String comment = null;

				if (commentElement != null) {
					comment = commentElement.getText();
				}
				else {
					comment = element.getText();
				}

				if (!_initializeMissingJavadocs && Validator.isNull(comment)) {
					continue;
				}

				maxNameLength = name.length();

				break;
			}
		}

		int indentLength =_getIndentLength(indent) + maxNameLength;

		String maxNameIndent = "\t ";

		for (int i = 0; i < maxNameLength; i++) {
			maxNameIndent += " ";
		}

		maxNameIndent = StringUtil.replace(maxNameIndent, "    ", "\t");

		for (String name : names) {
			List<Element> elements = parentElement.elements(name);

			for (Element element : elements) {
				Element commentElement = element.element("comment");

				String comment = null;

				if (commentElement != null) {
					comment = commentElement.getText();
				}
				else {
					comment = element.getText();
				}

				if (!_initializeMissingJavadocs && Validator.isNull(comment)) {
					continue;
				}

				sb.append(indent);
				sb.append(" * @");
				sb.append(name);
				sb.append(" ");

				for (int i = 0; i < maxNameLength - name.length(); i++) {
					sb.append(" ");
				}

				if (commentElement != null) {
					comment = element.elementText("name") + " " + comment;
				}

				comment = StringUtil.wrap(comment, 80 - indentLength - 5, "\n");

				comment = comment.trim();

				comment = StringUtil.replace(
					comment, "\n", "\n" + indent + " *" + maxNameIndent);

				while (comment.contains(" \n")) {
					comment = StringUtil.replace(comment, " \n", "\n");
				}

				while (comment.startsWith("\n")) {
					comment = comment.substring(1, comment.length());
				}

				sb.append(comment);
				sb.append("\n");
			}
		}

		return sb.toString();
	}

	private void _addFieldElement(Element rootElement, JavaField javaField) {
		Element fieldElement = rootElement.addElement("field");

		DocUtil.add(fieldElement, "name", javaField.getName());

		Element commentElement = fieldElement.addElement("comment");

		commentElement.addCDATA(_getCDATA(javaField));

		_addDocletElements(fieldElement, javaField, "version");
		_addDocletElements(fieldElement, javaField, "see");
		_addDocletElements(fieldElement, javaField, "since");
		_addDocletElements(fieldElement, javaField, "deprecated");
	}

	private void _addMethodElement(Element rootElement, JavaMethod javaMethod) {
		Element methodElement = rootElement.addElement("method");

		DocUtil.add(methodElement, "name", javaMethod.getName());

		Element commentElement = methodElement.addElement("comment");

		commentElement.addCDATA(_getCDATA(javaMethod));

		_addDocletElements(methodElement, javaMethod, "version");
		_addParamElements(methodElement, javaMethod);
		_addReturnElement(methodElement, javaMethod);
		_addThrowsElements(methodElement, javaMethod);
		_addDocletElements(methodElement, javaMethod, "see");
		_addDocletElements(methodElement, javaMethod, "since");
		_addDocletElements(methodElement, javaMethod, "deprecated");
	}

	private void _addParamElement(
		Element methodElement, JavaParameter javaParameter,
		DocletTag[] paramDocletTags) {

		String name = javaParameter.getName();
		String type = javaParameter.getType().getValue();
		String value = null;

		for (DocletTag paramDocletTag : paramDocletTags) {
			String curValue = paramDocletTag.getValue();

			if (!curValue.startsWith(name)) {
				continue;
			}
			else {
				curValue = value;

				break;
			}
		}

		Element paramElement = methodElement.addElement("param");

		DocUtil.add(paramElement, "name", name);
		DocUtil.add(paramElement, "type", type);

		if (value != null) {
			value = value.substring(name.length());
		}

		Element commentElement = paramElement.addElement("comment");

		commentElement.addCDATA(_getCDATA(value));
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
		String value = null;

		for (DocletTag throwsDocletTag : throwsDocletTags) {
			String curValue = throwsDocletTag.getValue();

			if (!curValue.startsWith(name)) {
				continue;
			}
			else {
				curValue = value;

				break;
			}
		}

		Element throwsElement = methodElement.addElement("throws");

		DocUtil.add(throwsElement, "name", name);
		DocUtil.add(throwsElement, "type", exception.getValue());

		if (value != null) {
			value = value.substring(name.length());
		}

		Element commentElement = throwsElement.addElement("comment");

		commentElement.addCDATA(_getCDATA(value));

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

	private String _getCDATA(String cdata) {
		if (cdata == null) {
			return StringPool.BLANK;
		}

		cdata = StringUtil.replace(
			cdata,
			new String[] {"\n", "<p>", "</p>"},
			new String[] {" ", " \n<p>\n", "\n</p>\n"});

		while (cdata.contains("\n ")) {
			cdata = StringUtil.replace(cdata, "\n ", "\n");
		}

		while (cdata.contains("  ")) {
			cdata = StringUtil.replace(cdata, "  ", " ");
		}

		cdata = StringUtil.replace(cdata, "</p>\n<p>", "</p>\n\n<p>");

		return cdata.trim();
	}

	private String _getFieldKey(Element fieldElement) {
		return fieldElement.elementText("name");
	}

	private String _getFieldKey(JavaField javaField) {
		return javaField.getName();
	}

	private int _getIndentLength(String indent) {
		int indentLength = 0;

		for (char c : indent.toCharArray()) {
			if (c == '\t') {
				indentLength = indentLength + 4;
			}
			else {
				indentLength++;
			}
		}

		return indentLength;
	}

	private JavaClass _getJavaClass(String fileName, Reader reader)
		throws Exception {

		int pos = fileName.indexOf("src/");

		if (pos == -1) {
			pos = fileName.indexOf("test/");
		}

		if (pos == -1) {
			throw new RuntimeException(fileName);
		}

		pos = fileName.indexOf("/", pos);

		String srcFile = fileName.substring(pos + 1, fileName.length());
		String className = StringUtil.replace(
			srcFile.substring(0, srcFile.length() - 5), "/", ".");

		JavaDocBuilder builder = new JavaDocBuilder();

		if (reader == null) {
			File file = new File(fileName);

			if (!file.exists()) {
				return null;
			}

			builder.addSource(file);
		}
		else {
			builder.addSource(reader);
		}

		return builder.getClassByName(className);
	}

	private String _getJavaClassComment(
		Element rootElement, JavaClass javaClass) {

		StringBuilder sb = new StringBuilder();

		String indent = StringPool.BLANK;

		sb.append("/**\n");
		sb.append(" * <a href=\"");
		sb.append(javaClass.getName());
		sb.append(".java.html\"><b><i>View Source</i></b></a>\n");

		String comment = rootElement.elementText("comment");

		if (_initializeMissingJavadocs || Validator.isNotNull(comment)) {
			sb.append(" *\n");
			sb.append(_wrapText(comment, indent));
			sb.append("\n");
		}

		String docletTags = _addDocletTags(
			rootElement,
			new String[] {
				"author", "verversionsion", "see", "since", "serial",
				"deprecated"
			},
			indent);

		if (docletTags.length() > 0) {
			//if (_initializeMissingJavadocs || Validator.isNotNull(comment)) {
				sb.append(" *\n");
			//}

			sb.append(docletTags);
		}

		sb.append(" */\n");

		return sb.toString();
	}

	private Document _getJavadocDocument(JavaClass javaClass) throws Exception {
		Element rootElement = _saxReaderUtil.createElement("javadoc");

		Document document = _saxReaderUtil.createDocument(rootElement);

		DocUtil.add(rootElement, "name", javaClass.getName());
		DocUtil.add(rootElement, "type", javaClass.getFullyQualifiedName());

		_addClassCommentElement(rootElement, javaClass);
		_addDocletElements(rootElement, javaClass, "author");
		_addDocletElements(rootElement, javaClass, "version");
		_addDocletElements(rootElement, javaClass, "see");
		_addDocletElements(rootElement, javaClass, "since");
		_addDocletElements(rootElement, javaClass, "serial");
		_addDocletElements(rootElement, javaClass, "deprecated");

		JavaMethod[] javaMethods = javaClass.getMethods();

		for (JavaMethod javaMethod : javaMethods) {
			_addMethodElement(rootElement, javaMethod);
		}

		JavaField[] javaFields = javaClass.getFields();

		for (JavaField javaField : javaFields) {
			_addFieldElement(rootElement, javaField);
		}

		return document;
	}

	private String _getJavaFieldComment(
		String[] lines, Map<String, Element> fieldElementsMap,
		JavaField javaField) {

		String fieldKey = _getFieldKey(javaField);

		Element fieldElement = fieldElementsMap.get(fieldKey);

		if (fieldElement == null) {
			return null;
		}

		String line = lines[javaField.getLineNumber() - 1];

		String indent = StringPool.BLANK;

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

		String comment = fieldElement.elementText("comment");

		if (_initializeMissingJavadocs || Validator.isNotNull(comment)) {
			sb.append(_wrapText(comment, indent));
			sb.append("\n");
		}

		String docletTags = _addDocletTags(
			fieldElement,
			new String[] {"version", "see", "since", "deprecated"}, indent);

		if (docletTags.length() > 0) {
			if (_initializeMissingJavadocs || Validator.isNotNull(comment)) {
				sb.append(indent);
				sb.append(" *\n");
			}

			sb.append(docletTags);
		}

		sb.append(indent);
		sb.append(" */\n");

		if (!_initializeMissingJavadocs && Validator.isNull(comment) &&
			Validator.isNull(docletTags)) {

			return null;
		}

		return sb.toString();
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

		String indent = StringPool.BLANK;

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

		String comment = methodElement.elementText("comment");

		if (_initializeMissingJavadocs || Validator.isNotNull(comment)) {
			sb.append(_wrapText(comment, indent));
			sb.append("\n");
		}

		String docletTags = _addDocletTags(
			methodElement,
			new String[] {
				"version", "param", "return", "throws", "see", "since",
				"deprecated"
			},
			indent);

		if (docletTags.length() > 0) {
			if (_initializeMissingJavadocs || Validator.isNotNull(comment)) {
				sb.append(indent);
				sb.append(" *\n");
			}

			sb.append(docletTags);
		}

		sb.append(indent);
		sb.append(" */\n");

		if (!_initializeMissingJavadocs && Validator.isNull(comment) &&
			Validator.isNull(docletTags)) {

			return null;
		}

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

	private boolean _isGenerated(String content) {
		if (content.contains("* @generated")) {
			return true;
		}
		else {
			return false;
		}
	}

	private String _removeJavadocFromJava(
		JavaClass javaClass, String content) {

		Set<Integer> lineNumbers = new HashSet<Integer>();

		lineNumbers.add(javaClass.getLineNumber());

		JavaMethod[] javaMethods = javaClass.getMethods();

		for (JavaMethod javaMethod : javaMethods) {
			lineNumbers.add(javaMethod.getLineNumber());
		}

		JavaField[] javaFields = javaClass.getFields();

		for (JavaField javaField : javaFields) {
			lineNumbers.add(javaField.getLineNumber());
		}

		String[] lines = StringUtil.split(content, "\n");

		for (int lineNumber : lineNumbers) {
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

		StringBuilder sb = new StringBuilder(content.length());

		for (String line : lines) {
			if (line != null) {
				sb.append(line);
				sb.append("\n");
			}
		}

		return sb.toString().trim();
	}

	private void _format(String fileName) throws Exception {
		String originalContent = _fileUtil.read(
			new File(_basedir + fileName));

		if (_isGenerated(originalContent)) {
			return;
		}

		JavaClass javaClass = _getJavaClass(
			fileName, new StringReader(originalContent));

		String javadocLessContent = _removeJavadocFromJava(
			javaClass, originalContent);

		Document document = _getJavadocDocument(javaClass);

		_updateJavaFromDocument(
			fileName, originalContent, javadocLessContent, document);
	}

	private void _updateJavaFromDocument(
			String fileName, String originalContent, String javadocLessContent,
			Document document)
		throws Exception {

		String[] lines = StringUtil.split(javadocLessContent, "\n");

		JavaClass javaClass = _getJavaClass(
			fileName, new StringReader(javadocLessContent));

		Element rootElement = document.getRootElement();

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
			if (commentsMap.containsKey(javaMethod.getLineNumber())) {
				continue;
			}

			commentsMap.put(
				javaMethod.getLineNumber(),
				_getJavaMethodComment(lines, methodElementsMap, javaMethod));
		}

		Map<String, Element> fieldElementsMap = new HashMap<String, Element>();

		List<Element> fieldElements = rootElement.elements("field");

		for (Element fieldElement : fieldElements) {
			String fieldKey = _getFieldKey(fieldElement);

			fieldElementsMap.put(fieldKey, fieldElement);
		}

		JavaField[] javaFields = javaClass.getFields();

		for (JavaField javaField : javaFields) {
			if (commentsMap.containsKey(javaField.getLineNumber())) {
				continue;
			}

			commentsMap.put(
				javaField.getLineNumber(),
				_getJavaFieldComment(lines, fieldElementsMap, javaField));
		}

		StringBuilder sb = new StringBuilder(javadocLessContent.length());

		for (int lineNumber = 1; lineNumber <= lines.length; lineNumber++) {
			String line = lines[lineNumber - 1];

			String comments = commentsMap.get(lineNumber);

			if (comments != null) {
				sb.append(comments);
			}

			sb.append(line);
			sb.append("\n");
		}

		String formattedContent = sb.toString().trim();

		formattedContent = new String(
			formattedContent.getBytes(), StringPool.UTF8);

		if (!originalContent.equals(formattedContent)) {
			File file = new File(_basedir + fileName);

			_fileUtil.write(file, formattedContent);

			System.out.println("Writing " + file);
		}
	}

	private String _wrapText(String text, String indent) {
		int indentLength = _getIndentLength(indent);

		text = StringUtil.wrap(text, 80 - indentLength - 3, "\n");

		text = "\n" + text.trim();

		text = StringUtil.replace(text, "\n", "\n" + indent + " * ");

		while (text.contains(" \n")) {
			text = StringUtil.replace(text, " \n", "\n");
		}

		while (text.startsWith("\n")) {
			text = text.substring(1, text.length());
		}

		return text;
	}

	private static FileImpl _fileUtil = FileImpl.getInstance();
	private static SAXReaderImpl _saxReaderUtil = SAXReaderImpl.getInstance();

	private String _basedir = "./";
	private boolean _initializeMissingJavadocs;

}