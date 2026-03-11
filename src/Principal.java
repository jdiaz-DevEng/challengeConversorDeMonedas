import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner lectura = new Scanner(System.in);
        // Instanciamos la clase que hace la conexión a la API
        ConsultaMoneda consulta = new ConsultaMoneda();
        int opcion = 0;

        System.out.println("***************************************************");
        System.out.println("¡Bienvenido al Sistema de Conversión de Monedas!");
        System.out.println("***************************************************");

        // El bucle principal de la aplicación
        while (opcion != 7) {
            System.out.println("\nElija una opción válida:");
            System.out.println("1) Dólar (USD) => Peso Argentino (ARS)");
            System.out.println("2) Peso Argentino (ARS) => Dólar (USD)");
            System.out.println("3) Dólar (USD) => Real Brasileño (BRL)");
            System.out.println("4) Real Brasileño (BRL) => Dólar (USD)");
            System.out.println("5) Dólar (USD) => Peso Colombiano (COP)");
            System.out.println("6) Peso Colombiano (COP) => Dólar (USD)");
            System.out.println("7) Salir");
            System.out.print("Elija una opción válida: ");

            try {
                opcion = lectura.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Error: Formato inválido. Por favor, ingrese un número del 1 al 7.");
                // Esta línea es VITAL: limpia el "enter" o la letra que quedó atascada en el Scanner
                lectura.nextLine();
                continue; // Obliga al bucle while a volver a empezar desde arriba
            }

            if (opcion == 7) {
                System.out.println("Finalizando programa. ¡Operación exitosa!");
                break;
            }

            // Variables para guiar nuestra lógica
            String monedaBase = "";
            String monedaObjetivo = "";

            // Asignamos las monedas según la elección del usuario
            switch (opcion) {
                case 1:
                    monedaBase = "USD";
                    monedaObjetivo = "ARS";
                    break;
                case 2:
                    monedaBase = "ARS";
                    monedaObjetivo = "USD";
                    break;
                case 3:
                    monedaBase = "USD";
                    monedaObjetivo = "BRL";
                    break;
                case 4:
                    monedaBase = "BRL";
                    monedaObjetivo = "USD";
                    break;
                case 5:
                    monedaBase = "USD";
                    monedaObjetivo = "COP";
                    break;
                case 6:
                    monedaBase = "COP";
                    monedaObjetivo = "USD";
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    continue; // Salta el resto del código y vuelve al inicio del while
            }

            System.out.print("Ingrese el valor que deseas convertir: ");
            double cantidad = 0; // Declaramos la variable afuera para que el resto del código pueda verla

            try {
                cantidad = lectura.nextDouble();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Error: Formato de número inválido. Asegúrese de ingresar solo números (ej: 1500 o 1500,50).");
                // Limpiamos el texto erróneo que quedó atascado en el tubo del Scanner
                lectura.nextLine();
                System.out.println("Operación cancelada. Volviendo al menú principal...\n");
                continue; // Cancelamos esta vuelta y regresamos al inicio del menú
            }

            try {
                // 1. Llamamos a nuestra API
                MonedaOmdb monedas = consulta.buscarMoneda(monedaBase);

                // 2. Extraemos la tasa de cambio específica del Map usando la llave (ej: "ARS")
                Double tasaDeCambio = monedas.conversion_rates().get(monedaObjetivo);

                // 3. Verificamos que la moneda exista y hacemos la matemática
                if (tasaDeCambio != null) {
                    double resultado = cantidad * tasaDeCambio;
                    // Usamos printf para un formato limpio y profesional
                    System.out.printf("El valor %.2f [%s] corresponde al valor final de => %.2f [%s]\n",
                            cantidad, monedaBase, resultado, monedaObjetivo);
                } else {
                    System.out.println("Lo siento, no se encontró la tasa de cambio para esa moneda.");
                }

            } catch (Exception e) {
                System.out.println("Error crítico al calcular: " + e.getMessage());
            }
        }

        // ¡Nunca olvidamos cerrar nuestros recursos!
        lectura.close();
    }
}