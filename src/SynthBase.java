import javax.sound.sampled.*;
import java.util.ArrayList;

public class SynthBase {
    private Voice[] synthVoices;

    public SynthBase(Voice[] synthVoices) {
        this.synthVoices = synthVoices;
    }

    private void playSample(byte[] sample) throws LineUnavailableException {
        AudioFormat format = new AudioFormat((float)synthVoices[0].getSampleRate(), 8, 1, true, false);
        SourceDataLine line = AudioSystem.getSourceDataLine(format);
        line.open(format, sample.length);
        line.start();
        line.write(sample, 0,  sample.length);
        line.drain();
        line.stop();
        line.close();
    }
    public void createFullSample(double frequency, double length)
        throws LineUnavailableException {
        ArrayList<byte[]> waveforms = new ArrayList<>();
        for (Voice v : synthVoices){
            AudioPlayerAndMixer p = new AudioPlayerAndMixer(v.waveform(frequency, length));
            new Thread(p).start();
        }
    }
    private class AudioPlayerAndMixer implements Runnable{
        private byte[] sample;
        public AudioPlayerAndMixer(byte[] sample){
            this.sample = sample;
        }
        public void run() {
            try{
                playSample(sample);
            } catch (LineUnavailableException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
