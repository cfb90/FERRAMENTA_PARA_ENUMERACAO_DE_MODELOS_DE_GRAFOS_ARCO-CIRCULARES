package Extra;

import java.math.BigInteger;
import java.util.ArrayList;

public class Fatorial {
	ArrayList<BigInteger> tabela;
	public Fatorial(int tam){
		tabela=new ArrayList<BigInteger>(tam);
		tabela.add(BigInteger.valueOf(1));
	}
	public BigInteger fatorar(int num){
		if(tabela.size()>num) return tabela.get(num);
		else{
			tabela.add(BigInteger.valueOf(num).multiply(fatorar(num-1)));
			return tabela.get(num);
		}
	}
}
