package org.bmi.cchmc.cohorttool.translation;

import java.util.HashMap;

public class Blosum80 {
	public static int get(AminoAcid a, AminoAcid b) {
		HashMap<AminoAcid, Integer> x = distance.get(a);
		if (x == null)
			return -1;
		Integer result = x.get(b);
		return (result == null) ? -1 : result;
	}
	
	private static HashMap<AminoAcid, HashMap<AminoAcid, Integer>> distance;
	private static void populate(AminoAcid a, AminoAcid b, int val) {
		distance.get(a).put(b, val);
		if (a != b)
			distance.get(b).put(a, val);
	}
	static {
		distance = new HashMap<AminoAcid, HashMap<AminoAcid, Integer>>();
		for (AminoAcid i : AminoAcid.values()) {
			distance.put(i, new HashMap<AminoAcid, Integer>());
		}
		populate(AminoAcid.ALA, AminoAcid.ALA , 4);

		populate(AminoAcid.ALA, AminoAcid.ARG , -1);
		populate(AminoAcid.ARG, AminoAcid.ARG , 5);

		populate(AminoAcid.ALA, AminoAcid.ASN , -2);
		populate(AminoAcid.ARG, AminoAcid.ASN , 0);
		populate(AminoAcid.ASN, AminoAcid.ASN , 6);

		populate(AminoAcid.ALA, AminoAcid.ASP , -2);
		populate(AminoAcid.ARG, AminoAcid.ASP , -2);
		populate(AminoAcid.ASN, AminoAcid.ASP , 1);
		populate(AminoAcid.ASP, AminoAcid.ASP , 6);

		populate(AminoAcid.ALA, AminoAcid.CYS , 0);
		populate(AminoAcid.ARG, AminoAcid.CYS , -3);
		populate(AminoAcid.ASN, AminoAcid.CYS , -3);
		populate(AminoAcid.ASP, AminoAcid.CYS , -3);
		populate(AminoAcid.CYS, AminoAcid.CYS , 9);

		populate(AminoAcid.ALA, AminoAcid.GLN , -1);
		populate(AminoAcid.ARG, AminoAcid.GLN , 1);
		populate(AminoAcid.ASN, AminoAcid.GLN , 0);
		populate(AminoAcid.ASP, AminoAcid.GLN , 0);
		populate(AminoAcid.CYS, AminoAcid.GLN , -3);
		populate(AminoAcid.GLN, AminoAcid.GLN , 5);

		populate(AminoAcid.ALA, AminoAcid.GLU , -1);
		populate(AminoAcid.ARG, AminoAcid.GLU , 0);
		populate(AminoAcid.ASN, AminoAcid.GLU , 0);
		populate(AminoAcid.ASP, AminoAcid.GLU , 2);
		populate(AminoAcid.CYS, AminoAcid.GLU , -4);
		populate(AminoAcid.GLN, AminoAcid.GLU , 2);
		populate(AminoAcid.GLU, AminoAcid.GLU , 5);

		populate(AminoAcid.ALA, AminoAcid.GLY , 0);
		populate(AminoAcid.ARG, AminoAcid.GLY , -2);
		populate(AminoAcid.ASN, AminoAcid.GLY , 0);
		populate(AminoAcid.ASP, AminoAcid.GLY , -1);
		populate(AminoAcid.CYS, AminoAcid.GLY , -3);
		populate(AminoAcid.GLN, AminoAcid.GLY , -2);
		populate(AminoAcid.GLU, AminoAcid.GLY , -2);
		populate(AminoAcid.GLY, AminoAcid.GLY , 6);

		populate(AminoAcid.ALA, AminoAcid.HIS , -2);
		populate(AminoAcid.ARG, AminoAcid.HIS , 0);
		populate(AminoAcid.ASN, AminoAcid.HIS , 1);
		populate(AminoAcid.ASP, AminoAcid.HIS , -1);
		populate(AminoAcid.CYS, AminoAcid.HIS , -3);
		populate(AminoAcid.GLN, AminoAcid.HIS , 0);
		populate(AminoAcid.GLU, AminoAcid.HIS , 0);
		populate(AminoAcid.GLY, AminoAcid.HIS , -2);
		populate(AminoAcid.HIS, AminoAcid.HIS , 8);

		populate(AminoAcid.ALA, AminoAcid.ILE , -1);
		populate(AminoAcid.ARG, AminoAcid.ILE , -3);
		populate(AminoAcid.ASN, AminoAcid.ILE , -3);
		populate(AminoAcid.ASP, AminoAcid.ILE , -3);
		populate(AminoAcid.CYS, AminoAcid.ILE , -1);
		populate(AminoAcid.GLN, AminoAcid.ILE , -3);
		populate(AminoAcid.GLU, AminoAcid.ILE , -3);
		populate(AminoAcid.GLY, AminoAcid.ILE , -4);
		populate(AminoAcid.HIS, AminoAcid.ILE , -3);
		populate(AminoAcid.ILE, AminoAcid.ILE , 4);

		populate(AminoAcid.ALA, AminoAcid.LEU , -1);
		populate(AminoAcid.ARG, AminoAcid.LEU , -2);
		populate(AminoAcid.ASN, AminoAcid.LEU , -3);
		populate(AminoAcid.ASP, AminoAcid.LEU , -4);
		populate(AminoAcid.CYS, AminoAcid.LEU , -1);
		populate(AminoAcid.GLN, AminoAcid.LEU , -2);
		populate(AminoAcid.GLU, AminoAcid.LEU , -3);
		populate(AminoAcid.GLY, AminoAcid.LEU , -4);
		populate(AminoAcid.HIS, AminoAcid.LEU , -3);
		populate(AminoAcid.ILE, AminoAcid.LEU , 2);
		populate(AminoAcid.LEU, AminoAcid.LEU , 4);

		populate(AminoAcid.ALA, AminoAcid.LYS , -1);
		populate(AminoAcid.ARG, AminoAcid.LYS , 2);
		populate(AminoAcid.ASN, AminoAcid.LYS , 0);
		populate(AminoAcid.ASP, AminoAcid.LYS , -1);
		populate(AminoAcid.CYS, AminoAcid.LYS , -3);
		populate(AminoAcid.GLN, AminoAcid.LYS , 1);
		populate(AminoAcid.GLU, AminoAcid.LYS , 1);
		populate(AminoAcid.GLY, AminoAcid.LYS , -2);
		populate(AminoAcid.HIS, AminoAcid.LYS , -1);
		populate(AminoAcid.ILE, AminoAcid.LYS , -3);
		populate(AminoAcid.LEU, AminoAcid.LYS , -2);
		populate(AminoAcid.LYS, AminoAcid.LYS , 5);

		populate(AminoAcid.ALA, AminoAcid.MET , -1);
		populate(AminoAcid.ARG, AminoAcid.MET , -1);
		populate(AminoAcid.ASN, AminoAcid.MET , -2);
		populate(AminoAcid.ASP, AminoAcid.MET , -3);
		populate(AminoAcid.CYS, AminoAcid.MET , -1);
		populate(AminoAcid.GLN, AminoAcid.MET , 0);
		populate(AminoAcid.GLU, AminoAcid.MET , -2);
		populate(AminoAcid.GLY, AminoAcid.MET , -3);
		populate(AminoAcid.HIS, AminoAcid.MET , -2);
		populate(AminoAcid.ILE, AminoAcid.MET , 1);
		populate(AminoAcid.LEU, AminoAcid.MET , 2);
		populate(AminoAcid.LYS, AminoAcid.MET , -1);
		populate(AminoAcid.MET, AminoAcid.MET , 5);

		populate(AminoAcid.ALA, AminoAcid.PHE , -2);
		populate(AminoAcid.ARG, AminoAcid.PHE , -3);
		populate(AminoAcid.ASN, AminoAcid.PHE , -3);
		populate(AminoAcid.ASP, AminoAcid.PHE , -3);
		populate(AminoAcid.CYS, AminoAcid.PHE , -2);
		populate(AminoAcid.GLN, AminoAcid.PHE , -3);
		populate(AminoAcid.GLU, AminoAcid.PHE , -3);
		populate(AminoAcid.GLY, AminoAcid.PHE , -3);
		populate(AminoAcid.HIS, AminoAcid.PHE , -1);
		populate(AminoAcid.ILE, AminoAcid.PHE , 0);
		populate(AminoAcid.LEU, AminoAcid.PHE , 0);
		populate(AminoAcid.LYS, AminoAcid.PHE , -3);
		populate(AminoAcid.MET, AminoAcid.PHE , 0);
		populate(AminoAcid.PHE, AminoAcid.PHE , 6);

		populate(AminoAcid.ALA, AminoAcid.PRO , -1);
		populate(AminoAcid.ARG, AminoAcid.PRO , -2);
		populate(AminoAcid.ASN, AminoAcid.PRO , -2);
		populate(AminoAcid.ASP, AminoAcid.PRO , -1);
		populate(AminoAcid.CYS, AminoAcid.PRO , -3);
		populate(AminoAcid.GLN, AminoAcid.PRO , -1);
		populate(AminoAcid.GLU, AminoAcid.PRO , -1);
		populate(AminoAcid.GLY, AminoAcid.PRO , -2);
		populate(AminoAcid.HIS, AminoAcid.PRO , -2);
		populate(AminoAcid.ILE, AminoAcid.PRO , -3);
		populate(AminoAcid.LEU, AminoAcid.PRO , -3);
		populate(AminoAcid.LYS, AminoAcid.PRO , -1);
		populate(AminoAcid.MET, AminoAcid.PRO , -2);
		populate(AminoAcid.PHE, AminoAcid.PRO , -4);
		populate(AminoAcid.PRO, AminoAcid.PRO , 7);

		populate(AminoAcid.ALA, AminoAcid.SER , 1);
		populate(AminoAcid.ARG, AminoAcid.SER , -1);
		populate(AminoAcid.ASN, AminoAcid.SER , 1);
		populate(AminoAcid.ASP, AminoAcid.SER , 0);
		populate(AminoAcid.CYS, AminoAcid.SER , -1);
		populate(AminoAcid.GLN, AminoAcid.SER , 0);
		populate(AminoAcid.GLU, AminoAcid.SER , 0);
		populate(AminoAcid.GLY, AminoAcid.SER , 0);
		populate(AminoAcid.HIS, AminoAcid.SER , -1);
		populate(AminoAcid.ILE, AminoAcid.SER , -2);
		populate(AminoAcid.LEU, AminoAcid.SER , -2);
		populate(AminoAcid.LYS, AminoAcid.SER , 0);
		populate(AminoAcid.MET, AminoAcid.SER , -1);
		populate(AminoAcid.PHE, AminoAcid.SER , -2);
		populate(AminoAcid.PRO, AminoAcid.SER , -1);
		populate(AminoAcid.SER, AminoAcid.SER , 4);

		populate(AminoAcid.ALA, AminoAcid.THR , 0);
		populate(AminoAcid.ARG, AminoAcid.THR , -1);
		populate(AminoAcid.ASN, AminoAcid.THR , 0);
		populate(AminoAcid.ASP, AminoAcid.THR , -1);
		populate(AminoAcid.CYS, AminoAcid.THR , -1);
		populate(AminoAcid.GLN, AminoAcid.THR , -1);
		populate(AminoAcid.GLU, AminoAcid.THR , -1);
		populate(AminoAcid.GLY, AminoAcid.THR , -2);
		populate(AminoAcid.HIS, AminoAcid.THR , -2);
		populate(AminoAcid.ILE, AminoAcid.THR , -1);
		populate(AminoAcid.LEU, AminoAcid.THR , -1);
		populate(AminoAcid.LYS, AminoAcid.THR , -1);
		populate(AminoAcid.MET, AminoAcid.THR , -1);
		populate(AminoAcid.PHE, AminoAcid.THR , -2);
		populate(AminoAcid.PRO, AminoAcid.THR , -1);
		populate(AminoAcid.SER, AminoAcid.THR , 1);
		populate(AminoAcid.THR, AminoAcid.THR , 5);

		populate(AminoAcid.ALA, AminoAcid.TRP , -3);
		populate(AminoAcid.ARG, AminoAcid.TRP , -3);
		populate(AminoAcid.ASN, AminoAcid.TRP , -4);
		populate(AminoAcid.ASP, AminoAcid.TRP , -4);
		populate(AminoAcid.CYS, AminoAcid.TRP , -2);
		populate(AminoAcid.GLN, AminoAcid.TRP , -2);
		populate(AminoAcid.GLU, AminoAcid.TRP , -3);
		populate(AminoAcid.GLY, AminoAcid.TRP , -2);
		populate(AminoAcid.HIS, AminoAcid.TRP , -2);
		populate(AminoAcid.ILE, AminoAcid.TRP , -3);
		populate(AminoAcid.LEU, AminoAcid.TRP , -2);
		populate(AminoAcid.LYS, AminoAcid.TRP , -3);
		populate(AminoAcid.MET, AminoAcid.TRP , -1);
		populate(AminoAcid.PHE, AminoAcid.TRP , 1);
		populate(AminoAcid.PRO, AminoAcid.TRP , -4);
		populate(AminoAcid.SER, AminoAcid.TRP , -3);
		populate(AminoAcid.THR, AminoAcid.TRP , -2);
		populate(AminoAcid.TRP, AminoAcid.TRP , 11);

		populate(AminoAcid.ALA, AminoAcid.TYR , -2);
		populate(AminoAcid.ARG, AminoAcid.TYR , -2);
		populate(AminoAcid.ASN, AminoAcid.TYR , -2);
		populate(AminoAcid.ASP, AminoAcid.TYR , -3);
		populate(AminoAcid.CYS, AminoAcid.TYR , -2);
		populate(AminoAcid.GLN, AminoAcid.TYR , -1);
		populate(AminoAcid.GLU, AminoAcid.TYR , -2);
		populate(AminoAcid.GLY, AminoAcid.TYR , -3);
		populate(AminoAcid.HIS, AminoAcid.TYR , 2);
		populate(AminoAcid.ILE, AminoAcid.TYR , -1);
		populate(AminoAcid.LEU, AminoAcid.TYR , -1);
		populate(AminoAcid.LYS, AminoAcid.TYR , -2);
		populate(AminoAcid.MET, AminoAcid.TYR , -1);
		populate(AminoAcid.PHE, AminoAcid.TYR , 3);
		populate(AminoAcid.PRO, AminoAcid.TYR , -3);
		populate(AminoAcid.SER, AminoAcid.TYR , -2);
		populate(AminoAcid.THR, AminoAcid.TYR , -2);
		populate(AminoAcid.TRP, AminoAcid.TYR , 2);
		populate(AminoAcid.TYR, AminoAcid.TYR , 7);

		populate(AminoAcid.ALA, AminoAcid.VAL , 0);
		populate(AminoAcid.ARG, AminoAcid.VAL , -3);
		populate(AminoAcid.ASN, AminoAcid.VAL , -3);
		populate(AminoAcid.ASP, AminoAcid.VAL , -3);
		populate(AminoAcid.CYS, AminoAcid.VAL , -1);
		populate(AminoAcid.GLN, AminoAcid.VAL , -2);
		populate(AminoAcid.GLU, AminoAcid.VAL , -2);
		populate(AminoAcid.GLY, AminoAcid.VAL , -3);
		populate(AminoAcid.HIS, AminoAcid.VAL , -3);
		populate(AminoAcid.ILE, AminoAcid.VAL , 3);
		populate(AminoAcid.LEU, AminoAcid.VAL , 1);
		populate(AminoAcid.LYS, AminoAcid.VAL , -2);
		populate(AminoAcid.MET, AminoAcid.VAL , 1);
		populate(AminoAcid.PHE, AminoAcid.VAL , -1);
		populate(AminoAcid.PRO, AminoAcid.VAL , -2);
		populate(AminoAcid.SER, AminoAcid.VAL , -2);
		populate(AminoAcid.THR, AminoAcid.VAL , 0);
		populate(AminoAcid.TRP, AminoAcid.VAL , -3);
		populate(AminoAcid.TYR, AminoAcid.VAL , -1);
		populate(AminoAcid.VAL, AminoAcid.VAL , 4);


	}
}
