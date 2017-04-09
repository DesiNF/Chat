import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
	boolean started = false;
	ServerSocket ss = null;

	List<Client> clients = new ArrayList<>();

	public static void main(String args[]) {
		new ChatServer().start();
	}

	public void start() {
		try {
			ss = new ServerSocket(8888);
			started = true;// 端口连接成功后
		} catch (BindException e) {
			System.out.println("端口使用中");
			System.out.println("请关闭相关程序");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			// 端口连接成功后处理客户端的连接
			while (started) {
				Socket s = ss.accept();
				// 客户端连接后new一个线程处理
				Client c = new Client(s);
				System.out.println("a client connected");
				new Thread(c).start();
				clients.add(c);
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
		DataOutputStream dos = null;
		private Socket s = null;
		private boolean bConnect = false;

		public Client(Socket s) {
			this.s = s;
			try {
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
				bConnect = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public void send(String str) {
			try {
				dos.writeUTF(str);
			}  catch (SocketException e) {
				clients.remove(this);
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void run() {
			try {
				while (bConnect) {
					String str = dis.readUTF();
					System.out.println(str);
					for (int i = 0; i < clients.size(); i++) {
						Client c = clients.get(i);
						c.send(str);
					}
				}
			} catch (EOFException e) {
				System.out.println("Client closed");
			} catch (IOException e) {
				e.printStackTrace();

			} finally {
				try {
					if (dis != null)
						dis.close();
					if (dos != null)
						dos.close();
					if (s != null)
						s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

}
