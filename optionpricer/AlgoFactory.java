/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package optionpricer;

import algorithm.model.Algorithm;

/**
 *
 * @author Tianyang Feng`
 */
public class AlgoFactory {
 
    public AlgoFactory(){        
    }
    
    public Algorithm createAlgorithm(String algo){
        if (algo.equalsIgnoreCase("Bionomial Tree")){
            return new BionomialTree();
        }else if (algo.equalsIgnoreCase("Simulation")){
            return new Simulation();
        }        
        return null;
    }
    
}
