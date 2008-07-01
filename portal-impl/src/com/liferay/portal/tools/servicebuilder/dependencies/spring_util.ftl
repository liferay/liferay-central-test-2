<#include "copyright.txt" parse="false">


package ${springUtilPackage};

import org.springframework.context.ApplicationContext;

/**
 * <a href="SpringUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 *
 */
public class SpringUtil {

	public static ApplicationContext getContext() {
		return _applicationContext;
	}

	public static void initContext(ApplicationContext applicationContext) {
		String[] beanDefinitionNames =
			_applicationContext.getBeanDefinitionNames();

		for (String beanDefinitionName : beanDefinitionNames) {
			_applicationContext.getBean(beanDefinitionName);
		}
	}

	public static void setContext(ApplicationContext applicationContext) {
		_applicationContext = applicationContext;

		initContext(applicationContext);
	}

	private static ApplicationContext _applicationContext = null;

}