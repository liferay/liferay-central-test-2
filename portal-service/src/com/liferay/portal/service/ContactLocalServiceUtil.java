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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="ContactLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link ContactLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ContactLocalService
 * @generated
 */
public class ContactLocalServiceUtil {
	public static com.liferay.portal.model.Contact addContact(
		com.liferay.portal.model.Contact contact)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addContact(contact);
	}

	public static com.liferay.portal.model.Contact createContact(long contactId) {
		return getService().createContact(contactId);
	}

	public static void deleteContact(long contactId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteContact(contactId);
	}

	public static void deleteContact(com.liferay.portal.model.Contact contact)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteContact(contact);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portal.model.Contact getContact(long contactId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getContact(contactId);
	}

	public static java.util.List<com.liferay.portal.model.Contact> getContacts(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getContacts(start, end);
	}

	public static int getContactsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getContactsCount();
	}

	public static com.liferay.portal.model.Contact updateContact(
		com.liferay.portal.model.Contact contact)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateContact(contact);
	}

	public static com.liferay.portal.model.Contact updateContact(
		com.liferay.portal.model.Contact contact, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateContact(contact, merge);
	}

	public static ContactLocalService getService() {
		if (_service == null) {
			_service = (ContactLocalService)PortalBeanLocatorUtil.locate(ContactLocalService.class.getName());
		}

		return _service;
	}

	public void setService(ContactLocalService service) {
		_service = service;
	}

	private static ContactLocalService _service;
}