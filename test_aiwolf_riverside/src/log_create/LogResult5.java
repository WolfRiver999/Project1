package log_create;

import java.awt.geom.RectangularShape;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.omg.CORBA.portable.ValueInputStream;

public class LogResult5 {
	public static Map<String,Map<String,LogResult>> agentMap = new HashMap<>(); // key = エージェント名 value = マップ<役職、LogResult>
	Map<String,Double> rankMap = new HashMap<>(); // key = エージェント value = 順位
	String agentName[] = new String[6];
	int agentID[] = new int[6];
	int agentCount = 0;
	int roleCount = 0;
	String agentRole[] = new String[6];
	double[] average = new double[4];
	String logDir = ""; // 解析したいログを置いておくディレクトリパス
	String agentsDir = ""; // エージェントごとの結果を格納するディレクトリパス
	String totalDir = ""; // 全エージェントの集計結果を格納するディレクトリパス
	File logData;

	public void OpenFile( File file ) throws IOException {
		BufferedReader br;
		String str;

		AgentListReset();

		// 公式からログデータを拾ってきた場合はこの形式のため、別処理
		if( file.toString().contains(".gz" ) ) {
			GZIPInputStream gz = new GZIPInputStream(new FileInputStream(file));
			InputStreamReader isr = new InputStreamReader(gz,"Shift-JIS");
			br = new BufferedReader(isr);
		}
		// 解凍済ファイル
		else {
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
		}

		try {
			while( (str = br.readLine()) != null) {
				String array[] = str.split(",");
				// ログデータのstatusを確認
				if( array[1].equals("status") ) {
					// statusのはじめはAgentの接続
					if ( array[0].toString().equals("0") ) {
						// 保持しているデータと照合する int ID, String name, String role
						AgentCheck(Integer.parseInt(array[2]),array[5].toString(),array[3].toString());
					}
				}
				else if( array[1].equals("result") ) {
					LogResult valueResult = new LogResult();
					// エージェントの人数分ループする
					for(int i = 1; i <= 5; i++) {
						Map<String,LogResult> resultMap = agentMap.get(agentName[i]);
						valueResult = agentMap.get(agentName[1]).get(agentRole[i]);
						// 人狼陣営が勝利した
						if( array[4].equals("WEREWOLF") ) {
							// agent[i]が人狼陣営
							if(agentRole[i].equals("WEREWOLF") || agentRole[i].equals("POSSESSED") ) {
								valueResult.addWin();
								resultMap.put(agentRole[i], valueResult);
								agentMap.put(agentName[i], resultMap);
							}
							// agent[i]が村人陣営
							else {
								valueResult.addLose();
								resultMap.put(agentRole[i], valueResult);
								agentMap.put(agentName[i], resultMap);
							}
						}
						// 村人陣営が勝利した
						else if( array[4].equals("VILLAGER") ) {
							// agent[i]が村人陣営
							if(agentRole[i].equals("VILLAGER") || agentRole[i].equals("SEER") ) {
								valueResult.addWin();
								resultMap.put(agentRole[i], valueResult);
								agentMap.put(agentName[i], resultMap);
							}
							// agent[i]が人狼陣営
							else {
								valueResult.addLose();
								resultMap.put(agentRole[i], valueResult);
								agentMap.put(agentName[i], resultMap);
							}
						}
					} // for
				} // else if( array[1].equals("result") )
				else if( array[1].equals("talk")) {
					// 発言を取得する
				} // else if( array[1].equals("talk"))
			} // while( (str = br.readLine()) != null)
		} // try
		catch (Exception e) {
			e.printStackTrace();
		}

		br.close();
	}

	private void AgentCheck( int ID, String name, String role ) {
		try {
			if( agentCount <= 5) {
				agentCount++;
				agentName[ID] = name;
				agentID[ID] = ID;
				agentRole[ID] = role;

				if( !agentMap.containsKey(agentName[ID]) ) {
					agentMap.put(agentName[ID], new HashMap<String, LogResult>());
					agentMap.get(agentName[ID]).put("VILLAGER", new LogResult());
					agentMap.get(agentName[ID]).put("SEER", new LogResult());
					agentMap.get(agentName[ID]).put("WEREWOLF", new LogResult());
					agentMap.get(agentName[ID]).put("POSSESSED", new LogResult());
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Write() {

	}

	private void AgentListReset() {
		for(int i = 0; i < agentName.length; i++) {
			agentName[i] = null;
			agentID[i] = 0;
		}
		agentCount = 0;
	}
}
