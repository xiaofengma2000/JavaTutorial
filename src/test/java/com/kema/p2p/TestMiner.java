package com.kema.p2p;

import com.kema.blockcoin.CoinBlock;
import com.kema.blockcoin.CoinChain;
import org.junit.Test;

public class TestMiner {

    @Test
    public void testGetCoin() throws Exception{
        MinerApp minerSomeone = new MinerApp(new ClientPeer(5000));

        CoinChain cc = (CoinChain) minerSomeone.get(MinerApp.CHAIN_KEY);
        cc.printInfo();
        minerSomeone.stop();
    }

    @Test
    public void testAddBlock() throws Exception{
        MinerApp minerSomeone = new MinerApp(new ClientPeer(5000));

        CoinChain cc = (CoinChain) minerSomeone.get(MinerApp.CHAIN_KEY);
        cc.printInfo();

        CoinBlock cb = new CoinBlock(cc.getChainList().get(cc.size()-1).getHash(), minerSomeone.person.publicKey);
        cc.addCoin(cb);
        minerSomeone.send(MinerApp.CHAIN_KEY, cc);
        System.out.println("Chain updated : " + cc.isChainValid());
        cc.printInfo();
        minerSomeone.stop();
    }

}
