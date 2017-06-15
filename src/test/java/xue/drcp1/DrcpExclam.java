package xue.drcp1;


import backtype.storm.utils.DRPCClient;

public class DrcpExclam {

	public static void main(String[] args) throws Exception {
		DRPCClient client = new DRPCClient("192.168.1.191", 3772);
		for (String word : new String[]{ "hello", "goodbye" }) {
			System.out.println(client.execute("exclamation", word));
		}
	}
}

