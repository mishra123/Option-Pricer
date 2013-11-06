/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package option;

import option.Option;

/**
 *
 * @author Tianyang Feng`
 */
public class putOption extends Option{
    
    private putOption(){     
         this.setName("put");
    }
    
    public putOption(double s0, double sK, double t, double rFR, double v, int type){
        super(s0,sK, t, rFR, v, type);
        this.setName("put");
    }
    
        public String getAvailiableAlgo(){
        switch(this.getType()){
            case AMERICAN: 
                return "Binomial tree; Simulation; Numerical integration";
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
          return Math.max(sK -s, 0);    
    }
        
}
