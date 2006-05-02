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

import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portal.shared.util.ClassUtil;
import com.liferay.portal.util.EntityResolver;
import com.liferay.portal.util.PortletKeys;
import com.liferay.util.FileUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.SimpleCachePool;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.TextFormatter;
import com.liferay.util.Time;
import com.liferay.util.Validator;

import de.hunsicker.io.FileFormat;
import de.hunsicker.jalopy.Jalopy;
import de.hunsicker.jalopy.storage.Convention;
import de.hunsicker.jalopy.storage.ConventionKeys;
import de.hunsicker.jalopy.storage.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import xjavadoc.Type;
import xjavadoc.XClass;
import xjavadoc.XJavaDoc;
import xjavadoc.XMethod;
import xjavadoc.XParameter;

import xjavadoc.filesystem.FileSourceSet;

/**
 * <a href="ServiceBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 * @author  Charles May
 *
 */
public class ServiceBuilder {

	public static void main(String[] args) {
		if (args.length == 6) {
			new ServiceBuilder(
				args[0], args[1], args[2], args[3], args[4], args[5]);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public static Set getBadCmpFields() {
		Set badCmpFields = new HashSet();

		badCmpFields.add("access");
		badCmpFields.add("active");
		badCmpFields.add("alias");
		badCmpFields.add("date");
		badCmpFields.add("end");
		badCmpFields.add("featured");
		badCmpFields.add("fields");
		badCmpFields.add("from");
		badCmpFields.add("hidden");
		badCmpFields.add("index");
		badCmpFields.add("internal");
		badCmpFields.add("interval");
		badCmpFields.add("join");
		badCmpFields.add("key");
		badCmpFields.add("log");
		badCmpFields.add("number");
		badCmpFields.add("password");
		badCmpFields.add("primary");
		badCmpFields.add("sale");
		badCmpFields.add("settings");
		badCmpFields.add("size");
		badCmpFields.add("start");
		badCmpFields.add("text");
		badCmpFields.add("to");
		badCmpFields.add("type");
		badCmpFields.add("values");

		return badCmpFields;
	}

	public static Set getBadTableNames() {
		Set badTableNames = new HashSet();

		badTableNames = new HashSet();
		badTableNames.add("Account");
		badTableNames.add("Action");
		badTableNames.add("Cache");
		badTableNames.add("Contact");
		badTableNames.add("Group");
		badTableNames.add("Organization");
		badTableNames.add("Permission");
		badTableNames.add("Release");
		badTableNames.add("Resource");
		badTableNames.add("Role");
		badTableNames.add("User");

		return badTableNames;
	}

	public static void writeFile(File file, String content) throws IOException {
		writeFile(file, content, "Brian Wing Shun Chan");
	}

	public static void writeFile(File file, String content, String author)
		throws IOException {

		File tempFile = new File("ServiceBuilder.temp");

		FileUtil.write(tempFile, content);

		// Strip unused imports

		String[] checkImports = new String[] {
			"com.liferay.portal.PortalException",
			"com.liferay.portal.security.auth.HttpPrincipal",
			"com.liferay.portal.service.impl.PrincipalSessionBean",
			"com.liferay.portal.servlet.TunnelUtil",
			"com.liferay.portal.spring.hibernate.HibernateUtil",
			"com.liferay.portal.util.PropsUtil",
			"com.liferay.portal.shared.util.BooleanWrapper",
			"com.liferay.portal.shared.util.DoubleWrapper",
			"com.liferay.portal.shared.util.FloatWrapper",
			"com.liferay.portal.shared.util.IntegerWrapper",
			"com.liferay.portal.shared.util.LongWrapper",
			"com.liferay.portal.shared.util.MethodWrapper",
			"com.liferay.portal.shared.util.NullWrapper",
			"com.liferay.portal.shared.util.ShortWrapper",
			"com.liferay.portal.shared.util.StackTraceUtil",
			"com.liferay.util.GetterUtil",
			"com.liferay.util.InstancePool",
			"com.liferay.util.StringPool",
			"com.liferay.util.ObjectValuePair",
			"com.liferay.util.dao.hibernate.OrderByComparator",
			"com.liferay.util.dao.hibernate.QueryPos",
			"com.liferay.util.dao.hibernate.QueryUtil",
			"java.rmi.RemoteException",
			"java.util.Collection",
			"java.util.Collections",
			"java.util.Date",
			"java.util.HashSet",
			"java.util.Properties",
			"java.util.Set",
			"org.hibernate.Hibernate",
			"org.hibernate.ObjectNotFoundException",
			"org.hibernate.Query",
			"org.hibernate.SQLQuery"
		};

		Set classes = ClassUtil.getClasses(tempFile);

		for (int i = 0; i < checkImports.length; i++) {
			String importClass = checkImports[i].substring(
				checkImports[i].lastIndexOf(".") + 1, checkImports[i].length());

			if (!classes.contains(importClass)) {
				content = StringUtil.replace(
					content, "import " + checkImports[i] + ";", "");
			}
		}

		FileUtil.write(tempFile, content);

		// Beautify

		StringBuffer sb = new StringBuffer();

		Jalopy jalopy = new Jalopy();
		jalopy.setFileFormat(FileFormat.UNIX);
		jalopy.setInput(tempFile);
		jalopy.setOutput(sb);

		try {
			Jalopy.setConvention("../tools/jalopy.xml");
		}
		catch (FileNotFoundException fnne) {
		}

		Environment env = Environment.getInstance();
		env.set("author", author);
		env.set("fileName", file.getName());

		Convention convention = Convention.getInstance();

		convention.put(
			ConventionKeys.COMMENT_JAVADOC_TEMPLATE_CLASS,
			env.interpolate(convention.get(
				ConventionKeys.COMMENT_JAVADOC_TEMPLATE_CLASS, "")));

		convention.put(
			ConventionKeys.COMMENT_JAVADOC_TEMPLATE_INTERFACE,
			env.interpolate(convention.get(
				ConventionKeys.COMMENT_JAVADOC_TEMPLATE_INTERFACE, "")));

		jalopy.format();

		String newContent = sb.toString();

		/*
		// Remove blank lines after try {

		newContent = StringUtil.replace(newContent, "try {\n\n", "try {\n");

		// Remove blank lines after ) {

		newContent = StringUtil.replace(newContent, ") {\n\n", ") {\n");

		// Remove blank lines empty braces { }

		newContent = StringUtil.replace(newContent, "\n\n\t}", "\n\t}");

		// Add space to last }

		newContent = newContent.substring(0, newContent.length() - 2) + "\n\n}";
		*/

		// Write file if and only if the file has changed

		String oldContent = null;

		if (file.exists()) {

			// Read file

			oldContent = FileUtil.read(file);

			// Keep old version number

			int x = oldContent.indexOf("@version $Revision:");

			if (x != -1) {
				int y = oldContent.indexOf("$", x);
				y = oldContent.indexOf("$", y + 1);

				String oldVersion = oldContent.substring(x, y + 1);

				newContent = StringUtil.replace(
					newContent, "@version $Rev: $", oldVersion);
			}
		}
		else {
			newContent = StringUtil.replace(
				newContent, "@version $Rev: $", "@version $Revision: 1.183 $");
		}

		if (oldContent == null || !oldContent.equals(newContent)) {
			FileUtil.write(file, newContent);

			System.out.println(file);

			// Workaround for bug with XJavaDoc

			file.setLastModified(
				System.currentTimeMillis() - (Time.SECOND * 5));
		}

		tempFile.deleteOnExit();
	}

	public ServiceBuilder(String fileName, String hbmFileName,
						  String modelHintsFileName, String springEntFileName,
						  String springProFileName,
						  String sprintUtilClassName) {

		new ServiceBuilder(
			fileName, hbmFileName, modelHintsFileName, springEntFileName,
			springProFileName, sprintUtilClassName, true);
	}

	public ServiceBuilder(String fileName, String hbmFileName,
						  String modelHintsFileName, String springEntFileName,
						  String springProFileName, String sprintUtilClassName,
						  boolean build) {

		try {
			_badTableNames = ServiceBuilder.getBadTableNames();
			_badCmpFields = ServiceBuilder.getBadCmpFields();

			_hbmFileName = hbmFileName;
			_modelHintsFileName = modelHintsFileName;
			_springEntFileName = springEntFileName;
			_springProFileName = springProFileName;
			_springUtilClassName = sprintUtilClassName;

			SAXReader reader = new SAXReader(true);

			reader.setEntityResolver(new EntityResolver());

			Document doc = reader.read(new File(fileName));

			Element root = doc.getRootElement();

			_portalRoot = root.attributeValue("root-dir");

			String packagePath = root.attributeValue("package-path");

			_portletName = root.element("portlet").attributeValue("name");

			_portletShortName =
				root.element("portlet").attributeValue("short-name");

			_portletClassName =
				TextFormatter.format(_portletName, TextFormatter.D);

			_portletPackageName =
				TextFormatter.format(_portletName, TextFormatter.B);

			_outputPath =
				"src/" + StringUtil.replace(packagePath, ".", "/") + "/" +
					_portletPackageName;

			_packagePath = packagePath + "." + _portletPackageName;

			_ejbList = new ArrayList();

			List entities = root.elements("entity");

			Iterator itr1 = entities.iterator();

			while (itr1.hasNext()) {
				Element entityEl = (Element)itr1.next();

				String ejbName = entityEl.attributeValue("name");

				String table = entityEl.attributeValue("table");
				if (Validator.isNull(table)) {
					table = ejbName;

					if (_badTableNames.contains(ejbName)) {
						table += "_";
					}
				}

				boolean localService = GetterUtil.get(
					entityEl.attributeValue("local-service"), false);
				boolean remoteService = GetterUtil.get(
					entityEl.attributeValue("remote-service"), true);
				String persistenceClass = GetterUtil.get(
					entityEl.attributeValue("persistence-class"),
					_packagePath + ".service.persistence."+ ejbName +
						"Persistence");

				Iterator itr2 = null;

				List pkList = new ArrayList();
				List collectionList = new ArrayList();
				List regularColList = new ArrayList();
				List columnList = new ArrayList();

				List columns = entityEl.elements("column");

				itr2 = columns.iterator();

				while (itr2.hasNext()) {
					Element column = (Element)itr2.next();

					String columnName = column.attributeValue("name");

					String columnDBName = column.attributeValue("db-name");
					if (Validator.isNull(columnDBName)) {
						columnDBName = columnName;

						if (_badCmpFields.contains(columnName)) {
							columnDBName += "_";
						}
					}

					String columnType = column.attributeValue("type");
					boolean primary = GetterUtil.get(
						column.attributeValue("primary"), false);
					String collectionEntity = column.attributeValue("entity");
					String mappingKey = column.attributeValue("mapping-key");
					String mappingTable = column.attributeValue("mapping-table");
					String idType = column.attributeValue("id-type");
					String idParam = column.attributeValue("id-param");

					EntityColumn col = new EntityColumn(
						columnName, columnDBName, columnType, primary,
						collectionEntity, mappingKey, mappingTable, idType, idParam);

					if (primary) {
						pkList.add(col);
					}

					if (columnType.equals("Collection")) {
						collectionList.add(col);
					}
					else {
						regularColList.add(col);
					}

					columnList.add(col);
				}

				EntityOrder order = null;

				Element orderEl = entityEl.element("order");

				if (orderEl != null) {
					boolean asc = true;
					if ((orderEl.attribute("by") != null) &&
						(orderEl.attributeValue("by").equals("desc"))) {

						asc = false;
					}

					List orderColsList = new ArrayList();

					order = new EntityOrder(asc, orderColsList);

					List orderCols = orderEl.elements("order-column");

					Iterator k = orderCols.iterator();

					while (k.hasNext()) {
						Element orderColEl = (Element)k.next();

						String orderColName =
							orderColEl.attributeValue("name");
						boolean orderColCaseSensitive = GetterUtil.get(
							orderColEl.attributeValue("case-sensitive"),
							true);

						boolean orderColByAscending = asc;
						String orderColBy = GetterUtil.getString(
							orderColEl.attributeValue("order-by"));
						if (orderColBy.equals("asc")) {
							orderColByAscending = true;
						}
						else if (orderColBy.equals("desc")) {
							orderColByAscending = false;
						}

						EntityColumn col = Entity.getColumn(
							orderColName, columnList);

						col = (EntityColumn)col.clone();

						col.setCaseSensitive(orderColCaseSensitive);
						col.setOrderByAscending(orderColByAscending);

						orderColsList.add(col);
					}
				}

				List finderList = new ArrayList();

				List finders = entityEl.elements("finder");

				itr2 = finders.iterator();

				while (itr2.hasNext()) {
					Element finder = (Element)itr2.next();

					String finderName = finder.attributeValue("name");
					String finderReturn =
						finder.attributeValue("return-type");
					String finderWhere =
						finder.attributeValue("where");
					boolean finderDBIndex = GetterUtil.get(
						finder.attributeValue("db-index"), true);

					List finderColsList = new ArrayList();

					List finderCols = finder.elements("finder-column");

					Iterator k = finderCols.iterator();

					while (k.hasNext()) {
						Element finderEl = (Element)k.next();

						String finderColName =
							finderEl.attributeValue("name");

						String finderColDBName =
							finderEl.attributeValue("db-name");
						if (Validator.isNull(finderColDBName)) {
							finderColDBName = finderColName;

							if (_badCmpFields.contains(finderColName)) {
								finderColDBName += "_";
							}
						}

						String finderColComparator = GetterUtil.get(
							finderEl.attributeValue("comparator"), "=");

						EntityColumn col = Entity.getColumn(
							finderColName, columnList);

						col = (EntityColumn)col.clone();

						col.setDBName(finderColDBName);
						col.setComparator(finderColComparator);

						finderColsList.add(col);
					}

					finderList.add(new EntityFinder(
						finderName, finderReturn, finderColsList, finderWhere,
						finderDBIndex));
				}

				List referenceList = new ArrayList();

				if (build) {
					List references = entityEl.elements("reference");

					itr2 = references.iterator();

					while (itr2.hasNext()) {
						Element reference = (Element)itr2.next();

						String refPackage =
							reference.attributeValue("package-path");
						String refEntity = reference.attributeValue("entity");

						if ((refPackage == null) || (refEntity == null)) {
							referenceList.add(reference.getText().trim());
						}
						else {
							referenceList.add(
								getEntity(refPackage + "." + refEntity));
						}
					}
				}

				_ejbList.add(
					new Entity(
						_packagePath, _portletName, _portletShortName, ejbName,
						table, localService, remoteService, persistenceClass,
						pkList, regularColList, collectionList, columnList,
						order, finderList, referenceList));
			}

			List exceptionList = new ArrayList();

			if (root.element("exceptions") != null) {
				List exceptions =
					root.element("exceptions").elements("exception");

				itr1 = exceptions.iterator();

				while (itr1.hasNext()) {
					Element exception = (Element)itr1.next();

					exceptionList.add(exception.getText());
				}
			}

			if (build) {
				for (int x = 0; x < _ejbList.size(); x++) {
					Entity entity = (Entity)_ejbList.get(x);

					System.out.println("Building " + entity.getName());

					if (entity.hasColumns()) {
						_createHBM(entity);
						_createHBMUtil(entity);

						_createPersistence(entity);
						_createPersistenceUtil(entity);

						_createModel(entity);
						_createExtendedModel(entity);

						if (entity.getPKList().size() > 1) {
							_createEJBPK(entity);
						}
					}

					if (entity.hasLocalService()) {
						_createServiceImpl(entity, _LOCAL);
						_createService(entity, _LOCAL);
						_createServiceEJB(entity, _LOCAL);
						_createServiceEJBImpl(entity, _LOCAL);
						_createServiceHome(entity, _LOCAL);
						_createServiceFactory(entity, _LOCAL);
						_createServiceUtil(entity, _LOCAL);
					}

					if (entity.hasRemoteService()) {
						_createServiceImpl(entity, _REMOTE);
						_createService(entity, _REMOTE);
						_createServiceEJB(entity, _REMOTE);
						_createServiceEJBImpl(entity, _REMOTE);
						_createServiceHome(entity, _REMOTE);
						_createServiceFactory(entity, _REMOTE);
						_createServiceUtil(entity, _REMOTE);

						_createServiceHttp(entity);
						_createServiceSoap(entity);
					}
				}

				_createEJBXML();
				_createHBMXML();
				_createModelHintsXML();
				_createSpringXML(true);
				_createSpringXML(false);

				_createSQLIndexes();
				_createSQLTables();
				_createSQLSequences();

				_createExceptions(exceptionList);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Entity getEntity(String name) throws IOException {
		int pos = name.lastIndexOf(".");

		if (pos == -1) {
			pos = _ejbList.indexOf(new Entity(name));

			return (Entity)_ejbList.get(pos);
		}
		else {
			String refPackage = name.substring(0, pos);
			String refPackageDir = StringUtil.replace(refPackage, ".", "/");
			String refEntity = name.substring(pos + 1, name.length());
			String refFileName = "src/" + refPackageDir + "/service.xml";

			File refFile = new File(refFileName);

			boolean useTempFile = false;

			if (!refFile.exists()) {
				refFileName = Time.getTimestamp();
				refFile = new File(refFileName);

				ClassLoader classLoader = getClass().getClassLoader();

				FileUtil.write(
					refFileName,
					StringUtil.read(
						classLoader, refPackageDir + "/service.xml"));

				useTempFile = true;
			}

			ServiceBuilder serviceBuilder = new ServiceBuilder(
				refFileName, _hbmFileName, _modelHintsFileName,
				_springEntFileName, _springProFileName, _springUtilClassName,
				false);

			Entity entity = serviceBuilder.getEntity(refEntity);

			if (useTempFile) {
				refFile.deleteOnExit();
			}

			return entity;
		}
	}

	private void _createEJBPK(Entity entity) throws IOException {
		List pkList = entity.getPKList();

		StringBuffer sb = new StringBuffer();

		// Package

		sb.append("package " + _packagePath + ".service.persistence;");

		// Imports

		sb.append("import com.liferay.util.StringPool;");
		sb.append("import java.io.Serializable;");
		sb.append("import java.util.Date;");

		// Class declaration

		sb.append("public class " + entity.getPKClassName() + " implements Comparable, Serializable {");

		// Fields

		for (int i = 0; i < pkList.size(); i++) {
			EntityColumn col = (EntityColumn)pkList.get(i);

			sb.append("public " + col.getType() + " " + col.getName() + ";");
		}

		// Default constructor

		sb.append("public " + entity.getPKClassName() + "() {}");

		// Primary key constructor

		sb.append("public " + entity.getPKClassName() + "(");

		for (int i = 0; i < pkList.size(); i++) {
			EntityColumn col = (EntityColumn)pkList.get(i);

			sb.append(col.getType() + " " + col.getName());

			if ((i + 1) != pkList.size()) {
				sb.append(", ");
			}
		}

		sb.append(") {");

		for (int i = 0; i < pkList.size(); i++) {
			EntityColumn col = (EntityColumn)pkList.get(i);

			sb.append("this." + col.getName() + " = " + col.getName() + ";");
		}

		sb.append("}");

		// Getter and setter methods

		for (int i = 0; i < pkList.size(); i++) {
			EntityColumn col = (EntityColumn)pkList.get(i);

			if (!col.isCollection()) {
				sb.append("public " + col.getType() + " get" + col.getMethodName() + "() {");
				sb.append("return " + col.getName() + ";");
				sb.append("}");

				sb.append("public void set" + col.getMethodName() + "(" + col.getType() + " " + col.getName() + ") {");
				sb.append("this." + col.getName() + " = " + col.getName() + ";");
				sb.append("}");
			}
		}

		// Compare to method

		sb.append("public int compareTo(Object obj) {");
		sb.append("if (obj == null) {");
		sb.append("return -1;");
		sb.append("}");
		sb.append(entity.getPKClassName() + " pk = (" + entity.getPKClassName() + ")obj;");
		sb.append("int value = 0;");

		for (int i = 0; i < pkList.size(); i++) {
			EntityColumn col = (EntityColumn)pkList.get(i);

			if (!col.isPrimitiveType()) {
				sb.append("value = " + col.getName() + ".compareTo(pk." + col.getName() + ");");
			}
			else {
				String colType = col.getType();

				if (colType.equals("boolean")) {
					sb.append("if (!" + col.getName() + " && pk." + col.getName() + ") {");
					sb.append("value = -1;");
					sb.append("}");
					sb.append("else if (" + col.getName() + " && !pk." + col.getName() + ") {");
					sb.append("value = 1;");
					sb.append("}");
					sb.append("else {");
					sb.append("value = 0;");
					sb.append("}");
				}
				else {
					sb.append("if (" + col.getName() + " < pk." + col.getName() + ") {");
					sb.append("value = -1;");
					sb.append("}");
					sb.append("else if (" + col.getName() + " > pk." + col.getName() + ") {");
					sb.append("value = 1;");
					sb.append("}");
					sb.append("else {");
					sb.append("value = 0;");
					sb.append("}");
				}
			}

			sb.append("if (value != 0) {");
			sb.append("return value;");
			sb.append("}");
		}

		sb.append("return 0;");
		sb.append("}");

		// Equals method

		sb.append("public boolean equals(Object obj) {");
		sb.append("if (obj == null) {");
		sb.append("return false;");
		sb.append("}");
		sb.append(entity.getPKClassName() + " pk = null;");
		sb.append("try {");
		sb.append("pk = (" + entity.getPKClassName() + ")obj;");
		sb.append("}");
		sb.append("catch (ClassCastException cce) {");
		sb.append("return false;");
		sb.append("}");
		sb.append("if (");

		for (int i = 0; i < pkList.size(); i++) {
			EntityColumn col = (EntityColumn)pkList.get(i);

			if (!col.isPrimitiveType()) {
				sb.append("(" + col.getName() + ".equals(pk." + col.getName() + "))");
			}
			else {
				sb.append("(" + col.getName() + " == pk." + col.getName() + ")");
			}

			if ((i + 1) != pkList.size()) {
				sb.append(" && ");
			}
		}

		sb.append(") {");
		sb.append("return true;");
		sb.append("} else {");
		sb.append("return false;");
		sb.append("}");
		sb.append("}");

		// Hash code method

		sb.append("public int hashCode() {");
		sb.append("return (");

		for (int i = 0; i < pkList.size(); i++) {
			EntityColumn col = (EntityColumn)pkList.get(i);

			if (i != 0) {
				sb.append(" + ");
			}

			if (!col.isPrimitiveType() && !col.getType().equals("String")) {
				sb.append(col.getName() + ".toString()");
			}
			else {
				sb.append(col.getName());				
			}
		}

		sb.append(").hashCode();");
		sb.append("}");

		// To string method

		sb.append("public String toString() {");
		sb.append("StringBuffer sb = new StringBuffer();");
		sb.append("sb.append(StringPool.OPEN_CURLY_BRACE);");

		for (int i = 0; i < pkList.size(); i++) {
			EntityColumn col = (EntityColumn)pkList.get(i);

			sb.append("sb.append(\"" + col.getName() + "\");");
			sb.append("sb.append(StringPool.EQUAL);");
			sb.append("sb.append(" + col.getName() + ");");

			if ((i + 1) != pkList.size()) {
				sb.append("sb.append(StringPool.COMMA);");
				sb.append("sb.append(StringPool.SPACE);");
			}
		}

		sb.append("sb.append(StringPool.CLOSE_CURLY_BRACE);");
		sb.append("return sb.toString();");
		sb.append("}");

		// Class close brace

		sb.append("}");

		// Write file

		File ejbFile = new File(_outputPath + "/service/persistence/" + entity.getPKClassName() + ".java");

		writeFile(ejbFile, sb.toString());
	}

	private void _createEJBXML() throws IOException {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < _ejbList.size(); i++) {
			Entity entity = (Entity)_ejbList.get(i);

			List referenceList = entity.getReferenceList();

			if (entity.hasLocalService()) {
				_createEJBXMLSession(entity, referenceList, sb, _LOCAL);
			}

			if (entity.hasRemoteService()) {
				_createEJBXMLSession(entity, referenceList, sb, _REMOTE);
			}
		}

		File xmlFile = new File("classes/META-INF/ejb-jar.xml");

		if (!xmlFile.exists()) {
			String content =
				"<?xml version=\"1.0\"?>\n" +
				"<!DOCTYPE ejb-jar PUBLIC \"-//Sun Microsystems, Inc.//DTD Enterprise JavaBeans 2.0//EN\" \"http://java.sun.com/dtd/ejb-jar_2_0.dtd\">\n" +
				"\n" +
				"<ejb-jar>\n" +
				"\t<enterprise-beans>\n" +
				"\t</enterprise-beans>\n" +
				"</ejb-jar>";

			FileUtil.write(xmlFile, content);
		}

		String oldContent = FileUtil.read(xmlFile);
		String newContent = new String(oldContent);

		int x = oldContent.indexOf("<session>");
		int y = oldContent.lastIndexOf("</session>");

		if (x == -1) {
			x = newContent.indexOf("<enterprise-beans/>");
			if (x != -1) {
				newContent = StringUtil.replace(
					newContent, "<enterprise-beans/>", "<enterprise-beans />");
			}

			x = newContent.indexOf("<enterprise-beans />");
			if (x != -1) {
				newContent = StringUtil.replace(
					newContent, "<enterprise-beans />",
					"<enterprise-beans>\n\t</enterprise-beans>");
			}

			x = newContent.indexOf("</enterprise-beans>") - 1;
			newContent =
				newContent.substring(0, x) + sb.toString() +
				newContent.substring(x, newContent.length());
		}
		else {
			int firstSession = newContent.indexOf(
				"<ejb-name>" + StringUtil.replace(
					_packagePath + ".service.ejb.", ".", "_"),
				x);
			int lastSession = newContent.lastIndexOf(
				"<ejb-name>" + StringUtil.replace(
					_packagePath + ".service.ejb.", ".", "_"),
				y);

			if (firstSession == -1 || firstSession > y) {
				x = newContent.indexOf("</enterprise-beans>") - 1;
				newContent =
					newContent.substring(0, x) + sb.toString() +
					newContent.substring(x, newContent.length());
			}
			else {
				firstSession = newContent.lastIndexOf(
					"<session>", firstSession) - 2;
				lastSession = newContent.indexOf(
					"</session>", lastSession) + 11;

				newContent =
					newContent.substring(0, firstSession) + sb.toString() +
					newContent.substring(lastSession, newContent.length());
			}
		}

		if (!oldContent.equals(newContent)) {
			FileUtil.write(xmlFile, newContent);
		}
	}

	private void _createEJBXMLSession(Entity entity, List referenceList, StringBuffer sb, int sessionType) {
		sb.append("\t\t<session>\n");
		sb.append("\t\t\t<display-name>" + entity.getName() + _getSessionTypeName(sessionType) + "ServiceEJB</display-name>\n");
		sb.append("\t\t\t<ejb-name>" + StringUtil.replace(_packagePath + ".service.ejb.", ".", "_") + entity.getName() + _getSessionTypeName(sessionType) + "ServiceEJB</ejb-name>\n");
		sb.append("\t\t\t<" + (sessionType == _LOCAL ? "local-" : "") + "home>" + _packagePath + ".service.ejb." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceHome</" + (sessionType == _LOCAL ? "local-" : "") + "home>\n");
		sb.append("\t\t\t<" + (sessionType == _LOCAL ? "local" : "remote") + ">" + _packagePath + ".service.ejb." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceEJB</" + (sessionType == _LOCAL ? "local" : "remote") + ">\n");
		sb.append("\t\t\t<ejb-class>" + _packagePath + ".service.ejb." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceEJBImpl</ejb-class>\n");
		sb.append("\t\t\t<session-type>Stateless</session-type>\n");
		sb.append("\t\t\t<transaction-type>Bean</transaction-type>\n");

		for (int j = 0; j < referenceList.size(); j++) {
			Object reference = referenceList.get(j);

			if (reference instanceof Entity) {
				Entity entityRef = (Entity)referenceList.get(j);

				if (entityRef.hasLocalService()) {
					sb.append("\t\t\t<ejb-local-ref>\n");
					sb.append("\t\t\t\t<ejb-ref-name>ejb/liferay/" + entityRef.getName() + "LocalServiceHome</ejb-ref-name>\n");
					sb.append("\t\t\t\t<ejb-ref-type>Session</ejb-ref-type>\n");
					sb.append("\t\t\t\t<local-home>" + entityRef.getPackagePath() + ".service.ejb." + entityRef.getName() + "LocalServiceHome</local-home>\n");
					sb.append("\t\t\t\t<local>" + entityRef.getPackagePath() + ".service.ejb." + entityRef.getName() + "LocalServiceEJB</local>\n");
					sb.append("\t\t\t\t<ejb-link>" + StringUtil.replace(entityRef.getPackagePath() + ".service.ejb.", ".", "_") + entityRef.getName() + "LocalServiceEJB</ejb-link>\n");
					sb.append("\t\t\t</ejb-local-ref>\n");
				}
			}
			else if (reference instanceof String) {
				sb.append("\t\t\t" + reference + "\n");
			}
		}

		for (int j = 0; j < _ejbList.size(); j++) {
			Entity entityRef = (Entity)_ejbList.get(j);

			if (!((sessionType == _LOCAL) && entity.equals(entityRef)) && entityRef.hasLocalService()) {
				sb.append("\t\t\t<ejb-local-ref>\n");
				sb.append("\t\t\t\t<ejb-ref-name>ejb/liferay/" + entityRef.getName() + "LocalServiceHome</ejb-ref-name>\n");
				sb.append("\t\t\t\t<ejb-ref-type>Session</ejb-ref-type>\n");
				sb.append("\t\t\t\t<local-home>" + _packagePath + ".service.ejb." + entityRef.getName() + "LocalServiceHome</local-home>\n");
				sb.append("\t\t\t\t<local>" + _packagePath + ".service.ejb." + entityRef.getName() + "LocalServiceEJB</local>\n");
				sb.append("\t\t\t\t<ejb-link>" + StringUtil.replace(_packagePath + ".service.ejb.", ".", "_") + entityRef.getName() + "LocalServiceEJB</ejb-link>\n");
				sb.append("\t\t\t</ejb-local-ref>\n");
			}
		}

		sb.append("\t\t</session>\n");
	}

	private void _createExceptions(List exceptions) throws IOException {
		String copyright = null;
		try {
			copyright = FileUtil.read("../copyright.txt");
		}
		catch (FileNotFoundException fnfe) {
		}

		for (int i = 0; i < _ejbList.size(); i++) {
			Entity entity = (Entity)_ejbList.get(i);

			if (entity.hasColumns()) {
				exceptions.add(_getNoSuchEntityException(entity));
			}
		}

		for (int i = 0; i < exceptions.size(); i++) {
			String exception = (String)exceptions.get(i);

			StringBuffer sb = new StringBuffer();

			if (Validator.isNotNull(copyright)) {
				sb.append(copyright + "\n");
				sb.append("\n");
			}

			sb.append("package " + _packagePath + ";\n");
			sb.append("\n");
			sb.append("import com.liferay.portal.PortalException;\n");
			sb.append("\n");

			if (Validator.isNotNull(copyright)) {
				sb.append("/**\n");
				sb.append(" * <a href=\"" + exception + "Exception.java.html\"><b><i>View Source</i></b></a>\n");
				sb.append(" *\n");
				sb.append(" * @author  Brian Wing Shun Chan\n");
				sb.append(" * @version $Revision: 1.183 $\n");
				sb.append(" *\n");
				sb.append(" */\n");
			}

			sb.append("public class " + exception + "Exception extends PortalException {\n");
			sb.append("\n");
			sb.append("\tpublic " + exception + "Exception() {\n");
			sb.append("\t\tsuper();\n");
			sb.append("\t}\n");
			sb.append("\n");
			sb.append("\tpublic " + exception + "Exception(String msg) {\n");
			sb.append("\t\tsuper(msg);\n");
			sb.append("\t}\n");
			sb.append("\n");
			sb.append("\tpublic " + exception + "Exception(String msg, Throwable cause) {\n");
			sb.append("\t\tsuper(msg, cause);\n");
			sb.append("\t}\n");
			sb.append("\n");
			sb.append("\tpublic " + exception + "Exception(Throwable cause) {\n");
			sb.append("\t\tsuper(cause);\n");
			sb.append("\t}\n");
			sb.append("\n");
			sb.append("}");

			File exceptionFile = new File(_outputPath + "/" + exception + "Exception.java");

			if (!exceptionFile.exists()) {
				FileUtil.write(exceptionFile, sb.toString());
			}
		}
	}

	private void _createExtendedModel(Entity entity) throws IOException {
		List pkList = entity.getPKList();
		List columnList = entity.getColumnList();

		StringBuffer sb = new StringBuffer();

		// Package

		sb.append("package " + _packagePath + ".model;");

		// Class declaration

		sb.append("public class " + entity.getName() + " extends " + entity.getName() + "Model {");

		// Empty constructor

		sb.append("public " + entity.getName() + "() {");
		sb.append("}");

		// Class close brace

		sb.append("}");

		// Write file

		File modelFile = new File(_outputPath + "/model/" + entity.getName() + ".java");

		if (!modelFile.exists()) {
			writeFile(modelFile, sb.toString());
		}
	}

	private void _createHBM(Entity entity) throws IOException {
		List pkList = entity.getPKList();
		List columnList = entity.getColumnList();

		StringBuffer sb = new StringBuffer();

		// Package

		sb.append("package " + _packagePath + ".service.persistence;");

		// Imports

		sb.append("import " + _packagePath + ".model." + entity.getName() + ";");
		sb.append("import java.util.Date;");
		sb.append("import java.util.HashSet;");
		sb.append("import java.util.Set;");

		// Class declaration

		sb.append("public class " + entity.getName() + "HBM extends " + entity.getName() + "{");

		// Empty constructor

		sb.append("protected " + entity.getName() + "HBM() {");
		sb.append("}");

		// Getter and setter methods

		for (int i = 0; i < columnList.size(); i++) {
			EntityColumn col = (EntityColumn)columnList.get(i);

			if (col.isCollection()) {
				sb.append("protected Set get" + col.getMethodName() + "() {");
				sb.append("if (_" + col.getName() + " == null) {");
				sb.append("_" + col.getName() + " = new HashSet();");
				sb.append("}");
				sb.append("return _" + col.getName() + ";");
				sb.append("}");

				sb.append("protected void set" + col.getMethodName() + "(Set " + col.getName() + ") {");
				sb.append("_" + col.getName() + " = " + col.getName() + ";");
				sb.append("}");
			}
		}

		// Fields

		for (int i = 0; i < columnList.size(); i++) {
			EntityColumn col = (EntityColumn)columnList.get(i);

			if (col.isCollection()) {
				sb.append("private Set _" + col.getName() + ";");
			}
		}

		// Class close brace

		sb.append("}");

		// Write file

		File modelFile = new File(_outputPath + "/service/persistence/" + entity.getName() + "HBM.java");

		writeFile(modelFile, sb.toString());
	}

	private void _createHBMUtil(Entity entity) throws IOException {
		List pkList = entity.getPKList();
		List columnList = entity.getColumnList();
		List finderList = entity.getFinderList();

		String pkClassName = entity.getPKClassName();
		String pkVarName = entity.getPKVarName();

		StringBuffer sb = new StringBuffer();

		// Package

		sb.append("package " + _packagePath + ".service.persistence;");

		// Imports

		sb.append("import com.liferay.util.dao.hibernate.Transformer;");

		// Class declaration

		sb.append("public class " + entity.getName() + "HBMUtil implements Transformer {");

		// Model methods

		sb.append("public static " + _packagePath + ".model." + entity.getName() + " model(" + entity.getName() + "HBM " + entity.getVarName() + "HBM) {");
		sb.append("return (" + _packagePath + ".model." + entity.getName() + ")" + entity.getVarName() + "HBM;");
		sb.append("}");

		sb.append("public static " + entity.getName() + "HBMUtil getInstance() {");
		sb.append("return _instance;");
		sb.append("}");

		sb.append("public Comparable transform(Object obj) {");
		sb.append("return model((" + entity.getName() + "HBM)obj);");
		sb.append("}");

		// Fields

		sb.append("private static " + entity.getName() + "HBMUtil _instance = new " + entity.getName() + "HBMUtil();");

		// Class close brace

		sb.append("}");

		// Write file

		File ejbFile = new File(_outputPath + "/service/persistence/" + entity.getName() + "HBMUtil.java");

		writeFile(ejbFile, sb.toString());
	}

	private void _createHBMXML() throws IOException {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < _ejbList.size(); i++) {
			Entity entity = (Entity)_ejbList.get(i);

			List pkList = entity.getPKList();
			List columnList = entity.getColumnList();
			List finderList = entity.getFinderList();

			if (entity.hasColumns()) {
				sb.append("\t<class name=\"" + _packagePath + ".service.persistence." + entity.getName() + "HBM\" table=\"" + entity.getTable() + "\">\n");
				sb.append("\t\t<cache usage=\"read-write\" />\n");

				if (entity.hasCompoundPK()) {
					sb.append("\t\t<composite-id name=\"primaryKey\" class=\"" + _packagePath + ".service.persistence." + entity.getName() + "PK\">\n");

					for (int j = 0; j < pkList.size(); j++) {
						EntityColumn col = (EntityColumn)pkList.get(j);

						sb.append("\t\t\t<key-property name=\"" + col.getName() + "\" ");

						if (!col.getName().equals(col.getDBName())) {
							sb.append("column=\"" + col.getDBName() + "\" />\n");
						}
						else {
							sb.append("/>\n");
						}
					}

					sb.append("\t\t</composite-id>\n");
				}
				else {
					EntityColumn col = (EntityColumn)pkList.get(0);

					sb.append("\t\t<id name=\"" + col.getName() + "\" ");

					if (!col.getName().equals(col.getDBName())) {
						sb.append("column=\"" + col.getDBName() + "\" ");
					}

					sb.append("type=\"java.lang." + col.getType() + "\">\n");

					String colIdType = col.getIdType();

					if (Validator.isNull(colIdType)) {
						sb.append("\t\t\t<generator class=\"assigned\" />\n");
					}
					else if (colIdType.equals("class")) {
						sb.append("\t\t\t<generator class=\"" + col.getIdParam() + "\" />\n");
					}
					else if (colIdType.equals("sequence")) {
						sb.append("\t\t\t<generator class=\"sequence\">\n");
						sb.append("\t\t\t\t<param name=\"sequence\">" + col.getIdParam() + "</param>\n");
						sb.append("\t\t\t</generator>\n");
					}
					else {
						sb.append("\t\t\t<generator class=\"" + colIdType + "\" />\n");
					}

					sb.append("\t\t</id>\n");
				}

				for (int j = 0; j < columnList.size(); j++) {
					EntityColumn col = (EntityColumn)columnList.get(j);

					if (!col.isPrimary() && !col.isCollection() && col.getEJBName() == null) {
						sb.append("\t\t<property name=\"" + col.getName() + "\" ");

						if (!col.getName().equals(col.getDBName())) {
							sb.append("column=\"" + col.getDBName() + "\" ");
						}

						if (col.isPrimitiveType()) {
							sb.append("type=\"com.liferay.util.dao.hibernate.");

							String colType = col.getType();

							if (colType.equals("boolean")) {
								sb.append("Boolean");
							}
							else if (colType.equals("double")) {
								sb.append("Double");
							}
							else if (colType.equals("float")) {
								sb.append("Float");
							}
							else if (colType.equals("int")) {
								sb.append("Integer");
							}
							else if (colType.equals("long")) {
								sb.append("Long");
							}
							else if (colType.equals("short")) {
								sb.append("Short");
							}

							sb.append("Type\" ");
						}

						sb.append("/>\n");
					}
					else if (!col.isPrimary() && col.isCollection() && col.getEJBName() != null) {
						Entity tempEntity = getEntity(col.getEJBName());

						EntityColumn pk1 = (EntityColumn)pkList.get(0);
						EntityColumn pk2 = (EntityColumn)tempEntity.getPKList().get(0);

						if (col.isMappingOneToMany()) {
							sb.append("\t\t<set name=\"" + col.getName() + "\" lazy=\"true\">\n");
							sb.append("\t\t\t<key column=\"" + col.getMappingKey() + "\" />\n");
							sb.append("\t\t\t<one-to-many class=\"" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM\" />\n");
							sb.append("\t\t</set>\n");
						}
						else if (col.isMappingManyToMany()) {
							sb.append("\t\t<set name=\"" + col.getName() + "\" table=\"" + col.getMappingTable() + "\" lazy=\"true\">\n");
							sb.append("\t\t\t<key column=\"" + pk1.getDBName() + "\" />\n");
							sb.append("\t\t\t<many-to-many class=\"" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM\" column=\"" + pk2.getDBName() + "\" />\n");
							sb.append("\t\t</set>\n");
						}
					}
				}

				sb.append("\t</class>\n");
			}
		}

		File xmlFile = new File(_hbmFileName);

		if (!xmlFile.exists()) {
			String content =
				"<?xml version=\"1.0\"?>\n" +
				"<!DOCTYPE hibernate-mapping PUBLIC \"-//Hibernate/Hibernate Mapping DTD 3.0//EN\" \"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd\">\n" +
				"\n" +
				"<hibernate-mapping default-lazy=\"false\">\n" +
				"</hibernate-mapping>";

			FileUtil.write(xmlFile, content);
		}

		String oldContent = FileUtil.read(xmlFile);
		String newContent = new String(oldContent);

		int firstClass = newContent.indexOf(
			"<class name=\"" + _packagePath + ".service.persistence.");
		int lastClass = newContent.lastIndexOf(
			"<class name=\"" + _packagePath + ".service.persistence.");

		if (firstClass == -1) {
			int x = newContent.indexOf("</hibernate-mapping>");
			newContent =
				newContent.substring(0, x) + sb.toString() +
				newContent.substring(x, newContent.length());
		}
		else {
			firstClass = newContent.lastIndexOf(
				"<class", firstClass) - 1;
			lastClass = newContent.indexOf(
				"</class>", lastClass) + 9;

			newContent =
				newContent.substring(0, firstClass) + sb.toString() +
				newContent.substring(lastClass, newContent.length());
		}

		if (!oldContent.equals(newContent)) {
			FileUtil.write(xmlFile, newContent);
		}
	}

	private void _createModel(Entity entity) throws IOException {
		List pkList = entity.getPKList();
		List columnList = entity.getColumnList();

		StringBuffer sb = new StringBuffer();

		// Package

		sb.append("package " + _packagePath + ".model;");

		// Imports

		if (entity.hasCompoundPK()) {
			sb.append("import " + _packagePath + ".service.persistence." + entity.getName() + "PK;");
		}

		sb.append("import com.liferay.portal.model.BaseModel;");
		sb.append("import com.liferay.portal.util.PropsUtil;");
		sb.append("import com.liferay.util.GetterUtil;");
		sb.append("import com.liferay.util.XSSUtil;");
		sb.append("import java.util.Date;");

		// Class declaration

		sb.append("public class " + entity.getName() + "Model extends BaseModel {");

		// Fields

		sb.append("public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.get(PropsUtil.get(\"xss.allow." + _packagePath + ".model." + entity.getName() + "\"), XSS_ALLOW);");

		for (int i = 0; i < columnList.size(); i++) {
			EntityColumn col = (EntityColumn)columnList.get(i);

			if (col.getType().equals("String")) {
				sb.append("public static boolean XSS_ALLOW_" + col.getName().toUpperCase() + " = GetterUtil.get(PropsUtil.get(\"xss.allow." + _packagePath + ".model." + entity.getName() + "." + col.getName() + "\"), XSS_ALLOW_BY_MODEL);");
			}
		}

		sb.append("public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(\"lock.expiration.time." + _packagePath + ".model." + entity.getName() + "Model\"));");

		// Empty constructor

		sb.append("public " + entity.getName() + "Model() {");
		sb.append("}");

		// Primary key accessor

		sb.append("public " + entity.getPKClassName() + " getPrimaryKey() {");

		if (entity.hasCompoundPK()) {
			sb.append("return new " + entity.getPKClassName() + "(");

			for (int i = 0; i < pkList.size(); i++) {
				EntityColumn col = (EntityColumn)pkList.get(i);

				sb.append("_" + col.getName());

				if ((i + 1) != (pkList.size())) {
					sb.append(", ");
				}
			}

			sb.append(");");
		}
		else {
			EntityColumn col = (EntityColumn)pkList.get(0);

			sb.append("return _" + col.getName() + ";");
		}

		sb.append("}");

		sb.append("public void setPrimaryKey(" + entity.getPKClassName() + " pk) {");

		if (entity.hasCompoundPK()) {
			for (int i = 0; i < pkList.size(); i++) {
				EntityColumn col = (EntityColumn)pkList.get(i);

				sb.append("set" + col.getMethodName() + "(pk." + col.getName() + ");");
			}
		}
		else {
			EntityColumn col = (EntityColumn)pkList.get(0);

			sb.append("set" + col.getMethodName() + "(pk);");
		}

		sb.append("}");

		// Getter and setter methods

		for (int i = 0; i < columnList.size(); i++) {
			EntityColumn col = (EntityColumn)columnList.get(i);

			String colType = col.getType();

			if (!col.isCollection()) {
				sb.append("public " + colType + " get" + col.getMethodName() + "() {");

				if (colType.equals("String")) {
					sb.append("return GetterUtil.getString(_" + col.getName() + ");");
				}
				else {
					sb.append("return _" + col.getName() + ";");
				}

				sb.append("}");

				if (colType.equals("boolean")) {
					sb.append("public " + colType + " is" + col.getMethodName() + "() {");
					sb.append("return _" + col.getName() + ";");
					sb.append("}");
				}

				sb.append("public void set" + col.getMethodName() + "(" + colType + " " + col.getName() + ") {");
				sb.append("if (");

				if (!col.isPrimitiveType()) {
					sb.append("(" + col.getName() + " == null && _" + col.getName() + " != null) ||");
					sb.append("(" + col.getName() + " != null && _" + col.getName() + " == null) ||");
					sb.append("(" + col.getName() + " != null && _" + col.getName() + " != null && !" + col.getName() + ".equals(_" + col.getName() + "))");
				}
				else {
					sb.append(col.getName() + " != _" + col.getName());
				}

				sb.append(") {");

				if (colType.equals("String")) {
					sb.append("if (!XSS_ALLOW_" + col.getName().toUpperCase() + ") {");
					sb.append(col.getName() + " = XSSUtil.strip(" + col.getName() + ");");
					sb.append("}");
				}

				sb.append("_" + col.getName() + " = " + col.getName() + ";");

				sb.append("setModified(true);");
				sb.append("}");
				sb.append("}");
			}
		}

		// Clone method

		sb.append("public Object clone() {");
		sb.append(entity.getName() + " clone = new " + entity.getName() + "();");

		for (int i = 0; i < columnList.size(); i++) {
			EntityColumn col = (EntityColumn)columnList.get(i);

			if (!col.isCollection()) {
				sb.append("clone.set" + col.getMethodName() + "(");

				if (col.getEJBName() == null) {
					sb.append("get" + col.getMethodName() + "()");
				}
				else {
					sb.append("(" + col.getEJBName() + ")get" + col.getMethodName() + "().clone()");
				}

				sb.append(");");
			}
		}

		sb.append("return clone;");
		sb.append("}");

		// Compare to method

		sb.append("public int compareTo(Object obj) {");
		sb.append("if (obj == null) {");
		sb.append("return -1;");
		sb.append("}");
		sb.append(entity.getName() + " " + entity.getVarName() + " = (" + entity.getName() + ")obj;");

		if (entity.isOrdered()) {
			EntityOrder order = entity.getOrder();

			List orderList = order.getColumns();

			sb.append("int value = 0;");

			for (int i = 0; i < orderList.size(); i++) {
				EntityColumn col = (EntityColumn)orderList.get(i);

				if (!col.isPrimitiveType()) {
					if (col.isCaseSensitive()) {
						sb.append("value = get" + col.getMethodName() + "().compareTo(" + entity.getVarName() + ".get" + col.getMethodName() + "());");
					}
					else {
						sb.append("value = get" + col.getMethodName() + "().toLowerCase().compareTo(" + entity.getVarName() + ".get" + col.getMethodName() + "().toLowerCase());");
					}
				}
				else {
					sb.append("if (get" + col.getMethodName() + "() < " + entity.getVarName() + ".get" + col.getMethodName() + "()) {");
					sb.append("value = -1;");
					sb.append("}");
					sb.append("else if (get" + col.getMethodName() + "() > " + entity.getVarName() + ".get" + col.getMethodName() + "()) {");
					sb.append("value = 1;");
					sb.append("}");
					sb.append("else {");
					sb.append("value = 0;");
					sb.append("}");
				}

				if (!col.isOrderByAscending()) {
					sb.append("value = value * -1;");
				}

				sb.append("if (value != 0) {");
				sb.append("return value;");
				sb.append("}");
			}

			sb.append("return 0;");
		}
		else {
			sb.append(entity.getPKClassName() + " pk = " + entity.getVarName() + ".getPrimaryKey();");
			sb.append("return getPrimaryKey().compareTo(pk);");
		}

		sb.append("}");

		// Equals method

		sb.append("public boolean equals(Object obj) {");
		sb.append("if (obj == null) {");
		sb.append("return false;");
		sb.append("}");
		sb.append(entity.getName() + " " + entity.getVarName() + " = null;");
		sb.append("try {");
		sb.append(entity.getVarName() + " = (" + entity.getName() + ")obj;");
		sb.append("}");
		sb.append("catch (ClassCastException cce) {");
		sb.append("return false;");
		sb.append("}");
		sb.append(entity.getPKClassName() + " pk = " + entity.getVarName() + ".getPrimaryKey();");
		sb.append("if (getPrimaryKey().equals(pk)) {");
		sb.append("return true;");
		sb.append("}");
		sb.append("else {");
		sb.append("return false;");
		sb.append("}");
		sb.append("}");

		// Hash code method

		sb.append("public int hashCode() {");
		sb.append("return getPrimaryKey().hashCode();");
		sb.append("}");

		// Fields

		for (int i = 0; i < columnList.size(); i++) {
			EntityColumn col = (EntityColumn)columnList.get(i);

			if (!col.isCollection()) {
				sb.append("private " + col.getType() + " _" + col.getName() + ";");
			}
		}

		// Class close brace

		sb.append("}");

		// Write file

		File modelFile = new File(_outputPath + "/model/" + entity.getName() + "Model.java");

		writeFile(modelFile, sb.toString());
	}

	private void _createModelHintsXML() throws IOException {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < _ejbList.size(); i++) {
			Entity entity = (Entity)_ejbList.get(i);

			List pkList = entity.getPKList();
			List columnList = entity.getColumnList();
			List finderList = entity.getFinderList();

			if (entity.hasColumns()) {
				sb.append("\t<model name=\"" + _packagePath + ".model." + entity.getName() + "\">\n");

				Map defaultHints = ModelHintsUtil.getDefaultHints(_packagePath + ".model." + entity.getName());

				if ((defaultHints != null) && (defaultHints.size() > 0)) {
					sb.append("\t\t<default-hints>\n");

					Iterator itr = defaultHints.entrySet().iterator();

					while (itr.hasNext()) {
						Map.Entry entry = (Map.Entry)itr.next();

						String key = (String)entry.getKey();
						String value = (String)entry.getValue();

						sb.append("\t\t\t<hint name=\"" + key + "\">" + value + "</hint>\n");
					}

					sb.append("\t\t</default-hints>\n");
				}

				for (int j = 0; j < columnList.size(); j++) {
					EntityColumn col = (EntityColumn)columnList.get(j);

					if (!col.isCollection()) {
						sb.append("\t\t<field name=\"" + col.getName() + "\" type=\"" + col.getType() + "\"");

						Element field = ModelHintsUtil.getFieldsEl(_packagePath + ".model." + entity.getName(), col.getName());

						List hints = null;
						
						if (field != null) {
							hints = field.elements();
						}

						if ((hints == null) || (hints.size() == 0)) {
							sb.append(" />\n");
						}
						else {
							sb.append(">\n");

							Iterator itr = hints.iterator();

							while (itr.hasNext()) {
								Element hint = (Element)itr.next();

								if (hint.getName().equals("hint")) {
									sb.append("\t\t\t<hint name=\"" + hint.attributeValue("name") + "\">" + hint.getText() + "</hint>\n");
								}
								else {
									sb.append("\t\t\t<hint-collection name=\"" + hint.attributeValue("name") + "\" />\n");
								}
							}

							sb.append("\t\t</field>\n");
						}
					}
				}

				sb.append("\t</model>\n");
			}
		}

		File xmlFile = new File(_modelHintsFileName);

		if (!xmlFile.exists()) {
			String content =
				"<?xml version=\"1.0\"?>\n" +
				"\n" +
				"<model-hints>\n" +
				"</model-hints>";

			FileUtil.write(xmlFile, content);
		}

		String oldContent = FileUtil.read(xmlFile);
		String newContent = new String(oldContent);

		int firstModel = newContent.indexOf(
			"<model name=\"" + _packagePath + ".model.");
		int lastModel = newContent.lastIndexOf(
			"<model name=\"" + _packagePath + ".model.");

		if (firstModel == -1) {
			int x = newContent.indexOf("</model-hints>");
			newContent =
				newContent.substring(0, x) + sb.toString() +
				newContent.substring(x, newContent.length());
		}
		else {
			firstModel = newContent.lastIndexOf(
				"<model", firstModel) - 1;
			lastModel = newContent.indexOf(
				"</model>", lastModel) + 9;

			newContent =
				newContent.substring(0, firstModel) + sb.toString() +
				newContent.substring(lastModel, newContent.length());
		}

		if (!oldContent.equals(newContent)) {
			FileUtil.write(xmlFile, newContent);
		}
	}

	private void _createPersistence(Entity entity) throws IOException {
		List pkList = entity.getPKList();
		List columnList = entity.getColumnList();
		List finderList = entity.getFinderList();

		String pkClassName = entity.getPKClassName();
		String pkVarName = entity.getPKVarName();

		StringBuffer sb = new StringBuffer();

		// Package

		sb.append("package " + _packagePath + ".service.persistence;");

		// Imports

		sb.append("import com.liferay.portal.PortalException;");
		sb.append("import com.liferay.portal.SystemException;");
		sb.append("import com.liferay.portal.service.persistence.BasePersistence;");
		sb.append("import com.liferay.portal.spring.hibernate.HibernateUtil;");
		sb.append("import com.liferay.util.StringPool;");
		sb.append("import com.liferay.util.dao.hibernate.OrderByComparator;");
		sb.append("import com.liferay.util.dao.hibernate.QueryPos;");
		sb.append("import com.liferay.util.dao.hibernate.QueryUtil;");
		sb.append("import java.util.ArrayList;");
		sb.append("import java.util.Collection;");
		sb.append("import java.util.Collections;");
		sb.append("import java.util.Date;");
		sb.append("import java.util.HashSet;");
		sb.append("import java.util.Iterator;");
		sb.append("import java.util.List;");
		sb.append("import java.util.Set;");
		sb.append("import org.apache.commons.logging.Log;");
		sb.append("import org.apache.commons.logging.LogFactory;");
		sb.append("import org.hibernate.Hibernate;");
		sb.append("import org.hibernate.HibernateException;");
		sb.append("import org.hibernate.ObjectNotFoundException;");
		sb.append("import org.hibernate.Query;");
		sb.append("import org.hibernate.Session;");
		sb.append("import org.hibernate.SQLQuery;");
		sb.append("import " + _packagePath + "." + _getNoSuchEntityException(entity) + "Exception;");

		// Class declaration

		sb.append("public class " + entity.getName() + "Persistence extends BasePersistence {");

		// Create method

		sb.append("public " + _packagePath + ".model." + entity.getName() + " create(" + entity.getPKClassName() + " " + pkVarName + ") {");
		sb.append(entity.getName() + "HBM " + entity.getVarName() + "HBM = new " + entity.getName() + "HBM();");
		sb.append(entity.getVarName() + "HBM.setNew(true);");
		sb.append(entity.getVarName() + "HBM.setPrimaryKey(" + pkVarName + ");");
		sb.append("return " + entity.getName() + "HBMUtil.model(" + entity.getVarName() + "HBM);");
		sb.append("}");

		// Remove method

		sb.append("public " + _packagePath + ".model." + entity.getName() + " remove(" + pkClassName + " " + pkVarName + ") throws " + _getNoSuchEntityException(entity) + "Exception, SystemException {");
		sb.append("Session session = null;");
		sb.append("try {");
		sb.append("session = openSession();");
		sb.append(entity.getName() + "HBM " + entity.getVarName() + "HBM = (" + entity.getName() + "HBM)session.get(" + entity.getName() + "HBM.class, " + pkVarName + ");");
		sb.append("if (" + entity.getVarName() + "HBM == null) {");
		sb.append("_log.warn(\"No " + entity.getName() + " exists with the primary key \" + " + pkVarName + ".toString());");
		sb.append("throw new " + _getNoSuchEntityException(entity) + "Exception(\"No " + entity.getName() + " exists with the primary key \" + " + pkVarName + ".toString());");
		sb.append("}");
		sb.append("session.delete(" + entity.getVarName() + "HBM);");
		sb.append("session.flush();");
		sb.append("return " + entity.getName() + "HBMUtil.model(" + entity.getVarName() + "HBM);");
		sb.append("}");
		sb.append("catch (HibernateException he) {");
		sb.append("throw new SystemException(he);");
		sb.append("}");
		sb.append("finally {");
		sb.append("closeSession(session);");
		sb.append("}");
		sb.append("}");

		// Update method

		sb.append("public " + _packagePath + ".model." + entity.getName() + " update(" + _packagePath + ".model." + entity.getName() + " " + entity.getVarName() + ") throws SystemException {");
		sb.append("Session session = null;");
		sb.append("try {");
		sb.append("if (" + entity.getVarName() + ".isNew() || " + entity.getVarName() + ".isModified()) {");
		sb.append("session = openSession();");
		sb.append("if (" + entity.getVarName() + ".isNew()) {");
		sb.append(entity.getName() + "HBM " + entity.getVarName() + "HBM = new " + entity.getName() + "HBM();");

		for (int i = 0; i < columnList.size(); i++) {
			EntityColumn col = (EntityColumn)columnList.get(i);

			if (!col.isCollection() && col.getEJBName() == null) {
				sb.append(entity.getVarName() + "HBM.set" + col.getMethodName() + "(" + entity.getVarName() + ".get" + col.getMethodName() + "());");
			}
		}

		sb.append("session.save(" + entity.getVarName() + "HBM);");
		sb.append("session.flush();");
		sb.append("}");
		sb.append("else {");
		sb.append(entity.getName() + "HBM " + entity.getVarName() + "HBM = (" + entity.getName() + "HBM)session.get(" + entity.getName() + "HBM.class, " + entity.getVarName() + ".getPrimaryKey());");
		sb.append("if (" + entity.getVarName() + "HBM != null) {");

		for (int i = 0; i < columnList.size(); i++) {
			EntityColumn col = (EntityColumn)columnList.get(i);

			if (!col.isPrimary() && !col.isCollection() && col.getEJBName() == null) {
				sb.append(entity.getVarName() + "HBM.set" + col.getMethodName() + "(" + entity.getVarName() + ".get" + col.getMethodName() + "());");
			}
		}

		sb.append("session.flush();");
		sb.append("}");
		sb.append("else {");
		sb.append(entity.getVarName() + "HBM = new " + entity.getName() + "HBM();");

		for (int i = 0; i < columnList.size(); i++) {
			EntityColumn col = (EntityColumn)columnList.get(i);

			if (!col.isCollection() && col.getEJBName() == null) {
				sb.append(entity.getVarName() + "HBM.set" + col.getMethodName() + "(" + entity.getVarName() + ".get" + col.getMethodName() + "());");
			}
		}

		sb.append("session.save(" + entity.getVarName() + "HBM);");
		sb.append("session.flush();");
		sb.append("}");
		sb.append("}");

		/*
		for (int i = 0; i < columnList.size(); i++) {
			EntityColumn col = (EntityColumn)columnList.get(i);

			if (!col.isCollection() && col.getEJBName() != null) {
				sb.append("try {");
				sb.append(entity.getVarName() + "EJB.set" + col.getMethodName() + "(" + col.getEJBName() + "HomeUtil.get" + col.getEJBName() + "Home().findByPrimaryKey(" + entity.getVarName() + ".get" + col.getMethodName() + "().getPrimaryKey()));");
				sb.append("}");
				sb.append("catch (FinderException fe) {");
				sb.append("fe.printStackTrace();");
				sb.append("}");
				sb.append("catch (NamingException ne) {");
				sb.append("ne.printStackTrace();");
				sb.append("}");
			}
		}
		*/

		sb.append(entity.getVarName() + ".setNew(false);");
		sb.append(entity.getVarName() + ".setModified(false);");
		sb.append("}");
		sb.append("return " + entity.getVarName() + ";");
		sb.append("}");
		sb.append("catch (HibernateException he) {");
		sb.append("throw new SystemException(he);");
		sb.append("}");
		sb.append("finally {");
		sb.append("closeSession(session);");
		sb.append("}");
		sb.append("}");

		// Relationship methods
		
		for (int i = 0; i < columnList.size(); i++) {
			EntityColumn col = (EntityColumn)columnList.get(i);

			if ((col.isCollection()) &&
				(col.isMappingManyToMany() || col.isMappingOneToMany())) {

				Entity tempEntity = getEntity(col.getEJBName());
				EntityOrder tempOrder = tempEntity.getOrder();
				EntityColumn tempCol = tempEntity.getColumnByMappingTable(col.getMappingTable());

				// getUsers(String pk)

				sb.append("public List get" + tempEntity.getNames() + "(" + entity.getPKClassName() + " pk) throws " + _getNoSuchEntityException(entity) + "Exception, SystemException {");
				sb.append("Session session = null;");
				sb.append("try {");
				sb.append("session = openSession();");
				sb.append(entity.getName() + "HBM " + entity.getVarName() + "HBM = (" + entity.getName() + "HBM)session.get(" + entity.getName() + "HBM.class, pk);");
				sb.append("if (" + entity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("throw new " + _getNoSuchEntityException(entity) + "Exception(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("}");
				sb.append("StringBuffer query = new StringBuffer();");
				sb.append("query.append(\"SELECT " + tempEntity.getVarName() + "HBM FROM \");");
				sb.append("query.append(" + entity.getPackagePath() + ".service.persistence." + entity.getName() + "HBM.class.getName());");
				sb.append("query.append(\" " + entity.getVarName() + "HBM \");");
				sb.append("query.append(\"JOIN " + entity.getVarName() + "HBM." + col.getName() + " AS " + tempEntity.getVarName() + "HBM \");");
				sb.append("query.append(\"WHERE " + entity.getVarName() + "HBM." + pkVarName + " = ? \");");

				if (tempOrder != null) {
					List tempOrderList = tempOrder.getColumns();

					sb.append("query.append(\"ORDER BY \");");

					for (int j = 0; j < tempOrderList.size(); j++) {
						EntityColumn tempOrderCol = (EntityColumn)tempOrderList.get(j);

						sb.append("query.append(\"" + tempEntity.getVarName() + "HBM." + tempOrderCol.getDBName() + " " + (tempOrderCol.isOrderByAscending() ? "ASC" : "DESC") + "\")");

						if ((j + 1) != tempOrderList.size()) {
							sb.append(".append(\", \");");
						}
						else {
							sb.append(";");
						}
					}
				}

				sb.append("Query q = session.createQuery(query.toString());");
				sb.append("q.set" + pkClassName + "(0, pk);");

				sb.append("Iterator itr = q.list().iterator();");
				sb.append("List list = new ArrayList();");

				sb.append("while (itr.hasNext()) {");
				sb.append(tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM " + tempEntity.getVarName() + "HBM = (" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM)itr.next();");
				sb.append("list.add(" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBMUtil.model(" + tempEntity.getVarName() + "HBM));");
				sb.append("}");

				sb.append("return list;");
				sb.append("}");
				sb.append("catch (HibernateException he) {");
				sb.append("throw new SystemException(he);");
				sb.append("}");
				sb.append("finally {");
				sb.append("closeSession(session);");
				sb.append("}");
				sb.append("}");

				// getUsers(String pk, int begin, int end)

				sb.append("public List get" + tempEntity.getNames() + "(" + entity.getPKClassName() + " pk, int begin, int end) throws " + _getNoSuchEntityException(entity) + "Exception, SystemException {");
				sb.append("return get" + tempEntity.getNames() + "(pk, begin, end, null);");
				sb.append("}");

				// getUsers(String pk, int begin, int end, OrderByComparator obc)

				sb.append("public List get" + tempEntity.getNames() + "(" + entity.getPKClassName() + " pk, int begin, int end, OrderByComparator obc) throws " + _getNoSuchEntityException(entity) + "Exception, SystemException {");
				sb.append("Session session = null;");
				sb.append("try {");
				sb.append("session = openSession();");
				sb.append(entity.getName() + "HBM " + entity.getVarName() + "HBM = (" + entity.getName() + "HBM)session.get(" + entity.getName() + "HBM.class, pk);");
				sb.append("if (" + entity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("throw new " + _getNoSuchEntityException(entity) + "Exception(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("}");
				sb.append("StringBuffer query = new StringBuffer();");
				sb.append("query.append(\"SELECT " + tempEntity.getVarName() + "HBM FROM \");");
				sb.append("query.append(" + entity.getPackagePath() + ".service.persistence." + entity.getName() + "HBM.class.getName());");
				sb.append("query.append(\" " + entity.getVarName() + "HBM \");");
				sb.append("query.append(\"JOIN " + entity.getVarName() + "HBM." + col.getName() + " AS " + tempEntity.getVarName() + "HBM \");");
				sb.append("query.append(\"WHERE " + entity.getVarName() + "HBM." + pkVarName + " = ? \");");

				sb.append("if (obc != null) {");
				sb.append("query.append(\"ORDER BY \" + obc.getOrderBy());");
				sb.append("}");

				if (tempOrder != null) {
					List tempOrderList = tempOrder.getColumns();

					sb.append("else {");
					sb.append("query.append(\"ORDER BY \");");

					for (int j = 0; j < tempOrderList.size(); j++) {
						EntityColumn tempOrderCol = (EntityColumn)tempOrderList.get(j);

						sb.append("query.append(\"" + tempEntity.getVarName() + "HBM." + tempOrderCol.getDBName() + " " + (tempOrderCol.isOrderByAscending() ? "ASC" : "DESC") + "\")");

						if ((j + 1) != tempOrderList.size()) {
							sb.append(".append(\", \");");
						}
						else {
							sb.append(";");
						}
					}

					sb.append("}");
				}

				sb.append("Query q = session.createQuery(query.toString());");
				sb.append("q.set" + pkClassName + "(0, pk);");

				sb.append("List list = new ArrayList();");

				sb.append("Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);");
				sb.append("while (itr.hasNext()) {");
				sb.append(tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM " + tempEntity.getVarName() + "HBM = (" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM)itr.next();");
				sb.append("list.add(" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBMUtil.model(" + tempEntity.getVarName() + "HBM));");
				sb.append("}");

				sb.append("return list;");
				sb.append("}");
				sb.append("catch (HibernateException he) {");
				sb.append("throw new SystemException(he);");
				sb.append("}");
				sb.append("finally {");
				sb.append("closeSession(session);");
				sb.append("}");
				sb.append("}");

				// getUsersSize(String pk)

				sb.append("public int get" + tempEntity.getNames() + "Size(" + entity.getPKClassName() + " pk) throws SystemException {");
				sb.append("Session session = null;");
				sb.append("try {");
				sb.append("session = openSession();");
				sb.append("StringBuffer query = new StringBuffer();");
				sb.append("query.append(\"SELECT COUNT(*) FROM \");");
				sb.append("query.append(" + entity.getPackagePath() + ".service.persistence." + entity.getName() + "HBM.class.getName());");
				sb.append("query.append(\" " + entity.getVarName() + "HBM \");");
				sb.append("query.append(\"JOIN " + entity.getVarName() + "HBM." + col.getName() + " AS " + tempEntity.getVarName() + "HBM \");");
				sb.append("query.append(\"WHERE " + entity.getVarName() + "HBM." + pkVarName + " = ? \");");

				sb.append("Query q = session.createQuery(query.toString());");
				sb.append("q.set" + pkClassName + "(0, pk);");

				sb.append("Iterator itr = q.iterate();");
				sb.append("if (itr.hasNext()) {");
				sb.append("Integer count = (Integer)itr.next();");
				sb.append("if (count != null) {");
				sb.append("return count.intValue();");
				sb.append("}");
				sb.append("}");
				sb.append("return 0;");
				sb.append("}");
				sb.append("catch (HibernateException he) {");
				sb.append("throw new SystemException(he);");
				sb.append("}");
				sb.append("finally {");
				sb.append("closeSession(session);");
				sb.append("}");
				sb.append("}");

				// setUsers(String pk, String[] pks)

				sb.append("public void set" + tempEntity.getNames() + "(" + entity.getPKClassName() + " pk, " + tempEntity.getPKClassName() + "[] pks) throws " + _getNoSuchEntityException(entity) + "Exception, " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception, SystemException {");
				sb.append("Session session = null;");
				sb.append("try {");
				sb.append("session = openSession();");
				sb.append(entity.getName() + "HBM " + entity.getVarName() + "HBM = (" + entity.getName() + "HBM)session.get(" + entity.getName() + "HBM.class, pk);");
				sb.append("if (" + entity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("throw new " + _getNoSuchEntityException(entity) + "Exception(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("}");
				sb.append("Set " + col.getName() + "Set = new HashSet();");
				sb.append("for (int i = 0; pks != null && i < pks.length; i++) {");
				sb.append(tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM " + tempEntity.getVarName() + "HBM = (" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM)session.get(" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM.class, pks[i]);");
				sb.append("if (" + tempEntity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + tempEntity.getName() + " exists with the primary key \" + pks[i].toString());");
				sb.append("throw new " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception(\"No " + tempEntity.getName() + " exists with the primary key \" + pks[i].toString());");
				sb.append("}");
				sb.append(col.getName() + "Set.add(" + tempEntity.getVarName() + "HBM);");
				sb.append("}");
				sb.append(entity.getVarName() + "HBM.set" + col.getMethodName() + "(" + col.getName() + "Set);");
				sb.append("session.flush();");
				sb.append("}");
				sb.append("catch (HibernateException he) {");
				sb.append("throw new SystemException(he);");
				sb.append("}");
				sb.append("finally {");
				sb.append("closeSession(session);");
				sb.append("}");
				sb.append("}");

				// setUsers(String pk, List pks)

				sb.append("public void set" + tempEntity.getNames() + "(" + entity.getPKClassName() + " pk, List " + col.getName() + ") throws " + _getNoSuchEntityException(entity) + "Exception, " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception, SystemException {");
				sb.append("Session session = null;");
				sb.append("try {");
				sb.append("session = openSession();");
				sb.append(entity.getName() + "HBM " + entity.getVarName() + "HBM = (" + entity.getName() + "HBM)session.get(" + entity.getName() + "HBM.class, pk);");
				sb.append("if (" + entity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("throw new " + _getNoSuchEntityException(entity) + "Exception(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("}");
				sb.append("Set " + col.getName() + "Set = new HashSet();");
				sb.append("Iterator itr = " + col.getName() + ".iterator();");
				sb.append("while (itr.hasNext()) {");
				sb.append(tempEntity.getPackagePath() + ".model." + tempEntity.getName() + " " + tempEntity.getVarName() + " = (" + tempEntity.getPackagePath() + ".model." + tempEntity.getName() + ")itr.next();");
				sb.append(tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM " + tempEntity.getVarName() + "HBM = (" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM)session.get(" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM.class, " + tempEntity.getVarName() + ".getPrimaryKey());");
				sb.append("if (" + tempEntity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + tempEntity.getName() + " exists with the primary key \" + " + tempEntity.getVarName() + ".getPrimaryKey().toString());");
				sb.append("throw new " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception(\"No " + tempEntity.getName() + " exists with the primary key \" + " + tempEntity.getVarName() + ".getPrimaryKey().toString());");
				sb.append("}");
				sb.append(col.getName() + "Set.add(" + tempEntity.getVarName() + "HBM);");
				sb.append("}");
				sb.append(entity.getVarName() + "HBM.set" + col.getMethodName() + "(" + col.getName() + "Set);");
				sb.append("session.flush();");
				sb.append("}");
				sb.append("catch (HibernateException he) {");
				sb.append("throw new SystemException(he);");
				sb.append("}");
				sb.append("finally {");
				sb.append("closeSession(session);");
				sb.append("}");
				sb.append("}");

				// addUser(String pk, String userPK)

				sb.append("public boolean add" + tempEntity.getName() + "(" + entity.getPKClassName() + " pk, " + tempEntity.getPKClassName() + " " + tempEntity.getVarName() + "PK) throws " + _getNoSuchEntityException(entity) + "Exception, " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception, SystemException {");
				sb.append("Session session = null;");
				sb.append("try {");
				sb.append("session = openSession();");
				sb.append(entity.getName() + "HBM " + entity.getVarName() + "HBM = (" + entity.getName() + "HBM)session.get(" + entity.getName() + "HBM.class, pk);");
				sb.append("if (" + entity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("throw new " + _getNoSuchEntityException(entity) + "Exception(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("}");
				sb.append(tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM " + tempEntity.getVarName() + "HBM = null;");
				sb.append(tempEntity.getVarName() + "HBM = (" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM)session.get(" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM.class, " + tempEntity.getVarName() + "PK);");
				sb.append("if (" + tempEntity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + tempEntity.getName() + " exists with the primary key \" + " + tempEntity.getVarName() + "PK.toString());");
				sb.append("throw new " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception(\"No " + tempEntity.getName() + " exists with the primary key \" + " + tempEntity.getVarName() + "PK.toString());");
				sb.append("}");
				sb.append("boolean value = " + entity.getVarName() + "HBM.get" + col.getMethodName() + "().add(" + tempEntity.getVarName() + "HBM);");
				sb.append("session.flush();");
				sb.append("return value;");
				sb.append("}");
				sb.append("catch (HibernateException he) {");
				sb.append("throw new SystemException(he);");
				sb.append("}");
				sb.append("finally {");
				sb.append("closeSession(session);");
				sb.append("}");
				sb.append("}");

				// addUser(String pk, User user)

				sb.append("public boolean add" + tempEntity.getName() + "(" + entity.getPKClassName() + " pk, " + tempEntity.getPackagePath() + ".model." + tempEntity.getName() + " " + tempEntity.getVarName() + ") throws " + _getNoSuchEntityException(entity) + "Exception, " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception, SystemException {");
				sb.append("Session session = null;");
				sb.append("try {");
				sb.append("session = openSession();");
				sb.append(entity.getName() + "HBM " + entity.getVarName() + "HBM = (" + entity.getName() + "HBM)session.get(" + entity.getName() + "HBM.class, pk);");
				sb.append("if (" + entity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("throw new " + _getNoSuchEntityException(entity) + "Exception(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("}");
				sb.append(tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM " + tempEntity.getVarName() + "HBM = null;");
				sb.append(tempEntity.getVarName() + "HBM = (" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM)session.get(" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM.class, " + tempEntity.getVarName() + ".getPrimaryKey());");
				sb.append("if (" + tempEntity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + tempEntity.getName() + " exists with the primary key \" + " + tempEntity.getVarName() + ".getPrimaryKey().toString());");
				sb.append("throw new " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception(\"No " + tempEntity.getName() + " exists with the primary key \" + " + tempEntity.getVarName() + ".getPrimaryKey().toString());");
				sb.append("}");
				sb.append("boolean value = " + entity.getVarName() + "HBM.get" + col.getMethodName() + "().add(" + tempEntity.getVarName() + "HBM);");
				sb.append("session.flush();");
				sb.append("return value;");
				sb.append("}");
				sb.append("catch (HibernateException he) {");
				sb.append("throw new SystemException(he);");
				sb.append("}");
				sb.append("finally {");
				sb.append("closeSession(session);");
				sb.append("}");
				sb.append("}");

				// addUsers(String pk, String[] userPKs)

				sb.append("public boolean add" + tempEntity.getNames() + "(" + entity.getPKClassName() + " pk, " + tempEntity.getPKClassName() + "[] " + tempEntity.getVarName() + "PKs) throws " + _getNoSuchEntityException(entity) + "Exception, " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception, SystemException {");
				sb.append("Session session = null;");
				sb.append("try {");
				sb.append("session = openSession();");
				sb.append(entity.getName() + "HBM " + entity.getVarName() + "HBM = (" + entity.getName() + "HBM)session.get(" + entity.getName() + "HBM.class, pk);");
				sb.append("if (" + entity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("throw new " + _getNoSuchEntityException(entity) + "Exception(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("}");
				sb.append("boolean value = false;");
				sb.append("for (int i = 0; i < " + tempEntity.getVarName() + "PKs.length; i++) {");
				sb.append(tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM " + tempEntity.getVarName() + "HBM = null;");
				sb.append(tempEntity.getVarName() + "HBM = (" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM)session.get(" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM.class, " + tempEntity.getVarName() + "PKs[i]);");
				sb.append("if (" + tempEntity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + tempEntity.getName() + " exists with the primary key \" + " + tempEntity.getVarName() + "PKs[i].toString());");
				sb.append("throw new " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception(\"No " + tempEntity.getName() + " exists with the primary key \" + " + tempEntity.getVarName() + "PKs[i].toString());");
				sb.append("}");
				sb.append("if (" + entity.getVarName() + "HBM.get" + col.getMethodName() + "().add(" + tempEntity.getVarName() + "HBM)) {");
				sb.append("value = true;");
				sb.append("}");
				sb.append("}");
				sb.append("session.flush();");
				sb.append("return value;");
				sb.append("}");
				sb.append("catch (HibernateException he) {");
				sb.append("throw new SystemException(he);");
				sb.append("}");
				sb.append("finally {");
				sb.append("closeSession(session);");
				sb.append("}");
				sb.append("}");

				// addUsers(String pk, List userPKs)

				sb.append("public boolean add" + tempEntity.getNames() + "(" + entity.getPKClassName() + " pk, List " + tempEntity.getVarNames() + ") throws " + _getNoSuchEntityException(entity) + "Exception, " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception, SystemException {");
				sb.append("Session session = null;");
				sb.append("try {");
				sb.append("session = openSession();");
				sb.append(entity.getName() + "HBM " + entity.getVarName() + "HBM = (" + entity.getName() + "HBM)session.get(" + entity.getName() + "HBM.class, pk);");
				sb.append("if (" + entity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("throw new " + _getNoSuchEntityException(entity) + "Exception(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("}");
				sb.append("boolean value = false;");
				sb.append("for (int i = 0; i < " + tempEntity.getVarNames() + ".size(); i++) {");
				sb.append(tempEntity.getPackagePath() + ".model." + tempEntity.getName() + " " + tempEntity.getVarName() + " = (" + tempEntity.getPackagePath() + ".model." + tempEntity.getName() + ")" + tempEntity.getVarNames() + ".get(i);");
				sb.append(tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM " + tempEntity.getVarName() + "HBM = (" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM)session.get(" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM.class, " + tempEntity.getVarName() + ".getPrimaryKey());");
				sb.append("if (" + tempEntity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + tempEntity.getName() + " exists with the primary key \" + " + tempEntity.getVarName() + ".getPrimaryKey().toString());");
				sb.append("throw new " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception(\"No " + tempEntity.getName() + " exists with the primary key \" + " + tempEntity.getVarName() + ".getPrimaryKey().toString());");
				sb.append("}");
				sb.append("if (" + entity.getVarName() + "HBM.get" + col.getMethodName() + "().add(" + tempEntity.getVarName() + "HBM)) {");
				sb.append("value = true;");
				sb.append("}");
				sb.append("}");
				sb.append("session.flush();");
				sb.append("return value;");
				sb.append("}");
				sb.append("catch (HibernateException he) {");
				sb.append("throw new SystemException(he);");
				sb.append("}");
				sb.append("finally {");
				sb.append("closeSession(session);");
				sb.append("}");
				sb.append("}");

				// clearUsers(String pk)

				sb.append("public void clear" + tempEntity.getNames() + "(" + entity.getPKClassName() + " pk) throws " + _getNoSuchEntityException(entity) + "Exception, SystemException {");
				sb.append("Session session = null;");
				sb.append("try {");
				sb.append("session = openSession();");
				sb.append(entity.getName() + "HBM " + entity.getVarName() + "HBM = (" + entity.getName() + "HBM)session.get(" + entity.getName() + "HBM.class, pk);");
				sb.append("if (" + entity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("throw new " + _getNoSuchEntityException(entity) + "Exception(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("}");
				sb.append(entity.getVarName() + "HBM.get" + col.getMethodName() + "().clear();");
				sb.append("session.flush();");
				sb.append("}");
				sb.append("catch (HibernateException he) {");
				sb.append("throw new SystemException(he);");
				sb.append("}");
				sb.append("finally {");
				sb.append("closeSession(session);");
				sb.append("}");
				sb.append("}");

				// containsUser(String pk, String userPK)

				if (col.isMappingManyToMany()) {
					sb.append("public static final String SQL_CONTAINS" + tempEntity.getName().toUpperCase() + " = \"SELECT COUNT(*) AS COUNT_VALUE FROM " + col.getMappingTable() + " WHERE " + entity.getPKVarName() + " = ? AND " + tempEntity.getPKVarName() + " = ?\";");

					sb.append("public boolean contains" + tempEntity.getName() + "(" + entity.getPKClassName() + " pk, " + tempEntity.getPKClassName() + " " + tempEntity.getVarName() + "PK) throws SystemException {");
					sb.append("Session session = null;");
					sb.append("try {");
					sb.append("session = openSession();");
					sb.append("SQLQuery q = session.createSQLQuery(SQL_CONTAINS" + tempEntity.getName().toUpperCase() + ");");
					sb.append("q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.INTEGER);");
					sb.append("QueryPos qPos = QueryPos.getInstance(q);");
					sb.append("qPos.add(pk);");
					sb.append("qPos.add(" + tempEntity.getVarName() + "PK);");
					sb.append("Iterator itr = q.list().iterator();");
					sb.append("if (itr.hasNext()) {");
					sb.append("Integer count = (Integer)itr.next();");
					sb.append("if ((count != null) && (count.intValue() > 0)) {");
					sb.append("return true;");
					sb.append("}");
					sb.append("}");
					sb.append("return false;");
					sb.append("}");
					sb.append("catch (HibernateException he) {");
					sb.append("throw new SystemException(he);");
					sb.append("}");
					sb.append("finally {");
					sb.append("closeSession(session);");
					sb.append("}");
					sb.append("}");
				}

				/*sb.append("public boolean contains" + tempEntity.getName() + "(" + entity.getPKClassName() + " pk, " + tempEntity.getPKClassName() + " " + tempEntity.getVarName() + "PK) throws " + _getNoSuchEntityException(entity) + "Exception, " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception, SystemException {");
				sb.append("Session session = null;");
				sb.append("try {");
				sb.append("session = openSession();");
				sb.append(entity.getName() + "HBM " + entity.getVarName() + "HBM = (" + entity.getName() + "HBM)session.get(" + entity.getName() + "HBM.class, pk);");
				sb.append("if (" + entity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("throw new " + _getNoSuchEntityException(entity) + "Exception(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("}");
				sb.append(tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM " + tempEntity.getVarName() + "HBM = null;");
				sb.append(tempEntity.getVarName() + "HBM = (" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM)session.get(" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM.class, " + tempEntity.getVarName() + "PK);");
				sb.append("if (" + tempEntity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + tempEntity.getName() + " exists with the primary key \" + " + tempEntity.getVarName() + "PK.toString());");
				sb.append("throw new " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception(\"No " + tempEntity.getName() + " exists with the primary key \" + " + tempEntity.getVarName() + "PK.toString());");
				sb.append("}");
				sb.append("Collection c = " + entity.getVarName() + "HBM.get" + col.getMethodName() + "();");
				sb.append("return c.contains(" + tempEntity.getVarName() + "HBM);");
				sb.append("}");
				sb.append("catch (HibernateException he) {");
				sb.append("throw new SystemException(he);");
				sb.append("}");
				sb.append("finally {");
				sb.append("closeSession(session);");
				sb.append("}");
				sb.append("}");*/

				// containsUser(String pk, User user)

				/*sb.append("public boolean contains" + tempEntity.getName() + "(" + entity.getPKClassName() + " pk, " + tempEntity.getPackagePath() + ".model." + tempEntity.getName() + " " + tempEntity.getVarName() + ") throws " + _getNoSuchEntityException(entity) + "Exception, " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception, SystemException {");
				sb.append("Session session = null;");
				sb.append("try {");
				sb.append("session = openSession();");
				sb.append(entity.getName() + "HBM " + entity.getVarName() + "HBM = (" + entity.getName() + "HBM)session.get(" + entity.getName() + "HBM.class, pk);");
				sb.append("if (" + entity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("throw new " + _getNoSuchEntityException(entity) + "Exception(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("}");
				sb.append(tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM " + tempEntity.getVarName() + "HBM = null;");
				sb.append(tempEntity.getVarName() + "HBM = (" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM)session.get(" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM.class, " + tempEntity.getVarName() + ".getPrimaryKey());");
				sb.append("if (" + tempEntity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + tempEntity.getName() + " exists with the primary key \" + " + tempEntity.getVarName() + ".getPrimaryKey().toString());");
				sb.append("throw new " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception(\"No " + tempEntity.getName() + " exists with the primary key \" + " + tempEntity.getVarName() + ".getPrimaryKey().toString());");
				sb.append("}");
				sb.append("Collection c = " + entity.getVarName() + "HBM.get" + col.getMethodName() + "();");
				sb.append("return c.contains(" + tempEntity.getVarName() + "HBM);");
				sb.append("}");
				sb.append("catch (HibernateException he) {");
				sb.append("throw new SystemException(he);");
				sb.append("}");
				sb.append("finally {");
				sb.append("closeSession(session);");
				sb.append("}");
				sb.append("}");*/

				// containsUsers(String pk)

				if (col.isMappingManyToMany()) {
					sb.append("public static final String SQL_CONTAINS" + tempEntity.getName().toUpperCase() + "S = \"SELECT COUNT(*) AS COUNT_VALUE FROM " + col.getMappingTable() + " WHERE " + entity.getPKVarName() + " = ?\";");

					sb.append("public boolean contains" + tempEntity.getName() + "s(" + entity.getPKClassName() + " pk) throws SystemException {");
					sb.append("Session session = null;");
					sb.append("try {");
					sb.append("session = openSession();");
					sb.append("SQLQuery q = session.createSQLQuery(SQL_CONTAINS" + tempEntity.getName().toUpperCase() + "S);");
					sb.append("q.addScalar(HibernateUtil.getCountColumnName(), Hibernate.INTEGER);");
					sb.append("QueryPos qPos = QueryPos.getInstance(q);");
					sb.append("qPos.add(pk);");
					sb.append("Iterator itr = q.list().iterator();");
					sb.append("if (itr.hasNext()) {");
					sb.append("Integer count = (Integer)itr.next();");
					sb.append("if ((count != null) && (count.intValue() > 0)) {");
					sb.append("return true;");
					sb.append("}");
					sb.append("}");
					sb.append("return false;");
					sb.append("}");
					sb.append("catch (HibernateException he) {");
					sb.append("throw new SystemException(he);");
					sb.append("}");
					sb.append("finally {");
					sb.append("closeSession(session);");
					sb.append("}");
					sb.append("}");
				}

				// removeUser(String pk, String userPK)

				sb.append("public boolean remove" + tempEntity.getName() + "(" + entity.getPKClassName() + " pk, " + tempEntity.getPKClassName() + " " + tempEntity.getVarName() + "PK) throws " + _getNoSuchEntityException(entity) + "Exception, " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception, SystemException {");
				sb.append("Session session = null;");
				sb.append("try {");
				sb.append("session = openSession();");
				sb.append(entity.getName() + "HBM " + entity.getVarName() + "HBM = (" + entity.getName() + "HBM)session.get(" + entity.getName() + "HBM.class, pk);");
				sb.append("if (" + entity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("throw new " + _getNoSuchEntityException(entity) + "Exception(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("}");
				sb.append(tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM " + tempEntity.getVarName() + "HBM = null;");
				sb.append(tempEntity.getVarName() + "HBM = (" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM)session.get(" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM.class, " + tempEntity.getVarName() + "PK);");
				sb.append("if (" + tempEntity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + tempEntity.getName() + " exists with the primary key \" + " + tempEntity.getVarName() + "PK.toString());");
				sb.append("throw new " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception(\"No " + tempEntity.getName() + " exists with the primary key \" + " + tempEntity.getVarName() + "PK.toString());");
				sb.append("}");
				sb.append("boolean value = " + entity.getVarName() + "HBM.get" + col.getMethodName() + "().remove(" + tempEntity.getVarName() + "HBM);");
				sb.append("session.flush();");
				sb.append("return value;");
				sb.append("}");
				sb.append("catch (HibernateException he) {");
				sb.append("throw new SystemException(he);");
				sb.append("}");
				sb.append("finally {");
				sb.append("closeSession(session);");
				sb.append("}");
				sb.append("}");

				// removeUser(String pk, User user)

				sb.append("public boolean remove" + tempEntity.getName() + "(" + entity.getPKClassName() + " pk, " + tempEntity.getPackagePath() + ".model." + tempEntity.getName() + " " + tempEntity.getVarName() + ") throws " + _getNoSuchEntityException(entity) + "Exception, " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception, SystemException {");
				sb.append("Session session = null;");
				sb.append("try {");
				sb.append("session = openSession();");
				sb.append(entity.getName() + "HBM " + entity.getVarName() + "HBM = (" + entity.getName() + "HBM)session.get(" + entity.getName() + "HBM.class, pk);");
				sb.append("if (" + entity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("throw new " + _getNoSuchEntityException(entity) + "Exception(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("}");
				sb.append(tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM " + tempEntity.getVarName() + "HBM = null;");
				sb.append(tempEntity.getVarName() + "HBM = (" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM)session.get(" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM.class, " + tempEntity.getVarName() + ".getPrimaryKey());");
				sb.append("if (" + tempEntity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + tempEntity.getName() + " exists with the primary key \" + " + tempEntity.getVarName() + ".getPrimaryKey().toString());");
				sb.append("throw new " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception(\"No " + tempEntity.getName() + " exists with the primary key \" + " + tempEntity.getVarName() + ".getPrimaryKey().toString());");
				sb.append("}");
				sb.append("boolean value = " + entity.getVarName() + "HBM.get" + col.getMethodName() + "().remove(" + tempEntity.getVarName() + "HBM);");
				sb.append("session.flush();");
				sb.append("return value;");
				sb.append("}");
				sb.append("catch (HibernateException he) {");
				sb.append("throw new SystemException(he);");
				sb.append("}");
				sb.append("finally {");
				sb.append("closeSession(session);");
				sb.append("}");
				sb.append("}");

				// removeUsers(String pk, String[] userPKs)

				sb.append("public boolean remove" + tempEntity.getNames() + "(" + entity.getPKClassName() + " pk, " + tempEntity.getPKClassName() + "[] " + tempEntity.getVarName() + "PKs) throws " + _getNoSuchEntityException(entity) + "Exception, " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception, SystemException {");
				sb.append("Session session = null;");
				sb.append("try {");
				sb.append("session = openSession();");
				sb.append(entity.getName() + "HBM " + entity.getVarName() + "HBM = (" + entity.getName() + "HBM)session.get(" + entity.getName() + "HBM.class, pk);");
				sb.append("if (" + entity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("throw new " + _getNoSuchEntityException(entity) + "Exception(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("}");
				sb.append("boolean value = false;");
				sb.append("for (int i = 0; i < " + tempEntity.getVarName() + "PKs.length; i++) {");
				sb.append(tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM " + tempEntity.getVarName() + "HBM = null;");
				sb.append(tempEntity.getVarName() + "HBM = (" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM)session.get(" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM.class, " + tempEntity.getVarName() + "PKs[i]);");
				sb.append("if (" + tempEntity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + tempEntity.getName() + " exists with the primary key \" + " + tempEntity.getVarName() + "PKs[i].toString());");
				sb.append("throw new " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception(\"No " + tempEntity.getName() + " exists with the primary key \" + " + tempEntity.getVarName() + "PKs[i].toString());");
				sb.append("}");
				sb.append("if (" + entity.getVarName() + "HBM.get" + col.getMethodName() + "().remove(" + tempEntity.getVarName() + "HBM)) {");
				sb.append("value = true;");
				sb.append("}");
				sb.append("}");
				sb.append("session.flush();");
				sb.append("return value;");
				sb.append("}");
				sb.append("catch (HibernateException he) {");
				sb.append("throw new SystemException(he);");
				sb.append("}");
				sb.append("finally {");
				sb.append("closeSession(session);");
				sb.append("}");
				sb.append("}");

				// removeUsers(String pk, List users)

				sb.append("public boolean remove" + tempEntity.getNames() + "(" + entity.getPKClassName() + " pk, List " + tempEntity.getVarNames() + ") throws " + _getNoSuchEntityException(entity) + "Exception, " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception, SystemException {");
				sb.append("Session session = null;");
				sb.append("try {");
				sb.append("session = openSession();");
				sb.append(entity.getName() + "HBM " + entity.getVarName() + "HBM = (" + entity.getName() + "HBM)session.get(" + entity.getName() + "HBM.class, pk);");
				sb.append("if (" + entity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("throw new " + _getNoSuchEntityException(entity) + "Exception(\"No " + entity.getName() + " exists with the primary key \" + pk.toString());");
				sb.append("}");
				sb.append("boolean value = false;");
				sb.append("for (int i = 0; i < " + tempEntity.getVarNames() + ".size(); i++) {");
				sb.append(tempEntity.getPackagePath() + ".model." + tempEntity.getName() + " " + tempEntity.getVarName() + " = (" + tempEntity.getPackagePath() + ".model." + tempEntity.getName() + ")" + tempEntity.getVarNames() + ".get(i);");
				sb.append(tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM " + tempEntity.getVarName() + "HBM = (" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM)session.get(" + tempEntity.getPackagePath() + ".service.persistence." + tempEntity.getName() + "HBM.class, " + tempEntity.getVarName() + ".getPrimaryKey());");
				sb.append("if (" + tempEntity.getVarName() + "HBM == null) {");
				sb.append("_log.warn(\"No " + tempEntity.getName() + " exists with the primary key \" + " + tempEntity.getVarName() + ".getPrimaryKey().toString());");
				sb.append("throw new " + tempEntity.getPackagePath() + "." + _getNoSuchEntityException(tempEntity) + "Exception(\"No " + tempEntity.getName() + " exists with the primary key \" + " + tempEntity.getVarName() + ".getPrimaryKey().toString());");
				sb.append("}");
				sb.append("if (" + entity.getVarName() + "HBM.get" + col.getMethodName() + "().remove(" + tempEntity.getVarName() + "HBM)) {");
				sb.append("value = true;");
				sb.append("}");
				sb.append("}");
				sb.append("session.flush();");
				sb.append("return value;");
				sb.append("}");
				sb.append("catch (HibernateException he) {");
				sb.append("throw new SystemException(he);");
				sb.append("}");
				sb.append("finally {");
				sb.append("closeSession(session);");
				sb.append("}");
				sb.append("}");
			}
		}

		// Finder methods

		sb.append("public " + _packagePath + ".model." + entity.getName() + " findByPrimaryKey(" + pkClassName + " " + pkVarName + ") throws " + _getNoSuchEntityException(entity) + "Exception, SystemException {");
		sb.append("Session session = null;");
		sb.append("try {");
		sb.append("session = openSession();");
		sb.append(entity.getName() + "HBM " + entity.getVarName() + "HBM = (" + entity.getName() + "HBM)session.get(" + entity.getName() + "HBM.class, " + pkVarName + ");");
		sb.append("if (" + entity.getVarName() + "HBM == null) {");
		sb.append("_log.warn(\"No " + entity.getName() + " exists with the primary key \" + " + pkVarName + ".toString());");
		sb.append("throw new " + _getNoSuchEntityException(entity) + "Exception(\"No " + entity.getName() + " exists with the primary key \" + " + pkVarName + ".toString());");
		sb.append("}");
		sb.append("return " + entity.getName() + "HBMUtil.model(" + entity.getVarName() + "HBM);");
		sb.append("}");
		sb.append("catch (HibernateException he) {");
		sb.append("throw new SystemException(he);");
		sb.append("}");
		sb.append("finally {");
		sb.append("closeSession(session);");
		sb.append("}");
		sb.append("}");

		for (int i = 0; i < finderList.size(); i++) {
			EntityFinder finder = (EntityFinder)finderList.get(i);

			List finderColsList = finder.getColumns();

			// Regular finder

			sb.append("public " + (finder.isCollection() ? "List" : _packagePath + ".model." + entity.getName()) + " findBy" + finder.getName() + "(");

			for (int j = 0; j < finderColsList.size(); j++) {
				EntityColumn col = (EntityColumn)finderColsList.get(j);

				sb.append(col.getType() + " " + col.getName());

				if ((j + 1) != finderColsList.size()) {
					sb.append(", ");
				}
			}

			if (finder.isCollection()) {
				sb.append(") throws SystemException {");
			}
			else {
				sb.append(") throws " + _getNoSuchEntityException(entity) + "Exception, SystemException {");
			}

			sb.append("Session session = null;");
			sb.append("try {");
			sb.append("session = openSession();");
			sb.append("StringBuffer query = new StringBuffer();");
			sb.append("query.append(\"FROM " + entity.getTable() + " IN CLASS " + _packagePath + ".service.persistence." + entity.getName() + "HBM WHERE \");");

			for (int j = 0; j < finderColsList.size(); j++) {
				EntityColumn col = (EntityColumn)finderColsList.get(j);

				sb.append("query.append(\"" + col.getDBName() + " " + col.getComparator() + " ?\");");

				if ((j + 1) != finderColsList.size()) {
					sb.append("query.append(\" AND \");");
				}
				else if (Validator.isNull(finder.getWhere())) {
					sb.append("query.append(\" \");");
				}
				else {
					sb.append("query.append(\" AND " + finder.getWhere() + " \");");
				}
			}

			EntityOrder order = entity.getOrder();

			if (order != null) {
				List orderList = order.getColumns();

				sb.append("query.append(\"ORDER BY \");");

				for (int j = 0; j < orderList.size(); j++) {
					EntityColumn col = (EntityColumn)orderList.get(j);

					sb.append("query.append(\"" + col.getDBName() + " " + (col.isOrderByAscending() ? "ASC" : "DESC") + "\")");

					if ((j + 1) != orderList.size()) {
						sb.append(".append(\", \");");
					}
					else {
						sb.append(";");
					}
				}
			}

			sb.append("Query q = session.createQuery(query.toString());");

			sb.append("int queryPos = 0;");

			for (int j = 0; j < finderColsList.size(); j++) {
				EntityColumn col = (EntityColumn)finderColsList.get(j);

				String colType = col.getType();
				String colObjType = colType;

				if (col.isPrimitiveType()) {
					if (colType.equals("boolean")) {
						colObjType = "Boolean";
					}
					else if (colType.equals("double")) {
						colObjType = "Double";
					}
					else if (colType.equals("float")) {
						colObjType = "Float";
					}
					else if (colType.equals("int")) {
						colObjType = "Integer";
					}
					else if (colType.equals("long")) {
						colObjType = "Long";
					}
					else if (colType.equals("short")) {
						colObjType = "Short";
					}
				}

				sb.append("q.set" + colObjType + "(queryPos++, " + col.getName());

				if (colType.equals("Boolean")) {
					sb.append(".booleanValue()");
				}
				else if (colType.equals("Double")) {
					sb.append(".doubleValue()");
				}
				else if (colType.equals("Float")) {
					sb.append(".floatValue()");
				}
				else if (colType.equals("Integer")) {
					sb.append(".intValue()");
				}
				else if (colType.equals("Long")) {
					sb.append(".longValue()");
				}
				else if (colType.equals("Short")) {
					sb.append(".shortValue()");
				}

				sb.append(");");
			}

			sb.append("Iterator itr = q.list().iterator();");

			if (finder.isCollection()) {
				sb.append("List list = new ArrayList();");

				sb.append("while (itr.hasNext()) {");
				sb.append(entity.getName() + "HBM " + entity.getVarName() + "HBM = (" + entity.getName() + "HBM)itr.next();");
				sb.append("list.add(" + entity.getName() + "HBMUtil.model(" + entity.getVarName() + "HBM));");
				sb.append("}");

				sb.append("return list;");
			}
			else {
				sb.append("if (!itr.hasNext()) {");
				sb.append("String msg = \"No " + entity.getName() + " exists with the key \";");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					if (j == 0) {
						sb.append("msg += StringPool.OPEN_CURLY_BRACE;");
					}

					sb.append("msg += \"" + col.getName() + "=\";");
					sb.append("msg += " + col.getName() + ";");

					if ((j + 1) != finderColsList.size()) {
						sb.append("msg += \", \";");
					}

					if ((j + 1) == finderColsList.size()) {
						sb.append("msg += StringPool.CLOSE_CURLY_BRACE;");
					}
				}

				sb.append("throw new " + _getNoSuchEntityException(entity) + "Exception(msg);");
				sb.append("}");

				sb.append(entity.getName() + "HBM " + entity.getVarName() + "HBM = (" + entity.getName() + "HBM)itr.next();");
				sb.append("return " + entity.getName() + "HBMUtil.model(" + entity.getVarName() + "HBM);");
			}

			sb.append("}");
			sb.append("catch (HibernateException he) {");
			sb.append("throw new SystemException(he);");
			sb.append("}");
			sb.append("finally {");
			sb.append("closeSession(session);");
			sb.append("}");
			sb.append("}");

			// Scrollable finder

			if (finder.isCollection()) {
				sb.append("public List findBy" + finder.getName() + "(");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sb.append(col.getType() + " " + col.getName() + ", ");
				}

				sb.append("int begin, int end) throws SystemException {");
				sb.append("return findBy" + finder.getName() + "(");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sb.append("" + col.getName() + ", ");
				}

				sb.append("begin, end, null);");
				sb.append("}");

				sb.append("public List findBy" + finder.getName() + "(");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sb.append(col.getType() + " " + col.getName() + ", ");
				}

				sb.append("int begin, int end, OrderByComparator obc) throws SystemException {");
				sb.append("Session session = null;");
				sb.append("try {");
				sb.append("session = openSession();");
				sb.append("StringBuffer query = new StringBuffer();");
				sb.append("query.append(\"FROM " + entity.getTable() + " IN CLASS " + _packagePath + ".service.persistence." + entity.getName() + "HBM WHERE \");");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sb.append("query.append(\"" + col.getDBName() + " " + col.getComparator() + " ?\");");

					if ((j + 1) != finderColsList.size()) {
						sb.append("query.append(\" AND \");");
					}
					else if (Validator.isNull(finder.getWhere())) {
						sb.append("query.append(\" \");");
					}
					else {
						sb.append("query.append(\" AND " + finder.getWhere() + " \");");
					}
				}

				sb.append("if (obc != null) {");
				sb.append("query.append(\"ORDER BY \" + obc.getOrderBy());");
				sb.append("}");

				if (order != null) {
					List orderList = order.getColumns();

					sb.append("else {");
					sb.append("query.append(\"ORDER BY \");");

					for (int j = 0; j < orderList.size(); j++) {
						EntityColumn col = (EntityColumn)orderList.get(j);

						sb.append("query.append(\"" + col.getDBName() + " " + (col.isOrderByAscending() ? "ASC" : "DESC") + "\")");

						if ((j + 1) != orderList.size()) {
							sb.append(".append(\", \");");
						}
						else {
							sb.append(";");
						}
					}

					sb.append("}");
				}

				sb.append("Query q = session.createQuery(query.toString());");

				sb.append("int queryPos = 0;");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					String colType = col.getType();
					String colObjType = colType;

					if (col.isPrimitiveType()) {
						if (colType.equals("boolean")) {
							colObjType = "Boolean";
						}
						else if (colType.equals("double")) {
							colObjType = "Double";
						}
						else if (colType.equals("float")) {
							colObjType = "Float";
						}
						else if (colType.equals("int")) {
							colObjType = "Integer";
						}
						else if (colType.equals("long")) {
							colObjType = "Long";
						}
						else if (colType.equals("short")) {
							colObjType = "Short";
						}
					}

					sb.append("q.set" + colObjType + "(queryPos++, " + col.getName());

					if (colType.equals("Boolean")) {
						sb.append(".booleanValue()");
					}
					else if (colType.equals("Double")) {
						sb.append(".doubleValue()");
					}
					else if (colType.equals("Float")) {
						sb.append(".floatValue()");
					}
					else if (colType.equals("Integer")) {
						sb.append(".intValue()");
					}
					else if (colType.equals("Long")) {
						sb.append(".longValue()");
					}
					else if (colType.equals("Short")) {
						sb.append(".shortValue()");
					}

					sb.append(");");
				}

				sb.append("List list = new ArrayList();");

				sb.append("Iterator itr = QueryUtil.iterate(q, getDialect(), begin, end);");
				sb.append("while (itr.hasNext()) {");
				sb.append(entity.getName() + "HBM " + entity.getVarName() + "HBM = (" + entity.getName() + "HBM)itr.next();");
				sb.append("list.add(" + entity.getName() + "HBMUtil.model(" + entity.getVarName() + "HBM));");
				sb.append("}");

				sb.append("return list;");
				sb.append("}");
				sb.append("catch (HibernateException he) {");
				sb.append("throw new SystemException(he);");
				sb.append("}");
				sb.append("finally {");
				sb.append("closeSession(session);");
				sb.append("}");
				sb.append("}");

				sb.append("public " + _packagePath + ".model." + entity.getName() + " findBy" + finder.getName() + "_First(");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sb.append(col.getType() + " " + col.getName() + ", ");
				}

				sb.append("OrderByComparator obc) throws " + _getNoSuchEntityException(entity) + "Exception, SystemException {");

				sb.append("List list = findBy" + finder.getName() + "(");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sb.append("" + col.getName() + ", ");
				}

				sb.append("0, 1, obc);");

				sb.append("if (list.size() == 0) {");
				sb.append("String msg = \"No " + entity.getName() + " exists with the key \";");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					if (j == 0) {
						sb.append("msg += StringPool.OPEN_CURLY_BRACE;");
					}

					sb.append("msg += \"" + col.getName() + "=\";");
					sb.append("msg += " + col.getName() + ";");

					if ((j + 1) != finderColsList.size()) {
						sb.append("msg += \", \";");
					}

					if ((j + 1) == finderColsList.size()) {
						sb.append("msg += StringPool.CLOSE_CURLY_BRACE;");
					}
				}

				sb.append("throw new " + _getNoSuchEntityException(entity) + "Exception(msg);");				sb.append("}");
				sb.append("else {");
				sb.append("return (" + _packagePath + ".model." + entity.getName() + ")list.get(0);");
				sb.append("}");
				sb.append("}");

				sb.append("public " + _packagePath + ".model." + entity.getName() + " findBy" + finder.getName() + "_Last(");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sb.append(col.getType() + " " + col.getName() + ", ");
				}

				sb.append("OrderByComparator obc) throws " + _getNoSuchEntityException(entity) + "Exception, SystemException {");

				sb.append("int count = countBy" + finder.getName() + "(");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sb.append("" + col.getName());

					if ((j + 1) != finderColsList.size()) {
						sb.append(", ");
					}
				}

				sb.append(");");

				sb.append("List list = findBy" + finder.getName() + "(");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sb.append("" + col.getName() + ", ");
				}

				sb.append("count - 1, count, obc);");

				sb.append("if (list.size() == 0) {");
				sb.append("String msg = \"No " + entity.getName() + " exists with the key \";");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					if (j == 0) {
						sb.append("msg += StringPool.OPEN_CURLY_BRACE;");
					}

					sb.append("msg += \"" + col.getName() + "=\";");
					sb.append("msg += " + col.getName() + ";");

					if ((j + 1) != finderColsList.size()) {
						sb.append("msg += \", \";");
					}

					if ((j + 1) == finderColsList.size()) {
						sb.append("msg += StringPool.CLOSE_CURLY_BRACE;");
					}
				}

				sb.append("throw new " + _getNoSuchEntityException(entity) + "Exception(msg);");				sb.append("}");
				sb.append("else {");
				sb.append("return (" + _packagePath + ".model." + entity.getName() + ")list.get(0);");
				sb.append("}");
				sb.append("}");

				sb.append("public " + _packagePath + ".model." + entity.getName() + "[] findBy" + finder.getName() + "_PrevAndNext(" + pkClassName + " " + pkVarName + ", ");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sb.append(col.getType() + " " + col.getName() + ", ");
				}

				sb.append("OrderByComparator obc) throws " + _getNoSuchEntityException(entity) + "Exception, SystemException {");
				sb.append(_packagePath + ".model." + entity.getName() + " " + entity.getVarName() + " = findByPrimaryKey(" + pkVarName + ");");

				sb.append("int count = countBy" + finder.getName() + "(");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sb.append("" + col.getName());

					if ((j + 1) != finderColsList.size()) {
						sb.append(", ");
					}
				}

				sb.append(");");

				sb.append("Session session = null;");
				sb.append("try {");
				sb.append("session = openSession();");
				sb.append("StringBuffer query = new StringBuffer();");
				sb.append("query.append(\"FROM " + entity.getTable() + " IN CLASS " + _packagePath + ".service.persistence." + entity.getName() + "HBM WHERE \");");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					sb.append("query.append(\"" + col.getDBName() + " " + col.getComparator() + " ?\");");

					if ((j + 1) != finderColsList.size()) {
						sb.append("query.append(\" AND \");");
					}
					else if (Validator.isNull(finder.getWhere())) {
						sb.append("query.append(\" \");");
					}
					else {
						sb.append("query.append(\" AND " + finder.getWhere() + " \");");
					}
				}

				sb.append("if (obc != null) {");
				sb.append("query.append(\"ORDER BY \" + obc.getOrderBy());");
				sb.append("}");

				if (order != null) {
					List orderList = order.getColumns();

					sb.append("else {");
					sb.append("query.append(\"ORDER BY \");");

					for (int j = 0; j < orderList.size(); j++) {
						EntityColumn col = (EntityColumn)orderList.get(j);

						sb.append("query.append(\"" + col.getDBName() + " " + (col.isOrderByAscending() ? "ASC" : "DESC") + "\")");

						if ((j + 1) != orderList.size()) {
							sb.append(".append(\", \");");
						}
						else {
							sb.append(";");
						}
					}

					sb.append("}");
				}

				sb.append("Query q = session.createQuery(query.toString());");

				sb.append("int queryPos = 0;");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					String colType = col.getType();
					String colObjType = colType;

					if (col.isPrimitiveType()) {
						if (colType.equals("boolean")) {
							colObjType = "Boolean";
						}
						else if (colType.equals("double")) {
							colObjType = "Double";
						}
						else if (colType.equals("float")) {
							colObjType = "Float";
						}
						else if (colType.equals("int")) {
							colObjType = "Integer";
						}
						else if (colType.equals("long")) {
							colObjType = "Long";
						}
						else if (colType.equals("short")) {
							colObjType = "Short";
						}
					}

					sb.append("q.set" + colObjType + "(queryPos++, " + col.getName());

					if (colType.equals("Boolean")) {
						sb.append(".booleanValue()");
					}
					else if (colType.equals("Double")) {
						sb.append(".doubleValue()");
					}
					else if (colType.equals("Float")) {
						sb.append(".floatValue()");
					}
					else if (colType.equals("Integer")) {
						sb.append(".intValue()");
					}
					else if (colType.equals("Long")) {
						sb.append(".longValue()");
					}
					else if (colType.equals("Short")) {
						sb.append(".shortValue()");
					}

					sb.append(");");
				}

				sb.append("Object[] objArray = QueryUtil.getPrevAndNext(q, count, obc, " + entity.getVarName() + ", " + entity.getName() + "HBMUtil.getInstance());");

				sb.append(_packagePath + ".model." + entity.getName() + "[] array = new " + _packagePath + ".model." + entity.getName() + "[3];");

				sb.append("array[0] = (" + _packagePath + ".model." + entity.getName() + ")objArray[0];");
				sb.append("array[1] = (" + _packagePath + ".model." + entity.getName() + ")objArray[1];");
				sb.append("array[2] = (" + _packagePath + ".model." + entity.getName() + ")objArray[2];");

				sb.append("return array;");
				sb.append("}");
				sb.append("catch (HibernateException he) {");
				sb.append("throw new SystemException(he);");
				sb.append("}");
				sb.append("finally {");
				sb.append("closeSession(session);");
				sb.append("}");
				sb.append("}");
			}
		}

		sb.append("public List findAll() throws SystemException {");
		sb.append("Session session = null;");
		sb.append("try {");
		sb.append("session = openSession();");
		sb.append("StringBuffer query = new StringBuffer();");
		sb.append("query.append(\"FROM " + entity.getTable() + " IN CLASS " + _packagePath + ".service.persistence." + entity.getName() + "HBM \");");

		EntityOrder order = entity.getOrder();

		if (order != null) {
			List orderList = order.getColumns();

			sb.append("query.append(\"ORDER BY \");");

			for (int j = 0; j < orderList.size(); j++) {
				EntityColumn col = (EntityColumn)orderList.get(j);

				sb.append("query.append(\"" + col.getDBName() + " " + (col.isOrderByAscending() ? "ASC" : "DESC") + "\")");

				if ((j + 1) != orderList.size()) {
					sb.append(".append(\", \");");
				}
				else {
					sb.append(";");
				}
			}
		}

		sb.append("Query q = session.createQuery(query.toString());");

		sb.append("Iterator itr = q.iterate();");
		sb.append("List list = new ArrayList();");

		sb.append("while (itr.hasNext()) {");
		sb.append(entity.getName() + "HBM " + entity.getVarName() + "HBM = (" + entity.getName() + "HBM)itr.next();");
		sb.append("list.add(" + entity.getName() + "HBMUtil.model(" + entity.getVarName() + "HBM));");
		sb.append("}");

		sb.append("return list;");
		sb.append("}");
		sb.append("catch (HibernateException he) {");
		sb.append("throw new SystemException(he);");
		sb.append("}");
		sb.append("finally {");
		sb.append("closeSession(session);");
		sb.append("}");
		sb.append("}");

		// Remove by methods

		for (int i = 0; i < finderList.size(); i++) {
			EntityFinder finder = (EntityFinder)finderList.get(i);

			List finderColsList = finder.getColumns();

			sb.append("public void removeBy" + finder.getName() + "(");

			for (int j = 0; j < finderColsList.size(); j++) {
				EntityColumn col = (EntityColumn)finderColsList.get(j);

				sb.append(col.getType() + " " + col.getName());

				if ((j + 1) != finderColsList.size()) {
					sb.append(", ");
				}
			}

			if (finder.isCollection()) {
				sb.append(") throws SystemException {");
			}
			else {
				sb.append(") throws " + _getNoSuchEntityException(entity) + "Exception, SystemException {");
			}

			sb.append("Session session = null;");
			sb.append("try {");
			sb.append("session = openSession();");
			sb.append("StringBuffer query = new StringBuffer();");
			sb.append("query.append(\"FROM " + entity.getTable() + " IN CLASS " + _packagePath + ".service.persistence." + entity.getName() + "HBM WHERE \");");

			for (int j = 0; j < finderColsList.size(); j++) {
				EntityColumn col = (EntityColumn)finderColsList.get(j);

				sb.append("query.append(\"" + col.getDBName() + " " + col.getComparator() + " ?\");");

				if ((j + 1) != finderColsList.size()) {
					sb.append("query.append(\" AND \");");
				}
				else if (Validator.isNull(finder.getWhere())) {
					sb.append("query.append(\" \");");
				}
				else {
					sb.append("query.append(\" AND " + finder.getWhere() + " \");");
				}
			}

			if (order != null) {
				List orderList = order.getColumns();

				sb.append("query.append(\"ORDER BY \");");

				for (int j = 0; j < orderList.size(); j++) {
					EntityColumn col = (EntityColumn)orderList.get(j);

					sb.append("query.append(\"" + col.getDBName() + " " + (col.isOrderByAscending() ? "ASC" : "DESC") + "\")");

					if ((j + 1) != orderList.size()) {
						sb.append(".append(\", \");");
					}
					else {
						sb.append(";");
					}
				}
			}

			sb.append("Query q = session.createQuery(query.toString());");

			sb.append("int queryPos = 0;");

			for (int j = 0; j < finderColsList.size(); j++) {
				EntityColumn col = (EntityColumn)finderColsList.get(j);

				String colType = col.getType();
				String colObjType = colType;

				if (col.isPrimitiveType()) {
					if (colType.equals("boolean")) {
						colObjType = "Boolean";
					}
					else if (colType.equals("double")) {
						colObjType = "Double";
					}
					else if (colType.equals("float")) {
						colObjType = "Float";
					}
					else if (colType.equals("int")) {
						colObjType = "Integer";
					}
					else if (colType.equals("long")) {
						colObjType = "Long";
					}
					else if (colType.equals("short")) {
						colObjType = "Short";
					}
				}

				sb.append("q.set" + colObjType + "(queryPos++, " + col.getName());

				if (colType.equals("Boolean")) {
					sb.append(".booleanValue()");
				}
				else if (colType.equals("Double")) {
					sb.append(".doubleValue()");
				}
				else if (colType.equals("Float")) {
					sb.append(".floatValue()");
				}
				else if (colType.equals("Integer")) {
					sb.append(".intValue()");
				}
				else if (colType.equals("Long")) {
					sb.append(".longValue()");
				}
				else if (colType.equals("Short")) {
					sb.append(".shortValue()");
				}

				sb.append(");");
			}

			sb.append("Iterator itr = q.list().iterator();");
			sb.append("while (itr.hasNext()) {");
			sb.append(entity.getName() + "HBM " + entity.getVarName() + "HBM = (" + entity.getName() + "HBM)itr.next();");
			sb.append("session.delete(" + entity.getVarName() + "HBM);");
			sb.append("}");

			sb.append("session.flush();");
			sb.append("}");
			sb.append("catch (HibernateException he) {");

			if (finder.isCollection()) {
				sb.append("throw new SystemException(he);");
			}
			else {
				sb.append("if (he instanceof ObjectNotFoundException) {");

				sb.append("String msg = \"No " + entity.getName() + " exists with the key \";");

				for (int j = 0; j < finderColsList.size(); j++) {
					EntityColumn col = (EntityColumn)finderColsList.get(j);

					if (j == 0) {
						sb.append("msg += StringPool.OPEN_CURLY_BRACE;");
					}

					sb.append("msg += \"" + col.getName() + "=\";");
					sb.append("msg += " + col.getName() + ";");

					if ((j + 1) != finderColsList.size()) {
						sb.append("msg += \", \";");
					}

					if ((j + 1) == finderColsList.size()) {
						sb.append("msg += StringPool.CLOSE_CURLY_BRACE;");
					}
				}

				sb.append("throw new " + _getNoSuchEntityException(entity) + "Exception(msg);");
				sb.append("}");
				sb.append("else {");
				sb.append("throw new SystemException(he);");
				sb.append("}");
			}

			sb.append("}");
			sb.append("finally {");
			sb.append("closeSession(session);");
			sb.append("}");
			sb.append("}");
		}

		// Count by methods

		for (int i = 0; i < finderList.size(); i++) {
			EntityFinder finder = (EntityFinder)finderList.get(i);

			List finderColsList = finder.getColumns();

			sb.append("public int countBy" + finder.getName() + "(");

			for (int j = 0; j < finderColsList.size(); j++) {
				EntityColumn col = (EntityColumn)finderColsList.get(j);

				sb.append(col.getType() + " " + col.getName());

				if ((j + 1) != finderColsList.size()) {
					sb.append(", ");
				}
			}

			sb.append(") throws SystemException {");
			sb.append("Session session = null;");
			sb.append("try {");
			sb.append("session = openSession();");
			sb.append("StringBuffer query = new StringBuffer();");
			sb.append("query.append(\"SELECT COUNT(*) \");");
			sb.append("query.append(\"FROM " + entity.getTable() + " IN CLASS " + _packagePath + ".service.persistence." + entity.getName() + "HBM WHERE \");");

			for (int j = 0; j < finderColsList.size(); j++) {
				EntityColumn col = (EntityColumn)finderColsList.get(j);

				sb.append("query.append(\"" + col.getDBName() + " " + col.getComparator() + " ?\");");

				if ((j + 1) != finderColsList.size()) {
					sb.append("query.append(\" AND \");");
				}
				else if (Validator.isNull(finder.getWhere())) {
					sb.append("query.append(\" \");");
				}
				else {
					sb.append("query.append(\" AND " + finder.getWhere() + " \");");
				}
			}

			sb.append("Query q = session.createQuery(query.toString());");

			sb.append("int queryPos = 0;");

			for (int j = 0; j < finderColsList.size(); j++) {
				EntityColumn col = (EntityColumn)finderColsList.get(j);

				String colType = col.getType();
				String colObjType = colType;

				if (col.isPrimitiveType()) {
					if (colType.equals("boolean")) {
						colObjType = "Boolean";
					}
					else if (colType.equals("double")) {
						colObjType = "Double";
					}
					else if (colType.equals("float")) {
						colObjType = "Float";
					}
					else if (colType.equals("int")) {
						colObjType = "Integer";
					}
					else if (colType.equals("long")) {
						colObjType = "Long";
					}
					else if (colType.equals("short")) {
						colObjType = "Short";
					}
				}

				sb.append("q.set" + colObjType + "(queryPos++, " + col.getName());

				if (colType.equals("Boolean")) {
					sb.append(".booleanValue()");
				}
				else if (colType.equals("Double")) {
					sb.append(".doubleValue()");
				}
				else if (colType.equals("Float")) {
					sb.append(".floatValue()");
				}
				else if (colType.equals("Integer")) {
					sb.append(".intValue()");
				}
				else if (colType.equals("Long")) {
					sb.append(".longValue()");
				}
				else if (colType.equals("Short")) {
					sb.append(".shortValue()");
				}

				sb.append(");");
			}

			sb.append("Iterator itr = q.list().iterator();");
			sb.append("if (itr.hasNext()) {");
			sb.append("Integer count = (Integer)itr.next();");
			sb.append("if (count != null) {");
			sb.append("return count.intValue();");
			sb.append("}");
			sb.append("}");
			sb.append("return 0;");
			sb.append("}");
			sb.append("catch (HibernateException he) {");
			sb.append("throw new SystemException(he);");
			sb.append("}");
			sb.append("finally {");
			sb.append("closeSession(session);");
			sb.append("}");
			sb.append("}");
		}

		// Fields

		sb.append("private static Log _log = LogFactory.getLog(" + entity.getName() + "Persistence.class);");

		// Class close brace

		sb.append("}");

		// Write file

		File ejbFile = new File(_outputPath + "/service/persistence/" + entity.getName() + "Persistence.java");

		writeFile(ejbFile, sb.toString());
	}

	private void _createPersistenceUtil(Entity entity) throws IOException {
		XClass xClass = _getXClass(_outputPath + "/service/persistence/" + entity.getName() + "Persistence.java");

		List methods = xClass.getMethods();

		StringBuffer sb = new StringBuffer();

		// Package

		sb.append("package " + _packagePath + ".service.persistence;");

		// Imports

		sb.append("import " + _springUtilClassName + ";");
		sb.append("import com.liferay.portal.model.ModelListener;");
		sb.append("import com.liferay.portal.util.PropsUtil;");
		sb.append("import com.liferay.util.GetterUtil;");
		sb.append("import com.liferay.util.InstancePool;");
		sb.append("import com.liferay.util.Validator;");
		sb.append("import org.apache.commons.logging.Log;");
		sb.append("import org.apache.commons.logging.LogFactory;");
		sb.append("import org.springframework.context.ApplicationContext;");

		// Class declaration

		sb.append("public class " + entity.getName() + "Util {");

		// Fields

		sb.append("public static final String CLASS_NAME = " + entity.getName() + "Util.class.getName();");
		sb.append("public static final String LISTENER = GetterUtil.getString(PropsUtil.get(\"value.object.listener." + _packagePath + ".model." + entity.getName() + "\"));");

		// Methods

		for (int i = 0; i < methods.size(); i++) {
			XMethod xMethod = (XMethod)methods.get(i);

			String methodName = xMethod.getName();

			sb.append("public static " + xMethod.getReturnType().getType().getQualifiedName() + xMethod.getReturnType().getDimensionAsString() + " " + methodName + "(");

			List parameters = xMethod.getParameters();

			String p0Name = "";
			if (parameters.size() > 0) {
				p0Name = ((XParameter)parameters.get(0)).getName();
			}

			for (int j = 0; j < parameters.size(); j++) {
				XParameter xParameter = (XParameter)parameters.get(j);

				sb.append(xParameter.getType().getQualifiedName() + xParameter.getDimensionAsString() + " " + xParameter.getName());

				if ((j + 1) != parameters.size()) {
					sb.append(", ");
				}
			}

			sb.append(")");

			List thrownExceptions = xMethod.getThrownExceptions();

			if (thrownExceptions.size() > 0) {
				sb.append(" throws ");

				Iterator itr = thrownExceptions.iterator();

				while (itr.hasNext()) {
					XClass thrownException = (XClass)itr.next();

					sb.append(thrownException.getQualifiedName());

					if (itr.hasNext()) {
						sb.append(", ");
					}
				}
			}

			sb.append(" {");

			if (methodName.equals("remove") || methodName.equals("update")) {
				sb.append("ModelListener listener = null;");

				sb.append("if (Validator.isNotNull(LISTENER)) {");

				sb.append("try {");
				sb.append("listener = (ModelListener)Class.forName(LISTENER).newInstance();");
				sb.append("}");
				sb.append("catch (Exception e) {");
				sb.append("_log.error(e);");
				sb.append("}");

				sb.append("}");

				if (methodName.equals("update")) {
					sb.append("boolean isNew = " + p0Name + ".isNew();");
				}

				sb.append("if (listener != null) {");

				if (methodName.equals("remove")) {
					sb.append("listener.onBeforeRemove(findByPrimaryKey(" + p0Name + "));");
				}
				else {
					sb.append("if (isNew) {");
					sb.append("listener.onBeforeCreate(" + p0Name + ");");
					sb.append("}");
					sb.append("else {");
					sb.append("listener.onBeforeUpdate(" + p0Name + ");");
					sb.append("}");
				}

				sb.append("}");

				if (methodName.equals("remove")) {
					sb.append(_packagePath + ".model." + entity.getName() + " " + entity.getVarName() + " = ");
				}
				else {
					sb.append(entity.getVarName() + " = ");
				}
			}
			else {
				if (!xMethod.getReturnType().getType().getQualifiedName().equals("void")) {
					sb.append("return ");
				}
			}

			sb.append("getPersistence()." + methodName + "(");

			for (int j = 0; j < parameters.size(); j++) {
				XParameter xParameter = (XParameter)parameters.get(j);

				sb.append(xParameter.getName());

				if ((j + 1) != parameters.size()) {
					sb.append(", ");
				}
			}

			sb.append(");");

			if (methodName.equals("remove") || methodName.equals("update")) {
				sb.append("if (listener != null) {");

				if (methodName.equals("remove")) {
					sb.append("listener.onAfterRemove(" + entity.getVarName() + ");");
				}
				else {
					sb.append("if (isNew) {");
					sb.append("listener.onAfterCreate(" + entity.getVarName() + ");");
					sb.append("}");
					sb.append("else {");
					sb.append("listener.onAfterUpdate(" + entity.getVarName() + ");");
					sb.append("}");
				}

				sb.append("}");

				sb.append("return " + entity.getVarName() + ";");
			}

			sb.append("}");
		}

		sb.append("public static " + entity.getName() + "Persistence getPersistence() {");
		sb.append("ApplicationContext ctx = SpringUtil.getContext();");
		sb.append(entity.getName() + "Util util = (" + entity.getName() + "Util)ctx.getBean(CLASS_NAME);");
		sb.append("return util._persistence;");
		sb.append("}");

		sb.append("public void setPersistence(" + entity.getName() + "Persistence persistence) {");
		sb.append("_persistence = persistence;");
		sb.append("}");

		// Fields

		sb.append("private static Log _log = LogFactory.getLog(" + entity.getName() + "Util.class);");

		sb.append("private " + entity.getName() + "Persistence _persistence;");

		// Class close brace

		sb.append("}");

		// Write file

		File ejbFile = new File(_outputPath + "/service/persistence/" + entity.getName() + "Util.java");

		writeFile(ejbFile, sb.toString());
	}

	private void _createService(Entity entity, int sessionType) throws IOException {
		XClass xClass = _getXClass(_outputPath + "/service/impl/" + entity.getName() + (sessionType != _REMOTE ? "Local" : "") + "ServiceImpl.java");

		List methods = xClass.getMethods();

		StringBuffer sb = new StringBuffer();

		// Package

		sb.append("package " + _packagePath + ".service.spring;");

		// Interface declaration

		sb.append("public interface " + entity.getName() + _getSessionTypeName(sessionType) + "Service {");

		// Methods

		for (int i = 0; i < methods.size(); i++) {
			XMethod xMethod = (XMethod)methods.get(i);

			String methodName = xMethod.getName();

			if (_isCustomMethod(xMethod) && xMethod.isPublic()) {
				sb.append("public " + xMethod.getReturnType().getType().getQualifiedName() + xMethod.getReturnType().getDimensionAsString() + " " + methodName + "(");

				List parameters = xMethod.getParameters();

				for (int j = 0; j < parameters.size(); j++) {
					XParameter xParameter = (XParameter)parameters.get(j);

					sb.append(xParameter.getType().getQualifiedName() + xParameter.getDimensionAsString() + " " + xParameter.getName());

					if ((j + 1) != parameters.size()) {
						sb.append(", ");
					}
				}

				sb.append(")");

				List thrownExceptions = xMethod.getThrownExceptions();

				Set newExceptions = new LinkedHashSet();

				for (int j = 0; j < thrownExceptions.size(); j++) {
					XClass thrownException = (XClass)thrownExceptions.get(j);

					newExceptions.add(thrownException.getQualifiedName());
				}

				if (sessionType != _LOCAL) {
					newExceptions.add("java.rmi.RemoteException");
				}

				if (newExceptions.size() > 0) {
					sb.append(" throws ");

					Iterator itr = newExceptions.iterator();

					while (itr.hasNext()) {
						sb.append(itr.next());

						if (itr.hasNext()) {
							sb.append(", ");
						}
					}
				}

				sb.append(";");
			}
		}

		// Interface close brace

		sb.append("}");

		// Write file

		File ejbFile = new File(_outputPath + "/service/spring/" + entity.getName() + _getSessionTypeName(sessionType) + "Service.java");

		writeFile(ejbFile, sb.toString());
	}

	private void _createServiceEJB(Entity entity, int sessionType) throws IOException {
		StringBuffer sb = new StringBuffer();

		// Package

		sb.append("package " + _packagePath + ".service.ejb;");

		// Imports

		sb.append("import " + _packagePath + ".service.spring." + entity.getName() + _getSessionTypeName(sessionType) + "Service;");

		if (sessionType == _LOCAL) {
			sb.append("import javax.ejb.EJBLocalObject;");
		}
		else {
			sb.append("import javax.ejb.EJBObject;");
		}

		// Interface declaration

		sb.append("public interface " + entity.getName() + _getSessionTypeName(sessionType) + "ServiceEJB extends EJB" + (sessionType == _LOCAL ? "Local" : "") + "Object, " + entity.getName() + _getSessionTypeName(sessionType) + "Service {");

		// Interface close brace

		sb.append("}");

		// Write file

		File ejbFile = new File(_outputPath + "/service/ejb/" + entity.getName() + _getSessionTypeName(sessionType) + "ServiceEJB.java");

		writeFile(ejbFile, sb.toString());
	}

	private void _createServiceEJBImpl(Entity entity, int sessionType) throws IOException {
		XClass xClass = _getXClass(_outputPath + "/service/spring/" + entity.getName() + (sessionType != _REMOTE ? "Local" : "") + "Service.java");

		List methods = xClass.getMethods();

		StringBuffer sb = new StringBuffer();

		// Package

		sb.append("package " + _packagePath + ".service.ejb;");

		// Imports

		sb.append("import " + _springUtilClassName + ";");
		sb.append("import " + _packagePath + ".service.spring." + entity.getName() + _getSessionTypeName(sessionType) + "Service;");
		sb.append("import javax.ejb.CreateException;");
		sb.append("import javax.ejb.SessionContext;");
		sb.append("import javax.ejb.SessionBean;");
		sb.append("import org.springframework.context.ApplicationContext;");

		if (sessionType == _REMOTE) {
			sb.append("import com.liferay.portal.service.impl.PrincipalSessionBean;");
		}

		// Class declaration

		sb.append("public class " + entity.getName() + _getSessionTypeName(sessionType) + "ServiceEJBImpl implements " + entity.getName() + _getSessionTypeName(sessionType) + "Service, SessionBean {");

		// Fields

		sb.append("public static final String CLASS_NAME = " + entity.getName() + _getSessionTypeName(sessionType) + "Service.class.getName() + \".transaction\";");

		// Methods

		sb.append("public static " + entity.getName() + _getSessionTypeName(sessionType) + "Service getService() {");
		sb.append("ApplicationContext ctx = SpringUtil.getContext();");
		sb.append("return (" + entity.getName() + _getSessionTypeName(sessionType) + "Service)ctx.getBean(CLASS_NAME);");
		sb.append("}");

		for (int i = 0; i < methods.size(); i++) {
			XMethod xMethod = (XMethod)methods.get(i);

			String methodName = xMethod.getName();

			if (_isCustomMethod(xMethod) && xMethod.isPublic()) {
				sb.append("public " + xMethod.getReturnType().getType().getQualifiedName() + xMethod.getReturnType().getDimensionAsString() + " " + methodName + "(");

				List parameters = xMethod.getParameters();

				for (int j = 0; j < parameters.size(); j++) {
					XParameter xParameter = (XParameter)parameters.get(j);

					sb.append(xParameter.getType().getQualifiedName() + xParameter.getDimensionAsString() + " " + xParameter.getName());

					if ((j + 1) != parameters.size()) {
						sb.append(", ");
					}
				}

				sb.append(")");

				List thrownExceptions = xMethod.getThrownExceptions();

				Set newExceptions = new LinkedHashSet();

				for (int j = 0; j < thrownExceptions.size(); j++) {
					XClass thrownException = (XClass)thrownExceptions.get(j);

					newExceptions.add(thrownException.getQualifiedName());
				}

				if (newExceptions.size() > 0) {
					sb.append(" throws ");

					Iterator itr = newExceptions.iterator();

					while (itr.hasNext()) {
						sb.append(itr.next());

						if (itr.hasNext()) {
							sb.append(", ");
						}
					}
				}

				sb.append("{");

				if (sessionType == _REMOTE) {
					sb.append("PrincipalSessionBean.setThreadValues(_sc);");
				}

				if (!xMethod.getReturnType().getType().getQualifiedName().equals("void")) {
					sb.append("return ");
				}

				sb.append("getService()." + methodName + "(");

				for (int j = 0; j < parameters.size(); j++) {
					XParameter xParameter = (XParameter)parameters.get(j);

					sb.append(xParameter.getName());

					if ((j + 1) != parameters.size()) {
						sb.append(", ");
					}
				}

				sb.append(");");
				sb.append("}");
			}
		}

		sb.append("public void ejbCreate() throws CreateException {");
		sb.append("}");

		sb.append("public void ejbRemove() {");
		sb.append("}");

		sb.append("public void ejbActivate() {");
		sb.append("}");

		sb.append("public void ejbPassivate() {");
		sb.append("}");

		sb.append("public SessionContext getSessionContext() {");
		sb.append("return _sc;");
		sb.append("}");

		sb.append("public void setSessionContext(SessionContext sc) {");
		sb.append("_sc = sc;");
		sb.append("}");

		// Fields

		sb.append("private SessionContext _sc;");

		// Class close brace

		sb.append("}");

		// Write file

		File ejbFile = new File(_outputPath + "/service/ejb/" + entity.getName() + _getSessionTypeName(sessionType) + "ServiceEJBImpl.java");

		writeFile(ejbFile, sb.toString());
	}

	private void _createServiceFactory(Entity entity, int sessionType) throws IOException {
		StringBuffer sb = new StringBuffer();

		// Package

		sb.append("package " + _packagePath + ".service.spring;");

		// Imports

		sb.append("import " + _springUtilClassName + ";");
		sb.append("import org.springframework.context.ApplicationContext;");

		// Class declaration

		sb.append("public class " + entity.getName() + _getSessionTypeName(sessionType) + "ServiceFactory {");

		// Fields

		sb.append("public static final String CLASS_NAME = " + entity.getName() + _getSessionTypeName(sessionType) + "ServiceFactory.class.getName();");

		// Methods

		sb.append("public static " + entity.getName() + _getSessionTypeName(sessionType) + "Service getService() {");
		sb.append("ApplicationContext ctx = SpringUtil.getContext();");
		sb.append(entity.getName() + _getSessionTypeName(sessionType) + "ServiceFactory factory = (" + entity.getName() + _getSessionTypeName(sessionType) + "ServiceFactory)ctx.getBean(CLASS_NAME);");
		sb.append("return factory._service;");
		sb.append("}");

		sb.append("public void setService(" + entity.getName() + _getSessionTypeName(sessionType) + "Service service) {");
		sb.append("_service = service;");
		sb.append("}");

		// Fields

		sb.append("private " + entity.getName() + _getSessionTypeName(sessionType) + "Service _service;");

		// Class close brace

		sb.append("}");

		// Write file

		File ejbFile = new File(_outputPath + "/service/spring/" + entity.getName() + _getSessionTypeName(sessionType) + "ServiceFactory.java");

		writeFile(ejbFile, sb.toString());
	}

	private void _createServiceHome(Entity entity, int sessionType) throws IOException {
		StringBuffer sb = new StringBuffer();

		// Package

		sb.append("package " + _packagePath + ".service.ejb;");

		// Imports

		sb.append("import javax.ejb.CreateException;");

		if (sessionType == _LOCAL) {
			sb.append("import javax.ejb.EJBLocalHome;");
		}
		else {
			sb.append("import java.rmi.RemoteException;");
			sb.append("import javax.ejb.EJBHome;");
		}

		// Interface declaration

		sb.append("public interface " + entity.getName() + _getSessionTypeName(sessionType) + "ServiceHome extends EJB" + (sessionType == _LOCAL ? "Local" : "") + "Home {");

		// Create method

		sb.append("public " + entity.getName() + _getSessionTypeName(sessionType) + "ServiceEJB create() throws CreateException");

		if (sessionType != _LOCAL) {
			sb.append(", RemoteException");
		}

		sb.append(";");

		// Interface close brace

		sb.append("}");

		// Write file

		File ejbFile = new File(_outputPath + "/service/ejb/" + entity.getName() + _getSessionTypeName(sessionType) + "ServiceHome.java");

		writeFile(ejbFile, sb.toString());
	}

	private void _createServiceHttp(Entity entity) throws IOException {
		XClass xClass = _getXClass(_outputPath + "/service/impl/" + entity.getName() + "ServiceImpl.java");

		List methods = xClass.getMethods();

		StringBuffer sb = new StringBuffer();

		// Package

		sb.append("package " + _packagePath + ".service.http;");

		// Imports

		if (_hasHttpMethods(xClass)) {
			sb.append("import " + _packagePath + ".service.spring." + entity.getName() + "ServiceUtil;");
		}

		sb.append("import com.liferay.portal.security.auth.HttpPrincipal;");
		sb.append("import com.liferay.portal.servlet.TunnelUtil;");
		sb.append("import com.liferay.portal.shared.util.BooleanWrapper;");
		sb.append("import com.liferay.portal.shared.util.DoubleWrapper;");
		sb.append("import com.liferay.portal.shared.util.FloatWrapper;");
		sb.append("import com.liferay.portal.shared.util.IntegerWrapper;");
		sb.append("import com.liferay.portal.shared.util.LongWrapper;");
		sb.append("import com.liferay.portal.shared.util.MethodWrapper;");
		sb.append("import com.liferay.portal.shared.util.NullWrapper;");
		sb.append("import com.liferay.portal.shared.util.ShortWrapper;");
		sb.append("import com.liferay.portal.shared.util.StackTraceUtil;");
		sb.append("import com.liferay.util.ObjectValuePair;");
		sb.append("import org.apache.commons.logging.Log;");
		sb.append("import org.apache.commons.logging.LogFactory;");

		// Class declaration

		sb.append("public class " + entity.getName() + "ServiceHttp {");

		// Methods

		for (int i = 0; i < methods.size(); i++) {
			XMethod xMethod = (XMethod)methods.get(i);

			String methodName = xMethod.getName();

			if (_isCustomMethod(xMethod) && xMethod.isPublic()) {
				XClass returnType = xMethod.getReturnType().getType();
				String returnTypeName = returnType.getQualifiedName() + xMethod.getReturnType().getDimensionAsString();

				sb.append("public static " + returnTypeName + " " + methodName + "(HttpPrincipal httpPrincipal");

				List parameters = xMethod.getParameters();

				for (int j = 0; j < parameters.size(); j++) {
					XParameter xParameter = (XParameter)parameters.get(j);

					if (j == 0) {
						sb.append(", ");
					}

					sb.append(xParameter.getType().getQualifiedName() + xParameter.getDimensionAsString() + " " + xParameter.getName());

					if ((j + 1) != parameters.size()) {
						sb.append(", ");
					}
				}

				sb.append(")");

				List thrownExceptions = xMethod.getThrownExceptions();

				Set newExceptions = new LinkedHashSet();

				for (int j = 0; j < thrownExceptions.size(); j++) {
					XClass thrownException = (XClass)thrownExceptions.get(j);

					newExceptions.add(thrownException.getQualifiedName());
				}

				sb.append(" throws ");

				if (newExceptions.contains("com.liferay.portal.PortalException")) {
					sb.append("com.liferay.portal.PortalException, ");
				}

				sb.append("com.liferay.portal.SystemException {");
				sb.append("try {");

				for (int j = 0; j < parameters.size(); j++) {
					XParameter xParameter = (XParameter)parameters.get(j);

					String parameterTypeName =
						xParameter.getType().getQualifiedName() +
							xParameter.getDimensionAsString();

					sb.append("Object paramObj" + j + " = ");

					if (parameterTypeName.equals("boolean")) {
						sb.append("new BooleanWrapper(" + xParameter.getName() + ");");
					}
					else if (parameterTypeName.equals("double")) {
						sb.append("new DoubleWrapper(" + xParameter.getName() + ");");
					}
					else if (parameterTypeName.equals("float")) {
						sb.append("new FloatWrapper(" + xParameter.getName() + ");");
					}
					else if (parameterTypeName.equals("int")) {
						sb.append("new IntegerWrapper(" + xParameter.getName() + ");");
					}
					else if (parameterTypeName.equals("long")) {
						sb.append("new LongWrapper(" + xParameter.getName() + ");");
					}
					else if (parameterTypeName.equals("short")) {
						sb.append("new ShortWrapper(" + xParameter.getName() + ");");
					}
					else {
						sb.append(xParameter.getName() + ";");

						sb.append("if (" + xParameter.getName() + " == null) {");
						sb.append("paramObj" + j + " = new NullWrapper(\"" + _getClassName(xParameter) + "\");");
						sb.append("}");
					}
				}

				sb.append("MethodWrapper methodWrapper = new MethodWrapper(");
				sb.append(entity.getName() + "ServiceUtil.class.getName(),");
				sb.append("\"" + methodName + "\",");

				if (parameters.size() == 0) {
					sb.append("new Object[0]);");
				}
				else {
					sb.append("new Object[] {");

					for (int j = 0; j < parameters.size(); j++) {
						sb.append("paramObj" + j);

						if ((j + 1) != parameters.size()) {
							sb.append(", ");
						}
					}

					sb.append("});");
				}

				sb.append("Object returnObj = null;");

				sb.append("try {");
				sb.append("returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);");
				sb.append("}");
				sb.append("catch (Exception e) {");

				Iterator itr = newExceptions.iterator();

				while (itr.hasNext()) {
					String exceptionType = (String)itr.next();

					sb.append("if (e instanceof " + exceptionType + ") {");
					sb.append("throw (" + exceptionType + ")e;");
					sb.append("}");
				}

				sb.append("throw e;");
				sb.append("}");

				if (!returnTypeName.equals("void")) {
					if (returnTypeName.equals("boolean")) {
						sb.append("return ((Boolean)returnObj).booleanValue();");
					}
					else if (returnTypeName.equals("double")) {
						sb.append("return ((Double)returnObj).doubleValue();");
					}
					else if (returnTypeName.equals("float")) {
						sb.append("return ((Float)returnObj).floatValue();");
					}
					else if (returnTypeName.equals("int")) {
						sb.append("return ((Integer)returnObj).intValue();");
					}
					else if (returnTypeName.equals("long")) {
						sb.append("return ((Long)returnObj).longValue();");
					}
					else if (returnTypeName.equals("short")) {
						sb.append("return ((Short)returnObj).shortValue();");
					}
					else {
						sb.append("return (" + returnTypeName + ")returnObj;");
					}
				}

				sb.append("}");

				if (newExceptions.contains("com.liferay.portal.PortalException")) {
					sb.append("catch (com.liferay.portal.PortalException pe) {");
					sb.append("_log.error(StackTraceUtil.getStackTrace(pe));");
					sb.append("throw pe;");
					sb.append("}");
				}

				if (newExceptions.contains("com.liferay.portal.SystemException")) {
					sb.append("catch (com.liferay.portal.SystemException se) {");
					sb.append("_log.error(StackTraceUtil.getStackTrace(se));");
					sb.append("throw se;");
					sb.append("}");
				}

				sb.append("catch (Exception e) {");
				sb.append("String stackTrace = StackTraceUtil.getStackTrace(e);");
				sb.append("_log.error(stackTrace);");
				sb.append("throw new com.liferay.portal.SystemException(stackTrace);");
				sb.append("}");
				sb.append("}");
			}
		}

		// Fields

		sb.append("private static Log _log = LogFactory.getLog(" + entity.getName() + "ServiceHttp.class);");

		// Class close brace

		sb.append("}");

		// Write file

		File ejbFile = new File(_outputPath + "/service/http/" + entity.getName() + "ServiceHttp.java");

		writeFile(ejbFile, sb.toString());
	}

	private void _createServiceImpl(Entity entity, int sessionType) throws IOException {
		StringBuffer sb = new StringBuffer();

		// Package

		sb.append("package " + _packagePath + ".service.impl;");

		// Imports

		if (sessionType == _REMOTE) {
			sb.append("import com.liferay.portal.service.impl.PrincipalBean;");
		}

		sb.append("import " + _packagePath + ".service.spring." + entity.getName() + _getSessionTypeName(sessionType) + "Service;");

		// Class declaration

		sb.append("public class " + entity.getName() + _getSessionTypeName(sessionType) + "ServiceImpl " + (sessionType == _REMOTE ? "extends PrincipalBean " : "") + "implements " + entity.getName() + _getSessionTypeName(sessionType) + "Service {");

		// Class close brace

		sb.append("}");

		// Write file

		File ejbFile = new File(_outputPath + "/service/impl/" + entity.getName() + _getSessionTypeName(sessionType) + "ServiceImpl.java");

		if (!ejbFile.exists()) {
			writeFile(ejbFile, sb.toString());
		}
	}

	private void _createServiceRemoteEJBImpl(Entity entity) throws IOException {
		StringBuffer sb = new StringBuffer();

		// Package

		sb.append("package " + _packagePath + ".service.ejb;");

		// Imports

		sb.append("import com.liferay.portal.service.impl.PrincipalSessionBean;");

		// Class declaration

		sb.append("public class " + entity.getName() + "RemoteServiceEJBImpl extends " + entity.getName() + "LocalServiceEJBImpl {");

		// Class close brace

		sb.append("}");

		// Write file

		File ejbFile = new File(_outputPath + "/service/ejb/" + entity.getName() + "RemoteServiceEJBImpl.java");

		writeFile(ejbFile, sb.toString());
	}

	private void _createServiceSoap(Entity entity) throws IOException {
		XClass xClass = _getXClass(_outputPath + "/service/impl/" + entity.getName() + "ServiceImpl.java");

		List methods = xClass.getMethods();

		StringBuffer sb = new StringBuffer();

		// Package

		sb.append("package " + _packagePath + ".service.http;");

		// Imports

		if (_hasSoapMethods(xClass)) {
			sb.append("import " + _packagePath + ".service.spring." + entity.getName() + "ServiceUtil;");
		}

		sb.append("import com.liferay.portal.shared.util.StackTraceUtil;");
		sb.append("import java.rmi.RemoteException;");
		sb.append("import org.apache.commons.logging.Log;");
		sb.append("import org.apache.commons.logging.LogFactory;");

		// Class declaration

		sb.append("public class " + entity.getName() + "ServiceSoap {");

		// Methods

		for (int i = 0; i < methods.size(); i++) {
			XMethod xMethod = (XMethod)methods.get(i);

			String methodName = xMethod.getName();

			if (_isCustomMethod(xMethod) && xMethod.isPublic() &&
				_isSoapMethod(xMethod)) {

				String returnValueName =
					xMethod.getReturnType().getType().getQualifiedName();
				String returnValueDimension =
					xMethod.getReturnType().getDimensionAsString();

				String extendedModelName =
					_packagePath + ".model." + entity.getName();
				String modelName = extendedModelName + "Model";

				sb.append("public static ");

				if (returnValueName.equals(extendedModelName)) {
					if (entity.hasColumns()) {
						sb.append(modelName + returnValueDimension);
					}
					else {
						sb.append(extendedModelName + returnValueDimension);
					}
				}
				else if (returnValueName.equals("java.util.List")) {
					if (entity.hasColumns()) {
						sb.append(modelName + "[]");
					}
					else {
						sb.append("java.util.List");
					}
				}
				else {
					sb.append(returnValueName + returnValueDimension);
				}

				sb.append(" " + methodName + "(");

				List parameters = xMethod.getParameters();

				for (int j = 0; j < parameters.size(); j++) {
					XParameter xParameter = (XParameter)parameters.get(j);

					sb.append(xParameter.getType().getQualifiedName() + xParameter.getDimensionAsString() + " " + xParameter.getName());

					if ((j + 1) != parameters.size()) {
						sb.append(", ");
					}
				}

				sb.append(") throws RemoteException {");
				sb.append("try {");

				if (!returnValueName.equals("void")) {
					sb.append(returnValueName + returnValueDimension + " returnValue = ");
				}

				sb.append(entity.getName() + "ServiceUtil." + methodName + "(");

				for (int j = 0; j < parameters.size(); j++) {
					XParameter xParameter = (XParameter)parameters.get(j);

					sb.append(xParameter.getName());

					if ((j + 1) != parameters.size()) {
						sb.append(", ");
					}
				}

				sb.append(");");

				if (!returnValueName.equals("void")) {
					sb.append("return ");

					if (entity.hasColumns() &&
						returnValueName.equals("java.util.List")) {

						sb.append("(" + extendedModelName + "[])returnValue.toArray(new " + extendedModelName + "[0])");
					}
					else {
						sb.append("returnValue");
					}

					sb.append(";");
				}

				sb.append("}");

				sb.append("catch (Exception e) {");
				sb.append("String stackTrace = StackTraceUtil.getStackTrace(e);");
				sb.append("_log.error(stackTrace);");
				sb.append("throw new RemoteException(stackTrace);");
				sb.append("}");
				sb.append("}");
			}
		}

		// Fields

		sb.append("private static Log _log = LogFactory.getLog(" + entity.getName() + "ServiceSoap.class);");

		// Class close brace

		sb.append("}");

		// Write file

		File ejbFile = new File(_outputPath + "/service/http/" + entity.getName() + "ServiceSoap.java");

		writeFile(ejbFile, sb.toString());
	}

	private void _createServiceUtil(Entity entity, int sessionType) throws IOException {
		XClass xClass = _getXClass(_outputPath + "/service/impl/" + entity.getName() + (sessionType != _REMOTE ? "Local" : "") + "ServiceImpl.java");

		List methods = xClass.getMethods();

		StringBuffer sb = new StringBuffer();

		// Package

		sb.append("package " + _packagePath + ".service.spring;");

		// Class declaration

		sb.append("public class " + entity.getName() + _getSessionTypeName(sessionType) + "ServiceUtil {");

		/*// Portlet id field

		String portletKey = TextFormatter.format(_portletName, TextFormatter.A);

		try {
			PortletKeys portletKeys = new PortletKeys();

			Field field = portletKeys.getClass().getField(portletKey);

			sb.append("public static final String PORTLET_ID = \"" + field.get(portletKeys) + "\";");
		}
		catch (Exception e) {
		}*/

		// Methods

		for (int i = 0; i < methods.size(); i++) {
			XMethod xMethod = (XMethod)methods.get(i);

			String methodName = xMethod.getName();

			if (_isCustomMethod(xMethod) && xMethod.isPublic()) {
				sb.append("public static " + xMethod.getReturnType().getType().getQualifiedName() + xMethod.getReturnType().getDimensionAsString() + " " + methodName + "(");

				List parameters = xMethod.getParameters();

				for (int j = 0; j < parameters.size(); j++) {
					XParameter xParameter = (XParameter)parameters.get(j);

					sb.append(xParameter.getType().getQualifiedName() + xParameter.getDimensionAsString() + " " + xParameter.getName());

					if ((j + 1) != parameters.size()) {
						sb.append(", ");
					}
				}

				sb.append(")");

				List thrownExceptions = xMethod.getThrownExceptions();

				Set newExceptions = new LinkedHashSet();

				for (int j = 0; j < thrownExceptions.size(); j++) {
					XClass thrownException = (XClass)thrownExceptions.get(j);

					newExceptions.add(thrownException.getQualifiedName());
				}

				sb.append(" throws ");

				if (newExceptions.contains("com.liferay.portal.PortalException")) {
					sb.append("com.liferay.portal.PortalException, ");
				}

				sb.append("com.liferay.portal.SystemException {");
				sb.append("try {");
				sb.append(entity.getName() + _getSessionTypeName(sessionType) + "Service " + entity.getVarName() + _getSessionTypeName(sessionType) + "Service = " + entity.getName() + _getSessionTypeName(sessionType) + "ServiceFactory.getService();");

				if (!xMethod.getReturnType().getType().getQualifiedName().equals("void")) {
					sb.append("return ");
				}

				sb.append(entity.getVarName() + _getSessionTypeName(sessionType) + "Service." + methodName + "(");

				for (int j = 0; j < parameters.size(); j++) {
					XParameter xParameter = (XParameter)parameters.get(j);

					sb.append(xParameter.getName());

					if ((j + 1) != parameters.size()) {
						sb.append(", ");
					}
				}

				sb.append(");");
				sb.append("}");

				if (newExceptions.contains("com.liferay.portal.PortalException")) {
					sb.append("catch (com.liferay.portal.PortalException pe) {");
					sb.append("throw pe;");
					sb.append("}");
				}

				if (newExceptions.contains("com.liferay.portal.SystemException")) {
					sb.append("catch (com.liferay.portal.SystemException se) {");
					sb.append("throw se;");
					sb.append("}");
				}

				sb.append("catch (Exception e) {");
				sb.append("throw new com.liferay.portal.SystemException(e);");
				sb.append("}");
				sb.append("}");
			}
		}

		// Class close brace

		sb.append("}");

		// Write file

		File ejbFile = new File(_outputPath + "/service/spring/" + entity.getName() + _getSessionTypeName(sessionType) + "ServiceUtil.java");

		writeFile(ejbFile, sb.toString());
	}

	private void _createSpringXML(boolean enterprise) throws IOException {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < _ejbList.size(); i++) {
			Entity entity = (Entity)_ejbList.get(i);

			if (entity.hasLocalService()) {
				_createSpringXMLSession(entity, sb, _LOCAL, enterprise);
			}

			if (entity.hasRemoteService()) {
				_createSpringXMLSession(entity, sb, _REMOTE, enterprise);
			}

			_createSpringXMLSession(entity, sb);
		}

		File xmlFile = null;

		if (enterprise) {
			xmlFile = new File(_springEntFileName);
		}
		else {
			xmlFile = new File(_springProFileName);
		}

		if (!xmlFile.exists()) {
			String content =
				"<?xml version=\"1.0\"?>\n" +
				"<!DOCTYPE beans PUBLIC \"-//SPRING//DTD BEAN//EN\" \"http://www.springframework.org/dtd/spring-beans.dtd\">\n" +
				"\n" +
				"<beans>\n" +
				"\t<bean id=\"liferaySessionFactory\" class=\"com.liferay.portal.spring.hibernate.HibernateConfiguration\" lazy-init=\"true\" />\n" +
				"\t<bean id=\"liferayTransactionManager\" class=\"org.springframework.orm.hibernate3.HibernateTransactionManager\" lazy-init=\"true\">\n" +
				"\t\t<property name=\"sessionFactory\">\n" +
				"\t\t\t<ref bean=\"liferaySessionFactory\" />\n" +
				"\t\t</property>\n" +
				"\t</bean>\n" +
				"</beans>";

			FileUtil.write(xmlFile, content);
		}

		String oldContent = FileUtil.read(xmlFile);
		String newContent = new String(oldContent);

		int x = oldContent.indexOf("<beans>");
		int y = oldContent.lastIndexOf("</beans>");

		int firstSession = newContent.indexOf(
			"<bean id=\"" +  _packagePath + ".service.spring.", x);

		int lastSession = -1;

		int lastSession1 = newContent.lastIndexOf(
			"<bean id=\"" +  _packagePath + ".service.spring.", y);
		int lastSession2 = newContent.lastIndexOf(
			"<bean id=\"" +  _packagePath + ".service.persistence.", y);

		if (lastSession1 != lastSession2) {
			if (lastSession1 > lastSession2) {
				lastSession = lastSession1;
			}
			else {
				lastSession = lastSession2;
			}
		}

		if (firstSession == -1 || firstSession > y) {
			x = newContent.indexOf("</beans>");
			newContent =
				newContent.substring(0, x) + sb.toString() +
				newContent.substring(x, newContent.length());
		}
		else {
			firstSession = newContent.lastIndexOf("<bean", firstSession) - 1;
			lastSession = newContent.indexOf("</bean>", lastSession) + 8;

			newContent =
				newContent.substring(0, firstSession) + sb.toString() +
				newContent.substring(lastSession, newContent.length());
		}

		if (!oldContent.equals(newContent)) {
			FileUtil.write(xmlFile, newContent);
		}
	}

	private void _createSpringXMLSession(Entity entity, StringBuffer sb, int sessionType, boolean enterprise) {
		if (enterprise) {
			sb.append("\t<bean id=\"" + _packagePath + ".service.spring." + entity.getName() + _getSessionTypeName(sessionType) + "Service.enterprise\" class=\"" + (sessionType == _LOCAL ? "com.liferay.portal.spring.ejb.LocalSessionFactoryBean" : "com.liferay.portal.spring.ejb.RemoteSessionFactoryBean") + "\" lazy-init=\"true\">\n");

			sb.append("\t\t<property name=\"businessInterface\">\n");
			sb.append("\t\t\t<value>" + _packagePath + ".service.spring." + entity.getName() + _getSessionTypeName(sessionType) + "Service</value>\n");
			sb.append("\t\t</property>\n");

			sb.append("\t\t<property name=\"jndiName\">\n");

			if (sessionType == _LOCAL) {
				sb.append("\t\t\t<value>ejb/liferay/" + entity.getName() + "LocalServiceHome</value>\n");
			}
			else {
				sb.append("\t\t\t<value>" + StringUtil.replace(_packagePath + ".service.ejb.", ".", "_") + entity.getName() + _getSessionTypeName(sessionType) + "ServiceEJB</value>\n");
			}

			sb.append("\t\t</property>\n");

			sb.append("\t</bean>\n");
		}

		sb.append("\t<bean id=\"" + _packagePath + ".service.spring." + entity.getName() + _getSessionTypeName(sessionType) + "Service.professional\" class=\"" + _packagePath + ".service.impl." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceImpl\" lazy-init=\"true\" />\n");

		sb.append("\t<bean id=\"" + _packagePath + ".service.spring." + entity.getName() + _getSessionTypeName(sessionType) + "Service.transaction\" class=\"org.springframework.transaction.interceptor.TransactionProxyFactoryBean\" lazy-init=\"true\">\n");
		sb.append("\t\t<property name=\"transactionManager\">\n");
		sb.append("\t\t\t<ref bean=\"liferayTransactionManager\" />\n");
		sb.append("\t\t</property>\n");
		sb.append("\t\t<property name=\"target\">\n");
		sb.append("\t\t\t<ref bean=\"" + _packagePath + ".service.spring." + entity.getName() + _getSessionTypeName(sessionType) + "Service.professional\" />\n");
		sb.append("\t\t</property>\n");
		sb.append("\t\t<property name=\"transactionAttributes\">\n");
		sb.append("\t\t\t<props>\n");
		//sb.append("\t\t\t\t<prop key=\"*\">PROPAGATION_REQUIRED,-PortalException,-SystemException</prop>\n");
		sb.append("\t\t\t\t<prop key=\"*\">PROPAGATION_REQUIRED</prop>\n");
		sb.append("\t\t\t</props>\n");
		sb.append("\t\t</property>\n");
		sb.append("\t</bean>\n");

		sb.append("\t<bean id=\"" + _packagePath + ".service.spring." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceFactory\" class=\"" + _packagePath + ".service.spring." + entity.getName() + _getSessionTypeName(sessionType) + "ServiceFactory\" lazy-init=\"true\">\n");
		sb.append("\t\t<property name=\"service\">\n");
		sb.append("\t\t\t<ref bean=\"" + _packagePath + ".service.spring." + entity.getName() + _getSessionTypeName(sessionType) + "Service." + (enterprise ? "enterprise" : "transaction") + "\" />\n");
		sb.append("\t\t</property>\n");
		sb.append("\t</bean>\n");
	}

	private void _createSpringXMLSession(Entity entity, StringBuffer sb) {
		if (entity.hasColumns()) {
			sb.append("\t<bean id=\"" + _packagePath + ".service.persistence." + entity.getName() + "Persistence\" class=\"" + entity.getPersistenceClass() + "\" lazy-init=\"true\">\n");
			sb.append("\t\t<property name=\"sessionFactory\">\n");
			sb.append("\t\t\t<ref bean=\"liferaySessionFactory\" />\n");
			sb.append("\t\t</property>\n");
			sb.append("\t</bean>\n");

			sb.append("\t<bean id=\"" + _packagePath + ".service.persistence." + entity.getName() + "Util\" class=\"" + _packagePath + ".service.persistence." + entity.getName() + "Util\" lazy-init=\"true\">\n");
			sb.append("\t\t<property name=\"persistence\">\n");
			sb.append("\t\t\t<ref bean=\"" + _packagePath + ".service.persistence." + entity.getName() + "Persistence\" />\n");
			sb.append("\t\t</property>\n");
			sb.append("\t</bean>\n");
		}
	}

	private void _createSQLIndexes() throws IOException {
		String sqlPath = _portalRoot + "/sql";

		File sqlFile = new File(sqlPath + "/indexes.sql");

		Set indexSQLs = new TreeSet();

		BufferedReader br = new BufferedReader(new FileReader(sqlFile));

		while (true) {
			String indexSQL = br.readLine();

			if (indexSQL == null) {
				break;
			}

			if (Validator.isNotNull(indexSQL)) {
				indexSQLs.add(indexSQL);
			}
		}

		br.close();

		String content = FileUtil.read(sqlFile);

		for (int i = 0; i < _ejbList.size(); i++) {
			Entity entity = (Entity)_ejbList.get(i);

			List finderList = entity.getFinderList();

			for (int j = 0; j < finderList.size(); j++) {
				EntityFinder finder = (EntityFinder)finderList.get(j);

				if (finder.isDBIndex()) {
					StringBuffer sb = new StringBuffer();

					String indexName =
						entity.getName() + "_ix_" + finder.getName();

					if (indexName.length() > 30) {
						indexName = indexName.substring(0, 30);
					}

					sb.append("create index " + indexName + " on " + entity.getTable() + " (");

					List finderColsList = finder.getColumns();

					for (int k = 0; k < finderColsList.size(); k++) {
						EntityColumn col = (EntityColumn)finderColsList.get(k);

						sb.append(col.getDBName());

						if ((k + 1) != finderColsList.size()) {
							sb.append(", ");
						}
					}

					sb.append(");");

					String indexSQL = sb.toString();

					if (!indexSQLs.contains(indexSQL)) {
						indexSQLs.add(indexSQL);
					}
				}
			}
		}

		StringBuffer sb = new StringBuffer();

		String prevEntityName = null;

		Iterator itr = indexSQLs.iterator();

		while (itr.hasNext()) {
			String indexSQL = (String)itr.next();

			int pos = indexSQL.indexOf("_ix_");

			String entityName = indexSQL.substring(0, pos);

			if ((prevEntityName != null) && 
				(!prevEntityName.equals(entityName))) {

				sb.append("\n");
			}

			sb.append(indexSQL);

			if (itr.hasNext()) {
				sb.append("\n");
			}

			prevEntityName = entityName;
		}

		FileUtil.write(sqlFile, sb.toString(), true);
	}

	private void _createSQLSequences() throws IOException {
		String sqlPath = _portalRoot + "/sql";

		File sqlFile = new File(sqlPath + "/sequences.sql");

		Set sequenceSQLs = new TreeSet();

		BufferedReader br = new BufferedReader(new FileReader(sqlFile));

		while (true) {
			String sequenceSQL = br.readLine();

			if (sequenceSQL == null) {
				break;
			}

			if (Validator.isNotNull(sequenceSQL)) {
				sequenceSQLs.add(sequenceSQL);
			}
		}

		br.close();

		String content = FileUtil.read(sqlFile);

		for (int i = 0; i < _ejbList.size(); i++) {
			Entity entity = (Entity)_ejbList.get(i);

			List columnList = entity.getColumnList();

			for (int j = 0; j < columnList.size(); j++) {
				EntityColumn column = (EntityColumn)columnList.get(j);

				if ("sequence".equals(column.getIdType())) {
					StringBuffer sb = new StringBuffer();

					String sequenceName = column.getIdParam();

					if (sequenceName.length() > 30) {
						sequenceName = sequenceName.substring(0, 30);
					}

					sb.append("create sequence " + sequenceName + ";");

					String sequenceSQL = sb.toString();

					if (!sequenceSQLs.contains(sequenceSQL)) {
						sequenceSQLs.add(sequenceSQL);
					}
				}
			}
		}

		StringBuffer sb = new StringBuffer();

		Iterator itr = sequenceSQLs.iterator();

		while (itr.hasNext()) {
			String sequenceSQL = (String)itr.next();

			sb.append(sequenceSQL);

			if (itr.hasNext()) {
				sb.append("\n");
			}
		}

		FileUtil.write(sqlFile, sb.toString(), true);
	}

	private void _createSQLTables() throws IOException {
		String sqlPath = _portalRoot + "/sql";

		File sqlFile = new File(sqlPath + "/portal-tables.sql");

		String content = FileUtil.read(sqlFile);

		for (int i = 0; i < _ejbList.size(); i++) {
			Entity entity = (Entity)_ejbList.get(i);

			List pkList = entity.getPKList();
			List regularColList = entity.getRegularColList();

			if (regularColList.size() > 0) {
				StringBuffer sb = new StringBuffer();

				sb.append(_CREATE_TABLE + entity.getTable() + " (\n");

				for (int j = 0; j < regularColList.size(); j++) {
					EntityColumn col = (EntityColumn)regularColList.get(j);

					String colName = col.getName();
					String colType = col.getType();
					String colIdType = col.getIdType();

					sb.append("\t" + col.getDBName());
					sb.append(" ");

					if (colType.equalsIgnoreCase("boolean")) {
						sb.append("BOOLEAN");
					}
					else if (colType.equalsIgnoreCase("double") ||
							 colType.equalsIgnoreCase("float")) {

						sb.append("DOUBLE");
					}
					else if (colType.equals("int") ||
							 colType.equals("Integer") ||
							 colType.equalsIgnoreCase("long") ||
							 colType.equalsIgnoreCase("short")) {

						sb.append("INTEGER");
					}
					else if (colType.equals("String")) {
						Map hints = ModelHintsUtil.getHints(_packagePath + ".model." + entity.getName(), colName);

						int maxLength = 75;

						if (hints != null) {
							maxLength = GetterUtil.getInteger(
								(String)hints.get("max-length"), maxLength);
						}

						if (maxLength < 4000) {
							sb.append("VARCHAR(" + maxLength + ")");
						}
						else if (maxLength == 4000) {
							sb.append("STRING");
						}
						else if (maxLength > 4000) {
							sb.append("TEXT");
						}
					}
					else if (colType.equals("Date")) {
						sb.append("DATE null");
					}
					else {
						sb.append("invalid");
					}

					if (col.isPrimary() || colName.equals("groupId") ||
						colName.equals("companyId") ||
						colName.equals("userId")) {

						sb.append(" not null");

						if (col.isPrimary() && !entity.hasCompoundPK()) { 
							sb.append(" primary key");
						}
					}
					else if (colType.equals("String")) {
						sb.append(" null");
					}

					if (Validator.isNotNull(colIdType) &&
						colIdType.equals("identity")) {

						sb.append(" IDENTITY");
					}

					if (((j + 1) != regularColList.size()) ||
						(entity.hasCompoundPK())) {

						sb.append(",");
					}

					sb.append("\n");
				}
				
				if (entity.hasCompoundPK()) {
					sb.append("\tprimary key (");

					for (int k = 0; k < pkList.size(); k++) {
						EntityColumn pk = (EntityColumn)pkList.get(k);

						sb.append(pk.getDBName());

						if ((k + 1) != pkList.size()) {
							sb.append(", ");
						}
					}

					sb.append(")\n");
				}

				sb.append(");");

				String newCreateTableString = sb.toString();

				_createSQLTables(sqlFile, newCreateTableString, entity, true);
				_createSQLTables(new File(sqlPath + "/update-3.6.0-4.0.0.sql"), newCreateTableString, entity, false);
			}
		}
	}

	private void _createSQLTables(File sqlFile, String newCreateTableString, Entity entity, boolean addMissingTables) throws IOException {
		if (!sqlFile.exists()) {
			FileUtil.write(sqlFile, StringPool.BLANK);
		}

		String content = FileUtil.read(sqlFile);

		int x = content.indexOf(_CREATE_TABLE + entity.getTable() + " (");
		int y = content.indexOf(");", x);

		if (x != -1) {
			String oldCreateTableString = content.substring(x + 1, y);

			if (!oldCreateTableString.equals(newCreateTableString)) {
				content =
					content.substring(0, x) + newCreateTableString +
						content.substring(y + 2, content.length());

				FileUtil.write(sqlFile, content);
			}
		}
		else if (addMissingTables) {
			StringBuffer sb = new StringBuffer();

			BufferedReader br = new BufferedReader(new StringReader(content));

			String line = null;
			boolean appendNewTable = true;

			while ((line = br.readLine()) != null) {
				if (appendNewTable && line.startsWith(_CREATE_TABLE)) {
					x = _CREATE_TABLE.length();
					y = line.indexOf(" ", x);

					String tableName = line.substring(x, y);

					if (tableName.compareTo(entity.getTable()) > 0) {
						sb.append(newCreateTableString + "\n\n");

						appendNewTable = false;
					}
				}

				sb.append(line).append('\n');
			}

			br.close();

			FileUtil.write(sqlFile, sb.toString(), true);
		}
	}

	private String _getClassName(Type type) {
		int dimension = type.getDimension();
		String name = type.getType().getQualifiedName();

		if (dimension > 0) {
			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < dimension; i++) {
				sb.append("[");
			}

			if (name.equals("boolean")) {
				return sb.toString() + "Z";
			}
			else if (name.equals("byte")) {
				return sb.toString() + "B";
			}
			else if (name.equals("char")) {
				return sb.toString() + "C";
			}
			else if (name.equals("double")) {
				return sb.toString() + "D";
			}
			else if (name.equals("float")) {
				return sb.toString() + "F";
			}
			else if (name.equals("int")) {
				return sb.toString() + "I";
			}
			else if (name.equals("long")) {
				return sb.toString() + "J";
			}
			else if (name.equals("short")) {
				return sb.toString() + "S";
			}
			else {
				return sb.toString() + "L" + name + ";";
			}
		}

		return name;
	}

	private String _getNoSuchEntityException(Entity entity) {
		String noSuchEntityException = entity.getName();
		if (Validator.isNull(entity.getPortletShortName()) || noSuchEntityException.startsWith(entity.getPortletShortName())) {
			noSuchEntityException = noSuchEntityException.substring(entity.getPortletShortName().length(), noSuchEntityException.length());
		}
		noSuchEntityException = "NoSuch" + noSuchEntityException;

		return noSuchEntityException;
	}

	private String _getSessionTypeName(int sessionType) {
		if (sessionType == _LOCAL) {
			return "Local";
		}
		else {
			return "";
		}
	}

	private XClass _getXClass(String fileName) {
		XClass xClass = (XClass)SimpleCachePool.get(fileName);

		if (xClass == null) {
			int pos = fileName.indexOf("/");

			String srcDir = fileName.substring(0, pos);
			String srcFile = fileName.substring(pos + 1, fileName.length());
			String className = StringUtil.replace(
				srcFile.substring(0, srcFile.length() - 5), "/", ".");

			XJavaDoc xJavaDoc = new XJavaDoc();

			xJavaDoc.addSourceSet(
				new FileSourceSet(new File(srcDir), new String[] {srcFile}));

			xClass = xJavaDoc.getXClass(className);

			// Caching this causes abnormal usage of memory

			//SimpleCachePool.put(fileName, xClass);
		}

		return xClass;
	}

	private boolean _hasHttpMethods(XClass xClass) {
		List methods = xClass.getMethods();

		for (int i = 0; i < methods.size(); i++) {
			XMethod xMethod = (XMethod)methods.get(i);

			if (_isCustomMethod(xMethod) && xMethod.isPublic()) {
				return true;
			}
		}

		return false;
	}

	private boolean _hasSoapMethods(XClass xClass) {
		List methods = xClass.getMethods();

		for (int i = 0; i < methods.size(); i++) {
			XMethod xMethod = (XMethod)methods.get(i);

			if (_isCustomMethod(xMethod) && xMethod.isPublic() &&
				_isSoapMethod(xMethod)) {

				return true;
			}
		}

		return false;
	}

	private boolean _isCustomMethod(XMethod method) {
		String methodName = method.getName();

		if (methodName.equals("hasAdministrator") ||
			methodName.equals("ejbCreate") ||
			methodName.equals("ejbRemove") ||
			methodName.equals("ejbActivate") ||
			methodName.equals("ejbPassivate") ||
			methodName.equals("getSessionContext") ||
			methodName.equals("setSessionContext") ||
			methodName.equals("hashCode") ||
			methodName.equals("getClass") ||
			methodName.equals("wait") ||
			methodName.equals("equals") ||
			methodName.equals("toString") ||
			methodName.equals("notify") ||
			methodName.equals("notifyAll")) {

			return false;
		}
		else if (methodName.equals("getUser") &&
				 method.getParameters().size() == 0) {

			return false;
		}
		else if (methodName.equals("getUserId") &&
				 method.getParameters().size() == 0) {

			return false;
		}
		else {
			return true;
		}
	}

	private boolean _isSoapMethod(XMethod method) {
		String returnValueName =
			method.getReturnType().getType().getQualifiedName();

		if (returnValueName.startsWith("java.io") ||
			returnValueName.equals("java.util.Properties") ||
			returnValueName.startsWith("javax")) {

			return false;
		}

		List parameters = method.getParameters();

		for (int i = 0; i < parameters.size(); i++) {
			XParameter xParameter = (XParameter)parameters.get(i);

			String parameterTypeName =
				xParameter.getType().getQualifiedName() +
					xParameter.getDimensionAsString();

			if ((parameterTypeName.indexOf(
					"com.liferay.portal.model.") != -1) ||
				(parameterTypeName.equals(
					"com.liferay.portlet.PortletPreferencesImpl")) ||
				 parameterTypeName.startsWith("java.io") ||
				 //parameterTypeName.startsWith("java.util.List") ||
				 parameterTypeName.startsWith("java.util.Locale") ||
				 parameterTypeName.startsWith("java.util.Properties") ||
				 parameterTypeName.startsWith("javax")) {

				return false;
			}
		}

		return true;
	}

	private static final int _REMOTE = 0;

	private static final int _LOCAL = 1;

	private static final String _CREATE_TABLE = "create table ";

	private Set _badTableNames;
	private Set _badCmpFields;
	private String _hbmFileName;
	private String _modelHintsFileName;
	private String _springEntFileName;
	private String _springProFileName;
	private String _springUtilClassName;
	private String _portalRoot;
	private String _portletName;
	private String _portletShortName;
	private String _portletClassName;
	private String _portletPackageName;
	private String _outputPath;
	private String _packagePath;
	private List _ejbList;

}