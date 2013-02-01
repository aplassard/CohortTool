CohortTool
==========

Cohort Analyzer for Large-Scale Non-Mendelian Disease Studies
Author: Andrew Plassard
Affiliations: Cincinnati Children's Hospital
			  University of Cincinnati Biomedical Engineering


Load Page:
	User load name, username, vcf, ped
	
Upload Page:
	Load data onto server
	

Run SNPomics Page:
	run SNPomics on VCF File
	
Load Info Page:
	Validate that all patients present in VCF are also in PED
	Build Cohort Model from VCF and PED
	

	
Important Classes:
	Cohort
		HashMap<Location,AnalysisMutation>
			AnalysisMutation
				int location
				String ref
				ArrayList<String> alt
				HashMap<String,PatientSpecificMutation> patients
					PatientSpecificMutation
						String alt;
						boolean zygosity;
				String[] genes
				String[] CDNA
				String[] Protein
				String rsID
		HashMap<String,Patient> patients
			Patient
				String id
				String motherID
				String fatherID
				String gender
				boolean afflicted
				

