<jsp:include page="index.jsp"></jsp:include>

<jsp:include page="/WEB-INF/readMessage.jsp"></jsp:include>
<div class="container">
	<h2>Messages</h2>

	<div class="row">
		<div class="col-sm-12">
		<div class="panel panel-default">
		  <div class="panel-heading">
		  	<div class="row">
		  		<div class="col-sm-3">Search Options</div>
		  		<div class="col-sm-8"></div>
		  		<div class="col-sm-1" style="text-align: right;"><b data-toggle="collapse" data-target="#demo" style="cursor: grab;">&plus;</b></div>
		  	</div>
		  </div>
		  <div class="panel-body collapse" id="demo">
		  	<div class="row">
		  		<div class="col-sm-6">
		  			<h3 style="margin-top: 0px;margin-bottom: 30px;">Message Type</h3>
		  				<div class="form-group">
		  					<label for="idView">&nbsp;</label>
		  					<select class="form-control" name="messageType" id="messageType">
		  						<!-- nisam koristio 0 1 jer kako bih reprezentovao all?(u bazi tinyint) -->
		  						<option value="All">All</option>
		  						<option value="Read">Read</option>
		  						<option selected="selected" value="NotRead">Not Read</option>
		  					</select>
		  				</div>
		  		</div>
		  		
		  		<div class="col-sm-6">
		  			<h3 style="margin-top: 0px;margin-bottom: 30px;">Message Content</h3>
		  			<div class="form-group">
		  				<label for="idName">&nbsp;</label>
			        	<input type="text" class="form-control" id="messageContent" name="messageContent" maxlength="1000">
	  				</div>
		  		</div>
		  	</div>
		  	
		  	<div class="row">
		  		<div class="col-sm-12" style="text-align: center;margin-top: 30px;">
		  			<button class="btn normalButton" onclick="loadElementsButton()">Search</button>
		  		</div>
		  	</div>
		  </div>
		</div>
		</div>
	</div>
	
	<div class="row">
		<div class="col-sm-12">
			<h3>View Messages</h3>
		</div>
		
		<div class="col-sm-12">
			<table class="table table-striped" id="myTable1">
				<thead>
					<tr>
						<th>Message Title</th>
						<th>Consumer</th>
						<th>DateTime</th>
						<th>Is It Read?</th>
						<th>Read</th>
					</tr>
				</thead>
				
				<tbody id="ViewSupportMessages">
				</tbody>
			</table>
			
			<div class="col-sm-12" style="text-align: center;" id="myPagination">
  				<nav aria-label="...">
				  <ul class="pagination">
				    <li class="page-item disabled" id="previous">
				      <a class="page-link normalButton" href="#" onclick="getNext(this.parentElement, -1)">Previous</a>
				    </li>
				    
				    <li class="page-item active"><a class="page-link" style="background-color: #222;border: 1px solid #222;" href="#" id="leftIndex">1</a></li>
				    
				    <li class="page-item" id="next">
				      <a class="page-link normalButton" href="#" style="color: black;" onclick="getNext(this.parentElement, 1)">Next</a>
				    </li>
				  </ul>
				</nav>
  			</div>
		</div>
	</div>
</div>

<script src="js/supportMessages.js"></script>