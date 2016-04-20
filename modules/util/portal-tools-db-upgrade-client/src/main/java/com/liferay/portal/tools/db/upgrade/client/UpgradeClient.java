/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.db.upgrade.client;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import jline.console.ConsoleReader;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author David Truong
 */
public class UpgradeClient {

	public static void main(String[] args) {
		try {
			Options options = _getOptions();

			CommandLineParser commandLineParser = new DefaultParser();

			CommandLine commandLine = commandLineParser.parse(options, args);

			if (commandLine.hasOption("help")) {
				HelpFormatter helpFormatter = new HelpFormatter();

				helpFormatter.printHelp(
					"Liferay Portal Tools Database Upgrade Client", options);

				return;
			}

			String jvmOpts = null;

			if (commandLine.hasOption("jvmOpts")) {
				jvmOpts = commandLine.getOptionValue("jvmOpts");
			}
			else {
				jvmOpts =
					"-Dfile.encoding=UTF8 -Duser.country=US " +
						"-Duser.language=en -Duser.timezone=GMT -Xmx2048m ";
			}

			File logFile = null;

			if (commandLine.hasOption("logFile")) {
				logFile = new File(commandLine.getOptionValue("logFile"));
			}
			else {
				logFile = new File("upgrade.log");
			}

			if (logFile.exists()) {
				String logFileName = logFile.getName();

				logFile.renameTo(
					new File(logFileName + "." + logFile.lastModified()));

				logFile = new File(logFileName);
			}

			UpgradeClient upgradeClient = new UpgradeClient(jvmOpts, logFile);

			upgradeClient.upgrade();
		}
		catch (ParseException pe) {
			System.err.println("Unable to parse command line properties");

			pe.printStackTrace();
		}
		catch (Exception e) {
			System.err.println("Error running upgrade");

			e.printStackTrace();
		}
	}

	public UpgradeClient(String jvmOpts, File logFile) throws IOException {
		_jvmOpts = jvmOpts;
		_logFile = logFile;

		_appServerPropertiesFile = new File("app-server.properties");

		_appServerProperties = _readProperties(_appServerPropertiesFile);

		_portalUpgradeDatabasePropertiesFile = new File(
			"portal-upgrade-datasource.properties");

		_portalUpgradeDatabaseProperties = _readProperties(
			_portalUpgradeDatabasePropertiesFile);

		_portalUpgradeExtPropertiesFile = new File(
			"portal-upgrade-ext.properties");

		_portalUpgradeExtProperties = _readProperties(
			_portalUpgradeExtPropertiesFile);
	}

	public void upgrade() throws IOException {
		verifyProperties();

		System.setOut(
			new TeePrintStream(new FileOutputStream(_logFile), System.out));

		ProcessBuilder processBuilder = new ProcessBuilder();

		List<String> commands = new ArrayList<>();

		if (_JAVA_HOME != null) {
			commands.add(_JAVA_HOME + "/bin/java");
		}
		else {
			commands.add("java");
		}

		commands.add("-cp");
		commands.add(_getClassPath());
		commands.addAll(Arrays.asList(_jvmOpts.split(" ")));
		commands.add("-Dexternal-properties=portal-upgrade.properties");
		commands.add("com.liferay.portal.tools.DBUpgrader");

		processBuilder.command(commands);

		processBuilder.redirectErrorStream(true);

		Process process = processBuilder.start();

		try (InputStreamReader inputStreamReader = new InputStreamReader(
				process.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(
				inputStreamReader)) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				if (line.equals(
						"Running modules upgrades. Connect to Gogo shell to " +
							"check the status.")) {

					break;
				}
				else {
					System.out.println(line);
				}
			}

			System.out.flush();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}

		boolean upgrading = true;

