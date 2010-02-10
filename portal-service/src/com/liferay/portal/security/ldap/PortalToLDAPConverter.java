/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.ldap;

import com.liferay.portal.SystemException;
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