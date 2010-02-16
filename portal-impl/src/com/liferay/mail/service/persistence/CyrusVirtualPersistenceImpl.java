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

package com.liferay.mail.service.persistence;

import com.liferay.mail.NoSuchCyrusVirtualException;
import com.liferay.mail.model.CyrusVirtual;
import com.liferay.portal.kernel.dao.orm.ObjectNotFoundException;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Dummy;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.util.Iterator;
import java.util.List;

/**
 * <a href="CyrusVirtualPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CyrusVirtualPersistenceImpl
	extends BasePersistenceImpl<Dummy> implements CyrusVirtualPersistence {

	public static String FIND_BY_USER_ID =
		"SELECT cyrusVirtual FROM CyrusVirtual cyrusVirtual WHERE userId = ?";

	public CyrusVirtual findByPrimaryKey(String emailAddress)
		throws NoSuchCyrusVirtualException, SystemException {

		Session session = null;

		try {
			session = openSession();

			return (CyrusVirtual)session.load(CyrusVirtual.class, emailAddress);
		}
		catch (ObjectNotFoundException onfe) {
			throw new NoSuchCyrusVirtualException();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<CyrusVirtual> findByUserId(long userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(FIND_BY_USER_ID);

			q.setString(0, String.valueOf(userId));

			return q.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public void remove(String emailAddress)
		throws NoSuchCyrusVirtualException, SystemException {

		Session session = null;

		try {
			session = openSession();

			CyrusVirtual virtual = (CyrusVirtual)session.load(
				CyrusVirtual.class, emailAddress);

			session.delete(virtual);

			session.flush();
		}
		catch (ObjectNotFoundException onfe) {
			throw new NoSuchCyrusVirtualException();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public void removeByUserId(long userId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(FIND_BY_USER_ID);

			q.setString(0, String.valueOf(userId));

			Iterator<CyrusVirtual> itr = q.iterate();

			while (itr.hasNext()) {
				CyrusVirtual virtual = itr.next();

				session.delete(virtual);
			}

			closeSession(session);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public void update(CyrusVirtual virtual) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			try {
				CyrusVirtual virtualModel = (CyrusVirtual)session.load(
					CyrusVirtual.class, virtual.getEmailAddress());

				virtualModel.setUserId(virtual.getUserId());

				session.flush();
			}
			catch (ObjectNotFoundException onfe) {
				CyrusVirtual virtualModel = new CyrusVirtual(
					virtual.getEmailAddress(), virtual.getUserId());

				session.save(virtualModel);

				session.flush();
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

}