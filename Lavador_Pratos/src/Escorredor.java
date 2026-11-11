import java.util.logging.Logger;

public class Escorredor {
	private Prato[] pratos;
	private int inicio;
	private int fim;
	private int quantidade;
	private boolean encerrado = false;
	private static final Logger LOGGER = Logger.getLogger(Escorredor.class.getName());

	public boolean estaCheio() {
		return quantidade == pratos.length;
	}

	public boolean estaVazio() {
		return quantidade == 0;
	}

	public Escorredor(int capacidade) {
		this.pratos = new Prato[capacidade];
		this.inicio = 0;
		this.fim = 0;
		this.quantidade = 0;

	}

	public synchronized void colocar(Prato prato) {
		while (estaCheio()) {
			notify();
			try {
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		pratos[fim] = prato;
		fim = (fim + 1) % pratos.length;
		quantidade++;

		if (estaCheio()) {
			LOGGER.info("Escorredor Cheio: " + quantidade + " pratos");

		}
		if (quantidade == 1) {
			notify();
		}
	}

	public synchronized Prato retirar() {
		while (estaVazio() && !encerrado) {
			notify();
			try {
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		if (estaVazio()) {
			notify();
			return null;
		}

		Prato prato = pratos[inicio];
		inicio = (inicio + 1) % pratos.length;
		quantidade--;

		if (estaVazio()) {
			LOGGER.info("Escorredor Vazio: " + quantidade + " pratos");

		}
		if (quantidade == pratos.length - 1) {
			notify();
		}
		return prato;
	}

	public synchronized void encerrar() {
		encerrado = true;
		notify();
	}
}
