/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.ccpp;

import java.util.Set;

import javax.ccpp.Attribute;
import javax.ccpp.Component;
import javax.ccpp.Profile;
import javax.ccpp.ProfileDescription;

/**
 * <a href="EmptyProfile.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class EmptyProfile implements Profile {

	public Attribute getAttribute(String name) {
		return null;
	}

	public Set<Attribute> getAttributes() {
		return null;
	}

	public Component getComponent(String localtype) {
		return null;
	}

	public Set<Component> getComponents() {
		return null;
	}

	public ProfileDescription getDescription() {
		return null;
	}

}