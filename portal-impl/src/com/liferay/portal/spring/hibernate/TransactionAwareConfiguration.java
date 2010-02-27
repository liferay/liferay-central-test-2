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

package com.liferay.portal.spring.hibernate;

import java.lang.reflect.Proxy;

import org.hibernate.SessionFactory;
import org.hibernate.engine.SessionFactoryImplementor;

import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

/**
 * <a href="TransactionAwareConfiguration.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class TransactionAwareConfiguration extends LocalSessionFactoryBean {

	protected SessionFactory wrapSessionFactoryIfNecessary(
		SessionFactory sessionFactory) {

		// LEP-2996

		Class<?> sessionFactoryInterface = SessionFactory.class;

		if (sessionFactory instanceof SessionFactoryImplementor) {
			sessionFactoryInterface = SessionFactoryImplementor.class;
		}

		return (SessionFactory)Proxy.newProxyInstance(
			sessionFactoryInterface.getClassLoader(),
			new Class[] {sessionFactoryInterface},
			new SessionFactoryInvocationHandler(sessionFactory));
	}

}