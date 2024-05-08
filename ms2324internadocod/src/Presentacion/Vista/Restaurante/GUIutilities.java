package Presentacion.Vista.Restaurante;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class GUIutilities{
	
	static Component comp;
	
	public static void onlyIntegers(JTextField c){
		c.addKeyListener(new KeyAdapter() {
		    public void keyTyped(KeyEvent e) {
			      char c = e.getKeyChar();
			      if (!((c >= '0') && (c <= '9') ||
			         (c == KeyEvent.VK_BACK_SPACE) ||
			         (c == KeyEvent.VK_DELETE))) {
			        e.consume();
			      }
			    }
			  });
	}
}
