<%@ page import="models.dto.Admin" %>

<nav class="navbar navbar-inverse" style="border-radius: 0px !important;">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="navigation?what=index">Admin System</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class="active"><a href="navigation?what=index">Home</a></li>
        <li id="users"><a href="navigation?what=users">Users</a></li>
        <li id="categories"><a href="navigation?what=categories">Categories</a></li>
        <li id="logs"><a href="navigation?what=logs">Logs</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
      	  <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">
			<%
				
				if (session.getAttribute("admin") != null)
					out.println(((Admin)session.getAttribute("admin")).getUsername());
			
			%>
			<span class="caret"></span></a>
	        <ul class="dropdown-menu">
	          <li><a href="navigation?what=view">View</a></li>
	          <li><a href="navigation?what=logout">Log out</a></li>
	          <!-- OVDE MOZEMO DA PROSIRIMO AKO TREBA POSEBNO DA ULOGOVANI KORISNIK VIDI SVOJE INFORMACIJE -->
	        </ul>
	      </li>
      </ul>
    </div>
  </div>
</nav>