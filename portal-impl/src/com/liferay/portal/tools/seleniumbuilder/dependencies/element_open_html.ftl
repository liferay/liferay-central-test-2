<#assign collapsed = true>

<#assign displayElementName = displayElement.getName()>

<#if
	(displayElementName == "command") ||
	(displayElementName == "elseif") ||
	(displayElementName == "if")
>
	<#assign collapsed = false>
</#if>

<#assign lineFolds = lineFolds + 1>

<div>
	<#if collapsed>
		<div id="toggle${lineFolds}" class="expand-toggle">+</div>
	<#else>
		<div id="toggle${lineFolds}" class="expand-toggle">-</div>
	</#if>
</div>

<div>
	<div class="expand-line">
		<#assign displayElementAttributes = displayElement.attributes()>

		<span class="arrow">&lt;</span>
		<span class="tag">${displayElementName}</span>

		<#list displayElementAttributes as displayElementAttribute>
			<#if displayElementAttribute.getName() != "line-number">
				<span class="attribute">${displayElementAttribute.getName()}</span>
				<span class="arrow">=</span>
				<span class="quote">&quot;${displayElementAttribute.getValue()}&quot;</span>
			</#if>
		</#list>

		<span class="arrow">&gt;</span>

		<div class="line-number">${lineNumber}</div>

		<#if collapsed>
			<#assign displayChildElements = displayElement.elements()>

			<#assign displayChildElementsSize = displayChildElements?size>

			<#if
				(displayElementName == "execute") &&
				(displayChildElementsSize != 0)
			>
				<div class="parameter-border">
					<#assign displayChildElements = displayElement.elements()>

					<#list displayChildElements as displayChildElement>
						<span>&lt;</span>
						<span>${displayChildElement.getName()}</span>

						<#assign displayChildElementAttributes = displayChildElement.attributes()>

						<#list displayChildElementAttributes as displayChildElementAttribute>
							<#if displayChildElementAttribute.getName() != "line-number">
								<span>${displayChildElementAttribute.getName()}</span>
								<span>=</span>
								<span class="parameter-value">&quot;${displayChildElementAttribute.getValue()}&quot;</span>
							</#if>
						</#list>

						<span>/&gt;</span>

						<div class="line-number">
							<#list displayChildElementAttributes as displayChildElementAttribute>
								<#if displayChildElementAttribute.getName() == "line-number">
									${displayChildElementAttribute.getValue()}
								</#if>
							</#list>
						</div>

						<br />
					</#list>
				</div>
			</#if>
		</#if>
	</div>
</div>

<#if collapsed>
	<ul id="collapseToggle${lineFolds}" class="collapse">
<#else>
	<ul id="collapseToggle${lineFolds}">
</#if>