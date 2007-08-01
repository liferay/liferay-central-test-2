<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>

		<body>

		<table border="1">
		<tr>
			<th>
				File Name
			</th>
			<th>
				Version
			</th>
			<th>
				Project
			</th>
			<th>
				License
			</th>
		</tr>

		<xsl:apply-templates />

		</table>

		</body>

		</html>
	</xsl:template>

	<xsl:template match="library">
		<tr>
			<td>
				<xsl:value-of select="file-name" />
			</td>
			<td>
				<xsl:value-of select="version" />
			</td>
			<td>
				<a>
					<xsl:attribute name="href">
						<xsl:value-of disable-output-escaping="yes" select="project-url" />
					</xsl:attribute>
					<xsl:value-of select="project-name" />
				</a>
			</td>
			<td>
				<table>
					
				<xsl:apply-templates select="licenses/license" />

				</table>
			</td>
		</tr>
	</xsl:template>

	<xsl:template match="licenses/license">
		<tr>
			<td>
				<a>
					<xsl:attribute name="href">
						<xsl:value-of disable-output-escaping="yes" select="license-url" />
					</xsl:attribute>
					<xsl:value-of select="license-name" />
				</a>

				<xsl:value-of select="copyright-notice" />
			</td>
		</tr>
	</xsl:template>
</xsl:stylesheet>