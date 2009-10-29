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

import com.liferay.portal.kernel.annotation.AnnotationHandler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * <a href="AnnotationScannerBeanPostProcessor.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Bruno Farache
 */
public class AnnotationScannerBeanPostProcessor implements BeanPostProcessor {

	public Object postProcessAfterInitialization(Object bean, String beanName)
		throws BeansException {

		try {
			if (!beanName.endsWith("LocalService.impl")) {
				return bean;
			}

			Object modifiedBean = null;

			for (AnnotationHandler annotationHandler : _annotationHandlers) {
				Class beanClass = bean.getClass();
				Class annotationClass = annotationHandler.getAnnotationClass();

				Annotation annotation =
					beanClass.getAnnotation(annotationClass);

				if (annotation != null) {
					try {
						modifiedBean = annotationHandler.handleClassAnnotation(
							bean, annotation);
					}
					catch (Exception e) {
						_log.error(
							"Couldn't handle class annotation " +
								annotationClass.getName() + "for bean" +
									beanName, e);
					}
				}

				Method[] methods = beanClass.getMethods();

				for (Method method : methods) {
					annotation = method.getAnnotation(annotationClass);

					if (annotation != null) {
						try {
							modifiedBean =
								annotationHandler.handleMethodAnnotation(
									bean, method, annotation);
						}
						catch (Exception e) {
							_log.error(
								"Couldn't handle method annotation " +
									annotationClass.getName() + "for bean" +
										beanName, e);
						}
					}
				}
			}

			if (modifiedBean != null) {
				bean = modifiedBean;
			}
		}
		catch (Exception e) {
		}
		finally {
			return bean;
		}
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName)
		throws BeansException {

		return bean;
	}

	public void setAnnotationHandlers(
		List<AnnotationHandler> annotationHandlers) {

		_annotationHandlers = annotationHandlers;
	}

	private static Log _log =
		LogFactoryUtil.getLog(AnnotationScannerBeanPostProcessor.class);

	private List<AnnotationHandler> _annotationHandlers;

}