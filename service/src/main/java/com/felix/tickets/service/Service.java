package com.felix.tickets.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Service {

	private Map<Integer,Client> clients = new HashMap<Integer,Client>();
	

	public Map<Integer, Client> getClients() {
		return clients;
	}

	public void start() {
		ServerSocket serverSocket;
		try {
			Integer port = 4774;
			serverSocket = new ServerSocket(port);
			while (true) {
				Socket clientSocket = serverSocket.accept();
				Client client = new Client(clientSocket, this);
		//		log.info("starting new client thread " + client.getId());
				client.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void removeClient(Client client) {
		this.clients.remove(client.getUser().getId());
	}
}
