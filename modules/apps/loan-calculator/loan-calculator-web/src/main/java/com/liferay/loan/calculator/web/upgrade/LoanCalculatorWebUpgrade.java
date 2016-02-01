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

package com.liferay.loan.calculator.web.upgrade;

import com.liferay.loan.calculator.web.constants.LoanCalculatorPortletKeys;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.util.UpgradePortletId;

import org.osgi.service.component.annotations.Component;

/**
 * @author Raymond Augé
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	service = {LoanCalculatorWebUpgrade.class, UpgradeStepRegistrator.class}
)
public class LoanCalculatorWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(UpgradeStepRegistrator.Registry registry) {
		registry.register(
			"com.liferay.loan.calculator.web", "0.0.0", "1.0.0",
			new DummyUpgradeStep());

		registry.register(
			"com.liferay.loan.calculator.web", "0.0.1", "1.0.0",
			new UpgradePortletId() {

				@Override
				protected String[][] getRenamePortletIdsArray() {
					return new String[][] {
						new String[] {
							"61", LoanCalculatorPortletKeys.LOAN_CALCULATOR
						}
					};
				}

			});
	}

}