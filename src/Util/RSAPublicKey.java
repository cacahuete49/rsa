package Util;

import java.io.Serializable;
import java.math.BigInteger;

public class RSAPublicKey implements Serializable{

	private static final long serialVersionUID = 1978638915783625707L;

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
