package xue.drcp2;

import backtype.storm.utils.DRPCClient;

public class DrpcReach {
	public static void main(String[] args) throws Exception {
		DRPCClient client = new DRPCClient("192.168.1.191", 3772);
		for (String url : new String[]{ "foo.com/blog/1", "engineering.twitter.com/blog/5" }) {
			System.out.println(client.execute("reach", url));
		}
	}

}
