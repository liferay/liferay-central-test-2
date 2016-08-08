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

package com.liferay.poshi.runner.pql;

import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class PQLOperatorFactory {

	public static PQLOperator newOperator(String operator) throws Exception {
		PQLOperator.validateOperator(operator);

		if (operator.equals("AND") || operator.equals("OR")) {
			return new PQLOperator(operator) {

				public Object getValue(
						PQLEntity pqlEntity1, PQLEntity pqlEntity2,
						Properties properties)
					throws Exception {

					String operator = getOperator();
					Object value1 = pqlEntity1.getValue(properties);
					Object value2 = pqlEntity2.getValue(properties);

					if ((value1 == null) || (value2 == null)) {
						throw new Exception(
							"'" + operator + "' operators must be " +
								"surrounded by 2 boolean values.");
					}

					if (!(value1 instanceof Boolean) ||
						!(value2 instanceof Boolean)) {

						throw new Exception(
							"'" + operator + "' operators must be " +
								"surrounded by 2 boolean values.");
					}

					Boolean booleanValue1 = (Boolean)value1;
					Boolean booleanValue2 = (Boolean)value2;

					if (operator.equals("AND")) {
						return (booleanValue1 && booleanValue2);
					}
					else if (operator.equals("OR")) {
						return (booleanValue1 || booleanValue2);
					}

					throw new Exception(
						"Unsupported '" + operator + "' operator.");
				}

			};
		}

		throw new Exception("Unsupported '" + operator + "' operator.");
	}

}