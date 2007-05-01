/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.ClassNameMapper;
import com.liferay.portal.service.base.ClassNameMapperLocalServiceBaseImpl;
import com.liferay.portal.service.persistence.ClassNameMapperUtil;
import com.liferay.util.CollectionFactory;

import java.util.Map;

/**
 * <a href="ClassNameMapperLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ClassNameMapperLocalServiceImpl
	extends ClassNameMapperLocalServiceBaseImpl {

	public ClassNameMapper getClassNameMapper(long classNameMapperId)
		throws PortalException, SystemException {

		return ClassNameMapperUtil.findByPrimaryKey(classNameMapperId);
	}

	public ClassNameMapper getClassNameMapper(String className)
		throws PortalException, SystemException {

		// Always cache the class name wrapper. This table exists to improve
		// performance. Create the class name wrapper if one does not exist.

		ClassNameMapper classNameMapper =
			(ClassNameMapper)_classNameMappers.get(className);

		if (classNameMapper == null) {
			classNameMapper = ClassNameMapperUtil.fetchByClassName(className);

			if (classNameMapper == null) {
				long classNameMapperId = CounterLocalServiceUtil.increment();

				classNameMapper = ClassNameMapperUtil.create(classNameMapperId);

				classNameMapper.setClassName(className);

				ClassNameMapperUtil.update(classNameMapper);
			}

			_classNameMappers.put(className, classNameMapper);
		}

		return classNameMapper;
	}

	private static Map _classNameMappers = CollectionFactory.getSyncHashMap();

}