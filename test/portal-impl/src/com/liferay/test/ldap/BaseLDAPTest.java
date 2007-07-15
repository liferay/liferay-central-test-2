/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.test.ldap;

import com.liferay.test.TestCase;
import com.liferay.test.TestProps;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 * <a href="BaseLDAPTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jerry Niu
 *
 */
public abstract class BaseLDAPTest extends TestCase {

	protected Properties getLdapProperties() {
		Properties env = new Properties();

		try {
			env.put(
				Context.INITIAL_CONTEXT_FACTORY,
				TestProps.get("ldap.factory.initial"));
			env.put(
				Context.PROVIDER_URL,
				TestProps.get("ldap.provider.url"));
			env.put(
				Context.SECURITY_PRINCIPAL,
				TestProps.get("ldap.security.principal"));
			env.put(
				Context.SECURITY_CREDENTIALS,
				TestProps.get("ldap.security.credentials"));
		}
		catch (Exception e) {
			fail(e);
		}

		return env;
	}

	public void test() {
		DirContext ctx = null;

		try {
			ctx = new InitialDirContext(getLdapProperties());

			useContext(ctx);
		}
		catch (Exception e) {
			fail(e);
		}
		finally {
			try {
				if (ctx != null) {
					ctx.close();
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected abstract void useContext(DirContext ctx);

}