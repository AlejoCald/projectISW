import java.util.ArrayList;
import java.util.Arrays;

public class Restriccion{
	private ArrayList<Integer> primerMiembro = new ArrayList<Integer>();
	private String tipoDesigualdad;
	private int segundoMiembro;

	public ArrayList<Integer> getPrimerMiembro(){
		return primerMiembro;
	}

	public int getPrimerMiembro(int posicion){
		return primerMiembro.get(posicion);
	}

	public String getTipoDesigualdad(){
		return tipoDesigualdad;
	}

	public int getSegundoMiembro(){
		return segundoMiembro;
	}

	public void recolectarCoeficiente(String restriccionDada, int i, int flag, int posicion){
		int coeficiente_temp = 0;

		if(i == 0){
			primerMiembro.set(posicion,1);
		}
		else if((i-flag) == 1){
			if(restriccionDada.charAt(flag) == '-'){
				primerMiembro.set(posicion,-1);
			}
			else if(restriccionDada.charAt(flag) == '+'){
				primerMiembro.set(posicion,1);
			}
			else{
				coeficiente_temp = Integer.parseInt(restriccionDada.substring(flag, i));
				primerMiembro.set(posicion, coeficiente_temp);
			}
		}
		else{
			coeficiente_temp = Integer.parseInt(restriccionDada.substring(flag, i));
			primerMiembro.set(posicion, coeficiente_temp);
		}
	}

	public void recolectarDatos(int num_variables, String restriccionDada){
		int flag = 0;
		int i = 0;
		int pase = 0;
		int longitud = 0;
		longitud = restriccionDada.length();

		for(int j = 0; j < num_variables; j++){
			primerMiembro.add(i, 0);
		}

		while(i<longitud){
			//RECOLECTAR COEFICIENTES
			if(restriccionDada.charAt(i) == 'x'){
				recolectarCoeficiente(restriccionDada, i, flag, 0);
				flag = i + 1;
			}

			else if(restriccionDada.charAt(i) == 'y'){
				recolectarCoeficiente(restriccionDada, i, flag, 1);
				flag = i + 1;
			}

			else if(restriccionDada.charAt(i) == 'z'){
				recolectarCoeficiente(restriccionDada, i, flag, 2);
				flag = i + 1;
			}

			else if(restriccionDada.charAt(i) == 'w'){
				recolectarCoeficiente(restriccionDada, i, flag, 3);
				flag = i + 1;
			}

			//RECOLECTAR SIMBOLO DE DESIGUALDAD
			if(restriccionDada.charAt(i) == '<'){
				tipoDesigualdad = "<=";
				i++;
				flag = i + 1;
				pase = 1;
			}
			else if(restriccionDada.charAt(i) == '>'){
				tipoDesigualdad = ">=";
				i++;
				flag = i + 1;
				pase = 1;
			}
			else if(restriccionDada.charAt(i) == '='){
				tipoDesigualdad = "==";
				i++;
				flag = i + 1;
				pase = 1;
			}

			//RECOLECTAR VALOR DEL SEGUNDO MIEMBRO
			if(pase == 1 && i<longitud-1){
				segundoMiembro = Integer.parseInt(restriccionDada.substring(flag, longitud));
				i = longitud - 1;
			}

			i++;
		}

		/*System.out.println(Arrays.toString(primerMiembro.toArray()));
		System.out.println("Tipo de desigualdad: " + tipoDesigualdad);
		System.out.println("Valor: " + segundoMiembro);*/
	}

	public int validarDatos(ArrayList<Integer> primerMiembro, String tipoDesigualdad, int segundoMiembro){
		if(primerMiembro.isEmpty() || tipoDesigualdad == null){
			return 0;
		}
		else{
			return 1;
		}
	}

	/*public int validarRestriccion(ArrayList<Integer> primerMiembro, String tipoDesigualdad, int segundoMiembro, double limiteSuperior, double limiteInferior){
		double double aleatorio = random.nextDouble()*selectorDeLimite[j].getLimiteSuperior()+selectorDeLimite[j].getLimiteInferior();


	}*/
}