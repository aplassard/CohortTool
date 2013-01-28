<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Cohort Analyzer</title>
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

      <h2>Welcome to the Cohort Analyzer for Large Scale Mendelian Diseases</h2>
      <div>
      <form method="post" action="/CohortTool/upload" enctype="multipart/form-data">
      VCF File: <input type="file" name="vcf">
      PED File: <input type="file" name="ped"><br>
      Analysis Name: <input type="text" name="name"><br>
      User Name: <input type="text" name="username"><br>
      <input type="submit" name="submit" value="upload">
      </form>
      </div>

    </div> <!-- /container -->

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