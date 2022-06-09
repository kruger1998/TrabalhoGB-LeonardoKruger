package leonardokruger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        DatagramSocket datagramSocketToSend = new DatagramSocket();
        DatagramSocket datagramSocketToReceive = new DatagramSocket(1235);
        InetAddress ip = InetAddress.getLocalHost();
        byte[] receive = new byte[65535];

        byte buf[] = null;
        DatagramPacket DpReceive = null;
        boolean isFirstTime = true;

        while (true)
        {
            if (!isFirstTime) {
                DpReceive = new DatagramPacket(receive, receive.length);
                datagramSocketToReceive.receive(DpReceive);
                System.out.println("\nMensagem recebida do servidor: " + decode(receive));
            }

            if (isFirstTime) {
                System.out.println("\n --- Liberado para inserção de novo comando ou 'sair' --- \n");
                isFirstTime = false;
            }

            String inp = sc.nextLine();
            buf = inp.getBytes();
            DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, 1234);
            datagramSocketToSend.send(DpSend);

            if (inp.equals("sair"))
                break;
        }
    }
    public static StringBuilder decode(byte[] a)
    {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0)
        {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }

}
