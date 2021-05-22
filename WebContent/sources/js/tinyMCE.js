tinymce
		.init({
			selector : "#bodyBlog",
			//inline : true,
			plugins : [
					"advlist autolink autosave link image lists charmap print preview hr anchor pagebreak spellchecker",
					"searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
					"table contextmenu directionality emoticons template textcolor paste fullpage textcolor" ],
			toolbar : "undo redo | styleselect | bold italic underline strikethrough subscript superscript | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | cut copy paste | table ",
			menubar: false,
	        toolbar_items_size: 'small'
		});