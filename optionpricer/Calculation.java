/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package optionpricer;

import option.Option;
import algorithm.model.Algorithm;

/**
 *
 * @author Tianyang Feng
 */
public class Calculation {
    private Option myOption;
    private double myOptionPrice;
    private Algorithm selectedAlgo;
    
    public Calculation(){        
    }
    
    public double calculatePrice(Option option, Algorithm algo){
       myOption = option;
       selectedAlgo = algo;
    System.out.println(myOption.getStrikePrice());
    System.out.println(selectedAlgo.toString());
       myOptionPrice = selectedAlgo.optionPrice(myOption);
       return myOptionPrice;
    }        
}
