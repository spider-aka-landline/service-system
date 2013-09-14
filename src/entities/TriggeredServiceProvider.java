package entities;

import messages.ProviderResponse;

public class TriggeredServiceProvider extends ServiceProvider {

    private Boolean isGoodTrigger = true;
    Double quality_min_value = 0.01;

    TriggeredServiceProvider(Double q) {
        super(q);
        changeTrigger();
    }

    @Override
    public ProviderResponse processUserTask(Task userTask) {
        Params p = new Params((isGoodTrigger)? ServiceQuality: quality_min_value);
        ProviderResponse pR = new ProviderResponse(p);
        changeTrigger();
        return pR;
    }

    private void changeTrigger() {
        isGoodTrigger = (Math.random()<0.5);
    }
}
