package log_create;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LogResult15 {
	public static Map<String,Map<String,LogResult>> agentMap = new HashMap<>(); // key = �G�[�W�F���g�� value = �}�b�v<��E�ALogResult>
	Map<String,Double> rankMap = new HashMap<>(); // key = �G�[�W�F���g value = ����
	String agentName[] = new String[16];
	int agentID[] = new int[16];
	int agentCount = 0;
	int roleCount = 0;
	String agentRole[] = new String[16];
	double[] average = new double[6];
	String logDir = ""; // ��͂��������O��u���Ă����f�B���N�g���p�X
	String agentsDir = ""; // �G�[�W�F���g���Ƃ̌��ʂ��i�[����f�B���N�g���p�X
	String totalDir = ""; // �S�G�[�W�F���g�̏W�v���ʂ��i�[����f�B���N�g���p�X
	File logData;

	public void OpenFile( File file ) throws IOException {

	}

	private void AgentCheck( int ID, String name, String role) {

	}

	public void Write() {

	}
}
