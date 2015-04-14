package view;
import java.util.ArrayList;
import java.util.List;

import util.PontoMapa;
import wavefront.Direcao;
import wavefront.IMovimentacao;
import wavefront.LogicaQuatro;

public class MainWavefront {
	private final int padrao = 99;
	private final int obstaculo = -1;

	private int[][] cenarioA = new int[6][7];
	private int[][] cenarioB = new int[6][7];
	private int[][] cenarioTeste = new int[10][8];

	private PontoMapa objetivoA = new PontoMapa(5, 2);
	private PontoMapa inicioRoboA = new PontoMapa(0, 6);
	private PontoMapa[] obstaculosA = { new PontoMapa(1, 0),
			new PontoMapa(3, 1), new PontoMapa(4, 2), new PontoMapa(2, 3),
			new PontoMapa(4, 4), new PontoMapa(0, 5), new PontoMapa(2, 5),
			new PontoMapa(3, 6), new PontoMapa(5, 6) };

	private PontoMapa objetivoB = new PontoMapa(5, 6);
	private PontoMapa inicioRoboB = new PontoMapa(0, 3);
	private PontoMapa[] obstaculosB = { new PontoMapa(2, 1),
			new PontoMapa(3, 2), new PontoMapa(1, 3), new PontoMapa(4, 3),
			new PontoMapa(2, 4), new PontoMapa(4, 5), new PontoMapa(3, 6) };

	private PontoMapa objetivoTeste = new PontoMapa(8, 4);
	private PontoMapa inicioRoboTeste = new PontoMapa(0, 3);
	private PontoMapa[] obstaculosTeste = { new PontoMapa(0, 7),
			new PontoMapa(1, 6), new PontoMapa(2, 5), new PontoMapa(2, 4),
			new PontoMapa(2, 3), new PontoMapa(2, 2), new PontoMapa(3, 4),
			new PontoMapa(3, 3), new PontoMapa(3, 2), new PontoMapa(4, 4),
			new PontoMapa(4, 3), new PontoMapa(4, 2) };

	// variaveis de controle da navega��o
	private IMovimentacao mov;
	private List<Direcao> navegacao = new ArrayList<Direcao>();
	
	public MainWavefront(IMovimentacao mov) {
		this.mov = mov;
	}
	
	public void preencheCenario(char cenario) {
		switch(cenario) {
			case 'A': preencheCenario(cenarioA, 
									  obstaculosA, 
									  objetivoA, 
									  inicioRoboA);
				      break;
			case 'B': preencheCenario(cenarioB, 
					                  obstaculosB, 
					                  objetivoB, 
					                  inicioRoboB);
					  break;
			default: preencheCenario(cenarioTeste, 
					                 obstaculosTeste, 
					                 objetivoTeste, 
					                 inicioRoboTeste);
		}
	}
	
	private void preencheCenario(int[][] cenario, 
			                     PontoMapa[] obstaculos, 
			                     PontoMapa objetivo,
			                     PontoMapa inicioRobo) {
		for (int i = 0; i < cenario.length; i++) {
			for (int j = 0; j < cenario[i].length; j++) {
				cenario[i][j] = padrao;
			}
		}

		// atribui os obstaculos no cenario
		for (PontoMapa pontoMapa : obstaculos) {
			cenario[pontoMapa.getX()][pontoMapa.getY()] = obstaculo;
		}
		
		cenario[objetivo.getX()][objetivo.getY()] = 0;
		cenario[inicioRobo.getX()][inicioRobo.getY()] = Integer.MAX_VALUE;

		preencherWavefront(cenario, objetivo.getX(), objetivo.getY(), 1, padrao);

		System.out.println(imprimirCenario(cenario));
	}
	
