import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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

    public int getNumarMatricol() { return numarMatricol; }
    public String getPrenume() { return prenume; }
    public String getNume() { return nume; }
    public String getFormatieDeStudiu() { return formatieDeStudiu; }

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

    public String toCsv() {
        return numarMatricol + "," + prenume + "," + nume + "," + formatieDeStudiu;
    }
}

public class Main {

    public static void main(String[] args) {
        String fisierIn = "src/studenti_in.txt";
        String fisierOut = "src/student_in.txt";
        String fisierOutSorted = "src/studenti_out_sorted.txt";

        List<Student> listaStudenti = new ArrayList<>();


        try {
            List<String> linii = Files.readAllLines(Paths.get(fisierIn));

            for (String linie : linii) {
                if (linie.trim().isEmpty()) continue;

                String[] date = linie.split(",");
                if (date.length == 4) {
                    int nrMatricol = Integer.parseInt(date[0].trim());
                    Student s = new Student(nrMatricol, date[1].trim(), date[2].trim(), date[3].trim());
                    listaStudenti.add(s);
                    System.out.println(s);
                }
            }


            listaStudenti.sort(Comparator.comparing(Student::getNume));


            StringBuilder builder = new StringBuilder();
            for(Student s : listaStudenti) {
                builder.append(s.toCsv()).append(System.lineSeparator());
            }
            Files.writeString(Paths.get(fisierOut), builder.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);


            listaStudenti.sort(Comparator
                    .comparing(Student::getFormatieDeStudiu)
                    .thenComparing(Student::getNume));


            StringBuilder builderDublu = new StringBuilder();
            for(Student s : listaStudenti) {
                builderDublu.append(s.toCsv()).append(System.lineSeparator());
            }
            Files.writeString(Paths.get(fisierOutSorted), builderDublu.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
