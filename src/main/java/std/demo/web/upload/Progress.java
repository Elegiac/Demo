package std.demo.web.upload;

public class Progress {
	private long pBytesRead;
	private long pContentLength;
	private int pItems;

	public long getpBytesRead() {
		return pBytesRead;
	}

	public void setpBytesRead(long pBytesRead) {
		this.pBytesRead = pBytesRead;
	}

	public long getpContentLength() {
		return pContentLength;
	}

	public void setpContentLength(long pContentLength) {
		this.pContentLength = pContentLength;
	}

	public int getpItems() {
		return pItems;
	}

	public void setpItems(int pItems) {
		this.pItems = pItems;
	}

	@Override
	public String toString() {
		return "Progress [pBytesRead=" + pBytesRead + ", pContentLength=" + pContentLength + ", pItems=" + pItems + "]";
	}

}
