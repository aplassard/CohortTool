package org.bmi.cchmc.cohorttol.util;

public class ServletUtilities {
	public static String getBootStrapHeader(String title){
		String headers = "<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n<meta charset=\"utf-8\">\n<title>";
		headers+=title;
		headers+="</title>\n<link href=\"./bootstrap/css/bootstrap.css\" rel=\"stylesheet\">\n<style>\nbody {\npadding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */\n}\n</style>";
	   	headers+="\n<link href=\"./bootstrap/css/bootstrap-responsive.css\" rel=\"stylesheet\">";
	   	headers+="\n<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->";
	   	headers+="\n<!--[if lt IE 9]>";
	   	headers+="\n<script src=\"http://html5shim.googlecode.com/svn/trunk/html5.js\"></script>";
	   	headers+="\n<![endif]-->";
	   	headers+="\n<style type=\"text/css\"></style>";
	   	headers+="\n</head>";
	   	headers+="\n<body>";
		return headers;
	}
	
	public static String getBootStrapEnd(String content){
		String ends="\n"+content+"\n";
		ends+="<!-- Le javascript";
		ends+="\n================================================== -->";
		ends+="\n<!-- Placed at the end of the document so the pages load faster -->";
		ends+="\n<script src=\"./bootstrap/js/jquery.js\"></script>";
		ends+="\n<script src=\"./bootstrap/js/bootstrap-transition.js\"></script>";
		ends+="\n<script src=\"./bootstrap/js/bootstrap-alert.js\"></script>";
		ends+="\n<script src=\"./bootstrap/js/bootstrap-modal.js\"></script>";
		ends+="\n<script src=\"./bootstrap/js/bootstrap-dropdown.js\"></script>";
		ends+="\n<script src=\"./bootstrap/js/bootstrap-scrollspy.js\"></script>";
		ends+="\n<script src=\"./bootstrap/js/bootstrap-tab.js\"></script>";
		ends+="\n<script src=\"./bootstrap/js/bootstrap-tooltip.js\"></script>";
		ends+="\n<script src=\"./bootstrap/js/bootstrap-popover.js\"></script>";
		ends+="\n<script src=\"./bootstrap/js/bootstrap-button.js\"></script>";
		ends+="\n<script src=\"./bootstrap/js/bootstrap-collapse.js\"></script>";
		ends+="\n<script src=\"./bootstrap/js/bootstrap-carousel.js\"></script>";
		ends+="\n<script src=\"./bootstrap/js/bootstrap-typeahead.js\"></script>";

		ends+="\n</body>";
		ends+="\n</html>";
		return ends;
	}
	
	public static String wrapBootstrap(String title, String content){
		String output=getBootStrapHeader(title);
		output+=getBootStrapEnd(content);
		return output;
	}
	
	public static String getNormalNavbar(){
		String header="";
		header+="\n<div class=\"navbar navbar-inverse navbar-fixed-top\">";
		header+="\n<div class=\"navbar-inner\">";
		header+="\n<div class=\"container\">";
		header+="\n<a class=\"btn btn-navbar\" data-toggle=\"collapse\" data-target=\".nav-collapse\">";
		header+="\n<span class=\"icon-bar\"></span>";
		header+="\n<span class=\"icon-bar\"></span>";
		header+="\n<span class=\"icon-bar\"></span>";
		header+="\n</a>";
		header+="\n<a class=\"brand\" href=\"/CohortTool/index.jsp\">Cohort Analyzer</a>";
		header+="\n <div class=\"nav-collapse collapse\">";
		header+="\n<ul class=\"nav\">";
		header+="\n<li><a href=\"/CohortTool/\">Home</a></li>";
		header+="\n<li><a href=\"/CohortTool/about.jsp\">About</a></li>";
		header+="\n<li><a href=\"/CohortTool/publications.jsp\">Publications</a></li>";
		header+="\n </ul>";
		header+="\n</div><!--/.nav-collapse -->";
		header+="\n</div>";
		header+="\n</div>";
		header+="\n</div>";
		return header;
	}
}
