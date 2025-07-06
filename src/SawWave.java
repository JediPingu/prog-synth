import java.util.function.DoubleFunction;

public class SawWave extends Voice {

    public SawWave(
        int offsetValue,
        double sampleRate,
        double volume,
        double attack,
        double decay,
        double sustain,
        double release
    ) {
        super(offsetValue, sampleRate, volume, attack, decay, sustain, release);
    }

    @Override
    protected byte[] waveform(double frequency, double length) {
        byte[] wave = new byte[(int) (length * this.getSampleRate())];
        double attackSlope = this.getSustain() / this.getAttack();
        double decaySlope;
        try{
            decaySlope = -this.getSustain()/this.getDecay();
        }
        catch (ArithmeticException e){
            decaySlope = 0;
        }

        for (int i = 0; i < wave.length; i++) {
            double time = i/this.getSampleRate();
            double amplitude;
            if (time  < this.getAttack()) {
                amplitude = attackSlope * time;
            }
            else {
                amplitude = this.getSustain() - (decaySlope * time);
            }
            if (amplitude <= 0.01){
                amplitude = 0.01;
            }
            wave[i] = (byte) (amplitude * 2.0 *
                (i / (2.0 * Math.PI) - Math.floor(0.5 + i / (2.0 * Math.PI))));
        }
        return wave;
    }
}
