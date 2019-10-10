package reed.ustc;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * created by reedfan on 2019/10/10 0010
 */
public class NettyServer {
    public static void main(String[] args) {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(parentGroup,childGroup);


        try {
            bootstrap.option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,false)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                        //    socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(65535, Delimiters.lineDelimiter()[0]));
                            socketChannel.pipeline().addLast(new StringDecoder());

                            socketChannel.pipeline().addLast(new SimpleServerHandler());

                            socketChannel.pipeline().addLast(new StringEncoder());
                        }
                    });
            ChannelFuture future = bootstrap.bind(8080).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }


    }
}
