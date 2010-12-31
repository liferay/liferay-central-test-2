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

package com.liferay.portal.kernel.dao.orm;

/**
 * @author Brian Wing Shun Chan
 */
public interface LockMode {

	public static final LockMode FORCE = new LockModeImpl("FORCE");

	public static final LockMode NONE = new LockModeImpl("NONE");

	public static final LockMode OPTIMISTIC = new LockModeImpl("OPTIMISTIC");

	public static final LockMode OPTIMISTIC_FORCE_INCREMENT = new LockModeImpl(
		"OPTIMISTIC_FORCE_INCREMENT");

	public static final LockMode PESSIMISTIC_FORCE_INCREMENT = new LockModeImpl(
		"PESSIMISTIC_FORCE_INCREMENT");

	public static final LockMode PESSIMISTIC_READ = new LockModeImpl(
		"PESSIMISTIC_READ");

	public static final LockMode PESSIMISTIC_WRITE = new LockModeImpl(
		"PESSIMISTIC_WRITE");

	public static final LockMode READ = new LockModeImpl("READ");

	public static final LockMode UPGRADE = new LockModeImpl("UPGRADE");

	public static final LockMode UPGRADE_NOWAIT =
		new LockModeImpl("UPGRADE_NOWAIT");

	public static final LockMode WRITE = new LockModeImpl("WRITE");

	public String getName();

}