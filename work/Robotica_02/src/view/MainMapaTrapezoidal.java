package view;

import java.util.ArrayList;

import mapaTrapezoidal.Trapezoide;
import util.PontoMapa;

public class MainMapaTrapezoidal {
	private final int padrao = 99;
	private final int obstaculo = -1;
	// Números aleatórios da minha cabeça
	private final int centro = 44;
	private final int pontoMedio = 12;

	private int[][] cenarioA = new int[6][7];
	private int[][] cenarioB = new int[6][7];

	private PontoMapa objetivoA = new PontoMapa(5, 2);
	private PontoMapa inicioRoboA = new PontoMapa(0, 6);
	private PontoMapa[] obstaculosA = { new PontoMapa(1, 0),
			new PontoMapa(3, 1), new PontoMapa(4, 2), new PontoMapa(2, 3),
			new PontoMapa(4, 4), new PontoMapa(0, 5), new PontoMapa(2, 5),
			new PontoMapa(3, 6), new PontoMapa(5, 6) };
	private ArrayList<Trapezoide> listaTrapezoidesA = new ArrayList<Trapezoide>();
	private int[][] pontosMediosA = new int[6][7];
	private ArrayList<PontoMapa> listaPontosA = new ArrayList<PontoMapa>();

	private PontoMapa objetivoB = new PontoMapa(5, 6);
	private PontoMapa inicioRoboB = new PontoMapa(0, 3);
	private PontoMapa[] obstaculosB = { new PontoMapa(2, 1),
			new PontoMapa(3, 2), new PontoMapa(1, 3), new PontoMapa(4, 3),
			new PontoMapa(2, 4), new PontoMapa(4, 5), new PontoMapa(3, 6) };
	private ArrayList<Trapezoide> listaTrapezoidesB = new ArrayList<Trapezoide>();
	private int[][] pontosMediosB = new int[6][7];
	private ArrayList<PontoMapa> listaPontosB = new ArrayList<PontoMapa>();

	public void preencherCenarioInicial(char cenario) throws Exception {
		switch (cenario) {
		case 'A':
			preencherCenarioInicial(listaTrapezoidesA, cenarioA, obstaculosA);
			break;
		case 'B':
			preencherCenarioInicial(listaTrapezoidesB, cenarioB, obstaculosB);
		}
	}

	private void preencherCenarioInicial(
			ArrayList<Trapezoide> listaTrapezoides, int[][] cenario,
			PontoMapa[] obstaculos) throws Exception {
		// Atribuir valor padrão
		for (int i = 0; i < cenario.length; i++) {
			for (int j = 0; j < cenario[i].length; j++) {
				cenario[i][j] = padrao;
			}
		}

		// atribui os obstaculos no cenario
		for (PontoMapa pontoMapa : obstaculos) {
			cenario[pontoMapa.getX()][pontoMapa.getY()] = obstaculo;
		}

		// Gerar os trapezóides
		// listaTrapezoides = new ArrayList<Trapezoide>();
		Trapezoide trapezoide = new Trapezoide();
		for (int x = 0; x < cenario.length; x++) {
			for (int y = 0; y < cenario[x].length; y++) {
				if (cenario[x][y] == obstaculo) {
					if (!trapezoide.estaVazio()) {
						trapezoide.setyFinal(y - 1);
						trapezoide.calcularCentro();
						listaTrapezoides.add(trapezoide);
						trapezoide = new Trapezoide();
					}
				} else {
					if (trapezoide.estaVazio()) {
						trapezoide.setX(x);
						trapezoide.setyInicial(y);
					}
				}
			}
			if (trapezoide.faltaFim()) {
				trapezoide.setyFinal(cenario[x].length - 1);
				listaTrapezoides.add(trapezoide);
				trapezoide.calcularCentro();
				trapezoide = new Trapezoide();
			}
		}
	}

