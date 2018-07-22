package log_create;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LogResult15 {
	public static Map<String,Map<String,LogResult>> agentMap = new HashMap<>(); // key = エージェント名 value = マップ<役職、LogResult>
	Map<String,Double> rankMap = new HashMap<>(); // key = エージェント value = 順位
	String agentName[] = new String[16];
	int agentID[] = new int[16];
	int agentCount = 0;
	int roleCount = 0;
	String agentRole[] = new String[16];
	double[] average = new double[6];
	String logDir = ""; // 解析したいログを置いておくディレクトリパス
	String agentsDir = ""; // エージェントごとの結果を格納するディレクトリパス
	String totalDir = ""; // 全エージェントの集計結果を格納するディレクトリパス
	File logData;

	public void OpenFile( File file ) throws IOException {

	}

	private void AgentCheck( int ID, String name, String role) {

	}

	public void Write() {

	}
}
