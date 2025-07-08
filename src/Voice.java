import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public abstract class Voice {

    private int offset;
    private double sampleRate;
    private double amplitude;

    public double getPeakAmplitude() {
        return peakAmplitude;
    }

    private double peakAmplitude;
    private double attack;
    private double decay;
    private double sustain;


    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public double getSampleRate() {
        return sampleRate;
    }

    public double getAmplitude() {
        return amplitude;
    }

    public double getAttack() {
        return attack;
    }


    public double getDecay() {
        return decay;
    }


    public double getSustain() {
        return sustain;
    }




    protected abstract byte[] waveform(double frequency, double length);

    public Voice(
        int offsetValue,
        double sampleRate,
        double volume,
        double peakVolume,
        double attack,
        double decay,
        double sustain
    ) {
        this.offset = offsetValue;
        this.peakAmplitude = peakVolume;
        this.sampleRate = sampleRate;
        this.amplitude = volume;
        this.attack = attack;
        this.decay = decay;
        this.sustain = sustain;
    }

    public void playWaveform(double frequency, double length) throws LineUnavailableException {
        byte[] wave = waveform(frequency, length);
        AudioFormat format = new AudioFormat((float) getSampleRate(), 8, 1, true, false);
        SourceDataLine line = AudioSystem.getSourceDataLine(format);
        line.open(format, wave.length);
        line.start();
        line.write(wave, 0,  wave.length);
        line.drain();
        line.stop();
        line.close();
    }

}
