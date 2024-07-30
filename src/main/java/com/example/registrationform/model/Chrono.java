package com.example.registrationform.model;

public class Chrono {
    private long startTime;
    private long stopTime;
    private boolean running;

    public void iniciar() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }

    public void detener() {
        this.stopTime = System.currentTimeMillis();
        this.running = false;
    }

    public void reiniciar() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }

    public long obtenerTiempoTranscurrido() {
        long tiempoTranscurrido;
        if (running) {
            tiempoTranscurrido = (System.currentTimeMillis() - startTime);
        } else {
            tiempoTranscurrido = (stopTime - startTime);
        }
        return tiempoTranscurrido/1000;
    }
}
