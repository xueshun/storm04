package xue.drcp1;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.LocalDRPC;
import backtype.storm.StormSubmitter;
import backtype.storm.drpc.LinearDRPCTopologyBuilder;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class BasicDRCPTopology {
	  
	public static class ExclaimBolt extends BaseBasicBolt {
		@Override
	    public void execute(Tuple tuple, BasicOutputCollector collector) {
	      String input = tuple.getString(1);
	      //System.out.println("==========" + tuple.getValue(0));
	      collector.emit(new Values(tuple.getValue(0), input + "!"));
	    }

	    @Override
	    public void declareOutputFields(OutputFieldsDeclarer declarer) {
	      declarer.declare(new Fields("id", "result"));
	    }
    }

	  public static void main(String[] args) throws Exception {
		  	//创建drpc实例
		    LinearDRPCTopologyBuilder builder = new LinearDRPCTopologyBuilder("exclamation");
		    //添加bolt
		    builder.addBolt(new ExclaimBolt(), 3);
		    Config conf = new Config();
		    if (args == null || args.length == 0) {
			      LocalDRPC drpc = new LocalDRPC();
			      LocalCluster cluster = new LocalCluster();
			      cluster.submitTopology("drpc-demo", conf, builder.createLocalTopology(drpc));
			      for (String word : new String[]{ "hello", "goodbye" }) {
			    	  System.out.println("Result for \"" + word + "\": " + drpc.execute("exclamation", word));
			      }
			
			      cluster.shutdown();
			      drpc.shutdown();
		    }
		    else {
			      conf.setNumWorkers(3);
			      StormSubmitter.submitTopology(args[0], conf, builder.createRemoteTopology());
		    }
	  }
}