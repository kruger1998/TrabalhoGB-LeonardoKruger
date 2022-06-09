package leonardokruger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class PrincipalServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        // Step 1 : Create a socket to listen at port 1234
        DatagramSocket datagramSocketToSend = new DatagramSocket();
        DatagramSocket datagramSocketToReceive = new DatagramSocket(1234);
        byte[] receive = new byte[65535];
        byte buf[] = null;
        InetAddress clientIp = InetAddress.getLocalHost();

        DatagramPacket DpReceive = null;
        while (true)
        {
            DpReceive = new DatagramPacket(receive, receive.length);
            datagramSocketToReceive.receive(DpReceive);

            String clientCommandResponse = executeCommandLine(decode(receive).toString());

            buf = clientCommandResponse.getBytes();
            DatagramPacket DpSend = new DatagramPacket(buf, buf.length, clientIp, 1235);
            datagramSocketToSend.send(DpSend);

            System.out.println("Response encaminhado ao client: " + clientIp.getHostAddress());

            receive = new byte[65535];

            if (false)
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

    private static String executeCommandLine(String command) throws InterruptedException {
        try {
            Process proc = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            String line = "";
            String finalStructure = "";
            while((line = reader.readLine()) != null) {
                finalStructure = finalStructure.concat(line).concat("\n");
            }
            proc.waitFor();

            finalStructure = finalStructure.concat("\n --- Liberado para insercao de novo comando --- \n");

            return finalStructure;

        } catch (IOException ex) {
            return "Nao foi possivel executar o comando -> " + command + "\n" +
                    "\n --- Liberado para insercao de novo comando --- \n";
        }
    }
}