package hw1; 

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import java.lang.Byte;

public class part3 {
    
    JFrame frame;
    JLabel lbIm1;
    BufferedImage img;
    ImageIcon image;
    BufferedImage[] wholeImg;
    
    public void convertFile(String[] args) {
    	String outPut =  args[1];
        String convertWay = args[2];
        int isFilter = Integer.parseInt(args[3]);
        int new_x = 0;
        int new_y = 0;
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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (convertWay.equals("HD2SD")) { 
            int totalFrame = 0;
            int frameByte = 960 * 540 * 3;   
            totalFrame = bytes.length/frameByte;
            byte[] res_byte = new byte[176 * 144 *3 * totalFrame];
            int mark = 0;
            int z = 0;
            int k = 0;
            int i = 0, j = 0;
        	if (isFilter == 0) {
        		for (z = 0; z < totalFrame; z++) {
        			for (k = 0; k < 3; k++) {
		        		for (i = 0; i < 144; i++) {
		        			for (j = 0; j < 176; j++) {
		        				new_x = (int) (i * (540/144));
		        				new_y = (int) (j * (960/176));
		        				res_byte[mark] = bytes[z * frameByte + new_x * 960 + new_y + k * 960 * 540];  
		        				mark++;
		        			}
		        		}
        			}
        		}
        	} 
        	mark = 0;
        	if (isFilter == 1) {
        		res_byte = new byte[176 * 144 *3 * totalFrame];
        		for (z = 0; z < totalFrame; z++) {
        			int[] dx = {-1, 0, 1, 0, -1, 1, -1, 1, -2, 2, -2, 2, -2, 2, 0, 0, -1, -1, 1, 1, -2, -2, 2, 2};
	    			int[] dy = {0, 1, 0, -1, 1, 1, -1, -1, 0, 0, -1, -1, 1, 1, -2, 2, -2, 2, -2, 2, -2, 2, -2, 2};
        			for (k = 0; k < 3; k++) {
        				for (i = 0; i < 144; i++) {
        					for (j = 0; j < 176; j++) {
        						new_x = (int) (i * (540/144));
		        				new_y = (int) (j * (960/176));
		        				res_byte[mark] = bytes[z * frameByte + new_x * 960 + new_y + k * 960 * 540];  
		        				int sumByte = res_byte[mark] & 0xff;
		        				int num = 1;
		        				Byte averageByte;
		        				for (int t = 0; t < 24; t++) {
		        					int post_x = new_x + dx[t];
		        					int post_y = new_y + dy[t];
		        					if (post_x >= 0 && post_y >= 0 && post_x < 540 && post_y < 960) {
		        						sumByte += (bytes[z * frameByte + post_x * 960 + post_y + k * 960 * 540])&0xff;
		        						num++;
		        					} 
		        				} 
		        				averageByte = (byte)(sumByte/num);
		        				res_byte[mark] = averageByte;
		        				mark++;
        					}
        				}
        			}
        		}
        	} 
        	try (FileOutputStream fos = new FileOutputStream(outPut)) {
        	    fos.write(res_byte);
        	} catch (IOException ioe) {
        	    ioe.printStackTrace();
        	}
        	showIms(res_byte, totalFrame);
        }
        if (convertWay.equals("SD2HD")) {
        	int totalFrame = 0;
            int frameByte = 176 * 144 * 3; 
            totalFrame = bytes.length/frameByte; 
            byte[] res_byte = new byte[960 * 540 *3 * totalFrame];
            int mark = 0;
            int z = 0;
            int k = 0;
            int i = 0, j = 0;
        	if (isFilter == 0) {
        		for (z = 0; z < totalFrame; z++) {
        			for (k = 0; k < 3; k++) {
		        		for (i = 0; i < 540; i++) {
		        			for (j = 0; j < 960; j++) {
		        				new_x = (int) ((i * 144)/540);
		        				new_y = (int) ((j * 176)/960);
		        				res_byte[mark] = bytes[z * frameByte + new_x * 176 + new_y + k * 176 * 144];  
		        				mark++;
		        			}
		        		}
        			}
        		} 
        	} 
        	mark = 0;
        	if (isFilter == 1) {
        		res_byte = new byte[960 * 540 *3 * totalFrame];
        		for (z = 0; z < totalFrame; z++) {
        			for (k = 0; k < 3; k++) {
        				for (i = 0; i < 540; i++) {
        					for (j = 0; j < 960; j++) {
        						new_x = (int) ((i * 144)/540);
		        				new_y = (int) ((j * 176)/960);
		        				res_byte[mark] = bytes[z * frameByte + new_x * 176 + new_y + k * 176 * 144];  
		        				mark++;
        					}
        				}
        			}
        		} 
        		mark = 0;
        		frameByte = 540 * 960 * 3; 
        		for (z = 0; z < totalFrame; z++) {
        			for (k = 0; k < 3; k++) {
        				for (i = 0; i < 540; i++) {
        					for (j = 0; j < 960; j++) {
				        		int[] dx = {-1, 0, 1, 0, -1, 1, -1, 1, -2, 2, -2, 2, -2, 2, 0, 0, -1, -1, 1, 1, -2, -2, 2, 2};
				    			int[] dy = {0, 1, 0, -1, 1, 1, -1, -1, 0, 0, -1, -1, 1, 1, -2, 2, -2, 2, -2, 2, -2, 2, -2, 2};
								int sumByte = res_byte[mark] & 0xff;
								int num = 1;
								Byte averageByte;
								for (int t = 0; t < 24; t++) {
									int post_x = i + dx[t];
									int post_y = j + dy[t];
									if (post_x >= 0 && post_y >= 0 && post_x < 540 && post_y < 960) {
										sumByte += (res_byte[z * frameByte + post_x * 960 + post_y + k * 960 * 540])&0xff;
										num++;
									} 
								} 
								averageByte = (byte)(sumByte/num);
								res_byte[mark] = averageByte;
								mark++; 
        					}
        				}
        			}
        		}
	        	
        	}
        	try (FileOutputStream fos = new FileOutputStream(outPut)) {
        	    fos.write(res_byte);
        	} catch (IOException ioe) {
        	    ioe.printStackTrace();
        	}
        	showIms(res_byte, totalFrame); 
        }
    }
    
