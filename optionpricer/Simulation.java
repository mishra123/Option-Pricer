/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package optionpricer;

import option.Option;
import algorithm.model.Algorithm;
import java.util.Random;
import java.text.*;

/**
 *
 * @author Tianyang Feng`
 */
public class Simulation implements Algorithm{
    private int numIntervals;
    private int numTrials;
    private String name = "Simulation";
    
    public Simulation(){        
        numIntervals = 500;
        numTrials = 10000;
    }
    
    @Override
    public double optionPrice(Option option){
        int i, trialCount;
        double deltaT = (option.getTerm())/((double)numIntervals);
        double trialRunningSum, trialAverage, trialPayoff;
        double simulationRunningSum, simulationAveragePayoff;
        double s;
        double valueOfOption = 0;
        Random rand = new Random();
        
        simulationRunningSum = 0;
        for (trialCount = 1; trialCount <= numTrials; trialCount ++){
           s = option.getUnderlyingPrice();
           trialRunningSum = 0;
           for (i = 0; i < numIntervals; i++){
               s = s * Math.exp((option.getRiskFreeRate() - option.getVolatility()*option.getVolatility()/2) * deltaT
                                  + option.getVolatility() * rand.nextGaussian()*Math.sqrt(deltaT));
               trialRunningSum += s;
           }
           trialAverage = trialRunningSum / numIntervals;
           trialPayoff = option.payoff(trialAverage, option.getStrikePrice());
           simulationRunningSum +=trialPayoff;
           simulationAveragePayoff = simulationRunningSum/ numTrials;           
           valueOfOption = simulationAveragePayoff * Math.exp(-option.getRiskFreeRate()*option.getTerm());   
        }
        System.out.println(valueOfOption); 
        DecimalFormat df=new DecimalFormat("#0.00");
        return Double.parseDouble(df.format(valueOfOption));
    } 
    
    public String getName(){
        return name;
    }
}