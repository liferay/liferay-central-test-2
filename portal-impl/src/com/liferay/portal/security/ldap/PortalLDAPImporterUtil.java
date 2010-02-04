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

package com.liferay.portal.security.ldap;

import com.liferay.portal.model.User;

import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapContext;

/**
 * <a href="PortalLDAPImporterUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Edward Han
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class PortalLDAPImporterUtil {

	public static void importFromLDAP() throws Exception {
		_portalLDAPImporter.importFromLDAP();
	}

	public static void importFromLDAP(long companyId) throws Exception {
		_portalLDAPImporter.importFromLDAP(companyId);
	}

	public static void importFromLDAP(long ldapServerId, long companyId)
		throws Exception {

		_portalLDAPImporter.importFromLDAP(ldapServerId, companyId);
	}

	public static User importLDAPUser(
			long ldapServerId, long companyId, LdapContext ldapContext,
			Attributes attributes, String password,
			boolean importGroupMembership)
		throws Exception {

		return _portalLDAPImporter.importLDAPUser(
			ldapServerId, companyId, ldapContext, attributes, password,
			importGroupMembership);
	}

	public void setPortalLDAPImporter(PortalLDAPImporter portalLDAPImporter) {
		_portalLDAPImporter = portalLDAPImporter;
	}

	private static PortalLDAPImporter _portalLDAPImporter;

}