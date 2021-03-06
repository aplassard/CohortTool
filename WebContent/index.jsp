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
      PED File: <input type="file" name="ped"> 
      <div id="example" class="modal hide fade in" style="display: none; ">
            <div class="modal-header">
              <a class="close" data-dismiss="modal">�</a>
              <h3>Example PED file</h3>
            </div>
            <div class="modal-body">
              <p>Sample,Family ID,Patient ID,Father ID,Mother ID,Sex,Affection Status
708.1,708,708.1,708.3,708.2,0,1
708.2,708,708.2,?,?,1,0
708.3,708,708.3,?,?,0,0
808.1,808,808.1,808.3,808.2,0,1
808.2,808,808.2,?,?,1,0
808.3,808,808.3,?,?,0,0
1164.1,1164,1164.1,1164.3,1164.2,1,1
1164.2,1164,1164.2,?,?,1,0
1164.3,1164,1164.3,?,?,0,0
1226.1,1226,1226.1,1226.3,1226.2,0,1
1226.2,1226,1226.2,?,?,1,0
1226.3,1226,1226.3,?,?,0,0
1231.1,1231,1231.1,1231.3,1231.2,0,1
1231.2,1231,1231.2,?,?,1,0
1231.3,1231,1231.3,?,?,0,0
1302.1,1302,1302.1,1302.2,1302.3,0,1
1302.2,1302,1302.2,?,?,0,0
1302.3,1302,1302.3,?,?,1,0
1346.1,1346,1346.1,1346.3,1346.2,0,1
1346.2,1346,1346.2,?,?,1,0
1346.3,1346,1346.3,?,?,0,0
1384.1,1384,1384.1,1384.3,1384.2,1,1
1384.2,1384,1384.2,?,?,1,0
1384.3,1384,1384.3,?,?,0,0
1424.1,1424,1424.1,1424.3,1424.2,0,1
1424.2,1424,1424.2,?,?,1,0
1424.3,1424,1424.3,?,?,0,0
1446.1,1446,1446.1,1446.3,1446.2,0,1
1446.2,1446,1446.2,?,?,1,0
1446.3,1446,1446.3,?,?,0,0
1454.1,1454,1454.1,1454.3,1454.2,0,1
1454.2,1454,1454.2,?,?,1,0
1454.3,1454,1454.3,?,?,0,0
1583.1,1583,1583.1,1583.3,1583.2,0,1
1583.2,1583,1583.2,?,?,1,0
1583.3,1583,1583.3,?,?,0,0
1614.1,1614,1614.1,1614.3,1614.2,0,1
1614.2,1614,1614.2,?,?,1,0
1614.3,1614,1614.3,?,?,0,0
1579.1,1579,1579.1,1579.3,1579.2,0,1
1579.2,1579,1579.2,?,?,1,0
1579.3,1579,1579.3,?,?,0,0
1511.1,1511,1511.1,1511.3,1511.2,0,1
1511.2,1511,1511.2,?,?,1,0
1511.3,1511,1511.3,?,?,0,0
1354.1,1354,1354.1,1354.3,1354.2,1,1
1354.2,1354,1354.2,?,?,1,0
1354.3,1354,1354.3,?,?,0,0
1352.1,1352,1352.1,1352.3,1352.2,0,1
1352.2,1352,1352.2,?,?,1,0
1352.3,1352,1352.3,?,?,0,0
1329.1,1329,1329.1,1329.3,1329.2,0,1
1329.2,1329,1329.2,?,?,1,0
1329.3,1329,1329.3,?,?,0,0
1220.1,1220,1220.1,1220.3,1220.2,0,1
1220.2,1220,1220.2,?,?,1,0
1220.3,1220,1220.3,?,?,0,0
788.1,788,788.1,788.3,788.2,0,1
788.2,788,788.2,?,?,1,0
788.3,788,788.3,?,?,0,0
1478.1,1478,1478.1,1478.3,1478.2,0,1
1478.2,1478,1478.2,?,?,1,0
1478.3,1478,1478.3,?,?,0,0
999.1,999,999.1,999.3,999.2,0,1
999.2,999,999.2,?,?,1,0
999.3,999,999.3,?,?,0,0
506.1,506,506.1,506.3,506.2,0,1
506.2,506,506.2,?,?,1,0
506.3,506,506.3,?,?,0,0
471.1,471,471.1,471.3,471.2,1,1
471.2,471,471.2,?,?,1,0
471.3,471,471.3,?,?,0,0
409.1,409,409.1,409.3,409.2,1,1
409.2,409,409.2,?,?,1,0
409.3,409,409.3,?,?,0,0
120.1,120,120.1,120.2,120.3,0,1
120.2,120,120.2,?,?,0,0
120.3,120,120.3,?,?,1,0
95.1,95,95.1,95.3,95.2,0,1
95.2,95,95.2,?,?,0,0
95.3,95,95.3,?,?,1,0
95.4,95,95.4,95.3,95.2,1,1
361.1,361,361.1,361.6,361.5,0,1
361.2,361,361.2,361.6,361.5,1,1
361.3,361,361.3,361.6,361.5,1,0
361.4,361,361.4,361.6,361.5,0,0
361.5,361,361.5,?,?,1,0
361.6,361,361.6,?,?,0,0
443.1,443,443.1,?,443.2,0,1
443.2,443,443.2,?,?,1,1
443.5,443,443.5,?,443.2,1,1
549.1,549,549.1,?,?,1,1
549.2,549,549.2,?,?,0,1
688.1,688,688.1,?,?,1,1
688.4,688,688.4,?,?,0,1
864.1,864,864.1,?,864.2,0,1
864.2,864,864.2,?,?,1,1
877.1,877,877.1,877.2,?,0,1
877.2,877,877.2,?,?,0,1
</p>		        
            </div>
            <div class="modal-footer">
              <a href="#" class="btn" data-dismiss="modal">Close</a>
            </div>
          </div>
<a data-toggle="modal" href="#example" class="btn btn-primary btn-small">Example PED File</a>
      <br>
      Analysis Name: <input type="text" name="name"> 
      User Name: <input type="text" name="username"><br>
      <input type="submit" name="submit" value="upload">
      </form>
      </div>

    </div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
<script src="http://code.jquery.com/jquery.min.js"></script>
<script src="http://twitter.github.com/bootstrap/assets/js/bootstrap.js"></script>

  </body>
</html>