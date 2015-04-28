package wavefront;

import util.PontoMapa;

public class LogicaQuatro implements IMovimentacao {

	@Override
	public Direcao proximoMovimento(int[][] cenario, PontoMapa robo) {
		int rX = robo.getX();
		int rY = robo.getY();

		int xE = (rX - 1); // validar campo na esquerda
		int xD = (rX + 1); // validar campo na direta
		int yC = (rY - 1); // validar campo em cima
		int yB = (rY + 1); // validar campo em baixo
		
		int contador = 9999;
		Direcao d = null;
		
		// se a posicao a esquerda da posicao atual estiver dentro da matriz entao valida
		if (xE > -1) {
			int aux = cenario[rY][xE];
			if (aux == 0) { // se ja for objetivo entao retorna
				robo.setX(xE);
				return Direcao.O;
			}

			if (validarContador(aux) && aux < contador) {
				contador = aux;
				d = Direcao.O;
			}
		}
		
		// se a posica do topo estiver dentro da matriz
		if (yC > -1) {
			int aux = cenario[yC][rX];
			if (aux == 0) { // se ja for objetivo entao retorna
				robo.setY(yC);
				return Direcao.N;	
			}

			if (validarContador(aux) && aux < contador) {
				contador = aux;
				d = Direcao.N;
			}
		}
		
		// se a posicao a direita estiver dentro da matriz
		if (xD < cenario[0].length) {
			int aux = cenario[rY][xD];
			if (aux == 0) { // se ja for objetivo entao retorna
				robo.setX(xD);
				return Direcao.L;	
			}

			if (validarContador(aux) && aux < contador) {
				contador = aux;
				d = Direcao.L;
			}
		}

		// se a posicao baixo estiver dentro da matriz
		if (yB < cenario.length) {
			int aux = cenario[yB][rX];
			if (aux == 0) { // se ja for objetivo entao retorna
				robo.setY(yB);
				return Direcao.S;
			}

			if (validarContador(aux) && aux < contador) {
				contador = aux;
				d = Direcao.S;
			}
		}

		// altera posicao do robo na matriz
		switch (d) {
		case O:
			robo.setX(xE);
			break;
		case N:
			robo.setY(yC);
			break;
		case L:
			robo.setX(xD);
			break;
		case S:
			robo.setY(yB);
			break;
		}
		
		return d;
	}

	@Override
	public boolean validarContador(int c) {
		return (c != -1 && c != Integer.MAX_VALUE);
	}

}
