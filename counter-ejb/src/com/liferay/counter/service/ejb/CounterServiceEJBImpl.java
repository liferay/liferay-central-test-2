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

package com.liferay.counter.service.ejb;

import com.liferay.counter.service.CounterService;
import com.liferay.counter.service.CounterServiceFactory;
import com.liferay.portal.SystemException;

import java.rmi.RemoteException;

import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="CounterServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class CounterServiceEJBImpl implements CounterService, SessionBean {

	public List getNames() throws RemoteException, SystemException {
		return CounterServiceFactory.getTxImpl().getNames();
	}

	public long increment(String name) throws RemoteException, SystemException {
		return CounterServiceFactory.getTxImpl().increment(name);
	}

	public long increment(String name, int size)
		throws RemoteException, SystemException {

		return CounterServiceFactory.getTxImpl().increment(name, size);
	}

	public void rename(String oldName, String newName)
		throws RemoteException, SystemException {

		CounterServiceFactory.getTxImpl().rename(oldName, newName);
	}

	public void reset(String name) throws RemoteException, SystemException {
		CounterServiceFactory.getTxImpl().reset(name);
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