package ${packagePath}.service;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ClassLoaderProxy;

public class ${entity.name}${sessionTypeName}ServiceUtil {

	<#list methods as method>
		<#if !method.isConstructor() && method.isPublic() && serviceBuilder.isCustomMethod(method)>
			public static ${method.returns.value}${method.returnsGenericsName}${serviceBuilder.getDimensions(method.returns.dimensions)} ${method.name}(

			<#list method.parameters as parameter>
				${parameter.type.value}${parameter.genericsName}${serviceBuilder.getDimensions(parameter.type.dimensions)} ${parameter.name}

				<#if parameter_has_next>
					,
				</#if>
			</#list>

			)

			<#list method.exceptions as exception>
				<#if exception_index == 0>
					throws
				</#if>

				${exception.value}

				<#if exception_has_next>
					,
				</#if>
			</#list>

			{
				<#if method.returns.value != "void">
					return
				</#if>

				getService().${method.name}(

				<#list method.parameters as parameter>
					${parameter.name}

					<#if parameter_has_next>
						,
					</#if>
				</#list>

				);
			}
		</#if>
	</#list>

	<#if pluginName != "">
		public static void clearService() {
			_service = null;
		}
	</#if>

	public static ${entity.name}${sessionTypeName}Service getService() {
		if (_service == null) {
			<#if pluginName != "">
				Object obj = PortletBeanLocatorUtil.locate(ClpSerializer.SERVLET_CONTEXT_NAME, ${entity.name}${sessionTypeName}ServiceUtil.class.getName());
				ClassLoader portletClassLoader = (ClassLoader)PortletBeanLocatorUtil.locate(ClpSerializer.SERVLET_CONTEXT_NAME, "portletClassLoader");

				ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(obj, portletClassLoader);

				_service = new ${entity.name}${sessionTypeName}ServiceClp(classLoaderProxy);

				ClpSerializer.setClassLoader(portletClassLoader);
			<#else>
				throw new RuntimeException("${entity.name}${sessionTypeName}Service is not set");
			</#if>
		}

		return _service;
	}

	public void setService(${entity.name}${sessionTypeName}Service service) {
		_service = service;
	}

	private static ${entity.name}${sessionTypeName}Service _service;

}