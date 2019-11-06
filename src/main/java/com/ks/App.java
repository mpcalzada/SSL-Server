package com.ks;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 */
public class App
{

  public static void main(String[] args)
  {
    int port = 8443;
    ServerSocketFactory factory = SSLServerSocketFactory.getDefault();
    try (ServerSocket listener = factory.createServerSocket(port))
    {
      SSLServerSocket sslListener = (SSLServerSocket) listener;
      sslListener.setNeedClientAuth(false);
      sslListener.setEnabledCipherSuites(new String[]{"TLS_DHE_DSS_WITH_AES_256_CBC_SHA256"});
      sslListener.setEnabledProtocols(new String[]{"TLSv1.2"});

      try (Socket socket = sslListener.accept())
      {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        while ((line = input.readLine()) != null)
        {
          //out.println("Hello World!");
          System.out.println(line);
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
