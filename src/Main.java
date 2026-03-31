import java.util.Objects;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

 class Student {
    private int numarMatricol;
    private String prenume;
    private String nume;
    private String formatieDeStudiu;
    private float nota;

    public Student(int numarMatricol, String prenume, String nume, String formatieDeStudiu) {
        this.numarMatricol = numarMatricol;
        this.prenume = prenume;
        this.nume = nume;
        this.formatieDeStudiu = formatieDeStudiu;
        this.nota = 0.0f;
    }

    public int getNumarMatricol() { return numarMatricol; }
    public String getPrenume() { return prenume; }
    public String getNume() { return nume; }
    public String getFormatieDeStudiu() { return formatieDeStudiu; }

    public float getNota() { return nota; }
    public void setNota(float nota) { this.nota = nota; }

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

    public static float gasesteNota(String prenume, String nume, Map<Integer, Student> tineri) {
        Map<String, Student> mapDupaNume = new HashMap<>();

        for (Student s : tineri.values()) {
            String cheieNume = s.getPrenume() + "-" + s.getNume();
            mapDupaNume.put(cheieNume, s);
        }

        String cheieCautata = prenume + "-" + nume;
        Student studentGasit = mapDupaNume.get(cheieCautata);

        if (studentGasit != null) {
            return studentGasit.getNota();
        }

        return 0.0f;
    }

    public static void main(String[] args) {
        String fisierStudenti = "src/studenti_in.txt";
        String fisierNote = "src/note_anon.txt";

        Map<Integer, Student> mapStudenti = new HashMap<>();

        try {
            List<String> liniiStudenti = Files.readAllLines(Paths.get(fisierStudenti));
            for (String linie : liniiStudenti) {
                if(linie.trim().isEmpty()) continue;
                String[] date = linie.split(",");
                if(date.length == 4) {
                    int nrM = Integer.parseInt(date[0].trim());
                    Student s = new Student(nrM, date[1].trim(), date[2].trim(), date[3].trim());
                    mapStudenti.put(nrM, s);
                }
            }

            List<String> liniiNote = Files.readAllLines(Paths.get(fisierNote));
            for (String linie : liniiNote) {
                if(linie.trim().isEmpty()) continue;
                String[] notare = linie.split(",");
                if(notare.length == 2) {
                    int idCautat = Integer.parseInt(notare[0].trim());
                    float notaGasita = Float.parseFloat(notare[1].trim());

                    Student DinMap = mapStudenti.get(idCautat);
                    if (DinMap != null) {
                        DinMap.setNota(notaGasita);
                    }
                }
            }
            for (Student student : mapStudenti.values()) {
                System.out.println(student);
            }

            float notaM = gasesteNota("Bianca", "Popescu", mapStudenti);
            float notaN = gasesteNota("Florin", "Popescu", mapStudenti);

            System.out.println("Bianca Popescu: " + notaM);
            System.out.println("Florin Popescu: " + notaN);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
