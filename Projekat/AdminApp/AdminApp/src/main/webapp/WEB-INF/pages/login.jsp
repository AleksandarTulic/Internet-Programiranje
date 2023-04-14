<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
	<head>
		<jsp:include page="/WEB-INF/pages/common/externalElements.jsp"/>
	</head>
	
	<body>
		<div class="container">
			<div class="row">
				<div class="col-sm-2">
				</div>
				<div class="col-sm-8">
					<h2>Admin System</h2>
					<form method="POST" action="login">
						<div class="form-group">
							<p>Username: <input required type="text" name="username" class="form-control" pattern="[a-zA-Z0-9]{2,50}"/></p>
						</div>
						
						<div class="form-group">
							<p>Password: <input required type="password" class="form-control" name="password" pattern="[a-zA-Z0-9]{6,50}"/></p>
						</div>
						<p>  <input style="width: 50%;margin-right: 25%;margin-left: 25%;" type="submit" value="Login" class="btn btn-default"/></p>
					</form>
				</div>
				<div class="col-sm-2">
				</div>
			</div>
		</div>   
	</body>
</html>