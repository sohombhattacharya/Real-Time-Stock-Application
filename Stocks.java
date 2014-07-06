import java.text.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.lang.*;

public class Stocks {

	public static void main(String[] args) throws IOException {

		//also include a way for the user to put in a company name if they don't know ticker 
		
		
		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);

		int hour = 11; 
		int minutes = 0;
		int numQuotes = 0; 
		int refresh = 0;
		String[] quotes = new String[numQuotes];
		String[] latestPrice = new String[numQuotes];
		System.out.println("test");
		
		while ((hour <= 16 && hour>=9) ){
			
			DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			Date dateObj = new Date();
			String dateTime = df.format(dateObj);
			String time = dateTime.substring(9, 14);
			hour = Integer.parseInt(time.substring(0, 2));
			minutes = Integer.parseInt(time.substring(3, 5));
			
			
			
			if (hour<9 || (hour == 9 && minutes<30) || (hour>16) || (hour == 16 && minutes>0)) {
				System.out.println("The Stock Market is currently closed. Please come back between 9:30 AM and 4:00 PM");
				return;
			}
			
			
			if (numQuotes == 0) {
				System.out.println("How many company quotes would you like to monitor?");
				numQuotes = reader.nextInt();
				quotes = new String[numQuotes];

				for (int i = 0; i < numQuotes; i++) {
					System.out.println("Enter Quote #" + (i + 1));
					String quote = reader.next();
					quotes[i] = quote;

				}

				latestPrice = new String[numQuotes];

				for (int i = 0; i < numQuotes; i++) {
					latestPrice[i] = "blank";
				}

				System.out.println("How long would you like to wait for feed to refresh?");
				refresh = reader.nextInt();
			}
			
			
			
			
			for (int i = 0; i < numQuotes; i++) {

				String ticker = quotes[i].toLowerCase();
				String website = "http://finance.yahoo.com/q;_ylt=AugwQt0Hk_iaaowuO255_2uiuYdG;_ylu=X3oDMTBxdGVyNzJxBHNlYwNVSCAzIERlc2t0b3AgU2VhcmNoIDEx;_ylg=X3oDMTBsdWsyY2FpBGxhbmcDZW4tVVMEcHQDMgR0ZXN0Aw--;_ylv=3?s="
						+ ticker
						+ "&uhb=uhb2&type=2button&fr=uh3_finance_vert_gs";

				URL financeYahoo = new URL(website);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						financeYahoo.openStream()));

				String line;

				while ((line = in.readLine()) != null) {
					if (line.contains("<span id=\"yfs_l84_" + ticker + "\">") == true) {
						int firstFound = line.indexOf("<span id=\"yfs_l84_"
								+ ticker + "\">");
						int secondFound = line.indexOf("</span>",
								firstFound + 1);
						String spanNode = line.substring(firstFound,
								secondFound + 7);
						int secondArrow = spanNode.indexOf('>');
						int thirdArrow = spanNode.indexOf('<', secondArrow + 1);
						String stockPrice = spanNode.substring(secondArrow + 1,
								thirdArrow);

						if (latestPrice[i] == "blank") {
							latestPrice[i] = stockPrice;
							System.out.println(ticker + ": " + stockPrice);
						}

						else if (latestPrice[i] != stockPrice) {

							double latest = Double.parseDouble(stockPrice);
							double previous = Double.parseDouble(latestPrice[i]);

							if (latest > previous)
								System.out.println(ticker + " went up to: "+ latest);

							else if(latest < previous){
								System.out.println(ticker + " went down to: "+ latest);
							}
						}

						
					}

				}
			}
			try {
				System.out.println("Waiting...");
				Thread.sleep(refresh*1000);
			}

			catch (InterruptedException exc) {

			}
		
		
		}

		
	}
}
