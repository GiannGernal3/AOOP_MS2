/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PaySys_Classes;

/**
 *
 * @author Giann Gernale
 */
public class Deductions {
    private static String SSSContribution, PhilHealth, PagIbig, WithholdingTax;

    public static double convertTimeToDouble(String timeString) {
        String[] timeComponents = timeString.split(":");
        int hours = Integer.parseInt(timeComponents[0]);
        int minutes = Integer.parseInt(timeComponents[1]);

        double fractionalHour = minutes / 60.0; // Calculate the fractional part of the hour
        double timeInDouble = hours + fractionalHour; // Combine hours and fractional part

        return timeInDouble;
    }

    public static double sssContribution(double monthlySalary) {
        if (monthlySalary <= 3250) {
            return 135.00;
        } else if (monthlySalary >= 3250 && monthlySalary <= 3750) {
            return 157.50;
        } else if (monthlySalary >= 3750 && monthlySalary <= 4250) {
            return 180.00;
        } else if (monthlySalary >= 4250 && monthlySalary <= 4750) {
            return 202.50;
        } else if (monthlySalary >= 4750 && monthlySalary <= 5250) {
            return 225.00;
        } else if (monthlySalary >= 5250 && monthlySalary <= 5750) {
            return 247.50;
        } else if (monthlySalary >= 5750 && monthlySalary <= 6250) {
            return 270.00;
        } else if (monthlySalary >= 6250 && monthlySalary <= 6750) {
            return 292.50;
        } else if (monthlySalary >= 6750 && monthlySalary <= 7250) {
            return 315.00;
        } else if (monthlySalary >= 7250 && monthlySalary <= 7750) {
            return 337.50;
        } else if (monthlySalary >= 7750 && monthlySalary <= 8250) {
            return 360.00;
        } else if (monthlySalary >= 8250 && monthlySalary <= 8750) {
            return 382.50;
        } else if (monthlySalary >= 8750 && monthlySalary <= 9250) {
            return 405.00;
        } else if (monthlySalary >= 9250 && monthlySalary <= 9750) {
            return 427.50;
        } else if (monthlySalary >= 9750 && monthlySalary <= 10250) {
            return 450.00;
        } else if (monthlySalary >= 10250 && monthlySalary <= 10750) {
            return 472.50;
        } else if (monthlySalary >= 10750 && monthlySalary <= 11250) {
            return 495.00;
        } else if (monthlySalary >= 11250 && monthlySalary <= 11750) {
            return 517.50;
        } else if (monthlySalary >= 11750 && monthlySalary <= 12250) {
            return 540.00;
        } else if (monthlySalary >= 12250 && monthlySalary <= 12750) {
            return 562.50;
        } else if (monthlySalary >= 12750 && monthlySalary <= 13250) {
            return 585.00;
        } else if (monthlySalary >= 13250 && monthlySalary <= 13750) {
            return 607.50;
        } else if (monthlySalary >= 13750 && monthlySalary <= 14250) {
            return 630.00;
        } else if (monthlySalary >= 14250 && monthlySalary <= 14750) {
            return 652.50;
        } else if (monthlySalary >= 14750 && monthlySalary <= 15250) {
            return 675.00;
        } else if (monthlySalary >= 15250 && monthlySalary <= 15750) {
            return 697.50;
        } else if (monthlySalary >= 15750 && monthlySalary <= 16250) {
            return 720.00;
        } else if (monthlySalary >= 16250 && monthlySalary <= 16750) {
            return 742.50;
        } else if (monthlySalary >= 16750 && monthlySalary <= 17250) {
            return 765.00;
        } else if (monthlySalary >= 17250 && monthlySalary <= 17750) {
            return 787.50;
        } else if (monthlySalary >= 17750 && monthlySalary <= 18250) {
            return 810.00;
        } else if (monthlySalary >= 18250 && monthlySalary <= 18750) {
            return 832.50;
        } else if (monthlySalary >= 18750 && monthlySalary <= 19250) {
            return 855.00;
        } else if (monthlySalary >= 19250 && monthlySalary <= 19750) {
            return 877.50;
        } else if (monthlySalary >= 19750 && monthlySalary <= 20250) {
            return 900.00;
        } else if (monthlySalary >= 20250 && monthlySalary <= 20750) {
            return 922.50;
        } else if (monthlySalary >= 20750 && monthlySalary <= 21250) {
            return 945.00;
        } else if (monthlySalary >= 21250 && monthlySalary <= 21750) {
            return 967.50;
        } else if (monthlySalary >= 21750 && monthlySalary <= 22250) {
            return 990.00;
        } else if (monthlySalary >= 22250 && monthlySalary <= 22750) {
            return 1012.50;
        } else if (monthlySalary >= 22750 && monthlySalary <= 23250) {
            return 1035.00;
        } else if (monthlySalary >= 23250 && monthlySalary <= 23750) {
            return 1057.50;
        } else if (monthlySalary >= 23750 && monthlySalary <= 24250) {
            return 1080.00;
        } else if (monthlySalary >= 24250 && monthlySalary <= 24750) {
            return 1102.50;
        } else {
            return 1125.00;
        }
    }

    public static double philhealthContribution(double basicSalary) {
        if (basicSalary <= 10000) {
            return 300 / 2;
        } else if (basicSalary >= 60000) {
            return 1800 / 2;
        } else {
            return (basicSalary * 0.03) / 2;
        }
    }

    public static double pagibigContribution(double basicSalary) {
        double pagIbig;
        if (basicSalary <= 1500) {
            pagIbig = 0.01 * basicSalary;
        } else {
            pagIbig = 0.02 * basicSalary;
        }

        if (pagIbig < 100) {
            return pagIbig;
        } else {
            return 100;
        }
    }

    public static double withholdingTax(double taxeableIncome) {
        if (taxeableIncome >= 20833 && taxeableIncome < 33333) {
            return (taxeableIncome - 20833) * 0.2;
        } else if (taxeableIncome >= 33333 && taxeableIncome < 66667) {
            return 2500 + ((taxeableIncome - 33333) * 0.25);
        } else if (taxeableIncome >= 66667 && taxeableIncome < 166667) {
            return 10833 + ((taxeableIncome - 66667) * 0.3);
        } else if (taxeableIncome >= 166667 && taxeableIncome < 666667) {
            return 40833.33 + ((taxeableIncome - 166667) * 0.32);
        } else if (taxeableIncome >= 666667) {
            return 200833.33 + ((taxeableIncome - 666667) * 0.35);
        } else {
            return 0;
        }
    }

    public static double totalDeductions(double basicSalary, double monthlySalary) {
        double deductions = sssContribution(monthlySalary) + philhealthContribution(basicSalary) + pagibigContribution(basicSalary);
        double total = deductions + withholdingTax(monthlySalary - deductions);
        return total;
    }

    public static void SetDeductions(double monthlySalary, double basicSalary) {
        double deductions = sssContribution(monthlySalary) + philhealthContribution(basicSalary) + pagibigContribution(basicSalary);
        SSSContribution = String.valueOf(sssContribution(monthlySalary));
        PhilHealth = String.valueOf(philhealthContribution(basicSalary));
        PagIbig = String.valueOf(pagibigContribution(basicSalary));
        WithholdingTax = String.valueOf(withholdingTax(monthlySalary - deductions));
    }

    public static String getSSS() {
        return SSSContribution;
    }

    public static String getPhilHealth() {
        return PhilHealth;
    }

    public static String getPagIbig() {
        return PagIbig;
    }

    public static String getWithholding() {
        return WithholdingTax;
    }

}
