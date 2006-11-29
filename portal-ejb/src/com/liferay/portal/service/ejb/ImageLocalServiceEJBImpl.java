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

package com.liferay.portal.service.ejb;

import com.liferay.portal.service.ImageLocalService;
import com.liferay.portal.service.ImageLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="ImageLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ImageLocalServiceEJBImpl implements ImageLocalService, SessionBean {
	public void deleteImage(java.lang.String imageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ImageLocalServiceFactory.getTxImpl().deleteImage(imageId);
	}

	public void deleteImages(java.lang.String imageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		ImageLocalServiceFactory.getTxImpl().deleteImages(imageId);
	}

	public com.liferay.portal.model.Image getImage(java.lang.String imageId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return ImageLocalServiceFactory.getTxImpl().getImage(imageId);
	}

	public java.util.List getImages() throws com.liferay.portal.SystemException {
		return ImageLocalServiceFactory.getTxImpl().getImages();
	}

	public java.util.List getImages(int begin, int end)
		throws com.liferay.portal.SystemException {
		return ImageLocalServiceFactory.getTxImpl().getImages(begin, end);
	}

	public java.util.List search(java.lang.String imageId)
		throws com.liferay.portal.SystemException {
		return ImageLocalServiceFactory.getTxImpl().search(imageId);
	}

	public com.liferay.portal.model.Image updateImage(
		java.lang.String imageId, byte[] bytes)
		throws com.liferay.portal.SystemException {
		return ImageLocalServiceFactory.getTxImpl().updateImage(imageId, bytes);
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