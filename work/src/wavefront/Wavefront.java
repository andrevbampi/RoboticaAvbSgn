package wavefront;

import java.util.ArrayList;
import java.util.List;

import util.PontoMapa;

public class Wavefront {
	private final int pista = 99;
	private final int pedra = -1;
	private final int objet = 0;
	private final int robot = Integer.MAX_VALUE;

	// cenario A
	//{
	private int[][] cenarioA = {
		{pista, pedra, pista, pista, pista, pista},
		{pista, pista, pista, pedra, pista, pista},
		{pista, pista, pista, pista, pedra, objet},
		{pista, pista, pedra, pista, pista, pista},
		{pista, pista, pista, pista, pedra, pista},
		{pedra, pista, pedra, pista, pista, pista},
		{robot, pista, pista, pedra, pista, pedra}
	};
	private PontoMapa objetivoA = new PontoMapa(5, 2);
	private PontoMapa inicioRoboA = new PontoMapa(0, 6);
	//}
	
	//cenario B
	//{
	private int[][] cenarioB = {
		{pista, pista, pista, pista, pista, pista},
		{pista, pista, pedra, pista, pista, pista},
		{pista, pista, pista, pedra, pista, pista},
		{robot, pedra, pista, pista, pedra, pista},
		{pista, pista, pedra, pista, pista, pista},
		{pista, pista, pista, pista, pedra, pista},
		{pista, pista, pista, pedra, pista, objet}
	};
	private PontoMapa objetivoB = new PontoMapa(5, 6);
	private PontoMapa inicioRoboB = new PontoMapa(0, 3);
	//}

	// variaveis da navegacao
	//{
	private IMovimentacao mov;
	private List<Direcao> navegacao = new ArrayList<Direcao>();
	//}

	public Wavefront(IMovimentacao mov) {
		this.mov = mov;
	}
	
	private void preencherWavefront(int[][] cenario, int x, int y, int contador) {
		if (x < 0 || x >= cenario[0].length || y < 0 || y >= cenario.length)
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
			v = cenario[y][xE];
			if (v == pista || (v != pedra && v != 0 && v != Integer.MAX_VALUE && v > contador)) {
				cenario[y][xE] = contador;
				esq = true;
			}	
		}
		
		// preenche campo em cima
		if (yC > -1) {
			v = cenario[yC][x];
			if (v == pista || (v != pedra && v != 0 && v != Integer.MAX_VALUE && v > contador)) {
				cenario[yC][x] = contador;
				cima = true;
			}	
		}
		
		// preenche campo na direita
		if (xD < cenario[0].length) {
			v = cenario[y][xD];
			if (v == pista || (v != pedra && v != 0 && v != Integer.MAX_VALUE && v > contador)) {
				cenario[y][xD] = contador;
				dir = true;
			}
		}
		
		// preenche campo em baixo
		if (yB < cenario.length) {
			v = cenario[yB][x];
			if (v == pista || (v != pedra && v != 0 && v != Integer.MAX_VALUE && v > contador)) {
				cenario[yB][x] = contador;
				baixo = true;
			}
		}
		
		if (esq)
			preencherWavefront(cenario, xE, y, (contador + 1));

		if (dir)
			preencherWavefront(cenario, xD, y, (contador + 1));

		if (cima)
			preencherWavefront(cenario, x, yC, (contador + 1));

		if (baixo)
			preencherWavefront(cenario, x, yB, (contador + 1));
	}

	public List<Direcao> navegarCenario(char c) {
		int[][] cenario;
		PontoMapa inicioRobo, objetivo;
		
		switch (c) {
		case 'A':
			cenario = cenarioA;
			inicioRobo = inicioRoboA;
			objetivo = objetivoA;
			break;
		case 'B':
			cenario = cenarioB;
			inicioRobo = inicioRoboB;
			objetivo = objetivoB;
			break;
		default:
			return null;
		}
		
		desenharCenario(c);
		
		this.navegacao.clear();
		
		while (!inicioRobo.pontosIguais(objetivo)) {
			this.navegacao.add(mov.proximoMovimento(cenario, inicioRobo));
			cenario[inicioRobo.getY()][inicioRobo.getX()] = Integer.MAX_VALUE;
		}

		for (Direcao d : this.navegacao) {
			System.out.print(d.toString() + " ");
		}
		
		return this.navegacao;
	}
	
	public void desenharCenario(char cenario) {
		switch (cenario) {
		case 'A':
			preencherWavefront(cenarioA, objetivoA.getX(), this.objetivoA.getY(), 1);
			System.out.println(imprimirCenario(cenarioA));
			break;
		case 'B':
			preencherWavefront(cenarioB, objetivoB.getX(), this.objetivoB.getY(), 1);
			System.out.println(imprimirCenario(cenarioB));
			break;
		default:
			break;
		}
	}

	private String imprimirCenario(int[][] cenario) {
 		StringBuilder str = new StringBuilder();

 		for (int i = 0; i < cenario.length; i++) {
			for (int j = 0; j < cenario[0].length; j++) {
				int valor = cenario[i][j];
				
				if (valor == Integer.MAX_VALUE)
					str.append("[RR]");
				else if (valor == pedra)
					str.append("[XX]");
				else
					str.append("[" + valor + "]");
			}
			
			str.append("\n");
		}

		return str.toString();
	}
}
