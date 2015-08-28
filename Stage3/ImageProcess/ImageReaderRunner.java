// ImagaReaderRunner.java
import imagereader.Runner;
 
public class ImageReaderRunner {
    public static void main(String[] args) {
        BMPImageIO imageioer = new BMPImageIO();
        BMPImageProcessor processor = new BMPImageProcessor();
        Runner.run(imageioer, processor);
    }
}
