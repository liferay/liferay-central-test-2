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

package com.liferay.portlet.journal.service;

/**
 * <a href="JournalContentSearchLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is <code>com.liferay.portlet.journal.service.impl.JournalContentSearchLocalServiceImpl</code>.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be accessed
 * from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.journal.service.JournalContentSearchServiceFactory
 * @see com.liferay.portlet.journal.service.JournalContentSearchServiceUtil
 *
 */
public interface JournalContentSearchLocalService {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException;

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException;

	public void checkContentSearches(long companyId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public void deleteArticleContentSearches(long companyId, long groupId,
		java.lang.String articleId) throws com.liferay.portal.SystemException;

	public void deleteLayoutContentSearches(long groupId,
		boolean privateLayout, long layoutId)
		throws com.liferay.portal.SystemException;

	public void deleteOwnerContentSearches(long groupId, boolean privateLayout)
		throws com.liferay.portal.SystemException;

	public java.util.List getArticleContentSearches()
		throws com.liferay.portal.SystemException;

	public java.util.List getArticleContentSearches(long companyId,
		long groupId, java.lang.String articleId)
		throws com.liferay.portal.SystemException;

	public java.util.List getLayoutIds(long groupId, boolean privateLayout,
		java.lang.String articleId) throws com.liferay.portal.SystemException;

	public int getLayoutIdsCount(long groupId, boolean privateLayout,
		java.lang.String articleId) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.journal.model.JournalContentSearch updateContentSearch(
		long companyId, long groupId, boolean privateLayout, long layoutId,
		java.lang.String portletId, java.lang.String articleId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List updateContentSearch(long companyId, long groupId,
		boolean privateLayout, long layoutId, java.lang.String portletId,
		java.lang.String[] articleIds)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;
}