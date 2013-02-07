<%@page import="org.bmi.cchmc.cohorttool.util.ServletUtilities"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<link href="http://twitter.github.com/bootstrap/assets/css/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="http://twitter.github.com/bootstrap/assets/css/bootstrap-responsive.css" rel="stylesheet" type="text/css" />
<meta charset=utf-8 />
<title>Continue Analysis</title>
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
	<div class="tabbable">
		<ul class="nav nav-pills">
		  <li class="active"><a href="#patients" data-toggle="tab">Patient Info</a></li>
		  <li><a href="#new" data-toggle="tab">Add New Analysis</a></li>
		  <li class="dropdown">
		  	<a href="#" class="dropdown-toggle" data-toggle="dropdown">Available Analyses<b class="caret"></b></a>
		  	<ul class="dropdown-menu">
				<%= ServletUtilities.getAnalysisTabs((String) request.getParameter("id")) %>
		  	</ul>
		  </li>
		  <li><a href="#long" data-toggle="tab">Long Term Link</a></li>
		</ul>

		<div class="tab-content">
		  <div class="tab-pane active" id="patients">
		  	<%=request.getParameter("patientset") %>
		  </div>
		  <div class="tab-pane" id="new">New Analysis<br>
		  		<form action="/CohortTool/Analysis/" method="post">
		  			<fieldset>
		  				<input type="text" placeholder="Name of Analysis" class="span3" name="name" />
		  				<label class="checkbox">
		  					<input type="checkbox" name="heterozygous" /> Remove Heterozygous Mutations<br>
		  				</label>
		  				<label class="checkbox">
		  					<input type="checkbox" name="homozygous"/> Remove Homozygous Mutations
		  				</label>
		  				<label class="checkbox">
		  					<input type="checkbox" name="rsID"/> Remove Mutations in dbSNP
		  				</label>
		  				<input type="hidden" name="id" value="<%= request.getParameter("id") %>" />
		  			</fieldset>
		  			<button type="submit" class="btn">Submit</button>
		  		</form>
		  	</div>
		  	<div class="tab-pabe" id="long">
		  		<h3>Long term link</h3><br>
		  		<code>localhost:8080/CohortTool/Load.jsp?id=<%=request.getParameter("id") %></code>
		  	</div>
		  	<%=ServletUtilities.getAllAnalysisTables( (String) request.getParameter("id")) %>
		</div>
	</div>
</div>
<script src="http://code.jquery.com/jquery.min.js"></script>
<script src="http://twitter.github.com/bootstrap/assets/js/bootstrap.js"></script>
<script>
$(function() {
	$("#myTabs").tab(); // initialize tabs
	$("#myTabs").bind("show", function(e) {
	var contentID = $(e.target).attr("data-target");
	var contentURL = $(e.target).attr("href");

	if (typeof(contentURL) != 'undefined') {
	// state: has a url to load from
	$(contentID).load(contentURL, function(){
	$("#myTabs").tab(); // reinitialize tabs
	});

	} else {
	//state: no url, to show static data
	$(contentID).tab('show');
	}
	});
	$('#myTabs a:first').tab("show"); // Load and display content for first tab
</script>
</body>
</html>