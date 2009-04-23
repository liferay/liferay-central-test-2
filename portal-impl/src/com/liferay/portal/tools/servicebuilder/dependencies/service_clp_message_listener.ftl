package ${packagePath}.service.messaging;

import ${packagePath}.service.ClpSerializer;

<#list entities as entity>
	<#if entity.hasLocalService()>
		import ${packagePath}.service.${entity.name}LocalServiceUtil;
	</#if>

	<#if entity.hasRemoteService()>
		import ${packagePath}.service.${entity.name}ServiceUtil;
	</#if>
</#list>

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

public class ClpMessageListener implements MessageListener {

	public static final String SERVLET_CONTEXT_NAME = ClpSerializer.SERVLET_CONTEXT_NAME;

	public void receive(Message message) {
		try {
			doReceive(message);
		}
		catch (Exception e) {
			_log.error("Unable to process message " + message, e);
		}
	}

	protected void doReceive(Message message) throws Exception {
		String command = message.getString("command");
		String servletContextName = message.getString("servletContextName");

		if (command.equals("undeploy") &&
			servletContextName.equals(SERVLET_CONTEXT_NAME)) {

			<#list entities as entity>
				<#if entity.hasLocalService()>
					${entity.name}LocalServiceUtil.clearService();
				</#if>

				<#if entity.hasRemoteService()>
					${entity.name}ServiceUtil.clearService();
				</#if>
			</#list>
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ClpMessageListener.class);

}