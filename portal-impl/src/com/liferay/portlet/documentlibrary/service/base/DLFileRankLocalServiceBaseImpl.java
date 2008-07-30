/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.InitializingBean;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.LayoutService;
import com.liferay.portal.service.persistence.LayoutFinder;
import com.liferay.portal.service.persistence.LayoutPersistence;

import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryService;
import com.liferay.portlet.documentlibrary.service.DLFileRankLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderService;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryAndShortcutFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileRankFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence;

import java.util.List;

/**
 * <a href="DLFileRankLocalServiceBaseImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public abstract class DLFileRankLocalServiceBaseImpl
	implements DLFileRankLocalService, InitializingBean {
	public DLFileRank addDLFileRank(DLFileRank dlFileRank)
		throws SystemException {
		dlFileRank.setNew(true);

		return dlFileRankPersistence.update(dlFileRank, false);
	}

	public void deleteDLFileRank(long fileRankId)
		throws PortalException, SystemException {
		dlFileRankPersistence.remove(fileRankId);
	}

	public void deleteDLFileRank(DLFileRank dlFileRank)
		throws SystemException {
		dlFileRankPersistence.remove(dlFileRank);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return dlFileRankPersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<Object> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) throws SystemException {
		return dlFileRankPersistence.findWithDynamicQuery(dynamicQuery, start,
			end);
	}

	public DLFileRank getDLFileRank(long fileRankId)
		throws PortalException, SystemException {
		return dlFileRankPersistence.findByPrimaryKey(fileRankId);
	}

	public List<DLFileRank> getDLFileRanks(int start, int end)
		throws SystemException {
		return dlFileRankPersistence.findAll(start, end);
	}

	public int getDLFileRanksCount() throws SystemException {
		return dlFileRankPersistence.countAll();
	}

	public DLFileRank updateDLFileRank(DLFileRank dlFileRank)
		throws SystemException {
		dlFileRank.setNew(false);

		return dlFileRankPersistence.update(dlFileRank, true);
	}

	public DLFileEntryLocalService getDLFileEntryLocalService() {
		return dlFileEntryLocalService;
	}

	public void setDLFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {
		this.dlFileEntryLocalService = dlFileEntryLocalService;
	}

	public DLFileEntryService getDLFileEntryService() {
		return dlFileEntryService;
	}

	public void setDLFileEntryService(DLFileEntryService dlFileEntryService) {
		this.dlFileEntryService = dlFileEntryService;
	}

	public DLFileEntryPersistence getDLFileEntryPersistence() {
		return dlFileEntryPersistence;
	}

	public void setDLFileEntryPersistence(
		DLFileEntryPersistence dlFileEntryPersistence) {
		this.dlFileEntryPersistence = dlFileEntryPersistence;
	}

	public DLFileEntryFinder getDLFileEntryFinder() {
		return dlFileEntryFinder;
	}

	public void setDLFileEntryFinder(DLFileEntryFinder dlFileEntryFinder) {
		this.dlFileEntryFinder = dlFileEntryFinder;
	}

	public DLFileEntryAndShortcutFinder getDLFileEntryAndShortcutFinder() {
		return dlFileEntryAndShortcutFinder;
	}

	public void setDLFileEntryAndShortcutFinder(
		DLFileEntryAndShortcutFinder dlFileEntryAndShortcutFinder) {
		this.dlFileEntryAndShortcutFinder = dlFileEntryAndShortcutFinder;
	}

	public DLFileRankPersistence getDLFileRankPersistence() {
		return dlFileRankPersistence;
	}

	public void setDLFileRankPersistence(
		DLFileRankPersistence dlFileRankPersistence) {
		this.dlFileRankPersistence = dlFileRankPersistence;
	}

	public DLFileRankFinder getDLFileRankFinder() {
		return dlFileRankFinder;
	}

	public void setDLFileRankFinder(DLFileRankFinder dlFileRankFinder) {
		this.dlFileRankFinder = dlFileRankFinder;
	}

	public DLFileShortcutLocalService getDLFileShortcutLocalService() {
		return dlFileShortcutLocalService;
	}

	public void setDLFileShortcutLocalService(
		DLFileShortcutLocalService dlFileShortcutLocalService) {
		this.dlFileShortcutLocalService = dlFileShortcutLocalService;
	}

	public DLFileShortcutService getDLFileShortcutService() {
		return dlFileShortcutService;
	}

	public void setDLFileShortcutService(
		DLFileShortcutService dlFileShortcutService) {
		this.dlFileShortcutService = dlFileShortcutService;
	}

	public DLFileShortcutPersistence getDLFileShortcutPersistence() {
		return dlFileShortcutPersistence;
	}

	public void setDLFileShortcutPersistence(
		DLFileShortcutPersistence dlFileShortcutPersistence) {
		this.dlFileShortcutPersistence = dlFileShortcutPersistence;
	}

	public DLFileShortcutFinder getDLFileShortcutFinder() {
		return dlFileShortcutFinder;
	}

	public void setDLFileShortcutFinder(
		DLFileShortcutFinder dlFileShortcutFinder) {
		this.dlFileShortcutFinder = dlFileShortcutFinder;
	}

	public DLFileVersionLocalService getDLFileVersionLocalService() {
		return dlFileVersionLocalService;
	}

	public void setDLFileVersionLocalService(
		DLFileVersionLocalService dlFileVersionLocalService) {
		this.dlFileVersionLocalService = dlFileVersionLocalService;
	}

	public DLFileVersionPersistence getDLFileVersionPersistence() {
		return dlFileVersionPersistence;
	}

	public void setDLFileVersionPersistence(
		DLFileVersionPersistence dlFileVersionPersistence) {
		this.dlFileVersionPersistence = dlFileVersionPersistence;
	}

	public DLFolderLocalService getDLFolderLocalService() {
		return dlFolderLocalService;
	}

	public void setDLFolderLocalService(
		DLFolderLocalService dlFolderLocalService) {
		this.dlFolderLocalService = dlFolderLocalService;
	}

	public DLFolderService getDLFolderService() {
		return dlFolderService;
	}

	public void setDLFolderService(DLFolderService dlFolderService) {
		this.dlFolderService = dlFolderService;
	}

	public DLFolderPersistence getDLFolderPersistence() {
		return dlFolderPersistence;
	}

	public void setDLFolderPersistence(DLFolderPersistence dlFolderPersistence) {
		this.dlFolderPersistence = dlFolderPersistence;
	}

	public CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	public void setCounterLocalService(CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	public CounterService getCounterService() {
		return counterService;
	}

	public void setCounterService(CounterService counterService) {
		this.counterService = counterService;
	}

	public LayoutLocalService getLayoutLocalService() {
		return layoutLocalService;
	}

	public void setLayoutLocalService(LayoutLocalService layoutLocalService) {
		this.layoutLocalService = layoutLocalService;
	}

	public LayoutService getLayoutService() {
		return layoutService;
	}

	public void setLayoutService(LayoutService layoutService) {
		this.layoutService = layoutService;
	}

	public LayoutPersistence getLayoutPersistence() {
		return layoutPersistence;
	}

	public void setLayoutPersistence(LayoutPersistence layoutPersistence) {
		this.layoutPersistence = layoutPersistence;
	}

	public LayoutFinder getLayoutFinder() {
		return layoutFinder;
	}

	public void setLayoutFinder(LayoutFinder layoutFinder) {
		this.layoutFinder = layoutFinder;
	}

	public void afterPropertiesSet() {
		if (dlFileEntryLocalService == null) {
			dlFileEntryLocalService = (DLFileEntryLocalService)PortalBeanLocatorUtil.locate(DLFileEntryLocalService.class.getName() +
					".impl");
		}

		if (dlFileEntryService == null) {
			dlFileEntryService = (DLFileEntryService)PortalBeanLocatorUtil.locate(DLFileEntryService.class.getName() +
					".impl");
		}

		if (dlFileEntryPersistence == null) {
			dlFileEntryPersistence = (DLFileEntryPersistence)PortalBeanLocatorUtil.locate(DLFileEntryPersistence.class.getName() +
					".impl");
		}

		if (dlFileEntryFinder == null) {
			dlFileEntryFinder = (DLFileEntryFinder)PortalBeanLocatorUtil.locate(DLFileEntryFinder.class.getName() +
					".impl");
		}

		if (dlFileEntryAndShortcutFinder == null) {
			dlFileEntryAndShortcutFinder = (DLFileEntryAndShortcutFinder)PortalBeanLocatorUtil.locate(DLFileEntryAndShortcutFinder.class.getName() +
					".impl");
		}

		if (dlFileRankPersistence == null) {
			dlFileRankPersistence = (DLFileRankPersistence)PortalBeanLocatorUtil.locate(DLFileRankPersistence.class.getName() +
					".impl");
		}

		if (dlFileRankFinder == null) {
			dlFileRankFinder = (DLFileRankFinder)PortalBeanLocatorUtil.locate(DLFileRankFinder.class.getName() +
					".impl");
		}

		if (dlFileShortcutLocalService == null) {
			dlFileShortcutLocalService = (DLFileShortcutLocalService)PortalBeanLocatorUtil.locate(DLFileShortcutLocalService.class.getName() +
					".impl");
		}

		if (dlFileShortcutService == null) {
			dlFileShortcutService = (DLFileShortcutService)PortalBeanLocatorUtil.locate(DLFileShortcutService.class.getName() +
					".impl");
		}

		if (dlFileShortcutPersistence == null) {
			dlFileShortcutPersistence = (DLFileShortcutPersistence)PortalBeanLocatorUtil.locate(DLFileShortcutPersistence.class.getName() +
					".impl");
		}

		if (dlFileShortcutFinder == null) {
			dlFileShortcutFinder = (DLFileShortcutFinder)PortalBeanLocatorUtil.locate(DLFileShortcutFinder.class.getName() +
					".impl");
		}

		if (dlFileVersionLocalService == null) {
			dlFileVersionLocalService = (DLFileVersionLocalService)PortalBeanLocatorUtil.locate(DLFileVersionLocalService.class.getName() +
					".impl");
		}

		if (dlFileVersionPersistence == null) {
			dlFileVersionPersistence = (DLFileVersionPersistence)PortalBeanLocatorUtil.locate(DLFileVersionPersistence.class.getName() +
					".impl");
		}

		if (dlFolderLocalService == null) {
			dlFolderLocalService = (DLFolderLocalService)PortalBeanLocatorUtil.locate(DLFolderLocalService.class.getName() +
					".impl");
		}

		if (dlFolderService == null) {
			dlFolderService = (DLFolderService)PortalBeanLocatorUtil.locate(DLFolderService.class.getName() +
					".impl");
		}

		if (dlFolderPersistence == null) {
			dlFolderPersistence = (DLFolderPersistence)PortalBeanLocatorUtil.locate(DLFolderPersistence.class.getName() +
					".impl");
		}

		if (counterLocalService == null) {
			counterLocalService = (CounterLocalService)PortalBeanLocatorUtil.locate(CounterLocalService.class.getName() +
					".impl");
		}

		if (counterService == null) {
			counterService = (CounterService)PortalBeanLocatorUtil.locate(CounterService.class.getName() +
					".impl");
		}

		if (layoutLocalService == null) {
			layoutLocalService = (LayoutLocalService)PortalBeanLocatorUtil.locate(LayoutLocalService.class.getName() +
					".impl");
		}

		if (layoutService == null) {
			layoutService = (LayoutService)PortalBeanLocatorUtil.locate(LayoutService.class.getName() +
					".impl");
		}

		if (layoutPersistence == null) {
			layoutPersistence = (LayoutPersistence)PortalBeanLocatorUtil.locate(LayoutPersistence.class.getName() +
					".impl");
		}

		if (layoutFinder == null) {
			layoutFinder = (LayoutFinder)PortalBeanLocatorUtil.locate(LayoutFinder.class.getName() +
					".impl");
		}
	}

	protected DLFileEntryLocalService dlFileEntryLocalService;
	protected DLFileEntryService dlFileEntryService;
	protected DLFileEntryPersistence dlFileEntryPersistence;
	protected DLFileEntryFinder dlFileEntryFinder;
	protected DLFileEntryAndShortcutFinder dlFileEntryAndShortcutFinder;
	protected DLFileRankPersistence dlFileRankPersistence;
	protected DLFileRankFinder dlFileRankFinder;
	protected DLFileShortcutLocalService dlFileShortcutLocalService;
	protected DLFileShortcutService dlFileShortcutService;
	protected DLFileShortcutPersistence dlFileShortcutPersistence;
	protected DLFileShortcutFinder dlFileShortcutFinder;
	protected DLFileVersionLocalService dlFileVersionLocalService;
	protected DLFileVersionPersistence dlFileVersionPersistence;
	protected DLFolderLocalService dlFolderLocalService;
	protected DLFolderService dlFolderService;
	protected DLFolderPersistence dlFolderPersistence;
	protected CounterLocalService counterLocalService;
	protected CounterService counterService;
	protected LayoutLocalService layoutLocalService;
	protected LayoutService layoutService;
	protected LayoutPersistence layoutPersistence;
	protected LayoutFinder layoutFinder;
}