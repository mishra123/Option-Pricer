/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package optionpricer;

import option.Option;
import algorithm.model.Algorithm;
import java.text.DecimalFormat;

/**
 *
 * @author YangJiang`
 */
public class BionomialTree implements Algorithm{
    
 private String name = "Bionomial Tree";

    private double underlyingPrice=0;
    private double strikePrice=0;
    private double term=0;
    private double volatility=0;
    private double riskFreeRate=0;
    private int type=0;
    
    public BionomialTree(){        
    }
    
    public double bionomialTreeCal(int numIntervals, Option option) {
        int i, j;
	double deltaT	= term / numIntervals;
	double up = 1.0 + riskFreeRate * deltaT + (volatility*Math.sqrt(deltaT));
	double down = 1.0 + riskFreeRate * deltaT - (volatility*Math.sqrt(deltaT));
	double upProb = 0.5;
	double downProb = 0.5;
	double binomValue;
        Price[][]binomTree=new Price[numIntervals+1][];
	for (i = 0; i <=numIntervals; i++) {
		binomTree[i] = new Price[i + 1];
                for(j=0;j<=i;j++)
                {
                    binomTree[i][j]=new Price();
                }
	}
	// Fill the stockPrice component of the binomialTree
	for (i = 0; i <= numIntervals; i++) {
		for (j = 0; j <= i; j++) {
			binomTree[i][j].stockPrice = underlyingPrice * Math.pow(up,j) * Math.pow(down,i-j);
		}
	}
	// Fill the optionPrices at the terminal nodes
	for (j = 0; j <= numIntervals; j++) {
		binomTree[numIntervals][j].optionPrice =
                        option.payoff((binomTree[numIntervals][j].stockPrice), strikePrice);
                
	}
	// Now work backwards, filling optionPrices in the rest of the tree
	double discount = Math.exp(-riskFreeRate*deltaT);
	for (i = numIntervals-1; i >= 0; i--) {
		for (j = 0; j <= i; j++) {
		
                 if (option.getName() == "call"){
                  // System.out.println(option.getName());
                    binomTree[i][j].optionPrice =
				Math.max(binomTree[i][j].stockPrice - strikePrice,
					discount*(upProb*binomTree[i+1][j+1].optionPrice +
						downProb*binomTree[i+1][j].optionPrice));
                }
                else{
                     binomTree[i][j].optionPrice =
				Math.max(strikePrice - binomTree[i][j].stockPrice,
					discount*(upProb*binomTree[i+1][j+1].optionPrice +
						downProb*binomTree[i+1][j].optionPrice));
                }
                     
		}

	}
        return binomTree[0][0].optionPrice;
}


    @Override
    public double optionPrice(Option option){
      this.underlyingPrice=option.getUnderlyingPrice();
      this.strikePrice=option.getStrikePrice();
      this.term=option.getTerm();
      this.volatility=option.getVolatility();
      this.riskFreeRate=option.getRiskFreeRate();
      this.type=option.getType();
      double optionPrice = bionomialTreeCal(500,option);
      System.out.println(optionPrice);
      DecimalFormat df=new DecimalFormat("#0.00");
      return Double.parseDouble(df.format(optionPrice));
    }
    
    public String getName(){
        return name;
    }
}
