package aitknCEDEC2017LogResult;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

import aitknCEDEC2017LogResult.Result;
import aitknCEDEC2017LogResult.LogResult;


public class LogResultfor15 extends LogResult{

	public static Map<String,Map<String,Result>> agentMap = new HashMap<>();// key=エージェント名、value=マップ＜役職、勝ち負け＞

	Map<String,Double> rankMap = new HashMap<>(); // key = エージェント　value = Ave
	
	String agentName[] = new String[16]; //100ログ分エージェント15体の名前格納(0は使わない)
	int agentID[] = new int[16]; //100ログ分エージェント15体のID格納(0は使わない)
	int count = 0; //エージェントID確認
	int c = 0;//役職確認用
	String agentRole[] = new String[16];
	double[] abe = new double[6];

	String logDir = "/Users/k14048kk/Desktop/モデルエージェント対戦ログ"; //ログを置いておくディレクトリ
	String agentsDir = "data/thesis/"; //エージェントごとのテキストを保存するディレクトリ
	String totalDir = "data/thesis/"; //集計のテキストを保存するディレクトリ

	//ログデータを読み込む
	public void openFile(File file) throws IOException{
		agentListReset(); //初期化
		BufferedReader br;
		if (file.toString().contains(".gz")) {
			GZIPInputStream gz = new GZIPInputStream(new FileInputStream(file));
			InputStreamReader is = new InputStreamReader(gz,"Shift-JIS");
			br = new BufferedReader(is);
		}
		else {
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
		}
		String str;
		try{
			while((str=br.readLine())!=null){
				String array[] = str.split(",");

				//status格納
				if(array[1].equals("status")){
					if(array[0].toString().equals("0")){
						AgentCheck(Integer.parseInt(array[2]),array[5].toString(),array[3].toString());
					}
				}	


				//結果格納
				else if(array[1].equals("result")){
					Result valueResult = new Result();
					for(int i = 1; i<=15; i++){
						Map<String, Result>resultMap = agentMap.get(agentName[i]);
						valueResult = agentMap.get(agentName[i]).get(agentRole[i]);

						//人狼勝利
						if(array[4].equals("WEREWOLF")){
							//勝ち役職
							if(agentRole[i].equals("WEREWOLF") || agentRole[i].equals("POSSESSED")){
								valueResult.addwin();
								resultMap.put(agentRole[i],valueResult);
								agentMap.put(agentName[i], resultMap);
							}
							//負け役職
							else{
								valueResult.addlose();
								resultMap.put(agentRole[i],valueResult);
								agentMap.put(agentName[i], resultMap);							
							}
						}

						//村人勝利
						else if(array[4].equals("VILLAGER")){
							//勝ち役職
							if(agentRole[i].equals("VILLAGER") || agentRole[i].equals("SEER") || agentRole[i].equals("MEDIUM") || agentRole[i].equals("BODYGUARD")){
								valueResult.addwin();
								resultMap.put(agentRole[i],valueResult);
								agentMap.put(agentName[i], resultMap);
							}
							//負け役職
							else{
								valueResult.addlose();
								resultMap.put(agentRole[i],valueResult);
								agentMap.put(agentName[i], resultMap);
							}
						}
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		br.close();

	}

	//エージェントの名前を格納
	private void AgentCheck(int ID, String name, String role){
		try{
			if(count<15){
				count++; //エージェントIDは１から（調整してないだけ）
				agentName[ID] = name; //エージェントName
				agentID[ID] = ID; //エージェントID
				agentRole[ID] = role;

				//マップにそのエージェントの名前が含まれていない
				if(!agentMap.containsKey(agentName[ID])){
					// エージェントの確認
					agentMap.put(agentName[ID], new HashMap<String,Result>());

					agentMap.get(agentName[ID]).put("VILLAGER", new Result());
					agentMap.get(agentName[ID]).put("SEER", new Result());
					agentMap.get(agentName[ID]).put("MEDIUM", new Result());
					agentMap.get(agentName[ID]).put("BODYGUARD", new Result());
					agentMap.get(agentName[ID]).put("POSSESSED", new Result());
					agentMap.get(agentName[ID]).put("WEREWOLF", new Result());
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void write(){
		try{
			for(String agent: agentMap.keySet()) {				
				String file = agent;
				FileWriter filewriter = new FileWriter(agentsDir+"/"+file+".txt",true);//書き出すディレクトリ
				BufferedWriter bw = new BufferedWriter(filewriter);
				bw.write("VILLAGER:"+agentMap.get(agent).get("VILLAGER").getWin() + "/" + agentMap.get(agent).get("VILLAGER").getCount());
				bw.newLine();
				bw.write("SEER:"+agentMap.get(agent).get("SEER").getWin() + "/" + agentMap.get(agent).get("SEER").getCount());
				bw.newLine();
				bw.write("MEDIUM:"+agentMap.get(agent).get("MEDIUM").getWin() + "/" + agentMap.get(agent).get("MEDIUM").getCount());
				bw.newLine();
				bw.write("BODYGUARD:"+agentMap.get(agent).get("BODYGUARD").getWin() + "/" + agentMap.get(agent).get("BODYGUARD").getCount());
				bw.newLine();
				bw.write("WEREWOLF:"+agentMap.get(agent).get("WEREWOLF").getWin() + "/" + agentMap.get(agent).get("WEREWOLF").getCount());
				bw.newLine();
				bw.write("POSSESSED:"+agentMap.get(agent).get("POSSESSED").getWin() + "/" + agentMap.get(agent).get("POSSESSED").getCount());
				bw.newLine();
				bw.close();

				double wincount = (agentMap.get(agent).get("VILLAGER").getWin() + agentMap.get(agent).get("SEER").getWin() + agentMap.get(agent).get("MEDIUM").getWin() + agentMap.get(agent).get("BODYGUARD").getWin() + agentMap.get(agent).get("POSSESSED").getWin() +agentMap.get(agent).get("WEREWOLF").getWin());
				filewriter = new FileWriter(totalDir+"/"+"total.txt",true);
				bw = new BufferedWriter(filewriter);
				bw.write(agent);
				bw.newLine();
				if (agentMap.get(agent).get("VILLAGER").getCount() != 0) {
					bw.write("VILLAGER:"+BigDeci(((double)agentMap.get(agent).get("VILLAGER").getWin()/(double)agentMap.get(agent).get("VILLAGER").getCount())));
					bw.newLine();
				}
				if (agentMap.get(agent).get("SEER").getCount() != 0) {
					bw.write("SEER:"+BigDeci(((double)agentMap.get(agent).get("SEER").getWin()/(double)agentMap.get(agent).get("SEER").getCount())));
					bw.newLine();
				}
				if (agentMap.get(agent).get("MEDIUM").getCount() != 0) {
					bw.write("MEDIUM:"+BigDeci(((double)agentMap.get(agent).get("MEDIUM").getWin()/(double)agentMap.get(agent).get("MEDIUM").getCount())));
					bw.newLine();
				}
				if (agentMap.get(agent).get("BODYGUARD").getCount() != 0) {
					bw.write("BODYGUARD:"+BigDeci(((double)agentMap.get(agent).get("BODYGUARD").getWin()/(double)agentMap.get(agent).get("BODYGUARD").getCount())));
					bw.newLine();
				}
				if (agentMap.get(agent).get("WEREWOLF").getCount() != 0) {
					bw.write("WEREWOLF:"+BigDeci(((double)agentMap.get(agent).get("WEREWOLF").getWin()/(double)agentMap.get(agent).get("WEREWOLF").getCount())));
					bw.newLine();
				}
				if (agentMap.get(agent).get("POSSESSED").getCount() != 0) {
					bw.write("POSSESSED:"+BigDeci(((double)agentMap.get(agent).get("POSSESSED").getWin()/(double)agentMap.get(agent).get("POSSESSED").getCount())));
					bw.newLine();
				}
				double wariate = agentMap.get(agent).get("VILLAGER").getCount()+agentMap.get(agent).get("SEER").getCount()+agentMap.get(agent).get("MEDIUM").getCount()+agentMap.get(agent).get("BODYGUARD").getCount()+agentMap.get(agent).get("WEREWOLF").getCount()+agentMap.get(agent).get("POSSESSED").getCount();
				bw.write("割り当て回数:"+wariate);
				bw.newLine();
				rankMap.put(agent, AverageReturn(wincount,wariate));
				bw.write("Ave:"+AverageReturn(wincount,wariate));
				bw.newLine();
				bw.close();
				c=0;
			}
			rank();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void rank() {
		try{
			FileWriter filewriter = new FileWriter(totalDir+"/"+"total.txt",true);
			BufferedWriter bw = new BufferedWriter(filewriter);

			List<Entry<String, Double>> list_entries = new ArrayList<Entry<String, Double>>(rankMap.entrySet());
			Collections.sort(list_entries, new Comparator<Entry<String, Double>>() {
				//compareを使用して値を比較する
				public int compare(Entry<String, Double> obj1, Entry<String, Double> obj2)
				{
					//降順
					return obj2.getValue().compareTo(obj1.getValue());
				}
			});

			bw.newLine();
			
			int rank = 1;
			for(Entry<String, Double> entry : list_entries) {
				bw.write((rank++ + entry.getKey() + " : " + entry.getValue()) + " ");
				bw.newLine();
			}
			
			bw.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private double BigDeci(double a) {
		BigDecimal x = new BigDecimal(a);
		x = x.setScale(3, BigDecimal.ROUND_HALF_UP);
		abe[c++] = x.doubleValue();
		return x.doubleValue();
	}

	private double AverageReturn(double wincount, double wariate) {
		BigDecimal x = new BigDecimal(wincount/wariate);
		x = x.setScale(3, BigDecimal.ROUND_HALF_UP);
		return x.doubleValue();
	}
	//ログデータの変更による名前・IDのリセット
	private void agentListReset(){
		for(int i = 0; i < agentName.length; i++){
			agentName[i] = null;
			agentID[i] = 0;
		}
		count = 0;
	}

}