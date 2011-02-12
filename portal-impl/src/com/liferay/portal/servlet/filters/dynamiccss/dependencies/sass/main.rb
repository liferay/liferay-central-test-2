require 'rubygems'
require 'sass'

begin
	engine = Sass::Engine.new(
		$content,
		:syntax => :scss,
		:ugly => true,
		:load_paths => ['html/']
	)

	$out.println engine.render
rescue
	$out.println $content
end