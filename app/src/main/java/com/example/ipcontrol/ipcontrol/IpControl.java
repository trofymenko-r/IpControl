package com.example.ipcontrol.ipcontrol;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.example.ipcontrol.MainActivity;
import com.example.ipcontrol.Utils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpControl {
    protected MainActivity parent = null;
    ReceiveUdpTask UdpReceiver;

    private int mControlPort;
    public String localIp;
    public String netMask;
    public String gateWay;

    public IpControl(MainActivity context) {
        parent = context;
        mControlPort = 16591;
        Scan();
        UdpReceiver = new ReceiveUdpTask(this, mControlPort);
        UdpReceiver.execute();
    }

    public void UdpReceive(final DatagramPacket packet) {
        if (parent == null)
            return;

        parent.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                parent.ReceiveUdpData(packet);
            }
        });
    }

    public void Scan() {
        localIp = Utils.getIPAddress(true);

        DhcpInfo d;
        WifiManager wifii;
        wifii= (WifiManager)parent.getSystemService(Context.WIFI_SERVICE);
        d = wifii.getDhcpInfo();

        //localIp = Utils.intToIp(d.ipAddress);

        //localIp = Utils.GetDeviceipMobileData();
        //try {
          //  localIp = InetAddress.getLocalHost().getHostAddress();
        //} catch (Exception e) {
       //     Log.d("IpControl", e.getMessage());
        //}

//        InetAddress address = InetAddress.getLocalHost();
//        String hostIP = address.getHostAddress() ;

        netMask = Utils.intToIp(d.netmask);
        gateWay = Utils.intToIp(d.gateway);

        //InetAddress netMask_ = InetAddress.getByName(netMask);
        //netMask_.getHostAddress()

    }

    public void Send(byte[] data) {

    }
}