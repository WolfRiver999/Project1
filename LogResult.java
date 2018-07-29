package aitknCEDEC2017LogResult;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import aitknCEDEC2017LogResult.LogResultfor5;
import aitknCEDEC2017LogResult.LogResultfor15;

public class LogResult {

	int count = 0; //エージェントID確認



	public static void main(String[] args){
		try{
			System.out.println("program start");
			String logDir = "/Users/k14048kk/Desktop/モデルエージェント対戦ログ"; //ログを置いておくディレクトリ
			File log = new File(logDir);
			(new LogResult()).mainLoop(log);			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
			(new LogResultfor5()).write();
			(new LogResultfor15()).write();
		}
	}

//	//ディレクトリ探索・ファイルオープン
//	private void mainLoop(){
//		File logdata = new File(logDir); //読み込むディレクトリ
//
//		//ログデータのディレクトリを探索
//		for(File dir:logdata.listFiles()){
//			if(dir.isHidden()){
//				dir.delete();
//				System.out.println("削除");
//				break;
//			}
//			for(File file:dir.listFiles()){
//				if (file.isDirectory()) {
//					mainLoop(file);
//				}
//				if(dir.isHidden()){
//					dir.delete();
//				}
//				if () {
//					try{
//						openFile(file);//読み込みメソッド呼び出し
//						System.out.println(file+" is open.");
//					}
//					catch(IOException e){
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//	}

	private void mainLoop(File file) {
		//ログデータのディレクトリを探索
		for(File dir:file.listFiles()){
//			if(dir.isHidden()){
//				dir.delete();
//				System.out.println("削除");
//				break;
//			}
			if (dir.isDirectory()) {
				mainLoop(dir);
			}
			if(dir.isHidden()){
//				dir.delete();
				continue;
			}
			if (dir.isFile()) {
				try{
					openFile(dir);//読み込みメソッド呼び出し
					System.out.println(dir+" is open.");
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
		}	
	}

	//ログデータを読み込む
	private void openFile(File file) throws IOException{
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
						count++;
					}
				}
			}

			if (count < 15) {
				(new LogResultfor5()).openFile(file);
			}
			else {
				(new LogResultfor15()).openFile(file);
			}
			count = 0;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		br.close();
	}
}