package ch26_socket.simpleGUI.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

import com.google.gson.Gson;

import ch26_socket.simpleGUI.server.dto.RequestBodyDto;
import ch26_socket.simpleGUI.server.dto.SendMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConnectedSocket extends Thread {
	
	private final Socket socket;
	
	@Override
	public void run() {
		
		while(true) {
			try {
				BufferedReader bufferedReader = 
						new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String requestBody = bufferedReader.readLine();
				
				requestController(requestBody);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void requestController(String requestBody) {
		Gson gson = new Gson();
		RequestBodyDto<?> requestBodyDto = gson.fromJson(requestBody, RequestBodyDto.class);
		
		switch(requestBodyDto.getResource()) {
			case "sendMessage" : 
				SendMessage sendMessage = (SendMessage) requestBodyDto.getBody();
				
				if(Objects.isNull(sendMessage.getToUsername())) {
					SimpleGUIServer.connectedSocketList.forEach(con -> {
						RequestBodyDto<String> showMessageDto = 
								new RequestBodyDto<String>("showMessage", sendMessage.getFromUsername() + ": " + sendMessage.getMessageBody());
						ServerSender.getInstance().send(con.socket, showMessageDto);
					});
				}
				
				break;
		}
	}
}
















