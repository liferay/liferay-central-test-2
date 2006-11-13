/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.mail.service.spring.MailService;
import com.liferay.portal.SystemException;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.util.mail.MailMessage;

import java.rmi.RemoteException;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.springframework.context.ApplicationContext;

/**
 * <a href="MailServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MailServiceEJBImpl implements MailService, SessionBean {

	public static final String CLASS_NAME =
		MailService.class.getName() + ".transaction";

	public static MailService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (MailService)ctx.getBean(CLASS_NAME);
	}

	public void addForward(
			String userId, List filters, List emailAddresses, boolean leaveCopy)
		throws RemoteException, SystemException {

		getService().addForward(userId, filters, emailAddresses, leaveCopy);
	}

	public void addUser(
			String userId, String password, String firstName, String middleName,
			String lastName, String emailAddress)
		throws RemoteException, SystemException {

		getService().addUser(
			userId, password, firstName, middleName, lastName, emailAddress);
	}

	public void addVacationMessage(
			String userId, String emailAddress, String vacationMessage)
		throws RemoteException, SystemException {

		getService().addVacationMessage(userId, emailAddress, vacationMessage);
	}

	public void deleteEmailAddress(String userId)
		throws RemoteException, SystemException {

		getService().deleteEmailAddress(userId);
	}

	public void deleteUser(String userId)
		throws RemoteException, SystemException {

		getService().deleteUser(userId);
	}

	public void sendEmail(MailMessage mailMessage)
		throws RemoteException, SystemException {

		getService().sendEmail(mailMessage);
	}

	public void updateBlocked(String userId, List blocked)
		throws RemoteException, SystemException {

		getService().updateBlocked(userId, blocked);
	}

	public void updateEmailAddress(String userId, String emailAddress)
		throws RemoteException, SystemException {

		getService().updateEmailAddress(userId, emailAddress);
	}

	public void updatePassword(String userId, String password)
		throws RemoteException, SystemException {

		getService().updatePassword(userId, password);
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