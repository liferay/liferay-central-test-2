<?xml version="1.0" encoding="UTF-8"?>

<javadoc>
	<name>${entity.name}ServiceHttp</name>
	<type>${packagePath}.service.http.${entity.name}ServiceHttp</type>
	<comment>
		<![CDATA[
			<p>
				ServiceBuilder generated this class. Modifications in this class will be
				overwritten the next time is generated.
			</p>

			<p>
				This class provides a HTTP utility for the
				{@link ${packagePath}.service.${entity.name}ServiceUtil} service utility. The
				static methods of this class calls the same methods of the service utility.
				However, the signatures are different because it requires an additional
				{@link com.liferay.portal.security.auth.HttpPrincipal} parameter.
			</p>

			<p>
				The benefits of using the HTTP utility is that it is fast and allows for
				tunneling without the cost of serializing to text. The drawback is that it
				only works with Java.
			</p>

			<p>
				Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties
				to configure security.
			</p>

			<p>
				The HTTP utility is only generated for remote services.
			</p>
		]]>
	</comment>
	<author>${author}</author>
	<see>${entity.name}ServiceSoap</see>
	<see>com.liferay.portal.security.auth.HttpPrincipal</see>
	<see>${packagePath}.service.${entity.name}ServiceUtil</see>
</javadoc>