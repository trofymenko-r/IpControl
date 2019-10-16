package com.example.ipcontrol.ipcontrol;

import android.os.AsyncTask;
import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ReceiveUdpTask extends AsyncTask<Void, DatagramPacket, Void> {
    protected IpControl context = null;
    private int mControlPort;
    DatagramSocket clientSocket;
    byte[] receiveData;

    public ReceiveUdpTask(IpControl _context, int controlPort) {
        context = _context;
        mControlPort = controlPort;
        receiveData = new byte[1024];
        try {
            clientSocket = new DatagramSocket(mControlPort, InetAddress.getByName("0.0.0.0"));
        } catch (Exception e) {
            Log.d("UDPSender", e.getMessage());
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        while (true) {
            try {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                //byte[] Data = receivePacket.getData();

                publishProgress(receivePacket);
            } catch (Exception e) {
                Log.d("UDPSender", e.getMessage());
            }
        }

        //return null;
    }

    @Override
    protected void onProgressUpdate(DatagramPacket... packets) {
        super.onProgressUpdate(packets);
        if (context != null) {
            for (DatagramPacket packet : packets) {
                context.UdpReceive(packet);
            }
        }

//        context.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                for (DatagramPacket packet : packets) {
//                    context.UdpReceive(packet);
//                }
//            }
//        });
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        clientSocket.close();

    }
}
