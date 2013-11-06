/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package option;

/**
 *
 * @author Tianyang Feng`
 */
public class Option {
    
    public static final int AMERICAN = 0;
    public static final int EUROPEAN = 1;
    public static final int ASIAN = 2;
    
    private double underlyingPrice;
    private double strikePrice;
    private double term;
    private double volatility;
    private double riskFreeRate;
    private int optionType;
    private String Name = "Option";
    
    public Option(){
    }
    
    public Option(double s0, double sK, double t, double rFR, double v, int type){
         underlyingPrice = s0;
         strikePrice = sK;
         term = t;
         volatility = v;
         riskFreeRate = rFR;
         optionType = type;
    }
    
    public double getUnderlyingPrice(){
        return underlyingPrice;
    }
    
    public double getStrikePrice(){
        return strikePrice;
    }
    
    public double getTerm(){
        return term;
    }
    
    public double getRiskFreeRate(){
        return riskFreeRate;
    }
    
    public double getVolatility(){
        return volatility;
    }        

    public void setVolatility(double v){
        volatility = v;
    }
    
    public int getType(){
        return optionType;
    } 
    
    public String getName(){
        return Name;
    }
    
    void setName(String n){
        Name = n;
    }
    
    public double payoff(double s, double sK){
        return 0;
    }
}

