package com.liferay.portal.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.shorturl.service.ShortURLLocalServiceUtil;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShortURLServlet extends HttpServlet {
	
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

		try {
			ShortURLLocalServiceUtil.addShortURL(
				"http://www.suntimes.com/sports/4450574-419/jerry-angelo-talks-about-cutler-the-draft-free-agents-and-rule-changes.html",
				"Jerry Angelo talks about Cutler, the draft, free agents and rule changes");
			ShortURLLocalServiceUtil.addShortURL("http://sports.espn.go.com/chicago/nfl/news/story?id=6247029");
			ShortURLLocalServiceUtil.addShortURL("http://www.gamefaqs.com/xbox360/930279-mass-effect/faqs/58939");
			ShortURLLocalServiceUtil.addCustomURL("http://www.google.com", "/google");
		}
		catch (Exception e) {};
	}

	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		try {
			String uri = request.getRequestURI();
			
			String redirect = ShortURLLocalServiceUtil.getURLByURI(uri);
			
			System.out.println(redirect);
			
			response.sendRedirect(redirect);
		}
		catch (Exception e) {
			_log.error(e, e);

			PortalUtil.sendError(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e, request,
				response);
		}
	}
	
	private static Log _log = LogFactoryUtil.getLog(ShortURLServlet.class);

}
