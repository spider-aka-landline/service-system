package entities;

import messages.ProviderResponse;

public class TriggeredServiceProvider extends ServiceProvider {

    private Boolean isGoodTrigger;

    TriggeredServiceProvider() {
        changeTrigger();
    }

    @Override
    public ProviderResponse processUserTask(Task userTask) {
        ProviderResponse pR = new ProviderResponse(isGoodTrigger);
        changeTrigger();
        return pR;
    }

    private void changeTrigger() {
        isGoodTrigger = (Math.random()<0.5);
    }
}
