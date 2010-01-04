package com.ext.portlet.reports.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;


/**
 * <a href="ReportsEntryServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link ReportsEntryService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ReportsEntryService
 * @generated
 */
public class ReportsEntryServiceUtil {
    private static ReportsEntryService _service;

    public static ReportsEntryService getService() {
        if (_service == null) {
            _service = (ReportsEntryService) PortalBeanLocatorUtil.locate(ReportsEntryService.class.getName());
        }

        return _service;
    }

    public void setService(ReportsEntryService service) {
        _service = service;
    }
}
