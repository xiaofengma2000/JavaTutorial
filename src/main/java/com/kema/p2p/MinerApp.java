package com.kema.p2p;

import com.kema.Coin.CoinBlock;
import com.kema.Coin.CoinChain;
import com.kema.Coin.Person;
import net.tomp2p.peers.Number160;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class MinerApp {

    public static final Number160 CHAIN_KEY = Number160.createHash("ROOT_CHAIN");

    CoinChain chain;

    public MinerApp(ClientPeer myPeer) {
        this.myPeer = myPeer;
        person = new Person();

        Executors.newSingleThreadExecutor().submit(new Runnable() {
//        ForkJoinPool.commonPool().execute(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    try
                    {
                        Thread.sleep(20000);
                        //check if there is a newer chain
                        CoinChain cc = (CoinChain)get(CHAIN_KEY);
                        refreshChain(cc);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private synchronized void refreshChain(CoinChain cc){
        if(this.chain == null){
            chain = cc;
        } else {
            if(cc.size() >= chain.size() && cc.isChainValid()){
                chain = cc;
                System.out.println("Block Chain refreshed : ");
                chain.printInfo();
            } else {
                try {
                    send(CHAIN_KEY, chain);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void send(Number160 key, Object value) throws IOException, InterruptedException {
        myPeer.send(key, value);
    }

    public Object get(Number160 key) throws IOException, ClassNotFoundException, InterruptedException {
        return myPeer.get(key);
    }

    public void stop() {
        myPeer.stop();
    }

    ClientPeer myPeer;
    Person person;

    public static final void main(String[] args){

        try
        {
            //1 start miner 1
            MinerApp m1 = new MinerApp(new ClientPeer(4001));
            //2 create the chain
            CoinBlock b1 = new CoinBlock("root", m1.person.publicKey);
            CoinBlock b2 = new CoinBlock(b1.getHash(), m1.person.publicKey);
            CoinBlock b3 = new CoinBlock(b2.getHash(), m1.person.publicKey);
            CoinChain cc = new CoinChain();
            cc.addCoin(b1);
            cc.addCoin(b2);
            cc.addCoin(b3);
            m1.send(CHAIN_KEY, cc);

            //3. start miner 2
            MinerApp m2 = new MinerApp(new ClientPeer(4002));
            CoinChain cc2 = (CoinChain) m2.get(CHAIN_KEY);
            cc.printInfo();




        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}
