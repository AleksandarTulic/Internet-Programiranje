<!-- Modal HTML Markup -->
<div id="insertModal" class="modal fade">
    <div class="modal-dialog" style=" overflow-y: initial !important">
        <div class="modal-content">
            <div class="modal-header">
                <div class="col-sm-4" style="padding-left: 0px;">
                	<h2 class="modal-title centerThem">Category</h2>
                </div>
                
                <div class="col-sm-1">
                	&nbsp;
                </div>
                
                <div class="col-sm-5" id="insertMessageCategory">
                </div>
                
                <div class="col-sm-1 centerThem">
                	<button style="margin-top: 10px;" onclick="insertResetAll()" class="btn normalButton">Reset All</button>
                </div>
                
                <div class="col-sm-1">
                	&nbsp;
                </div>
            </div>
            <div class="modal-body" style="max-height: 600px; overflow-y: auto !important;">
               	<div class="row">
               		<div class="col-sm-12">
               			<div class="form-group">
	                    	<label class="control-label">Category Title</label>
	                        <div>
	                            <input type="text" required id="insertCategoryTitle" class="form-control" pattern="^[a-zA-Z0-9]{1}[a-zA-Z0-9\s]{1,98}$">
	                        </div>
	                    </div>
               		</div>
               		
               		<div id="insertBody">
               		</div>
               	</div>
            </div>
            
            <div class="modal-footer">
            	<div class="col-sm-12" style="text-align:center;">
       				<div class="form-group">
       					<button class="btn normalButton" onclick="insertAddField()">Add Field</button>
       				</div>
   				</div>
   				
   				<div class="col-sm-12" style="text-align:center;">
       				<div class="form-group">
       					<button class="btn normalButton" onclick="insertCategory()">Add Category</button>
       				</div>
   				</div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script>
	var insertNumberOfFields = Number(0);
		
	function insertResetAll(){
		document.getElementById("insertCategoryTitle").value = "";
		document.getElementById("insertBody").innerHTML = "";
		insertNumberOfFields = Number(0);
	}
	
	function insertAddField(){
		
		//document.getElementById("insertBody").innerHTML += 
			document.getElementById("insertBody").insertAdjacentHTML("beforeend", "<div class=\"col-sm-12\" style=\"margin-top:15px;\">"
        	+ "<div class=\"form-group\">"
    		+ "<div class=\"col-sm-6 insertCategory_" + insertNumberOfFields + "\">"
    		+ "<label class=\"control-label\">Field Name</label>"
    		+ "<div>"
            +	"<input required type=\"text\" value=\"\" class=\"form-control insertCategoryRow\" maxlength=\"100\" pattern=\"^[a-zA-Z0-9]{2,100}$\">"
        	+ "</div>"
    	    + "</div>"
    	    + "<div class=\"col-sm-6 insertCategory_" + insertNumberOfFields + "\">"
    		+ "<label class=\"control-label\">Field Type</label>"
    		+ "<div>"
            +	"<select class=\"form-control insertCategoryRow\" onchange=\"insertFieldTypeChanged(this.value, " + insertNumberOfFields + ")\">"
            + 	"<option value=\"" + TYPE_TEXT + "\" selected=\"selected\">" + TYPE_TEXT + "</option>"
            + 	"<option value=\"" + TYPE_FIXED + "\">" + TYPE_FIXED + "</option>"
            + 	"<option value=\"" + TYPE_NUMBER + "\">" + TYPE_NUMBER + "</option>"
            +   "</select>"
        	+ "</div>"
    	    + "</div>"
    	    
    	    + "<div class=\"col-sm-3\" style=\"display:none;\">"
    		+ "<label class=\"control-label\">Min Value</label>"
    		+ "<div>"
            +	"<input required type=\"number\" value=\"0\" class=\"form-control insertCategoryRow insertCategory_" + insertNumberOfFields + "\" min=\"0\">"
        	+ "</div>"
    	    + "</div>"
    	    
    	    + "<div class=\"col-sm-3\" style=\"display:none;\">"
    		+ "<label class=\"control-label\">Max Value</label>"
    		+ "<div>"
            +	"<input required type=\"number\" value=\"1\" class=\"form-control insertCategoryRow insertCategory_" + insertNumberOfFields + "\" min=\"1\">"
        	+ "</div>"
    	    + "</div>"
    	    
    	    + "<div class=\"col-sm-4\" style=\"display:none;\">"
    		+ "<label class=\"control-label\">Multiple Values</label>"
    		+ "<div>"
            +	"<input required type=\"text\" class=\"form-control insertCategoryRow insertCategory_" + insertNumberOfFields + "\" maxlength=\"398\" pattern=\"^[a-zA-Z0-9]{1,}[a-zA-Z0-9_\s]{0,397}$\" title=\"Set values separated by | sign.\nFor Example: aaa|bbb\">"
        	+ "</div>"
    	    + "</div>"
    	    
  			+  "</div>"
	 			+ "</div>");
	 		
		insertNumberOfFields++;
	}
	
	function insertFieldTypeChanged(value, index){
		var someElements = document.getElementsByClassName("insertCategory_" + index);
		var fieldName = someElements[0].value;
		
		if (value + "" === TYPE_FIXED + ""){
			someElements[0].className = "col-sm-4 insertCategory_" + index;
			someElements[1].className = "col-sm-4 insertCategory_" + index;
			someElements[2].parentElement.parentElement.style.cssText = "display:none !important";
			someElements[3].parentElement.parentElement.style.cssText = "display:none !important";
			someElements[4].parentElement.parentElement.style.cssText = "display:block !important";
		}else if (value + "" === TYPE_NUMBER + ""){
			someElements[0].className = "col-sm-3 insertCategory_" + index;
			someElements[1].className = "col-sm-3 insertCategory_" + index;
			someElements[2].parentElement.parentElement.style.cssText = "display:block !important";
			someElements[3].parentElement.parentElement.style.cssText = "display:block !important";
			someElements[4].parentElement.parentElement.style.cssText = "display:none !important";
		}else if (value + "" === TYPE_TEXT + ""){
			someElements[0].className = "col-sm-6 insertCategory_" + index;
			someElements[1].className = "col-sm-6 insertCategory_" + index;
			
			someElements[2].parentElement.parentElement.style.cssText = "display:none !important";
			someElements[3].parentElement.parentElement.style.cssText = "display:none !important";
			someElements[4].parentElement.parentElement.style.cssText = "display:none !important";
		}
	}
	
	function insertCategory(){
		//console.log(insertNumberOfFields);
		//console.log(new RegExp("^[a-zA-Z0-9]{1}[a-zA-Z0-9\\s]{1,98}$").test(document.getElementById("insertCategoryTitle").value));
		
		var ele = document.getElementsByClassName("insertCategoryRow");
		var flagFields = true;
		
		if (ele + "" != "null" && ele + "" != "undefined"){
			for (let i=0;i<ele.length;i+=5){
				//console.log(new RegExp("^[a-zA-Z0-9]{2,100}$").test(ele[i].value));
				if (new RegExp("^[a-zA-Z0-9]{2,100}$").test(ele[i].value) + "" === "false"){
					flagFields = false;
				}
			}
		}
		
		if (new RegExp("^[a-zA-Z0-9]{1}[a-zA-Z0-9\\s]{1,98}$").test(document.getElementById("insertCategoryTitle").value) + "" === "true"
				&& flagFields + "" === "true"){
			const arrSend = [];

			for (let i=0;i<ele.length;i+=5){
				var currObj = {
						"name": ele[i].value,
						"fieldType": ele[i+1].value,
						"regex": ele[i+2].value + "-" + ele[i+3].value + "-" + ele[i+4].value,
						"flagFixedMultipleValues": false
				}
				
				arrSend.push(currObj);
			}
			
			var objSend = {
					"title": document.getElementById("insertCategoryTitle").value,
					fields: arrSend
			};
			
			//console.log(objSend);
			//console.log(JSON.stringify(objSend));
			
			$.ajax({
				url: "category?what=insertCategory",
				type: "POST",
				data: {"value": JSON.stringify(objSend)},
				success: function(res){
					
					//console.log("Some Result");
					//console.log("END: " + res.result);
					
					if (res.result + "" === "true"){
						loadElementsButton();
					}
					
					var showResult = document.getElementById("insertMessageCategory");
					showResult.innerHTML = "<div class=\"" + ((res.result + "") === "true" ? "alert alert-success" : "alert alert-danger") + "\"> " + ((res.result + "") === "true" ? "Operation Successful" : "Operation Failed") + "</div>";
					
					setTimeout(function(){
						document.getElementById("insertMessageCategory").innerHTML = "";
					}, 3000);
					
				}
			});
		}else{
			var showResult = document.getElementById("insertMessageCategory");
			showResult.innerHTML = "<div class=\"" + "alert alert-danger" + "\"> " + "Operation Failed" + "</div>";
		
			setTimeout(function(){
				document.getElementById("insertMessageCategory").innerHTML = "";
			}, 3000);
		}
	}
</script>