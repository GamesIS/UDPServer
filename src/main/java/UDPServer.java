import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;


class UDPServer
{
    static DatagramSocket serverSocket;
    static int packetSize;
    static  int numFloats;
    static  ByteOrder be;
    static byte[] receiveData;
    static byte[] sendData;
    static  float[] msg;

    public static void main(String args[]) throws Exception
    {

        serverSocket = new DatagramSocket(9876);
        packetSize = 64;
        numFloats = (packetSize / 4) - 1;
        be = ByteOrder.LITTLE_ENDIAN;

        receiveData = new byte[4096];

        while(true)
        {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String str =  new String(receivePacket.getData(), 0, receivePacket.getLength(), Charset.forName("UTF-8"));

            ObjectMapper mapper = new ObjectMapper();
            Object jsonObject = mapper.readValue(str, Object.class);
            System.out.println(getCutrrentTime() + " Получено сообщение" + "\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject));
        }
    }

    static Timestamp getCutrrentTime(){
        Date date= new Date();
        long time = date.getTime();
        return new Timestamp(time);
    }


}