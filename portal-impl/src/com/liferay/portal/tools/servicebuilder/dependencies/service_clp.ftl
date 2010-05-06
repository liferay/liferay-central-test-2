package ${packagePath}.service;

import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.ClassLoaderProxy;
import com.liferay.portal.kernel.util.DoubleWrapper;
import com.liferay.portal.kernel.util.FloatWrapper;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.kernel.util.ShortWrapper;

public class ${entity.name}${sessionTypeName}ServiceClp implements ${entity.name}${sessionTypeName}Service {

	public ${entity.name}${sessionTypeName}ServiceClp(ClassLoaderProxy classLoaderProxy) {
		_classLoaderProxy = classLoaderProxy;
	}

	<#list methods as method>
		<#if !method.isConstructor() && method.isPublic() && serviceBuilder.isCustomMethod(method)>
			<#assign returnTypeName = method.returns.value + method.returnsGenericsName + serviceBuilder.getDimensions(method.returns.dimensions)>
			<#assign parameters = method.parameters>

			<#if method.name = "dynamicQuery">
				@SuppressWarnings("unchecked")
			</#if>

			public ${method.returns.value}${method.returnsGenericsName}${serviceBuilder.getDimensions(method.returns.dimensions)} ${method.name}(

			<#list parameters as parameter>
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
				<#list parameters as parameter>
					<#assign parameterTypeName = parameter.type.value + serviceBuilder.getDimensions(parameter.type.dimensions)>

					Object paramObj${parameter_index} =

					<#if parameterTypeName == "boolean">
						new BooleanWrapper(${parameter.name});
					<#elseif parameterTypeName == "double">
						new DoubleWrapper(${parameter.name});
					<#elseif parameterTypeName == "float">
						new FloatWrapper(${parameter.name});
					<#elseif parameterTypeName == "int">
						new IntegerWrapper(${parameter.name});
					<#elseif parameterTypeName == "long">
						new LongWrapper(${parameter.name});
					<#elseif parameterTypeName == "short">
						new ShortWrapper(${parameter.name});
					<#else>
						ClpSerializer.translateInput(${parameter.name});

						if (${parameter.name} == null) {
							paramObj${parameter_index} = new NullWrapper("${serviceBuilder.getClassName(parameter.type)}");
						}
					</#if>
				</#list>

				<#if returnTypeName != "void">
					Object returnObj = null;
				</#if>

				try {
					<#if returnTypeName != "void">
						returnObj =
					</#if>

					_classLoaderProxy.invoke("${method.name}",

					<#if parameters?size == 0>
						new Object[0]
					<#else>
						new Object[] {
							<#list parameters as parameter>
								paramObj${parameter_index}

								<#if parameter_has_next>
									,
								</#if>
							</#list>
						}
					</#if>

					);
				}
				catch (Throwable t) {
					<#list method.exceptions as exception>
						if (t instanceof ${exception.value}) {
							throw (${exception.value})t;
						}
					</#list>

					if (t instanceof RuntimeException) {
						throw (RuntimeException)t;
					}
					else {
						throw new RuntimeException(t.getClass().getName() + " is not a valid exception");
					}
				}

				<#if returnTypeName != "void">
					<#if returnTypeName == "boolean">
						return ((Boolean)returnObj).booleanValue();
					<#elseif returnTypeName == "double">
						return ((Double)returnObj).doubleValue();
					<#elseif returnTypeName == "float">
						return ((Float)returnObj).floatValue();
					<#elseif returnTypeName == "int">
						return ((Integer)returnObj).intValue();
					<#elseif returnTypeName == "long">
						return ((Long)returnObj).longValue();
					<#elseif returnTypeName == "short">
						return ((Short)returnObj).shortValue();
					<#else>
						return (${returnTypeName})ClpSerializer.translateOutput(returnObj);
					</#if>
				</#if>
			}
		</#if>
	</#list>

	public ClassLoaderProxy getClassLoaderProxy() {
		return _classLoaderProxy;
	}

	private ClassLoaderProxy _classLoaderProxy;

}