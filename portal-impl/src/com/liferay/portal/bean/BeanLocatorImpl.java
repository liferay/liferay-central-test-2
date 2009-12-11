/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.bean;

import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.BeanLocatorException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.lang.reflect.Proxy;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationContext;

/**
 * <a href="BeanLocatorImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class BeanLocatorImpl implements BeanLocator {

	public static final String VELOCITY_SUFFIX = ".velocity";

	public BeanLocatorImpl(
		ClassLoader classLoader, ApplicationContext applicationContext) {

		_classLoader = classLoader;
		_applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return _applicationContext;
	}

	public ClassLoader getClassLoader() {
		return _classLoader;
	}

	public Object locate(String name) throws BeanLocatorException {
		if (_log.isDebugEnabled()) {
			_log.debug("Locating " + name);
		}

		if (name.endsWith(VELOCITY_SUFFIX)) {
			Object bean = _velocityBeans.get(name);

			if (bean == null) {
				String originalName = name.substring(
					0, name.length() - VELOCITY_SUFFIX.length());

				bean = _applicationContext.getBean(originalName);

				Class<?>[] interfaces = bean.getClass().getInterfaces();

				List<Class<?>> interfacesList = ListUtil.fromArray(interfaces);

				Iterator<Class<?>> itr = interfacesList.iterator();

				while (itr.hasNext()) {
					Class<?> classObj = itr.next();

					try {
						_classLoader.loadClass(classObj.getName());
					}
					catch (Exception e) {
						itr.remove();
					}
				}

				bean = Proxy.newProxyInstance(
					_classLoader,
					interfacesList.toArray(new Class<?>[interfacesList.size()]),
					new VelocityBeanHandler(bean, _classLoader));

				_velocityBeans.put(name, bean);
			}

			return bean;
		}
		else {
			return _applicationContext.getBean(name);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(BeanLocatorImpl.class);

	private ClassLoader _classLoader;
	private ApplicationContext _applicationContext;
	private Map<String, Object> _velocityBeans =
		new ConcurrentHashMap<String, Object>();

}