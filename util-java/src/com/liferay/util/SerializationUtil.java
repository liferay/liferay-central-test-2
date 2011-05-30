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

import com.liferay.portal.kernel.util.StreamUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
public class SerializationUtil {

	public static Object clone(Serializable object) {
		return deserialize(serialize(object));
	}

	public static Object deserialize(InputStream inputStream) {
		if (inputStream == null) {
			throw new IllegalArgumentException(
				"The InputStream must not be null");
		}

		ObjectInputStream in = null;

		try {
			in = new ObjectInputStream(inputStream);

			return in.readObject();
		}
		catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		finally {
			StreamUtil.cleanUp(in);
		}
	}

	public static Object deserialize(byte[] objectData) {
		if (objectData == null) {
			throw new IllegalArgumentException("The byte[] must not be null");
		}

		ByteArrayInputStream bais = new ByteArrayInputStream(objectData);

		return deserialize(bais);
	}

	public static byte[] serialize(Serializable obj) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(512);

		serialize(obj, baos);

		return baos.toByteArray();
	}

	public static void serialize(Serializable obj, OutputStream outputStream) {

		if (outputStream == null) {
			throw new IllegalArgumentException(
				"The OutputStream must not be null");
		}

		ObjectOutputStream out = null;

		try {
			out = new ObjectOutputStream(outputStream);

			out.writeObject(obj);
		}
		catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		finally {
			StreamUtil.cleanUp(out);
		}
	}

}