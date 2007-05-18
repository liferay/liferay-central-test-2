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

package com.liferay.portlet.journal.service.ejb;

import com.liferay.portlet.journal.service.JournalArticleImageLocalService;
import com.liferay.portlet.journal.service.JournalArticleImageLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="JournalArticleImageLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class is the EJB implementation of the service that is used when Liferay
 * is run inside a full J2EE container.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.journal.service.JournalArticleImageLocalService
 * @see com.liferay.portlet.journal.service.JournalArticleImageLocalServiceUtil
 * @see com.liferay.portlet.journal.service.ejb.JournalArticleImageLocalServiceEJB
 * @see com.liferay.portlet.journal.service.ejb.JournalArticleImageLocalServiceHome
 * @see com.liferay.portlet.journal.service.impl.JournalArticleImageLocalServiceImpl
 *
 */
public class JournalArticleImageLocalServiceEJBImpl
	implements JournalArticleImageLocalService, SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return JournalArticleImageLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return JournalArticleImageLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public void deleteArticleImage(long articleImageId)
		throws com.liferay.portal.SystemException {
		JournalArticleImageLocalServiceFactory.getTxImpl().deleteArticleImage(articleImageId);
	}

	public void deleteArticleImage(long groupId, java.lang.String articleId,
		double version, java.lang.String elName, java.lang.String languageId)
		throws com.liferay.portal.SystemException {
		JournalArticleImageLocalServiceFactory.getTxImpl().deleteArticleImage(groupId,
			articleId, version, elName, languageId);
	}

	public void deleteImages(long groupId, java.lang.String articleId,
		double version) throws com.liferay.portal.SystemException {
		JournalArticleImageLocalServiceFactory.getTxImpl().deleteImages(groupId,
			articleId, version);
	}

	public long getArticleImageId(long groupId, java.lang.String articleId,
		double version, java.lang.String elName, java.lang.String languageId)
		throws com.liferay.portal.SystemException {
		return JournalArticleImageLocalServiceFactory.getTxImpl()
													 .getArticleImageId(groupId,
			articleId, version, elName, languageId);
	}

	public long getArticleImageId(long groupId, java.lang.String articleId,
		double version, java.lang.String elName, java.lang.String languageId,
		boolean tempImage) throws com.liferay.portal.SystemException {
		return JournalArticleImageLocalServiceFactory.getTxImpl()
													 .getArticleImageId(groupId,
			articleId, version, elName, languageId, tempImage);
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