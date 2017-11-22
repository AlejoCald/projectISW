public class Cromosoma{
	private int numeroBits;

	public int getNumeroBits(){
		return numeroBits;
	}

	public void calcularNumeroBits(double limiteInferior, double limiteSuperior, int n){
		double numerador, denominador;

		System.out.println("\tsup: " + limiteSuperior + " inf: " + limiteInferior);
		numerador = Math.log10((limiteSuperior - limiteInferior)*((double)Math.pow(10,n)));
		System.out.println("\tnumerador: " + numerador);
		denominador = Math.log10(2);
		System.out.println("\tdenominador: " + denominador);
		numeroBits = (int)(numerador / denominador) + 1;
	}
}