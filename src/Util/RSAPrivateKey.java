package Util;

import java.io.Serializable;
import java.math.BigInteger;

public class RSAPrivateKey implements Serializable {

	private static final long serialVersionUID = 1554968680635154995L;
	
	private BigInteger n;
	private BigInteger u;

	public RSAPrivateKey(BigInteger n, BigInteger u) {
		this.n = n;
		this.u = u;
	}

	public BigInteger getN() {
		return this.n;
	}

	public BigInteger getU() {
		return this.u;
	}

}
