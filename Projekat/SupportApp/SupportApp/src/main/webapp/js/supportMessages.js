var currLeft = Number(0);
var currRight = Number(10);
var increase = Number(10);
var numberOfPages = Number(0);
var currentSelectedElement = Number(-1);

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
	var messageType = document.getElementById("messageType").value;
	var messageContent = document.getElementById("messageContent").value;
	
	var obj = {
		"messageType": messageType,
		"messageContent": messageContent,
		"leftNumber":  currLeft,
		"rightNumber": increase
	};
	
	check();
	
	$.ajax({
			url: "data?what=getSupportMessages",
			type: "POST",
			data: obj,
			success: function(res){
				//console.log(res);
				var resObj = res;
				elements = res;
				var table = document.getElementById("ViewSupportMessages");
				table.innerHTML = "";
				
				for (let i=0;i<res.length;i++){
					var row = table.insertRow(0);
					var cell1 = row.insertCell(0);
					var cell2 = row.insertCell(1);
					var cell3 = row.insertCell(2);
					var cell4 = row.insertCell(3);
					var cell5 = row.insertCell(4);

					cell1.innerHTML = resObj[i].title;
					cell2.innerHTML = resObj[i].consumer.firstName + " " + resObj[i].consumer.lastName;
					cell3.innerHTML = resObj[i].datetime.split(" ")[0] + " " + resObj[i].datetime.split(" ")[1];
					cell4.innerHTML = resObj[i].read + "" === false + "" ? "No" : "Yes";
					cell5.innerHTML = "<button type=\"button\" class=\"btn normalButton\" onclick=\"readMessage(" + i + ")\">Read</button>";
				}
			}
	});
}

function readMessage(id){
	currentSelectedElement = id;
	document.getElementById("messageReceivedTextArea").value = "";
	document.getElementById("messageSentTextArea").value = "";
	
	document.getElementById("messageReceivedTextArea").value = elements[id].message;
	if (elements[id].return_message != undefined)
		document.getElementById("messageSentTextArea").value = elements[id].return_message;
	$('#myModal').modal('show');
	
	if (elements[id].read + "" === "false"){
		var obj = {
			"id": elements[id].id
		};
		
		$.ajax({
				url: "cud?what=updateRead",
				type: "POST",
				data: obj,
				success: function(res){
					//console.log(res);
					if (res + "" === "true"){
						elements[id].read = true;
						var table = document.getElementById("ViewSupportMessages");
						var rows = table.rows;
						var cols = rows[elements.length - id - 1].cells;
						cols[3].innerHTML = "Yes";
					}
				}
		});
	}
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
	var countObj = {
		"messageType": document.getElementById("messageType").value
	};
	
	$.ajax({
			url: "data?what=getNumberOfMessages",
			type: "POST",
			data: countObj,
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

function sendMessage(){
	var value = document.getElementById("messageSentTextArea").value;
	var obj = {
		"id": elements[currentSelectedElement].id,
		"return_message": value,
		"email": elements[currentSelectedElement].consumer.email,
		"firstName": elements[currentSelectedElement].consumer.firstName,
		"lastName": elements[currentSelectedElement].consumer.lastName
	};
	
	console.log(new RegExp("^.{1,1000}$").test(value + ""));
	if (!(value + "" === "undefined" || value.length === 0) && new RegExp("^.{1,1000}$").test(value + "") + "" === "true"){
		$.ajax({
			url: "cud?what=updateReturnMessage",
			type: "POST",
			data: obj,
			success: function(res){
				if (res + "" === "true")
					elements[currentSelectedElement].return_message = value;
			
				var showResult = document.getElementById("message");
				showResult.innerHTML = "<div class=\"" + ((res + "") === "true" ? "alert alert-success" : "alert alert-danger") + "\"> " + ((res + "") === "true" ? "Operation Successful" : "Operation Failed") + "</div>";
				
				setTimeout(function(){
					document.getElementById("message").innerHTML = "";
				}, 3000);
			}
		});
	}
}

var current = document.getElementsByClassName("active");
current[0].className = "";
document.getElementById("supportMessages").className += "active";