(function() {
	Expanse.Resize = new Expanse.Class(YAHOO.util.Resize);

	Expanse.Resize.implement(
		{
		    CSS_RESIZE: 'exp-resizable',
		    CSS_DRAG: 'exp-draggable',
		    CSS_HOVER: 'exp-resizable-hover',
		    CSS_PROXY: 'exp-resizable-proxy',
		    CSS_WRAP: 'exp-resizable-wrap',
		    CSS_KNOB: 'exp-resizable-knob',
		    CSS_HIDDEN: 'exp-resizable-hidden',
		    CSS_HANDLE: 'exp-resizable-handle',
		    CSS_STATUS: 'exp-resizable-status',
		    CSS_GHOST: 'exp-resizable-ghost',
		    CSS_RESIZING: 'exp-resizable-resizing'
		}
	);
})();