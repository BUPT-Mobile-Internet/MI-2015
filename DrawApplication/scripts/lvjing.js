/*var tempContext = null; // global variable 2d context
		window.onload = function() {
			//var source = document.getElementById("canvas-draw");
			var canvas = document.getElementById("canvas-draw");
			//canvas.width = canvas.clientWidth;
			//canvas.height = canvas.clientHeight;
			
			  if (!canvas.getContext) {
			    console.log("Canvas not supported. Please install a HTML5 compatible browser.");
			    return;
			}*/
			
			// get 2D context of canvas and draw image
			//tempContext = canvas.getContext("2d");
	//tempContext.drawImage(canvas, 0, 0, canvas.width, canvas.height);
	
	        // initialization actions
			var CANVAS_WIDTH = 667,
              CANVAS_HEIGHT = 500;
	         var inButton = document.getElementById("invert-button");
	        var adButton = document.getElementById("adjust-button");
	        var blurButton = document.getElementById("blur-button");
	        var reButton = document.getElementById("relief-button");
	        var dkButton = document.getElementById("diaoke-button");
	        var mirrorButton = document.getElementById("mirror-button");

			canvasView = $('#canvas-view')[0];
            canvasDraw = $('#canvas-draw')[0];
            var contextView = initializeCanvasContext(canvasView, CANVAS_WIDTH, CANVAS_HEIGHT);
            var contextDraw = initializeCanvasContext(canvasDraw, CANVAS_WIDTH, CANVAS_HEIGHT);
            function initializeCanvasContext(canvasElement, width, height) {
            var canvas = canvasElement,
                context = canvas.getContext('2d'); 
                canvas.width = width;
                  canvas.height = height;
 
            return context;
             }

	        // bind mouse click event
	        bindButtonEvent(inButton, "click", invertColor);
	        bindButtonEvent(adButton, "click", adjustColor);
	        bindButtonEvent(blurButton, "click", blurImage);
	        bindButtonEvent(reButton, "click", fudiaoImage);
	        bindButtonEvent(dkButton, "click", kediaoImage);
	        bindButtonEvent(mirrorButton, "click", mirrorImage);
		
		
		function bindButtonEvent(element, type, handler)  
		{  
			if(element.addEventListener) {  
			   element.addEventListener(type, handler, false);  
			} else {  
			   element.attachEvent('on'+type, handler); // for IE6,7,8
			}  
		}  
		
		function invertColor() {
            //var canvas = document.getElementById('canvas-draw'); 
            if (canvas.getContext){ 
           var context = canvas.getContext('2d');
           var len = canvas.width * canvas.height * 4;
              var canvasData = context.getImageData(0, 0, canvas.width, canvas.height);
              var binaryData = canvasData.data;
              gfilter.colorInvertProcess(binaryData, len);

          // 在指定位置进行像素重绘
           context.putImageData(canvasdata, 0, 0); 
	    } 
      }

			
		
		function adjustColor() {
			var canvas = document.getElementById("canvas-target");
			var len = canvas.width * canvas.height * 4;
			var canvasData = tempContext.getImageData(0, 0, canvas.width, canvas.height);
	        var binaryData = canvasData.data;
	        
	        // Processing all the pixels
	        gfilter.colorAdjustProcess(binaryData, len);

	        // Copying back canvas data to canvas
	        tempContext.putImageData(canvasData, 0, 0);
		}
		
		function blurImage() 
		{
			var canvas = document.getElementById("canvas-target");
			var len = canvas.width * canvas.height * 4;
			var canvasData = tempContext.getImageData(0, 0, canvas.width, canvas.height);
	        
	        // Processing all the pixels
	        gfilter.blurProcess(tempContext, canvasData);

	        // Copying back canvas data to canvas
	        tempContext.putImageData(canvasData, 0, 0);
		}
			
		function fudiaoImage() 
		{
			var canvas = document.getElementById("canvas-target");
			var len = canvas.width * canvas.height * 4;
			var canvasData = tempContext.getImageData(0, 0, canvas.width, canvas.height);
	        
	        // Processing all the pixels
	        gfilter.reliefProcess(tempContext, canvasData);

	        // Copying back canvas data to canvas
	        tempContext.putImageData(canvasData, 0, 0);
		}
		
		function kediaoImage() 
		{
			var canvas = document.getElementById("canvas-target");
			var len = canvas.width * canvas.height * 4;
			var canvasData = tempContext.getImageData(0, 0, canvas.width, canvas.height);
	        
	        // Processing all the pixels
	        gfilter.diaokeProcess(tempContext, canvasData);

	        // Copying back canvas data to canvas
	        tempContext.putImageData(canvasData, 0, 0);
		}
		
		function mirrorImage() 
		{
			var canvas = document.getElementById("canvas-target");
			var len = canvas.width * canvas.height * 4;
			var canvasData = tempContext.getImageData(0, 0, canvas.width, canvas.height);
	        
	        // Processing all the pixels
	        gfilter.mirrorProcess(tempContext, canvasData);

	        // Copying back canvas data to canvas
	        tempContext.putImageData(canvasData, 0, 0);
		}