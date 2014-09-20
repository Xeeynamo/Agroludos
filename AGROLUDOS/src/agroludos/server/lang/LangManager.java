/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agroludos.server.lang;

import java.awt.Component;
import java.awt.Container;
import java.beans.*;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.*;

/**
 *
 * @author Luciano
 */
public class LangManager extends PropertyEditorSupport {
    
    ResourceBundle bundle;
    
    public LangManager() {
        openLocale(Locale.getDefault());
    }
    
    public LangManager(Locale locale)
    {
        openLocale(locale);
    }
    
    private void SubApplyLanguage(Component component, String str)
    {
        if (component instanceof JTabbedPane)
        {
            JTabbedPane c = (JTabbedPane)component;
        }
        else if (component instanceof JLabel)
        {
            JLabel c = (JLabel)component;
            c.setText(str);
        }
        else if (component instanceof JButton)
        {
            JButton c = (JButton)component;
            c.setText(str);
        }
        else if (component instanceof JCheckBox)
        {
            JCheckBox c = (JCheckBox)component;
            c.setText(str);
        }
        else if (component instanceof Container)
        {
            ApplyLanguage((Container)component);
        }
    }
    public void ApplyLanguage(Container frame)
    {
        Component[] component = frame.getComponents();
        for(Component c : component)
        {
            String name = c.getName();
            if (name == null || name.compareTo("") == 0)
                SubApplyLanguage(c, "NONE");
            else
                SubApplyLanguage(c, getString(name));
        }
    }
    
    String getString(String key) throws MissingResourceException
    {
        try
        {
            return bundle.getString(key);
        }
        catch(Exception e)
        {
            return key;
        }
    }
    
    private void openLocale(Locale locale)
    {
        String str = locale.getDisplayLanguage();
        try
        {
            bundle = ResourceBundle.getBundle("agroludos/server/lang/" + str);
        }
        catch (Exception e)
        {
            System.out.println("Unable to load " + str + " language. Default language will be used.");
            bundle = ResourceBundle.getBundle("agroludos/server/lang/" + Locale.ENGLISH.getDisplayLanguage());
        }
    }
}
