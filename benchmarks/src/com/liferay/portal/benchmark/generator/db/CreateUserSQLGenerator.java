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

package com.liferay.portal.benchmark.generator.db;

import com.liferay.portal.benchmark.generator.db.model.ClassName;
import com.liferay.portal.benchmark.generator.db.model.Contact;
import com.liferay.portal.benchmark.generator.db.model.Group;
import com.liferay.portal.benchmark.generator.db.model.GroupRole;
import com.liferay.portal.benchmark.generator.db.model.LayoutSet;
import com.liferay.portal.benchmark.generator.db.model.Permission;
import com.liferay.portal.benchmark.generator.db.model.Resource;
import com.liferay.portal.benchmark.generator.db.model.User;
import com.liferay.portal.benchmark.generator.db.model.common.ResourceAction;
import com.liferay.portal.benchmark.generator.db.model.common.ResourceCodes;
import com.liferay.portal.benchmark.generator.db.model.common.CommonRoles;
import com.liferay.portal.benchmark.generator.db.model.Role;
import com.liferay.portal.freemarker.FreeMarkerUtil;

import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="CreateUserSQLGenerator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class CreateUserSQLGenerator {

	public CreateUserSQLGenerator(String sqlTemplateLocation,
								  String dataTemplateLocation,
								  IDGenerator generator,
								  long companyId, String domain,
								  String defaultPassword,
								  long defaultUserId, String defaultUserName) {
		_sqlTemplateLocation = sqlTemplateLocation;
		_dataTemplateLocation = dataTemplateLocation;
		_generator = generator;
		_companyId = companyId;
		_domain = domain;
		_defaultPassword = defaultPassword;
		_defaultUserId = defaultUserId;
		_defaultUserName = defaultUserName;
	}

	public void generate(Writer sqlWriter, Writer testDataWriter,
						 String firstName, String lastName)
			throws Exception {
		generate(sqlWriter, testDataWriter, firstName, lastName,
				 false, 0, 0);
	}

	public void generate(Writer sqlWriter, Writer testDataWriter,
						 String firstName, String lastName,
						 boolean addCommunityRole, long communityId,
						 long communityRoleId)
			throws Exception {
		Map<String, Object> context = new HashMap<String, Object>();
		List<Role> roles = new ArrayList<Role>();
		//TBD we should allow dynamically setting of roles...
		roles.add(CommonRoles.POWER_USER(_companyId));
		roles.add(CommonRoles.USER(_companyId));
		User user = addRequiredUserValues(firstName, lastName, roles, context);

		if (addCommunityRole) {
			user.addCommunityRole(new GroupRole(user.getUserId(),
												communityId, communityRoleId));
		}


		FreeMarkerUtil.process(
				_sqlTemplateLocation + _CREATE_USER_TEMPLATE_FILE, context, sqlWriter);
		FreeMarkerUtil.process(
				_dataTemplateLocation + _USER_LOGINS_TEMPLATE_FILE, context, testDataWriter);
	}

	private User addRequiredUserValues(String firstName, String lastName,
									   List<Role> roles,
									   Map<String, Object> context) {
		Contact contact =
				new Contact(_generator.generate(), _companyId,
							_defaultUserId, _defaultUserName,
							firstName, lastName);
		User user = new User(_companyId, _generator.generate(),
							 createScreenName(firstName, lastName),
							 createEmail(firstName, lastName, _domain),
							 _defaultPassword, contact);
		context.put("user", user);
		user.addRoles(roles);
		Group group = new Group(_generator.generate(), _companyId,
								user.getUserId(),
								ClassName.USER.getClassNameId(),
								user.getUserId());
		user.setPrivateGroup(group);
		user.setPublicLayoutSet(new LayoutSet(_generator.generate(),
											  group.getGroupId(), _companyId,
											  false, "classic", "01",
											  "mobile", "01"));
		user.setPrivateLayoutSet(new LayoutSet(_generator.generate(),
											   group.getGroupId(), _companyId,
											   true, "classic", "01",
											   "mobile", "01"));

		Resource resource =
				new Resource(_generator.generate(IDGenerator.RESOURCE),
							 ResourceCodes.USER_4.getCodeId(),
							 String.valueOf(user.getUserId()));
		context.put("resource", resource);
		List<Permission> permissions = new ArrayList<Permission>();
		permissions.add(
				new Permission(_generator.generate(IDGenerator.PERMISSION),
							   _companyId, ResourceAction.DELETE.name(),
							   resource.getResourceId()));
		permissions.add(
				new Permission(_generator.generate(IDGenerator.PERMISSION),
							   _companyId, ResourceAction.IMPERSONATE.name(),
							   resource.getResourceId()));
		permissions.add(
				new Permission(_generator.generate(IDGenerator.PERMISSION),
							   _companyId, ResourceAction.PERMISSIONS.name(),
							   resource.getResourceId()));
		permissions.add(
				new Permission(_generator.generate(IDGenerator.PERMISSION),
							   _companyId, ResourceAction.UPDATE.name(),
							   resource.getResourceId()));
		permissions.add(
				new Permission(_generator.generate(IDGenerator.PERMISSION),
							   _companyId, ResourceAction.VIEW.name(),
							   resource.getResourceId()));
		context.put("permissions", permissions);
		return user;
	}

	private String createScreenName(String firstName, String lastName) {
		return firstName + lastName;
	}

	private String createEmail(String firstName, String lastName,
							   String domain) {
		return firstName + lastName + "@" + domain;
	}

	private static final String _CREATE_USER_TEMPLATE_FILE = "/create_user.ftl";
	private static final String _USER_LOGINS_TEMPLATE_FILE = "/user_logins.ftl";
	private String _sqlTemplateLocation;
	private String _dataTemplateLocation;
	private IDGenerator _generator;
	private long _companyId;
	private String _domain;
	private String _defaultPassword;
	private long _defaultUserId;
	private String _defaultUserName;
}
