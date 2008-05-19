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

package com.liferay.portal.model.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.service.AddressLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="OrganizationImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class OrganizationImpl
	extends OrganizationModelImpl implements Organization {

	public OrganizationImpl() {
	}

	public boolean isRoot() {
		if (getParentOrganizationId() ==
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

			return true;
		}
		else {
			return false;
		}
	}

	public boolean isRegular() {
		return !isLocation();
	}

	public int getType() {
		if (isLocation()) {
			return OrganizationConstants.TYPE_LOCATION;
		}
		else {
			return OrganizationConstants.TYPE_REGULAR;
		}
	}

	public int getType(boolean location) {
		int type = OrganizationConstants.TYPE_REGULAR;

		if (location) {
			type = OrganizationConstants.TYPE_LOCATION;
		}

		return type;
	}

	public String getTypeLabel() {
		return getTypeLabel(getType());
	}

	public String getTypeLabel(int type) {
		if (type == OrganizationConstants.TYPE_LOCATION) {
			return OrganizationConstants.TYPE_LOCATION_LABEL;
		}
		else {
			return OrganizationConstants.TYPE_REGULAR_LABEL;
		}
	}

	public Group getGroup() {
		if (getOrganizationId() > 0) {
			try {
				return GroupLocalServiceUtil.getOrganizationGroup(
					getCompanyId(), getOrganizationId());
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return new GroupImpl();
	}

	public int getPrivateLayoutsPageCount() {
		try {
			Group group = getGroup();

			if (group == null) {
				return 0;
			}
			else {
				return group.getPrivateLayoutsPageCount();
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		return 0;
	}

	public boolean hasPrivateLayouts() {
		if (getPrivateLayoutsPageCount() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public int getPublicLayoutsPageCount() {
		try {
			Group group = getGroup();

			if (group == null) {
				return 0;
			}
			else {
				return group.getPublicLayoutsPageCount();
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		return 0;
	}

	public boolean hasPublicLayouts() {
		if (getPublicLayoutsPageCount() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public Address getAddress() {
		Address address = null;

		try {
			List<Address> addresses = getAddresses();

			if (addresses.size() > 0) {
				address = addresses.get(0);
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		if (address == null) {
			address = new AddressImpl();
		}

		return address;
	}

	public List<Address> getAddresses()
		throws PortalException, SystemException {

		return AddressLocalServiceUtil.getAddresses(
			getCompanyId(), Organization.class.getName(), getOrganizationId());
	}

	private static Log _log = LogFactory.getLog(Organization.class);

}