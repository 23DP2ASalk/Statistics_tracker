package lv.rvt;

public class Main 
{
    public static void main( String[] args )
    {
        PlayerService service = new PlayerService();
        ConsoleUI ui = new ConsoleUI(service);
        ui.start();
    }
}
