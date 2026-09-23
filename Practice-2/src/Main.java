public class BulbAdapter implements SmartDevice {
    // Инкапсулированный объект устройства
    private final LegacyBulb legacyDevice;

    // Математическая константа калибровки (последняя цифра ID)
    private final int K = 3;

    public BulbAdapter(LegacyBulb legacyDevice) {
        if (legacyDevice == null) {
            throw new IllegalArgumentException("Error: LegacyBulb reference cannot be null.");
        }
        this.legacyDevice = legacyDevice;
    }

    @Override
    public void turnOn() {
        legacyDevice.setBrightness(255);
    }

    @Override
    public void turnOff() {
        legacyDevice.setBrightness(0);
    }

    @Override
    public boolean isOn() {
        // Устройство считается включенным только при наличии питания и яркости больше нуля
        return legacyDevice.hasPower() && legacyDevice.readBrightness() > 0;
    }

    @Override
    public int getPowerPercent() {
        // Сценарий аппаратного сбоя: если нить порвана или яркость на нуле
        if (!legacyDevice.hasPower() || legacyDevice.readBrightness() == 0) {
            return 0;
        }

        // Расчет мощности по формуле: floor((raw * 100) / 255) + K
        double rawBrightness = legacyDevice.readBrightness();
        int basePercentage = (int) Math.floor((rawBrightness * 100.0) / 255.0);
        int finalPower = basePercentage + K;

        // Ограничение максимального значения до 100%
        return Math.min(100, finalPower);
    }
}
