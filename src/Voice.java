public abstract class Voice {

    private int offset;
    private double sampleRate;
    private double amplitude;
    private double attack;
    private double decay;
    private double sustain;
    private double release;

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

    public void setAmplitude(double amplitude) {
        this.amplitude = amplitude;
    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public double getDecay() {
        return decay;
    }

    public void setDecay(double decay) {
        this.decay = decay;
    }

    public double getSustain() {
        return sustain;
    }

    public void setSustain(double sustain) {
        this.sustain = sustain;
    }

    public double getRelease() {
        return release;
    }

    public void setRelease(double release) {
        this.release = release;
    }

    protected abstract byte[] waveform(double frequency, double length);

    public Voice(
        int offsetValue,
        double sampleRate,
        double volume,
        double attack,
        double decay,
        double sustain,
        double release
    ) {
        this.offset = offsetValue;
        this.sampleRate = sampleRate;
        this.amplitude = volume;
        this.attack = attack;
        this.decay = decay;
        this.sustain = sustain;
        this.release = release;
    }
}
