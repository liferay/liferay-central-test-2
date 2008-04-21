package ${beanLocatorUtilPackage};

import ${springUtilPackage}.SpringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContext;

public class BeanLocatorUtil {

	public static Object locate(String name) {
		ApplicationContext ctx = SpringUtil.getContext();

		if (_log.isDebugEnabled()) {
			_log.debug("Locating " + name);
		}

		return ctx.getBean(name);
	}

	private static Log _log = LogFactory.getLog(BeanLocatorUtil.class);

}