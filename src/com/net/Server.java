package com.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.pb.Mobile;
import com.pb.Mobile.MobilePhone;

public class Server {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		int port = 8888;
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			while (true) {
				Socket socket = serverSocket.accept();
				System.err.println("accept one connect : " + socket.getRemoteSocketAddress());
				InputStream inputStream = socket.getInputStream();
				int bodyLength = inputStream.read();
				byte[] message = new byte[bodyLength];
				inputStream.read(message);
				MobilePhone mobilePhone = Mobile.MobilePhone.parseFrom(message);
				System.out.println("receive message :\n" + mobilePhone);
				// 关闭客户端连接
				inputStream.close();
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
