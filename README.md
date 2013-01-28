CohortTool
==========

Cohort Analyzer for Large-Scale Non-Mendelian Disease Studies
Author: Andrew Plassard
Affiliations: Cincinnati Children's Hospital
			  University of Cincinnati Biomedical Engineering


//Load Page:
//	User loads vcf and ped
//	User inputs username and analysis name
	
//Upload Page:
//	Loads vcf and ped
//	if vcf is .gz run gunzip
//	load ped into database "projects"
//	run SNPomics on vcf
//		Play Star Wars gif
	pull mutations from output file from SNPomics
		get all info from them and dump them to collection "mutations"
			protein
			gene
			CDNA
			etc
			id
		load mutations for each patient into collection "patientmutations"
			id

Start Page:
	Show info about patients
	Start filterings / Analysis
		
	