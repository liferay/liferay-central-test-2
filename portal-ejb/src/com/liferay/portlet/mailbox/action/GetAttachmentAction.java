package com.liferay.portlet.mailbox.action;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.liferay.portal.shared.util.StackTraceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.mailbox.util.MailUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.servlet.ServletResponseUtil;

public class GetAttachmentAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		try {
			String fileName = ParamUtil.getString(req, "fileName");
			String contentPath = ParamUtil.getString(req, "contentPath");

			HttpSession ses =
				((ActionRequestImpl)req).getHttpServletRequest().
					getSession();

			Object [] parts = MailUtil.getAttachment(ses, contentPath);
			byte [] content = (byte [])parts[0];
			String contentType = (String)parts[1];

			HttpServletResponse httpRes =
				((ActionResponseImpl)res).getHttpServletResponse();

			ServletResponseUtil.sendFile(
				httpRes, fileName, content, contentType);
		}
		catch (Exception e) {
			_log.error("Error in retrieving an attachment.  " + 
				StackTraceUtil.getStackTrace(e));
		}
	}

	private static Log _log = LogFactory.getLog(GetAttachmentAction.class);

}