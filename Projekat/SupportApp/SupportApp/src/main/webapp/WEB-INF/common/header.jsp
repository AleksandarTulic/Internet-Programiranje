<%@ page import="models.dto.SupportSpecialist" %>

<nav class="navbar navbar-inverse" style="border-radius: 0px !important;">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="index.jsp">Support System</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class="active"><a href="index.jsp">Home</a></li>
        <li id="supportMessages"><a href="supportMessages.jsp">Support Messages</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
      	  <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">
			<%
				
				if (session.getAttribute("supportSpecialist") != null)
					out.println(((SupportSpecialist)session.getAttribute("supportSpecialist")).getUsername());
			
			%>
			<span class="caret"></span></a>
	        <ul class="dropdown-menu">
	          <li><a href="view.jsp">View</a></li>
	          <li><a href="logout.jsp">Log out</a></li>
	          <!-- OVDE MOZEMO DA PROSIRIMO AKO TREBA POSEBNO DA ULOGOVANI KORISNIK VIDI SVOJE INFORMACIJE -->
	        </ul>
	      </li>
      </ul>
    </div>
  </div>
</nav>