package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.AttributeKey;
import reed.ustc.server.SimpleServerHandler;

/**
 * created by reedfan on 2019/10/12 0012
 */
public class NettyClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 8080;
        EventLoopGroup workGroup = new NioEventLoopGroup();


        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {

                socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(65535, Delimiters.lineDelimiter()[0]));
                socketChannel.pipeline().addLast(new StringDecoder());

                socketChannel.pipeline().addLast(new SimpleClientHandler());

                socketChannel.pipeline().addLast(new StringEncoder());
            }
        });

        try {
            ChannelFuture future = bootstrap.connect(host,port).sync();
            future.channel().writeAndFlush("hello server");
            future.channel().writeAndFlush("\r\n");
            future.channel().closeFuture().sync();
            Object result = future.channel().attr(AttributeKey.valueOf("test")).get();
            System.out.println("获取服务器的返回数据==="+result.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
