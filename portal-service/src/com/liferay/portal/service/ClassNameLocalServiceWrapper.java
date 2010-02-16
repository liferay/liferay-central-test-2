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

package com.liferay.portal.service;


/**
 * <a href="ClassNameLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ClassNameLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ClassNameLocalService
 * @generated
 */
public class ClassNameLocalServiceWrapper implements ClassNameLocalService {
	public ClassNameLocalServiceWrapper(
		ClassNameLocalService classNameLocalService) {
		_classNameLocalService = classNameLocalService;
	}

	public com.liferay.portal.model.ClassName addClassName(
		com.liferay.portal.model.ClassName className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.addClassName(className);
	}

	public com.liferay.portal.model.ClassName createClassName(long classNameId) {
		return _classNameLocalService.createClassName(classNameId);
	}

	public void deleteClassName(long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_classNameLocalService.deleteClassName(classNameId);
	}

	public void deleteClassName(com.liferay.portal.model.ClassName className)
		throws com.liferay.portal.kernel.exception.SystemException {
		_classNameLocalService.deleteClassName(className);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portal.model.ClassName getClassName(long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.getClassName(classNameId);
	}

	public java.util.List<com.liferay.portal.model.ClassName> getClassNames(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.getClassNames(start, end);
	}

	public int getClassNamesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.getClassNamesCount();
	}

	public com.liferay.portal.model.ClassName updateClassName(
		com.liferay.portal.model.ClassName className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.updateClassName(className);
	}

	public com.liferay.portal.model.ClassName updateClassName(
		com.liferay.portal.model.ClassName className, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.updateClassName(className, merge);
	}

	public com.liferay.portal.model.ClassName addClassName(
		java.lang.String value)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.addClassName(value);
	}

	public void checkClassNames()
		throws com.liferay.portal.kernel.exception.SystemException {
		_classNameLocalService.checkClassNames();
	}

	public com.liferay.portal.model.ClassName getClassName(
		java.lang.String value)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _classNameLocalService.getClassName(value);
	}

	public long getClassNameId(java.lang.Class<?> classObj) {
		return _classNameLocalService.getClassNameId(classObj);
	}

	public long getClassNameId(java.lang.String value) {
		return _classNameLocalService.getClassNameId(value);
	}

	public ClassNameLocalService getWrappedClassNameLocalService() {
		return _classNameLocalService;
	}

	private ClassNameLocalService _classNameLocalService;
}