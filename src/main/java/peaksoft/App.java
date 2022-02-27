package peaksoft;

import org.hibernate.Session;
import peaksoft.entity.Employee;
import peaksoft.util.HibernateUtil;

import javax.persistence.Query;
import java.util.List;

/**
 ** Аты Аза жана жашы 20дан чон болгон баардык жумушчуларды алыныз.
 *  Аты Аза болгон жумушчулардын жашын 18ге озгортунуз.
 *  Аты Аза болгон жумушчуларды очурунуз.
 */
public class App {
    public static void main(String[] args) {
        HibernateUtil.getSession();
        Employee employee = new Employee("Aza","Rinatov",25);
//       create(employee);
        getAllEmployees();
//        updateEmployee("Aza",20);
//        delete("Aza");
    }

    public static int create(Employee employee) {
        Session session = HibernateUtil.getSession().openSession();
        session.beginTransaction();
        session.save(employee);
        session.getTransaction().commit();
        session.close();
        System.out.println("Successfully added : " + employee);
        return employee.getId();
    }

    public static List<Employee> getAllEmployees() {
        Session session = HibernateUtil.getSession().openSession();
        session.beginTransaction();
        List<Employee> employeeList = session.createQuery("from Employee ").getResultList();
        session.getTransaction().commit();
        System.out.println("Finded: " +  employeeList+ " employees ");
        return employeeList;
    }

    public static void getEmployeeByNameAndAge(int age,String name) {
        String sql = "FROM Employee WHERE age >:age and name =:name";
        Session session = HibernateUtil.getSession().openSession();
        session.beginTransaction();
        Query query = session.createQuery(sql)
                .setParameter("age", age)
                .setParameter("name", name);
        List<Employee> employee = query.getResultList();
        for (Employee e : employee) {
            System.out.println(e.toString());
        }
        session.getTransaction().commit();
        session.close();

    }

        public static void updateEmployee(String name,int age) {
        System.out.println("HQL UPDATE: ");
        Session session=HibernateUtil.getSession().openSession();
        session.beginTransaction();
        String SQL = "update Employee set age = :age" +
                " where name= :name";
        Query query=session.createQuery(SQL);
       query.setParameter("name",name);
       query.setParameter("age",age);
        int result = query.executeUpdate();
        session.getTransaction().commit();
        session.close();
       System.out.println("Result: " + result);
    }

    public static void delete(String name) {
        Session session = HibernateUtil.getSession().openSession();
        session.beginTransaction();
        Query SQL =session.createQuery("delete from Employee where name= :name") ;
        SQL.setParameter("name",name);
        SQL.executeUpdate();
        session.getTransaction().commit();
        session.close();
        System.out.println("Successfully deleted " + name);
    }
}
