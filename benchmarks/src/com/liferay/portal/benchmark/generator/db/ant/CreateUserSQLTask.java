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

package com.liferay.portal.benchmark.generator.db.ant;

import com.liferay.portal.benchmark.generator.db.CreateUserSQLGenerator;
import com.liferay.portal.benchmark.generator.db.DefaultIDGenerator;
import com.liferay.portal.benchmark.generator.db.IDGenerator;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

/**
 * <a href="CreateUserTask.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class CreateUserSQLTask extends Task {

	public static void main(String[] args) {
		CreateUserSQLTask task = new CreateUserSQLTask();
		task.setNumUsers(Integer.parseInt(args[0]));
		task.setSqlOutput("c:/temp/load_user.sql");
		task.setTestLoginOutput("c:/temp/user_logins.csv");
		task.setPathToFirstNameFile("/projects/liferay/trunk/portal/benchmarks/data/user/first_names.txt");
		task.setPathToLastNameFile("/projects/liferay/trunk/portal/benchmarks/data/user/last_names.txt");
		task.init();
		task.execute();
	}

	public void execute() throws BuildException {
		super.execute();
		log("Generating " + _numUsers + " users for " + _version + " on " +
				_database);
		Writer sqlWriter = null;
		Writer testLoginWriter = null;
		try {
			sqlWriter = new BufferedWriter(new FileWriter(_sqlOutput));
			testLoginWriter =
					new BufferedWriter(new FileWriter(_testLoginOutput));
			int counter = 0;
			for (String lastName : _lastNames) {
				for (String firstName : _firstNames) {
					_generator.generate(sqlWriter, testLoginWriter,
										firstName, lastName,
										_addUserGroupRole,
										_userGroupId, _userRoleId);
					counter++;
					if (counter >= _numUsers) {
						return;
					}
					testLoginWriter.flush();
					sqlWriter.flush();
				}
			}
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

	public void init()
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
					"generate for the build user SQL file");
		}
		if ((_pathToFirstNameFile == null) || _pathToFirstNameFile.equals("")) {
			throw new BuildException("Must specify path to file containining " +
					"first names");
		}
		if ((_pathToLastNameFile == null) || _pathToLastNameFile.equals("")) {
			throw new BuildException("Must specify path to file containining " +
					"last names");
		}
		_idGenerator = new DefaultIDGenerator();
		_generator =
				new CreateUserSQLGenerator(
						_templatePrefix + "/" + _version + "/" + _database,
						_templatePrefix,
						_idGenerator, _companyId,
						_domain, _defaultPassword, _defaultUserId,
						_defaultUserName);
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

	public void setDefaultUserId(long defaultUserId) {
		_defaultUserId = defaultUserId;
	}

	public void setDefaultUserName(String defaultUserName) {
		_defaultUserName = defaultUserName;
	}

	public void setAddUserGroupRole(boolean addUserGroupRole) {
		_addUserGroupRole = addUserGroupRole;
	}

	public void setUserGroupId(long userGroupId) {
		_userGroupId = userGroupId;
	}

	public void setUserRoleId(long userRoleId) {
		_userRoleId = userRoleId;
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
			"com/liferay/portal/benchmark/generator/db/ftl";
	private String _version = "5_1";
	private String _database = "mysql";
	private long _companyId = 10106;
	private String _domain = "liferay.com";
	private String _defaultPassword = "test";
	private long _defaultUserId = 10127;
	private String _defaultUserName = "Test test";
	private boolean _addUserGroupRole;
	private long _userGroupId;
	private long _userRoleId;
	private CreateUserSQLGenerator _generator;
	private IDGenerator _idGenerator;
	private Set<String> _firstNames;
	private Set<String> _lastNames;
}
