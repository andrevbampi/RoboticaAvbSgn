package wavefront;

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
		
		// se a posi��o a esquerda da posi��o atual estiver dentro da matriz ent�o valida
		if (xE > -1) {
			int aux = cenario[xE][rY];

			if (aux == 0) { // se j� for objetivo ent�o retorna
				robo.setX(xE);
				return Direcao.O;
			}

			if (validarContador(aux) && aux < contador) {
				contador = aux;
				d = Direcao.O;
			}
		}
		
		// se a posi��o do topo estiver dentro da matriz
		if (yC > -1) {
			int aux = cenario[rX][yC];

			if (aux == 0) { // se j� for objetivo ent�o retorna
				robo.setY(yC);
				return Direcao.N;	
			}

			if (validarContador(aux) && aux < contador) {
				contador = aux;
				d = Direcao.N;
			}
		}
		
		// se a posi��o a direita estiver dentro da matriz
		if (xD < cenario.length) {
			int aux = cenario[xD][rY];

			if (aux == 0) { // se j� for objetivo ent�o retorna
				robo.setX(xD);
				return Direcao.L;	
			}

			if (validarContador(aux) && aux < contador) {
				contador = aux;
				d = Direcao.L;
			}
		}

		// se a posi��o baixo estiver dentro da matriz
		if (yB < cenario[0].length) {
			int aux = cenario[rX][yB];

			if (aux == 0) { // se j� for objetivo ent�o retorna
				robo.setY(yB);
				return Direcao.S;
			}

			if (validarContador(aux) && aux < contador) {
				contador = aux;
				d = Direcao.S;
			}
		}

		// altera posi��o do robo na matriz
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
