import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class Student {
    protected int numarMatricol;
    protected String prenume;
    protected String nume;
    protected String formatieDeStudiu;
    protected double nota;

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


public class Main {

    public static void main(String[] args) {

        List<Student> studentiCuNote = Arrays.asList(
                new Student(1025, "Andrei", "Popa", "ISM141/2", 8.70),
                new Student(1024, "Ioan", "Mihalcea", "ISM141/1", 10.0),
                new Student(1026, "Anamaria", "Prodan", "TI131/1", 8.90),
                new Student(1029, "Bianca", "Popescu", "TI131/1", 10.0),
                new Student(1029, "Maria", "Pana", "TI131/2", 4.10),
                new Student(1029, "Gabriela", "Mohanu", "TI131/2", 7.33),
                new Student(1029, "Marius", "Nasta", "TI131/2", 3.20),
                new Student(1029, "Marius", "Nasta", "TI131/1", 5.12),
                new Student(1029, "Andrei", "Dobrescu", "TI131/2", 2.22)
        );

        System.out.println("\na)");
        studentiCuNote.stream()
                .filter(s -> s.getNota() == 10.0)
                .forEach(System.out::println);


        System.out.println("\nb) ");
        studentiCuNote.stream()
                .filter(s -> s.getNota() < 5.0)
                .forEach(System.out::println);


        System.out.println("\nc) ");
        List<Student> studentiActualizati = studentiCuNote.stream()
                .map(s -> {

                    if (s.getNota() < 4.0) {
                        return new Student(s.getNumarMatricol(), s.getPrenume(), s.getNume(), s.getFormatieDeStudiu(), 4.0);
                    }
                    return s;
                })
                .collect(Collectors.toList());

        studentiActualizati.forEach(System.out::println);

        System.out.println("\nd)");
        double sumaNotelor = studentiActualizati.stream()
                .map(Student::getNota)
                .reduce(0.0, (a, b) -> a + b);

        System.out.println("Suma totală: " + String.format("%.2f", sumaNotelor));

        System.out.println("\ne)");
        double media = sumaNotelor / studentiActualizati.size();
        System.out.println("Media calculată: " + String.format("%.2f", media));
    }
}
