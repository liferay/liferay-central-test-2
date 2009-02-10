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

package com.liferay.portal.tools.samplesqlbuilder;

import com.liferay.counter.model.Counter;
import com.liferay.portal.freemarker.FreeMarkerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.tools.sql.DBUtil;
import com.liferay.portal.util.InitUtil;
import com.liferay.util.SimpleCounter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.Writer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="SampleSQLBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SampleSQLBuilder {

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		String outputDir = System.getProperty("sample.sql.output.dir");
		int maxUserCount = GetterUtil.getInteger(
			System.getProperty("sample.sql.max.user.count"));

		new SampleSQLBuilder(outputDir, maxUserCount);
	}

	public SampleSQLBuilder(String outputDir, int maxUserCount) {
		try {
			_maxUserCount = maxUserCount;

			_mysqlWriter = new FileWriter(new File(
				outputDir +  "/sample-mysql.sql"));

			_mysqlDBUtil = DBUtil.getInstance(DBUtil.TYPE_MYSQL);

			createClassNames();
			createResourceCodes();
			createCompany();
			createRoles();
			createGroups();
			createOrganizations();
			createUsers();
			createCounters();

			_mysqlWriter.write(_mysqlDBUtil.buildSQL("COMMIT_TRANSACTION"));

			_mysqlWriter.flush();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void createClassName(long classNameId, String value)
		throws Exception {

		Map<String, Object> context = getContext();

		put(context, "classNameId", classNameId);
		put(context, "value", value);

		processTemplate(_tplClassName, context);
	}

	protected void createClassNames() throws Exception {
		createClassName(_organizationClassNameId, Organization.class.getName());
		createClassName(_userClassNameId, User.class.getName());

		write(StringPool.NEW_LINE);
	}

	protected void createCompany() throws Exception {
		processTemplate(_tplCompany, getContext());

		write(StringPool.NEW_LINE);
	}

	protected void createCounter(long currentId, String name) throws Exception {
		Map<String, Object> context = getContext();

		put(context, "currentId", currentId);
		put(context, "name", name);

		processTemplate(_tplCounter, context);

		write(StringPool.NEW_LINE);
	}

	protected void createCounters() throws Exception {
		createCounter(_counter.get(), Counter.class.getName());
		createCounter(_permissionCounter.get(), Permission.class.getName());
		createCounter(_resourceCounter.get(), Resource.class.getName());
		createCounter(_resourceCodeCounter.get(), ResourceCode.class.getName());

		write(StringPool.NEW_LINE);
	}

	protected void createGroup(String friendlyURL, long groupId, String name)
		throws Exception {

		Map<String, Object> context = getContext();

		put(context, "friendlyURL", friendlyURL);
		put(context, "groupId", groupId);
		put(context, "name", name);

		processTemplate(_tplGroup, context);
	}

	protected void createGroups() throws Exception {
		createGroup("/guest", _guestGroupId, GroupConstants.GUEST);

		write(StringPool.NEW_LINE);
	}

	protected void createOrganization(
			String friendlyURL, long groupId, String name, long organizationId,
			long parentOrganizationId, String type)
		throws Exception {

		Map<String, Object> context = getContext();

		put(context, "friendlyURL", friendlyURL);
		put(context, "groupId", groupId);
		put(context, "name", name);
		put(context, "organizationId", organizationId);
		put(context, "parentOrganizationId", parentOrganizationId);
		put(context, "type", type);

		processTemplate(_tplOrganization, context);
	}

	protected void createOrganizations() throws Exception {
		createOrganization(
			"/liferayinc", _counter.get(), "Liferay, Inc.", _counter.get(), -1,
			"regular-organization");

		write(StringPool.NEW_LINE);
	}

	protected void createPermission(
			String actionId, long permissionId, long resourceId)
		throws Exception {

		Map<String, Object> context = getContext();

		put(context, "actionId", actionId);
		put(context, "permissionId", permissionId);
		put(context, "resourceId", resourceId);

		processTemplate(_tplPermission, context);
	}

	protected void createResource(long codeId, String primKey, long resourceId)
		throws Exception {

		Map<String, Object> context = getContext();

		put(context, "codeId", codeId);
		put(context, "primKey", primKey);
		put(context, "resourceId", resourceId);

		processTemplate(_tplResource, context);
	}

	protected void createResourceCode(long codeId, String name, int scope)
		throws Exception {

		Map<String, Object> context = getContext();

		put(context, "codeId", codeId);
		put(context, "name", name);
		put(context, "scope", scope);

		processTemplate(_tplResourceCode, context);
	}

	protected void createResourceCodes() throws Exception {
		createResourceCode(
			_userCompanyResourceCodeId, User.class.getName(),
			ResourceConstants.SCOPE_COMPANY);
		createResourceCode(
			_userIndividualResourceCodeId, User.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL);

		write(StringPool.NEW_LINE);
	}

	protected void createRole(String name, long roleId) throws Exception {
		Map<String, Object> context = getContext();

		put(context, "name", name);
		put(context, "roleId", roleId);

		processTemplate(_tplRole, context);
	}

	protected void createRoles() throws Exception {
		createRole(RoleConstants.ADMINISTRATOR, _administratorRoleId);
		createRole(RoleConstants.GUEST, _guestRoleId);
		createRole(RoleConstants.POWER_USER, _powerUserRoleId);
		createRole(RoleConstants.USER, _userRoleId);

		write(StringPool.NEW_LINE);
	}

	protected void createUser(
			long contactId, String emailAddress, String firstName, long groupId,
			String lastName, String screenName, List<Group> userGroups,
			long userId, List<Organization> userOrganizations,
			List<Role> userRoles)
		throws Exception {

		String userName = firstName + StringPool.SPACE + lastName;

		if (Validator.isNull(firstName) && Validator.isNull(lastName)) {
			userName = StringPool.BLANK;
		}

		String userUuid = PortalUUIDUtil.generate();

		Map<String, Object> context = getContext();

		put(context, "contactId", contactId);
		put(context, "emailAddress", emailAddress);
		put(context, "firstName", firstName);
		put(context, "friendlyURL", "/" + screenName);

		if (groupId > 0) {
			put(context, "groupId", groupId);
		}

		put(context, "lastName", lastName);
		put(context, "screenName", screenName);
		put(context, "userClassNameId", _userClassNameId);
		put(context, "userGroups", userGroups);
		put(context, "userId", userId);
		put(context, "userName", userName);
		put(context, "userOrganizations", userOrganizations);
		put(context, "userRoles", userRoles);
		put(context, "userUuid", userUuid);

		processTemplate(_tplUser, context);

		write(StringPool.NEW_LINE);

		long resourceId = _resourceCounter.get();

		createResource(
			_userIndividualResourceCodeId, String.valueOf(userId), resourceId);

		write(StringPool.NEW_LINE);

		String[] actionIds = new String[] {
			ActionKeys.DELETE, ActionKeys.IMPERSONATE, ActionKeys.PERMISSIONS,
			ActionKeys.UPDATE, ActionKeys.VIEW
		};

		for (String actionId : actionIds) {
			createPermission(actionId, _permissionCounter.get(), resourceId);
		}
	}

	protected void createUsers() throws Exception {
		createUser(
			_defaultContactId, "default@liferay.com", StringPool.BLANK, 0,
			StringPool.BLANK, String.valueOf(_defaultUserId), null,
			_defaultUserId, null, null);

		write(StringPool.NEW_LINE);

		String dependenciesDir =
			"../portal-impl/src/com/liferay/portal/tools/samplesqlbuilder/" +
				"dependencies/";

		List<String> firstNames = ListUtil.fromFile(
			dependenciesDir + "first_names.txt");
		List<String> lastNames = ListUtil.fromFile(
			dependenciesDir + "last_names.txt");

		List<Group> userGroups = new ArrayList<Group>();

		Group group = new GroupImpl();

		group.setGroupId(_guestGroupId);
		group.setName(GroupConstants.GUEST);

		userGroups.add(group);

		List<Role> userRoles = new ArrayList<Role>();

		Role role = new RoleImpl();

		role.setRoleId(_userRoleId);
		role.setName(RoleConstants.USER);

		userRoles.add(role);

		int userCount = 0;

		for (String lastName : lastNames) {
			for (String firstName : firstNames) {
				userCount++;

				long contactId = _counter.get();
				long groupId = _counter.get();
				long userId = _counter.get();

				createUser(
					contactId, userId + "@liferay.com", firstName, groupId,
					lastName, String.valueOf(userId), userGroups, userId, null,
					userRoles);

				write(StringPool.NEW_LINE);

				if (userCount >= _maxUserCount) {
					break;
				}
			}

			if (userCount >= _maxUserCount) {
				break;
			}
		}
	}

	protected Map<String, Object> getContext() {
		Map<String, Object> context = new HashMap<String, Object>();

		put(context, "accountId", _accountId);
		put(context, "administratorRoleId", _administratorRoleId);
		put(context, "counter", _counter);
		put(context, "defaultContactId", _defaultContactId);
		put(context, "defaultUserId", _defaultUserId);
		put(context, "companyId", _companyId);
		put(context, "guestGroupId", _guestGroupId);
		put(context, "guestRoleId", _guestRoleId);
		put(context, "organizationClassNameId", _organizationClassNameId);
		put(context, "powerUserRoleId", _powerUserRoleId);
		put(context, "userClassNameId", _userClassNameId);
		put(context, "userRoleId", _userRoleId);

		return context;
	}

	protected void processTemplate(String name, Map<String, Object> context)
		throws Exception {

		String sql = FreeMarkerUtil.process(name, context);

		String mysqlSQL = _mysqlDBUtil.buildSQL(sql);

		boolean previousBlankLine = false;

		BufferedReader br = new BufferedReader(new StringReader(mysqlSQL));

		String s = null;

		while ((s = br.readLine()) != null) {
			s = s.trim();

			_mysqlWriter.write(s);

			if (previousBlankLine && Validator.isNull(s)) {
			}
			else {
				write(StringPool.NEW_LINE);
			}

			if (Validator.isNull(s)) {
				previousBlankLine = true;
			}
		}

		br.close();
	}

	protected void put(Map<String, Object> context, String key, boolean value) {
		context.put(key, String.valueOf(value));
	}

	protected void put(Map<String, Object> context, String key, int value) {
		context.put(key, String.valueOf(value));
	}

	protected void put(Map<String, Object> context, String key, long value) {
		context.put(key, String.valueOf(value));
	}

	protected void put(Map<String, Object> context, String key, Object value) {
		context.put(key, value);
	}

	protected void put(Map<String, Object> context, String key, String value) {
		context.put(key, value);
	}

	protected void write(String s) throws Exception {
		_mysqlWriter.write(s);
	}

	private static final String _TPL_ROOT =
		"com/liferay/portal/tools/samplesqlbuilder/dependencies/";

	private String _tplClassName = _TPL_ROOT + "class_name.ftl";
	private String _tplCompany = _TPL_ROOT + "company.ftl";
	private String _tplCounter = _TPL_ROOT + "counter.ftl";
	private String _tplGroup = _TPL_ROOT + "group.ftl";
	private String _tplOrganization = _TPL_ROOT + "organization.ftl";
	private String _tplPermission = _TPL_ROOT + "permission.ftl";
	private String _tplResource = _TPL_ROOT + "resource.ftl";
	private String _tplResourceCode = _TPL_ROOT + "resource_code.ftl";
	private String _tplRole = _TPL_ROOT + "role.ftl";
	private String _tplUser = _TPL_ROOT + "user.ftl";
	private Writer _mysqlWriter;
	private DBUtil _mysqlDBUtil;
	private int _maxUserCount;
	private SimpleCounter _counter = new SimpleCounter();
	private SimpleCounter _permissionCounter = new SimpleCounter();
	private SimpleCounter _resourceCodeCounter = new SimpleCounter();
	private SimpleCounter _resourceCounter = new SimpleCounter();
	private long _accountId = _counter.get();
	private long _administratorRoleId = _counter.get();
	private long _defaultContactId = _counter.get();
	private long _defaultUserId = _counter.get();
	private long _companyId = _counter.get();
	private long _guestGroupId = _counter.get();
	private long _guestRoleId = _counter.get();
	private long _organizationClassNameId = _counter.get();
	private long _powerUserRoleId = _counter.get();
	private long _userClassNameId = _counter.get();
	private long _userCompanyResourceCodeId = _resourceCodeCounter.get();
	private long _userIndividualResourceCodeId = _resourceCodeCounter.get();
	private long _userRoleId = _counter.get();

}