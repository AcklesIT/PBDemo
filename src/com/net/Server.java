package com.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.pb.Mobile;
import com.pb.Mobile.MobilePhone;
import com.pb.Userobject;
import com.pb.Userobject.User;

public class Server {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		int port = 8888;
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			System.err.println("server socket ready!");
			while (true) {
				Socket socket = serverSocket.accept();
				System.err.println("accept one connect : " + socket.getRemoteSocketAddress());
				 InputStream inputStream = socket.getInputStream();
				byte[] message = readMessage(inputStream);
				MobilePhone mobilePhone = Mobile.MobilePhone.parseFrom(message);
				System.out.println("receive message :\n" + mobilePhone);
				message = readMessage(inputStream);
				User user = Userobject.User.parseFrom(message);
				System.out.println("receive message :\n" + user);
				// 关闭客户端连接
				socket.getInputStream().close();
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static byte[] readMessage(InputStream inputStream) throws IOException {
		int bodyLength = inputStream.read();
		byte[] message = new byte[bodyLength];
		inputStream.read(message);
		return message;
	}

}