    public void showIms(byte[] res_byte, int totalFrame) {
    	if (res_byte.length == 176 * 144 *3 * totalFrame) {
	        int frameByte = 176 * 144 * 3; 
	        byte[][] totalBytes; 
	        totalBytes = new byte[totalFrame][frameByte];
	        int k = 0;
	        for (int i = 0; i < totalFrame; i++) {
	            for (int j = 0; j < frameByte; j++) {
	                totalBytes[i][j] = res_byte[j + frameByte * k];
	            }
	            k++;
	        }
	        wholeImg = new BufferedImage[totalFrame];
	        for (int z = 0; z < totalFrame; z++) {
	            img = new BufferedImage(176, 144, BufferedImage.TYPE_INT_RGB);
	            int ind = 0;
	            for(int y = 0; y < 144; y++){
	                for(int x = 0; x < 176; x++){ 
	                    byte r = totalBytes[z][ind];
	                    byte g = totalBytes[z][ind+144*176];
	                    byte b = totalBytes[z][ind+144*176*2];
	                    
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
	        String result = String.format("Video height: %d, width: %d", 144, 176);
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
	                Thread.sleep(100);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	            if (i == wholeImg.length - 1) {
	            	i = 0;
	            }
	        }
    	}
    	if (res_byte.length == 960 * 540 *3 * totalFrame) {
	        int frameByte = 960 * 540 * 3; 
	        byte[][] totalBytes; 
	        totalBytes = new byte[totalFrame][frameByte];
	        int k = 0;
	        for (int i = 0; i < totalFrame; i++) {
	            for (int j = 0; j < frameByte; j++) {
	                totalBytes[i][j] = res_byte[j + frameByte * k];
	            }
	            k++;
	        }
	        wholeImg = new BufferedImage[totalFrame];
	        for (int z = 0; z < totalFrame; z++) {
	            img = new BufferedImage(960, 540, BufferedImage.TYPE_INT_RGB);
	            int ind = 0;
	            for(int y = 0; y < 540; y++){
	                for(int x = 0; x < 960; x++){ 
	                    byte r = totalBytes[z][ind];
	                    byte g = totalBytes[z][ind+540*960];
	                    byte b = totalBytes[z][ind+540*960*2];
	                    
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
	        String result = String.format("Video height: %d, width: %d", 540, 960);
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
	                Thread.sleep(100);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	            if (i == wholeImg.length - 1) {
	            	i = 0;
	            }
	        }
    	}
    }
    
    public static void main(String[] args) {
        part3 ren = new part3();
        ren.convertFile(args);
    }
    
}

