package Util;

import java.math.BigInteger;

public class RSAPublicKey {

	private BigInteger m,n,e;

	public RSAPublicKey(BigInteger m, BigInteger n, BigInteger e) {
		this.m = m;
		this.n = n;
		this.e = e;
	}

	public BigInteger getN() {
		return this.n;
	}

	public BigInteger getE() {
		return this.e;
	}

	public BigInteger getM() {
		return this.m;
	}

}
