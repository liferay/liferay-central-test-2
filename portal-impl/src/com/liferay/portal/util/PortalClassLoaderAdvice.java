/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author Dennis Ju
 */
public class PortalClassLoaderAdvice {

	public Object setClassLoader(ProceedingJoinPoint proceedingJoinPoint)
		throws Throwable {

		ClassLoader currentClassLoader =
			Thread.currentThread().getContextClassLoader();

		Thread.currentThread().setContextClassLoader(
			this.getClass().getClassLoader());

		try {
			return proceedingJoinPoint.proceed();
		}
		finally {
			Thread.currentThread().setContextClassLoader(currentClassLoader);
		}
	}

}