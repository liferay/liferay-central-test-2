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

package com.liferay.portal.kernel.workflow.spi;

import java.io.Serializable;
import java.util.Set;

/**
 * <a href="UserCredential.java.html"><b><i>View Source</i></b></a>
 * 
 * <p>
 * The user credential is a container for a user's id and its roles and is used
 * as the credential towards the workflow engine. For convenience, it is just
 * used within the SPI as the API just takes the user id, the role set is being
 * added by the proxy in order to avoid the implementation or adapter having to
 * call back the portal for the set of roles of a user's id.
 * </p>
 * 
 * @author Micha Kiener
 * 
 */
public class UserCredential implements Serializable {

	/**
	 * Default constructor, just used for de-serialization, never for
	 * construction.
	 */
	public UserCredential() {

	}

	/**
	 * The constructor for the user credential object representing the given
	 * user id and its role set.
	 * 
	 * @param set the set of role ids the user is assigned to
	 * @param id the id of the user
	 */
	public UserCredential(Set<Long> set, long id) {
		super();
		_roleSet = set;
		_userId = id;
	}


	/**
	 * @return the id of the user reflected by this credential
	 */
	public long getUserId() {
		return _userId;
	}

	/**
	 * @return the set of the role ids the user reflected by this credential has
	 */
	public Set<Long> getRoleIds() {
		return _roleSet;
	}

	private Set<Long> _roleSet;
	private long _userId;
}
