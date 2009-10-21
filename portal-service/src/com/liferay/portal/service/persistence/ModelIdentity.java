/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import java.io.Serializable;

/**
 * <a href="ModelIdentity.java.html"><b><i>View Source</i></b></a>
 * 
 * <p>
 * The model identity represents a single instance of an entity managed by
 * service builder and contains the entity class name, its primary key, even if
 * it is a composite key and optionally the name of the servlet context the
 * entity is loaded from, if it is used in a plugin or portlet rather than the
 * portal implementation.
 * </p>
 * 
 * @author Micha Kiener
 * 
 */
public interface ModelIdentity {
	
	/**
	 * @return the name of the plugin or portlet servlet context or
	 *         <code>null</code> to represent the portal implementation
	 */
	public String getContextName();
	
	/**
	 * @return the fully qualified class name of the entity
	 */
	public String getModelClassName();
	
	/**
	 * @return the primary key of the entity instance which can also be a
	 *         composite key
	 */
	public Serializable getPrimaryKey();
	
}
