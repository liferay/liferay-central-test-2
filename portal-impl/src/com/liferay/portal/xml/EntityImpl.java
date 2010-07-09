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

package com.liferay.portal.xml;

import com.liferay.portal.kernel.xml.Entity;

/**
 * @author Brian Wing Shun Chan
 */
public class EntityImpl extends NodeImpl implements Entity {

	public EntityImpl(org.dom4j.Entity entity) {
		super(entity);

		_entity = entity;
	}

	public boolean equals(Object obj) {
		org.dom4j.Entity entity = ((EntityImpl)obj).getWrappedEntity();

		return _entity.equals(entity);
	}

	public org.dom4j.Entity getWrappedEntity() {
		return _entity;
	}

	public int hashCode() {
		return _entity.hashCode();
	}

	public String toString() {
		return _entity.toString();
	}

	private org.dom4j.Entity _entity;

}