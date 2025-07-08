import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.util.ArrayList;

public class SynthBase {
    private Voice[] synthVoices;
    public SynthBase(Voice[] synthVoices) {
        this.synthVoices = synthVoices;
    }

    public void createFullSample(double frequency, double length)
        throws LineUnavailableException {
        int total_length = (int) (synthVoices[0].getSampleRate() * length);
        byte[] finalSample = new byte[total_length];
        ArrayList<byte[]> waveforms = new ArrayList<>();
        for (Voice v : synthVoices)
            waveforms.add(v.waveform(frequency, length));

        for (int i = 0; i < total_length; i++){
            int ith_sample = 0;
            for (byte[] wave : waveforms){
                ith_sample += wave[i];
            }
            finalSample[i] = (byte) ith_sample;
        }
        AudioFormat format = new AudioFormat((float)synthVoices[0].getSampleRate(), 8, 1, true, false);
        SourceDataLine line = AudioSystem.getSourceDataLine(format);
        line.open(format, finalSample.length);
        line.start();
        line.write(finalSample, 0,  finalSample.length);
        line.drain();
        line.stop();
        line.close();
    }
}
