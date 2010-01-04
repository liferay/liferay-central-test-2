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

package com.liferay.portal.spring.annotation;

import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

/**
 * <a href="BeanReferenceAnnotationBeanPostProcessor.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Michael Young
 * @author Shuyang Zhou
 */
public class BeanReferenceAnnotationBeanPostProcessor
	implements BeanFactoryAware, BeanPostProcessor {

	public Object postProcessAfterInitialization(Object bean, String beanName)
		throws BeansException {

		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName)
		throws BeansException {

		_autoInject(bean, beanName, bean.getClass());

		return bean;
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		_beanFactory = beanFactory;
	}

	private void _autoInject(
		Object targetBean, String targetBeanName, Class<?> beanClass) {

		if ((beanClass == null) || beanClass.isInterface()) {
			return;
		}

		String className = beanClass.getName();

		if (className.equals(_JAVA_LANG_OBJECT) ||
			className.startsWith(_ORG_SPRINGFRAMEWORK)) {

			return;
		}

		Field[] fields = beanClass.getDeclaredFields();

		for (Field field : fields) {
			BeanReference beanReference = field.getAnnotation(
				BeanReference.class);

			if (beanReference == null) {
				continue;
			}

			String referencedBeanName = beanReference.name();

			Object referencedBean = _beans.get(referencedBeanName);

			if (referencedBean == null) {
				try {
					referencedBean = _beanFactory.getBean(referencedBeanName);
				}
				catch (NoSuchBeanDefinitionException nsbde) {
					referencedBean = PortalBeanLocatorUtil.locate(
						referencedBeanName);
				}

				_beans.put(referencedBeanName, referencedBean);
			}

			ReflectionUtils.makeAccessible(field);

			try {
				field.set(targetBean, referencedBean);
			}
			catch (Throwable t) {
				throw new BeanCreationException(
					targetBeanName, "Could not inject BeanReference fields",
					t);
			}
		}

		_autoInject(targetBean, targetBeanName, beanClass.getSuperclass());

		return;
	}

	private static String _JAVA_LANG_OBJECT = "java.lang.Object";

	private static String _ORG_SPRINGFRAMEWORK = "org.springframework";

	private BeanFactory _beanFactory;
	private Map<String, Object> _beans = new HashMap<String, Object>();

}