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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Alexander Chow
 */
public class SerializableUtil {

	public static Object deserialize(byte[] bytes)
		throws ClassNotFoundException, IOException {

		ObjectInputStream ois = null;

		try {
			ois = new ObjectInputStream(new UnsyncByteArrayInputStream(bytes));

			Object obj = ois.readObject();

			ois.close();

			ois = null;

			return obj;
		}
		finally {
			if (ois != null) {
				ois.close();
			}
		}
	}

	public static byte[] serialize(Object obj) throws IOException {
		ObjectOutputStream oos = null;

		try {
			UnsyncByteArrayOutputStream ubaos =
				new UnsyncByteArrayOutputStream();

			oos = new ObjectOutputStream(ubaos);

			oos.writeObject(obj);

			oos.close();

			oos = null;

			return ubaos.toByteArray();
		}
		finally {
			if (oos != null) {
				oos.close();
			}
		}
	}

}