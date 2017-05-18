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

package com.liferay.asset.publisher.web.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Pavel Savinov
 */
@ExtendedObjectClassDefinition(category = "web-experience")
@Meta.OCD(
	id = "com.liferay.asset.publisher.web.configuration.AssetPublisherWebConfiguration",
	localization = "content/Language",
	name = "asset.publisher.web.configuration.name"
)
public interface AssetPublisherWebConfiguration {

	/**
	 * Set the interval in hours on how often CheckAssetEntryMessageListener
	 * will run to check for new assets. Users will be notified via email of
	 * new assets.
	 *
	 * @return interval in hours on how often to check for new assets.
	 */
	@Meta.AD(
		deflt = "24", description = "check.interval.key.description",
		required = false
	)
	public int checkInterval();

	/**
	 * Set this to true to enable exporting contents related to asset entries
	 * for dynamic selection.
	 *
	 * @return <code>true</code> if dynamic export is enabled.
	 */
	@Meta.AD(
		deflt = "true", description = "dynamic.export.enabled.key.description",
		required = false
	)
	public boolean dynamicExportEnabled();

	/**
	 * Set the maximum number of entries to export for dynamic selection. Set
	 * this property to 0 if there is no limit.
	 *
	 * @return maximum number of entries to export for dynamic selection.
	 */
	@Meta.AD(
		deflt = "20", description = "dynamic.export.limit.key.description",
		required = false
	)
	public int dynamicExportLimit();

	/**
	 * Set the maximum number of new entries to notify subscribers of Asset
	 * Publisher portlets. Set this property to 0 if there is no limit.
	 *
	 * @return maximum number of new entries to notify subscribers.
	 */
	@Meta.AD(
		deflt = "20",
		description = "dynamic.subscription.limit.key.description",
		required = false
	)
	public int dynamicSubscriptionLimit();

	/**
	 * Set this to true to enable exporting contents related to asset entries
	 * for manual selection.
	 *
	 * @return <code>true</code> if manual export is enabled.
	 */
	@Meta.AD(
		deflt = "true", description = "manual.export.enabled.key.description",
		required = false
	)
	public boolean manualExportEnabled();

	/**
	 * Set this to true to search assets in Asset Publisher from the index. Set
	 * this to false to search assets in Asset Publisher from the database.
	 *
	 * @return <code>true</code> search with index is enabled.
	 */
	@Meta.AD(
		deflt = "true", description = "search.with.index.key.description",
		required = false
	)
	public boolean searchWithIndex();

	/**
	 * Set this to true to allow users to configure Asset Publisher, Most Viewed
	 * Assets, and Highest Rated Assets to skip the permissions checking on the
	 * displayed assets. Enabling this property will allow regular users to view
	 * assets that they do not have permission to view.
	 *
	 * @return <code>true</code> if permission checking is configurable.
	 */
	@Meta.AD(
		deflt = "false",
		description = "permission.checking.configurable.key.description",
		required = false
	)
	public boolean permissionCheckingConfigurable();

}