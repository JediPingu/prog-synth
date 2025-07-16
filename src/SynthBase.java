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
    public void play(double frequency, double length)
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

    public static class MidiNotes{
        public static ArrayList<Double> notes = new ArrayList<Double>();
        public MidiNotes(){
            generateNotes();
        }
        void generateNotes(){
            double[] normalNoteFrequencies = {16.35, 17.32, 18.35, 19.45, 20.60, 21.83, 23.12, 24.50, 25.96, 27.50, 29.14, 30.87};
            for (int i = 0; i < 7; i++) {
                for (double n : normalNoteFrequencies){
                    notes.add((double) (n * Math.pow(2, i)));
                }
            }
        }
        double midiNoteToFrequency(int midiNote){
            return notes.get(midiNote);
        }
    }

}
