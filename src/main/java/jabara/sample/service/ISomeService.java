package jabara.sample.service;


import jabara.sample.service.impl.SomeServiceImpl;

import com.google.inject.ImplementedBy;

/**
 * @author jabaraster
 */
@ImplementedBy(SomeServiceImpl.class)
public interface ISomeService {

    /**
     * 
     */
    void run();

}