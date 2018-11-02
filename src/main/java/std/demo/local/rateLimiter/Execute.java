package std.demo.local.rateLimiter;

import com.google.common.util.concurrent.RateLimiter;

public class Execute {
	public static void main(String[] args) {
		RateLimiter rateLimiter = RateLimiter.create(0.2);
	}
}
