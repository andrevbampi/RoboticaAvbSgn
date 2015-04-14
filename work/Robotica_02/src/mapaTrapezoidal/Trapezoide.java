package mapaTrapezoidal;

public class Trapezoide {
	private int x;
	private int yInicial;
	private int yFinal;
	private int yCentro;
	private final int padrao = 99;
	
	public Trapezoide() {
		x = padrao;
		yInicial = padrao;
		yFinal = padrao;
		yCentro = padrao;
	}
	
	public boolean estaVazio() {
		return (x==padrao) && (yInicial==padrao) && (yFinal==padrao);
	}
	
	public boolean faltaFim() {
		return (x<padrao) && (yInicial<padrao) && (yFinal==padrao);
	}
	
	public String toString() {
		return "X = " + x + "; Y Inicial = " + yInicial + "; Y Final = " + yFinal + "; Y Centro = " + yCentro;
	}
	
	public void calcularCentro() throws Exception {
		if (x == padrao) {
			throw new Exception("Trapezóide sem o valor do X informado.");
		}
		if (yInicial == padrao) {
			throw new Exception("Trapezóide sem o valor do Y Inicial informado.");
		}
		if (yFinal == padrao) {
			throw new Exception("Trapezóide sem o valor do Y Final informado.");
		}
		yCentro = (yInicial + yFinal) / 2;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Trapezoide) {
			Trapezoide trapezoide =  (Trapezoide) obj;
			return (trapezoide.getX() == x) &&
					trapezoide.getyInicial() == yInicial &&
					trapezoide.getyFinal() == yFinal;
		}
		return false;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getyInicial() {
		return yInicial;
	}
	public void setyInicial(int yInicial) {
		this.yInicial = yInicial;
	}
	public int getyFinal() {
		return yFinal;
	}
	public void setyFinal(int yFinal) {
		this.yFinal = yFinal;
	}
	public int getyCentro() {
		return yCentro;
	}
}
