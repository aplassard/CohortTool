<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Run SNPomics</title>
<link href="./bootstrap/css/bootstrap.css" rel="stylesheet">
<style>
body {
padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
}
</style>
<link href="./bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<style type="text/css"></style>
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

<div class="container">
<h2 class="">Annotating VCF</h2>
<br><h3 class="">This may take a while, so watch Star Wars Episode 4 while you wait!</h3>
<br>
<img src="${pageContext.request.contextPath}/content/StarWars4.gif" alt="Star Wars"><br><br>
</div>
<form name="auto" action="/CohortTool/RunSNPomics" method="POST">  
    <input type="hidden" name="filename" value="<%=request.getAttribute("filename")%>"> 
    <input type="hidden" name="id" value="<%=request.getAttribute("id") %>">
</form>  
<!-- this script submits the form AFTER it has been completely loaded -->  
<script>  
    document.auto.submit();  
</script>  
<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="./bootstrap/js/jquery.js"></script>
<script src="./bootstrap/js/bootstrap-transition.js"></script>
<script src="./bootstrap/js/bootstrap-alert.js"></script>
<script src="./bootstrap/js/bootstrap-modal.js"></script>
<script src="./bootstrap/js/bootstrap-dropdown.js"></script>
<script src="./bootstrap/js/bootstrap-scrollspy.js"></script>
<script src="./bootstrap/js/bootstrap-tab.js"></script>
<script src="./bootstrap/js/bootstrap-tooltip.js"></script>
<script src="./bootstrap/js/bootstrap-popover.js"></script>
<script src="./bootstrap/js/bootstrap-button.js"></script>
<script src="./bootstrap/js/bootstrap-collapse.js"></script>
<script src="./bootstrap/js/bootstrap-carousel.js"></script>
<script src="./bootstrap/js/bootstrap-typeahead.js"></script>
</body>
</html>

