
package wjl.java.base.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class FileUtil {
    public static void main( String[] args ) throws IOException {
        byte[] data = readFile( "D:\\work\\资料\\文档\\博阅资料\\测试资料\\第一节、与三角形有关的线段.pdf" );
        writeFileByBytes( data, "D:\\work\\资料\\文档\\博阅资料\\测试资料\\测试文件复制", "第一节、与三角形有关的线段_副本.pdf",
                false );
    }

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

    /**
     * 将byte数组写入文件
     * 
     * @param data
     * @param destDir
     * @param fileName
     * @param append
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void writeFileByBytes( byte[] data, String destDir, String fileName,
            boolean append ) throws FileNotFoundException, IOException {
        File dir = new File( destDir );
        if ( !dir.exists() && !dir.isDirectory() ){
            dir.mkdir();
        }
        try (OutputStream out = new BufferedOutputStream(
                new FileOutputStream( destDir + File.separator + fileName, append ) )){
            out.write( data );
        }
    }
}
