class Student {
    private String numarMatricol;
    private String prenume;
    private String nume;
    private String formatieDeStudiu;

    public Student(String numarMatricol, String prenume, String nume, String formatieDeStudiu) {
        this.numarMatricol = numarMatricol;
        this.prenume = prenume;
        this.nume = nume;
        this.formatieDeStudiu = formatieDeStudiu;
    }

    public String toString() {
        return "Student " + numarMatricol + " " + prenume + " " + nume + " " + formatieDeStudiu + "";
    }
}
    public class Main {
        public void main(String[] args) {

            Student student1 = new Student("500", "Florin", "Popescu", "ISM");
            System.out.println(student1);
        }
    }




