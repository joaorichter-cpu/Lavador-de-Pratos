import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

	private static final long TEMPO_EXECUCAO_MS = 5_000L; 

	private static void configurarLog(Level nivel) {
		Logger raiz = Logger.getLogger("");
		raiz.setLevel(nivel);
		for (Handler handler : raiz.getHandlers()) {
			handler.setLevel(nivel);
		}
	}

	private final Escorredor escorredor;
	private final int quantidadeLavadores;
	private final int quantidadeEnxugadores;
	private final List<Lavador> lavadores = new ArrayList<>();
	private final List<Thread> threadsLavadores = new ArrayList<>();
	private final List<Thread> threadsEnxugadores = new ArrayList<>();

	public App(int capacidade, int quantidadeLavadores, int quantidadeEnxugadores) {
		this.escorredor = new Escorredor(capacidade);
		this.quantidadeLavadores = quantidadeLavadores;
		this.quantidadeEnxugadores = quantidadeEnxugadores;
	}

	public void iniciar() {
		for (int i = 0; i < quantidadeLavadores; i++) {
			Lavador lavador = new Lavador(escorredor);
			lavadores.add(lavador);
			Thread thread = new Thread(lavador);
			threadsLavadores.add(thread);
			thread.start();
		}
		for (int i = 0; i < quantidadeEnxugadores; i++) {
			Thread thread = new Thread(new Enxugador(escorredor));
			threadsEnxugadores.add(thread);
			thread.start();
		}
	}

	public void parar() {
		for (Lavador lavador : lavadores) {
			lavador.parar();
		}
		esperar(threadsLavadores);
		escorredor.encerrar();
		esperar(threadsEnxugadores);
	}

	private void esperar(List<Thread> threads) {
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	public static void main(String[] args) {
		//	configurarLog(Level.FINE);
		configurarLog(Level.INFO);
		App app = new App(3, 2, 2);
		app.iniciar();

		try {
			Thread.sleep(TEMPO_EXECUCAO_MS);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		app.parar();
	}
}