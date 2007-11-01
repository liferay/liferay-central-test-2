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

package com.liferay.portlet.imagegallery.service.base;

import com.liferay.portal.service.impl.PrincipalBean;

import com.liferay.portlet.imagegallery.service.IGFolderLocalService;
import com.liferay.portlet.imagegallery.service.IGFolderLocalServiceFactory;
import com.liferay.portlet.imagegallery.service.IGFolderService;
import com.liferay.portlet.imagegallery.service.IGFolderServiceFactory;
import com.liferay.portlet.imagegallery.service.IGImageLocalService;
import com.liferay.portlet.imagegallery.service.IGImageLocalServiceFactory;
import com.liferay.portlet.imagegallery.service.IGImageService;
import com.liferay.portlet.imagegallery.service.persistence.IGFolderPersistence;
import com.liferay.portlet.imagegallery.service.persistence.IGFolderUtil;
import com.liferay.portlet.imagegallery.service.persistence.IGImageFinder;
import com.liferay.portlet.imagegallery.service.persistence.IGImageFinderUtil;
import com.liferay.portlet.imagegallery.service.persistence.IGImagePersistence;
import com.liferay.portlet.imagegallery.service.persistence.IGImageUtil;

import org.springframework.beans.factory.InitializingBean;

/**
 * <a href="IGImageServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class IGImageServiceBaseImpl extends PrincipalBean
	implements IGImageService, InitializingBean {
	public IGFolderLocalService getIGFolderLocalService() {
		return igFolderLocalService;
	}

	public void setIGFolderLocalService(
		IGFolderLocalService igFolderLocalService) {
		this.igFolderLocalService = igFolderLocalService;
	}

	public IGFolderService getIGFolderService() {
		return igFolderService;
	}

	public void setIGFolderService(IGFolderService igFolderService) {
		this.igFolderService = igFolderService;
	}

	public IGFolderPersistence getIGFolderPersistence() {
		return igFolderPersistence;
	}

	public void setIGFolderPersistence(IGFolderPersistence igFolderPersistence) {
		this.igFolderPersistence = igFolderPersistence;
	}

	public IGImageLocalService getIGImageLocalService() {
		return igImageLocalService;
	}

	public void setIGImageLocalService(IGImageLocalService igImageLocalService) {
		this.igImageLocalService = igImageLocalService;
	}

	public IGImagePersistence getIGImagePersistence() {
		return igImagePersistence;
	}

	public void setIGImagePersistence(IGImagePersistence igImagePersistence) {
		this.igImagePersistence = igImagePersistence;
	}

	public IGImageFinder getIGImageFinder() {
		return igImageFinder;
	}

	public void setIGImageFinder(IGImageFinder igImageFinder) {
		this.igImageFinder = igImageFinder;
	}

	public void afterPropertiesSet() {
		if (igFolderLocalService == null) {
			igFolderLocalService = IGFolderLocalServiceFactory.getImpl();
		}

		if (igFolderService == null) {
			igFolderService = IGFolderServiceFactory.getImpl();
		}

		if (igFolderPersistence == null) {
			igFolderPersistence = IGFolderUtil.getPersistence();
		}

		if (igImageLocalService == null) {
			igImageLocalService = IGImageLocalServiceFactory.getImpl();
		}

		if (igImagePersistence == null) {
			igImagePersistence = IGImageUtil.getPersistence();
		}

		if (igImageFinder == null) {
			igImageFinder = IGImageFinderUtil.getFinder();
		}
	}

	protected IGFolderLocalService igFolderLocalService;
	protected IGFolderService igFolderService;
	protected IGFolderPersistence igFolderPersistence;
	protected IGImageLocalService igImageLocalService;
	protected IGImagePersistence igImagePersistence;
	protected IGImageFinder igImageFinder;
}