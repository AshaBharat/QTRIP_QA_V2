package qtriptest;


import java.io.File;
import java.sql.Timestamp;
import com.relevantcodes.extentreports.ExtentReports;


public class ReportSingleton {
    private static ReportSingleton instanceOfExtentReportSingleton = null;
    private ExtentReports report;

    public static String getTimestamp(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return String.valueOf(timestamp.getTime());
    }
    private ReportSingleton(){
        report = new ExtentReports(System.getProperty("user.dir") + "/Reports/" + getTimestamp() + ".html", true);
        report.loadConfig(new File(System.getProperty("user.dir")+"/extent_customization_configs.xml"));
    }
    public static ReportSingleton getInstanceOfSingletonReportClass(){
        if(instanceOfExtentReportSingleton == null){
            instanceOfExtentReportSingleton = new ReportSingleton();
        }
        return instanceOfExtentReportSingleton;
    }
    public ExtentReports getReport(){
        return report;
    }
}