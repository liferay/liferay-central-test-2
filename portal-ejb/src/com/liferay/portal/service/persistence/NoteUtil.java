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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="NoteUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class NoteUtil {
	public static final String CLASS_NAME = NoteUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portal.model.Note"));

	public static com.liferay.portal.model.Note create(java.lang.String noteId) {
		return getPersistence().create(noteId);
	}

	public static com.liferay.portal.model.Note remove(java.lang.String noteId)
		throws com.liferay.portal.NoSuchNoteException, 
			com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		if (listener != null) {
			listener.onBeforeRemove(findByPrimaryKey(noteId));
		}

		com.liferay.portal.model.Note note = getPersistence().remove(noteId);

		if (listener != null) {
			listener.onAfterRemove(note);
		}

		return note;
	}

	public static com.liferay.portal.model.Note update(
		com.liferay.portal.model.Note note)
		throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		boolean isNew = note.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(note);
			}
			else {
				listener.onBeforeUpdate(note);
			}
		}

		note = getPersistence().update(note);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(note);
			}
			else {
				listener.onAfterUpdate(note);
			}
		}

		return note;
	}

	public static com.liferay.portal.model.Note findByPrimaryKey(
		java.lang.String noteId)
		throws com.liferay.portal.NoSuchNoteException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(noteId);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end);
	}

	public static java.util.List findByCompanyId(java.lang.String companyId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId(companyId, begin, end, obc);
	}

	public static com.liferay.portal.model.Note findByCompanyId_First(
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchNoteException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_First(companyId, obc);
	}

	public static com.liferay.portal.model.Note findByCompanyId_Last(
		java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchNoteException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_Last(companyId, obc);
	}

	public static com.liferay.portal.model.Note[] findByCompanyId_PrevAndNext(
		java.lang.String noteId, java.lang.String companyId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchNoteException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByCompanyId_PrevAndNext(noteId, companyId,
			obc);
	}

	public static java.util.List findByUserId(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId);
	}

	public static java.util.List findByUserId(java.lang.String userId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end);
	}

	public static java.util.List findByUserId(java.lang.String userId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByUserId(userId, begin, end, obc);
	}

	public static com.liferay.portal.model.Note findByUserId_First(
		java.lang.String userId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchNoteException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_First(userId, obc);
	}

	public static com.liferay.portal.model.Note findByUserId_Last(
		java.lang.String userId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchNoteException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_Last(userId, obc);
	}

	public static com.liferay.portal.model.Note[] findByUserId_PrevAndNext(
		java.lang.String noteId, java.lang.String userId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchNoteException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByUserId_PrevAndNext(noteId, userId, obc);
	}

	public static java.util.List findByC_C(java.lang.String companyId,
		java.lang.String className) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(companyId, className);
	}

	public static java.util.List findByC_C(java.lang.String companyId,
		java.lang.String className, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(companyId, className, begin, end);
	}

	public static java.util.List findByC_C(java.lang.String companyId,
		java.lang.String className, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C(companyId, className, begin, end, obc);
	}

	public static com.liferay.portal.model.Note findByC_C_First(
		java.lang.String companyId, java.lang.String className,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchNoteException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_First(companyId, className, obc);
	}

	public static com.liferay.portal.model.Note findByC_C_Last(
		java.lang.String companyId, java.lang.String className,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchNoteException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_Last(companyId, className, obc);
	}

	public static com.liferay.portal.model.Note[] findByC_C_PrevAndNext(
		java.lang.String noteId, java.lang.String companyId,
		java.lang.String className,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchNoteException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_PrevAndNext(noteId, companyId,
			className, obc);
	}

	public static java.util.List findByC_C_C(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C(companyId, className, classPK);
	}

	public static java.util.List findByC_C_C(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C(companyId, className, classPK,
			begin, end);
	}

	public static java.util.List findByC_C_C(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK, int begin,
		int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C(companyId, className, classPK,
			begin, end, obc);
	}

	public static com.liferay.portal.model.Note findByC_C_C_First(
		java.lang.String companyId, java.lang.String className,
		java.lang.String classPK,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchNoteException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C_First(companyId, className,
			classPK, obc);
	}

	public static com.liferay.portal.model.Note findByC_C_C_Last(
		java.lang.String companyId, java.lang.String className,
		java.lang.String classPK,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchNoteException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C_Last(companyId, className, classPK,
			obc);
	}

	public static com.liferay.portal.model.Note[] findByC_C_C_PrevAndNext(
		java.lang.String noteId, java.lang.String companyId,
		java.lang.String className, java.lang.String classPK,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.NoSuchNoteException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_C_C_PrevAndNext(noteId, companyId,
			className, classPK, obc);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static void removeByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	public static void removeByUserId(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByUserId(userId);
	}

	public static void removeByC_C(java.lang.String companyId,
		java.lang.String className) throws com.liferay.portal.SystemException {
		getPersistence().removeByC_C(companyId, className);
	}

	public static void removeByC_C_C(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByC_C_C(companyId, className, classPK);
	}

	public static int countByCompanyId(java.lang.String companyId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	public static int countByUserId(java.lang.String userId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByUserId(userId);
	}

	public static int countByC_C(java.lang.String companyId,
		java.lang.String className) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C(companyId, className);
	}

	public static int countByC_C_C(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByC_C_C(companyId, className, classPK);
	}

	public static NotePersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		NoteUtil util = (NoteUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(NotePersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(NoteUtil.class);
	private NotePersistence _persistence;
}