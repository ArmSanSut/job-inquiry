package coding.assessment.job_inquiry.utility;

import io.micrometer.common.util.StringUtils;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Utils {

    public static Integer parseSalary(String rawSalary) {
        if (StringUtils.isBlank(rawSalary)) {
            return null;
        }

        String cleanedSalary = rawSalary.trim().toLowerCase();

        if (cleanedSalary.endsWith("k")) {
            try {
                double kVal = Double.parseDouble(cleanedSalary.replace("k", ""));
                return (int) (kVal * 1000);
            } catch (NumberFormatException e) {
                return null;
            }
        }

        cleanedSalary = cleanedSalary.replace("[^\\d]", "");

        if (cleanedSalary.isBlank()) return null;

        try {
            return Integer.parseInt(cleanedSalary);
        } catch (Exception ex) {
            return null;
        }
    }
}
