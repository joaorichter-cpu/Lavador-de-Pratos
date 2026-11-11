import java.util.Random;
import java.util.logging.Logger;

public class Enxugador implements Runnable {

	private Escorredor escorredor;
	private static final Random RANDOM = new Random();
	private static final long PASSO = 10_000_000L;
	private static final Logger LOGGER = Logger.getLogger(Enxugador.class.getName());

	public Enxugador(Escorredor escorredor) {
		this.escorredor = escorredor;
	}

	public void run() {
		Prato prato = escorredor.retirar();
		while (prato != null) {
			long limite = (RANDOM.nextInt(8) + 3) * PASSO;
			for (long j = 0; j < limite; j++) {
			}

			LOGGER.fine("Enxugador enxugou prato " + prato.getNumeroSerie());
			prato = escorredor.retirar();
		}
	}
}