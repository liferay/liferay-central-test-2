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

package com.liferay.portal.kernel.portlet;

import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

/**
 * <a href="LiferayWindowState.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class LiferayWindowState extends WindowState {

	public final static WindowState EXCLUSIVE = new WindowState("exclusive");

	public final static WindowState POP_UP = new WindowState("pop_up");

	public static boolean isExclusive(HttpServletRequest request) {
		String state = request.getParameter("p_p_state");

		if ((state != null) && (state.equals(EXCLUSIVE.toString()))) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isMaximized(HttpServletRequest request) {
		String state = request.getParameter("p_p_state");

		if ((state != null) &&
			(state.equals(WindowState.MAXIMIZED.toString()))) {

			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isPopUp(HttpServletRequest request) {
		String state = request.getParameter("p_p_state");

		if ((state != null) && (state.equals(POP_UP.toString()))) {
			return true;
		}
		else {
			return false;
		}
	}

	public static boolean isWindowStatePreserved(
		WindowState oldWindowState, WindowState newWindowState) {

		// Changes to EXCLUSIVE are always preserved

		if ((newWindowState != null) &&
			(newWindowState.equals(LiferayWindowState.EXCLUSIVE))) {

			return true;
		}

		// Some window states are automatically preserved

		if ((oldWindowState != null) &&
			(oldWindowState.equals(LiferayWindowState.POP_UP))) {

			return false;
		}
		else {
			return true;
		}
	}

	public LiferayWindowState(String name) {
		super(name);
	}

}