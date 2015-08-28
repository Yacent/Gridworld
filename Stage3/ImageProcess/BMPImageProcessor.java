import imagereader.IImageProcessor;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;


/**
 * useful interface information
 * http://www.cjsdn.net/Doc/JDK50/java/awt/image/RGBImageFilter.html
 * http://www.cjsdn.net/Doc/JDK60/java/awt/image/FilteredImageSource.html
 */

public class BMPImageProcessor implements IImageProcessor{
	
	/**
	 * the FilteredImageSource
	 * look for the API on oracle
	 * what we have to do is send the parameter and the method of filter
	 */
	public Image showChanelB(Image sourceImage) {
		return Toolkit.getDefaultToolkit().createImage(
				new FilteredImageSource(sourceImage.getSource(),
						new maskFilter(maskFilter.BLUE_MASK)));
	}

	public Image showChanelG(Image sourceImage) {
		return Toolkit.getDefaultToolkit().createImage(
				new FilteredImageSource(sourceImage.getSource(),
						new maskFilter(maskFilter.GREEN_MASK)));
	}

	public Image showChanelR(Image sourceImage) {
		return Toolkit.getDefaultToolkit().createImage(
				new FilteredImageSource(sourceImage.getSource(),
						new maskFilter(maskFilter.RED_MASK)));
	}

	public Image showGray(Image sourceImage) {
		return Toolkit.getDefaultToolkit().createImage(
				new FilteredImageSource(sourceImage.getSource(),
						new GrayFilter()));
	}
	
}

/**
 * define the method of filter
 * 
 * JAVA API of RGBImageFilter
 * class RedBlueSwapFilter extends RGBImageFilter {
   	   public RedBlueSwapFilter() {
       	// The filter's operation does not depend on the
        // pixel's location, so IndexColorModels can be
        // filtered directly.
        canFilterIndexColorModel = true;
       }

       public int filterRGB(int x, int y, int rgb) {
          return ((rgb & 0xff00ff00)
              | ((rgb & 0xff0000) >> 16)
              | ((rgb & 0xff) << 16));
          }
      }
 */
class maskFilter extends RGBImageFilter {

	private int mask;
	
	public static final int RED_MASK = 0xFFFF0000;
	public static final int GREEN_MASK = 0xFF00FF00;
	public static final int BLUE_MASK = 0xFF0000FF;
	
	public maskFilter(int mask) {
		this.mask = mask;
		
		/**
		 *  此布尔值指示是否可以接受用 filterRGB 方法的颜色过滤替代逐像素过滤，
		 *  将它应用于 IndexColorModel 对象的颜色表项。
		 */
		canFilterIndexColorModel = true;
	}
	
	
	/**
	 * 该方法将默认 RGB ColorModel 中的单个输入像素转换成单个输出像素。
	 * @return a filtered pixel in the default RGB color model
	 */
	public int filterRGB(int x, int y, int rgb) {
		return rgb & mask;
	}
	
}

/**
 * 将彩色图转换成灰度图，建议采用NTSC推荐的彩色图到灰度图的转换公式：
 * I = 0.299 * R + 0.587 * G + 0.114 * B，其中R,G,B分别为红、绿、蓝通道的颜色值。
 * 然后将三个色彩通道的颜色值改为这个值即可。这样，原来的彩色图像就变成了灰度图像了。
 */
class GrayFilter extends RGBImageFilter {
	
	// 0xFF R G B
	private int red_mask = 0x00FF0000;
	private int green_mask = 0x0000FF00;
	private int blue_mask = 0x000000FF;
	
	public GrayFilter() {
		/**
		 *  此布尔值指示是否可以接受用 filterRGB 方法的颜色过滤替代逐像素过滤，
		 *  将它应用于 IndexColorModel 对象的颜色表项。
		 */
		canFilterIndexColorModel = true;
	}
	
	public int filterRGB(int x, int y, int rgb) {
		// in order to calculate the value
		int red = (rgb & red_mask) >> 16;
		int green = (rgb & green_mask) >>8;
		int blue = (rgb & blue_mask);
		
		int gray = (int) (red * 0.299 + green * 0.587 + blue * 0.114);
		return (rgb& 0xFF000000) | (gray << 16) | (gray << 8) | gray;
	}
	
}
