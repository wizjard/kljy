/* 

	Tablecloth 
	written by Alen Grakalic, provided by Css Globe (cssglobe.com)
	please visit http://cssglobe.com/lab/tablecloth/
	
*/

this.tablecloth = function(){
	// CONFIG 
	
	// if set to true then mouseover a table cell will highlight entire column (except sibling headings)
	var highlightCols = false;
	
	// if set to true then mouseover a table cell will highlight entire row	(except sibling headings)
	var highlightRows = true;	
	
	// if set to true then click on a table sell will select row or column based on config
	var selectable = true;
	
	// this function is called when 
	// add your own code if you want to add action 
	// function receives object that has been clicked 
	this.clickAction = function(obj){
		//alert(obj.innerHTML);
		
	};


	
	// END CONFIG (do not edit below this line)
	
	
	var tableover = false;
	this.start = function(){
		var tables = document.getElementsByTagName("table");
		//alert(' table count='+tables.length);
		for (var i=0;i<tables.length;i++)
		{
			//?????
			if (tables[i].className=='tablecloth')
			{
				tables[i].onmouseover = function(){tableover = true};
				tables[i].onmouseout = function(){tableover = false};	
				rows(tables[i]);
			}
		};
	};
	
	this.rows = function(table){
		var css = "";
		var tr = table.getElementsByTagName("tr");
		for (var i=0;i<tr.length;i++){
			css = (css == "odd") ? "even" : "odd";
			tr[i].className = css;
			var arr = new Array();
			for(var j=0;j<tr[i].childNodes.length;j++){
				//��ֻ֤�б���е����ݶ����Ǳ�ͷ�Ż�������ƹ�ʱ�ķ�Ӧ
				var curNode = tr[i].childNodes[j];
				if(curNode.nodeType == 1 && curNode.tagName == 'TD') {
					arr.push(curNode);
					
					//���ֲ��������е�checkbox
					if(j == 0 && curNode.childNodes.length > 0 && curNode.childNodes[0].tagName == "INPUT" && curNode.childNodes[0].type == "checkbox"){
					  curNode.childNodes[0].onclick = function(){
					    chClick(table, this);
					  }
					}
				}
			}
			
			for (var j=0;j<arr.length;j++){				
				arr[j].row = i;
				arr[j].col = j;
				if(arr[j].innerHTML == "&nbsp;" || arr[j].innerHTML == "") arr[j].className += " empty";					
				arr[j].css = arr[j].className;
				arr[j].onmouseover = function(){
					over(table,this,this.row,this.col);
				};
				arr[j].onmouseout = function(){
					out(table,this,this.row,this.col);
				};
				arr[j].onmousedown = function(){
					down(table,this,this.row,this.col);
				};
				arr[j].onmouseup = function(){
					up(table,this,this.row,this.col);
				};				
				arr[j].onclick = function(){
					click(table,this,this.row,this.col);
				}							
			}
		}
	};
	
	//��ѡ����ʱ�Ĵ�����
	this.chClick = function(table, checkbox) {
	  if(!event.ctrlKey){
	    unselectOtherChks(table, checkbox);
	  }
	};
	
	this.unselectOtherChks = function(table, checkbox){
	  var tr = table.getElementsByTagName("tr");
		for (var i=0;i<tr.length;i++){
			var curNode = tr[i].childNodes[0];
			if(curNode.nodeType == 1 && curNode.tagName == 'TD') {
				//���ֲ��������е�checkbox
				if(curNode.childNodes[0] != checkbox){
					curNode.childNodes[0].checked = false;
				}
			}
		}
	};
	
	// appyling mouseover state for objects (th or td)
	this.over = function(table,obj,row,col){
		if (!highlightCols && !highlightRows) obj.className = obj.css + " over";  
		if(check1(obj,col)){
			if(highlightCols) highlightCol(table,obj,col);
			if(highlightRows) highlightRow(table,obj,row);		
		};
	};
	// appyling mouseout state for objects (th or td)	
	this.out = function(table,obj,row,col){
		if (!highlightCols && !highlightRows) obj.className = obj.css; 
		unhighlightCol(table,col);
		unhighlightRow(table,row);
	};
	// appyling mousedown state for objects (th or td)	
	this.down = function(table,obj,row,col){
		obj.className = obj.css + " down";  
	};
	// appyling mouseup state for objects (th or td)	
	this.up = function(table,obj,row,col){
		obj.className = obj.css + " over";  
	};	
	// onclick event for objects (th or td)	
	this.click = function(table,obj,row,col){
		if(check1){
			if(selectable) {
			    if(!event.ctrlKey){
				   unselect(table);	
				}
				if(highlightCols) highlightCol(table,obj,col,true);
				if(highlightRows) highlightRow(table,obj,row,true);
				document.onclick = unselectAll;
			}
		};
		clickAction(obj); 		
	};
	
	this.highlightCol = function(table,active,col,sel){
		var css = (typeof(sel) != "undefined") ? "selected" : "over";
		var tr = table.getElementsByTagName("tr");
		for (var i=0;i<tr.length;i++){	
			var arr = new Array();
			for(j=0;j<tr[i].childNodes.length;j++){				
				if(tr[i].childNodes[j].nodeType == 1) arr.push(tr[i].childNodes[j]);
			};							
			var obj = arr[col];
			if (check2(active,obj) && check3(obj)) obj.className = obj.css + " " + css; 		
		};
	};
	this.unhighlightCol = function(table,col){
		var tr = table.getElementsByTagName("tr");
		for (var i=0;i<tr.length;i++){
			var arr = new Array();
			for(j=0;j<tr[i].childNodes.length;j++){				
				if(tr[i].childNodes[j].nodeType == 1) arr.push(tr[i].childNodes[j])
			};				
			var obj = arr[col];
			if(check3(obj)) obj.className = obj.css; 
		};
	};	
	this.highlightRow = function(table,active,row,sel){
		var css = (typeof(sel) != "undefined") ? "selected" : "over";
		var tr = table.getElementsByTagName("tr")[row];		
		for (var i=0;i<tr.childNodes.length;i++){		
			var obj = tr.childNodes[i];
			if (check2(active,obj) && check3(obj)) obj.className = obj.css + " " + css; 		
		};
	};
	this.unhighlightRow = function(table,row){
		var tr = table.getElementsByTagName("tr")[row];		
		for (var i=0;i<tr.childNodes.length;i++){
			var obj = tr.childNodes[i];			
			if(check3(obj)) obj.className = obj.css; 			
		};
	};
	this.unselect = function(table){
		tr = table.getElementsByTagName("tr")
		for (var i=0;i<tr.length;i++){
			for (var j=0;j<tr[i].childNodes.length;j++){
				var obj = tr[i].childNodes[j];
				if(obj.className) obj.className = obj.className.replace("selected","");
			};
		};
	};
	this.unselectAll = function(){
		if(!tableover){
			tables = document.getElementsByTagName("table");
			for (var i=0;i<tables.length;i++){
				//by mxs
				if (tables[i].className=='tablecloth')
					unselect(tables[i])
			};		
		};
	};	
	this.check1 = function(obj,col){
		return (!(col == 0 && obj.className.indexOf("empty") != -1));
	}
	this.check2 = function(active,obj){
		return (!(active.tagName == "TH" && obj.tagName == "TH")); 
	};
	this.check3 = function(obj){
		return (obj.className != 'undefinded' && obj.className != '') ? (obj.className.indexOf("selected") == -1) : true; 
	};	
	
	start();
};

/* script initiates on page load. */
window.onload = tablecloth;
