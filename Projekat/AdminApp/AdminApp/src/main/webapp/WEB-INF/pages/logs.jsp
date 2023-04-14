<jsp:include page="/WEB-INF/pages/index.jsp"></jsp:include>

<div class="container">
	<div class="row">
		<div class="row">
			<div class="col-sm-12" id="messageCategory">
				
			</div>
		</div>
		<div class="row" style="margin-right: 0px;margin-top: 15px;">
			<div class="col-sm-6 centerThem">
				<h3>View Logs</h3>
			</div>
			
			<div class="col-sm-4">
				&nbsp;
			</div>
			
			<div class="col-sm-2 centerThem" style="padding-right: 0px;text-align: right;margin-top:15px;">
				<label id="labelSearchBy" class="centerThem">Search By</label>
				<input type="text" name="searchBy" id="searchBy" onkeyup="loadElementsButton()">
			</div>
		</div>
		<div class="row">
		
		<div class="col-sm-12">
			<table class="table table-striped" id="myTable1">
				<thead>
					<tr>
						<th>Consumer</th>
						<th>Level</th>
						<th>Description</th>
						<th>Date</th>
						<th>time</th>
					</tr>
				</thead>
				
				<tbody id="ViewCategories">
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
</div>

<script>

	var currLeft = Number(0);
	var currRight = Number(10);
	var increase = Number(10);
	var numberOfPages = Number(0);
	
	var elements = [];
	
	loadElements();
	
	function loadElementsButton(){
		currLeft = Number(0);
		currRight = Number(10);
		numberOfPages = Number(0);
		document.getElementById("leftIndex").innerHTML = 1;
		
		loadElements();
	}
	
	function loadElements(){
		var obj = {
			"searchBy": document.getElementById("searchBy").value,
			"left":  currLeft,
			"right": increase
		};
		
		check();
		
		$.ajax({
				url: "log?what=selectLogs",
				type: "POST",
				data: obj,
				success: function(res){
					//console.log(res);
					var resObj = res;
					elements = res;
					var table = document.getElementById("ViewCategories");
					table.innerHTML = "";
					
					for (let i=0;i<res.length;i++){
						var row = table.insertRow(0);
						var cell1 = row.insertCell(0);
						var cell2 = row.insertCell(1);
						var cell3 = row.insertCell(2);
						var cell4 = row.insertCell(3);
						var cell5 = row.insertCell(4);
	
						cell1.innerHTML = resObj[i].user.firstName + " " + resObj[i].user.lastName;
						cell2.innerHTML = resObj[i].level;
						cell3.innerHTML = resObj[i].description;
						cell4.innerHTML = resObj[i].date;
						cell5.innerHTML = resObj[i].time;
					}
				}
		});
	}
	
	function getNext(element, value){
		if (!element.className.includes("disabled")){
			currLeft += Number(value) * increase;
			currRight = currLeft + increase;
			
			console.log(currLeft + " " + currRight);
			loadElements();
		}
	}
	
	function check(){
		var myPagination = document.getElementById("myPagination");
		
		var customObj = {
			"searchBy": document.getElementById("searchBy").value
		};
		
		$.ajax({
				url: "log?what=getNumberOfLogs",
				type: "POST",
				data: customObj,
				success: function(res){
					//console.log(res);
					var myObj = JSON.parse(res);
					numberOfPages = parseInt(myObj.number / increase, 10) + (myObj.number % increase === 0 ? 0 : 1);
					
					if (numberOfPages === 0){
						myPagination.style.display = "none";
					}else{
						myPagination.style.display = "block";
						if (numberOfPages === 1){
							document.getElementById("previous").className = "page-item disabled";
							document.getElementById("next").className = "page-item disabled";
							document.getElementById("previous").children[0].style.color = "gray";
							document.getElementById("next").children[0].style.color = "gray";
						}else{
							document.getElementById("previous").children[0].className = currLeft === 0 ? "page-item disabled" : "page-item";
							document.getElementById("previous").children[0].style.color = currLeft === 0 ? "gray" : "black";
							document.getElementById("previous").className = currLeft === 0 ? "page-item disabled" : "page-item";
							document.getElementById("next").className = currRight >= myObj.number ? "page-item disabled" : "page-item";
							document.getElementById("next").children[0].className = currRight >= myObj.number ? "page-item" : "page-item normalButton";
							document.getElementById("next").children[0].style.color = currRight >= myObj.number ? "gray" : "black";
							
							document.getElementById("leftIndex").innerHTML = (currLeft / increase) + 1;
						}
					}
				}
		});
	}

	var current = document.getElementsByClassName("active");
	current[0].className = "";
	document.getElementById("logs").className += "active";

</script>