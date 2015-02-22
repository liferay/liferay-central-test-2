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

package com.liferay.portal.kernel.cache;

/**
 * @author Tina Tian
 */
public interface CacheReplicator {

	public static final boolean DEFAULT_REPLICATE_PUTS = true;

	public static final boolean DEFAULT_REPLICATE_PUTS_VIA_COPY = false;

	public static final boolean DEFAULT_REPLICATE_REMOVALS = true;

	public static final boolean DEFAULT_REPLICATE_UPDATES = true;

	public static final boolean DEFAULT_REPLICATE_UPDATES_VIA_COPY = false;

	public static final String REPLICATE_PUTS = "replicatePuts";

	public static final String REPLICATE_PUTS_VIA_COPY = "replicatePutsViaCopy";

	public static final String REPLICATE_REMOVALS = "replicateRemovals";

	public static final String REPLICATE_UPDATES = "replicateUpdates";

	public static final String REPLICATE_UPDATES_VIA_COPY =
		"replicateUpdatesViaCopy";

}