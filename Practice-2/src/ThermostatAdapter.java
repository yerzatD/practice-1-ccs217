public class ThermostatAdapter implements SmartDevice {
    private final LegacyThermostat thermostat;

    public ThermostatAdapter(LegacyThermostat thermostat) {
        if (thermostat == null) {
            throw new IllegalArgumentException("Thermostat cannot be null");
        }
        this.thermostat = thermostat;
    }

    @Override
    public void turnOn() {
        String currentState = thermostat.checkDial();
        if ("IDLE".equals(currentState)) {
            thermostat.rotateDial("LOW");
        }
    }

    @Override
    public void turnOff() {
        thermostat.rotateDial("IDLE");
    }

    @Override
    public boolean isOn() {
        String state = thermostat.checkDial();
        if (state == null) {
            return false;
        }
        return state.equals("LOW") || state.equals("MEDIUM") || state.equals("MAX");
    }

    @Override
    public int getPowerPercent() {
        String state = thermostat.checkDial();
        if (state == null) {
            return -1;
        }

        switch (state) {
            case "IDLE":
                return 0;
            case "LOW":
                return 33;
            case "MEDIUM":
                return 66;
            case "MAX":
                return 100;
            default:
                return -1;
        }
    }
}