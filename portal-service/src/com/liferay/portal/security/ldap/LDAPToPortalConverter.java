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

import java.util.Properties;

import javax.naming.directory.Attributes;

/**
 * <a href="LDAPToPortalConverter.java.html"><b><i>View Source</i></b></a>
 *
 * Provides the ability to customize what attributes are retrieved from
 * LDAP and placed into User and Groups
 *
 * @author Edward Han
 */
public interface LDAPToPortalConverter {

	public LDAPUserHolder importLDAPUser(
			Attributes attributes, Properties userMappings,
			Properties contactMappings,
			long companyId, String password)
		throws Exception;

	public LDAPUserGroupHolder importLDAPGroup(
			Attributes attributes, Properties groupMappings,
			long companyId)
		throws Exception;

}