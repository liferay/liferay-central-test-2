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

package com.liferay.portal.kernel.bean;

/**
 * <a href="BeanProperties.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public interface BeanProperties {

	public void copyProperties(Object source, Object target);

	public void copyProperties(Object source, Object target, Class<?> editable);

	public void copyProperties(
		Object source, Object target, String[] ignoreProperties);

	public boolean getBoolean(Object bean, String param);

	public boolean getBoolean(Object bean, String param, boolean defaultValue);

	public double getDouble(Object bean, String param);

	public double getDouble(Object bean, String param, double defaultValue);

	public int getInteger(Object bean, String param);

	public int getInteger(Object bean, String param, int defaultValue);

	public long getLong(Object bean, String param);

	public long getLong(Object bean, String param, long defaultValue);

	public Object getObject(Object bean, String param);

	public Object getObject(Object bean, String param, Object defaultValue);

	public String getString(Object bean, String param);

	public String getString(Object bean, String param, String defaultValue);

	public void setProperty(Object bean, String param, Object value);

}