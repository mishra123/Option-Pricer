/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package optionpricer;

import algorithm.model.Algorithm;
import java.awt.Component;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import javax.swing.JOptionPane;

/**
 *
 * @author YangJiang
 */
public class Base {

    protected static void loadJarFileToMemory(Component com, String jarFilePath) {
        try {
            ClassLoader loader = AlgorithmBusiness.class.getClassLoader();
            URLClassLoader urlCL = (URLClassLoader) loader;
            Class<?>[] classes = {URL.class};
            Method m = URLClassLoader.class.getDeclaredMethod("addURL", classes);
            m.setAccessible(true);
            m.invoke(urlCL, new Object[]{(new File(jarFilePath)).toURI().toURL()});
        } catch (Exception e) {
            JOptionPane.showMessageDialog(com, e.getMessage(), "BaseCalculator.loadJarFileToMemory(): Internal error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static Map<String, Algorithm> getListAlgorithms(Component com, String jarFilePath) {
        Map<String, Algorithm> algorithms = new HashMap<String, Algorithm>();
        try {
            File f = new File(jarFilePath);
            JarFile jar = new JarFile(f);
            final Manifest manifest = jar.getManifest();
            final Attributes mattr = manifest.getMainAttributes();
            String algorithmPaths = "";
            for (Object a : mattr.keySet()) {
                String keyName = ((Attributes.Name) a).toString();
                System.out.println(keyName);
                System.out.println((String) mattr.getValue((Attributes.Name) a));
                if (keyName == "Main-Class") {
                    algorithmPaths = (String) mattr.getValue((Attributes.Name) a);
                    System.out.println(algorithmPaths);
                    break;
                }
            }
            String[] classPaths = algorithmPaths.split(", ");
            for (int i = 0; i < classPaths.length; i++) {
                System.out.println(classPaths[i]);
                Algorithm algorithm = (Algorithm) Class.forName(classPaths[i]).newInstance();
                algorithms.put(algorithm.getName(), algorithm);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(com, e.getMessage(), "Base.getListAlgorithm() error", JOptionPane.ERROR_MESSAGE);
        }
        return algorithms;
    }

    protected double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("########.##");
        return Double.valueOf(twoDForm.format(d));
    }
}
