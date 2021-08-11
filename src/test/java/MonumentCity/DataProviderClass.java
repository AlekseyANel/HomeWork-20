package MonumentCity;

import org.testng.annotations.DataProvider;

public class DataProviderClass {

    @DataProvider(name="search")
    public static Object[][] getDataProviderData(){
        Object[][] searchWords=new Object[2][2];
        //Enter data into Object Array
        searchWords[0][0]="Taj Mahal";
        searchWords[0][1]="Agra";
        searchWords[1][0]="Char Minar";
        searchWords[1][1]="Hyderabad";
        return searchWords;

    }

}
