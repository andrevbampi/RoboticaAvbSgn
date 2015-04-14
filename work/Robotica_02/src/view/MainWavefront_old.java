package view;
import util.PontoMapa;

public class MainWavefront_old {

	private int[][] cenarioB = new int[6][7];
	private int[][] cenarioA = new int[6][7];
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
	private PontoMapa inicioRoboTeste = new PontoMapa(5, 6);
	private PontoMapa[] obstaculosTeste = { new PontoMapa(0, 7),
			new PontoMapa(1, 6), new PontoMapa(2, 5), new PontoMapa(2, 4),
			new PontoMapa(2, 3), new PontoMapa(2, 2), new PontoMapa(3, 4),
			new PontoMapa(3, 3), new PontoMapa(3, 2), new PontoMapa(4, 4),
			new PontoMapa(4, 3), new PontoMapa(4, 2) };
	
	public void preencheCenarioA() {
		for (int i = 0; i < cenarioA.length; i++) {
			for (int j = 0; j < cenarioA[i].length; j++) {
				cenarioA[i][j] = 9999;
			}
		}

		// atribui os obstaculos no cenario
		for (PontoMapa pontoMapa : obstaculosA) {
			cenarioA[pontoMapa.getX()][pontoMapa.getY()] = -1;
		}

		preencherWavefront(cenarioA, objetivoA, inicioRoboA);

		System.out.println(imprimirCenario(cenarioA));
	}

	public void preencheCenarioB() {
		for (int i = 0; i < cenarioB.length; i++) {
			for (int j = 0; j < cenarioB[i].length; j++) {
				cenarioB[i][j] = 9999;
			}
		}

		// atribui os obstaculos no cenario
		for (PontoMapa pontoMapa : obstaculosB) {
			cenarioB[pontoMapa.getX()][pontoMapa.getY()] = -1;
		}

		preencherWavefront(cenarioB, objetivoB, inicioRoboB);

		System.out.println(imprimirCenario(cenarioB));
	}

	public void preencheCenarioTeste() {
		for (int i = 0; i < cenarioTeste.length; i++) {
			for (int j = 0; j < cenarioTeste[i].length; j++) {
				cenarioTeste[i][j] = 9999;
			}
		}

		// atribui os obstaculos no cenario
		for (PontoMapa pontoMapa : obstaculosTeste) {
			cenarioTeste[pontoMapa.getX()][pontoMapa.getY()] = -1;
		}

		preencherWavefront(cenarioTeste, objetivoTeste, inicioRoboTeste);

		System.out.println(imprimirCenario(cenarioTeste));
	}
	
	private void preencherWavefront(int[][] cenario, PontoMapa objetivo, PontoMapa robo) {
		cenario[objetivo.getX()][objetivo.getY()] = 0;
		cenario[robo.getX()][robo.getY()] = Integer.MAX_VALUE;

		int objetivoX = objetivo.getX();
		int objetivoY = objetivo.getY();
		int roboX = robo.getX();
		int roboY = robo.getY();
		int contador = 1;

		while (temValorDefault(cenario, 9999)) {
			for (int i = (objetivoX - contador), j = (objetivoX + contador); i <= j; i++) {
				for (int i2 = (objetivoY - contador), j2 = (objetivoY + contador); i2 <= j2; i2++) {
					if (i < 0 || i >= cenario.length)
						continue;

					if (i2 < 0
							|| i2 >= cenario[0].length
							|| (i == objetivoX && i2 == objetivoY)
							|| (i == roboX && i2 == roboY)
							|| cenario[i][i2] == -1
							|| (cenario[i][i2] != -1 && cenario[i][i2] < contador))
						continue;

					cenario[i][i2] = contador;
				}
			}

			contador++;
		}
	}

	public String imprimirCenario(int[][] cenario) {
		StringBuilder str = new StringBuilder();

		for (int i = 0; i < cenario[0].length; i++) {
			for (int j = 0; j < cenario.length; j++) {
				if (cenario[j][i] == Integer.MAX_VALUE)
					str.append("R ");
				else if (cenario[j][i] == -1)
					str.append("X ");
				else
					str.append(cenario[j][i] + " ");
			}

			str.append("\n");
		}

		return str.toString();
	}
	
	private boolean temValorDefault(int [][] cenario, int padrao) {
		for (int i = 0; i < cenario.length; i++) {
			for (int j = 0; j < cenario[0].length; j++) {
				if (cenario[i][j] == padrao)
					return true;
			}
		}
		
		return false;
	}

	public static void main(String[] args) {
		MainWavefront_old m = new MainWavefront_old();

//		System.out.println("Cen�rio A:");
//		m.preencheCenarioA();
//		
//		System.out.println("\n");
//		
//		System.out.println("Cen�rio B:");
//		m.preencheCenarioB();
//		
//		System.out.println("\n");
		
		System.out.println("Cen�rio Teste:");
		m.preencheCenarioTeste();
	}
}
