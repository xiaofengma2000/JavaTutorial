package com.kema.p2p;

import java.util.Random;

import net.tomp2p.connection.DiscoverNetworks;
import net.tomp2p.futures.FutureResponse;
import net.tomp2p.p2p.Peer;
import net.tomp2p.p2p.PeerMaker;
import net.tomp2p.peers.Number160;
import net.tomp2p.peers.PeerAddress;

public class MasterPeer {

    int port = 4000;

    public MasterPeer(int port) throws Exception{
        this.port = port;
        startServer();
    }

    public static final void main(String[]  args){
        try {
            new MasterPeer(4000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startServer() throws Exception {
        Random rnd = new Random(43L);
        Peer master = new PeerMaker(new Number160(rnd)).setPorts(4000).makeAndListen();

        //.bindings(b).start();
        System.out.println("Server started Listening to: " + DiscoverNetworks.discoverInterfaces(master.getBindings()));
        System.out.println("address visible to outside is " + master.getPeerAddress());
        while (true) {
            for (PeerAddress pa : master.getPeerBean().getPeerMap().getAll()) {
                System.out.println("PeerAddress: " + pa);
//                FutureChannelCreator fcc = master.getConnectionBean().getConnectionReservation().reserve(1);
//                fcc.awaitUninterruptibly();
//                ChannelCreator cc = fcc.getChannelCreator();

                FutureResponse fr1 = master.ping().ping(pa.createSocketTCP(), false);
                fr1.awaitUninterruptibly();

                if (fr1.isSuccess()) {
                    System.out.println("peer online T:" + pa);
                } else {
                    System.out.println("offline " + pa);
                }

//                FutureResponse fr2 = master.pingRPC().pingUDP(pa, cc, new DefaultConnectionConfiguration());
//                fr2.awaitUninterruptibly();

//                cc.shutdown();

//                if (fr2.isSuccess()) {
//                    System.out.println("peer online U:" + pa);
//                } else {
//                    System.out.println("offline " + pa);
//                }
            }
            Thread.sleep(30000);
        }
    }

}
