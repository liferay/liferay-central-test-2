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

package com.liferay.portal.kernel.deploy.sandbox;

import java.io.File;

/**
 * <a href="SandboxThemeListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Igor Spasic
 */
public class SandboxThemeListener implements SandboxListener {

	public static final String TYPE = "theme";

	public String getType() {
		return TYPE;
	}

	public void deploy(File dir) {
		System.out.println(">>> deploy theme " + dir);
	}

}