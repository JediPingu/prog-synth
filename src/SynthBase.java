import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public abstract class SynthBase {

    public SynthBase() {}

    public static void playSound(double frequency, double length, double volume)
        throws LineUnavailableException {
        int sampleRate = 44100;
        byte[] buffer = new byte[(int) (length * sampleRate)];

        for (int i = 0; i < buffer.length; i++) {
            double angle =
                ((Math.PI * 2.0) * (double) i * frequency) / sampleRate;
            buffer[i] = (byte) ((int) (Math.sin(angle) * (double) 127.0F));
        }
        for (int i = 0; i < buffer.length - 1; i++) {
            double angle =
                ((Math.PI * 2.0) * (double) (i * frequency)) / sampleRate;
            buffer[i + 1] = (byte) ((int) (Math.sin(angle) * (double) 127.0F));
        }

        AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, false);
        SourceDataLine line = AudioSystem.getSourceDataLine(format);
        line.open(format);
        line.start();
        line.write(buffer, 0, buffer.length);
        line.drain();
        line.stop();
        line.close();
    }
}
