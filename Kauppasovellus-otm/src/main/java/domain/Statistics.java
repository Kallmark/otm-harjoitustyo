
package domain;

import java.util.List;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class Statistics {
    
    /**
     * provides statistics using The Apache Commons Math -library. 
     */
    public Statistics() {

    }
    
    /**
     * Calculates the overall balance from each user. 
     * @param users a List of users from the database
     * @return overall balance as a double. 
     */
    public Double calculateOverallBalance(List<User> users) {
        DescriptiveStatistics allBalances = new DescriptiveStatistics();
        for (User user : users) {
            allBalances.addValue(user.getBalance());
        }
        return allBalances.getSum();
    }
    
    /**
     * Calculates the average balance of a user. 
     * @param users a List of users from the database. 
     * @return the average balance as a double. 
     */
    public Double calculateAverageBalance(List<User> users) {
        DescriptiveStatistics allBalances = new DescriptiveStatistics();
        for (User user : users) {
            allBalances.addValue(user.getBalance());
        }
        return allBalances.getMean();
    }

}
