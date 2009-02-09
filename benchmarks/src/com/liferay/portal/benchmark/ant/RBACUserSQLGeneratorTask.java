/*
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.benchmark.ant;

import com.liferay.portal.benchmark.generator.util.DefaultIDGenerator;
import com.liferay.portal.benchmark.generator.util.IDGenerator;
import com.liferay.portal.benchmark.model.builder.ModelBuilderConstants;
import com.liferay.portal.benchmark.model.builder.ModelBuilderContext;
import com.liferay.portal.benchmark.model.builder.RBACUserModelBuilder;
import com.liferay.portal.freemarker.FreeMarkerUtil;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

/**
 * <a href="CreateUserTask.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class RBACUserSQLGeneratorTask extends Task {

	public void execute() throws BuildException {
		super.execute();
		initialize();
		log("Generating " + _numUsers + " users for " + _version + " on " +
				_database);
		Writer sqlWriter = null;
		Writer testLoginWriter = null;
		try {
			sqlWriter = new BufferedWriter(new FileWriter(_sqlOutput));
			testLoginWriter =
					new BufferedWriter(new FileWriter(_testLoginOutput));
			int counter = 0;
			ModelBuilderContext context = new ModelBuilderContext();
			for (String lastName : _lastNames) {
				for (String firstName : _firstNames) {
					context.put(ModelBuilderConstants.FIRST_NAME_KEY, firstName);
					context.put(ModelBuilderConstants.LAST_NAME_KEY, lastName);
					context.put(ModelBuilderConstants.PASSWORD_KEY,
								_defaultPassword);
				   	context.put(ModelBuilderConstants.DOMAIN_KEY, _domain);
					Map<String, Object> templateContext = 
							_builder.createProducts(context);
					FreeMarkerUtil.process(_createUserSQLTemplate,
										   templateContext, sqlWriter);
					FreeMarkerUtil.process(_userListTemplate,
										   templateContext, testLoginWriter);
					counter++;
					if (counter >= _numUsers) {
						return;
					}
					testLoginWriter.flush();
					sqlWriter.flush();
				}
			}
			Map<String, Object> counters = new HashMap<String, Object>();
			counters.put("counters", _idGenerator.report());
			FreeMarkerUtil.process(_updateIdTemplate, counters, sqlWriter);
			sqlWriter.flush();

		} catch (Exception e) {
			throw new BuildException(e);
		}
		finally {
			log("Completed users.  " + _idGenerator.report());
			if (testLoginWriter != null) {
				try {
					testLoginWriter.flush();
					testLoginWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (sqlWriter != null) {
				try {
					sqlWriter.flush();
					sqlWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void initialize()
			throws BuildException {
		if (_sqlOutput == null) {
			throw new BuildException("Must specify a output file name " +
					"for the resulting user SQL file");
		}
		if (_testLoginOutput == null) {
			throw new BuildException("Must specify a output file name " +
					"for the resulting user login and password");
		}
		if (_numUsers <= 0) {
			throw new BuildException("Must specify the number of users to " +
					"createProducts for the build user SQL file");
		}
		if ((_pathToFirstNameFile == null) || _pathToFirstNameFile.equals("")) {
			throw new BuildException("Must specify path to file containining " +
					"first names");
		}
		if ((_pathToLastNameFile == null) || _pathToLastNameFile.equals("")) {
			throw new BuildException("Must specify path to file containining " +
					"last names");
		}

		_createUserSQLTemplate =
				_templatePrefix + "/db/" + _database + "/" + _version + "/" +
						"create_user_rbac.ftl";
		_createUserSQLTemplate =
				_templatePrefix + "/" + _database + "/" + "user_list.ftl";
		_updateIdTemplate =
				_templatePrefix + "/db/" + _database + "/" + "update_counters.ftl";


		_idGenerator = new DefaultIDGenerator();
		_builder = new RBACUserModelBuilder();
		_builder.setCompanyId(_companyId);
		_builder.setIdGenerator(_idGenerator);
		_builder.setOwnerName(_ownerUserName);
		_builder.setOwnerId(_ownerId);

		_firstNames = new HashSet<String>();
		_lastNames = new HashSet<String>();

		BufferedReader firstNameReader = null;
		try {
			firstNameReader =
					new BufferedReader(new FileReader(_pathToFirstNameFile));
			String value;
			while ((value = firstNameReader.readLine()) != null) {
				_firstNames.add(value.trim().toLowerCase());
			}
		}
		catch (Exception e) {
			throw new BuildException("Unable to read " + _pathToFirstNameFile, e);
		} finally {
			try {
				if (firstNameReader != null) {
					firstNameReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		BufferedReader lastNameReader = null;
		try {
			lastNameReader =
					new BufferedReader(new FileReader(_pathToLastNameFile));
			String value;
			while ((value = lastNameReader.readLine()) != null) {
				_lastNames.add(value.trim().toLowerCase());
			}
		}
		catch (Exception e) {
			throw new BuildException("Unable to read " + _pathToLastNameFile, e);
		} finally {
			try {
				if (lastNameReader != null) {
					lastNameReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setPathToFirstNameFile(String pathToFirstNameFile) {
		_pathToFirstNameFile = pathToFirstNameFile;
	}

	public void setPathToLastNameFile(String pathToLastNameFile) {
		_pathToLastNameFile = pathToLastNameFile;
	}

	public void setSqlOutput(String sqlOutput) {
		_sqlOutput = sqlOutput;
	}

	public void setTemplatePrefix(String templatePrefix) {
		_templatePrefix = templatePrefix;
	}

	public void setVersion(String version) {
		_version = version;
	}

	public void setNumUsers(int numUsers) {
		_numUsers = numUsers;
	}

	public void setDatabase(String database) {
		_database = database;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setDomain(String domain) {
		_domain = domain;
	}

	public void setDefaultPassword(String defaultPassword) {
		_defaultPassword = defaultPassword;
	}

	public void setOwnerId(long ownerId) {
		_ownerId = ownerId;
	}

	public void setOwnerUserName(String ownerUserName) {
		_ownerUserName = ownerUserName;
	}

	private String _sqlOutput;

	public void setTestLoginOutput(String testLoginOutput) {
		_testLoginOutput = testLoginOutput;
	}

	private String _testLoginOutput;
	private int _numUsers;
	private String _pathToFirstNameFile;
	private String _pathToLastNameFile;

	private String _templatePrefix =
			"com/liferay/portal/benchmark/generator/ftl";
	private String _version = "5_1";
	private String _database = "mysql";
	private long _companyId = 10106;
	private String _domain = "liferay.com";
	private String _defaultPassword = "test";
	private long _ownerId = 10127;
	private String _ownerUserName = "Test test";
	private IDGenerator _idGenerator;
	private Set<String> _firstNames;
	private Set<String> _lastNames;
	private RBACUserModelBuilder _builder;
	private String _createUserSQLTemplate;
	private String _userListTemplate;
	private String _updateIdTemplate;
}
