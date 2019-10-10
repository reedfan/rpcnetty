package reed.ustc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * created by reedfan on 2019/10/10 0010
 */
public class SimpleServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.channel().writeAndFlush("ok");
        ctx.channel().close();
    }
}
