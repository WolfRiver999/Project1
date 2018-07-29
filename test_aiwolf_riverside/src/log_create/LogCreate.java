package log_create;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

public class LogCreate {
	int agentCount = 0; // 参加エージェントのIDカウント用

	/**
	 * ディレクトリ指定、各関数呼び出し
	 * @param args
	 */
	public static void main( String args[] ) {
		try {
			String logPass = "data/log"; // 解析したいログのパスを指定する。脳筋で絶対参照。
			File logData = new File(logPass); // ファイルにアクセスするための変数。
			System.out.println("main_program start.");
			(new LogCreate()).mainLoop(logData);

		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		finally {
			(new LogResult5()).Write(); // 5人村のログ書き出し
			(new LogResult15()).Write(); // 15人村のログ書き出し
			System.out.println("main_program end");
		}
	}

	/**
	 * ログデータのディレクトリを探索する
	 * @param file
	 */
	private void mainLoop( File file ) {
		for( File dir:file.listFiles() ) {
			// ディレクトリの場合は再帰的に呼び出し
			if( dir.isDirectory() ) {
				mainLoop(dir);
			}
			// 隠しファイルの場合は処理しない
			if( dir.isHidden() ) {
				continue;
			}
			// .log形式のファイルを読む
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
	 * ログデータを読み込む
	 * @param file
	 * @throws IOException
	 */
	private void openFile( File file ) throws IOException {
		BufferedReader br;
		String str;
		// 公式からログデータを拾ってきた場合はこの形式のため、別処理
		if( file.toString().contains(".gz" ) ) {
			GZIPInputStream gz = new GZIPInputStream( new FileInputStream(file) );
			InputStreamReader isr = new InputStreamReader( gz,"Shift-JIS" );
			br = new BufferedReader( isr );
		}
		// 解凍済ファイル
		else {
			FileReader fr = new FileReader( file );
			br = new BufferedReader( fr );
		}

		try {
			while( (str = br.readLine()) != null ) {
				String array[] = str.split(",");
				// ログデータのstatusを確認
				if( array[1].equals("status") ) {
					// statusのはじめはAgentの接続
					if ( array[0].toString().equals("0") ) {
						agentCount++; // 接続されたエージェント数をカウント
					}
				}
			}
			// 5人村のログデータ
			if( agentCount < 15 ) {
				(new LogResult5()).OpenFile(file);
			}
			// 15人村のログデータ
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
