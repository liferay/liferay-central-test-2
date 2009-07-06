(function() {
	Alloy.Resize = new Alloy.Class(YAHOO.util.Resize);

	Alloy.Resize.implement(
		{
		    CSS_RESIZE: 'aui-resizable',
		    CSS_DRAG: 'aui-draggable',
		    CSS_HOVER: 'aui-resizable-hover',
		    CSS_PROXY: 'aui-resizable-proxy',
		    CSS_WRAP: 'aui-resizable-wrap',
		    CSS_KNOB: 'aui-resizable-knob',
		    CSS_HIDDEN: 'aui-resizable-hidden',
		    CSS_HANDLE: 'aui-resizable-handle',
		    CSS_STATUS: 'aui-resizable-status',
		    CSS_GHOST: 'aui-resizable-ghost',
		    CSS_RESIZING: 'aui-resizable-resizing'
		}
	);
})();