	public void mostrarTrapezoides(char cenario) {
		switch (cenario) {
		case 'A':
			mostrarTrapezoides(listaTrapezoidesA);
			break;
		case 'B':
			mostrarTrapezoides(listaTrapezoidesB);
		}
	}

	public void mostrarTrapezoides(ArrayList<Trapezoide> listaTrapezoides) {
		for (Trapezoide trapezoide : listaTrapezoides) {
			System.out.println(trapezoide);
		}
	}

	public void montarMatrizPontosMedios(char cenario) throws Exception {
		switch(cenario) {
			case 'A': montarMatrizPontosMedios(listaTrapezoidesA, cenarioA, pontosMediosA, inicioRoboA, objetivoA, listaPontosA);
					  break;
			case 'B': montarMatrizPontosMedios(listaTrapezoidesB, cenarioB, pontosMediosB, inicioRoboB, objetivoB, listaPontosB);
		}
	}


	// Pontos cegos são os pontos em que não se pode ir nem pra cima, nem pra
	// direita e nem pra baixo.
	// Não devem ser inseridos, porque não levarão à lugar nenhum.
	private boolean pontoCego(Trapezoide trapezoide, int[][] matriz) {
		boolean bloqueadocima = (trapezoide.getyCentro() == 0 || (matriz[trapezoide
				.getX()][trapezoide.getyCentro() - 1] == obstaculo));
		boolean bloqueadodireita = (trapezoide.getX() == matriz.length - 1 || (matriz[trapezoide
				.getX() + 1][trapezoide.getyCentro()] == obstaculo));
		boolean bloqueadobaixo = (trapezoide.getyCentro() == matriz[trapezoide
				.getX()].length - 1 || (matriz[trapezoide.getX()][trapezoide
				.getyCentro() + 1] == obstaculo));
		return (bloqueadocima && bloqueadodireita && bloqueadobaixo);
	}

	private void montarMatrizPontosMedios(ArrayList<Trapezoide> listaTrapezoides,
            int[][] cenario,
            int[][] pontosMedios,
            PontoMapa inicioRobo, 
            PontoMapa objetivo,
            ArrayList<PontoMapa> listaPontos) throws Exception {

		// Copiar a matriz de cenário
		for (int x = 0; x < cenario.length; x++) {
			for (int y = 0; y < cenario[x].length; y++) {
				pontosMedios[x][y] = cenario[x][y];
			}
		}

		
		  for (Trapezoide trapezoide : listaTrapezoides) { // O centro dos trapezóides também serão vértices. Não haverá // distinção.
			  if (!pontoCego(trapezoide, pontosMedios)) {
				  pontosMedios[trapezoide.getX()][trapezoide.getyCentro()] = pontoMedio;
				  listaPontos.add(new PontoMapa(trapezoide.getX(), trapezoide.getyCentro())); 
			  } 
		  }
		 

		/*Trapezoide trapTemp = null;
		for (Trapezoide trapezoide : listaTrapezoides) {
			for (Trapezoide trapezoide2 : listaTrapezoides) {
				if (!trapezoide.equals(trapezoide2)) {
					if ((trapezoide.getX() == (trapezoide2.getX() - 1))
							&& (trapezoide.getyInicial() <= trapezoide2
									.getyFinal())
							&& (trapezoide.getyFinal() >= trapezoide2
									.getyInicial())) {
						trapTemp = new Trapezoide();
						trapTemp.setX(trapezoide2.getX());
						if (trapezoide.getyInicial() < trapezoide2
								.getyInicial()) {
							trapTemp.setyInicial(trapezoide2.getyInicial());
						} else {
							trapTemp.setyInicial(trapezoide.getyInicial());
						}
						if (trapezoide.getyFinal() < trapezoide2.getyFinal()) {
							trapTemp.setyFinal(trapezoide.getyFinal());
						} else {
							trapTemp.setyFinal(trapezoide2.getyFinal());
						}
						trapTemp.calcularCentro();

						if (!pontoCego(trapTemp, pontosMedios)) {
							pontosMedios[trapTemp.getX()][trapTemp.getyCentro()] = pontoMedio;
							PontoMapa pontoMapa = new PontoMapa(
									trapTemp.getX(), trapTemp.getyCentro());
							if (!listaPontos.contains(pontoMapa))
								listaPontos.add(pontoMapa);
							trapTemp = new Trapezoide();
						}
					}
				}
			}
		}*/
	}

