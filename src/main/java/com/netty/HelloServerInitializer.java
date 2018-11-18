package com.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author ZiJie.Yip
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        //通过管道，添加handler
        //HttpServerCodec是由netty自己提供的助手类，可以理解为拦截器
        //当请求到服务器，我们需要做解码，响应到客户端做编码
        pipeline.addLast("HttpServerCodec",new HttpServerCodec());

        pipeline.addLast("customHandler",new CustomHandler());
    }
}
