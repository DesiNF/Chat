import java.io.*;
import java.net.*;

public class ChatServer {

	public static void main(String args[]) {
		boolean started = false;
		ServerSocket ss=null;
		DataInputStream dis=null;
		Socket s =null;
		
		try {
			ss = new ServerSocket(8888);
		}catch(BindException e){
			System.out.println("端口使用中");
			System.out.println("请关闭相关程序");
			System.exit(0);
		}catch(IOException e){
			e.printStackTrace();
		}
		try{
			started = true;
			while (started) {
				boolean bConnect = false;
				s = ss.accept();
				bConnect = true;
				System.out.println("a client connected");
				dis=new DataInputStream(s.getInputStream());
				while (bConnect) {
					String str = dis.readUTF();
					System.out.println(str);
				}
				//dis.close();
			}
		}catch(EOFException e){
			System.out.println("Client closed");
		}catch (IOException e) {
			e.printStackTrace();
			
		}finally{
			try {
				if(dis!=null)dis.close();
				if(s!=null)s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
