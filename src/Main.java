import javax.sound.sampled.LineUnavailableException;
import javax.sound.midi.*;

public class Main {
    static double sampleRate = 44100;
    static SawWave sawWave = new SawWave(0, sampleRate, 0.8);
    static SineWave sineWave = new SineWave(-2, sampleRate, 1.0);
    static NoiseWave noise = new NoiseWave(0, sampleRate, 0.2);
    static Voice[] voices = {sawWave, sineWave, noise};
    static SynthBase myVoice = new SynthBase(voices);
    static SynthBase.MidiNotes notes = new SynthBase.MidiNotes();


    public static void main(String[] args) throws MidiUnavailableException{
        MidiDevice.Info[] deviceInfo = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info i : deviceInfo ) {
            System.out.println(i.getName());
        }
        MidiDevice device;
        device = MidiSystem.getMidiDevice(deviceInfo[3]);
        device.open();
        Transmitter transmitter = device.getTransmitter();
        transmitter.setReceiver(new MidiReceiver());

        sawWave.setAttackDecaySustain(0.2, 0.5, 0.56);
        sineWave.setAttackDecaySustain(0.2, 0.5, 0.7);
        noise.setAttackDecaySustain(0.2, 0.5, 0.2);
    }

    static class MidiReceiver implements Receiver{
        @Override
        public void send(MidiMessage message, long timeStamp){
            if (message instanceof ShortMessage) {
                ShortMessage sm = (ShortMessage) message;
                int command = sm.getCommand();
                int note = sm.getData1();
                int velocity = sm.getData2();

                if (command == ShortMessage.NOTE_ON && velocity > 0) {
                    // Note pressed
                    try{
                        myVoice.play(notes.midiNoteToFrequency(note), 1.5);
                    } catch (LineUnavailableException e) {
                        throw new RuntimeException(e);
                    }

                }
                else if (command == ShortMessage.NOTE_OFF ||
                        (command == ShortMessage.NOTE_ON && velocity == 0)) {
                        System.out.println("Note OFF: " +  note);
                }
                else {
                    System.out.println("Note OFF: " + note + " (no matching ON)");
                }
                }
            }
            @Override
            public void close() {}
        }
    }

