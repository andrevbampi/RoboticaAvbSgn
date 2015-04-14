package mapaTrapezoidal;

public class Trapezoide {
	private int x;
	private int yInicial;
	private int yFinal;
	private final int padrao = 99;
	
	public Trapezoide() {
		x = padrao;
		yInicial = padrao;
		yFinal = padrao;
	}
	
	public boolean estaVazio() {
		return (x==padrao) && (yInicial==padrao) && (yFinal==padrao);
	}
	
	public boolean faltaFim() {
		return (x<padrao) && (yInicial<padrao) && (yFinal==padrao);
	}
	
	public String toString() {
		return "X = " + x + "; Y Inicial = " + yInicial + "; Y Final = " + yFinal;
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
}
