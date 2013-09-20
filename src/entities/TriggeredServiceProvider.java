package entities;

public class TriggeredServiceProvider extends ServiceProvider {

    private Boolean isGoodTrigger = true;
    Double quality_min_value = 0.01;

    TriggeredServiceProvider(Double q) {
        super(q);
        changeTrigger();
    }


    private void changeTrigger() {
        isGoodTrigger = (Math.random()<0.5);
    }
}
