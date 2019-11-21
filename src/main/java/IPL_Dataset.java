import java.util.*;
import java.io.*;
public class IPL_Dataset
{
    /*Method to read data from the csv files*/
    public static List<String[]> readData(String path)
    {
        //Method to read data from the csv files
        BufferedReader br;
        String line="";
        List<String[]> seperated_Line=new ArrayList<>();
        try
        {
            br=new BufferedReader(new FileReader(path));
            while((line=br.readLine())!=null)
            {
                String word[]=line.split(",");
                seperated_Line.add(word);
            }
            br.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return seperated_Line;
    }

    /*1 -Method to count the number of matches played per year*/
    public static HashMap<String, Integer> matches_per_year(List<String[]> matches)
    {
        HashMap<String, Integer> count=new HashMap<>();
        for(int i=1;i<matches.size();i++)
        {
            String a[]=matches.get(i);
            if(count.isEmpty() || !count.containsKey(a[1]))
            {
                count.put(a[1],1);
            }
            else
            {
                count.put(a[1],(count.get(a[1])+1));
            }
        }
        return count;
    }

    /*2 - Method to count the number of matches won per year*/
    public static HashMap<String, Integer> won_per_year(List<String[]> matches)
    {
        HashMap<String, Integer> count=new HashMap<>();
        for(int i=1;i<matches.size();i++)
        {
            String a[]=matches.get(i);
            if(count.isEmpty() || !count.containsKey(a[10]))
            {
                if(a[10].equals(""))
                {
                    continue;
                }
                else
                {
                    count.put(a[10], 1);
                }
            }
            else
            {
                count.put(a[10], (count.get(a[10]) + 1));
            }
        }
        return count;
    }

    /*3 - Method for getting extra runs conceded per team in 2016*/
    public static HashMap<String, Integer> extra_runs(List<String[]> matches,List<String[]> deliveries)
    {
        HashMap<String, Integer> runs_conceded=new HashMap<>();
        List<String> match_id=new ArrayList<>();
        for(int i=1;i<matches.size();i++)
        {
            String a[]=matches.get(i);
            if(a[1].equals("2016"))
            {
                match_id.add(a[0]);
            }
        }
        for(String id:match_id)
        {
            for (int i = 1; i < deliveries.size(); i++)
            {
                String b[] = deliveries.get(i);
                if (id.equals(b[0]))
                {
                    if (runs_conceded.isEmpty() || !runs_conceded.containsKey(b[2]))
                    {
                        int er = Integer.valueOf(b[16]);
                        runs_conceded.put(b[2], er);
                    }
                    else
                        {
                        int er = Integer.valueOf(b[16]);
                        int c = Integer.valueOf(runs_conceded.get(b[2]));
                        runs_conceded.put(b[2], (er + c));
                    }
                }
            }
        }
        return runs_conceded;
    }
    public static void main(String[] args)
    {
        //Path of csv files
        String deliveries_csv_path="C:/Users/rohit/IdeaProjects/Data_Project/src/main/resources/deliveries.csv";
        String matches_csv_path="C:/Users/rohit/IdeaProjects/Data_Project/src/main/resources/matches.csv";

        //Storing the data of csv file in the list of string array
        List<String[]> matches=readData(matches_csv_path);
        List<String[]> deliveries=readData(deliveries_csv_path);

        //User input for choice
        Scanner sc=new Scanner(System.in);
        System.out.println("1. Number of matches played per year of all the years in IPL.\n" +
                "2. Number of matches won of all teams over all the years of IPL.\n" +
                "3. For the year 2016 get the extra runs conceded per team.\n" +
                "4. For the year 2015 get the top economical bowlers.\n");
        System.out.println("Enter Your Choice : ");
        int choice=sc.nextInt();
        switch(choice)
        {
            case 1:
                //1 - Counting number of matches played per year
                HashMap<String, Integer> matches_played_per_year=matches_per_year(matches);
                System.out.println("<----------------Number of matches played per year in IPL-------------->");
                System.out.println("Year\tMatches");
                for(String i:matches_played_per_year.keySet())
                {
                    System.out.println(i+"\t"+matches_played_per_year.get(i));
                }
                break;
            case 2:
                //2 - Counting number of matches won per year
                System.out.println("<----------------Number of matches won per year in IPL-------------->");
                System.out.println("Team\t\tMatches");
                HashMap<String, Integer> matches_won_per_year=won_per_year(matches);
                for(String i:matches_won_per_year.keySet())
                {
                    System.out.println(i+"\t\t"+matches_won_per_year.get(i));
                }
                break;
            case 3:
                //3 - Extra runs conceded in 2016 per team
                System.out.println("<----------------Extra runs conceded per team in 2016-------------->");
                System.out.println("Team\t\tRuns");
                HashMap<String, Integer> extra_runs_per_team=extra_runs(matches,deliveries);
                for(String i:extra_runs_per_team.keySet())
                {
                    System.out.println(i+"\t\t"+extra_runs_per_team.get(i));
                }
                break;
            default:
                System.out.println("Invalid Choice");
        }
    }
}
