package wavefront;

/**
 * 
 * @author Silvio
 *
 * Interface que define os métodos
 * para fazer a movimentação do robô
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
	 * Metodo que verifica se a posição que
	 * está sendo testado não é a posição original
	 * do robo ou obstaculo
	 * 
	 * @param c
	 * @return
	 */
	boolean validarContador(int c);
	
}
