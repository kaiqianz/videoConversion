package hw1; 

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;

public class part2 {
    
    JFrame frame;
    JLabel lbIm1;
    BufferedImage img;
    ImageIcon image;
    BufferedImage[] wholeImg;
    
    public void showVideo(String[] args) {
        int width = Integer.parseInt(args[1]);
        int height = Integer.parseInt(args[2]);
        int speed = Integer.parseInt(args[3]);
        int frameByte = width * height * 3;
        int totalFrame = 0;
        byte[][] totalBytes;
        byte[] bytes = null;
        try {
            File file = new File(args[0]);
            InputStream is = new FileInputStream(file);
            
            long len = file.length();
            bytes = new byte[(int)len];
            
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }
            totalFrame = bytes.length/frameByte;
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        totalBytes = new byte[totalFrame][frameByte];
        int k = 0;
        for (int i = 0; i < totalFrame; i++) {
            for (int j = 0; j < frameByte; j++) {
                totalBytes[i][j] = bytes[j + frameByte * k];
            }
            k++;
        }
        wholeImg = new BufferedImage[totalFrame];
        for (int z = 0; z < totalFrame; z++) {
            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            int ind = 0;
            for(int y = 0; y < height; y++){
                for(int x = 0; x < width; x++){ 
                    byte r = totalBytes[z][ind];
                    byte g = totalBytes[z][ind+height*width];
                    byte b = totalBytes[z][ind+height*width*2];
                    
                    int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
                    //int pix = ((a << 24) + (r << 16) + (g << 8) + b);
                    img.setRGB(x,y,pix);
                    ind++;
                }
            }
            wholeImg[z] = img;
        }
        // Use labels to display the images
        
        frame = new JFrame();
        GridBagLayout gLayout = new GridBagLayout();
        frame.getContentPane().setLayout(gLayout);
        String result = String.format("Video height: %d, width: %d", height, width);
        JLabel lbText1 = new JLabel(result);
        lbText1.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        frame.getContentPane().add(lbText1, c);
        lbIm1 = new JLabel();
        for (int i = 0; i < wholeImg.length; i++) {
            image = new ImageIcon(wholeImg[i]);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 1;
            lbIm1.setIcon(image);
            frame.getContentPane().add(lbIm1, c);
            frame.pack();
            frame.setVisible(true);
            try {
                Thread.sleep(1000/speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (i == wholeImg.length - 1) {
            	i = 0;
            }
        }
        
    }
    
    public static void main(String[] args) {
        part2 ren = new part2();
        ren.showVideo(args);
    }
    
}

