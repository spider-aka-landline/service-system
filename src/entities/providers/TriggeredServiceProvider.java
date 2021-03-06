package entities.providers;

import entities.ID;
import entities.Params;
import entities.Task;
import messages.ProviderResponse;

public class TriggeredServiceProvider extends ServiceProvider {

    private Boolean isGood;
    static final Double QUALITY_MIN_VALUE = 0.01; //почему она здесь?

    TriggeredServiceProvider(ID id, Params pr, Boolean good) {
        super(id, pr);
        isGood = good;
    }

    private void changeTrigger() {
        isGood = (Math.random() < 0.5);
    }
    
    @Override
    public ProviderResponse processUserTask(Task t) {
        Params temp = (isGood) ? properties : new Params(QUALITY_MIN_VALUE);
        changeTrigger();
        return new ProviderResponse(id, t, temp);
    }
    
}
