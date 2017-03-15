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

package com.liferay.portal.search.elasticsearch.settings;

import aQute.bnd.annotation.ProviderType;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface IndexSettingsContributor
	extends Comparable<IndexSettingsContributor> {

	public void contribute(
		String indexName, TypeMappingsHelper typeMappingsHelper);

	/**
	 * @deprecated As of 2.0.0, replaced by
	 *      {@link #contribute(String, TypeMappingsHelper)}
	 */
	@Deprecated
	public void contribute(TypeMappingsHelper typeMappingsHelper);

	public int getPriority();

	public void populate(IndexSettingsHelper indexSettingsHelper);

}