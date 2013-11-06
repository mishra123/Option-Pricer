/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm.model;

import option.Option;

/**
 *
 * @author Tianyang Feng`
 */
public interface Algorithm {
    
    public static final String ALGORITHM_CLASS_PATH = "ALGORITHM-CLASS-PATH";
    
    public double optionPrice(Option option);
    
    String getName();
    
}
