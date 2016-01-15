package Util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RSAKeysTools {

	private static final int BITLENGTH = 128;

	private static final BigInteger TWO = new BigInteger("2");

	public static RSAPublicKey buildPublicKey() {
		BigInteger p = BigInteger.probablePrime(BITLENGTH, new Random());
		BigInteger q;
		// Test la différence entre p & q
		do {
			q = BigInteger.probablePrime(BITLENGTH, new Random());
		} while (p.compareTo(q) == 0);

		BigInteger n = p.multiply(q);
		BigInteger m = (p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)));

		// Calcul le plus petit nombre premier entre doit etre impaire, > a 1, <
		// a m
		BigInteger e = new BigInteger("3");

		while ((m.gcd(e).compareTo(BigInteger.ONE) == 0) && (e.compareTo(m) == -1)) {
			e = e.add(BigInteger.ONE.add(BigInteger.ONE));
		}

		// si pas de nombre premier avec alors on retente sa chance ;-)
		if (e.compareTo(m) == 0)
			return buildPublicKey();

		return new RSAPublicKey(m, n, e);
	}

	public static RSAPrivateKey buildPrivatesKey(RSAPublicKey pub) {
		Couple c = extendEuclide(pub);

		BigInteger k = BigInteger.ZERO;
		BigInteger tmp = c.u.subtract(k.multiply(pub.getM()));
		while (!((TWO.compareTo(tmp) == -1) && (tmp.compareTo(pub.getM()) == -1))) {
			k = k.subtract(BigInteger.ONE);
			tmp = c.u.subtract(k.multiply(pub.getM()));
		}
		return new RSAPrivateKey(c.size, tmp);
	}

	private static Couple extendEuclide(RSAPublicKey pub) {
		List<BigInteger> r = new ArrayList<BigInteger>();
		List<BigInteger> u = new ArrayList<BigInteger>();
		List<BigInteger> v = new ArrayList<BigInteger>();
		r.add(pub.getE());
		r.add(pub.getM());
		u.add(BigInteger.ONE);
		u.add(BigInteger.ZERO);
		v.add(BigInteger.ZERO);
		v.add(BigInteger.ONE);

		while (r.get(r.size() - 1).intValue() != 0) {
			BigInteger tmp = r.get(r.size() - 2).divide(r.get(r.size() - 1));
			r.add(r.get(r.size() - 2).subtract(tmp.multiply(r.get(r.size() - 1))));
			u.add(u.get(u.size() - 2).subtract(tmp.multiply(u.get(u.size() - 1))));
			v.add(v.get(v.size() - 2).subtract(tmp.multiply(v.get(v.size() - 1))));
		}

		return new Couple(r.size()-1, u.get(u.size() - 2));

	}

		private static class Couple {
			public Integer size;
			public BigInteger u;
	
			public Couple(Integer size, BigInteger u) {
				this.size=size;
				this.u = u;
			}
		}

}
