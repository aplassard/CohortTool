<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<link href="http://twitter.github.com/bootstrap/assets/css/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="http://twitter.github.com/bootstrap/assets/css/bootstrap-responsive.css" rel="stylesheet" type="text/css" />
<meta charset=utf-8 />
<title>Start Analysis</title>
</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
			<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</a>
			<a class="brand" href="/CohortTool/index.jsp">Cohort Analyzer</a>
 			<div class="nav-collapse collapse">
				<ul class="nav">
					<li><a href="/CohortTool/">Home</a></li>
					<li><a href="/CohortTool/about.jsp">About</a></li>
					<li><a href="/CohortTool/publications.jsp">Publications</a></li>
 				</ul>
			</div><!--/.nav-collapse -->
		</div>
	</div>
</div>
<br><br><br>
<div class="span9 offset1">
	<h4>Add New Client</h4>
	<div class="tabbable">
		<ul class="nav nav-tabs">
		  <li class="active"><a href="#patients" data-toggle="tab">Patient Info</a></li>
		  <li><a href="#new" data-toggle="tab">Add New Analysis</a></li>
		</ul>

		<div class="tab-content">
		  <div class="tab-pane active" id="patients"><%=request.getAttribute("patientset") %></div>
		  <div class="tab-pane" id="new">New Analysis</div>
		</div>
	</div>
</div>
<script src="http://code.jquery.com/jquery.min.js"></script>
<script src="http://twitter.github.com/bootstrap/assets/js/bootstrap.js"></script>
</body>
</html>