public class BulbAdapter implements SmartDevice {
    private final LegacyBulb bulb;
    private final int k = 7;

    public BulbAdapter(LegacyBulb b) {
        if (b == null) {
            throw new IllegalArgumentException("Bulb cannot be null");
        }
        bulb = b;
    }

    @Override
    public void turnOn() {
        bulb.setBrightness(255);
    }

    @Override
    public void turnOff() {
        bulb.setBrightness(0);
    }

    @Override
    public boolean isOn() {
        if (!bulb.hasPower()) {
            return false;
        }
        return bulb.readBrightness() > 0;
    }

    @Override
    public int getPowerPercent() {
        if (!bulb.hasPower()) {
            return 0;
        }

        int rawBrightness = bulb.readBrightness();
        if (rawBrightness == 0) {
            return 0;
        }

        int rawPercent = (rawBrightness * 100) / 255;
        int calibratedPercent = rawPercent + k;

        return Math.min(100, calibratedPercent);
    }
}