/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.portlet.PortletPreferencesSerializer;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.List;

/**
 * @author Douglas Wong
 */
public class VerifyAssetPublisher extends VerifyProcess {

	protected void doVerify() throws Exception {
		List<PortletPreferences> portletPreferences =
			PortletPreferencesLocalServiceUtil.getPortletPreferencesByPortletId(
				"101_INSTANCE_%");

		for (PortletPreferences portletPreference : portletPreferences) {
			try {
				long ownerId = portletPreference.getOwnerId();
				int ownerType = portletPreference.getOwnerType();
				long plid = portletPreference.getPlid();
				String portletId = portletPreference.getPortletId();
				String xml = portletPreference.getPreferences();

				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				long companyId = layout.getCompanyId();

				PortletPreferencesImpl preferences =
					PortletPreferencesSerializer.fromXML(
						companyId, ownerId, ownerType, plid, portletId, xml);

				String[] assetEntryXmls = preferences.getValues(
					"asset-entry-xml", new String[0]);

				String[] manualEntries = preferences.getValues(
					"manual-entries", new String[0]);

				String selectionStyle = preferences.getValue(
					"selection-style", null);

				if (Validator.isNotNull(selectionStyle) &&
					!selectionStyle.equals("dynamic") &&
					Validator.isNull(assetEntryXmls) &&
					Validator.isNotNull(manualEntries)) {

					String newPreferences = upgradeManualEntryPreferences(
						preferences, manualEntries);

					PortletPreferencesLocalServiceUtil.updatePreferences(
						ownerId, ownerType, plid, portletId, newPreferences);
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	protected void updatePortletPreferences(
			long portletPreferencesId, String preferences)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"update PortletPreferences set preferences = ? where " +
					"portletPreferencesId = " + portletPreferencesId);

			ps.setString(1, preferences);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected String upgradeManualEntryPreferences(
			PortletPreferencesImpl preferences, String[] manualEntries)
		throws Exception {

		String[] assetEntryXmls = new String[manualEntries.length];

		for (int i = 0; i < manualEntries.length; i++) {
			String manualEntry = manualEntries[i];

			Document document = SAXReaderUtil.read(manualEntry);

			Element rootElement = document.getRootElement();

			Element assetIdElement = rootElement.element("asset-id");

			if (Validator.isNotNull(assetIdElement)) {
				try {
					long assetId = GetterUtil.getLong(assetIdElement.getText());

					AssetEntry assetEntry =
						AssetEntryLocalServiceUtil.getAssetEntry(assetId);

					String classUuid = assetEntry.getClassUuid();

					Element assetEntryUuidElement = rootElement.addElement(
						"asset-entry-uuid");

					assetEntryUuidElement.addText(classUuid);

					rootElement.remove(assetIdElement);
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}

			Element assetTypeElement = rootElement.element("asset-type");

			if (Validator.isNotNull(assetTypeElement)) {
				String assetEntryType = assetTypeElement.getText();

				Element assetEntryTypeElement = rootElement.addElement(
					"asset-entry-type");

				assetEntryTypeElement.addText(assetEntryType);

				rootElement.remove(assetTypeElement);
			}

			assetEntryXmls[i] = document.formattedString(
				StringPool.BLANK);
		}

		preferences.setValues("asset-entry-xml", assetEntryXmls);

		return PortletPreferencesSerializer.toXML(preferences);
	}

	private static Log _log = LogFactoryUtil.getLog(VerifyAssetPublisher.class);

}