import urllib2
import datetime
import time

def main():
    hour = 11
    minutes = 0
    numQuotes = 0 
    refresh = 0
    quotes = []
    latestPrice = []

    while int(hour) <= 16 and int(hour) >= 9:
        dateAndTime = str(datetime.datetime.now())
        totalTime = dateAndTime[11:16]
        hour = totalTime[0:2]
        minutes = totalTime[3:5]
        
    
       ## if (int(hour) < 9) or (int(hour) == 9 and int(minutes) < 30) or (int(hour) > 16) or (int(hour) == 16 and int(minutes) > 0):
         ##   print ("Stock Market is currently closed. Please come back between 9:30 AM and 4:00 PM")
           ## return
    
        if numQuotes == 0:
            numQuotes = int(input("How many company quotes would you like to monitor?"))
            
        
            for x in xrange(0, numQuotes):
                quotes.append(raw_input("Enter Quote #" + str(x + 1) + ":"))
    
                
    
            for x in xrange(0, numQuotes):
                latestPrice.append("blank")
        
            refresh = int(input("How long would you like to wait for the feed to refresh?"))
    
        for x in xrange(0, numQuotes):
            ticker = quotes[x].lower()
            website = "http://finance.yahoo.com/q;_ylt=AugwQt0Hk_iaaowuO255_2uiuYdG;_ylu=X3oDMTBxdGVyNzJxBHNlYwNVSCAzIERlc2t0b3AgU2VhcmNoIDEx;_ylg=X3oDMTBsdWsyY2FpBGxhbmcDZW4tVVMEcHQDMgR0ZXN0Aw--;_ylv=3?s=" + ticker + "&uhb=uhb2&type=2button&fr=uh3_finance_vert_gs"
            response = urllib2.urlopen(website)
            financeYahoo = ""
            while response.readline():
                financeYahoo = financeYahoo + str(response.readline())
            strWithTicker = "<span id=\"yfs_l84_" + ticker + "\">"
            if strWithTicker in financeYahoo:
                firstFound = financeYahoo.index("<span id=\"yfs_l84_" + ticker + "\">")
                secondFound = financeYahoo.find("</span>", firstFound+1)
                spanNode = financeYahoo[firstFound:secondFound+7]
                secondArrow = int(spanNode.index('>'))
                thirdArrow = int(spanNode.index('<', secondArrow+1))
                stockPrice = spanNode[secondArrow+1:thirdArrow]
                
                if (latestPrice[x] == "blank"):
                    latestPrice[x] = stockPrice
                    print (ticker+": " +latestPrice[x])
                
                else:
                    if latestPrice[x] != stockPrice:
                        latest = float(stockPrice)
                        previous = float(latestPrice[x])
                        
                        if latest>previous:
                            print (ticker + " went up to: "+ str(latest))
                    
                        else:
                            if latest<previous:
                                print (ticker + " went down to: "+ str(latest))
                    
                    else:
                        print (ticker + " remains at: " + str(latestPrice[x]))
        print ("Refreshing...")
        time.sleep(refresh)
        
                    
if __name__ == '__main__':
    main()
