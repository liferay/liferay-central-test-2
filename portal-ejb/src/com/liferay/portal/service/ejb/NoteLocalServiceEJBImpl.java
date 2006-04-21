/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portal.service.ejb;

import com.liferay.portal.service.spring.NoteLocalService;
import com.liferay.portal.spring.util.SpringUtil;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="NoteLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class NoteLocalServiceEJBImpl implements NoteLocalService, SessionBean {
	public static final String CLASS_NAME = NoteLocalService.class.getName() +
		".transaction";

	public static NoteLocalService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (NoteLocalService)ctx.getBean(CLASS_NAME);
	}

	public com.liferay.portal.model.Note addNote(java.lang.String userId,
		java.lang.String className, java.lang.String classPK,
		java.lang.String content)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().addNote(userId, className, classPK, content);
	}

	public void deleteNote(java.lang.String noteId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteNote(noteId);
	}

	public void deleteNotes(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		getService().deleteNotes(companyId, className, classPK);
	}

	public com.liferay.portal.model.Note getNote(java.lang.String noteId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getNote(noteId);
	}

	public java.util.List getNotes(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		return getService().getNotes(companyId, className, classPK);
	}

	public com.liferay.portal.model.Note updateNote(java.lang.String noteId,
		java.lang.String content)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().updateNote(noteId, content);
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