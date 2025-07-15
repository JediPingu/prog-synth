import javax.sound.sampled.LineUnavailableException;

public class Main {

    static double sampleRate = 44100;

    public static void main(String[] args) throws LineUnavailableException {
        NoiseWave n = new NoiseWave(0, sampleRate, 0.5);
        n.setAttackDecaySustain(0.2, 0.2, 0.1);
        n.playWaveform(220, 2);
    }
}
