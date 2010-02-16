/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.expando.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.service.ServiceContext;

import java.io.Serializable;

import java.util.Enumeration;
import java.util.Map;

/**
 * <a href="ExpandoBridge.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public interface ExpandoBridge {

	public void addAttribute(String name) throws PortalException;

	public void addAttribute(String name, int type) throws PortalException;

	public void addAttribute(String name, int type, Serializable defaultValue)
		throws PortalException;

	public Serializable getAttribute(String name);

	public Serializable getAttributeDefault(String name);

	public Enumeration<String> getAttributeNames();

	public UnicodeProperties getAttributeProperties(String name);

	public Map<String, Serializable> getAttributes();

	public int getAttributeType(String name);

	public String getClassName();

	public long getClassPK();

	public boolean hasAttribute(String name);

	public boolean isIndexEnabled();

	public void setAttribute(String name, Serializable value);

	public void setAttributeDefault(String name, Serializable defaultValue);

	public void setAttributeProperties(
		String name, UnicodeProperties properties);

	public void setAttributes(Map<String, Serializable> attributes);

	public void setAttributes(ServiceContext serviceContext);

	public void setIndexEnabled(boolean indexEnabled);

}