	public void navegarCenario(char cenario) {
		switch(cenario) {
		case 'A': navegarCenario(cenarioA, 
				                 inicioRoboA, 
								 objetivoA);
			      break;
		case 'B': navegarCenario(cenarioB, 
                                 inicioRoboB, 
				                 objetivoB);
				  break;
		default: navegarCenario(cenarioTeste, 
                                inicioRoboTeste, 
				                objetivoTeste);
		}
	}
	
	private void navegarCenario(int[][] cenario,
						       PontoMapa inicioRobo,
			                   PontoMapa objetivo) {
		while (!inicioRobo.pontosIguais(objetivo)) {
			this.navegacao.add(mov.proximoMovimento(cenario, inicioRobo));
			cenario[inicioRobo.getX()][inicioRobo.getY()] = Integer.MAX_VALUE;
		}

		for (Direcao d : this.navegacao) {
			System.out.print(d.toString() + " ");
		}
	}
	
	private void preencherWavefront(int[][] cenario, int x, int y, int contador, int padrao) {
		if (x < 0 || x >= cenario.length || y < 0 || y >= cenario[0].length)
			return;
		
		int xE = (x - 1);
		boolean esq = false;
		int xD = (x + 1);
		boolean dir = false;
		int yC = (y - 1);
		boolean cima = false;
		int yB = (y + 1);
		boolean baixo = false;

		int v;
		
		// preenche campo na esquerda
		if (xE > -1) {
			v = cenario[xE][y];
			if (v == padrao || (v != obstaculo && v != 0 && v != Integer.MAX_VALUE && v > contador)) {
				cenario[xE][y] = contador;
				esq = true;
			}	
		}
		
		// preenche campo em cima
		if (yC > -1) {
			v = cenario[x][yC];
			if (v == padrao || (v != obstaculo && v != 0 && v != Integer.MAX_VALUE && v > contador)) {
				cenario[x][yC] = contador;
				cima = true;
			}	
		}
		
		// preenche campo na direita
		if (xD < cenario.length) {
			v = cenario[xD][y];
			if (v == padrao || (v != obstaculo && v != 0 && v != Integer.MAX_VALUE && v > contador)) {
				cenario[xD][y] = contador;
				dir = true;
			}
		}
		
		// preenche campo em baixo
		if (yB < cenario[0].length) {
			v = cenario[x][yB];
			if (v == padrao || (v != obstaculo && v != 0 && v != Integer.MAX_VALUE && v > contador)) {
				cenario[x][yB] = contador;
				baixo = true;
			}
		}
		
		if (esq)
			preencherWavefront(cenario, xE, y, (contador + 1), padrao);

		if (dir)
			preencherWavefront(cenario, xD, y, (contador + 1), padrao);

		if (cima)
			preencherWavefront(cenario, x, yC, (contador + 1), padrao);

		if (baixo)
			preencherWavefront(cenario, x, yB, (contador + 1), padrao);
	}

	public String imprimirCenario(int[][] cenario) {
 		StringBuilder str = new StringBuilder();

		for (int i = 0; i < cenario[0].length; i++) {
			for (int j = 0; j < cenario.length; j++) {
				if (cenario[j][i] == Integer.MAX_VALUE)
					str.append("[RR]");
				else if (cenario[j][i] == obstaculo)
					str.append("[XX]");
				else {
					String s = String.valueOf(cenario[j][i]);
					if (s.length() < 2)
						str.append("[" + String.format("%1s", "0") + s + "]");
					else
						str.append("[" + s + "]");
				}
			}

			str.append("\n");
		}

		return str.toString();
	}

	public static void main(String[] args) {
		MainWavefront m = new MainWavefront(new LogicaQuatro());
		
		System.out.println("Cenario A:");
		m.preencheCenario('A');
		m.navegarCenario('A');
		
		System.out.println("\n");
		
		System.out.println("Cenario B:");
		m.preencheCenario('B');
		m.navegarCenario('B');
		
/*		System.out.println("\n");
		
		System.out.println("Cenario teste:");
		m.preencheCenario('T');
		m.navegarCenario('T');*/
	}
}