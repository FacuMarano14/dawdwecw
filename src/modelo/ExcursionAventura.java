/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author facun
 */
public class ExcursionAventura extends Excursion {

    private int nivelDificultad;
    private double seguroAdicional;

    public ExcursionAventura(String titulo, double precioBase, int cupoMaximo, int nivelDificultad, double seguroAdicional) {
        super(titulo, precioBase, cupoMaximo);

        if (nivelDificultad < 1 || nivelDificultad > 5) {
            throw new IllegalArgumentException("El nivel de dificultad debe estar entre 1 y 5");
        }

        this.nivelDificultad = nivelDificultad;
        this.seguroAdicional = seguroAdicional;
    }

    @Override
    protected double calcularPrecioFinal() {
        return getPrecioBase() * (1 + 0.1 * nivelDificultad + seguroAdicional);
    }

    public String consejoSeguridad() {
        switch (nivelDificultad) {
            case 1:
                return "Apto para principiantes";
            case 2:
                return "Se requiere algo de experiencia";
            case 3:
                return "Requiere buena condicion fisica";
            case 4:
                return "Recomendada para expertos";
            case 5:
                return "Solo para profesionales";
            default:
                return "Sin consejos disponibles";
        }
    }

    public int getNivelDificultad() {
        return nivelDificultad;
    }

    public void setNivelDificultad(int nivelDificultad) {
        this.nivelDificultad = nivelDificultad;
    }

    public double getSeguroAdicional() {
        return seguroAdicional;
    }

    public void setSeguroAdicional(double seguroAdicional) {
        this.seguroAdicional = seguroAdicional;
    }

}
