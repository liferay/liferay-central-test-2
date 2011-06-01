/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.servicebuilder.ServiceBuilder;
import com.liferay.portal.util.FileImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeTableBuilder {

	public static void main(String[] args) {
		try {
			new UpgradeTableBuilder(args[0]);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public UpgradeTableBuilder(String upgradeTableDir) throws Exception {
		File[] files = new File("src/com/liferay/portal/upgrade").listFiles();

		for (File file : files) {
			if (!file.isDirectory()) {
				continue;
			}

			String folderName = file.getName();

			if (!folderName.startsWith("v")) {
				continue;
			}

			File utilFolder = new File(file, "util");

			if (!utilFolder.exists()) {
				continue;
			}

			String version = StringUtil.replace(
				folderName.substring(1), "_", ".");

			int pos = version.indexOf(".to.");

			if (pos != -1) {
				version = version.substring(pos + 4);
			}

			File upgradeDir = new File(upgradeTableDir, version);

			checkUpgradeTable(upgradeDir, utilFolder.listFiles(), version);
		}
	}

	protected void checkUpgradeTable(
			File upgradeDir, File[] files, String version)
		throws Exception {

		boolean currentRelease = version.equals(ReleaseInfo.getVersion());

		if (!currentRelease && !upgradeDir.exists()) {
			return;
		}

		File indexesFile = null;

		if (currentRelease) {
			indexesFile = new File("../sql/indexes.sql");
		}
		else {
			indexesFile = new File(upgradeDir, "indexes.sql");
		}

		for (File file : files) {
			String fileName = file.getName();

			if (!fileName.endsWith("Table.java")) {
				continue;
			}

			String modelName = fileName.substring(0, fileName.length() - 10);

			File upgradeFile = null;

			if (currentRelease) {
				String upgradeFileName = _findUpgradeFileName(modelName);

				if (upgradeFileName == null) {
					continue;
				}

				upgradeFile = new File("src", upgradeFileName);
			}
			else {
				String modelImplFileName = modelName + "ModelImpl.java";

				upgradeFile = new File(upgradeDir, modelImplFileName);
			}

			if (!upgradeFile.exists()) {
				continue;
			}

			String content = _fileUtil.read(upgradeFile);

			String packagePath =
				"com.liferay.portal.upgrade.v" +
					StringUtil.replace(version, ".", "_") + ".util";

			String className = modelName + "Table";

			String author = _getAuthor(fileName);

			String[] addIndexes = _getAddIndexes(indexesFile, modelName);

			content = _getContent(
				packagePath, className, content, author, addIndexes);

			_fileUtil.write(file, content);
		}
	}

	private String _findUpgradeFileName(String modelName) {
		DirectoryScanner ds = new DirectoryScanner();

		ds.setBasedir("src");
		ds.setIncludes(new String[] {"**\\" + modelName + "ModelImpl.java"});

		ds.scan();

		String[] fileNames = ds.getIncludedFiles();

		if (fileNames.length > 0) {
			return fileNames[0];
		}
		else {
			return null;
		}
	}

	private String[] _getAddIndexes(File indexesFile, String tableName)
		throws Exception {

		List<String> addIndexes = new ArrayList<String>();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			new InputStreamReader(new FileInputStream(indexesFile)));

		String line = null;

		while ((line = unsyncBufferedReader.readLine()) != null) {
			if (line.contains(" on " + tableName + " (") ||
				line.contains(" on " + tableName + "_ (")) {

				String sql = line.trim();

				if (sql.endsWith(";")) {
					sql = sql.substring(0, sql.length() - 1);
				}

				addIndexes.add(sql);
			}
		}

		unsyncBufferedReader.close();

		return addIndexes.toArray(new String[addIndexes.size()]);
	}

	private String _getAuthor(String fileName) throws Exception {
		if (_fileUtil.exists(fileName)) {
			String content = _fileUtil.read(fileName);

			int x = content.indexOf("* @author ");

			if (x != -1) {
				int y = content.indexOf("*", x + 1);

				if (y != -1) {
					return content.substring(x + 10, y).trim();
				}
			}
		}

		return ServiceBuilder.AUTHOR;
	}

	private String _getContent(
			String packagePath, String className, String content, String author,
			String[] addIndexes)
		throws Exception {

		int x = content.indexOf("public static final String TABLE_NAME =");

		if (x == -1) {
			x = content.indexOf("public static String TABLE_NAME =");
		}

		int y = content.indexOf("public static final String TABLE_SQL_DROP =");

		if (y == -1) {
			y = content.indexOf("public static String TABLE_SQL_DROP =");
		}

		y = content.indexOf(";", y);

		content = content.substring(x, y + 1);

		content = StringUtil.replace(
			content,
			new String[] {
				"\t", "{ \"", "new Integer(Types.", ") }", " }"
			},
			new String[] {
				"", "{\"", "Types.", "}", "}"
			});

		while (content.contains("\n\n")) {
			content = StringUtil.replace(content, "\n\n", "\n");
		}

		StringBundler sb = new StringBundler();

		sb.append(_fileUtil.read("../copyright.txt"));

		sb.append("\n\npackage ");
		sb.append(packagePath);
		sb.append(";\n\n");

		sb.append("import java.sql.Types;\n\n");

		sb.append("/**\n");
		sb.append(" * @author\t  ");
		sb.append(author);
		sb.append("\n");
		sb.append(" * @generated\n");
		sb.append(" */\n");
		sb.append("public class ");
		sb.append(className);
		sb.append(" {\n\n");

		String[] lines = StringUtil.split(content, "\n");

		for (String line : lines) {
			if (line.startsWith("public static") ||
				line.startsWith("};")) {

				sb.append("\t");
			}
			else if (line.startsWith("{\"")) {
				sb.append("\t\t");
			}

			sb.append(line);
			sb.append("\n");

			if (line.endsWith(";")) {
				sb.append("\n");
			}
		}

		sb.append("\tpublic static final String[] TABLE_SQL_ADD_INDEXES = {\n");

		for (int i = 0; i < addIndexes.length; i++) {
			String addIndex = addIndexes[i];

			sb.append("\t\t\"");
			sb.append(addIndex);
			sb.append("\"");

			if ((i + 1) < addIndexes.length) {
				sb.append(",");
			}

			sb.append("\n");
		}

		sb.append("\t};\n\n");

		sb.append("}");

		return sb.toString();
	}

	private static FileImpl _fileUtil = FileImpl.getInstance();

}