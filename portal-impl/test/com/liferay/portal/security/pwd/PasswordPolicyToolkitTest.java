package com.liferay.portal.security.pwd;

import com.liferay.portal.kernel.test.TestCase;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.impl.PasswordPolicyImpl;


public class PasswordPolicyToolkitTest extends TestCase {

	public PasswordPolicyToolkitTest() {
		_toolkit = new PasswordPolicyToolkit();
		_passwordPolicy = new PasswordPolicyImpl();

		_passwordPolicy.setAllowDictionaryWords(true);
		_passwordPolicy.setChangeable(true);
		_passwordPolicy.setCheckSyntax(true);
		_passwordPolicy.setMinAlphaNumeric(5);
		_passwordPolicy.setMinLength(8);
		_passwordPolicy.setMinLowerCase(2);
		_passwordPolicy.setMinUpperCase(2);
		_passwordPolicy.setMinNumbers(1);
		_passwordPolicy.setMinSymbols(1);
	}

	public void testGeneratePassword() {

		String password = _toolkit.generate(_passwordPolicy);

		try {
			_toolkit.validate(password, password, _passwordPolicy);
		}
		catch (Exception e) {
			fail("Generated password did not validate agains policy");
		}
	}

	public void testValidateLength() {
		String password = "xH9fxM@";

		assertEquals(false, validate(password));
	}

	public void testValidateMinAlphaNumeric() {
		String password = "xH9f.,@-";

		assertEquals(false, validate(password));
	}

	public void testValidateMinLowerChars() {
		String password = "xHFXM@W";

		assertEquals(false, validate(password));
	}

	public void testValidateMinNumbers() {
		String password = "xHafxMkw";

		assertEquals(false, validate(password));
	}

	public void testValidateMinSpecial() {
		String password = "xH9fxMkw";

		assertEquals(false, validate(password));
	}

	public void testValidateMinUpperChars() {
		String password = "xh9fxM@w";

		assertEquals(false, validate(password));
	}

	public void testValidateValid() {
		String password = "xH9fxM@w";

		assertEquals(true, validate(password));
	}

	protected boolean validate(String password) {
		try {
			_toolkit.validate(password, password, _passwordPolicy);
		}
		catch (Exception e) {
			return false;
		}

		return true;
	}

	private PasswordPolicy _passwordPolicy;
	private PasswordPolicyToolkit _toolkit;

}
