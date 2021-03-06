/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package option;

import option.Option;

/**
 *
 * @author Tianyang Feng
 */
public class callOption extends Option {
    
    private callOption(){    
       this.setName("call");
    }
    
    public callOption(double s0, double sK, double t, double rFR, double v, int type){
        super(s0,sK, t, rFR, v, type); 
        this.setName("call");
    }
    
    public String getAvailiableAlgo(){
        switch(this.getType()){
            case AMERICAN: 
                return "Binomial tree; Simulation; Numerical integration; B-S formula";
            case EUROPEAN:
                return "Binomial tree; Simulation; Numerical integration; B-S formula";
            case ASIAN:
                return "Simulation";
            default:
                return "Simulation;";
        }                       
    }
    
    @Override
    public double payoff(double s, double sK){
         return Math.max(s - sK, 0);    
    }    
}
