package src.utils;
public class Cronometro {
    private long tempoInicio;
    
    public void iniciar() {
        tempoInicio = System.nanoTime();
    }
    
    public long obterTempoDecorrido() {
        return System.nanoTime() - tempoInicio;
    }
    
    public double obterTempoEmMilissegundos() {
        return obterTempoDecorrido() / 1_000_000.0;
    }
}