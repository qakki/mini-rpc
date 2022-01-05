package com.qakki.rpc.transport.netty;

import com.qakki.rpc.Peer;
import com.qakki.rpc.Request;
import com.qakki.rpc.Response;
import com.qakki.rpc.common.codec.json.JSONDecoder;
import com.qakki.rpc.common.codec.json.JSONEncoder;
import com.qakki.rpc.transport.ResponseHandler;
import com.qakki.rpc.transport.TransportClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * todo
 *
 * @author qakki
 * @date 2022/1/5 12:24 上午
 */
@Slf4j
public class NettyTransportClient implements TransportClient {

    private NioEventLoopGroup nioEventLoopGroup;
    private Channel channel;
    private final ChannelDuplexHandler clientHandler;

    public NettyTransportClient(ChannelDuplexHandler handler) {
        this.clientHandler = handler;
    }

    @Override
    public void connect(Peer peer) {
        nioEventLoopGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(nioEventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        //添加编码器
                        pipeline.addLast(new RpcEncoder(Request.class, new JSONEncoder()));
                        //添加解码器
                        pipeline.addLast(new RpcDecoder<Response>(Response.class, new JSONDecoder()));
                        //请求处理类
                        pipeline.addLast(clientHandler);
                    }
                });
        ChannelFuture channelFuture = b.connect(peer.getHost(), peer.getPort()).addListener(future -> log.info("client init success={}", future.isSuccess()));
        channel = channelFuture.channel();
    }

    @Override
    public Response write(Request request) {
        try {
            channel.writeAndFlush(request).await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ((ResponseHandler) clientHandler).getRpcResponse(request.getRequestId());
    }

    @Override
    public void close() {
        nioEventLoopGroup.shutdownGracefully();
    }
}
