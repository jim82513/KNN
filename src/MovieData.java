public class MovieData{
		
		int uid;
		int mid;
		double rank;
		int time; //useless
			
		public MovieData(String csvString){
			
			String[] datas = csvString.split(",");
			
			this.uid = Integer.parseInt(datas[0]);
			this.mid = Integer.parseInt(datas[1]);
			this.rank = Double.parseDouble(datas[2]);
			this.time = Integer.parseInt(datas[3]);
		}
	}