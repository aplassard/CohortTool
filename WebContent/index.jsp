<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    	               "http://www.w3.org/TR/html4/loose.dtd">

<html>
  <head>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    	<title>Cohort Analyzer</title>
  </head>
  <body>
  	<center>
    <h1>Enter Genes Here</h1>
    <form action="ExpressionSets" method="post">
    Enter Gene Annotation Type<br>
    <input name="gene_type" type="radio" value="HGNC">HGNC<br>
    <input name="gene_type" type="radio" value="Entrez">Entrez<br>
    <input name="gene_type" type="radio" value="Refseq">Refseq<br>
    <input name="gene_type" type="radio" value="UCSC">UCSC<br><br>
    Enter Genes:<br>
    <textarea name="genes" rows=10 columns=40></textarea>
    <br>
    <input type="submit" value="Submit">
    </form>
    </center>
    
  </body>
</html> 
