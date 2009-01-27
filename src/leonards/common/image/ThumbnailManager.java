/*
 * Project: Leonards Common Libraries
 * This class is member of leonards.common.image
 * File: ThumbnailManager.java
 *
 * Property of Leonards / Mindpool
 * Created on May 6, 2006 (4:13:32 PM) 
 */
package leonards.common.image;

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * This class is the abstraction
 *
 * @author mariano
 */
public class ThumbnailManager {
	
	/**
	 * 
	 */
	private ThumbnailManager() {
		super();
	}
	
	/**
	 * Creates a new thumbnail file resizing and adjusting the quality of
	 * the original image.
	 * @param imagePath Path to the source image file which thumbnail will be created.
	 * @param thumbPath Path to the destination image file where the thumbnail
	 *                      will be created.
	 * @param thumbWidth Width in pixels of the new image. A ratio will be calculated with
	 *              width and height.
	 * @param thumbHeight Heighy in pixels of the new image. A ratio will be calculated with
	 *              height and width.
	 * @param quality The quality ratio of the new image (from 0 to 100)
	 * @throws ImageException In case the thumbnail cannot be created.
	 */
	public static void createThumbnail(String imagePath, String thumbPath, int thumbWidth, int thumbHeight, int quality) throws ImageException {
		BufferedOutputStream out = null;
		try {
			// load source image
			Image image = Toolkit.getDefaultToolkit().getImage(imagePath);
			MediaTracker mediaTracker = new MediaTracker(new Container());
			mediaTracker.addImage(image, 0);
			mediaTracker.waitForID(0);
			
			// determine thumbnail size from WIDTH and HEIGHT
			double thumbRatio = (double)thumbWidth / (double)thumbHeight;
			int imageWidth = image.getWidth(null);
			int imageHeight = image.getHeight(null);
			double imageRatio = (double)imageWidth / (double)imageHeight;
			if (thumbRatio < imageRatio) {
				thumbHeight = (int)(thumbWidth / imageRatio);
			} else {
				thumbWidth = (int)(thumbHeight * imageRatio);
			}
			// draw original image to thumbnail image object and
			// scale it to the new size on-the-fly
			BufferedImage thumbImage = new BufferedImage(thumbWidth, 
					thumbHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2D = thumbImage.createGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);
			
			// save thumbnail image to OUTFILE
			out = new BufferedOutputStream(new
					FileOutputStream(thumbPath));
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.
			getDefaultJPEGEncodeParam(thumbImage);
			quality = Math.max(0, Math.min(quality, 100));
			param.setQuality((float)quality / 100.0f, false);
			encoder.setJPEGEncodeParam(param);
			encoder.encode(thumbImage);
			
		} catch(InterruptedException ex) {
			throw new ImageException("Could not create thumbnail. Media tracker waitForID failed.", ex);
		} catch(FileNotFoundException ex) {
			throw new ImageException("Could not create file [" + thumbPath + "]. Path to file not found.", ex);
		} catch(IOException ex) {
			throw new ImageException("Could not write file [" + thumbPath + "].", ex);
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch(IOException ex) {
					//:TODO: What???
				}
			}
		}
	}
	
	
}
