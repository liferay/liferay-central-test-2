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

import com.liferay.mail.NoSuchCyrusUserException;
import com.liferay.mail.model.CyrusUser;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.dao.orm.ObjectNotFoundException;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.model.Dummy;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

/**
 * <a href="CyrusUserPersistenceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CyrusUserPersistenceImpl
	extends BasePersistenceImpl<Dummy> implements CyrusUserPersistence {

	public CyrusUser findByPrimaryKey(long userId)
		throws NoSuchCyrusUserException, SystemException {

		Session session = null;

		try {
			session = openSession();

			return (CyrusUser)session.load(
				CyrusUser.class, String.valueOf(userId));
		}
		catch (ObjectNotFoundException onfe) {
			throw new NoSuchCyrusUserException();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public void remove(long userId)
		throws NoSuchCyrusUserException, SystemException {

		Session session = null;

		try {
			session = openSession();

			CyrusUser user = (CyrusUser)session.load(
				CyrusUser.class, String.valueOf(userId));

			session.delete(user);

			session.flush();
		}
		catch (ObjectNotFoundException onfe) {
			throw new NoSuchCyrusUserException();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public void update(CyrusUser user) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			try {
				CyrusUser userModel = (CyrusUser)session.load(
					CyrusUser.class, String.valueOf(user.getUserId()));

				userModel.setPassword(user.getPassword());

				session.flush();
			}
			catch (ObjectNotFoundException onfe) {
				CyrusUser userModel = new CyrusUser(
					user.getUserId(), user.getPassword());

				session.save(userModel);

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