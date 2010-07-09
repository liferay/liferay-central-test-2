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

package com.liferay.portlet.words.util;

import com.liferay.portlet.words.ScramblerException;
import com.liferay.portlet.words.util.comparator.WordComparator;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Brian Wing Shun Chan
 */
public class Scrambler {

	public Scrambler(String word) throws ScramblerException {
		if (word == null || word.length() < 3) {
			throw new ScramblerException();
		}

		_word = word;
		_words = new TreeSet<String>(new WordComparator());
	}

	public String[] scramble() {
		if (_word == null) {
			return new String[0];
		}

		_scramble(0, _word.length(), _word.toCharArray());

		return _words.toArray(new String[_words.size()]);
	}

	private void _rotate(char[] charArray, int start) {
		char temp = charArray[start];

		for (int i = charArray.length - start -1; i > 0; i--) {
			charArray[start] = charArray[++start];
		}

		charArray[start] = temp;
	}

	private void _scramble(int start, int length, char[] charArray) {
		if (length == 0) {
			String word = new String(charArray);

			for (int i = 3; i <= charArray.length; i++) {
				_words.add(word.substring(0, i));
			}
		}
		else {
			for (int i = 0; i < length; i++) {
				_scramble(start + 1, length - 1, charArray);
				_rotate(charArray, start);
			}
		}
	}

	private String _word;
	private Set<String> _words;

}