<#include "copyright.txt" parse="false">


package ${springUtilPackage};

import ${propsUtilPackage}.PropsUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * <a href="LazyClassPathApplicationContext.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * <p>
 * This web application context will first load bean definitions in the
 * contextConfigLocation parameter in web.xml. Then, the context will load bean
 * definitions specified by the property "spring.configs" in portal.properties.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 *
 */
public class LazyClassPathApplicationContext extends XmlWebApplicationContext {

	protected void loadBeanDefinitions(XmlBeanDefinitionReader reader)
		throws BeansException {

		try {
			super.loadBeanDefinitions(reader);
		}
		catch (Exception e) {
			_log.warn(e);
		}

		reader.setResourceLoader(new DefaultResourceLoader());

		String[] configLocations = PropsUtil.getArray(PropsUtil.SPRING_CONFIGS);

		for (int i = 0; i < configLocations.length; i++) {
			try {
				reader.loadBeanDefinitions(configLocations[i]);
			}
			catch (Exception e) {
				_log.warn(e);
			}
		}
	}

	private static Log _log =
		LogFactory.getLog(LazyClassPathApplicationContext.class);

}