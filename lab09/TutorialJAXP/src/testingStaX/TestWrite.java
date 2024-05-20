package testingStaX;

public class TestWrite {

    public static void main(String[] args) {
            StaXWriter configFile = new StaXWriter();
            configFile.setFile("config2.xml");
            try {
                    configFile.saveConfig();
            } catch (Exception e) {
                    e.printStackTrace();
            }
    }
}