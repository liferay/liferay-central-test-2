/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.currency.converter.web.portlet;

import com.liferay.currency.converter.web.upgrade.CurrencyConverterUpgrade;
import com.liferay.util.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
* @author Raymond Augé
* @author Peter Fellwock
*/
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-currency-converter",
		"com.liferay.portlet.display-category=category.finance",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/currency_converter.png",
		"com.liferay.portlet.preferences-owned-by-group=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.remoteable=true",
		"com.liferay.portlet.render-weight=0",
		"com.liferay.portlet.struts-path=currency_converter",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.init-param.edit-guest-template=/edit.jsp",
		"javax.portlet.init-param.edit-template=/edit.jsp",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.portlet.display-name=Currency Converter",
		"javax.portlet.portlet.expiration-cache=0",
		"javax.portlet.portlet.modes=text/html;edit,edit-guest",
		"javax.portlet.preferences=classpath:/META-INF/portlet-preferences/default-portlet-preferences.xml",
		"javax.portlet.resource-bundle=content.Language"
	},
	service = Portlet.class
)
public class CurrencyConverterPortlet extends MVCPortlet {

	@Reference(unbind = "-")
	protected void setCurrencyConverterUpgrade(
		CurrencyConverterUpgrade currencyConverterUpgrade) {
	}

}