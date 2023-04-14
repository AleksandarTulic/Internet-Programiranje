<!-- Modal HTML Markup -->
<div id="updateModal" class="modal fade">
    <div class="modal-dialog" style=" overflow-y: initial !important">
        <div class="modal-content">
            <div class="modal-header">
                <div class="col-sm-4" style="padding-left: 0px;">
                	<h2 class="modal-title">Category</h2>
                </div>
                
                <div class="col-sm-1">
                	&nbsp;
                </div>
                
                <div class="col-sm-7" id="updateMessageCategory">
                </div>
            </div>
            <div class="modal-body" style="max-height: 600px; overflow-y: auto !important;">
               	<div class="row">
               		<div class="col-sm-6">
               			<div class="form-group">
	                    	<label class="control-label">Category Title</label>
	                        <div>
	                            <input type="text" readonly id="updateCategoryTitle1" class="form-control updateCategoryName">
	                        </div>
	                    </div>
               		</div>
               		
               		<div class="col-sm-6">
               			<div class="form-group">
	                    	<label class="control-label">Category Title</label>
	                        <div>
	                            <input type="text" required id="updateCategoryTitle2" class="form-control updateCategoryName" pattern="^[a-zA-Z0-9]{1}[a-zA-Z0-9\s]{1,98}$">
	                        </div>
	                    </div>
               		</div>
               		
               		<div id="updateContent">
               		
               		</div>
               	</div>
            </div>
            
            <div class="modal-footer">
            	<div class="col-sm-12" style="text-align:center;">
       				<div class="form-group">
       					<button class="btn normalButton" onclick="updateAddField()">Add Field</button>
       				</div>
   				</div>
   				
   				<div class="col-sm-12" style="text-align:center;">
       				<div class="form-group">
       					<button class="btn normalButton" onclick="updateCategory()">Update Category</button>
       				</div>
   				</div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script>
	var updateNumberOfFields = Number(0);
	
	function updateAddField(){
		document.getElementById("updateContent").insertAdjacentHTML("beforeend", "<div class=\"col-sm-12\" style=\"margin-top:15px;\">"
	        	+ "<div class=\"form-group\">"
	    		+ "<div class=\"col-sm-6 insertCategory_" + updateNumberOfFields + "\">"
	    		+ "<label class=\"control-label\">Field Name</label>"
	    		+ "<div>"
	            +	"<input required type=\"text\" value=\"\" class=\"form-control updateCategoryRow\" maxlength=\"100\" pattern=\"^[a-zA-Z0-9]{2,100}$\">"
	        	+ "</div>"
	    	    + "</div>"
	    	    + "<div class=\"col-sm-6 insertCategory_" + updateNumberOfFields + "\">"
	    		+ "<label class=\"control-label\">Field Type</label>"
	    		+ "<div>"
	            +	"<select class=\"form-control updateCategoryRow\" onchange=\"insertFieldTypeChanged(this.value, " + updateNumberOfFields + ")\">"
	            + 	"<option value=\"" + TYPE_TEXT + "\" selected=\"selected\">" + TYPE_TEXT + "</option>"
	            + 	"<option value=\"" + TYPE_FIXED + "\">" + TYPE_FIXED + "</option>"
	            + 	"<option value=\"" + TYPE_NUMBER + "\">" + TYPE_NUMBER + "</option>"
	            +   "</select>"
	        	+ "</div>"
	    	    + "</div>"
	    	    
	    	    + "<div class=\"col-sm-3\" style=\"display:none;\">"
	    		+ "<label class=\"control-label\">Min Value</label>"
	    		+ "<div>"
	            +	"<input required type=\"number\" value=\"0\" class=\"form-control updateCategoryRow insertCategory_" + updateNumberOfFields + "\" min=\"0\">"
	        	+ "</div>"
	    	    + "</div>"
	    	    
	    	    + "<div class=\"col-sm-3\" style=\"display:none;\">"
	    		+ "<label class=\"control-label\">Max Value</label>"
	    		+ "<div>"
	            +	"<input required type=\"number\" value=\"1\" class=\"form-control updateCategoryRow insertCategory_" + updateNumberOfFields + "\" min=\"1\">"
	        	+ "</div>"
	    	    + "</div>"
	    	    
	    	    + "<div class=\"col-sm-4\" style=\"display:none;\">"
	    		+ "<label class=\"control-label\">Multiple Values</label>"
	    		+ "<div>"
	            +	"<input required type=\"text\" class=\"form-control updateCategoryRow insertCategory_" + updateNumberOfFields + "\" maxlength=\"398\" pattern=\"^[a-zA-Z0-9]{1,}[a-zA-Z0-9_\s]{0,397}$\" title=\"Set values separated by | sign.\nFor Example: aaa|bbb\">"
	        	+ "</div>"
	    	    + "</div>"
	    	    
	  			+  "</div>"
		 			+ "</div>");
		 		
			updateNumberOfFields++;
	}

	function updateCategory(){
		var ele = document.getElementsByClassName("updateCategoryName");
		var ele1 = document.getElementsByClassName("updateCategoryRow");
		const arrSend = [];
		
		for (let i=3;i<ele.length;i+=2){
			var currObj = {
					"name": ele[i].value + "-" + ele[i-1].value,
					"fieldType": "-",
					"regex": "-",
					"flagFixedMultipleValues": false
			}
			
			arrSend.push(currObj);
		}
		
		for (let i=0;i<ele1.length;i+=5){
			var currObj = {
					"name": ele1[i].value,
					"fieldType": ele1[i+1].value,
					"regex": ele1[i+2].value + "-" + ele1[i+3].value + "-" + ele1[i+4].value,
					"flagFixedMultipleValues": false
			}
			
			arrSend.push(currObj);
		}
		
		var objSend = {
				"title": ele[1].value + "-" + ele[0].value,
				fields: arrSend
		};
		
		$.ajax({
			url: "category?what=updateCategory",
			type: "POST",
			data: {"value": JSON.stringify(objSend)},
			success: function(res){
				if (res.result + "" === "true"){
					loadElementsButton();
				}
				
				var showResult = document.getElementById("updateMessageCategory");
				showResult.innerHTML = "<div class=\"" + ((res.result + "") === "true" ? "alert alert-success" : "alert alert-danger") + "\"> " + ((res.result + "") === "true" ? "Operation Successful" : "Operation Failed") + "</div>";
				
				setTimeout(function(){
					document.getElementById("updateMessageCategory").innerHTML = "";
				}, 3000);
				
			}
		});
	}

</script>