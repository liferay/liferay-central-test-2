/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.ldap;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;

import java.io.Serializable;

import java.util.Map;
import java.util.Properties;

import javax.naming.directory.Attributes;

/**
 * <a href="PortalToLDAPConverter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public interface PortalToLDAPConverter {

	public Modifications getLDAPContactModifications(
			Contact contact, Map<String, Serializable> contactExpandoAttributes,
			Properties contactMappings, Properties contactExpandoMappings)
		throws Exception;

	public Attributes getLDAPUserAttributes(
			long ldapServerId, User user, Properties userMappings)
		throws SystemException;

	public Modifications getLDAPUserModifications(
			User user, Map<String, Serializable> userExpandoAttributes,
			Properties userMappings, Properties userExpandoMappings)
		throws Exception;

	public String getUserDNName(
			long ldapServerId, User user, Properties userMappings)
		throws Exception;

}