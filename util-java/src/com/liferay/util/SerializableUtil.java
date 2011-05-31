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

package com.liferay.util;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.StreamUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Alexander Chow
 */
public class SerializableUtil {

	public static Object clone(Object object) {
		return deserialize(serialize(object));
	}

	public static Object deserialize(byte[] bytes) {

		ObjectInputStream ois = null;

		try {
			ois = new ObjectInputStream(new UnsyncByteArrayInputStream(bytes));

			return ois.readObject();
		}
		catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		finally {
			StreamUtil.cleanUp(ois);
		}
	}

	public static byte[] serialize(Object obj) {
		ObjectOutputStream oos = null;

		UnsyncByteArrayOutputStream ubaos = new UnsyncByteArrayOutputStream();

		try {
			oos = new ObjectOutputStream(ubaos);

			oos.writeObject(obj);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		finally {
			StreamUtil.cleanUp(oos);
		}

		return ubaos.toByteArray();
	}

}