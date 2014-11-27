/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package strategies.newpackage.criterae;

import entities.providers.ServiceProvider;
import java.util.Map;
import reputationsystem.DataEntity;

/**
 *
 * @author Spider
 */
public interface Criteria {
    
   ServiceProvider chooseProvider(Map<ServiceProvider, DataEntity> searchSet);
}
