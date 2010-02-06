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
 * <a href="ContactLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ContactLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ContactLocalService
 * @generated
 */
public class ContactLocalServiceWrapper implements ContactLocalService {
	public ContactLocalServiceWrapper(ContactLocalService contactLocalService) {
		_contactLocalService = contactLocalService;
	}

	public com.liferay.portal.model.Contact addContact(
		com.liferay.portal.model.Contact contact)
		throws com.liferay.portal.SystemException {
		return _contactLocalService.addContact(contact);
	}

	public com.liferay.portal.model.Contact createContact(long contactId) {
		return _contactLocalService.createContact(contactId);
	}

	public void deleteContact(long contactId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_contactLocalService.deleteContact(contactId);
	}

	public void deleteContact(com.liferay.portal.model.Contact contact)
		throws com.liferay.portal.SystemException {
		_contactLocalService.deleteContact(contact);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _contactLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _contactLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portal.model.Contact getContact(long contactId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _contactLocalService.getContact(contactId);
	}

	public java.util.List<com.liferay.portal.model.Contact> getContacts(
		int start, int end) throws com.liferay.portal.SystemException {
		return _contactLocalService.getContacts(start, end);
	}

	public int getContactsCount() throws com.liferay.portal.SystemException {
		return _contactLocalService.getContactsCount();
	}

	public com.liferay.portal.model.Contact updateContact(
		com.liferay.portal.model.Contact contact)
		throws com.liferay.portal.SystemException {
		return _contactLocalService.updateContact(contact);
	}

	public com.liferay.portal.model.Contact updateContact(
		com.liferay.portal.model.Contact contact, boolean merge)
		throws com.liferay.portal.SystemException {
		return _contactLocalService.updateContact(contact, merge);
	}

	public ContactLocalService getWrappedContactLocalService() {
		return _contactLocalService;
	}

	private ContactLocalService _contactLocalService;
}