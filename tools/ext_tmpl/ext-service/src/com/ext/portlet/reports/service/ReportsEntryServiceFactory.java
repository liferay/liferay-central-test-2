package com.ext.portlet.reports.service;

public class ReportsEntryServiceFactory {
    private static final String _FACTORY = ReportsEntryServiceFactory.class.getName();
    private static final String _TX_IMPL = ReportsEntryService.class.getName() +
        ".transaction";
    private static ReportsEntryServiceFactory _factory;
    private static ReportsEntryService _txImpl;
    private ReportsEntryService _service;

    public static ReportsEntryService getService() {
        return _getFactory()._service;
    }

    public static ReportsEntryService getTxImpl() {
        if (_txImpl == null) {
            _txImpl = (ReportsEntryService) com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_TX_IMPL);
        }

        return _txImpl;
    }

    public void setService(ReportsEntryService service) {
        _service = service;
    }

    private static ReportsEntryServiceFactory _getFactory() {
        if (_factory == null) {
            _factory = (ReportsEntryServiceFactory) com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_FACTORY);
        }

        return _factory;
    }
}
