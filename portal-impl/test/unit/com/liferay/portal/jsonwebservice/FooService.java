/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.jsonwebservice;

import com.liferay.portal.service.ServiceContext;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * @author Igor Spasic
 */
public class FooService {

	public static FooData getFooData(int id) {
		FooData fooData = new FooDataImpl();

		fooData.setId(id);

		return fooData;
	}

	public static String hello() {
		return "world";
	}

	public static String helloWorld(Integer userId, String worldName) {
		return "Welcome " + userId + " to " + worldName;
	}

	public static String hey(
		Calendar calendar, long[] userIds, List<Locale> locales) {

		return calendar.get(Calendar.YEAR) + ", " + userIds[0] + '/' +
			userIds.length + ", " + locales.get(0) + '/' + locales.size();
	}

	public static String methodOne(long id, long nameId) {
		return "m-2";
	}

	public static String methodOne(long id, long nameId, String subname) {
		return "m-3";
	}

	public static String methodOne(long id, String name) {
		return "m-1";
	}

	public static String nullLover(String name, int number) {
		if (name == null) {
			return "null!";
		}

		return '[' + name + '|' + number + ']';
	}

	public static String srvcctx(ServiceContext serviceContext) {
		Class<?> clazz = serviceContext.getClass();

		return clazz.getName();
	}

	public static String use1(FooDataImpl fooData) {
		return "using #1: " + fooData.getValue();
	}

	public static String use2(FooData fooData) {
		return "using #2: " + fooData.getValue();
	}

}