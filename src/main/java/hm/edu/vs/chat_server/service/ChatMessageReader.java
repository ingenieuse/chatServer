/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hm.edu.vs.chat_server.service;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.google.gson.Gson;
import hm.edu.vs.chat_server.service.ChatPDU;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@MessageDriven(name = "MessageReader", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "queue/testqueue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class ChatMessageReader implements MessageListener {
	@Inject
	ChatMessageEJB chatController;
	protected ChatPDU chatPDU = null;
	protected Gson gson = new Gson();
	
	@Override
	public void onMessage(Message m) {
		System.out.println("Unwrap the message put POJO");
		
		// readChatPDU
		TextMessage message = TextMessage.class.cast(m);
		try {
			chatPDU = gson.fromJson(message.getText(), ChatPDU.class);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("ERROR: convert json to chatPDU");
		} finally {
			System.out.println("CHATPDU:");
			System.out.println(chatPDU.toString());
		}
		try {
			System.out.println(message.getText());
		} catch (JMSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			
			chatController.saveChatMessage(message.getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// TODO - Setting CHATPDU Properperties like servertime, serverthreadname etc.
	private void setChatPDUProperties(){
		
	}
	
	// TODO - Sending the ChatPDU as json
	private void sendChatPDU(ChatPDU cPDU){
		
	}
	
	// TODO - update the trace db entries
	private void updateTraceDB(){
			
	}

	// TODO - update the Count db entries
	private void updateCountDB(){
		
	}
}
