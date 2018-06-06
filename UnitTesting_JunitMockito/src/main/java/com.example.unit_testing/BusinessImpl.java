/**
  * Created by Saddam on 05/31/2018.
  */
 
package com.example.unit_testing;
 
import java.util.ArrayList;
import java.util.List;

public class BusinessImpl {

    StubClass stubClass = new StubClass();

    public BusinessImpl(StubClass stubClass) {
      super();
      this.stubClass = stubClass;
    }

    public List<String> retrieveRelatedToSpring(String input) {

        List<String> filteredValue = new ArrayList<String>();
        List<String> stubValue = stubClass.retrieve(input);

        for(String value: stubValue) {

            if(value.contains("Spring")) {
              filteredValue.add(value);
            }
        }
      return filteredValue;
    }
}
