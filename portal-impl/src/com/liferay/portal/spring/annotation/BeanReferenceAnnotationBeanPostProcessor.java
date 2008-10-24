/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import java.beans.PropertyDescriptor;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

/**
 * <a href="BeanReferenceAnnotationBeanPostProcessor.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 *
 */
public class BeanReferenceAnnotationBeanPostProcessor implements
		InstantiationAwareBeanPostProcessor, MergedBeanDefinitionPostProcessor,
		BeanFactoryAware {

	protected InjectionMetadata findResourceMetadata(Class clazz) {
		InjectionMetadata metadata = _injectionMetadataCache.get(clazz);

		if (metadata == null) {
			synchronized (_injectionMetadataCache) {
				metadata = _injectionMetadataCache.get(clazz);

				if (metadata == null) {
					metadata = new InjectionMetadata(clazz);

					BeanReferenceCallback callback = new BeanReferenceCallback(
							metadata, clazz);

					ReflectionUtils.doWithFields(clazz, callback);
					ReflectionUtils.doWithMethods(clazz, callback);

					_injectionMetadataCache.put(clazz, metadata);
				}
			}
		}

		return metadata;
	}

	public Object postProcessAfterInitialization(Object bean, String beanName)
		throws BeansException {

		return bean;
	}
	
	public boolean postProcessAfterInstantiation(Object bean, String beanName)
		throws BeansException {

		InjectionMetadata metadata = findResourceMetadata(bean.getClass());
	
		try {
			metadata.injectFields(bean, beanName);
		} catch (Throwable ex) {
			throw new BeanCreationException(beanName,
					"Injection of BeanReference fields failed", ex);
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
	
		if (beanType != null) {
			InjectionMetadata metadata = findResourceMetadata(beanType);	
			metadata.checkConfigMembers(beanDefinition);
		}
	}

	public PropertyValues postProcessPropertyValues(
			PropertyValues pvs, PropertyDescriptor[] pds, Object bean, 
			String beanName) 
		throws BeansException {

		InjectionMetadata metadata = findResourceMetadata(bean.getClass());
	
		try {
			metadata.injectMethods(bean, beanName, pvs);
		} catch (Throwable ex) {
			throw new BeanCreationException(
				beanName, "Injection of BeanReference methods failed", ex);
		}
		
		return pvs;
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		_beanFactory = beanFactory;
	}

	protected class BeanReferenceElement extends
			InjectionMetadata.InjectedElement {

		public BeanReferenceElement(Member member, PropertyDescriptor pd) {
			super(member, pd);

			AnnotatedElement ae = (AnnotatedElement) member;
			BeanReference reference = ae.getAnnotation(BeanReference.class);

			_name = reference.name();
		}

		protected Object getResourceToInject(
				Object target, String requestingBeanName) {

			return _beanFactory.getBean(_name, getResourceType());
		}

		private String _name;

	}

	protected class BeanReferenceCallback implements
			ReflectionUtils.FieldCallback, ReflectionUtils.MethodCallback {

		public BeanReferenceCallback(
				InjectionMetadata injectionMetadata, Class clazz) {

			_injectionMetadata = injectionMetadata;
			_class = clazz;
		}

		public void doWith(Field field) {
			if (field.isAnnotationPresent(BeanReference.class)) {
				if (Modifier.isStatic(field.getModifiers())) {
					throw new IllegalStateException(
						"@BeanReference annotation is not supported on "
						+ "static fields");
				}

				_injectionMetadata.addInjectedField(
					new BeanReferenceElement(field, null));
			}
		}

		public void doWith(Method method) {
			if (method.isAnnotationPresent(BeanReference.class)
					&& method.equals(ClassUtils.getMostSpecificMethod(method,
							_class))) {

				if (Modifier.isStatic(method.getModifiers())) {
					throw new IllegalStateException(
						"@BeanReference annotation is not supported on "
						+ "static methods");
				}

				Class[] paramTypes = method.getParameterTypes();

				if (paramTypes.length != 1) {
					throw new IllegalStateException(
						"@BeanReference annotation requires a single-arg "
						+ "method: " + method);
				}

				PropertyDescriptor pd = BeanUtils.findPropertyForMethod(method);

				_injectionMetadata.addInjectedMethod(
						new BeanReferenceElement(method, pd));
			}
		}

		private InjectionMetadata _injectionMetadata;
		private Class _class;
	}

	private BeanFactory _beanFactory;

	private Map<Class<?>, InjectionMetadata> _injectionMetadataCache = 
		new ConcurrentHashMap<Class<?>, InjectionMetadata>();
}