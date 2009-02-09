/*
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

package com.liferay.portal.benchmark.model.builder;

import com.liferay.portal.benchmark.generator.util.IDGenerator;
import com.liferay.portal.benchmark.generator.util.ResourceAction;
import com.liferay.portal.benchmark.generator.util.ResourceCodes;
import com.liferay.portal.benchmark.model.ClassName;
import com.liferay.portal.benchmark.model.Contact;
import com.liferay.portal.benchmark.model.Group;
import com.liferay.portal.benchmark.model.LayoutSet;
import com.liferay.portal.benchmark.model.Permission;
import com.liferay.portal.benchmark.model.Resource;
import com.liferay.portal.benchmark.model.Role;
import com.liferay.portal.benchmark.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="UserBuilder.java.html"><b><i>View Source</i></b></a>
 *
 * TBD - This does not accomodate for organizations, org roles,
 * communities, and community roles yet
 *
 * @author Michael C. Han
 */
public class RBACUserModelBuilder {

	public Map<String, Object> createProducts(ModelBuilderContext context) {

		addContact(context.getString(ModelBuilderConstants.FIRST_NAME_KEY),
				   context.getString(ModelBuilderConstants.LAST_NAME_KEY));
		addUser(context.getString(ModelBuilderConstants.PASSWORD_KEY),
				   context.getString(ModelBuilderConstants.DOMAIN_KEY));
		addUserPrivateGroup();
		addPublicLayoutSet();
		addPrivateLayoutSet();
		addRoles((List<Role>)context.get(ModelBuilderConstants.ROLES_KEY));
		addSecurityACL();

		HashMap<String, Object> hash = new HashMap<String, Object>();
		hash.put(ModelBuilderConstants.USER_KEY, _user);
		hash.put(ModelBuilderConstants.RESOURCE_KEY, _resource);
		hash.put(ModelBuilderConstants.PERMISSIONS_KEY, _permissions);
		return hash;
	}

	public User getUser() {
		return _user;
	}

	public List<Permission> getPermissions() {
		return _permissions;
	}

	public Resource getResource() {
		return _resource;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setIdGenerator(IDGenerator generator) {
		_generator = generator;
	}

	public void setOwnerId(long userId) {
		_ownerId = userId;
	}

	public void setOwnerName(String userName) {
		_ownerName = userName;
	}

	public void addContact(String firstName, String lastName) {
		validate();
		_contact =
				new Contact(_generator.generate(), _companyId,
							_ownerId, _ownerName,
							firstName, lastName);
	}

	public void addUser(String password, String domain) {
		validate();
		if (_contact == null) {
			throw new IllegalStateException("Must create a contact first");
		}
		_user =
				new User(_companyId, _generator.generate(),
						 createScreenName(_contact.getFirstName(),
										  _contact.getLastName()),
						 createEmail(_contact.getFirstName(),
									 _contact.getLastName(),
									 domain),
						 password, _contact);
	}

	public void addUserPrivateGroup() {
		validate();
		if (_user == null) {
			throw new IllegalStateException("Must create a user first");
		}
		_user.setPrivateGroup(new Group(_generator.generate(), _companyId,
										_user.getUserId(),
										ClassName.USER.getClassNameId(),
										_user.getUserId()));
	}

	public void addPublicLayoutSet() {
		addPublicLayoutSet(_DEFAULT_THEME_ID, _DEFAULT_COLOR_SCHEME_ID,
						   _DEFAULT_WAP_ID, _DEFAULT_WAP_COLOR_SCHEME_ID);
	}

	public void addPublicLayoutSet(String themeId, String themeColorSchemeId,
								   String wapThemeId, String wapColorSchemeId) {
		validate();
		if ((_user == null) || (_user.getPrivateGroup() == null)) {
			throw new IllegalStateException(
					"Must create a user and private group first");
		}
		_user.setPublicLayoutSet(
				new LayoutSet(_generator.generate(),
							  _user.getPrivateGroup().getGroupId(),
							  _companyId,
							  false, themeId, themeColorSchemeId,
							  wapThemeId, wapColorSchemeId));
	}

	public void addPrivateLayoutSet() {
		addPrivateLayoutSet(_DEFAULT_THEME_ID, _DEFAULT_COLOR_SCHEME_ID,
							_DEFAULT_WAP_ID, _DEFAULT_WAP_COLOR_SCHEME_ID);
	}

	public void addPrivateLayoutSet(String themeId, String themeColorSchemeId,
									String wapThemeId,
									String wapColorSchemeId) {
		validate();
		if ((_user == null) || (_user.getPrivateGroup() == null)) {
			throw new IllegalStateException(
					"Must create a user and private group first");
		}
		_user.setPrivateLayoutSet(
				new LayoutSet(_generator.generate(),
							  _user.getPrivateGroup().getGroupId(),
							  _companyId,
							  true, themeId, themeColorSchemeId,
							  wapThemeId, wapColorSchemeId));
	}

	public void addRoles(List<Role> roles) {
		_user.addRoles(roles);
	}

	public void addSecurityACL() {
		_resource =
				new Resource(_generator.generate(IDGenerator.RESOURCE),
							 ResourceCodes.USER_4.getCodeId(),
							 String.valueOf(_user.getUserId()));

		_permissions = new ArrayList<Permission>();
		_permissions.add(
				new Permission(_generator.generate(IDGenerator.PERMISSION),
							   _companyId, ResourceAction.DELETE.name(),
							   _resource.getResourceId()));
		_permissions.add(
				new Permission(_generator.generate(IDGenerator.PERMISSION),
							   _companyId, ResourceAction.IMPERSONATE.name(),
							   _resource.getResourceId()));
		_permissions.add(
				new Permission(_generator.generate(IDGenerator.PERMISSION),
							   _companyId, ResourceAction.PERMISSIONS.name(),
							   _resource.getResourceId()));
		_permissions.add(
				new Permission(_generator.generate(IDGenerator.PERMISSION),
							   _companyId, ResourceAction.UPDATE.name(),
							   _resource.getResourceId()));
		_permissions.add(
				new Permission(_generator.generate(IDGenerator.PERMISSION),
							   _companyId, ResourceAction.VIEW.name(),
							   _resource.getResourceId()));
	}

	private void validate() {
		if (_companyId == 0) {
			throw new IllegalStateException("Must initialize with companyId");
		}
		if (_generator == null) {
			throw new IllegalStateException("Must initialize an id generator");
		}
	}

	private String createScreenName(String firstName, String lastName) {
		return firstName + lastName;
	}

	private String createEmail(String firstName, String lastName,
							   String domain) {
		return firstName + lastName + "@" + domain;
	}

	private long _companyId;
	private IDGenerator _generator;
	private long _ownerId;
	private String _ownerName = "Test test";
	private Contact _contact;
	private User _user;
	private List<Permission> _permissions;
	private Resource _resource;
	private static final String _DEFAULT_THEME_ID = "classic";
	private static final String _DEFAULT_COLOR_SCHEME_ID = "01";
	private static final String _DEFAULT_WAP_ID = "mobile";
	private static final String _DEFAULT_WAP_COLOR_SCHEME_ID = "01";
}