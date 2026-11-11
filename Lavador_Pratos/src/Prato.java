import java.util.Random;

public class Prato {

	private static int proximoNumero = 1;
	private static final Random RANDOM = new Random();
	private int numeroSerie;
	private NivelSujeira nivelSujeira;

	public Prato() {
		synchronized (Prato.class) {
			this.numeroSerie = proximoNumero++;
		}
		int sorteio = RANDOM.nextInt(100);
		;

		if (sorteio <= 29) {
			this.nivelSujeira = NivelSujeira.BAIXO;
		} else if (sorteio <= 89) {
			this.nivelSujeira = NivelSujeira.MEDIO;
		} else {
			this.nivelSujeira = NivelSujeira.ENGORDURADO;
		}
	}

	public int getNumeroSerie() {
		return numeroSerie;
	}

	public NivelSujeira getNivelSujeira() {
		return nivelSujeira;
	}
}