package com.liferay.portal.audit.events.user;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.audit.messaging.AuditMessageSenderUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.AddressLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <a href="UserAddressModificationAudit.java.html"><b><i>View
 * Source</i></b></a>
 *
 * @author Mika Koivisto
 */
public class UserAddressModificationAudit extends BaseModelListener<Address> {

	public void onBeforeUpdate(Address newAddress)
		throws ModelListenerException {

		if (!newAddress.getClassName().equals(User.class.getName())) {
			return;
		}

		try {

			Address oldAddress = AddressLocalServiceUtil.getAddress(
				newAddress.getAddressId());

			Collection<ProfileAttribute> attributes = getChangedAttributes(
				newAddress, oldAddress);

			if (attributes.size() > 0) {
				AuditMessageSenderUtil.send(
					UserProfileModificationAuditMessageBuilder.build(
						newAddress.getUserId(), attributes));
			}
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	protected Collection<ProfileAttribute> getChangedAttributes(
		Address newAddress, Address oldAddress) {
		Collection<ProfileAttribute> attributes =
			new ArrayList<ProfileAttribute>();

		if (!newAddress.getCity().equals(oldAddress.getCity())) {
			attributes.add(
				new ProfileAttribute(
					"city", oldAddress.getCity(), newAddress.getCity()));
		}
		if (newAddress.getCountryId() != oldAddress.getCountryId()) {
			attributes.add(
				new ProfileAttribute(
					"countryId",
					String.valueOf(oldAddress.getCountryId()),
					String.valueOf(newAddress.getCountryId())));
		}
		if (newAddress.getMailing() != oldAddress.getMailing()) {
			attributes.add(
				new ProfileAttribute(
					"mailing",
					String.valueOf(oldAddress.getMailing()),
					String.valueOf(newAddress.getMailing())));
		}
		if (newAddress.getPrimary() != oldAddress.getPrimary()) {
			attributes.add(
				new ProfileAttribute(
					"primary",
					String.valueOf(oldAddress.getPrimary()),
					String.valueOf(newAddress.getPrimary())));
		}
		if (newAddress.getRegionId() != oldAddress.getRegionId()) {
			attributes.add(
				new ProfileAttribute(
					"regionId",
					String.valueOf(oldAddress.getRegionId()),
					String.valueOf(newAddress.getRegionId())));
		}
		if (!newAddress.getStreet1().equals(oldAddress.getStreet1())) {
			attributes.add(
				new ProfileAttribute(
					"street1",
					oldAddress.getStreet1(), newAddress.getStreet1()));
		}
		if (!newAddress.getStreet2().equals(oldAddress.getStreet2())) {
			attributes.add(
				new ProfileAttribute(
					"street2",
					oldAddress.getStreet2(), newAddress.getStreet2()));
		}
		if (!newAddress.getStreet3().equals(oldAddress.getStreet3())) {
			attributes.add(
				new ProfileAttribute(
					"street3",
					oldAddress.getStreet3(), newAddress.getStreet3()));
		}
		if (newAddress.getTypeId() != oldAddress.getTypeId()) {
			attributes.add(
				new ProfileAttribute(
					"typeId",
					String.valueOf(oldAddress.getTypeId()),
					String.valueOf(newAddress.getTypeId())));
		}
		if (!newAddress.getZip().equals(oldAddress.getZip())) {
			attributes.add(
				new ProfileAttribute(
					"zip",
					oldAddress.getZip(), newAddress.getZip()));
		}
		return attributes;
	}

}
