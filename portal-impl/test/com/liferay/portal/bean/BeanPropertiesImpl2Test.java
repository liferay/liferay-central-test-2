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

package com.liferay.portal.bean;

/**
 * <a href="BeanPropertiesImpl2Test.java.html"><b><i>View Source</i></b></a>
 *
 * @author Igor Spasic
 */
public class BeanPropertiesImpl2Test extends BaseBeanTestCase {

	public void testSetInnerProperty() {
		FooBean2 fb2 = new FooBean2();

		bp.setProperty(fb2, "foo.int", Integer.valueOf(173));
		assertEquals(173, fb2.getFoo().getInt());
	}

}
