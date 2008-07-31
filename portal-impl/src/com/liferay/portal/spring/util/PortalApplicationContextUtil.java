/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.spring.util;

import com.liferay.portal.spring.context.ArrayApplicationContext;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

import org.springframework.context.ApplicationContext;

/**
 * <a href="PortalApplicationContextUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael Young
 *
 */
public class PortalApplicationContextUtil {

	public static ApplicationContext getContext() {
		if (_applicationContext == null) {
			return _parentApplicationContext;
		}
		else {
			return _applicationContext;
		}
	}

	public static void initContext() {
		loadStandaloneContext();
		SpringUtil.initContext(_applicationContext);
	}

	public static void loadStandaloneContext() {
		_parentApplicationContext = new ArrayApplicationContext(
			PropsUtil.getArray(PropsKeys.SPRING_PARENT_CONFIGS));
			
		_applicationContext = new ArrayApplicationContext(
			PropsUtil.getArray(PropsKeys.SPRING_CONFIGS), 
			_parentApplicationContext);		
	}
	
	public static void setContext(ApplicationContext applicationContext) {
		_applicationContext = applicationContext;

		SpringUtil.initContext(applicationContext);
	}
	
	public static void setParentContext(
		ApplicationContext parentApplicationContext) {

		_parentApplicationContext = parentApplicationContext;	
	}

	private static ApplicationContext _applicationContext = null;
	
	private static ApplicationContext _parentApplicationContext = null;

}