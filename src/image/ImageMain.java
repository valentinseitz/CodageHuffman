/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package image;

import static core.Core.codage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fenix
 */
public class ImageMain {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {


		try {
			BufferedReader br = new BufferedReader(new FileReader((new ImageMain()).getClass().getResource("lena_gray.raw").getFile()));
			StringBuilder img = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				img.append(line);
				line = br.readLine();
			}
			br.close();

			codage("de l'image", img.toString().split(" "));

		} catch (IOException ex) {
			Logger.getLogger(ImageMain.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
