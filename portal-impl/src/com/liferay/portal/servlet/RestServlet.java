package com.liferay.portal.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.rest.RestAction;
import com.liferay.portal.kernel.rest.RestActionsManagerUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <a href="RestServlet.java.html"><b><i>View Source</i></b></a>
 *
 * @author Igor Spasic
 */
public class RestServlet extends HttpServlet {

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		String path = GetterUtil.getString(request.getPathInfo());

		String method = GetterUtil.getString(request.getMethod());

		RestAction restAction = RestActionsManagerUtil.lookup(path, method);

		if (restAction == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("REST action not found for path: '" + path + "'.");
			}

			return;
		}

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Invoke REST action for path: '" + path + "'.");
			}

			Object result = restAction.invoke();

		}
		catch (Exception e) {
			_log.error(e, e);
			throw new ServletException(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(RestServlet.class);

}
