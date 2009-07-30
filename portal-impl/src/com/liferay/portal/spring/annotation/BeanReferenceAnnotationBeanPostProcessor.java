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

import java.beans.PropertyDescriptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.util.ReflectionUtils;

/**
 * <a href="BeanReferenceAnnotationBeanPostProcessor.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Michael Young
 */
public class BeanReferenceAnnotationBeanPostProcessor
	implements BeanFactoryAware, InstantiationAwareBeanPostProcessor,
		MergedBeanDefinitionPostProcessor {

	public Object postProcessAfterInitialization(Object bean, String beanName)
		throws BeansException {

		return bean;
	}

	public boolean postProcessAfterInstantiation(Object bean, String beanName)
		throws BeansException {

		InjectionMetadata injectionMetadata = findResourceMetadata(
			bean.getClass());

		try {
			injectionMetadata.injectFields(bean, beanName);
		}
		catch (Throwable t) {
			throw new BeanCreationException(
				beanName, "Injection of BeanReference fields failed", t);
		}

		return true;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName)
		throws BeansException {

		return bean;
	}

	public Object postProcessBeforeInstantiation(
			Class beanClass, String beanName)
		throws BeansException {

		return null;
	}

	public void postProcessMergedBeanDefinition(
			RootBeanDefinition beanDefinition, Class beanType,
			String beanName) {

		if (beanType == null) {
			return;
		}

		InjectionMetadata injectionMetadata = findResourceMetadata(beanType);

		injectionMetadata.checkConfigMembers(beanDefinition);
	}

	public PropertyValues postProcessPropertyValues(
			PropertyValues propertyValues,
			PropertyDescriptor[] propertyDescriptors, Object bean,
			String beanName)
		throws BeansException {

		InjectionMetadata metadata = findResourceMetadata(bean.getClass());

		try {
			metadata.injectMethods(bean, beanName, propertyValues);
		}
		catch (Throwable t) {
			throw new BeanCreationException(
				beanName, "Injection of BeanReference methods failed", t);
		}

		return propertyValues;
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		_beanFactory = beanFactory;
	}

	protected InjectionMetadata findResourceMetadata(Class clazz) {
		InjectionMetadata injectionMetadata = _injectionMetadataMap.get(clazz);

		if (injectionMetadata != null) {
			return injectionMetadata;
		}

		synchronized (_injectionMetadataMap) {
			injectionMetadata = _injectionMetadataMap.get(clazz);

			if (injectionMetadata == null) {
				injectionMetadata = new InjectionMetadata(clazz);

				BeanReferenceCallback callback = new BeanReferenceCallback(
					_beanFactory, injectionMetadata, clazz);

				ReflectionUtils.doWithFields(clazz, callback);
				ReflectionUtils.doWithMethods(clazz, callback);

				_injectionMetadataMap.put(clazz, injectionMetadata);
			}
		}

		return injectionMetadata;
	}

	private BeanFactory _beanFactory;

	private Map<Class<?>, InjectionMetadata> _injectionMetadataMap =
		new ConcurrentHashMap<Class<?>, InjectionMetadata>();

}