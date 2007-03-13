/**
 * jReflection.js
 * 
 * @author Abdur-Rahman Advany, http://manage2share.com
 * @license MIT-licence
 * @version 0.3
 * @updated-on 17-10-2006
 * @projectDescription jquerified version of reflection.js
 *
 * Contributors: Cow http://cow.neondragon.net
 *               Gfx http://www.jroller.com/page/gfx/
 *               Sitharus http://www.sitharus.com
 *               Andreas Linde http://www.andreaslinde.de
 *               Tralala, coder @ http://www.vbulletin.org
 * 
 */

/**
 * limit the amount of active queue items in a queue
 *
 * @example $("#all_my_images").find('img').Reflection({height : 0.5, opacity : 0.5});
 *
 * @name Reflection
 * @type jQuery.fn
 * @param options to be passed (height and opacity, an value between 0 and 1).
 * @cat fx
 * @return container div's containing both image and reflection.
 */
jQuery.fn.extend({
  Reflection: function(settings){
    return this.findImages().each(function(){
      if(this.tagName == 'IMG'){
        var image = this;
      
        if (image.parentNode.className == "reflected") {
          image.className = image.parentNode.className;
          image.parentNode.parentNode.replaceChild(image, image.parentNode);
        }
        
        var defaults = { "height" : 0.5, "opacity" : 0.5 };
        
        var options = jQuery.extend(defaults, settings);
      
        try {
          var d = document.createElement('div');
          jQuery(d).addClass('reflected');
          var p = image;
            
          var reflectionHeight = Math.floor(p.height*options['height']);
          var divHeight = Math.floor(p.height*(1+options['height']));
          
          var reflectionWidth = p.width;
  
          jQuery(d).css('cssText', jQuery(image).css('cssText'));
          jQuery(image).css('vertical-align', 'bottom');
          
          if (jQuery.browser.msie) {
          
            var reflection = document.createElement('img');
            reflection.src = p.src;
            reflection.style.width = reflectionWidth+'px';
            
            reflection.style.marginBottom = "-"+(p.height-reflectionHeight)+'px';
            reflection.style.filter = 'flipv progid:DXImageTransform.Microsoft.Alpha(opacity='+(options['opacity']*100)+', style=1, finishOpacity=0, startx=0, starty=0, finishx=0, finishy='+(options['height']*100)+')';
            
            d.style.height=divHeight+'px';
            d.style.width=reflectionWidth+'px';
            
            p.parentNode.replaceChild(d, p);
            
            d.appendChild(p);
            d.appendChild(reflection);
          } else {
            var canvas = document.createElement('canvas');
            if (canvas.getContext) {
              /* Copy original image's classes & styles to div */
                                  
              var context = canvas.getContext("2d");
  
              canvas.height=reflectionHeight;
              canvas.width=reflectionWidth;
              canvas.style.height=reflectionHeight;
              canvas.style.width=reflectionWidth;
              
              d.style.width=reflectionWidth+'px';
              d.style.height=divHeight+'px';
              
              p.parentNode.replaceChild(d, p);
              
              d.appendChild(p);
              d.appendChild(canvas);
              
              context.save();
              
              context.translate(0,image.height-1);
              context.scale(1,-1);
              
              context.drawImage(image, 0, 0, reflectionWidth, image.height);
      
              context.restore();
              
              context.globalCompositeOperation = "destination-out";
              var gradient = context.createLinearGradient(0, 0, 0, reflectionHeight);
              
              gradient.addColorStop(1, "rgba(255, 255, 255, 1.0)");
              gradient.addColorStop(0, "rgba(255, 255, 255, "+(1-options['opacity'])+")");
        
              context.fillStyle = gradient;
              if (navigator.appVersion.indexOf('WebKit') != -1) {
                context.fill();
              } else {
                context.fillRect(0, 0, reflectionWidth, reflectionHeight*2);
              }
            }
          }
        } catch (e) {
          }
      }
    }).parent();
  },
  findImages: function() {
    if(this.size() == 1 && this.get(0).tagName == 'IMG'){
      return this
    } else {
      return this.find('img');
    }
  }
});