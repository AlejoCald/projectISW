import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.text.DecimalFormat;
import java.util.Random;

public class ProbadorRestriccion{
	public static void main(String[] args) {
		Scanner leer = new Scanner(System.in);
		String entradaTeclado;
		Restriccion[] restriccion;
		SelectorDeLimite[] selectorDeLimite;
        FuncionObjetivo funcionObjetivo = new FuncionObjetivo();
        DecimalFormat df2 = new DecimalFormat("#.##");
        DecimalFormat df4 = new DecimalFormat("#.######");
        Random random = new Random();
                
		//Cromosoma[] cromosoma;		
		int num_variables, num_restricciones, iteraciones, validacion, iteracion_final = 0;
        double n, valor = 0, max_min;
        String op_iterar;
        double[] aleatorio;
        double[] delta;
        double[] variables_finales;

        do{
            validacion = 0;
            System.out.println("\nIntroduce el numero de variables:");
            System.out.print(" > ");
            num_variables = Integer.parseInt(leer.nextLine());

            System.out.print("\nIntroduce la Funcion Objetivo (Z):  ");
            entradaTeclado = leer.nextLine();
            funcionObjetivo.recolectarDatos(num_variables, entradaTeclado);

            System.out.println("\nIntroduce el total de restricciones:");
            System.out.print(" > ");
            num_restricciones = Integer.parseInt(leer.nextLine());
            
            restriccion = new Restriccion[num_restricciones];
            selectorDeLimite = new SelectorDeLimite[num_variables];
            aleatorio = new double[num_variables];
            delta = new double[num_variables];
            variables_finales = new double[num_variables];

            for(int i = 0; i < num_restricciones; i++){
                restriccion[i] = new Restriccion();
            }
            for(int j = 0; j < num_variables; j++){
                selectorDeLimite[j] = new SelectorDeLimite();
            }

            //GUARDA LOS COEFICIENTES DE LAS RESTRICCIONES
            for(int i = 0; i < num_restricciones; i++){
                System.out.print("\n   Restriccion " + (i+1) + ":  ");
                entradaTeclado = leer.nextLine();
                restriccion[i].recolectarDatos(num_variables, entradaTeclado);
                validacion += restriccion[i].validarDatos(restriccion[i].getPrimerMiembro(), restriccion[i].getTipoDesigualdad(), restriccion[i].getSegundoMiembro());
            }

            //VALIDA LAS RESTRICCIONES
            if(validacion != num_restricciones){
                System.out.println("\n\n\t\t > Verifica tus restricciones - " + (num_restricciones - validacion) + " incorrecta(s)");
            }
        }while(validacion != num_restricciones);


        //TABULA
        for(int i = 0; i < num_restricciones; i++){
            for(int j = 0; j < num_variables; j++){
                selectorDeLimite[j].tabular(restriccion[i].getPrimerMiembro(j), restriccion[i].getSegundoMiembro());
                //System.out.println("tabulados: " + i + "," +j + Arrays.toString((selectorDeLimite[j].getNumerosTabulados()).toArray()));
            }
        }

        //IDENTIFICA EL LIMITE SUPERIOR
        for(int i = 0; i < num_restricciones; i++){
            for(int j = 0; j < num_variables; j++){
                selectorDeLimite[j].calcularLimiteSuperior(i, j, restriccion[i].getPrimerMiembro(j), restriccion[i].getTipoDesigualdad(), restriccion[i].getSegundoMiembro());
                //System.out.println("sup: " + Arrays.toString((selectorDeLimite[j].getNumerosTabulados()).toArray()));
            }
        }

        //IDENTIFICA SI HAY LIMITE INFERIOR DIFERENTE DE 0
        for(int i = 0; i < num_restricciones; i++){
            if(restriccion[i].getTipoDesigualdad() == ">=" || restriccion[i].getTipoDesigualdad() == "=="){
                for(int j = 0; j < num_variables; j++){
                    selectorDeLimite[j].calcularLimiteInferior(i, num_restricciones, restriccion[i].getPrimerMiembro(j), restriccion[i].getTipoDesigualdad());
                    //System.out.println("inf: " + Arrays.toString((selectorDeLimite[j].getNumerosTabulados()).toArray()));
                }
            }
        }

        do{
            iteracion_final = 0;
            n = 0;
            max_min = 0;

            System.out.println("\nDetermina el valor de n:");
            System.out.print(" > ");
            n = Double.parseDouble(leer.nextLine());
            
            System.out.println("\nIntroduce el total de iteraciones:");
            System.out.print(" > ");
            iteraciones = Integer.parseInt(leer.nextLine());
            System.out.println();

            for(int i = 0; i < iteraciones; i++){
                validacion = 0;
                double temp_max_min = 0.0;
                int negativo = 0, plus = 0;

                System.out.print((i+1) + ". ");
                            
                //restricciones
                for(int j = 0; j < num_restricciones; j++){
                    valor = 0;

                    //CREA ALEATORIO INICIAL
                    if(i == 0 && j == 0){
                        for(int k = 0; k < num_variables; k++){
                            //System.out.println("inf: " + selectorDeLimite[k].getLimiteInferior() + "sup: " + selectorDeLimite[k].getLimiteSuperior());
                            aleatorio[k] = ((random.nextInt((int)((selectorDeLimite[k].getLimiteSuperior()-selectorDeLimite[k].getLimiteInferior())*100+1))+selectorDeLimite[k].getLimiteInferior()*100)/100.0);
                            //aleatorio[k] = selectorDeLimite[k].getLimiteInferior() + (selectorDeLimite[k].getLimiteSuperior() - selectorDeLimite[k].getLimiteInferior()) * random.nextDouble();
                            //System.out.println("aleat: " + aleatorio[k]);
                        }
                    }

                    //ASIGNA UNICAMENTE EN LA PRIMER ITERACION DE LAS VARIABLES
                    if(j == 0){
                        for(int k = 0; k < num_variables; k++){
                            aleatorio[k] = Math.round((aleatorio[k] + delta[k])*100.0)/100.0;
                            if(aleatorio[k] <= 0){
                                negativo++;
                            }
                        }
                    }

                    //IMPRIME LOS ALEATORIOS
                    for(int k = 0; k < num_variables; k++){
                        if(j == 0){
                            System.out.print("\t" + aleatorio[k]);
                            temp_max_min += funcionObjetivo.getCoeficientes(k) * aleatorio[k];
                        }
                        valor += restriccion[j].getPrimerMiembro(k) * aleatorio[k];
                    }

                    if(restriccion[j].getTipoDesigualdad() == "<="){
                        if(valor <= (double)restriccion[j].getSegundoMiembro()){
                            if(j == 0){
                                System.out.print("\tr" + (j+1));
                            }
                            else{
                                System.out.print("  r" + (j+1));
                            }
                            validacion++;
                        }
                        else{
                            if(j == 0){
                                System.out.print("\t--");
                            }
                            else{
                                System.out.print("  --");
                            }
                        }
                    }

                    else if(restriccion[j].getTipoDesigualdad() == ">="){
                        if(valor >= (double)restriccion[j].getSegundoMiembro()){
                            if(j == 0){
                                System.out.print("\tr" + (j+1));
                            }
                            else{
                                System.out.print("  r" + (j+1));
                            }
                            validacion++;
                        }
                        else{
                            if(j == 0){
                                System.out.print("\t--");
                            }
                            else{
                                System.out.print("  --");
                            }
                        }
                    }

                    else if(restriccion[j].getTipoDesigualdad() == "=="){
                        if(valor == (double)restriccion[j].getSegundoMiembro()){
                            if(j == 0){
                                System.out.print("\tr" + (j+1));
                            }
                            else{
                                System.out.print("  r" + (j+1));
                            }
                            validacion++;
                        }
                        else{
                            if(j == 0){
                                System.out.print("\t--");
                            }
                            else{
                                System.out.print("  --");
                            }
                            if(valor > ((double)restriccion[j].getSegundoMiembro() - 10) && valor < (double)restriccion[j].getSegundoMiembro()){
                                plus=1;
                            }
                            else if(valor > (double)restriccion[j].getSegundoMiembro() && valor < ((double)restriccion[j].getSegundoMiembro() + 10)){
                                plus=2;
                            }
                        }
                    }
                }

                if(negativo >= 1){
                    System.out.print("  ----");
                    System.out.print("  ----");
                    for(int k = 0; k < num_variables; k++){
                        //ORIGINAL (r.nextInt((int)((max-min)*10+1))+min*10) / 10.0;
                        delta[k] = ((random.nextInt((int)((50-0)*10+1))+0*10)/100.0) * n * 2;
                        System.out.print("\td" + delta[k]);
                    }
                    /*delta[out] = ((random.nextInt((int)((10-0)*10+1))+0*10)/100.0) * n;
                    System.out.print("\td" + delta[out]);*/
                }

                else if(plus == 1){
                    System.out.print("  ~neg");
                    System.out.print("  ----");
                    for(int k = 0; k < num_variables; k++){
                        delta[k] = ((random.nextInt((int)((2-0)*10+1))+0*10)/100.0) * n;
                        //delta[k] = 0.0 + (1.00-0.00)*((random.nextDouble()) * n);
                        System.out.print("\td" + delta[k]);
                    }
                }

                else if(plus == 2){
                    System.out.print("  ~neg");
                    System.out.print("  ----");
                    for(int k = 0; k < num_variables; k++){
                        delta[k] = (-1) * (((random.nextInt((int)((2-0)*10+1))+0*10)/100.0) * n);
                        //delta[k] = (-1) * ((0.00 + random.nextDouble() * (1.00 - 0.00)));
                        //delta[k] = 0.0 + (0.0001-0.0)*(((-1)*random.nextDouble()) * n);
                        System.out.print("\td" + delta[k]);
                    }
                }

                else if(validacion == num_restricciones){
                    System.out.print("  ~neg");
                    System.out.print("  " + df2.format(temp_max_min));
                    //ASIGNA LOS MAXIMOS
                    if(temp_max_min > max_min){
                        iteracion_final = i;
                        max_min = temp_max_min;
                        for(int k = 0; k < num_variables; k++){
                            variables_finales[k] = aleatorio[k];
                        }
                    }

                    //INCREMENTA LAS DELTA
                    for(int k = 0; k < num_variables; k++){
                        delta[k] = ((random.nextInt((int)((7-0)*10+1))+0*10)/100.0) * n;
                        //delta[k] = (0.00 + random.nextDouble() * (1.00 - 0.00));
                        //delta[k] = 0.0 + (1-0.0)*((random.nextDouble()) * n);
                        System.out.print("\td" + delta[k]);
                    }
                }                                

                else{
                    System.out.print("  ~neg");
                    System.out.print("  ----");
                    for(int k = 0; k < num_variables; k++){
                        delta[k] = (-1) * (((random.nextInt((int)((9-0)*10+1))+0*10)/100.0) * n);
                        //delta[k] = (-1) * ((0.00 + random.nextDouble() * (1.00 - 0.00)));
                        //delta[k] = 0.0 + (1-0.0)*(((-1)*random.nextDouble()) * n);
                        System.out.print("\td" + delta[k]);
                    }
                }

                System.out.println();
            }
                        
            System.out.print("\n > " + (iteracion_final+1));
            System.out.print("\tMAX Z = " + df2.format(max_min));
            System.out.print("\n\tVARIABLES = " + Arrays.toString(variables_finales));
            System.out.println("\n\nDeseas iterar de nuevo? (s/n)");
            System.out.print(" > ");
            op_iterar = leer.nextLine();
            
            Arrays.fill(aleatorio, 0);
            Arrays.fill(delta, 0);
            Arrays.fill(variables_finales, 0);
                        
            System.out.println("\n-------------------------------------");
        }while(op_iterar.charAt(0) == 's' || op_iterar.charAt(0) == 'S' || op_iterar.charAt(0) == 'y' || op_iterar.charAt(0) == 'Y');
	}
}