import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Main {

    public static void main(String[] args) throws LineUnavailableException {

        SineWave wave1 = new SineWave(0, 44100, 0.2, 1, 1, 1, 0.3);
        SawWave wave2 = new SawWave(0, 44100, 0.2, 1, 1, 1, 0.3);
        wave2.playWaveform(220,2);
        Voice[] voices = {wave1, wave2};
        SynthBase base = new SynthBase(voices);
        //base.createFullSample(261, 2.0);

        // Setup audio format: mono, 8-bit, signed

    }
}
