package com.example.ipcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.text.method.ScrollingMovementMethod;
import com.example.ipcontrol.ipcontrol.IpControl;

import java.net.DatagramPacket;

public class MainActivity extends AppCompatActivity {
    TextView textViewConsole;
    TextOut console;
    IpControl ipControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewConsole = (TextView) findViewById(R.id.console);
        textViewConsole.setMovementMethod(new ScrollingMovementMethod());
        console = new TextOut(textViewConsole);

        ipControl = new IpControl(this);

        console.Out("local:\t" + ipControl.localIp + " " + ipControl.netMask + " " + ipControl.gateWay);
    }

    public void ReceiveUdpData(DatagramPacket packet) {
        console.Out("rec packet");
    }
}
