/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package optionpricer;

/**
 *
 * @author Tianyang Feng`
 */

import option.Option;
import algorithm.model.Algorithm;
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import javax.swing.*;

public class Graph extends JPanel {
        
    double[] data; 
    public void calculateGraph(Option option, Algorithm algo){
       data = new double[10]; 
       int i;
       double volatility = 0.15;
       double optionPrice;
       Calculation graphCalculation = new Calculation(); 
       for (i = 0; i < 10; i++){
           System.out.println(i);
           System.out.println(volatility);
           option.setVolatility(volatility);
           optionPrice = graphCalculation.calculatePrice(option, algo);
           data[i] = optionPrice;
           volatility += 0.05;
           System.out.println(data[i]);          
       }      
    }  
    
    final int PAD = 20;
 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        // Draw ordinate.
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h-PAD));
        // Draw abcissa.
        g2.draw(new Line2D.Double(PAD, h-PAD, w-PAD, h-PAD));
        // Draw labels.
        Font font = g2.getFont();
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics lm = font.getLineMetrics("0", frc);
        float sh = lm.getAscent() + lm.getDescent();
        // Ordinate label.
        String s = "Option Price";
        float sy = PAD + ((h - 2*PAD) - s.length()*sh)/2 + lm.getAscent();
        for(int i = 0; i < s.length(); i++) {
            String letter = String.valueOf(s.charAt(i));
            float sw = (float)font.getStringBounds(letter, frc).getWidth();
            float sx = (PAD - sw)/2;
            g2.drawString(letter, sx, sy);
            sy += sh;
        }
        // Abcissa label.
        s = "Volatility";
        sy = h - PAD + (PAD - sh)/2 + lm.getAscent();
        float sw = (float)font.getStringBounds(s, frc).getWidth();
        float sx = (w - sw)/2;
        g2.drawString(s, sx, sy);
        
        // mark scale
        String[] v = {"0.15", "0.20","0.25", "0.30", "0.35", "0.40","0.45", "0.50", "0.55", "0.60"};
        g2.setPaint(Color.black);
        g2.setFont(font);
         for(int i = 0; i < data.length; i++) {          
            g2.drawString(v[i],PAD-8 + i*(w - 2*PAD)/(data.length-1), h - PAD);  
        }      
                 
        // Draw lines.
        double xInc = (double)(w - 2*PAD)/(data.length-1);
        double scale = (double)(h - 2*PAD)/getMax();
        g2.setPaint(Color.blue.darker());
        for(int i = 0; i < data.length-1; i++) {
            double x1 = PAD + i*xInc;
            double y1 = h - PAD - scale*data[i];
            double x2 = PAD + (i+1)*xInc;
            double y2 = h - PAD - scale*data[i+1];
            g2.draw(new Line2D.Double(x1, y1, x2, y2));                     
        }
        
        // Mark data points.
        g2.setPaint(Color.red);
        for(int i = 0; i < data.length; i++) {
            double x = PAD + i*xInc;
            double y = h - PAD - scale*data[i];
            g2.fill(new Ellipse2D.Double(x-2, y-2, 4, 4));
        }      
        // Mark option price
        g2.setPaint(Color.black);
        for(int i = 0; i < data.length; i++) {
            double x = PAD + i*xInc;
            double y = h - PAD - scale*data[i];
            g2.drawString("" + data[i],(float)(x),(float)(y+15));
        } 
    }
 
    private double getMax() {
        double max = -Double.MAX_VALUE;
        for(int i = 0; i < data.length; i++) {
            if(data[i] > max)
                max = data[i];
        }
        return max;
    }
    
    }
    
    
