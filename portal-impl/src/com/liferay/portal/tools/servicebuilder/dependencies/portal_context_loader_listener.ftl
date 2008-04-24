<#include "copyright.txt" parse="false">


package ${springUtilPackage};

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * <a href="PortalContextLoaderListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 *
 */
public class PortalContextLoaderListener extends ContextLoaderListener {

	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);

		ServletContext servletContext = event.getServletContext();

		ApplicationContext applicationContext =
			WebApplicationContextUtils.getWebApplicationContext(servletContext);

		SpringUtil.setContext(applicationContext);
	}

}