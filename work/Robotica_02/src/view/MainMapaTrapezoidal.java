package view;

import java.util.ArrayList;

import mapaTrapezoidal.Trapezoide;
import util.PontoMapa;

public class MainMapaTrapezoidal {
	private final int padrao = 99;
	private final int obstaculo = -1;

	private int[][] cenarioA = new int[6][7];
	private int[][] cenarioB = new int[6][7];

	private PontoMapa objetivoA = new PontoMapa(5, 2);
	private PontoMapa inicioRoboA = new PontoMapa(0, 6);
	private PontoMapa[] obstaculosA = { new PontoMapa(1, 0),
			new PontoMapa(3, 1), new PontoMapa(4, 2), new PontoMapa(2, 3),
			new PontoMapa(4, 4), new PontoMapa(0, 5), new PontoMapa(2, 5),
			new PontoMapa(3, 6), new PontoMapa(5, 6) };
	private ArrayList<Trapezoide> listaTrapezoidesA = new ArrayList<Trapezoide>();

	private PontoMapa objetivoB = new PontoMapa(5, 6);
	private PontoMapa inicioRoboB = new PontoMapa(0, 3);
	private PontoMapa[] obstaculosB = { new PontoMapa(2, 1),
			new PontoMapa(3, 2), new PontoMapa(1, 3), new PontoMapa(4, 3),
			new PontoMapa(2, 4), new PontoMapa(4, 5), new PontoMapa(3, 6) };
	private ArrayList<Trapezoide> listaTrapezoidesB = new ArrayList<Trapezoide>();


	public void preencherCenarioInicial(char cenario) {
		switch(cenario) {
			case 'A': preencherCenarioInicial(listaTrapezoidesA,
                    						  cenarioA, 
                    						  obstaculosA);
			break;
			case 'B': preencherCenarioInicial(listaTrapezoidesB,
					  cenarioB, 
					  obstaculosB);
		}
	}
	
	private void preencherCenarioInicial(ArrayList<Trapezoide> listaTrapezoides,
			                             int[][] cenario, 
			                             PontoMapa[] obstaculos) {
		//Atribuir valor padrão
		for (int i = 0; i < cenario.length; i++) {
			for (int j = 0; j < cenario[i].length; j++) {
				cenario[i][j] = padrao;
			}
		}

		// atribui os obstaculos no cenario
		for (PontoMapa pontoMapa : obstaculos) {
			cenario[pontoMapa.getX()][pontoMapa.getY()] = obstaculo;
		}
		
		//Gerar os trapezóides
		//listaTrapezoides = new ArrayList<Trapezoide>();
		Trapezoide trapezoide = new Trapezoide();
		for (int x = 0; x < cenario.length; x++) {
			for (int y = 0; y < cenario[x].length; y++) {
				if (cenario[x][y] == obstaculo) {
					if (!trapezoide.estaVazio()) {
						trapezoide.setyFinal(y-1);
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
				trapezoide.setyFinal(cenario[x].length-1);
				listaTrapezoides.add(trapezoide);
				trapezoide = new Trapezoide();
			}
		}
	}
	
	public void mostrarTrapezoides(char cenario) {
		switch(cenario) {
			case 'A': mostrarTrapezoides(listaTrapezoidesA);
			break;
			case 'B': mostrarTrapezoides(listaTrapezoidesB);
		}
	}
	
	public void mostrarTrapezoides(ArrayList<Trapezoide> listaTrapezoides) {
		for (Trapezoide trapezoide : listaTrapezoides) {
			System.out.println(trapezoide);
		}
	}
	
	public static void main(String[] args) {
		MainMapaTrapezoidal mapaTrapezoidal = new MainMapaTrapezoidal();
		
		System.out.println("Cenário A");
		mapaTrapezoidal.preencherCenarioInicial('A');
		mapaTrapezoidal.mostrarTrapezoides('A');
		System.out.println("\n");
		
		System.out.println("Cenário B");
		mapaTrapezoidal.preencherCenarioInicial('B');
		mapaTrapezoidal.mostrarTrapezoides('B');
		System.out.println("\n");
	}
}
