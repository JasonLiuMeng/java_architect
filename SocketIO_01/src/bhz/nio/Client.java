package bhz.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Client implements Runnable{
	
	private ByteBuffer writeBuf = ByteBuffer.allocate(1024);
	private ByteBuffer readBuf = ByteBuffer.allocate(1024);
	private Selector selector;
	
	private String IP = "127.0.0.1";
	private int PORT = 8765;
	private InetSocketAddress address;

	public Client() {
		// TODO Auto-generated constructor stub
		try {
			this.address = new InetSocketAddress(IP,PORT);
			this.selector = Selector.open();
			SocketChannel clientChannel = SocketChannel.open();
			clientChannel.configureBlocking(false);
			clientChannel.connect(address);
			clientChannel.register(selector, SelectionKey.OP_CONNECT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				this.selector.select();
				Iterator<SelectionKey> skIterator = this.selector.selectedKeys().iterator();
				while(skIterator.hasNext()){
					SelectionKey nextKey = skIterator.next();
					skIterator.remove();
					if( nextKey.isValid() ){
						if( nextKey.isAcceptable() ){
							this.accept(nextKey);
						}else if(nextKey.isReadable()){
							this.read(nextKey);
						}else if(nextKey.isWritable()){
							this.write(nextKey);
						}else if(nextKey.isConnectable()){
							this.connect(nextKey);
						}
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void connect(SelectionKey key){
		System.out.println("Client Connect...");
		SocketChannel sc = (SocketChannel)key.channel();
		try {
			if( sc.isConnectionPending() ){
				sc.finishConnect();
			}
			sc.configureBlocking(false);
             //在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限
			sc.register(this.selector, SelectionKey.OP_READ);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}	
	
	private void accept(SelectionKey key){
		System.out.println("Client Accept...");
	}
	
	private void write(SelectionKey key){
		System.out.println("Client Write...");
		SocketChannel sc = (SocketChannel)key.channel();
		try {
			//定义一个字节数组，然后使用系统录入功能：
			byte[] bytes = new byte[1024];
			System.in.read(bytes);
			//清空缓冲区数据
			writeBuf.clear();
			//把数据放到缓冲区中
			writeBuf.put(bytes);
			//对缓冲区进行复位
			writeBuf.flip();
			//写出数据
			sc.write(writeBuf);
			sc.register(this.selector, SelectionKey.OP_READ);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void read(SelectionKey key){
		System.out.println("Client Read...");
		SocketChannel sc = (SocketChannel)key.channel();
		try {
			this.readBuf.clear();
			sc.read(this.readBuf);
			this.readBuf.flip();
			byte[] bytes = new byte[this.readBuf.remaining()];
			this.readBuf.get(bytes);
			System.out.println("Client打印：" + new String(bytes).trim());
			sc.register(this.selector, SelectionKey.OP_WRITE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//需要一个Selector 
	public static void main(String[] args) {
		new Thread(new Client()).start();
	}
	
}
