package yazlab2;

import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Random;

/**
 *
 * @author merth
 */
public class Yazlab2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("KARE OLMAYAN MATRISIN SOZDE TERSINI BULMA\n");
        System.out.println("Manuel için [1]\nRandom için [2]\n");
        Scanner s= new Scanner(System.in);
        int secenek;
        do{
            secenek=s.nextInt();
        }while(!(secenek>=1 && secenek <=2));
        double matris[][];
        double transpoz[][];
        int satir;
        int sutun;
        if(secenek==1){
            System.out.println("Matris satir sayisini girin [1-5]:");
            do{
            satir=s.nextInt();
            }while(!(satir>0 && satir<6));
            System.out.println("Matris sütun sayisini girin [1-5]:");
            do{
            sutun=s.nextInt();
            }while(!(sutun>0 && sutun<6));
            matris=new double[satir][sutun];
            transpoz=new double[sutun][satir];
            
            for (int i = 0; i < satir; i++) {
                for (int j = 0; j < sutun; j++) {
                    System.out.println("Matris ["+i+"]["+j+"] girin:");
                    double sayi=s.nextDouble();
                    matris[i][j]=sayi;
                    
                }
            }

            for (int i = 0; i < sutun; i++) {
                for (int j = 0; j < satir; j++) {
                    transpoz[i][j]=matris[j][i];
                }
            }
            
            Form f=new Form();
            //Carpimin solunda transpoz oldugu icin satir sutunlari ters yolluyoruz
            f.getMatris(transpoz,matris,sutun,satir);
            f.setVisible(true);
 
        }else if(secenek==2){
            Random r=new Random();
            DecimalFormat df=new DecimalFormat("#.#");
            double rand;
            String randstr;
            satir=r.nextInt(5)+1;
            System.out.println("Satir:"+satir);
            sutun=r.nextInt(5)+1;
            System.out.println("Sutun:"+sutun);
            matris=new double[satir][sutun];
            transpoz=new double[sutun][satir];
            for (int i = 0; i < satir; i++) {
                for (int j = 0; j < sutun; j++) {
                    rand=(r.nextDouble()*10)%8.0+1.0;
                    randstr=df.format(rand);
                    randstr=randstr.replace(',', '.');
                    rand=Double.valueOf(randstr);
                    matris[i][j]=rand;
                }
            }
            for (int i = 0; i < sutun; i++) {
                for (int j = 0; j < satir; j++) {
                    transpoz[i][j]=matris[j][i];
                }
            }
            Form f=new Form();
            f.getMatris(transpoz,matris,sutun,satir);
            f.setVisible(true);
            
        }
        
    }
    
}