		while (upgrading) {
			try (GogoTelnetClient gogoTelnetClient = new GogoTelnetClient()) {
				System.out.println("You are connected to Gogo shell.");

				_printHelp();

				_consoleReader.setPrompt("g! ");

				String line;

				while ((line = _consoleReader.readLine()) != null) {
					if (line.equals("exit") || line.equals("quit")) {
						break;
					}
					else if (line.equals("upgrade:help")) {
						_printHelp();
					}
					else {
						System.out.println(gogoTelnetClient.send(line));
					}
				}

				System.out.print(
					"Checking to see if all upgrades steps have completed...");

				String upgradeSteps = gogoTelnetClient.send(
					"upgrade:list | grep Registered | grep step");

				upgrading = upgradeSteps.contains("true");

				if (upgrading) {
					System.out.println(
						" one of your upgrades is still running or failed.");
					System.out.println("Are you sure you want to exit (y/N)?");

					_consoleReader.setPrompt("");

					String response = _consoleReader.readLine();

					if (response.equals("y")) {
						upgrading = false;
					}
				}
				else {
					System.out.println(" done.");
				}
			}
			catch (Exception e) {
				upgrading = false;
			}
		}

		System.out.println("Exiting Gogo shell.");

		_close(process.getErrorStream());
		_close(process.getInputStream());
		_close(process.getOutputStream());

