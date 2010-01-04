package com.ext.portlet.reports.service.base;

import com.ext.portlet.reports.service.ReportsEntryService;
import com.ext.portlet.reports.service.persistence.ReportsEntryPersistence;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterService;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.ResourceService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserService;
import com.liferay.portal.service.base.PrincipalBean;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;


public abstract class ReportsEntryServiceBaseImpl extends PrincipalBean
    implements ReportsEntryService {
    @BeanReference(name = "com.ext.portlet.reports.service.ReportsEntryService")
    protected ReportsEntryService reportsEntryService;
    @BeanReference(name = "com.ext.portlet.reports.service.persistence.ReportsEntryPersistence")
    protected ReportsEntryPersistence reportsEntryPersistence;
    @BeanReference(name = "com.liferay.counter.service.CounterLocalService")
    protected CounterLocalService counterLocalService;
    @BeanReference(name = "com.liferay.counter.service.CounterService")
    protected CounterService counterService;
    @BeanReference(name = "com.liferay.portal.service.ResourceLocalService")
    protected ResourceLocalService resourceLocalService;
    @BeanReference(name = "com.liferay.portal.service.ResourceService")
    protected ResourceService resourceService;
    @BeanReference(name = "com.liferay.portal.service.persistence.ResourcePersistence")
    protected ResourcePersistence resourcePersistence;
    @BeanReference(name = "com.liferay.portal.service.UserLocalService")
    protected UserLocalService userLocalService;
    @BeanReference(name = "com.liferay.portal.service.UserService")
    protected UserService userService;
    @BeanReference(name = "com.liferay.portal.service.persistence.UserPersistence")
    protected UserPersistence userPersistence;

    public ReportsEntryService getReportsEntryService() {
        return reportsEntryService;
    }

    public void setReportsEntryService(ReportsEntryService reportsEntryService) {
        this.reportsEntryService = reportsEntryService;
    }

    public ReportsEntryPersistence getReportsEntryPersistence() {
        return reportsEntryPersistence;
    }

    public void setReportsEntryPersistence(
        ReportsEntryPersistence reportsEntryPersistence) {
        this.reportsEntryPersistence = reportsEntryPersistence;
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

    protected void runSQL(String sql) throws SystemException {
        try {
            DB db = DBFactoryUtil.getDB();

            db.runSQL(sql);
        } catch (Exception e) {
            throw new SystemException(e);
        }
    }
}
