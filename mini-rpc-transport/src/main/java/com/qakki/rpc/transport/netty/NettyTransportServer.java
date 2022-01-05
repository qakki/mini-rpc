package com.qakki.rpc.transport.netty;

import com.qakki.rpc.Request;
import com.qakki.rpc.Response;
import com.qakki.rpc.common.codec.json.JSONDecoder;
import com.qakki.rpc.common.codec.json.JSONEncoder;
import com.qakki.rpc.transport.RequestHandler;
import com.qakki.rpc.transport.TransportServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * todo
 *
 * @author qakki
 * @date 2022/1/4 11:31 下午
 */
@Slf4j
public class NettyTransportServer implements TransportServer {

    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workerGroup;
    private int port;
    private RequestHandler handler;

    @Override
    public void init(int port, RequestHandler handler) {
        this.port = port;
        this.handler = handler;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void start() {
        if (!(handler instanceof SimpleChannelInboundHandler)) {
            throw new IllegalStateException();
        }

        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup(8);

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new RpcDecoder<>(Request.class, new JSONDecoder()));
                            pipeline.addLast(new RpcEncoder(Response.class, new JSONEncoder()));
                            pipeline.addLast((SimpleChannelInboundHandler<Request>) handler);
                        }
                    });
            b.bind(port).sync().channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("server start error ", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
