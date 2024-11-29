package test;

import java.util.Optional;
import negocio.GestorExcursiones;
import modelo.ExcursionAventura;
import modelo.Excursion;
import negocio.GestorReservas;
import modelo.Reserva;

public class Main {

    public static void main(String[] args) {
        System.out.println("Iniciando pruebas...");

        //Prueba de creacion y gestion de excursiones
        pruebaDeCreacionYGestiondeExcursiones();

        //Prueba de Reservas
        pruebaReservas();

        //Pruebas de métodos con predicados
        pruebaMetodosConPredicados();

        //Prueba de calculo de ingresos
        pruebaCalculoIngresos();

    }

    private static void pruebaDeCreacionYGestiondeExcursiones() {
        GestorExcursiones gestorExcursiones = new GestorExcursiones();
        System.out.println("\nTest 1: Creacion y Gestion de Excursiones");

        //Test 1.1 Crear y agregar una excursion valida
        try {
            ExcursionAventura rafting = new ExcursionAventura("Rafting en Rio Claro", 1500.0, 10, 3, 200.0);
            gestorExcursiones.agregarExcursion(rafting);
            System.out.println("Excursión de tipo Aventura agregada exitosamente con ID" + rafting.getId());
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }

        //Test 1.2 Intentar crear una excursion con un cupo negativo
        try {
            ExcursionAventura excursionInvalida = new ExcursionAventura("Excursión en la Montaña", 800.0, -5, 2, 100.0);
            gestorExcursiones.agregarExcursion(excursionInvalida);
        } catch (Exception e) {
            System.out.println("Excepcion lanzada" + e.getMessage());
        }

        //Test 1.3 Intentar crear una excursion sin un titulo
        try {
            ExcursionAventura excursionSinTitulo = new ExcursionAventura("", 1000.0, 15, 2, 50.0);
            gestorExcursiones.agregarExcursion(excursionSinTitulo);
        } catch  (Exception e){
            System.out.println("Excepcion lanzada" + e.getMessage());
        }
        
        //Test 1.4 Intentar crear una excursion con titulo menor a 3 caracteres
        try{
            ExcursionAventura excursionTituloCorto = new ExcursionAventura("Ra", 800.0, 10, 1, 20.0);
            gestorExcursiones.agregarExcursion(excursionTituloCorto);
        } catch  (Exception e){
            System.out.println("Excepcion lanzada" + e.getMessage());
        }
        
        //Test 1.5 Calcular el precio final de una excursion de aventura
        Optional<Excursion> rafting = gestorExcursiones.buscarPorId(1);
        rafting.ifPresent(excursion -> {
            double precioFinal = excursion.calcularPrecioFinal();
            System.out.println("Precio Final: " + precioFinal);
        });
        
        //Test 1.6 Agregar actividades a una excursion
        rafting.ifPresent(excursion -> {
            excursion.agregarActividad("Navegar el río");
            excursion.agregarActividad("Instrucción sobre seguridad");
            System.out.println("Actividades agregadas correctamente: " + excursion.getActividades());
        });
        
        

    }
    
    private static void pruebaReservas(){
        GestorExcursiones gestorExcursiones = new GestorExcursiones();
        GestorReservas gestorReservas = new GestorReservas(10);
        System.out.println("\nTest 2: Reservas");
        
        // Test 2.1: Realizar una reserva válida
        Optional<Excursion> rafting = gestorExcursiones.buscarPorId(1);
        rafting.ifPresent(excursion -> {
            boolean reservaExitosa = excursion.realizarReserva("Carlos Pérez");
            if (reservaExitosa) {
                System.out.println("Reserva realizada con éxito para el cliente Carlos Pérez.");
            }
        });
        
        // Test 2.2: Intentar realizar una reserva sin cupos disponibles
        for (int i = 0; i < 10; i++) {
            rafting.ifPresent(excursion -> excursion.realizarReserva("Cliente " + (i + 1)));
        }
        rafting.ifPresent(excursion -> {
            boolean reservaFallida = excursion.realizarReserva("Ana García");
            if (!reservaFallida) {
                System.out.println("No hay cupos disponibles para la excursión.");
            }
        });
        
        // Test 2.3: Confirmar pago de una reserva
        Optional<Reserva> reserva = gestorReservas.getReservas().stream().findFirst();
        reserva.ifPresent(r -> {
            r.confirmarPago();
            System.out.println("Pago confirmado para la reserva de " + r.getCliente() + ".");
        });

        // Test 2.4: Consultar el detalle de una reserva
        reserva.ifPresent(r -> System.out.println(r.detalleReserva()));
    }
    
    private static void pruebaMetodosConPredicados(){
        GestorExcursiones gestorExcursiones = new GestorExcursiones();
        System.out.println("\n Test 3: Metodos con Predicados");
        
        //Test 3.1 Filtrar excursiones por precio minimo
        System.out.println("Excursiones con precio mayor o igual a 1200.0");
        gestorExcursiones.consultarExcursionesPorPrecio(1200.0).forEach(excursion ->
            System.out.println(excursion.mostrarResumen())
        );
        
        //Test 3.2 Filtrar excursiones por nivel de dificultad
        System.out.println("Excursiones con nivel de dificultad 3:");
        gestorExcursiones.filtrarExcursiones(excursion -> {
            if (excursion instanceof ExcursionAventura) {
                return ((ExcursionAventura) excursion).getNivelDificultad() == 3;
            }
            return false;
        }).forEach(excursion -> System.out.println(excursion.mostrarResumen()));
    }
    
    private static void pruebaCalculoIngresos(){
        GestorReservas gestorReservas = new GestorReservas(10);
        System.out.println("\n Test 4: Calculo de Ingresos");
        
        //Test 4.1 Calcular ingresos totales
        double ingresosTotales = gestorReservas.calcularIngresosTotales();
        System.out.println("Ingresos totales: " + ingresosTotales);
        
        //Test 4.2 Calcular ingresos filtrados
        double ingresosFiltrados = gestorReservas.calcularIngresos(Reserva::isPagoConfirmado);
        System.out.println("Ingresos filtrados: " + ingresosFiltrados);
    }
    
    
    
    
    
}
