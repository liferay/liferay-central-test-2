/*
Copyright (c) 2003-2010, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://com/license
*/

CKEDITOR.editorConfig = function( config )
{	
	CKEDITOR.config.toolbar_liferay =
		[
			['Style','FontSize','-','TextColor','BGColor'],
			['Bold','Italic','Underline','StrikeThrough'],
			['Subscript','Superscript'],
			'/',
			['Undo','Redo','-','Cut','Copy','Paste','PasteText','PasteWord','-','SelectAll','RemoveFormat'],
			['Find','Replace','SpellCheck'],
			['OrderedList','UnorderedList','-','Outdent','Indent'],
			['JustifyLeft','JustifyCenter','JustifyRight','JustifyFull'],
			'/',
			['Source'],
			['Link','Unlink','Anchor'],
			['Image','Flash','Table','-','Smiley','SpecialChar']
		] ;

	CKEDITOR.config.toolbar_liferayArticle =
		[
			['Style','FontSize','-','TextColor','BGColor'],
			['Subscript','Superscript'],
			'/',
			['Undo','Redo','-','Cut','Copy','Paste','PasteText','PasteWord','-','SelectAll','RemoveFormat'],
			['Find','Replace','SpellCheck'],
			['OrderedList','UnorderedList','-','Outdent','Indent'],
			['JustifyLeft','JustifyCenter','JustifyRight','JustifyFull'],
			'/',
			['Source'],
			['Link','Unlink','Anchor'],
			['Image','Flash','Table','-','Smiley','SpecialChar','LiferayPageBreak']
		] ;

	CKEDITOR.config.toolbar_email = 
		[
	      	['FontSize','TextColor','BGColor','-','Bold','Italic','Underline','StrikeThrough'],
	      	['JustifyLeft','JustifyCenter','JustifyRight','JustifyFull'],
	        ['SpellCheck'],
	        '/',
	        ['Undo','Redo','-','Cut','Copy','Paste','PasteText','PasteWord','-','SelectAll','RemoveFormat'],
	        ['Source'],
	        ['Link','Unlink'],
	        ['Image']
	     ] ;
	
	CKEDITOR.config.toolbar_editInPlace = 
		[
		 	['Style'],
		 	['Bold','Italic','Underline','StrikeThrough'],
		 	['Subscript','Superscript','SpecialChar'],
		 	['Undo','Redo'],
		 	['SpellCheck'],
		 	['OrderedList','UnorderedList','-','Outdent','Indent'],['Source','RemoveFormat'],
		 ] ;	
};
