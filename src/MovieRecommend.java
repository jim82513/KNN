import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;




public class MovieRecommend {
	final int USER = 943;
	final int MOVIES = 1682;
	int KNN=10;
	double userItem[][] = new double[MOVIES][USER]; 
	double CosPredictUserItem[][] = new double[MOVIES][USER];
	double PearPredictUserItem[][] = new double[MOVIES][USER]; 
	double userAvg[] = new double[USER];
	double CosSim[][] = new double[USER][USER];
	double PeaSim[][] = new double[USER][USER];
	
	public static void main(String args[]){
		
		new MovieRecommend().execute();
	}

	private void execute() {
		userItem = new MovieRecommend().ReadCsv();
		System.out.println(userItem[0].length);
		CreateAvg(userAvg,userItem);
		CreateUserSim(userItem,CosSim);
		CreatePearUserSim(userItem,PeaSim);
		
		System.out.println();

		//System.out.println(CosPredictUserItem[0].length);
		Predict(KNN);
		//PearPredict(KNN);
		System.out.println("finish");
	}
	
	private void PearPredict(int KNN) {
		for(KNN=50;KNN<=50;KNN+=10){
			for(int i=0;i<PearPredictUserItem[0].length;i++){
				for(int j=0;j<PearPredictUserItem.length;j++){
					if(userItem[j][i]!=0){
						PearPredictUserItem[j][i]= userAvg[i]+PeaKnn(i,j,KNN);
					}
					else{
						PearPredictUserItem[j][i]=0;
					}
				}
			}
			double PearcountNumber=0;
			double PMAE=0;
			for(int i=0;i<PearPredictUserItem.length;i++){
				for(int j=0;j<PearPredictUserItem[0].length;j++){
					if(userItem[i][j]!=0){
						double b = (Math.abs(PearPredictUserItem[i][j]-userItem[i][j]));
						PearcountNumber++;
						PMAE+=b;						
					}
					else{
					
					}
				}
			}
			//System.out.println("when k is "+KNN+" MAE= "+MAE/countNumber);
			System.out.println("when k is "+KNN+" PMAE= "+PMAE/PearcountNumber);
			//System.out.println("   ");
			
			/*for(int i=0;i<CosPredictUserItem[0].length;i++){
					System.out.println(CosPredictUserItem[i][0]+" "+userItem[i][0]);
			
			}*/
			
		}	
	}

	private void Predict(int KNN) {
		for(KNN=5;KNN<=40;KNN+=5){
			for(int i=0;i<CosPredictUserItem[0].length;i++){
				for(int j=0;j<CosPredictUserItem.length;j++){
					if(userItem[j][i]!=0){
						CosPredictUserItem[j][i]= userAvg[i]+Knn(i,j,KNN);
					}
					else{
						CosPredictUserItem[j][i]=0;
					}
				}
			}
			double countNumber =0;
			double MAE=0;
			for(int i=0;i<CosPredictUserItem.length;i++){
				for(int j=0;j<CosPredictUserItem[0].length;j++){
					if(userItem[i][j]!=0){
						double a = (Math.abs(CosPredictUserItem[i][j]-userItem[i][j]));
						countNumber++;
						MAE+=a;
						
					}
					else{
					
					}
				}
			}
			System.out.println("when k is "+KNN+" MAE= "+MAE/countNumber);
			
			/*for(int i=0;i<CosPredictUserItem[0].length;i++){
					System.out.println(CosPredictUserItem[i][0]+" "+userItem[i][0]);
			
			}*/
			
		}
	}

	private double PeaKnn(int main_user, int item, int KNN) {
		double temp[]=new double[PeaSim.length];
		double Max= 0;
		double sum_sim=0;
		double sum=0;
		for(int i=0;i<temp.length;i++){
			if(i==main_user){
				temp[i]=-1;
			}
			else{
				temp[i]=PeaSim[main_user][i];
			}
		}
		Arrays.sort(temp);
		/*System.out.println("Pearson");
		for(int i=temp.length-10;i<temp.length;i++){
			System.out.print(temp[i]+" ");
		}
		System.out.println(" ");*/
		Max=temp[temp.length-KNN];
		for(int sideUser=0;sideUser<PeaSim.length;sideUser++){
			if(PeaSim[main_user][sideUser]>=Max){
				sum+=(PeaSim[main_user][sideUser])*(userItem[item][sideUser]);
				sum_sim+=(PeaSim[main_user][sideUser]);   //abs?
			}
			else{
				
			}
		}
		//System.out.println("PSum is + "+sum);
		//System.out.println("PSum_sim is + "+sum_sim);
		return sum/sum_sim;
	}

	private double Knn(int main_user, int item, int KNN) {
		//System.out.println(main_user+" "+item+" "+KNN);
		double temp[]=new double[CosSim.length];
		double Max=0;
		double sum_sim=0;
		double sum=0;
		for(int i=0;i<temp.length;i++){
			if(i==main_user){
				temp[i]=0;
			}
			else{
				temp[i]=CosSim[main_user][i];
			}
		}

		Arrays.sort(temp);
		/*for(int i=temp.length-10;i<temp.length;i++){
			System.out.print(temp[i]+" ");
		}*/
		Max=temp[temp.length-KNN];
		for(int sideUser=0;sideUser<CosSim.length;sideUser++){
			if(CosSim[main_user][sideUser]>=Max){
				sum+=(CosSim[main_user][sideUser])*(userItem[item][sideUser]);
				sum_sim+=(CosSim[main_user][sideUser]);
			}
			else{
				
			}
		}
		//System.out.println("CSum is + "+sum);
		//System.out.println("CSum_sim is + "+sum_sim);
		return sum/sum_sim;
	}

