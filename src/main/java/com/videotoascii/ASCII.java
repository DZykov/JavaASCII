package com.videotoascii;

import java.awt.image.BufferedImage;
import java.awt.Color;

public class ASCII {
    
    boolean negative;
    double num_of_shades;
    double max_gray = 255;
    String shades = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,^`\"'. "; 

    public ASCII() {
		this.negative = false;
        this.num_of_shades = shades.length();
	}

	public ASCII(boolean negative) {
		this.negative = negative;
        this.num_of_shades = shades.length();
	}

    public double grayscale(double red, double green, double blue){
        // double gray = (Math.max(red, Math.max(green, blue)) + Math.min(red, Math.min(green, blue))) / 2; to simple but nice
        double conversion_factor = this.max_gray / (this.num_of_shades - 1);
        double average = (red + green + blue) / 3;
        double gray = ((average / conversion_factor) + 0.5) * conversion_factor;
        return gray;
    }

    public char negative(double gray_value){
        double symbol_pos = gray_value / (this.max_gray / this.num_of_shades);
        return shades.charAt((int) Math.floor(symbol_pos));
    }

    public char positive(double gray_value){
        double symbol_pos = gray_value / (this.max_gray / this.num_of_shades);
        int pos = (int)(this.num_of_shades - Math.floor(symbol_pos));
        if(pos >= 70){
            pos = 69;
        } else if (pos < 0){
            pos = 0;
        }
        return shades.charAt(pos);
    }

    public String convert(final BufferedImage image) {
		StringBuilder sb = new StringBuilder((image.getWidth() + 1) * image.getHeight());
		for (int y = 0; y < image.getHeight(); y++) {
			
            if (sb.length() != 0){
                sb.append("\n");
            }

			for (int x = 0; x < image.getWidth(); x++) {
				Color pixelColor = new Color(image.getRGB(x, y));
                
				double gray_value = grayscale((double) pixelColor.getRed(), (double) pixelColor.getBlue(), (double) pixelColor.getGreen());
				final char s = negative ? negative(gray_value) : positive(gray_value);
				sb.append(s);
			}
		}
		return sb.toString();
	}

}
