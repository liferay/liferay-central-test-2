/*
 * Copyright (c) 2011 Liferay Inc.
 */

CKEDITOR.dialog.add( 'link', function( editor )
{
	var plugin = CKEDITOR.plugins.link;

	var commonLang = editor.lang.common,
		linkLang = editor.lang.link;

	var parseLink = function( editor, element )
	{
		var href = ( element  && ( element.data( 'cke-saved-href' ) || element.getAttribute( 'href' ) ) ) || '';

		var data = {
			address: href
		};

		this._.selectedElement = element;

		return data;
	};

	return {
		title : linkLang.title,
		minWidth : 250,
		minHeight : 100,
		contents : [
			{
				id : 'info',
				label : linkLang.info,
				title : linkLang.info,
				elements :
				[
					{
						type :  'vbox',
						id : 'linkOptions',
						padding : 1,
						children :
						[
							{
								type : 'text',
								id : 'linkAddress',
								label : commonLang.url,
								required : true,
								validate : function()
								{
									var func = CKEDITOR.dialog.validate.notEmpty( linkLang.noUrl );

									return func.apply( this );
								},
								setup : function( data )
								{
									if ( data ) {
										this.setValue( data.address );
									}

									var linkType = this.getDialog().getContentElement( 'info', 'linkType' );

									if ( linkType && linkType.getValue() ){
										this.select();
									}
								},
								commit : function( data )
								{
									if ( !data ) {
										data = {};
									}

									data.address = this.getValue();
								}
							}
						]
					}
				]
			}
		],
		onShow : function()
		{
			this.fakeObj = false;

			var editor = this.getParentEditor(),
				selection = editor.getSelection(),
				element = null;

			// Fill in all the relevant fields if there's already one link selected.
			if ( ( element = plugin.getSelectedLink( editor ) ) ){
				selection.selectElement( element );
			}
			else {
				element = null;
			}

			this.setupContent( parseLink.apply( this, [ editor, element ] ) );
		},
		onOk : function()
		{
			var attributes = {},
				data = {},
				me = this,
				editor = this.getParentEditor();

			this.commitContent( data );

			attributes[ 'data-cke-saved-href' ] = data.address;
			attributes.href = data.address;

			if ( !this._.selectedElement )
			{
				// Create element if current selection is collapsed.
				var selection = editor.getSelection(),
					ranges = selection.getRanges( true );

				if ( ranges.length == 1 && ranges[0].collapsed )
				{
					var text = new CKEDITOR.dom.text( attributes[ 'data-cke-saved-href' ], editor.document );

					ranges[0].insertNode( text );
					ranges[0].selectNodeContents( text );
					selection.selectRanges( ranges );
				}

				// Apply style.
				var style = new CKEDITOR.style( { element : 'a', attributes : attributes } );
				style.type = CKEDITOR.STYLE_INLINE;		// need to override... dunno why.
				style.apply( editor.document );
			}
			else
			{
				var element = this._.selectedElement;

				element.setAttributes( attributes );
			}
		},
		onLoad : function()
		{
		},
		// Inital focus on 'url' field if link is of type URL.
		onFocus : function()
		{
			var urlField = this.getContentElement( 'info', 'linkAddress' );

			urlField.select();
		}
	};
});
