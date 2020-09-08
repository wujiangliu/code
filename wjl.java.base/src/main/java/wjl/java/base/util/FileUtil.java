
package wjl.java.base.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class FileUtil {
    /**
     * @description 读取数据流
     * @param is
     * @return
     */
    private static byte[] readInputStream( InputStream is ) {
        ByteArrayOutputStream baos   = new ByteArrayOutputStream();
        byte[]                buffer = new byte[1024];
        int                   length = -1;
        try{
            while ((length = is.read( buffer )) != -1){
                baos.write( buffer, 0, length );
            }
            baos.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
        byte[] data = baos.toByteArray();
        try{
            is.close();
            baos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 读取文件到byte数组中
     * 
     * @param filePath
     * @return
     * @throws FileNotFoundException
     */
    public static byte[] readFile( String filePath ) throws FileNotFoundException {
        File file = new File( filePath );
        if ( !file.exists() ){
            throw new FileNotFoundException( filePath );
        }
        FileInputStream fis = new FileInputStream( file );
        return readInputStream( fis );
    }

    /**
     * 读取大文件到byte数组中
     * 
     * @param filePath
     * @return
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static byte[] readBigFile( String filePath ) throws IOException {
        FileChannel fc = null;
        try{
            fc = (new RandomAccessFile( filePath, "r" )).getChannel();
            MappedByteBuffer byteBuffer = fc.map( MapMode.READ_ONLY, 0, fc.size() ).load();
            byte[]           result     = new byte[(int) fc.size()];
            if ( byteBuffer.remaining() > 0 ){
                byteBuffer.get( result, 0, byteBuffer.remaining() );
            }
            return result;
        } catch (IOException e){
            e.printStackTrace();
            throw e;
        } finally{
            try{
                fc.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
