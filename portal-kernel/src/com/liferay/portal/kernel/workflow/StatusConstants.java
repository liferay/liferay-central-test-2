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

package com.liferay.portal.kernel.workflow;

/**
 * <a href="StatusConstants.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class StatusConstants {

	public static final int ANY = -1;

	public static final int APPROVED = 0;

	public static final int DENIED = 4;

	public static final int DRAFT = 2;

	public static final int EXPIRED = 3;

	public static final String LABEL_ANY = "any";

	public static final String LABEL_APPROVED = "approved";

	public static final String LABEL_DENIED = "denied";

	public static final String LABEL_DRAFT = "draft";

	public static final String LABEL_EXPIRED = "expired";

	public static final String LABEL_PENDING = "pending";

	public static final int PENDING = 1;

	public static int fromLabel(String status) {
		if (status.equals(LABEL_ANY)) {
			return ANY;
		}
		else if (status.equals(LABEL_APPROVED)) {
			return APPROVED;
		}
		else if (status.equals(LABEL_DENIED)) {
			return DENIED;
		}
		else if (status.equals(LABEL_DRAFT)) {
			return DRAFT;
		}
		else if (status.equals(LABEL_EXPIRED)) {
			return EXPIRED;
		}
		else if (status.equals(LABEL_PENDING)) {
			return PENDING;
		}
		else {
			return ANY;
		}
	}

	public static String toLabel(int status) {
		if (status == ANY) {
			return LABEL_ANY;
		}
		else if (status == APPROVED) {
			return LABEL_APPROVED;
		}
		else if (status == DENIED) {
			return LABEL_DENIED;
		}
		else if (status == DRAFT) {
			return LABEL_DRAFT;
		}
		else if (status == EXPIRED) {
			return LABEL_EXPIRED;
		}
		else if (status == PENDING) {
			return LABEL_PENDING;
		}
		else {
			return LABEL_ANY;
		}
	}

}