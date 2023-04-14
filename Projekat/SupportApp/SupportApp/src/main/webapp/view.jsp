<jsp:include page="index.jsp"></jsp:include>

<%@ page import="models.dto.SupportSpecialist" %>

<%

	SupportSpecialist dto = (SupportSpecialist)session.getAttribute("supportSpecialist");

%>
<div class="container">
	<div class="row">
		<div class="col-sm-4">
			<h3>View Personal Info</h3>
		</div>
		
		<div class="col-sm-8">
			&nbsp;
		</div>
	</div>
	<div class="row">
		<div class="col-sm-12">
			<div class="table-responsive" style="margin-top: 10px;">
		    	<table id="myTable1" class="table table-striped" style="text-align: center;">
		    		<tbody>
		    			<tr>
		    				<th style="text-align: center;">ID:</th>
		    				<%
		    				
		    					if (dto != null){
		    						out.println("<td>" + dto.getId() + "</td>");
		    					}
		    				
		    				%>
		    			</tr>
		    			
		    			<tr>
		    				<th style="text-align: center;">Name:</th>
		    				<%
		    				
		    					if (dto != null){
		    						out.println("<td>" + dto.getFirstName() + "</td>");
		    					}
		    				
		    				%>
		    			</tr>
		    			
		    			<tr>
		    				<th style="text-align: center;">Surname:</th>
		    				<%
		    				
		    					if (dto != null){
		    						out.println("<td>" + dto.getLastName() + "</td>");
		    					}
		    				
		    				%>
		    			</tr>
		    			
		    			<tr>
		    				<th style="text-align: center;">Username:</th>
		    				<%
		    				
		    					if (dto != null){
		    						out.println("<td>" + dto.getUsername() + "</td>");
		    					}
		    				
		    				%>
		    			</tr>
		    		</tbody>
	   			</table>
			</div>
		</div>
	</div>
</div>