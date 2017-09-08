package std.demo.local.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

//netty源码分析
//https://segmentfault.com/a/1190000007282628
//http://www.jianshu.com/u/4fdc8c2315e8
//http://www.jianshu.com/p/493b36e439bc
public class Server {
	/**
	 * 服务端监听的端口地址
	 */
	private static final int portNumber = 7878;

	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup);
			b.channel(NioServerSocketChannel.class);
			// b.childHandler(new StringServerInitializer());
			b.childHandler(new StringServerInitializer());

			// 服务器绑定端口监听
			ChannelFuture f = b.bind(portNumber).sync();
			// 监听服务器关闭监听
			f.channel().closeFuture().sync();

			// 可以简写为
			// b.bind(portNumber).sync().channel().closeFuture().sync();
		} finally {
			// 优雅关闭
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

}
