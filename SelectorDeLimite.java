import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SelectorDeLimite{
	private ArrayList<Double> numerosTabulados = new ArrayList<Double>();
	private double limiteSuperior;
	private double limiteInferior = 0.0;

	public ArrayList<Double> getNumerosTabulados(){
		return numerosTabulados;
	}

	public double getLimiteSuperior(){
		return limiteSuperior;
	}

	public double getLimiteInferior(){
		return limiteInferior;
	}

	public void tabular(double coeficiente, int valor){
		if(coeficiente <= 0 || valor == 0){
			numerosTabulados.add((double)0);
		}
		else{
			numerosTabulados.add((double)valor/coeficiente);
		}
		//System.out.println(Arrays.toString(numerosTabulados.toArray()));
	}

	public void calcularLimiteSuperior(int i, int j, double coeficiente, String tipoDesigualdad, int valor){
		//Collections.sort(numerosTabulados, Collections.reverseOrder());

		//System.out.println(i + " - tab" + Arrays.toString(numerosTabulados.toArray()));
		//Collections.sort(numerosTabulados, Collections.reverseOrder());
		if(i == 0){
			limiteSuperior = numerosTabulados.get(0);
			//System.out.println("x" + limiteSuperior);
		}

		if(tipoDesigualdad == "<=" && coeficiente != 0){
			/*if(i == 0 && (coeficiente * limiteSuperior) >  (double)valor){
				limiteSuperior = numerosTabulados.get(1);
				System.out.println("a" + limiteSuperior);
			}*/
			if ((coeficiente * limiteSuperior) >  (double)valor){
				limiteSuperior = numerosTabulados.get(i);
				//System.out.println("b" + limiteSuperior + " x " + coeficiente);
			}
		}
		else if(tipoDesigualdad == ">=" && coeficiente != 0){
			/*if(i == 0 && (coeficiente * limiteSuperior) < (double)valor){
				limiteSuperior = numerosTabulados.get(1);
				System.out.println("c" + limiteSuperior + " x " + coeficiente);
			}*/
			if ((coeficiente * limiteSuperior) <  (double)valor){
				limiteSuperior = numerosTabulados.get(i);
				//System.out.println("d" + limiteSuperior + " x " + coeficiente);
			}
		}
		else if(tipoDesigualdad == "==" && coeficiente != 0){
			limiteSuperior = numerosTabulados.get(i);
			/*if(i == 0 && (coeficiente * limiteSuperior) != (double)valor){
				limiteSuperior = numerosTabulados.get(1);
				System.out.println("e-igual" + limiteSuperior + " x " + coeficiente);
			}
			else if ((coeficiente * limiteSuperior) !=  (double)valor){
				limiteSuperior = numerosTabulados.get(i);
				System.out.println("igual" + limiteSuperior + " x " + coeficiente);
			}*/
		}

		/*for(int i = 0; i < num_restricciones; i++){
			if(numerosTabulados.get(i) > superior){
				superior = numerosTabulados.get(i);
			}
		}*/
	}

	public void calcularLimiteInferior(int i, int num_restricciones, double coeficiente, String tipoDesigualdad){
		if(tipoDesigualdad == ">="){
			for(int k = 0; k < num_restricciones; k++){
				if(k == 0 && coeficiente != 0){
					limiteInferior = numerosTabulados.get(k);
				}
				else if(numerosTabulados.get(k) != 0 && numerosTabulados.get(k) < limiteInferior){
					limiteInferior = numerosTabulados.get(k);
				}
			}
		}
		else{
			if(coeficiente != 0){
				limiteInferior = numerosTabulados.get(i);
			}
		}

		//System.out.println("coef" + coeficiente + "inf  " + limiteInferior);
	}
}