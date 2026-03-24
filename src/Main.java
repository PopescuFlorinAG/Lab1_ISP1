import java.util.Objects;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Student {
    private int numarMatricol;
    private String prenume;
    private String nume;
    private String formatieDeStudiu;

    public Student(int numarMatricol, String prenume, String nume, String formatieDeStudiu) {
        this.numarMatricol = numarMatricol;
        this.prenume = prenume;
        this.nume = nume;
        this.formatieDeStudiu = formatieDeStudiu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(prenume, student.prenume) &&
                Objects.equals(nume, student.nume) &&
                Objects.equals(formatieDeStudiu, student.formatieDeStudiu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prenume, nume, formatieDeStudiu);
    }
    @Override
    public String toString() {
        return "Student " + numarMatricol + " " + nume + " " + prenume + " " + formatieDeStudiu;
    }
}
    public class Main {
        public static boolean cautaStudent_O1(Set<Student> setStudenti, Student studentCautat) {
            return setStudenti.contains(studentCautat);
        }
        public static void main(String[] args) {

            Student s1 = new Student(532, "Florin", "Popescu", "ISM21/2");
            Student s2 = new Student(112, "Maria", "Popa", "TI21/1");
            Student s3 = new Student(120, "Alis", "Popa", "TI21/2");

            List<Student> listaStudenti = new ArrayList<>();

            listaStudenti.add(s1);
            listaStudenti.add(s2);
            listaStudenti.add(s3);

            System.out.println("a)");
            for (Student s : listaStudenti) {
                System.out.println(s);
            }
            System.out.println("b)");

            Set<Student> setStudenti_O1 = new HashSet<>(listaStudenti);
            Student studentC = new Student(112, "Maria", "Popa", "TI21/1");
            Student studentB = new Student(120, "Alis", "Popa", "TI21/2");


            boolean rezultatB = cautaStudent_O1(setStudenti_O1, studentB);
            boolean rezultatC = cautaStudent_O1(setStudenti_O1, studentC);
            System.out.println("gasit: " + rezultatB);
            System.out.println("gasit: " + rezultatC);
        }
    }




