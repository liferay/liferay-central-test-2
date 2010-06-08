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

package com.liferay.portal.cache;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.cache.BufferedCounter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.util.ConcurrentCounter;
import com.liferay.util.SocialEquity;

import java.lang.reflect.Method;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * <a href="CounterCacheAdvice.java.html"><b><i>View Source</i></b></a>
 *
 * @author Zsolt Berentey
 */
@Aspect
public class CounterCacheAdvice {

	@Before("execution(@com.liferay.portal.kernel.cache.BufferedCounter * *(" +
		"*,*)) && args(id, count) && @annotation(anno)")
	public void bufferCounter(
		JoinPoint joinPoint, Object id, Object count, BufferedCounter anno) {

		incrementCounter(joinPoint, id, (Number)count, anno);
	}

	@Before("execution(@com.liferay.portal.kernel.cache.BufferedCounter * *(" +
	"*,*,*)) && args(id, k, b) && @annotation(anno)")
	public void bufferCounter(
		JoinPoint joinPoint, Object id, double k, double b,
		BufferedCounter anno) {

		incrementCounter(joinPoint, id, new SocialEquity(k,b), anno);
	}

	public void flushCaches() {

		for (Map.Entry<String, Map<Object, ConcurrentCounter>>
			counterCacheEntry : _counterCaches.entrySet()) {

			String key = counterCacheEntry.getKey();

			String serviceName = StringUtil.extractFirst(key, StringPool.POUND);

			String updateMethodName = StringUtil.extractLast(
				key, StringPool.POUND);

			if ("none".equalsIgnoreCase(updateMethodName)) {
				continue;
			}

			Object service = PortalBeanLocatorUtil.locate(serviceName);

			for (Method method : service.getClass().getMethods()) {
				if (method.getName().equalsIgnoreCase(updateMethodName)) {
					for (Map.Entry<Object, ConcurrentCounter> counterEntry :
						counterCacheEntry.getValue().entrySet()) {

						Number count = counterEntry.getValue().getValue();

						if (count.doubleValue() > 0) {
							Object[] args = null;

							if (count instanceof SocialEquity) {
								args = new Object[] {
									counterEntry.getKey(),
									((SocialEquity)count).getK(),
									((SocialEquity)count).getB()
								};
							} else {
								args = new Object[] {
									counterEntry.getKey(),
									count
								};
							}

							try {
								method.invoke(service, args);
							} catch (Exception e) {
								_log.error("Cannot flush buffered counter " +
									"value to the database", e);
							}

							counterEntry.getValue().decrement(count);
						}
					}
				}
			}
		}
	}

	public static Number getCounterValue(String cacheKey, Object id) {
		Map<Object, ConcurrentCounter> counterCache =
			_counterCaches.get(cacheKey);

		if (counterCache == null) {
			return null;
		}

		ConcurrentCounter counter = counterCache.get(id);

		if (counter == null) {
			return null;
		}

		return counter.getValue();
	}

	private String _buildCacheKey(
		JoinPoint joinPoint, BufferedCounter annotation) {

		String methodName = joinPoint.getSignature().getName();

		String updateMethodName = annotation.flushMethod();

		if ("none".equalsIgnoreCase(updateMethodName)) {
			methodName = methodName + StringPool.POUND + "none";
			updateMethodName = "";
		}

		if ("".equals(updateMethodName)) {
			if (methodName.startsWith("increment")) {
				updateMethodName = "update" + methodName.substring(9);
			}
		}

		String serviceName = joinPoint.getTarget().getClass().getName();

		serviceName = StringUtil.replace(serviceName, ".impl.",".");
		serviceName = StringUtil.replaceLast(serviceName, "Impl", "");

		return serviceName + StringPool.POUND + updateMethodName;
	}

	private void incrementCounter(
		JoinPoint joinPoint, Object id, Number count, BufferedCounter anno) {

		String cacheKey = _buildCacheKey(joinPoint, anno);

		Map<Object, ConcurrentCounter> counterCache =
			_counterCaches.get(cacheKey);

		if (counterCache == null) {
			counterCache = new ConcurrentHashMap<Object, ConcurrentCounter>();

			Map<Object, ConcurrentCounter> value =
				((ConcurrentHashMap<String, Map<Object, ConcurrentCounter>>)
					_counterCaches).putIfAbsent(cacheKey, counterCache);

			if (value != null) {
				counterCache = value;
			}
		}

		ConcurrentCounter counter = counterCache.get(id);

		if (counter == null) {
			counter = new ConcurrentCounter(count);
			ConcurrentCounter value =
				((ConcurrentHashMap<Object, ConcurrentCounter>)
					counterCache).putIfAbsent(id, counter);

			if (value != null) {
				counter = value;
			} else {
				return;
			}
		}

		counter.increment(count);
	}

	private static Map<String, Map<Object, ConcurrentCounter>> _counterCaches =
		new ConcurrentHashMap<String, Map<Object, ConcurrentCounter>>();

	private static Log _log = LogFactoryUtil.getLog(CounterCacheAdvice.class);

}