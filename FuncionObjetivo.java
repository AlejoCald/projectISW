import java.util.ArrayList;
import java.util.Arrays;

public class FuncionObjetivo{
	private ArrayList<Integer> coeficientes = new ArrayList<Integer>();

	public ArrayList<Integer> getCoeficientes(){
		return coeficientes;
	}

	public int getCoeficientes(int posicion){
		return coeficientes.get(posicion);
	}

	public void recolectarCoeficiente(String funcionObjetivo, int i, int flag, int posicion){
		int coeficiente_temp = 0;

		if(i == 0){
			coeficientes.set(posicion,1);
		}
		else if((i-flag) == 1){
			if(funcionObjetivo.charAt(flag) == '-'){
				coeficientes.set(posicion,-1);
			}
			else if(funcionObjetivo.charAt(flag) == '+'){
				coeficientes.set(posicion,1);
			}
			else{
				coeficiente_temp = Integer.parseInt(funcionObjetivo.substring(flag, i));
				coeficientes.set(posicion, coeficiente_temp);
			}
		}
		else{
			coeficiente_temp = Integer.parseInt(funcionObjetivo.substring(flag, i));
			coeficientes.set(posicion, coeficiente_temp);
		}
	}

	public void recolectarDatos(int num_variables, String funcionObjetivo){
		int flag = 0;
		int i = 0;
		int pase = 0;
		int longitud = 0;
		longitud = funcionObjetivo.length();

		for(int j = 0; j < num_variables; j++){
			coeficientes.add(i, 0);
		}

		while(i<longitud){
			if(funcionObjetivo.charAt(i) == 'x'){
				recolectarCoeficiente(funcionObjetivo, i, flag, 0);
				flag = i + 1;
			}

			else if(funcionObjetivo.charAt(i) == 'y'){
				recolectarCoeficiente(funcionObjetivo, i, flag, 1);
				flag = i + 1;
			}

			else if(funcionObjetivo.charAt(i) == 'z'){
				recolectarCoeficiente(funcionObjetivo, i, flag, 2);
				flag = i + 1;
			}

			else if(funcionObjetivo.charAt(i) == 'w'){
				recolectarCoeficiente(funcionObjetivo, i, flag, 3);
				flag = i + 1;
			}

			i++;
		}

		//System.out.println(Arrays.toString(coeficientes.toArray()));
	}
}