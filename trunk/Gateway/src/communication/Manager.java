package communication;

import driver.MsgHttp;
import driver.tinyOSMsg;

public class Manager {
	DBCommunication communication = new DBCommunication();
	RSSFCommunication rssf = new RSSFCommunication();

	public void TinyOSMsgReceived(tinyOSMsg http){

		if(http.get_codigo()==2000){
			// Comparacoes de banco de dados
			//
			String plataform="";


			switch(http.get_path()){
			case 0:
				plataform = this.Check(http.get_shost(), "demo");
				break;
			case 2:
				plataform = this.Check(http.get_shost(), "umidade");
				break;
			case 3:
				plataform = this.Check(http.get_shost(), "photo");
				break;
			}

			//			System.out.println("Plataforma="+plataform);
			if (plataform.equalsIgnoreCase("TinyOS")){
				// Se existir no banco

			} else {
				// Adiciona no banco e envia de volta
				switch(http.get_path()){
				case 0:
					communication.addService(http.get_shost(), "demo", "TinyOS");
					break;
				case 2:
					communication.addService(http.get_shost(), "umidade", "TinyOS");
					break;
				case 3:
					communication.addService(http.get_shost(), "photo", "TinyOS");
					break;
				}

			}
		} else {


			if ((char)http.get_metodo() == 'G' || (char)http.get_metodo() == 'P') {
				//				System.out.println(6);
				switch(http.get_path()){
				case 5:		    		
					communication.addData(http.get_shost(), "demo", http.get_dados());
					break;
				case 2:		    		
					communication.addData(http.get_shost(), "umidade", http.get_dados());
					break;
				case 3:
					communication.addData(http.get_shost(), "photo", http.get_dados());
					break;
				}

			} else {

				// Se n�o for Get ou Put envia mensagem de erro.
			}

		}

	}

	public MsgHttp ArduinoMsgReceived(MsgHttp http) {


		// Verifica se e' discovery
		if (http.getCodigo() == 2000) {
			//			System.out.println(2);
			// Comparacoes de banco de dados
			//
			String plataform="";
			switch(http.getPath()){
			case 0:
				plataform = this.Check(http.getShost(), "demo");
				break;
			case 2:
				plataform = this.Check(http.getShost(), "umidade");
				break;
			case 3:
				plataform = this.Check(http.getShost(), "photo");				
				break;
			}

			//			System.out.println("Plataforma="+plataform);
			if (plataform.equalsIgnoreCase("Arduino")) {
				// Se existir no banco
				//				System.out.println(3);
				http.criaHttp(http.getShost(), 2000);
				return http;


			} else {
				// Adiciona no banco e envia de volta
				switch(http.getPath()){
				case 0:
					communication.addService(http.getShost(), "demo", "Arduino");
					break;
				case 2:
					communication.addService(http.getShost(), "umidade", "Arduino");
					break;
				case 3:
					communication.addService(http.getShost(), "photo", "Arduino");
					break;
				}
				//				System.out.println(4);
				//				System.out.println("shost:" + http.getShost());
				http.criaHttp(http.getShost(), 2000);
				return http;
			}
			// >>>>>>
		} else {
			//			System.out.println(5);
			// Verifica se � Get ou Put.
			if ((char)http.getMetodo() == 'G' || (char)http.getMetodo() == 'P') {
				//				System.out.println(6);
				switch(http.getPath()){
				case 5:		    		
					communication.addData(http.getShost(), "demo", http.getDados());
					break;
				case 2:		    		
					communication.addData(http.getShost(), "umidade", http.getDados());
					break;
				case 3:
					communication.addData(http.getShost(), "photo", http.getDados());
					break;
				}
				return http;
			} else {
				//				System.out.println(7);
				// Se n�o for Get ou Put envia mensagem de erro.
				http.criaHttp(http.getShost(), (short) 405);
				return http;
			}

		}
	}


	public String NewQuery(String sensorType){

		String sql = "select * from DATA_READ where sensor_type = "+"'"+sensorType+"'";		
		return communication.Query(sql);		
	}
	public String NewQuery(int sensorId){

		String sql = "select * from DATA_READ where sensor_id = "+sensorId;
		return communication.Query(sql);
	}
	public String NewQuery(){

		String sql = "select * from SERVICES";// ORDER BY AREA";
		return communication.Query(sql);

	}

	public void DataRequest(int sensorId, String sensorType, String plataform) {
		rssf.request(sensorId, sensorType, plataform);		
	}

	public String Check(int sensorId, String sensorType) {
		String sql = " select DISTINCT plataform from SERVICES where sensor_id= "+sensorId+" AND sensor_type= '"+sensorType+"'"; 
		return communication.CheckExistance(sql);
	}


}
