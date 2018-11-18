package com.netty.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

/**
 * @Description:处理消息的handler
 * TextWebSocketFrame :在netty中，是用于为websocket专门处理文本的对象，frame是消息的载体
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        //获取客户端发送的消息
        String content = msg.text();
        System.out.println("接受到的数据: " + content);
        clients.writeAndFlush(new TextWebSocketFrame("[服务器在: ]" + LocalDateTime.now() + "接受到消息，消息为: " + content ));
        System.out.println("发送成功");
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        clients.add(channel);
        System.out.println("clients size : "+clients.size());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //当触发handlerRemoved，ChannelGroup会自动移除对应客户端的channel
        System.out.println("客户端断开，channel对应的长id为" + ctx.channel().id().asLongText());
        System.out.println("客户端断开，channel对应的短id为" + ctx.channel().id().asShortText());
    }
}
