/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search.facet.config;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Raymond Augé
 */
public class FacetConfigurationUtil {

	public static List<FacetConfiguration> load(String configuration) throws Exception {
		List<FacetConfiguration> configurationList =
			new ArrayList<FacetConfiguration>();

		if (Validator.isNull(configuration)) {
			return configurationList;
		}

		try {
			JSONObject configurationJSONObject = JSONFactoryUtil.createJSONObject(
				configuration);

			JSONArray facetsJSONArray = configurationJSONObject.getJSONArray(
				"facets");

			if (facetsJSONArray != null) {
				for (int i = 0; i < facetsJSONArray.length(); i++) {
					JSONObject facetJSONObject = facetsJSONArray.getJSONObject(i);

					FacetConfiguration config = new FacetConfiguration();

					config.setClassName(facetJSONObject.getString("className"));

					if (facetJSONObject.has("data")) {
						config.setData(facetJSONObject.getJSONObject("data"));
					}

					config.setDisplayStyle(
						facetJSONObject.getString("displayStyle"));
					config.setFieldName(facetJSONObject.getString("fieldName"));
					config.setLabel(facetJSONObject.getString("label"));
					config.setOrder(facetJSONObject.getString("order"));
					config.setStatic(facetJSONObject.getBoolean("static"));
					config.setWeight(facetJSONObject.getDouble("weight"));

					configurationList.add(config);
				}
			}
		}
		catch (Exception e) {
		}

		return configurationList;
	}

}