package com.qakki.rpc.client.config;

import com.qakki.rpc.Peer;
import com.qakki.rpc.common.utils.ReflectionUtils;
import com.qakki.rpc.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author qakki
 * @date 2021/1/16 8:26 下午
 */
@Slf4j
public class RandomTransportSelector implements TransportSelector {

    List<TransportClient> clients;

    public RandomTransportSelector() {
        clients = new ArrayList<>();
    }

    @Override
    public synchronized void init(List<Peer> peers, int count, Class<? extends TransportClient> clazz) {
        count = Math.max(count, 1);
        for (Peer peer : peers) {
            for (int i = 0; i < count; i++) {
                TransportClient client = ReflectionUtils.newInstance(clazz);
                client.connect(peer);
                log.info("connect:{}", peer);
                clients.add(client);
            }
        }
    }

    @Override
    public synchronized TransportClient select() {
        int i = new Random().nextInt(clients.size());
        return clients.remove(i);
    }

    @Override
    public synchronized void release(TransportClient client) {
        clients.add(client);
    }

    @Override
    public synchronized void close() {
        clients.forEach(TransportClient::close);
    }
}
