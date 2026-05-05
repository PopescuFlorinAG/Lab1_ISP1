import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

final class Student {


    private final int numarMatricol;
    private final String prenume;
    private final String nume;
    private final String formatieDeStudiu;
    private final double nota;

    public Student(int numarMatricol, String prenume, String nume, String formatieDeStudiu, double nota) {
        this.numarMatricol = numarMatricol;
        this.prenume = prenume;
        this.nume = nume;
        this.formatieDeStudiu = formatieDeStudiu;
        this.nota = nota;
    }

    public int getNumarMatricol() { return numarMatricol; }
    public String getPrenume() { return prenume; }
    public String getNume() { return nume; }
    public String getFormatieDeStudiu() { return formatieDeStudiu; }
    public double getNota() { return nota; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return numarMatricol == student.numarMatricol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numarMatricol);
    }

    @Override
    public String toString() {
        return "Student " + numarMatricol + " | " + prenume + " " + nume + " | " + formatieDeStudiu + " | Nota: " + nota;
    }
}

class Main {

    static Student schimbaFormatia(Student st, String nouaFormatieDeStudiu) {
        return new Student(
                st.getNumarMatricol(),
                st.getPrenume(),
                st.getNume(),
                nouaFormatieDeStudiu,
                st.getNota()
        );
    }


    static Set<Student> imparteInDouaFormatii(Set<Student> studenti, String formatia1, String formatia2) {
        Set<Student> formatieNoua = new HashSet<>();


        int limita = (studenti.size() + 1) / 2;
        int contor = 0;

        for (Student s : studenti) {
            if (contor < limita) {
                formatieNoua.add(schimbaFormatia(s, formatia1));
            } else {
                formatieNoua.add(schimbaFormatia(s, formatia2));
            }
            contor++;
        }

        return formatieNoua;
    }

    public static void main(String[] args) {
        Set<Student> listaInitiala = new HashSet<>();


        listaInitiala.add(new Student(1024, "Ioan", "Mihalcea", "Veche", 9.80));
        listaInitiala.add(new Student(1025, "Andrei", "Popa", "Veche", 8.70));
        listaInitiala.add(new Student(1026, "Anamaria", "Prodan", "Veche", 8.90));
        listaInitiala.add(new Student(1029, "Bianca", "Popescu", "Veche", 9.10));
        listaInitiala.add(new Student(1030, "Mihai", "Ionescu", "Veche", 7.50));

        System.out.println("--- LISTA INAINTE DE IMPARTIRE ---");
        for (Student s : listaInitiala) {
            System.out.println(s);
        }


        Set<Student> listaImpartita = imparteInDouaFormatii(listaInitiala, "TI 211_1", "TI 211_2");

        System.out.println("\n--- LISTA DUPA IMPARTIREA IN CELE DOUA GRUPE ---");
        for (Student s : listaImpartita) {
            System.out.println(s);
        }
    }
}
