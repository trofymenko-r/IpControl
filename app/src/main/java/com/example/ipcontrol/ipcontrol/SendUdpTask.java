package com.example.ipcontrol.ipcontrol;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SendUdpTask extends AsyncTask<EndPoint, Void, Void> {
    final private boolean sendingBytes;
    final private byte[] bytes;
    final private String str;
    private Context context = null;

    public SendUdpTask(byte[] bytes) {
        sendingBytes = true;
        this.bytes = bytes;
        this.str = null;
    }

    public SendUdpTask(String str) {
        sendingBytes = false;
        this.bytes = null;
        this.str = str;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static void SendUdp(EndPoint endPoint, byte[] array) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IpAddress = InetAddress.getByName(endPoint.ipAddress);
            byte[] sendData = array;
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IpAddress, endPoint.port);
            clientSocket.send(sendPacket);
            clientSocket.close();
        } catch (Exception e) {
            Log.d("UDPSender", e.getMessage());
        }
    }

    public static void SendUdp(EndPoint endPoint, String string) {
        SendUdp(endPoint, string.getBytes());
    }

    @Override
    protected Void doInBackground(EndPoint... endPoints) {
        for (EndPoint endPoint : endPoints) {
            if (sendingBytes) {
                SendUdp(endPoint, bytes);
            } else {
                SendUdp(endPoint, str);
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
//        if (context != null) {
//            //Toast.makeText(context, R.string.sent, Toast.LENGTH_SHORT).show();
//        }
    }
}
