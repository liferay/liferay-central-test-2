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
		return _ctx;
	}

	public static void initContext(ApplicationContext ctx) {
		String[] beanDefinitionNames = _ctx.getBeanDefinitionNames();

		for (String beanDefinitionName : beanDefinitionNames) {
			_ctx.getBean(beanDefinitionName);
		}
	}

	public static void setContext(ApplicationContext ctx) {
		_ctx = ctx;

		initContext(ctx);
	}

	private static ApplicationContext _ctx = null;

}