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

package com.liferay.portal.tools.upgrade.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

	public static final String JAVA_HOME = System.getenv("JAVA_HOME");

	public static void main(String[] args) {
		try {
			Options options = _getOptions();

			CommandLineParser parser = new DefaultParser();

			CommandLine cmd = parser.parse(options, args);

			if (cmd.hasOption("help")) {
				HelpFormatter formatter = new HelpFormatter();

				formatter.printHelp("Liferay Database Upgrade Tool", options);

				return;
			}

			String jvmOpts;

			if (cmd.hasOption("jvmOpts")) {
				jvmOpts = cmd.getOptionValue("jvmOpts");
			}
			else {
				jvmOpts =
					"-Xmx2048m -Dfile.encoding=UTF8 -Duser.country=US " +
						"-Duser.language=en -Duser.timezone=GMT";
			}

			File logFile;

			if (cmd.hasOption("logFile")) {
				logFile = new File(cmd.getOptionValue("logFile"));
			}
			else {
				logFile = new File("upgrade.log");
			}

			if (logFile.exists()) {
				String fileName = logFile.getName();

				logFile.renameTo(
					new File(fileName + "." + logFile.lastModified()));

				logFile = new File(fileName);
			}

			UpgradeClient upgradeClient = new UpgradeClient(jvmOpts, logFile);

			upgradeClient.upgrade();
		}
		catch (ParseException pe) {
			System.err.println("Failed to parse command line properties");

			pe.printStackTrace();
		}
		catch (Exception e) {
			System.err.println("Error running upgrade");

			e.printStackTrace();
		}
	}

	public UpgradeClient(String jvmOpts, File logFile) throws Exception {
		_consoleReader = new ConsoleReader();

		_datasourcePropertiesFile = new File(
			"portal-upgrade-datasource.properties");

		_datasourceProperties = new Properties();

		if (_datasourcePropertiesFile.exists()) {
			try (
				InputStream inputStream =
					new FileInputStream(_datasourcePropertiesFile)) {

				_datasourceProperties.load(inputStream);
			}
			catch (IOException ioe) {
				System.err.println("Could not load server.properties");
			}
		}

		_jvmOpts = jvmOpts;

		_logFile = logFile;

		_serverPropertiesFile = new File("server.properties");

		_serverProperties = new Properties();

		if (_serverPropertiesFile.exists()) {
			try (
				InputStream inputStream =
					new FileInputStream(_serverPropertiesFile)) {

				_serverProperties.load(inputStream);
			}
			catch (IOException ioe) {
				System.err.println("Could not load server.properties");
			}
		}

		_upgradePropertiesFile = new File("portal-upgrade-ext.properties");

		_upgradeProperties = new Properties();

		if (_upgradePropertiesFile.exists()) {
			try (
				InputStream inputStream =
					new FileInputStream(_upgradePropertiesFile)) {

				_upgradeProperties.load(inputStream);
			}
			catch (IOException ioe) {
				System.err.println(
					"Could not load portal-upgrade-ext.properties");
			}
		}
	}

	public void upgrade() throws Exception {
		verifyProperties();

		System.setOut(
			new TeePrintStream(new FileOutputStream(_logFile), System.out));

		String classPath = _getClassPath();

		List<String> commands = new ArrayList<>();

		if (JAVA_HOME != null) {
			commands.add(JAVA_HOME + "/bin/java");
		}
		else {
			commands.add("java");
		}

		commands.addAll(Arrays.asList(_jvmOpts.split(" ")));
		commands.add("-Dexternal-properties=portal-upgrade.properties");
		commands.add("-cp");
		commands.add(classPath);
		commands.add("com.liferay.portal.tools.DBUpgrader");

		ProcessBuilder processBuilder = new ProcessBuilder();

		processBuilder.redirectErrorStream(true);

		processBuilder.command(commands);

		Process process = processBuilder.start();

		try (InputStreamReader isr = new InputStreamReader(
				process.getInputStream());
			BufferedReader br = new BufferedReader(isr)) {

			String line;

			while ((line = br.readLine()) != null) {
				if (line.equals(
						"Running modules upgrades. Connect to your Gogo " +
							"Shell to check the status.")) {

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
			try (GogoTelnetClient client = new GogoTelnetClient()) {
				System.out.println("You are now connected to Gogo Shell");

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
						System.out.println(client.send(line));
					}
				}

				System.out.print(
					"Making sure all upgrades steps have been completed");

				String upgradeSteps = client.send(
					"upgrade:list | grep Registered | grep step");

				upgrading = upgradeSteps.contains("true");

				if (upgrading) {
					System.out.println(
						"...one of your upgrades is still running or failed");
					System.out.println("Are you sure you want to exit (y/N)?");

					_consoleReader.setPrompt("");

					String response = _consoleReader.readLine();

					if (response.equals("y")) {
						upgrading = false;
					}
				}
				else {
					System.out.println("...done.");
				}
			}
			catch (Exception e) {
				upgrading = false;
			}
		}

		System.out.println("Exiting Gogo Shell");

		process.getInputStream().close();
		process.getOutputStream().close();
		process.getErrorStream().close();

		process.destroy();
	}

	public void verifyProperties() {
		try {
			_verifyDatasourceProperties();

			_verifyServerProperties();

			_verifyUpgradeProperties();

			_saveProperties();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private static Options _getOptions() {
		Options options = new Options();

		options.addOption(new Option("h", "help", false, "print this message"));
		options.addOption(
			new Option(
				"j", "jvmOpts", true, "set the JVM_OPTS of the upgrade"));
		options.addOption(new Option("l", "logFile", true, "name of log file"));

		return options;
	}

	private String _getClassPath() throws Exception {
		StringBuilder classPath = new StringBuilder();

		String liferayClassPath = System.getenv("LIFERAY_CLASSPATH");

		if ((liferayClassPath != null) && !liferayClassPath.equals("")) {
			classPath.append(liferayClassPath);

			classPath.append(File.pathSeparator);
		}

		_getLibs(classPath, new File("lib"));

		_getLibs(classPath, new File("."));

		_getLibs(classPath, new File(_appServer.getDir(), "bin"));

		_getLibs(classPath, _appServer.getGlobalLibDir());

		classPath.append(_appServer.getPortalClassesDir().getCanonicalPath());

		_getLibs(classPath, _appServer.getPortalLibDir());

		return classPath.toString();
	}

	private void _getLibs(StringBuilder classPath, File dir) throws Exception {
		if (dir.exists() && dir.isDirectory()) {
			for (File lib : dir.listFiles()) {
				String fileName = lib.getName();

				if (lib.isFile() && fileName.endsWith("jar")) {
					classPath.append(
						lib.getCanonicalPath() + File.pathSeparator);
				}
				else if (lib.isDirectory()) {
					_getLibs(classPath, lib);
				}
			}
		}
	}

	private String _getRelativePath(File base, File path) {
		return _getRelativePath(base.toPath(), path.toPath());
	}

	private String _getRelativePath(Path base, Path path) {
		Path relative = base.relativize(path);

		return relative.toString();
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

	private void _saveProperties() throws IOException {
		_store(_datasourceProperties, _datasourcePropertiesFile);

		_store(_serverProperties, _serverPropertiesFile);

		_store(_upgradeProperties, _upgradePropertiesFile);
	}

	private void _store(Properties props, File propertyFile)
		throws FileNotFoundException {

		try (PrintWriter pw = new PrintWriter(propertyFile)) {
			for (Enumeration e = props.propertyNames(); e.hasMoreElements();) {
				String key = (String)e.nextElement();
				pw.println(key + "=" + props.getProperty(key));
			}
		}
	}

	private void _verifyDatasourceProperties() throws IOException {
		String value = _datasourceProperties.getProperty(
			"jdbc.default.driverClassName");

		if ((value == null) || value.equals("")) {
			String response;

			Datasource datasource = null;

			while (datasource == null) {
				System.out.print("[ ");

				for (String name : _datasources.keySet()) {
					System.out.print(name + " ");
				}

				System.out.println("]");

				System.out.println("Please enter your database (mysql): ");

				response = _consoleReader.readLine();

				if (response.equals("")) {
					response = "mysql";
				}

				datasource = _datasources.get(response);

				if (datasource == null) {
					System.err.println(
						response + " is not a supported database");
				}
			}

			System.out.println(
				"Please enter your database JDBC driver protocol (" +
					datasource.getProtocol() + "): ");

			response = _consoleReader.readLine();

			if (!response.equals("")) {
				datasource.setProtocol(response);
			}

			System.out.println(
				"Please enter your database JDBC driver class name(" +
					datasource.getClassName() + "): ");

			response = _consoleReader.readLine();

			if (!response.equals("")) {
				datasource.setClassName(response);
			}

			System.out.println(
				"Please enter your database host (" + datasource.getHost() +
					"): ");

			response = _consoleReader.readLine();

			if (!response.equals("")) {
				datasource.setHost(response);
			}

			System.out.println(
				"Please enter your database port (" +
					(datasource.getPort() > 0 ?
						datasource.getPort() : "none") + "): ");

			response = _consoleReader.readLine();

			if (!response.equals("")) {
				if (response.equals("none")) {
					datasource.setPort(0);
				}
				else {
					datasource.setPort(Integer.parseInt(response));
				}
			}

			System.out.println(
				"Please enter your database name (" +
					datasource.getDatabaseName() + "): ");

			response = _consoleReader.readLine();

			if (!response.equals("")) {
				datasource.setDatabaseName(response);
			}

			System.out.println("Please enter your database username: ");

			String username = _consoleReader.readLine();

			System.out.println("Please enter your database password: ");

			String password = _consoleReader.readLine();

			_datasourceProperties.setProperty(
				"jdbc.default.driverClassName", datasource.getClassName());
			_datasourceProperties.setProperty(
				"jdbc.default.url", datasource.getUrl());
			_datasourceProperties.setProperty(
				"jdbc.default.username", username);
			_datasourceProperties.setProperty(
				"jdbc.default.password", password);
		}
	}

	private void _verifyServerProperties() throws IOException {
		String value = _serverProperties.getProperty("dir");

		if ((value == null) || value.equals("")) {
			String response;

			while (_appServer == null) {
				System.out.print("[ ");

				for (String appServer : _appServers.keySet()) {
					System.out.print(appServer + " ");
				}

				System.out.println("]");

				System.out.println("Please enter your app server (tomcat): ");

				response = _consoleReader.readLine();

				if (response.equals("")) {
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

			if (!response.equals("")) {
				_appServer.setDir(response);
			}

			System.out.println(
				"Please enter your global lib dir (" + globalLibDir + "): ");

			response = _consoleReader.readLine();

			if (!response.equals("")) {
				_appServer.setGlobalLibDir(response);
			}

			System.out.println(
				"Please enter your portal dir (" + portalDir + "): ");

			response = _consoleReader.readLine();

			if (!response.equals("")) {
				_appServer.setPortalDir(response);
			}

			_serverProperties.setProperty("dir", dir.getCanonicalPath());
			_serverProperties.setProperty(
				"global.dir.lib", _getRelativePath(dir, globalLibDir));
			_serverProperties.setProperty(
				"portal.dir", _getRelativePath(dir, portalDir));
		}
		else {
			_appServer = new AppServer(
				value, _serverProperties.getProperty("global.dir.lib"),
				_serverProperties.getProperty("portal.dir"));
		}
	}

	private void _verifyUpgradeProperties() throws IOException {
		String value = _upgradeProperties.getProperty("liferay.home");

		if ((value == null) || value.equals("")) {
			System.out.println("Please enter your Liferay home (../): ");

			String response = _consoleReader.readLine();

			if (response.equals("")) {
				response = "../";
			}

			File liferayHome = new File(response);

			_upgradeProperties.setProperty(
				"liferay.home", liferayHome.getCanonicalPath());
		}
	}

	private static final Map<String, AppServer> _appServers =
		new LinkedHashMap<>();
	private static final Map<String, Datasource> _datasources =
		new LinkedHashMap<>();

	static {
		_appServers.put("jboss", AppServer.getJBossEAP());
		_appServers.put("jonas", AppServer.getJOnAS());
		_appServers.put("resin", AppServer.getResin());
		_appServers.put("tcserver", AppServer.getTCServer());
		_appServers.put("tomcat", AppServer.getTomcat());
		_appServers.put("weblogic", AppServer.getWeblogic());
		_appServers.put("websphere", AppServer.getWebsphere());
		_appServers.put("wildfly", AppServer.getWildfly());

		_datasources.put("db2", Datasource.getDB2Connection());
		_datasources.put("mariadb", Datasource.getMariaDBConnection());
		_datasources.put("mysql", Datasource.getMySQLConnection());
		_datasources.put("oracle", Datasource.getOracleConnection());
		_datasources.put("postgresql", Datasource.getPostgreSQLConnection());
		_datasources.put("sqlserver", Datasource.getSQLServerConnection());
		_datasources.put("sybase", Datasource.getSybaseConnection());
	}

	private AppServer _appServer;
	private final ConsoleReader _consoleReader;
	private final Properties _datasourceProperties;
	private final File _datasourcePropertiesFile;
	private final String _jvmOpts;
	private final File _logFile;
	private final Properties _serverProperties;
	private final File _serverPropertiesFile;
	private final Properties _upgradeProperties;
	private final File _upgradePropertiesFile;

}