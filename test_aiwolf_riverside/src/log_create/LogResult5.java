package log_create;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

public class LogResult5 {
	public static Map<String,Map<String,LogResult>> agentMap = new HashMap<>(); // key = �G�[�W�F���g�� value = �}�b�v<��E�ALogResult>
	Map<String,Double> rankMap = new HashMap<String, Double>(); // key = �G�[�W�F���g value = ����
	String agentName[] = new String[6];
	int agentID[] = new int[6];
	int agentCount = 0;
	int roleCount = 0;
	String agentRole[] = new String[6];
	double[] average = new double[4];
	String logDir = "data/log"; // ��͂��������O��u���Ă����f�B���N�g���p�X
	String agentsDir = "data/result/5"; // �G�[�W�F���g���Ƃ̌��ʂ��i�[����f�B���N�g���p�X
	String totalDir = "data/result/5"; // �S�G�[�W�F���g�̏W�v���ʂ��i�[����f�B���N�g���p�X
	File logData;
	Calendar c = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddHHmmss");
	String current = sdf.format(c.getTime());


	public void OpenFile( File file ) throws IOException {
		BufferedReader br;
		String str;

		AgentListReset();

		// �������烍�O�f�[�^���E���Ă����ꍇ�͂��̌`���̂��߁A�ʏ���
		if( file.toString().contains(".gz" ) ) {
			GZIPInputStream gz = new GZIPInputStream( new FileInputStream(file) );
			InputStreamReader isr = new InputStreamReader( gz,"Shift-JIS" );
			br = new BufferedReader(isr);
		}
		// �𓀍σt�@�C��
		else {
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
		}

		try {
			while( (str = br.readLine()) != null) {
				String array[] = str.split(",");
				// ���O�f�[�^��status���m�F
				if( array[1].equals("status") ) {
					// status�̂͂��߂�Agent�̐ڑ�
					if ( array[0].toString().equals("0") ) {
						// �ێ����Ă���f�[�^�Əƍ����� int ID, String name, String role
						AgentCheck( Integer.parseInt(array[2]), array[5].toString(), array[3].toString() );
					}
				}
				else if( array[1].equals("result") ) {
					LogResult valueResult = new LogResult();
					// �G�[�W�F���g�̐l�������[�v����
					for(int i = 1; i <= 5; i++) {
						Map<String,LogResult> resultMap = agentMap.get(agentName[i]);
						valueResult = agentMap.get(agentName[i]).get(agentRole[i]);
						// �l�T�w�c����������
						if( array[4].equals("WEREWOLF") ) {
							// agent[i]���l�T�w�c
							if(agentRole[i].equals("WEREWOLF") || agentRole[i].equals("POSSESSED") ) {
								valueResult.addWin();
								resultMap.put(agentRole[i], valueResult);
								agentMap.put(agentName[i], resultMap);
							}
							// agent[i]�����l�w�c
							else {
								valueResult.addLose();
								resultMap.put(agentRole[i], valueResult);
								agentMap.put(agentName[i], resultMap);
							}
						}
						// ���l�w�c����������
						else if( array[4].equals("VILLAGER") ) {
							// agent[i]�����l�w�c
							if(agentRole[i].equals("VILLAGER") || agentRole[i].equals("SEER") ) {
								valueResult.addWin();
								resultMap.put(agentRole[i], valueResult);
								agentMap.put(agentName[i], resultMap);
							}
							// agent[i]���l�T�w�c
							else {
								valueResult.addLose();
								resultMap.put(agentRole[i], valueResult);
								agentMap.put(agentName[i], resultMap);
							}
						}
					} // for
				} // else if( array[1].equals("result") )
				else if( array[1].equals("talk")) {
					// �������擾����
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
					/* �}�b�v�ɓo�^����Ă��Ȃ��ꍇ�͐V�K�o�^���� */
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
		double joinCount = 0.0;
		double winCount = 0.0;
		try {
			for( String agent:agentMap.keySet() ) {
				/* �e�v���C�����ƂŁA��E���ƂɁu������/�Q���񐔁v���������� */
				File newfile = new File(agentsDir + "/" + current);
				newfile.mkdirs();
				FileWriter fw = new FileWriter( agentsDir + "/" + current + "/" + agent + ".txt" , true );
				BufferedWriter bw = new BufferedWriter( fw );
				bw.write( "VILLAGER(win/count):" + agentMap.get(agent).get("VILLAGER").getWin() + "/" + agentMap.get(agent).get("VILLAGER").getCount() + "\n" );
				bw.newLine();
				bw.write( "SEER(win/count):" + agentMap.get(agent).get("SEER").getWin() + "/" + agentMap.get(agent).get("SEER").getCount() + "\n" );
				bw.newLine();
				bw.write( "WEREWOLF(win/count):" + agentMap.get(agent).get("WEREWOLF").getWin() + "/" + agentMap.get(agent).get("WEREWOLF").getCount() + "\n" );
				bw.newLine();
				bw.write( "POSSESSED(win/count):" + agentMap.get(agent).get("POSSESSED").getWin() + "/" + agentMap.get(agent).get("POSSESSED").getCount() + "\n" );
				bw.newLine();
				bw.write("joinCount:" + (agentMap.get(agent).get("VILLAGER").getCount() + agentMap.get(agent).get("SEER").getCount() + agentMap.get(agent).get("WEREWOLF").getCount() + agentMap.get(agent).get("POSSESSED").getCount()));
				bw.close();

				/* ���ۂ̏������v�Z���� */
				fw = new FileWriter( totalDir + "/" + current + "/" + "total.txt" , true );
				bw = new BufferedWriter( fw );
				bw.write( agent );
				bw.newLine();

				if( agentMap.get(agent).get("VILLAGER").getCount() != 0 ) {
					bw.write( "VILLAGER(winrate):" + BigDeci((agentMap.get(agent).get("VILLAGER").getWin() / (double)agentMap.get(agent).get("VILLAGER").getCount())));
					bw.newLine();
				}
				if( agentMap.get(agent).get("SEER").getCount() != 0 ) {
					bw.write( "SEER(winrate):" + BigDeci((agentMap.get(agent).get("SEER").getWin() / (double)agentMap.get(agent).get("SEER").getCount())));
					bw.newLine();
				}
				if( agentMap.get(agent).get("WEREWOLF").getCount() != 0 ) {
					bw.write( "WEREWOLF(winrate):" + BigDeci((agentMap.get(agent).get("WEREWOLF").getWin() / (double)agentMap.get(agent).get("WEREWOLF").getCount())));
					bw.newLine();
				}
				if( agentMap.get(agent).get("POSSESSED").getCount() != 0 ) {
					bw.write( "POSSESSED(winrate):" + BigDeci((agentMap.get(agent).get("POSSESSED").getWin() / (double)agentMap.get(agent).get("POSSESSED").getCount())) + "\n" );
					bw.newLine();
				}

				winCount = agentMap.get(agent).get("VILLAGER").getWin() + agentMap.get(agent).get("SEER").getWin() + agentMap.get(agent).get("WEREWOLF").getWin() + agentMap.get(agent).get("POSSESSED").getWin();
				joinCount = agentMap.get(agent).get("VILLAGER").getCount() + agentMap.get(agent).get("SEER").getCount() + agentMap.get(agent).get("WEREWOLF").getCount() + agentMap.get(agent).get("POSSESSED").getCount();

				bw.write("�Q���񐔁F" + joinCount);
				bw.newLine();
				rankMap.put(agent, AverageReturn(winCount, joinCount));
				bw.write("Average:" + AverageReturn(winCount, joinCount));
				bw.newLine();
				bw.newLine();
				bw.write("**********************************************************");
				bw.newLine();
				bw.newLine();
				bw.close();
				winCount = 0.0;
				joinCount = 0.0;
				roleCount = 0; // ������
			}
			Ranking();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void Ranking() {
		int rank = 1;
		try {
			File newfile = new File(totalDir + "/" + current);
			newfile.mkdirs();
			FileWriter fw = new FileWriter( totalDir + "/" + current + "/" + "total.txt", true );
			BufferedWriter bw = new BufferedWriter( fw );
			List<Entry<String, Double>> list_entries = new ArrayList<Entry<String, Double>>(rankMap.entrySet());
			Collections.sort(list_entries, new Comparator<Entry<String, Double>>() {
				public int compare(Entry<String, Double> obj1, Entry<String, Double> obj2 ) {
					return obj2.getValue().compareTo(obj1.getValue());
				}
			});

			bw.write("RANKING" + "\n");
			bw.write("���� | �G�[�W�F���g�� | ����");
			bw.newLine();
			bw.newLine();
			for( Entry<String, Double> entry : list_entries ) {
				bw.write(((rank++) + ". " + entry.getKey() + " : " + entry.getValue()) + "\n" );
				bw.newLine();
			}
			bw.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void AgentListReset() {
		for(int i = 0; i < agentName.length; i++) {
			agentName[i] = null;
			agentID[i] = 0;
		}
		agentCount = 0;
	}

	/* �����_��O�ʁi�؂�グ�j */
	private double BigDeci(double a) {
		BigDecimal x = new BigDecimal(a);
		x = x.setScale(3, BigDecimal.ROUND_HALF_UP);
		average[roleCount++] = x.doubleValue();
		return x.doubleValue();
	}

	private double AverageReturn(double winCount, double joinCount) {
		BigDecimal x = new BigDecimal( winCount / joinCount);
		x = x.setScale( 3,  BigDecimal.ROUND_HALF_UP );
		return x.doubleValue();
	}
}