	/*
	 *create user similarity to a user-user martix 
	 * */
	private double[][] CreateUserSim(double[][] userItem, double[][] CosSim) {
		for(int j=0;j<USER;j++){
			for(int i=0;i<USER;i++){
				double sim1[] =new double[MOVIES];
				double sim2[] = new double[MOVIES];
				for(int k=0;k<MOVIES;k++){
					sim1[k]=userItem[k][i];
					sim2[k]=userItem[k][j];
				}
				CosSim[j][i]=CosineSim(sim1,sim2);
			}
		}
		return CosSim;
	}
	/*
	 *pearson sim 
	 * */
	private double[][] CreatePearUserSim(double[][] userItem, double[][] PeaSim) {
		for(int j=0;j<USER;j++){
			for(int i=0;i<USER;i++){
				double sim1[] =new double[MOVIES];
				double sim2[] = new double[MOVIES];
				for(int k=0;k<MOVIES;k++){
					sim1[k]=userItem[k][i];
					sim2[k]=userItem[k][j];
				}
				PeaSim[j][i]=PearsonSim(sim1,sim2,j,i);
			}
		}
		return PeaSim;
	}
	/*
	 * cal pearson sim
	 * */
	private double PearsonSim(double[] sim1, double[] sim2, int mainUser, int sideUser) {
		double dotProduct= 0.0;
		double magnitude1 = 0.0;   
        double magnitude2 = 0.0;   
        double pearsonSimilarity = 0.0;
        boolean noCommon= false; 
        
        for(int j=0;j<MOVIES;j++){
        	if(sim1[j]!=0 && sim2[j]!=0){
				dotProduct += (sim1[j]-userAvg[mainUser])*(sim2[j]-userAvg[sideUser]);
				magnitude1 += Math.pow(sim1[j]-userAvg[mainUser],2);
				magnitude2 += Math.pow(sim2[j]-userAvg[sideUser],2);
				//System.out.println(dotProduct+" "+magnitude1+" "+magnitude2);
				noCommon=true;
			}
			else{
				
			}   	
        }
		if(noCommon==false){
			pearsonSimilarity=-1;
		}
		else{
			double mag= magnitude1 * magnitude2;
			double s = Math.sqrt(mag);//sqrt(a^2)   
			pearsonSimilarity = dotProduct / s;
		}
            /*System.out.println("*******************************");
            System.out.println(cosineSimilarity);*/  
        return pearsonSimilarity;
	}

	/*
	 * cosine similarity
	 * */
	private double CosineSim(double[] userItem, double[] user_item2) {
		double dotProduct= 0.0;
		double magnitude1 = 0.0;   
        double magnitude2 = 0.0;   
        double cosineSimilarity = 0.0;
        boolean noCommon= false;
        
		for(int j=0;j<MOVIES;j++){
			if(userItem[j]!=0 && user_item2[j]!=0){
				dotProduct += userItem[j] * user_item2[j];
				magnitude1 += Math.pow(userItem[j],2);
				magnitude2 += Math.pow(user_item2[j],2);
				//System.out.println(dotProduct+" "+magnitude1+" "+magnitude2);
				noCommon=true;
			}
			else{
				
			}
 		}
		
		if(noCommon==false){
			cosineSimilarity=0.0;
		}
		else{
			double mag= magnitude1 * magnitude2;
			double s = Math.sqrt(mag);//sqrt(a^2)   
	        cosineSimilarity = dotProduct / s;
		}
            /*System.out.println("*******************************");
            System.out.println(cosineSimilarity);*/  
        return cosineSimilarity;
	}

	/*
	 * user avg rating
	 * */
	private double[] CreateAvg(double[] userAvg, double[][] userItem) {
		for(int i=0;i<userItem[0].length;i++){
			int count=0;
			int avg_time=0;
			for(int j=0;j<userItem.length;j++){
				if(userItem[j][i]==0){
					
				}
				else{
					count+= userItem[j][i];
					avg_time++;
				}
			}
			userAvg[i]=count/((double)avg_time);
			//userAvg[i]=(double)(Math.round(userAvg[i]*100))/100;
		}
		return userAvg;
	}
	/*
	 * reading csv file
	 * */
	private double [][] ReadCsv() {
		try{
			BufferedReader reader = new BufferedReader(new FileReader("rating.csv"));
			System.out.println("check");
			reader.readLine();
			String line = null;
			int count=0;
			while((line=reader.readLine())!=null){
				MovieData d = new MovieData(line);
                /*Double value = Double.parseDouble(last);//如果是数值，可以转化为数值
                System.out.println("User ID: "+d.uid);
                System.out.println("Movie ID: "+d.mid);
                System.out.println("Rank: "+d.rank);*/
                userItem[(d.mid)-1][(d.uid)-1] = d.rank;
                //System.out.println(userItem[(d.uid)-1][(d.mid)-1]);
			}
		} catch (Exception e) { 
            e.printStackTrace(); 
        }
		
		return userItem;
	}
	

}
