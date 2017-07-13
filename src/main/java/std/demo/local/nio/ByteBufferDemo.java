package std.demo.local.nio;

import java.nio.ByteBuffer;

public class ByteBufferDemo {

	public static void main(String[] args) {

		// 无论是读数据还是写数据
		// 都是从position开始
		// 至多进行到limit-1
		// 每读/写操作position+1(byte)/2(char)/......
		// position > limit抛出BufferOverflowException

		// filp()
		// limit置为position
		// position置为0
		// 可以开始从头读取数据
		// 读模式

		// rewind()
		// position置为0
		// 读模式(重读)

		// clear()
		// limit置为capacity
		// position置为0
		// 可以开始从头写入数据
		// 从读模式切换到写模式

		// compact()
		// 将所有未读的数据拷贝到Buffer起始处
		// limit置为capacity
		// position设到最后一个未读元素正后面
		// 从读模式切换到写模式(保留未读完数据)

		// mark()/reset()
		// 标记一个position/回到标记的position
		
		test1();
		
		
		

	}

	public static void test1() {
		ByteBuffer buffer = ByteBuffer.allocate(3);

		printBufferDetail(buffer);

		putBytesAndPrint(buffer, 2);

		flipAndPrint(buffer);

		getBytesAndPrint(buffer, 2);

		rewindAndPrint(buffer);

		getBytesAndPrint(buffer, 2);

		clearAndPrint(buffer);

		putBytesAndPrint(buffer, 3);

		flipAndPrint(buffer);

		getBytesAndPrint(buffer, 1);

		compactAndPrint(buffer);

		putBytesAndPrint(buffer, 1);
	}

	public static void flipAndPrint(ByteBuffer buffer) {
		buffer.flip();
		System.out.print("flip——>");
		printBufferDetail(buffer);
	}

	public static void rewindAndPrint(ByteBuffer buffer) {
		buffer.rewind();
		System.out.print("rewind——>");
		printBufferDetail(buffer);
	}

	public static void compactAndPrint(ByteBuffer buffer) {
		buffer.compact();
		System.out.print("compact——>");
		printBufferDetail(buffer);
	}

	public static void clearAndPrint(ByteBuffer buffer) {
		buffer.clear();
		System.out.print("clear——>");
		printBufferDetail(buffer);
	}

	public static void putBytesAndPrint(ByteBuffer buffer, int length) {
		for (int i = 1; i <= length; i++) {
			buffer.put((byte) i);
			System.out.print("写入一个字节:");
			System.out.print(i);
			System.out.print("——>");
			printBufferDetail(buffer);
		}
	}

	public static void getBytesAndPrint(ByteBuffer buffer, int length) {
		for (int i = 1; i <= length; i++) {
			System.out.print("读取一个字节:");
			System.out.print(buffer.get());
			System.out.print("——>");
			printBufferDetail(buffer);
		}
	}

	public static void printBufferDetail(ByteBuffer buffer) {

		StringBuilder sbd = new StringBuilder();

		sbd.append("capacity:");
		sbd.append(buffer.capacity());
		sbd.append(",position:");
		sbd.append(buffer.position());
		sbd.append(",limit:");
		sbd.append(buffer.limit());
		sbd.append(",可以继续读/写:");
		sbd.append(buffer.hasRemaining());

		System.out.println(sbd.toString());
	}

}
