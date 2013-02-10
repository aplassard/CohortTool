<%@page import="org.bmi.cchmc.cohorttool.util.ServletUtilities"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<link href="http://twitter.github.com/bootstrap/assets/css/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="http://twitter.github.com/bootstrap/assets/css/bootstrap-responsive.css" rel="stylesheet" type="text/css" />
<meta charset=utf-8 />
<title>Analysis</title>
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
		<%= ServletUtilities.getTabContent((String) request.getAttribute("name")) %>
		<div class="tab-pane" id="long">
		  	<h3>Long term link</h3><br>
		  	<a href="<%=request.getServerName().toString()%>:<%=request.getServerPort() %>/CohortTool/Load.jsp?id=<%=request.getAttribute("name") %>"><%=request.getServerName().toString()%>:<%=request.getServerPort() %>/CohortTool/Load.jsp?id=<%=request.getAttribute("name") %></a>
		</div>
		</div>
	</div>
</div> <!-- Extra div added in getTabContent --> 
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