require 'rubygems'
require 'sass'
require 'java'

java_import com.liferay.portal.kernel.log.LogFactoryUtil
java_import com.liferay.portal.servlet.filters.dynamiccss.DynamicCSSFilter

_log = LogFactoryUtil.getLog(DynamicCSSFilter.java_class)

begin
	engine = Sass::Engine.new(
		$content,
		:syntax => :scss,
		:ugly => true,
		:load_paths => ['html/']
	)

	$out.println engine.render
rescue
	_log.error "Error on #$cssRealPath"
	_log.error $!

	$out.println $content
end