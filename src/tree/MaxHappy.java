package tree;

import java.util.List;

// http://poj.org/problem?id=2342
public class MaxHappy {

    static class Employee {
        int happy;
        List<Employee> nexts;

        public Employee(int happy, List<Employee> nexts) {
            this.happy = happy;
            this.nexts = nexts;
        }
    }

    public static int maxHappy(Employee boss) {
        Info info = process(boss);
        return Math.max(info.yes, info.no);
    }

    public static Info process(Employee emp) {
        if (emp == null) return new Info(0, 0);

        int yes = emp.happy, no = 0;

        for (Employee e : emp.nexts) {
            Info nextInfo = process(e);
            yes += nextInfo.no;
            no += Math.max(nextInfo.yes, nextInfo.no);
        }

        return new Info(yes, no);
    }

    static class Info {
        int yes;
        int no;

        public Info(int yes, int no) {
            this.yes = yes;
            this.no = no;
        }
    }
}
