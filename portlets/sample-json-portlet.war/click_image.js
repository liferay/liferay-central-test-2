toString : function() {
	var main = document.createElement("div");
	var clickMe = document.createElement("span");
	
	clickMe.innerHTML = "Click me.";
	clickMe.setAttribute("onclick", "document.getElementById('click_image').style.display = 'inline'; this.style.display = 'none'");
	clickMe.id = "click_link";
	clickMe.style.cursor = "pointer";
	
	var image = document.createElement("img");
	image.src = "http://portal.liferay.com/image/company_logo?img_id=liferay.com&key=63152";
	image.setAttribute("onclick", "document.getElementById('click_link').style.display = 'inline'; this.style.display = 'none'");
	image.style.cursor = "pointer";
	image.id = "click_image";
	image.style.display = "none";
	
	main.appendChild(clickMe);
	main.appendChild(document.createElement("br"));
	main.appendChild(image);

	return(main.innerHTML);
}
