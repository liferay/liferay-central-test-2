package com.liferay.portlet.mailbox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.liferay.portal.shared.util.StackTraceUtil;
import com.liferay.portlet.mailbox.util.MailUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.servlet.ServletResponseUtil;

public class GetAttachmentAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res)
		throws Exception {

		try {
			String fileName = ParamUtil.getString(req, "fileName");
			String contentPath = ParamUtil.getString(req, "contentPath");

			HttpSession ses = req.getSession();

			Object [] parts = MailUtil.getAttachment(ses, contentPath);
			byte [] content = (byte [])parts[0];
			String contentType = (String)parts[1];

			ServletResponseUtil.sendFile(res, fileName, content, contentType);
		}
		catch (Exception e) {
			_log.error("Error in retrieving an attachment.  " + 
				StackTraceUtil.getStackTrace(e));
		}
		
		return null;
	}

	private static Log _log = LogFactory.getLog(GetAttachmentAction.class);

}