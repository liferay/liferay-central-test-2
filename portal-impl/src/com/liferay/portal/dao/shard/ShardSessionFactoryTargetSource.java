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

package com.liferay.portal.dao.shard;

import com.liferay.portal.util.PropsValues;

import java.util.Map;

import org.hibernate.SessionFactory;

import org.springframework.aop.TargetSource;

/**
 * @author Michael Young
 */
public class ShardSessionFactoryTargetSource implements TargetSource {

	public SessionFactory getSessionFactory() {
		return _sessionFactory.get();
	}

	public Object getTarget() throws Exception {
		return getSessionFactory();
	}

	public Class<?> getTargetClass() {
		return _sessionFactories.get(PropsValues.SHARD_DEFAULT_NAME).getClass();
	}

	public boolean isStatic() {
		return false;
	}

	public void releaseTarget(Object target) throws Exception {
	}

	public void setSessionFactory(String shardName) {
		_sessionFactory.set(_sessionFactories.get(shardName));
	}

	public void setSessionFactories(
		Map<String, SessionFactory> sessionFactories) {

		_sessionFactories = sessionFactories;
	}

	private static ThreadLocal<SessionFactory> _sessionFactory =
		new ThreadLocal<SessionFactory>() {

		protected SessionFactory initialValue() {
			return _sessionFactories.get(PropsValues.SHARD_DEFAULT_NAME);
		}

	};

	private static Map<String, SessionFactory> _sessionFactories;

}