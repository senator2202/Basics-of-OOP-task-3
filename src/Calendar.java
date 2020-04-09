
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

public class Calendar {
    LocalDate currentDate;
    int year;
    ArrayList<DayOff> dayOffs;
    int nonWorkingDays;

    public Calendar(int year){
        currentDate=LocalDate.now();
        this.year=year;
        dayOffs=new ArrayList<DayOff>();
        initialize();//заполняем список праздников и выходных в году
    }


    public static void main(String []args) {
        int year=2025;
        Calendar c=new Calendar(year);
        System.out.println("All non-working holidays in "+year+":");
        for (String s:c.getAllHolidays()) {
            System.out.println(s);
        }
        if(c.isDayOff(5,9)==true)
            System.out.println(year+"-05-09 is a non-working day!");
        else
            System.out.println(year+"-05-09 is working day!");
        if(c.isDayOff(2,2)==true)
            System.out.println(year+"-02-02 is a non-working day!");
        else
            System.out.println(year+"-02-02 is a working day!");
    }


    void initialize(){
        LocalDate l=currentDate.minusDays(currentDate.getDayOfYear()-1);
        l=l.plusYears(year-currentDate.getYear());//начало нужного года
        for(int i=1;i<l.lengthOfYear();i++){
            if(isWeekend(l)==true){
                DayOff d=new DayOff(l,DayOffType.Weekend);
                d.date=l;
                dayOffs.add(d);
            }
            else
                if(isHoliday(l)==true){
                    DayOff d=new DayOff(l,DayOffType.Weekend);
                    d.type=DayOffType.Holiday;
                    d.date=l;
                    nonWorkingDays++;
                    dayOffs.add(d);
                }
            l=l.plusDays(1);
        }
    }//заполняем список праздников и выходных в году

    boolean isWeekend(LocalDate l){
        return l.getDayOfWeek()== DayOfWeek.SATURDAY ||l.getDayOfWeek()==DayOfWeek.SUNDAY;
    }

    boolean isHoliday(LocalDate l){
        if(l.getDayOfMonth()==1 && l.getMonth()== Month.JANUARY)
            return true;
        if(l.getDayOfMonth()==7 && l.getMonth()== Month.JANUARY)
            return true;
        if(l.getDayOfMonth()==8 && l.getMonth()== Month.MARCH)
            return true;
        if(l.getDayOfMonth()==1 && l.getMonth()== Month.MAY)
            return true;
        if(l.getDayOfMonth()==9 && l.getMonth()== Month.MAY)
            return true;
        if(l.getDayOfMonth()==3 && l.getMonth()== Month.JULY)
            return true;
        if(l.getDayOfMonth()==7 && l.getMonth()== Month.NOVEMBER)
            return true;
        if(l.getDayOfMonth()==25 && l.getMonth()== Month.DECEMBER)
            return true;
        return false;
    }

    public String [] getAllHolidays(){
        String []res=new String[nonWorkingDays];
        int i=0;
        for(DayOff d: dayOffs)
            if(d.type==DayOffType.Holiday)
                res[i++]=d.date.toString()+", "+d.day;
        return res;
    }

    public boolean isDayOff(int month, int day){
        for (DayOff d:dayOffs
             ) {
            if (d.date.getMonth().getValue()==month && d.date.getDayOfMonth()==day)
                return true;
        }
        return false;
    }

    class DayOff{
        DayOffType type;
        LocalDate date;
        DayOfWeek day;

        DayOff(LocalDate date, DayOffType type){
            this.date=date;
            this.type=type;
            day=date.getDayOfWeek();
        }
    }
}
