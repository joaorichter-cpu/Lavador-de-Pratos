import java.util.logging.Logger;

public class Lavador implements Runnable {

	private Escorredor escorredor;
	private volatile boolean done = false;
	private static final long CONTAGEM_BAIXO = 30_000_000L;
	private static final long CONTAGEM_MEDIO = 50_000_000L;
	private static final long CONTAGEM_ENGORDURADO = 100_000_000L;
	private static final Logger LOGGER = Logger.getLogger(Lavador.class.getName());

	public Lavador(Escorredor escorredor) {
		this.escorredor = escorredor;
	}

	@Override
	public void run() {
		while (!done) {
			Prato prato = new Prato();
			long limite;
			if (prato.getNivelSujeira() == NivelSujeira.BAIXO) {
				limite = CONTAGEM_BAIXO;
			} else if (prato.getNivelSujeira() == NivelSujeira.MEDIO) {
				limite = CONTAGEM_MEDIO;
			} else {
				limite = CONTAGEM_ENGORDURADO;

			}
			for (long j = 0; j < limite; j++) {
			}

			escorredor.colocar(prato);
			LOGGER.fine("Lavador colocou prato " + prato.getNumeroSerie());
		}
	}

	public void parar() {
		done = true;
	}
}
