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

/**
 * <a href="Validator_IW.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class Validator_IW {
	public static Validator_IW getInstance() {
		return _instance;
	}

	public boolean equals(Object obj1, Object obj2) {
		return Validator.equals(obj1, obj2);
	}

	public boolean equals(int int1, int int2) {
		return Validator.equals(int1, int2);
	}

	public boolean equals(long long1, long long2) {
		return Validator.equals(long1, long2);
	}

	public boolean equals(byte byte1, byte byte2) {
		return Validator.equals(byte1, byte2);
	}

	public boolean equals(short short1, short short2) {
		return Validator.equals(short1, short2);
	}

	public boolean equals(char char1, char char2) {
		return Validator.equals(char1, char2);
	}

	public boolean equals(boolean boolean1, boolean boolean2) {
		return Validator.equals(boolean1, boolean2);
	}

	public boolean equals(float float1, float float2) {
		return Validator.equals(float1, float2);
	}

	public boolean equals(double double1, double double2) {
		return Validator.equals(double1, double2);
	}

	public boolean isAddress(String address) {
		return Validator.isAddress(address);
	}

	public boolean isAscii(char c) {
		return Validator.isAscii(c);
	}

	public boolean isChar(char c) {
		return Validator.isChar(c);
	}

	public boolean isChar(String s) {
		return Validator.isChar(s);
	}

	public boolean isDate(int month, int day, int year) {
		return Validator.isDate(month, day, year);
	}

	public boolean isDigit(char c) {
		return Validator.isDigit(c);
	}

	public boolean isDigit(String s) {
		return Validator.isDigit(s);
	}

	public boolean isDomain(String domainName) {
		return Validator.isDomain(domainName);
	}

	public boolean isEmailAddress(String emailAddress) {
		return Validator.isEmailAddress(emailAddress);
	}

	public boolean isEmailAddressSpecialChar(char c) {
		return Validator.isEmailAddressSpecialChar(c);
	}

	public boolean isGregorianDate(int month, int day, int year) {
		return Validator.isGregorianDate(month, day, year);
	}

	public boolean isHex(String s) {
		return Validator.isHex(s);
	}

	public boolean isHTML(String s) {
		return Validator.isHTML(s);
	}

	public boolean isIPAddress(String ipAddress) {
		return Validator.isIPAddress(ipAddress);
	}

	public boolean isJulianDate(int month, int day, int year) {
		return Validator.isJulianDate(month, day, year);
	}

	public boolean isLUHN(String number) {
		return Validator.isLUHN(number);
	}

	public boolean isName(String name) {
		return Validator.isName(name);
	}

	public boolean isNotNull(Object obj) {
		return Validator.isNotNull(obj);
	}

	public boolean isNotNull(Long l) {
		return Validator.isNotNull(l);
	}

	public boolean isNotNull(String s) {
		return Validator.isNotNull(s);
	}

	public boolean isNotNull(Object[] array) {
		return Validator.isNotNull(array);
	}

	public boolean isNull(Object obj) {
		return Validator.isNull(obj);
	}

	public boolean isNull(Long l) {
		return Validator.isNull(l);
	}

	public boolean isNull(String s) {
		return Validator.isNull(s);
	}

	public boolean isNull(Object[] array) {
		return Validator.isNull(array);
	}

	public boolean isNumber(String number) {
		return Validator.isNumber(number);
	}

	public boolean isPassword(String password) {
		return Validator.isPassword(password);
	}

	public boolean isPhoneNumber(String phoneNumber) {
		return Validator.isPhoneNumber(phoneNumber);
	}

	public boolean isUrl(String url) {
		return Validator.isUrl(url);
	}

	public boolean isVariableName(String variableName) {
		return Validator.isVariableName(variableName);
	}

	public boolean isVariableTerm(String s) {
		return Validator.isVariableTerm(s);
	}

	public boolean isWhitespace(char c) {
		return Validator.isWhitespace(c);
	}

	public boolean isXml(String s) {
		return Validator.isXml(s);
	}

	private Validator_IW() {
	}

	private static Validator_IW _instance = new Validator_IW();
}