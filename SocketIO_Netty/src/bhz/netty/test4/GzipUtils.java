package bhz.netty.test4;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipUtils {

	public static byte[] gzip(byte[] data) throws Exception{
		//构造一个byte输出流，用来缓存数据，然后提取成byte[]
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(bos);
		gzip.write(data);
		gzip.finish();
		gzip.close();
		byte[] zipData = bos.toByteArray();
		bos.flush();
		bos.close();
		return zipData;
	}

	
	public static byte[] unGzip(byte[] data) throws Exception{
		//构造一个byte输入流，将传入的byte[]数据转成输入流
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		GZIPInputStream gzip = new GZIPInputStream(bis);
		byte[] readBuf = new byte[1024];
		int num = -1;
		//构造一个byte输出流，用来缓存数据，然后提取成byte[]
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while((num = gzip.read(readBuf, 0, readBuf.length)) != -1){
			bos.write(readBuf, 0, num);
		}
		gzip.close();
		bis.close();
		byte[] unZipData = bos.toByteArray();
		bos.flush();
		bos.close();
		return unZipData;
	}
	
	public static void main(String[] args) throws Exception {
		String path = System.getProperty("user.dir") + File.separatorChar + "source" + File.separatorChar + "20160107001.mp4";
		File file = new File(path);
		if( !file.exists() ){
			System.out.println("This file + (" + path + ") not exist!");
			return;
		}
		FileInputStream in = new FileInputStream(file);
		byte[] fileData = new byte[in.available()];
		in.read(fileData, 0, fileData.length);
		in.close();
		
		System.out.println("原数据长度为：" + fileData.length);
		
		byte[] gZipData = GzipUtils.gzip(fileData);
		
		System.out.println("压缩后的数据长度为：" + gZipData.length);
		
		byte[] unGzipData = GzipUtils.unGzip(gZipData);
		
		System.out.println("解压后的数据长度为：" + unGzipData.length);
		
		String outPath1 = System.getProperty("user.dir") + File.separatorChar + "receive" + File.separatorChar + "20160107001.zip";
		FileOutputStream out1 = new FileOutputStream(outPath1);
		out1.write(gZipData);
		out1.flush();
		out1.close();
		
		String outPath2 = System.getProperty("user.dir") + File.separatorChar + "receive" + File.separatorChar + "20160107001.mp4";
		FileOutputStream out2 = new FileOutputStream(outPath2);
		out2.write(unGzipData);
		out2.flush();
		out2.close();
	}
}
