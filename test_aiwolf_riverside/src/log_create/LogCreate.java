package log_create;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

public class LogCreate {
	int agentCount = 0; // �Q���G�[�W�F���g��ID�J�E���g�p

	/**
	 * �f�B���N�g���w��A�e�֐��Ăяo��
	 * @param args
	 */
	public static void main( String args[] ) {
		try {
			String logPass = "data/log"; // ��͂��������O�̃p�X���w�肷��B�]�؂Ő�ΎQ�ƁB
			File logData = new File(logPass); // �t�@�C���ɃA�N�Z�X���邽�߂̕ϐ��B
			System.out.println("main_program start.");
			(new LogCreate()).mainLoop(logData);

		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		finally {
			(new LogResult5()).Write(); // 5�l���̃��O�����o��
			(new LogResult15()).Write(); // 15�l���̃��O�����o��
			System.out.println("main_program end");
		}
	}

	/**
	 * ���O�f�[�^�̃f�B���N�g����T������
	 * @param file
	 */
	private void mainLoop( File file ) {
		for( File dir:file.listFiles() ) {
			// �f�B���N�g���̏ꍇ�͍ċA�I�ɌĂяo��
			if( dir.isDirectory() ) {
				mainLoop(dir);
			}
			// �B���t�@�C���̏ꍇ�͏������Ȃ�
			if( dir.isHidden() ) {
				continue;
			}
			// .log�`���̃t�@�C����ǂ�
			if( dir.isFile() ) {
				try {
					System.out.println( dir.toString() + " is open." );
					openFile(dir);
				}
				catch ( Exception e ) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * ���O�f�[�^��ǂݍ���
	 * @param file
	 * @throws IOException
	 */
	private void openFile( File file ) throws IOException {
		BufferedReader br;
		String str;
		// �������烍�O�f�[�^���E���Ă����ꍇ�͂��̌`���̂��߁A�ʏ���
		if( file.toString().contains(".gz" ) ) {
			GZIPInputStream gz = new GZIPInputStream( new FileInputStream(file) );
			InputStreamReader isr = new InputStreamReader( gz,"Shift-JIS" );
			br = new BufferedReader( isr );
		}
		// �𓀍σt�@�C��
		else {
			FileReader fr = new FileReader( file );
			br = new BufferedReader( fr );
		}

		try {
			while( (str = br.readLine()) != null ) {
				String array[] = str.split(",");
				// ���O�f�[�^��status���m�F
				if( array[1].equals("status") ) {
					// status�̂͂��߂�Agent�̐ڑ�
					if ( array[0].toString().equals("0") ) {
						agentCount++; // �ڑ����ꂽ�G�[�W�F���g�����J�E���g
					}
				}
			}
			// 5�l���̃��O�f�[�^
			if( agentCount < 15 ) {
				(new LogResult5()).OpenFile(file);
			}
			// 15�l���̃��O�f�[�^
			else {
				(new LogResult15()).OpenFile(file);
			}


			agentCount = 0;

		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		br.close();
	}

}
