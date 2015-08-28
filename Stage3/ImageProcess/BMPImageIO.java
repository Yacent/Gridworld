import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import imagereader.IImageIO;

/**
 * useful interface link
 * http://download.oracle.com/technetwork/java/javase/6/docs/zh/api/java/awt/image/MemoryImageSource.html
 * http://download.oracle.com/technetwork/java/javase/6/docs/zh/api/java/awt/image/BufferedImage.html*/

public class BMPImageIO implements IImageIO {
	
	/**
	 * define some useable variable
	 * biWidth biHeight biSize biCount
	 * @parameter fillByte which use to fill the empty byte
	 * @parameter bmpHead, using to store general infomation
	 * @parameter bmpInfo store the details of the pic
	 * @parameter size, store the size of the biSize.
	 */
	private int biWidth, biHeight, biSize, biCount, fillByte, bmpHead, bmpInfo, size;
	
	private int[] rgbInfo;
	
	public Image myRead(String filePath) throws IOException {
		
		FileInputStream fs = new FileInputStream(filePath);
		
		/**
		 * accoding to the structure of the BMP
		 * the head has 14 bytes
		 * byte 0 - 13
		 */
		bmpHead = 14;
		byte[] header = new byte[bmpHead];
		fs.read(header, 0, bmpHead);
		
		/**
		 * the info has 40 bytes
		 * byte 14 - 53
		 */
		bmpInfo = 40;
		byte[] info = new byte[bmpInfo];
		fs.read(info, 0, bmpInfo);
		
		/**
		 * get the biwidth of the bmp, the number of pixes in x
		 * from byte 18-21
		 * 
		 * why to use & 0xff, u can check for the complement  in computer
		 * look for some information about ByteToInt
		 * 
		 * why to scan from the highposition to low
		 * since x86 using Little Endian
		 */
		biWidth = ((int)(info[7] & 0xff)) << 24
				| ((int)(info[6] & 0xff)) << 16
				| ((int)(info[5] & 0xff)) << 8
				| ((int)(info[4] & 0xff));
		
		/**
		 * get the biHeight of the bmp, the number of pixes in y
		 * from byte 22-25
		 */
		biHeight = ((int)(info[11] & 0xff)) << 24
				| ((int)(info[10] & 0xff)) << 16
				| ((int)(info[9] & 0xff)) << 8
				| ((int)(info[8] & 0xff));
		
		/**
		 * biCount, means the bit of a pixes hold
		 * from byte 28-29
		 */
		biCount = ((int)(info[15] & 0xff)) << 8
				| ((int)(info[14] & 0xff));
		
		/**
		 * get biSize, means the size of the info of color
		 * from 34 -37
		 */
		biSize = ((int)(info[23] & 0xff)) << 24
				| ((int)(info[22] & 0xff)) << 16
				| ((int)(info[21] & 0xff)) << 8
				| ((int)(info[20] & 0xff));
		
		if (biCount == 24) {
			/**
			 * biSize / biHeight means the length of bytes of each row
			 * sub the biWidth * 3 equals to the fillByte
			 */
			fillByte = (biSize / biHeight) - biWidth * 3;
			
			/**
			 * new a Array to store the bytes of the color of pixes
			 */
			byte[] rgbs = new byte[biSize];
			fs.read(rgbs, 0, biSize);
			
			size = biWidth * biHeight;
			int[] pixels = new int[size];
			
			int index = 0;
			
			/**
			 * scan the pixel in the biSize, and put them in a new array
			 * using the MemoryImageSource(), we should put the pixel in
			 * biSize reverse, or the pic will show updown.
			 */
			
			for (int i = 0; i < biHeight; i++) {
				for (int j = 0; j < biWidth; j++) {
					pixels[biWidth * (biHeight - i - 1) + j] =
							(255 & 0xff) << 24
							| ((int)rgbs[index + 2] & 0xff) << 16
							| ((int)rgbs[index + 1] & 0xff) << 8
							| ((int)rgbs[index] & 0xff);
					index += 3;
				}
				index += fillByte;
			}
			
			rgbInfo = pixels;
			
			Image image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(biWidth, biHeight, pixels, 0, biWidth));
			fs.close();
			return image;
		} else if (biCount == 8) {
			byte[] color = new byte[1024];
			fs.read(color, 0, 1024);
			
			int[] colorSet = new int[256];
			
			int k = 0;
			
			for (int index = 0; index < 1020; index += 4) {
				colorSet[k++] = ((int)color[index + 3] & 0xff) << 24
						| ((int)color[index + 2] & 0xff) << 16
						| ((int)color[index + 1] & 0xff) << 8
						| ((int)color[index] & 0xff);
			}
			
			fillByte = ((biSize / biHeight) - biWidth) % 4;
			
			byte[] rgbs = new byte[biSize];
			fs.read(rgbs, 0, biSize);
			
			size = biWidth * biHeight;
			int[] pixels = new int[size];
			
			int index = 0;
			int ans = 0;
			
			for (int i = 0; i < biHeight; i++) {
				for (int j = 0; j < biWidth; j++) {
					index = (int)(rgbs[ans] & 0xff);
					pixels[biWidth * (biHeight - i - 1) + j] =
							colorSet[index] | ((255 & 0xff) << 24);
					ans += 1;
				}
				ans += fillByte;
			}
			
			Image image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(biWidth, biHeight, pixels, 0, biWidth));
			fs.close();
			return image;
		} else {
			fs.close();
			throw new IOException("Bits per pixel is not 24");
		}
		
	}

	public Image myWrite(Image img, String filePath) throws IOException {
		int width = img.getHeight(null);
		int height = img.getWidth(null);
		
		BufferedImage bufferedImage;
		// TYOE_BYTE_BGR means a 8 bit a panel, a pixel hold 3 byte, 24bit
		if (biCount == 24) {
			 bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		} else {
			 bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		}
		
		Graphics graphics = bufferedImage.getGraphics();
		graphics.drawImage(img, 0, 0, null);
		
		/**
		 * open file
		 */
		File iFile = new File(filePath + ".bmp");
		ImageIO.write(bufferedImage, "bmp", iFile);
		
		return img;
	}
	
	public int getWidth() {
		return biWidth;
	}
	
	public int getHeight() {
		return biHeight;
	}
	
	public int getBiSize() {
		return biCount;
	}
	
}