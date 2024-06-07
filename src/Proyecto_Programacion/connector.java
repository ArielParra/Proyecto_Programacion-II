package Proyecto_Programacion;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class connector {
    public static void Iniciar(){
        final int PORT = 12345;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor esperando conexiones en el puerto " + PORT);

            // Aceptar conexiones de clientes
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado desde " + clientSocket.getInetAddress().getHostAddress());

            // Cerrar el socket del cliente
            clientSocket.close();
        } catch (IOException ex) {
            System.out.println("Error en el servidor: " + ex.getMessage());
        }
    }
}
