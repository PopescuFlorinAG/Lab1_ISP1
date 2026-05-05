import java.util.Objects;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Student {
    protected int numarMatricol;
    protected String prenume;
    protected String nume;
    protected String formatieDeStudiu;
    protected double nota;

    public Student(int numarMatricol, String prenume, String nume, String formatieDeStudiu) {
        this.numarMatricol = numarMatricol;
        this.prenume = prenume;
        this.nume = nume;
        this.formatieDeStudiu = formatieDeStudiu;
        this.nota = 0.0;
    }

    public int getNumarMatricol() { return numarMatricol; }
    public String getPrenume() { return prenume; }
    public String getNume() { return nume; }
    public String getFormatieDeStudiu() { return formatieDeStudiu; }
    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }

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

class StudentBursier extends Student {
    private double cuantumBursa;

    public StudentBursier(int numarMatricol, String prenume, String nume, String formatieDeStudiu, double nota, double bursa) {
        super(numarMatricol, prenume, nume, formatieDeStudiu);
        super.nota = nota;
        this.cuantumBursa = bursa;
    }

    public double getCuantumBursa() {
        return cuantumBursa;
    }

    @Override
    public String toString() {
        String s = super.toString();
        s += String.format(" [ Bursa: %6.2f RON ]", cuantumBursa);
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StudentBursier that = (StudentBursier) o;
        return Double.compare(cuantumBursa, that.cuantumBursa) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cuantumBursa);
    }
}

class AplicatieCuBursa {

    public static void main(String[] args) {
        AplicatieCuBursa instanta = new AplicatieCuBursa();

        List<StudentBursier> lista = instanta.genereaza();
        System.out.println("Lista inainte de sortare:");
        for (StudentBursier student : lista) {
            System.out.println(student);
        }

        System.out.println("--------------------------------------------------");

        List<StudentBursier> sortata = instanta.sorteaza(lista);
        System.out.println("Lista DUPA sortare:");
        for (StudentBursier student : sortata) {
            System.out.println(student);
        }
    }

    public List<StudentBursier> genereaza() {
        List<StudentBursier> lista = new ArrayList<>();
        lista.add(new StudentBursier(1025, "Andrei", "Popa", "ISM141/2", 8.70, 725.50));
        lista.add(new StudentBursier(1024, "Ioan", "Mihalcea", "ISM141/1", 9.80, 801.10));
        lista.add(new StudentBursier(1029, "Bianca", "Popescu", "TI131/1", 9.10, 780.80));
        lista.add(new StudentBursier(1026, "Anamaria", "Prodan", "TI131/1", 8.90, 745.50));
        lista.add(new StudentBursier(1029, "Bianca", "Popescu", "TI131/1", 9.10, 100.00));
        return lista;
    }

    public List<StudentBursier> sorteaza(List<StudentBursier> lst) {
        lst.sort(Comparator.comparing(StudentBursier::getFormatieDeStudiu)
                .thenComparing(StudentBursier::getNume)
                .thenComparing(StudentBursier::getPrenume)
                .thenComparing(StudentBursier::getNota)
                .thenComparing(StudentBursier::getCuantumBursa));
        return lst;
    }
}

class AplicatieCuBursaTest {

    AplicatieCuBursa appCuBursa = new AplicatieCuBursa();

    @Test
    void testSorteaza() {
        // Arrange
        List<StudentBursier> listaInitiala = appCuBursa.genereaza();

        // Act
        List<StudentBursier> listaSortata = appCuBursa.sorteaza(listaInitiala);

        // Assert - parcurgem lista și comparăm elementele 2 câte 2
        for (int i = 0; i < listaSortata.size() - 1; i++) {
            StudentBursier s1 = listaSortata.get(i);
            StudentBursier s2 = listaSortata.get(i + 1);

            int cmpFormatie = s1.getFormatieDeStudiu().compareTo(s2.getFormatieDeStudiu());
            Assertions.assertTrue(cmpFormatie <= 0, "Eroare la formatie!");

            if (cmpFormatie == 0) {
                int cmpNume = s1.getNume().compareTo(s2.getNume());
                Assertions.assertTrue(cmpNume <= 0, "Eroare la nume!");

                if (cmpNume == 0) {
                    int cmpPrenume = s1.getPrenume().compareTo(s2.getPrenume());
                    Assertions.assertTrue(cmpPrenume <= 0, "Eroare la prenume!");

                    if (cmpPrenume == 0) {
                        int cmpNota = Double.compare(s1.getNota(), s2.getNota());
                        Assertions.assertTrue(cmpNota <= 0, "Eroare la nota!");

                        if (cmpNota == 0) {
                            int cmpBursa = Double.compare(s1.getCuantumBursa(), s2.getCuantumBursa());
                            Assertions.assertTrue(cmpBursa <= 0, "Eroare la cuantum bursa!");
                        }
                    }
                }
            }
        }
    }
}
