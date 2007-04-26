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

package com.liferay.mail.service.ejb;

import com.liferay.mail.service.MailService;
import com.liferay.mail.service.MailServiceFactory;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.mail.MailMessage;

import java.rmi.RemoteException;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="MailServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MailServiceEJBImpl implements MailService, SessionBean {

	public void addForward(
			long userId, List filters, List emailAddresses, boolean leaveCopy)
		throws RemoteException, SystemException {

		MailServiceFactory.getTxImpl().addForward(
			userId, filters, emailAddresses, leaveCopy);
	}

	public void addUser(
			long userId, String password, String firstName, String middleName,
			String lastName, String emailAddress)
		throws RemoteException, SystemException {

		MailServiceFactory.getTxImpl().addUser(
			userId, password, firstName, middleName, lastName, emailAddress);
	}

	public void addVacationMessage(
			long userId, String emailAddress, String vacationMessage)
		throws RemoteException, SystemException {

		MailServiceFactory.getTxImpl().addVacationMessage(
			userId, emailAddress, vacationMessage);
	}

	public void deleteEmailAddress(long userId)
		throws RemoteException, SystemException {

		MailServiceFactory.getTxImpl().deleteEmailAddress(userId);
	}

	public void deleteUser(long userId)
		throws RemoteException, SystemException {

		MailServiceFactory.getTxImpl().deleteUser(userId);
	}

	public void sendEmail(MailMessage mailMessage)
		throws RemoteException, SystemException {

		MailServiceFactory.getTxImpl().sendEmail(mailMessage);
	}

	public void updateBlocked(long userId, List blocked)
		throws RemoteException, SystemException {

		MailServiceFactory.getTxImpl().updateBlocked(userId, blocked);
	}

	public void updateEmailAddress(long userId, String emailAddress)
		throws RemoteException, SystemException {

		MailServiceFactory.getTxImpl().updateEmailAddress(userId, emailAddress);
	}

	public void updatePassword(long userId, String password)
		throws RemoteException, SystemException {

		MailServiceFactory.getTxImpl().updatePassword(userId, password);
	}

	public void ejbCreate() throws CreateException {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public SessionContext getSessionContext() {
		return _sc;
	}

	public void setSessionContext(SessionContext sc) {
		_sc = sc;
	}

	private SessionContext _sc;

}