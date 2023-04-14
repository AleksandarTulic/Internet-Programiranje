<!-- Modal HTML Markup -->
<div id="myModal2" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="col-sm-4" style="padding-left: 0px;">
                	<h2 class="modal-title">Update User</h2>
                </div>
                
                <div class="col-sm-1">
                	&nbsp;
                </div>
                
                <div class="col-sm-7" id="updateMessage">
                </div>
            </div>
            <div class="modal-body">
                <form action="" method="POST" enctype="multipart/form-data">
                	<div class="row">
                		<div class="col-sm-6">
		                    <div class="form-group">
		                    	<label class="control-label">First Name</label>
		                        <div>
		                            <input required pattern="^[a-zA-Z]{2,100}$"  type="text" class="form-control" name="updateFirstName" id="updateFirstName" maxlength="100" value="">
		                        </div>
		                    </div>
		                    
		                    <div class="form-group">
		                    	<label class="control-label">Last Name</label>
		                        <div>
		                            <input required pattern="^[a-zA-Z]{2,100}$"  type="text" class="form-control" name="updateLastName" id="updateLastName" maxlength="100" value="">
		                        </div>
		                    </div>
		                    
		                    <div class="form-group">
		                    	<label class="control-label">Username</label>
		                        <div>
		                            <input required pattern="^[a-zA-Z0-9]{2,50}$"  type="text" class="form-control" name="updateUsername" id="updateUsername" maxlength="50" value="">
		                        </div>
		                    </div>
		                    
		                    <div class="form-group">
		                    	<label class="control-label">Password</label>
		                        <div>
		                            <input required pattern="^[a-zA-Z0-9]{6,50}$"  type="password" class="form-control" name="updatePassword" id="updatePassword" maxlength="50" value="">
		                        </div>
		                    </div>
                		</div>
                		
                		<div class="col-sm-6">
                			<div class="form-group">
                				<label class="control-label">Email</label>
		                        <div>
		                            <input required type="email" class="form-control" name="updateEmail" id="updateEmail" maxlength="100"> <!-- OVO VRSI REGEX VALIDACIJU AUTOMATSKI -->
		                        </div>
                			</div>
                			
                			<div class="form-group">
                				<label class="control-label">Phone</label>
		                        <div>
		                            <input required pattern="^[0-9]{2,50}$"  type="text" class="form-control" maxlength="50" name="updatePhone" id="updatePhone">
		                        </div>
                			</div>
                			
                			<div class="form-group">
                				<label class="control-label">City</label>
		                        <div>
		                            <input required pattern="^[a-zA-Z0-9]{1,1}[a-zA-Z0-9\s]{1,98}$"  type="text" maxlength="100" class="form-control" name="updateCity" id="updateCity">
		                        </div>
                			</div>
                			
                			<div class="form-group">
                				<label class="control-label">Avatar</label>
		                        <div class="custom-file mb-3">
							      <input type="file" class="custom-file-input" id="updateFile" name="updateFile">
							    </div>
                			</div>
                			
                			<input type="text" required name="updateId" id="updateId" style="display: none" value="">
                		</div>
                	</div>
                    
                    <hr>
                    
                    <div class="form-group">
                    	<div class="row">
                    		<div class="col-sm-4">
                    			&nbsp;
                    		</div>
                    		
	                        <div class="col-sm-4" style="text-align: center">
	                            <input onclick="updateUser()" type="button" class="btn normalButton" value="Update">
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

	function updateUser(){
		var obj = new FormData(document.forms[1]);
		
		if (document.forms[1].checkValidity()){
			$.ajax({
				method: 'post',
			    processData: false,
			    contentType: false,
			    data: obj,
			    url: 'user?what=updateUser',
				success: function(res){
					if (res.result + "" === "true"){
						document.forms[1].reset();
						loadElementsButton();
					}
					
					var showResult = document.getElementById("updateMessage");
					showResult.innerHTML = "<div class=\"" + ((res.result + "") === "true" ? "alert alert-success" : "alert alert-danger") + "\"> " + ((res.result + "") === "true" ? "Operation Successful" : "Operation Failed") + "</div>";
					
					setTimeout(function(){
						document.getElementById("updateMessage").innerHTML = "";
					}, 3000);
				}
			});
		}else{
			var res = {
					"result": false
			};
			
			var showResult = document.getElementById("updateMessage");
			showResult.innerHTML = "<div class=\"" + ((res.result + "") === "true" ? "alert alert-success" : "alert alert-danger") + "\"> " + ((res.result + "") === "true" ? "Operation Successful" : "Operation Failed") + "</div>";
			
			setTimeout(function(){
				document.getElementById("updateMessage").innerHTML = "";
			}, 3000);
		}
	}

</script>