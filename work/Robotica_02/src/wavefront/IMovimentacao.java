package wavefront;

import util.PontoMapa;

/**
 * 
 * @author Silvio
 *
 * Interface que define os m�todos
 * para fazer a movimenta��o do rob�
 * na matriz do wavefront
 *
 */
public interface IMovimentacao {

	/**
	 * 
	 * @param cenario
	 * @param robo
	 * @return
	 */
	Direcao proximoMovimento(int [][] cenario, PontoMapa robo);
	
	/**
	 * Metodo que verifica se a posi��o que
	 * est� sendo testado n�o � a posi��o original
	 * do robo ou obstaculo
	 * 
	 * @param c
	 * @return
	 */
	boolean validarContador(int c);
	
}
