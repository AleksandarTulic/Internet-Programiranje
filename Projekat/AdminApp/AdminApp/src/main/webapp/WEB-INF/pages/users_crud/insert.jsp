<!-- Modal HTML Markup -->
<div id="myModal1" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="col-sm-4" style="padding-left: 0px;">
                	<h2 class="modal-title">Add User</h2>
                </div>
                
                <div class="col-sm-1">
                	&nbsp;
                </div>
                
                <div class="col-sm-7" id="insertMessage">
                </div>
            </div>
            <div class="modal-body">
                <form action="" method="POST" enctype="multipart/form-data">
                	<div class="row">
                		<div class="col-sm-6">
		                    <div class="form-group">
		                    	<label class="control-label">First Name</label>
		                        <div>
		                            <input required pattern="^[a-zA-Z]{2,100}$"  type="text" class="form-control" name="insertFirstName" id="insertFirstName" maxlength="100" value="">
		                        </div>
		                    </div>
		                    
		                    <div class="form-group">
		                    	<label class="control-label">Last Name</label>
		                        <div>
		                            <input required pattern="^[a-zA-Z]{2,100}$"  type="text" class="form-control" name="insertLastName" id="insertLastName" maxlength="100" value="">
		                        </div>
		                    </div>
		                    
		                    <div class="form-group">
		                    	<label class="control-label">Username</label>
		                        <div>
		                            <input required pattern="^[a-zA-Z0-9]{2,50}$"  type="text" class="form-control" name="insertUsername" id="insertUsername" maxlength="50" value="">
		                        </div>
		                    </div>
		                    
		                    <div class="form-group">
		                    	<label class="control-label">Password</label>
		                        <div>
		                            <input required pattern="^[a-zA-Z0-9]{6,50}$"  type="password" class="form-control" name="insertPassword" id="insertPassword" maxlength="50" value="">
		                        </div>
		                    </div>
                		</div>
                		
                		<div class="col-sm-6">
                			<div class="form-group">
                				<label class="control-label">Email</label>
		                        <div>
		                            <input required type="email" class="form-control" name="insertEmail" id="insertEmail" maxlength="100"> <!-- OVO VRSI REGEX VALIDACIJU AUTOMATSKI -->
		                        </div>
                			</div>
                			
                			<div class="form-group">
                				<label class="control-label">Phone</label>
		                        <div>
		                            <input required pattern="^[0-9]{2,50}$"  type="text" class="form-control" maxlength="50" name="insertPhone" id="insertPhone">
		                        </div>
                			</div>
                			
                			<div class="form-group">
                				<label class="control-label">City</label>
		                        <div>
		                            <input required pattern="^[a-zA-Z0-9]{1,1}[a-zA-Z0-9\s]{1,98}$"  type="text" maxlength="100" class="form-control" name="insertCity" id="insertCity">
		                        </div>
                			</div>
                			
                			<div class="form-group">
                				<label class="control-label">Avatar</label>
		                        <div class="custom-file mb-3">
							      <input type="file" class="custom-file-input" id="insertFile" name="insertFile">
							      <label class="custom-file-label" for="customFile">Choose file</label>
							    </div>
                			</div>
                		</div>
                	</div>
                    
                    <hr>
                    
                    <div class="form-group">
                    	<div class="row">
                    		<div class="col-sm-4">
                    			&nbsp;
                    		</div>
                    		
	                        <div class="col-sm-4" style="text-align: center">
	                            <input onclick="insertUser()" type="button" class="btn normalButton" value="Add">
	                        </div>
	                        
	                        <div class="col-sm-4">
                    			&nbsp;
                    		</div>
                        </div>
                    </div>
                </form>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script>

	function insertUser(){
		var obj = new FormData(document.forms[0]);
		
		if (document.forms[0].checkValidity() === true){
			$.ajax({
				method: 'post',
			    processData: false,
			    contentType: false,
			    data: obj,
			    url: 'user?what=insertUser',
				success: function(res){
					if (res.result + "" === "true"){
						document.forms[0].reset();
						loadElementsButton();
					}
					
					var showResult = document.getElementById("insertMessage");
					showResult.innerHTML = "<div class=\"" + ((res.result + "") === "true" ? "alert alert-success" : "alert alert-danger") + "\"> " + ((res.result + "") === "true" ? "Operation Successful" : "Operation Failed") + "</div>";
					
					setTimeout(function(){
						document.getElementById("insertMessage").innerHTML = "";
					}, 3000);
				}
			});
		}else{
			var res = {
					"result": false
			};
			
			var showResult = document.getElementById("insertMessage");
			showResult.innerHTML = "<div class=\"" + ((res.result + "") === "true" ? "alert alert-success" : "alert alert-danger") + "\"> " + ((res.result + "") === "true" ? "Operation Successful" : "Operation Failed") + "</div>";
			
			setTimeout(function(){
				document.getElementById("insertMessage").innerHTML = "";
			}, 3000);
		}
	}

</script>