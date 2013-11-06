/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package optionpricer;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import option.Option;
import algorithm.model.Algorithm;
/**
 *
 * @author samsung`
 */
public class DropInAlgorithm extends Base {
    public Algorithm calculate(OptionPricerUI ui) {
        Option option= ui.getMyOption();

        String algorithmName = ui.getSelectedAlgorithmName();
        Algorithm myAlgo = AlgorithmBusiness.getAlgorithmByName(algorithmName);
      
        return myAlgo;
}
}