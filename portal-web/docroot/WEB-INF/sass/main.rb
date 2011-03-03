require 'java'
require 'rubygems'
require 'sass'

java_import com.liferay.portal.kernel.log.LogFactoryUtil
java_import com.liferay.portal.servlet.filters.dynamiccss.DynamicCSSFilter

log = LogFactoryUtil.getLog(DynamicCSSFilter.java_class)

begin
	engine = Sass::Engine.new(
		$content,
		:syntax => :scss,
		:ugly => true,
		:load_paths => ['html/']
	)

	$out.println engine.render
rescue
	log.error "Error on #$cssRealPath"
	log.error $!

	$out.println $content
end