package socketTCP_uploadVsDownload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;


public class Transport extends Thread{
	private Socket socket; 
	DataInputStream dis;
	DataOutputStream dos;
	String saveServerDirDefault = "upload";
	String saveClientDirDefault = "download";
	String message = "";
	
    public Transport(Socket socket) throws IOException {
    	this.socket = socket;
    	dis = new DataInputStream(socket.getInputStream());
    	dos = new DataOutputStream(socket.getOutputStream());
	}
    
    @Override
    public void run() {
    	try {
			dos.writeUTF("Welcome: \n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	while (true) {
    		String request;
			try {
				request = dis.readUTF();
				if ("QUIT".equalsIgnoreCase(request)) {
					message = "Thanks! Goodbye and see you again...";
					dos.writeUTF(request);
					dos.flush();
					break;
				}
				StringTokenizer tokenizer = new StringTokenizer(request, " ");
				String cmd = tokenizer.nextToken().toUpperCase();
				switch (cmd) {
				case "SEND": {
					String sourceFile = tokenizer.nextToken();
					String saveFileWithName = tokenizer.nextToken();
					if(tokenizer.hasMoreTokens()){
						dos.writeUTF("-11");dos.flush();break;
					}
					dos.writeUTF(saveClientDirDefault);dos.flush();
					long fileSize = dis.readLong();
					fileReceive(saveFileWithName, fileSize);
					message = "Receive success!";
					break;
				}
				case "GET":
					String sfName = tokenizer.nextToken();
					String dfName = tokenizer.nextToken();
					if(tokenizer.hasMoreTokens()){
						dos.writeUTF("-11");dos.flush();break;
					}
					File sf = new File(saveServerDirDefault+File.separator+sfName);
					if(!sf.exists()) {
						dos.writeUTF("-1");dos.flush();break;
					}
					dos.writeUTF(saveClientDirDefault);dos.flush();
					fileSend(sf);
					message = "Send success!";
					break;
				case "SET_SERVER_DIR":
					saveServerDirDefault=tokenizer.nextToken();
					if(tokenizer.hasMoreTokens()){
						dos.writeUTF("-11");dos.flush();break;
					}
					message = "Changed save directory from C://dest to "+ saveServerDirDefault+" success!";
					break;
				case "SET_CLIENT_DIR":
					saveClientDirDefault=tokenizer.nextToken();
//					if(st.hasMoreTokens()){
//						dos.writeUTF("-11");dos.flush();break;
//					}
					message = "Changed save directory from C://source to "+ saveClientDirDefault+" success!";
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + cmd);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }

	private void fileSend(File sf) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sf));
		dos.writeLong(sf.length()); dos.flush();
		int data;
		while ((data = bis.read())!=-1) {
			dos.write(data);
			dos.flush();
		}
	}

	private void fileReceive(String saveFileWithName, long fileSize) throws IOException {
		File f = new File(saveServerDirDefault+File.separator+saveFileWithName);
		BufferedOutputStream bos;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(f));
			dos.writeInt(0);
			dos.flush();
			for (int i = 0; i < fileSize; i++) {
				bos.write(dis.read());
				bos.flush();
			}
			bos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
