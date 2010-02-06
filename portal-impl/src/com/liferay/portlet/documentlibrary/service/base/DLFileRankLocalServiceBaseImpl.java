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

package com.liferay.portlet.documentlibrary.service.base;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.LayoutService;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.persistence.LayoutFinder;
import com.liferay.portal.service.persistence.LayoutPersistence;
import com.liferay.portal.service.persistence.ResourceFinder;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;

import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryService;
import com.liferay.portlet.documentlibrary.service.DLFileRankLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderService;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionPersistence;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderFinder;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence;

import java.util.List;

/**
 * <a href="DLFileRankLocalServiceBaseImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class DLFileRankLocalServiceBaseImpl
	implements DLFileRankLocalService {
	public DLFileRank addDLFileRank(DLFileRank dlFileRank)
		throws SystemException {
		dlFileRank.setNew(true);

		return dlFileRankPersistence.update(dlFileRank, false);
	}

	public DLFileRank createDLFileRank(long fileRankId) {
		return dlFileRankPersistence.create(fileRankId);
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

	public DLFileRank updateDLFileRank(DLFileRank dlFileRank, boolean merge)
		throws SystemException {
		dlFileRank.setNew(false);

		return dlFileRankPersistence.update(dlFileRank, merge);
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

	public DLFileRankLocalService getDLFileRankLocalService() {
		return dlFileRankLocalService;
	}

	public void setDLFileRankLocalService(
		DLFileRankLocalService dlFileRankLocalService) {
		this.dlFileRankLocalService = dlFileRankLocalService;
	}

	public DLFileRankPersistence getDLFileRankPersistence() {
		return dlFileRankPersistence;
	}

	public void setDLFileRankPersistence(
		DLFileRankPersistence dlFileRankPersistence) {
		this.dlFileRankPersistence = dlFileRankPersistence;
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

	public DLFolderFinder getDLFolderFinder() {
		return dlFolderFinder;
	}

	public void setDLFolderFinder(DLFolderFinder dlFolderFinder) {
		this.dlFolderFinder = dlFolderFinder;
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

	public ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	public void setResourceLocalService(
		ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	public ResourceService getResourceService() {
		return resourceService;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public ResourcePersistence getResourcePersistence() {
		return resourcePersistence;
	}

	public void setResourcePersistence(ResourcePersistence resourcePersistence) {
		this.resourcePersistence = resourcePersistence;
	}

	public ResourceFinder getResourceFinder() {
		return resourceFinder;
	}

	public void setResourceFinder(ResourceFinder resourceFinder) {
		this.resourceFinder = resourceFinder;
	}

	public UserLocalService getUserLocalService() {
		return userLocalService;
	}

	public void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public UserFinder getUserFinder() {
		return userFinder;
	}

	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	protected void runSQL(String sql) throws SystemException {
		try {
			DB db = DBFactoryUtil.getDB();

			db.runSQL(sql);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService")
	protected DLFileEntryLocalService dlFileEntryLocalService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFileEntryService")
	protected DLFileEntryService dlFileEntryService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryPersistence")
	protected DLFileEntryPersistence dlFileEntryPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryFinder")
	protected DLFileEntryFinder dlFileEntryFinder;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFileRankLocalService")
	protected DLFileRankLocalService dlFileRankLocalService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileRankPersistence")
	protected DLFileRankPersistence dlFileRankPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalService")
	protected DLFileShortcutLocalService dlFileShortcutLocalService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFileShortcutService")
	protected DLFileShortcutService dlFileShortcutService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutPersistence")
	protected DLFileShortcutPersistence dlFileShortcutPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFileVersionLocalService")
	protected DLFileVersionLocalService dlFileVersionLocalService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFileVersionPersistence")
	protected DLFileVersionPersistence dlFileVersionPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFolderLocalService")
	protected DLFolderLocalService dlFolderLocalService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.DLFolderService")
	protected DLFolderService dlFolderService;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFolderPersistence")
	protected DLFolderPersistence dlFolderPersistence;
	@BeanReference(name = "com.liferay.portlet.documentlibrary.service.persistence.DLFolderFinder")
	protected DLFolderFinder dlFolderFinder;
	@BeanReference(name = "com.liferay.counter.service.CounterLocalService")
	protected CounterLocalService counterLocalService;
	@BeanReference(name = "com.liferay.counter.service.CounterService")
	protected CounterService counterService;
	@BeanReference(name = "com.liferay.portal.service.LayoutLocalService")
	protected LayoutLocalService layoutLocalService;
	@BeanReference(name = "com.liferay.portal.service.LayoutService")
	protected LayoutService layoutService;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutPersistence")
	protected LayoutPersistence layoutPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.LayoutFinder")
	protected LayoutFinder layoutFinder;
	@BeanReference(name = "com.liferay.portal.service.ResourceLocalService")
	protected ResourceLocalService resourceLocalService;
	@BeanReference(name = "com.liferay.portal.service.ResourceService")
	protected ResourceService resourceService;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
	protected ResourcePersistence resourcePersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.ResourceFinder")
	protected ResourceFinder resourceFinder;
	@BeanReference(name = "com.liferay.portal.service.UserLocalService")
	protected UserLocalService userLocalService;
	@BeanReference(name = "com.liferay.portal.service.UserService")
	protected UserService userService;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
	protected UserPersistence userPersistence;
	@BeanReference(name = "com.liferay.portal.service.persistence.UserFinder")
	protected UserFinder userFinder;
}