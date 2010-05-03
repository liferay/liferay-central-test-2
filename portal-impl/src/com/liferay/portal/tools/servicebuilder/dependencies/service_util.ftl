package ${packagePath}.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ClassLoaderProxy;

/**
 * <a href="${entity.name}${sessionTypeName}ServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link ${entity.name}${sessionTypeName}Service} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    ${author}
 * @see       ${entity.name}${sessionTypeName}Service
 * @generated
 */
public class ${entity.name}${sessionTypeName}ServiceUtil {

	<#list methods as method>
		<#if !method.isConstructor() && !method.isStatic() && method.isPublic() && serviceBuilder.isCustomMethod(method)>
			<#if method.name = "dynamicQuery">
				@SuppressWarnings("unchecked")
			</#if>

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
				Object obj = PortletBeanLocatorUtil.locate(ClpSerializer.SERVLET_CONTEXT_NAME, ${entity.name}${sessionTypeName}Service.class.getName());
				ClassLoader portletClassLoader = (ClassLoader)PortletBeanLocatorUtil.locate(ClpSerializer.SERVLET_CONTEXT_NAME, "portletClassLoader");

				ClassLoaderProxy classLoaderProxy = new ClassLoaderProxy(obj, ${entity.name}${sessionTypeName}Service.class.getName(), portletClassLoader);

				_service = new ${entity.name}${sessionTypeName}ServiceClp(classLoaderProxy);

				ClpSerializer.setClassLoader(portletClassLoader);
			<#else>
				_service = (${entity.name}${sessionTypeName}Service)PortalBeanLocatorUtil.locate(${entity.name}${sessionTypeName}Service.class.getName());
			</#if>
		}

		return _service;
	}

	public void setService(${entity.name}${sessionTypeName}Service service) {
		_service = service;
	}

	private static ${entity.name}${sessionTypeName}Service _service;

}