package Util;

import java.math.BigInteger;

public class RSAPrivateKey {

	private Integer n;
	private BigInteger e;

	public RSAPrivateKey(Integer n, BigInteger e) {
		this.n = n;
		this.e = e;
	}

	public Integer getN() {
		return this.n;
	}

	public BigInteger getE() {
		return this.e;
	}

}
