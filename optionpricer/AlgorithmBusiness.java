/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package optionpricer;


import algorithm.model.Algorithm;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author YangJiang
 */
public class AlgorithmBusiness extends Base {

    private static Map<String, Algorithm> algorithms = new HashMap<String, Algorithm>();
    private static Map<String, Algorithm> temps = new HashMap<String, Algorithm>();
    private static String tempJarFilePath = null;

    public static void initAlgorithms() {
        if (!checkDefaultLocation(null)) {
            return;
        }
        String userDir = System.getProperty("user.dir");

        File folder = new File(userDir + "/algorithms");

        File[] jarFiles = folder.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                boolean result = false;
                if (pathname.isFile()) {
                    result = pathname.getName().endsWith("jar");
                }
                return result;
            }
        });
        if (jarFiles == null || jarFiles.length <= 0) {
            return;
        } else {
            for (int i = 0; i < jarFiles.length; i++) {
                File jarFile = jarFiles[i];
                loadJarFileToMemory(null, jarFile.getAbsolutePath());
                Map<String, Algorithm> list = getListAlgorithms(null, jarFile.getAbsolutePath());
                algorithms.putAll(list);
            }
        }
    }

    public static Map<String, Algorithm> getCurrentAlgorithm() {
        return algorithms;
    }

     public void getJarFilePath(OptionPricerUI ui) {
          final JFileChooser fc = new JFileChooser();
          fc.showOpenDialog(ui);
          try {
              File file = fc.getSelectedFile();
              String filepath = file.getAbsolutePath();
              ui.setJarFilePath(filepath);
          } catch (Exception e) {
              JOptionPane.showMessageDialog(ui, e.getMessage(), "AlgorithmBusiness.getJarFilePath(): Internal error", JOptionPane.ERROR_MESSAGE);
          }
      }

    public void loadAlgorithms(OptionPricerUI ui) {
        String jarFilePath = ui.getJarFilePath();
        System.out.println(jarFilePath);
        tempJarFilePath = jarFilePath;
        loadJarFileToMemory(ui, jarFilePath);
        Map<String, Algorithm> list = getListAlgorithms(ui, jarFilePath);
        temps.clear();
        temps.putAll(list);
        
        Set<String> keys = temps.keySet();
        for (String key : keys) {
            System.out.println(key);
            Algorithm algorithm = temps.get(key);
            algorithms.put(key, algorithm);
        }
        if (!checkDefaultLocation(ui)) {
            return;
        }

        File sourceFile = new File(tempJarFilePath);

        try {
            copyFile(sourceFile, new File(System.getProperty("user.dir") + "/algorithms/" + sourceFile.getName()));
        } catch (Exception ee) {
            JOptionPane.showMessageDialog(ui, ee.getMessage(), "AlgorithmBusiness.addAperator(): Internal error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private static void copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.exists()) destFile.createNewFile();
        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            long count = 0;
            long size = source.size();
            while ((count += destination.transferFrom(source, count, size - count)) < size);
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }

    public static Algorithm getAlgorithmByName(String algoName) {
        return algorithms.get(algoName);
    }

    private static boolean checkDefaultLocation(OptionPricerUI ui) {
        String userDir = System.getProperty("user.dir");
        File f = new File(userDir + "/algorithms");
        if (!f.exists()) {
            if (!f.mkdirs()) {
                JOptionPane.showMessageDialog(ui, "Cannot create folder: '" + userDir + "/algorithms" + "'", "AlgorithmBusiness.checkDefaultLocation(): Internal error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }
}
