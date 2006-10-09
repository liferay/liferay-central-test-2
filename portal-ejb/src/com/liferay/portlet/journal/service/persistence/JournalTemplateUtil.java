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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.model.ModelListener;
import com.liferay.portal.spring.util.SpringUtil;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

/**
 * <a href="JournalTemplateUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class JournalTemplateUtil {
	public static final String CLASS_NAME = JournalTemplateUtil.class.getName();
	public static final String LISTENER = GetterUtil.getString(PropsUtil.get(
				"value.object.listener.com.liferay.portlet.journal.model.JournalTemplate"));

	public static com.liferay.portlet.journal.model.JournalTemplate create(
		com.liferay.portlet.journal.service.persistence.JournalTemplatePK journalTemplatePK) {
		return getPersistence().create(journalTemplatePK);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate remove(
		com.liferay.portlet.journal.service.persistence.JournalTemplatePK journalTemplatePK)
		throws com.liferay.portlet.journal.NoSuchTemplateException, 
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
			listener.onBeforeRemove(findByPrimaryKey(journalTemplatePK));
		}

		com.liferay.portlet.journal.model.JournalTemplate journalTemplate = getPersistence()
																				.remove(journalTemplatePK);

		if (listener != null) {
			listener.onAfterRemove(journalTemplate);
		}

		return journalTemplate;
	}

	public static com.liferay.portlet.journal.model.JournalTemplate remove(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate)
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

		if (listener != null) {
			listener.onBeforeRemove(journalTemplate);
		}

		journalTemplate = getPersistence().remove(journalTemplate);

		if (listener != null) {
			listener.onAfterRemove(journalTemplate);
		}

		return journalTemplate;
	}

	public static com.liferay.portlet.journal.model.JournalTemplate update(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate)
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

		boolean isNew = journalTemplate.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(journalTemplate);
			}
			else {
				listener.onBeforeUpdate(journalTemplate);
			}
		}

		journalTemplate = getPersistence().update(journalTemplate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(journalTemplate);
			}
			else {
				listener.onAfterUpdate(journalTemplate);
			}
		}

		return journalTemplate;
	}

	public static com.liferay.portlet.journal.model.JournalTemplate update(
		com.liferay.portlet.journal.model.JournalTemplate journalTemplate,
		boolean saveOrUpdate) throws com.liferay.portal.SystemException {
		ModelListener listener = null;

		if (Validator.isNotNull(LISTENER)) {
			try {
				listener = (ModelListener)Class.forName(LISTENER).newInstance();
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		boolean isNew = journalTemplate.isNew();

		if (listener != null) {
			if (isNew) {
				listener.onBeforeCreate(journalTemplate);
			}
			else {
				listener.onBeforeUpdate(journalTemplate);
			}
		}

		journalTemplate = getPersistence().update(journalTemplate, saveOrUpdate);

		if (listener != null) {
			if (isNew) {
				listener.onAfterCreate(journalTemplate);
			}
			else {
				listener.onAfterUpdate(journalTemplate);
			}
		}

		return journalTemplate;
	}

	public static com.liferay.portlet.journal.model.JournalTemplate findByPrimaryKey(
		com.liferay.portlet.journal.service.persistence.JournalTemplatePK journalTemplatePK)
		throws com.liferay.portlet.journal.NoSuchTemplateException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByPrimaryKey(journalTemplatePK);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate fetchByPrimaryKey(
		com.liferay.portlet.journal.service.persistence.JournalTemplatePK journalTemplatePK)
		throws com.liferay.portal.SystemException {
		return getPersistence().fetchByPrimaryKey(journalTemplatePK);
	}

	public static java.util.List findByGroupId(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	public static java.util.List findByGroupId(java.lang.String groupId,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end);
	}

	public static java.util.List findByGroupId(java.lang.String groupId,
		int begin, int end, com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByGroupId(groupId, begin, end, obc);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate findByGroupId_First(
		java.lang.String groupId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.journal.NoSuchTemplateException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_First(groupId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate findByGroupId_Last(
		java.lang.String groupId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.journal.NoSuchTemplateException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_Last(groupId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate[] findByGroupId_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalTemplatePK journalTemplatePK,
		java.lang.String groupId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.journal.NoSuchTemplateException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByGroupId_PrevAndNext(journalTemplatePK,
			groupId, obc);
	}

	public static java.util.List findByC_S(java.lang.String companyId,
		java.lang.String structureId) throws com.liferay.portal.SystemException {
		return getPersistence().findByC_S(companyId, structureId);
	}

	public static java.util.List findByC_S(java.lang.String companyId,
		java.lang.String structureId, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_S(companyId, structureId, begin, end);
	}

	public static java.util.List findByC_S(java.lang.String companyId,
		java.lang.String structureId, int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findByC_S(companyId, structureId, begin, end,
			obc);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate findByC_S_First(
		java.lang.String companyId, java.lang.String structureId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.journal.NoSuchTemplateException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_S_First(companyId, structureId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate findByC_S_Last(
		java.lang.String companyId, java.lang.String structureId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.journal.NoSuchTemplateException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_S_Last(companyId, structureId, obc);
	}

	public static com.liferay.portlet.journal.model.JournalTemplate[] findByC_S_PrevAndNext(
		com.liferay.portlet.journal.service.persistence.JournalTemplatePK journalTemplatePK,
		java.lang.String companyId, java.lang.String structureId,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portlet.journal.NoSuchTemplateException, 
			com.liferay.portal.SystemException {
		return getPersistence().findByC_S_PrevAndNext(journalTemplatePK,
			companyId, structureId, obc);
	}

	public static java.util.List findAll()
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll();
	}

	public static java.util.List findAll(int begin, int end)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end);
	}

	public static java.util.List findAll(int begin, int end,
		com.liferay.util.dao.hibernate.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getPersistence().findAll(begin, end, obc);
	}

	public static void removeByGroupId(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	public static void removeByC_S(java.lang.String companyId,
		java.lang.String structureId) throws com.liferay.portal.SystemException {
		getPersistence().removeByC_S(companyId, structureId);
	}

	public static int countByGroupId(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	public static int countByC_S(java.lang.String companyId,
		java.lang.String structureId) throws com.liferay.portal.SystemException {
		return getPersistence().countByC_S(companyId, structureId);
	}

	public static void initDao() {
		getPersistence().initDao();
	}

	public static JournalTemplatePersistence getPersistence() {
		ApplicationContext ctx = SpringUtil.getContext();
		JournalTemplateUtil util = (JournalTemplateUtil)ctx.getBean(CLASS_NAME);

		return util._persistence;
	}

	public void setPersistence(JournalTemplatePersistence persistence) {
		_persistence = persistence;
	}

	private static Log _log = LogFactory.getLog(JournalTemplateUtil.class);
	private JournalTemplatePersistence _persistence;
}