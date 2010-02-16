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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.annotation.Propagation;
import com.liferay.portal.kernel.annotation.Transactional;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portal.model.impl.ClassNameImpl;
import com.liferay.portal.service.base.ClassNameLocalServiceBaseImpl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="ClassNameLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ClassNameLocalServiceImpl extends ClassNameLocalServiceBaseImpl {

	public ClassName addClassName(String value) throws SystemException {
		long classNameId = counterLocalService.increment();

		ClassName className = classNamePersistence.create(classNameId);

		className.setValue(value);

		classNamePersistence.update(className, false);

		return className;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void checkClassNames() throws SystemException {
		if (_classNames.isEmpty()) {
			List<ClassName> classNames = classNamePersistence.findAll();

			for (ClassName className : classNames) {
				_classNames.put(className.getValue(), className);
			}
		}

		List<String> models = ModelHintsUtil.getModels();

		for (String model : models) {
			getClassName(model);
		}
	}

	public ClassName getClassName(long classNameId)
		throws PortalException, SystemException {

		return classNamePersistence.findByPrimaryKey(classNameId);
	}

	public ClassName getClassName(String value) throws SystemException {
		if (Validator.isNull(value)) {
			return _nullClassName;
		}

		// Always cache the class name. This table exists to improve
		// performance. Create the class name if one does not exist.

		ClassName className = _classNames.get(value);

		if (className == null) {
			className = classNamePersistence.fetchByValue(value);

			if (className == null) {
				className = classNameLocalService.addClassName(value);
			}

			_classNames.put(value, className);
		}

		return className;
	}

	public long getClassNameId(Class<?> classObj) {
		return getClassNameId(classObj.getName());
	}

	public long getClassNameId(String value) {
		try {
			ClassName className = getClassName(value);

			return className.getClassNameId();
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Unable to get class name from value " + value, e);
		}
	}

	private static ClassName _nullClassName = new ClassNameImpl();
	private static Map<String, ClassName> _classNames =
		new ConcurrentHashMap<String, ClassName>();

}