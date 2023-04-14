<jsp:include page="/WEB-INF/pages/index.jsp"></jsp:include>
<jsp:include page="/WEB-INF/pages/users_crud/insert.jsp"></jsp:include>
<jsp:include page="/WEB-INF/pages/users_crud/update.jsp"></jsp:include>
<jsp:include page="/WEB-INF/pages/users_crud/viewMore.jsp"></jsp:include>

<div class="container">
	<div class="row">
		<div class="row">
			<div class="col-sm-12" id="messageUser">
				
			</div>
		</div>
		<div class="row" style="margin-right: 0px;margin-top: 15px;">
			<div class="col-sm-6 centerThem">
				<label id="labelSearchBy" class="centerThem">Search By</label>
				<input type="text" name="searchBy" id="searchBy" onkeyup="loadElementsButton()">
			</div>
			
			<div class="col-sm-4">
				&nbsp;
			</div>
			
			<div class="col-sm-2 centerThem" style="padding-right: 0px;text-align: right;">
				<input type="submit" class="btn normalButton" value="Add" data-toggle="modal" data-target="#myModal1">
			</div>
		</div>
		<div class="row">
		<div class="col-sm-12">
			<h3>View Users</h3>
		</div>
		
		<div class="col-sm-12">
			<table class="table table-striped" id="myTable1">
				<thead>
					<tr>
						<th>ID</th>
						<th>First Name</th>
						<th>Last Name</th>
						<th>View</th>
						<th>Update</th>
						<th>Delete</th>
					</tr>
				</thead>
				
				<tbody id="ViewUsers">
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
			"firstNameOrLastName": document.getElementById("searchBy").value,
			"left":  currLeft,
			"right": increase
		};
		
		check();
		
		$.ajax({
				url: "user?what=selectUsers",
				type: "POST",
				data: obj,
				success: function(res){
					//console.log(res);
					var resObj = res;
					elements = res;
					var table = document.getElementById("ViewUsers");
					table.innerHTML = "";
					
					for (let i=0;i<res.length;i++){
						var row = table.insertRow(0);
						var cell1 = row.insertCell(0);
						var cell2 = row.insertCell(1);
						var cell3 = row.insertCell(2);
						var cell4 = row.insertCell(3);
						var cell5 = row.insertCell(4);
						var cell6 = row.insertCell(4);
	
						cell1.innerHTML = resObj[i].id;
						cell2.innerHTML = resObj[i].firstName;
						cell3.innerHTML = resObj[i].lastName;
						cell4.innerHTML = "<button type=\"button\" class=\"btn btn-info\" onclick=\"openViewModalPersonInfo(" + i + ")\">View More</button>";
						cell5.innerHTML = "<button type=\"button\" class=\"btn btn-warning\" onclick=\"openUpdateModal(" + i + ")\">Update</button>";
						cell6.innerHTML = "<button type=\"button\" class=\"btn btn-danger\" onclick=\"deleteUser(" + i + ")\">Delete</button>";
					}
				}
		});
	}
	
	function getNext(element, value){
		if (!element.className.includes("disabled")){
			currLeft += Number(value) * increase;
			currRight = currLeft + increase;
			
			loadElements();
		}
	}

	function check(){
		var myPagination = document.getElementById("myPagination");
		
		var customObj = {
			"firstNameOrLastName": document.getElementById("searchBy").value
		};
		
		$.ajax({
				url: "user?what=getNumberOfUsers",
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
	
	function deleteUser(id){
		var obj = {
			"id": elements[id].id	
		};
		
		var flagRes = confirm("Are you sure?");
		
		if (flagRes + "" === "true"){
			$.ajax({
				url: "user?what=deleteUser",
				type: "POST",
				data: obj,
				success: function(res){
					if (res.result + "" === "true"){
						loadElementsButton();
					}
					
					//console.log(res.result);
					
					var showResult = document.getElementById("messageUser");
					showResult.innerHTML = "<div class=\"" + ((res.result + "") === "true" ? "alert alert-success" : "alert alert-danger") + "\"> " + ((res.result + "") === "true" ? "Operation Successful" : "Operation Failed") + "</div>";
					
					setTimeout(function(){
						document.getElementById("messageUser").innerHTML = "";
					}, 3000);
				}
			});
		}
	}
	
	function openUpdateModal(id){
		document.getElementById("updateFirstName").value = elements[id].firstName;
		document.getElementById("updateLastName").value = elements[id].lastName;
		document.getElementById("updateUsername").value = elements[id].username;
		document.getElementById("updateEmail").value = elements[id].email;
		document.getElementById("updatePhone").value = elements[id].phone;
		document.getElementById("updateCity").value = elements[id].city;
		document.getElementById("updateId").value = elements[id].id;
		
		$("#myModal2").modal("show");
	}
	
	function openViewModalPersonInfo(id){
		document.getElementById("viewMoreFirstName").value = elements[id].firstName;
		document.getElementById("viewMoreLastName").value = elements[id].lastName;
		document.getElementById("viewMoreUsername").value = elements[id].username;
		document.getElementById("viewMoreEmail").value = elements[id].email;
		document.getElementById("viewMorePhone").value = elements[id].phone;
		document.getElementById("viewMoreCity").value = elements[id].city;
		document.getElementById("viewMoreID").value = elements[id].id;
		
			var obj = {
				"avatar": elements[id].avatar,
				"username": elements[id].username
			};
			
			//console.log(obj.avatar);
			if (obj.avatar + "" !== "undefined"){
				$.ajax({
					url: "user?what=getUserPicturePath",
					type: "POST",
					data: obj,
					success: function(res){
						//console.log(res);
						
						document.getElementById("viewMoreImage").style.cssText = "display:inline;";
						document.getElementById("viewMoreImage").setAttribute("src", "data:image/gif;base64," + res.result);
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
					}
				});
			}else{
				document.getElementById("viewMoreImage").style.cssText = "display:none;";
			}
		
		$("#myModal3").modal("show");
	}

	var current = document.getElementsByClassName("active");
	current[0].className = "";
	document.getElementById("users").className += "active";
</script>