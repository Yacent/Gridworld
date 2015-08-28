import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;


public class BMPImageIOTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testMyReadWidth() throws IOException {
		BMPImageIO image = new BMPImageIO();
		Image imgTest = image.myRead("1.bmp");
		image.myWrite(imgTest, "goal_1_width");
		image.myRead("goal_1_width.bmp");
		assertEquals(400, image.getWidth());
	}

	@Test
	public void testMyReadHeight() throws IOException {
		BMPImageIO image = new BMPImageIO();
		Image imgTest = image.myRead("1.bmp");
		image.myWrite(imgTest, "goal_1_height");
		image.myRead("goal_1_height.bmp");
		assertEquals(400, image.getHeight());
	}
	
	@Test
	public void testMyReadPixel() throws IOException {		
		BMPImageIO image = new BMPImageIO();
		Image imgOrigin = image.myRead("1.bmp");
		image.myWrite(imgOrigin, "goal_1_pixel");
		Image imgWrite =  image.myRead("goal_1_pixel.bmp");
		BufferedImage bfImgOrigin =  toBufferedImage(imgOrigin);
		BufferedImage bfImgWrite =  toBufferedImage(imgWrite);
		
		
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				assertEquals(bfImgOrigin.getRGB(i, j),
						bfImgWrite.getRGB(i, j));
			}
		}
	}
	
	
	private BufferedImage toBufferedImage(Image image) {
		image = new ImageIcon(image).getImage();
		 
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			int transparency = Transparency.OPAQUE;

			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(
					image.getWidth(null), image.getHeight(null), transparency);
		} catch (HeadlessException e) {
			e.printStackTrace(); 
		}
	 
		if (bimage == null) {
			int type = BufferedImage.TYPE_INT_RGB;
			bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
		}
	 
	    // Copy image to buffered image
		Graphics g = bimage.createGraphics();
	 
	    // Paint the image onto the buffered image
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return bimage;
	}
	

}
