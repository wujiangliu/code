package wjl.java.base.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;


public class ImageUtil {
    /**
     * 拼接图片
     *
     * @param isVertical true:垂直拼接 false:水平拼接
     * @param imageUrls 本地和网络url都可以
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public static BufferedImage spliceImage( boolean isVertical, String... imageUrls )
            throws MalformedURLException, IOException {
        int           imageCount  = imageUrls.length;
        BufferedImage targetImage = null;
        if ( imageCount <= 0 ){
            return targetImage;
        }
        if ( imageCount < 1 ){
            String imageUrl = imageUrls[0];
            if ( imageUrl.startsWith( "http" ) ){
                targetImage = ImageIO.read( new URL( imageUrl ) );
            } else{
                targetImage = ImageIO.read( new File( imageUrl ) );
            }
            return targetImage;
        }
        int                 totalHeight = 0;
        int                 totalWidth  = 0;
        int                 maxHeight   = 0;
        int                 maxWidth    = 0;
        List<BufferedImage> imageList   = new ArrayList<>();
        for ( String imageUrl : imageUrls ){
            BufferedImage image = null;
            if ( imageUrl.startsWith( "http" ) ){
                image = ImageIO.read( new URL( imageUrl ) );
            } else{
                image = ImageIO.read( new File( imageUrl ) );
            }
            int h = image.getHeight();
            int w = image.getWidth();
            if ( maxHeight < h ){
                maxHeight = h;
            }
            if ( maxWidth < w ){
                maxWidth = w;
            }
            totalHeight += h;
            totalWidth += w;
            imageList.add( image );
        }
        if ( isVertical ){ // 垂直方向合并
            targetImage = new BufferedImage( maxWidth, totalHeight, BufferedImage.TYPE_INT_RGB );
            int move = 0;
            for ( BufferedImage image : imageList ){
                int h         = image.getHeight();
                int w         = image.getWidth();
                int levelMove = 0;
                if ( w < maxWidth ){
                    levelMove = (maxWidth - w) / 2;
                }
                int[] imageArray = new int[w * h];
                imageArray = image.getRGB( 0, 0, w, h, imageArray, 0, w );
                targetImage.setRGB( levelMove, move, w, h, imageArray, 0, w );
                move += h;
            }
        } else{// 水平方向合并
            targetImage = new BufferedImage( totalWidth, maxHeight, BufferedImage.TYPE_INT_RGB );
            int move = 0;
            for ( BufferedImage image : imageList ){
                int h            = image.getHeight();
                int w            = image.getWidth();
                int verticalMove = 0;
                if ( h < maxHeight ){
                    verticalMove = (maxHeight - h) / 2;
                }
                int[] imageArray = new int[w * h];
                imageArray = image.getRGB( 0, 0, w, h, imageArray, 0, w );
                targetImage.setRGB( move, verticalMove, w, h, imageArray, 0, w );
                move += w;
            }
        }
        return targetImage;
    }
}
