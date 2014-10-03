/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.fabric.netty.handlers;

import com.liferay.portal.fabric.netty.rpc.handlers.NettyRPCChannelHandler;
import com.liferay.portal.kernel.concurrent.AsyncBroker;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.runners.AspectJMockingNewClassLoaderJUnitTestRunner;

import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.Attribute;

import java.io.Serializable;

import java.util.concurrent.atomic.AtomicLong;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Shuyang Zhou
 */
@RunWith(AspectJMockingNewClassLoaderJUnitTestRunner.class)
public class NettyChannelAttributesTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@AdviseWith(adviceClasses = AttributeAdvice.class)
	@Test
	public void testConcurrentGetAsyncBroker() {
		AsyncBroker<Long, Serializable> asyncBroker =
			new AsyncBroker<Long, Serializable>();

		AttributeAdvice.setConcurrentValue(asyncBroker);

		Assert.assertSame(
			asyncBroker,
				NettyChannelAttributes.getAsyncBroker(_embeddedChannel));

		// Get from cache

		Assert.assertSame(
			asyncBroker,
			NettyChannelAttributes.getAsyncBroker(_embeddedChannel));
	}

	@AdviseWith(adviceClasses = AttributeAdvice.class)
	@Test
	public void testConcurrentNextId() {
		AttributeAdvice.setConcurrentValue(new AtomicLong());

		testNextId();
	}

	@Test
	public void testConstructor() {
		new NettyChannelAttributes();
	}

	@Test
	public void testGetAsyncBroker() {
		AsyncBroker<Long, Serializable> asyncBroker =
			NettyChannelAttributes.getAsyncBroker(_embeddedChannel);

		Assert.assertSame(
			asyncBroker,
			NettyChannelAttributes.getAsyncBroker(_embeddedChannel));
	}

	@Test
	public void testNextId() {
		Assert.assertEquals(0, NettyChannelAttributes.nextId(_embeddedChannel));
		Assert.assertEquals(1, NettyChannelAttributes.nextId(_embeddedChannel));
	}

	@Aspect
	public static class AttributeAdvice {

		public static void setConcurrentValue(Object concurrentValue) {
			_concurrentValue = concurrentValue;
		}

		@Around(
			"execution(public Object io.netty.util.Attribute.setIfAbsent(" +
				"Object))")
		public Object setIfAbsent(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			Attribute<Object> attribute =
				(Attribute<Object>)proceedingJoinPoint.getThis();

			attribute.set(_concurrentValue);

			return proceedingJoinPoint.proceed();
		}

		private static Object _concurrentValue;

	}

	private final EmbeddedChannel _embeddedChannel = new EmbeddedChannel(
		NettyRPCChannelHandler.INSTANCE);

}