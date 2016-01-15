package Util;

import java.io.Serializable;
import java.math.BigInteger;

public class RSAPrivateKey implements Serializable {

	private static final long serialVersionUID = 1554968680635154995L;
	
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
