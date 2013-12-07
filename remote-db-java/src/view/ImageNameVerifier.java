package view;

import java.awt.Color;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

import com.Application;
import com.Application.Mode;


public class ImageNameVerifier extends InputVerifier {

    @Override
    public boolean verify(JComponent input) {
        boolean verified = false;
        String text = ((JTextField) input).getText();
        char chFrom = 'a', chTo = 'o';
        if(Application.appMode.equals(Mode.DBAO))
        	{chFrom = 'a'; chTo = 'o';}
        else if(Application.appMode.equals(Mode.DBPZ))
    		{chFrom = 'p'; chTo = 'z';}
        
            if (text.length() > 0 && text.toLowerCase().charAt(0)>=chFrom && text.toLowerCase().charAt(0)<=chTo) {
                input.setBackground(Color.WHITE);
                verified = true;
            } else {
                input.setBackground(Color.RED);
            }

        return verified;
    }
}