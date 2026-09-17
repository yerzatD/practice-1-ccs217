package Practice1;

public class LegacyPaymentAdapter implements PaymentProcessor {
    private final LegacyPaymentGateway legacyGateway;

    public LegacyPaymentAdapter(LegacyPaymentGateway legacyGateway) {
        this.legacyGateway = legacyGateway;
    }

    @Override
    public boolean pay(double amount) {
        int to_cent = (int) Math.round(amount * 100);
        String s = legacyGateway.makePayment(to_cent);

        return "SUCCESS".equals(s);
    }
}