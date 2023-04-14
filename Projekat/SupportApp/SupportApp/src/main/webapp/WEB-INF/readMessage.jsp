<div id="myModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <div class="col-sm-4" style="padding-left: 0px;">
                	<h2 class="modal-title">Messages</h2>
                </div>
                
                <div class="col-sm-1">
                	&nbsp;
                </div>
                
                <div class="col-sm-7" id="message">
                </div>
            </div>
            <div class="modal-body">
                <form action="Insert" method="POST">
                	<div class="row">
                		<div class="col-sm-6">
                			<div class="form-group">
		                        <label class="control-label">Message Received</label>
		                        <div>
		                            <textarea style="resize: vertical;" class="form-control" rows="5" id="messageReceivedTextArea"></textarea>
		                        </div>
		                    </div>
                		</div>
                		
                		<div class="col-sm-6">
                			<div class="form-group">
                				<label class="control-label">Message Sent</label>
		                        <div>
		                            <textarea style="resize: vertical;" class="form-control" rows="5" id="messageSentTextArea"></textarea>
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
	                            <input id="SendMessageButton" onclick="sendMessage()" type="button" class="btn normalButton" value="Send Message">
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