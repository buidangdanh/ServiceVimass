package vn.vimass.service.log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Log {
	
	public static void logServices(String sNoiDung)
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		SimpleDateFormat sdfDate = new SimpleDateFormat("dd_MM_yyyy");

		String giophutgiay = sdf.format(cal.getTime());
		
		Date dateNow = new Date();
//		String part = "" + (dateNow.getMinutes() / 10);
		
		int gio =  dateNow.getHours();
		
		String Gio = "_" + gio
//				+ "_" + part + "_"
				;
		
		if(gio >=0 && gio <= 5)
		{
			Gio = "_0_5";
		}
		else if(gio >=6 && gio <= 8)
		{
			Gio = "_6_8";
		}
		else if(gio >=9 && gio <= 10)
		{
			Gio = "_9_10";
		}
		else if(gio >=11 && gio <= 12)
		{
			Gio = "_11_12";
		}
		else if(gio >=13 && gio <= 14)
		{
			Gio = "_13_14";
		}
		else if(gio >=15 && gio <= 16)
		{
			Gio = "_15_16";
		}
		else if(gio >=17 && gio <= 18)
		{
			Gio = "_17_18";
		}
		else if(gio >=19 && gio <= 23)
		{
			Gio = "_19_23";
		}
		
		String tenFile = sdfDate.format(cal.getTime()) + Gio + ".txt";
		String noiDungLog = giophutgiay + "\t"
				 + sNoiDung + "\n";
//		Log.logServices(noiDungLog);
		 FileManager.writeFile(ServicesData.LOG_PATH +"servicesLog"+ "_" + tenFile , noiDungLog , true);
	}

}
