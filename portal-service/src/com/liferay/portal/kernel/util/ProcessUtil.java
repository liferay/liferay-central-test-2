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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.exception.SystemException;

import java.io.IOException;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ProcessUtil {

	public static void close(Process p) {
		try {
			p.waitFor();
		}
		catch (InterruptedException ie) {
			ie.printStackTrace();
		}

		try {
			p.getInputStream().close();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}

		try {
			p.getOutputStream().close();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}

		try {
			p.getErrorStream().close();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static int getCurrentProcessId() throws SystemException {
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		String name = runtimeMXBean.getName();
		int index = name.indexOf(CharPool.AT);
		if (index == -1) {
			throw new SystemException("Fail to parse process name " + name);
		}
		String idString = name.substring(0, index);
		try {
			return Integer.parseInt(idString);
		}
		catch(NumberFormatException nfe) {
			throw new SystemException("Fail to parse process name " + name,
				nfe);
		}
	}

}