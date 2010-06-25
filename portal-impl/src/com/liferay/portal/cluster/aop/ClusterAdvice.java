/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.cluster.aop;

import com.liferay.portal.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.annotation.Cluster;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.util.MethodTargetClassKey;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.spring.aop.AnnotationChainableMethodAdvice;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

/**
 * <a href="ClusterAdvice.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class ClusterAdvice extends AnnotationChainableMethodAdvice<Cluster> {

	public void afterReturning(MethodInvocation methodInvocation, Object result)
		throws Throwable {
		if (ClusterInvokeThreadLocal.isClusterInvoke()) {
			return;
		}

		MethodTargetClassKey methodTargetClassKey = buildMethodTargetClassKey(
			methodInvocation);

		Cluster cluster = findAnnotation(methodTargetClassKey);

		if (cluster == _nullCluster) {
			return;
		}

		Method method = methodTargetClassKey.getMethod();
		Method utilClassMethod = _getUtilClassMethod(method);

		MethodWrapper methodWrapper = new MethodWrapper(utilClassMethod,
			methodInvocation.getArguments());

		ClusterExecutorUtil.executeMulticastCall(methodWrapper, true);
	}

	public Class<Cluster> getAnnotationClass() {
		return Cluster.class;
	}

	public Cluster getNullAnnotation() {
		return _nullCluster;
	}

	private Method _getUtilClassMethod(Method method) throws Exception {
		String utilClassName =
			method.getDeclaringClass().getName().concat("Util");
		Class<?> utilClass = Thread.currentThread().getContextClassLoader().
			loadClass(utilClassName);

		return utilClass.getMethod(method.getName(),
			method.getParameterTypes());
	}

	private static Cluster _nullCluster =
		new Cluster() {

			public Class<? extends Annotation> annotationType() {
				return Cluster.class;
			}

		};

}