	public String imprimirPontosMedios(char cenario) {
		switch (cenario) {
		case 'A':
			return imprimirPontosMedios(pontosMediosA);
		case 'B':
			return imprimirPontosMedios(pontosMediosB);
		}
		return "";
	}

	private String imprimirPontosMedios(int[][] pontosMedios) {
		StringBuilder str = new StringBuilder();

		for (int i = 0; i < pontosMedios[0].length; i++) {
			for (int j = 0; j < pontosMedios.length; j++) {
				if (pontosMedios[j][i] == pontoMedio)
					str.append("[PM]");
				else if (pontosMedios[j][i] == centro)
					str.append("[ce]");
				else if (pontosMedios[j][i] == obstaculo)
					str.append("[XX]");
				else {
					String s = String.valueOf(pontosMedios[j][i]);
					// if (s.length() < 2)
					// str.append("[" + String.format("%1s", "0") + s + "]");
					// else
					// str.append("[" + s + "]");
					str.append("[  ]");
				}
			}
			str.append("\n");
		}
		return str.toString();
	}

	/*private boolean temAdjacencia(PontoMapa pontoIni, PontoMapa pontoFim, int[][] pontosMedios) {
		int xIni = pontoIni.getX();
		int xFim = pontoFim.getX();
		
		if (((xIni + 1) < xFim) || (xIni > xFim))
			return false;
		
		boolean achouI = false, achouF = false;
		for (int j = 0; j < pontosMedios[xFim].length; j++) {
			int v = pontosMedios[xFim][j];
			if (v != padrao) {
				if (j == pontoIni.getY())
					achouI = true;
				
				if (j == pontoFim.getY())
					achouF = true;
				
				if (v == obstaculo && (achouF || achouI))
					return false;
				
				if (achouI && achouF)
					return true;
			}
		}
		
		return false;
	}

	private int[][] gerarMatrizAdjacencia(ArrayList<PontoMapa> listaPontos, int[][] pontosMedios) {
		int[][] matrizAdj = new int[listaPontos.size()][listaPontos.size()];
		
		for (int i = 0; i < listaPontos.size(); i++) {
			for (int j = 0; j < listaPontos.size(); j++) {
				if ((i == j) || !temAdjacencia(listaPontos.get(i), listaPontos.get(j), pontosMedios)) {
					matrizAdj[i][j] = 0;
				} else {
					matrizAdj[i][j] = 1;
				}
			}
		}
		
		return null;
	}*/
	
	public void montarCaminho(int[][]listaPontos, PontoMapa inicioRobo, PontoMapa objetivo) {
		
	}

	public static void main(String[] args) {
		try {
			MainMapaTrapezoidal mapaTrapezoidal = new MainMapaTrapezoidal();

			System.out.println("Cenário A");
			mapaTrapezoidal.preencherCenarioInicial('A');
			mapaTrapezoidal.mostrarTrapezoides('A');
			System.out.println("\n");
			mapaTrapezoidal.montarMatrizPontosMedios('A');
			System.out.println("\n");
			System.out
					.println(mapaTrapezoidal.imprimirPontosMedios('A') + "\n");

			System.out.println("Cenário B");
			mapaTrapezoidal.preencherCenarioInicial('B');
			mapaTrapezoidal.mostrarTrapezoides('B');
			System.out.println("\n");
			mapaTrapezoidal.montarMatrizPontosMedios('B');
			System.out.println("\n");
			System.out
					.println(mapaTrapezoidal.imprimirPontosMedios('B') + "\n");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