		process.destroy();
	}

	public void verifyProperties() {
		try {
			_verifyAppServerProperties();
			_verifyPortalUpgradeDatabaseProperties();
			_verifyPortalUpgradeExtProperties();

			_saveProperties();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private static Options _getOptions() {
		Options options = new Options();

		options.addOption(
			new Option("h", "help", false, "Print this message."));
		options.addOption(
			new Option(
				"j", "jvmOpts", true,
				"Set the JVM_OPTS used for the upgrade."));
		options.addOption(
			new Option("l", "logFile", true, "Set the name of log file."));

		return options;
	}

	private void _appendClassPath(StringBuilder sb, File dir)
		throws IOException {

		if (dir.exists() && dir.isDirectory()) {
			for (File file : dir.listFiles()) {
				String fileName = file.getName();

				if (file.isFile() && fileName.endsWith("jar")) {
					sb.append(file.getCanonicalPath() + File.pathSeparator);
				}
				else if (file.isDirectory()) {
					_appendClassPath(sb, file);
				}
			}
		}
	}

	private void _close(Closeable closeable) throws IOException {
		closeable.close();
	}

	private String _getClassPath() throws IOException {
		StringBuilder sb = new StringBuilder();

		String liferayClassPath = System.getenv("LIFERAY_CLASSPATH");

		if ((liferayClassPath != null) && !liferayClassPath.isEmpty()) {
			sb.append(liferayClassPath);
			sb.append(File.pathSeparator);
		}

		_appendClassPath(sb, new File("lib"));
		_appendClassPath(sb, new File("."));
		_appendClassPath(sb, new File(_appServer.getDir(), "bin"));
		_appendClassPath(sb, _appServer.getGlobalLibDir());

		File portalClassesDir = _appServer.getPortalClassesDir();

		sb.append(portalClassesDir.getCanonicalPath());

		_appendClassPath(sb, _appServer.getPortalLibDir());

		return sb.toString();
	}

	private String _getRelativePath(File baseFile, File pathFile) {
		return _getRelativePath(baseFile.toPath(), pathFile.toPath());
	}

	private String _getRelativePath(Path basePath, Path path) {
		Path relativePath = basePath.relativize(path);

		return relativePath.toString();
	}

	private void _printHelp() {
		System.out.println("\nUpgrade commands:");
		System.out.println("exit or quit - exit Gogo Shell");
		System.out.println("upgrade:help - show upgrade commands");
		System.out.println(
			"upgrade:execute {module_name} - Execute upgrade for that module");
		System.out.println("upgrade:list - List all registered upgrades");
		System.out.println(
			"upgrade:list {module_name} - " +
				"List the upgrade steps required for that module");
		System.out.println(
			"upgrade:list | grep Registered - " +
				"List upgrades that are registered and what version they are " +
					"at");
		System.out.println(
			"upgrade:list | grep Registered | grep steps - " +
				"List upgrades in progress");
		System.out.println("verify:execute {module_name} - execute a verifier");
		System.out.println("verify:list - List all registered verifiers");
	}

	private Properties _readProperties(File file) {
		Properties properties = new Properties();

		if (file.exists()) {
			try (InputStream inputStream = new FileInputStream(file)) {
				properties.load(inputStream);
			}
			catch (IOException ioe) {
				System.err.println("Unable to load " + file);
			}
		}

		return properties;
	}

	private void _saveProperties() throws IOException {
		_store(
			_portalUpgradeDatabaseProperties,
			_portalUpgradeDatabasePropertiesFile);
		_store(_portalUpgradeExtProperties, _portalUpgradeExtPropertiesFile);
		_store(_appServerProperties, _appServerPropertiesFile);
	}

	private void _store(Properties properties, File file) throws IOException {
		try (PrintWriter printWriter = new PrintWriter(file)) {
			Enumeration<?> enumeration = properties.propertyNames();

			while (enumeration.hasMoreElements()) {
				String key = (String)enumeration.nextElement();
				String value = properties.getProperty(key);

				printWriter.println(key + "=" + value);
			}
		}
	}

	private void _verifyAppServerProperties() throws IOException {
		String value = _appServerProperties.getProperty("dir");

		if ((value == null) || value.isEmpty()) {
			String response;

			while (_appServer == null) {
				System.out.print("[ ");

				for (String appServer : _appServers.keySet()) {
					System.out.print(appServer + " ");
				}

				System.out.println("]");

				System.out.println("Please enter your app server (tomcat): ");

				response = _consoleReader.readLine();

				if (response.isEmpty()) {
					response = "tomcat";
				}

				_appServer = _appServers.get(response);

				if (_appServer == null) {
					System.err.println(
						response + " is not a supported app server");
				}
			}

			File dir = _appServer.getDir();
			File globalLibDir = _appServer.getGlobalLibDir();
			File portalDir = _appServer.getPortalDir();

			System.out.println(
				"Please enter your app server dir (" + dir + "): ");

			response = _consoleReader.readLine();

			if (!response.isEmpty()) {
				_appServer.setDirName(response);
			}

			System.out.println(
				"Please enter your global lib dir (" + globalLibDir + "): ");

			response = _consoleReader.readLine();

			if (!response.isEmpty()) {
				_appServer.setGlobalLibDirName(response);
			}

			System.out.println(
				"Please enter your portal dir (" + portalDir + "): ");

			response = _consoleReader.readLine();

			if (!response.isEmpty()) {
				_appServer.setPortalDirName(response);
			}

			_appServerProperties.setProperty("dir", dir.getCanonicalPath());
			_appServerProperties.setProperty(
				"global.dir.lib", _getRelativePath(dir, globalLibDir));
			_appServerProperties.setProperty(
				"portal.dir", _getRelativePath(dir, portalDir));
		}
		else {
			_appServer = new AppServer(
				value, _appServerProperties.getProperty("global.dir.lib"),
				_appServerProperties.getProperty("portal.dir"));
		}
	}

	private void _verifyPortalUpgradeDatabaseProperties() throws IOException {
		String value = _portalUpgradeDatabaseProperties.getProperty(
			"jdbc.default.driverClassName");

		if ((value == null) || value.isEmpty()) {
			String response;

			Database dataSource = null;

			while (dataSource == null) {
				System.out.print("[ ");

				for (String name : _databases.keySet()) {
					System.out.print(name + " ");
				}

				System.out.println("]");

				System.out.println("Please enter your database (mysql): ");

				response = _consoleReader.readLine();

				if (response.isEmpty()) {
					response = "mysql";
				}

				dataSource = _databases.get(response);

				if (dataSource == null) {
					System.err.println(
						response + " is not a supported database");
				}
			}

			System.out.println(
				"Please enter your database JDBC driver protocol (" +
					dataSource.getProtocol() + "): ");

			response = _consoleReader.readLine();

			if (!response.isEmpty()) {
				dataSource.setProtocol(response);
			}

			System.out.println(
				"Please enter your database JDBC driver class name(" +
					dataSource.getClassName() + "): ");

			response = _consoleReader.readLine();

			if (!response.isEmpty()) {
				dataSource.setClassName(response);
			}

			System.out.println(
				"Please enter your database host (" + dataSource.getHost() +
					"): ");

			response = _consoleReader.readLine();

			if (!response.isEmpty()) {
				dataSource.setHost(response);
			}

			String portString;

			if (dataSource.getPort() > 0) {
				portString = String.valueOf(dataSource.getPort());
			}
			else {
				portString = "none";
			}

			System.out.println(
				"Please enter your database port (" + portString + "): ");

			response = _consoleReader.readLine();

			if (!response.isEmpty()) {
				if (response.equals("none")) {
					dataSource.setPort(0);
				}
				else {
					dataSource.setPort(Integer.parseInt(response));
				}
			}

			System.out.println(
				"Please enter your database name (" +
					dataSource.getDatabaseName() + "): ");

			response = _consoleReader.readLine();

			if (!response.isEmpty()) {
				dataSource.setDatabaseName(response);
			}

			System.out.println("Please enter your database username: ");

			String username = _consoleReader.readLine();

			System.out.println("Please enter your database password: ");

			String password = _consoleReader.readLine();

			_portalUpgradeDatabaseProperties.setProperty(
				"jdbc.default.driverClassName", dataSource.getClassName());
			_portalUpgradeDatabaseProperties.setProperty(
				"jdbc.default.url", dataSource.getURL());
			_portalUpgradeDatabaseProperties.setProperty(
				"jdbc.default.username", username);
			_portalUpgradeDatabaseProperties.setProperty(
				"jdbc.default.password", password);
		}
	}

	private void _verifyPortalUpgradeExtProperties() throws IOException {
		String value = _portalUpgradeExtProperties.getProperty("liferay.home");

		if ((value == null) || value.isEmpty()) {
			System.out.println("Please enter your Liferay home (../): ");

			String response = _consoleReader.readLine();

			if (response.isEmpty()) {
				response = "../";
			}

			File liferayHome = new File(response);

			_portalUpgradeExtProperties.setProperty(
				"liferay.home", liferayHome.getCanonicalPath());
		}
	}

	private static final String _JAVA_HOME = System.getenv("JAVA_HOME");

	private static final Map<String, AppServer> _appServers =
		new LinkedHashMap<>();
	private static final Map<String, Database> _databases =
		new LinkedHashMap<>();

	static {
		_appServers.put("jboss", AppServer.getJBossEAPAppServer());
		_appServers.put("jonas", AppServer.getJOnASAppServer());
		_appServers.put("resin", AppServer.getResinAppServer());
		_appServers.put("tcserver", AppServer.getTCServerAppServer());
		_appServers.put("tomcat", AppServer.getTomcatAppServer());
		_appServers.put("weblogic", AppServer.getWebLogicAppServer());
		_appServers.put("websphere", AppServer.getWebSphereAppServer());
		_appServers.put("wildfly", AppServer.getWildFlyAppServer());

		_databases.put("db2", Database.getDB2Database());
		_databases.put("mariadb", Database.getMariaDBDatabase());
		_databases.put("mysql", Database.getMySQLDatabase());
		_databases.put("oracle", Database.getOracleDataSource());
		_databases.put("postgresql", Database.getPostgreSQLDatabase());
		_databases.put("sqlserver", Database.getSQLServerDatabase());
		_databases.put("sybase", Database.getSybaseDatabase());
	}

	private AppServer _appServer;
	private final Properties _appServerProperties;
	private final File _appServerPropertiesFile;
	private final ConsoleReader _consoleReader = new ConsoleReader();
	private final String _jvmOpts;
	private final File _logFile;
	private final Properties _portalUpgradeDatabaseProperties;
	private final File _portalUpgradeDatabasePropertiesFile;
	private final Properties _portalUpgradeExtProperties;
	private final File _portalUpgradeExtPropertiesFile;

}