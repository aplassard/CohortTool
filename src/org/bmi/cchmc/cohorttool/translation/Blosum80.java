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
		populate(AminoAcid.A, AminoAcid.A , 5);

		populate(AminoAcid.A, AminoAcid.R , -2);
		populate(AminoAcid.R, AminoAcid.R , 6);

		populate(AminoAcid.A, AminoAcid.N , -2);
		populate(AminoAcid.R, AminoAcid.N , -1);
		populate(AminoAcid.N, AminoAcid.N , 6);

		populate(AminoAcid.A, AminoAcid.D , -2);
		populate(AminoAcid.R, AminoAcid.D , -2);
		populate(AminoAcid.N, AminoAcid.D , 1);
		populate(AminoAcid.D, AminoAcid.D , 6);

		populate(AminoAcid.A, AminoAcid.C , -1);
		populate(AminoAcid.R, AminoAcid.C , -4);
		populate(AminoAcid.N, AminoAcid.C , -3);
		populate(AminoAcid.D, AminoAcid.C , -4);
		populate(AminoAcid.C, AminoAcid.C , 9);

		populate(AminoAcid.A, AminoAcid.Q , -1);
		populate(AminoAcid.R, AminoAcid.Q , 1);
		populate(AminoAcid.N, AminoAcid.Q , 0);
		populate(AminoAcid.D, AminoAcid.Q , -1);
		populate(AminoAcid.C, AminoAcid.Q , -4);
		populate(AminoAcid.Q, AminoAcid.Q , 6);

		populate(AminoAcid.A, AminoAcid.E , -1);
		populate(AminoAcid.R, AminoAcid.E , -1);
		populate(AminoAcid.N, AminoAcid.E , -1);
		populate(AminoAcid.D, AminoAcid.E , 1);
		populate(AminoAcid.C, AminoAcid.E , -5);
		populate(AminoAcid.Q, AminoAcid.E , 2);
		populate(AminoAcid.E, AminoAcid.E , 6);

		populate(AminoAcid.A, AminoAcid.G , 0);
		populate(AminoAcid.R, AminoAcid.G , -3);
		populate(AminoAcid.N, AminoAcid.G , -1);
		populate(AminoAcid.D, AminoAcid.G , -2);
		populate(AminoAcid.C, AminoAcid.G , -4);
		populate(AminoAcid.Q, AminoAcid.G , -2);
		populate(AminoAcid.E, AminoAcid.G , -3);
		populate(AminoAcid.G, AminoAcid.G , 6);

		populate(AminoAcid.A, AminoAcid.H , -2);
		populate(AminoAcid.R, AminoAcid.H , 0);
		populate(AminoAcid.N, AminoAcid.H , 0);
		populate(AminoAcid.D, AminoAcid.H , -2);
		populate(AminoAcid.C, AminoAcid.H , -4);
		populate(AminoAcid.Q, AminoAcid.H , 1);
		populate(AminoAcid.E, AminoAcid.H , 0);
		populate(AminoAcid.G, AminoAcid.H , -3);
		populate(AminoAcid.H, AminoAcid.H , 8);

		populate(AminoAcid.A, AminoAcid.I , -2);
		populate(AminoAcid.R, AminoAcid.I , -3);
		populate(AminoAcid.N, AminoAcid.I , -4);
		populate(AminoAcid.D, AminoAcid.I , -4);
		populate(AminoAcid.C, AminoAcid.I , -2);
		populate(AminoAcid.Q, AminoAcid.I , -3);
		populate(AminoAcid.E, AminoAcid.I , -4);
		populate(AminoAcid.G, AminoAcid.I , -5);
		populate(AminoAcid.H, AminoAcid.I , -4);
		populate(AminoAcid.I, AminoAcid.I , 5);

		populate(AminoAcid.A, AminoAcid.L , -2);
		populate(AminoAcid.R, AminoAcid.L , -3);
		populate(AminoAcid.N, AminoAcid.L , -4);
		populate(AminoAcid.D, AminoAcid.L , -5);
		populate(AminoAcid.C, AminoAcid.L , -2);
		populate(AminoAcid.Q, AminoAcid.L , -3);
		populate(AminoAcid.E, AminoAcid.L , -4);
		populate(AminoAcid.G, AminoAcid.L , -4);
		populate(AminoAcid.H, AminoAcid.L , -3);
		populate(AminoAcid.I, AminoAcid.L , 1);
		populate(AminoAcid.L, AminoAcid.L , 4);

		populate(AminoAcid.A, AminoAcid.K , -1);
		populate(AminoAcid.R, AminoAcid.K , 2);
		populate(AminoAcid.N, AminoAcid.K , 0);
		populate(AminoAcid.D, AminoAcid.K , -1);
		populate(AminoAcid.C, AminoAcid.K , -4);
		populate(AminoAcid.Q, AminoAcid.K , 1);
		populate(AminoAcid.E, AminoAcid.K , 1);
		populate(AminoAcid.G, AminoAcid.K , -2);
		populate(AminoAcid.H, AminoAcid.K , -1);
		populate(AminoAcid.I, AminoAcid.K , -3);
		populate(AminoAcid.L, AminoAcid.K , -3);
		populate(AminoAcid.K, AminoAcid.K , 5);

		populate(AminoAcid.A, AminoAcid.M , -1);
		populate(AminoAcid.R, AminoAcid.M , -2);
		populate(AminoAcid.N, AminoAcid.M , -3);
		populate(AminoAcid.D, AminoAcid.M , -4);
		populate(AminoAcid.C, AminoAcid.M , -2);
		populate(AminoAcid.Q, AminoAcid.M , 0);
		populate(AminoAcid.E, AminoAcid.M , -2);
		populate(AminoAcid.G, AminoAcid.M , -4);
		populate(AminoAcid.H, AminoAcid.M , -2);
		populate(AminoAcid.I, AminoAcid.M , 1);
		populate(AminoAcid.L, AminoAcid.M , 2);
		populate(AminoAcid.K, AminoAcid.M , -2);
		populate(AminoAcid.M, AminoAcid.M , 6);

		populate(AminoAcid.A, AminoAcid.F , -3);
		populate(AminoAcid.R, AminoAcid.F , -4);
		populate(AminoAcid.N, AminoAcid.F , -4);
		populate(AminoAcid.D, AminoAcid.F , -4);
		populate(AminoAcid.C, AminoAcid.F , -3);
		populate(AminoAcid.Q, AminoAcid.F , -4);
		populate(AminoAcid.E, AminoAcid.F , -4);
		populate(AminoAcid.G, AminoAcid.F , -4);
		populate(AminoAcid.H, AminoAcid.F , -2);
		populate(AminoAcid.I, AminoAcid.F , -1);
		populate(AminoAcid.L, AminoAcid.F , 0);
		populate(AminoAcid.K, AminoAcid.F , -4);
		populate(AminoAcid.M, AminoAcid.F , 0);
		populate(AminoAcid.F, AminoAcid.F , 6);

		populate(AminoAcid.A, AminoAcid.P , -1);
		populate(AminoAcid.R, AminoAcid.P , -2);
		populate(AminoAcid.N, AminoAcid.P , -3);
		populate(AminoAcid.D, AminoAcid.P , -2);
		populate(AminoAcid.C, AminoAcid.P , -4);
		populate(AminoAcid.Q, AminoAcid.P , -2);
		populate(AminoAcid.E, AminoAcid.P , -2);
		populate(AminoAcid.G, AminoAcid.P , -3);
		populate(AminoAcid.H, AminoAcid.P , -3);
		populate(AminoAcid.I, AminoAcid.P , -4);
		populate(AminoAcid.L, AminoAcid.P , -3);
		populate(AminoAcid.K, AminoAcid.P , -1);
		populate(AminoAcid.M, AminoAcid.P , -3);
		populate(AminoAcid.F, AminoAcid.P , -4);
		populate(AminoAcid.P, AminoAcid.P , 8);

		populate(AminoAcid.A, AminoAcid.S , 1);
		populate(AminoAcid.R, AminoAcid.S , -1);
		populate(AminoAcid.N, AminoAcid.S , 0);
		populate(AminoAcid.D, AminoAcid.S , -1);
		populate(AminoAcid.C, AminoAcid.S , -2);
		populate(AminoAcid.Q, AminoAcid.S , 0);
		populate(AminoAcid.E, AminoAcid.S , 0);
		populate(AminoAcid.G, AminoAcid.S , -1);
		populate(AminoAcid.H, AminoAcid.S , -1);
		populate(AminoAcid.I, AminoAcid.S , -3);
		populate(AminoAcid.L, AminoAcid.S , -3);
		populate(AminoAcid.K, AminoAcid.S , -1);
		populate(AminoAcid.M, AminoAcid.S , -2);
		populate(AminoAcid.F, AminoAcid.S , -3);
		populate(AminoAcid.P, AminoAcid.S , -1);
		populate(AminoAcid.S, AminoAcid.S , 5);

		populate(AminoAcid.A, AminoAcid.T , 0);
		populate(AminoAcid.R, AminoAcid.T , -1);
		populate(AminoAcid.N, AminoAcid.T , 0);
		populate(AminoAcid.D, AminoAcid.T , -1);
		populate(AminoAcid.C, AminoAcid.T , -1);
		populate(AminoAcid.Q, AminoAcid.T , -1);
		populate(AminoAcid.E, AminoAcid.T , -1);
		populate(AminoAcid.G, AminoAcid.T , -2);
		populate(AminoAcid.H, AminoAcid.T , -2);
		populate(AminoAcid.I, AminoAcid.T , -1);
		populate(AminoAcid.L, AminoAcid.T , -2);
		populate(AminoAcid.K, AminoAcid.T , -1);
		populate(AminoAcid.M, AminoAcid.T , -1);
		populate(AminoAcid.F, AminoAcid.T , -2);
		populate(AminoAcid.P, AminoAcid.T , -2);
		populate(AminoAcid.S, AminoAcid.T , 1);
		populate(AminoAcid.T, AminoAcid.T , 5);

		populate(AminoAcid.A, AminoAcid.W , -3);
		populate(AminoAcid.R, AminoAcid.W , -4);
		populate(AminoAcid.N, AminoAcid.W , -4);
		populate(AminoAcid.D, AminoAcid.W , -6);
		populate(AminoAcid.C, AminoAcid.W , -3);
		populate(AminoAcid.Q, AminoAcid.W , -3);
		populate(AminoAcid.E, AminoAcid.W , -4);
		populate(AminoAcid.G, AminoAcid.W , -4);
		populate(AminoAcid.H, AminoAcid.W , -3);
		populate(AminoAcid.I, AminoAcid.W , -3);
		populate(AminoAcid.L, AminoAcid.W , -2);
		populate(AminoAcid.K, AminoAcid.W , -4);
		populate(AminoAcid.M, AminoAcid.W , -2);
		populate(AminoAcid.F, AminoAcid.W , 0);
		populate(AminoAcid.P, AminoAcid.W , -5);
		populate(AminoAcid.S, AminoAcid.W , -4);
		populate(AminoAcid.T, AminoAcid.W , -4);
		populate(AminoAcid.W, AminoAcid.W , 11);

		populate(AminoAcid.A, AminoAcid.Y , -2);
		populate(AminoAcid.R, AminoAcid.Y , -3);
		populate(AminoAcid.N, AminoAcid.Y , -3);
		populate(AminoAcid.D, AminoAcid.Y , -4);
		populate(AminoAcid.C, AminoAcid.Y , -3);
		populate(AminoAcid.Q, AminoAcid.Y , -2);
		populate(AminoAcid.E, AminoAcid.Y , -3);
		populate(AminoAcid.G, AminoAcid.Y , -4);
		populate(AminoAcid.H, AminoAcid.Y , 2);
		populate(AminoAcid.I, AminoAcid.Y , -2);
		populate(AminoAcid.L, AminoAcid.Y , -2);
		populate(AminoAcid.K, AminoAcid.Y , -3);
		populate(AminoAcid.M, AminoAcid.Y , -2);
		populate(AminoAcid.F, AminoAcid.Y , 3);
		populate(AminoAcid.P, AminoAcid.Y , -4);
		populate(AminoAcid.S, AminoAcid.Y , -2);
		populate(AminoAcid.T, AminoAcid.Y , -2);
		populate(AminoAcid.W, AminoAcid.Y , 2);
		populate(AminoAcid.Y, AminoAcid.Y , 7);

		populate(AminoAcid.A, AminoAcid.V , 0);
		populate(AminoAcid.R, AminoAcid.V , -3);
		populate(AminoAcid.N, AminoAcid.V , -4);
		populate(AminoAcid.D, AminoAcid.V , -4);
		populate(AminoAcid.C, AminoAcid.V , -1);
		populate(AminoAcid.Q, AminoAcid.V , -3);
		populate(AminoAcid.E, AminoAcid.V , -3);
		populate(AminoAcid.G, AminoAcid.V , -4);
		populate(AminoAcid.H, AminoAcid.V , -4);
		populate(AminoAcid.I, AminoAcid.V , 3);
		populate(AminoAcid.L, AminoAcid.V , 1);
		populate(AminoAcid.K, AminoAcid.V , -3);
		populate(AminoAcid.M, AminoAcid.V , 1);
		populate(AminoAcid.F, AminoAcid.V , -1);
		populate(AminoAcid.P, AminoAcid.V , -3);
		populate(AminoAcid.S, AminoAcid.V , -2);
		populate(AminoAcid.T, AminoAcid.V , 0);
		populate(AminoAcid.W, AminoAcid.V , -3);
		populate(AminoAcid.Y, AminoAcid.V , -2);
		populate(AminoAcid.V, AminoAcid.V , 4);

		populate(AminoAcid.A, AminoAcid.B , -2);
		populate(AminoAcid.R, AminoAcid.B , -1);
		populate(AminoAcid.N, AminoAcid.B , 5);
		populate(AminoAcid.D, AminoAcid.B , 5);
		populate(AminoAcid.C, AminoAcid.B , -4);
		populate(AminoAcid.Q, AminoAcid.B , 0);
		populate(AminoAcid.E, AminoAcid.B , 1);
		populate(AminoAcid.G, AminoAcid.B , -1);
		populate(AminoAcid.H, AminoAcid.B , -1);
		populate(AminoAcid.I, AminoAcid.B , -4);
		populate(AminoAcid.L, AminoAcid.B , -4);
		populate(AminoAcid.K, AminoAcid.B , -1);
		populate(AminoAcid.M, AminoAcid.B , -3);
		populate(AminoAcid.F, AminoAcid.B , -4);
		populate(AminoAcid.P, AminoAcid.B , -2);
		populate(AminoAcid.S, AminoAcid.B , 0);
		populate(AminoAcid.T, AminoAcid.B , -1);
		populate(AminoAcid.W, AminoAcid.B , -5);
		populate(AminoAcid.Y, AminoAcid.B , -3);
		populate(AminoAcid.V, AminoAcid.B , -4);
		populate(AminoAcid.B, AminoAcid.B , 5);

		populate(AminoAcid.A, AminoAcid.J , -2);
		populate(AminoAcid.R, AminoAcid.J , -3);
		populate(AminoAcid.N, AminoAcid.J , -4);
		populate(AminoAcid.D, AminoAcid.J , -5);
		populate(AminoAcid.C, AminoAcid.J , -2);
		populate(AminoAcid.Q, AminoAcid.J , -3);
		populate(AminoAcid.E, AminoAcid.J , -4);
		populate(AminoAcid.G, AminoAcid.J , -5);
		populate(AminoAcid.H, AminoAcid.J , -4);
		populate(AminoAcid.I, AminoAcid.J , 3);
		populate(AminoAcid.L, AminoAcid.J , 3);
		populate(AminoAcid.K, AminoAcid.J , -3);
		populate(AminoAcid.M, AminoAcid.J , 2);
		populate(AminoAcid.F, AminoAcid.J , 0);
		populate(AminoAcid.P, AminoAcid.J , -4);
		populate(AminoAcid.S, AminoAcid.J , -3);
		populate(AminoAcid.T, AminoAcid.J , -1);
		populate(AminoAcid.W, AminoAcid.J , -3);
		populate(AminoAcid.Y, AminoAcid.J , -2);
		populate(AminoAcid.V, AminoAcid.J , 2);
		populate(AminoAcid.B, AminoAcid.J , -4);
		populate(AminoAcid.J, AminoAcid.J , 3);

		populate(AminoAcid.A, AminoAcid.Z , -1);
		populate(AminoAcid.R, AminoAcid.Z , 0);
		populate(AminoAcid.N, AminoAcid.Z , 0);
		populate(AminoAcid.D, AminoAcid.Z , 1);
		populate(AminoAcid.C, AminoAcid.Z , -4);
		populate(AminoAcid.Q, AminoAcid.Z , 4);
		populate(AminoAcid.E, AminoAcid.Z , 5);
		populate(AminoAcid.G, AminoAcid.Z , -3);
		populate(AminoAcid.H, AminoAcid.Z , 0);
		populate(AminoAcid.I, AminoAcid.Z , -4);
		populate(AminoAcid.L, AminoAcid.Z , -3);
		populate(AminoAcid.K, AminoAcid.Z , 1);
		populate(AminoAcid.M, AminoAcid.Z , -1);
		populate(AminoAcid.F, AminoAcid.Z , -4);
		populate(AminoAcid.P, AminoAcid.Z , -2);
		populate(AminoAcid.S, AminoAcid.Z , 0);
		populate(AminoAcid.T, AminoAcid.Z , -1);
		populate(AminoAcid.W, AminoAcid.Z , -3);
		populate(AminoAcid.Y, AminoAcid.Z , -3);
		populate(AminoAcid.V, AminoAcid.Z , -3);
		populate(AminoAcid.B, AminoAcid.Z , 0);
		populate(AminoAcid.J, AminoAcid.Z , -3);
		populate(AminoAcid.Z, AminoAcid.Z , 5);

		populate(AminoAcid.A, AminoAcid.X , -1);
		populate(AminoAcid.R, AminoAcid.X , -1);
		populate(AminoAcid.N, AminoAcid.X , -1);
		populate(AminoAcid.D, AminoAcid.X , -1);
		populate(AminoAcid.C, AminoAcid.X , -1);
		populate(AminoAcid.Q, AminoAcid.X , -1);
		populate(AminoAcid.E, AminoAcid.X , -1);
		populate(AminoAcid.G, AminoAcid.X , -1);
		populate(AminoAcid.H, AminoAcid.X , -1);
		populate(AminoAcid.I, AminoAcid.X , -1);
		populate(AminoAcid.L, AminoAcid.X , -1);
		populate(AminoAcid.K, AminoAcid.X , -1);
		populate(AminoAcid.M, AminoAcid.X , -1);
		populate(AminoAcid.F, AminoAcid.X , -1);
		populate(AminoAcid.P, AminoAcid.X , -1);
		populate(AminoAcid.S, AminoAcid.X , -1);
		populate(AminoAcid.T, AminoAcid.X , -1);
		populate(AminoAcid.W, AminoAcid.X , -1);
		populate(AminoAcid.Y, AminoAcid.X , -1);
		populate(AminoAcid.V, AminoAcid.X , -1);
		populate(AminoAcid.B, AminoAcid.X , -1);
		populate(AminoAcid.J, AminoAcid.X , -1);
		populate(AminoAcid.Z, AminoAcid.X , -1);
		populate(AminoAcid.X, AminoAcid.X , -1);

	}
}
