package view;

import java.util.List;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import wavefront.Direcao;
import wavefront.LogicaQuatro;
import wavefront.Wavefront;

public class Main {

	public static void executaComandos(List<Direcao> navegacao) {
		Direcao dAtual = Direcao.L;
		int anguloPositivo = 270;
		int anguloNegativo = anguloPositivo * -1;
		
		for (Direcao d : navegacao) {
			switch (d) {
			case N:
				if (dAtual == Direcao.O) {
					Motor.A.rotate(anguloPositivo, true);
					Motor.B.rotate(anguloNegativo);
				} else if (dAtual == Direcao.L) {
					Motor.A.rotate(anguloNegativo, true);
					Motor.B.rotate(anguloPositivo);
				}
				
				frente();
				dAtual = d;
				break;
			case O:
				if (dAtual == Direcao.S) {
					Motor.A.rotate(anguloPositivo, true);
					Motor.B.rotate(anguloNegativo);
				} else if (dAtual == Direcao.N) {
					Motor.A.rotate(anguloNegativo, true);
					Motor.B.rotate(anguloPositivo);
				}

				frente();
				dAtual = d;
				break;
			case L:
				if (dAtual == Direcao.N) {
					Motor.A.rotate(anguloPositivo, true);
					Motor.B.rotate(anguloNegativo);
				} else if (dAtual == Direcao.S) {
					Motor.A.rotate(anguloNegativo, true);
					Motor.B.rotate(anguloPositivo);
				}

				frente();
				dAtual = d;
				break;
			case S:
				if (dAtual == Direcao.L) {
					Motor.A.rotate(anguloPositivo, true);
					Motor.B.rotate(anguloNegativo);
				} else if (dAtual == Direcao.O) {
					Motor.A.rotate(anguloNegativo, true);
					Motor.B.rotate(anguloPositivo);
				}
				
				frente();
				dAtual = d;
				break;
			default:
				break;
			}
		}
	}
	
	public static void frente() {
		int andar = 600;
		Motor.A.rotate(andar, true);
		Motor.B.rotate(andar);
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Wavefront w = new Wavefront(new LogicaQuatro());

		while (!Button.ENTER.isPressed());
		
		List<Direcao> movimentos = w.navegarCenario('A');
		executaComandos(movimentos);

		while (!Button.ENTER.isPressed());
		
		movimentos = w.navegarCenario('B');
		executaComandos(movimentos);
	}
}