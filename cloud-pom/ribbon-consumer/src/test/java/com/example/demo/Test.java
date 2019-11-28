package com.example.demo;

import com.google.common.collect.Lists;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.Server;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by mc on 2018/5/25.
 */
public class Test {

    private static int count = 0;

    public static void rec() {
        count++;
        rec();
    }

    public static void main(String[] args) {
        Socket socket = null;
        InetSocketAddress remoteUrl = new InetSocketAddress("119.23.114.82", 6666);
        String result = "";
        while (true) {
            try {
                result = "";
                socket = new Socket();
                long begin = System.currentTimeMillis();
                socket.connect(remoteUrl, 100);
                socket.close();
                long end = System.currentTimeMillis();
                result = (end - begin) + "ms";
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(result);
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    public static void main23(String[] args) {
        PingUrl pingUrl = new PingUrl(false, "");
        boolean alive;
        while (true) {
            alive = pingUrl.isAlive(new Server("119.23.114.82", 6666));
            System.out.println(alive);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main2(String[] args) {
        try {
            rec();
        } catch (Throwable e) {
            System.out.println("deep:" + count);
            e.printStackTrace();
        }
    }


    public static void main15(String[] args) {
        int a = -10;
        for (int i = 0; i < 32; i++) {
            int t = (a & 0x8000_0000 >>> i) >>> (31 - i);
            System.out.print(t);
        }
    }
}
