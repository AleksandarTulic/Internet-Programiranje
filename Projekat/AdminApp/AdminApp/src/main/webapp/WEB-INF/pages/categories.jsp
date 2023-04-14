<jsp:include page="/WEB-INF/pages/index.jsp"></jsp:include>
<jsp:include page="/WEB-INF/pages/categories_crud/viewMore.jsp"></jsp:include>
<jsp:include page="/WEB-INF/pages/categories_crud/insert.jsp"></jsp:include>
<jsp:include page="/WEB-INF/pages/categories_crud/update.jsp"></jsp:include>

<div class="container">
	<div class="row">
		<div class="row">
			<div class="col-sm-12" id="messageCategory">
				
			</div>
		</div>
		<div class="row" style="margin-right: 0px;margin-top: 15px;">
			<div class="col-sm-6 centerThem">
				<h3>View Categories</h3>
			</div>
			
			<div class="col-sm-4">
				&nbsp;
			</div>
			
			<div class="col-sm-2 centerThem" style="padding-right: 0px;text-align: right;margin-top:15px;">
				<input type="submit" class="btn normalButton" value="Add" data-toggle="modal" data-target="#insertModal">
			</div>
		</div>
		<div class="row">
		
		<div class="col-sm-12">
			<table class="table table-striped" id="myTable1">
				<thead>
					<tr>
						<th>Category Title</th>
						<th>Number Of Fields</th>
						<th>View</th>
						<th>Update</th>
						<th>Delete</th>
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

	const TYPE_NUMBER = "NUMBER";
	const TYPE_TEXT = "TEXT";
	const TYPE_FIXED = "FIXED";
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
			"left":  currLeft,
			"right": increase
		};
		
		check();
		
		$.ajax({
				url: "category?what=selectCategories",
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
	
						cell1.innerHTML = resObj[i].title;
						cell2.innerHTML = resObj[i].fields.length;;
						cell3.innerHTML = "<button type=\"button\" class=\"btn btn-info\" onclick=\"viewMore(" + i + ")\">View More</button>";
						cell4.innerHTML = "<button type=\"button\" class=\"btn btn-warning\" onclick=\"openUpdateModal(" + i + ")\">Update</button>";
						cell5.innerHTML = "<button type=\"button\" class=\"btn btn-danger\" onclick=\"deleteCategory(" + i + ")\">Delete</button>";
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
		
		$.ajax({
				url: "category?what=getNumberOfCategories",
				type: "POST",
				data: null,
				success: function(res){
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
	
	function deleteCategory(id){
		var obj = {
			"title": elements[id].title	
		};
		
		var flagRes = confirm("Are you sure?");
		
		if (flagRes + "" === "true"){
			$.ajax({
				url: "category?what=deleteCategory",
				type: "POST",
				data: obj,
				success: function(res){
					if (res.result + "" === "true"){
						loadElementsButton();
					}
					
					//console.log(res.result);
					
					var showResult = document.getElementById("messageCategory");
					showResult.innerHTML = "<div class=\"" + ((res.result + "") === "true" ? "alert alert-success" : "alert alert-danger") + "\"> " + ((res.result + "") === "true" ? "Operation Successful" : "Operation Failed") + "</div>";
					
					setTimeout(function(){
						document.getElementById("messageCategory").innerHTML = "";
					}, 3000);
				}
			});
		}
	}
	
	function openUpdateModal(id){
		document.getElementById("updateCategoryTitle1").value = elements[id].title;
		document.getElementById("updateContent").innerHTML = "";
		
		for (let i=0;i<elements[id].fields.length;i++){
			var obj = elements[id].fields[i];
			//console.log(obj);
				document.getElementById("updateContent").innerHTML += "<div class=\"col-sm-12\">"
                	+ "<div class=\"form-group\">"
            		+ "<div class=\"col-sm-6\">"
            		+ "<label class=\"control-label\">Field Name</label>"
            		+ "<div>"
                    +	"<input type=\"text\" readonly value=\"" + obj.name +"\" class=\"form-control updateCategoryName\">"
                	+ "</div>"
            	    + "</div>"
            	    + "<div class=\"col-sm-6\">"
            		+ "<label class=\"control-label\">Field Name</label>"
            		+ "<div>"
                    +	"<input type=\"text\" required class=\"form-control updateCategoryName\" pattern=\"^[a-zA-Z0-9]{1}[a-zA-Z0-9\s]{1,98}$\">"
                	+ "</div>"
            	    + "</div>"
          			+  "</div>"
   		 			+ "</div>";
		}
		
		$("#updateModal").modal("show");
	}
	
	function viewMore(id){
		//console.log(elements[id]);
		//viewMoreModal
		//viewMoreContent
		
		document.getElementById("viewMoreCategoryTitle").value = elements[id].title;
		document.getElementById("viewMoreContent").innerHTML = "";
		
		for (let i=0;i<elements[id].fields.length;i++){
			var obj = elements[id].fields[i];
			//console.log(obj);
			if (obj.fieldType + "" === TYPE_TEXT + ""){
				document.getElementById("viewMoreContent").innerHTML += "<div class=\"col-sm-12\">"
                	+ "<div class=\"form-group\">"
            		+ "<div class=\"col-sm-12\">"
            		+ "<label class=\"control-label\">Field Name</label>"
            		+ "<div>"
                    +	"<input type=\"text\" readonly value=\"" + obj.name +"\" class=\"form-control\">"
                	+ "</div>"
            	    + "</div>"
          			+  "</div>"
   		 			+ "</div>";
			}else if (obj.fieldType + "" === TYPE_FIXED + ""){
				var arrSp = obj.regex.split("|");
				var addText = "";
				
				for (let j=0;j<arrSp.length;j++){
					var valueAdd = arrSp[j];
					//console.log(valueAdd);
					
					valueAdd = arrSp[j].split("^")[1].split("$")[0];
					
					addText += "<option>" + valueAdd + "</option>";
				}
				
				document.getElementById("viewMoreContent").innerHTML += "<div class=\"col-sm-12\">"
                	+ "<div class=\"form-group\">"
            		+ "<div class=\"col-sm-6\">"
            		+ "<label class=\"control-label\">Field Name</label>"
            		+ "<div>"
                    +	"<input type=\"text\" readonly value=\"" + obj.name +"\" class=\"form-control\">"
                	+ "</div>"
            	    + "</div>"
            	    + "<div class=\"col-sm-6\">"
            		+ "<label class=\"control-label\">Possible Values</label>"
            		+ "<div>"
                    +	"<select readonly class=\"form-control\">"
                    + addText
                    + 	"</select>"
                	+ "</div>"
            	    + "</div>"
          			+  "</div>"
   		 			+ "</div>";
			}else if (obj.fieldType + "" === TYPE_NUMBER + ""){
				document.getElementById("viewMoreContent").innerHTML += "<div class=\"col-sm-12\">"
            	+ "<div class=\"form-group\">"
        		+ "<div class=\"col-sm-4\">"
        		+ "<label class=\"control-label\">Field Name</label>"
        		+ "<div>"
                +	"<input type=\"text\" readonly value=\"" + obj.name +"\" class=\"form-control\">"
            	+ "</div>"
        	    + "</div>"
        	    + "<div class=\"col-sm-4\">"
        		+ "<label class=\"control-label\">Min Value</label>"
        		+ "<div>"
                +	"<input type=\"text\" readonly value=" + obj.regex.split("|")[0] +" class=\"form-control\">"
            	+ "</div>"
        	    + "</div>"
        	    + "<div class=\"col-sm-4\">"
        		+ "<label class=\"control-label\">Max Value</label>"
        		+ "<div>"
                +	"<input type=\"text\" readonly value=" + obj.regex.split("|")[1] +" class=\"form-control\">"
            	+ "</div>"
        	    + "</div>"
      			+  "</div>"
		 			+ "</div>";
			}
		}
		
		$("#viewMoreModal").modal("show");
	}

	var current = document.getElementsByClassName("active");
	current[0].className = "";
	document.getElementById("categories").className += "active";
</script>