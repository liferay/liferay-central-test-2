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

package com.liferay.portal.kernel.workflow;

/**
 * <a href="UserCredentialFactory.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * This service provider interface is going to be injected into the
 * {@link WorkflowUtil} to create {@link UserCredential} objects based on the id
 * of the user to be represented.
 * </p>
 *
 * <p>
 * The portal kernel is not able to invoke the service layer, so the
 * implementation of the factory goes into the portal impl.
 * </p>
 *
 * @author Micha Kiener
 *
 */
public interface UserCredentialFactory {

	/**
	 * Creates a new user credential object based on the user given by its id.
	 * The credential will represent that user, some attributes and its role
	 * set.
	 *
	 * @param userId the id of the user to create a credential for
	 * @return the credential representing the specified user
	 * @throws WorkflowException is thrown, if creating the credential failed
	 *			 due to a failure to load the user's information
	 */
	public UserCredential createCredential(long userId)
		throws WorkflowException;

}