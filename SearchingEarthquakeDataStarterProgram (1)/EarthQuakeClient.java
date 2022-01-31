import java.util.*;
import edu.duke.*;

public class EarthQuakeClient {
    public EarthQuakeClient() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData,
    double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        for(QuakeEntry q: quakeData){
            if (q.getMagnitude()> magMin){
                answer.add(q);
                
            }
        }
        return answer;
    }

    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData,
    double distMax,
    Location from) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        // TODO
        for(QuakeEntry qp: quakeData ){
            if(qp.getLocation().distanceTo(from) < distMax){
                answer.add(qp);
            }
        }
        return answer;
    }
    public ArrayList<QuakeEntry> filterByPhase(ArrayList<QuakeEntry> quakeData, String where,
    String phrase){
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qp: quakeData){
            if(where.equals("start")){
                if(qp.getInfo().startsWith(phrase)){
                answer.add(qp);
            }
            } else if(where.equals("end")){
                if(qp.getInfo().endsWith(phrase)){
                answer.add(qp);
            }
            } else if(where.equals("any")){
                if(qp.getInfo().contains(phrase)){
                answer.add(qp);
            }
            }
        }
        return answer;
    }
    
    
    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData, 
    double maxDepth, double minDepth){
        ArrayList<QuakeEntry> ans = new ArrayList<QuakeEntry>();
        for(QuakeEntry qp: quakeData){
            if(qp.getDepth() > minDepth && qp.getDepth() < maxDepth ){
                ans.add(qp);
            }
        }
        return ans;
    }
    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }

    }

    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        ArrayList<QuakeEntry> listy = filterByMagnitude(list,5.0);
        System.out.println("read data for "+list.size()+" quakes");
        for(QuakeEntry j : listy){
            System.out.println(j);
        
        }
        System.out.println("Found "+listy.size()+" quakes that match that criteria");

    }
    public void QuakesOfDepth(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        ArrayList<QuakeEntry> listy = filterByDepth(list,-5000.0,-8000.0);
        for(QuakeEntry pj: listy){
            System.out.println(pj);
        }
        System.out.println("Found "+listy.size()+" quakes that match that criteria");
    }
    public void closeToMe(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");

        // This location is Durham, NC
        Location city = new Location(35.988, -78.907);
    
        // This location is Bridgeport, CA
        // Location city =  new Location(38.17, -118.82);

        // TODO
        ArrayList<QuakeEntry> closeBy = filterByDistanceFrom(list,1000*1000, city);
        for(QuakeEntry i: closeBy){
            double distance = city.distanceTo(i.getLocation());
            System.out.println(distance/1000+" "+i.getInfo());
        }
        
    }
    
    public void quakesByPhrase(){
       EarthQuakeParser parser = new EarthQuakeParser();
       String source = "data/nov20quakedata.atom";
       ArrayList<QuakeEntry> list  = parser.read(source);
       System.out.println("read data for "+list.size()+" quakes");
       System.out.println();
       String where = "any";
       String phrase = "Creek";
       ArrayList<QuakeEntry> listy = filterByPhase(list, where, phrase);
       for(QuakeEntry qp: listy){
           System.out.println(qp);
       }
       System.out.println("Found " + listy.size() + " quakes that match that criteria");
    }

    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        for (QuakeEntry qe : list) {
            System.out.println(qe);
        }
    }
    
}
