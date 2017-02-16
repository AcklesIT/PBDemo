package com.net;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import com.google.protobuf.GeneratedMessageV3;
import com.pb.Mobile;
import com.pb.Userobject;

public class Client {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		String host = "127.0.0.1";
		int port = 8888;
		try {
			// 创建消息
			Mobile.HardWare.Builder hardWareBuilder = Mobile.HardWare.newBuilder();
			hardWareBuilder.setRam(200).setRom(100).setSize(300);
			Mobile.MobilePhone.Builder mobilePhoneBuilder = Mobile.MobilePhone.newBuilder();
			mobilePhoneBuilder.setBrand("Huawei")
			.setHardWare(hardWareBuilder)
			.addSoftware("Camera")
			.addSoftware("net");
			byte[] message = buildMessage(mobilePhoneBuilder);
			// 通过socket向服务端发送消息
			Socket socket = new Socket(host, port);
			OutputStream out = socket.getOutputStream();
			out.write(message);
			//
			Userobject.User.Builder userBuilder = Userobject.User.newBuilder();
			userBuilder.setUid(100);
			userBuilder.setName("屌爆了");
			message = buildMessage(userBuilder);
			out.write(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static byte[] buildMessage(GeneratedMessageV3.Builder<?> builder) {
		byte[] body = builder.build().toByteArray();
		int bodyLength = 1;
		byte[] message = new byte[body.length + bodyLength];
		message[0] = (byte) body.length;
		System.arraycopy(body, 0, message, 1, body.length);
		return message;
	}
	
}
