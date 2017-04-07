import java.io.*;
import java.net.*;

public class ChatServer {
	boolean started = false;
	ServerSocket ss = null;

	public static void main(String args[]) {
		new ChatServer().start();
	}

	public void start() {
		try {
			ss = new ServerSocket(8888);
			started = true;//端口连接成功后
		} catch (BindException e) {
			System.out.println("端口使用中");
			System.out.println("请关闭相关程序");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			//端口连接成功后处理客户端的连接
			while (started) {
				Socket s = ss.accept();
				//客户端连接后new一个线程处理
				Client c = new Client(s);
				System.out.println("a client connected");
				new Thread(c).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ss.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class Client implements Runnable {

		private DataInputStream dis = null;
		private Socket s = null;
		private boolean bConnect = false;

		public Client(Socket s) {
			this.s = s;
			try {
				dis = new DataInputStream(s.getInputStream());
				bConnect = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				while (bConnect) {
					String str = dis.readUTF();
					System.out.println(str);
				}
			} catch (EOFException e) {
				System.out.println("Client closed");
			} catch (IOException e) {
				e.printStackTrace();

			} finally {
				try {
					if (dis != null)
						dis.close();
					if (s != null)
						s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

}
