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

package com.liferay.portlet.tags.service.ejb;

import com.liferay.portlet.tags.service.TagsAssetLocalService;
import com.liferay.portlet.tags.service.TagsAssetLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="TagsAssetLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see com.liferay.portlet.tags.service.TagsAssetLocalService
 * @see com.liferay.portlet.tags.service.TagsAssetLocalServiceUtil
 * @see com.liferay.portlet.tags.service.ejb.TagsAssetLocalServiceEJB
 * @see com.liferay.portlet.tags.service.ejb.TagsAssetLocalServiceHome
 * @see com.liferay.portlet.tags.service.impl.TagsAssetLocalServiceImpl
 *
 */
public class TagsAssetLocalServiceEJBImpl implements TagsAssetLocalService,
	SessionBean {
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		return TagsAssetLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer);
	}

	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		return TagsAssetLocalServiceFactory.getTxImpl().dynamicQuery(queryInitializer,
			begin, end);
	}

	public void deleteAsset(long assetId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsAssetLocalServiceFactory.getTxImpl().deleteAsset(assetId);
	}

	public void deleteAsset(java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsAssetLocalServiceFactory.getTxImpl().deleteAsset(className, classPK);
	}

	public void deleteAsset(com.liferay.portlet.tags.model.TagsAsset asset)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		TagsAssetLocalServiceFactory.getTxImpl().deleteAsset(asset);
	}

	public com.liferay.portlet.tags.model.TagsAsset getAsset(long assetId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return TagsAssetLocalServiceFactory.getTxImpl().getAsset(assetId);
	}

	public com.liferay.portlet.tags.model.TagsAsset getAsset(
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return TagsAssetLocalServiceFactory.getTxImpl().getAsset(className,
			classPK);
	}

	public java.util.List getAssets(long[] entryIds, long[] notEntryIds,
		boolean andOperator, int begin, int end)
		throws com.liferay.portal.SystemException {
		return TagsAssetLocalServiceFactory.getTxImpl().getAssets(entryIds,
			notEntryIds, andOperator, begin, end);
	}

	public int getAssetsCount(long[] entryIds, long[] notEntryIds,
		boolean andOperator) throws com.liferay.portal.SystemException {
		return TagsAssetLocalServiceFactory.getTxImpl().getAssetsCount(entryIds,
			notEntryIds, andOperator);
	}

	public com.liferay.portlet.tags.model.TagsAsset updateAsset(
		java.lang.String userId, java.lang.String className,
		java.lang.String classPK, java.lang.String[] entryNames)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return TagsAssetLocalServiceFactory.getTxImpl().updateAsset(userId,
			className, classPK, entryNames);
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